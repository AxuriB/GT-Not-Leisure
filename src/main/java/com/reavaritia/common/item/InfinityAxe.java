package com.reavaritia.common.item;

import static com.reavaritia.ReAvaritia.RESOURCE_ROOT_ID;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

import com.reavaritia.ReAvaCreativeTabs;
import com.reavaritia.ReAvaItemList;
import com.reavaritia.common.ItemLoader;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class InfinityAxe extends ItemAxe {

    private static final ToolMaterial INFINITY = EnumHelper.addToolMaterial("INFINITY", 32, 9999, 9999F, 98F, 200);
    private static final int LEAF_RADIUS = 16;

    public InfinityAxe() {
        super(INFINITY);
        setUnlocalizedName("InfinityAxe");
        setTextureName(RESOURCE_ROOT_ID + ":" + "InfinityAxe");
        setCreativeTab(ReAvaCreativeTabs.ReAvaritia);
        setMaxDamage(9999);
        ReAvaItemList.InfinityAxe.set(new ItemStack(this, 1));
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        super.setDamage(stack, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> toolTip,
        boolean advancedToolTips) {
        toolTip.add(StatCollector.translateToLocal("Tooltip_InfinityAxe_00"));
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
        if (player.isSneaking()) {
            World world = player.worldObj;
            Block targetBlock = world.getBlock(x, y, z);

            if (isLog(targetBlock)) {
                harvestTree(world, x, y, z, targetBlock, player);
                return true;
            }
        }
        return super.onBlockStartBreak(stack, x, y, z, player);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (player.isSneaking() && !world.isRemote) {
            processLeaves(world, player);
        }
        return stack;
    }

    private void harvestTree(World world, int x, int y, int z, Block logBlock, EntityPlayer player) {
        Map<ItemStackWrapper, Integer> logCounts = new HashMap<>();
        Set<BlockPos> logs = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();
        queue.add(new BlockPos(x, y, z));

        // BFS收集原木
        while (!queue.isEmpty()) {
            BlockPos pos = queue.poll();
            if (logs.contains(pos)) continue;

            if (world.getBlock(pos.x, pos.y, pos.z) == logBlock) {
                // 获取掉落物
                List<ItemStack> drops = logBlock.getDrops(
                    world,
                    pos.x,
                    pos.y,
                    pos.z,
                    world.getBlockMetadata(pos.x, pos.y, pos.z),
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, player.getHeldItem()));

                // 合并到统计
                for (ItemStack stack : drops) {
                    ItemStackWrapper key = new ItemStackWrapper(stack.getItem(), stack.getItemDamage());
                    logCounts.put(key, logCounts.getOrDefault(key, 0) + stack.stackSize);
                }

                world.setBlockToAir(pos.x, pos.y, pos.z);
                logs.add(pos);

                // 添加相邻方块
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        for (int dz = -1; dz <= 1; dz++) {
                            queue.add(new BlockPos(pos.x + dx, pos.y + dy, pos.z + dz));
                        }
                    }
                }
            }
        }

        // 生成物质团
        if (!logCounts.isEmpty()) {
            generateMatterCluster(world, player, logCounts);
        }
    }

    private void processLeaves(World world, EntityPlayer player) {
        if (world.isRemote) return;

        Map<ItemStackWrapper, Integer> leavesCount = new HashMap<>();
        ItemStack tool = player.getHeldItem();
        int silkTouchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, tool);

        int baseX = MathHelper.floor_double(player.posX);
        int baseY = MathHelper.floor_double(player.posY);
        int baseZ = MathHelper.floor_double(player.posZ);

        for (int dx = -LEAF_RADIUS; dx <= LEAF_RADIUS; dx++) {
            for (int dy = -LEAF_RADIUS; dy <= LEAF_RADIUS; dy++) {
                for (int dz = -LEAF_RADIUS; dz <= LEAF_RADIUS; dz++) {
                    int x = baseX + dx;
                    int y = baseY + dy;
                    int z = baseZ + dz;

                    Block block = world.getBlock(x, y, z);
                    int meta = world.getBlockMetadata(x, y, z);

                    if (block instanceof BlockLeaves) {
                        // 修正1：移除腐烂状态检查
                        // 修正2：设置正确的harvest等级
                        int harvestLevel = (int) this.efficiencyOnProperMaterial; // 获取斧头的原生采集等级
                        List<ItemStack> drops = block.getDrops(world, x, y, z, meta, harvestLevel);

                        world.setBlockToAir(x, y, z);

                        for (ItemStack stack : drops) {
                            // 修正3：修改过滤逻辑
                            if (shouldKeepItem(stack, silkTouchLevel)) {
                                ItemStackWrapper key = new ItemStackWrapper(stack.getItem(), stack.getItemDamage());
                                leavesCount.put(key, leavesCount.getOrDefault(key, 0) + stack.stackSize);
                            }
                        }
                    }
                }
            }
        }

        if (!leavesCount.isEmpty()) {
            generateMatterCluster(world, player, leavesCount);
        }
    }

    // 修正后的物品过滤方法
    private boolean shouldKeepItem(ItemStack stack, int silkLevel) {
        if (silkLevel > 0) {
            // 精准采集模式：只保留树叶
            return stack.getItem() == Item.getItemFromBlock(Blocks.leaves);
        } else {
            // 普通模式：保留所有掉落物（包括树苗和木棍）
            return true;
        }
    }

    private void generateMatterCluster(World world, EntityPlayer player, Map<ItemStackWrapper, Integer> items) {
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagCompound clusterItems = new NBTTagCompound();

        // 构建total
        int total = items.values()
            .stream()
            .mapToInt(Integer::intValue)
            .sum();
        clusterItems.setInteger("total", total);

        // 构建items列表
        NBTTagList itemsList = new NBTTagList();
        items.forEach((key, count) -> {
            NBTTagCompound entry = new NBTTagCompound();

            // 构建item子标签
            NBTTagCompound itemTag = new NBTTagCompound();
            itemTag.setShort("id", (short) Item.getIdFromItem(key.item));
            itemTag.setByte("Count", (byte) 1);
            itemTag.setShort("Damage", (short) key.damage);

            entry.setTag("item", itemTag);
            entry.setInteger("count", count);

            itemsList.appendTag(entry);
        });

        clusterItems.setTag("items", itemsList);
        tag.setTag("clusteritems", clusterItems);

        // 创建物品实体
        ItemStack cluster = new ItemStack(ItemLoader.MatterCluster, 1);
        cluster.setTagCompound(tag);

        EntityItem entity = new EntityItem(world, player.posX, player.posY + 0.5, player.posZ, cluster);
        entity.delayBeforeCanPickup = 0;
        world.spawnEntityInWorld(entity);
    }

    private boolean isLog(Block block) {
        return block == Blocks.log || block == Blocks.log2;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumHelper.addRarity("COSMIC", EnumChatFormatting.RED, "Cosmic");
    }

    @Override
    public boolean hasEffect(ItemStack stack, int pass) {
        return true;
    }

    public static class ItemStackWrapper {

        final Item item;
        final int damage;

        ItemStackWrapper(Item item, int damage) {
            this.item = item;
            this.damage = damage;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ItemStackWrapper that)) return false;
            return damage == that.damage && Item.getIdFromItem(item) == Item.getIdFromItem(that.item);
        }

        @Override
        public int hashCode() {
            return 31 * Item.getIdFromItem(item) + damage;
        }
    }

    public static class BlockPos {

        final int x, y, z;

        BlockPos(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BlockPos blockPos)) return false;
            return x == blockPos.x && y == blockPos.y && z == blockPos.z;
        }

        @Override
        public int hashCode() {
            return 31 * (31 * x + y) + z;
        }
    }
}
