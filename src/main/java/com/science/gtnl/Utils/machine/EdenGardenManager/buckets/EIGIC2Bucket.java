package com.science.gtnl.Utils.machine.EdenGardenManager.buckets;

import static com.science.gtnl.common.machine.multiblock.EdenGarden.*;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.science.gtnl.Utils.machine.EdenGardenManager.EIGBucket;
import com.science.gtnl.Utils.machine.EdenGardenManager.EIGDropTable;
import com.science.gtnl.Utils.machine.EdenGardenManager.IEIGBucketFactory;
import com.science.gtnl.common.machine.multiblock.EdenGarden;

import gregtech.api.enums.ItemList;
import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.core.Ic2Items;
import ic2.core.crop.TileEntityCrop;

public class EIGIC2Bucket extends EIGBucket {

    public final static IEIGBucketFactory factory = new Factory();
    public static final String NBT_IDENTIFIER = "IC2";
    public static final int REVISION_NUMBER = 0;

    public static class Factory implements IEIGBucketFactory {

        @Override
        public String getNBTIdentifier() {
            return NBT_IDENTIFIER;
        }

        @Override
        public EIGBucket tryCreateBucket(EdenGarden greenhouse, ItemStack input) {
            if (!ItemList.IC2_Crop_Seeds.isStackEqual(input, true, true)) return null;
            if (!input.hasTagCompound()) return null;

            CropCard cc = Crops.instance.getCropCard(input);
            if (cc == null) return null;
            return new EIGIC2Bucket(greenhouse, input);
        }

        @Override
        public EIGBucket restore(NBTTagCompound nbt) {
            return new EIGIC2Bucket(nbt);
        }
    }

    public double growthTime = 0;
    public EIGDropTable drops = new EIGDropTable();
    public boolean isValid = false;

    public EIGIC2Bucket(EdenGarden greenhouse, ItemStack seed) {
        super(seed, 1, null);
        this.recalculateDrops(greenhouse);
    }

    public EIGIC2Bucket(NBTTagCompound nbt) {
        super(nbt);
        if (!nbt.hasKey("invalid")) {
            this.drops = new EIGDropTable(nbt, "drops");
            this.growthTime = nbt.getDouble("growthTime");
            this.isValid = nbt.getInteger("version") == REVISION_NUMBER && this.growthTime > 0 && !this.drops.isEmpty();
        }
    }

    @Override
    public NBTTagCompound save() {
        NBTTagCompound nbt = super.save();
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
        double growthPercent = multiplier / this.growthTime;
        if (this.drops != null) {
            this.drops.addTo(tracker, this.seedCount * growthPercent);
        }
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
        int[] xyz = new int[] { greenhouse.getBaseMetaTileEntity()
            .getXCoord(),
            greenhouse.getBaseMetaTileEntity()
                .getYCoord(),
            greenhouse.getBaseMetaTileEntity()
                .getZCoord() };

        try {
            FakeTileEntityCrop crop = new FakeTileEntityCrop(this, greenhouse, xyz);
            if (!crop.isValid) return;

            CropCard cc = crop.getCrop();
            crop.setSize((byte) cc.maxSize());

            EIGDropTable drops = new EIGDropTable();
            for (int i = 0; i < NUMBER_OF_DROPS_TO_SIMULATE; i++) {
                ItemStack drop = cc.getGain(crop);
                if (drop == null || drop.stackSize <= 0) continue;
                drops.addDrop(drop, drop.stackSize / (double) NUMBER_OF_DROPS_TO_SIMULATE);
            }

            if (drops.isEmpty()) return;

            this.growthTime = 1.0;
            this.drops = drops;
            this.isValid = true;

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public static class FakeTileEntityCrop extends TileEntityCrop {

        public boolean isValid;

        public FakeTileEntityCrop(EIGIC2Bucket bucket, EdenGarden greenhouse, int[] xyz) {
            super();
            this.isValid = false;
            this.ticker = 1;

            CropCard cc = Crops.instance.getCropCard(bucket.seed);
            this.setCrop(cc);
            this.setGrowth((byte) 31);
            this.setGain((byte) 31);
            this.setResistance((byte) 0);
            this.setWorldObj(
                greenhouse.getBaseMetaTileEntity()
                    .getWorld());

            this.xCoord = xyz[0];
            this.yCoord = xyz[1];
            this.zCoord = xyz[2];
            this.blockType = Block.getBlockFromItem(Ic2Items.crop.getItem());
            this.blockMetadata = 0;

            this.isValid = true;
        }

        @Override
        public int getLightLevel() {
            return 15;
        }

        @Override
        public byte getHumidity() {
            return 100;
        }

        @Override
        public byte updateHumidity() {
            return 100;
        }

        @Override
        public byte getNutrients() {
            return 100;
        }

        @Override
        public byte updateNutrients() {
            return 100;
        }

        @Override
        public byte getAirQuality() {
            return 100;
        }

        @Override
        public byte updateAirQuality() {
            return 100;
        }

        @Override
        public boolean isBlockBelow(Block reqBlock) {
            return true;
        }

        @Override
        public boolean isBlockBelow(String oreDictionaryName) {
            return true;
        }
    }
}
