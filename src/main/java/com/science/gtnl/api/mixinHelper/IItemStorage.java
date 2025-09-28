package com.science.gtnl.api.mixinHelper;

import net.minecraft.item.ItemStack;

public interface IItemStorage {

    boolean storePartial(ItemStack stack);

    boolean storePartial(ItemStack stack, boolean simulate);
}
