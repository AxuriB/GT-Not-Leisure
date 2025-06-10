package com.science.gtnl.common.recipe.GregTech;

import net.minecraftforge.fluids.FluidStack;

import com.science.gtnl.api.IRecipePool;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gtPlusPlus.core.material.MaterialsElements;

public class FluidExtractorRecipes implements IRecipePool {

    final RecipeMap<?> FER = RecipeMaps.fluidExtractionRecipes;

    @Override
    public void loadRecipes() {
        GTValues.RA.stdBuilder()
            .itemInputs(MaterialsElements.getInstance().RADIUM.getDust(1))
            .fluidOutputs(new FluidStack(MaterialsElements.getInstance().RADIUM.getFluid(), 144))
            .duration(20)
            .eut(TierEU.RECIPE_EV)
            .addTo(FER);
    }
}
