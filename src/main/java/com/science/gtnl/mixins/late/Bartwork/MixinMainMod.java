package com.science.gtnl.mixins.late.Bartwork;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.config.MainConfig;
import com.science.gtnl.loader.RecipePool;
import com.science.gtnl.utils.recipes.RecipeUtil;
import com.science.gtnl.utils.recipes.RemoveRecipes;

import bartworks.MainMod;
import gregtech.api.recipe.RecipeMaps;

@Mixin(value = MainMod.class, remap = false)
public class MixinMainMod {

    @Unique
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
