package com.science.gtnl.common.recipe.GregTech;

import static gregtech.api.util.GTRecipeConstants.COIL_HEAT;

import net.minecraft.item.ItemStack;

import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.api.IRecipePool;
import com.science.gtnl.common.material.MaterialPool;

import bartworks.system.material.WerkstoffLoader;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gtPlusPlus.core.item.ModItems;

public class BlastFurnaceRecipes implements IRecipePool {

    final RecipeMap<?> BFR = RecipeMaps.blastFurnaceRecipes;

    @Override
    public void loadRecipes() {

        GTValues.RA.stdBuilder()
            .itemInputsUnsafe(
                GTOreDictUnificator.get(OrePrefixes.block, Materials.SiliconSG, 96),
                ItemList.GalliumArsenideCrystal.get(8),
                GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Neutronium, 4),
                GTUtility.getIntegratedCircuit(3))
            .itemOutputs(GTNLItemList.NeutroniumBoule.get(1))
            .fluidInputs(Materials.Radon.getGas(32000))
            .duration(18000)
            .eut(TierEU.RECIPE_LuV)
            .metadata(COIL_HEAT, 10000)
            .addTo(BFR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Europium, 1),
                GTUtility.getIntegratedCircuit(11))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Europium, 1))
            .fluidInputs(Materials.Helium.getGas(1000))
            .duration(120)
            .eut(TierEU.RECIPE_IV)
            .metadata(COIL_HEAT, 8300)
            .addTo(BFR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                MaterialPool.PitchblendeSlag.get(OrePrefixes.dust, 12),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.SodiumCarbonate, 6))
            .itemOutputs(
                MaterialPool.UraniumSlag.get(OrePrefixes.dust, 12),
                new ItemStack(ModItems.dustCalciumCarbonate, 1, 5),
                GTUtility.copyAmountUnsafe(7, WerkstoffLoader.Sodiumsulfate.get(OrePrefixes.dust, 1)))
            .duration(100)
            .eut(TierEU.RECIPE_HV)
            .metadata(COIL_HEAT, 3650)
            .addTo(BFR);

    }
}
