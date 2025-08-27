package com.science.gtnl.common.recipe.AprilFool;

import static gregtech.api.enums.GTValues.RA;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.science.gtnl.api.IRecipePool;
import com.science.gtnl.loader.RecipePool;

import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;

public class SteamWoodcutterRecipes implements IRecipePool {

    public RecipeMap<?> SWR = RecipePool.SteamWoodcutterRecipes;

    @Override
    public void loadRecipes() {

        RA.stdBuilder()
            .itemInputs(new ItemStack(Blocks.cactus, 0))
            .itemOutputs(new ItemStack(Blocks.cactus, 32))
            .duration(200)
            .eut(TierEU.RECIPE_LV)
            .addTo(SWR);

        RA.stdBuilder()
            .itemInputs(new ItemStack(Items.reeds, 0))
            .itemOutputs(new ItemStack(Items.reeds, 32))
            .duration(200)
            .eut(TierEU.RECIPE_LV)
            .addTo(SWR);
    }
}
