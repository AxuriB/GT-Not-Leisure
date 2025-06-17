package com.science.gtnl.Utils.recipes;

import java.util.LinkedList;
import java.util.OptionalDouble;
import java.util.function.Function;

import com.science.gtnl.ScienceNotLeisure;
import com.science.gtnl.config.MainConfig;

import gregtech.api.metatileentity.implementations.MTEMultiBlockBase;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;

public class ChanceBonusManager {

    // Original code from Overpowered Mod. MIT License. 2025/4/18

    public static final LinkedList<ChanceBonusProvider> bonusProviders = new LinkedList<>();
    public static CustomChanceBonusProvider customProvider = new CustomChanceBonusProvider();

    public interface ChanceBonusProvider {

        Double getBonus(Object machine, int recipeTier, double prevMultiplier, GTRecipe recipe);
    }

    public static class ChanceBonusProviderContext {

        public final Object machine;
        public final int recipeTier;
        public final double prevChanceMultiplier;
        public final GTRecipe recipe;

        public ChanceBonusProviderContext(Object machine, int recipeTier, double prevChanceMultiplier,
            GTRecipe recipe) {
            this.machine = machine;
            this.recipeTier = recipeTier;
            this.prevChanceMultiplier = prevChanceMultiplier;
            this.recipe = recipe;
        }
    }

    public static void addLastBonusProvider(ChanceBonusProvider provider) {
        bonusProviders.addLast(provider);
    }

    public static void addFirstBonusProvider(ChanceBonusProvider provider) {
        bonusProviders.addFirst(provider);
    }

    public static void addLastBonusProvider(Function<ChanceBonusProviderContext, Double> provider) {
        bonusProviders.addLast(
            (machine, tier, prev, recipe) -> provider
                .apply(new ChanceBonusProviderContext(machine, tier, prev, recipe)));
    }

    public static void addFirstBonusProvider(Function<ChanceBonusProviderContext, Double> provider) {
        bonusProviders.addFirst(
            (machine, tier, prev, recipe) -> provider
                .apply(new ChanceBonusProviderContext(machine, tier, prev, recipe)));
    }

    private static double getTierChanceBonus(int tier, int baseTier, double bonusPerTier) {
        return tier <= baseTier ? 0.0 : (tier - baseTier) * bonusPerTier;
    }

    public static Double getChanceBonus(Object machine, int recipeTier, double prevMultiplier, GTRecipe recipe) {
        for (ChanceBonusProvider provider : bonusProviders) {
            try {
                Double bonus = provider.getBonus(machine, recipeTier, prevMultiplier, recipe);
                if (bonus != null) return bonus;
            } catch (Exception e) {
                ScienceNotLeisure.LOG.warn("Error in chance bonus provider", e);
            }
        }
        return null;
    }

    public static OptionalDouble getChanceBonusOptional(Object machine, int recipeTier, double prevMultiplier,
        GTRecipe recipe) {
        Double result = getChanceBonus(machine, recipeTier, prevMultiplier, recipe);
        return result != null ? OptionalDouble.of(result) : OptionalDouble.empty();
    }

    public static GTRecipe copyAndBonusChance(GTRecipe recipe, double bonus) {
        if (recipe.mChances == null) {
            return recipe;
        } else {
            GTRecipe copy = recipe.copy();
            int[] newChances = new int[copy.mChances.length];
            for (int i = 0; i < copy.mChances.length; i++) {
                newChances[i] = Math.min((int) (copy.mChances[i] + (bonus * 10000)), 10000);
            }
            copy.mChances = newChances;
            return copy;
        }
    }

    static {
        ChanceBonusManager.addFirstBonusProvider(customProvider);
        addLastBonusProvider((machine, recipeTier, prevBonus, recipe) -> {
            if (machine instanceof MTEMultiBlockBase mte) {
                try {
                    int machineTier = GTUtility.getTier(Math.min(Integer.MAX_VALUE, mte.getMaxInputVoltage()));
                    int baseTier = GTUtility.getTier(recipe.mEUt);
                    double bonusPerTier = MainConfig.recipeOutputChance / 100.0;

                    return getTierChanceBonus(Math.min(16, machineTier), baseTier, bonusPerTier);
                } catch (Exception e) {
                    ScienceNotLeisure.LOG.warn("Error reading MTEMultiBlockBase voltage tier:{}", mte, e);
                }
            }
            return null;
        });
    }

}
