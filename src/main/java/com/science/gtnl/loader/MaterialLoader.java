package com.science.gtnl.loader;

import static com.science.gtnl.Utils.CardboardBoxUtils.*;

import net.minecraft.init.Blocks;
import net.minecraftforge.oredict.OreDictionary;

import com.brandon3055.draconicevolution.common.ModBlocks;
import com.cleanroommc.bogosorter.BogoSortAPI;
import com.science.gtnl.Utils.gui.portableWorkbench.ContainerPortableAvaritiaddonsChest;
import com.science.gtnl.Utils.gui.portableWorkbench.ContainerPortableChest;
import com.science.gtnl.Utils.machine.GreenHouseManager.GreenHouseBucket;
import com.science.gtnl.Utils.text.LanguageLoader;
import com.science.gtnl.api.TickrateAPI;
import com.science.gtnl.common.entity.EntitySteamRocket;
import com.science.gtnl.common.item.items.MilledOre;
import com.science.gtnl.common.item.steamRocket.SchematicSteamRocket;
import com.science.gtnl.common.material.MaterialPool;
import com.science.gtnl.common.recipe.AprilFool.OreDictionary.SteamCarpenterOreRecipe;
import com.science.gtnl.common.recipe.GTNL.RocketAssemblerRecipes;
import com.science.gtnl.common.recipe.OreDictionary.LaserEngraverOreRecipes;
import com.science.gtnl.common.recipe.OreDictionary.PortalToAlfheimOreRecipes;
import com.science.gtnl.common.recipe.OreDictionary.WoodDistillationRecipes;
import com.science.gtnl.config.MainConfig;

import bartworks.API.WerkstoffAdderRegistry;
import gregtech.api.enums.Mods;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTModHandler;
import micdoodle8.mods.galacticraft.api.recipe.RocketFuels;
import micdoodle8.mods.galacticraft.api.recipe.SchematicRegistry;

public class MaterialLoader {

    public static void loadPreInit() {
        EffectLoader.registry();
        EntityLoader.registry();
        LanguageLoader.registry();
        if (Mods.BetterQuesting.isModLoaded() && MainConfig.enableDebugMode) {
            QuestLoader.registry();
        }

        BlockLoader.registry();
        BlockLoader.registerTreeBrickuoia();

        ItemLoader.registry();
        WerkstoffAdderRegistry.addWerkstoffAdder(new MaterialPool());

        loadOreDictionaryRecipes();

        if (Mods.InventoryBogoSorter.isModLoaded()) {
            BogoSortAPI.INSTANCE.addGenericCompat(ContainerPortableChest.class);
            BogoSortAPI.INSTANCE.addGenericCompat(ContainerPortableAvaritiaddonsChest.class);
        }
    }

    public static void loadInit() {
        MachineLoader.registerGlasses();
        WailaLoader.register();
        TickrateAPI.changeTickrate(MainConfig.defaultTickrate);
    }

    public static void loadPostInit() {
        MilledOre.registry();
        GreenHouseBucket.LoadEIGBuckets();
        MachineLoader.registry();
        AchievementsLoader.registry();

        if (Mods.StevesCarts2.isModLoaded() && Mods.Railcraft.isModLoaded()
            && Mods.IronTanks.isModLoaded()
            && Mods.GraviSuite.isModLoaded()) {
            SchematicRegistry.registerSchematicRecipe(new SchematicSteamRocket());
            RocketAssemblerRecipes.loadSteamRocketRecipe();
        }
        RocketFuels.addFuel(EntitySteamRocket.class, MaterialPool.CompressedSteam.getMolten(1));
    }

    public static void loadCompleteInit() {
        ScriptLoader.registry();

        if (Mods.Nutrition.isModLoaded()) {
            NutrientLoader.registry();
        }

        loadCardBoardBoxBlackList();

        if (MainConfig.enableStickItem) {
            RecipeLoader.loadVillageTrade();
        }
    }

    public static void loadOreDictionaryRecipes() {
        GTLog.out.println("GTNL: Register Ore Dictionary Recipe.");
        new WoodDistillationRecipes();
        new PortalToAlfheimOreRecipes();
        new LaserEngraverOreRecipes();
        new SteamCarpenterOreRecipe();
    }

    public static void loadCardBoardBoxBlackList() {
        addBoxBlacklist(Blocks.wooden_door, OreDictionary.WILDCARD_VALUE);
        addBoxBlacklist(Blocks.iron_door, OreDictionary.WILDCARD_VALUE);
        addBoxBlacklist(BlockLoader.cardboardBox, OreDictionary.WILDCARD_VALUE);
        addBoxBlacklist(ModBlocks.reactorCore, OreDictionary.WILDCARD_VALUE);
        addBoxBlacklist(ModBlocks.chaosCrystal, OreDictionary.WILDCARD_VALUE);
        addBoxBlacklist(GTModHandler.getModItem(Mods.IndustrialCraft2.ID, "blockGenerator", 1, 5));
        addBoxBlacklist(
            GTModHandler.getModItem(Mods.IndustrialCraft2.ID, "blockReactorChamber", 1, OreDictionary.WILDCARD_VALUE));
    }
}
