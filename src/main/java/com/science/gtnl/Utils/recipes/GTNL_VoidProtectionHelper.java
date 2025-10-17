package com.science.gtnl.Utils.recipes;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Function;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.gtnewhorizon.gtnhlib.util.map.ItemStackMap;
import com.science.gtnl.api.IInfinitySlot;

import gregtech.api.enums.GTValues;
import gregtech.api.interfaces.fluid.IFluidStore;
import gregtech.api.interfaces.tileentity.IVoidable;
import gregtech.api.metatileentity.implementations.MTEHatchOutputBus;
import gregtech.api.metatileentity.implementations.MTEMultiBlockBase;
import gregtech.api.util.OutputHatchWrapper;
import gregtech.api.util.VoidProtectionHelper;
import gregtech.common.tileentities.machines.MTEHatchOutputME;

/**
 * Helper class to calculate how many parallels of items / fluids can fit in the output buses / hatches.
 */
public class GTNL_VoidProtectionHelper extends VoidProtectionHelper {

    /**
     * Machine used for calculation
     */
    public IVoidable machine;
    /**
     * Does void protection enabled for items
     */
    public boolean protectExcessItem;
    /**
     * Does void protection enabled for fluids
     */
    public boolean protectExcessFluid;
    /**
     * The maximum possible parallel possible for the multiblock
     */
    public int maxParallel = 1;
    /**
     * If item output is full.
     */
    public boolean isItemFull;
    /**
     * If fluid output is full.
     */
    public boolean isFluidFull;
    /**
     * The item outputs to check
     */
    public ItemStack[] itemOutputs;
    /**
     * The fluid outputs to check
     */
    public FluidStack[] fluidOutputs;
    /**
     * Has this helper been built?
     */
    public boolean built;
    /**
     * Multiplier that is applied on the output chances
     */
    public double chanceMultiplier = 1;

    public Function<Integer, Integer> chanceGetter = i -> 10000;

    public GTNL_VoidProtectionHelper() {}

    /**
     * Sets machine, with current configuration for void protection mode.
     */
    @Override
    public GTNL_VoidProtectionHelper setMachine(IVoidable machine) {
        return setMachine(machine, machine.protectsExcessItem(), machine.protectsExcessFluid());
    }

    /**
     * Sets machine, with void protection mode forcibly.
     */
    @Override
    public GTNL_VoidProtectionHelper setMachine(IVoidable machine, boolean protectExcessItem,
        boolean protectExcessFluid) {
        this.protectExcessItem = protectExcessItem;
        this.protectExcessFluid = protectExcessFluid;
        this.machine = machine;
        return this;
    }

    @Override
    public GTNL_VoidProtectionHelper setItemOutputs(ItemStack[] itemOutputs) {
        this.itemOutputs = itemOutputs;
        return this;
    }

    @Override
    public GTNL_VoidProtectionHelper setFluidOutputs(FluidStack[] fluidOutputs) {
        this.fluidOutputs = fluidOutputs;
        return this;
    }

    /**
     * Sets the MaxParallel a multi can handle
     */
    @Override
    public GTNL_VoidProtectionHelper setMaxParallel(int maxParallel) {
        this.maxParallel = maxParallel;
        return this;
    }

    @Override
    public GTNL_VoidProtectionHelper setChanceMultiplier(double chanceMultiplier) {
        this.chanceMultiplier = chanceMultiplier;
        return this;
    }

    @Override
    public GTNL_VoidProtectionHelper setChangeGetter(Function<Integer, Integer> getter) {
        this.chanceGetter = getter;
        return this;
    }

    /**
     * Finishes the VoidProtectionHelper. Anything changed after this will not affect anything
     */
    @Override
    public GTNL_VoidProtectionHelper build() {
        if (built) {
            throw new IllegalStateException("Tried to build twice");
        }
        if (machine == null) {
            throw new IllegalStateException("Machine is not set");
        }
        built = true;
        determineParallel();
        return this;
    }

    /**
     * @return The current parallels possible by the multiblock
     */
    @Override
    public int getMaxParallel() {
        if (!built) {
            throw new IllegalStateException("Tried to get parallels before building");
        }
        return maxParallel;
    }

    /**
     * @return If the calculation resulted in item output being full.
     */
    @Override
    public boolean isItemFull() {
        if (!built) {
            throw new IllegalStateException("Tried to get isItemFull before building");
        }
        return isItemFull;
    }

    /**
     * @return If the calculation resulted in fluid output being full.
     */
    @Override
    public boolean isFluidFull() {
        if (!built) {
            throw new IllegalStateException("Tried to get isFluidFull before building");
        }
        return isFluidFull;
    }

    /**
     * Called by {@link #build()}. Determines the parallels and everything else that needs to be done at build time
     */
    public void determineParallel() {
        if (itemOutputs == null) {
            itemOutputs = GTValues.emptyItemStackArray;
        }
        if (fluidOutputs == null) {
            fluidOutputs = GTValues.emptyFluidStackArray;
        }

        // Don't check IVoidable#protectsExcessItem nor #protectsExcessFluid here,
        // to allow more involved setting for void protections (see ComplexParallelProcessingLogic)
        if (protectExcessItem && itemOutputs.length > 0 && !machine.canDumpItemToME()) {
            maxParallel = Math.min(calculateMaxItemParallels(), maxParallel);
            if (maxParallel <= 0) {
                isItemFull = true;
                return;
            }
        }
        if (protectExcessFluid && fluidOutputs.length > 0 && !machine.canDumpFluidToME()) {
            maxParallel = Math.min(calculateMaxFluidParallels(), maxParallel);
            if (maxParallel <= 0) {
                isFluidFull = true;
            }
        }
    }

    /**
     * Calculates the max parallel for fluids if void protection is turned on
     */
    public int calculateMaxFluidParallels() {
        List<? extends IFluidStore> hatches = machine.getFluidOutputSlots(fluidOutputs);
        if (hatches.size() < fluidOutputs.length) {
            return 0;
        }

        // A map to hold the items we will be 'inputting' into the output hatches. These fluidstacks are actually
        // the recipe outputs.
        Map<FluidStack, Integer> tFluidOutputMap = new HashMap<>();

        // Map that keeps track of the number of parallel crafts we can accommodate for each fluid output.
        // In the pair, we keep track of number of full crafts plus mb of fluid in a partial craft, to avoid
        // issues with floating point math not being completely accurate when summing.
        Map<FluidStack, ParallelData> tParallels = new HashMap<>();

        // Iterate over the outputs, calculating require stack spacing they will require.
        for (FluidStack aY : fluidOutputs) {
            if (aY == null || aY.amount <= 0) {
                continue;
            }
            tFluidOutputMap.merge(aY, aY.amount, Integer::sum);
            tParallels.put(aY, new ParallelData(0, 0));
        }

        if (tFluidOutputMap.isEmpty()) {
            // nothing to output, bail early
            return maxParallel;
        }

        for (IFluidStore tHatch : hatches) {
            int tSpaceLeft;
            if (tHatch instanceof MTEHatchOutputME tMEHatch) {
                tSpaceLeft = tMEHatch.canAcceptFluid() ? Integer.MAX_VALUE : 0;
            } else if (tHatch instanceof OutputHatchWrapper w && w.unwrap() instanceof MTEHatchOutputME tMEHatch) {
                tSpaceLeft = tMEHatch.canAcceptFluid() ? Integer.MAX_VALUE : 0;
            } else {
                tSpaceLeft = tHatch.getCapacity() - tHatch.getFluidAmount();
            }

            // check if hatch filled
            if (tSpaceLeft <= 0) continue;

            // check if hatch is empty and unrestricted
            if (tHatch.isEmptyAndAcceptsAnyFluid()) continue;

            for (Map.Entry<FluidStack, ParallelData> entry : tParallels.entrySet()) {
                FluidStack tFluidOutput = entry.getKey();
                if (!tHatch.canStoreFluid(tFluidOutput)) continue;
                // this fluid is not prevented by restrictions on output hatch
                ParallelData tParallel = entry.getValue();
                Integer tCraftSize = tFluidOutputMap.get(tFluidOutput);
                tParallel.batch += (tParallel.partial + tSpaceLeft) / tCraftSize;
                tParallel.partial = (tParallel.partial + tSpaceLeft) % tCraftSize;
            }
        }
        // now that all partial/restricted hatches have been counted, create a priority queue for our outputs
        // the lowest priority fluid is the number of complete parallel crafts we can support
        PriorityQueue<ParallelStackInfo<FluidStack>> aParallelQueue = new PriorityQueue<>(
            Comparator.comparing(i -> i.batch));
        for (Map.Entry<FluidStack, ParallelData> entry : tParallels.entrySet()) {
            aParallelQueue
                .add(new ParallelStackInfo<>(entry.getValue().batch, entry.getValue().partial, entry.getKey()));
        }
        // add extra parallels for open slots as well
        for (IFluidStore tHatch : hatches) {
            // partially filled or restricted hatch. done in the last pass
            if (!tHatch.isEmptyAndAcceptsAnyFluid()) continue;

            ParallelStackInfo<FluidStack> tParallel = aParallelQueue.poll();
            assert tParallel != null; // will always be true, specifying assert here to avoid IDE/compiler warnings
            Integer tCraftSize = tFluidOutputMap.get(tParallel.stack);

            int tSpaceLeft;
            if (tHatch instanceof MTEHatchOutputME tMEHatch) {
                tSpaceLeft = tMEHatch.canAcceptFluid() ? Integer.MAX_VALUE : 0;
            } else if (tHatch instanceof OutputHatchWrapper w && w.unwrap() instanceof MTEHatchOutputME tMEHatch) {
                tSpaceLeft = tMEHatch.canAcceptFluid() ? Integer.MAX_VALUE : 0;
            } else {
                tSpaceLeft = tHatch.getCapacity();
            }

            tParallel.batch += (tParallel.partial + tSpaceLeft) / tCraftSize;
            tParallel.partial = (tParallel.partial + tSpaceLeft) % tCraftSize;
            aParallelQueue.add(tParallel);
        }
        return (int) Math.min(aParallelQueue.element().batch, Integer.MAX_VALUE);
    }

    /**
     * Calculates the max parallels one can do with items if void protection is on
     */
    public int calculateMaxItemParallels() {
        List<ItemStack> busStacks = machine.getItemOutputSlots(itemOutputs);
        List<ItemStack> voidStacks = machine.getVoidOutputSlots();
        // A map to hold the items we will be 'inputting' into the output buses. These itemstacks are actually the
        // recipe outputs.
        Map<ItemStack, Integer> tItemOutputMap = new ItemStackMap<>();

        // Map that keeps track of the number of parallel crafts we can accommodate for each item output.
        // In the pair, we keep track of number of full crafts plus number of items in a partial craft, to avoid
        // issues with floating point math not being completely accurate when summing.
        Map<ItemStack, ParallelData> tParallels = new ItemStackMap<>();
        int tSlotsFree = 0;
        int index = 0;
        for (ItemStack tItem : itemOutputs) {
            // GTRecipeBuilder doesn't handle null item output
            if (tItem == null) continue;
            int itemStackSize = (int) (tItem.stackSize
                * Math.ceil(chanceMultiplier * chanceGetter.apply(index++) / 10000));
            if (itemStackSize <= 0) continue;
            tItemOutputMap.merge(tItem, itemStackSize, Integer::sum);
            tParallels.put(tItem, new ParallelData(0, 0));
        }

        if (tItemOutputMap.isEmpty()) {
            // nothing to output, bail early
            return maxParallel;
        }

        if (itemOutputs.length > 0) {
            for (ItemStack tBusStack : busStacks) {
                if (tBusStack == null) {
                    tSlotsFree++;
                } else if (tBusStack.stackSize == 65) {
                    for (Map.Entry<ItemStack, ParallelData> entry : tParallels.entrySet()) {
                        ItemStack tItemOutput = entry.getKey();
                        if (!tBusStack.isItemEqual(tItemOutput)) continue;
                        // this fluid is not prevented by restrictions on output hatch
                        ParallelData tParallel = entry.getValue();
                        Integer tCraftSize = tItemOutputMap.get(tBusStack);
                        tParallel.batch += (tParallel.partial + Integer.MAX_VALUE) / tCraftSize;
                        tParallel.partial = (tParallel.partial + Integer.MAX_VALUE) % tCraftSize;
                    }
                } else {
                    // get the real stack size
                    // we ignore the bus inventory stack limit here as no one set it to anything other than 64
                    int tMaxBusStackSize = tBusStack.getMaxStackSize();
                    if (machine instanceof MTEMultiBlockBase multiBlockBase) {
                        for (MTEHatchOutputBus outputBus : multiBlockBase.mOutputBusses) {
                            if (outputBus instanceof IInfinitySlot) {
                                tMaxBusStackSize = Integer.MAX_VALUE;
                                break;
                            }
                        }
                    }
                    if (tBusStack.stackSize >= tMaxBusStackSize)
                        // this bus stack is full. no checking
                        continue;
                    int tSpaceLeft = tMaxBusStackSize - tBusStack.stackSize;
                    Integer tCraftSize = tItemOutputMap.get(tBusStack);
                    if (tCraftSize == null) {
                        // we don't have a matching stack to output, ignore this bus stack
                        continue;
                    }
                    ParallelData tParallel = tParallels.get(tBusStack);
                    tParallel.batch += (tParallel.partial + tSpaceLeft) / tCraftSize;
                    tParallel.partial = (tParallel.partial + tSpaceLeft) % tCraftSize;
                }

            }
            // now that all partial stacks have been counted, create a priority queue for our outputs
            // the lowest priority item is the number of complete parallel crafts we can support
            PriorityQueue<ParallelStackInfo<ItemStack>> aParallelQueue = new PriorityQueue<>(
                Comparator.comparing(i -> i.batch));
            for (Map.Entry<ItemStack, ParallelData> entry : tParallels.entrySet()) {
                aParallelQueue
                    .add(new ParallelStackInfo<>(entry.getValue().batch, entry.getValue().partial, entry.getKey()));
            }

            while (tSlotsFree > 0) {
                ParallelStackInfo<ItemStack> tParallel = aParallelQueue.poll();
                assert tParallel != null; // will always be true, specifying assert here to avoid IDE/compiler warnings
                Integer tCraftSize = tItemOutputMap.get(tParallel.stack);
                int tStackSize = tParallel.stack.getMaxStackSize();
                if (machine instanceof MTEMultiBlockBase multiBlockBase) {
                    for (MTEHatchOutputBus outputBus : multiBlockBase.mOutputBusses) {
                        if (outputBus instanceof IInfinitySlot) {
                            tStackSize = Integer.MAX_VALUE;
                            break;
                        }
                    }
                }
                tParallel.batch += (tParallel.partial + tStackSize) / tCraftSize;
                tParallel.partial = (tParallel.partial + tStackSize) % tCraftSize;
                aParallelQueue.add(tParallel);
                --tSlotsFree;
            }

            // Ensure that the head of the queue, if voidable, will have maximum batch size
            for (ItemStack vBusStack : voidStacks) {
                if (aParallelQueue.element().stack.isItemEqual(vBusStack)) {
                    ParallelStackInfo<ItemStack> tParallel = aParallelQueue.poll();
                    tParallel.batch = Integer.MAX_VALUE;
                    aParallelQueue.add(tParallel);
                }
            }

            return (int) Math.min(aParallelQueue.element().batch, Integer.MAX_VALUE);
        }
        return 0;
    }

    public static class ParallelData {

        public long batch;
        public long partial;

        public ParallelData(long batch, long partial) {
            this.batch = batch;
            this.partial = partial;
        }
    }

    public static class ParallelStackInfo<T> {

        public long batch;
        public long partial;
        public final T stack;

        public ParallelStackInfo(long batch, long partial, T stack) {
            this.batch = batch;
            this.partial = partial;
            this.stack = stack;
        }
    }
}
