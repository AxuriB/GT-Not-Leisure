package com.science.gtnl.config;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

import com.science.gtnl.utils.enums.ModList;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;

public class ConfigGui extends GuiConfig {

    public ConfigGui(GuiScreen parentScreen) {
        super(
            parentScreen,
            getConfigElements(),
            ModList.ScienceNotLeisure.ID,
            false,
            false,
            GuiConfig.getAbridgedConfigPath(MainConfig.config.toString()));
    }

    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<>();

        if (MainConfig.config != null) {
            list.add(new ConfigElement(MainConfig.config.getCategory(MainConfig.CATEGORY_GTNL_CONFIG)));
        } else {
            System.err.println("Error: MainConfig.config is null when trying to get config elements!");
        }

        return list;
    }
}
