package com.science.gtnl.Utils.enums;

import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;

import gregtech.api.enums.Textures;

public enum HPCAModifier {

    RED(new Textures.BlockIcons.CustomIcon(RESOURCE_ROOT_ID + ":" + "iconsets/OVERLAY_FRONT_INDICATOR_RED"), 1, 3, 1, 1,
        1.5, 1),
    YELLOW(new Textures.BlockIcons.CustomIcon(RESOURCE_ROOT_ID + ":" + "iconsets/OVERLAY_FRONT_INDICATOR_YELLOW"), 1.5,
        1.5, 1, 1.5, 1.25, 1),
    GREEN(new Textures.BlockIcons.CustomIcon(RESOURCE_ROOT_ID + ":" + "iconsets/OVERLAY_FRONT_INDICATOR_GREEN"), 1, 1,
        1, 1, 1, 1);

    public final Textures.BlockIcons.CustomIcon overlay;
    public final double computationCoefficientX;
    public final double coolantCoefficientX;
    public final double heatCoefficientX;
    public final double computationCoefficientY;
    public final double coolantCoefficientY;
    public final double heatCoefficientY;

    HPCAModifier(Textures.BlockIcons.CustomIcon overlay, double computationX, double coolantX, double heatX,
        double computationY, double coolantY, double heatY) {
        this.overlay = overlay;
        this.computationCoefficientX = computationX;
        this.coolantCoefficientX = coolantX;
        this.heatCoefficientX = heatX;
        this.computationCoefficientY = computationY;
        this.coolantCoefficientY = coolantY;
        this.heatCoefficientY = heatY;
    }
}
