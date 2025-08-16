package com.science.gtnl;

import static com.science.gtnl.ScienceNotLeisure.network;

import com.science.gtnl.client.GTNLInputHandler;
import com.science.gtnl.common.packet.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import com.science.gtnl.Utils.SubscribeEventUtils;
import com.science.gtnl.Utils.detrav.DetravScannerGUI;
import com.science.gtnl.Utils.machine.VMTweakHelper;
import com.science.gtnl.Utils.text.PlayerDollWaila;
import com.science.gtnl.common.machine.hatch.SuperCraftingInputHatchME;
import com.science.gtnl.common.recipe.GTNL.ExtremeExtremeEntityCrusherRecipes;
import com.science.gtnl.config.MainConfig;

import appeng.api.AEApi;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import gregtech.api.enums.Mods;

public class CommonProxy implements IGuiHandler {

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

        MinecraftForge.EVENT_BUS.register(GTNLInputHandler.INSTANCE);

        int i = 0;
        network.registerMessage(TitlePacket.Handler.class, TitlePacket.class, i++, Side.CLIENT);
        network.registerMessage(TickratePacket.Handler.class, TickratePacket.class, i++, Side.CLIENT);
        network.registerMessage(ConfigSyncPacket.Handler.class, ConfigSyncPacket.class, i++, Side.CLIENT);
        network.registerMessage(TileEntityNBTPacket.Handler.class, TileEntityNBTPacket.class, i++, Side.CLIENT);
        network.registerMessage(SoundPacket.Handler.class, SoundPacket.class, i++, Side.CLIENT);
        network.registerMessage(SyncHPCAVariablesPacket.Handler.class, SyncHPCAVariablesPacket.class, 6, Side.CLIENT);
        network.registerMessage(ProspectingPacket.Handler.class, ProspectingPacket.class, i++, Side.CLIENT);

        i = 64;
        network.registerMessage(
            GetTileEntityNBTRequestPacket.Handler.class,
            GetTileEntityNBTRequestPacket.class,
            i++,
            Side.SERVER);
        network.registerMessage(TeleportRequestPacket.Handler.class, TeleportRequestPacket.class, 7, Side.SERVER);
        network.registerMessage(KeyBindingHandler.Handler.class, KeyBindingHandler.class,i++,Side.SERVER);
        network.registerMessage(WirelessPickBlock.Handler.class, WirelessPickBlock.class,i++,Side.SERVER);
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        PlayerDollWaila.init();
        if (!com.science.gtnl.Utils.enums.Mods.VMTweak.isModLoaded() && MainConfig.enableVoidMinerTweak) {
            MinecraftForge.EVENT_BUS.register(new VMTweakHelper());
            FMLCommonHandler.instance()
                .bus()
                .register(new VMTweakHelper());
        }
    }

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
        AEApi.instance()
            .registries()
            .interfaceTerminal()
            .register(SuperCraftingInputHatchME.class);

        // AltarStructure.registerAltarStructureInfo();
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == DetravScannerGUI.GUI_ID) {
            return new DetravScannerGUI();
        }
        return null;
    }

    public void openProspectorGUI() {
        // just Client code
    }
}
