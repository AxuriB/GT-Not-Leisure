package galaxyspace.core.nei;

import com.science.gtnl.api.mixinHelper.IRocketRecipeIdProvider;

public class CustomRockRecipeHandler extends RocketRecipeHandler implements IRocketRecipeIdProvider {

    public final String recipeId;
    public final String handlerId;

    public CustomRockRecipeHandler(int tier, String textureSuffix, int y, int h, String recipeId, String handlerId) {
        super(tier, textureSuffix, y, h);
        this.recipeId = recipeId;
        this.handlerId = handlerId;
    }

    public CustomRockRecipeHandler(int tier, int y, int h, String recipeId, String handlerId) {
        super(tier, y, h);
        this.recipeId = recipeId;
        this.handlerId = handlerId;
    }

    @Override
    public String getCustomRecipeId() {
        return this.recipeId;
    }

    @Override
    public String getHandlerId() {
        return this.handlerId;
    }
}
