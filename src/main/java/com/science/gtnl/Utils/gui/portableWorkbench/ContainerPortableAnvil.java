package com.science.gtnl.Utils.gui.portableWorkbench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.science.gtnl.common.item.items.PortableItem;
import com.science.gtnl.mixins.early.Minecraft.AccessorContainerRepair;

public class ContainerPortableAnvil extends ContainerRepair {

    public IInventory inputSlots;
    public IInventory outputSlot;

    public ContainerPortableAnvil(InventoryPlayer playerInv, EntityPlayer player) {
        super(playerInv, player.worldObj, 0, 0, 0, player);

        AccessorContainerRepair accessor = (AccessorContainerRepair) this;
        this.inputSlots = accessor.getInputSlots();
        this.outputSlot = accessor.getOutputSlots();

        this.inventorySlots.set(0, new Slot(inputSlots, 0, 27, 47) {

            @Override
            public boolean isItemValid(ItemStack stack) {
                return !(stack != null && stack.getItem() instanceof PortableItem && stack.getItemDamage() == 3);
            }
        });

        this.inventorySlots.set(1, new Slot(inputSlots, 1, 76, 47) {

            @Override
            public boolean isItemValid(ItemStack stack) {
                return !(stack != null && stack.getItem() instanceof PortableItem && stack.getItemDamage() == 3);
            }
        });

        this.inventorySlots.set(2, new Slot(outputSlot, 2, 134, 47) {

            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        ItemStack held = player.getHeldItem();
        return held != null && held.getItem() instanceof PortableItem && held.getItemDamage() == 3;
    }
}
