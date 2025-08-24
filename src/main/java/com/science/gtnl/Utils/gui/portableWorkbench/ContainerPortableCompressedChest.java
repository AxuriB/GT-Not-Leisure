package com.science.gtnl.Utils.gui.portableWorkbench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

import com.science.gtnl.common.item.items.PortableItem;

public class ContainerPortableCompressedChest extends ContainerPortableAvaritiaddonsChest {

    public ContainerPortableCompressedChest(ItemStack stack, InventoryPlayer playerInv) {
        super(stack, playerInv);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        ItemStack held = player.getHeldItem();
        return held != null && held.getItemDamage() == 6 && held.getItem() instanceof PortableItem;
    }
}
