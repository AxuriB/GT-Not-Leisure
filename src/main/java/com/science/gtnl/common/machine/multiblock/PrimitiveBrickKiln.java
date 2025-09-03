package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.ScienceNotLeisure.*;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.enums.Textures.BlockIcons.*;
import static gregtech.api.util.GTStructureUtility.*;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableList;
import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.recipes.GTNL_OverclockCalculator;
import com.science.gtnl.Utils.recipes.GTNL_ProcessingLogic;
import com.science.gtnl.common.machine.multiMachineClasses.SteamMultiMachineBase;
import com.science.gtnl.loader.RecipePool;

import gregtech.api.GregTechAPI;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.common.misc.GTStructureChannels;

public class PrimitiveBrickKiln extends SteamMultiMachineBase<PrimitiveBrickKiln> implements ISurvivalConstructable {

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new PrimitiveBrickKiln(this.mName);
    }

    @Override
    public String getMachineType() {
        return StatCollector.translateToLocal("PrimitiveBrickKilnRecipeType");
    }

    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final String PBK_STRUCTURE_FILE_PATH = RESOURCE_ROOT_ID + ":" + "multiblock/primitive_brick_kiln";
    public static final String[][] shape = StructureUtils.readStructureFromFile(PBK_STRUCTURE_FILE_PATH);

    public PrimitiveBrickKiln(String aName) {
        super(aName);
    }

    public PrimitiveBrickKiln(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    protected final int HORIZONTAL_OFF_SET = 2;
    protected final int VERTICAL_OFF_SET = 5;
    protected final int DEPTH_OFF_SET = 0;

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(MACHINE_CASING_BRICKEDBLASTFURNACE_ACTIVE)
                    .extFacing()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(MACHINE_CASING_BRICKEDBLASTFURNACE_INACTIVE)
                    .extFacing()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    @Override
    protected int getCasingTextureID() {
        return StructureUtils.getTextureIndex(GregTechAPI.sBlockCasings4, 15);
    }

    @Override
    public IStructureDefinition<PrimitiveBrickKiln> getStructureDefinition() {
        return StructureDefinition.<PrimitiveBrickKiln>builder()
            .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
            .addElement(
                'A',
                ofChain(
                    buildSteamWirelessInput(PrimitiveBrickKiln.class).casingIndex(getCasingTextureID())
                        .dot(1)
                        .build(),
                    buildSteamBigInput(PrimitiveBrickKiln.class).casingIndex(getCasingTextureID())
                        .dot(1)
                        .build(),
                    buildSteamInput(PrimitiveBrickKiln.class).casingIndex(getCasingTextureID())
                        .dot(1)
                        .build(),
                    buildHatchAdder(PrimitiveBrickKiln.class).casingIndex(getCasingTextureID())
                        .dot(1)
                        .atLeast(
                            SteamHatchElement.InputBus_Steam,
                            SteamHatchElement.OutputBus_Steam,
                            InputBus,
                            OutputBus,
                            InputHatch,
                            Maintenance)
                        .buildAndChain(onElementPass(x -> ++x.tCountCasing, ofBlock(GregTechAPI.sBlockCasings4, 15)))))
            .addElement(
                'B',
                GTStructureChannels.TIER_MACHINE_CASING.use(
                    ofBlocksTiered(
                        PrimitiveBrickKiln::getTierMachineCasing,
                        ImmutableList.of(Pair.of(sBlockCasings1, 10), Pair.of(sBlockCasings2, 0)),
                        -1,
                        (t, m) -> t.tierMachineCasing = m,
                        t -> t.tierMachineCasing)))
            .addElement(
                'C',
                GTStructureChannels.TIER_MACHINE_CASING.use(
                    ofBlocksTiered(
                        PrimitiveBrickKiln::getTierFireboxCasing,
                        ImmutableList.of(Pair.of(sBlockCasings3, 13), Pair.of(sBlockCasings3, 14)),
                        -1,
                        (t, m) -> t.tierFireboxCasing = m,
                        t -> t.tierFireboxCasing)))
            .addElement(
                'D',
                GTStructureChannels.TIER_MACHINE_CASING.use(
                    ofBlocksTiered(
                        PrimitiveBrickKiln::getTierPipeCasing,
                        ImmutableList.of(Pair.of(sBlockCasings2, 12), Pair.of(sBlockCasings2, 13)),
                        -1,
                        (t, m) -> t.tierPipeCasing = m,
                        t -> t.tierPipeCasing)))
            .build();
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        this.buildPiece(
            STRUCTURE_PIECE_MAIN,
            stackSize,
            hintsOnly,
            HORIZONTAL_OFF_SET,
            VERTICAL_OFF_SET,
            DEPTH_OFF_SET);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (this.mMachine) return -1;
        return this.survivalBuildPiece(
            STRUCTURE_PIECE_MAIN,
            stackSize,
            HORIZONTAL_OFF_SET,
            VERTICAL_OFF_SET,
            DEPTH_OFF_SET,
            elementBudget,
            env,
            false,
            true);
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        tierMachine = -1;
        tierMachineCasing = -1;
        tierFireboxCasing = -1;
        tierPipeCasing = -1;
        tCountCasing = 0;
        if (!checkPiece(STRUCTURE_PIECE_MAIN, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET)) return false;
        if (tierMachineCasing == 1 && tierFireboxCasing == 1
            && tierPipeCasing == 1
            && tCountCasing >= 40
            && checkHatches()) {
            tierMachine = 1;
            getCasingTextureID();
            updateHatchTexture();
            return true;
        }
        if (tierMachineCasing == 2 && tierFireboxCasing == 2
            && tierPipeCasing == 1
            && tCountCasing >= 40
            && checkHatches()) {
            tierMachine = 2;
            getCasingTextureID();
            updateHatchTexture();
            return true;
        }
        updateHatchTexture();
        return false;
    }

    @Override
    public int getMaxParallelRecipes() {
        if (tierMachine == 1) {
            return 8;
        } else if (tierMachine == 2) {
            return 16;
        }
        return 8;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipePool.PrimitiveBrickKilnRecipes;
    }

    @Override
    public ProcessingLogic createProcessingLogic() {

        return new GTNL_ProcessingLogic() {

            @Override
            @Nonnull
            protected GTNL_OverclockCalculator createOverclockCalculator(@NotNull GTRecipe recipe) {
                return super.createOverclockCalculator(recipe).setExtraDurationModifier(configSpeedBoost)
                    .setEUtDiscount(tierMachine * Math.pow(4, Math.min(4, recipeOcCount)))
                    .setDurationModifier(1.0 / tierMachine / Math.pow(2, Math.min(4, recipeOcCount)))
                    .setMaxTierSkips(0);
            }
        }.setMaxParallelSupplier(this::getTrueParallel);
    }

    @Override
    public int getTierRecipes() {
        if (tierMachine == 2) {
            return 2;
        } else return 1;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        final MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(StatCollector.translateToLocal("PrimitiveBrickKilnRecipeType"))
            .addInfo(StatCollector.translateToLocal("Tooltip_PrimitiveBrickKiln_00"))
            .addInfo(StatCollector.translateToLocal("Tooltip_PrimitiveBrickKiln_01"))
            .addInfo(StatCollector.translateToLocal("HighPressureTooltipNotice"))
            .addSeparator()
            .addInfo(StatCollector.translateToLocal("StructureTooComplex"))
            .addInfo(StatCollector.translateToLocal("BLUE_PRINT_INFO"))
            .beginStructureBlock(5, 7, 5, false)
            .addInputBus(StatCollector.translateToLocal("Tooltip_LargeSteamFormingPress_Casing"), 1)
            .addOutputBus(StatCollector.translateToLocal("Tooltip_LargeSteamFormingPress_Casing"), 1)
            .addSubChannelUsage(GTStructureChannels.TIER_MACHINE_CASING)
            .toolTipFinisher();
        return tt;
    }
}
