package com.science.gtnl.api;

import java.math.BigInteger;

import net.minecraft.item.ItemStack;

import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IItemList;

public interface IItemVault {

    int pull(ItemStack aItem, boolean doPull);

    long pull(IAEItemStack aeItem, boolean doPull);

    void push(ItemStack aItem, boolean doPush);

    void push(int amount, boolean doPush);

    long push(IAEItemStack aeItem, boolean doPush);

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
