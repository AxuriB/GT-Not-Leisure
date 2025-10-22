package com.science.gtnl.mixins.late.Overpowered;

import java.util.OptionalDouble;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.api.IConfigurationMaintenance;
import com.science.gtnl.config.MainConfig;
import com.science.gtnl.utils.enums.ModList;
import com.science.gtnl.utils.recipes.ChanceBonusManager;

import gregtech.api.interfaces.tileentity.IVoidable;
import gregtech.api.metatileentity.implementations.MTEHatchMaintenance;
import gregtech.api.metatileentity.implementations.MTEMultiBlockBase;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ParallelHelper;

@Mixin(value = ParallelHelper.class, remap = false)
public abstract class MixinParallelHelper {

    // Original code from Overpowered Mod. MIT License. 2025/4/18

    @Shadow
    protected GTRecipe recipe;

    @Shadow
    protected double chanceMultiplier;

    @Shadow
    protected IVoidable machine;

    /**
     * Inject at the start of determineParallel to apply voltage-based chance bonus.
     */
    @Inject(method = "determineParallel", at = @At("HEAD"), remap = false)
    private void opDetermineParallel(CallbackInfo ci) {
        if (machine instanceof MTEMultiBlockBase multiBlockBase) {
            for (MTEHatchMaintenance maintenance : multiBlockBase.mMaintenanceHatches) {
                if (maintenance instanceof IConfigurationMaintenance customMaintenance
                    && customMaintenance.isConfiguration()) {
                    recipe.mDuration = recipe.mDuration * customMaintenance.getConfigTime() / 100;
                }
            }
        }

        if (!ModList.Overpowered.isModLoaded() && MainConfig.enableRecipeOutputChance) {
            // Compute optional bonus based on machine and current EU/t
            OptionalDouble bonusOptional = ChanceBonusManager
                .getChanceBonusOptional(machine, GTUtility.getTier(recipe.mEUt), chanceMultiplier, recipe);
            // If present, apply bonus by copying the recipe with increased chances
            if (bonusOptional.isPresent()) {
                recipe = ChanceBonusManager.copyAndBonusChance(recipe, bonusOptional.getAsDouble());
            }
        }
    }
}
