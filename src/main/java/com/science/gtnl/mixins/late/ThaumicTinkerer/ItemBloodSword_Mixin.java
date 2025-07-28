package com.science.gtnl.mixins.late.ThaumicTinkerer;

import static com.reavaritia.common.item.InfinitySword.cancelBloodSword;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import thaumic.tinkerer.common.item.ItemBloodSword;

@Mixin(value = ItemBloodSword.EventHandler.class, remap = false)
public abstract class ItemBloodSword_Mixin {

    @Unique
    private static int GTNotLeisure$handleNext = 0;

    /**
     * @reason Overwrites the original ItemBloodSword#onDamageTaken method.
     *         When cancelBloodSword is true (e.g., the player is holding the InfinitySword),
     *         this method prevents the BloodSword from triggering its block and retaliation effects.
     * @author GTNotLeisure
     */
    @Overwrite
    @SubscribeEvent
    public void onDamageTaken(LivingAttackEvent event) {
        if (!cancelBloodSword) {
            if (event.entity == null) return;

            if (event.entity.worldObj.isRemote) return;

            boolean handle = GTNotLeisure$handleNext == 0;
            if (!handle) GTNotLeisure$handleNext--;

            if (event.entityLiving instanceof EntityPlayer && handle) {
                EntityPlayer player = (EntityPlayer) event.entityLiving;
                ItemStack itemInUse = player.itemInUse;
                if (itemInUse != null && itemInUse.getItem() instanceof ItemBloodSword) {

                    event.setCanceled(true);
                    GTNotLeisure$handleNext = 3;
                    player.attackEntityFrom(DamageSource.magic, 3);
                    return;
                }
            }

            if (handle) {
                Entity source = event.source.getSourceOfDamage();
                if (source instanceof EntityLivingBase) {
                    EntityLivingBase attacker = (EntityLivingBase) source;
                    ItemStack itemInUse = attacker.getHeldItem();
                    if (itemInUse != null && itemInUse.getItem() instanceof ItemBloodSword) {
                        attacker.attackEntityFrom(DamageSource.magic, 2);
                    }
                }
            }
        }
    }
}
