package com.science.gtnl.common.recipe.GTNL;

import static gregtech.api.util.GTRecipeBuilder.TICKS;

import net.minecraftforge.fluids.FluidRegistry;

import com.science.gtnl.Utils.recipes.SteamFusionTierKey;
import com.science.gtnl.api.IRecipePool;
import com.science.gtnl.loader.RecipePool;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.recipe.RecipeMap;
import gtPlusPlus.core.util.minecraft.FluidUtils;

public class SteamFusionReactorRecipes implements IRecipePool {

    final RecipeMap<?> SFRR = RecipePool.SteamFusionReactorRecipes;

    @Override
    public void loadRecipes() {

        GTValues.RA.stdBuilder()
            .fluidInputs(Materials.Steam.getGas(16000), Materials.Creosote.getFluid(4000))
            .fluidOutputs(FluidUtils.getSuperHeatedSteam(16000))
            .duration(10 * TICKS)
            .eut(0)
            .addTo(SFRR);

        GTValues.RA.stdBuilder()
            .fluidInputs(FluidUtils.getSuperHeatedSteam(16000), Materials.Creosote.getFluid(4000))
            .fluidOutputs(FluidRegistry.getFluidStack("supercriticalsteam", 16000))
            .duration(10 * TICKS)
            .eut(0)
            .addTo(SFRR);

        GTValues.RA.stdBuilder()
            .fluidInputs(Materials.Water.getFluid(100), Materials.Lava.getFluid(125))
            .fluidOutputs(FluidRegistry.getFluidStack("supercriticalsteam", 16000))
            .duration(10 * TICKS)
            .eut(0)
            .metadata(SteamFusionTierKey.INSTANCE, 1)
            .addTo(SFRR);
    }
}
