package com.science.gtnl.utils.recipes;

import static kubatech.api.Variables.numberFormat;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.util.StatCollector;

import gregtech.api.recipe.RecipeMetadataKey;
import gregtech.api.util.MethodsReturnNonnullByDefault;
import gregtech.nei.RecipeDisplayInfo;

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
        recipeInfo.drawText(
            StatCollector.translateToLocal("NEI.CactusWonderFakeRecipes.specialValue") + numberFormat.format(offer));
    }
}
