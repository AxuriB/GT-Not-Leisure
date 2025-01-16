package com.science.gtnl.common.recipe.GregTech;

import static com.science.gtnl.loader.IScriptLoader.missing;
import static gregtech.api.enums.Mods.DraconicEvolution;

import com.science.gtnl.Utils.recipes.RecipeBuilder;
import com.science.gtnl.common.materials.MaterialPool;
import com.science.gtnl.common.recipe.IRecipePool;

import gregtech.api.enums.Materials;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTUtility;
import gtPlusPlus.api.recipe.GTPPRecipeMaps;
import gtPlusPlus.core.item.chemistry.AgriculturalChem;
import gtPlusPlus.core.util.minecraft.FluidUtils;

public class CentrifugeRecipes implements IRecipePool {

    final RecipeMap<?> CNCR = GTPPRecipeMaps.centrifugeNonCellRecipes;

    @Override
    public void loadRecipes() {
        RecipeBuilder.builder()
            .fluidInputs(MaterialPool.NeutralisedRedMud.getFluidOrGas(2000))
            .fluidOutputs(
                FluidUtils.getFluidStack(AgriculturalChem.RedMud, 1000),
                MaterialPool.FerricReeChloride.getFluidOrGas(1000),
                Materials.Hydrogen.getGas(4000))
            .specialValue(0)
            .noOptimize()
            .duration(100)
            .eut(120)
            .addTo(CNCR);

        RecipeBuilder.builder()
            .fluidInputs(MaterialPool.FerricReeChloride.getFluidOrGas(2000))
            .fluidOutputs(
                MaterialPool.RareEarthChlorides.getFluidOrGas(1000),
                Materials.IronIIIChloride.getFluid(1000),
                Materials.Water.getFluid(3000))
            .specialValue(0)
            .noOptimize()
            .duration(320)
            .eut(480)
            .addTo(CNCR);

        RecipeBuilder.builder()
            .itemInputs(GTUtility.copyAmount(0, GTModHandler.getModItem(DraconicEvolution.ID, "magnet", 1, 1)), missing)
            .fluidInputs(MaterialPool.RareEarthChlorides.getFluidOrGas(2000))
            .fluidOutputs(
                MaterialPool.LaNdOxidesSolution.getFluidOrGas(250),
                MaterialPool.SmGdOxidesSolution.getFluidOrGas(250),
                MaterialPool.TbHoOxidesSolution.getFluidOrGas(250),
                MaterialPool.ErLuOxidesSolution.getFluidOrGas(250),
                Materials.HydrochloricAcid.getFluid(1000))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(480)
            .addTo(CNCR);
    }
}
