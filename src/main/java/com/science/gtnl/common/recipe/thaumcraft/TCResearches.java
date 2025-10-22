package com.science.gtnl.common.recipe.thaumcraft;

import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;
import static com.science.gtnl.common.recipe.thaumcraft.TCRecipePool.infusionRecipeTimeStopPocketWatch;

import net.minecraft.util.ResourceLocation;

import com.science.gtnl.utils.enums.GTNLItemList;

import gregtech.api.enums.Mods;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

public class TCResearches {

    public static void register() {
        loadResearchTab();
        loadResearches();
    }

    public static void loadResearchTab() {
        ResearchCategories.registerCategory(
            "gtnl",
            new ResourceLocation(RESOURCE_ROOT_ID, "textures/items/TestItem.png"),
            new ResourceLocation("thaumicinsurgence", "textures/gui/eldritch_bg.png"));
    }

    public static void loadResearches() {
        new ResearchItem("gtnl.welcome", "gtnl", new AspectList(), 0, 0, 0, GTNLItemList.TestItem.get(1))
            .setAutoUnlock()
            .registerResearchItem()
            .setPages(new ResearchPage("tc.research_text.gtnl.welcome.1"))
            .setSpecial();

        new ResearchItem(
            "gtnl.timeStopPocketWatch",
            "gtnl",
            new AspectList().merge(Mods.MagicBees.isModLoaded() ? Aspect.getAspect("tempus") : Aspect.MAGIC, 2)
                .merge(Aspect.ORDER, 1)
                .merge(Aspect.MAGIC, 1)
                .merge(Aspect.TRAVEL, 1)
                .merge(Aspect.ENERGY, 1)
                .merge(Aspect.VOID, 1),
            -1,
            -2,
            5,
            GTNLItemList.TimeStopPocketWatch.get(1))
                .setPages(
                    new ResearchPage("tc.research_text.gtnl.timeStopPocketWatch.1"),
                    new ResearchPage(infusionRecipeTimeStopPocketWatch))
                .setParents("gtnl.welcome")
                .registerResearchItem();
    }
}
