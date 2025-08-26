package com.science.gtnl.Utils.gui.portableWorkbench;

import java.lang.reflect.Field;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.science.gtnl.common.item.items.PortableItem;

public class ContainerPortableAnvil extends ContainerRepair {

    public IInventory inputSlots;
    public IInventory outputSlot;

    public ContainerPortableAnvil(InventoryPlayer playerInv, EntityPlayer player) {
        super(playerInv, player.worldObj, 0, 0, 0, player);
        try {
            Field fInput = ContainerRepair.class.getDeclaredField("inputSlots");
            fInput.setAccessible(true);
            this.inputSlots = (IInventory) fInput.get(this);

            Field fOutput = ContainerRepair.class.getDeclaredField("outputSlot");
            fOutput.setAccessible(true);
            this.outputSlot = (IInventory) fOutput.get(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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
