package com.science.gtnl.Utils.gui.portableWorkbench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import com.science.gtnl.common.item.items.PortableItem;

public class ContainerPortableEnderChest extends ContainerChest {

    public ContainerPortableEnderChest(InventoryPlayer playerInv, IInventory enderInv) {
        super(playerInv, enderInv);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        ItemStack held = player.getHeldItem();
        return held != null && held.getItemDamage() == 4 && held.getItem() instanceof PortableItem;
    }
}
