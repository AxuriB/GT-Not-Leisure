package com.science.gtnl.Utils.recipes;

import static com.science.gtnl.Utils.text.TextUtils.texter;
import static kubatech.api.Variables.numberFormat;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import gregtech.api.recipe.RecipeMetadataKey;
import gregtech.api.util.MethodsReturnNonnullByDefault;
import gregtech.nei.RecipeDisplayInfo;

/**
 * Base success chance for Purification Plant recipes
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SteamAmountSpecialValue extends RecipeMetadataKey<Long> {

    public static final SteamAmountSpecialValue INSTANCE = new SteamAmountSpecialValue();

    private SteamAmountSpecialValue() {
        super(Long.class, "offer_value");
    }

    @Override
    public void drawInfo(RecipeDisplayInfo recipeInfo, @Nullable Object value) {
        long offer = cast(value, 0L);
        recipeInfo
            .drawText(texter("Offer Value: ", "NEI.CactusWonderFakeRecipes.specialValue") + numberFormat.format(offer));
    }
}
