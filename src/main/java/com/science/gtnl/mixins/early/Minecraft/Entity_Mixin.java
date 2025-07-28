package com.science.gtnl.mixins.early.Minecraft;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.common.item.TimeStopManager;

@Mixin(value = Entity.class)
public class Entity_Mixin {

    @Inject(method = "moveEntity", at = @At("HEAD"), cancellable = true)
    public void mixin$moveEntity(double x, double y, double z, CallbackInfo ci) {
        if (TimeStopManager.isTimeStopped() && !(((Entity) ((Object) this)) instanceof EntityPlayer)) {
            ci.cancel();
        }
    }
}
