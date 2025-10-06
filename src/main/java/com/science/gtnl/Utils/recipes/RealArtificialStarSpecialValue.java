package com.science.gtnl.Utils.recipes;

import static com.science.gtnl.Utils.text.TextUtils.texter;

import java.util.ArrayList;
import java.util.List;

import gregtech.nei.RecipeDisplayInfo;
import gregtech.nei.formatter.INEISpecialInfoFormatter;

public class RealArtificialStarSpecialValue implements INEISpecialInfoFormatter {

    public static final RealArtificialStarSpecialValue INSTANCE = new RealArtificialStarSpecialValue();

    @Override
    public List<String> format(RecipeDisplayInfo recipeInfo) {
        List<String> msgs = new ArrayList<>();
        msgs.add(
            texter("Generate : ", "NEI.RealArtificialStarGeneratingRecipes.specialValue")
                + recipeInfo.recipe.mSpecialValue
                + " × 2,147,483,647 EU");
        return msgs;
    }
}
