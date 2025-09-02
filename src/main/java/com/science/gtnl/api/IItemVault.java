package com.science.gtnl.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IItemList;

public interface IItemVault {

    default int injectItems(ItemStack aItem, boolean doInput) {
        return 0;
    }

    default long injectItems(IAEItemStack aeItem, boolean doInput) {
        return 0;
    }

    default void extractItems(ItemStack aItem, boolean doOutput) {}

    default void extractItems(int amount, boolean doOutput) {}

    default long extractItems(IAEItemStack aeItem, boolean doOutput) {
        return 0;
    }

    default long itemsCount() {
        return 0;
    }

    default long maxItemCount() {
        return 0;
    }

    default IAEItemStack getStoredItem(ItemStack aItem) {
        return null;
    }

    default boolean containsItems(ItemStack aItem) {
        return false;
    }

    default IItemList<IAEItemStack> getStoreItems() {
        return null;
    }

    default int injectFluids(FluidStack aFluid, boolean doInput) {
        return 0;
    }

    default long injectFluids(IAEFluidStack aeFluid, boolean doInput) {
        return 0;
    }

    default void extractFluids(FluidStack aFluid, boolean doOutput) {}

    default void extractFluids(int amount, boolean doOutput) {}

    default long extractFluids(IAEFluidStack aeFluid, boolean doOutput) {
        return 0;
    }

    default long fluidsCount() {
        return 0;
    }

    default long maxFluidCount() {
        return 0;
    }

    default IAEFluidStack getStoredFluid(FluidStack aItem) {
        return null;
    }

    default boolean containsFluids(FluidStack aItem) {
        return false;
    }

    default IItemList<IAEFluidStack> getStoreFluids() {
        return null;
    }

    boolean isValid();
}
