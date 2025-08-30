package com.science.gtnl.Utils.item;

import static com.google.common.primitives.Ints.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nonnull;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.github.bsideup.jabel.Desugar;
import com.google.common.primitives.UnsignedBytes;

import gregtech.api.util.GTUtility;

public class ItemTank {

    public ItemStack mItem;
    public long mCapacity, mAmount;
    public boolean mPreventDraining = false, mVoidExcess = false, mChangedItems = false;
    public Map<String, Long> mAdjustableCapacity = null;
    /** Gives you a Tank Index in case there is multiple Tanks on a TileEntity that cares. */
    public int mIndex = 0;

    public long mAdjustableMultiplier = 1;

    public ItemTank() {
        this(Long.MAX_VALUE);
    }

    public ItemTank(long capacity) {
        this.mCapacity = capacity;
        this.mAmount = 0;
        this.mItem = null;
    }

    public ItemTank(ItemStack stack, long amount, long capacity) {
        this.mItem = stack == null ? null : stack.copy();
        this.mAmount = amount;
        this.mCapacity = capacity;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tag, String key) {
        NBTTagCompound data = new NBTTagCompound();
        if (mItem != null) {
            mItem.writeToNBT(data);
            data.setLong("Amount", mAmount);
        }
        tag.setTag(key, data);
        return tag;
    }

    public ItemTank readFromNBT(NBTTagCompound tag, String key) {
        if (tag.hasKey(key)) {
            NBTTagCompound data = tag.getCompoundTag(key);
            mItem = ItemStack.loadItemStackFromNBT(data);
            mAmount = data.getLong("Amount");
        }
        return this;
    }

    public ItemStack drain(int aDrained) {
        return drain(aDrained, true);
    }

    public ItemStack drain(int aDrained, boolean aDoDrain) {
        if (isEmpty() || aDrained <= 0) return null;
        if (mAmount < aDrained) aDrained = (int) mAmount;
        final ItemStack rItem = new ItemStack(mItem.getItem(), aDrained, mItem.getItemDamage());
        if (aDoDrain) {
            mAmount -= aDrained;
            if (mAmount <= 0) {
                if (mPreventDraining) {
                    mAmount = 0;
                } else {
                    setEmpty();
                }
            }
        }
        return rItem;
    }

    public boolean drainAll(long aDrained) {
        if (isEmpty() || mAmount < aDrained) return false;
        mAmount -= aDrained;
        if (mAmount <= 0) {
            if (mPreventDraining) {
                mAmount = 0;
            } else {
                setEmpty();
            }
        }
        return true;
    }

    public long remove(long aDrained) {
        if (isEmpty() || mAmount <= 0 || aDrained <= 0) return 0;
        if (mAmount < aDrained) aDrained = mAmount;
        mAmount -= aDrained;
        if (mAmount <= 0) {
            if (mPreventDraining) {
                mAmount = 0;
            } else {
                setEmpty();
            }
        }
        return aDrained;
    }

    public long add(long aFilled) {
        if (isEmpty() || aFilled <= 0) return 0;
        final long tCapacity = capacity();
        if (mAmount + aFilled > tCapacity) {
            if (!mVoidExcess) aFilled = tCapacity - mAmount;
            mAmount = tCapacity;
            return aFilled;
        }
        mAmount += aFilled;
        return aFilled;
    }

    public long add(long aFilled, ItemStack aItem) {
        if (aItem == null || aFilled <= 0) return 0;
        if (isEmpty()) {
            mItem = aItem.copy();
            mChangedItems = true;
            mAmount = Math.min(capacity(aItem), aFilled);
            return mVoidExcess ? aFilled : mAmount;
        }
        return contains(aItem) ? add(aFilled) : 0;
    }

    public int fill(ItemStack aItem) {
        return fill(aItem, true);
    }

    public int fill(ItemStack aItem, boolean aDoFill) {
        if (aItem == null) return 0;
        if (aDoFill) {
            if (isEmpty()) {
                mItem = aItem.copy();
                mChangedItems = true;
                mAmount = Math.min(capacity(aItem), aItem.stackSize);
                return mVoidExcess ? aItem.stackSize : (int) mAmount;
            }
            if (!contains(aItem)) return 0;
            final long tCapacity = capacity(aItem);
            long tFilled = tCapacity - mAmount;
            if (aItem.stackSize < tFilled) {
                mAmount += aItem.stackSize;
                tFilled = aItem.stackSize;
            } else mAmount = tCapacity;
            return mVoidExcess ? aItem.stackSize : (int) tFilled;
        }
        return saturatedCast(
            isEmpty() ? mVoidExcess ? aItem.stackSize : Math.min(capacity(aItem), aItem.stackSize)
                : contains(aItem) ? mVoidExcess ? aItem.stackSize : Math.min(capacity(aItem) - mAmount, aItem.stackSize)
                    : 0);
    }

    public boolean canFillAll(ItemStack aItem) {
        return aItem == null || aItem.stackSize <= 0
            || (isEmpty() ? mVoidExcess || aItem.stackSize <= capacity(aItem)
                : contains(aItem) && (mVoidExcess || mAmount + aItem.stackSize <= capacity(aItem)));
    }

    public boolean canFillAll(long aAmount) {
        return aAmount <= 0 || mVoidExcess || mAmount + aAmount <= capacity();
    }

    public boolean fillAll(ItemStack aItem) {
        if (aItem == null || aItem.stackSize <= 0) return true;
        if (isEmpty()) {
            final long tCapacity = capacity(aItem);
            if (aItem.stackSize <= tCapacity || mVoidExcess) {
                mItem = aItem.copy();
                mChangedItems = true;
                mAmount = aItem.stackSize;
                if (mAmount > tCapacity) mAmount = tCapacity;
                return true;
            }
            return false;
        }
        if (contains(aItem)) {
            if (mAmount + aItem.stackSize <= capacity()) {
                mAmount += aItem.stackSize;
                return true;
            }
            if (mVoidExcess) {
                mAmount = capacity();
                return true;
            }
        }
        return false;
    }

    public boolean fillAll(ItemStack aItem, long aMultiplier) {
        if (aMultiplier <= 0) return true;
        if (aMultiplier == 1) return fillAll(aItem);
        if (aItem == null || aItem.stackSize <= 0) return true;
        if (isEmpty()) {
            final long tCapacity = capacity(aItem);
            if (aItem.stackSize * aMultiplier <= tCapacity || mVoidExcess) {
                mItem = aItem.copy();
                mChangedItems = true;
                mAmount = aItem.stackSize * aMultiplier;
                if (mAmount > tCapacity) mAmount = tCapacity;
                return true;
            }
            return false;
        }
        if (contains(aItem)) {
            if (mAmount + aItem.stackSize * aMultiplier <= capacity()) {
                mAmount += aItem.stackSize * aMultiplier;
                return true;
            }
            if (mVoidExcess) {
                mAmount = capacity();
                return true;
            }
        }
        return false;
    }

    public ItemTank setEmpty() {
        mItem = null;
        mChangedItems = true;
        mAmount = 0;
        return this;
    }

    public ItemTank setItem(ItemStack aItem) {
        mItem = aItem;
        mChangedItems = true;
        mAmount = (aItem == null ? 0 : aItem.stackSize);
        return this;
    }

    public ItemTank setItem(ItemStack aItem, long aAmount) {
        mItem = aItem;
        mChangedItems = true;
        mAmount = (aItem == null ? 0 : aAmount);
        return this;
    }

    public ItemTank setItem(ItemTank aTank) {
        ItemStack itemStack = aTank.mItem.copy();
        itemStack.stackSize = saturatedCast(aTank.mAmount);
        mItem = itemStack;
        mChangedItems = true;
        mAmount = aTank.mAmount;
        return this;
    }

    public ItemTank setIndex(int aIndex) {
        mIndex = aIndex;
        return this;
    }

    public ItemTank setCapacity(long aCapacity) {
        if (aCapacity >= 0) mCapacity = aCapacity;
        return this;
    }

    public ItemTank setCapacityMultiplier(long aCapacityMultiplier) {
        if (aCapacityMultiplier >= 0) mAdjustableMultiplier = aCapacityMultiplier;
        return this;
    }

    public ItemTank setCapacity(Map<String, Long> aMap, long aCapacityMultiplier) {
        mAdjustableCapacity = aMap;
        mAdjustableMultiplier = aCapacityMultiplier;
        return this;
    }

    public ItemTank setPreventDraining() {
        return setPreventDraining(true);
    }

    public ItemTank setPreventDraining(boolean aPrevent) {
        mPreventDraining = aPrevent;
        return this;
    }

    public ItemTank setVoidExcess() {
        return setVoidExcess(true);
    }

    public ItemTank setVoidExcess(boolean aVoidExcess) {
        mVoidExcess = aVoidExcess;
        return this;
    }

    public boolean isFull() {
        return mItem != null && mAmount >= capacity();
    }

    public long capacity() {
        return capacity(mItem);
    }

    public long capacity(ItemStack aItem) {
        if (mAdjustableCapacity == null || aItem == null) return mCapacity;
        return capacity(aItem.getItem(), aItem.getItemDamage());
    }

    public long capacity(Item aItem, int meta) {
        if (mAdjustableCapacity == null || aItem == null) return mCapacity;
        return capacity(aItem.getUnlocalizedName() + ":" + meta);
    }

    public long capacity(String aItem) {
        if (mAdjustableCapacity == null || aItem == null) return mCapacity;

        Long tSize = mAdjustableCapacity.get(aItem);
        return tSize == null ? Math.max(mAmount, mCapacity)
            : Math.max(tSize * mAdjustableMultiplier, Math.max(mAmount, mCapacity));
    }

    public boolean isHalf() {
        return mItem != null && mAmount * 2 >= capacity();
    }

    public boolean contains(Item aItem) {
        return mItem != null && mItem.getItem() == aItem;
    }

    public boolean contains(ItemStack aItem) {
        return GTUtility.areStacksEqual(mItem, aItem);
    }

    public boolean has(long aAmount) {
        return mAmount >= aAmount;
    }

    public boolean has() {
        return mAmount > 0;
    }

    public boolean check() {
        if (mChangedItems) {
            mChangedItems = false;
            return true;
        }
        return false;
    }

    public boolean update() {
        return mChangedItems = true;
    }

    public boolean changed() {
        return mChangedItems;
    }

    public long amount() {
        return isEmpty() ? 0 : mAmount;
    }

    public boolean isEmpty() {
        return mItem == null;
    }

    public long amount(long aMax) {
        return isEmpty() || aMax <= 0 ? 0 : Math.min(mAmount, aMax);
    }

    public String name() {
        return mItem == null ? null
            : Objects.requireNonNull(mItem.getItem())
                .getItemStackDisplayName(mItem);
    }

    public ItemStack get() {
        return mItem;
    }

    public ItemStack get(long aMax) {
        if (isEmpty() || aMax <= 0) {
            return null;
        } else {
            ItemStack itemStack = mItem.copy();
            itemStack.stackSize = saturatedCast(Math.min(mAmount, aMax));
            return itemStack;
        }
    }

    public ItemStack getItem() {
        if (mItem != null) mItem.stackSize = saturatedCast(mAmount);
        return mItem;
    }

    public int getItemAmount() {
        return saturatedCast(mAmount);
    }

    public int getCapacity() {
        return saturatedCast(capacity());
    }

    public long getCapacityMultiplier() {
        return mAdjustableMultiplier;
    }

    public ItemTankInfo getInfo() {
        return new ItemTankInfo(isEmpty() ? null : mItem.copy(), UnsignedBytes.saturatedCast(capacity()));
    }

    public static ItemStack[] getItemsFromTanks(ItemTank[] tanks) {
        if (tanks == null) {
            return null;
        }
        List<ItemStack> itemStacks = new ArrayList<>();
        for (ItemTank tank : tanks) {
            if (tank.getItem() != null) {
                itemStacks.add(tank.getItem());
            }
        }
        return itemStacks.toArray(new ItemStack[0]);
    }

    public boolean isEmptyAndAcceptsAnyItem() {
        return getItemAmount() == 0;
    }

    public boolean canStoreItem(@Nonnull ItemStack itemStack) {
        return GTUtility.areStacksEqual(mItem, itemStack);
    }

    @Desugar
    public record ItemTankInfo(ItemStack item, int capacity) {}

}
