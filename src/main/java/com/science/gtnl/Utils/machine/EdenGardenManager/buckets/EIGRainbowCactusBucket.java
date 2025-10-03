package com.science.gtnl.Utils.machine.EdenGardenManager.buckets;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.science.gtnl.Utils.machine.EdenGardenManager.EIGBucket;
import com.science.gtnl.Utils.machine.EdenGardenManager.EIGDropTable;
import com.science.gtnl.Utils.machine.EdenGardenManager.IEIGBucketFactory;
import com.science.gtnl.common.machine.multiblock.EdenGarden;

import tb.common.block.BlockRainbowCactus;
import tb.init.TBBlocks;

public class EIGRainbowCactusBucket extends EIGBucket {

    private static final Random RANDOM = new Random();
    private static final String NBT_IDENTIFIER = "TB:RAINCACTI";
    private static final ArrayList<ItemStack> TEMP_DROPS = new ArrayList<>();

    public static final IEIGBucketFactory factory = new Factory();

    public static class Factory implements IEIGBucketFactory {

        @Override
        public String getNBTIdentifier() {
            return NBT_IDENTIFIER;
        }

        @Override
        public EIGBucket tryCreateBucket(EdenGarden greenhouse, ItemStack input) {
            Block block = Block.getBlockFromItem(input.getItem());
            return (block instanceof BlockRainbowCactus) ? new EIGRainbowCactusBucket(input, 1) : null;
        }

        @Override
        public EIGBucket restore(NBTTagCompound nbt) {
            return new EIGRainbowCactusBucket(nbt);
        }
    }

    public EIGRainbowCactusBucket(ItemStack seed, int seedCount) {
        super(seed, seedCount, null);
    }

    public EIGRainbowCactusBucket(NBTTagCompound nbt) {
        super(nbt);
    }

    @Override
    public boolean revalidate(EdenGarden greenhouse) {
        return this.isValid();
    }

    @Override
    protected String getNBTIdentifier() {
        return NBT_IDENTIFIER;
    }

    @Override
    public void addProgress(double multiplier, EIGDropTable tracker) {
        if (!this.isValid()) return;

        TEMP_DROPS.clear();
        ((BlockRainbowCactus) TBBlocks.rainbowCactus).addDyeDropsToOutput(RANDOM, TEMP_DROPS);

        for (ItemStack drop : TEMP_DROPS) {
            if (drop != null && drop.stackSize > 0) {
                tracker.addDrop(drop, drop.stackSize * multiplier * this.seedCount);
            }
        }
    }
}
