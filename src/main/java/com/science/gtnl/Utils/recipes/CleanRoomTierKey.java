package com.science.gtnl.Utils.recipes;

import static gregtech.api.util.GTUtility.trans;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import gregtech.api.recipe.RecipeMetadataKey;
import gregtech.api.util.MethodsReturnNonnullByDefault;
import gregtech.nei.RecipeDisplayInfo;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CleanRoomTierKey extends RecipeMetadataKey<Integer> {

    public static final CleanRoomTierKey INSTANCE = new CleanRoomTierKey();

    private CleanRoomTierKey() {
        super(Integer.class, "cleanroom_tier");
    }

    @Override
    public void drawInfo(RecipeDisplayInfo recipeInfo, @Nullable Object value) {
        int tier = cast(value, 1);
        switch (tier) {
            case 1 -> recipeInfo.drawText(trans("709", "Requires Sterile Environment"));
            case 2 -> recipeInfo.drawText(trans("710", "Requires Absolute Cleaning Environment"));
        }
    }
}
