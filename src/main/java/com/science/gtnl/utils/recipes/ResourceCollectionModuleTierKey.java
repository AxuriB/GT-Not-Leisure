package com.science.gtnl.utils.recipes;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.util.StatCollector;

import gregtech.api.recipe.RecipeMetadataKey;
import gregtech.api.util.MethodsReturnNonnullByDefault;
import gregtech.nei.RecipeDisplayInfo;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ResourceCollectionModuleTierKey extends RecipeMetadataKey<Integer> {

    public static final ResourceCollectionModuleTierKey INSTANCE = new ResourceCollectionModuleTierKey();

    private ResourceCollectionModuleTierKey() {
        super(Integer.class, "resourcecollectionmoduletierkey_tier");
    }

    @Override
    public void drawInfo(RecipeDisplayInfo recipeInfo, @Nullable Object value) {
        int tier = cast(value, 1);
        switch (tier) {
            case 1 -> recipeInfo.drawText(StatCollector.translateToLocal("ResourceCollectionModuleTierKey.0"));
            case 2 -> recipeInfo.drawText(StatCollector.translateToLocal("ResourceCollectionModuleTierKey.1"));
            case 3 -> recipeInfo.drawText(StatCollector.translateToLocal("ResourceCollectionModuleTierKey.2"));
            case 4 -> recipeInfo.drawText(StatCollector.translateToLocal("ResourceCollectionModuleTierKey.3"));
            case 5 -> recipeInfo.drawText(StatCollector.translateToLocal("ResourceCollectionModuleTierKey.4"));
            case 6 -> recipeInfo.drawText(StatCollector.translateToLocal("ResourceCollectionModuleTierKey.5"));
        }
    }

}
