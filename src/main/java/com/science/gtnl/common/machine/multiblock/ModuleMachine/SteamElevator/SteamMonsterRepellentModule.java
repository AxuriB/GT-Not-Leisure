package com.science.gtnl.common.machine.multiblock.ModuleMachine.SteamElevator;

import static gregtech.api.enums.GTValues.V;

import javax.annotation.Nonnull;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;

import gregtech.GTMod;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.shutdown.ShutDownReason;

public class SteamMonsterRepellentModule extends SteamElevatorModule {

    public int mRange;

    public SteamMonsterRepellentModule(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier);
        mRange = (int) Math.pow(2, 5 + aTier);
    }

    public SteamMonsterRepellentModule(String aName, int aTier) {
        super(aName, aTier);
        mRange = (int) Math.pow(2, 5 + aTier);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new SteamMonsterRepellentModule(this.mName, this.mTier);
    }

    @Override
    public String getMachineType() {
        return StatCollector.translateToLocal("SteamMonsterRepellentModuleRecipeType");
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        final MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(StatCollector.translateToLocal("SteamMonsterRepellentModuleRecipeType"));
        switch (mTier) {
            case 1:
                tt.addInfo(StatCollector.translateToLocal("Tooltip_SteamMonsterRepellentModuleI_00"));
                break;
            case 2:
                tt.addInfo(StatCollector.translateToLocal("Tooltip_SteamMonsterRepellentModuleII_00"));
                break;
            case 3:
                tt.addInfo(StatCollector.translateToLocal("Tooltip_SteamMonsterRepellentModuleIII_00"));
                break;
        }
        tt.addInfo(StatCollector.translateToLocal("Tooltip_SteamMonsterRepellentModule_00"))
            .addInfo(StatCollector.translateToLocal("Tooltip_SteamMonsterRepellentModule_01"))
            .addInfo(StatCollector.translateToLocal("Tooltip_SteamMonsterRepellentModule_02"))
            .addInfo(
                StatCollector.translateToLocal("Tooltip_SteamMonsterRepellentModule_03") + (int) Math.pow(2, 5 + mTier))
            .addSeparator()
            .addInfo(StatCollector.translateToLocal("StructureTooComplex"))
            .addInfo(StatCollector.translateToLocal("BLUE_PRINT_INFO"))
            .beginStructureBlock(1, 5, 2, false)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public ProcessingLogic createProcessingLogic() {
        return null;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("mRange", mRange);
    }

    @Override
    public void loadNBTData(final NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        mRange = aNBT.getInteger("mRange");
    }

    @Override
    @NotNull
    public CheckRecipeResult checkProcessing() {
        this.lEUt = mTier * V[3];
        this.mEfficiency = 10000;
        this.mMaxProgresstime = 1000;
        IGregTechTileEntity tileEntity = getBaseMetaTileEntity();
        if (tileEntity.isAllowedToWork() && tileEntity.isServerSide()) {
            int x = tileEntity.getXCoord();
            int y = tileEntity.getYCoord();
            int z = tileEntity.getZCoord();
            World world = tileEntity.getWorld();
            GTMod.gregtechproxy.spawnEventHandler.putRepellent(world.provider.dimensionId, x, y, z, mRange);
        }
        return CheckRecipeResultRegistry.SUCCESSFUL;
    }

    @Override
    public void stopMachine(@Nonnull ShutDownReason reason) {
        final IGregTechTileEntity mte = this.getBaseMetaTileEntity();
        if (mte.isServerSide()) {
            GTMod.gregtechproxy.spawnEventHandler.removeRepellent(
                mte.getWorld().provider.dimensionId,
                mte.getXCoord(),
                mte.getYCoord(),
                mte.getZCoord());
        }
        super.stopMachine(reason);
    }

    @Override
    public void onRemoval() {
        final IGregTechTileEntity mte = this.getBaseMetaTileEntity();
        if (mte.isServerSide()) {
            GTMod.gregtechproxy.spawnEventHandler.removeRepellent(
                mte.getWorld().provider.dimensionId,
                mte.getXCoord(),
                mte.getYCoord(),
                mte.getZCoord());
        }
        super.onRemoval();
    }

    @Override
    public void onUnload() {
        final IGregTechTileEntity mte = this.getBaseMetaTileEntity();
        if (mte.isServerSide()) {
            GTMod.gregtechproxy.spawnEventHandler.removeRepellent(
                mte.getWorld().provider.dimensionId,
                mte.getXCoord(),
                mte.getYCoord(),
                mte.getZCoord());
        }
        super.onUnload();
    }

    @Override
    protected int getMachineEffectRange() {
        return mRange;
    }
}
