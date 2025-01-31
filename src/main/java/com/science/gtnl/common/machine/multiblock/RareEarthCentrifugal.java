package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaBlockCasing;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTUtility.validMTEList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.Utils.item.TextUtils;
import com.science.gtnl.common.machine.multiMachineClasses.MultiMachineBase;
import com.science.gtnl.common.recipe.RecipeRegister;

import gregtech.api.GregTechAPI;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MTEHatchMaintenance;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.common.blocks.BlockCasings3;
import gtPlusPlus.core.block.ModBlocks;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock;

public class RareEarthCentrifugal extends MultiMachineBase<RareEarthCentrifugal> implements ISurvivalConstructable {

    public int mCasing;
    public static IStructureDefinition<RareEarthCentrifugal> STRUCTURE_DEFINITION = null;
    public static final String STRUCTURE_PIECE_MAIN = "main";
    public static String[][] shape;
    public static final String REC_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/rare_earth_centrifugal";
    public final int horizontalOffSet = 2;
    public final int verticalOffSet = 2;
    public final int depthOffSet = 0;
    public static final int CASING_INDEX = ((BlockCasings3) GregTechAPI.sBlockCasings3).getTextureIndex(12);

    public RareEarthCentrifugal(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        shape = StructureUtils.readStructureFromFile(REC_STRUCTURE_FILE_PATH);
    }

    public RareEarthCentrifugal(String aName) {
        super(aName);
        shape = StructureUtils.readStructureFromFile(REC_STRUCTURE_FILE_PATH);
    }

    @Override
    public boolean isEnablePerfectOverclock() {
        return false;
    }

    @Override
    public float getSpeedBonus() {
        return 1;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new RareEarthCentrifugal(this.mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(TexturesGtBlock.oMCDIndustrialThermalCentrifugeActive)
                    .extFacing()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(TexturesGtBlock.oMCDIndustrialThermalCentrifuge)
                    .extFacing()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    public int getCasingTextureID() {
        return CASING_INDEX;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeRegister.RareEarthCentrifugalRecipes;
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.RareEarthCentrifugalRecipeType)
            .addInfo(TextLocalization.Tooltip_LargeCircuitAssembler_00)
            .addInfo(TextLocalization.Tooltip_LargeCircuitAssembler_01)
            .addInfo(TextLocalization.Tooltip_LargeCircuitAssembler_02)
            .addInfo(TextLocalization.Tooltip_LargeCircuitAssembler_03)
            .addInfo(TextLocalization.Tooltip_LargeCircuitAssembler_04)
            .addInfo(TextLocalization.Tooltip_LargeCircuitAssembler_05)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(5, 5, 5, true)
            .addInputHatch(TextLocalization.Tooltip_RareEarthCentrifugal_Casing)
            .addOutputHatch(TextLocalization.Tooltip_RareEarthCentrifugal_Casing)
            .addInputBus(TextLocalization.Tooltip_RareEarthCentrifugal_Casing)
            .addOutputBus(TextLocalization.Tooltip_RareEarthCentrifugal_Casing)
            .addEnergyHatch(TextLocalization.Tooltip_RareEarthCentrifugal_Casing)
            .addMaintenanceHatch(TextLocalization.Tooltip_RareEarthCentrifugal_Casing)
            .toolTipFinisher(TextUtils.SNL + TextUtils.SQY);
        return tt;
    }

    @Override
    public IStructureDefinition<RareEarthCentrifugal> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<RareEarthCentrifugal>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofBlock(MetaBlockCasing, 4))
                .addElement('B', ofBlock(MetaBlockCasing, 12))
                .addElement(
                    'C',
                    buildHatchAdder(RareEarthCentrifugal.class).casingIndex(CASING_INDEX)
                        .dot(1)
                        .atLeast(InputHatch, OutputHatch, InputBus, OutputBus, Maintenance, Energy)
                        .buildAndChain(
                            onElementPass(x -> ++x.mCasing, ofBlock(ModBlocks.blockSpecialMultiCasings, 11))))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, horizontalOffSet, verticalOffSet, depthOffSet);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (mMachine) return -1;
        return survivialBuildPiece(
            STRUCTURE_PIECE_MAIN,
            stackSize,
            horizontalOffSet,
            verticalOffSet,
            depthOffSet,
            elementBudget,
            env,
            false,
            true);
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        mCasing = 0;

        if (!checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet) && checkHatch()) {
            return false;
        }

        if (this.mEnergyHatches.size() > 2) return false;
        return mCasing >= 75;
    }

    @Override
    public int getMaxParallelRecipes() {
        int tier = Math.max(0, GTUtility.getTier(this.getMaxInputVoltage() - 6));
        return 4 + 4 * tier;
    }

    @Override
    public boolean getDefaultHasMaintenanceChecks() {
        return true;
    }

    @Override
    public boolean shouldCheckMaintenance() {
        return true;
    }

    @Override
    public void checkMaintenance() {
        if (!shouldCheckMaintenance()) return;

        if (getRepairStatus() != getIdealStatus()) {
            for (MTEHatchMaintenance tHatch : validMTEList(mMaintenanceHatches)) {
                if (tHatch.mAuto) tHatch.autoMaintainance();
                if (tHatch.mWrench) mWrench = true;
                if (tHatch.mScrewdriver) mScrewdriver = true;
                if (tHatch.mSoftHammer) mSoftHammer = true;
                if (tHatch.mHardHammer) mHardHammer = true;
                if (tHatch.mSolderingTool) mSolderingTool = true;
                if (tHatch.mCrowbar) mCrowbar = true;

                tHatch.mWrench = false;
                tHatch.mScrewdriver = false;
                tHatch.mSoftHammer = false;
                tHatch.mHardHammer = false;
                tHatch.mSolderingTool = false;
                tHatch.mCrowbar = false;
            }
        }
    }
}
