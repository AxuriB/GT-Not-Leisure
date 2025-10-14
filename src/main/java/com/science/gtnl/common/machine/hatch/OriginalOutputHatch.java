package com.science.gtnl.common.machine.hatch;

import net.minecraft.util.StatCollector;

import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.MTEHatchOutput;
import gregtech.api.util.GTUtility;

public class OriginalOutputHatch extends MTEHatchOutput {

    public OriginalOutputHatch(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional, 0);
    }

    public OriginalOutputHatch(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new OriginalOutputHatch(mName, mTier, mDescriptionArray, mTextures);
    }

    @Override
    public String[] getDescription() {
        return new String[] { StatCollector.translateToLocal("Tooltip_OriginalOutputHatch_00"),
            StatCollector
                .translateToLocalFormatted("Tooltip_OriginalOutputHatch_01", GTUtility.formatNumbers(getCapacity())),
            StatCollector.translateToLocal("Tooltip_OriginalOutputHatch_02"),
            StatCollector.translateToLocal("Tooltip_OriginalOutputHatch_03"),
            StatCollector.translateToLocal("Tooltip_OriginalOutputHatch_04") };
    }

    @Override
    public boolean doesFillContainers() {
        return false;
    }

    @Override
    public int getCapacity() {
        return 8000;
    }
}
