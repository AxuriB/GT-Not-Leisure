package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;
import static com.science.gtnl.Utils.item.ItemUtils.readItemStackFromNBT;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.enums.Textures.BlockIcons.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTUtility.validMTEList;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.alignment.IAlignmentLimits;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.gtnewhorizons.modularui.api.ModularUITextures;
import com.gtnewhorizons.modularui.api.drawable.Text;
import com.gtnewhorizons.modularui.api.math.Color;
import com.gtnewhorizons.modularui.api.math.MainAxisAlignment;
import com.gtnewhorizons.modularui.api.screen.ModularUIContext;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.gtnewhorizons.modularui.common.builder.UIInfo;
import com.gtnewhorizons.modularui.common.internal.wrapper.ModularUIContainer;
import com.gtnewhorizons.modularui.common.widget.ButtonWidget;
import com.gtnewhorizons.modularui.common.widget.Column;
import com.gtnewhorizons.modularui.common.widget.CycleButtonWidget;
import com.gtnewhorizons.modularui.common.widget.DrawableWidget;
import com.gtnewhorizons.modularui.common.widget.DynamicPositionedColumn;
import com.gtnewhorizons.modularui.common.widget.DynamicPositionedRow;
import com.gtnewhorizons.modularui.common.widget.FakeSyncWidget;
import com.gtnewhorizons.modularui.common.widget.Scrollable;
import com.gtnewhorizons.modularui.common.widget.SlotWidget;
import com.gtnewhorizons.modularui.common.widget.TextWidget;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.machine.EdenGardenManager.EIGBucket;
import com.science.gtnl.Utils.machine.EdenGardenManager.EIGDropTable;
import com.science.gtnl.Utils.machine.EdenGardenManager.EIGDynamicInventory;
import com.science.gtnl.Utils.machine.EdenGardenManager.EIGMode;
import com.science.gtnl.Utils.machine.EdenGardenManager.EIGModes;
import com.science.gtnl.Utils.machine.EdenGardenManager.buckets.EIGIC2Bucket;
import com.science.gtnl.common.machine.multiMachineClasses.MultiMachineBase;
import com.science.gtnl.loader.BlockLoader;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Textures;
import gregtech.api.enums.VoltageIndex;
import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MTEHatchOutputBus;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.recipe.check.SimpleCheckRecipeResult;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.VoidProtectionHelper;
import gregtech.common.tileentities.machines.MTEHatchOutputBusME;
import gtPlusPlus.core.block.ModBlocks;
import gtnhlanth.common.register.LanthItemList;
import ic2.core.init.BlocksItems;
import ic2.core.init.InternalName;

public class EdenGarden extends MultiMachineBase<EdenGarden> {

    public static int EIG_BALANCE_IC2_ACCELERATOR_TIER = VoltageIndex.EV;
    public static int EIG_BALANCE_REGULAR_MODE_MIN_TIER = VoltageIndex.EV;
    public static double EIG_BALANCE_MAX_FERTILIZER_BOOST = 1.0d;
    public static int NUMBER_OF_DROPS_TO_SIMULATE = 5;

    public static boolean debug = false;

    public static int NBT_REVISION = 1;
    public static int CONFIGURATION_WINDOW_ID = 999;

    public List<EIGBucket> buckets = new LinkedList<>();
    public EIGDropTable dropTracker = new EIGDropTable();
    public Collection<EIGMigrationHolder> toMigrate;
    public EIGDropTable guiDropTracker = new EIGDropTable();
    public HashMap<ItemStack, Double> synchedGUIDropTracker = new HashMap<>();
    public int maxSeedTypes = Integer.MAX_VALUE;
    public int maxSeedCount = Integer.MAX_VALUE;
    public int setupPhase = 1;
    public int waterUsage = 0;
    public EIGMode mode = EIGModes.Normal;
    public boolean useNoHumidity = false;

    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final String EG_STRUCTURE_FILE_PATH = RESOURCE_ROOT_ID + ":" + "multiblock/eden_garden";
    private static final String[][] shape = StructureUtils.readStructureFromFile(EG_STRUCTURE_FILE_PATH);
    protected final int HORIZONTAL_OFF_SET = 6;
    protected final int VERTICAL_OFF_SET = 43;
    protected final int DEPTH_OFF_SET = 10;

    @Override
    public int getCasingTextureID() {
        return StructureUtils.getTextureIndex(sBlockCasings10, 5);
    }

    @Override
    public boolean getPerfectOC() {
        return true;
    }

    @Override
    public int getMaxParallelRecipes() {
        return 1;
    }

    @Override
    public IStructureDefinition<EdenGarden> getStructureDefinition() {
        return StructureDefinition.<EdenGarden>builder()
            .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
            .addElement('A', ofBlock(LanthItemList.SHIELDED_ACCELERATOR_CASING, 0))
            .addElement(
                'B',
                ofChain(
                    buildHatchAdder(EdenGarden.class)
                        .atLeast(Maintenance, InputBus, OutputBus, InputHatch, Maintenance, Energy.or(ExoticEnergy))
                        .dot(1)
                        .casingIndex(StructureUtils.getTextureIndex(sBlockCasings10, 4))
                        .build(),
                    onElementPass(x -> ++x.mCountCasing, ofBlock(sBlockCasings10, 4))))
            .addElement('C', ofBlock(sBlockCasings10, 5))
            .addElement('D', ofBlock(sBlockCasings8, 10))
            .addElement('E', ofBlock(sBlockCasings9, 11))
            .addElement('F', ofBlock(ModBlocks.blockCasings2Misc, 3))
            .addElement('G', ofBlock(LanthItemList.SHIELDED_ACCELERATOR_GLASS, 0))
            .addElement('H', ofBlock(BlockLoader.metaBlockGlow, 0))
            .addElement('I', ofBlock(Blocks.farmland, 0))
            .addElement(
                'J',
                ofChain(
                    ofBlockAnyMeta(Blocks.water),
                    ofBlock(BlocksItems.getFluidBlock(InternalName.fluidDistilledWater), 0)))
            .build();
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack itemStack) {
        if (!checkPiece(STRUCTURE_PIECE_MAIN, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET) || !checkHatch()) {
            return false;
        }
        setupParameters();
        return mCountCasing >= 1000;
    }

    @Override
    public boolean checkHatch() {
        return super.checkHatch() && !(this.mEnergyHatches.isEmpty() && this.mExoticEnergyHatches.isEmpty());
    }

    @Override
    public void setupParameters() {
        super.setupParameters();
        updateSeedLimits();
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
    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (d, r, f) -> d.offsetY == 0 && r.isNotRotated() && f.isNotFlipped();
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(StatCollector.translateToLocal("EdenGardenRecipeType"))
            .addInfo(StatCollector.translateToLocal("Tooltip_EdenGarden_00"))
            .addInfo(StatCollector.translateToLocal("Tooltip_EdenGarden_01"))
            .addInfo(StatCollector.translateToLocal("Tooltip_EdenGarden_02"))
            .addInfo(StatCollector.translateToLocal("Tooltip_EdenGarden_03"))
            .addInfo(StatCollector.translateToLocal("Tooltip_EdenGarden_04"))
            .addInfo(StatCollector.translateToLocal("Tooltip_EdenGarden_05"))
            .addInfo(StatCollector.translateToLocal("Tooltip_EdenGarden_06"))
            .addInfo(StatCollector.translateToLocal("Tooltip_Tectech_Hatch"))
            .addSeparator()
            .addInfo(StatCollector.translateToLocal("StructureTooComplex"))
            .addInfo(StatCollector.translateToLocal("BLUE_PRINT_INFO"))
            .beginStructureBlock(6, 43, 10, false)
            .addInputBus(StatCollector.translateToLocal("Tooltip_EdenGarden_Casing"), 1)
            .addOutputBus(StatCollector.translateToLocal("Tooltip_EdenGarden_Casing"), 1)
            .addInputHatch(StatCollector.translateToLocal("Tooltip_EdenGarden_Casing"), 1)
            .addEnergyHatch(StatCollector.translateToLocal("Tooltip_EdenGarden_Casing"), 1)
            .addMaintenanceHatch(StatCollector.translateToLocal("Tooltip_EdenGarden_Casing"), 1)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public String[] getStructureDescription(ItemStack stackSize) {
        List<String> info = new ArrayList<>(Arrays.asList(super.getStructureDescription(stackSize)));
        return info.toArray(new String[] {});
    }

    public EdenGarden(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public EdenGarden(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new EdenGarden(this.mName);
    }

    @Override
    public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
        super.onFirstTick(aBaseMetaTileEntity);
        if (this.toMigrate == null) return;

        if (this.mode == EIGModes.IC2) {
            toMigrate.forEach(holder -> buckets.add(new EIGIC2Bucket(this, holder.seed)));
        } else {
            this.mode = EIGModes.Normal;
            for (EIGMigrationHolder holder : toMigrate) {
                holder.seed.stackSize = holder.count;
                EIGBucket bucket = this.mode.tryCreateNewBucket(this, holder.seed, Integer.MAX_VALUE, false);
                if (bucket != null) {
                    buckets.add(bucket);
                } else {
                    holder.seed.stackSize = holder.count;
                    addOutput(holder.seed);
                }
            }
        }
    }

    @Override
    public void onRemoval() {
        super.onRemoval();
        buckets.removeIf(this::tryEmptyBucket);
        if (buckets.isEmpty()) return;

        IGregTechTileEntity mte = getBaseMetaTileEntity();
        buckets.forEach(bucket -> {
            for (ItemStack stack : bucket.tryRemoveSeed(bucket.getSeedCount(), false)) {
                EntityItem entityitem = new EntityItem(
                    mte.getWorld(),
                    mte.getXCoord(),
                    mte.getYCoord(),
                    mte.getZCoord(),
                    stack);
                entityitem.delayBeforeCanPickup = 10;
                mte.getWorld()
                    .spawnEntityInWorld(entityitem);
            }
        });
    }

    @Override
    public void onScrewdriverRightClick(ForgeDirection side, EntityPlayer aPlayer, float aX, float aY, float aZ,
        ItemStack aTool) {
        if (aPlayer.isSneaking()) {
            tryChangeMode(aPlayer);
        } else {
            tryChangeSetupPhase(aPlayer);
        }
    }

    @Override
    public boolean onWireCutterRightClick(ForgeDirection side, ForgeDirection wrenchingSide, EntityPlayer aPlayer,
        float aX, float aY, float aZ, ItemStack aTool) {
        this.tryChangeHumidityMode(aPlayer);
        return true;
    }

    public void tryChangeSetupPhase(EntityPlayer aPlayer) {
        if (this.mMaxProgresstime > 0) {
            GTUtility.sendChatToPlayer(aPlayer, StatCollector.translateToLocal("Info_EdenGarden_SetupPhase_Working"));
            return;
        }
        this.setupPhase++;
        if (this.setupPhase == 3) this.setupPhase = 0;

        String phaseChangeMessage = StatCollector.translateToLocal("Info_EdenGarden_SetupPhase_Change") + " ";
        switch (this.setupPhase) {
            case 0:
                phaseChangeMessage += StatCollector.translateToLocal("Info_EdenGarden_Operating");
                break;
            case 1:
                phaseChangeMessage += StatCollector.translateToLocal("Info_EdenGarden_Input");
                break;
            case 2:
                phaseChangeMessage += StatCollector.translateToLocal("Info_EdenGarden_Output");
                break;
            default:
                phaseChangeMessage += StatCollector.translateToLocal("Info_EdenGarden_SetupPhase_Invalid");
                break;
        }
        this.updateSeedLimits();
        GTUtility.sendChatToPlayer(aPlayer, phaseChangeMessage);
    }

    public void tryChangeMode(EntityPlayer aPlayer) {
        if (this.mMaxProgresstime > 0) {
            GTUtility.sendChatToPlayer(aPlayer, StatCollector.translateToLocal("Info_EdenGarden_Mode_Working"));
            return;
        }
        if (!this.buckets.isEmpty()) {
            GTUtility.sendChatToPlayer(aPlayer, StatCollector.translateToLocal("Info_EdenGarden_Mode_HasSeeds"));
            return;
        }
        this.mode = EIGModes.getNextMode(this.mode);
        this.updateSeedLimits();
        GTUtility.sendChatToPlayer(
            aPlayer,
            StatCollector.translateToLocalFormatted("Info_EdenGarden_Mode_Change", this.mode.getName()));
    }

    public void tryChangeHumidityMode(EntityPlayer aPlayer) {
        this.useNoHumidity = !this.useNoHumidity;
        if (this.useNoHumidity) {
            GTUtility
                .sendChatToPlayer(aPlayer, StatCollector.translateToLocal("Info_EdenGarden_NoHumidityMode_Enabled"));
        } else {
            GTUtility
                .sendChatToPlayer(aPlayer, StatCollector.translateToLocal("Info_EdenGarden_NoHumidityMode_Disabled"));
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("version", NBT_REVISION);
        aNBT.setInteger("setupPhase", this.setupPhase);
        aNBT.setString("mode", this.mode.getName());
        aNBT.setBoolean("isNoHumidity", this.useNoHumidity);
        NBTTagList bucketListNBT = new NBTTagList();
        for (EIGBucket b : this.buckets) {
            bucketListNBT.appendTag(b.save());
        }
        aNBT.setTag(
            "progress",
            this.dropTracker.intersect(this.guiDropTracker)
                .save());
        aNBT.setTag("buckets", bucketListNBT);
    }

    public static class EIGMigrationHolder {

        public final ItemStack seed;
        public final ItemStack supportBlock;
        public final boolean useNoHumidity;
        public int count;
        public boolean isValid;

        public EIGMigrationHolder(NBTTagCompound nbt) {
            this.seed = readItemStackFromNBT(nbt.getCompoundTag("input"));
            if (this.seed != null) {
                this.count = this.seed.stackSize;
                this.seed.stackSize = 1;
            }
            this.supportBlock = nbt.hasKey("undercrop", 10) ? readItemStackFromNBT(nbt.getCompoundTag("undercrop"))
                : null;
            this.useNoHumidity = nbt.getBoolean("noHumidity");
            this.isValid = true;
        }

        public String getKey() {
            if (this.supportBlock == null) return seed.toString();
            return "(" + this.seed.toString() + "," + this.supportBlock + ")";
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        int revision = aNBT.hasKey("version", 3) ? aNBT.getInteger("version") : 0;
        if (revision <= 0) {
            this.setupPhase = aNBT.getInteger("setupphase");
            this.mode = aNBT.getBoolean("isIC2Mode") ? EIGModes.IC2 : EIGModes.Normal;
            this.useNoHumidity = aNBT.getBoolean("isNoHumidity");
            HashMap<String, EIGMigrationHolder> toMigrate = new HashMap<>();
            for (int i = 0; i < aNBT.getInteger("mStorageSize"); i++) {
                EIGMigrationHolder holder = new EIGMigrationHolder(aNBT.getCompoundTag("mStorage." + i));
                if (toMigrate.containsKey(holder.getKey())) {
                    toMigrate.get(holder.getKey()).count += holder.count;
                } else {
                    toMigrate.put(holder.getKey(), holder);
                }
            }

            this.toMigrate = toMigrate.values();
        } else {
            this.setupPhase = aNBT.getInteger("setupPhase");
            this.mode = EIGModes.getModeFromName(aNBT.getString("mode"));
            this.useNoHumidity = aNBT.getBoolean("isNoHumidity");
            this.mode.restoreBuckets(aNBT.getTagList("buckets", 10), this.buckets);
            new EIGDropTable(aNBT.getTagList("progress", 10)).addTo(this.dropTracker);
        }
    }

    public int getTotalSeedCount() {
        return buckets.stream()
            .mapToInt(EIGBucket::getSeedCount)
            .sum();
    }

    public void updateSeedLimits() {
        this.maxSeedTypes = Integer.MAX_VALUE;
        this.maxSeedCount = Integer.MAX_VALUE;
    }

    public boolean tryDrain(FluidStack toConsume, boolean drainPartial) {
        if (toConsume == null || toConsume.amount <= 0) return true;

        List<FluidStack> fluids = getStoredFluids();
        int remaining = toConsume.amount;

        for (FluidStack fluid : fluids) {
            if (!fluid.isFluidEqual(toConsume)) continue;
            int used = Math.min(remaining, fluid.amount);
            fluid.amount -= used;
            remaining -= used;
            if (remaining <= 0) break;
        }

        if (!drainPartial && remaining > 0 && !debug) return false;
        return remaining <= 0;
    }

    public boolean tryEmptyBucket(EIGBucket bucket) {
        int totalSeeds = bucket.getSeedCount();
        if (totalSeeds <= 0) return true;

        for (MTEHatchOutputBus tHatch : validMTEList(mOutputBusses)) {
            if (!(tHatch instanceof MTEHatchOutputBusME)) continue;
            for (ItemStack stack : bucket.tryRemoveSeed(totalSeeds, false)) {
                tHatch.storePartial(stack);
            }
            return true;
        }

        ItemStack[] simulated = bucket.tryRemoveSeed(1, true);
        VoidProtectionHelper helper = new VoidProtectionHelper().setMachine(this, true, false)
            .setItemOutputs(simulated)
            .setMaxParallel(totalSeeds)
            .build();

        if (helper.getMaxParallel() > 0) {
            for (ItemStack toOutput : bucket.tryRemoveSeed(helper.getMaxParallel(), false)) {
                for (MTEHatchOutputBus tHatch : validMTEList(mOutputBusses)) {
                    if (tHatch.storePartial(toOutput)) break;
                }
            }
        }

        return bucket.getSeedCount() <= 0;
    }

    @Override
    @NotNull
    public CheckRecipeResult checkProcessing() {
        updateSeedLimits();

        if (setupPhase > 0) return processSetupPhase();

        CheckRecipeResult validation = validateBuckets();
        if (validation != null) return validation;

        return calculateProgressAndDrops();
    }

    public CheckRecipeResult processSetupPhase() {
        if ((setupPhase == 1 && buckets.size() >= maxSeedTypes) || (setupPhase == 2 && buckets.isEmpty())) {
            return CheckRecipeResultRegistry.NO_RECIPE;
        }

        if (setupPhase == 1) {
            for (ItemStack input : getStoredInputs()) {
                addCrop(input);
                if (buckets.size() >= maxSeedTypes) break;
            }
        } else if (setupPhase == 2) {
            Iterator<EIGBucket> iter = buckets.iterator();
            while (iter.hasNext()) {
                EIGBucket bucket = iter.next();
                if (tryEmptyBucket(bucket)) iter.remove();
                else {
                    this.mMaxProgresstime = 20;
                    this.lEUt = 0;
                    return CheckRecipeResultRegistry.ITEM_OUTPUT_FULL;
                }
            }
        }

        this.updateSlots();
        this.mMaxProgresstime = 10;
        this.lEUt = 0;
        this.mEfficiency = 10000;
        this.mEfficiencyIncrease = 10000;
        return CheckRecipeResultRegistry.SUCCESSFUL;
    }

    public CheckRecipeResult validateBuckets() {
        if (buckets.size() > maxSeedTypes) return SimpleCheckRecipeResult.ofFailure("EIG_slotoverflow");
        if (getTotalSeedCount() > maxSeedCount) return SimpleCheckRecipeResult.ofFailure("EIG_seedOverflow");

        Iterator<EIGBucket> iter = buckets.iterator();
        while (iter.hasNext()) {
            EIGBucket bucket = iter.next();
            if (bucket.isValid() || bucket.revalidate(this)) continue;
            tryEmptyBucket(bucket);
            if (bucket.getSeedCount() <= 0) iter.remove();
        }

        if (buckets.isEmpty()) return CheckRecipeResultRegistry.NO_RECIPE;
        if (!tryDrain(new FluidStack(FluidRegistry.WATER, getTotalSeedCount() * 2000), false))
            return SimpleCheckRecipeResult.ofFailure("EIG_missingwater");

        return null;
    }

    public CheckRecipeResult calculateProgressAndDrops() {
        double multiplier = EIG_BALANCE_MAX_FERTILIZER_BOOST;
        this.guiDropTracker = new EIGDropTable();

        int baseTime = 1200;
        if (mode == EIGModes.IC2) {
            this.mMaxProgresstime = Math.max(20, baseTime / Math.max(mEnergyHatchTier - 3, 1));
            double timeElapsed = (double) mMaxProgresstime * (1 << EIG_BALANCE_IC2_ACCELERATOR_TIER);
            buckets.forEach(bucket -> bucket.addProgress(timeElapsed * multiplier, guiDropTracker));
        } else {
            this.mMaxProgresstime = Math.max(20, baseTime / mEnergyHatchTier);
            buckets.forEach(bucket -> bucket.addProgress(multiplier, guiDropTracker));
        }

        guiDropTracker.addTo(dropTracker, multiplier);
        mOutputItems = dropTracker.getDrops();

        lEUt = -(long) (GTValues.V[mEnergyHatchTier] * 0.99d);
        mEfficiency = mEfficiencyIncrease = 10000;

        updateSlots();
        return CheckRecipeResultRegistry.SUCCESSFUL;
    }

    public ItemStack addCrop(ItemStack input) {
        return addCrop(input, false) ? input : null;
    }

    public boolean addCrop(ItemStack input, boolean simulate) {
        if (input == null || input.stackSize <= 0) return true;
        if (simulate) input = input.copy();

        int addCap = Math.min(input.stackSize, maxSeedCount - getTotalSeedCount());
        if (addCap <= 0) return false;

        ItemStack finalInput = input;
        boolean added = buckets.stream()
            .anyMatch(bucket -> bucket.tryAddSeed(this, finalInput, addCap, simulate) > 0);
        if (added) return input.stackSize <= 0;

        if (buckets.size() >= maxSeedTypes) return false;
        EIGBucket bucket = mode.tryCreateNewBucket(this, input, addCap, simulate);
        if (bucket == null) return false;

        buckets.add(bucket);
        return input.stackSize <= 0;
    }

    public static final UIInfo<?, ?> GreenhouseUI = createMetaTileEntityUI(
        ModulaUIContainer_ExtremeIndustrialGreenhouse::new);

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        if (aBaseMetaTileEntity.isClientSide()) return true;
        GreenhouseUI.open(
            aPlayer,
            aBaseMetaTileEntity.getWorld(),
            aBaseMetaTileEntity.getXCoord(),
            aBaseMetaTileEntity.getYCoord(),
            aBaseMetaTileEntity.getZCoord());
        return true;
    }

    public static class ModulaUIContainer_ExtremeIndustrialGreenhouse extends ModularUIContainer {

        final WeakReference<EdenGarden> parent;

        public ModulaUIContainer_ExtremeIndustrialGreenhouse(ModularUIContext context, ModularWindow mainWindow,
            EdenGarden mte) {
            super(context, mainWindow);
            parent = new WeakReference<>(mte);
        }

        @Override
        public ItemStack transferStackInSlot(EntityPlayer aPlayer, int aSlotIndex) {
            if (!(aPlayer instanceof EntityPlayerMP)) return super.transferStackInSlot(aPlayer, aSlotIndex);
            final Slot s = getSlot(aSlotIndex);
            if (s == null) return super.transferStackInSlot(aPlayer, aSlotIndex);
            if (aSlotIndex >= 36) return super.transferStackInSlot(aPlayer, aSlotIndex);
            final ItemStack aStack = s.getStack();
            if (aStack == null) return super.transferStackInSlot(aPlayer, aSlotIndex);
            EdenGarden mte = parent.get();
            if (mte == null) return super.transferStackInSlot(aPlayer, aSlotIndex);
            if (mte.mMaxProgresstime > 0) {
                GTUtility.sendChatToPlayer(
                    aPlayer,
                    EnumChatFormatting.RED + StatCollector.translateToLocal("Info_EdenGarden_00"));
                return super.transferStackInSlot(aPlayer, aSlotIndex);
            }

            mte.addCrop(aStack);
            if (aStack.stackSize <= 0) s.putStack(null);
            else s.putStack(aStack);
            detectAndSendChanges();
            return null;
        }
    }

    @Override
    protected void addConfigurationWidgets(DynamicPositionedRow configurationElements, UIBuildContext buildContext) {
        buildContext.addSyncedWindow(CONFIGURATION_WINDOW_ID, this::createConfigurationWindow);
        configurationElements.setSynced(false);
        configurationElements.widget(
            new ButtonWidget().setOnClick(
                (clickData, widget) -> {
                    if (!widget.isClient()) widget.getContext()
                        .openSyncedWindow(CONFIGURATION_WINDOW_ID);
                })
                .setBackground(GTUITextures.BUTTON_STANDARD, GTUITextures.OVERLAY_BUTTON_CYCLIC)
                .addTooltip(StatCollector.translateToLocal("Info_EdenGarden_Configuration"))
                .setSize(16, 16));
    }

    EIGDynamicInventory<EIGBucket> dynamicInventory = new EIGDynamicInventory<>(
        128,
        60,
        () -> this.maxSeedTypes,
        () -> this.maxSeedCount,
        this.buckets::size,
        this::getTotalSeedCount,
        this.buckets,
        EIGBucket::getSeedStack).allowInventoryInjection(this::addCrop)
            .allowInventoryExtraction((bucket, player) -> {
                if (bucket == null) return null;
                int maxRemove = bucket.getSeedStack()
                    .getMaxStackSize();
                ItemStack[] outputs = bucket.tryRemoveSeed(maxRemove, false);
                if (outputs == null || outputs.length == 0) return null;
                ItemStack ret = outputs[0];
                for (int i = 1; i < outputs.length; i++) {
                    ItemStack suppertItem = outputs[i];
                    if (!player.inventory.addItemStackToInventory(suppertItem)) {
                        player.entityDropItem(suppertItem, 0.f);
                    }
                }
                if (bucket.getSeedCount() <= 0) this.buckets.remove(bucket);
                return ret;
            })
            .setEnabled(() -> this.mMaxProgresstime == 0);

    @Override
    public void createInventorySlots() {}

    public boolean isInInventory = true;

    @Override
    public void addUIWidgets(ModularWindow.Builder builder, UIBuildContext buildContext) {
        isInInventory = !getBaseMetaTileEntity().isActive();
        builder.widget(
            new DrawableWidget().setDrawable(GTUITextures.PICTURE_SCREEN_BLACK)
                .setPos(4, 4)
                .setSize(190, 85)
                .setEnabled(w -> !isInInventory));
        builder.widget(
            dynamicInventory.asWidget(builder, buildContext)
                .setPos(10, 16)
                .setEnabled(w -> isInInventory));

        builder.widget(
            new CycleButtonWidget().setToggle(() -> isInInventory, i -> isInInventory = i)
                .setTextureGetter(
                    i -> i == 0 ? new Text(StatCollector.translateToLocal("Info_EdenGarden_Inventory"))
                        : new Text(StatCollector.translateToLocal("Info_EdenGarden_Status")))
                .setBackground(GTUITextures.BUTTON_STANDARD)
                .setPos(140, 91)
                .setSize(55, 16));

        final DynamicPositionedColumn screenElements = new DynamicPositionedColumn();
        drawTexts(screenElements, null);
        builder.widget(
            new Scrollable().setVerticalScroll()
                .widget(screenElements.setPos(10, 0))
                .setPos(0, 7)
                .setSize(190, 79)
                .setEnabled(w -> !isInInventory));

        builder.widget(createPowerSwitchButton(builder))
            .widget(createVoidExcessButton(builder))
            .widget(createInputSeparationButton(builder))
            .widget(createBatchModeButton(builder))
            .widget(createLockToSingleRecipeButton(builder))
            .widget(createStructureUpdateButton(builder));

        DynamicPositionedRow configurationElements = new DynamicPositionedRow();
        addConfigurationWidgets(configurationElements, buildContext);

        builder.widget(
            configurationElements.setSpace(2)
                .setAlignment(MainAxisAlignment.SPACE_BETWEEN)
                .setPos(getRecipeLockingButtonPos().add(18, 0)));
    }

    protected ModularWindow createConfigurationWindow(final EntityPlayer player) {
        ModularWindow.Builder builder = ModularWindow.builder(200, 100);
        builder.setBackground(ModularUITextures.VANILLA_BACKGROUND);
        builder.widget(
            new DrawableWidget().setDrawable(GTUITextures.OVERLAY_BUTTON_CYCLIC)
                .setPos(5, 5)
                .setSize(16, 16))
            .widget(new TextWidget(StatCollector.translateToLocal("Info_EdenGarden_Configuration")).setPos(25, 9))
            .widget(
                ButtonWidget.closeWindowButton(true)
                    .setPos(185, 3))
            .widget(
                new Column().widget(
                    new CycleButtonWidget().setLength(3)
                        .setGetter(() -> this.setupPhase)
                        .setSetter(val -> {
                            if (!(player instanceof EntityPlayerMP)) return;
                            tryChangeSetupPhase(player);
                        })
                        .addTooltip(
                            0,
                            new Text(StatCollector.translateToLocal("Info_EdenGarden_Operating"))
                                .color(Color.GREEN.dark(3)))
                        .addTooltip(
                            1,
                            new Text(StatCollector.translateToLocal("Info_EdenGarden_Input"))
                                .color(Color.YELLOW.dark(3)))
                        .addTooltip(
                            2,
                            new Text(StatCollector.translateToLocal("Info_EdenGarden_Output"))
                                .color(Color.YELLOW.dark(3)))
                        .setTextureGetter(
                            i -> i == 0
                                ? new Text(StatCollector.translateToLocal("Info_EdenGarden_Operating"))
                                    .color(Color.GREEN.dark(3))
                                    .withFixedSize(70 - 18, 18, 15, 0)
                                : i == 1
                                    ? new Text(StatCollector.translateToLocal("Info_EdenGarden_Input"))
                                        .color(Color.YELLOW.dark(3))
                                        .withFixedSize(70 - 18, 18, 15, 0)
                                    : new Text(StatCollector.translateToLocal("Info_EdenGarden_Output"))
                                        .color(Color.YELLOW.dark(3))
                                        .withFixedSize(70 - 18, 18, 15, 0))
                        .setBackground(
                            ModularUITextures.VANILLA_BACKGROUND,
                            GTUITextures.OVERLAY_BUTTON_CYCLIC.withFixedSize(18, 18))
                        .setSize(70, 18)
                        .addTooltip(StatCollector.translateToLocal("Info_EdenGarden_SetupMode")))
                    .widget(
                        new CycleButtonWidget().setLength(2)
                            .setGetter(() -> this.mode.getUIIndex())
                            .setSetter(val -> {
                                if (!(player instanceof EntityPlayerMP)) return;
                                tryChangeMode(player);
                            })
                            .addTooltip(
                                0,
                                new Text(StatCollector.translateToLocal("Info_EdenGarden_Disabled"))
                                    .color(Color.RED.dark(3)))
                            .addTooltip(
                                1,
                                new Text(StatCollector.translateToLocal("Info_EdenGarden_Enabled"))
                                    .color(Color.GREEN.dark(3)))
                            .setTextureGetter(
                                i -> i == 0
                                    ? new Text(StatCollector.translateToLocal("Info_EdenGarden_Disabled"))
                                        .color(Color.RED.dark(3))
                                        .withFixedSize(70 - 18, 18, 15, 0)
                                    : new Text(StatCollector.translateToLocal("Info_EdenGarden_Enabled"))
                                        .color(Color.GREEN.dark(3))
                                        .withFixedSize(70 - 18, 18, 15, 0))
                            .setBackground(
                                ModularUITextures.VANILLA_BACKGROUND,
                                GTUITextures.OVERLAY_BUTTON_CYCLIC.withFixedSize(18, 18))
                            .setSize(70, 18)
                            .addTooltip(StatCollector.translateToLocal("Info_EdenGarden_IC2Mode")))
                    .widget(
                        new CycleButtonWidget().setLength(2)
                            .setGetter(() -> useNoHumidity ? 1 : 0)
                            .setSetter(val -> {
                                if (!(player instanceof EntityPlayerMP)) return;
                                this.tryChangeHumidityMode(player);
                            })
                            .addTooltip(
                                0,
                                new Text(StatCollector.translateToLocal("Info_EdenGarden_Disabled"))
                                    .color(Color.RED.dark(3)))
                            .addTooltip(
                                1,
                                new Text(StatCollector.translateToLocal("Info_EdenGarden_Enabled"))
                                    .color(Color.GREEN.dark(3)))
                            .setTextureGetter(
                                i -> i == 0
                                    ? new Text(StatCollector.translateToLocal("Info_EdenGarden_Disabled"))
                                        .color(Color.RED.dark(3))
                                        .withFixedSize(70 - 18, 18, 15, 0)
                                    : new Text(StatCollector.translateToLocal("Info_EdenGarden_Enabled"))
                                        .color(Color.GREEN.dark(3))
                                        .withFixedSize(70 - 18, 18, 15, 0))
                            .setBackground(
                                ModularUITextures.VANILLA_BACKGROUND,
                                GTUITextures.OVERLAY_BUTTON_CYCLIC.withFixedSize(18, 18))
                            .setSize(70, 18)
                            .addTooltip(StatCollector.translateToLocal("Info_EdenGarden_NoHumidityMode")))
                    .setEnabled(widget -> !getBaseMetaTileEntity().isActive())
                    .setPos(10, 30))
            .widget(
                new Column()
                    .widget(
                        new TextWidget(StatCollector.translateToLocal("Info_EdenGarden_SetupMode")).setSize(100, 18))
                    .widget(new TextWidget(StatCollector.translateToLocal("Info_EdenGarden_IC2Mode")).setSize(100, 18))
                    .widget(
                        new TextWidget(StatCollector.translateToLocal("Info_EdenGarden_NoHumidityMode"))
                            .setSize(100, 18))
                    .setEnabled(widget -> !getBaseMetaTileEntity().isActive())
                    .setPos(80, 30))
            .widget(
                new DrawableWidget().setDrawable(GTUITextures.OVERLAY_BUTTON_CROSS)
                    .setSize(18, 18)
                    .setPos(10, 30)
                    .addTooltip(new Text("Can't change configuration when running !").color(Color.RED.dark(3)))
                    .setEnabled(widget -> getBaseMetaTileEntity().isActive()));
        return builder.build();
    }

    @Override
    public String generateCurrentRecipeInfoString() {
        StringBuilder ret = new StringBuilder(
            EnumChatFormatting.WHITE + StatCollector.translateToLocal("GT5U.gui.text.progress"))
                .append(String.format("%,.2f", (double) this.mProgresstime / 20))
                .append("s / ")
                .append(String.format("%,.2f", (double) this.mMaxProgresstime / 20))
                .append("s (")
                .append(String.format("%,.1f", (double) this.mProgresstime / this.mMaxProgresstime * 100))
                .append("%)\n");

        for (Map.Entry<ItemStack, Double> drop : this.synchedGUIDropTracker.entrySet()
            .stream()
            .sorted(
                Comparator.comparing(
                    a -> a.getKey()
                        .toString()
                        .toLowerCase()))
            .collect(Collectors.toList())) {
            int outputSize = Arrays.stream(this.mOutputItems)
                .filter(s -> s.isItemEqual(drop.getKey()))
                .mapToInt(i -> i.stackSize)
                .sum();
            ret.append(EnumChatFormatting.AQUA)
                .append(
                    drop.getKey()
                        .getDisplayName())
                .append(EnumChatFormatting.WHITE)
                .append(": ");
            if (outputSize == 0) {
                ret.append(String.format("%.2f", drop.getValue() * 100))
                    .append("%\n");
            } else {
                ret.append(EnumChatFormatting.GOLD)
                    .append(
                        String.format(
                            "x%d %s(+%.2f/s)\n",
                            outputSize,
                            EnumChatFormatting.WHITE,
                            (double) outputSize / (mMaxProgresstime / 20)));
            }
        }
        return ret.toString();
    }

    @Override
    protected void drawTexts(DynamicPositionedColumn screenElements, SlotWidget inventorySlot) {
        screenElements.widget(
            new FakeSyncWidget.BooleanSyncer(
                () -> this.mode == EIGModes.IC2,
                b -> this.mode = b ? EIGModes.IC2 : EIGModes.Normal));
        screenElements.widget(new FakeSyncWidget<>(() -> {
            HashMap<ItemStack, Double> ret = new HashMap<>();

            for (Map.Entry<ItemStack, Double> drop : this.guiDropTracker.entrySet()) {
                ret.merge(drop.getKey(), drop.getValue(), Double::sum);
            }

            return ret;
        }, h -> this.synchedGUIDropTracker = h, (buffer, h) -> {
            buffer.writeVarIntToBuffer(h.size());
            for (Map.Entry<ItemStack, Double> itemStackDoubleEntry : h.entrySet()) {
                try {
                    buffer.writeItemStackToBuffer(itemStackDoubleEntry.getKey());
                    buffer.writeDouble(itemStackDoubleEntry.getValue());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, buffer -> {
            int len = buffer.readVarIntFromBuffer();
            HashMap<ItemStack, Double> ret = new HashMap<>(len);
            for (int i = 0; i < len; i++) {
                try {
                    ret.put(buffer.readItemStackFromBuffer(), buffer.readDouble());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return ret;
        }));
        super.drawTexts(screenElements, inventorySlot);
    }

    @Override
    public String[] getInfoData() {
        List<String> info = new ArrayList<>(
            Arrays
                .asList(
                    StatCollector.translateToLocalFormatted(
                        "Info_EdenGarden_01",
                        EnumChatFormatting.GREEN
                            + (this.setupPhase == 0 ? this.mode.getName()
                                : StatCollector.translateToLocal(
                                    this.setupPhase == 1 ? "Info_EdenGarden_02" : "Info_EdenGarden_03"))
                            + EnumChatFormatting.RESET),

                    StatCollector.translateToLocalFormatted("Info_EdenGarden_04", waterUsage),

                    StatCollector.translateToLocalFormatted(
                        "Info_EdenGarden_05",
                        EnumChatFormatting.GREEN,
                        this.maxSeedTypes,
                        EnumChatFormatting.RESET),

                    StatCollector.translateToLocalFormatted(
                        "Info_EdenGarden_06",
                        ((this.buckets.size() > maxSeedTypes) ? EnumChatFormatting.RED : EnumChatFormatting.GREEN),
                        this.buckets.size())));

        for (EIGBucket bucket : buckets) {
            info.add(bucket.getInfoData());
        }

        if (this.buckets.size() > this.maxSeedTypes) {
            info.add(
                EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("Info_EdenGarden_07")
                    + EnumChatFormatting.RESET);
        }

        if (this.getTotalSeedCount() > this.maxSeedCount) {
            info.add(
                EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("Info_EdenGarden_08")
                    + EnumChatFormatting.RESET);
        }

        info.addAll(Arrays.asList(super.getInfoData()));
        return info.toArray(new String[0]);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
        int colorIndex, boolean aActive, boolean aRedstone) {
        if (side == facing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_DISTILLATION_TOWER_ACTIVE)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_DISTILLATION_TOWER_ACTIVE_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_DISTILLATION_TOWER)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_DISTILLATION_TOWER_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }
}
