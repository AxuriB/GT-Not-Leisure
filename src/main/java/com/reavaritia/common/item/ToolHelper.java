package com.reavaritia.common.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ToolHelper {

    public static Set<EntityPlayer> hammering = new HashSet<>();
    public static Map<EntityPlayer, List<ItemStack>> hammerdrops = new WeakHashMap<>();

    public static void removeBlocksInIteration(EntityPlayer player, ItemStack stack, World world, int x, int y, int z,
        int xs, int ys, int zs, int xe, int ye, int ze, Block block, Material[] materialsListing, boolean silk,
        int fortune, boolean dispose) {

        if (!hammerdrops.containsKey(player) || hammerdrops.get(player) == null) {
            hammerdrops.put(player, new ArrayList<>());
        }

        hammering.add(player);

        for (int x1 = xs; x1 < xe; x1++)
            for (int y1 = ys; y1 < ye; y1++) for (int z1 = zs; z1 < ze; z1++) removeBlockWithDrops(
                player,
                stack,
                world,
                x1 + x,
                y1 + y,
                z1 + z,
                block,
                materialsListing,
                silk,
                fortune,
                dispose);

        int meta = world.getBlockMetadata(x, y, z);
        if (!world.isRemote) world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));

        hammering.remove(player);
        if (!world.isRemote) {
            List<ItemStack> clusters = MatterCluster.makeClusters(hammerdrops.get(player));

            for (ItemStack cluster : clusters) {
                EntityItem ent = new EntityItem(world, x, y, z, cluster);
                world.spawnEntityInWorld(ent);
            }
        }

        hammerdrops.put(player, null);
    }

    public static boolean isRightMaterial(Material material, Material[] materialsListing) {
        for (Material mat : materialsListing) if (material == mat) return true;

        return false;
    }

    public static void removeBlockWithDrops(EntityPlayer player, ItemStack stack, World world, int x, int y, int z,
        Block block, Material[] materialsListing, boolean silk, int fortune, boolean dispose) {
        removeBlockWithDrops(player, stack, world, x, y, z, block, materialsListing, silk, fortune, dispose, false);
    }

    public static void removeBlockWithDrops(EntityPlayer player, ItemStack stack, World world, int x, int y, int z,
        Block block, Material[] materialsListing, boolean silk, int fortune, boolean dispose, boolean bypassMaterial) {
        if (!world.blockExists(x, y, z)) return;

        Block blk = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);

        if (blk == null || blk.isAir(world, x, y, z)) return;
        if (block != null && blk != block) return;

        Material mat = blk.getMaterial();
        if (!bypassMaterial && !isRightMaterial(mat, materialsListing)) return;

        float hardness = blk.getBlockHardness(world, x, y, z);
        if (hardness < 0) return;

        if (!player.capabilities.isCreativeMode) {
            blk.onBlockHarvested(world, x, y, z, meta, player);
            if (blk.removedByPlayer(world, player, x, y, z, true)) {
                blk.onBlockDestroyedByPlayer(world, x, y, z, meta);

                if (!dispose) {
                    List<ItemStack> drops;
                    if (silk) {
                        ItemStack drop = blk.getPickBlock(
                            ToolHelper.raytraceFromEntity(world, player, true, 10),
                            world,
                            x,
                            y,
                            z,
                            player);
                        drops = drop != null ? Collections.singletonList(drop) : Collections.emptyList();
                    } else {
                        drops = blk.getDrops(world, x, y, z, meta, fortune);
                    }

                    Map<ItemStackWrapper, Integer> merged = new HashMap<>();
                    for (ItemStack drop : drops) {
                        if (drop == null) continue;
                        ItemStackWrapper key = new ItemStackWrapper(drop);
                        merged.put(key, merged.getOrDefault(key, 0) + drop.stackSize);
                    }

                    for (Map.Entry<ItemStackWrapper, Integer> entry : merged.entrySet()) {
                        ItemStack dropStack = entry.getKey().stack().copy();
                        dropStack.stackSize = entry.getValue();
                        dropItem(dropStack, world, player.posX, player.posY + 1, player.posZ);
                    }
                }
            }
        } else {
            world.setBlockToAir(x, y, z);
        }
    }

    public static MovingObjectPosition raytraceFromEntity(World world, Entity player, boolean wut, double range) {
        float f = 1.0F;
        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double d0 = player.prevPosX + (player.posX - player.prevPosX) * f;
        double d1 = player.prevPosY + (player.posY - player.prevPosY) * f;
        if (!world.isRemote && player instanceof EntityPlayer) d1 += 1.62D;
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        Vec3 vec31 = vec3.addVector(f7 * range, f6 * range, f8 * range);
        return world.rayTraceBlocks(vec3, vec31, wut);
    }

    public static void dropItem(ItemStack drop, World world, double x, double y, double z) {
        float f = 0.7F;
        double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
        double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
        double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
        EntityItem entityitem = new EntityItem(world, x + d0, y + d1, z + d2, drop);
        entityitem.delayBeforeCanPickup = 10;
        world.spawnEntityInWorld(entityitem);
    }

    public static List<ItemStack> collateMatterClusterContents(Map<ItemStackWrapper, Integer> input) {
        List<ItemStack> collated = new ArrayList<>();
        if (input == null) return collated;
        for (Entry<ItemStackWrapper, Integer> e : input.entrySet()) {
            int count = e.getValue();
            ItemStackWrapper wrap = e.getKey();

            int size = wrap.stack().getMaxStackSize();
            int fullstacks = (int) (double) (count / size);

            for (int i = 0; i < fullstacks; i++) {
                count -= size;
                ItemStack stack = wrap.stack().copy();
                stack.stackSize = size;
                collated.add(stack);
            }

            if (count > 0) {
                ItemStack stack = wrap.stack().copy();
                stack.stackSize = count;
                collated.add(stack);
            }
        }

        return collated;
    }

    public static Map<ItemStackWrapper, Integer> collateMatterCluster(List<ItemStack> input) {
        Map<ItemStackWrapper, Integer> counts = new HashMap<>();

        if (input != null) {
            for (ItemStack stack : input) {
                ItemStackWrapper wrap = new ItemStackWrapper(stack);
                if (!counts.containsKey(wrap)) {
                    counts.put(wrap, 0);
                }

                counts.put(wrap, counts.get(wrap) + stack.stackSize);
            }
        }

        return counts;
    }
}
