package com.science.gtnl.Utils.gui.recipe;

import javax.annotation.ParametersAreNonnullByDefault;

import com.science.gtnl.Utils.recipes.BloodSoulSpecialValue;

import gregtech.api.recipe.BasicUIPropertiesBuilder;
import gregtech.api.recipe.NEIRecipePropertiesBuilder;
import gregtech.api.util.MethodsReturnNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BloodSoulFrontend extends GTNLLogoFrontend {

    public BloodSoulFrontend(BasicUIPropertiesBuilder uiPropertiesBuilder,
        NEIRecipePropertiesBuilder neiPropertiesBuilder) {
        super(uiPropertiesBuilder, neiPropertiesBuilder.neiSpecialInfoFormatter(new BloodSoulSpecialValue()));
    }

}
