package com.science.gtnl.mixins.late.RandomComplement;

import appeng.api.storage.ITerminalHost;
import appeng.container.implementations.ContainerMEMonitorable;
import com.science.gtnl.Utils.RCAEBaseContainer;
import com.science.gtnl.Utils.RCWirelessTerminalGuiObject;
import net.minecraft.entity.player.InventoryPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ContainerMEMonitorable.class,remap = false)
public class MixinContainerMEMonitorable implements RCAEBaseContainer {

    @Unique
    private int gtnl$slot;

    @Unique
    private boolean gtnl$isBauble;

    @Unique
    private boolean gtnl$isSpecial;

    @Inject(method = "<init>(Lnet/minecraft/entity/player/InventoryPlayer;Lappeng/api/storage/ITerminalHost;Z)V",at = @At("TAIL"))
    public void onInit(InventoryPlayer ip, ITerminalHost monitorable, boolean bindInventory, CallbackInfo ci){
        if (monitorable instanceof RCWirelessTerminalGuiObject w){
            gtnl$slot = w.getInventorySlot();
            gtnl$isBauble = w.isBauble();
            gtnl$isSpecial = w.isSpecial();
        }
    }

    @Unique
    @Override
    public int rc$getInventorySlot() {
        return gtnl$slot;
    }

    @Unique
    @Override
    public boolean rc$isBauble() {
        return gtnl$isBauble;
    }

    @Unique
    @Override
    public boolean rc$isSpecial() {
        return gtnl$isSpecial;
    }
}
