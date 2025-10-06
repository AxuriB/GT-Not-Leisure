package com.science.gtnl.common.recipe.GTNL;

import static gregtech.api.util.GTRecipeBuilder.*;

import java.util.HashMap;
import java.util.List;

import net.minecraft.item.ItemStack;

import com.science.gtnl.Utils.recipes.RecipeBuilder;
import com.science.gtnl.api.IRecipePool;
import com.science.gtnl.loader.RecipePool;

import galaxyspace.core.recipe.RocketRecipes;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;
import micdoodle8.mods.galacticraft.api.recipe.INasaWorkbenchRecipe;

public class RocketAssemblerRecipes implements IRecipePool {

    public RecipeMap<?> RAR = RecipePool.RocketAssemblerRecipes;

    @Override
    public void loadRecipes() {
        registerRocketRecipes(RocketRecipes.getRocketT1Recipes(), 1);
        registerRocketRecipes(RocketRecipes.getRocketT2Recipes(), 2);
        registerRocketRecipes(RocketRecipes.getRocketT3Recipes(), 3);
        registerRocketRecipes(RocketRecipes.getRocketT4Recipes(), 4);
        registerRocketRecipes(RocketRecipes.getRocketT5Recipes(), 5);
        registerRocketRecipes(RocketRecipes.getRocketT6Recipes(), 6);
        registerRocketRecipes(RocketRecipes.getRocketT7Recipes(), 7);
        registerRocketRecipes(RocketRecipes.getRocketT8Recipes(), 8);
    }

    public void registerRocketRecipes(List<INasaWorkbenchRecipe> recipes, int specialValue) {
        for (INasaWorkbenchRecipe recipe : recipes) {
            HashMap<Integer, ItemStack> inputs = recipe.getRecipeInput();

            int maxSlot = inputs.keySet()
                .stream()
                .max(Integer::compareTo)
                .orElse(0);
            ItemStack[] orderedInputs = new ItemStack[maxSlot];

            for (int slot = 1; slot <= maxSlot; slot++) {
                orderedInputs[slot - 1] = inputs.getOrDefault(slot, null);
            }

            RecipeBuilder.builder()
                .itemInputsAllowNulls(orderedInputs)
                .itemOutputs(recipe.getRecipeOutput())
                .duration(recipe.getRecipeSize() * SECONDS)
                .specialValue(specialValue)
                .eut((int) TierEU.RECIPE_HV)
                .addTo(RAR);
        }
    }

}
