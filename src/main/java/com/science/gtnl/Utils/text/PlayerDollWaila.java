package com.science.gtnl.Utils.text;

import com.science.gtnl.common.block.blocks.tile.TileEntityPlayerDoll;

import cpw.mods.fml.common.event.FMLInterModComms;
import gregtech.api.enums.Mods;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

public final class PlayerDollWaila {

    private PlayerDollWaila() {}

    public static void callbackRegister(IWailaRegistrar registrar) {
        IWailaDataProvider provider = new PlayerDollWailaDataProvider();
        Class<TileEntityPlayerDoll> clazz = TileEntityPlayerDoll.class;

        registrar.registerBodyProvider(provider, clazz);
        registrar.registerNBTProvider(provider, clazz);
        registrar.registerTailProvider(provider, clazz);
    }

    public static void init() {
        FMLInterModComms.sendMessage(Mods.Waila.ID, "register", PlayerDollWaila.class.getName() + "#callbackRegister");
    }
}
