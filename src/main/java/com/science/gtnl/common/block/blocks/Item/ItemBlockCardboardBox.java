package com.science.gtnl.common.block.blocks.Item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.science.gtnl.Utils.CardboardBoxUtils;
import com.science.gtnl.Utils.text.AnimatedTooltipHandler;
import com.science.gtnl.common.block.blocks.tile.TileEntityCardboardBox;
import com.science.gtnl.loader.BlockLoader;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Original Author: Mekanism
 * License: MIT
 * Original Code Date: 2014-01-13
 */
public class ItemBlockCardboardBox extends ItemBlock {

    private static boolean isMonitoring;

    public Block metaBlock;

    public ItemBlockCardboardBox(Block block) {
        super(block);
        metaBlock = block;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List<String> list, boolean flag) {
        list.add(
            AnimatedTooltipHandler.BLUE + StatCollector
                .translateToLocal("Tooltip_CardBoardBox_00_" + (getBlockData(itemstack) != null ? "Yes" : "No")));

        if (getBlockData(itemstack) != null) {
            list.add(
                StatCollector.translateToLocal("Tooltip_CardBoardBox_01")
                    + new ItemStack(getBlockData(itemstack).block, getBlockData(itemstack).meta).getDisplayName());
            list.add(StatCollector.translateToLocal("Tooltip_CardBoardBox_02") + getBlockData(itemstack).meta);

            if (getBlockData(itemstack).tileTag != null) {
                list.add(
                    StatCollector.translateToLocal("Tooltip_CardBoardBox_03")
                        + getBlockData(itemstack).tileTag.getString("id"));
            }
        }
    }

    @Override
    public int getMetadata(int i) {
        return i;
    }

    @Override
    public IIcon getIconFromDamage(int i) {
        return metaBlock.getIcon(2, i);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        if (!player.isSneaking() && !world.isAirBlock(x, y, z) && stack.getItemDamage() == 0) {
            Block block = world.getBlock(x, y, z);
            int meta = world.getBlockMetadata(x, y, z);

            if (!world.isRemote && CardboardBoxUtils.isBlockCompatible(Item.getItemFromBlock(block), meta)
                && block.getBlockHardness(world, x, y, z) != -1) {
                CardboardBoxUtils.BlockData data = new CardboardBoxUtils.BlockData();
                data.block = block;
                data.meta = meta;

                isMonitoring = true;

                if (world.getTileEntity(x, y, z) != null) {
                    TileEntity tile = world.getTileEntity(x, y, z);
                    NBTTagCompound tag = new NBTTagCompound();

                    tile.writeToNBT(tag);
                    data.tileTag = tag;
                }

                if (!player.capabilities.isCreativeMode) {
                    stack.stackSize--;
                }

                world.setBlock(x, y, z, BlockLoader.cardboardBox, 1, 3);

                isMonitoring = false;

                TileEntityCardboardBox tileEntity = (TileEntityCardboardBox) world.getTileEntity(x, y, z);

                if (tileEntity != null) {
                    tileEntity.storedData = data;
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ, int metadata) {
        if (world.isRemote) {
            return true;
        }

        boolean place = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);

        if (place) {
            TileEntityCardboardBox tileEntity = (TileEntityCardboardBox) world.getTileEntity(x, y, z);

            if (tileEntity != null) {
                tileEntity.storedData = getBlockData(stack);
            }
        }

        return place;
    }

    public void setBlockData(ItemStack itemstack, CardboardBoxUtils.BlockData data) {
        if (itemstack.stackTagCompound == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }

        itemstack.stackTagCompound.setTag("blockData", data.write(new NBTTagCompound()));
    }

    public CardboardBoxUtils.BlockData getBlockData(ItemStack itemstack) {
        if (itemstack.stackTagCompound == null || !itemstack.stackTagCompound.hasKey("blockData")) {
            return null;
        }

        return CardboardBoxUtils.BlockData.read(itemstack.stackTagCompound.getCompoundTag("blockData"));
    }

    @SubscribeEvent
    public void onEntitySpawn(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityItem && isMonitoring) {
            event.setCanceled(true);
        }
    }
}
