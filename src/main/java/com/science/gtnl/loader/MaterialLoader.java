package com.science.gtnl.loader;

import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.Utils.enums.Mods;
import com.science.gtnl.common.entity.EntityArrowCustom;
import com.science.gtnl.common.entity.EntitySaddleSlime;
import com.science.gtnl.common.entity.EntitySteamRocket;
import com.science.gtnl.common.item.items.MilledOre;
import com.science.gtnl.common.material.MaterialPool;

import bartworks.API.WerkstoffAdderRegistry;
import codechicken.nei.api.API;
import cpw.mods.fml.common.registry.EntityRegistry;
import micdoodle8.mods.galacticraft.api.recipe.RocketFuels;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;

public class MaterialLoader {

    public static void loadPreInit() {

        EffectLoader.registry();

        BlockLoader.registry();
        BlockLoader.registerTreeBrickuoia();

        ItemLoader.registry();
        ItemLoader.registryOreDictionary();
        ItemLoader.registryOreBlackList();
        WerkstoffAdderRegistry.addWerkstoffAdder(new MaterialPool());

        API.hideItem(GTNLItemList.NanoPhagocytosisPlantRender.get(1));
        API.hideItem(GTNLItemList.ArtificialStarRender.get(1));
        API.hideItem(GTNLItemList.TwilightSword.get(1));

        registerEntity();
        RocketFuels.addFuel(EntitySteamRocket.class, "molten.compressedsteam");
    }

    public static void loadPostInit() {
        new MilledOre();
    }

    public static void registerEntity() {
        GCCoreUtil.registerGalacticraftNonMobEntity(EntitySteamRocket.class, "SteamRocket", 150, 1, false);
        EntityRegistry
            .registerModEntity(EntityArrowCustom.class, "ArrowCustom", 0, Mods.ScienceNotLeisure.ID, 64, 2, true);
        EntityRegistry
            .registerModEntity(EntitySaddleSlime.class, "SaddleSlime", 1, Mods.ScienceNotLeisure.ID, 64, 2, true);
    }
}
