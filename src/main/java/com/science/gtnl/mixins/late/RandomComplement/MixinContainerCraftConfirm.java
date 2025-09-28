package com.science.gtnl.mixins.late.RandomComplement;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.science.gtnl.Utils.RCAEBaseContainer;

import appeng.container.implementations.ContainerCraftConfirm;

@Mixin(value = ContainerCraftConfirm.class, remap = false)
public abstract class MixinContainerCraftConfirm implements RCAEBaseContainer {

    @Shadow
    public abstract void switchToOriginalGUI();

    @Redirect(
        method = "startJob(Z)V",
        at = @At(
            value = "INVOKE",
            target = "Lappeng/container/implementations/ContainerCraftConfirm;switchToOriginalGUI()V"))
    public void startJob0(ContainerCraftConfirm instance) {
        if (this.rc$getOldContainer() == null) {
            this.switchToOriginalGUI();
        }
    }
}
