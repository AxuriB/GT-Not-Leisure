package com.science.gtnl.Utils.gui.portableWorkbench;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiPortableEnderChest extends GuiChest {

    public GuiPortableEnderChest(InventoryPlayer playerInv, IInventory enderInv) {
        super(playerInv, enderInv);
    }
}
