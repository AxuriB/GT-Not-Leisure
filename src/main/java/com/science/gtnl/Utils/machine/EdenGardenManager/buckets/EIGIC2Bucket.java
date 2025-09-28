package com.science.gtnl.Utils.machine.EdenGardenManager.buckets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import com.science.gtnl.Utils.machine.EdenGardenManager.EIGBucket;
import com.science.gtnl.Utils.machine.EdenGardenManager.EIGDropTable;
import com.science.gtnl.Utils.machine.EdenGardenManager.IEIGBucketFactory;
import com.science.gtnl.common.machine.multiblock.EdenGarden;

import gregtech.api.enums.ItemList;
import gregtech.common.blocks.BlockOresAbstract;
import gregtech.common.blocks.ItemOres;
import gregtech.common.blocks.TileEntityOres;
import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.core.Ic2Items;
import ic2.core.crop.CropStickreed;
import ic2.core.crop.IC2Crops;
import ic2.core.crop.TileEntityCrop;

public class EIGIC2Bucket extends EIGBucket {

    public final static IEIGBucketFactory factory = new Factory();
    public static final String NBT_IDENTIFIER = "IC2";
    public static final int REVISION_NUMBER = 0;
    public final static int NUMBER_OF_DROPS_TO_SIMULATE = 100;
    public static final int WATER_STORAGE_VALUE = 100;
    public static final int NUMBER_OF_DIRT_BLOCKS_UNDER = 100;
    public static final int FERTILIZER_STORAGE_VALUE = 100;
    public static final int CROP_OBSTRUCTION_VALUE = 100;
    public static final boolean CROP_CAN_SEE_SKY = true;

    public static class Factory implements IEIGBucketFactory {

        @Override
        public String getNBTIdentifier() {
            return NBT_IDENTIFIER;
        }

        @Override
        public EIGBucket tryCreateBucket(EdenGarden greenhouse, ItemStack input) {
            if (!ItemList.IC2_Crop_Seeds.isStackEqual(input, true, true)) return null;
            if (!input.hasTagCompound()) return null;
            NBTTagCompound nbt = input.getTagCompound();
            if (!(nbt.hasKey("growth") && nbt.hasKey("gain") && nbt.hasKey("resistance"))) return null;

            CropCard cc = IC2Crops.instance.getCropCard(input);
            if (cc == null) return null;
            return new EIGIC2Bucket(greenhouse, input);
        }

        @Override
        public EIGBucket restore(NBTTagCompound nbt) {
            return new EIGIC2Bucket(nbt);
        }
    }

    public final boolean useNoHumidity;
    public double growthTime = 0;
    public EIGDropTable drops = new EIGDropTable();
    public boolean isValid = false;

    public EIGIC2Bucket(ItemStack seed, int count, ItemStack supportBlock, boolean useNoHumidity) {
        super(seed, count, supportBlock == null ? null : new ItemStack[] { supportBlock });
        this.useNoHumidity = useNoHumidity;
        this.isValid = false;
    }

    public EIGIC2Bucket(EdenGarden greenhouse, ItemStack seed) {
        super(seed, 1, null);
        this.useNoHumidity = greenhouse.useNoHumidity;
        this.recalculateDrops(greenhouse);
    }

    public EIGIC2Bucket(NBTTagCompound nbt) {
        super(nbt);
        this.useNoHumidity = nbt.getBoolean("useNoHumidity");
        if (!nbt.hasKey("invalid")) {
            this.drops = new EIGDropTable(nbt, "drops");
            this.growthTime = nbt.getDouble("growthTime");
            this.isValid = nbt.getInteger("version") == REVISION_NUMBER && this.growthTime > 0 && !this.drops.isEmpty();
        }
    }

    @Override
    public NBTTagCompound save() {
        NBTTagCompound nbt = super.save();
        nbt.setBoolean("useNoHumidity", this.useNoHumidity);
        if (this.isValid) {
            nbt.setTag("drops", this.drops.save());
            nbt.setDouble("growthTime", this.growthTime);
        } else {
            nbt.setBoolean("invalid", true);
        }
        nbt.setInteger("version", REVISION_NUMBER);
        return nbt;
    }

    @Override
    protected String getNBTIdentifier() {
        return NBT_IDENTIFIER;
    }

    @Override
    public void addProgress(double multiplier, EIGDropTable tracker) {
        if (!this.isValid()) return;
        double growthPercent = multiplier / (this.growthTime * TileEntityCrop.tickRate);
        if (this.drops != null) {
            this.drops.addTo(tracker, this.seedCount * growthPercent);
        }
    }

    @Override
    protected void getAdditionalInfoData(StringBuilder sb) {
        sb.append(" | Humidity: ");
        sb.append(this.useNoHumidity ? "Off" : "On");
    }

    @Override
    public boolean revalidate(EdenGarden greenhouse) {
        this.recalculateDrops(greenhouse);
        return this.isValid();
    }

    @Override
    public boolean isValid() {
        return super.isValid() && this.isValid;
    }

    public void recalculateDrops(EdenGarden greenhouse) {
        this.isValid = false;
        World world = greenhouse.getBaseMetaTileEntity()
            .getWorld();
        int[] abc = new int[] { 0, -1, 5 };
        int[] xyz = new int[] { 0, 0, 0 };
        greenhouse.getExtendedFacing()
            .getWorldOffset(abc, xyz);
        xyz[0] += greenhouse.getBaseMetaTileEntity()
            .getXCoord();
        xyz[1] += greenhouse.getBaseMetaTileEntity()
            .getYCoord();
        xyz[2] += greenhouse.getBaseMetaTileEntity()
            .getZCoord();
        FakeTileEntityCrop crop;
        try {
            crop = new FakeTileEntityCrop(this, greenhouse, xyz);
            if (!crop.isValid) return;
            CropCard cc = crop.getCrop();

            if (this.supportItems != null && this.supportItems.length == 1 && this.supportItems[0] != null) {
                if (!setBlock(this.supportItems[0], xyz[0], xyz[1] + 2, xyz[2], world)) {
                    return;
                }
                crop.updateNutrientsForBlockUnder();
            }

            if (calcAvgGrowthRate(crop, cc, 0) < 0) return;
            if (calcAvgGrowthRate(crop, cc, 6) <= 0) return;

            ItemStack blockInputStackToConsume = null;

            if (this.supportItems == null) {
                cc.getGain(crop);
                if (crop.hasRequestedBlockUnder()) {
                    ArrayList<ItemStack> inputs = greenhouse.getStoredInputs();
                    boolean keepLooking = !inputs.isEmpty();
                    if (keepLooking && !crop.reqBlockOreDict.isEmpty()) {
                        oreDictLoop: for (String reqOreDictName : crop.reqBlockOreDict) {
                            if (reqOreDictName == null || OreDictionary.doesOreNameExist(reqOreDictName)) continue;
                            int oreId = OreDictionary.getOreID(reqOreDictName);
                            for (ItemStack potentialBlock : inputs) {
                                if (potentialBlock == null || potentialBlock.stackSize <= 0) continue;
                                for (int inputOreId : OreDictionary.getOreIDs(potentialBlock)) {
                                    if (inputOreId != oreId) continue;
                                    blockInputStackToConsume = potentialBlock;
                                    ItemStack newSupport = potentialBlock.copy();
                                    newSupport.stackSize = 1;
                                    this.supportItems = new ItemStack[] { newSupport };
                                    keepLooking = false;
                                    crop.updateNutrientsForBlockUnder();
                                    break oreDictLoop;
                                }
                            }
                        }
                    }
                    if (keepLooking && !crop.reqBlockSet.isEmpty()) {
                        blockLoop: for (Block reqBlock : crop.reqBlockSet) {
                            if (reqBlock == null || reqBlock instanceof BlockLiquid) continue;
                            for (ItemStack potentialBlockStack : inputs) {
                                if (potentialBlockStack == null || potentialBlockStack.stackSize <= 0) continue;
                                Block inputBlock = Block.getBlockFromItem(potentialBlockStack.getItem());
                                if (inputBlock != reqBlock) continue;
                                blockInputStackToConsume = potentialBlockStack;
                                ItemStack newSupport = potentialBlockStack.copy();
                                newSupport.stackSize = 1;
                                this.supportItems = new ItemStack[] { newSupport };
                                crop.updateNutrientsForBlockUnder();
                                break blockLoop;
                            }
                        }
                    }
                }
            }

            crop.setSize((byte) cc.maxSize());
            if (!cc.canBeHarvested(crop)) return;
            EIGDropTable drops = new EIGDropTable();
            double avgDropRounds = getRealAverageDropRounds(crop, cc);
            double avgStackIncrease = getRealAverageDropIncrease(crop, cc);
            HashMap<Integer, Integer> sizeAfterHarvestFrequencies = new HashMap<>();
            for (int i = 0; i < NUMBER_OF_DROPS_TO_SIMULATE; i++) {
                ItemStack drop = cc.getGain(crop);
                if (drop == null || drop.stackSize <= 0) continue;
                sizeAfterHarvestFrequencies.merge((int) cc.getSizeAfterHarvest(crop), 1, Integer::sum);

                double avgAmount = (drop.stackSize + avgStackIncrease) * avgDropRounds;
                drops.addDrop(drop, avgAmount / NUMBER_OF_DROPS_TO_SIMULATE);
            }
            if (drops.isEmpty()) return;
            double avgGrowthCyclesToHarvest = calcRealAvgGrowthRate(crop, cc, sizeAfterHarvestFrequencies);
            if (avgGrowthCyclesToHarvest <= 0) {
                return;
            }

            if (blockInputStackToConsume != null) blockInputStackToConsume.stackSize -= this.seedCount;
            this.growthTime = avgGrowthCyclesToHarvest;
            this.drops = drops;
            this.isValid = true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public static boolean setBlock(ItemStack stack, int x, int y, int z, World world) {
        Item item = stack.getItem();
        Block b = Block.getBlockFromItem(item);
        if (b == Blocks.air || !(item instanceof ItemBlock)) return false;
        short tDamage = (short) item.getDamage(stack);
        if (item instanceof ItemOres && tDamage > 0) {
            if (!world.setBlock(
                x,
                y,
                z,
                b,
                TileEntityOres
                    .getHarvestData(tDamage, ((BlockOresAbstract) b).getBaseBlockHarvestLevel(tDamage % 16000 / 1000)),
                0)) {
                return false;
            }
            TileEntityOres tTileEntity = (TileEntityOres) world.getTileEntity(x, y, z);
            tTileEntity.mMetaData = tDamage;
            tTileEntity.mNatural = false;
        } else world.setBlock(x, y, z, b, tDamage, 0);
        return true;
    }

    public static double getRealAverageDropRounds(TileEntityCrop te, CropCard cc) {
        double chance = (double) cc.dropGainChance() * Math.pow(1.03, te.getGain());
        double min = -10, max = 10;
        int steps = 10000;
        double stepSize = (max - min) / steps;
        double sum = 0;
        for (int k = 1; k <= steps - 1; k++) {
            sum += getWeightedDropChance(min + k * stepSize, chance);
        }
        double minVal = getWeightedDropChance(min, chance);
        double maxVal = getWeightedDropChance(max, chance);
        return stepSize * ((minVal + maxVal) / 2 + sum);
    }

    public static double stdNormDistr(double x) {
        return Math.exp(-0.5 * (x * x)) / SQRT2PI;
    }

    public static final double SQRT2PI = Math.sqrt(2.0d * Math.PI);

    public static double getWeightedDropChance(double x, double chance) {
        return Math.max(0L, Math.round(x * chance * 0.6827d + chance)) * stdNormDistr(x);
    }

    public static double getRealAverageDropIncrease(TileEntityCrop te, CropCard cc) {
        return (te.getGain() + 1) / 100.0d;
    }

    public static double calcRealAvgGrowthRate(TileEntityCrop te, CropCard cc,
        HashMap<Integer, Integer> sizeAfterHarvestFrequencies) {
        int[] growthSpeeds = new int[7];
        for (int i = 0; i < 7; i++) growthSpeeds[i] = calcAvgGrowthRate(te, cc, i);

        if (cc.getClass() == CropStickreed.class) {
            sizeAfterHarvestFrequencies.clear();
            sizeAfterHarvestFrequencies.put(1, 1);
            sizeAfterHarvestFrequencies.put(2, 1);
            sizeAfterHarvestFrequencies.put(3, 1);
        }

        int[] growthDurations = new int[cc.maxSize()];
        growthDurations[0] = 0;
        for (byte i = 1; i < growthDurations.length; i++) {
            te.setSize(i);
            growthDurations[i] = cc.growthDuration(te);
        }

        return calcRealAvgGrowthRate(growthSpeeds, growthDurations, sizeAfterHarvestFrequencies);
    }

    public static double calcRealAvgGrowthRate(int[] growthSpeeds, int[] stageGoals,
        HashMap<Integer, Integer> startStageFrequency) {

        int[] nonZeroSpeeds = Arrays.stream(growthSpeeds)
            .filter(x -> x > 0)
            .toArray();
        int zeroRolls = growthSpeeds.length - nonZeroSpeeds.length;
        if (zeroRolls >= growthSpeeds.length) return -1;

        double[] avgCyclePerStage = new double[stageGoals.length];
        double[] normalizedStageFrequencies = new double[stageGoals.length];
        long frequenciesSum = startStageFrequency.values()
            .parallelStream()
            .mapToInt(x -> x)
            .sum();
        for (int i = 0; i < stageGoals.length; i++) {
            avgCyclePerStage[i] = calcAvgCyclesToGoal(nonZeroSpeeds, stageGoals[i]);
            normalizedStageFrequencies[i] = startStageFrequency.getOrDefault(i, 0) * stageGoals.length
                / (double) frequenciesSum;
        }

        double[] frequencyMultipliers = new double[avgCyclePerStage.length];
        Arrays.fill(frequencyMultipliers, 1.0d);
        conv1DAndCopyToSignal(
            frequencyMultipliers,
            normalizedStageFrequencies,
            new double[avgCyclePerStage.length],
            0,
            frequencyMultipliers.length,
            0);

        for (int i = 0; i < avgCyclePerStage.length; i++) avgCyclePerStage[i] *= frequencyMultipliers[i];

        double average = Arrays.stream(avgCyclePerStage)
            .average()
            .orElse(-1);
        if (average <= 0) return -1;
        if (zeroRolls > 0) {
            average = average / nonZeroSpeeds.length * growthSpeeds.length;
        }

        // profit
        return average;
    }

    public static double calcAvgCyclesToGoal(int[] speeds, int goal) {
        // even if the goal is 0, it will always take at least 1 cycle.
        if (goal <= 0) return 1;
        double mult = 1.0d;
        int goalCap = speeds[speeds.length - 1] * 1000;
        if (goal > goalCap) {
            mult = (double) goal / goalCap;
            goal = goalCap;
        }
        double[] signal = new double[goal];
        Arrays.fill(signal, 0);
        signal[0] = 1;

        double[] kernel = tabulate(speeds, 1.0d / speeds.length);
        double[] convolutionTarget = new double[signal.length];

        double p, avgRolls = 1;
        int iterNo = 0;
        int min = speeds[0];
        int max = speeds[speeds.length - 1];
        do {
            avgRolls += p = conv1DAndCopyToSignal(signal, kernel, convolutionTarget, min, max, iterNo);
            iterNo += 1;
        } while (p >= 1e-1 / goal);
        return avgRolls * mult;
    }

    public static double[] tabulate(int[] bin, double multiplier) {
        double[] ret = new double[bin[bin.length - 1] + 1];
        Arrays.fill(ret, 0);
        for (int i : bin) ret[i] += multiplier;
        return ret;
    }

    public static double conv1DAndCopyToSignal(double[] signal, double[] kernel, double[] fixedLengthTarget,
        int minValue, int maxValue, int iterNo) {
        double sum = 0;
        int maxK = Math.min(signal.length, (iterNo + 1) * maxValue + 1);
        int startAt = Math.min(signal.length, minValue * (iterNo + 1));
        int k = Math.max(0, startAt - kernel.length);
        for (; k < startAt; k++) fixedLengthTarget[k] = 0;
        for (; k < maxK; k++) {
            fixedLengthTarget[k] = 0;
            for (int i = Math.max(0, k - kernel.length + 1); i <= k; i++) {
                double v = signal[i] * kernel[k - i];
                sum += v;
                fixedLengthTarget[k] += v;
            }
        }
        System.arraycopy(fixedLengthTarget, 0, signal, 0, signal.length);
        return sum;
    }

    public static int calcAvgGrowthRate(TileEntityCrop te, CropCard cc, int rngRoll) {
        int base = 3 + rngRoll + te.getGrowth();
        int have = cc.weightInfluences(te, te.getHumidity(), te.getNutrients(), te.getAirQuality()) * 5;
        return base * have / 5;
    }

    public static byte getHumidity(EdenGarden greenhouse, boolean useNoHumidity) {
        if (useNoHumidity) return 0;
        int value = 100;
        if (WATER_STORAGE_VALUE >= 5) value += 2;
        value += WATER_STORAGE_VALUE;
        return (byte) value;
    }

    public static byte getNutrients(EdenGarden greenhouse) {
        int value = 100;
        value += NUMBER_OF_DIRT_BLOCKS_UNDER;
        value += (FERTILIZER_STORAGE_VALUE + 19) / 2;
        return (byte) value;
    }

    public static byte getAirQuality(EdenGarden greenhouse) {
        int value = 100;
        value += CROP_OBSTRUCTION_VALUE / 2;
        if (CROP_CAN_SEE_SKY) value += 2;
        return (byte) value;
    }

    public static class FakeTileEntityCrop extends TileEntityCrop {

        public boolean isValid;
        public Set<Block> reqBlockSet = new HashSet<>();
        public Set<String> reqBlockOreDict = new HashSet<>();

        public FakeTileEntityCrop(EIGIC2Bucket bucket, EdenGarden greenhouse, int[] xyz) {
            super();
            this.isValid = false;
            this.ticker = 1;

            CropCard cc = Crops.instance.getCropCard(bucket.seed);
            this.setCrop(cc);
            NBTTagCompound nbt = bucket.seed.getTagCompound();
            this.setGrowth(nbt.getByte("growth"));
            this.setGain(nbt.getByte("gain"));
            this.setResistance(nbt.getByte("resistance"));
            this.setWorldObj(
                greenhouse.getBaseMetaTileEntity()
                    .getWorld());

            this.xCoord = xyz[0];
            this.yCoord = xyz[1];
            this.zCoord = xyz[2];
            this.blockType = Block.getBlockFromItem(Ic2Items.crop.getItem());
            this.blockMetadata = 0;

            this.waterStorage = bucket.useNoHumidity ? 0 : WATER_STORAGE_VALUE;
            this.humidity = EIGIC2Bucket.getHumidity(greenhouse, bucket.useNoHumidity);
            this.nutrientStorage = FERTILIZER_STORAGE_VALUE;
            this.nutrients = EIGIC2Bucket.getNutrients(greenhouse);
            this.airQuality = EIGIC2Bucket.getAirQuality(greenhouse);

            this.isValid = true;
        }

        @Override
        public boolean isBlockBelow(Block reqBlock) {
            this.reqBlockSet.add(reqBlock);
            return super.isBlockBelow(reqBlock);
        }

        @Override
        public boolean isBlockBelow(String oreDictionaryName) {
            this.reqBlockOreDict.add(oreDictionaryName);
            return super.isBlockBelow(oreDictionaryName);
        }

        @Override
        public int getLightLevel() {
            return 15;
        }

        @Override
        public byte getHumidity() {
            return (byte) 100;
        }

        @Override
        public byte updateHumidity() {
            return (byte) 100;
        }

        @Override
        public byte getNutrients() {
            return (byte) 100;
        }

        @Override
        public byte updateNutrients() {
            return (byte) 100;
        }

        @Override
        public byte getAirQuality() {
            return (byte) 100;
        }

        @Override
        public byte updateAirQuality() {
            return (byte) 100;
        }

        public void updateNutrientsForBlockUnder() {
            if ((this.getCrop()
                .getrootslength(this) - 1
                - NUMBER_OF_DIRT_BLOCKS_UNDER) <= 0 && this.nutrients > 0) {
                this.nutrients--;
            }
        }

        public boolean hasRequestedBlockUnder() {
            return !this.reqBlockSet.isEmpty() || !this.reqBlockOreDict.isEmpty();
        }
    }
}
