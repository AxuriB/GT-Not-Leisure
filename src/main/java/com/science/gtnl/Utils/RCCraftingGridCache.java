package com.science.gtnl.Utils;

import com.google.common.collect.Multiset;

import appeng.api.storage.data.IAEItemStack;

public interface RCCraftingGridCache {

    Multiset<IAEItemStack> rc$getCanCraftableItems();
}
