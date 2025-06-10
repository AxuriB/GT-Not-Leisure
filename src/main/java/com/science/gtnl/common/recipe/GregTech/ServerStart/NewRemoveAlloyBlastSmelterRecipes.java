package com.science.gtnl.common.recipe.GregTech.ServerStart;

import com.science.gtnl.api.IRecipePool;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gtPlusPlus.api.recipe.GTPPRecipeMaps;

public class NewRemoveAlloyBlastSmelterRecipes implements IRecipePool {

    final RecipeMap<?> aBS = GTPPRecipeMaps.alloyBlastSmelterRecipes;

    @Override
    public void loadRecipes() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Europium, 1))
            .fluidOutputs(Materials.Europium.getMolten(144))
            .specialValue(0)
            .duration(120)
            .eut(TierEU.RECIPE_LuV)
            .addTo(aBS);
    }
}
