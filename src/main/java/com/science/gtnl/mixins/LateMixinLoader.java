package com.science.gtnl.mixins;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.science.gtnl.Utils.enums.Mods;
import com.science.gtnl.config.MainConfig;

import io.github.tox1cozz.mixinbooterlegacy.ILateMixinLoader;
import io.github.tox1cozz.mixinbooterlegacy.LateMixin;

@LateMixin
public class LateMixinLoader implements ILateMixinLoader {

    public static final Logger LOG = LogManager.getLogger("GTNL");
    public static final String LOG_PREFIX = "[GTNL]" + ' ';
    private static final Map<String, BooleanSupplier> MIXIN_CONFIGS = new LinkedHashMap<>();

    static {
        addMixinCFG("mixins.sciencenotleisure.late.json");
        addMixinCFG("mixins.sciencenotleisure.late.rc.json");
        addMixinCFG("mixins.sciencenotleisure.late.Debug.json", () -> MainConfig.enableDebugMode);
        addMixinCFG(
            "mixins.sciencenotleisure.late.Overpowered.json",
            () -> !Mods.Overpowered.isModLoaded() && MainConfig.enableRecipeOutputChance);
        addMixinCFG("mixins.sciencenotleisure.late.NoNHU.json", () -> !Mods.NHUtilities.isModLoaded());
        addMixinCFG("mixins.sciencenotleisure.late.TwistSpaceTechnology.json", Mods.TwistSpaceTechnology::isModLoaded);
        addMixinCFG("mixins.sciencenotleisure.late.MeteorOptimize.json", () -> MainConfig.enableMeteorSetBlockOptimize);
        addMixinCFG("mixins.sciencenotleisure.late.OPChange.json", () -> MainConfig.enableIntegratedOreFactoryChange);
    }

    @Override
    public List<String> getMixinConfigs() {
        return new ArrayList<>(MIXIN_CONFIGS.keySet());
    }

    @Override
    public boolean shouldMixinConfigQueue(final String mixinConfig) {
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
