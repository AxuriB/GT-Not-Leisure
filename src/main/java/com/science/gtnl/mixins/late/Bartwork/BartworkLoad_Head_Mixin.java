package com.science.gtnl.mixins.late.Bartwork;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.Utils.recipes.RecipeUtil;
import com.science.gtnl.Utils.recipes.RemoveRecipes;
import com.science.gtnl.config.MainConfig;
import com.science.gtnl.loader.RecipePool;

import bartworks.MainMod;
import gregtech.api.recipe.RecipeMaps;

@SuppressWarnings("UnusedMixin")
@Mixin(value = MainMod.class, remap = false)
public class BartworkLoad_Head_Mixin {

    private static boolean recipesDel;

    @Inject(method = "runOnPlayerJoined(ZZ)V", at = @At("HEAD"), remap = false)
    private static void onRunOnPlayerJoined(boolean classicMode, boolean disableExtraGasRecipes, CallbackInfo ci) {
        if (MainConfig.enableDeleteRecipe && !recipesDel) {
            RemoveRecipes.removeRecipes();
        }
        RecipeUtil.removeMatchingRecipes(RecipePool.ConvertToCircuitAssembler, RecipeMaps.circuitAssemblerRecipes);

        recipesDel = true;
    }
}
