package com.science.gtnl.common.recipe.GregTech;

import net.minecraft.item.ItemStack;

import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.api.IRecipePool;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTOreDictUnificator;

public class CuttingRecipes implements IRecipePool {

    final RecipeMap<?> CR = RecipeMaps.cutterRecipes;

    public void recipeWithPurifiedWater(ItemStack[] inputs, ItemStack[] outputs, Materials lowTierWater,
        Materials highTierWater, int duration, int boostedDuration, long eut) {
        GTValues.RA.stdBuilder()
            .itemInputs(inputs)
            .itemOutputs(outputs)
            .fluidInputs(lowTierWater.getFluid(100L))
            .duration(duration)
            .eut(eut)
            .addTo(CR);
        GTValues.RA.stdBuilder()
            .itemInputs(inputs)
            .itemOutputs(outputs)
            .fluidInputs(highTierWater.getFluid(100L))
            .duration(boostedDuration)
            .eut(eut)
            .addTo(CR);
    }

    @Override
    public void loadRecipes() {

        recipeWithPurifiedWater(
            new ItemStack[] { GTNLItemList.NeutroniumBoule.get(1) },
            new ItemStack[] { GTNLItemList.NeutroniumWafer.get(64), GTNLItemList.NeutroniumWafer.get(64),
                GTNLItemList.NeutroniumWafer.get(16),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.SiliconSG, 64) },
            Materials.Grade3PurifiedWater,
            Materials.Grade4PurifiedWater,
            2400,
            1200,
            TierEU.RECIPE_IV);

        recipeWithPurifiedWater(
            new ItemStack[] { GTNLItemList.HighlyAdvancedSocWafer.get(1) },
            new ItemStack[] { GTNLItemList.HighlyAdvancedSoc.get(6) },
            Materials.Grade5PurifiedWater,
            Materials.Grade6PurifiedWater,
            900,
            450,
            TierEU.RECIPE_IV);

        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.Circuit_Silicon_Wafer7.get(1))
            .itemOutputs(ItemList.Circuit_Chip_Optical.get(16))
            .fluidInputs(Materials.Grade6PurifiedWater.getFluid(280L))
            .duration(560)
            .eut(TierEU.RECIPE_UHV)
            .addTo(CR);
    }
}
