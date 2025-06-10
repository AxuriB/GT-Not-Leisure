package com.science.gtnl.common.recipe.GTNL;

import static com.science.gtnl.Utils.enums.GTNLItemList.TrollFace;
import static com.science.gtnl.Utils.text.TextUtils.texter;
import static gregtech.api.util.GTModHandler.getModItem;

import com.science.gtnl.api.IRecipePool;
import com.science.gtnl.loader.RecipePool;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTUtility;

public class ReFusionReactorRecipes implements IRecipePool {

    final RecipeMap<?> RFRR = RecipePool.RecombinationFusionReactorRecipes;

    @Override
    public void loadRecipes() {
        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.copyAmountUnsafe(2147483647, getModItem("gregtech", "gt.metaitem.01", 1, 2299)))
            .itemOutputs(
                TrollFace.get(1)
                    .setStackDisplayName(texter("It's just out of reach, isn't it?", "RFRRRecipes.1")))
            .fluidOutputs(
                MaterialsUEVplus.MagMatter.getMolten(Integer.MAX_VALUE),
                MaterialsUEVplus.MagnetohydrodynamicallyConstrainedStarMatter.getMolten(Integer.MAX_VALUE),
                MaterialsUEVplus.Universium.getMolten(Integer.MAX_VALUE),
                MaterialsUEVplus.WhiteDwarfMatter.getMolten(Integer.MAX_VALUE),
                MaterialsUEVplus.BlackDwarfMatter.getMolten(Integer.MAX_VALUE),
                MaterialsUEVplus.SpaceTime.getMolten(Integer.MAX_VALUE),
                MaterialsUEVplus.TranscendentMetal.getMolten(Integer.MAX_VALUE),
                MaterialsUEVplus.Eternity.getMolten(Integer.MAX_VALUE),
                MaterialsUEVplus.PrimordialMatter.getMolten(Integer.MAX_VALUE),
                MaterialsUEVplus.Space.getFluid(Integer.MAX_VALUE),
                MaterialsUEVplus.Time.getFluid(Integer.MAX_VALUE),
                MaterialsUEVplus.SixPhasedCopper.getMolten(Integer.MAX_VALUE),
                MaterialsUEVplus.StargateCrystalSlurry.getFluid(Integer.MAX_VALUE),
                Materials.Antimatter.getFluid(Integer.MAX_VALUE))
            .outputChances(1)
            .duration(1)
            .eut(1)
            .addTo(RFRR);
    }
}
