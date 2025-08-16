package com.science.gtnl.mixins;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.science.gtnl.Utils.enums.Mods;
import com.science.gtnl.config.MainConfig;

import io.github.tox1cozz.mixinbooterlegacy.ILateMixinLoader;
import io.github.tox1cozz.mixinbooterlegacy.LateMixin;

@LateMixin
public class LateMixinLoader implements ILateMixinLoader {

    public static final Logger LOG = LogManager.getLogger("GTNL");
    public static final String LOG_PREFIX = "[GTNL] ";

    private static final List<String> MIXIN_CONFIGS = new ArrayList<>();

    static {
        MIXIN_CONFIGS.add("mixins.sciencenotleisure.late.json");
        MIXIN_CONFIGS.add("mixins.sciencenotleisure.late.rc.json");

        if (MainConfig.enableDebugMode) {
            MIXIN_CONFIGS.add("mixins.sciencenotleisure.late.Debug.json");
        }

        if (!Mods.Overpowered.isModLoaded() && MainConfig.enableRecipeOutputChance) {
            MIXIN_CONFIGS.add("mixins.sciencenotleisure.late.Overpowered.json");
        }

        if (!Mods.NHUtilities.isModLoaded()) {
            MIXIN_CONFIGS.add("mixins.sciencenotleisure.late.NoNHU.json");
        }

        if (Mods.TwistSpaceTechnology.isModLoaded()) {
            MIXIN_CONFIGS.add("mixins.sciencenotleisure.late.TwistSpaceTechnology.json");
        }

        if (MainConfig.enableMeteorSetBlockOptimize) {
            MIXIN_CONFIGS.add("mixins.sciencenotleisure.late.MeteorOptimize.json");
        }

        if (MainConfig.enableIntegratedOreFactoryChange) {
            MIXIN_CONFIGS.add("mixins.sciencenotleisure.late.OPChange.json");
        }
    }

    @Override
    public List<String> getMixinConfigs() {
        return MIXIN_CONFIGS;
    }
}
