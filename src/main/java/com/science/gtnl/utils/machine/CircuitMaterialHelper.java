package com.science.gtnl.utils.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;

import gregtech.api.enums.MaterialsUEVplus;
import lombok.Getter;
import lombok.Setter;

public class CircuitMaterialHelper {

    @Getter
    @Setter
    public static List<ItemStackData> materialParameterList = new ArrayList<>();
    @Getter
    @Setter
    public static long worldSeed;

    public static void init() {
        addOrUpdateEuModifier(materialParameterList, MaterialsUEVplus.MagMatter.getNanite(1), 0, 1);
    }

    public static void addOrUpdate(List<ItemStackData> list, ItemStack stack, double speedBoost, double euModifier,
        int maxTierSkips, double successChance, double failedChance, int parallelCount, double outputMultiplier,
        double speedBoostMin, double speedBoostMax, double euModifierMin, double euModifierMax, int maxTierSkipsMin,
        int maxTierSkipsMax, double successChanceMin, double successChanceMax, double failedChanceMin,
        double failedChanceMax, int parallelCountMin, int parallelCountMax, double outputMultiplierMin,
        double outputMultiplierMax) {

        speedBoost = Math.min(speedBoost, 1.0);
        euModifier = Math.min(euModifier, 1.0);
        maxTierSkips = Math.min(maxTierSkips, 1);
        successChance = Math.min(successChance, 1.0);
        failedChance = Math.min(failedChance, 1.0);
        parallelCount = Math.max(parallelCount, 0);
        outputMultiplier = Math.max(outputMultiplier, 1.0);

        speedBoostMin = Math.min(speedBoostMin, 1.0);
        speedBoostMax = Math.min(speedBoostMax, 1.0);
        euModifierMin = Math.min(euModifierMin, 1.0);
        euModifierMax = Math.min(euModifierMax, 1.0);
        maxTierSkipsMin = Math.min(maxTierSkipsMin, 1);
        maxTierSkipsMax = Math.min(maxTierSkipsMax, 1);
        successChanceMin = Math.min(successChanceMin, 1.0);
        successChanceMax = Math.min(successChanceMax, 1.0);
        failedChanceMin = Math.min(failedChanceMin, 1.0);
        failedChanceMax = Math.min(failedChanceMax, 1.0);
        parallelCountMin = Math.min(parallelCountMin, 1);
        parallelCountMax = Math.min(parallelCountMax, 1);
        outputMultiplierMin = Math.min(outputMultiplierMin, 1.0);
        outputMultiplierMax = Math.min(outputMultiplierMax, 1.0);

        for (ItemStackData data : list) {
            if (ItemStack.areItemStacksEqual(stack, data.stack)) {
                data.setDirectParams(
                    speedBoost,
                    euModifier,
                    maxTierSkips,
                    successChance,
                    failedChance,
                    parallelCount,
                    outputMultiplier);
                data.setRangeParams(
                    speedBoostMin,
                    speedBoostMax,
                    euModifierMin,
                    euModifierMax,
                    maxTierSkipsMin,
                    maxTierSkipsMax,
                    successChanceMin,
                    successChanceMax,
                    failedChanceMin,
                    failedChanceMax,
                    parallelCountMin,
                    parallelCountMax,
                    outputMultiplierMin,
                    outputMultiplierMax);
                return;
            }
        }

        list.add(
            new ItemStackData(
                stack,
                speedBoost,
                euModifier,
                maxTierSkips,
                successChance,
                failedChance,
                parallelCount,
                outputMultiplier,
                speedBoostMin,
                speedBoostMax,
                euModifierMin,
                euModifierMax,
                maxTierSkipsMin,
                maxTierSkipsMax,
                successChanceMin,
                successChanceMax,
                failedChanceMin,
                failedChanceMax,
                parallelCountMin,
                parallelCountMax,
                outputMultiplierMin,
                outputMultiplierMax));
    }

    public static void addOrUpdate(List<ItemStackData> list, ItemStack stack, double speedBoost, double euModifier,
        int maxTierSkips, double successChance, double failedChance, int parallelCount, double outputMultiplier) {
        addOrUpdate(
            list,
            stack,
            speedBoost,
            euModifier,
            maxTierSkips,
            successChance,
            failedChance,
            parallelCount,
            outputMultiplier,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1);
    }

    public static void addOrUpdate(List<ItemStackData> list, ItemStack stack, double speedBoostMin,
        double speedBoostMax, double euModifierMin, double euModifierMax, int maxTierSkipsMin, int maxTierSkipsMax,
        double successChanceMin, double successChanceMax, double failedChanceMin, double failedChanceMax,
        int parallelCountMin, int parallelCountMax, double outputMultiplierMin, double outputMultiplierMax) {
        addOrUpdate(
            list,
            stack,
            -1,
            -1,
            -1,
            -1,
            -1,
            0,
            1.0,
            speedBoostMin,
            speedBoostMax,
            euModifierMin,
            euModifierMax,
            maxTierSkipsMin,
            maxTierSkipsMax,
            successChanceMin,
            successChanceMax,
            failedChanceMin,
            failedChanceMax,
            parallelCountMin,
            parallelCountMax,
            outputMultiplierMin,
            outputMultiplierMax);
    }

    public static void addOrUpdateSpeedBoost(List<ItemStackData> list, ItemStack stack, double speedBoostMin,
        double speedBoostMax) {
        addOrUpdate(
            list,
            stack,
            -1,
            -1,
            -1,
            -1,
            -1,
            0,
            1.0,
            speedBoostMin,
            speedBoostMax,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1);
    }

    public static void addOrUpdateEuModifier(List<ItemStackData> list, ItemStack stack, double euModifierMin,
        double euModifierMax) {
        addOrUpdate(
            list,
            stack,
            -1,
            -1,
            -1,
            -1,
            -1,
            0,
            1.0,
            -1,
            -1,
            euModifierMin,
            euModifierMax,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1);
    }

    public static void addOrUpdateMaxTierSkips(List<ItemStackData> list, ItemStack stack, int maxTierSkipsMin,
        int maxTierSkipsMax) {
        addOrUpdate(
            list,
            stack,
            -1,
            -1,
            -1,
            -1,
            -1,
            0,
            1.0,
            -1,
            -1,
            -1,
            -1,
            maxTierSkipsMin,
            maxTierSkipsMax,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1);
    }

    public static void addOrUpdateSuccessChance(List<ItemStackData> list, ItemStack stack, double successChanceMin,
        double successChanceMax) {
        addOrUpdate(
            list,
            stack,
            -1,
            -1,
            -1,
            -1,
            -1,
            0,
            1.0,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            successChanceMin,
            successChanceMax,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1);
    }

    public static void addOrUpdateFailedChance(List<ItemStackData> list, ItemStack stack, double failedChanceMin,
        double failedChanceMax) {
        addOrUpdate(
            list,
            stack,
            -1,
            -1,
            -1,
            -1,
            -1,
            0,
            1.0,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            failedChanceMin,
            failedChanceMax,
            -1,
            -1,
            -1,
            -1);
    }

    public static void addOrUpdateParallelCount(List<ItemStackData> list, ItemStack stack, int parallelCountMin,
        int parallelCountMax) {
        addOrUpdate(
            list,
            stack,
            -1,
            -1,
            -1,
            -1,
            -1,
            0,
            1.0,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            parallelCountMin,
            parallelCountMax,
            -1,
            -1);
    }

    public static void addOrUpdateOutputMultiplier(List<ItemStackData> list, ItemStack stack,
        double outputMultiplierMin, double outputMultiplierMax) {
        addOrUpdate(
            list,
            stack,
            -1,
            -1,
            -1,
            -1,
            -1,
            0,
            1.0,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            outputMultiplierMin,
            outputMultiplierMax);
    }

    public static boolean remove(List<ItemStackData> list, ItemStack stack) {
        return list.removeIf(data -> ItemStack.areItemStacksEqual(stack, data.stack));
    }

    public static boolean update(List<ItemStackData> list, ItemStack stack, double speedBoost, double euModifier,
        int maxTierSkips, double successChance, double failedChance, int parallelCount, double outputMultiplier,
        double speedBoostMin, double speedBoostMax, double euModifierMin, double euModifierMax, int maxTierSkipsMin,
        int maxTierSkipsMax, double successChanceMin, double successChanceMax, double failedChanceMin,
        double failedChanceMax, int parallelCountMin, int parallelCountMax, double outputMultiplierMin,
        double outputMultiplierMax) {

        for (ItemStackData data : list) {
            if (ItemStack.areItemStacksEqual(stack, data.stack)) {
                data.setDirectParams(
                    speedBoost,
                    euModifier,
                    maxTierSkips,
                    successChance,
                    failedChance,
                    parallelCount,
                    outputMultiplier);
                data.setRangeParams(
                    speedBoostMin,
                    speedBoostMax,
                    euModifierMin,
                    euModifierMax,
                    maxTierSkipsMin,
                    maxTierSkipsMax,
                    successChanceMin,
                    successChanceMax,
                    failedChanceMin,
                    failedChanceMax,
                    parallelCountMin,
                    parallelCountMax,
                    outputMultiplierMin,
                    outputMultiplierMax);
                return true;
            }
        }
        return false;
    }

    public static boolean update(List<ItemStackData> list, ItemStack stack, double speedBoost, double euModifier,
        int maxTierSkips, double successChance, double failedChance, int parallelCount, double outputMultiplier) {

        return update(
            list,
            stack,
            speedBoost,
            euModifier,
            maxTierSkips,
            successChance,
            failedChance,
            parallelCount,
            outputMultiplier,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1);
    }

    public static boolean update(List<ItemStackData> list, ItemStack stack, double speedBoostMin, double speedBoostMax,
        double euModifierMin, double euModifierMax, int maxTierSkipsMin, int maxTierSkipsMax, double successChanceMin,
        double successChanceMax, double failedChanceMin, double failedChanceMax, int parallelCountMin,
        int parallelCountMax, double outputMultiplierMin, double outputMultiplierMax) {

        return update(
            list,
            stack,
            -1,
            -1,
            -1,
            -1,
            -1,
            0,
            1.0,
            speedBoostMin,
            speedBoostMax,
            euModifierMin,
            euModifierMax,
            maxTierSkipsMin,
            maxTierSkipsMax,
            successChanceMin,
            successChanceMax,
            failedChanceMin,
            failedChanceMax,
            parallelCountMin,
            parallelCountMax,
            outputMultiplierMin,
            outputMultiplierMax);
    }

    public static boolean updateSpeedBoostRange(List<ItemStackData> list, ItemStack stack, double speedBoostMin,
        double speedBoostMax) {
        return update(
            list,
            stack,
            -1,
            -1,
            -1,
            -1,
            -1,
            0,
            1.0,
            speedBoostMin,
            speedBoostMax,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1);
    }

    public static boolean updateEuModifierRange(List<ItemStackData> list, ItemStack stack, double euModifierMin,
        double euModifierMax) {
        return update(
            list,
            stack,
            -1,
            -1,
            -1,
            -1,
            -1,
            0,
            1.0,
            -1,
            -1,
            euModifierMin,
            euModifierMax,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1);
    }

    public static boolean updateMaxTierSkipsRange(List<ItemStackData> list, ItemStack stack, int maxTierSkipsMin,
        int maxTierSkipsMax) {
        return update(
            list,
            stack,
            -1,
            -1,
            -1,
            -1,
            -1,
            0,
            1.0,
            -1,
            -1,
            -1,
            -1,
            maxTierSkipsMin,
            maxTierSkipsMax,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1);
    }

    public static boolean updateSuccessChanceRange(List<ItemStackData> list, ItemStack stack, double successChanceMin,
        double successChanceMax) {
        return update(
            list,
            stack,
            -1,
            -1,
            -1,
            -1,
            -1,
            0,
            1.0,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            successChanceMin,
            successChanceMax,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1);
    }

    public static boolean updateFailedChanceRange(List<ItemStackData> list, ItemStack stack, double failedChanceMin,
        double failedChanceMax) {
        return update(
            list,
            stack,
            -1,
            -1,
            -1,
            -1,
            -1,
            0,
            1.0,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            failedChanceMin,
            failedChanceMax,
            -1,
            -1,
            -1,
            -1);
    }

    public static boolean updateParallelCountRange(List<ItemStackData> list, ItemStack stack, int parallelCountMin,
        int parallelCountMax) {
        return update(
            list,
            stack,
            -1,
            -1,
            -1,
            -1,
            -1,
            0,
            1.0,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            parallelCountMin,
            parallelCountMax,
            -1,
            -1);
    }

    public static boolean updateOutputMultiplierRange(List<ItemStackData> list, ItemStack stack,
        double outputMultiplierMin, double outputMultiplierMax) {
        return update(
            list,
            stack,
            -1,
            -1,
            -1,
            -1,
            -1,
            0,
            1.0,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            outputMultiplierMin,
            outputMultiplierMax);
    }

    public static ItemStackData get(List<ItemStackData> list, ItemStack stack) {
        for (ItemStackData data : list) {
            if (ItemStack.areItemStacksEqual(stack, data.stack)) {
                return data;
            }
        }
        return null;
    }

    public static void clear(List<ItemStackData> bigList) {
        bigList.clear();
    }

    public static void applyRandomizedParams(List<ItemStackData> list, long worldSeed) {
        Random random = new Random(worldSeed);

        for (ItemStackData data : list) {
            data.speedBoost = round2(randomInRange(random, data.speedBoostMin, data.speedBoostMax));
            data.euModifier = round2(randomInRange(random, data.euModifierMin, data.euModifierMax));
            data.maxTierSkips = randomInRange(random, data.maxTierSkipsMin, data.maxTierSkipsMax);
            data.successChance = round2(randomInRange(random, data.successChanceMin, data.successChanceMax));
            data.failedChance = round2(randomInRange(random, data.failedChanceMin, data.failedChanceMax));
            data.parallelCount = randomInRange(random, data.parallelCountMin, data.parallelCountMax);
            data.outputMultiplier = round2(randomInRange(random, data.outputMultiplierMin, data.outputMultiplierMax));
        }
    }

    private static int randomInRange(Random random, int min, int max) {
        if (min < 0 || max < 0) return 0;
        if (min > max) return min;
        return min + random.nextInt(max - min + 1);
    }

    private static double randomInRange(Random random, double min, double max) {
        if (!Double.isFinite(min) || !Double.isFinite(max)) return 0.0;
        if (min > max) {
            double temp = min;
            min = max;
            max = temp;
        }
        if (min == max) return min;
        return min + random.nextDouble() * (max - min);
    }

    private static double round2(double val) {
        return Math.round(val * 100.0) / 100.0;
    }

    public static class ItemStackData {

        @Getter
        @Setter
        public ItemStack stack;

        @Getter
        @Setter
        public double speedBoost = 1.0, euModifier = 1.0, successChance = 1.0, failedChance = 0, outputMultiplier = 1.0;

        @Getter
        @Setter
        public int parallelCount = 1, maxTierSkips = 1;

        @Getter
        @Setter
        private double speedBoostMin = 0, speedBoostMax = 1;

        @Getter
        @Setter
        private double euModifierMin = 0, euModifierMax = 1;

        @Getter
        @Setter
        private int maxTierSkipsMin = 0, maxTierSkipsMax = 1;

        @Getter
        @Setter
        private double successChanceMin = 0, successChanceMax = 1;

        @Getter
        @Setter
        private double failedChanceMin = 0, failedChanceMax = 1;

        @Getter
        @Setter
        private int parallelCountMin = 0, parallelCountMax = 1;

        @Getter
        @Setter
        private double outputMultiplierMin = 0, outputMultiplierMax = 1;

        public ItemStackData(ItemStack stack, double speedBoost, double euModifier, int maxTierSkips,
            double successChance, double failedChance, int parallelCount, double outputMultiplier, double speedBoostMin,
            double speedBoostMax, double euModifierMin, double euModifierMax, int maxTierSkipsMin, int maxTierSkipsMax,
            double successChanceMin, double successChanceMax, double failedChanceMin, double failedChanceMax,
            int parallelCountMin, int parallelCountMax, double outputMultiplierMin, double outputMultiplierMax) {
            this.stack = stack;
            setDirectParams(
                speedBoost,
                euModifier,
                maxTierSkips,
                successChance,
                failedChance,
                parallelCount,
                outputMultiplier);
            setRangeParams(
                speedBoostMin,
                speedBoostMax,
                euModifierMin,
                euModifierMax,
                maxTierSkipsMin,
                maxTierSkipsMax,
                successChanceMin,
                successChanceMax,
                failedChanceMin,
                failedChanceMax,
                parallelCountMin,
                parallelCountMax,
                outputMultiplierMin,
                outputMultiplierMax);
        }

        public void setDirectParams(double speedBoost, double euModifier, int maxTierSkips, double successChance,
            double failedChance, int parallelCount, double outputMultiplier) {
            if (speedBoost >= 0) this.speedBoost = speedBoost;
            if (euModifier >= 0) this.euModifier = euModifier;
            if (maxTierSkips >= 0) this.maxTierSkips = maxTierSkips;
            if (successChance >= 0) this.successChance = successChance;
            if (failedChance >= 0) this.failedChance = failedChance;
            if (parallelCount >= 0) this.parallelCount = parallelCount;
            if (outputMultiplier >= 0) this.outputMultiplier = outputMultiplier;
        }

        public void setRangeParams(double speedBoostMin, double speedBoostMax, double euModifierMin,
            double euModifierMax, int maxTierSkipsMin, int maxTierSkipsMax, double successChanceMin,
            double successChanceMax, double failedChanceMin, double failedChanceMax, int parallelCountMin,
            int parallelCountMax, double outputMultiplierMin, double outputMultiplierMax) {
            if (speedBoostMin >= 0) this.speedBoostMin = speedBoostMin;
            if (speedBoostMax >= 0) this.speedBoostMax = speedBoostMax;

            if (euModifierMin >= 0) this.euModifierMin = euModifierMin;
            if (euModifierMax >= 0) this.euModifierMax = euModifierMax;

            if (maxTierSkipsMin >= 0) this.maxTierSkipsMin = maxTierSkipsMin;
            if (maxTierSkipsMax >= 0) this.maxTierSkipsMax = maxTierSkipsMax;

            if (successChanceMin >= 0) this.successChanceMin = successChanceMin;
            if (successChanceMax >= 0) this.successChanceMax = successChanceMax;

            if (failedChanceMin >= 0) this.failedChanceMin = failedChanceMin;
            if (failedChanceMax >= 0) this.failedChanceMax = failedChanceMax;
            if (parallelCountMin >= 0) this.parallelCountMin = parallelCountMin;
            if (parallelCountMax >= 0) this.parallelCountMax = parallelCountMax;
            if (outputMultiplierMin >= 0) this.outputMultiplierMin = outputMultiplierMin;
            if (outputMultiplierMax >= 0) this.outputMultiplierMax = outputMultiplierMax;
        }
    }
}
