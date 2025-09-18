package com.science.gtnl.Utils;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;

import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.storage.data.IAEItemStack;

public class CraftableItemMap extends HashMap<IAEItemStack, ImmutableList<ICraftingPatternDetails>> {

    private final Multiset<IAEItemStack> outputs = HashMultiset.create();

    @Override
    public ImmutableList<ICraftingPatternDetails> put(IAEItemStack key, ImmutableList<ICraftingPatternDetails> value) {
        outputs.add(key);
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends IAEItemStack, ? extends ImmutableList<ICraftingPatternDetails>> map) {
        outputs.addAll(map.keySet());
        super.putAll(map);
    }

    @Override
    @SuppressWarnings("SuspiciousMethodCalls")
    public ImmutableList<ICraftingPatternDetails> remove(Object key) {
        outputs.remove(key);
        return super.remove(key);
    }

    @Override
    public void clear() {
        outputs.clear();
        super.clear();
    }

    public Multiset<IAEItemStack> getCanCraftableItems() {
        return ImmutableMultiset.copyOf(outputs);
    }
}
