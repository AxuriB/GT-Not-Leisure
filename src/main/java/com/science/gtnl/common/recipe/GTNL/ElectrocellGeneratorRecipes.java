package com.science.gtnl.common.recipe.GTNL;

import com.science.gtnl.Utils.gui.recipe.ElectrocellGeneratorFrontend;
import com.science.gtnl.api.IRecipePool;
import com.science.gtnl.loader.RecipePool;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTOreDictUnificator;

public class ElectrocellGeneratorRecipes implements IRecipePool {

    public RecipeMap<?> EGR = RecipePool.ElectrocellGeneratorRecipes;
    public ElectrocellGeneratorFrontend.SpecialValueFormatter GENERATOR_EUT = ElectrocellGeneratorFrontend.SpecialValueFormatter.INSTANCE;

    @Override
    public void loadRecipes() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.plate, MaterialsUEVplus.Universium, 1),
                GTOreDictUnificator.get(OrePrefixes.plate, MaterialsUEVplus.MagMatter, 1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1))
            .fluidInputs(
                Materials.Lava.getFluid(1),
                Materials.Iron.getMolten(114514),
                MaterialsUEVplus.SpaceTime.getMolten(144))
            .fluidOutputs(Materials.Water.getFluid(1))
            .outputChances(5000)
            .eut(0)
            .specialValue(120)
            .metadata(GENERATOR_EUT, 1000)
            .duration(5000)
            .addTo(EGR);
    }
}
