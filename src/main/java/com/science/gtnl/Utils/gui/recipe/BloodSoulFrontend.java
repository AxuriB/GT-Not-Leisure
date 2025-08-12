package com.science.gtnl.Utils.gui.recipe;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.util.StatCollector;

import gregtech.api.recipe.BasicUIPropertiesBuilder;
import gregtech.api.recipe.NEIRecipePropertiesBuilder;
import gregtech.api.util.MethodsReturnNonnullByDefault;
import gregtech.nei.RecipeDisplayInfo;
import gregtech.nei.formatter.INEISpecialInfoFormatter;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BloodSoulFrontend extends GTNLLogoFrontend {

    public BloodSoulFrontend(BasicUIPropertiesBuilder uiPropertiesBuilder,
        NEIRecipePropertiesBuilder neiPropertiesBuilder) {
        super(uiPropertiesBuilder, neiPropertiesBuilder.neiSpecialInfoFormatter(new BloodSoulSpecialValueFormatter()));
    }

    public static class BloodSoulSpecialValueFormatter implements INEISpecialInfoFormatter {

        @Override
        public List<String> format(RecipeDisplayInfo recipeInfo) {
            List<String> specialInfo = new ArrayList<>();
            if (recipeInfo.recipe.mSpecialValue > 0) {
                specialInfo.add(
                    String.format(
                        StatCollector.translateToLocal("NEI.BloodSoulSacrificialArray.specialValue"),
                        recipeInfo.recipe.mSpecialValue));
            }
            return specialInfo;
        }
    }

}
