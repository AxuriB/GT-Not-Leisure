package com.science.gtnl.Utils.machine.EdenGardenManager;

import static kubatech.api.Variables.ln4;

import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import com.science.gtnl.common.machine.multiMachineClasses.MultiMachineBase;

import gregtech.api.metatileentity.implementations.MTEHatchEnergy;
import gregtech.api.metatileentity.implementations.MTEMultiBlockBase;
import gregtech.api.util.GTUtility.ItemId;

public class GTHelper {

    public static long getMaxInputEU(MTEMultiBlockBase mte) {
        if (mte instanceof MultiMachineBase) return mte.getMaxInputEu();
        long rEU = 0;
        for (MTEHatchEnergy tHatch : mte.mEnergyHatches)
            if (tHatch.isValid()) rEU += tHatch.maxEUInput() * tHatch.maxAmperesIn();
        return rEU;
    }

    public static double getVoltageTierD(long voltage) {
        return Math.log((double) voltage / 8L) / ln4;
    }

    public static double getVoltageTierD(MTEMultiBlockBase mte) {
        return Math.log((double) getMaxInputEU(mte) / 8L) / ln4;
    }

    public static int getVoltageTier(long voltage) {
        return (int) getVoltageTierD(voltage);
    }

    public static int getVoltageTier(MTEMultiBlockBase mte) {
        return (int) getVoltageTierD(mte);
    }

    public static class StackableItemSlot {

        public StackableItemSlot(int count, ItemStack stack, ArrayList<Integer> realSlots) {
            this.count = count;
            this.stack = stack;
            this.hashcode = ItemId.createNoCopyWithStackSize(stack)
                .hashCode();
            this.realSlots = realSlots;
        }

        public final int count;
        public final ItemStack stack;
        private final int hashcode;
        public final ArrayList<Integer> realSlots;

        public void write(PacketBuffer buffer) throws IOException {
            buffer.writeVarIntToBuffer(count);
            buffer.writeItemStackToBuffer(stack);
        }

        public static StackableItemSlot read(PacketBuffer buffer) throws IOException {
            return new StackableItemSlot(
                buffer.readVarIntFromBuffer(),
                buffer.readItemStackFromBuffer(),
                new ArrayList<>());
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof StackableItemSlot other)) return false;
            return count == other.count && hashcode == other.hashcode && realSlots.equals(other.realSlots);
        }
    }
}
