package com.science.gtnl.mixins.late.Gregtech;

import static gregtech.api.util.GTUtility.*;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import com.science.gtnl.api.mixinHelper.IItemStorage;

import gregtech.api.interfaces.metatileentity.IItemLockable;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.metatileentity.implementations.MTEHatchOutput;
import gregtech.api.util.GTUtility;
import gregtech.common.tileentities.machines.MTEHatchOutputBusME;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.MTEHatchSteamBusOutput;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.GTPPMultiBlockBase;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.MTESteamMultiBase;

@Mixin(value = MTESteamMultiBase.class, remap = false)
public abstract class MixinMTESteamMultiBase<T extends MTESteamMultiBase<T>> extends GTPPMultiBlockBase<T> {

    @Shadow
    public ArrayList<MTEHatchSteamBusOutput> mSteamOutputs;

    public MixinMTESteamMultiBase(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    @Override
    public List<ItemStack> getItemOutputSlots(ItemStack[] toOutput) {
        List<ItemStack> ret = new ArrayList<>();
        if (mSteamOutputs != null && !mSteamOutputs.isEmpty()) {
            for (final MTEHatch tBus : validMTEList(mSteamOutputs)) {
                if (!(tBus instanceof MTEHatchOutputBusME)) {
                    final IInventory tBusInv = tBus.getBaseMetaTileEntity();
                    for (int i = 0; i < tBusInv.getSizeInventory(); i++) {
                        final ItemStack stackInSlot = tBus.getStackInSlot(i);
                        if (stackInSlot == null && tBus instanceof IItemLockable lockable && lockable.isLocked()) {
                            assert lockable.getLockedItem() != null;
                            ItemStack fakeItemStack = lockable.getLockedItem()
                                .copy();
                            fakeItemStack.stackSize = 0;
                            ret.add(fakeItemStack);
                        } else {
                            ret.add(stackInSlot);
                        }
                    }
                }
            }
        }
        return ret;
    }

    @Override
    public boolean addOutput(ItemStack aStack) {
        if (GTUtility.isStackInvalid(aStack)) return false;
        aStack = GTUtility.copy(aStack);
        boolean outputSuccess = true;
        List<MTEHatchSteamBusOutput> filteredSteamBusses = filterValidMTEs(mSteamOutputs);
        if (dumpItemSteam(filteredSteamBusses, aStack, true)) return true;
        if (dumpItemSteam(filteredSteamBusses, aStack, false)) return true;

        while (outputSuccess && aStack.stackSize > 0) {
            outputSuccess = false;
            ItemStack single = aStack.splitStack(1);
            for (MTEHatchSteamBusOutput tHatch : filterValidMTEs(mSteamOutputs)) {
                if (!outputSuccess) {
                    if (tHatch.isLocked() && !GTUtility.areStacksEqual(tHatch.getLockedItem(), single)) {
                        continue;
                    }

                    for (int i = tHatch.getSizeInventory() - 1; i >= 0 && !outputSuccess; i--) {
                        if (tHatch.getBaseMetaTileEntity()
                            .addStackToSlot(i, single)) {
                            aStack.stackSize--;
                            outputSuccess = true;
                        }
                    }
                }
            }
            for (MTEHatchOutput tHatch : filterValidMTEs(mOutputHatches)) {
                if (!outputSuccess && tHatch.outputsItems()) {
                    if (tHatch.getBaseMetaTileEntity()
                        .addStackToSlot(1, single)) outputSuccess = true;
                }
            }
        }
        return outputSuccess;
    }

    @Unique
    public boolean dumpItemSteam(List<MTEHatchSteamBusOutput> outputBuses, ItemStack itemStack,
        boolean restrictiveBusesOnly) {
        return dumpItemSteam(outputBuses, itemStack, restrictiveBusesOnly, false);
    }

    @Unique
    public boolean dumpItemSteam(List<MTEHatchSteamBusOutput> outputBuses, ItemStack itemStack,
        boolean restrictiveBusesOnly, boolean simulate) {
        for (MTEHatchSteamBusOutput outputBus : outputBuses) {
            if (restrictiveBusesOnly && !outputBus.isLocked()) {
                continue;
            }
            if (outputBus instanceof IItemStorage iItemStorage) {
                if (iItemStorage.storePartial(itemStack, simulate)) {
                    return true;
                }
            }
        }

        return false;
    }
}
