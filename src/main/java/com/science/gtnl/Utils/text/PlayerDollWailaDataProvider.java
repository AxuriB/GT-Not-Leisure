package com.science.gtnl.Utils.text;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

import com.science.gtnl.common.block.blocks.tile.TileEntityPlayerDoll;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;

public class PlayerDollWailaDataProvider implements IWailaDataProvider {

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        return currentTip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        final NBTTagCompound tag = accessor.getNBTData();

        if (tag.hasKey("CapeHttp", 8)) {
            String capeUrl = tag.getString("CapeHttp");
            if (!StringUtils.isNullOrEmpty(capeUrl)) {
                currentTip.add(
                    EnumChatFormatting.AQUA + StatCollector.translateToLocal("Waila_TileEntityPlayerDoll_02")
                        + EnumChatFormatting.GOLD
                        + capeUrl);
            }
        }

        if (tag.hasKey("enableElytra")) {
            boolean enableElytra = tag.getBoolean("enableElytra");
            String elytraStatus = enableElytra ? StatCollector.translateToLocal("Waila_TileEntityPlayerDoll_03_On")
                : StatCollector.translateToLocal("Waila_TileEntityPlayerDoll_03_Off");
            currentTip.add(
                EnumChatFormatting.AQUA + StatCollector.translateToLocal("Waila_TileEntityPlayerDoll_03")
                    + EnumChatFormatting.GOLD
                    + elytraStatus);
        }

        if (tag.hasKey("SkinHttp", 8)) {
            String skinUrl = tag.getString("SkinHttp");
            if (!StringUtils.isNullOrEmpty(skinUrl)) {
                currentTip.add(
                    EnumChatFormatting.AQUA + StatCollector.translateToLocal("Waila_TileEntityPlayerDoll_01")
                        + EnumChatFormatting.GOLD
                        + skinUrl);
                return currentTip;
            }
        }

        if (tag.hasKey("SkullOwner", 8)) {
            String playerName = tag.getString("SkullOwner");
            if (!StringUtils.isNullOrEmpty(playerName)) {
                currentTip.add(
                    EnumChatFormatting.AQUA + StatCollector.translateToLocal("Waila_TileEntityPlayerDoll_00")
                        + EnumChatFormatting.GOLD
                        + playerName);
            }
        }

        return currentTip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        return currentTip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x,
        int y, int z) {
        if (te instanceof TileEntityPlayerDoll playerDoll) {

            if (playerDoll.getSkullOwner() != null) {
                tag.setString("SkullOwner", playerDoll.getSkullOwner());
            }

            if (playerDoll.getSkinHttp() != null) {
                tag.setString("SkinHttp", playerDoll.getSkinHttp());
            }
            if (playerDoll.getCapeHttp() != null) {
                tag.setString("CapeHttp", playerDoll.getCapeHttp());
            }
            tag.setBoolean("enableElytra", playerDoll.getEnableElytra());

        }
        return tag;
    }
}
