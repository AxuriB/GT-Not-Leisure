package com.science.gtnl.loader;

import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.addItemTooltip;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.text;
import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.Utils.item.TextHandler;
import com.science.gtnl.Utils.text.AnimatedText;
import com.science.gtnl.client.GTNLCreativeTabs;
import com.science.gtnl.common.item.ItemRecord;
import com.science.gtnl.common.item.MetaItemAdder;
import com.science.gtnl.common.item.items.CircuitIntegratedPlus;
import com.science.gtnl.common.item.items.FakeItemSiren;
import com.science.gtnl.common.item.items.FuelRod.FuelRod;
import com.science.gtnl.common.item.items.FuelRod.FuelRodDepleted;
import com.science.gtnl.common.item.items.ItemSteamRocket;
import com.science.gtnl.common.item.items.KFCFamily;
import com.science.gtnl.common.item.items.PhysicsCape;
import com.science.gtnl.common.item.items.RejectionRing;
import com.science.gtnl.common.item.items.SatietyRing;
import com.science.gtnl.common.item.items.SuperReachRing;
import com.science.gtnl.common.item.items.TestItem;
import com.science.gtnl.common.item.items.TimeStopPocketWatch;
import com.science.gtnl.common.item.items.TwilightSword;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.common.render.items.InfinityMetaItemRenderer;

public class ItemLoader {

    public static Item SteamRocket = new ItemSteamRocket();
    public static Item FakeItemSiren = new FakeItemSiren();
    public static Item TestItem = new TestItem();
    public static Item KFCFamily = new KFCFamily(20, 20, true);
    public static Item SatietyRing = new SatietyRing();
    public static Item RejectionRing = new RejectionRing();
    public static Item TwilightSword = new TwilightSword();
    public static Item PhysicsCape = new PhysicsCape();
    public static Item CircuitIntegratedPlus = new CircuitIntegratedPlus();
    public static Item TimeStopPocketWatch = new TimeStopPocketWatch();
    public static Item SuperReachRing = new SuperReachRing();
    public static Item RecordSus = new ItemRecord("sus");
    public static Item RecordNewHorizons = new ItemRecord("newhorizons");

    public static Item InfinityFuelRodDepleted = new FuelRodDepleted("InfinityFuelRodDepleted", 2000);
    public static Item InfinityFuelRod = new FuelRod(
        "InfinityFuelRod",
        1,
        491520,
        500,
        15000,
        160000,
        70F,
        new ItemStack(InfinityFuelRodDepleted, 1));

    public static final Item MetaItem = new MetaItemAdder("MetaItemBase", "MetaItem", GTNLCreativeTabs.GTNotLeisureItem)
        .setTextureName(RESOURCE_ROOT_ID + ":" + "MetaItem/0");

    public static void registryItems() {
        Item[] itemsToReg = { MetaItem };

        for (Item item : itemsToReg) {
            GameRegistry.registerItem(item, item.getUnlocalizedName());
        }

        GTNLItemList.RecordSus.set(new ItemStack(RecordSus, 1));
        GTNLItemList.RecordNewHorizons.set(new ItemStack(RecordNewHorizons, 1));
        GTNLItemList.InfinityFuelRodDepleted.set(new ItemStack(InfinityFuelRodDepleted, 1));
        GTNLItemList.InfinityFuelRod.set(new ItemStack(InfinityFuelRod, 1));

        IRegistry(RecordSus, "RecordSus");
        IRegistry(RecordNewHorizons, "RecordNewHorizons");
        IRegistry(InfinityFuelRodDepleted, "InfinityFuelRodDepleted");
        IRegistry(InfinityFuelRod, "InfinityFuelRod");
    }

    public static void IRegistry(Item item, String name) {
        GameRegistry.registerItem(item, name);
    }

    public static void registryItemContainers() {
        GTNLItemList.TrollFace.set(
            MetaItemAdder.initItem(
                "TrollFace",
                0,
                new String[] { TextHandler.texter("Never Gonna Give You Up~", "tooltips.TrollFace") }));
        GTNLItemList.DepletedExcitedNaquadahFuelRod.set(
            MetaItemAdder.initItem(
                "§bDepleted Excited Naquadah Fuel Rod",
                1,
                new String[] { TextHandler
                    .texter("§oUltimate Form of Naquadah Fuel.", "tooltips.DepletedExcitedNaquadahFuelRod") }));
        GTNLItemList.BlazeCube.set(
            MetaItemAdder.initItem(
                "§eBlaze Cube",
                2,
                new String[] { TextHandler.texter("§8§oBlaze Storm.", "tooltips.BlazeCube") }));
        GTNLItemList.EnhancementCore.set(
            MetaItemAdder.initItem(
                "Enhancement Core",
                3,
                new String[] { TextHandler.texter("§8§oThe road to completion!", "tooltips.EnhancementCore") }));
        GTNLItemList.StellarConstructionFrameMaterial.set(
            MetaItemAdder.initItem(
                "Stellar Construction Frame Material",
                4,
                new String[] { TextHandler.texter("A test item, no use.", "tooltips.TestItem0.line1") }));
        GTNLItemList.ActivatedGaiaPylon.set(MetaItemAdder.initItem("Activated Gaia Pylon", 5));
        GTNLItemList.PrecisionSteamMechanism.set(MetaItemAdder.initItem("Precision Steam Mechanism", 6));
        GTNLItemList.MeteorMinerSchematic1.set(
            MetaItemAdder.initItem(
                "Meteor Miner Tier 1 Schematic",
                7,
                new String[] { TextHandler
                    .texter("Schematic needed to enable the Tier 1 Meteor Miner", "tooltips.MeteorMinerSchematic1") }));
        GTNLItemList.MeteorMinerSchematic2.set(
            MetaItemAdder.initItem(
                "Meteor Miner Tier 2 Schematic",
                8,
                new String[] { TextHandler
                    .texter("Schematic needed to enable the Tier 2 Meteor Miner", "tooltips.MeteorMinerSchematic2") }));
        GTNLItemList.CircuitResonaticULV.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic ULV",
                9,
                new String[] { TextHandler.texter("An Original Circuit", "tooltips.CircuitResonaticULV_00"),
                    TextHandler.texter("§fULV-Tier", "tooltips.CircuitResonaticULV_01") }));
        GTNLItemList.CircuitResonaticLV.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic LV",
                10,
                new String[] { TextHandler.texter("An Basic Circuit", "tooltips.CircuitResonaticLV_00"),
                    TextHandler.texter("LV-Tier", "tooltips.CircuitResonaticLV_01") }));
        GTNLItemList.CircuitResonaticMV.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic MV",
                11,
                new String[] { TextHandler.texter("An Good Circuit", "tooltips.CircuitResonaticMV_00"),
                    TextHandler.texter("§6MV-Tier", "tooltips.CircuitResonaticMV_01") }));
        GTNLItemList.CircuitResonaticHV.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic HV",
                12,
                new String[] { TextHandler.texter("An Adavanced Circuit", "tooltips.CircuitResonaticHV_00"),
                    TextHandler.texter("§eHV-Tier", "tooltips.CircuitResonaticHV_01") }));
        GTNLItemList.CircuitResonaticEV.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic EV",
                13,
                new String[] { TextHandler.texter("An Data Circuit", "tooltips.CircuitResonaticEV_00"),
                    TextHandler.texter("§8EV-Tier", "tooltips.CircuitResonaticEV_01") }));
        GTNLItemList.CircuitResonaticIV.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic IV",
                14,
                new String[] { TextHandler.texter("An Elite Circuit", "tooltips.CircuitResonaticIV_00"),
                    TextHandler.texter("§aIV-Tier", "tooltips.CircuitResonaticIV_01") }));
        GTNLItemList.CircuitResonaticLuV.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic LuV",
                15,
                new String[] { TextHandler.texter("An Master Circuit", "tooltips.CircuitResonaticLuV_00"),
                    TextHandler.texter("§dLuV-Tier", "tooltips.CircuitResonaticLuV_01") }));
        GTNLItemList.CircuitResonaticZPM.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic ZPM",
                16,
                new String[] { TextHandler.texter("An Ultimate Circuit", "tooltips.CircuitResonaticZPM_00"),
                    TextHandler.texter("§bZPM-Tier", "tooltips.CircuitResonaticZPM_01") }));
        GTNLItemList.CircuitResonaticUV.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic UV",
                17,
                new String[] { TextHandler.texter("An Superconductor Circuit", "tooltips.CircuitResonaticUV_00"),
                    TextHandler.texter("§2UV-Tier", "tooltips.CircuitResonaticUV_01") }));
        GTNLItemList.CircuitResonaticUHV.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic UHV",
                18,
                new String[] { TextHandler.texter("An Infinite Circuit", "tooltips.CircuitResonaticUHV_00"),
                    TextHandler.texter("§4UHV-Tier", "tooltips.CircuitResonaticUHV_01") }));
        GTNLItemList.CircuitResonaticUEV.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic UEV",
                19,
                new String[] { TextHandler.texter("An Bio Circuit", "tooltips.CircuitResonaticUEV_00"),
                    TextHandler.texter("§5UEV-Tier", "tooltips.CircuitResonaticUEV_01") }));
        GTNLItemList.CircuitResonaticUIV.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic UIV",
                20,
                new String[] { TextHandler.texter("An Optical Circuit", "tooltips.CircuitResonaticUIV_00"),
                    TextHandler.texter("§l§1UIV-Tier", "tooltips.CircuitResonaticUIV_01") }));
        GTNLItemList.VerySimpleCircuit.set(
            MetaItemAdder.initItem(
                "Very Simple Circuit",
                21,
                new String[] { TextHandler.texter("An Very Simple Circuit", "tooltips.VerySimpleCircuit_00"),
                    TextHandler.texter("§fULV-Tier", "tooltips.VerySimpleCircuit_01") }));
        GTNLItemList.SimpleCircuit.set(
            MetaItemAdder.initItem(
                "Simple Circuit",
                22,
                new String[] { TextHandler.texter("An Simple Circuit", "tooltips.SimpleCircuit_00"),
                    TextHandler.texter("LV-Tier", "tooltips.SimpleCircuit_01") }));
        GTNLItemList.BasicCircuit.set(
            MetaItemAdder.initItem(
                "Basic Circuit",
                23,
                new String[] { TextHandler.texter("An Basic Circuit", "tooltips.BasicCircuit_00"),
                    TextHandler.texter("§6MV-Tier", "tooltips.BasicCircuit_01") }));
        GTNLItemList.AdvancedCircuit.set(
            MetaItemAdder.initItem(
                "Advanced Circuit",
                24,
                new String[] { TextHandler.texter("An Advanced Circuit", "tooltips.AdvancedCircuit_00"),
                    TextHandler.texter("§eHV-Tier", "tooltips.AdvancedCircuit_01") }));
        GTNLItemList.EliteCircuit.set(
            MetaItemAdder.initItem(
                "Elite Circuit",
                25,
                new String[] { TextHandler.texter("An Elite Circuit", "tooltips.EliteCircuit_00"),
                    TextHandler.texter("§8EV-Tier", "tooltips.EliteCircuit_01") }));
        GTNLItemList.StargateSingularity.set(MetaItemAdder.initItem("Stargate Singularity", 26))
            .setRender(new InfinityMetaItemRenderer());
        GTNLItemList.StargateCompressedSingularity.set(MetaItemAdder.initItem("Stargate Compressed Singularity", 27))
            .setRender(new InfinityMetaItemRenderer());
        GTNLItemList.BiowareSMDCapacitor.set(MetaItemAdder.initItem("Bioware SMD Capacitor", 28));
        GTNLItemList.BiowareSMDDiode.set(MetaItemAdder.initItem("Bioware SMD Diode", 29));
        GTNLItemList.BiowareSMDInductor.set(MetaItemAdder.initItem("Bioware SMD Inductor", 30));
        GTNLItemList.BiowareSMDResistor.set(MetaItemAdder.initItem("Bioware SMD Resistor", 31));
        GTNLItemList.BiowareSMDTransistor.set(MetaItemAdder.initItem("Bioware SMD Transistor", 32));
        GTNLItemList.CosmicSMDCapacitor.set(MetaItemAdder.initItem("Cosmic SMD Capacitor", 33));
        GTNLItemList.CosmicSMDDiode.set(MetaItemAdder.initItem("Cosmic SMD Diode", 34));
        GTNLItemList.CosmicSMDInductor.set(MetaItemAdder.initItem("Cosmic SMD Inductor", 35));
        GTNLItemList.CosmicSMDResistor.set(MetaItemAdder.initItem("Cosmic SMD Resistor", 36));
        GTNLItemList.CosmicSMDTransistor.set(MetaItemAdder.initItem("Cosmic SMD Transistor", 37));
        GTNLItemList.ExoticSMDCapacitor.set(MetaItemAdder.initItem("Exotic SMD Capacitor", 38));
        GTNLItemList.ExoticSMDDiode.set(MetaItemAdder.initItem("Exotic SMD Diode", 39));
        GTNLItemList.ExoticSMDInductor.set(MetaItemAdder.initItem("Exotic SMD Inductor", 40));
        GTNLItemList.ExoticSMDResistor.set(MetaItemAdder.initItem("Exotic SMD Resistor", 41));
        GTNLItemList.ExoticSMDTransistor.set(MetaItemAdder.initItem("Exotic SMD Transistor", 42));
        GTNLItemList.TemporallySMDCapacitor.set(MetaItemAdder.initItem("Temporally SMD Capacitor", 43));
        GTNLItemList.TemporallySMDDiode.set(MetaItemAdder.initItem("Temporally SMD Diode", 44));
        GTNLItemList.TemporallySMDInductor.set(MetaItemAdder.initItem("Temporally SMD Inductor", 45));
        GTNLItemList.TemporallySMDResistor.set(MetaItemAdder.initItem("Temporally SMD Resistor", 46));
        GTNLItemList.TemporallySMDTransistor.set(MetaItemAdder.initItem("Temporally SMD Transistor", 47));
        GTNLItemList.LVParallelControllerCore.set(
            MetaItemAdder.initItem(
                "LV Parallel Controller Core",
                48,
                new String[] { TextHandler.texter("§dSpeed: +1%%", "tooltips.LVParallelControllerCore_00"),
                    TextHandler.texter("§5EU Usage: -2%%", "tooltips.LVParallelControllerCore_01"),
                    TextHandler.texter("§bParallel: 0", "tooltips.LVParallelControllerCore_02") }));

        GTNLItemList.MVParallelControllerCore.set(
            MetaItemAdder.initItem(
                "MV Parallel Controller Core",
                49,
                new String[] { TextHandler.texter("§dSpeed: +2%%", "tooltips.MVParallelControllerCore_00"),
                    TextHandler.texter("§5EU Usage: -4%%", "tooltips.MVParallelControllerCore_01"),
                    TextHandler.texter("§bParallel: 1", "tooltips.MVParallelControllerCore_02") }));

        GTNLItemList.HVParallelControllerCore.set(
            MetaItemAdder.initItem(
                "HV Parallel Controller Core",
                50,
                new String[] { TextHandler.texter("§dSpeed: +4%%", "tooltips.HVParallelControllerCore_00"),
                    TextHandler.texter("§5EU Usage: -6%%", "tooltips.HVParallelControllerCore_01"),
                    TextHandler.texter("§bParallel: 4", "tooltips.HVParallelControllerCore_02") }));

        GTNLItemList.EVParallelControllerCore.set(
            MetaItemAdder.initItem(
                "EV Parallel Controller Core",
                51,
                new String[] { TextHandler.texter("§dSpeed: +6%%", "tooltips.EVParallelControllerCore_00"),
                    TextHandler.texter("§5EU Usage: -8%%", "tooltips.EVParallelControllerCore_01"),
                    TextHandler.texter("§bParallel: 16", "tooltips.EVParallelControllerCore_02") }));

        GTNLItemList.IVParallelControllerCore.set(
            MetaItemAdder.initItem(
                "IV Parallel Controller Core",
                52,
                new String[] { TextHandler.texter("§dSpeed: +8%%", "tooltips.IVParallelControllerCore_00"),
                    TextHandler.texter("§5EU Usage: -10%%", "tooltips.IVParallelControllerCore_01"),
                    TextHandler.texter("§bParallel: 64", "tooltips.IVParallelControllerCore_02") }));

        GTNLItemList.LuVParallelControllerCore.set(
            MetaItemAdder.initItem(
                "LuV Parallel Controller Core",
                53,
                new String[] { TextHandler.texter("§dSpeed: +10%%", "tooltips.LuVParallelControllerCore_00"),
                    TextHandler.texter("§5EU Usage: -12%%", "tooltips.LuVParallelControllerCore_01"),
                    TextHandler.texter("§bParallel: 256", "tooltips.LuVParallelControllerCore_02") }));

        GTNLItemList.ZPMParallelControllerCore.set(
            MetaItemAdder.initItem(
                "ZPM Parallel Controller Core",
                54,
                new String[] { TextHandler.texter("§dSpeed: +12%%", "tooltips.ZPMParallelControllerCore_00"),
                    TextHandler.texter("§5EU Usage: -14%%", "tooltips.ZPMParallelControllerCore_01"),
                    TextHandler.texter("§bParallel: 1024", "tooltips.ZPMParallelControllerCore_02") }));

        GTNLItemList.UVParallelControllerCore.set(
            MetaItemAdder.initItem(
                "UV Parallel Controller Core",
                55,
                new String[] { TextHandler.texter("§dSpeed: +14%%", "tooltips.UVParallelControllerCore_00"),
                    TextHandler.texter("§5EU Usage: -16%%", "tooltips.UVParallelControllerCore_01"),
                    TextHandler.texter("§bParallel: 4096", "tooltips.UVParallelControllerCore_02") }));

        GTNLItemList.UHVParallelControllerCore.set(
            MetaItemAdder.initItem(
                "UHV Parallel Controller Core",
                56,
                new String[] { TextHandler.texter("§dSpeed: +16%%", "tooltips.UHVParallelControllerCore_00"),
                    TextHandler.texter("§5EU Usage: -18%%", "tooltips.UHVParallelControllerCore_01"),
                    TextHandler.texter("§bParallel: 16384", "tooltips.UHVParallelControllerCore_02") }));

        GTNLItemList.UEVParallelControllerCore.set(
            MetaItemAdder.initItem(
                "UEV Parallel Controller Core",
                57,
                new String[] { TextHandler.texter("§dSpeed: +18%%", "tooltips.UEVParallelControllerCore_00"),
                    TextHandler.texter("§5EU Usage: -20%%", "tooltips.UEVParallelControllerCore_01"),
                    TextHandler.texter("§bParallel: 65536", "tooltips.UEVParallelControllerCore_02") }));

        GTNLItemList.UIVParallelControllerCore.set(
            MetaItemAdder.initItem(
                "UIV Parallel Controller Core",
                58,
                new String[] { TextHandler.texter("§dSpeed: +20%%", "tooltips.UIVParallelControllerCore_00"),
                    TextHandler.texter("§5EU Usage: -22%%", "tooltips.UIVParallelControllerCore_01"),
                    TextHandler.texter("§bParallel: 262144", "tooltips.UIVParallelControllerCore_02") }));

        GTNLItemList.UMVParallelControllerCore.set(
            MetaItemAdder.initItem(
                "UMV Parallel Controller Core",
                59,
                new String[] { TextHandler.texter("§dSpeed: +22%%", "tooltips.UMVParallelControllerCore_00"),
                    TextHandler.texter("§5EU Usage: -24%%", "tooltips.UMVParallelControllerCore_01"),
                    TextHandler.texter("§bParallel: 1048576", "tooltips.UMVParallelControllerCore_02") }));

        GTNLItemList.UXVParallelControllerCore.set(
            MetaItemAdder.initItem(
                "UXV Parallel Controller Core",
                60,
                new String[] { TextHandler.texter("§dSpeed: +24%%", "tooltips.UXVParallelControllerCore_00"),
                    TextHandler.texter("§5EU Usage: -26%%", "tooltips.UXVParallelControllerCore_01"),
                    TextHandler.texter("§bParallel: 4194304", "tooltips.UXVParallelControllerCore_02") }));

        GTNLItemList.MAXParallelControllerCore.set(
            MetaItemAdder.initItem(
                "MAX Parallel Controller Core",
                61,
                new String[] { TextHandler.texter("§dSpeed: +26%%", "tooltips.MAXParallelControllerCore_00"),
                    TextHandler.texter("§5EU Usage: -28%%", "tooltips.MAXParallelControllerCore_01"),
                    TextHandler.texter("§bParallel: 16777216", "tooltips.MAXParallelControllerCore_02") }));

        GTNLItemList.NagaBook.set(MetaItemAdder.initItem("§7Canard - Naga Book", 62));
        GTNLItemList.TwilightForestBook.set(MetaItemAdder.initItem("§bUrban legends - Twilight Forest Book", 63));
        GTNLItemList.LichBook.set(MetaItemAdder.initItem("§6Urban Myth - Lich Book", 64));
        GTNLItemList.MinotaurBook.set(MetaItemAdder.initItem("§9Urban Legend - Minotaur Book", 65));
        GTNLItemList.HydraBook.set(MetaItemAdder.initItem("§9Urban Legend - Hydra Book", 66));
        GTNLItemList.KnightPhantomBook.set(MetaItemAdder.initItem("§aUrban Plague - Knight Phantom Book", 67));
        GTNLItemList.UrGhastBook.set(MetaItemAdder.initItem("§aUrban Plague - Ur-Ghast Book", 68));
        GTNLItemList.AlphaYetiBook.set(MetaItemAdder.initItem("§aUrban Plague - Alpha Yeti Book", 69));
        GTNLItemList.SnowQueenBook.set(MetaItemAdder.initItem("§5Urban Nightmare - Snow Queen Book", 70));
        GTNLItemList.FinalBook.set(MetaItemAdder.initItem("§cImpurity - Final Book", 71));
        GTNLItemList.GiantBook.set(MetaItemAdder.initItem("§5Urban Nightmare - Giant Book", 72));
        GTNLItemList.ClayedGlowstone.set(MetaItemAdder.initItem("Clayed Glowstone", 73));
        GTNLItemList.QuantumDisk.set(MetaItemAdder.initItem("Quantum Disk", 74));
        GTNLItemList.NeutroniumBoule.set(
            MetaItemAdder.initItem(
                "Neutronium Boule",
                75,
                new String[] { TextHandler.texter("Raw Circuit", "tooltips.NeutroniumBoule_00") }));
        GTNLItemList.NeutroniumWafer.set(
            MetaItemAdder.initItem(
                "Neutronium Wafer",
                76,
                new String[] { TextHandler.texter("Raw Circuit", "tooltips.NeutroniumWafer_00") }));
        GTNLItemList.HighlyAdvancedSocWafer.set(
            MetaItemAdder.initItem(
                "Highly Advanced Soc Wafer",
                77,
                new String[] { TextHandler.texter("Raw Circuit", "tooltips.HighlyAdvancedSocWafer_00") }));
        GTNLItemList.HighlyAdvancedSoc.set(
            MetaItemAdder.initItem(
                "Highly Advanced Soc",
                78,
                new String[] { TextHandler.texter("Highly Advanced SoC", "tooltips.HighlyAdvancedSoc_00") }));
        GTNLItemList.ZnFeAlClCatalyst.set(MetaItemAdder.initItem("Zn-Fe-Al-Cl Catalyst", 79));
        GTNLItemList.BlackLight.set(
            MetaItemAdder.initItem(
                "Black Light",
                80,
                new String[] {
                    TextHandler.texter("§8long-wave §dultraviolet §8light source", "tooltips.BlackLight_00") }));
        GTNLItemList.SteamgateDialingDevice.set(
            MetaItemAdder.initItem(
                "Steamgate Dialing Device",
                81,
                new String[] {
                    TextHandler.texter("Links steamgates for teleportation.", "tooltips.SteamgateDialingDevice_00") }));
        GTNLItemList.SteamgateChevron.set(MetaItemAdder.initItem("Steamgate Chevron", 82));
        GTNLItemList.SteamgateChevronUpgrade.set(MetaItemAdder.initItem("Steamgate Chevron Upgrade", 83));
        GTNLItemList.SteamgateIrisBlade.set(MetaItemAdder.initItem("Steamgate Iris Blade", 84));
        GTNLItemList.SteamgateIrisUpgrade.set(MetaItemAdder.initItem("Steamgate Iris Upgrade", 85));
        GTNLItemList.SteamgateHeatContainmentPlate.set(
            MetaItemAdder.initItem(
                "Steamgate Heat Containment Plate",
                86,
                new String[] { TextHandler
                    .texter("Perfect temperature stability...", "tooltips.SteamgateHeatContainmentPlate_00") }));
        GTNLItemList.SteamgateFrame.set(
            MetaItemAdder.initItem(
                "Steamgate Frame",
                87,
                new String[] {
                    TextHandler.texter("Innumerable interlocking gears...", "tooltips.SteamgateFrame_00") }));
        GTNLItemList.SteamgateCoreCrystal.set(
            MetaItemAdder.initItem(
                "Steamgate Core Crystal",
                88,
                new String[] { TextHandler
                    .texter("A crystal made of pure condensed staem...", "tooltips.SteamgateCoreCrystal_00") }));
        GTNLItemList.HydraulicMotor.set(MetaItemAdder.initItem("Hydraulic Motor", 89));
        GTNLItemList.HydraulicPiston.set(MetaItemAdder.initItem("Hydraulic Piston", 90));
        GTNLItemList.HydraulicPump.set(
            MetaItemAdder.initItem(
                "Hydraulic Pump",
                91,
                new String[] {
                    TextHandler.texter("1,048,576 L/t (20,971,520 L/s) as Cover", "tooltips.HydraulicPump_00") }));
        GTNLItemList.HydraulicArm.set(MetaItemAdder.initItem("Hydraulic Arm", 92));
        GTNLItemList.HydraulicConveyor.set(
            MetaItemAdder.initItem(
                "Hydraulic Conveyor",
                93,
                new String[] {
                    TextHandler.texter("16 stacks every 1/20 sec (as Cover)", "tooltips.HydraulicConveyor_00") }));
        GTNLItemList.HydraulicRegulator.set(
            MetaItemAdder.initItem(
                "Hydraulic Regulator",
                94,
                new String[] {
                    TextHandler
                        .texter("Configurable up to 20,971,520 L/sec (as Cover)", "tooltips.HydraulicRegulator_00"),
                    TextHandler.texter(
                        "Rightclick/Screwdriver-rightclick/Shift-screwdriver-rightclick",
                        "tooltips.HydraulicRegulator_01"),
                    TextHandler.texter(
                        "to adjust the pump speed by 1/16/256 L/sec per click",
                        "tooltips.HydraulicRegulator_02") }));
        GTNLItemList.HydraulicVaporGenerator.set(MetaItemAdder.initItem("Hydraulic Vapor Generator", 95));
        GTNLItemList.HydraulicSteamJetSpewer.set(MetaItemAdder.initItem("Hydraulic Steam Jet Spewer", 96));
        GTNLItemList.HydraulicSteamReceiver.set(MetaItemAdder.initItem("Hydraulic Steam Receiver", 97));
        GTNLItemList.HydraulicSteamValve.set(
            MetaItemAdder.initItem(
                "Hydraulic Steam Valve",
                98,
                new String[] { TextHandler
                    .texter("16,777,216 L/t (335,544,320 L/s) as Cover", "tooltips.HydraulicSteamValve_00") }));
        addItemTooltip(
            GTNLItemList.HydraulicSteamValve.get(1),
            AnimatedText.buildTextWithAnimatedEnd(text("Tips: 瑶光Alkaid要的")));
        GTNLItemList.HydraulicSteamRegulator.set(
            MetaItemAdder.initItem(
                "Hydraulic Steam Regulator",
                99,
                new String[] { TextHandler
                    .texter("16,777,216 L/t (335,544,320 L/s) as Cover", "tooltips.HydraulicSteamRegulator_00") }));
        addItemTooltip(
            GTNLItemList.HydraulicSteamRegulator.get(1),
            AnimatedText.buildTextWithAnimatedEnd(text("Tips: 瑶光Alkaid要的")));
        GTNLItemList.SadBapyCatToken.set(
            MetaItemAdder.initItem(
                "SadBapyCat Token",
                100,
                new String[] { TextHandler.texter(":sadbapycat:", "tooltips.SadBapyCatToken_00") }));
        GTNLItemList.CompressedSteamTurbine.set(
            MetaItemAdder.initItem(
                "Compressed Steam Turbine",
                101,
                new String[] { TextHandler.texter("Infinite Throughtput", "tooltips.CompressedSteamTurbine_00") }));
        GTNLItemList.SteelTurbine.set(
            MetaItemAdder.initItem(
                "Steel Turbine",
                102,
                new String[] { TextHandler.texter("Stable like a Table", "tooltips.SteelTurbine_00") }));

    }

    public static void registry() {
        registryItems();
        registryItemContainers();

        addItemTooltip(
            new ItemStack(ItemLoader.SatietyRing, 1),
            AnimatedText.buildTextWithAnimatedEnd(text("Most machine recipe by zero_CM")));
    }

    public static void registryOreDictionary() {

        GTOreDictUnificator
            .registerOre(OrePrefixes.circuit.get(Materials.ULV), GTNLItemList.CircuitResonaticULV.get(1));
        GTOreDictUnificator.registerOre(OrePrefixes.circuit.get(Materials.LV), GTNLItemList.CircuitResonaticLV.get(1));
        GTOreDictUnificator.registerOre(OrePrefixes.circuit.get(Materials.MV), GTNLItemList.CircuitResonaticMV.get(1));
        GTOreDictUnificator.registerOre(OrePrefixes.circuit.get(Materials.HV), GTNLItemList.CircuitResonaticHV.get(1));
        GTOreDictUnificator.registerOre(OrePrefixes.circuit.get(Materials.EV), GTNLItemList.CircuitResonaticEV.get(1));
        GTOreDictUnificator.registerOre(OrePrefixes.circuit.get(Materials.IV), GTNLItemList.CircuitResonaticIV.get(1));
        GTOreDictUnificator
            .registerOre(OrePrefixes.circuit.get(Materials.LuV), GTNLItemList.CircuitResonaticLuV.get(1));
        GTOreDictUnificator
            .registerOre(OrePrefixes.circuit.get(Materials.ZPM), GTNLItemList.CircuitResonaticZPM.get(1));
        GTOreDictUnificator.registerOre(OrePrefixes.circuit.get(Materials.UV), GTNLItemList.CircuitResonaticUV.get(1));
        GTOreDictUnificator
            .registerOre(OrePrefixes.circuit.get(Materials.UHV), GTNLItemList.CircuitResonaticUHV.get(1));
        GTOreDictUnificator
            .registerOre(OrePrefixes.circuit.get(Materials.UEV), GTNLItemList.CircuitResonaticUEV.get(1));
        GTOreDictUnificator
            .registerOre(OrePrefixes.circuit.get(Materials.UIV), GTNLItemList.CircuitResonaticUIV.get(1));

        GTOreDictUnificator.registerOre(OrePrefixes.circuit.get(Materials.ULV), GTNLItemList.VerySimpleCircuit.get(1));
        GTOreDictUnificator.registerOre(OrePrefixes.circuit.get(Materials.LV), GTNLItemList.SimpleCircuit.get(1));
        GTOreDictUnificator.registerOre(OrePrefixes.circuit.get(Materials.MV), GTNLItemList.BasicCircuit.get(1));
        GTOreDictUnificator.registerOre(OrePrefixes.circuit.get(Materials.HV), GTNLItemList.AdvancedCircuit.get(1));
        GTOreDictUnificator.registerOre(OrePrefixes.circuit.get(Materials.EV), GTNLItemList.EliteCircuit.get(1));
    }

    public static void registryOreBlackList() {
        GTOreDictUnificator.addToBlacklist(GTNLItemList.CircuitResonaticULV.get(1));
        GTOreDictUnificator.addToBlacklist(GTNLItemList.CircuitResonaticLV.get(1));
        GTOreDictUnificator.addToBlacklist(GTNLItemList.CircuitResonaticMV.get(1));
        GTOreDictUnificator.addToBlacklist(GTNLItemList.CircuitResonaticHV.get(1));
        GTOreDictUnificator.addToBlacklist(GTNLItemList.CircuitResonaticEV.get(1));
        GTOreDictUnificator.addToBlacklist(GTNLItemList.CircuitResonaticIV.get(1));
        GTOreDictUnificator.addToBlacklist(GTNLItemList.CircuitResonaticLuV.get(1));
        GTOreDictUnificator.addToBlacklist(GTNLItemList.CircuitResonaticZPM.get(1));
        GTOreDictUnificator.addToBlacklist(GTNLItemList.CircuitResonaticUV.get(1));
        GTOreDictUnificator.addToBlacklist(GTNLItemList.CircuitResonaticUHV.get(1));
        GTOreDictUnificator.addToBlacklist(GTNLItemList.CircuitResonaticUEV.get(1));
        GTOreDictUnificator.addToBlacklist(GTNLItemList.CircuitResonaticUIV.get(1));
        GTOreDictUnificator.addToBlacklist(GTNLItemList.VerySimpleCircuit.get(1));
        GTOreDictUnificator.addToBlacklist(GTNLItemList.SimpleCircuit.get(1));
        GTOreDictUnificator.addToBlacklist(GTNLItemList.BasicCircuit.get(1));
        GTOreDictUnificator.addToBlacklist(GTNLItemList.AdvancedCircuit.get(1));
        GTOreDictUnificator.addToBlacklist(GTNLItemList.EliteCircuit.get(1));
    }
}
