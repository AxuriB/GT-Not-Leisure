package com.science.gtnl.mixins.late.RandomComplement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.llamalad7.mixinextras.sugar.Local;
import com.science.gtnl.ScienceNotLeisure;
import com.science.gtnl.Utils.RCAEBaseContainer;
import com.science.gtnl.client.GTNLInputHandler;
import com.science.gtnl.common.packet.ContainerRollBACK;

import appeng.client.gui.implementations.GuiCraftConfirm;
import appeng.core.sync.AppEngPacket;
import appeng.core.sync.network.NetworkHandler;

@Mixin(value = GuiCraftConfirm.class, remap = false)
public abstract class MixinGuiCraftConfirm {

    @Shadow
    private GuiButton start;

    @Shadow
    private GuiButton startWithFollow;

    @Shadow
    public abstract void switchToOriginalGUI();

    @Redirect(
        method = "actionPerformed",
        at = @At(
            value = "INVOKE",
            target = "Lappeng/client/gui/implementations/GuiCraftConfirm;switchToOriginalGUI()V"))
    public void onActionPerformed0(GuiCraftConfirm instance) {
        GuiScreen oldGui;
        if ((oldGui = GTNLInputHandler.oldGui) != null) {
            Minecraft.getMinecraft()
                .displayGuiScreen(oldGui);
            ScienceNotLeisure.network.sendToServer(new ContainerRollBACK());
            var player = Minecraft.getMinecraft().thePlayer;
            var newContainer = player.openContainer;
            if (newContainer instanceof RCAEBaseContainer rac) {
                var gtnl$oldContainer = rac.rc$getOldContainer();
                if (gtnl$oldContainer != null) {
                    final int w = player.openContainer.windowId % 100 + 1;
                    player.openContainer = gtnl$oldContainer;
                    player.openContainer.windowId = w;
                }
            }
            return;
        }
        this.switchToOriginalGUI();
    }

    @Redirect(
        method = "actionPerformed",
        at = @At(
            value = "INVOKE",
            target = "Lappeng/core/sync/network/NetworkHandler;sendToServer(Lappeng/core/sync/AppEngPacket;)V"))
    public void onActionPerformed1(NetworkHandler instance, AppEngPacket message, @Local(name = "btn") GuiButton btn) {
        instance.sendToServer(message);
        if (btn == this.start || btn == this.startWithFollow) {
            GuiScreen oldGui;
            if ((oldGui = GTNLInputHandler.oldGui) != null) {
                GTNLInputHandler.delayMethod = () -> Minecraft.getMinecraft()
                    .displayGuiScreen(oldGui);
                ScienceNotLeisure.network.sendToServer(new ContainerRollBACK());
            }
        }
    }
}
