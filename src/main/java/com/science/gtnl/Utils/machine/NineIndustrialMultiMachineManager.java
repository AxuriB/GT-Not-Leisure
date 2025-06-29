package com.science.gtnl.Utils.machine;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bartworks.API.recipe.BartWorksRecipeMaps;
import goodgenerator.api.recipe.GoodGeneratorRecipeMaps;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gtPlusPlus.api.recipe.GTPPRecipeMaps;
import gtnhlanth.api.recipe.LanthanidesRecipeMaps;
import kubatech.loaders.DEFCRecipes;
import tectech.recipe.TecTechRecipeMaps;

public class NineIndustrialMultiMachineManager {

    private static final Map<Integer, String> modeLocalizationMap = new HashMap<>();

    public static final int MACHINEMODE_1 = 0;
    public static final int MACHINEMODE_2 = 1;
    public static final int MACHINEMODE_3 = 2;
    public static final int MACHINEMODE_4 = 3;
    public static final int MACHINEMODE_5 = 4;
    public static final int MACHINEMODE_6 = 5;
    public static final int MACHINEMODE_7 = 6;
    public static final int MACHINEMODE_8 = 7;
    public static final int MACHINEMODE_9 = 8;
    public static final int MACHINEMODE_10 = 9;
    public static final int MACHINEMODE_11 = 10;
    public static final int MACHINEMODE_12 = 11;
    public static final int MACHINEMODE_13 = 12;
    public static final int MACHINEMODE_14 = 13;
    public static final int MACHINEMODE_15 = 14;
    public static final int MACHINEMODE_16 = 15;
    public static final int MACHINEMODE_17 = 16;
    public static final int MACHINEMODE_18 = 17;
    public static final int MACHINEMODE_19 = 18;
    public static final int MACHINEMODE_20 = 19;
    public static final int MACHINEMODE_21 = 20;
    public static final int MACHINEMODE_22 = 21;
    public static final int MACHINEMODE_23 = 22;
    public static final int MACHINEMODE_24 = 23;
    public static final int MACHINEMODE_25 = 24;
    public static final int MACHINEMODE_26 = 25;
    public static final int MACHINEMODE_27 = 26;
    public static final int MACHINEMODE_28 = 27;
    public static final int MACHINEMODE_29 = 28;
    public static final int MACHINEMODE_30 = 29;
    public static final int MACHINEMODE_31 = 30;
    public static final int MACHINEMODE_32 = 31;
    public static final int MACHINEMODE_33 = 32;
    public static final int MACHINEMODE_34 = 33;
    public static final int MACHINEMODE_35 = 34;
    public static final int MACHINEMODE_36 = 35;

    public int getNextMachineMode(int currentMode) {
        return switch (currentMode) {
            case MACHINEMODE_1 -> MACHINEMODE_2;
            case MACHINEMODE_2 -> MACHINEMODE_3;
            case MACHINEMODE_3 -> MACHINEMODE_4;
            case MACHINEMODE_4 -> MACHINEMODE_5;
            case MACHINEMODE_5 -> MACHINEMODE_6;
            case MACHINEMODE_6 -> MACHINEMODE_7;
            case MACHINEMODE_7 -> MACHINEMODE_8;
            case MACHINEMODE_8 -> MACHINEMODE_9;
            case MACHINEMODE_9 -> MACHINEMODE_10;
            case MACHINEMODE_10 -> MACHINEMODE_11;
            case MACHINEMODE_11 -> MACHINEMODE_12;
            case MACHINEMODE_12 -> MACHINEMODE_13;
            case MACHINEMODE_13 -> MACHINEMODE_14;
            case MACHINEMODE_14 -> MACHINEMODE_15;
            case MACHINEMODE_15 -> MACHINEMODE_16;
            case MACHINEMODE_16 -> MACHINEMODE_17;
            case MACHINEMODE_17 -> MACHINEMODE_18;
            case MACHINEMODE_18 -> MACHINEMODE_19;
            case MACHINEMODE_19 -> MACHINEMODE_20;
            case MACHINEMODE_20 -> MACHINEMODE_21;
            case MACHINEMODE_21 -> MACHINEMODE_22;
            case MACHINEMODE_22 -> MACHINEMODE_23;
            case MACHINEMODE_23 -> MACHINEMODE_24;
            case MACHINEMODE_24 -> MACHINEMODE_25;
            case MACHINEMODE_25 -> MACHINEMODE_26;
            case MACHINEMODE_26 -> MACHINEMODE_27;
            case MACHINEMODE_27 -> MACHINEMODE_28;
            case MACHINEMODE_28 -> MACHINEMODE_29;
            case MACHINEMODE_29 -> MACHINEMODE_30;
            case MACHINEMODE_30 -> MACHINEMODE_31;
            case MACHINEMODE_31 -> MACHINEMODE_32;
            case MACHINEMODE_32 -> MACHINEMODE_33;
            case MACHINEMODE_33 -> MACHINEMODE_34;
            case MACHINEMODE_34 -> MACHINEMODE_35;
            case MACHINEMODE_35 -> MACHINEMODE_36;
            default -> MACHINEMODE_1;
        };
    }

    static {
        modeLocalizationMap.put(MACHINEMODE_1, "NineIndustrialMultiMachine_Mode_1");
        modeLocalizationMap.put(MACHINEMODE_2, "NineIndustrialMultiMachine_Mode_2");
        modeLocalizationMap.put(MACHINEMODE_3, "NineIndustrialMultiMachine_Mode_3");
        modeLocalizationMap.put(MACHINEMODE_4, "NineIndustrialMultiMachine_Mode_4");
        modeLocalizationMap.put(MACHINEMODE_5, "NineIndustrialMultiMachine_Mode_5");
        modeLocalizationMap.put(MACHINEMODE_6, "NineIndustrialMultiMachine_Mode_6");
        modeLocalizationMap.put(MACHINEMODE_7, "NineIndustrialMultiMachine_Mode_7");
        modeLocalizationMap.put(MACHINEMODE_8, "NineIndustrialMultiMachine_Mode_8");
        modeLocalizationMap.put(MACHINEMODE_9, "NineIndustrialMultiMachine_Mode_9");
        modeLocalizationMap.put(MACHINEMODE_10, "NineIndustrialMultiMachine_Mode_10");
        modeLocalizationMap.put(MACHINEMODE_11, "NineIndustrialMultiMachine_Mode_11");
        modeLocalizationMap.put(MACHINEMODE_12, "NineIndustrialMultiMachine_Mode_12");
        modeLocalizationMap.put(MACHINEMODE_13, "NineIndustrialMultiMachine_Mode_13");
        modeLocalizationMap.put(MACHINEMODE_14, "NineIndustrialMultiMachine_Mode_14");
        modeLocalizationMap.put(MACHINEMODE_15, "NineIndustrialMultiMachine_Mode_15");
        modeLocalizationMap.put(MACHINEMODE_16, "NineIndustrialMultiMachine_Mode_16");
        modeLocalizationMap.put(MACHINEMODE_17, "NineIndustrialMultiMachine_Mode_17");
        modeLocalizationMap.put(MACHINEMODE_18, "NineIndustrialMultiMachine_Mode_18");
        modeLocalizationMap.put(MACHINEMODE_19, "NineIndustrialMultiMachine_Mode_19");
        modeLocalizationMap.put(MACHINEMODE_20, "NineIndustrialMultiMachine_Mode_20");
        modeLocalizationMap.put(MACHINEMODE_21, "NineIndustrialMultiMachine_Mode_21");
        modeLocalizationMap.put(MACHINEMODE_22, "NineIndustrialMultiMachine_Mode_22");
        modeLocalizationMap.put(MACHINEMODE_23, "NineIndustrialMultiMachine_Mode_23");
        modeLocalizationMap.put(MACHINEMODE_24, "NineIndustrialMultiMachine_Mode_24");
        modeLocalizationMap.put(MACHINEMODE_25, "NineIndustrialMultiMachine_Mode_25");
        modeLocalizationMap.put(MACHINEMODE_26, "NineIndustrialMultiMachine_Mode_26");
        modeLocalizationMap.put(MACHINEMODE_27, "NineIndustrialMultiMachine_Mode_27");
        modeLocalizationMap.put(MACHINEMODE_28, "NineIndustrialMultiMachine_Mode_28");
        modeLocalizationMap.put(MACHINEMODE_29, "NineIndustrialMultiMachine_Mode_29");
        modeLocalizationMap.put(MACHINEMODE_30, "NineIndustrialMultiMachine_Mode_30");
        modeLocalizationMap.put(MACHINEMODE_31, "NineIndustrialMultiMachine_Mode_31");
        modeLocalizationMap.put(MACHINEMODE_32, "NineIndustrialMultiMachine_Mode_32");
        modeLocalizationMap.put(MACHINEMODE_33, "NineIndustrialMultiMachine_Mode_33");
        modeLocalizationMap.put(MACHINEMODE_34, "NineIndustrialMultiMachine_Mode_34");
        modeLocalizationMap.put(MACHINEMODE_35, "NineIndustrialMultiMachine_Mode_35");
        modeLocalizationMap.put(MACHINEMODE_36, "NineIndustrialMultiMachine_Mode_36");
    }

    public static String getModeLocalization(int machineMode) {
        return modeLocalizationMap.getOrDefault(machineMode, "");
    }

    private static final int MODE_COMPRESSOR = 0;
    private static final int MODE_LATHE = 1;
    private static final int MODE_MAGNETIC = 2;
    private static final int MODE_FERMENTER = 3;
    private static final int MODE_FLUIDEXTRACT = 4;
    private static final int MODE_EXTRACTOR = 5;
    private static final int MODE_LASER = 6;
    private static final int MODE_AUTOCLAVE = 7;
    private static final int MODE_FLUIDSOLIDIFY = 8;
    private static final int MODE_OREWASHER = 9;
    private static final int MODE_THERMALCENTRIFUGE = 10;
    private static final int MODE_NEUTRONIUMCOMPRESSOR = 11;
    private static final int MODE_RECYCLER = 12;
    private static final int MODE_FURNACE = 13;
    private static final int MODE_MICROWAVE = 14;
    private static final int MODE_REPLICATOR = 15;
    private static final int MODE_PLASMAARCFURNACE = 16;
    private static final int MODE_ARCFURNACE = 17;
    private static final int MODE_PRINTER = 18;
    private static final int MODE_SIFTER = 19;
    private static final int MODE_FORMINGPRESS = 20;
    private static final int MODE_MACERATOR = 21;
    private static final int MODE_CHEMICALBATH = 22;
    private static final int MODE_FLUIDCANNER = 23;
    private static final int MODE_BREWING = 24;
    private static final int MODE_FLUIDHEATER = 25;
    private static final int MODE_DISTILLERY = 26;
    private static final int MODE_PACKAGER = 27;
    private static final int MODE_UNPACKAGER = 28;
    private static final int MODE_FUSION = 29;
    private static final int MODE_BLASTFURNACE = 30;
    private static final int MODE_PLASMAFORGE = 31;
    private static final int MODE_TRANSCENDENTPLASMAMIXER = 32;
    private static final int MODE_PRIMITIVEBLAST = 33;
    private static final int MODE_IMPLOSION = 34;
    private static final int MODE_VACUUMFREEZER = 35;
    private static final int MODE_MULTIBLOCKCHEMICALREACTOR = 36;
    private static final int MODE_DISTILLATIONTOWER = 37;
    private static final int MODE_CRACKING = 38;
    private static final int MODE_PYROLYSE = 39;
    private static final int MODE_WIREMILL = 40;
    private static final int MODE_BENDER = 41;
    private static final int MODE_ALLOYSMELTER = 42;
    private static final int MODE_ASSEMBLER = 43;
    private static final int MODE_CIRCUITASSEMBLER = 44;
    private static final int MODE_CUTTER = 45;
    private static final int MODE_SLICER = 46;
    private static final int MODE_EXTRUDER = 47;
    private static final int MODE_HAMMER = 48;
    private static final int MODE_AMPLIFIER = 49;
    private static final int MODE_EXTREMEDIESELFUELS = 50;
    private static final int MODE_HOTFUELS = 51;
    private static final int MODE_DENSELIQUIDFUELS = 52;
    private static final int MODE_PLASMAFUELS = 53;
    private static final int MODE_MAGICFUELS = 54;
    private static final int MODE_SMALLNAQUADAHREACTORFUELS = 55;
    private static final int MODE_LARGENAQUADAHREACTORFUELS = 56;
    private static final int MODE_HUGENAQUADAHREACTORFUELS = 57;
    private static final int MODE_EXTREMENAQUADAHREACTORFUELS = 58;
    private static final int MODE_ULTRAHUGENAQUADAHREACTORFUELS = 59;
    private static final int MODE_NANOFORGE = 60;
    private static final int MODE_PCBFACTORY = 61;
    private static final int MODE_COKEOVEN = 62;
    private static final int MODE_ROCKETFUELS = 63;
    private static final int MODE_QUANTUMFORCETRANSFORMER = 64;
    private static final int MODE_VACUUMFURNACE = 65;
    private static final int MODE_ALLOYBLASTSMELTER = 66;
    private static final int MODE_LIQUIDFLUORINETHORIUMREACTOR = 67;
    private static final int MODE_NUCLEARSALTPROCESSINGPLANT = 68;
    private static final int MODE_MILLING = 69;
    private static final int MODE_FISSIONFUELPROCESSING = 70;
    private static final int MODE_COLDTRAP = 71;
    private static final int MODE_REACTORPROCESSINGUNIT = 72;
    private static final int MODE_SIMPLEWASHER = 73;
    private static final int MODE_MOLECULARTRANSFORMER = 74;
    private static final int MODE_CHEMICALPLANT = 75;
    private static final int MODE_RTG = 76;
    private static final int MODE_THERMALBOILER = 77;
    private static final int MODE_SOLARTOWER = 78;
    private static final int MODE_CYCLOTRON = 79;
    private static final int MODE_FISHPOND = 80;
    private static final int MODE_CENTRIFUGENONCELL = 81;
    private static final int MODE_ELECTROLYZERNONCELL = 82;
    private static final int MODE_MIXERNONCELL = 83;
    private static final int MODE_CHEMICALDEHYDRATORNONCELL = 84;
    private static final int MODE_SEMIFLUIDFUELS = 85;
    private static final int MODE_FLOTATIONCELL = 86;
    private static final int MODE_EYEOFHARMONY = 87;
    private static final int MODE_GODFORGEPLASMA = 88;
    private static final int MODE_GODFORGEEXOTICMATTER = 89;
    private static final int MODE_GODFORGEMOLTEN = 90;
    private static final int MODE_PRECISEASSEMBLERRECIPES = 91;
    private static final int MODE_FUSIONCRAFTING = 92;
    private static final int MODE_BIOLAB = 93;
    private static final int MODE_BACTERIALVAT = 94;
    private static final int MODE_ACIDGENFUELS = 95;
    private static final int MODE_CIRCUITASSEMBLYLINE = 96;
    private static final int MODE_ELECTRICIMPLOSIONCOMPRESSOR = 97;
    private static final int MODE_COMPONENTASSEMBLYLINERECIPES = 98;
    private static final int MODE_EXTREMEHEATEXCHANGERFUELS = 99;
    private static final int MODE_NEUTRONACTIVATORRECIPES = 100;
    private static final int MODE_NAQUADAHFUELREFINEFACTORYRECIPES = 101;
    private static final int MODE_NAQUADAHREACTORFUELS = 102;
    private static final int MODE_DIGESTERRECIPES = 103;
    private static final int MODE_DISSOLUTIONTANKRECIPES = 104;
    private static final int MODE_ASSEMBLYLINEVISUALRECIPES = 105;
    private static final int MODE_CANNERRECIPES = 106;
    private static final int MODE_GASTURBINEFUELS = 107;

    public static final List<RecipeMap<?>> RECIPE_MAPS = Collections.unmodifiableList(
        Arrays.asList(
            RecipeMaps.compressorRecipes,
            RecipeMaps.latheRecipes,
            RecipeMaps.polarizerRecipes,
            RecipeMaps.fermentingRecipes,
            RecipeMaps.fluidExtractionRecipes,
            RecipeMaps.extractorRecipes,
            RecipeMaps.laserEngraverRecipes,
            RecipeMaps.autoclaveRecipes,
            RecipeMaps.fluidSolidifierRecipes,
            RecipeMaps.oreWasherRecipes,
            RecipeMaps.thermalCentrifugeRecipes,
            RecipeMaps.neutroniumCompressorRecipes,
            RecipeMaps.recyclerRecipes,
            RecipeMaps.furnaceRecipes,
            RecipeMaps.microwaveRecipes,
            RecipeMaps.replicatorRecipes,
            RecipeMaps.plasmaArcFurnaceRecipes,
            RecipeMaps.arcFurnaceRecipes,
            RecipeMaps.printerRecipes,
            RecipeMaps.sifterRecipes,
            RecipeMaps.formingPressRecipes,
            RecipeMaps.maceratorRecipes,
            RecipeMaps.chemicalBathRecipes,
            RecipeMaps.fluidCannerRecipes,
            RecipeMaps.brewingRecipes,
            RecipeMaps.fluidHeaterRecipes,
            RecipeMaps.distilleryRecipes,
            RecipeMaps.packagerRecipes,
            RecipeMaps.unpackagerRecipes,
            RecipeMaps.fusionRecipes,
            RecipeMaps.blastFurnaceRecipes,
            RecipeMaps.plasmaForgeRecipes,
            RecipeMaps.transcendentPlasmaMixerRecipes,
            RecipeMaps.primitiveBlastRecipes,
            RecipeMaps.implosionRecipes,
            RecipeMaps.vacuumFreezerRecipes,
            RecipeMaps.multiblockChemicalReactorRecipes,
            RecipeMaps.distillationTowerRecipes,
            RecipeMaps.crackingRecipes,
            RecipeMaps.pyrolyseRecipes,
            RecipeMaps.wiremillRecipes,
            RecipeMaps.benderRecipes,
            RecipeMaps.alloySmelterRecipes,
            RecipeMaps.assemblerRecipes,
            RecipeMaps.circuitAssemblerRecipes,
            RecipeMaps.cutterRecipes,
            RecipeMaps.slicerRecipes,
            RecipeMaps.extruderRecipes,
            RecipeMaps.hammerRecipes,
            RecipeMaps.amplifierRecipes,
            RecipeMaps.extremeDieselFuels,
            RecipeMaps.hotFuels,
            RecipeMaps.denseLiquidFuels,
            RecipeMaps.plasmaFuels,
            RecipeMaps.magicFuels,
            RecipeMaps.smallNaquadahReactorFuels,
            RecipeMaps.largeNaquadahReactorFuels,
            RecipeMaps.hugeNaquadahReactorFuels,
            RecipeMaps.extremeNaquadahReactorFuels,
            RecipeMaps.ultraHugeNaquadahReactorFuels,
            RecipeMaps.nanoForgeRecipes,
            RecipeMaps.pcbFactoryRecipes,
            GTPPRecipeMaps.cokeOvenRecipes,
            GTPPRecipeMaps.rocketFuels,
            GTPPRecipeMaps.quantumForceTransformerRecipes,
            GTPPRecipeMaps.vacuumFurnaceRecipes,
            GTPPRecipeMaps.alloyBlastSmelterRecipes,
            GTPPRecipeMaps.liquidFluorineThoriumReactorRecipes,
            GTPPRecipeMaps.nuclearSaltProcessingPlantRecipes,
            GTPPRecipeMaps.millingRecipes,
            GTPPRecipeMaps.fissionFuelProcessingRecipes,
            GTPPRecipeMaps.coldTrapRecipes,
            GTPPRecipeMaps.reactorProcessingUnitRecipes,
            GTPPRecipeMaps.simpleWasherRecipes,
            GTPPRecipeMaps.molecularTransformerRecipes,
            GTPPRecipeMaps.chemicalPlantRecipes,
            GTPPRecipeMaps.rtgFuels,
            GTPPRecipeMaps.thermalBoilerRecipes,
            GTPPRecipeMaps.solarTowerRecipes,
            GTPPRecipeMaps.cyclotronRecipes,
            GTPPRecipeMaps.fishPondRecipes,
            GTPPRecipeMaps.centrifugeNonCellRecipes,
            GTPPRecipeMaps.electrolyzerNonCellRecipes,
            GTPPRecipeMaps.mixerNonCellRecipes,
            GTPPRecipeMaps.chemicalDehydratorNonCellRecipes,
            GTPPRecipeMaps.semiFluidFuels,
            GTPPRecipeMaps.flotationCellRecipes,
            TecTechRecipeMaps.eyeOfHarmonyRecipes,
            TecTechRecipeMaps.godforgePlasmaRecipes,
            TecTechRecipeMaps.godforgeExoticMatterRecipes,
            TecTechRecipeMaps.godforgeMoltenRecipes,
            TecTechRecipeMaps.godforgeFakeUpgradeCostRecipes,
            DEFCRecipes.fusionCraftingRecipes,
            BartWorksRecipeMaps.bioLabRecipes,
            BartWorksRecipeMaps.bacterialVatRecipes,
            BartWorksRecipeMaps.acidGenFuels,
            BartWorksRecipeMaps.circuitAssemblyLineRecipes,
            BartWorksRecipeMaps.electricImplosionCompressorRecipes,
            GoodGeneratorRecipeMaps.componentAssemblyLineRecipes,
            GoodGeneratorRecipeMaps.extremeHeatExchangerFuels,
            GoodGeneratorRecipeMaps.neutronActivatorRecipes,
            GoodGeneratorRecipeMaps.naquadahFuelRefineFactoryRecipes,
            GoodGeneratorRecipeMaps.naquadahReactorFuels,
            LanthanidesRecipeMaps.digesterRecipes,
            LanthanidesRecipeMaps.dissolutionTankRecipes,
            RecipeMaps.assemblylineVisualRecipes,
            RecipeMaps.cannerRecipes,
            RecipeMaps.gasTurbineFuels));

    private static final Map<Integer, RecipeMap<?>> RECIPE_MAP_LOOKUP = new HashMap<>();

    static {
        RECIPE_MAP_LOOKUP.put(MODE_COMPRESSOR, RecipeMaps.compressorRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_LATHE, RecipeMaps.latheRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_MAGNETIC, RecipeMaps.polarizerRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_FERMENTER, RecipeMaps.fermentingRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_FLUIDEXTRACT, RecipeMaps.fluidExtractionRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_EXTRACTOR, RecipeMaps.extractorRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_LASER, RecipeMaps.laserEngraverRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_AUTOCLAVE, RecipeMaps.autoclaveRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_FLUIDSOLIDIFY, RecipeMaps.fluidSolidifierRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_OREWASHER, RecipeMaps.oreWasherRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_THERMALCENTRIFUGE, RecipeMaps.thermalCentrifugeRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_NEUTRONIUMCOMPRESSOR, RecipeMaps.neutroniumCompressorRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_RECYCLER, RecipeMaps.recyclerRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_FURNACE, RecipeMaps.furnaceRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_MICROWAVE, RecipeMaps.microwaveRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_REPLICATOR, RecipeMaps.replicatorRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_PLASMAARCFURNACE, RecipeMaps.plasmaArcFurnaceRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_ARCFURNACE, RecipeMaps.arcFurnaceRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_PRINTER, RecipeMaps.printerRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_SIFTER, RecipeMaps.sifterRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_FORMINGPRESS, RecipeMaps.formingPressRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_MACERATOR, RecipeMaps.maceratorRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_CHEMICALBATH, RecipeMaps.chemicalBathRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_FLUIDCANNER, RecipeMaps.fluidCannerRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_BREWING, RecipeMaps.brewingRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_FLUIDHEATER, RecipeMaps.fluidHeaterRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_DISTILLERY, RecipeMaps.distilleryRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_PACKAGER, RecipeMaps.packagerRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_UNPACKAGER, RecipeMaps.unpackagerRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_FUSION, RecipeMaps.fusionRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_BLASTFURNACE, RecipeMaps.blastFurnaceRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_PLASMAFORGE, RecipeMaps.plasmaForgeRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_TRANSCENDENTPLASMAMIXER, RecipeMaps.transcendentPlasmaMixerRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_PRIMITIVEBLAST, RecipeMaps.primitiveBlastRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_IMPLOSION, RecipeMaps.implosionRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_VACUUMFREEZER, RecipeMaps.vacuumFreezerRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_MULTIBLOCKCHEMICALREACTOR, RecipeMaps.multiblockChemicalReactorRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_DISTILLATIONTOWER, RecipeMaps.distillationTowerRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_CRACKING, RecipeMaps.crackingRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_PYROLYSE, RecipeMaps.pyrolyseRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_WIREMILL, RecipeMaps.wiremillRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_BENDER, RecipeMaps.benderRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_ALLOYSMELTER, RecipeMaps.alloySmelterRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_ASSEMBLER, RecipeMaps.assemblerRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_CIRCUITASSEMBLER, RecipeMaps.circuitAssemblerRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_CUTTER, RecipeMaps.cutterRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_SLICER, RecipeMaps.slicerRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_EXTRUDER, RecipeMaps.extruderRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_HAMMER, RecipeMaps.hammerRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_AMPLIFIER, RecipeMaps.amplifierRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_EXTREMEDIESELFUELS, RecipeMaps.extremeDieselFuels);
        RECIPE_MAP_LOOKUP.put(MODE_HOTFUELS, RecipeMaps.hotFuels);
        RECIPE_MAP_LOOKUP.put(MODE_DENSELIQUIDFUELS, RecipeMaps.denseLiquidFuels);
        RECIPE_MAP_LOOKUP.put(MODE_PLASMAFUELS, RecipeMaps.plasmaFuels);
        RECIPE_MAP_LOOKUP.put(MODE_MAGICFUELS, RecipeMaps.magicFuels);
        RECIPE_MAP_LOOKUP.put(MODE_SMALLNAQUADAHREACTORFUELS, RecipeMaps.smallNaquadahReactorFuels);
        RECIPE_MAP_LOOKUP.put(MODE_LARGENAQUADAHREACTORFUELS, RecipeMaps.largeNaquadahReactorFuels);
        RECIPE_MAP_LOOKUP.put(MODE_HUGENAQUADAHREACTORFUELS, RecipeMaps.hugeNaquadahReactorFuels);
        RECIPE_MAP_LOOKUP.put(MODE_EXTREMENAQUADAHREACTORFUELS, RecipeMaps.extremeNaquadahReactorFuels);
        RECIPE_MAP_LOOKUP.put(MODE_ULTRAHUGENAQUADAHREACTORFUELS, RecipeMaps.ultraHugeNaquadahReactorFuels);
        RECIPE_MAP_LOOKUP.put(MODE_NANOFORGE, RecipeMaps.nanoForgeRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_PCBFACTORY, RecipeMaps.pcbFactoryRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_COKEOVEN, GTPPRecipeMaps.cokeOvenRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_ROCKETFUELS, GTPPRecipeMaps.rocketFuels);
        RECIPE_MAP_LOOKUP.put(MODE_QUANTUMFORCETRANSFORMER, GTPPRecipeMaps.quantumForceTransformerRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_VACUUMFURNACE, GTPPRecipeMaps.vacuumFurnaceRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_ALLOYBLASTSMELTER, GTPPRecipeMaps.alloyBlastSmelterRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_LIQUIDFLUORINETHORIUMREACTOR, GTPPRecipeMaps.liquidFluorineThoriumReactorRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_NUCLEARSALTPROCESSINGPLANT, GTPPRecipeMaps.nuclearSaltProcessingPlantRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_MILLING, GTPPRecipeMaps.millingRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_FISSIONFUELPROCESSING, GTPPRecipeMaps.fissionFuelProcessingRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_COLDTRAP, GTPPRecipeMaps.coldTrapRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_REACTORPROCESSINGUNIT, GTPPRecipeMaps.reactorProcessingUnitRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_SIMPLEWASHER, GTPPRecipeMaps.simpleWasherRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_MOLECULARTRANSFORMER, GTPPRecipeMaps.molecularTransformerRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_CHEMICALPLANT, GTPPRecipeMaps.chemicalPlantRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_RTG, GTPPRecipeMaps.rtgFuels);
        RECIPE_MAP_LOOKUP.put(MODE_THERMALBOILER, GTPPRecipeMaps.thermalBoilerRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_SOLARTOWER, GTPPRecipeMaps.solarTowerRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_CYCLOTRON, GTPPRecipeMaps.cyclotronRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_FISHPOND, GTPPRecipeMaps.fishPondRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_CENTRIFUGENONCELL, GTPPRecipeMaps.centrifugeNonCellRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_ELECTROLYZERNONCELL, GTPPRecipeMaps.electrolyzerNonCellRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_MIXERNONCELL, GTPPRecipeMaps.mixerNonCellRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_CHEMICALDEHYDRATORNONCELL, GTPPRecipeMaps.chemicalDehydratorNonCellRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_SEMIFLUIDFUELS, GTPPRecipeMaps.semiFluidFuels);
        RECIPE_MAP_LOOKUP.put(MODE_FLOTATIONCELL, GTPPRecipeMaps.flotationCellRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_EYEOFHARMONY, TecTechRecipeMaps.eyeOfHarmonyRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_GODFORGEPLASMA, TecTechRecipeMaps.godforgePlasmaRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_GODFORGEEXOTICMATTER, TecTechRecipeMaps.godforgeExoticMatterRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_GODFORGEMOLTEN, TecTechRecipeMaps.godforgeMoltenRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_PRECISEASSEMBLERRECIPES, GoodGeneratorRecipeMaps.preciseAssemblerRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_FUSIONCRAFTING, DEFCRecipes.fusionCraftingRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_BIOLAB, BartWorksRecipeMaps.bioLabRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_BACTERIALVAT, BartWorksRecipeMaps.bacterialVatRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_ACIDGENFUELS, BartWorksRecipeMaps.acidGenFuels);
        RECIPE_MAP_LOOKUP.put(MODE_CIRCUITASSEMBLYLINE, BartWorksRecipeMaps.circuitAssemblyLineRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_ELECTRICIMPLOSIONCOMPRESSOR, BartWorksRecipeMaps.electricImplosionCompressorRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_COMPONENTASSEMBLYLINERECIPES, GoodGeneratorRecipeMaps.componentAssemblyLineRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_EXTREMEHEATEXCHANGERFUELS, GoodGeneratorRecipeMaps.extremeHeatExchangerFuels);
        RECIPE_MAP_LOOKUP.put(MODE_NEUTRONACTIVATORRECIPES, GoodGeneratorRecipeMaps.neutronActivatorRecipes);
        RECIPE_MAP_LOOKUP
            .put(MODE_NAQUADAHFUELREFINEFACTORYRECIPES, GoodGeneratorRecipeMaps.naquadahFuelRefineFactoryRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_NAQUADAHREACTORFUELS, GoodGeneratorRecipeMaps.naquadahReactorFuels);
        RECIPE_MAP_LOOKUP.put(MODE_DIGESTERRECIPES, LanthanidesRecipeMaps.digesterRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_DISSOLUTIONTANKRECIPES, LanthanidesRecipeMaps.dissolutionTankRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_ASSEMBLYLINEVISUALRECIPES, RecipeMaps.assemblylineVisualRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_CANNERRECIPES, RecipeMaps.cannerRecipes);
        RECIPE_MAP_LOOKUP.put(MODE_GASTURBINEFUELS, RecipeMaps.gasTurbineFuels);
    }

    public static Collection<RecipeMap<?>> getAvailableRecipeMaps() {
        return RECIPE_MAPS;
    }

    public static RecipeMap<?> getRecipeMap(int aMode) {
        return RECIPE_MAP_LOOKUP.get(aMode);
    }

    public static final int[][] MODE_MAP = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 9, 10, 11 }, { 12, 13, 14 },
        { 15, 16, 17 }, { 18, 19, 20 }, { 21, 22, 23 }, { 24, 25, 26 }, { 27, 28, 29 }, { 30, 31, 32 }, { 33, 34, 35 },
        { 36, 37, 38 }, { 39, 40, 41 }, { 42, 43, 44 }, { 45, 46, 47 }, { 48, 49, 50 }, { 51, 52, 53 }, { 54, 55, 56 },
        { 57, 58, 59 }, { 60, 61, 62 }, { 63, 64, 65 }, { 66, 67, 68 }, { 69, 70, 71 }, { 72, 73, 74 }, { 75, 76, 77 },
        { 78, 79, 80 }, { 81, 82, 83 }, { 84, 85, 86 }, { 87, 88, 89 }, { 90, 91, 92 }, { 93, 94, 95 }, { 96, 97, 98 },
        { 99, 100, 101 }, { 102, 103, 104 }, { 105, 106, 107 } };

}
