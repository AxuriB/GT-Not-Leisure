package com.science.gtnl.Utils.rewrites;

import java.util.Objects;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import org.jetbrains.annotations.Nullable;

import gregtech.api.util.GTUtility;

public class GTNL_ItemID extends GTUtility.ItemId {

    // region Member Variables
    private Item item;
    private int metaData;
    private NBTTagCompound nbt;
    // endregion

    // region Class Constructors
    public GTNL_ItemID(Item item, int metaData, NBTTagCompound nbt) {
        this.item = item;
        this.metaData = metaData;
        this.nbt = nbt;
    }

    public GTNL_ItemID(Item item, int metaData) {
        this.item = item;
        this.metaData = metaData;
    }

    public GTNL_ItemID(Item item) {
        this.item = item;
        this.metaData = 0;
    }

    public GTNL_ItemID() {}
    // endregion

    // region Static Methods
    public static final GTNL_ItemID NULL = new GTNL_ItemID();

    public static GTNL_ItemID create(ItemStack itemStack) {
        if (null == itemStack) return NULL;
        return new GTNL_ItemID(itemStack.getItem(), itemStack.getItemDamage(), itemStack.getTagCompound());
    }

    public static GTNL_ItemID createNoNBT(ItemStack itemStack) {
        if (null == itemStack) return NULL;
        return new GTNL_ItemID(itemStack.getItem(), itemStack.getItemDamage());
    }

    public static GTNL_ItemID createAsWildcard(ItemStack itemStack) {
        if (null == itemStack) return NULL;
        return new GTNL_ItemID(itemStack.getItem(), OreDictionary.WILDCARD_VALUE);
    }
    // endregion

    // region Special Methods
    public ItemStack getItemStack() {
        return new ItemStack(item, 1, metaData);
    }

    public ItemStack getItemStack(int amount) {
        return new ItemStack(item, amount, metaData);
    }

    public ItemStack getItemStackWithNBT() {
        ItemStack itemStack = new ItemStack(item, 1, metaData);
        itemStack.setTagCompound(nbt);
        return itemStack;
    }

    public ItemStack getItemStackWithNBT(int amount) {
        ItemStack itemStack = new ItemStack(item, amount, metaData);
        itemStack.setTagCompound(nbt);
        return itemStack;
    }

    // endregion

    // region General Methods
    public boolean isWildcard() {
        return this.metaData == OreDictionary.WILDCARD_VALUE;
    }

    public GTNL_ItemID setItem(Item item) {
        this.item = item;
        return this;
    }

    public GTNL_ItemID setMetaData(int metaData) {
        this.metaData = metaData;
        return this;
    }

    public GTNL_ItemID setNbt(NBTTagCompound nbt) {
        this.nbt = nbt;
        return this;
    }

    @Override
    protected Item item() {
        return item;
    }

    @Override
    protected int metaData() {
        return metaData;
    }

    @Nullable
    @Override
    protected NBTTagCompound nbt() {
        return nbt;
    }

    @Nullable
    @Override
    protected Integer stackSize() {
        // todo
        return null;
    }

    @Nullable
    protected NBTTagCompound getNBT() {
        return nbt;
    }

    protected Item getItem() {
        return item;
    }

    protected int getMetaData() {
        return metaData;
    }

    public boolean equalItemStack(ItemStack itemStack) {
        return this.equals(isWildcard() ? createAsWildcard(itemStack) : createNoNBT(itemStack));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GTNL_ItemID)) {
            return false;
        }
        GTNL_ItemID tstItemID = (GTNL_ItemID) o;
        return metaData == tstItemID.metaData && Objects.equals(item, tstItemID.item)
            && Objects.equals(nbt, tstItemID.nbt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, metaData, nbt);
    }
}
