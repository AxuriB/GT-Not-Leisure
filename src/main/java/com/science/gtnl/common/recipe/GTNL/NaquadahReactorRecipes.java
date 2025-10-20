package com.science.gtnl.common.recipe.GTNL;

import com.science.gtnl.api.IRecipePool;
import com.science.gtnl.loader.RecipePool;

import goodgenerator.items.GGMaterial;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.recipe.RecipeMap;

public class NaquadahReactorRecipes implements IRecipePool {

    public RecipeMap<?> NRR = RecipePool.NaquadahReactorRecipes;

    @Override
    public void loadRecipes() {

        GTValues.RA.stdBuilder()
            .fluidInputs(GGMaterial.naquadahBasedFuelMkI.getFluidOrGas(16), Materials.Hydrogen.getGas(80))
            .fluidOutputs(GGMaterial.naquadahBasedFuelMkIDepleted.getFluidOrGas(16))
            .duration(875)
            .eut(0)
            .specialValue(524288)
            .addTo(NRR);

        GTValues.RA.stdBuilder()
            .fluidInputs(GGMaterial.naquadahBasedFuelMkI.getFluidOrGas(160), Materials.Oxygen.getPlasma(40))
            .fluidOutputs(GGMaterial.naquadahBasedFuelMkIDepleted.getFluidOrGas(160))
            .duration(14000)
            .eut(0)
            .specialValue(524288)
            .addTo(NRR);

        GTValues.RA.stdBuilder()
            .fluidInputs(GGMaterial.naquadahBasedFuelMkII.getFluidOrGas(16), Materials.Hydrogen.getGas(80))
            .fluidOutputs(GGMaterial.naquadahBasedFuelMkIIDepleted.getFluidOrGas(16))
            .duration(1250)
            .eut(0)
            .specialValue(524288)
            .addTo(NRR);

        GTValues.RA.stdBuilder()
            .fluidInputs(GGMaterial.naquadahBasedFuelMkII.getFluidOrGas(160), Materials.Nitrogen.getPlasma(40))
            .fluidOutputs(GGMaterial.naquadahBasedFuelMkIIDepleted.getFluidOrGas(160))
            .duration(20000)
            .eut(0)
            .specialValue(524288)
            .addTo(NRR);

    }
}
