package com.science.gtnl.api;

import java.math.BigInteger;

import net.minecraft.item.ItemStack;

import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IItemList;

public interface IItemVault {

    int inputStorage(ItemStack aItem, boolean doInput);

    long inputStorage(IAEItemStack aeItem, boolean doInput);

    void outputStroage(ItemStack aItem, boolean doOutput);

    void outputStroage(int amount, boolean doOutput);

    long outputStroage(IAEItemStack aeItem, boolean doOutput);

    long getcapacityPerItem();

    void setCapacity(BigInteger capacity);

    int itemCount();

    IAEItemStack getStoredItem(String itemName);

    IAEItemStack getStoredItem(ItemStack aItem);

    boolean contains(String itemName);

    boolean contains(ItemStack aItem);

    BigInteger getStoredAmount();

    void setDoVoidExcess(boolean doVoidExcess);

    IItemList<IAEItemStack> getStore();
}
