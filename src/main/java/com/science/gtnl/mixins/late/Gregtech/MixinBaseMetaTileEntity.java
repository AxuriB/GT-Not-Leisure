package com.science.gtnl.mixins.late.Gregtech;

import net.minecraft.util.AxisAlignedBB;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.science.gtnl.api.ITileEntityTickAcceleration;
import com.science.gtnl.common.machine.multiblock.MeteorMiner;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.HighPerformanceComputationArray;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.KuangBiaoOneGiantNuclearFusionReactor;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.RocketAssembler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.metatileentity.CommonBaseMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;

@Mixin(value = BaseMetaTileEntity.class, remap = false)
public abstract class MixinBaseMetaTileEntity extends CommonBaseMetaTileEntity implements ITileEntityTickAcceleration {

    @Shadow
    protected MetaTileEntity mMetaTileEntity;

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        if (mMetaTileEntity instanceof MeteorMiner || mMetaTileEntity instanceof HighPerformanceComputationArray
            || mMetaTileEntity instanceof KuangBiaoOneGiantNuclearFusionReactor
            || mMetaTileEntity instanceof RocketAssembler) {
            return AxisAlignedBB.getBoundingBox(
                this.xCoord - 256,
                this.yCoord - 256,
                this.zCoord - 256,
                this.xCoord + 256,
                this.yCoord + 256,
                this.zCoord + 256);
        }
        return super.getRenderBoundingBox();
    }
}
