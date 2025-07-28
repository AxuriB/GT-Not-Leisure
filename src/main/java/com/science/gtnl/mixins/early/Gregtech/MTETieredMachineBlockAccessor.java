package com.science.gtnl.mixins.early.Gregtech;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import gregtech.api.metatileentity.implementations.MTETieredMachineBlock;

@Mixin(value = MTETieredMachineBlock.class, remap = false)
public interface MTETieredMachineBlockAccessor {

    @Accessor("mTier")
    byte getMachineTier();

    @Accessor("mTier")
    void setMachineTier(byte mTier);

}
