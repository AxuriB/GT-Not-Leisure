package com.science.gtnl.common.item.items;

import static com.science.gtnl.ScienceNotLeisure.*;
import static com.science.gtnl.Utils.item.ItemUtils.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

import com.github.bsideup.jabel.Desugar;
import com.reavaritia.common.SubtitleDisplay;
import com.reavaritia.common.item.ItemStackWrapper;
import com.reavaritia.common.item.ToolHelper;
import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.client.GTNLCreativeTabs;
import com.science.gtnl.loader.ItemLoader;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.items.MetaGeneratedTool;

public class VeinMiningPickaxe extends ItemPickaxe implements SubtitleDisplay {

    public VeinMiningPickaxe() {
        super(EnumHelper.addToolMaterial("VEIN", 15, Integer.MAX_VALUE, 15, 3, 10));
        this.setUnlocalizedName("VeinMiningPickaxe");
        this.setCreativeTab(GTNLCreativeTabs.GTNotLeisureItem);
        this.setTextureName(RESOURCE_ROOT_ID + ":" + "VeinMiningPickaxe");
        this.setMaxStackSize(1);
        this.setMaxDamage(Integer.MAX_VALUE);
        GTNLItemList.VeinMiningPickaxe.set(new ItemStack(this, 1));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> toolTip,
        boolean advancedToolTips) {
        NBTTagCompound tags = itemStack.getTagCompound();
        int range = 3;
        int amount = 32767;
        boolean preciseMode = false;

        if (tags != null) {
            if (tags.hasKey("range")) {
                range = Math.max(0, Math.min(32, tags.getInteger("range")));
            }
            if (tags.hasKey("amount")) {
                amount = Math.max(0, Math.min(327670, tags.getInteger("amount")));
            }
            if (tags.hasKey("preciseMode")) {
                preciseMode = tags.getBoolean("preciseMode");
            }
        }

        toolTip.add(StatCollector.translateToLocalFormatted("Tooltip_VeinMiningPickaxe_00", range));
        toolTip.add(StatCollector.translateToLocalFormatted("Tooltip_VeinMiningPickaxe_01", amount));
        toolTip.add(
            StatCollector.translateToLocal(
                preciseMode ? "Tooltip_VeinMiningPickaxe_PreciseMode_On"
                    : "Tooltip_VeinMiningPickaxe_PreciseMode_Off"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack, int pass) {
        NBTTagCompound nbt = stack.getTagCompound();
        return nbt != null && nbt.getBoolean("preciseMode");
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        setToolDamage(stack, damage);
    }

    @Override
    public int getDamage(ItemStack stack) {
        return Math.toIntExact(MetaGeneratedTool.getToolDamage(stack));
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return Math.toIntExact(MetaGeneratedTool.getToolMaxDamage(stack));
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int par4, int par5, int par6,
        EntityLivingBase entityLiving) {
        return true;
    }

    @Override
    public boolean canHarvestBlock(Block block, ItemStack stack) {
        return true;
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        return 20;
    }

    @Override
    public boolean isRepairable() {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack itemstack1, ItemStack itemstack2) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item aItem, CreativeTabs aCreativeTabs, List<ItemStack> aList) {
        ItemStack stack = new ItemStack(ItemLoader.veinMiningPickaxe, 1);
        setToolMaxDamage(stack, Integer.MAX_VALUE);
        aList.add(stack);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (player.isSneaking()) {
            NBTTagCompound tags = stack.getTagCompound();
            if (tags == null) {
                tags = new NBTTagCompound();
                stack.setTagCompound(tags);
            }

            boolean isPreciseMode = !tags.getBoolean("preciseMode");
            tags.setBoolean("preciseMode", isPreciseMode);
            player.swingItem();

            if (world.isRemote) {
                String key = isPreciseMode ? StatCollector.translateToLocal("Tooltip_VeinMiningPickaxe_PreciseMode_On")
                    : StatCollector.translateToLocal("Tooltip_VeinMiningPickaxe_PreciseMode_Off");
                showSubtitle(key);
            }
        }
        return stack;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
        if (player.worldObj.isRemote) return false;
        if (player.isSneaking()) {
            int range = 3;
            int amount = 32767;
            boolean preciseMode = false;
            NBTTagCompound tags = stack.getTagCompound();
            if (tags != null) {
                if (tags.hasKey("range")) {
                    range = Math.max(-1, Math.min(32, tags.getInteger("range")));
                }
                if (tags.hasKey("preciseMode")) {
                    preciseMode = tags.getBoolean("preciseMode");
                }
                if (tags.hasKey("amount")) {
                    amount = Math.max(0, Math.min(32767, tags.getInteger("amount")));
                }
            }

            Block block = player.worldObj.getBlock(x, y, z);
            int meta = player.worldObj.getBlockMetadata(x, y, z);

            if (block != null && range >= 0) {
                clearConnectedBlocks(player, stack, x, y, z, block, meta, range, amount, preciseMode);
            }
        }
        return false;
    }

    public void clearConnectedBlocks(EntityPlayer player, ItemStack stack, int x, int y, int z, Block targetBlock,
        int targetMeta, int maxGap, int amount, boolean preciseMode) {
        if (player.getFoodStats()
            .getFoodLevel() <= 0
            && player.getFoodStats()
                .getSaturationLevel() <= 0f
            && !player.capabilities.isCreativeMode) {
            return;
        }
        World world = player.worldObj;
        Queue<Node> queue = new ArrayDeque<>();
        Set<Long> visited = new HashSet<>();
        int cleared = 0;
        int blocksSinceHunger = 0;
        int toolMaxDamage = Math.toIntExact(MetaGeneratedTool.getToolMaxDamage(stack));
        List<ItemStack> allDrops = new ArrayList<>();

        queue.add(new Node(x, y, z, 0));

        while (!queue.isEmpty() && cleared < amount) {
            if (!player.isSneaking()) break;

            Node node = queue.poll();
            int px = node.x, py = node.y, pz = node.z;
            int gap = node.gap;

            long key = (((long) px) & 0x3FFFFFF) << 38 | (((long) py) & 0xFFF) << 26 | (((long) pz) & 0x3FFFFFF);
            if (!visited.add(key)) continue;
            if (!world.blockExists(px, py, pz)) continue;

            Block block = world.getBlock(px, py, pz);
            int meta = world.getBlockMetadata(px, py, pz);

            boolean matches = false;
            if (block.getBlockHardness(world, x, y, z) >= 0) {
                if (block == targetBlock && (!preciseMode || meta == targetMeta)) {
                    matches = true;
                } else {
                    ItemStack stackAt = new ItemStack(block, 1, meta);
                    int[] oreIds = OreDictionary.getOreIDs(stackAt);
                    for (int id : oreIds) {
                        String name = OreDictionary.getOreName(id);
                        int[] targetIds = OreDictionary.getOreIDs(new ItemStack(targetBlock, 1, targetMeta));
                        for (int tid : targetIds) {
                            String tname = OreDictionary.getOreName(tid);
                            if (preciseMode) {
                                if (tname.equals(name)) {
                                    matches = true;
                                    break;
                                }
                            } else {
                                if ((name.startsWith("ore") && tname.startsWith("ore")) || tname.startsWith(name)) {
                                    matches = true;
                                    break;
                                }
                            }
                        }

                        if (matches) break;
                    }
                }
            }

            if (matches) {
                List<ItemStack> drops = removeBlockAndGetDrops(
                    player,
                    stack,
                    world,
                    px,
                    py,
                    pz,
                    block,
                    EnchantmentHelper.getSilkTouchModifier(player),
                    0);
                allDrops.addAll(drops);

                cleared++;
                blocksSinceHunger++;
                gap = 0;

                if (blocksSinceHunger >= 50) {
                    blocksSinceHunger = 0;
                    player.getFoodStats()
                        .addExhaustion(1f);
                }

                if (player.worldObj.rand.nextFloat() < 0.5f && !player.capabilities.isCreativeMode) {
                    if (toolMaxDamage > 0) {
                        if (MetaGeneratedTool.getToolDamage(stack) + 1 >= toolMaxDamage) {
                            world.playSoundEffect(player.posX, player.posY, player.posZ, "random.break", 1.0F, 1.0F);
                            if (stack.stackSize > 0) stack.stackSize--;
                            break;
                        } else {
                            setToolDamage(stack, MetaGeneratedTool.getToolDamage(stack) + 1);
                        }
                    }
                }

            } else {
                if (gap >= maxGap) continue;
                gap++;
            }

            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        if (Math.abs(dx) + Math.abs(dy) + Math.abs(dz) == 1) {
                            queue.add(new Node(px + dx, py + dy, pz + dz, gap));
                        }
                    }
                }
            }
        }

        if (blocksSinceHunger > 0) {
            player.getFoodStats()
                .addExhaustion(1f);
        }

        Map<ItemStackWrapper, Integer> merged = new HashMap<>();
        for (ItemStack drop : allDrops) {
            if (drop == null) continue;
            ItemStackWrapper key = new ItemStackWrapper(drop);
            merged.put(key, merged.getOrDefault(key, 0) + drop.stackSize);
        }
        for (Map.Entry<ItemStackWrapper, Integer> entry : merged.entrySet()) {
            ItemStack dropStack = entry.getKey()
                .stack()
                .copy();
            dropStack.stackSize = entry.getValue();
            ToolHelper.dropItem(dropStack, world, player.posX, player.posY + 1, player.posZ);
        }
    }

    public List<ItemStack> removeBlockAndGetDrops(EntityPlayer player, ItemStack stack, World world, int x, int y,
        int z, Block block, boolean silk, int fortune) {
        List<ItemStack> drops = new ArrayList<>();
        if (!world.blockExists(x, y, z)) return drops;

        Block blk = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);

        if (blk == null || blk.isAir(world, x, y, z)) return drops;
        if (block != null && blk != block) return drops;

        float hardness = blk.getBlockHardness(world, x, y, z);
        if (hardness < 0) return drops;

        if (!player.capabilities.isCreativeMode) {
            blk.onBlockHarvested(world, x, y, z, meta, player);
            if (blk.removedByPlayer(world, player, x, y, z, true)) {
                blk.onBlockDestroyedByPlayer(world, x, y, z, meta);

                if (silk) {
                    ItemStack drop = blk
                        .getPickBlock(ToolHelper.raytraceFromEntity(world, player, true, 10), world, x, y, z, player);
                    if (drop != null) drops.add(drop);
                } else {
                    drops.addAll(blk.getDrops(world, x, y, z, meta, fortune));
                }
            }
        } else {
            world.setBlockToAir(x, y, z);
        }
        world.removeTileEntity(x, y, z);
        return drops;
    }

    @Desugar
    private record Node(int x, int y, int z, int gap) {}

    @SideOnly(Side.CLIENT)
    @Override
    public void showSubtitle(String messageKey) {
        IChatComponent component = new ChatComponentTranslation(messageKey);
        component.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.WHITE));
        Minecraft.getMinecraft().ingameGUI.func_110326_a(component.getFormattedText(), true);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void showSubtitle(String messageKey, int range) {
        IChatComponent component = new ChatComponentTranslation(messageKey, range);
        component.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.WHITE));
        Minecraft.getMinecraft().ingameGUI.func_110326_a(component.getFormattedText(), true);
    }

}
