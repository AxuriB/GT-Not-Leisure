package com.science.gtnl.loader;

import net.minecraft.item.ItemStack;

import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.common.hatch.MTEHatchCustomFluid;
import com.science.gtnl.common.machine.multiblock.AdvancedPhotovoltaicPowerStation;
import com.science.gtnl.common.machine.multiblock.BloodSoulSacrificialArray;
import com.science.gtnl.common.machine.multiblock.BrickedBlastFurnace;
import com.science.gtnl.common.machine.multiblock.CheatOreProcessingFactory;
import com.science.gtnl.common.machine.multiblock.ComponentAssembler;
import com.science.gtnl.common.machine.multiblock.Desulfurizer;
import com.science.gtnl.common.machine.multiblock.EdenGarden;
import com.science.gtnl.common.machine.multiblock.EnergeticPhotovoltaicPowerStation;
import com.science.gtnl.common.machine.multiblock.GenerationEarthEngine;
import com.science.gtnl.common.machine.multiblock.HandOfJohnDavisonRockefeller;
import com.science.gtnl.common.machine.multiblock.IndustrialArcaneAssembler;
import com.science.gtnl.common.machine.multiblock.LapotronChip;
import com.science.gtnl.common.machine.multiblock.LargeBrewer;
import com.science.gtnl.common.machine.multiblock.LargeCircuitAssembler;
import com.science.gtnl.common.machine.multiblock.LargeSteamAlloySmelter;
import com.science.gtnl.common.machine.multiblock.LargeSteamChemicalBath;
import com.science.gtnl.common.machine.multiblock.LargeSteamCircuitAssembler;
import com.science.gtnl.common.machine.multiblock.LargeSteamCrusher;
import com.science.gtnl.common.machine.multiblock.LargeSteamFurnace;
import com.science.gtnl.common.machine.multiblock.LargeSteamThermalCentrifuge;
import com.science.gtnl.common.machine.multiblock.LibraryOfRuina;
import com.science.gtnl.common.machine.multiblock.MatterFabricator;
import com.science.gtnl.common.machine.multiblock.MeteorMiner;
import com.science.gtnl.common.machine.multiblock.NeutroniumWireCutting;
import com.science.gtnl.common.machine.multiblock.NineIndustrialMultiMachine;
import com.science.gtnl.common.machine.multiblock.PetrochemicalPlant;
import com.science.gtnl.common.machine.multiblock.PrimitiveDistillationTower;
import com.science.gtnl.common.machine.multiblock.ProcessingArray;
import com.science.gtnl.common.machine.multiblock.RareEarthCentrifugal;
import com.science.gtnl.common.machine.multiblock.RealArtificialStar;
import com.science.gtnl.common.machine.multiblock.SmeltingMixingFurnace;
import com.science.gtnl.common.machine.multiblock.SteamCracking;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.BlazeBlastFurnace;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.ChemicalPlant;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.ColdIceFreezer;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.ElectricImplosionCompressor;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeArcSmelter;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeAssembler;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeAutoclave;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeBender;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeCentrifuge;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeChemicalBath;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeCutter;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeElectrolyzer;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeElectromagnet;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeExtractor;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeExtruder;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeForming;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeIndustrialLathe;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeMacerationTower;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeMaterialPress;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeMixer;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeSiftingFunnel;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeSolidifier;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeWiremill;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.MegaBlastFurnace;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.VacuumFreezer;
import com.science.gtnl.common.machine.multiblock.TeleportationArrayToAlfheim;
import com.science.gtnl.common.machine.multiblock.VibrantPhotovoltaicPowerStation;
import com.science.gtnl.common.machine.multiblock.WhiteNightGenerator;

import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.util.minecraft.FluidUtils;
import tectech.thing.metaTileEntity.hatch.MTEHatchCapacitor;
import tectech.thing.metaTileEntity.hatch.MTEHatchRack;

public class MachineLoader {

    public static ItemStack LargeSteamCircuitAssembler;
    public static ItemStack GenerationEarthEngine;
    public static ItemStack BloodSoulSacrificialArray;
    public static ItemStack TeleportationArrayToAlfheim;
    public static ItemStack LapotronChip;
    public static ItemStack RealArtificialStar;
    public static ItemStack EdenGarden;
    public static ItemStack NeutroniumWireCutting;
    public static ItemStack LargeSteamCrusher;
    public static ItemStack ComponentAssembler;
    public static ItemStack LargeSteamFurnace;
    public static ItemStack LargeSteamAlloySmelter;
    public static ItemStack LargeSteamThermalCentrifuge;
    public static ItemStack LargeSteamChemicalBath;
    public static ItemStack SteamCracking;
    public static ItemStack PrimitiveDistillationTower;
    public static ItemStack CheatOreProcessingFactory;
    public static ItemStack MeteorMiner;
    public static ItemStack Desulfurizer;
    public static ItemStack LargeCircuitAssembler;
    public static ItemStack PetrochemicalPlant;
    public static ItemStack SmeltingMixingFurnace;
    public static ItemStack WhiteNightGenerator;
    public static ItemStack ProcessingArray;
    public static ItemStack MegaBlastFurnace;
    public static ItemStack BrickedBlastFurnace;
    public static ItemStack RareEarthCentrifugal;
    public static ItemStack NineIndustrialMultiMachine;
    public static ItemStack ColdIceFreezer;
    public static ItemStack BlazeBlastFurnace;
    public static ItemStack ChemicalPlant;
    public static ItemStack VacuumFreezer;
    public static ItemStack IndustrialArcaneAssembler;
    public static ItemStack EnergeticPhotovoltaicPowerStation;
    public static ItemStack AdvancedPhotovoltaicPowerStation;
    public static ItemStack VibrantPhotovoltaicPowerStation;
    public static ItemStack LargeMacerationTower;
    public static ItemStack HandOfJohnDavisonRockefeller;
    public static ItemStack LargeSiftingFunnel;
    public static ItemStack LargeCutter;
    public static ItemStack LargeBrewer;
    public static ItemStack LargeIndustrialLathe;
    public static ItemStack LargeMaterialPress;
    public static ItemStack LargeWiremill;
    public static ItemStack LargeBender;
    public static ItemStack ElectricImplosionCompressor;
    public static ItemStack LargeExtruder;
    public static ItemStack LargeArcSmelter;
    public static ItemStack LargeForming;
    public static ItemStack MatterFabricator;
    public static ItemStack LargeElectrolyzer;
    public static ItemStack LargeElectromagnet;
    public static ItemStack LargeAssembler;
    public static ItemStack LargeMixer;
    public static ItemStack LargeCentrifuge;
    public static ItemStack LibraryOfRuina;
    public static ItemStack LargeChemicalBath;
    public static ItemStack LargeAutoclave;
    public static ItemStack LargeSolidifier;
    public static ItemStack LargeExtractor;

    public static void loadMachines() {

        /*
         * hatch_AutoSterileMaintenanceHatch.set(
         * new MTEHatchSterileMaintenance(
         * 21001,
         * "gtnl.sterile.maintenance",
         * "Auto-Taping Sterile Maintenance Hatch",
         * 14).getStackForm(1L));
         * MTEInfinityHatchOutputBusME.set(
         * new MTEInfinityHatchOutputBusME(21002, "gtnl.ae2.InfinityOutputBusME", "Infinity Output Bus (ME)", 4)
         * .getStackForm(1L));
         * MTEInfinityHatchOutputME.set(
         * new MTEInfinityHatchOutputME(21003, "gtnl.ae2.InfinityOutputME", "Infinity Output Hatch (ME)", 3)
         * .getStackForm(1L));
         */
        EdenGarden = new EdenGarden(21004, "NameEdenGarden", TextLocalization.NameEdenGarden).getStackForm(1);
        GTNLItemList.EdenGarden.set(EdenGarden);

        LargeSteamCircuitAssembler = new LargeSteamCircuitAssembler(
            21005,
            "NameLargeSteamCircuitAssembler",
            TextLocalization.NameLargeSteamCircuitAssembler).getStackForm(1);
        GTNLItemList.LargeSteamCircuitAssembler.set(LargeSteamCircuitAssembler);

        GenerationEarthEngine = new GenerationEarthEngine(
            21006,
            "NameGenerationEarthEngine",
            TextLocalization.NameGenerationEarthEngine).getStackForm(1);
        GTNLItemList.GenerationEarthEngine.set(GenerationEarthEngine);

        BloodSoulSacrificialArray = new BloodSoulSacrificialArray(
            21007,
            "NameBloodSoulSacrificialArray",
            TextLocalization.NameBloodSoulSacrificialArray).getStackForm(1);
        GTNLItemList.BloodSoulSacrificialArray.set(BloodSoulSacrificialArray);

        RealArtificialStar = new RealArtificialStar(
            21008,
            "NameRealArtificialStar",
            TextLocalization.NameRealArtificialStar).getStackForm(1);
        GTNLItemList.RealArtificialStar.set(RealArtificialStar);

        TeleportationArrayToAlfheim = new TeleportationArrayToAlfheim(
            21009,
            "NameTeleportationArrayToAlfheim",
            TextLocalization.NameTeleportationArrayToAlfheim).getStackForm(1);
        GTNLItemList.TeleportationArrayToAlfheim.set(TeleportationArrayToAlfheim);

        LapotronChip = new LapotronChip(21010, "NameLapotronChip", TextLocalization.NameLapotronChip).getStackForm(1);
        GTNLItemList.LapotronChip.set(LapotronChip);

        NeutroniumWireCutting = new NeutroniumWireCutting(
            21011,
            "NameNeutroniumWireCutting",
            TextLocalization.NameNeutroniumWireCutting).getStackForm(1);
        GTNLItemList.NeutroniumWireCutting.set(NeutroniumWireCutting);

        LargeSteamCrusher = new LargeSteamCrusher(
            21012,
            "NameLargeSteamCrusher",
            TextLocalization.NameLargeSteamCrusher).getStackForm(1);
        GTNLItemList.LargeSteamCrusher.set(LargeSteamCrusher);

        ComponentAssembler = new ComponentAssembler(
            21013,
            "NameComponentAssembler",
            TextLocalization.NameComponentAssembler).getStackForm(1);
        GTNLItemList.ComponentAssembler.set(ComponentAssembler);

        LargeSteamFurnace = new LargeSteamFurnace(
            21014,
            "NameLargeSteamFurnace",
            TextLocalization.NameLargeSteamFurnace).getStackForm(1);
        GTNLItemList.LargeSteamFurnace.set(LargeSteamFurnace);

        LargeSteamAlloySmelter = new LargeSteamAlloySmelter(
            21015,
            "NameLargeSteamAlloySmelter",
            TextLocalization.NameLargeSteamAlloySmelter).getStackForm(1);
        GTNLItemList.LargeSteamAlloySmelter.set(LargeSteamAlloySmelter);

        LargeSteamThermalCentrifuge = new LargeSteamThermalCentrifuge(
            21016,
            "NameLargeSteamThermalCentrifuge",
            TextLocalization.NameLargeSteamThermalCentrifuge).getStackForm(1);
        GTNLItemList.LargeSteamThermalCentrifuge.set(LargeSteamThermalCentrifuge);

        SteamCracking = new SteamCracking(21017, "NameSteamCracking", TextLocalization.NameSteamCracking)
            .getStackForm(1);
        GTNLItemList.SteamCracking.set(SteamCracking);

        LargeSteamChemicalBath = new LargeSteamChemicalBath(
            21018,
            "NameLargeSteamChemicalBath",
            TextLocalization.NameLargeSteamChemicalBath).getStackForm(1);
        GTNLItemList.LargeSteamChemicalBath.set(LargeSteamChemicalBath);

        PrimitiveDistillationTower = new PrimitiveDistillationTower(
            21019,
            "NamePrimitiveDistillationTower",
            TextLocalization.NamePrimitiveDistillationTower).getStackForm(1);
        GTNLItemList.PrimitiveDistillationTower.set(PrimitiveDistillationTower);

        MeteorMiner = new MeteorMiner(21020, "NameMeteorMiner", TextLocalization.NameMeteorMiner).getStackForm(1);
        GTNLItemList.MeteorMiner.set(MeteorMiner);

        Desulfurizer = new Desulfurizer(21021, "NameDesulfurizer", TextLocalization.NameDesulfurizer).getStackForm(1);
        GTNLItemList.Desulfurizer.set(Desulfurizer);

        LargeCircuitAssembler = new LargeCircuitAssembler(
            21022,
            "NameLargeCircuitAssembler",
            TextLocalization.NameLargeCircuitAssembler).getStackForm(1);
        GTNLItemList.LargeCircuitAssembler.set(LargeCircuitAssembler);

        PetrochemicalPlant = new PetrochemicalPlant(
            21023,
            "NamePetrochemicalPlant",
            TextLocalization.NamePetrochemicalPlant).getStackForm(1);
        GTNLItemList.PetrochemicalPlant.set(PetrochemicalPlant);

        SmeltingMixingFurnace = new SmeltingMixingFurnace(
            21024,
            "NameSmeltingMixingFurnace",
            TextLocalization.NameSmeltingMixingFurnace).getStackForm(1);
        GTNLItemList.SmeltingMixingFurnace.set(SmeltingMixingFurnace);

        WhiteNightGenerator = new WhiteNightGenerator(
            21025,
            "NameWhiteNightGenerator",
            TextLocalization.NameWhiteNightGenerator).getStackForm(1);
        GTNLItemList.WhiteNightGenerator.set(WhiteNightGenerator);

        ProcessingArray = new ProcessingArray(21026, "NameProcessingArray", TextLocalization.NameProcessingArray)
            .getStackForm(1);
        GTNLItemList.ProcessingArray.set(ProcessingArray);

        MegaBlastFurnace = new MegaBlastFurnace(21027, "NameMegaBlastFurnace", TextLocalization.NameMegaBlastFurnace)
            .getStackForm(1);
        GTNLItemList.MegaBlastFurnace.set(MegaBlastFurnace);

        BrickedBlastFurnace = new BrickedBlastFurnace(
            21028,
            "NameBrickedBlastFurnace",
            TextLocalization.NameBrickedBlastFurnace).getStackForm(1);
        GTNLItemList.BrickedBlastFurnace.set(BrickedBlastFurnace);

        RareEarthCentrifugal = new RareEarthCentrifugal(
            21029,
            "NameRareEarthCentrifugal",
            TextLocalization.NameRareEarthCentrifugal).getStackForm(1);
        GTNLItemList.RareEarthCentrifugal.set(RareEarthCentrifugal);

        ColdIceFreezer = new ColdIceFreezer(21030, "NameColdIceFreezer", TextLocalization.NameColdIceFreezer)
            .getStackForm(1);
        GTNLItemList.ColdIceFreezer.set(ColdIceFreezer);

        BlazeBlastFurnace = new BlazeBlastFurnace(
            21031,
            "NameBlazeBlastFurnace",
            TextLocalization.NameBlazeBlastFurnace).getStackForm(1);
        GTNLItemList.BlazeBlastFurnace.set(BlazeBlastFurnace);

        ChemicalPlant = new ChemicalPlant(21032, "NameChemicalPlant", TextLocalization.NameChemicalPlant)
            .getStackForm(1);
        GTNLItemList.ChemicalPlant.set(ChemicalPlant);

        VacuumFreezer = new VacuumFreezer(21033, "NameVacuumFreezer", TextLocalization.NameVacuumFreezer)
            .getStackForm(1);
        GTNLItemList.VacuumFreezer.set(VacuumFreezer);

        IndustrialArcaneAssembler = new IndustrialArcaneAssembler(
            21034,
            "NameIndustrialArcaneAssembler",
            TextLocalization.NameIndustrialArcaneAssembler).getStackForm(1);
        GTNLItemList.IndustrialArcaneAssembler.set(IndustrialArcaneAssembler);

        EnergeticPhotovoltaicPowerStation = new EnergeticPhotovoltaicPowerStation(
            21035,
            "NameEnergeticPhotovoltaicPowerStation",
            TextLocalization.NameEnergeticPhotovoltaicPowerStation).getStackForm(1);
        GTNLItemList.EnergeticPhotovoltaicPowerStation.set(EnergeticPhotovoltaicPowerStation);

        AdvancedPhotovoltaicPowerStation = new AdvancedPhotovoltaicPowerStation(
            21036,
            "NameAdvancedPhotovoltaicPowerStation",
            TextLocalization.NameAdvancedPhotovoltaicPowerStation).getStackForm(1);
        GTNLItemList.AdvancedPhotovoltaicPowerStation.set(AdvancedPhotovoltaicPowerStation);

        VibrantPhotovoltaicPowerStation = new VibrantPhotovoltaicPowerStation(
            21037,
            "NameVibrantPhotovoltaicPowerStation",
            TextLocalization.NameVibrantPhotovoltaicPowerStation).getStackForm(1);
        GTNLItemList.VibrantPhotovoltaicPowerStation.set(VibrantPhotovoltaicPowerStation);

        LargeMacerationTower = new LargeMacerationTower(
            21038,
            "NameLargeMacerationTower",
            TextLocalization.NameLargeMacerationTower).getStackForm(1);
        GTNLItemList.LargeMacerationTower.set(LargeMacerationTower);

        HandOfJohnDavisonRockefeller = new HandOfJohnDavisonRockefeller(
            21039,
            "NameHandOfJohnDavisonRockefeller",
            TextLocalization.NameHandOfJohnDavisonRockefeller).getStackForm(1);
        GTNLItemList.HandOfJohnDavisonRockefeller.set(HandOfJohnDavisonRockefeller);

        LargeSiftingFunnel = new LargeSiftingFunnel(
            21040,
            "NameLargeSiftingFunnel",
            TextLocalization.NameLargeSiftingFunnel).getStackForm(1);
        GTNLItemList.LargeSiftingFunnel.set(LargeSiftingFunnel);

        LargeCutter = new LargeCutter(21041, "NameLargeCutter", TextLocalization.NameLargeCutter).getStackForm(1);
        GTNLItemList.LargeCutter.set(LargeCutter);

        LargeBrewer = new LargeBrewer(21042, "NameLargeBrewer", TextLocalization.NameLargeBrewer).getStackForm(1);
        GTNLItemList.LargeBrewer.set(LargeBrewer);

        LargeIndustrialLathe = new LargeIndustrialLathe(
            21043,
            "NameLargeIndustrialLathe",
            TextLocalization.NameLargeIndustrialLathe).getStackForm(1);
        GTNLItemList.LargeIndustrialLathe.set(LargeIndustrialLathe);

        LargeMaterialPress = new LargeMaterialPress(
            21044,
            "NameLargeMaterialPress",
            TextLocalization.NameLargeMaterialPress).getStackForm(1);
        GTNLItemList.LargeMaterialPress.set(LargeMaterialPress);

        LargeWiremill = new LargeWiremill(21045, "NameLargeWiremill", TextLocalization.NameLargeWiremill)
            .getStackForm(1);
        GTNLItemList.LargeWiremill.set(LargeWiremill);

        LargeBender = new LargeBender(21046, "NameLargeBender", TextLocalization.NameLargeBender).getStackForm(1);
        GTNLItemList.LargeBender.set(LargeBender);

        ElectricImplosionCompressor = new ElectricImplosionCompressor(
            21047,
            "NameElectricImplosionCompressor",
            TextLocalization.NameElectricImplosionCompressor).getStackForm(1);
        GTNLItemList.ElectricImplosionCompressor.set(ElectricImplosionCompressor);

        LargeExtruder = new LargeExtruder(21048, "NameLargeExtruder", TextLocalization.NameLargeExtruder)
            .getStackForm(1);
        GTNLItemList.LargeExtruder.set(LargeExtruder);

        LargeArcSmelter = new LargeArcSmelter(21049, "NameLargeArcSmelter", TextLocalization.NameLargeArcSmelter)
            .getStackForm(1);
        GTNLItemList.LargeArcSmelter.set(LargeArcSmelter);

        LargeForming = new LargeForming(21050, "NameLargeForming", TextLocalization.NameLargeForming).getStackForm(1);
        GTNLItemList.LargeForming.set(LargeForming);

        MatterFabricator = new MatterFabricator(21051, "NameMatterFabricator", TextLocalization.NameMatterFabricator)
            .getStackForm(1);
        GTNLItemList.MatterFabricator.set(MatterFabricator);

        LargeElectrolyzer = new LargeElectrolyzer(
            21052,
            "NameLargeElectrolyzer",
            TextLocalization.NameLargeElectrolyzer).getStackForm(1);
        GTNLItemList.LargeElectrolyzer.set(LargeElectrolyzer);

        LargeElectromagnet = new LargeElectromagnet(
            21053,
            "NameLargeElectromagnet",
            TextLocalization.NameLargeElectromagnet).getStackForm(1);
        GTNLItemList.LargeElectromagnet.set(LargeElectromagnet);

        LargeAssembler = new LargeAssembler(21054, "NameLargeAssembler", TextLocalization.NameLargeAssembler)
            .getStackForm(1);
        GTNLItemList.LargeAssembler.set(LargeAssembler);

        LargeMixer = new LargeMixer(21055, "NameLargeMixer", TextLocalization.NameLargeMixer).getStackForm(1);
        GTNLItemList.LargeMixer.set(LargeMixer);

        LargeCentrifuge = new LargeCentrifuge(21056, "NameLargeCentrifuge", TextLocalization.NameLargeCentrifuge)
            .getStackForm(1);
        GTNLItemList.LargeCentrifuge.set(LargeCentrifuge);

        LibraryOfRuina = new LibraryOfRuina(21057, "NameLibraryOfRuina", TextLocalization.NameLibraryOfRuina)
            .getStackForm(1);
        GTNLItemList.LibraryOfRuina.set(LibraryOfRuina);

        LargeChemicalBath = new LargeChemicalBath(
            21058,
            "NameLargeChemicalBath",
            TextLocalization.NameLargeChemicalBath).getStackForm(1);
        GTNLItemList.LargeChemicalBath.set(LargeChemicalBath);

        LargeAutoclave = new LargeAutoclave(21059, "NameLargeAutoclave", TextLocalization.NameLargeAutoclave)
            .getStackForm(1);
        GTNLItemList.LargeAutoclave.set(LargeAutoclave);

        LargeSolidifier = new LargeSolidifier(21060, "NameLargeSolidifier", TextLocalization.NameLargeSolidifier)
            .getStackForm(1);
        GTNLItemList.LargeSolidifier.set(LargeSolidifier);

        LargeExtractor = new LargeExtractor(21061, "NameLargeExtractor", TextLocalization.NameLargeExtractor)
            .getStackForm(1);
        GTNLItemList.LargeExtractor.set(LargeExtractor);

        CheatOreProcessingFactory = new CheatOreProcessingFactory(
            21919,
            "NameCheatOreProcessingFactory",
            TextLocalization.NameCheatOreProcessingFactory).getStackForm(1);
        GTNLItemList.CheatOreProcessingFactory.set(CheatOreProcessingFactory);

        NineIndustrialMultiMachine = new NineIndustrialMultiMachine(
            21920,
            "NameNineIndustrialMultiMachine",
            TextLocalization.NameNineIndustrialMultiMachine).getStackForm(1);
        GTNLItemList.NineIndustrialMultiMachine.set(NineIndustrialMultiMachine);

        MTEHatchRack.run();
        MTEHatchCapacitor.run();
    }

    public static void registerMTEHatch() {

        GTNLItemList.FluidManaInputHatch.set(
            new MTEHatchCustomFluid(
                FluidUtils.getFluidStack("fluidmana", 1)
                    .getFluid(),
                512000,
                21501,
                "Fluid Mana Input Hatch",
                TextLocalization.FluidManaInputHatch,
                6).getStackForm(1L));

        GTNLItemList.FluidIceInputHatch.set(
            new MTEHatchCustomFluid(
                FluidUtils.getFluidStack("ice", 1)
                    .getFluid(),
                256000,
                21502,
                "Fluid Ice Input Hatch",
                TextLocalization.FluidIceInputHatch,
                5).getStackForm(1L));

        GTNLItemList.FluidBlazeInputHatch.set(
            new MTEHatchCustomFluid(
                FluidUtils.getFluidStack("molten.blaze", 1)
                    .getFluid(),
                256000,
                21503,
                "Fluid Blaze Input Hatch",
                TextLocalization.FluidBlazeInputHatch,
                5).getStackForm(1L));
    }

    public static void run() {
        Logger.INFO("GTNL Content | Registering Custom MTE Hatches.");
        registerMTEHatch();
    }
}
