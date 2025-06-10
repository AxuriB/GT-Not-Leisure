package com.science.gtnl;

import static com.science.gtnl.ScienceNotLeisure.network;

import net.minecraftforge.common.MinecraftForge;

import com.science.gtnl.Utils.SubscribeEventUtils;
import com.science.gtnl.Utils.text.PlayerDollWaila;
import com.science.gtnl.common.machine.hatch.SuperCraftingInputHatchME;
import com.science.gtnl.common.packet.ConfigSyncPacket;
import com.science.gtnl.common.packet.GetTileEntityNBTRequestPacket;
import com.science.gtnl.common.packet.SoundPacket;
import com.science.gtnl.common.packet.TickratePacket;
import com.science.gtnl.common.packet.TileEntityNBTPacket;
import com.science.gtnl.common.packet.TitlePacket;
import com.science.gtnl.common.recipe.GTNL.ExtremeExtremeEntityCrusherRecipes;

import appeng.api.AEApi;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import gregtech.api.enums.Mods;

public class CommonProxy {

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new SubscribeEventUtils());
        if (Mods.MobsInfo.isModLoaded()) {
            MinecraftForge.EVENT_BUS.register(new ExtremeExtremeEntityCrusherRecipes());
        }
        FMLCommonHandler.instance()
            .bus()
            .register(new SubscribeEventUtils());

        network.registerMessage(TitlePacket.Handler.class, TitlePacket.class, 0, Side.CLIENT);
        network.registerMessage(TickratePacket.Handler.class, TickratePacket.class, 1, Side.CLIENT);
        network.registerMessage(ConfigSyncPacket.Handler.class, ConfigSyncPacket.class, 2, Side.CLIENT);
        network.registerMessage(
            GetTileEntityNBTRequestPacket.Handler.class,
            GetTileEntityNBTRequestPacket.class,
            3,
            Side.SERVER);
        network.registerMessage(TileEntityNBTPacket.Handler.class, TileEntityNBTPacket.class, 4, Side.CLIENT);
        network.registerMessage(SoundPacket.Handler.class, SoundPacket.class, 5, Side.CLIENT);
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        PlayerDollWaila.init();
    }

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
        AEApi.instance()
            .registries()
            .interfaceTerminal()
            .register(SuperCraftingInputHatchME.class);
    }
}
