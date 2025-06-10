package com.science.gtnl.Utils.machine.EdenGardenManager;

import net.minecraft.launchwrapper.Launch;

import cpw.mods.fml.common.FMLCommonHandler;

public class ModUtils {

    public static final boolean isDeobfuscatedEnvironment = (boolean) Launch.blackboard
        .get("fml.deobfuscatedEnvironment");
    public static boolean isClientSided = false;

    public static boolean isClientThreaded() {
        return FMLCommonHandler.instance()
            .getEffectiveSide()
            .isClient();
    }
}
