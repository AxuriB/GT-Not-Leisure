package com.science.gtnl.Utils.machine.EdenGardenManager.buckets;

import static com.science.gtnl.common.machine.multiblock.EdenGarden.*;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.IPlantable;

import com.science.gtnl.Utils.machine.EdenGardenManager.EIGBucket;
import com.science.gtnl.Utils.machine.EdenGardenManager.EIGDropTable;
import com.science.gtnl.Utils.machine.EdenGardenManager.IEIGBucketFactory;
import com.science.gtnl.common.machine.multiblock.EdenGarden;

import gregtech.mixin.interfaces.accessors.IBlockStemAccessor;

public class EIGStemBucket extends EIGBucket {

    public static final IEIGBucketFactory factory = new Factory();
    private static final String NBT_IDENTIFIER = "STEM";
    private static final int REVISION_NUMBER = 0;

    private boolean isValid = false;
    private EIGDropTable drops = new EIGDropTable();

    public static class Factory implements IEIGBucketFactory {

        @Override
        public String getNBTIdentifier() {
            return NBT_IDENTIFIER;
        }

        @Override
        public EIGBucket tryCreateBucket(EdenGarden greenhouse, ItemStack input) {
            Item item = input.getItem();
            if (!(item instanceof IPlantable)) return null;

            Block block = ((IPlantable) item).getPlant(
                greenhouse.getBaseMetaTileEntity()
                    .getWorld(),
                0,
                0,
                0);

            if (!(block instanceof BlockStem)) return null;

            return new EIGStemBucket(greenhouse, input);
        }

        @Override
        public EIGBucket restore(NBTTagCompound nbt) {
            return new EIGStemBucket(nbt);
        }
    }

    private EIGStemBucket(EdenGarden greenhouse, ItemStack input) {
        super(input, 1, null);
        recalculateDrops(greenhouse);
    }

    private EIGStemBucket(NBTTagCompound nbt) {
        super(nbt);
        this.drops = new EIGDropTable(nbt, "drops");
        this.isValid = nbt.getInteger("version") == REVISION_NUMBER && !this.drops.isEmpty();
    }

    @Override
    public NBTTagCompound save() {
        NBTTagCompound nbt = super.save();
        if (this.drops != null) {
            nbt.setTag("drops", this.drops.save());
        }
        nbt.setInteger("version", REVISION_NUMBER);
        return nbt;
    }

    @Override
    public String getNBTIdentifier() {
        return NBT_IDENTIFIER;
    }

    @Override
    public void addProgress(double multiplier, EIGDropTable tracker) {
        if (!this.isValid()) return;
        this.drops.addTo(tracker, multiplier * this.seedCount);
    }

    @Override
    public boolean isValid() {
        return super.isValid() && this.isValid;
    }

    @Override
    public boolean revalidate(EdenGarden greenhouse) {
        recalculateDrops(greenhouse);
        return this.isValid();
    }

    /**
     * 重新计算茎类作物的掉落。
     * 南瓜/西瓜类作物特殊处理：直接掉落对应方块。
     */
    public void recalculateDrops(EdenGarden greenhouse) {
        this.isValid = false;

        Item item = this.seed.getItem();
        if (!(item instanceof IPlantable)) return;

        var base = greenhouse.getBaseMetaTileEntity();
        var world = base.getWorld();
        int x = base.getXCoord();
        int y = base.getYCoord();
        int z = base.getZCoord();

        Block stemBlock = ((IPlantable) item).getPlant(world, x, y, z);
        if (!(stemBlock instanceof BlockStem)) return;

        Block cropBlock = ((IBlockStemAccessor) stemBlock).gt5u$getCropBlock();
        if (cropBlock == null || cropBlock == Blocks.air) return;

        int metadata = 0; // 如果某些作物需要特定 metadata，可以在这里扩展

        EIGDropTable newDrops = new EIGDropTable();

        for (int i = 0; i < NUMBER_OF_DROPS_TO_SIMULATE; i++) {
            List<ItemStack> blockDrops = cropBlock.getDrops(world, x, y, z, metadata, 0);
            if (blockDrops.isEmpty()) continue;

            // 特殊情况：如果是南瓜或西瓜，直接掉落自身方块，跳出循环
            if (i == 0 && blockDrops.size() == 1) {
                ItemStack drop = blockDrops.get(0);
                if (drop != null && drop.getItem() == Item.getItemFromBlock(cropBlock)) {
                    newDrops.addDrop(drop, drop.stackSize);
                    break;
                }
            }

            for (ItemStack drop : blockDrops) {
                newDrops.addDrop(drop, drop.stackSize);
            }
        }

        // 归一化到平均值
        newDrops.entrySet()
            .forEach(e -> e.setValue(e.getValue() / NUMBER_OF_DROPS_TO_SIMULATE));

        if (newDrops.isEmpty()) return;

        this.drops = newDrops;
        this.isValid = true;
    }
}
