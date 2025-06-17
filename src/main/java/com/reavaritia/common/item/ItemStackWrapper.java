package com.reavaritia.common.item;

import java.util.Objects;

import net.minecraft.item.ItemStack;

public class ItemStackWrapper {

    public final ItemStack stack;

    public ItemStackWrapper(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public boolean equals(Object otherobj) {
        if (otherobj instanceof ItemStackWrapper other) {

            if (Objects.equals(this.stack.getItem(), other.stack.getItem())
                && this.stack.getItemDamage() == other.stack.getItemDamage()) {

                if (this.stack.stackTagCompound == null && other.stack.stackTagCompound == null) {
                    return true;
                } else {
                    if (this.stack.stackTagCompound == null ^ other.stack.stackTagCompound == null) {
                        return false;
                    } else return this.stack.stackTagCompound.equals(other.stack.stackTagCompound);
                }

            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int h = this.stack.getItem()
            .hashCode();
        if (this.stack.stackTagCompound != null) {
            h ^= this.stack.stackTagCompound.hashCode();
        }
        return h ^ this.stack.getItemDamage();
    }

    @Override
    public String toString() {
        return this.stack.toString();
    }
}
