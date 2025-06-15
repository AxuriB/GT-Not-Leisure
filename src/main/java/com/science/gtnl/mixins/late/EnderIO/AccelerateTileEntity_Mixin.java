package com.science.gtnl.mixins.late.EnderIO;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import com.enderio.core.common.TileEntityEnder;
import com.science.gtnl.api.ITileEntityTickAcceleration;

@SuppressWarnings("UnusedMixin")
@Mixin(value = TileEntityEnder.class, remap = false)
public abstract class AccelerateTileEntity_Mixin implements ITileEntityTickAcceleration {

    @Shadow
    private long lastUpdate;

    @Shadow(remap = true)
    public abstract void updateEntity();

    @Unique
    private int GTNotLeisure$tickAcceleratedRate = 1;

    @Override
    @SuppressWarnings("AddedMixinMembersNamePattern")
    public int getTickAcceleratedRate() {
        return this.GTNotLeisure$tickAcceleratedRate;
    }

    @Override
    @SuppressWarnings("AddedMixinMembersNamePattern")
    public boolean tickAcceleration(int tickAcceleratedRate) {
        this.GTNotLeisure$tickAcceleratedRate = tickAcceleratedRate;
        for (int i = 0; i < tickAcceleratedRate; i++) {
            this.lastUpdate = -1L; // make sure updateEntity() be called
            this.updateEntity();
        }
        return true;
    }
}
