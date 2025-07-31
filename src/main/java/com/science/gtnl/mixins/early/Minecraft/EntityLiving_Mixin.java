package com.science.gtnl.mixins.early.Minecraft;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.science.gtnl.common.entity.EntityPlayerLeashKnot;

@Mixin(value = EntityLiving.class)
public class EntityLiving_Mixin extends EntityLivingBase {

    @Shadow
    private Entity leashedToEntity;

    public EntityLiving_Mixin(World p_i1594_1_) {
        super(p_i1594_1_);
    }

    @Redirect(
        method = "clearLeashed",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/EntityLiving;dropItem(Lnet/minecraft/item/Item;I)Lnet/minecraft/entity/item/EntityItem;"))
    private EntityItem cancelLeadDropIfPlayerLeash(EntityLiving self, Item item, int amount) {
        if (this.leashedToEntity instanceof EntityPlayerLeashKnot) {
            return null;
        } else {
            return self.dropItem(item, amount);
        }
    }

    @Override
    public ItemStack getHeldItem() {
        return null;
    }

    @Override
    public ItemStack getEquipmentInSlot(int p_71124_1_) {
        return null;
    }

    @Override
    public void setCurrentItemOrArmor(int slotIn, ItemStack itemStackIn) {

    }

    @Override
    public ItemStack[] getLastActiveItems() {
        return new ItemStack[0];
    }
}
