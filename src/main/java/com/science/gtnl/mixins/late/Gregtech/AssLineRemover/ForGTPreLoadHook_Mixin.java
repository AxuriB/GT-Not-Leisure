package com.science.gtnl.mixins.late.Gregtech.AssLineRemover;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.Utils.recipes.AssLineRecipeHook;
import com.science.gtnl.config.MainConfig;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import gregtech.GTMod;

@Mixin(value = GTMod.class, remap = false)
public class ForGTPreLoadHook_Mixin {

    @Inject(method = "onPreInitialization", at = @At("HEAD"))
    public void science$loadHook(FMLPreInitializationEvent aEvent, CallbackInfo ci) {
        if (MainConfig.enableDeleteRecipe) {
            AssLineRecipeHook.loadAndInit();
        }
    }
}
