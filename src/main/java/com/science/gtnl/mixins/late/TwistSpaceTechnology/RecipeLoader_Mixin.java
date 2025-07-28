package com.science.gtnl.mixins.late.TwistSpaceTechnology;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.Nxer.TwistSpaceTechnology.loader.RecipeLoader;

@Mixin(value = RecipeLoader.class, remap = false)
public abstract class RecipeLoader_Mixin {

    @Redirect(
        method = "loadRecipes",
        at = @At(
            value = "INVOKE",
            target = "Lcom/Nxer/TwistSpaceTechnology/recipe/machineRecipe/expanded/CircuitAssemblyLineWithoutImprintRecipePool;loadRecipes()V"))
    private static void redirectCircuitAssemblyLineWithoutImprintLoadRecipes() {
        System.out.println(
            "[GTNL] Detected TwistSpaceTechnology, intercept AdvCircuitAssemblyLine recipe loader to server start");
    }
}
