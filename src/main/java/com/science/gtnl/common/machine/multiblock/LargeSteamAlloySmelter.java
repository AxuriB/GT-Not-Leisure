package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;

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

import gregtech.api.GregTechAPI;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.common.blocks.BlockCasings1;
import gregtech.common.blocks.BlockCasings2;
import gregtech.common.misc.GTStructureChannels;

public class LargeSteamAlloySmelter extends SteamMultiMachineBase<LargeSteamAlloySmelter>
    implements ISurvivalConstructable {

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new LargeSteamAlloySmelter(this.mName);
    }

    @Override
    public String getMachineType() {
        return StatCollector.translateToLocal("LargeSteamAlloySmelterRecipeType");
    }

    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final String LSAS_STRUCTURE_FILE_PATH = RESOURCE_ROOT_ID + ":"
        + "multiblock/large_steam_alloy_smelter";
    public static final String[][] shape = StructureUtils.readStructureFromFile(LSAS_STRUCTURE_FILE_PATH);

    public LargeSteamAlloySmelter(String aName) {
        super(aName);
    }

    public LargeSteamAlloySmelter(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    protected final int HORIZONTAL_OFF_SET = 1;
    protected final int VERTICAL_OFF_SET = 2;
    protected final int DEPTH_OFF_SET = 0;

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        int id = tierMachine == 2 ? ((BlockCasings2) GregTechAPI.sBlockCasings2).getTextureIndex(0)
            : ((BlockCasings1) GregTechAPI.sBlockCasings1).getTextureIndex(10);
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(id), TextureFactory.builder()
                .addIcon(Textures.BlockIcons.OVERLAY_FRONT_STEAM_ALLOY_SMELTER_ACTIVE)
                .extFacing()
                .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(id), TextureFactory.builder()
                .addIcon(Textures.BlockIcons.OVERLAY_FRONT_STEAM_ALLOY_SMELTER)
                .extFacing()
                .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(id) };
    }

    @Override
    public IStructureDefinition<LargeSteamAlloySmelter> getStructureDefinition() {
        return StructureDefinition.<LargeSteamAlloySmelter>builder()
            .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
            .addElement(
                'A',
                GTStructureChannels.TIER_MACHINE_CASING.use(
                    ofChain(
                        buildSteamWirelessInput(LargeSteamAlloySmelter.class).casingIndex(getCasingTextureID())
                            .dot(1)
                            .build(),
                        buildSteamBigInput(LargeSteamAlloySmelter.class).casingIndex(getCasingTextureID())
                            .dot(1)
                            .build(),
                        buildSteamInput(LargeSteamAlloySmelter.class).casingIndex(getCasingTextureID())
                            .dot(1)
                            .build(),
                        buildHatchAdder(LargeSteamAlloySmelter.class).casingIndex(getCasingTextureID())
                            .dot(1)
                            .atLeast(
                                SteamHatchElement.InputBus_Steam,
                                SteamHatchElement.OutputBus_Steam,
                                InputBus,
                                OutputBus,
                                Maintenance)
                            .buildAndChain(
                                onElementPass(
                                    x -> ++x.tCountCasing,
                                    ofBlocksTiered(
                                        LargeSteamAlloySmelter::getTierMachineCasing,
                                        ImmutableList.of(Pair.of(sBlockCasings1, 10), Pair.of(sBlockCasings2, 0)),
                                        -1,
                                        (t, m) -> t.tierMachineCasing = m,
                                        t -> t.tierMachineCasing))))))
            .addElement(
                'B',
                GTStructureChannels.TIER_MACHINE_CASING.use(
                    ofBlocksTiered(
                        LargeSteamAlloySmelter::getTierFireboxCasing,
                        ImmutableList.of(Pair.of(sBlockCasings3, 13), Pair.of(sBlockCasings3, 14)),
                        -1,
                        (t, m) -> t.tierFireboxCasing = m,
                        t -> t.tierFireboxCasing)))
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
        tCountCasing = 0;
        if (!checkPiece(STRUCTURE_PIECE_MAIN, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET)) return false;
        if (tierMachineCasing == 1 && tierFireboxCasing == 1 && tCountCasing >= 9 && checkHatches()) {
            tierMachine = 1;
            getCasingTextureID();
            updateHatchTexture();
            return true;
        }
        if (tierMachineCasing == 2 && tierFireboxCasing == 2 && tCountCasing >= 9 && checkHatches()) {
            tierMachine = 2;
            getCasingTextureID();
            updateHatchTexture();
            return true;
        }
        getCasingTextureID();
        updateHatchTexture();
        return false;
    }

    @Override
    public int getMaxParallelRecipes() {
        if (tierMachine == 1) {
            return 16;
        } else if (tierMachine == 2) {
            return 32;
        }
        return 16;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeMaps.alloySmelterRecipes;
    }

    @Override
    public ProcessingLogic createProcessingLogic() {

        return new GTNL_ProcessingLogic() {

            @Override
            @Nonnull
            protected GTNL_OverclockCalculator createOverclockCalculator(@NotNull GTRecipe recipe) {
                return super.createOverclockCalculator(recipe).setExtraDurationModifier(configSpeedBoost)
                    .setEUtDiscount(0.8 * tierMachine * Math.pow(4, Math.min(4, recipeOcCount)))
                    .setDurationModifier(1 / 1.43 / tierMachine / Math.pow(2, Math.min(4, recipeOcCount)))
                    .setMaxTierSkips(0)
                    .setMaxOverclocks(0);
            }
        }.setMaxParallelSupplier(this::getTrueParallel);
    }

    @Override
    public int getTierRecipes() {
        return 3;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        final MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(StatCollector.translateToLocal("LargeSteamAlloySmelterRecipeType"))
            .addInfo(StatCollector.translateToLocal("Tooltip_LargeSteamAlloySmelter_00"))
            .addInfo(StatCollector.translateToLocal("Tooltip_LargeSteamAlloySmelter_01"))
            .addInfo(StatCollector.translateToLocal("Tooltip_LargeSteamAlloySmelter_02"))
            .addInfo(StatCollector.translateToLocal("HighPressureTooltipNotice"))
            .addSeparator()
            .addInfo(StatCollector.translateToLocal("StructureTooComplex"))
            .addInfo(StatCollector.translateToLocal("BLUE_PRINT_INFO"))
            .beginStructureBlock(3, 4, 3, false)
            .addInputBus(StatCollector.translateToLocal("Tooltip_LargeSteamAlloySmelter_Casing"), 1)
            .addOutputBus(StatCollector.translateToLocal("Tooltip_LargeSteamAlloySmelter_Casing"), 1)
            .addSubChannelUsage(GTStructureChannels.TIER_MACHINE_CASING)
            .toolTipFinisher();
        return tt;
    }
}
