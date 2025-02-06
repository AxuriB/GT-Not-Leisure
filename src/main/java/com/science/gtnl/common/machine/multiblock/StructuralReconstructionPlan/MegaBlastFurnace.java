package com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE_ACTIVE;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE_ACTIVE_GLOW;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE_GLOW;
import static gregtech.api.util.GTStructureUtility.*;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.Utils.item.TextUtils;
import com.science.gtnl.common.machine.multiMachineClasses.GTMMultiMachineBase;

import bartworks.util.BWUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import goodgenerator.loader.Loaders;
import gregtech.api.enums.HeatingCoilLevel;
import gregtech.api.enums.Materials;
import gregtech.api.enums.SoundResource;
import gregtech.api.enums.TAE;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import gregtech.common.blocks.BlockCasings8;
import gtPlusPlus.core.block.ModBlocks;

public class MegaBlastFurnace extends GTMMultiMachineBase<MegaBlastFurnace> implements ISurvivalConstructable {

    private HeatingCoilLevel mCoilLevel;
    private int mHeatingCapacity = 0;
    private static IStructureDefinition<MegaBlastFurnace> STRUCTURE_DEFINITION = null;
    public static final String STRUCTURE_PIECE_MAIN = "main";
    public static String[][] shape;
    public static final String MBF_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/mega_blast_furnace";
    private int mCasing;
    public final int horizontalOffSet = 11;
    public final int verticalOffSet = 41;
    public final int depthOffSet = 0;

    public MegaBlastFurnace(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        shape = StructureUtils.readStructureFromFile(MBF_STRUCTURE_FILE_PATH);
    }

    public MegaBlastFurnace(String aName) {
        super(aName);
        shape = StructureUtils.readStructureFromFile(MBF_STRUCTURE_FILE_PATH);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MegaBlastFurnace(this.mName);
    }

    @Override
    public IStructureDefinition<MegaBlastFurnace> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<MegaBlastFurnace>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement(
                    'A',
                    buildHatchAdder(MegaBlastFurnace.class)
                        .atLeast(InputHatch, OutputHatch, InputBus, OutputBus, Maintenance, Energy.or(ExoticEnergy))
                        .casingIndex(TAE.GTPP_INDEX(15))
                        .dot(1)
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(ModBlocks.blockCasingsMisc, 15))))
                .addElement('B', ofBlock(sBlockCasings2, 0))
                .addElement('S', Muffler.newAny(((BlockCasings8) sBlockCasings8).getTextureIndex(10), 2))
                .addElement('C', ofBlock(sBlockCasings2, 12))
                .addElement('D', ofBlock(sBlockCasings2, 13))
                .addElement('E', ofBlock(sBlockCasings2, 14))
                .addElement('F', ofBlock(sBlockCasings2, 15))
                .addElement('G', ofBlock(sBlockCasings3, 13))
                .addElement('H', ofBlock(sBlockCasings3, 14))
                .addElement('I', ofBlock(sBlockCasings3, 15))
                .addElement('J', ofBlock(sBlockCasings4, 3))
                .addElement('K', ofBlock(sBlockCasings4, 13))
                .addElement('L', ofCoil(MegaBlastFurnace::setCoilLevel, MegaBlastFurnace::getCoilLevel))
                .addElement('M', ofBlock(sBlockCasings8, 1))
                .addElement('N', ofBlock(sBlockCasings8, 2))
                .addElement('O', ofBlock(Loaders.FRF_Casings, 0))
                .addElement('P', ofBlock(sBlockCasings8, 4))
                .addElement('Q', ofBlock(sBlockCasings8, 10))
                .addElement('R', ofFrame(Materials.Naquadah))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.Tooltip_MegaBlastFurnaceRecipeType)
            .addInfo(TextLocalization.Tooltip_MegaBlastFurnace_00)
            .addInfo(TextLocalization.Tooltip_MegaBlastFurnace_01)
            .addInfo(TextLocalization.Tooltip_MegaBlastFurnace_02)
            .addInfo(TextLocalization.Tooltip_MegaBlastFurnace_03)
            .addInfo(TextLocalization.Tooltip_MegaBlastFurnace_04)
            .addInfo(TextLocalization.Tooltip_MegaBlastFurnace_05)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_03)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(23, 44, 23, true)
            .addInputHatch(TextLocalization.Tooltip_MegaBlastFurnace_Casing_00)
            .addOutputHatch(TextLocalization.Tooltip_MegaBlastFurnace_Casing_00)
            .addOutputHatch(TextLocalization.Tooltip_MegaBlastFurnace_Casing_01)
            .addInputBus(TextLocalization.Tooltip_MegaBlastFurnace_Casing_00)
            .addOutputBus(TextLocalization.Tooltip_MegaBlastFurnace_Casing_00)
            .addEnergyHatch(TextLocalization.Tooltip_MegaBlastFurnace_Casing_00)
            .addMaintenanceHatch(TextLocalization.Tooltip_MegaBlastFurnace_Casing_00)
            .addMufflerHatch(TextLocalization.Tooltip_MegaBlastFurnace_Casing_02)
            .toolTipFinisher(TextUtils.SNL + TextUtils.SQY + " §rX " + TextUtils.SRP);
        return tt;
    }

    public void setCoilLevel(HeatingCoilLevel aCoilLevel) {
        this.mCoilLevel = aCoilLevel;
    }

    public HeatingCoilLevel getCoilLevel() {
        return this.mCoilLevel;
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        if (!aNBT.hasKey(INPUT_SEPARATION_NBT_KEY)) {
            this.inputSeparation = aNBT.getBoolean("isBussesSeparate");
        }
        if (!aNBT.hasKey(BATCH_MODE_NBT_KEY)) {
            this.batchMode = aNBT.getBoolean("mUseMultiparallelMode");
        }
    }

    @Override
    public boolean onWireCutterRightClick(ForgeDirection side, ForgeDirection wrenchingSide, EntityPlayer aPlayer,
        float aX, float aY, float aZ) {
        if (!aPlayer.isSneaking()) {
            this.inputSeparation = !this.inputSeparation;
            GTUtility.sendChatToPlayer(
                aPlayer,
                StatCollector.translateToLocal("GT5U.machines.separatebus") + " " + this.inputSeparation);
            return true;
        }
        this.batchMode = !this.batchMode;
        if (this.batchMode) {
            GTUtility.sendChatToPlayer(aPlayer, StatCollector.translateToLocal("misc.BatchModeTextOn"));
        } else {
            GTUtility.sendChatToPlayer(aPlayer, StatCollector.translateToLocal("misc.BatchModeTextOff"));
        }
        return true;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
        int aColorIndex, boolean aActive, boolean aRedstone) {
        if (side == facing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE_ACTIVE)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE_ACTIVE_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    public int getCasingTextureID() {
        return TAE.GTPP_INDEX(15);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
    }

    @Override
    public ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @Nonnull
            @Override
            protected OverclockCalculator createOverclockCalculator(@Nonnull GTRecipe recipe) {
                return super.createOverclockCalculator(recipe).setRecipeHeat(recipe.mSpecialValue)
                    .setMachineHeat(MegaBlastFurnace.this.mHeatingCapacity)
                    .setHeatOC(true)
                    .setHeatDiscount(true)
                    .setEUtDiscount(0.8 - (ParallelTier / 50.0))
                    .setSpeedBoost(Math.max(0.05, 0.2 - (ParallelTier / 200.0)));
            }

            @Override
            protected @Nonnull CheckRecipeResult validateRecipe(@Nonnull GTRecipe recipe) {
                return recipe.mSpecialValue <= MegaBlastFurnace.this.mHeatingCapacity
                    ? CheckRecipeResultRegistry.SUCCESSFUL
                    : CheckRecipeResultRegistry.insufficientHeat(recipe.mSpecialValue);
            }
        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    @Override
    public int getMaxParallelRecipes() {
        if (ParallelTier <= 2) {
            return 256;
        } else {
            return (int) (256 + Math.pow(4, ParallelTier - 3));
        }
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        this.buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, horizontalOffSet, verticalOffSet, depthOffSet);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (mMachine) return -1;
        this.setCoilLevel(HeatingCoilLevel.None);
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
    public boolean checkMachine(IGregTechTileEntity iGregTechTileEntity, ItemStack itemStack) {
        this.mHeatingCapacity = 0;
        mCasing = 0;
        this.setCoilLevel(HeatingCoilLevel.None);

        if (!checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet)) return false;

        if (getCoilLevel() == HeatingCoilLevel.None) return false;

        if (mMaintenanceHatches.size() != 1 && mMufflerHatches.size() != 1) return false;

        this.mHeatingCapacity = (int) this.getCoilLevel()
            .getHeat() + 100 * (BWUtil.getTier(this.getMaxInputEu()) - 2);

        return mCasing >= 3500 && checkHatch();
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeMaps.blastFurnaceRecipes;
    }

    @Override
    public int getRecipeCatalystPriority() {
        return -2;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected SoundResource getActivitySoundLoop() {
        return SoundResource.GT_MACHINES_MEGA_BLAST_FURNACE_LOOP;
    }

}
