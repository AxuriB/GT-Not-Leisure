package com.science.gtnl.common.recipe.AprilFool;

import static gregtech.api.enums.GTValues.RA;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.api.IRecipePool;
import com.science.gtnl.loader.RecipePool;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTOreDictUnificator;

public class SteamCarpenterRecipe implements IRecipePool {

    public RecipeMap<?> SCR = RecipePool.SteamCarpenterRecipes;

    @Override
    public void loadRecipes() {

        RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 4))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Wood, 1))
            .duration(1 * SECONDS)
            .eut(4)
            .addTo(SCR);

        RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Wood, 1),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 6))
            .itemOutputs(GTNLItemList.IronReinforcedWood.get(1))
            .duration(5 * SECONDS)
            .eut(16)
            .addTo(SCR);

        RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Wood, 1),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 6))
            .itemOutputs(GTNLItemList.BronzeReinforcedWood.get(1))
            .duration(5 * SECONDS)
            .eut(16)
            .addTo(SCR);

        RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Wood, 1),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 6))
            .itemOutputs(GTNLItemList.SteelReinforcedWood.get(1))
            .duration(5 * SECONDS)
            .eut(16)
            .addTo(SCR);

    }

}
