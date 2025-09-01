package com.science.gtnl.api;

import java.math.BigInteger;

import net.minecraft.item.ItemStack;

import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IItemList;

public interface IItemVault {

    int inject(ItemStack aItem, boolean doInput);

    long inject(IAEItemStack aeItem, boolean doInput);

    void extract(ItemStack aItem, boolean doOutput);

    void extract(int amount, boolean doOutput);

    long extract(IAEItemStack aeItem, boolean doOutput);

    long getcapacityPerItem();

    void setCapacity(BigInteger capacity);

    int itemCount();

    long maxItemCount();

    IAEItemStack getStoredItem(ItemStack aItem);

    boolean contains(ItemStack aItem);

    boolean isValid();

    BigInteger getStoredAmount();

    void setDoVoidExcess(boolean doVoidExcess);

    IItemList<IAEItemStack> getStore();
}
