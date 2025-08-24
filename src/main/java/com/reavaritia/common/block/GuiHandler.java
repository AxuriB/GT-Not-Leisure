package com.reavaritia.common.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.reavaritia.common.block.ExtremeAnvil.ContainerExtremeAnvil;
import com.reavaritia.common.block.ExtremeAnvil.ExtremeAnvilGUI;
import com.reavaritia.common.block.ExtremeAnvil.TileEntityExtremeAnvil;
import com.reavaritia.common.block.NeutronCollector.ContainerNeutronItem;
import com.reavaritia.common.block.NeutronCollector.NeutronCollectorGUI;
import com.reavaritia.common.block.NeutronCollector.TileEntityNeutronCollector;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0)
            return new NeutronCollectorGUI(player.inventory, (TileEntityNeutronCollector) world.getTileEntity(x, y, z));
        if (ID == 1) {
            TileEntityExtremeAnvil tileEntity = (TileEntityExtremeAnvil) world.getTileEntity(x, y, z);
            return new ExtremeAnvilGUI(player.inventory, world, x, y, z, tileEntity, tileEntity, player);
        }
        return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0) return new ContainerNeutronItem(
            player.inventory,
            (TileEntityNeutronCollector) world.getTileEntity(x, y, z));
        if (ID == 1) {
            TileEntityExtremeAnvil tileEntity = (TileEntityExtremeAnvil) world.getTileEntity(x, y, z);
            return new ContainerExtremeAnvil(player.inventory, world, x, y, z, tileEntity, tileEntity, player);
        }
        return null;
    }

}
