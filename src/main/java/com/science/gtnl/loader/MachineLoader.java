package com.science.gtnl.loader;

import static com.science.gtnl.Utils.enums.GTNLMachineID.*;
import static com.science.gtnl.Utils.text.AnimatedTooltipHandler.*;
import static gregtech.api.enums.Textures.BlockIcons.*;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;

import com.google.common.collect.ImmutableSet;
import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.Utils.enums.GTNLMachineID;
import com.science.gtnl.Utils.enums.ModList;
import com.science.gtnl.Utils.item.ItemUtils;
import com.science.gtnl.Utils.text.AnimatedText;
import com.science.gtnl.common.machine.basicMachine.DebugResearchStation;
import com.science.gtnl.common.machine.basicMachine.DieselGenerator;
import com.science.gtnl.common.machine.basicMachine.Enchanting;
import com.science.gtnl.common.machine.basicMachine.GasTurbine;
import com.science.gtnl.common.machine.basicMachine.LootBagRedemption;
import com.science.gtnl.common.machine.basicMachine.ManaTank;
import com.science.gtnl.common.machine.basicMachine.Replicator;
import com.science.gtnl.common.machine.basicMachine.SteamAssemblerBronze;
import com.science.gtnl.common.machine.basicMachine.SteamAssemblerSteel;
import com.science.gtnl.common.machine.basicMachine.SteamTurbine;
import com.science.gtnl.common.machine.cover.VoidCover;
import com.science.gtnl.common.machine.cover.WirelessSteamCover;
import com.science.gtnl.common.machine.hatch.CustomFluidHatch;
import com.science.gtnl.common.machine.hatch.CustomMaintenanceHatch;
import com.science.gtnl.common.machine.hatch.DebugDataAccessHatch;
import com.science.gtnl.common.machine.hatch.DebugEnergyHatch;
import com.science.gtnl.common.machine.hatch.DualInputHatch;
import com.science.gtnl.common.machine.hatch.DualOutputHatch;
import com.science.gtnl.common.machine.hatch.EnergyTransferNode;
import com.science.gtnl.common.machine.hatch.ExplosionDynamoHatch;
import com.science.gtnl.common.machine.hatch.HumongousDualInputHatch;
import com.science.gtnl.common.machine.hatch.HumongousNinefoldInputHatch;
import com.science.gtnl.common.machine.hatch.HumongousSolidifierHatch;
import com.science.gtnl.common.machine.hatch.ManaDynamoHatch;
import com.science.gtnl.common.machine.hatch.ManaEnergyHatch;
import com.science.gtnl.common.machine.hatch.ModFilteredInputBusME;
import com.science.gtnl.common.machine.hatch.NanitesInputBus;
import com.science.gtnl.common.machine.hatch.NinefoldInputHatch;
import com.science.gtnl.common.machine.hatch.OredictInputBusME;
import com.science.gtnl.common.machine.hatch.OriginalInputHatch;
import com.science.gtnl.common.machine.hatch.OriginalOutputHatch;
import com.science.gtnl.common.machine.hatch.ParallelControllerHatch;
import com.science.gtnl.common.machine.hatch.SuperCraftingInputHatchME;
import com.science.gtnl.common.machine.hatch.SuperCraftingInputProxy;
import com.science.gtnl.common.machine.hatch.SuperDataAccessHatch;
import com.science.gtnl.common.machine.hatch.SuperInputBusME;
import com.science.gtnl.common.machine.hatch.SuperInputHatchME;
import com.science.gtnl.common.machine.hatch.SuperVoidBus;
import com.science.gtnl.common.machine.hatch.SuperVoidHatch;
import com.science.gtnl.common.machine.hatch.TapDynamoHatch;
import com.science.gtnl.common.machine.hatch.VaultPortHatch;
import com.science.gtnl.common.machine.hatch.WirelessSteamDynamoHatch;
import com.science.gtnl.common.machine.hatch.WirelessSteamEnergyHatch;
import com.science.gtnl.common.machine.multiblock.AdvancedCircuitAssemblyLine;
import com.science.gtnl.common.machine.multiblock.AdvancedInfiniteDriller;
import com.science.gtnl.common.machine.multiblock.AprilFool.HighPressureSteamFusionReactor;
import com.science.gtnl.common.machine.multiblock.AprilFool.MegaSolarBoiler;
import com.science.gtnl.common.machine.multiblock.AprilFool.MegaSteamCompressor;
import com.science.gtnl.common.machine.multiblock.AprilFool.SteamCactusWonder;
import com.science.gtnl.common.machine.multiblock.AprilFool.SteamCarpenter;
import com.science.gtnl.common.machine.multiblock.AprilFool.SteamExtractinator;
import com.science.gtnl.common.machine.multiblock.AprilFool.SteamFusionReactor;
import com.science.gtnl.common.machine.multiblock.AprilFool.SteamGateAssembler;
import com.science.gtnl.common.machine.multiblock.AprilFool.SteamInfernalCokeOven;
import com.science.gtnl.common.machine.multiblock.AprilFool.SteamLavaMaker;
import com.science.gtnl.common.machine.multiblock.AprilFool.SteamManufacturer;
import com.science.gtnl.common.machine.multiblock.AprilFool.SteamRockBreaker;
import com.science.gtnl.common.machine.multiblock.AprilFool.SteamWoodcutter;
import com.science.gtnl.common.machine.multiblock.AprilFool.Steamgate;
import com.science.gtnl.common.machine.multiblock.BloodSoulSacrificialArray;
import com.science.gtnl.common.machine.multiblock.BrickedBlastFurnace;
import com.science.gtnl.common.machine.multiblock.CheatOreProcessingFactory;
import com.science.gtnl.common.machine.multiblock.ComponentAssembler;
import com.science.gtnl.common.machine.multiblock.DecayHastener;
import com.science.gtnl.common.machine.multiblock.Desulfurizer;
import com.science.gtnl.common.machine.multiblock.DraconicFusionCrafting;
import com.science.gtnl.common.machine.multiblock.EdenGarden;
import com.science.gtnl.common.machine.multiblock.ElectrocellGenerator;
import com.science.gtnl.common.machine.multiblock.ElementCopying;
import com.science.gtnl.common.machine.multiblock.EyeOfHarmonyInjector;
import com.science.gtnl.common.machine.multiblock.FOGAlloyBlastSmelterModule;
import com.science.gtnl.common.machine.multiblock.FOGAlloySmelterModule;
import com.science.gtnl.common.machine.multiblock.FOGExtractorModule;
import com.science.gtnl.common.machine.multiblock.FuelRefiningComplex;
import com.science.gtnl.common.machine.multiblock.GenerationEarthEngine;
import com.science.gtnl.common.machine.multiblock.GrandAssemblyLine;
import com.science.gtnl.common.machine.multiblock.IndustrialArcaneAssembler;
import com.science.gtnl.common.machine.multiblock.LapotronChip;
import com.science.gtnl.common.machine.multiblock.LargeBioLab;
import com.science.gtnl.common.machine.multiblock.LargeBrewer;
import com.science.gtnl.common.machine.multiblock.LargeCircuitAssembler;
import com.science.gtnl.common.machine.multiblock.LargeGasCollector;
import com.science.gtnl.common.machine.multiblock.LargeIncubator;
import com.science.gtnl.common.machine.multiblock.LargeNaquadahReactor;
import com.science.gtnl.common.machine.multiblock.LargeSteamAlloySmelter;
import com.science.gtnl.common.machine.multiblock.LargeSteamChemicalBath;
import com.science.gtnl.common.machine.multiblock.LargeSteamCircuitAssembler;
import com.science.gtnl.common.machine.multiblock.LargeSteamCrusher;
import com.science.gtnl.common.machine.multiblock.LargeSteamCutting;
import com.science.gtnl.common.machine.multiblock.LargeSteamExtractor;
import com.science.gtnl.common.machine.multiblock.LargeSteamExtruder;
import com.science.gtnl.common.machine.multiblock.LargeSteamFormingPress;
import com.science.gtnl.common.machine.multiblock.LargeSteamFurnace;
import com.science.gtnl.common.machine.multiblock.LargeSteamLathe;
import com.science.gtnl.common.machine.multiblock.LargeSteamSifter;
import com.science.gtnl.common.machine.multiblock.LargeSteamThermalCentrifuge;
import com.science.gtnl.common.machine.multiblock.LibraryOfRuina;
import com.science.gtnl.common.machine.multiblock.MatterFabricator;
import com.science.gtnl.common.machine.multiblock.MegaMixer;
import com.science.gtnl.common.machine.multiblock.MeteorMiner;
import com.science.gtnl.common.machine.multiblock.ModuleMachine.EternalGregTechWorkshop.EGTWFusionModule;
import com.science.gtnl.common.machine.multiblock.ModuleMachine.EternalGregTechWorkshop.ETGWEyeOfHarmonyModule;
import com.science.gtnl.common.machine.multiblock.ModuleMachine.EternalGregTechWorkshop.EternalGregTechWorkshop;
import com.science.gtnl.common.machine.multiblock.ModuleMachine.NanitesIntegratedProcessingCenter.BioengineeringModule;
import com.science.gtnl.common.machine.multiblock.ModuleMachine.NanitesIntegratedProcessingCenter.NanitesIntegratedProcessingCenter;
import com.science.gtnl.common.machine.multiblock.ModuleMachine.SteamElevator.SteamApiaryModule;
import com.science.gtnl.common.machine.multiblock.ModuleMachine.SteamElevator.SteamBeaconModule;
import com.science.gtnl.common.machine.multiblock.ModuleMachine.SteamElevator.SteamBeeBreedingModule;
import com.science.gtnl.common.machine.multiblock.ModuleMachine.SteamElevator.SteamElevator;
import com.science.gtnl.common.machine.multiblock.ModuleMachine.SteamElevator.SteamEntityCrusherModule;
import com.science.gtnl.common.machine.multiblock.ModuleMachine.SteamElevator.SteamFlightModule;
import com.science.gtnl.common.machine.multiblock.ModuleMachine.SteamElevator.SteamGreenhouseModule;
import com.science.gtnl.common.machine.multiblock.ModuleMachine.SteamElevator.SteamMonsterRepellentModule;
import com.science.gtnl.common.machine.multiblock.ModuleMachine.SteamElevator.SteamOreProcessorModule;
import com.science.gtnl.common.machine.multiblock.ModuleMachine.SteamElevator.SteamWeatherModule;
import com.science.gtnl.common.machine.multiblock.OreExtractionModule;
import com.science.gtnl.common.machine.multiblock.PetrochemicalPlant;
import com.science.gtnl.common.machine.multiblock.PhotovoltaicPowerStation;
import com.science.gtnl.common.machine.multiblock.PlatinumBasedTreatment;
import com.science.gtnl.common.machine.multiblock.PolymerTwistingModule;
import com.science.gtnl.common.machine.multiblock.PrimitiveBrickKiln;
import com.science.gtnl.common.machine.multiblock.PrimitiveDistillationTower;
import com.science.gtnl.common.machine.multiblock.ProcessingArray;
import com.science.gtnl.common.machine.multiblock.RareEarthCentrifugal;
import com.science.gtnl.common.machine.multiblock.ReactionFurnace;
import com.science.gtnl.common.machine.multiblock.RealArtificialStar;
import com.science.gtnl.common.machine.multiblock.ResourceCollectionModule;
import com.science.gtnl.common.machine.multiblock.ShallowChemicalCoupling;
import com.science.gtnl.common.machine.multiblock.SingularityDataHub;
import com.science.gtnl.common.machine.multiblock.SpaceAssembler;
import com.science.gtnl.common.machine.multiblock.SteamCracking;
import com.science.gtnl.common.machine.multiblock.SteamItemVault;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.AlloyBlastSmelter;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.BlazeBlastFurnace;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.ChemicalPlant;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.ColdIceFreezer;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.Digester;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.ElectricBlastFurnace;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.ElectricImplosionCompressor;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.EnergyInfuser;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.FishingGround;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.FlotationCellRegulator;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.HighPerformanceComputationArray;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.Incubator;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.IsaMill;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.KuangBiaoOneGiantNuclearFusionReactor;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeAlloySmelter;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeArcSmelter;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeAssembler;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeAutoclave;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeBender;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeBoiler;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeCanning;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeCentrifuge;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeChemicalBath;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeCutter;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeDistillery;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeElectrolyzer;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeElectromagnet;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeEngravingLaser;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeExtractor;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeExtruder;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeForming;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeHammer;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeIndustrialLathe;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeMacerationTower;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeMaterialPress;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeMixer;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargePacker;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargePyrolyseOven;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeSiftingFunnel;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeSolidifier;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeSteamCentrifuge;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeSteamCompressor;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeSteamHammer;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeSteamMixer;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeSteamOreWasher;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeWiremill;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.MegaAlloyBlastSmelter;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.MegaBlastFurnace;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.MolecularTransformer;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.PrecisionAssembler;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.RocketAssembler;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.VacuumDryingFurnace;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.VacuumFreezer;
import com.science.gtnl.common.machine.multiblock.SuperSpaceElevator;
import com.science.gtnl.common.machine.multiblock.TeleportationArrayToAlfheim;
import com.science.gtnl.common.machine.multiblock.WhiteNightGenerator;
import com.science.gtnl.common.machine.multiblock.WirelessMachine.AetronPressor;
import com.science.gtnl.common.machine.multiblock.WirelessMachine.CompoundExtremeCoolingUnit;
import com.science.gtnl.common.machine.multiblock.WirelessMachine.CrackerHub;
import com.science.gtnl.common.machine.multiblock.WirelessMachine.EngravingLaserPlant;
import com.science.gtnl.common.machine.multiblock.WirelessMachine.FieldForgePress;
import com.science.gtnl.common.machine.multiblock.WirelessMachine.HandOfJohnDavisonRockefeller;
import com.science.gtnl.common.machine.multiblock.WirelessMachine.HeavyRolling;
import com.science.gtnl.common.machine.multiblock.WirelessMachine.HighEnergyLaserLathe;
import com.science.gtnl.common.machine.multiblock.WirelessMachine.IntegratedAssemblyFacility;
import com.science.gtnl.common.machine.multiblock.WirelessMachine.MagneticEnergyReactionFurnace;
import com.science.gtnl.common.machine.multiblock.WirelessMachine.NanitesCircuitAssemblyFactory;
import com.science.gtnl.common.machine.multiblock.WirelessMachine.NanoAssemblerMarkL;
import com.science.gtnl.common.machine.multiblock.WirelessMachine.NanoPhagocytosisPlant;
import com.science.gtnl.common.machine.multiblock.WirelessMachine.NeutroniumWireCutting;
import com.science.gtnl.common.machine.multiblock.WirelessMachine.NineIndustrialMultiMachine;
import com.science.gtnl.common.machine.multiblock.WirelessMachine.SmeltingMixingFurnace;
import com.science.gtnl.common.machine.multiblock.WirelessMachine.SuperconductingElectromagnetism;
import com.science.gtnl.common.machine.multiblock.WirelessMachine.SuperconductingMagneticPresser;
import com.science.gtnl.common.machine.multiblock.WirelessMachine.VortexMatterCentrifuge;
import com.science.gtnl.common.machine.multiblock.WoodDistillation;
import com.science.gtnl.common.material.MaterialPool;
import com.science.gtnl.common.material.MaterialUtils;
import com.science.gtnl.config.MainConfig;

import bartworks.common.loaders.ItemRegistry;
import goodgenerator.util.CrackRecipeAdder;
import gregtech.api.covers.CoverRegistry;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.SoundResource;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.implementations.MTEBasicMachineWithRecipe;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GlassTier;
import gregtech.common.covers.CoverConveyor;
import gregtech.common.covers.CoverPump;
import gregtech.common.covers.CoverSteamRegulator;
import gregtech.common.covers.CoverSteamValve;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.util.minecraft.FluidUtils;

public class MachineLoader {

    public static ItemStack ResourceCollectionModule;
    public static ItemStack SuperSpaceElevator;

    public static void registerMachines() {

        GTNLItemList.EdenGarden
            .set(new EdenGarden(EDEN_GARDEN.ID, "EdenGarden", StatCollector.translateToLocal("NameEdenGarden")));
        addItemTooltip(GTNLItemList.EdenGarden.get(1), AnimatedText.SNL_EDEN_GARDEN);

        GTNLItemList.LargeSteamCircuitAssembler.set(
            new LargeSteamCircuitAssembler(
                LARGE_STEAM_CIRCUIT_ASSEMBLER.ID,
                "LargeSteamCircuitAssembler",
                StatCollector.translateToLocal("NameLargeSteamCircuitAssembler")));
        addItemTooltip(GTNLItemList.LargeSteamCircuitAssembler.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.GenerationEarthEngine.set(
            new GenerationEarthEngine(
                GENERATION_EARTH_ENGINE.ID,
                "GenerationEarthEngine",
                StatCollector.translateToLocal("NameGenerationEarthEngine")));
        addItemTooltip(GTNLItemList.GenerationEarthEngine.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.BloodSoulSacrificialArray.set(
            new BloodSoulSacrificialArray(
                BLOOD_SOUL_SACRIFICIAL_ARRAY.ID,
                "BloodSoulSacrificialArray",
                StatCollector.translateToLocal("NameBloodSoulSacrificialArray")));
        addItemTooltip(GTNLItemList.BloodSoulSacrificialArray.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.RealArtificialStar.set(
            new RealArtificialStar(
                REAL_ARTIFICIAL_STAR.ID,
                "RealArtificialStar",
                StatCollector.translateToLocal("NameRealArtificialStar")));
        addItemTooltip(GTNLItemList.RealArtificialStar.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.TeleportationArrayToAlfheim.set(
            new TeleportationArrayToAlfheim(
                TELEPORTATION_ARRAY_TO_ALFHEIM.ID,
                "TeleportationArrayToAlfheim",
                StatCollector.translateToLocal("NameTeleportationArrayToAlfheim")));
        addItemTooltip(GTNLItemList.TeleportationArrayToAlfheim.get(1), AnimatedText.SNL_NLXCJH);

        GTNLItemList.LapotronChip.set(
            new LapotronChip(LAPOTRON_CHIP.ID, "LapotronChip", StatCollector.translateToLocal("NameLapotronChip")));
        addItemTooltip(GTNLItemList.LapotronChip.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NeutroniumWireCutting.set(
            new NeutroniumWireCutting(
                NEUTRONIUM_WIRE_CUTTING.ID,
                "NeutroniumWireCutting",
                StatCollector.translateToLocal("NameNeutroniumWireCutting")));
        addItemTooltip(GTNLItemList.NeutroniumWireCutting.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeSteamCrusher.set(
            new LargeSteamCrusher(
                LARGE_STEAM_CRUSHER.ID,
                "LargeSteamCrusher",
                StatCollector.translateToLocal("NameLargeSteamCrusher")));
        addItemTooltip(GTNLItemList.LargeSteamCrusher.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.ComponentAssembler.set(
            new ComponentAssembler(
                COMPONENT_ASSEMBLER.ID,
                "ComponentAssembler",
                StatCollector.translateToLocal("NameComponentAssembler")));
        addItemTooltip(GTNLItemList.ComponentAssembler.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeSteamFurnace.set(
            new LargeSteamFurnace(
                LARGE_STEAM_FURNACE.ID,
                "LargeSteamFurnace",
                StatCollector.translateToLocal("NameLargeSteamFurnace")));
        addItemTooltip(GTNLItemList.LargeSteamFurnace.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeSteamAlloySmelter.set(
            new LargeSteamAlloySmelter(
                LARGE_STEAM_ALLOY_SMELTER.ID,
                "LargeSteamAlloySmelter",
                StatCollector.translateToLocal("NameLargeSteamAlloySmelter")));
        addItemTooltip(GTNLItemList.LargeSteamAlloySmelter.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeSteamThermalCentrifuge.set(
            new LargeSteamThermalCentrifuge(
                LARGE_STEAM_THERMAL_CENTRIFUGE.ID,
                "LargeSteamThermalCentrifuge",
                StatCollector.translateToLocal("NameLargeSteamThermalCentrifuge")));
        addItemTooltip(GTNLItemList.LargeSteamThermalCentrifuge.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.SteamCracking.set(
            new SteamCracking(STEAM_CRACKING.ID, "SteamCracking", StatCollector.translateToLocal("NameSteamCracking")));
        addItemTooltip(GTNLItemList.SteamCracking.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeSteamChemicalBath.set(
            new LargeSteamChemicalBath(
                LARGE_STEAM_CHEMICAL_BATH.ID,
                "LargeSteamChemicalBath",
                StatCollector.translateToLocal("NameLargeSteamChemicalBath")));
        addItemTooltip(GTNLItemList.LargeSteamChemicalBath.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.PrimitiveDistillationTower.set(
            new PrimitiveDistillationTower(
                PRIMITIVE_DISTILLATION_TOWER.ID,
                "PrimitiveDistillationTower",
                StatCollector.translateToLocal("NamePrimitiveDistillationTower")));
        addItemTooltip(GTNLItemList.PrimitiveDistillationTower.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.MeteorMiner
            .set(new MeteorMiner(METEOR_MINER.ID, "MeteorMiner", StatCollector.translateToLocal("NameMeteorMiner")));
        addItemTooltip(GTNLItemList.MeteorMiner.get(1), AnimatedText.SNL_TOTTO);

        GTNLItemList.Desulfurizer
            .set(new Desulfurizer(DESULFURIZER.ID, "Desulfurizer", StatCollector.translateToLocal("NameDesulfurizer")));
        addItemTooltip(GTNLItemList.Desulfurizer.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeCircuitAssembler.set(
            new LargeCircuitAssembler(
                LARGE_CIRCUIT_ASSEMBLER.ID,
                "LargeCircuitAssembler",
                StatCollector.translateToLocal("NameLargeCircuitAssembler")));
        addItemTooltip(GTNLItemList.LargeCircuitAssembler.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.PetrochemicalPlant.set(
            new PetrochemicalPlant(
                PETROCHEMICAL_PLANT.ID,
                "PetrochemicalPlant",
                StatCollector.translateToLocal("NamePetrochemicalPlant")));
        addItemTooltip(GTNLItemList.PetrochemicalPlant.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.SmeltingMixingFurnace.set(
            new SmeltingMixingFurnace(
                SMELTING_MIXING_FURNACE.ID,
                "SmeltingMixingFurnace",
                StatCollector.translateToLocal("NameSmeltingMixingFurnace")));
        addItemTooltip(GTNLItemList.SmeltingMixingFurnace.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.WhiteNightGenerator.set(
            new WhiteNightGenerator(
                WHITE_NIGHT_GENERATOR.ID,
                "WhiteNightGenerator",
                StatCollector.translateToLocal("NameWhiteNightGenerator")));
        addItemTooltip(GTNLItemList.WhiteNightGenerator.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ProcessingArray.set(
            new ProcessingArray(
                PROCESSING_ARRAY.ID,
                "ProcessingArray",
                StatCollector.translateToLocal("NameProcessingArrayGTNL")));
        addItemTooltip(GTNLItemList.ProcessingArray.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.MegaBlastFurnace.set(
            new MegaBlastFurnace(
                MEGA_BLAST_FURNACE.ID,
                "MegaBlastFurnace",
                StatCollector.translateToLocal("NameMegaBlastFurnace")));
        addItemTooltip(GTNLItemList.MegaBlastFurnace.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.BrickedBlastFurnace.set(
            new BrickedBlastFurnace(
                BRICKED_BLAST_FURNACE.ID,
                "BrickedBlastFurnace",
                StatCollector.translateToLocal("NameBrickedBlastFurnace")));
        addItemTooltip(GTNLItemList.BrickedBlastFurnace.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.RareEarthCentrifugal.set(
            new RareEarthCentrifugal(
                RARE_EARTH_CENTRIFUGAL.ID,
                "RareEarthCentrifugal",
                StatCollector.translateToLocal("NameRareEarthCentrifugal")));
        addItemTooltip(GTNLItemList.RareEarthCentrifugal.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.ColdIceFreezer.set(
            new ColdIceFreezer(
                COLD_ICE_FREEZER.ID,
                "ColdIceFreezer",
                StatCollector.translateToLocal("NameColdIceFreezer")));
        addItemTooltip(GTNLItemList.ColdIceFreezer.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.BlazeBlastFurnace.set(
            new BlazeBlastFurnace(
                BLAZE_BLAST_FURNACE.ID,
                "BlazeBlastFurnace",
                StatCollector.translateToLocal("NameBlazeBlastFurnace")));
        addItemTooltip(GTNLItemList.BlazeBlastFurnace.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.ChemicalPlant.set(
            new ChemicalPlant(CHEMICAL_PLANT.ID, "ChemicalPlant", StatCollector.translateToLocal("NameChemicalPlant")));
        addItemTooltip(GTNLItemList.ChemicalPlant.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.VacuumFreezer.set(
            new VacuumFreezer(VACUUM_FREEZER.ID, "VacuumFreezer", StatCollector.translateToLocal("NameVacuumFreezer")));
        addItemTooltip(GTNLItemList.VacuumFreezer.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.IndustrialArcaneAssembler.set(
            new IndustrialArcaneAssembler(
                INDUSTRIAL_ARCANE_ASSEMBLER.ID,
                "IndustrialArcaneAssembler",
                StatCollector.translateToLocal("NameIndustrialArcaneAssembler")));
        addItemTooltip(GTNLItemList.IndustrialArcaneAssembler.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.EnergeticPhotovoltaicPowerStation.set(
            new PhotovoltaicPowerStation.EnergeticPhotovoltaicPowerStation(
                ENERGETIC_PHOTOVOLTAIC_POWER_STATION.ID,
                "EnergeticPhotovoltaicPowerStation",
                StatCollector.translateToLocal("NameEnergeticPhotovoltaicPowerStation")));
        addItemTooltip(GTNLItemList.EnergeticPhotovoltaicPowerStation.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.AdvancedPhotovoltaicPowerStation.set(
            new PhotovoltaicPowerStation.AdvancedPhotovoltaicPowerStation(
                ADVANCED_PHOTOVOLTAIC_POWER_STATION.ID,
                "AdvancedPhotovoltaicPowerStation",
                StatCollector.translateToLocal("NameAdvancedPhotovoltaicPowerStation")));
        addItemTooltip(GTNLItemList.AdvancedPhotovoltaicPowerStation.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.VibrantPhotovoltaicPowerStation.set(
            new PhotovoltaicPowerStation.VibrantPhotovoltaicPowerStation(
                VIBRANT_PHOTOVOLTAIC_POWER_STATION.ID,
                "VibrantPhotovoltaicPowerStation",
                StatCollector.translateToLocal("NameVibrantPhotovoltaicPowerStation")));
        addItemTooltip(GTNLItemList.VibrantPhotovoltaicPowerStation.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeMacerationTower.set(
            new LargeMacerationTower(
                LARGE_MACERATION_TOWER.ID,
                "LargeMacerationTower",
                StatCollector.translateToLocal("NameLargeMacerationTower")));
        addItemTooltip(GTNLItemList.LargeMacerationTower.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.HandOfJohnDavisonRockefeller.set(
            new HandOfJohnDavisonRockefeller(
                HAND_OF_JOHN_DAVISON_ROCKEFELLER.ID,
                "HandOfJohnDavisonRockefeller",
                StatCollector.translateToLocal("NameHandOfJohnDavisonRockefeller")));
        addItemTooltip(GTNLItemList.HandOfJohnDavisonRockefeller.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeSiftingFunnel.set(
            new LargeSiftingFunnel(
                LARGE_SIFTING_FUNNEL.ID,
                "LargeSiftingFunnel",
                StatCollector.translateToLocal("NameLargeSiftingFunnel")));
        addItemTooltip(GTNLItemList.LargeSiftingFunnel.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeCutter
            .set(new LargeCutter(LARGE_CUTTER.ID, "LargeCutter", StatCollector.translateToLocal("NameLargeCutter")));
        addItemTooltip(GTNLItemList.LargeCutter.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeBrewer
            .set(new LargeBrewer(LARGE_BREWER.ID, "LargeBrewer", StatCollector.translateToLocal("NameLargeBrewer")));
        addItemTooltip(GTNLItemList.LargeBrewer.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeIndustrialLathe.set(
            new LargeIndustrialLathe(
                LARGE_INDUSTRIAL_LATHE.ID,
                "LargeIndustrialLathe",
                StatCollector.translateToLocal("NameLargeIndustrialLathe")));
        addItemTooltip(GTNLItemList.LargeIndustrialLathe.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeMaterialPress.set(
            new LargeMaterialPress(
                LARGE_MATERIAL_PRESS.ID,
                "LargeMaterialPress",
                StatCollector.translateToLocal("NameLargeMaterialPress")));
        addItemTooltip(GTNLItemList.LargeMaterialPress.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeWiremill.set(
            new LargeWiremill(LARGE_WIREMILL.ID, "LargeWiremill", StatCollector.translateToLocal("NameLargeWiremill")));
        addItemTooltip(GTNLItemList.LargeWiremill.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeBender
            .set(new LargeBender(LARGE_BENDER.ID, "LargeBender", StatCollector.translateToLocal("NameLargeBender")));
        addItemTooltip(GTNLItemList.LargeBender.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.ElectricImplosionCompressor.set(
            new ElectricImplosionCompressor(
                ELECTRIC_IMPLOSION_COMPRESSOR.ID,
                "ElectricImplosionCompressor",
                StatCollector.translateToLocal("NameElectricImplosionCompressor")));
        addItemTooltip(GTNLItemList.ElectricImplosionCompressor.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.LargeExtruder.set(
            new LargeExtruder(LARGE_EXTRUDER.ID, "LargeExtruder", StatCollector.translateToLocal("NameLargeExtruder")));
        addItemTooltip(GTNLItemList.LargeExtruder.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeArcSmelter.set(
            new LargeArcSmelter(
                LARGE_ARC_SMELTER.ID,
                "LargeArcSmelter",
                StatCollector.translateToLocal("NameLargeArcSmelter")));
        addItemTooltip(GTNLItemList.LargeArcSmelter.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeForming.set(
            new LargeForming(LARGE_FORMING.ID, "LargeForming", StatCollector.translateToLocal("NameLargeForming")));
        addItemTooltip(GTNLItemList.LargeForming.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.MatterFabricator.set(
            new MatterFabricator(
                MATTER_FABRICATOR.ID,
                "MatterFabricator",
                StatCollector.translateToLocal("NameMatterFabricator")));
        addItemTooltip(GTNLItemList.MatterFabricator.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeElectrolyzer.set(
            new LargeElectrolyzer(
                LARGE_ELECTROLYZER.ID,
                "LargeElectrolyzer",
                StatCollector.translateToLocal("NameLargeElectrolyzer")));
        addItemTooltip(GTNLItemList.LargeElectrolyzer.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeElectromagnet.set(
            new LargeElectromagnet(
                LARGE_ELECTROMAGNET.ID,
                "LargeElectromagnet",
                StatCollector.translateToLocal("NameLargeElectromagnet")));
        addItemTooltip(GTNLItemList.LargeElectromagnet.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeAssembler.set(
            new LargeAssembler(
                LARGE_ASSEMBLER.ID,
                "LargeAssembler",
                StatCollector.translateToLocal("NameLargeAssembler")));
        addItemTooltip(GTNLItemList.LargeAssembler.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeMixer
            .set(new LargeMixer(LARGE_MIXER.ID, "LargeMixer", StatCollector.translateToLocal("NameLargeMixer")));
        addItemTooltip(GTNLItemList.LargeMixer.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeCentrifuge.set(
            new LargeCentrifuge(
                LARGE_CENTRIFUGE.ID,
                "LargeCentrifuge",
                StatCollector.translateToLocal("NameLargeCentrifuge")));
        addItemTooltip(GTNLItemList.LargeCentrifuge.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LibraryOfRuina.set(
            new LibraryOfRuina(
                LIBRARY_OF_RUINA.ID,
                "LibraryOfRuina",
                StatCollector.translateToLocal("NameLibraryOfRuina")));
        addItemTooltip(GTNLItemList.LibraryOfRuina.get(1), AnimatedText.SNL_NLXCJH);

        GTNLItemList.LargeChemicalBath.set(
            new LargeChemicalBath(
                LARGE_CHEMICAL_BATH.ID,
                "LargeChemicalBath",
                StatCollector.translateToLocal("NameLargeChemicalBath")));
        addItemTooltip(GTNLItemList.LargeChemicalBath.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeAutoclave.set(
            new LargeAutoclave(
                LARGE_AUTOCLAVE.ID,
                "LargeAutoclave",
                StatCollector.translateToLocal("NameLargeAutoclave")));
        addItemTooltip(GTNLItemList.LargeAutoclave.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeSolidifier.set(
            new LargeSolidifier(
                LARGE_SOLIDIFIER.ID,
                "LargeSolidifier",
                StatCollector.translateToLocal("NameLargeSolidifier")));
        addItemTooltip(GTNLItemList.LargeSolidifier.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeExtractor.set(
            new LargeExtractor(
                LARGE_EXTRACTOR.ID,
                "LargeExtractor",
                StatCollector.translateToLocal("NameLargeExtractor")));
        addItemTooltip(GTNLItemList.LargeExtractor.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.ReactionFurnace.set(
            new ReactionFurnace(
                REACTION_FURNACE.ID,
                "ReactionFurnace",
                StatCollector.translateToLocal("NameReactionFurnace")));
        addItemTooltip(GTNLItemList.ReactionFurnace.get(1), AnimatedText.SNL_NLXCJH);

        GTNLItemList.EnergyInfuser.set(
            new EnergyInfuser(ENERGY_INFUSER.ID, "EnergyInfuser", StatCollector.translateToLocal("NameEnergyInfuser")));
        addItemTooltip(GTNLItemList.EnergyInfuser.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.LargeCanning.set(
            new LargeCanning(LARGE_CANNING.ID, "LargeCanning", StatCollector.translateToLocal("NameLargeCanning")));
        addItemTooltip(GTNLItemList.LargeCanning.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.Digester
            .set(new Digester(DIGESTER.ID, "Digester", StatCollector.translateToLocal("NameDigester")));
        addItemTooltip(GTNLItemList.Digester.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.AlloyBlastSmelter.set(
            new AlloyBlastSmelter(
                ALLOY_BLAST_SMELTER.ID,
                "AlloyBlastSmelter",
                StatCollector.translateToLocal("NameAlloyBlastSmelter")));
        addItemTooltip(GTNLItemList.AlloyBlastSmelter.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeSteamExtractor.set(
            new LargeSteamExtractor(
                LARGE_STEAM_EXTRACTOR.ID,
                "LargeSteamExtractor",
                StatCollector.translateToLocal("NameLargeSteamExtractor")));
        addItemTooltip(GTNLItemList.LargeSteamExtractor.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeSteamOreWasher.set(
            new LargeSteamOreWasher(
                LARGE_STEAM_ORE_WASHER.ID,
                "LargeSteamOreWasher",
                StatCollector.translateToLocal("NameLargeSteamOreWasher")));
        addItemTooltip(GTNLItemList.LargeSteamOreWasher.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.LargeHammer
            .set(new LargeHammer(LARGE_HAMMER.ID, "LargeHammer", StatCollector.translateToLocal("NameLargeHammer")));
        addItemTooltip(GTNLItemList.LargeHammer.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.IsaMill.set(new IsaMill(ISA_MILL.ID, "IsaMill", StatCollector.translateToLocal("NameIsaMill")));
        addItemTooltip(GTNLItemList.IsaMill.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.FlotationCellRegulator.set(
            new FlotationCellRegulator(
                FLOTATION_CELL_REGULATOR.ID,
                "FlotationCellRegulator",
                StatCollector.translateToLocal("NameFlotationCellRegulator")));
        addItemTooltip(GTNLItemList.FlotationCellRegulator.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.VacuumDryingFurnace.set(
            new VacuumDryingFurnace(
                VACUUM_DRYING_FURNACE.ID,
                "VacuumDryingFurnace",
                StatCollector.translateToLocal("NameVacuumDryingFurnace")));
        addItemTooltip(GTNLItemList.VacuumDryingFurnace.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeDistillery.set(
            new LargeDistillery(
                LARGE_DISTILLERY.ID,
                "LargeDistillery",
                StatCollector.translateToLocal("NameLargeDistillery")));
        addItemTooltip(GTNLItemList.LargeDistillery.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.Incubator
            .set(new Incubator(INCUBATOR.ID, "Incubator", StatCollector.translateToLocal("NameIncubator")));
        addItemTooltip(GTNLItemList.Incubator.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.LargeIncubator.set(
            new LargeIncubator(
                LARGE_INCUBATOR.ID,
                "LargeIncubator",
                StatCollector.translateToLocal("NameLargeIncubator")));
        addItemTooltip(GTNLItemList.LargeIncubator.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeEngravingLaser.set(
            new LargeEngravingLaser(
                LARGE_ENGRAVING_LASER.ID,
                "LargeEngravingLaser",
                StatCollector.translateToLocal("NameLargeEngravingLaser")));
        addItemTooltip(GTNLItemList.LargeEngravingLaser.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.FishingGround.set(
            new FishingGround(FISHING_GROUND.ID, "FishingGround", StatCollector.translateToLocal("NameFishingGround")));
        addItemTooltip(GTNLItemList.FishingGround.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.ElementCopying.set(
            new ElementCopying(
                ELEMENT_COPYING.ID,
                "ElementCopying",
                StatCollector.translateToLocal("NameElementCopying")));
        addItemTooltip(GTNLItemList.ElementCopying.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.WoodDistillation.set(
            new WoodDistillation(
                WOOD_DISTILLATION.ID,
                "WoodDistillation",
                StatCollector.translateToLocal("NameWoodDistillation")));
        addItemTooltip(GTNLItemList.WoodDistillation.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargePacker
            .set(new LargePacker(LARGE_PACKER.ID, "LargePacker", StatCollector.translateToLocal("NameLargePacker")));
        addItemTooltip(GTNLItemList.LargePacker.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeAlloySmelter.set(
            new LargeAlloySmelter(
                LARGE_ALLOY_SMELTER.ID,
                "LargeAlloySmelter",
                StatCollector.translateToLocal("NameLargeAlloySmelter")));
        addItemTooltip(GTNLItemList.LargeAlloySmelter.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.MolecularTransformer.set(
            new MolecularTransformer(
                MOLECULAR_TRANSFORMER.ID,
                "MolecularTransformer",
                StatCollector.translateToLocal("NameMolecularTransformer")));
        addItemTooltip(GTNLItemList.MolecularTransformer.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.LargePyrolyseOven.set(
            new LargePyrolyseOven(
                LARGE_PYROLYSE_OVEN.ID,
                "LargePyrolyseOven",
                StatCollector.translateToLocal("NameLargePyrolyseOven")));
        addItemTooltip(GTNLItemList.LargePyrolyseOven.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.LargeNaquadahReactor.set(
            new LargeNaquadahReactor(
                LARGE_NAQUADAH_REACTOR.ID,
                "LargeNaquadahReactor",
                StatCollector.translateToLocal("NameLargeNaquadahReactor")));
        addItemTooltip(GTNLItemList.LargeNaquadahReactor.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.DraconicFusionCrafting.set(
            new DraconicFusionCrafting(
                DRACONIC_FUSION_CRAFTING.ID,
                "DraconicFusionCrafting",
                StatCollector.translateToLocal("NameDraconicFusionCrafting")));
        addItemTooltip(GTNLItemList.DraconicFusionCrafting.get(1), AnimatedText.SNL_NLXCJH);

        GTNLItemList.LargeSteamExtruder.set(
            new LargeSteamExtruder(
                LARGE_STEAM_EXTRUDER.ID,
                "LargeSteamExtruder",
                StatCollector.translateToLocal("NameLargeSteamExtruder")));
        addItemTooltip(GTNLItemList.LargeSteamExtruder.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.DecayHastener.set(
            new DecayHastener(DECAY_HASTENER.ID, "DecayHastener", StatCollector.translateToLocal("NameDecayHastener")));
        addItemTooltip(GTNLItemList.DecayHastener.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.PreciseAssembler.set(
            new PrecisionAssembler(
                PRECISION_ASSEMBLER.ID,
                "PrecisionAssembler",
                StatCollector.translateToLocal("NamePrecisionAssembler")));
        addItemTooltip(GTNLItemList.PreciseAssembler.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.MegaAlloyBlastSmelter.set(
            new MegaAlloyBlastSmelter(
                MEGA_ALLOY_BLAST_SMELTER.ID,
                "MegaAlloyBlastSmelter",
                StatCollector.translateToLocal("NameMegaAlloyBlastSmelter")));
        addItemTooltip(GTNLItemList.MegaAlloyBlastSmelter.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.GrandAssemblyLine.set(
            new GrandAssemblyLine(
                GRAND_ASSEMBLY_LINE.ID,
                "GrandAssemblyLine",
                StatCollector.translateToLocal("NameGrandAssemblyLine")));
        addItemTooltip(GTNLItemList.GrandAssemblyLine.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.FuelRefiningComplex.set(
            new FuelRefiningComplex(
                FUEL_REFINING_COMPLEX.ID,
                "FuelRefiningComplex",
                StatCollector.translateToLocal("NameFuelRefiningComplex")));
        addItemTooltip(GTNLItemList.FuelRefiningComplex.get(1), AnimatedText.SNL_QYZG);

        ResourceCollectionModule = new ResourceCollectionModule(
            RESOURCE_COLLECTION_MODULE.ID,
            "ResourceCollectionModule",
            StatCollector.translateToLocal("NameResourceCollectionModule")).getStackForm(1);
        GTNLItemList.ResourceCollectionModule.set(ResourceCollectionModule);
        addItemTooltip(GTNLItemList.ResourceCollectionModule.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.LuvKuangBiaoOneGiantNuclearFusionReactor.set(
            new KuangBiaoOneGiantNuclearFusionReactor.LuVTier(
                LUV_KUANG_BIAO_ONE_GIANT_NUCLEAR_FUSION_REACTOR.ID,
                "KuangBiaoOneGiantNuclearFusionReactor",
                StatCollector.translateToLocal("NameLuvKuangBiaoOneGiantNuclearFusionReactor")));
        addItemTooltip(GTNLItemList.LuvKuangBiaoOneGiantNuclearFusionReactor.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.ZpmKuangBiaoTwoGiantNuclearFusionReactor.set(
            new KuangBiaoOneGiantNuclearFusionReactor.ZPMTier(
                ZPM_KUANG_BIAO_TWO_GIANT_NUCLEAR_FUSION_REACTOR.ID,
                "KuangBiaoTwoGiantNuclearFusionReactor",
                StatCollector.translateToLocal("NameZpmKuangBiaoTwoGiantNuclearFusionReactor")));
        addItemTooltip(GTNLItemList.ZpmKuangBiaoTwoGiantNuclearFusionReactor.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.UvKuangBiaoThreeGiantNuclearFusionReactor.set(
            new KuangBiaoOneGiantNuclearFusionReactor.UVTier(
                UV_KUANG_BIAO_THREE_GIANT_NUCLEAR_FUSION_REACTOR.ID,
                "KuangBiaoThreeGiantNuclearFusionReactor",
                StatCollector.translateToLocal("NameUvKuangBiaoThreeGiantNuclearFusionReactor")));
        addItemTooltip(GTNLItemList.UvKuangBiaoThreeGiantNuclearFusionReactor.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.UhvKuangBiaoFourGiantNuclearFusionReactor.set(
            new KuangBiaoOneGiantNuclearFusionReactor.UHVTier(
                UHV_KUANG_BIAO_FOUR_GIANT_NUCLEAR_FUSION_REACTOR.ID,
                "KuangBiaoFourGiantNuclearFusionReactor",
                StatCollector.translateToLocal("NameUhvKuangBiaoFourGiantNuclearFusionReactor")));
        addItemTooltip(GTNLItemList.UhvKuangBiaoFourGiantNuclearFusionReactor.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.UevKuangBiaoFiveGiantNuclearFusionReactor.set(
            new KuangBiaoOneGiantNuclearFusionReactor.UEVTier(
                UEV_KUANG_BIAO_FIVE_GIANT_NUCLEAR_FUSION_REACTOR.ID,
                "KuangBiaoFiveGiantNuclearFusionReactor",
                StatCollector.translateToLocal("NameUevKuangBiaoFiveGiantNuclearFusionReactor")));
        addItemTooltip(GTNLItemList.UevKuangBiaoFiveGiantNuclearFusionReactor.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.LargeSteamCentrifuge.set(
            new LargeSteamCentrifuge(
                LARGE_STEAM_CENTRIFUGE.ID,
                "LargeSteamCentrifuge",
                StatCollector.translateToLocal("NameLargeSteamCentrifuge")));
        addItemTooltip(GTNLItemList.LargeSteamCentrifuge.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeSteamHammer.set(
            new LargeSteamHammer(
                LARGE_STEAM_HAMMER.ID,
                "LargeSteamHammer",
                StatCollector.translateToLocal("NameLargeSteamHammer")));
        addItemTooltip(GTNLItemList.LargeSteamHammer.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeSteamCompressor.set(
            new LargeSteamCompressor(
                LARGE_STEAM_COMPRESSOR.ID,
                "LargeSteamCompressor",
                StatCollector.translateToLocal("NameLargeSteamCompressor")));
        addItemTooltip(GTNLItemList.LargeSteamCompressor.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeSteamSifter.set(
            new LargeSteamSifter(
                LARGE_STEAM_SIFTER.ID,
                "LargeSteamSifter",
                StatCollector.translateToLocal("NameLargeSteamSifter")));
        addItemTooltip(GTNLItemList.LargeSteamSifter.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeBoilerBronze.set(
            new LargeBoiler.LargeBoilerBronze(
                LARGE_BOILER_BRONZE.ID,
                "LargeBoilerBronze",
                StatCollector.translateToLocal("NameLargeBoilerBronze")));
        addItemTooltip(GTNLItemList.LargeBoilerBronze.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeBoilerSteel.set(
            new LargeBoiler.LargeBoilerSteel(
                LARGE_BOILER_STEEL.ID,
                "LargeBoilerSteel",
                StatCollector.translateToLocal("NameLargeBoilerSteel")));
        addItemTooltip(GTNLItemList.LargeBoilerSteel.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeBoilerTitanium.set(
            new LargeBoiler.LargeBoilerTitanium(
                LARGE_BOILER_TITANIUM.ID,
                "LargeBoilerTitanium",
                StatCollector.translateToLocal("NameLargeBoilerTitanium")));
        addItemTooltip(GTNLItemList.LargeBoilerTitanium.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeBoilerTungstenSteel.set(
            new LargeBoiler.LargeBoilerTungstenSteel(
                LARGE_BOILER_TUNGSTEN_STEEL.ID,
                "LargeBoilerTungstenSteel",
                StatCollector.translateToLocal("NameLargeBoilerTungstenSteel")));
        addItemTooltip(GTNLItemList.LargeBoilerTungstenSteel.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeSteamFormingPress.set(
            new LargeSteamFormingPress(
                LARGE_STEAM_FORMING_PRESS.ID,
                "LargeSteamFormingPress",
                StatCollector.translateToLocal("NameLargeSteamFormingPress")));
        addItemTooltip(GTNLItemList.LargeSteamFormingPress.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.LargeSteamMixer.set(
            new LargeSteamMixer(
                LARGE_STEAM_MIXER.ID,
                "LargeSteamMixer",
                StatCollector.translateToLocal("NameLargeSteamMixer")));
        addItemTooltip(GTNLItemList.LargeSteamMixer.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.CrackerHub
            .set(new CrackerHub(CRACKER_HUB.ID, "CrackerHub", StatCollector.translateToLocal("NameCrackerHub")));
        addItemTooltip(GTNLItemList.CrackerHub.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.AdvancedInfiniteDriller.set(
            new AdvancedInfiniteDriller(
                ADVANCED_INFINITE_DRILLER.ID,
                "AdvancedInfiniteDriller",
                StatCollector.translateToLocal("NameAdvancedInfiniteDriller")));
        addItemTooltip(GTNLItemList.AdvancedInfiniteDriller.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.ElectricBlastFurnace.set(
            new ElectricBlastFurnace(
                ELECTRIC_BLAST_FURNACE.ID,
                "ElectricBlastFurnace",
                StatCollector.translateToLocal("NameElectricBlastFurnace")));
        addItemTooltip(GTNLItemList.ElectricBlastFurnace.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.PlatinumBasedTreatment.set(
            new PlatinumBasedTreatment(
                PLATINUM_BASED_TREATMENT.ID,
                "PlatinumBasedTreatment",
                StatCollector.translateToLocal("NamePlatinumBasedTreatment")));
        addItemTooltip(GTNLItemList.PlatinumBasedTreatment.get(1), AnimatedText.SNL_PBTR);

        GTNLItemList.ShallowChemicalCoupling.set(
            new ShallowChemicalCoupling(
                SHALLOW_CHEMICAL_COUPLING.ID,
                "ShallowChemicalCoupling",
                StatCollector.translateToLocal("NameShallowChemicalCoupling")));
        addItemTooltip(GTNLItemList.ShallowChemicalCoupling.get(1), AnimatedText.SNL_SCCR);

        GTNLItemList.Steamgate
            .set(new Steamgate(STEAMGATE.ID, "Steamgate", StatCollector.translateToLocal("NameSteamgate")));
        addItemTooltip(GTNLItemList.Steamgate.get(1), AnimatedText.SCIENCE_NOT_LEISURE);
        addItemTooltip(GTNLItemList.Steamgate.get(1), AnimatedText.SteamgateCredits);

        GTNLItemList.SteamGateAssembler.set(
            new SteamGateAssembler(
                STEAM_GATE_ASSEMBLER.ID,
                "SteamGateAssembler",
                StatCollector.translateToLocal("NameSteamGateAssembler")));
        addItemTooltip(GTNLItemList.SteamGateAssembler.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.MegaSteamCompressor.set(
            new MegaSteamCompressor(
                MEGA_STEAM_COMPRESSOR.ID,
                "MegaSteamCompressor",
                StatCollector.translateToLocal("NameMegaSteamCompressor")));
        addItemTooltip(GTNLItemList.MegaSteamCompressor.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.MegaSolarBoiler.set(
            new MegaSolarBoiler(
                MEGA_SOLAR_BOILER.ID,
                "MegaSolarBoiler",
                StatCollector.translateToLocal("NameMegaSolarBoiler")));
        addItemTooltip(GTNLItemList.MegaSolarBoiler.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamCactusWonder.set(
            new SteamCactusWonder(
                STEAM_CACTUS_WONDER.ID,
                "SteamCactusWonder",
                StatCollector.translateToLocal("NameSteamCactusWonder")));
        addItemTooltip(GTNLItemList.SteamCactusWonder.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamCarpenter.set(
            new SteamCarpenter(
                STEAM_CARPENTER.ID,
                "SteamCarpenter",
                StatCollector.translateToLocal("NameSteamCarpenter")));
        addItemTooltip(GTNLItemList.SteamCarpenter.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamLavaMaker.set(
            new SteamLavaMaker(
                STEAM_LAVA_MAKER.ID,
                "SteamLavaMaker",
                StatCollector.translateToLocal("NameSteamLavaMaker")));
        addItemTooltip(GTNLItemList.SteamLavaMaker.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamManufacturer.set(
            new SteamManufacturer(
                STEAM_MANUFACTURER.ID,
                "SteamManufacturer",
                StatCollector.translateToLocal("NameSteamManufacturer")));
        addItemTooltip(GTNLItemList.SteamManufacturer.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamRockBreaker.set(
            new SteamRockBreaker(
                STEAM_ROCK_BREAKER.ID,
                "SteamRockBreaker",
                StatCollector.translateToLocal("NameSteamRockBreaker")));
        addItemTooltip(GTNLItemList.SteamRockBreaker.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamWoodcutter.set(
            new SteamWoodcutter(
                STEAM_WOODCUTTER.ID,
                "SteamWoodcutter",
                StatCollector.translateToLocal("NameSteamWoodcutter")));
        addItemTooltip(GTNLItemList.SteamWoodcutter.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamExtractinator.set(
            new SteamExtractinator(
                STEAM_EXTRACTINATOR.ID,
                "SteamExtractinator",
                StatCollector.translateToLocal("NameSteamExtractinator")));
        addItemTooltip(GTNLItemList.SteamExtractinator.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamFusionReactor.set(
            new SteamFusionReactor(
                STEAM_FUSION_REACTOR.ID,
                "SteamFusionReactor",
                StatCollector.translateToLocal("NameSteamFusionReactor")));
        addItemTooltip(GTNLItemList.SteamFusionReactor.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HighPressureSteamFusionReactor.set(
            new HighPressureSteamFusionReactor(
                HIGH_PRESSURE_STEAM_FUSION_REACTOR.ID,
                "HighPressureSteamFusionReactor",
                StatCollector.translateToLocal("NameHighPressureSteamFusionReactor")));
        addItemTooltip(GTNLItemList.HighPressureSteamFusionReactor.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamInfernalCokeOven.set(
            new SteamInfernalCokeOven(
                STEAM_INFERNAL_COKE_OVEN.ID,
                "SteamInfernalCokeOven",
                StatCollector.translateToLocal("NameSteamInfernalCokeOven")));
        addItemTooltip(GTNLItemList.SteamInfernalCokeOven.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.IntegratedAssemblyFacility.set(
            new IntegratedAssemblyFacility(
                INTEGRATED_ASSEMBLY_FACILITY.ID,
                "IntegratedAssemblyFacility",
                StatCollector.translateToLocal("NameIntegratedAssemblyFacility")));
        addItemTooltip(GTNLItemList.IntegratedAssemblyFacility.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.AdvancedCircuitAssemblyLine.set(
            new AdvancedCircuitAssemblyLine(
                ADVANCED_CIRCUIT_ASSEMBLY_LINE.ID,
                "AdvancedCircuitAssemblyLine",
                StatCollector.translateToLocal("NameAdvancedCircuitAssemblyLine")));
        addItemTooltip(GTNLItemList.AdvancedCircuitAssemblyLine.get(1), AnimatedText.SNL_SCCR);

        GTNLItemList.NanoPhagocytosisPlant.set(
            new NanoPhagocytosisPlant(
                NANO_PHAGOCYTOSIS_PLANT.ID,
                "NanoPhagocytosisPlant",
                StatCollector.translateToLocal("NameNanoPhagocytosisPlant")));
        addItemTooltip(GTNLItemList.NanoPhagocytosisPlant.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.MagneticEnergyReactionFurnace.set(
            new MagneticEnergyReactionFurnace(
                MAGNETIC_ENERGY_REACTION_FURNACE.ID,
                "MagneticEnergyReactionFurnace",
                StatCollector.translateToLocal("NameMagneticEnergyReactionFurnace")));
        addItemTooltip(GTNLItemList.MagneticEnergyReactionFurnace.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.NanitesIntegratedProcessingCenter.set(
            new NanitesIntegratedProcessingCenter(
                NANITES_INTEGRATED_PROCESSING_CENTER.ID,
                "NanitesIntegratedProcessingCenter",
                StatCollector.translateToLocal("NameNanitesIntegratedProcessingCenter")));
        addItemTooltip(GTNLItemList.NanitesIntegratedProcessingCenter.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.BioengineeringModule.set(
            new BioengineeringModule(
                BIOENGINEERING_MODULE.ID,
                "BioengineeringModule",
                StatCollector.translateToLocal("NameBioengineeringModule")));
        addItemTooltip(GTNLItemList.BioengineeringModule.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.PolymerTwistingModule.set(
            new PolymerTwistingModule(
                POLYMER_TWISTING_MODULE.ID,
                "PolymerTwistingModule",
                StatCollector.translateToLocal("NamePolymerTwistingModule")));
        addItemTooltip(GTNLItemList.PolymerTwistingModule.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.OreExtractionModule.set(
            new OreExtractionModule(
                ORE_EXTRACTION_MODULE.ID,
                "OreExtractionModule",
                StatCollector.translateToLocal("NameOreExtractionModule")));
        addItemTooltip(GTNLItemList.OreExtractionModule.get(1), AnimatedText.SNL_QYZG);

        SuperSpaceElevator = new SuperSpaceElevator(
            SUPER_SPACE_ELEVATOR.ID,
            "SuperSpaceElevator",
            StatCollector.translateToLocal("NameSuperSpaceElevator")).getStackForm(1);
        GTNLItemList.SuperSpaceElevator.set(SuperSpaceElevator);
        addItemTooltip(GTNLItemList.SuperSpaceElevator.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeBioLab
            .set(new LargeBioLab(LARGE_BIO_LAB.ID, "LargeBioLab", StatCollector.translateToLocal("NameLargeBioLab")));
        addItemTooltip(GTNLItemList.LargeBioLab.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.LargeGasCollector.set(
            new LargeGasCollector(
                LARGE_GAS_COLLECTOR.ID,
                "LargeGasCollector",
                StatCollector.translateToLocal("NameLargeGasCollector")));
        addItemTooltip(GTNLItemList.LargeGasCollector.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.EternalGregTechWorkshop.set(
            new EternalGregTechWorkshop(
                ETERNAL_GREG_TECH_WORKSHOP.ID,
                "EternalGregTechWorkshop",
                StatCollector.translateToLocal("NameEternalGregTechWorkshop")));
        addItemTooltip(GTNLItemList.EternalGregTechWorkshop.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.EGTWFusionModule.set(
            new EGTWFusionModule(
                EGTW_FUSION_MODULE.ID,
                "EGTWFusionModule",
                StatCollector.translateToLocal("NameEGTWFusionModule")));
        addItemTooltip(GTNLItemList.EGTWFusionModule.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SpaceAssembler.set(
            new SpaceAssembler(
                SPACE_ASSEMBLER.ID,
                "SpaceAssembler",
                StatCollector.translateToLocal("NameSpaceAssembler")));
        addItemTooltip(GTNLItemList.SpaceAssembler.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.EngravingLaserPlant.set(
            new EngravingLaserPlant(
                ENGRAVING_LASER_PLANT.ID,
                "EngravingLaserPlant",
                StatCollector.translateToLocal("NameEngravingLaserPlant")));
        addItemTooltip(GTNLItemList.EngravingLaserPlant.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.VortexMatterCentrifuge.set(
            new VortexMatterCentrifuge(
                VORTEX_MATTER_CENTRIFUGE.ID,
                "VortexMatterCentrifuge",
                StatCollector.translateToLocal("NameVortexMatterCentrifuge")));
        addItemTooltip(GTNLItemList.VortexMatterCentrifuge.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.SuperconductingElectromagnetism.set(
            new SuperconductingElectromagnetism(
                SUPERCONDUCTING_ELECTROMAGNETISM.ID,
                "SuperconductingElectromagnetism",
                StatCollector.translateToLocal("NameSuperconductingElectromagnetism")));
        addItemTooltip(GTNLItemList.SuperconductingElectromagnetism.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.FieldForgePress.set(
            new FieldForgePress(
                FIELD_FORGE_PRESS.ID,
                "FieldForgePress",
                StatCollector.translateToLocal("NameFieldForgePress")));
        addItemTooltip(GTNLItemList.FieldForgePress.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.SuperconductingMagneticPresser.set(
            new SuperconductingMagneticPresser(
                SUPERCONDUCTING_MAGNETIC_PRESSER.ID,
                "SuperconductingMagneticPresser",
                StatCollector.translateToLocal("NameSuperconductingMagneticPresser")));
        addItemTooltip(GTNLItemList.SuperconductingMagneticPresser.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.HeavyRolling.set(
            new HeavyRolling(HEAVY_ROLLING.ID, "HeavyRolling", StatCollector.translateToLocal("NameHeavyRolling")));
        addItemTooltip(GTNLItemList.HeavyRolling.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.HighEnergyLaserLathe.set(
            new HighEnergyLaserLathe(
                HIGH_ENERGY_LASER_LATHE.ID,
                "HighEnergyLaserLathe",
                StatCollector.translateToLocal("NameHighEnergyLaserLathe")));
        addItemTooltip(GTNLItemList.HighEnergyLaserLathe.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.MegaMixer
            .set(new MegaMixer(MEGA_MIXER.ID, "MegaMixer", StatCollector.translateToLocal("NameMegaMixer")));
        addItemTooltip(GTNLItemList.MegaMixer.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.SteamBeaconModuleI.set(
            new SteamBeaconModule(
                STEAM_BEACON_MODULE_I.ID,
                "SteamBeaconModuleI",
                StatCollector.translateToLocal("NameSteamBeaconModuleI"),
                1));
        addItemTooltip(GTNLItemList.SteamBeaconModuleI.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamBeaconModuleII.set(
            new SteamBeaconModule(
                STEAM_BEACON_MODULE_II.ID,
                "SteamBeaconModuleII",
                StatCollector.translateToLocal("NameSteamBeaconModuleII"),
                2));
        addItemTooltip(GTNLItemList.SteamBeaconModuleII.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamBeaconModuleIII.set(
            new SteamBeaconModule(
                STEAM_BEACON_MODULE_III.ID,
                "SteamBeaconModuleIII",
                StatCollector.translateToLocal("NameSteamBeaconModuleIII"),
                3));
        addItemTooltip(GTNLItemList.SteamBeaconModuleIII.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NanitesCircuitAssemblyFactory.set(
            new NanitesCircuitAssemblyFactory(
                NANITES_CIRCUIT_ASSEMBLY_FACTORY.ID,
                "NanitesCircuitAssemblyFactory",
                StatCollector.translateToLocal("NameNanitesCircuitAssemblyFactory")));
        addItemTooltip(GTNLItemList.NanitesCircuitAssemblyFactory.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.ETGWEyeOfHarmonyModule.set(
            new ETGWEyeOfHarmonyModule(
                ETGW_EYE_OF_HARMONY_MODULE.ID,
                "ETGWEyeOfHarmonyModule",
                StatCollector.translateToLocal("NameETGWEyeOfHarmonyModule")));
        addItemTooltip(GTNLItemList.ETGWEyeOfHarmonyModule.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.AetronPressor.set(
            new AetronPressor(AETRON_PRESSOR.ID, "AetronPressor", StatCollector.translateToLocal("NameAetronPressor")));
        addItemTooltip(GTNLItemList.AetronPressor.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.SteamElevator.set(
            new SteamElevator(STEAM_ELEVATOR.ID, "SteamElevator", StatCollector.translateToLocal("NameSteamElevator")));
        addItemTooltip(GTNLItemList.SteamElevator.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamMonsterRepellentModuleI.set(
            new SteamMonsterRepellentModule(
                STEAM_MONSTER_REPELLENT_MODULE_I.ID,
                "SteamMonsterRepellentModuleI",
                StatCollector.translateToLocal("NameSteamMonsterRepellentModuleI"),
                1));
        addItemTooltip(GTNLItemList.SteamMonsterRepellentModuleI.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamMonsterRepellentModuleII.set(
            new SteamMonsterRepellentModule(
                STEAM_MONSTER_REPELLENT_MODULE_II.ID,
                "SteamMonsterRepellentModuleII",
                StatCollector.translateToLocal("NameSteamMonsterRepellentModuleII"),
                2));
        addItemTooltip(GTNLItemList.SteamMonsterRepellentModuleII.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamMonsterRepellentModuleIII.set(
            new SteamMonsterRepellentModule(
                STEAM_MONSTER_REPELLENT_MODULE_III.ID,
                "SteamMonsterRepellentModuleIII",
                StatCollector.translateToLocal("NameSteamMonsterRepellentModuleIII"),
                3));
        addItemTooltip(GTNLItemList.SteamMonsterRepellentModuleIII.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamFlightModule.set(
            new SteamFlightModule(
                STEAM_FLIGHT_MODULE.ID,
                "SteamFlightModule",
                StatCollector.translateToLocal("NameSteamFlightModule")));
        addItemTooltip(GTNLItemList.SteamFlightModule.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamWeatherModule.set(
            new SteamWeatherModule(
                STEAM_WEATHER_MODULE.ID,
                "SteamWeatherModule",
                StatCollector.translateToLocal("NameSteamWeatherModule")));
        addItemTooltip(GTNLItemList.SteamWeatherModule.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NanoAssemblerMarkL.set(
            new NanoAssemblerMarkL(
                NANO_ASSEMBLER_MARK_L.ID,
                "NanoAssemblerMarkL",
                StatCollector.translateToLocal("NameNanoAssemblerMarkL")));
        addItemTooltip(GTNLItemList.NanoAssemblerMarkL.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.HighPerformanceComputationArray.set(
            new HighPerformanceComputationArray(
                HIGH_PERFORMANCE_COMPUTATION_ARRAY.ID,
                "HighPerformanceComputationArray",
                StatCollector.translateToLocal("NameHighPerformanceComputationArray")));
        addItemTooltip(GTNLItemList.HighPerformanceComputationArray.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.EyeOfHarmonyInjector.set(
            new EyeOfHarmonyInjector(
                EYE_OF_HARMONY_INJECTOR.ID,
                "EyeOfHarmonyInjector",
                StatCollector.translateToLocal("NameEyeOfHarmonyInjector")));
        addItemTooltip(GTNLItemList.EyeOfHarmonyInjector.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.CompoundExtremeCoolingUnit.set(
            new CompoundExtremeCoolingUnit(
                COMPOUND_EXTREME_COOLING_UNIT.ID,
                "CompoundExtremeCoolingUnit",
                StatCollector.translateToLocal("NameCompoundExtremeCoolingUnit")));
        addItemTooltip(GTNLItemList.CompoundExtremeCoolingUnit.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.SteamOreProcessorModule.set(
            new SteamOreProcessorModule(
                STEAM_ORE_PROCESSOR_MODULE.ID,
                "SteamOreProcessorModule",
                StatCollector.translateToLocal("NameSteamOreProcessorModule")));
        addItemTooltip(GTNLItemList.SteamOreProcessorModule.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.LargeSteamLathe.set(
            new LargeSteamLathe(
                LARGE_STEAM_LATHE.ID,
                "LargeSteamLathe",
                StatCollector.translateToLocal("NameLargeSteamLathe")));
        addItemTooltip(GTNLItemList.LargeSteamLathe.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeSteamCutting.set(
            new LargeSteamCutting(
                LARGE_STEAM_CUTTING.ID,
                "LargeSteamCutting",
                StatCollector.translateToLocal("NameLargeSteamCutting")));
        addItemTooltip(GTNLItemList.LargeSteamCutting.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.SteamItemVault.set(
            new SteamItemVault(
                STEAM_ITEM_VAULT.ID,
                "SteamItemVault",
                StatCollector.translateToLocal("NameSteamItemVault")));
        addItemTooltip(GTNLItemList.SteamItemVault.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.PrimitiveBrickKiln.set(
            new PrimitiveBrickKiln(
                PRIMITIVE_BRICK_KILN.ID,
                "PrimitiveBrickKiln",
                StatCollector.translateToLocal("NamePrimitiveBrickKiln")));
        addItemTooltip(GTNLItemList.PrimitiveBrickKiln.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SingularityDataHub.set(
            new SingularityDataHub(
                SINGULARITY_DATA_HUB.ID,
                "SingularityDataHub",
                StatCollector.translateToLocal("NameSingularityDataHub")));
        addItemTooltip(GTNLItemList.SingularityDataHub.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ElectrocellGenerator.set(
            new ElectrocellGenerator(
                ELECTROCELL_GENERATOR.ID,
                "ElectrocellGenerator",
                StatCollector.translateToLocal("NameElectrocellGenerator")));
        addItemTooltip(GTNLItemList.ElectrocellGenerator.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.FOGAlloySmelterModule.set(
            new FOGAlloySmelterModule(
                FOG_ALLOY_SMELTER_MODULE.ID,
                "NameFOGAlloySmelterModule",
                StatCollector.translateToLocal("NameFOGAlloySmelterModule")));
        addItemTooltip(GTNLItemList.FOGAlloySmelterModule.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.FOGAlloyBlastSmelterModule.set(
            new FOGAlloyBlastSmelterModule(
                FOG_ALLOY_BLAST_SMELTER_MODULE.ID,
                "NameFOGAlloyBlastSmelterModule",
                StatCollector.translateToLocal("NameFOGAlloyBlastSmelterModule")));
        addItemTooltip(GTNLItemList.FOGAlloyBlastSmelterModule.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.FOGExtractorModule.set(
            new FOGExtractorModule(
                FOG_EXTRACTOR_MODULE.ID,
                "NameFOGExtractorModule",
                StatCollector.translateToLocal("NameFOGExtractorModule")));
        addItemTooltip(GTNLItemList.FOGExtractorModule.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamEntityCrusherModule.set(
            new SteamEntityCrusherModule(
                STEAM_ENTITY_CRUSHER_MODULE.ID,
                "NameSteamEntityCrusherModule",
                StatCollector.translateToLocal("NameSteamEntityCrusherModule")));
        addItemTooltip(GTNLItemList.SteamEntityCrusherModule.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamApiaryModule.set(
            new SteamApiaryModule(
                STEAM_APIARY_MODULE.ID,
                "NameSteamApiaryModule",
                StatCollector.translateToLocal("NameSteamApiaryModule")));
        addItemTooltip(GTNLItemList.SteamApiaryModule.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamBeeBreedingModule.set(
            new SteamBeeBreedingModule(
                STEAM_BEE_BREEDING_MODULE.ID,
                "NameSteamBeeBreedingModule",
                StatCollector.translateToLocal("NameSteamBeeBreedingModule")));
        addItemTooltip(GTNLItemList.SteamBeeBreedingModule.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamGreenhouseModule.set(
            new SteamGreenhouseModule(
                STEAM_GREENHOUSE_MODULE.ID,
                "NameSteamGreenhouseModule",
                StatCollector.translateToLocal("NameSteamGreenhouseModule")));
        addItemTooltip(GTNLItemList.SteamGreenhouseModule.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.RocketAssembler.set(
            new RocketAssembler(
                ROCKET_ASSEMBLER.ID,
                "NameRocketAssembler",
                StatCollector.translateToLocal("NameRocketAssembler")));
        addItemTooltip(GTNLItemList.RocketAssembler.get(1), AnimatedText.SNL_QYZG);

        // Special Machine
        GTNLItemList.CheatOreProcessingFactory.set(
            new CheatOreProcessingFactory(
                CHEAT_ORE_PROCESSING_FACTORY.ID,
                "CheatOreProcessingFactory",
                StatCollector.translateToLocal("NameCheatOreProcessingFactory")));
        addItemTooltip(GTNLItemList.CheatOreProcessingFactory.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.NineIndustrialMultiMachine.set(
            new NineIndustrialMultiMachine(
                NINE_INDUSTRIAL_MULTI_MACHINE.ID,
                "NineIndustrialMultiMachine",
                StatCollector.translateToLocal("NameNineIndustrialMultiMachine")));
        addItemTooltip(GTNLItemList.NineIndustrialMultiMachine.get(1), AnimatedText.SNL_QYZG);
    }

    public static void registerHatch() {
        Set<Fluid> acceptedFluids = new HashSet<>();
        acceptedFluids.add(
            MaterialPool.FluidMana.getFluidOrGas(1)
                .getFluid());

        if (ModList.TwistSpaceTechnology.isModLoaded()) {
            acceptedFluids.add(
                FluidUtils.getFluidStack("liquid mana", 1)
                    .getFluid());
        }

        GTNLItemList.FluidManaInputHatch.set(
            new CustomFluidHatch(
                ImmutableSet.copyOf(acceptedFluids),
                512000,
                FLUID_MANA_INPUT_HATCH.ID,
                "FluidManaInputHatch",
                StatCollector.translateToLocal("FluidManaInputHatch"),
                6));
        addItemTooltip(GTNLItemList.FluidManaInputHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.FluidIceInputHatch.set(
            new CustomFluidHatch(
                ImmutableSet.of(
                    FluidUtils.getFluidStack("ice", 1)
                        .getFluid()),
                256000,
                FLUID_ICE_INPUT_HATCH.ID,
                "FluidIceInputHatch",
                StatCollector.translateToLocal("FluidIceInputHatch"),
                5));
        addItemTooltip(GTNLItemList.FluidIceInputHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.FluidBlazeInputHatch.set(
            new CustomFluidHatch(
                ImmutableSet.of(
                    FluidUtils.getFluidStack("molten.blaze", 1)
                        .getFluid()),
                256000,
                FLUID_BLAZE_INPUT_HATCH.ID,
                "FluidBlazeInputHatch",
                StatCollector.translateToLocal("FluidBlazeInputHatch"),
                5));
        addItemTooltip(GTNLItemList.FluidBlazeInputHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SuperCraftingInputHatchME.set(
            new SuperCraftingInputHatchME(
                SUPER_CRAFTING_INPUT_HATCH_ME.ID,
                "SuperCraftingInputBuffer(ME)",
                StatCollector.translateToLocal("SuperCraftingInputHatchME"),
                true));
        addItemTooltip(GTNLItemList.SuperCraftingInputHatchME.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SuperCraftingInputBusME.set(
            new SuperCraftingInputHatchME(
                SUPER_CRAFTING_INPUT_BUS_ME.ID,
                "SuperCraftingInputBus(ME)",
                StatCollector.translateToLocal("SuperCraftingInputBusME"),
                false));
        addItemTooltip(GTNLItemList.SuperCraftingInputBusME.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousSolidifierHatch.set(
            new HumongousSolidifierHatch(
                HUMONGOUS_SOLIDIFIER_HATCH.ID,
                "HumongousSolidifierHatch",
                StatCollector.translateToLocal("HumongousSolidifierHatch"),
                14));
        addItemTooltip(GTNLItemList.HumongousSolidifierHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DebugEnergyHatch.set(
            new DebugEnergyHatch(
                DEBUG_ENERGY_HATCH.ID,
                "DebugEnergyHatch",
                StatCollector.translateToLocal("DebugEnergyHatch")));
        addItemTooltip(GTNLItemList.DebugEnergyHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchEV.set(
            new NinefoldInputHatch(
                NINEFOLD_INPUT_HATCH_EV.ID,
                9,
                "NinefoldInputHatchEV",
                StatCollector.translateToLocal("NinefoldInputHatchEV"),
                4));
        addItemTooltip(GTNLItemList.NinefoldInputHatchEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchIV.set(
            new NinefoldInputHatch(
                NINEFOLD_INPUT_HATCH_IV.ID,
                9,
                "NinefoldInputHatchIV",
                StatCollector.translateToLocal("NinefoldInputHatchIV"),
                5));
        addItemTooltip(GTNLItemList.NinefoldInputHatchIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchLuV.set(
            new NinefoldInputHatch(
                NINEFOLD_INPUT_HATCH_LUV.ID,
                9,
                "NinefoldInputHatchLuV",
                StatCollector.translateToLocal("NinefoldInputHatchLuV"),
                6));
        addItemTooltip(GTNLItemList.NinefoldInputHatchLuV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchZPM.set(
            new NinefoldInputHatch(
                NINEFOLD_INPUT_HATCH_ZPM.ID,
                9,
                "NinefoldInputHatchZPM",
                StatCollector.translateToLocal("NinefoldInputHatchZPM"),
                7));
        addItemTooltip(GTNLItemList.NinefoldInputHatchZPM.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchUV.set(
            new NinefoldInputHatch(
                NINEFOLD_INPUT_HATCH_UV.ID,
                9,
                "NinefoldInputHatchUV",
                StatCollector.translateToLocal("NinefoldInputHatchUV"),
                8));
        addItemTooltip(GTNLItemList.NinefoldInputHatchUV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchUHV.set(
            new NinefoldInputHatch(
                NINEFOLD_INPUT_HATCH_UHV.ID,
                9,
                "NinefoldInputHatchUHV",
                StatCollector.translateToLocal("NinefoldInputHatchUHV"),
                9));
        addItemTooltip(GTNLItemList.NinefoldInputHatchUHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchUEV.set(
            new NinefoldInputHatch(
                NINEFOLD_INPUT_HATCH_UEV.ID,
                9,
                "NinefoldInputHatchUEV",
                StatCollector.translateToLocal("NinefoldInputHatchUEV"),
                10));
        addItemTooltip(GTNLItemList.NinefoldInputHatchUEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchUIV.set(
            new NinefoldInputHatch(
                NINEFOLD_INPUT_HATCH_UIV.ID,
                9,
                "NinefoldInputHatchUIV",
                StatCollector.translateToLocal("NinefoldInputHatchUIV"),
                11));
        addItemTooltip(GTNLItemList.NinefoldInputHatchUIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchUMV.set(
            new NinefoldInputHatch(
                NINEFOLD_INPUT_HATCH_UMV.ID,
                9,
                "NinefoldInputHatchUMV",
                StatCollector.translateToLocal("NinefoldInputHatchUMV"),
                12));
        addItemTooltip(GTNLItemList.NinefoldInputHatchUMV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchUXV.set(
            new NinefoldInputHatch(
                NINEFOLD_INPUT_HATCH_UXV.ID,
                9,
                "NinefoldInputHatchUXV",
                StatCollector.translateToLocal("NinefoldInputHatchUXV"),
                13));
        addItemTooltip(GTNLItemList.NinefoldInputHatchUXV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchMAX.set(
            new NinefoldInputHatch(
                NINEFOLD_INPUT_HATCH_MAX.ID,
                9,
                "NinefoldInputHatchMAX",
                StatCollector.translateToLocal("NinefoldInputHatchMAX"),
                14));
        addItemTooltip(GTNLItemList.NinefoldInputHatchMAX.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousNinefoldInputHatch.set(
            new HumongousNinefoldInputHatch(
                HUMONGOUS_NINEFOLD_INPUT_HATCH.ID,
                9,
                "HumongousNinefoldInputHatch",
                StatCollector.translateToLocal("HumongousNinefoldInputHatch")));
        addItemTooltip(GTNLItemList.HumongousNinefoldInputHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchLV.set(
            new DualInputHatch(
                DUAL_INPUT_HATCH_LV.ID,
                "DualInputHatchLV",
                StatCollector.translateToLocal("DualInputHatchLV"),
                1));
        addItemTooltip(GTNLItemList.DualInputHatchLV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchMV.set(
            new DualInputHatch(
                DUAL_INPUT_HATCH_MV.ID,
                "DualInputHatchMV",
                StatCollector.translateToLocal("DualInputHatchMV"),
                2));
        addItemTooltip(GTNLItemList.DualInputHatchMV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchHV.set(
            new DualInputHatch(
                DUAL_INPUT_HATCH_HV.ID,
                "DualInputHatchHV",
                StatCollector.translateToLocal("DualInputHatchHV"),
                3));
        addItemTooltip(GTNLItemList.DualInputHatchHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchEV.set(
            new DualInputHatch(
                DUAL_INPUT_HATCH_EV.ID,
                "DualInputHatchEV",
                StatCollector.translateToLocal("DualInputHatchEV"),
                4));
        addItemTooltip(GTNLItemList.DualInputHatchEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchIV.set(
            new DualInputHatch(
                DUAL_INPUT_HATCH_IV.ID,
                "DualInputHatchIV",
                StatCollector.translateToLocal("DualInputHatchIV"),
                5));
        addItemTooltip(GTNLItemList.DualInputHatchIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchLuV.set(
            new DualInputHatch(
                DUAL_INPUT_HATCH_LUV.ID,
                "DualInputHatchLuV",
                StatCollector.translateToLocal("DualInputHatchLuV"),
                6));
        addItemTooltip(GTNLItemList.DualInputHatchLuV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchZPM.set(
            new DualInputHatch(
                DUAL_INPUT_HATCH_ZPM.ID,
                "DualInputHatchZPM",
                StatCollector.translateToLocal("DualInputHatchZPM"),
                7));
        addItemTooltip(GTNLItemList.DualInputHatchZPM.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchUV.set(
            new DualInputHatch(
                DUAL_INPUT_HATCH_UV.ID,
                "DualInputHatchUV",
                StatCollector.translateToLocal("DualInputHatchUV"),
                8));
        addItemTooltip(GTNLItemList.DualInputHatchUV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchUHV.set(
            new DualInputHatch(
                DUAL_INPUT_HATCH_UHV.ID,
                "DualInputHatchUHV",
                StatCollector.translateToLocal("DualInputHatchUHV"),
                9));
        addItemTooltip(GTNLItemList.DualInputHatchUHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchUEV.set(
            new DualInputHatch(
                DUAL_INPUT_HATCH_UEV.ID,
                "DualInputHatchUEV",
                StatCollector.translateToLocal("DualInputHatchUEV"),
                10));
        addItemTooltip(GTNLItemList.DualInputHatchUEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchUIV.set(
            new DualInputHatch(
                DUAL_INPUT_HATCH_UIV.ID,
                "DualInputHatchUIV",
                StatCollector.translateToLocal("DualInputHatchUIV"),
                11));
        addItemTooltip(GTNLItemList.DualInputHatchUIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchUMV.set(
            new DualInputHatch(
                DUAL_INPUT_HATCH_UMV.ID,
                "DualInputHatchUMV",
                StatCollector.translateToLocal("DualInputHatchUMV"),
                12));
        addItemTooltip(GTNLItemList.DualInputHatchUMV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchUXV.set(
            new DualInputHatch(
                DUAL_INPUT_HATCH_UXV.ID,
                "DualInputHatchUXV",
                StatCollector.translateToLocal("DualInputHatchUXV"),
                13));
        addItemTooltip(GTNLItemList.DualInputHatchUXV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchMAX.set(
            new DualInputHatch(
                DUAL_INPUT_HATCH_MAX.ID,
                "DualInputHatchMAX",
                StatCollector.translateToLocal("DualInputHatchMAX"),
                14));
        addItemTooltip(GTNLItemList.DualInputHatchMAX.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SuperCraftingInputProxy.set(
            new SuperCraftingInputProxy(
                SUPER_CRAFTING_INPUT_PROXY.ID,
                "SuperCraftingInputProxy",
                StatCollector.translateToLocal("SuperCraftingInputProxy")).getStackForm(1L));
        addItemTooltip(GTNLItemList.SuperCraftingInputProxy.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SuperDataAccessHatch.set(
            new SuperDataAccessHatch(
                SUPER_DATA_ACCESS_HATCH.ID,
                "SuperDataAccessHatch",
                StatCollector.translateToLocal("SuperDataAccessHatch"),
                14));
        addItemTooltip(GTNLItemList.SuperDataAccessHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.BigSteamInputHatch.set(
            new CustomFluidHatch(
                ImmutableSet.of(
                    Materials.Steam.mGas,
                    FluidUtils.getSuperHeatedSteam(1)
                        .getFluid(),
                    Materials.DenseSupercriticalSteam.mGas,
                    MaterialPool.CompressedSteam.getMolten(1)
                        .getFluid()),
                4096000,
                BIG_STEAM_INPUT_HATCH.ID,
                "BigSteamInputHatch",
                StatCollector.translateToLocal("BigSteamInputHatch"),
                1,
                ItemUtils.PICTURE_GTNL_STEAM_LOGO));
        addItemTooltip(GTNLItemList.BigSteamInputHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchLV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_LV.ID,
                "ParallelControllerHatchLV",
                StatCollector.translateToLocal("ParallelControllerHatchLV"),
                1));
        addItemTooltip(GTNLItemList.ParallelControllerHatchLV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchMV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_MV.ID,
                "ParallelControllerHatchMV",
                StatCollector.translateToLocal("ParallelControllerHatchMV"),
                2));
        addItemTooltip(GTNLItemList.ParallelControllerHatchMV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchHV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_HV.ID,
                "ParallelControllerHatchHV",
                StatCollector.translateToLocal("ParallelControllerHatchHV"),
                3));
        addItemTooltip(GTNLItemList.ParallelControllerHatchHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchEV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_EV.ID,
                "ParallelControllerHatchEV",
                StatCollector.translateToLocal("ParallelControllerHatchEV"),
                4));
        addItemTooltip(GTNLItemList.ParallelControllerHatchEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchIV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_IV.ID,
                "ParallelControllerHatchIV",
                StatCollector.translateToLocal("ParallelControllerHatchIV"),
                5));
        addItemTooltip(GTNLItemList.ParallelControllerHatchIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchLuV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_LUV.ID,
                "ParallelControllerHatchLuV",
                StatCollector.translateToLocal("ParallelControllerHatchLuV"),
                6));
        addItemTooltip(GTNLItemList.ParallelControllerHatchLuV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchZPM.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_ZPM.ID,
                "ParallelControllerHatchZPM",
                StatCollector.translateToLocal("ParallelControllerHatchZPM"),
                7));
        addItemTooltip(GTNLItemList.ParallelControllerHatchZPM.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchUV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_UV.ID,
                "ParallelControllerHatchUV",
                StatCollector.translateToLocal("ParallelControllerHatchUV"),
                8));
        addItemTooltip(GTNLItemList.ParallelControllerHatchUV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchUHV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_UHV.ID,
                "ParallelControllerHatchUHV",
                StatCollector.translateToLocal("ParallelControllerHatchUHV"),
                9));
        addItemTooltip(GTNLItemList.ParallelControllerHatchUHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchUEV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_UEV.ID,
                "ParallelControllerHatchUEV",
                StatCollector.translateToLocal("ParallelControllerHatchUEV"),
                10));
        addItemTooltip(GTNLItemList.ParallelControllerHatchUEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchUIV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_UIV.ID,
                "ParallelControllerHatchUIV",
                StatCollector.translateToLocal("ParallelControllerHatchUIV"),
                11));
        addItemTooltip(GTNLItemList.ParallelControllerHatchUIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchUMV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_UMV.ID,
                "ParallelControllerHatchUMV",
                StatCollector.translateToLocal("ParallelControllerHatchUMV"),
                12));
        addItemTooltip(GTNLItemList.ParallelControllerHatchUMV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchUXV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_UXV.ID,
                "ParallelControllerHatchUXV",
                StatCollector.translateToLocal("ParallelControllerHatchUXV"),
                13));
        addItemTooltip(GTNLItemList.ParallelControllerHatchUXV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchMAX.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_MAX.ID,
                "ParallelControllerHatchMAX",
                StatCollector.translateToLocal("ParallelControllerHatchMAX"),
                14));
        addItemTooltip(GTNLItemList.ParallelControllerHatchMAX.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.TapDynamoHatchLV.set(
            new TapDynamoHatch(
                TAP_DYNAMO_HATCH.ID,
                "TapDynamoHatchLV",
                StatCollector.translateToLocal("TapDynamoHatchLV"),
                1));
        addItemTooltip(GTNLItemList.TapDynamoHatchLV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.PipelessSteamHatch.set(
            new WirelessSteamEnergyHatch(
                PIPELESS_STEAM_HATCH.ID,
                "PipelessSteamHatch",
                StatCollector.translateToLocal("PipelessSteamHatch"),
                0));
        addItemTooltip(GTNLItemList.PipelessSteamHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.PipelessSteamVent.set(
            new WirelessSteamDynamoHatch(
                PIPELESS_STEAM_VENT.ID,
                "PipelessSteamVent",
                StatCollector.translateToLocal("PipelessSteamVent"),
                0));
        addItemTooltip(GTNLItemList.PipelessSteamVent.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.PipelessJetstreamHatch.set(
            new WirelessSteamEnergyHatch(
                PIPELESS_JETSTREAM_HATCH.ID,
                "PipelessJetstreamHatch",
                StatCollector.translateToLocal("PipelessJetstreamHatch"),
                1));
        addItemTooltip(GTNLItemList.PipelessJetstreamHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.PipelessJetstreamVent.set(
            new WirelessSteamDynamoHatch(
                PIPELESS_JETSTREAM_VENT.ID,
                "PipelessJetstreamVent",
                StatCollector.translateToLocal("PipelessJetstreamVent"),
                1));
        addItemTooltip(GTNLItemList.PipelessJetstreamVent.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.AutoConfigurationMaintenanceHatch.set(
            new CustomMaintenanceHatch(
                AUTO_CONFIGURATION_MAINTENANCE_HATCH.ID,
                "AutoConfigurationMaintenanceHatch",
                StatCollector.translateToLocal("AutoConfigurationMaintenanceHatch"),
                80,
                120,
                5));
        addItemTooltip(GTNLItemList.AutoConfigurationMaintenanceHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ExplosionDynamoHatch.set(
            new ExplosionDynamoHatch(
                EXPLOSION_DYNAMO_HATCH.ID,
                "ExplosionDynamoHatch",
                StatCollector.translateToLocal("ExplosionDynamoHatch"),
                1));
        addItemTooltip(GTNLItemList.ExplosionDynamoHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DebugResearchStation.set(
            new DebugResearchStation(
                DEBUG_RESEARCH_STATION.ID,
                "DebugResearchStation",
                StatCollector.translateToLocal("DebugResearchStation"),
                14));
        addItemTooltip(GTNLItemList.DebugResearchStation.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SuperInputBusME.set(
            new SuperInputBusME(
                SUPER_INPUT_BUS_ME.ID,
                false,
                "SuperInputBusME",
                StatCollector.translateToLocal("SuperInputBusME")));
        addItemTooltip(GTNLItemList.SuperInputBusME.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.AdvancedSuperInputBusME.set(
            new SuperInputBusME(
                ADVANCED_SUPER_INPUT_BUS_ME.ID,
                true,
                "AdvancedSuperInputBusME",
                StatCollector.translateToLocal("AdvancedSuperInputBusME")));
        addItemTooltip(GTNLItemList.AdvancedSuperInputBusME.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SuperInputHatchME.set(
            new SuperInputHatchME(
                SUPER_INPUT_HATCH_ME.ID,
                false,
                "SuperInputHatchME",
                StatCollector.translateToLocal("SuperInputHatchME")));
        addItemTooltip(GTNLItemList.SuperInputHatchME.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.AdvancedSuperInputHatchME.set(
            new SuperInputHatchME(
                ADVANCED_SUPER_INPUT_HATCH_ME.ID,
                true,
                "AdvancedSuperInputHatchME",
                StatCollector.translateToLocal("AdvancedSuperInputHatchME")));
        addItemTooltip(GTNLItemList.AdvancedSuperInputHatchME.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.Replicator
            .set(new Replicator(REPLICATOR.ID, "Replicator", StatCollector.translateToLocal("Replicator"), 7));

        GTNLItemList.Enchanting
            .set(new Enchanting(ENCHANTING.ID, "Enchanting", StatCollector.translateToLocal("Enchanting"), 7));
        addItemTooltip(GTNLItemList.Enchanting.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.OredictInputBusME.set(
            new OredictInputBusME(
                OREDICT_INPUT_BUS_HATCH_ME.ID,
                "OredictInputBusME",
                StatCollector.translateToLocal("OredictInputBusME")));
        addItemTooltip(GTNLItemList.OredictInputBusME.get(1), AnimatedText.SNL_SKYINR);

        GTNLItemList.NanitesInputBus.set(
            new NanitesInputBus(
                NANITES_INPUT_BUS.ID,
                "NanitesInputBus",
                StatCollector.translateToLocal("NanitesInputBus")));
        addItemTooltip(GTNLItemList.NanitesInputBus.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.VaultPortHatch.set(
            new VaultPortHatch(
                VAULT_PORT_HATCH.ID,
                "VaultPortHatch",
                StatCollector.translateToLocal("VaultPortHatch")));
        addItemTooltip(GTNLItemList.VaultPortHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.OriginalInputHatch.set(
            new OriginalInputHatch(
                ORIGINAL_INPUT_HATCH.ID,
                "OriginalInputHatch",
                StatCollector.translateToLocal("OriginalInputHatch")));
        addItemTooltip(GTNLItemList.OriginalInputHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.OriginalOutputHatch.set(
            new OriginalOutputHatch(
                ORIGINAL_OUTPUT_HATCH.ID,
                "OriginalOutputHatch",
                StatCollector.translateToLocal("OriginalOutputHatch")));
        addItemTooltip(GTNLItemList.OriginalOutputHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SuperVoidBus
            .set(new SuperVoidBus(SUPER_VOID_BUS.ID, "SuperVoidBus", StatCollector.translateToLocal("SuperVoidBus")));
        addItemTooltip(GTNLItemList.SuperVoidBus.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SuperVoidHatch.set(
            new SuperVoidHatch(
                SUPER_VOID_HATCH.ID,
                "SuperVoidHatch",
                StatCollector.translateToLocal("SuperVoidHatch")));
        addItemTooltip(GTNLItemList.SuperVoidHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DebugDataAccessHatch.set(
            new DebugDataAccessHatch(
                DEBUG_DATA_ACCESS_HATCH.ID,
                "DebugDataAccessHatch",
                StatCollector.translateToLocal("DebugDataAccessHatch")));
        addItemTooltip(GTNLItemList.DebugDataAccessHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousDualInputHatchLV.set(
            new HumongousDualInputHatch(
                HUMONGOUS_DUAL_INPUT_HATCH_LV.ID,
                "HumongousDualInputHatchLV",
                StatCollector.translateToLocal("HumongousDualInputHatchLV"),
                1));
        addItemTooltip(GTNLItemList.HumongousDualInputHatchLV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousDualInputHatchMV.set(
            new HumongousDualInputHatch(
                HUMONGOUS_DUAL_INPUT_HATCH_MV.ID,
                "HumongousDualInputHatchMV",
                StatCollector.translateToLocal("HumongousDualInputHatchMV"),
                2));
        addItemTooltip(GTNLItemList.HumongousDualInputHatchMV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousDualInputHatchHV.set(
            new HumongousDualInputHatch(
                HUMONGOUS_DUAL_INPUT_HATCH_HV.ID,
                "HumongousDualInputHatchHV",
                StatCollector.translateToLocal("HumongousDualInputHatchHV"),
                3));
        addItemTooltip(GTNLItemList.HumongousDualInputHatchHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousDualInputHatchEV.set(
            new HumongousDualInputHatch(
                HUMONGOUS_DUAL_INPUT_HATCH_EV.ID,
                "HumongousDualInputHatchEV",
                StatCollector.translateToLocal("HumongousDualInputHatchEV"),
                4));
        addItemTooltip(GTNLItemList.HumongousDualInputHatchEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousDualInputHatchIV.set(
            new HumongousDualInputHatch(
                HUMONGOUS_DUAL_INPUT_HATCH_IV.ID,
                "HumongousDualInputHatchIV",
                StatCollector.translateToLocal("HumongousDualInputHatchIV"),
                5));
        addItemTooltip(GTNLItemList.HumongousDualInputHatchIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousDualInputHatchLuV.set(
            new HumongousDualInputHatch(
                HUMONGOUS_DUAL_INPUT_HATCH_LUV.ID,
                "HumongousDualInputHatchLuV",
                StatCollector.translateToLocal("HumongousDualInputHatchLuV"),
                6));
        addItemTooltip(GTNLItemList.HumongousDualInputHatchLuV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousDualInputHatchZPM.set(
            new HumongousDualInputHatch(
                HUMONGOUS_DUAL_INPUT_HATCH_ZPM.ID,
                "HumongousDualInputHatchZPM",
                StatCollector.translateToLocal("HumongousDualInputHatchZPM"),
                7));
        addItemTooltip(GTNLItemList.HumongousDualInputHatchZPM.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousDualInputHatchUV.set(
            new HumongousDualInputHatch(
                HUMONGOUS_DUAL_INPUT_HATCH_UV.ID,
                "HumongousDualInputHatchUV",
                StatCollector.translateToLocal("HumongousDualInputHatchUV"),
                8));
        addItemTooltip(GTNLItemList.HumongousDualInputHatchUV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousDualInputHatchUHV.set(
            new HumongousDualInputHatch(
                HUMONGOUS_DUAL_INPUT_HATCH_UHV.ID,
                "HumongousDualInputHatchUHV",
                StatCollector.translateToLocal("HumongousDualInputHatchUHV"),
                9));
        addItemTooltip(GTNLItemList.HumongousDualInputHatchUHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousDualInputHatchUEV.set(
            new HumongousDualInputHatch(
                HUMONGOUS_DUAL_INPUT_HATCH_UEV.ID,
                "HumongousDualInputHatchUEV",
                StatCollector.translateToLocal("HumongousDualInputHatchUEV"),
                10));
        addItemTooltip(GTNLItemList.HumongousDualInputHatchUEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousDualInputHatchUIV.set(
            new HumongousDualInputHatch(
                HUMONGOUS_DUAL_INPUT_HATCH_UIV.ID,
                "HumongousDualInputHatchUIV",
                StatCollector.translateToLocal("HumongousDualInputHatchUIV"),
                11));
        addItemTooltip(GTNLItemList.HumongousDualInputHatchUIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousDualInputHatchUMV.set(
            new HumongousDualInputHatch(
                HUMONGOUS_DUAL_INPUT_HATCH_UMV.ID,
                "HumongousDualInputHatchUMV",
                StatCollector.translateToLocal("HumongousDualInputHatchUMV"),
                12));
        addItemTooltip(GTNLItemList.HumongousDualInputHatchUMV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousDualInputHatchUXV.set(
            new HumongousDualInputHatch(
                HUMONGOUS_DUAL_INPUT_HATCH_UXV.ID,
                "HumongousDualInputHatchUXV",
                StatCollector.translateToLocal("HumongousDualInputHatchUXV"),
                13));
        addItemTooltip(GTNLItemList.HumongousDualInputHatchUXV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousDualInputHatchMAX.set(
            new HumongousDualInputHatch(
                HUMONGOUS_DUAL_INPUT_HATCH_MAX.ID,
                "HumongousDualInputHatchMAX",
                StatCollector.translateToLocal("HumongousDualInputHatchMAX"),
                14));
        addItemTooltip(GTNLItemList.HumongousDualInputHatchMAX.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ManaDynamoHatchLV.set(
            new ManaDynamoHatch(
                MANA_DYNAMO_HATCH_LV.ID,
                "ManaDynamoHatchLV",
                StatCollector.translateToLocal("ManaDynamoHatchLV"),
                1,
                16));
        addItemTooltip(GTNLItemList.ManaDynamoHatchLV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ManaDynamoHatchHV.set(
            new ManaDynamoHatch(
                MANA_DYNAMO_HATCH_HV.ID,
                "ManaDynamoHatchHV",
                StatCollector.translateToLocal("ManaDynamoHatchHV"),
                3,
                16));
        addItemTooltip(GTNLItemList.ManaDynamoHatchHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ManaDynamoHatchIV.set(
            new ManaDynamoHatch(
                MANA_DYNAMO_HATCH_IV.ID,
                "ManaDynamoHatchIV",
                StatCollector.translateToLocal("ManaDynamoHatchIV"),
                5,
                16));
        addItemTooltip(GTNLItemList.ManaDynamoHatchIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ManaDynamoHatchZPM.set(
            new ManaDynamoHatch(
                MANA_DYNAMO_HATCH_ZPM.ID,
                "ManaDynamoHatchZPM",
                StatCollector.translateToLocal("ManaDynamoHatchZPM"),
                7,
                16));
        addItemTooltip(GTNLItemList.ManaDynamoHatchZPM.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ManaEnergyHatchLV.set(
            new ManaEnergyHatch(
                MANA_ENERGY_HATCH_LV.ID,
                "ManaEnergyHatchLV",
                StatCollector.translateToLocal("ManaEnergyHatchLV"),
                1,
                16));
        addItemTooltip(GTNLItemList.ManaEnergyHatchLV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ManaEnergyHatchHV.set(
            new ManaEnergyHatch(
                MANA_ENERGY_HATCH_HV.ID,
                "ManaEnergyHatchHV",
                StatCollector.translateToLocal("ManaEnergyHatchHV"),
                3,
                16));
        addItemTooltip(GTNLItemList.ManaEnergyHatchHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ManaEnergyHatchIV.set(
            new ManaEnergyHatch(
                MANA_ENERGY_HATCH_IV.ID,
                "ManaEnergyHatchIV",
                StatCollector.translateToLocal("ManaEnergyHatchIV"),
                5,
                16));
        addItemTooltip(GTNLItemList.ManaEnergyHatchIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ManaEnergyHatchZPM.set(
            new ManaEnergyHatch(
                MANA_ENERGY_HATCH_ZPM.ID,
                "ManaEnergyHatchZPM",
                StatCollector.translateToLocal("ManaEnergyHatchZPM"),
                7,
                16));
        addItemTooltip(GTNLItemList.ManaEnergyHatchZPM.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ModFilteredInputBusME.set(
            new ModFilteredInputBusME(
                MOD_FILTERED_INPUT_BUS_ME.ID,
                "ModFilteredInputBusME",
                StatCollector.translateToLocal("ModFilteredInputBusME")));
        addItemTooltip(GTNLItemList.ModFilteredInputBusME.get(1), AnimatedText.SCIENCE_NOT_LEISURE);
    }

    @Deprecated
    public static void registerTestMachine() {
        GTNLItemList.QuadrupleOutputHatchEV.set(
            new DualOutputHatch(
                21700,
                4,
                "QuadrupleOutputHatchEV",
                StatCollector.translateToLocal("QuadrupleOutputHatchEV"),
                4));
        addItemTooltip(GTNLItemList.QuadrupleOutputHatchEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldOutputHatchEV.set(
            new DualOutputHatch(
                21701,
                9,
                "NinefoldOutputHatchEV",
                StatCollector.translateToLocal("NinefoldOutputHatchEV"),
                4));
        addItemTooltip(GTNLItemList.NinefoldOutputHatchEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);
    }

    public static void registerBasicMachine() {
        GTNLItemList.SteamTurbineLV.set(
            new SteamTurbine(
                STEAM_TURBINE_LV.ID,
                "SteamTurbineLV",
                StatCollector.translateToLocal("SteamTurbineLV"),
                1));
        addItemTooltip(GTNLItemList.SteamTurbineLV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamTurbineMV.set(
            new SteamTurbine(
                STEAM_TURBINE_MV.ID,
                "SteamTurbineMV",
                StatCollector.translateToLocal("SteamTurbineMV"),
                2));
        addItemTooltip(GTNLItemList.SteamTurbineMV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamTurbineHV.set(
            new SteamTurbine(
                STEAM_TURBINE_HV.ID,
                "SteamTurbineHV",
                StatCollector.translateToLocal("SteamTurbineHV"),
                3));
        addItemTooltip(GTNLItemList.SteamTurbineHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamAssemblerBronze.set(
            new SteamAssemblerBronze(
                STEAM_ASSEMBLER_BRONZE.ID,
                "SteamAssembler",
                StatCollector.translateToLocal("SteamAssemblerBronze")));
        addItemTooltip(GTNLItemList.SteamAssemblerBronze.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamAssemblerSteel.set(
            new SteamAssemblerSteel(
                STEAM_ASSEMBLER_STEEL.ID,
                "HighPressureSteamAssembler",
                StatCollector.translateToLocal("SteamAssemblerSteel")));
        addItemTooltip(GTNLItemList.SteamAssemblerSteel.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ManaTank.set(new ManaTank(MANA_TANK.ID, "ManaTank", StatCollector.translateToLocal("ManaTank")));
        addItemTooltip(GTNLItemList.ManaTank.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        for (GasCollectorTier tier : GasCollectorTier.values()) {
            IMetaTileEntity mte = new MTEBasicMachineWithRecipe(
                tier.id.ID,
                tier.unlocalizedName,
                StatCollector.translateToLocal(tier.unlocalizedName),
                tier.tier,
                new String[] { StatCollector.translateToLocal(tier.tooltipKey),
                    StatCollector.translateToLocal("GT5U.MBTT.MachineType") + ": "
                        + EnumChatFormatting.YELLOW
                        + StatCollector.translateToLocal("GasCollectorRecipeType")
                        + EnumChatFormatting.RESET },
                RecipePool.GasCollectorRecipes,
                3,
                3,
                true,
                SoundResource.NONE,
                MTEBasicMachineWithRecipe.SpecialEffects.NONE,
                "GAS_COLLECTOR");

            tier.itemEnum.set(mte);

            addItemTooltip(tier.itemEnum.get(1), AnimatedText.SCIENCE_NOT_LEISURE);
        }

        GTNLItemList.GasTurbineLV
            .set(new GasTurbine(GAS_TURBINE_LV.ID, "GasTurbineLV", StatCollector.translateToLocal("GasTurbineLV"), 1));
        addItemTooltip(GTNLItemList.GasTurbineLV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.GasTurbineMV
            .set(new GasTurbine(GAS_TURBINE_MV.ID, "GasTurbineMV", StatCollector.translateToLocal("GasTurbineMV"), 2));
        addItemTooltip(GTNLItemList.GasTurbineMV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.GasTurbineHV
            .set(new GasTurbine(GAS_TURBINE_HV.ID, "GasTurbineHV", StatCollector.translateToLocal("GasTurbineHV"), 3));
        addItemTooltip(GTNLItemList.GasTurbineHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DieselGeneratorLV.set(
            new DieselGenerator(
                DIESEL_GENERATOR_LV.ID,
                "DieselGeneratorLV",
                StatCollector.translateToLocal("DieselGeneratorLV"),
                1));
        addItemTooltip(GTNLItemList.DieselGeneratorLV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DieselGeneratorMV.set(
            new DieselGenerator(
                DIESEL_GENERATOR_MV.ID,
                "DieselGeneratorMV",
                StatCollector.translateToLocal("DieselGeneratorMV"),
                2));
        addItemTooltip(GTNLItemList.DieselGeneratorMV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DieselGeneratorHV.set(
            new DieselGenerator(
                DIESEL_GENERATOR_HV.ID,
                "DieselGeneratorHV",
                StatCollector.translateToLocal("DieselGeneratorHV"),
                3));
        addItemTooltip(GTNLItemList.DieselGeneratorHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.EnergyTransferNode.set(
            new EnergyTransferNode(
                ENERGY_TRANSFER_NODE.ID,
                "EnergyTransferNode",
                StatCollector.translateToLocal("EnergyTransferNode")));
        addItemTooltip(GTNLItemList.EnergyTransferNode.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.LootBagRedemption.set(
            new LootBagRedemption(
                LOOT_BAG_REDEMPTION.ID,
                "LootBagRedemption",
                StatCollector.translateToLocal("LootBagRedemption"),
                1));
        addItemTooltip(GTNLItemList.LootBagRedemption.get(1), AnimatedText.SCIENCE_NOT_LEISURE);
    }

    public static void registerWireAndPipe() {
        CrackRecipeAdder.registerWire(STAR_GATE_WIRE.ID, MaterialPool.Stargate, 2147483647, 2147483647, 0, true);
        MaterialUtils.generateGTFluidPipes(Materials.BlueAlloy, BLUE_ALLOY_PIPE.ID, 4000, 3000, true);
        CrackRecipeAdder.registerPipe(COMPRESSED_STEAM_PIPE.ID, MaterialPool.CompressedSteam, 250000, 10000, true);
        CrackRecipeAdder.registerPipe(STRONZE_PIPE.ID, MaterialPool.Stronze, 15000, 10000, true);
        CrackRecipeAdder.registerPipe(BREEL_PIPE.ID, MaterialPool.Breel, 10000, 10000, true);
        // 这个可用 MaterialUtils.generateNonGTFluidPipes(GregtechOrePrefixes.GT_Materials.Void, 22013, 500, 2000,
        // true);
        // 这个渲染炸了 MaterialUtils.registerPipeGTPP(22020, MaterialsAlloy.BLOODSTEEL, 123, 123, true);
    }

    private static void registerCovers() {
        CoverRegistry.registerCover(
            GTNLItemList.HydraulicPump.get(1L),
            TextureFactory.of(MACHINE_CASINGS[1][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverPump(context, 1048576, TextureFactory.of(OVERLAY_PUMP)));

        CoverRegistry.registerCover(
            GTNLItemList.HydraulicConveyor.get(1L),
            TextureFactory.of(MACHINE_CASINGS[1][0], TextureFactory.of(OVERLAY_CONVEYOR)),
            context -> new CoverConveyor(context, 1, 16, TextureFactory.of(OVERLAY_CONVEYOR)));

        CoverRegistry.registerCover(
            GTNLItemList.HydraulicRegulator.get(1L),
            TextureFactory.of(MACHINE_CASINGS[1][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverSteamRegulator(context, 1048576, TextureFactory.of(OVERLAY_PUMP)));

        CoverRegistry.registerCover(
            GTNLItemList.HydraulicSteamValve.get(1L),
            TextureFactory.of(MACHINE_CASINGS[1][0], TextureFactory.of(OVERLAY_VALVE)),
            context -> new CoverSteamValve(context, 16777216, TextureFactory.of(OVERLAY_VALVE)));

        CoverRegistry.registerCover(
            GTNLItemList.HydraulicSteamRegulator.get(1L),
            TextureFactory.of(MACHINE_CASINGS[1][0], TextureFactory.of(OVERLAY_VALVE)),
            context -> new CoverSteamRegulator(context, 16777216, TextureFactory.of(OVERLAY_VALVE)));

        CoverRegistry.registerCover(
            GTNLItemList.PipelessSteamCover.get(1L),
            TextureFactory.of(MACHINE_CASINGS[1][0], TextureFactory.of(OVERLAYS_ENERGY_ON_WIRELESS[0])),
            WirelessSteamCover::new,
            CoverRegistry.INTERCEPTS_RIGHT_CLICK_COVER_PLACER);

        CoverRegistry.registerCover(
            GTNLItemList.VoidCover.get(1L),
            TextureFactory.of(MACHINE_CASINGS[1][0], TextureFactory.of(ITEM_VOID_SIGN)),
            VoidCover::new,
            CoverRegistry.INTERCEPTS_RIGHT_CLICK_COVER_PLACER);

        // CoverRegistry.registerCover(
        // GTNLItemList.SteelTurbine.get(1L),
        // TextureFactory.of(TexturesGtBlock.Overlay_Water),
        // ctx -> new FluidCover(ctx, Materials.Water.mFluid, "InfinityWaterCover"),
        // CoverRegistry.INTERCEPTS_RIGHT_CLICK_COVER_PLACER);
    }

    public static void registerGlasses() {
        GlassTier.addCustomGlass(ItemRegistry.bw_realglas2, 1, 13, 1);
        if (MainConfig.enableRegisterPlayerDollGlass) {
            GlassTier.addCustomGlass(BlockLoader.playerDoll, 0, 14, 1);
        }
        GlassTier.addCustomGlass(ItemRegistry.bw_realglas2, 2, 14, 2);
        GlassTier.addCustomGlass(BlockLoader.metaBlockGlass, 0, 10, 2);
        GlassTier.addCustomGlass(BlockLoader.metaBlockGlass, 1, 8, 2);
        GlassTier.addCustomGlass(BlockLoader.metaBlockGlass, 2, 7, 1);

        for (int lampMeta = 1; lampMeta <= 32; lampMeta++) {
            GlassTier.addCustomGlass(BlockLoader.metaBlockGlow, lampMeta, 3, 1);
        }

        for (int lampOffMeta = 3; lampOffMeta <= 34; lampOffMeta++) {
            GlassTier.addCustomGlass(BlockLoader.metaBlock, lampOffMeta, 3, 1);
        }
    }

    public static void registry() {
        Logger.INFO("GTNL Content | Registering MTE Block Machine.");
        registerHatch();
        registerMachines();
        registerWireAndPipe();
        registerBasicMachine();
        registerCovers();

        if (MainConfig.enableIntegratedOreFactoryChange) {
            addItemTooltip(ItemList.Ore_Processor.get(1), AnimatedText.SCIENCE_NOT_LEISURE_CHANGE);
        }
    }

    public enum GasCollectorTier {

        LV(GTNLItemList.GasCollectorLV, GAS_COLLECTOR_LV, "GasCollectorLV", 1, "Tooltip_GasCollector_00"),
        MV(GTNLItemList.GasCollectorMV, GAS_COLLECTOR_MV, "GasCollectorMV", 2, "Tooltip_GasCollector_00"),
        HV(GTNLItemList.GasCollectorHV, GAS_COLLECTOR_HV, "GasCollectorHV", 3, "Tooltip_GasCollector_00"),
        EV(GTNLItemList.GasCollectorEV, GAS_COLLECTOR_EV, "GasCollectorEV", 4, "Tooltip_GasCollector_00"),
        IV(GTNLItemList.GasCollectorIV, GAS_COLLECTOR_IV, "GasCollectorIV", 5, "Tooltip_GasCollector_01"),
        LuV(GTNLItemList.GasCollectorLuV, GAS_COLLECTOR_LUV, "GasCollectorLuV", 6, "Tooltip_GasCollector_01"),
        ZPM(GTNLItemList.GasCollectorZPM, GAS_COLLECTOR_ZPM, "GasCollectorZPM", 7, "Tooltip_GasCollector_01"),
        UV(GTNLItemList.GasCollectorUV, GAS_COLLECTOR_UV, "GasCollectorUV", 8, "Tooltip_GasCollector_02"),
        UHV(GTNLItemList.GasCollectorUHV, GAS_COLLECTOR_UHV, "GasCollectorUHV", 9, "Tooltip_GasCollector_02"),
        UEV(GTNLItemList.GasCollectorUEV, GAS_COLLECTOR_UEV, "GasCollectorUEV", 10, "Tooltip_GasCollector_02"),
        UIV(GTNLItemList.GasCollectorUIV, GAS_COLLECTOR_UIV, "GasCollectorUIV", 11, "Tooltip_GasCollector_02"),
        UMV(GTNLItemList.GasCollectorUMV, GAS_COLLECTOR_UMV, "GasCollectorUMV", 12, "Tooltip_GasCollector_02"),
        UXV(GTNLItemList.GasCollectorUXV, GAS_COLLECTOR_UXV, "GasCollectorUXV", 13, "Tooltip_GasCollector_03");

        public final GTNLItemList itemEnum;
        public final GTNLMachineID id;
        public final String unlocalizedName;
        public final int tier;
        public final String tooltipKey;

        GasCollectorTier(GTNLItemList itemEnum, GTNLMachineID id, String name, int tier, String tooltipKey) {
            this.itemEnum = itemEnum;
            this.id = id;
            this.unlocalizedName = name;
            this.tier = tier;
            this.tooltipKey = tooltipKey;
        }
    }
}
