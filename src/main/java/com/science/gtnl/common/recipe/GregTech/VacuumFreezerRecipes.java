package com.science.gtnl.common.recipe.GregTech;

import com.science.gtnl.api.IRecipePool;
import com.science.gtnl.common.material.MaterialPool;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;

public class VacuumFreezerRecipes implements IRecipePool {

    public RecipeMap<?> CNCR = RecipeMaps.vacuumFreezerRecipes;

    @Override
    public void loadRecipes() {
        GTValues.RA.stdBuilder()
            .fluidInputs(MaterialPool.EnderAir.getFluidOrGas(4000))
            .fluidOutputs(MaterialPool.LiquidEnderAir.getFluidOrGas(4000))
            .specialValue(0)
            .duration(80)
            .eut(TierEU.RECIPE_HV)
            .addTo(CNCR);

    }
}
