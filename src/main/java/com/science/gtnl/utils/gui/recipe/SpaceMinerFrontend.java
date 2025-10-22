package com.science.gtnl.utils.gui.recipe;

import static gtnhintergalactic.recipe.IGRecipeMaps.SPACE_LOCATION;
import static gtnhintergalactic.recipe.IGRecipeMaps.SPACE_PROJECT;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.util.StatCollector;

import gregtech.api.recipe.BasicUIPropertiesBuilder;
import gregtech.api.recipe.NEIRecipePropertiesBuilder;
import gregtech.api.util.MethodsReturnNonnullByDefault;
import gregtech.common.misc.spaceprojects.SpaceProjectManager;
import gregtech.nei.RecipeDisplayInfo;
import gregtech.nei.formatter.INEISpecialInfoFormatter;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SpaceMinerFrontend extends GTNLLogoFrontend {

    public SpaceMinerFrontend(BasicUIPropertiesBuilder uiPropertiesBuilder,
        NEIRecipePropertiesBuilder neiPropertiesBuilder) {
        super(uiPropertiesBuilder, neiPropertiesBuilder.neiSpecialInfoFormatter(new SpecialValueFormatter()));
    }

    public static class SpecialValueFormatter implements INEISpecialInfoFormatter {

        @Override
        public List<String> format(RecipeDisplayInfo recipeInfo) {
            List<String> specialInfo = new ArrayList<>();
            specialInfo.add(StatCollector.translateToLocalFormatted("ig.nei.module", recipeInfo.recipe.mSpecialValue));

            String neededProject = recipeInfo.recipe.getMetadata(SPACE_PROJECT);
            String neededProjectLocation = recipeInfo.recipe.getMetadata(SPACE_LOCATION);
            if (neededProject != null && !neededProject.isEmpty()) {
                specialInfo.add(
                    String.format(
                        StatCollector.translateToLocal("ig.nei.spaceassembler.project"),
                        SpaceProjectManager.getProject(neededProject)
                            .getLocalizedName()));
                specialInfo.add(
                    String.format(
                        StatCollector.translateToLocal("ig.nei.spaceassembler.projectAt"),
                        neededProjectLocation == null || neededProjectLocation.isEmpty()
                            ? StatCollector.translateToLocal("ig.nei.spaceassembler.projectAnyLocation")
                            : StatCollector.translateToLocal(
                                SpaceProjectManager.getLocation(neededProjectLocation)
                                    .getUnlocalizedName())));
            }
            return specialInfo;
        }
    }

}
