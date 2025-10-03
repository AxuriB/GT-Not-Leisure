package com.science.gtnl.Utils.machine.EdenGardenManager.buckets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockVine;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.science.gtnl.Utils.machine.EdenGardenManager.EIGBucket;
import com.science.gtnl.Utils.machine.EdenGardenManager.EIGDropTable;
import com.science.gtnl.Utils.machine.EdenGardenManager.IEIGBucketFactory;
import com.science.gtnl.common.machine.multiblock.EdenGarden;

public class EIGFlowerBucket extends EIGBucket {

    public static final IEIGBucketFactory factory = new Factory();
    public static final String NBT_IDENTIFIER = "FLOWER";
    public static final int REVISION_NUMBER = 1;

    public static class Factory implements IEIGBucketFactory {

        @Override
        public String getNBTIdentifier() {
            return NBT_IDENTIFIER;
        }

        @Override
        public EIGBucket tryCreateBucket(EdenGarden greenhouse, ItemStack input) {
            Item item = input.getItem();
            Block block = Block.getBlockFromItem(item);

            boolean isValidPlant = item == Items.reeds || block == Blocks.cactus
                || block instanceof BlockFlower
                || block instanceof BlockMushroom
                || block instanceof BlockDoublePlant
                || block instanceof BlockVine;

            if (!isValidPlant) return null;

            return new EIGFlowerBucket(input);
        }

        @Override
        public EIGBucket restore(NBTTagCompound nbt) {
            return new EIGFlowerBucket(nbt);
        }
    }

    private EIGFlowerBucket(ItemStack input) {
        super(input, 1, null);
    }

    private EIGFlowerBucket(NBTTagCompound nbt) {
        super(nbt);
    }

    @Override
    public NBTTagCompound save() {
        NBTTagCompound nbt = super.save();
        nbt.setInteger("version", REVISION_NUMBER);
        return nbt;
    }

    @Override
    protected String getNBTIdentifier() {
        return NBT_IDENTIFIER;
    }

    @Override
    public void addProgress(double multiplier, EIGDropTable tracker) {
        int dropCount = seedCount;
        Block block = Block.getBlockFromItem(seed.getItem());
        if (block instanceof BlockDoublePlant) dropCount *= 2;

        tracker.addDrop(seed, dropCount * multiplier);
    }

    @Override
    public boolean revalidate(EdenGarden greenhouse) {
        return this.isValid();
    }
}
