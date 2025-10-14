package com.science.gtnl.Utils.machine.GreenHouseManager;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.science.gtnl.api.IGreenHouse;

public interface GreenHouseBucketFactory {

    String getNBTIdentifier();

    GreenHouseBucket tryCreateBucket(IGreenHouse greenhouse, ItemStack stack);

    GreenHouseBucket restore(NBTTagCompound nbt);
}
