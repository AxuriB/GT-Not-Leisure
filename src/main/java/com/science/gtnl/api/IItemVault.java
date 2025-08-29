package com.science.gtnl.api;

import java.math.BigInteger;

import net.minecraft.item.ItemStack;

import com.science.gtnl.Utils.item.ItemTank;

public interface IItemVault {

    int pull(ItemStack aItem, boolean doPull);

    long pull(ItemStack aItem, long amount, boolean doPull);

    ItemStack push(ItemStack aItem, boolean doPush);

    ItemStack push(int amount, boolean doPush);

    long push(ItemStack aItem, long amount, boolean doPush);

    long getcapacityPerItem();

    void setCapacity(BigInteger capacity);

    int itemCount();

    int getItemPosition(String itemName);

    int getItemPosition(ItemStack aItem);

    int getNullSlot();

    boolean contains(String itemName);

    boolean contains(ItemStack aItem);

    int firstNotNullSlot();

    ItemTank firstNotNull();

    BigInteger getStoredAmount();

    int getItemSelector();

    ItemTank getSelectedItem();

    void setDoVoidExcess(boolean doVoidExcess);

    ItemTank.ItemTankInfo[] getTankInfo();

    ItemTank[] getStore();
}
