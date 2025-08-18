package com.science.gtnl.mixins.late.RandomComplement;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multiset;
import com.science.gtnl.Utils.CraftableItemMap;
import com.science.gtnl.Utils.RCCraftingGridCache;
import com.science.gtnl.Utils.SimpleItem;

import appeng.api.networking.IGrid;
import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.storage.data.IAEItemStack;
import appeng.me.cache.CraftingGridCache;

@Mixin(value = CraftingGridCache.class, remap = false)
public class MixinCraftingGridCache implements RCCraftingGridCache {

    @Mutable
    @Shadow
    @Final
    private Map<IAEItemStack, ImmutableList<ICraftingPatternDetails>> craftableItems;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void onInit(IGrid grid, CallbackInfo ci) {
        craftableItems = new CraftableItemMap();
    }

    @Unique
    @Override
    public Multiset<SimpleItem> rc$getCanCraftableItems() {
        if (craftableItems instanceof CraftableItemMap map) {
            return map.getCanCraftableItems();
        }
        return HashMultiset.create(0);
    }
}
