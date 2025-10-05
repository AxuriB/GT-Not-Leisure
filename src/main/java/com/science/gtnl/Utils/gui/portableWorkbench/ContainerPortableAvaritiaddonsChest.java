package com.science.gtnl.Utils.gui.portableWorkbench;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.cleanroommc.bogosorter.api.IPosSetter;
import com.cleanroommc.bogosorter.api.ISortableContainer;
import com.cleanroommc.bogosorter.api.ISortingContextBuilder;
import com.science.gtnl.common.item.items.PortableItem;

import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "com.cleanroommc.bogosorter.api.ISortableContainer", modid = "bogosorter")
public class ContainerPortableAvaritiaddonsChest extends Container implements ISortableContainer {

    public InventoryInfinityChest chestInventory;
    public ItemStack itemStack;
    protected final String portableID;

    public ContainerPortableAvaritiaddonsChest(ItemStack stack, InventoryPlayer playerInv, boolean isInfinity) {
        chestInventory = isInfinity ? PortableItem.getInfinityInventory(stack) : PortableItem.getInventory(stack);
        itemStack = stack;

        this.portableID = PortableItem.ensurePortableID(stack);
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 27; x++) {
                addSlotToContainer(new Slot(chestInventory, y * 27 + x, 8 + (18 * x), 18 + (18 * y)) {

                    @Override
                    public boolean isItemValid(ItemStack stack) {
                        if (stack != null && stack.getItem() instanceof PortableItem
                            && (stack.getItemDamage() >= 6 && stack.getItemDamage() <= 17)) {
                            return false;
                        }
                        return super.isItemValid(stack);
                    }
                });
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
    public void buildSortingContext(ISortingContextBuilder builder) {
        builder.addSlotGroup(0, 243, 27)
            .buttonPosSetter(IPosSetter.TOP_RIGHT_VERTICAL);
    }

    @Override
    public @Nullable IPosSetter getPlayerButtonPosSetter() {
        return IPosSetter.TOP_RIGHT_VERTICAL;
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

            if (isMatchingPortableItem(itemStack, portableID)) {
                return null;
            }

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
            if (held.getItemDamage() == 6 && PortableItem.matchesPortableID(held, portableID)) {
                PortableItem.saveInventory(held, this.chestInventory);
                itemStack = held;
            } else if (held.getItemDamage() == 7 && PortableItem.matchesPortableID(held, portableID)) {
                PortableItem.saveInfinityInventory(held, this.chestInventory);
                itemStack = held;
            }
        }
        return itemstack;
    }

    public boolean isMatchingPortableItem(ItemStack itemStack, String portableID) {
        return itemStack != null && itemStack.getItem() instanceof PortableItem
            && (itemStack.getItemDamage() == 6 || itemStack.getItemDamage() == 7);
    }

}
