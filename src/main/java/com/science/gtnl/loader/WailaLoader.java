package com.science.gtnl.loader;

import java.util.Arrays;
import java.util.List;

import net.minecraft.tileentity.TileEntity;

import com.github.bsideup.jabel.Desugar;
import com.science.gtnl.Utils.text.CardboardBoxWailaDataProvider;
import com.science.gtnl.Utils.text.PlayerDollWailaDataProvider;
import com.science.gtnl.common.block.blocks.tile.TileEntityCardboardBox;
import com.science.gtnl.common.block.blocks.tile.TileEntityPlayerDoll;

import cpw.mods.fml.common.event.FMLInterModComms;
import gregtech.api.enums.Mods;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

public class WailaLoader {

    public static void callbackRegister(IWailaRegistrar registrar) {
        List<ProviderEntry<?>> entries = Arrays.asList(
            new ProviderEntry<>(new PlayerDollWailaDataProvider(), TileEntityPlayerDoll.class),
            new ProviderEntry<>(new CardboardBoxWailaDataProvider(), TileEntityCardboardBox.class));

        for (ProviderEntry<?> entry : entries) {
            registrar.registerBodyProvider(entry.provider(), entry.clazz());
            registrar.registerNBTProvider(entry.provider(), entry.clazz());
            registrar.registerTailProvider(entry.provider(), entry.clazz());
        }
    }

    public static void register() {
        FMLInterModComms.sendMessage(Mods.Waila.ID, "register", WailaLoader.class.getName() + ".callbackRegister");
    }

    @Desugar
    public record ProviderEntry<T extends TileEntity> (IWailaDataProvider provider, Class<T> clazz) {}

}
