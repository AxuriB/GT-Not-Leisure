package com.science.gtnl.common.recipe;

import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.common.recipe.Special.RealArtificialStar_SpecialValueFormatter;

import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMapBackend;
import gregtech.api.recipe.RecipeMapBuilder;

public class RecipeRegister {

    public static final RecipeMap<GTNLRecipeMapBackend> RecombinationFusionReactorRecipes = RecipeMapBuilder
        .of("gtnl.recipe.RecombinationFusionReactorRecipes", GTNLRecipeMapBackend::new)
        .maxIO(16, 16, 16, 16)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .frontend(GeneralFrontend::new)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.GenerationEarthEngine.get(1))
                .setMaxRecipesPerPage(1))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> BloodSoulTradingRecipes = RecipeMapBuilder
        .of("gtnl.recipe.BloodSoulTradingRecipes")
        .maxIO(4, 0, 1, 1)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.BloodSoulSacrificialArray.get(1))
                .setMaxRecipesPerPage(2))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> BloodDemonInjectionRecipes = RecipeMapBuilder
        .of("gtnl.recipe.BloodDemonInjectionRecipes")
        .maxIO(4, 1, 1, 1)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.BloodSoulSacrificialArray.get(1))
                .setMaxRecipesPerPage(2))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> AlchemicChemistrySetRecipes = RecipeMapBuilder
        .of("gtnl.recipe.AlchemicChemistrySetRecipes")
        .maxIO(5, 1, 1, 1)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.BloodSoulSacrificialArray.get(1))
                .setMaxRecipesPerPage(2))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> RealArtificialStarRecipes = RecipeMapBuilder
        .of("gtnl.recipe.ArtificialStarGeneratingRecipes")
        .maxIO(1, 1, 0, 0)
        .neiSpecialInfoFormatter(RealArtificialStar_SpecialValueFormatter.INSTANCE)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(builder -> builder.setDisplayStack(GTNLItemList.RealArtificialStar.get(1)))
        .disableOptimize()
        .build();

    public static final RecipeMap<GTNLRecipeMapBackend> PortalToAlfheimRecipes = RecipeMapBuilder
        .of("gtnl.recipe.PortalToAlfheimRecipes", GTNLRecipeMapBackend::new)
        .maxIO(4, 36, 1, 0)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .frontend(GeneralFrontend::new)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.TeleportationArrayToAlfheim.get(1))
                .setMaxRecipesPerPage(1))
        .disableOptimize()
        .build();

    public static final RecipeMap<GTNLRecipeMapBackend> RuneAltarRecipes = RecipeMapBuilder
        .of("gtnl.recipe.RuneAltarRecipes", GTNLRecipeMapBackend::new)
        .maxIO(8, 1, 1, 0)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .frontend(GeneralFrontend::new)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.TeleportationArrayToAlfheim.get(1))
                .setMaxRecipesPerPage(1))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> NatureSpiritArrayRecipes = RecipeMapBuilder
        .of("gtnl.recipe.NatureSpiritArrayRecipes")
        .maxIO(1, 0, 0, 1)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(builder -> builder.setDisplayStack(GTNLItemList.TeleportationArrayToAlfheim.get(1)))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> ManaInfusionRecipes = RecipeMapBuilder
        .of("gtnl.recipe.ManaInfusionRecipes")
        .maxIO(4, 1, 1, 0)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(builder -> builder.setDisplayStack(GTNLItemList.TeleportationArrayToAlfheim.get(1)))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> LapotronChipRecipes = RecipeMapBuilder
        .of("gtnl.recipe.LapotronChipRecipes")
        .maxIO(9, 9, 3, 3)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(builder -> builder.setDisplayStack(GTNLItemList.LapotronChip.get(1)))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> SteamCrackerRecipes = RecipeMapBuilder
        .of("gtnl.recipe.SteamCrackerRecipes")
        .maxIO(1, 0, 1, 1)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(builder -> builder.setDisplayStack(GTNLItemList.SteamCracking.get(1)))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> CheatOreProcessingRecipes = RecipeMapBuilder
        .of("gtnl.recipe.CheatOreProcessingRecipes")
        .maxIO(1, 9, 1, 0)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(builder -> builder.setDisplayStack(GTNLItemList.CheatOreProcessingFactory.get(1)))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> DesulfurizerRecipes = RecipeMapBuilder
        .of("gtnl.recipe.DesulfurizerRecipes")
        .maxIO(0, 1, 1, 1)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(builder -> builder.setDisplayStack(GTNLItemList.Desulfurizer.get(1)))
        .disableOptimize()
        .build();

    public static final RecipeMap<GTNLRecipeMapBackend> PetrochemicalPlantRecipes = RecipeMapBuilder
        .of("gtnl.recipe.PetrochemicalPlantRecipes", GTNLRecipeMapBackend::new)
        .maxIO(4, 4, 4, 12)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .frontend(GeneralFrontend::new)
        .neiHandlerInfo(builder -> builder.setDisplayStack(GTNLItemList.PetrochemicalPlant.get(1)))
        .disableOptimize()
        .build();

    public static final RecipeMap<GTNLRecipeMapBackend> SmeltingMixingFurnaceRecipes = RecipeMapBuilder
        .of("gtnl.recipe.SmeltingMixingFurnaceRecipes", GTNLRecipeMapBackend::new)
        .maxIO(8, 4, 16, 4)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .frontend(GeneralFrontend::new)
        .neiHandlerInfo(builder -> builder.setDisplayStack(GTNLItemList.SmeltingMixingFurnace.get(1)))
        .disableOptimize()
        .build();

    public static final RecipeMap<GTNLRecipeMapBackend> RareEarthCentrifugalRecipes = RecipeMapBuilder
        .of("gtnl.recipe.RareEarthCentrifugalRecipes", GTNLRecipeMapBackend::new)
        .maxIO(1, 17, 1, 1)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .frontend(GeneralFrontend::new)
        .neiHandlerInfo(builder -> builder.setDisplayStack(GTNLItemList.RareEarthCentrifugal.get(1)))
        .disableOptimize()
        .build();

    public static RecipeMap<RecipeMapBackend> IndustrialShapedArcaneCraftingRecipes = RecipeMapBuilder
        .of("gtnl.recipe.IndustrialShapedArcaneCraftingRecipes")
        .maxIO(9, 1, 0, 0)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.IndustrialArcaneAssembler.get(1))
                .setMaxRecipesPerPage(1))
        .disableOptimize()
        .build();

    public static RecipeMap<RecipeMapBackend> MatterFabricatorRecipes = RecipeMapBuilder
        .of("gtnl.recipe.MatterFabricatorRecipes")
        .maxIO(2, 1, 0, 1)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.MatterFabricator.get(1))
                .setMaxRecipesPerPage(1))
        .disableOptimize()
        .build();

    public static RecipeMap<RecipeMapBackend> TheTwilightForestRecipes = RecipeMapBuilder
        .of("gtnl.recipe.TheTwilightForestRecipes")
        .maxIO(3, 9, 3, 3)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.LibraryOfRuina.get(1))
                .setMaxRecipesPerPage(1))
        .disableOptimize()
        .build();

    public static RecipeMap<RecipeMapBackend> IndustrialInfusionCraftingRecipes = RecipeMapBuilder
        .of("gtnl.recipe.IndustrialInfusionCraftingRecipes")
        .maxIO(25, 1, 0, 0)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .frontend(IndustrialInfusionCraftingRecipesFrontend::new)
        .neiTransferRect(100, 45, 18, 72)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.IndustrialArcaneAssembler.get(1))
                .setMaxRecipesPerPage(1))
        .disableOptimize()
        .build();
}
