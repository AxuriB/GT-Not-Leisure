package com.science.gtnl.common.recipe;

import static gregtech.api.enums.Mods.Botania;

import net.minecraftforge.fluids.FluidRegistry;

import com.science.gtnl.Utils.recipes.RecipeBuilder;
import com.science.gtnl.common.RecipeRegister;

import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTUtility;

public class NatureSpiritArrayRecipes implements IRecipePool {

    final RecipeMap<?> NSAR = RecipeRegister.NatureSpiritArrayRecipes;

    @Override
    public void loadRecipes() {
        RecipeBuilder.builder()
            .itemInputs(GTModHandler.getModItem("Botania", "manaResource", 1, 0))
            .fluidOutputs(FluidRegistry.getFluidStack("fluidmana", 3300))
            .noOptimize()
            .duration(20)
            .eut(2048)
            .addTo(NSAR);

        RecipeBuilder.builder()
            .itemInputs(GTModHandler.getModItem("Botania", "manaResource", 1, 1))
            .fluidOutputs(FluidRegistry.getFluidStack("fluidmana", 6500))
            .noOptimize()
            .duration(20)
            .eut(2048)
            .addTo(NSAR);

        RecipeBuilder.builder()
            .itemInputs(GTModHandler.getModItem("Botania", "manaResource", 1, 2))
            .fluidOutputs(FluidRegistry.getFluidStack("fluidmana", 44000))
            .noOptimize()
            .duration(20)
            .eut(2048)
            .addTo(NSAR);

        RecipeBuilder.builder()
            .itemInputs(GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "pool", 1, 1)))
            .fluidOutputs(FluidRegistry.getFluidStack("fluidmana", 2147483647))
            .noOptimize()
            .duration(20)
            .eut(7864320)
            .addTo(NSAR);
    }
}
