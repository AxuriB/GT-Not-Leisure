package com.science.gtnl.common.item.steamRocket;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

import com.science.gtnl.config.MainConfig;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import micdoodle8.mods.galacticraft.api.recipe.SchematicPage;

public class SchematicSteamRocket extends SchematicPage {

    @Override
    public int getPageID() {
        return MainConfig.idSchematicRocketSteam;
    }

    @Override
    public int getGuiID() {
        return MainConfig.NASA_WORKBENCH_STEAM_ROCKET;
    }

    @Override
    public ItemStack getRequiredItem() {
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiScreen getResultScreen(EntityPlayer player, int x, int y, int z) {
        return new GuiSchematicSteamRocket(player.inventory, x, y, z);
    }

    @Override
    public Container getResultContainer(EntityPlayer player, int x, int y, int z) {
        return new ContainerSchematicSteamRocket(player.inventory, x, y, z);
    }
}
