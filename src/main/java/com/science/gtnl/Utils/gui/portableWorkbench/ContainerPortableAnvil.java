package com.science.gtnl.Utils.gui.portableWorkbench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.item.ItemStack;

import com.science.gtnl.common.item.items.PortableItem;

public class ContainerPortableAnvil extends ContainerRepair {

    public ContainerPortableAnvil(InventoryPlayer playerInv, EntityPlayer player) {
        super(playerInv, player.worldObj, 0, 0, 0, player);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        ItemStack held = player.getHeldItem();
        return held != null && held.getItemDamage() == 3 && held.getItem() instanceof PortableItem;
    }
}
