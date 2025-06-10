package com.science.gtnl.common.recipe.GTNL;

import net.minecraft.item.ItemStack;

import com.science.gtnl.api.IRecipePool;
import com.science.gtnl.common.material.MaterialPool;
import com.science.gtnl.loader.RecipePool;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTUtility;
import gtneioreplugin.plugin.block.ModBlocks;

public class GasCollectorRecipes implements IRecipePool {

    final RecipeMap<?> GCR = RecipePool.GasCollectorRecipes;

    @Override
    public void loadRecipes() {

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(1))
            .fluidOutputs(Materials.Air.getGas(10000))
            .duration(200)
            .eut(16)
            .addTo(GCR);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(2), new ItemStack(ModBlocks.getBlock("ED"), 0))
            .fluidOutputs(MaterialPool.EnderAir.getFluidOrGas(10000))
            .duration(100)
            .eut(16)
            .addTo(GCR);
    }
}
