package com.science.gtnl.Utils.gui.portableWorkbench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;

import com.science.gtnl.common.item.items.PortableItem;

public class ContainerAdvancedWorkbench extends Container {

    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
    public IInventory craftResult = new InventoryCraftResult();
    public World worldObj;
    public EntityPlayer player;

    public ContainerAdvancedWorkbench(InventoryPlayer playerInventory, World world, ItemStack stack) {
        this.worldObj = world;
        this.player = playerInventory.player;

        IInventory savedInv = PortableItem.getAdvancedInventory(stack);
        for (int i = 0; i < craftMatrix.getSizeInventory(); i++) {
            craftMatrix.setInventorySlotContents(i, savedInv.getStackInSlot(i));
        }

        this.addSlotToContainer(
            new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, 0, 124, 35));

        int l;
        int i1;

        for (l = 0; l < 3; ++l) {
            for (i1 = 0; i1 < 3; ++i1) {
                this.addSlotToContainer(new Slot(this.craftMatrix, i1 + l * 3, 30 + i1 * 18, 17 + l * 18));
            }
        }

        for (l = 0; l < 3; ++l) {
            for (i1 = 0; i1 < 9; ++i1) {
                this.addSlotToContainer(new Slot(playerInventory, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
            }
        }

        for (l = 0; l < 9; ++l) {
            this.addSlotToContainer(new Slot(playerInventory, l, 8 + l * 18, 142));
        }

        this.onCraftMatrixChanged(this.craftMatrix);
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventory) {
        this.craftResult.setInventorySlotContents(
            0,
            CraftingManager.getInstance()
                .findMatchingRecipe(this.craftMatrix, this.worldObj));

        ItemStack held = player.getHeldItem();
        if (held != null && held.getItemDamage() == 1 && held.getItem() instanceof PortableItem) {
            PortableItem.saveAdvancedInventory(held, this.craftMatrix);
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        ItemStack held = player.getHeldItem();
        if (held != null && held.getItemDamage() == 1 && held.getItem() instanceof PortableItem) {
            PortableItem.saveAdvancedInventory(held, this.craftMatrix);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        ItemStack held = player.getHeldItem();
        return held != null && held.getItemDamage() == 1 && held.getItem() instanceof PortableItem;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 0) {
                if (!this.mergeItemStack(itemstack1, 10, 46, true)) return null;
                slot.onSlotChange(itemstack1, itemstack);
            } else if (index >= 10 && index < 37) {
                if (!this.mergeItemStack(itemstack1, 37, 46, false)) return null;
            } else if (index >= 37 && index < 46) {
                if (!this.mergeItemStack(itemstack1, 10, 37, false)) return null;
            } else if (!this.mergeItemStack(itemstack1, 10, 46, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) return null;

            slot.onPickupFromSlot(player, itemstack1);
        }

        ItemStack held = player.getHeldItem();
        if (held != null && held.getItemDamage() == 1 && held.getItem() instanceof PortableItem) {
            PortableItem.saveAdvancedInventory(held, this.craftMatrix);
        }

        return itemstack;
    }

    @Override
    public boolean func_94530_a(ItemStack stack, Slot slot) {
        return slot.inventory != this.craftResult && super.func_94530_a(stack, slot);
    }
}
