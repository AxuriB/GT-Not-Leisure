package com.science.gtnl.common.recipe.AprilFool;

import static gregtech.api.enums.GTValues.RA;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import com.science.gtnl.api.IRecipePool;
import com.science.gtnl.loader.RecipePool;

import gregtech.api.recipe.RecipeMap;
import gtPlusPlus.core.util.minecraft.FluidUtils;

public class LavaMakerRecipes implements IRecipePool {

    final RecipeMap<?> LMR = RecipePool.LavaMakerRecipes;

    @Override
    public void loadRecipes() {

        RA.stdBuilder()
            .itemInputs(new ItemStack(Blocks.stone, 1))
            .fluidOutputs(FluidUtils.getLava(1000))
            .duration(1 * SECONDS)
            .eut(16)
            .addTo(LMR);
    }
}
