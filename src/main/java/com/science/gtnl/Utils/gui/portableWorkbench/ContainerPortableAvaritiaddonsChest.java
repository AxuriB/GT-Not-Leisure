package com.science.gtnl.Utils.gui.portableWorkbench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.science.gtnl.common.item.items.PortableItem;

public class ContainerPortableAvaritiaddonsChest extends Container {

    public InventoryInfinityChest chestInventory = new InventoryInfinityChest(Integer.MAX_VALUE);

    public ContainerPortableAvaritiaddonsChest(ItemStack stack, InventoryPlayer playerInv) {
        PortableItem.loadInfinityInventory(stack, chestInventory);

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 27; x++) {
                addSlotToContainer(new Slot(chestInventory, y * 27 + x, 8 + (18 * x), 18 + (18 * y)));
            }
        }

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                addSlotToContainer(new Slot(playerInv, 9 + y * 9 + x, 170 + (18 * x), 194 + (18 * y)));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(playerInv, i, 170 + (18 * i), 252));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            itemstack = stackInSlot.copy();

            if (index < 243) {
                if (!this.mergeItemStack(stackInSlot, 243, this.inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(stackInSlot, 0, 243, false)) {
                return null;
            }

            if (stackInSlot.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }

        ItemStack held = player.getHeldItem();
        if (held != null && held.getItem() instanceof PortableItem) {
            if (held.getItemDamage() == 6) {
                PortableItem.saveAdvancedInventory(held, this.chestInventory);
            } else if (held.getItemDamage() == 7) {
                PortableItem.saveInfinityInventory(held, this.chestInventory);

            }
        }
        return itemstack;
    }
}
