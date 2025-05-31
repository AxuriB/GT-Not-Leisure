package com.science.gtnl.Utils.enums;

import java.util.Locale;

import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.common.Loader;

public enum Mods {

    ScienceNotLeisure(ModIds.SCIENCENOTLEISURE, Names.SCIENCENOTLEISURE),
    EyeOfHarmonyBuffer(ModIds.EYEOFHARMONYBUFFER, Names.EYEOFHARMONYBUFFER),
    ProgrammableHatches(ModIds.PROGRAMMABLEHATCHES, Names.PROGRAMMABLEHATCHES),
    TwistSpaceTechnology(ModIds.TWISTSPACETECHNOLOGY, Names.TWISTSPACETECHNOLOGY),
    BoxPlusPlus(ModIds.BOXPLUSPLUS, Names.BOXPLUSPLUS),
    NHUtilities(ModIds.NHUTILITIES, Names.NHUTILITIES),
    AE2Thing(ModIds.AE2THING, Names.AE2THING),
    QzMiner(ModIds.QZMINER, Names.QZMINER),
    OTHTechnology(ModIds.OTHTECHNOLOGY, Names.OTHTECHNOLOGY),
    Baubles(ModIds.BAUBLES, Names.BAUBLES),
    Overpowered(ModIds.OVERPOWERED, Names.OVERPOWERED),
    ThinkTech(ModIds.THINKTECH, Names.THINKTECH),
    ReAvaritia(ModIds.REAVARITIA, Names.REAVARITIA),
    Sudoku(ModIds.SUDOKU, Names.SUDOKU),
    GiveCount(ModIds.GIVECOUNT, Names.GIVECOUNT);

    public static class ModIds {

        public static final String SCIENCENOTLEISURE = "sciencenotleisure";
        public static final String EYEOFHARMONYBUFFER = "eyeofharmonybuffer";
        public static final String PROGRAMMABLEHATCHES = "programmablehatches";
        public static final String TWISTSPACETECHNOLOGY = "TwistSpaceTechnology";
        public static final String BOXPLUSPLUS = "boxplusplus";
        public static final String NHUTILITIES = "NHUtilities";
        public static final String AE2THING = "ae2thing";
        public static final String QZMINER = "qz_miner";
        public static final String OTHTECHNOLOGY = "123Technology";
        public static final String BAUBLES = "Baubles";
        public static final String OVERPOWERED = "Overpowered";
        public static final String THINKTECH = "thinktech";
        public static final String GIVECOUNT = "givecount";
        public static final String REAVARITIA = "reavaritia";
        public static final String SUDOKU = "sudoku";
    }

    public static class Names {

        public static final String SCIENCENOTLEISURE = "ScienceNotLeisure";
        public static final String EYEOFHARMONYBUFFER = "EyeOfHarmonyBuffer";
        public static final String PROGRAMMABLEHATCHES = "ProgrammableHatches";
        public static final String TWISTSPACETECHNOLOGY = "TwistSpaceTechnology";
        public static final String BOXPLUSPLUS = "BoxPlusPlus";
        public static final String NHUTILITIES = "NH-Utilities";
        public static final String AE2THING = "AE2Things";
        public static final String QZMINER = "QzMiner";
        public static final String OTHTECHNOLOGY = "123Technology";
        public static final String BAUBLES = "Baubles";
        public static final String OVERPOWERED = "Overpowered";
        public static final String THINKTECH = "ThinkTech";
        public static final String GIVECOUNT = "GiveCount";
        public static final String REAVARITIA = "ReAvaritia";
        public static final String SUDOKU = "Sudoku";
    }

    public final String ID;
    public final String resourceDomain;
    public final String displayName;
    public Boolean modLoaded;

    Mods(String ID, String displayName) {
        this.ID = ID;
        this.resourceDomain = ID.toLowerCase(Locale.ENGLISH);
        this.displayName = displayName;
    }

    public boolean isModLoaded() {
        if (this.modLoaded == null) {
            this.modLoaded = Loader.isModLoaded(ID);
        }
        return this.modLoaded;
    }

    public String getModId() {
        return this.ID;
    }

    public String getResourcePath(String... path) {
        return this.getResourceLocation(path)
            .toString();
    }

    public ResourceLocation getResourceLocation(String... path) {
        return new ResourceLocation(this.resourceDomain, String.join("/", path));
    }
}
