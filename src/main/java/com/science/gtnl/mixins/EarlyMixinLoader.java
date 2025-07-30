package com.science.gtnl.mixins;

import static com.science.gtnl.ScienceNotLeisure.LOG;
import static com.science.gtnl.mixins.LateMixinLoader.LOG_PREFIX;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

import com.science.gtnl.config.MainConfig;

public class EarlyMixinLoader {

    private static final Map<String, BooleanSupplier> MIXIN_CONFIGS = new LinkedHashMap<>();

    static {
        addMixinCFG("mixins.sciencenotleisure.early.json");
        addMixinCFG("mixins.sciencenotleisure.early.SuperCreeper.json", () -> MainConfig.enableSuperCreeper);
        addMixinCFG("mixins.sciencenotleisure.early.SpecialCheatIcon.json", () -> MainConfig.enableSpecialCheatIcon);
    }

    public static List<String> getMixinConfigs() {
        return new ArrayList<>(MIXIN_CONFIGS.keySet());
    }

    public static boolean shouldMixinConfigQueue(final String mixinConfig) {
        var supplier = MIXIN_CONFIGS.get(mixinConfig);
        if (supplier == null) {
            LOG.warn(LOG_PREFIX + "Mixin config {} is not found in config map! It will never be loaded.", mixinConfig);
            return false;
        }
        return supplier.getAsBoolean();
    }

    private static void addMixinCFG(final String mixinConfig) {
        MIXIN_CONFIGS.put(mixinConfig, () -> true);
    }

    private static void addMixinCFG(final String mixinConfig, final BooleanSupplier conditions) {
        MIXIN_CONFIGS.put(mixinConfig, conditions);
    }

}
