package com.science.gtnl.Utils.gui.portableWorkbench;

import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;

public class GuiPortableEnchanting extends GuiEnchantment {

    public GuiPortableEnchanting(InventoryPlayer playerInv, World world) {
        super(playerInv, world, 0, 0, 0, null);
    }
}
