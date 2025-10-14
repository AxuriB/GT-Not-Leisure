package com.science.gtnl.mixins.late.Gregtech;

import org.spongepowered.asm.mixin.Mixin;

import gregtech.api.metatileentity.implementations.MTEHatchOutputBus;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.MTEHatchSteamBusOutput;

@Mixin(value = MTEHatchSteamBusOutput.class)
public abstract class MixinMTEHatchSteamBusOutput extends MTEHatchOutputBus {

    public MixinMTEHatchSteamBusOutput(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier);
    }

    @Override
    public boolean pushOutputInventory() {
        return true;
    }
}
