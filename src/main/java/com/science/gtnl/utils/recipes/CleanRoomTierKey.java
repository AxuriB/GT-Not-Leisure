package com.science.gtnl.utils.recipes;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.util.StatCollector;

import gregtech.api.recipe.RecipeMetadataKey;
import gregtech.api.util.MethodsReturnNonnullByDefault;
import gregtech.nei.RecipeDisplayInfo;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CleanRoomTierKey extends RecipeMetadataKey<Integer> {

    public static final CleanRoomTierKey INSTANCE = new CleanRoomTierKey();

    public CleanRoomTierKey() {
        super(Integer.class, "cleanroom_tier");
    }

    @Override
    public void drawInfo(RecipeDisplayInfo recipeInfo, @Nullable Object value) {
        int tier = cast(value, 1);
        switch (tier) {
            case 1 -> recipeInfo.drawText(StatCollector.translateToLocal("CleanroomTierKey.0"));
            case 2 -> recipeInfo.drawText(StatCollector.translateToLocal("CleanroomTierKey.1"));
            case 3 -> recipeInfo.drawText(StatCollector.translateToLocal("CleanroomTierKey.2"));
        }
    }
}
