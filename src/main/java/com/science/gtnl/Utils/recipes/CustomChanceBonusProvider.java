package com.science.gtnl.Utils.recipes;

import java.util.Map;
import java.util.WeakHashMap;

import com.science.gtnl.config.MainConfig;

import gregtech.api.metatileentity.implementations.MTEMultiBlockBase;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;

public class CustomChanceBonusProvider implements ChanceBonusManager.ChanceBonusProvider {

    public Map<MTEMultiBlockBase, GTRecipe> recipeMap = new WeakHashMap<>();

    @Override
    public Double getBonus(Object machine, int recipeTier, double prevMultiplier, GTRecipe recipe) {
        if (machine instanceof MTEMultiBlockBase mte) {
            recipeMap.put(mte, recipe);
            int machineTier = GTUtility.getTier(mte.getMaxInputVoltage());
            int baseTier = GTUtility.getTier(recipe.mEUt);
            double bonusPerTier = MainConfig.recipeOutputChance / 100.0;
            return getTierChanceBonus(machineTier, baseTier, bonusPerTier);
        }
        return null;
    }

    public double getTierChanceBonus(int tier, int baseTier, double bonusPerTier) {
        return tier <= baseTier ? 0.0 : (tier - baseTier) * bonusPerTier;
    }

    public GTRecipe getRecipeForMachine(MTEMultiBlockBase mte) {
        return recipeMap.get(mte);
    }
}
