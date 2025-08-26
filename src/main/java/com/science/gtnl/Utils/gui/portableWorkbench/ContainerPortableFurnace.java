package com.science.gtnl.Utils.gui.portableWorkbench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;

import com.science.gtnl.common.item.items.PortableItem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerPortableFurnace extends Container {

    public IInventory furnaceInventory;
    public EntityPlayer player;
    public ItemStack furnaceStack;

    public int cookTime;
    public int burnTime;
    public int currentItemBurnTime;

    public ContainerPortableFurnace(InventoryPlayer playerInventory, World world, ItemStack stack) {
        this.furnaceStack = stack;
        this.player = playerInventory.player;

        this.furnaceInventory = new InventoryBasic("PortableFurnace", false, 3);
        IInventory saved = PortableItem.getFurnaceInventory(stack);
        for (int i = 0; i < 3; i++) {
            furnaceInventory.setInventorySlotContents(i, saved.getStackInSlot(i));
        }

        Slot inputSlot = new Slot(furnaceInventory, 0, 56, 17) {

            @Override
            public boolean isItemValid(ItemStack stack) {
                if (stack != null && stack.getItem() instanceof PortableItem && stack.getItemDamage() == 2) {
                    return false;
                }
                return super.isItemValid(stack);
            }
        };
        Slot fuelSlot = new Slot(furnaceInventory, 1, 56, 53) {

            @Override
            public boolean isItemValid(ItemStack stack) {
                if (stack != null && stack.getItem() instanceof PortableItem && stack.getItemDamage() == 2) {
                    return false;
                }
                return super.isItemValid(stack);
            }
        };
        Slot outputSlot = new SlotFurnace(playerInventory.player, furnaceInventory, 2, 116, 35);

        this.addSlotToContainer(inputSlot);
        this.addSlotToContainer(fuelSlot);
        this.addSlotToContainer(outputSlot);

        for (int l = 0; l < 3; ++l) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.addSlotToContainer(new Slot(playerInventory, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        ItemStack held = player.getHeldItem();
        return held != null && held.getItemDamage() == 2 && held.getItem() instanceof PortableItem;
    }

    @Override
    public ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer player) {
        ItemStack result = super.slotClick(slotId, clickedButton, mode, player);
        ItemStack held = player.getHeldItem();
        if (held != null && held.getItemDamage() == 2 && held.getItem() instanceof PortableItem) {
            PortableItem.saveFurnaceInventory(held, furnaceInventory);
            furnaceInventory = PortableItem.getFurnaceInventory(held);
            furnaceStack = held;
        }

        return result;
    }

    @Override
    public void addCraftingToCrafters(ICrafting iCrafting) {
        super.addCraftingToCrafters(iCrafting);
        if (furnaceStack == null) return;

        NBTTagCompound tag = furnaceStack.getTagCompound();
        if (tag == null) return;

        int cookTimeNBT = tag.getInteger("CookTime");
        int burnTimeNBT = tag.getInteger("BurnTime");
        int currentItemBurnTimeNBT = tag.getInteger("CurrentItemBurnTime");

        iCrafting.sendProgressBarUpdate(this, 0, cookTimeNBT);
        iCrafting.sendProgressBarUpdate(this, 1, burnTimeNBT);
        iCrafting.sendProgressBarUpdate(this, 2, currentItemBurnTimeNBT);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        if (furnaceStack == null) return;

        NBTTagCompound tag = furnaceStack.getTagCompound();
        if (tag == null) return;

        int cookTimeNBT = tag.getInteger("CookTime");
        int burnTimeNBT = tag.getInteger("BurnTime");
        int currentItemBurnTimeNBT = tag.getInteger("CurrentItemBurnTime");

        for (ICrafting crafter : this.crafters) {
            if (this.cookTime != cookTimeNBT) crafter.sendProgressBarUpdate(this, 0, cookTimeNBT);
            if (this.burnTime != burnTimeNBT) crafter.sendProgressBarUpdate(this, 1, burnTimeNBT);
            if (this.currentItemBurnTime != currentItemBurnTimeNBT)
                crafter.sendProgressBarUpdate(this, 2, currentItemBurnTimeNBT);
        }

        this.cookTime = cookTimeNBT;
        this.burnTime = burnTimeNBT;
        this.currentItemBurnTime = currentItemBurnTimeNBT;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int value) {
        if (furnaceStack == null) return;

        if (!furnaceStack.hasTagCompound()) furnaceStack.setTagCompound(new NBTTagCompound());
        NBTTagCompound tag = furnaceStack.getTagCompound();

        switch (id) {
            case 0:
                tag.setInteger("CookTime", value);
                break;
            case 1:
                tag.setInteger("BurnTime", value);
                break;
            case 2:
                tag.setInteger("CurrentItemBurnTime", value);
                break;
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        ItemStack held = player.getHeldItem();
        if (held != null && held.getItemDamage() == 2 && held.getItem() instanceof PortableItem) {
            PortableItem.saveFurnaceInventory(held, furnaceInventory);
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            itemstack = stackInSlot.copy();

            if (index == 2) {
                if (!this.mergeItemStack(stackInSlot, 3, 39, true)) return null;
                slot.onSlotChange(stackInSlot, itemstack);
            } else if (index != 1 && index != 0) {
                if (FurnaceRecipes.smelting()
                    .getSmeltingResult(stackInSlot) != null) {
                    if (!this.mergeItemStack(stackInSlot, 0, 1, false)) return null;
                } else if (TileEntityFurnace.isItemFuel(stackInSlot)) {
                    if (!this.mergeItemStack(stackInSlot, 1, 2, false)) return null;
                } else if (index < 30) {
                    if (!this.mergeItemStack(stackInSlot, 30, 39, false)) return null;
                } else if (index < 39) {
                    if (!this.mergeItemStack(stackInSlot, 3, 30, false)) return null;
                }
            } else {
                if (!this.mergeItemStack(stackInSlot, 3, 39, false)) return null;
            }

            if (stackInSlot.stackSize == 0) slot.putStack(null);
            else slot.onSlotChanged();

            if (stackInSlot.stackSize == itemstack.stackSize) return null;
            slot.onPickupFromSlot(player, stackInSlot);
        }

        ItemStack held = player.getHeldItem();
        if (held != null && held.getItemDamage() == 2 && held.getItem() instanceof PortableItem) {
            PortableItem.saveFurnaceInventory(held, furnaceInventory);
        }

        return itemstack;
    }
}
