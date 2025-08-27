package com.science.gtnl.loader;

import com.cleanroommc.bogosorter.BogoSortAPI;
import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.Utils.gui.portableWorkbench.ContainerPortableAvaritiaddonsChest;
import com.science.gtnl.Utils.gui.portableWorkbench.ContainerPortableChest;
import com.science.gtnl.Utils.machine.EdenGardenManager.EIGBucket;
import com.science.gtnl.Utils.text.LanguageLoader;
import com.science.gtnl.api.TickrateAPI;
import com.science.gtnl.common.entity.EntitySteamRocket;
import com.science.gtnl.common.item.items.MilledOre;
import com.science.gtnl.common.material.MaterialPool;
import com.science.gtnl.common.recipe.AprilFool.OreDictionary.SteamCarpenterOreRecipe;
import com.science.gtnl.common.recipe.OreDictionary.LaserEngraverOreRecipes;
import com.science.gtnl.common.recipe.OreDictionary.PortalToAlfheimOreRecipes;
import com.science.gtnl.common.recipe.OreDictionary.WoodDistillationRecipes;
import com.science.gtnl.config.MainConfig;

import bartworks.API.WerkstoffAdderRegistry;
import bartworks.system.material.Werkstoff;
import codechicken.nei.api.API;
import gregtech.api.enums.Mods;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GTLog;
import micdoodle8.mods.galacticraft.api.recipe.RocketFuels;

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

        API.hideItem(GTNLItemList.EternalGregTechWorkshopRender.get(1));
        API.hideItem(GTNLItemList.NanoPhagocytosisPlantRender.get(1));
        API.hideItem(GTNLItemList.ArtificialStarRender.get(1));
        API.hideItem(GTNLItemList.TwilightSword.get(1));
        API.hideItem(GTNLItemList.FakeItemSiren.get(1));

        if (Mods.InventoryBogoSorter.isModLoaded()) {
            BogoSortAPI.INSTANCE.addGenericCompat(ContainerPortableChest.class);
            BogoSortAPI.INSTANCE.addGenericCompat(ContainerPortableAvaritiaddonsChest.class);
        }
    }

    public static void loadInit() {
        MachineLoader.registerGlasses();
        TickrateAPI.changeTickrate(MainConfig.defaultTickrate);
    }

    public static void loadPostInit() {
        MilledOre.registry();
        EIGBucket.LoadEIGBuckets();
        MachineLoader.registry();

        RocketFuels.addFuel(EntitySteamRocket.class, MaterialPool.CompressedSteam.getMolten(1));
    }

    public static void loadCompleteInit() {
        ScriptLoader.registry();

        Werkstoff[] hiddenMaterials = { MaterialPool.Polyimide, MaterialPool.AcrylonitrileButadieneStyrene,
            MaterialPool.Polyetheretherketone, MaterialPool.HSLASteel, MaterialPool.Actinium,
            MaterialPool.Rutherfordium, MaterialPool.Dubnium, MaterialPool.Seaborgium, MaterialPool.Technetium,
            MaterialPool.Bohrium, MaterialPool.Hassium, MaterialPool.Meitnerium, MaterialPool.Darmstadtium,
            MaterialPool.Roentgenium, MaterialPool.Copernicium, MaterialPool.Moscovium, MaterialPool.Livermorium,
            MaterialPool.Astatine, MaterialPool.Tennessine, MaterialPool.Francium, MaterialPool.Berkelium,
            MaterialPool.Einsteinium, MaterialPool.Mendelevium, MaterialPool.Nobelium, MaterialPool.Lawrencium,
            MaterialPool.Nihonium, MaterialPool.CompressedSteam, MaterialPool.Breel, MaterialPool.Stronze,
            MaterialPool.Periodicium, MaterialPool.Stargate };

        OrePrefixes[] orePrefixes = { OrePrefixes.ingotHot, OrePrefixes.toolHeadSaw, OrePrefixes.toolHeadWrench,
            OrePrefixes.toolHeadHammer };

        for (OrePrefixes ore : orePrefixes) {
            for (Werkstoff mat : hiddenMaterials) {
                API.hideItem(mat.get(ore));
            }
        }

        if (Mods.Nutrition.isModLoaded()) {
            NutrientLoader.registry();
        }
    }

    public static void loadOreDictionaryRecipes() {
        GTLog.out.println("GTNL: Register Ore Dictionary Recipe.");
        new WoodDistillationRecipes();
        new PortalToAlfheimOreRecipes();
        new LaserEngraverOreRecipes();
        new SteamCarpenterOreRecipe();
    }
}
