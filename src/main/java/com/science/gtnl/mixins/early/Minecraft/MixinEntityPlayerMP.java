package com.science.gtnl.mixins.early.Minecraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;

import com.mojang.authlib.GameProfile;
import com.science.gtnl.utils.PlayerHealData;

@Mixin(EntityPlayerMP.class)
public abstract class MixinEntityPlayerMP extends EntityPlayer {

    public MixinEntityPlayerMP(World p_i45324_1_, GameProfile p_i45324_2_) {
        super(p_i45324_1_, p_i45324_2_);
    }

    @Override
    public void setHealth(float health) {
        EntityPlayerMP player = (EntityPlayerMP) (Object) this;
        if (health > 0 && PlayerHealData.getBonus(player)) {
            health = Math.min(player.getMaxHealth(), health + 1);
        }
        this.dataWatcher.updateObject(6, MathHelper.clamp_float(health, 0.0F, this.getMaxHealth()));
    }

}
