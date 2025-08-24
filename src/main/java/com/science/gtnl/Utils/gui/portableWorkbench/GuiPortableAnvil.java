package com.science.gtnl.Utils.gui.portableWorkbench;

import net.minecraft.client.gui.GuiRepair;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;

public class GuiPortableAnvil extends GuiRepair {

    public GuiPortableAnvil(InventoryPlayer playerInv, World world) {
        super(playerInv, world, 0, 0, 0);
    }
}
