package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.GregTechAPI.sBlockFrames;
import static gregtech.api.enums.GTValues.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTUtility.validMTEList;
import static gtPlusPlus.core.block.ModBlocks.blockCustomMachineCasings;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.init.Blocks;
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
import com.science.gtnl.loader.BlockLoader;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.common.misc.GTStructureChannels;
import gregtech.common.tileentities.machines.MTEHatchOutputBusME;

public class LargeSteamFurnace extends SteamMultiMachineBase<LargeSteamFurnace> implements ISurvivalConstructable {

    protected final int HORIZONTAL_OFF_SET = 7;
    protected final int VERTICAL_OFF_SET = 6;
    protected final int DEPTH_OFF_SET = 1;
    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final String LSF_STRUCTURE_FILE_PATH = RESOURCE_ROOT_ID + ":" + "multiblock/large_steam_furnace";
    public static final String[][] shape = StructureUtils.readStructureFromFile(LSF_STRUCTURE_FILE_PATH);

    @Override
    public IStructureDefinition<LargeSteamFurnace> getStructureDefinition() {
        return StructureDefinition.<LargeSteamFurnace>builder()
            .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
            .addElement(
                'A',
                GTStructureChannels.TIER_MACHINE_CASING.use(
                    ofChain(
                        buildSteamWirelessInput(LargeSteamFurnace.class).casingIndex(getCasingTextureID())
                            .dot(1)
                            .build(),
                        buildSteamBigInput(LargeSteamFurnace.class).casingIndex(getCasingTextureID())
                            .dot(1)
                            .build(),
                        buildSteamInput(LargeSteamFurnace.class).casingIndex(getCasingTextureID())
                            .dot(1)
                            .build(),
                        buildHatchAdder(LargeSteamFurnace.class).casingIndex(getCasingTextureID())
                            .dot(1)
                            .atLeast(
                                SteamHatchElement.InputBus_Steam,
                                SteamHatchElement.OutputBus_Steam,
                                InputBus,
                                OutputBus,
                                Maintenance)
                            .buildAndChain(
                                onElementPass(
                                    x -> ++x.mCountCasing,
                                    ofBlocksTiered(
                                        LargeSteamFurnace::getTierMachineCasing,
                                        ImmutableList.of(Pair.of(sBlockCasings1, 10), Pair.of(sBlockCasings2, 0)),
                                        -1,
                                        (t, m) -> t.tierMachineCasing = m,
                                        t -> t.tierMachineCasing))))))
            .addElement(
                'B',
                GTStructureChannels.TIER_MACHINE_CASING.use(
                    ofBlocksTiered(
                        LargeSteamFurnace::getTierPipeCasing,
                        ImmutableList.of(Pair.of(sBlockCasings2, 12), Pair.of(sBlockCasings2, 13)),
                        -1,
                        (t, m) -> t.tierPipeCasing = m,
                        t -> t.tierPipeCasing)))
            .addElement(
                'C',
                GTStructureChannels.TIER_MACHINE_CASING.use(
                    ofBlocksTiered(
                        LargeSteamFurnace::getTierFireboxCasing,
                        ImmutableList.of(Pair.of(sBlockCasings3, 13), Pair.of(sBlockCasings3, 14)),
                        -1,
                        (t, m) -> t.tierFireboxCasing = m,
                        t -> t.tierFireboxCasing)))
            .addElement(
                'D',
                GTStructureChannels.TIER_MACHINE_CASING.use(
                    ofBlocksTiered(
                        LargeSteamFurnace::getTierFrameCasing,
                        ImmutableList.of(Pair.of(sBlockFrames, 300), Pair.of(sBlockFrames, 305)),
                        -1,
                        (t, m) -> t.tierFrameCasing = m,
                        t -> t.tierFrameCasing)))
            .addElement(
                'E',
                GTStructureChannels.TIER_MACHINE_CASING.use(
                    ofBlocksTiered(
                        LargeSteamFurnace::getTierPlatedCasing,
                        ImmutableList.of(Pair.of(blockCustomMachineCasings, 0), Pair.of(sBlockCasings2, 0)),
                        -1,
                        (t, m) -> t.tierPlatedCasing = m,
                        t -> t.tierPlatedCasing)))
            .addElement(
                'F',
                GTStructureChannels.TIER_MACHINE_CASING.use(
                    ofBlocksTiered(
                        LargeSteamFurnace::getTierBrickCasing,
                        ImmutableList
                            .of(Pair.of(BlockLoader.metaBlockColumn, 0), Pair.of(BlockLoader.metaBlockColumn, 1)),
                        -1,
                        (t, m) -> t.tierBrickCasing = m,
                        t -> t.tierBrickCasing)))
            .addElement('G', ofBlock(Blocks.stonebrick, 0))
            .addElement(
                'H',
                GTStructureChannels.TIER_MACHINE_CASING.use(
                    ofBlocksTiered(
                        LargeSteamFurnace::getTierIndustrialCasing,
                        ImmutableList.of(Pair.of(BlockLoader.metaCasing02, 1), Pair.of(BlockLoader.metaCasing02, 2)),
                        -1,
                        (t, m) -> t.tierIndustrialCasing = m,
                        t -> t.tierIndustrialCasing)))
            .build();
    }

    public LargeSteamFurnace(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public LargeSteamFurnace(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new LargeSteamFurnace(this.mName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(StatCollector.translateToLocal("LargeSteamFurnaceRecipeType"))
            .addInfo(StatCollector.translateToLocal("Tooltip_LargeSteamFurnace_00"))
            .addInfo(StatCollector.translateToLocal("Tooltip_LargeSteamFurnace_01"))
            .addInfo(StatCollector.translateToLocal("Tooltip_LargeSteamFurnace_02"))
            .addInfo(StatCollector.translateToLocal("HighPressureTooltipNotice"))
            .addSeparator()
            .addInfo(StatCollector.translateToLocal("StructureTooComplex"))
            .addInfo(StatCollector.translateToLocal("BLUE_PRINT_INFO"))
            .beginStructureBlock(9, 8, 10, false)
            .addInputBus(StatCollector.translateToLocal("Tooltip_LargeSteamFurnace_Casing"), 1)
            .addOutputBus(StatCollector.translateToLocal("Tooltip_LargeSteamFurnace_Casing"), 1)
            .addSubChannelUsage(GTStructureChannels.TIER_MACHINE_CASING)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        int id = tierMachine == 2 ? StructureUtils.getTextureIndex(sBlockCasings2, 0)
            : StructureUtils.getTextureIndex(sBlockCasings1, 10);
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(id), TextureFactory.builder()
                .addIcon(Textures.BlockIcons.OVERLAY_FRONT_STEAM_FURNACE_ACTIVE)
                .extFacing()
                .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(id), TextureFactory.builder()
                .addIcon(Textures.BlockIcons.OVERLAY_FRONT_STEAM_FURNACE)
                .extFacing()
                .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(id) };
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeMaps.furnaceRecipes;
    }

    @Override
    public ProcessingLogic createProcessingLogic() {

        return new GTNL_ProcessingLogic() {

            @Override
            @Nonnull
            protected GTNL_OverclockCalculator createOverclockCalculator(@NotNull GTRecipe recipe) {
                return super.createOverclockCalculator(recipe).setExtraDurationModifier(configSpeedBoost)
                    .setEUtDiscount(0.5 * tierMachine * Math.pow(4, Math.min(4, recipeOcCount)))
                    .setDurationModifier(1.0 / 10.0 / tierMachine / Math.pow(2, Math.min(4, recipeOcCount)))
                    .setMaxTierSkips(0)
                    .setMaxOverclocks(0);
            }
        }.setMaxParallelSupplier(this::getTrueParallel);
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        if (!checkPiece(STRUCTURE_PIECE_MAIN, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET) || !checkHatches())
            return false;
        if (tierPipeCasing == 1 && tierMachineCasing == 1
            && tierFrameCasing == 1
            && tierPlatedCasing == 1
            && tierBrickCasing == 1
            && tierFireboxCasing == 1
            && tierIndustrialCasing == 1
            && mCountCasing >= 50) {
            tierMachine = 1;
            getCasingTextureID();
            updateHatchTexture();
            return true;
        }
        if (tierPipeCasing == 2 && tierMachineCasing == 2
            && tierFrameCasing == 2
            && tierPlatedCasing == 2
            && tierBrickCasing == 2
            && tierFireboxCasing == 2
            && tierIndustrialCasing == 2
            && mCountCasing >= 50) {
            tierMachine = 2;
            getCasingTextureID();
            updateHatchTexture();
            return true;
        }
        return false;
    }

    @Override
    public int getMaxParallelRecipes() {
        if (tierMachine == 1) {
            return 128;
        } else if (tierMachine == 2) {
            return 256;
        }
        return 128;
    }

    @Override
    public String getMachineType() {
        return StatCollector.translateToLocal("LargeSteamFurnaceRecipeType");
    }

    @Override
    public int getTierRecipes() {
        return 3;
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
    @NotNull
    public CheckRecipeResult checkProcessing() {
        List<ItemStack> tInput = getAllStoredInputs();
        long availableEUt = GTUtility.roundUpVoltage(getMaxInputVoltage());
        if (availableEUt < 4) {
            return CheckRecipeResultRegistry.insufficientPower(4);
        } else {
            availableEUt = Integer.MAX_VALUE;
        }
        if (tInput.isEmpty()) {
            return CheckRecipeResultRegistry.NO_RECIPE;
        }
        int maxParallel = getTrueParallel();
        int originalMaxParallel = getTrueParallel();

        GTNL_OverclockCalculator calculator = new GTNL_OverclockCalculator().setEUt(availableEUt)
            .setRecipeEUt(4)
            .setDuration(64)
            .setParallel(originalMaxParallel)
            .setNoOverclock(true);

        maxParallel = GTUtility.safeInt((long) (maxParallel * calculator.calculateMultiplierUnderOneTick()), 0);

        int maxParallelBeforeBatchMode = maxParallel;
        if (isBatchModeEnabled()) {
            maxParallel = GTUtility.safeInt((long) maxParallel * getMaxBatchSize(), 0);
        }

        int currentParallel = (int) Math.min(maxParallel, availableEUt / 4);
        int itemParallel = 0;
        for (ItemStack item : tInput) {
            ItemStack smeltedOutput = GTModHandler.getSmeltingOutput(item, false, null);
            if (smeltedOutput != null) {
                int parallelsLeft = currentParallel - itemParallel;
                if (parallelsLeft <= 0) break;
                itemParallel += Math.min(item.stackSize, parallelsLeft);
            }
        }

        currentParallel = itemParallel;
        if (currentParallel <= 0) {
            return CheckRecipeResultRegistry.NO_RECIPE;
        }
        int currentParallelBeforeBatchMode = Math.min(currentParallel, maxParallelBeforeBatchMode);
        calculator.setCurrentParallel(currentParallelBeforeBatchMode)
            .calculate();

        double batchMultiplierMax = 1;
        // In case batch mode enabled
        if (currentParallel > maxParallelBeforeBatchMode && calculator.getDuration() < getMaxBatchSize()) {
            batchMultiplierMax = (double) getMaxBatchSize() / calculator.getDuration();
            batchMultiplierMax = Math.min(batchMultiplierMax, (double) currentParallel / maxParallelBeforeBatchMode);
        }

        int finalParallel = (int) (batchMultiplierMax * currentParallelBeforeBatchMode);

        // Copy the getItemOutputSlots as to not mutate the output busses' slots.
        List<ItemStack> outputSlots = new ArrayList<>();
        for (ItemStack stack : getItemOutputSlots(null)) {
            if (stack != null) {
                outputSlots.add(stack.copy());
            } else {
                outputSlots.add(null);
            }
        }

        boolean hasMEOutputBus = false;
        for (final MTEHatch bus : validMTEList(mOutputBusses)) {
            if (bus instanceof MTEHatchOutputBusME meBus) {
                if (!meBus.isLocked() && meBus.canAcceptItem()) {
                    hasMEOutputBus = true;
                    break;
                }
            }
        }
        // Consume items and generate outputs
        ArrayList<ItemStack> smeltedOutputs = new ArrayList<>();
        int toSmelt = finalParallel;
        for (ItemStack item : tInput) {
            ItemStack smeltedOutput = GTModHandler.getSmeltingOutput(item, false, null);
            if (smeltedOutput != null) {
                int maxOutput = 0;
                int remainingToSmelt = Math.min(toSmelt, item.stackSize);

                if (hasMEOutputBus) {
                    // Has an unlocked ME Output Bus and therefore can always fit the full stack
                    maxOutput = remainingToSmelt;
                } else {

                    // Calculate how many of this output can fit in the output slots
                    int needed = remainingToSmelt;
                    ItemStack outputType = smeltedOutput.copy();
                    outputType.stackSize = 1;

                    for (int i = 0; i < outputSlots.size(); i++) {
                        ItemStack slot = outputSlots.get(i);
                        if (slot == null) {
                            // Empty slot: can fit a full stack
                            int canFit = Math.min(needed, outputType.getMaxStackSize());
                            ItemStack newStack = outputType.copy();
                            newStack.stackSize = canFit;
                            outputSlots.set(i, newStack); // Fill the slot
                            maxOutput += canFit;
                            needed -= canFit;
                        } else if (slot.isItemEqual(outputType)) {
                            int canFit;
                            // Check for locked ME Output bus
                            if (slot.stackSize == 65) {
                                canFit = needed;
                            } else {
                                // Same type: can fit up to max stack size
                                int space = outputType.getMaxStackSize() - slot.stackSize;
                                canFit = Math.min(needed, space);
                            }
                            slot.stackSize += canFit;
                            maxOutput += canFit;
                            needed -= canFit;
                            // No need to set, since slot is a reference
                        }
                        if (needed <= 0) break;
                    }
                }

                // If void protection is enabled, only process what fits
                int toProcess = protectsExcessItem() ? maxOutput : remainingToSmelt;

                if (toProcess > 0) {
                    ItemStack outputStack = smeltedOutput.copy();
                    outputStack.stackSize *= toProcess;
                    smeltedOutputs.add(outputStack);

                    item.stackSize -= toProcess;
                    toSmelt -= toProcess;
                    if (toSmelt <= 0) break;
                }
            }
        }
        if (smeltedOutputs.isEmpty()) {
            return CheckRecipeResultRegistry.NO_RECIPE;
        }

        this.mOutputItems = smeltedOutputs.toArray(new ItemStack[0]);

        this.mEfficiency = 10000 - (getIdealStatus() - getRepairStatus()) * 1000;
        this.mEfficiencyIncrease = 10000;
        this.mMaxProgresstime = (int) (calculator.getDuration() * batchMultiplierMax);
        this.lEUt = VP[GTUtility.getTier(calculator.getConsumption())];
        if (this.lEUt > 0) {
            this.lEUt = -this.lEUt;
        }
        this.updateSlots();

        return CheckRecipeResultRegistry.SUCCESSFUL;
    }
}
