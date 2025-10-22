package com.science.gtnl.utils;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Original Author: Mekanism
 * License: MIT
 * Original Code Date: 2014-01-13
 */
public class CardboardBoxUtils {

    public static Set<BlockInfo> cardboardBoxIgnore = new HashSet<>();

    public static boolean isBlockCompatible(Item item, int meta) {
        for (BlockInfo i : cardboardBoxIgnore) {
            if (i.block == Block.getBlockFromItem(item) && (i.meta == OreDictionary.WILDCARD_VALUE || i.meta == meta)) {
                return false;
            }
        }

        return true;
    }

    public static void addBoxBlacklist(Block block, int meta) {
        cardboardBoxIgnore.add(new BlockInfo(block, meta));
    }

    public static void addBoxBlacklist(Item item, int meta) {
        if (item instanceof ItemBlock itemBlock) {
            Block block = itemBlock.field_150939_a;
            addBoxBlacklist(block, meta);
        }
    }

    public static void addBoxBlacklist(ItemStack stack) {
        if (stack == null) return;
        Item item = stack.getItem();
        if (item instanceof ItemBlock itemBlock) {
            Block block = itemBlock.field_150939_a;
            int meta = stack.getItemDamage();
            addBoxBlacklist(block, meta);
        }
    }

    public static void removeBoxBlacklist(Block block, int meta) {
        cardboardBoxIgnore.remove(new BlockInfo(block, meta));
    }

    public static Set<BlockInfo> getBoxIgnore() {
        return cardboardBoxIgnore;
    }

    public static void setBlockData(ItemStack itemstack, CardboardBoxUtils.BlockData data) {
        if (itemstack.stackTagCompound == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }

        itemstack.stackTagCompound.setTag("blockData", data.write(new NBTTagCompound()));
    }

    public static CardboardBoxUtils.BlockData getBlockData(ItemStack itemstack) {
        if (itemstack.stackTagCompound == null || !itemstack.stackTagCompound.hasKey("blockData")) {
            return null;
        }

        return CardboardBoxUtils.BlockData.read(itemstack.stackTagCompound.getCompoundTag("blockData"));
    }

    public static class BlockInfo {

        public Block block;
        public int meta;

        public BlockInfo(Block b, int j) {
            block = b;
            meta = j;
        }

        public static BlockInfo get(ItemStack stack) {
            return new BlockInfo(Block.getBlockFromItem(stack.getItem()), stack.getItemDamage());
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof BlockInfo && ((BlockInfo) obj).block == block && ((BlockInfo) obj).meta == meta;
        }

        @Override
        public int hashCode() {
            int code = 1;
            code = 31 * code + block.getUnlocalizedName()
                .hashCode();
            code = 31 * code + meta;
            return code;
        }
    }

    public static class BlockData {

        public Block block;
        public int meta;
        public int metaSpecial = -1;
        public NBTTagCompound tileTag;

        public BlockData(Block block, int meta, int metaSpecial, NBTTagCompound nbtTags) {
            this.block = block;
            this.meta = meta;
            this.metaSpecial = metaSpecial;
            tileTag = nbtTags;
        }

        public BlockData() {}

        public void updateLocation(int x, int y, int z) {
            if (tileTag != null) {
                tileTag.setInteger("x", x);
                tileTag.setInteger("y", y);
                tileTag.setInteger("z", z);
            }
        }

        public NBTTagCompound write(NBTTagCompound nbtTags) {
            nbtTags.setInteger("id", Block.getIdFromBlock(block));
            nbtTags.setInteger("meta", meta);
            nbtTags.setInteger("metaSpecial", metaSpecial);

            if (tileTag != null) {
                nbtTags.setTag("tileTag", tileTag);
            }

            return nbtTags;
        }

        public static BlockData read(NBTTagCompound nbtTags) {
            BlockData data = new BlockData();

            data.block = Block.getBlockById(nbtTags.getInteger("id"));
            data.meta = nbtTags.getInteger("meta");
            data.metaSpecial = nbtTags.getInteger("metaSpecial");

            if (nbtTags.hasKey("tileTag")) {
                data.tileTag = nbtTags.getCompoundTag("tileTag");
            }

            return data;
        }
    }
}
