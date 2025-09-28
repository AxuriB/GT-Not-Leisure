package com.science.gtnl.Utils.machine.EdenGardenManager;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.science.gtnl.common.machine.multiblock.EdenGarden;

public interface IEIGBucketFactory {

    String getNBTIdentifier();

    EIGBucket tryCreateBucket(EdenGarden greenhouse, ItemStack stack);

    EIGBucket restore(NBTTagCompound nbt);
}
