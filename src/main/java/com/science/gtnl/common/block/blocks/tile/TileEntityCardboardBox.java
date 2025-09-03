package com.science.gtnl.common.block.blocks.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.science.gtnl.Utils.CardboardBoxUtils;

public class TileEntityCardboardBox extends TileEntity {

    public CardboardBoxUtils.BlockData storedData;

    @Override
    public void readFromNBT(NBTTagCompound nbtTags) {
        super.readFromNBT(nbtTags);

        if (nbtTags.hasKey("storedData")) {
            storedData = CardboardBoxUtils.BlockData.read(nbtTags.getCompoundTag("storedData"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTags) {
        super.writeToNBT(nbtTags);

        if (storedData != null) {
            nbtTags.setTag("storedData", storedData.write(new NBTTagCompound()));
        }
    }

    @Override
    public boolean canUpdate() {
        return false;
    }
}
