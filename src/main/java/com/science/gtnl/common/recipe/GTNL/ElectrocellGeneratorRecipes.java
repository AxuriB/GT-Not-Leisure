package com.science.gtnl.common.recipe.GTNL;

import com.science.gtnl.Utils.recipes.ElectrocellGeneratorSpecialValue;
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
    public ElectrocellGeneratorSpecialValue GENERATOR_EUT = ElectrocellGeneratorSpecialValue.INSTANCE;

    @Override
    public void loadRecipes() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.plate, MaterialsUEVplus.Universium, 1),
                GTOreDictUnificator.get(OrePrefixes.plate, MaterialsUEVplus.MagMatter, 1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1))
            .fluidInputs(
                Materials.Lava.getFluid(2147483647),
                Materials.Iron.getMolten(114514),
                MaterialsUEVplus.SpaceTime.getMolten(144),
                Materials.Oxygen.getGas(1919810))
            .fluidOutputs(MaterialsUEVplus.MagnetohydrodynamicallyConstrainedStarMatter.getMolten(1919810))
            .outputChances(5000)
            .eut(0)
            .specialValue(120)
            .metadata(GENERATOR_EUT, Long.MAX_VALUE)
            .duration(5000)
            .addTo(EGR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Carbon, 1),
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Lead, 2))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Galena, 2))
            .fluidInputs(
                Materials.SodiumPersulfate.getFluid(2000))
            .fluidOutputs(Materials.Hydrogen.getGas(2000))
            .outputChances(5000)
            .eut(0)
            .specialValue(110)
            .metadata(GENERATOR_EUT, 2560L)
            .duration(430)
            .addTo(EGR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Carbon, 3),
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Lead, 4))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Galena, 3))
            .fluidInputs(
                Materials.SulfuricAcid.getFluid(6000))
            .fluidOutputs(Materials.Hydrogen.getGas(6000))
            .outputChances(7500)
            .eut(0)
            .specialValue(110)
            .metadata(GENERATOR_EUT, 6144L)
            .duration(430)
            .addTo(EGR);
    }
}


