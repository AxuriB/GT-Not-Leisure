package com.science.gtnl.recipe;

import static com.science.gtnl.Mods.ScienceNotLeisure;
import static gregtech.api.recipe.RecipeMaps.neutroniumCompressorRecipes;
import static gregtech.api.util.GTModHandler.getModItem;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeConstants.COMPRESSION_TIER;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTUtility;

public class CompressorRecipes implements IRecipePool {

    @Override
    public void loadRecipes() {
        GTValues.RA.stdBuilder()
            .itemInputsUnsafe(GTUtility.copyAmountUnsafe(7296, getModItem(ScienceNotLeisure.ID, "StargateTier9", 1, 0)))
            .itemOutputs(getModItem(ScienceNotLeisure.ID, "StargateSingularity", 1, 0))
            .duration(120 * SECONDS)
            .eut(TierEU.RECIPE_MAX)
            .metadata(COMPRESSION_TIER, 2)
            .addTo(neutroniumCompressorRecipes);
    }
}
