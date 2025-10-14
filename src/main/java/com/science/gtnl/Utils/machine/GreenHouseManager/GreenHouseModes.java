package com.science.gtnl.Utils.machine.GreenHouseManager;

import java.util.HashMap;

import com.science.gtnl.Utils.machine.GreenHouseManager.modes.GreenHouseIC2Mode;
import com.science.gtnl.Utils.machine.GreenHouseManager.modes.GreenHouseNormalMode;

import gregtech.api.util.MultiblockTooltipBuilder;

public class GreenHouseModes {

    private static final HashMap<String, GreenHouseMode> modes = new HashMap<>();

    public static final GreenHouseMode Normal = addMode(GreenHouseNormalMode.instance);
    public static final GreenHouseMode IC2 = addMode(GreenHouseIC2Mode.instance);

    // this is basically a fake enum, plz don't instantiate
    private GreenHouseModes() {}

    private static GreenHouseMode addMode(GreenHouseMode mode) {
        modes.put(mode.getName(), mode);
        return mode;
    }

    public static GreenHouseMode getModeFromName(String name) {
        return modes.get(name);
    }

    public static GreenHouseMode getNextMode(GreenHouseMode from) {
        int id = (from.getUIIndex() + 1) % modes.size();
        for (GreenHouseMode mode : modes.values()) {
            if (mode.getUIIndex() == id) return mode;
        }
        return Normal;
    }

    public static void addTooltipInfo(MultiblockTooltipBuilder tt) {
        // maybe make this use the mods list instead
        GreenHouseModes.Normal.addTooltipInfo(tt);
        GreenHouseModes.IC2.addTooltipInfo(tt);
    }
}
