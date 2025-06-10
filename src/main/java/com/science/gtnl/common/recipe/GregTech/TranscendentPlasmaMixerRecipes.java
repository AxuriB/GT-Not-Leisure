package com.science.gtnl.common.recipe.GregTech;

import com.science.gtnl.common.materials.MaterialPool;
import com.science.gtnl.loader.IRecipePool;

import goodgenerator.items.GGMaterial;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTUtility;

public class TranscendentPlasmaMixerRecipes implements IRecipePool {

    final RecipeMap<?> TPMP = RecipeMaps.transcendentPlasmaMixerRecipes;

    @Override
    public void loadRecipes() {
        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(6))
            .fluidInputs(
                MaterialsUEVplus.Time.getFluid(710),
                MaterialsUEVplus.Space.getFluid(710),
                GGMaterial.naquadahBasedFuelMkI.getFluidOrGas(1000),
                GGMaterial.naquadahBasedFuelMkII.getFluidOrGas(1000),
                GGMaterial.naquadahBasedFuelMkIII.getFluidOrGas(1000),
                GGMaterial.naquadahBasedFuelMkIV.getFluidOrGas(1000),
                GGMaterial.naquadahBasedFuelMkV.getFluidOrGas(1000),
                GGMaterial.naquadahBasedFuelMkVI.getFluidOrGas(1000),
                Materials.LiquidAir.getFluid(85200))
            .fluidOutputs(MaterialPool.ExcitedNaquadahFuel.getFluidOrGas(1000))
            .duration(20)
            .eut(TierEU.RECIPE_UXV)
            .addTo(TPMP);
    }
}
