package com.science.gtnl.asm;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.science.gtnl.api.TickrateAPI;
import com.science.gtnl.mixins.EarlyMixinLoader;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.github.tox1cozz.mixinbooterlegacy.IEarlyMixinLoader;

@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.TransformerExclusions({ "com.science.gtnl.asm" })
@IFMLLoadingPlugin.Name("GTNL core plugin")
public class GTNLEarlyCoreMod implements IFMLLoadingPlugin, IEarlyMixinLoader, IFMLCallHook {

    public static GTNLEarlyCoreMod INSTANCE;
    public static Logger LOGGER = LogManager.getLogger("GTNL Asm Core Mod");
    public static File CONFIG_FILE = null;
    public static final String GAME_RULE = "tickrate";

    // Stored client-side tickrate
    public static float TICKS_PER_SECOND = 20;
    // Server-side tickrate in miliseconds
    public static long MILISECONDS_PER_TICK = 50L;

    public GTNLEarlyCoreMod() {
        INSTANCE = this;
    }

    static {
        try {
            if (System.getProperty("java.version")
                .startsWith("1.8")) {
                LOGGER.info("Patching ObfuscationRun.theConstructor for Java 8 compatibility...");
                ObfuscationRunPatcher.patchConstructor();
            } else {
                LOGGER.warn("Skipping ObfuscationRun patch, not running Java 8.");
            }
        } catch (Throwable t) {
            LOGGER.error("Failed to patch ObfuscationRun", t);
        }
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { "com.science.gtnl.asm.TickrateTransformer" };
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return "com.science.gtnl.asm.GTNLEarlyCoreMod";
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public Void call() throws Exception {
        return null;
    }

    private Field clientTimer = null;

    @SideOnly(Side.CLIENT)
    public void updateClientTickrate(float tickrate, boolean log) {
        if (!TickrateAPI.isValidTickrate(tickrate)) {
            GTNLEarlyCoreMod.LOGGER.info("Ignoring invalid tickrate: {}", tickrate);
            return;
        }
        if (log) LOGGER.info("Updating client tickrate to {}", tickrate);
        TICKS_PER_SECOND = tickrate;
        Minecraft mc = Minecraft.getMinecraft();
        if (mc == null) return; // Oops!
        try {
            if (clientTimer == null) {
                GTNLEarlyCoreMod.LOGGER.info("Creating reflection instances...");
                for (Field f : mc.getClass()
                    .getDeclaredFields()) {
                    if (f.getType() == Timer.class) {
                        clientTimer = f;
                        clientTimer.setAccessible(true);
                        break;
                    }
                }
            }
            clientTimer.set(mc, new Timer(TICKS_PER_SECOND));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateServerTickrate(float tickrate, boolean log) {
        if (!TickrateAPI.isValidTickrate(tickrate)) {
            GTNLEarlyCoreMod.LOGGER.info("Ignoring invalid tickrate: {}", tickrate);
            return;
        }
        if (log) LOGGER.info("Updating server tickrate to {}", tickrate);
        MILISECONDS_PER_TICK = (long) (1000L / tickrate);
    }

    @Override
    public List<String> getMixinConfigs() {
        return EarlyMixinLoader.getMixinConfigs();
    }

    @Override
    public boolean shouldMixinConfigQueue(final String mixinConfig) {
        return EarlyMixinLoader.shouldMixinConfigQueue(mixinConfig);
    }
}
