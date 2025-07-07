package com.science.gtnl;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import com.science.gtnl.Utils.SubscribeEventClientUtils;
import com.science.gtnl.common.block.blocks.ItemBlockEternalGregTechWorkshopRender;
import com.science.gtnl.common.block.blocks.ItemBlockNanoPhagocytosisPlantRender;
import com.science.gtnl.common.block.blocks.tile.TileEntityArtificialStar;
import com.science.gtnl.common.block.blocks.tile.TileEntityEternalGregTechWorkshop;
import com.science.gtnl.common.block.blocks.tile.TileEntityLaserBeacon;
import com.science.gtnl.common.block.blocks.tile.TileEntityNanoPhagocytosisPlant;
import com.science.gtnl.common.block.blocks.tile.TileEntityPlayerDoll;
import com.science.gtnl.common.entity.EntitySteamRocket;
import com.science.gtnl.common.render.entity.SteamRocketRender;
import com.science.gtnl.common.render.item.ItemBlockArtificialStarRender;
import com.science.gtnl.common.render.item.ItemMeteorMinerMachineRender;
import com.science.gtnl.common.render.item.ItemPlayerDollRenderer;
import com.science.gtnl.common.render.item.ItemSteamRocketRenderer;
import com.science.gtnl.common.render.item.ItemTwilightSwordRender;
import com.science.gtnl.common.render.tile.MeteorMinerRenderer;
import com.science.gtnl.common.render.tile.PlayerDollRenderer;
import com.science.gtnl.common.render.tile.RealArtificialStarRender;
import com.science.gtnl.common.render.tile.RenderEternalGregTechWorkshop;
import com.science.gtnl.common.render.tile.RenderNanoPhagocytosisPlant;
import com.science.gtnl.config.MainConfig;
import com.science.gtnl.loader.BlockLoader;
import com.science.gtnl.loader.ItemLoader;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import fox.spiteful.avaritia.render.FancyHaloRenderer;
import gregtech.api.GregTechAPI;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.client.model.ModelRocketTier1;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;

public class ClientProxy extends CommonProxy {

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaserBeacon.class, new MeteorMinerRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlayerDoll.class, new PlayerDollRenderer());
        MinecraftForgeClient
            .registerItemRenderer(Item.getItemFromBlock(BlockLoader.playerDoll), new ItemPlayerDollRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityArtificialStar.class, new RealArtificialStarRender());
        MinecraftForgeClient.registerItemRenderer(
            Item.getItemFromBlock(BlockLoader.blockArtificialStarRender),
            new ItemBlockArtificialStarRender());

        MinecraftForgeClient.registerItemRenderer(ItemLoader.twilightSword, new ItemTwilightSwordRender());

        MinecraftForgeClient.registerItemRenderer(
            Item.getItemFromBlock(BlockLoader.blockNanoPhagocytosisPlantRender),
            new ItemBlockNanoPhagocytosisPlantRender(BlockLoader.blockNanoPhagocytosisPlantRender));
        ClientRegistry
            .bindTileEntitySpecialRenderer(TileEntityNanoPhagocytosisPlant.class, new RenderNanoPhagocytosisPlant());

        MinecraftForgeClient.registerItemRenderer(
            Item.getItemFromBlock(BlockLoader.blockEternalGregTechWorkshopRender),
            new ItemBlockEternalGregTechWorkshopRender(BlockLoader.blockEternalGregTechWorkshopRender));
        ClientRegistry.bindTileEntitySpecialRenderer(
            TileEntityEternalGregTechWorkshop.class,
            new RenderEternalGregTechWorkshop());

        MinecraftForgeClient.registerItemRenderer(ItemLoader.testItem, new FancyHaloRenderer());
        MinecraftForgeClient.registerItemRenderer(ItemLoader.metaItem, new FancyHaloRenderer());

        if (MainConfig.enableDebugMode) {
            MinecraftForgeClient.registerItemRenderer(
                Item.getItemFromBlock(GregTechAPI.sBlockMachines),
                new ItemMeteorMinerMachineRender());
        }

        MinecraftForgeClient.registerItemRenderer(
            ItemLoader.steamRocket,
            new ItemSteamRocketRenderer(
                new EntitySteamRocket(ClientProxyCore.mc.theWorld),
                new ModelRocketTier1(),
                new ResourceLocation(GalacticraftCore.ASSET_PREFIX, "textures/model/rocketT1.png")));

        RenderingRegistry.registerEntityRenderingHandler(
            EntitySteamRocket.class,
            new SteamRocketRender(new ModelRocketTier1(), GalacticraftCore.ASSET_PREFIX, "rocketT1"));
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new SubscribeEventClientUtils());
        FMLCommonHandler.instance()
            .bus()
            .register(new SubscribeEventClientUtils());
        super.preInit(event);
    }

}
