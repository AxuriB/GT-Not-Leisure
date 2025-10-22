package com.science.gtnl.common.recipe.gregtech;

import static gregtech.api.enums.Mods.*;

import com.science.gtnl.api.IRecipePool;
import com.science.gtnl.common.material.MaterialPool;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;

public class AutoclaveRecipes implements IRecipePool {

    public RecipeMap<?> AR = RecipeMaps.autoclaveRecipes;

    @Override
    public void loadRecipes() {

        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 16))
            .fluidInputs(MaterialPool.Polyetheretherketone.getMolten(9))
            .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartCarbonFibre", 64))
            .outputChances(10000)
            .duration(60)
            .eut(TierEU.RECIPE_IV)
            .addTo(AR);
    }
}
