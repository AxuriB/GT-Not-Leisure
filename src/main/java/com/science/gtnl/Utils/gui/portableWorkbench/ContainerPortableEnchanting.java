package com.science.gtnl.Utils.gui.portableWorkbench;

import java.util.Random;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.science.gtnl.common.item.items.PortableItem;

public class ContainerPortableEnchanting extends ContainerEnchantment {

    public Random rand = new Random();
    public World worldPointer;

    public ContainerPortableEnchanting(InventoryPlayer playerInv, World world) {
        super(playerInv, world, 0, 0, 0);
        this.worldPointer = world;
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventory) {
        if (inventory == this.tableInventory) {
            ItemStack itemstack = inventory.getStackInSlot(0);

            if (itemstack != null && itemstack.isItemEnchantable()) {
                this.nameSeed = this.rand.nextLong();

                if (!this.worldPointer.isRemote) {
                    int power = 15;

                    for (int j = 0; j < 3; ++j) {
                        this.enchantLevels[j] = EnchantmentHelper
                            .calcItemStackEnchantability(this.rand, j, power, itemstack);
                    }

                    this.detectAndSendChanges();
                }
            } else {
                for (int i = 0; i < 3; ++i) {
                    this.enchantLevels[i] = 0;
                }
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        ItemStack held = player.getHeldItem();
        return held != null && held.getItemDamage() == 5 && held.getItem() instanceof PortableItem;
    }
}
