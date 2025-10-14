package com.science.gtnl.loader;

import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;
import static com.science.gtnl.Utils.text.AnimatedTooltipHandler.*;
import static com.science.gtnl.common.item.items.SuspiciousStew.*;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fluids.FluidRegistry;

import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.Utils.text.AnimatedTooltipHandler;
import com.science.gtnl.Utils.text.TextUtils;
import com.science.gtnl.client.GTNLCreativeTabs;
import com.science.gtnl.common.item.ItemDebug;
import com.science.gtnl.common.item.ItemInfinityCell;
import com.science.gtnl.common.item.ItemInfinityItem;
import com.science.gtnl.common.item.ItemRecord;
import com.science.gtnl.common.item.MetaItemAdder;
import com.science.gtnl.common.item.items.Bauble.LuckyHorseshoe;
import com.science.gtnl.common.item.items.Bauble.PhysicsCape;
import com.science.gtnl.common.item.items.Bauble.RejectionRing;
import com.science.gtnl.common.item.items.Bauble.RoyalGel;
import com.science.gtnl.common.item.items.Bauble.SatietyRing;
import com.science.gtnl.common.item.items.Bauble.SuperReachRing;
import com.science.gtnl.common.item.items.CircuitIntegratedPlus;
import com.science.gtnl.common.item.items.ElectricProspectorTool;
import com.science.gtnl.common.item.items.FakeItemSiren;
import com.science.gtnl.common.item.items.FuelRod.FuelRod;
import com.science.gtnl.common.item.items.FuelRod.FuelRodDepleted;
import com.science.gtnl.common.item.items.GTNLItemBucket;
import com.science.gtnl.common.item.items.KFCFamily;
import com.science.gtnl.common.item.items.NetherTeleporter;
import com.science.gtnl.common.item.items.NullPointerException;
import com.science.gtnl.common.item.items.PortableItem;
import com.science.gtnl.common.item.items.SlimeSaddle;
import com.science.gtnl.common.item.items.SteamRocketItem;
import com.science.gtnl.common.item.items.Stick;
import com.science.gtnl.common.item.items.SuspiciousStew;
import com.science.gtnl.common.item.items.TestItem;
import com.science.gtnl.common.item.items.TimeStopPocketWatch;
import com.science.gtnl.common.item.items.TwilightSword;
import com.science.gtnl.common.item.items.VeinMiningPickaxe;
import com.science.gtnl.common.item.items.WirelessUpgradeChip;

import appeng.api.storage.StorageChannel;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.common.render.items.InfinityMetaItemRenderer;

public class ItemLoader {

    public static Item steamRocket;
    public static Item nullPointerException;
    public static Item fakeItemSiren;
    public static Item netherTeleporter;
    public static Item testItem;
    public static Item KFCFamily;
    public static Item twilightSword;
    public static Item circuitIntegratedPlus;
    public static Item timeStopPocketWatch;
    public static Item infinityTorch;
    public static Item infinityWaterBucket;
    public static Item infinityLavaBucket;
    public static Item infinityHoneyBucket;
    public static Item infinityShimmerBucket;
    public static Item superstrongSponge;
    public static Item slimeSaddle;
    public static Item infinityFuelRodDepleted;
    public static Item infinityFuelRod;
    public static Item metaItem;
    public static ItemBucket honeyBucket;
    public static ItemBucket shimmerBucket;
    public static ItemInfinityCell infinityCell;
    public static Item wirelessUpgradeChip;
    public static Item stick;
    public static Item suspiciousStew;
    public static Item veinMiningPickaxe;
    public static Item portableItem;

    public static Item electricProspectorTool;

    public static Item satietyRing;
    public static Item rejectionRing;
    public static Item superReachRing;
    public static Item physicsCape;
    public static Item royalGel;
    public static Item luckyHorseshoe;

    public static ItemStack infinityDyeCell;
    public static ItemStack infinityDyeFluidCell;
    public static ItemStack infinityCobblestoneCell;

    public static void registryItems() {
        portableItem = new PortableItem();
        veinMiningPickaxe = new VeinMiningPickaxe();
        suspiciousStew = new SuspiciousStew();
        steamRocket = new SteamRocketItem();
        nullPointerException = new NullPointerException();
        fakeItemSiren = new FakeItemSiren();
        wirelessUpgradeChip = new WirelessUpgradeChip();
        testItem = new TestItem();
        netherTeleporter = new NetherTeleporter();
        electricProspectorTool = new ElectricProspectorTool();
        KFCFamily = new KFCFamily(20, 20, true);
        twilightSword = new TwilightSword();
        circuitIntegratedPlus = new CircuitIntegratedPlus();
        timeStopPocketWatch = new TimeStopPocketWatch();
        infinityTorch = new ItemInfinityItem("InfinityTorch", Blocks.torch, GTNLItemList.InfinityTorch);
        infinityWaterBucket = new ItemInfinityItem(
            "InfinityWaterBucket",
            Blocks.water,
            GTNLItemList.InfinityWaterBucket);
        infinityLavaBucket = new ItemInfinityItem("InfinityLavaBucket", Blocks.lava, GTNLItemList.InfinityLavaBucket);
        infinityHoneyBucket = new ItemInfinityItem(
            "InfinityHoneyBucket",
            BlockLoader.honeyFluidBlock,
            GTNLItemList.InfinityHoneyBucket);
        infinityShimmerBucket = new ItemInfinityItem(
            "InfinityShimmerBucket",
            BlockLoader.shimmerFluidBlock,
            GTNLItemList.InfinityShimmerBucket);
        superstrongSponge = new ItemInfinityItem("SuperstrongSponge", null, GTNLItemList.SuperstrongSponge, false);
        RecordLoader.recordSus = new ItemRecord("sus").setCreativeTab(GTNLCreativeTabs.GTNotLeisureItem);
        RecordLoader.recordNewHorizons = new ItemRecord("new_horizons")
            .setCreativeTab(GTNLCreativeTabs.GTNotLeisureItem);
        infinityFuelRodDepleted = new FuelRodDepleted("InfinityFuelRodDepleted", 2000);
        infinityFuelRod = new FuelRod(
            "InfinityFuelRod",
            1,
            491520,
            500,
            15000,
            160000,
            70F,
            new ItemStack(infinityFuelRodDepleted, 1));
        metaItem = new MetaItemAdder("MetaItemBase", "MetaItem", GTNLCreativeTabs.GTNotLeisureItem)
            .setTextureName(RESOURCE_ROOT_ID + ":" + "MetaItem/0");
        honeyBucket = GTNLItemBucket.create(BlockLoader.honeyFluid);
        shimmerBucket = GTNLItemBucket.create(BlockLoader.shimmerFluid);
        superReachRing = new SuperReachRing();
        satietyRing = new SatietyRing();
        rejectionRing = new RejectionRing();
        slimeSaddle = new SlimeSaddle();
        physicsCape = new PhysicsCape();
        royalGel = new RoyalGel();
        luckyHorseshoe = new LuckyHorseshoe();
        infinityCell = new ItemInfinityCell();
        stick = new Stick();

        var subDyeItems = new ItemInfinityCell.SubItem[16];
        for (short i = 0; i < 16; i++) {
            subDyeItems[i] = ItemInfinityCell.SubItem.getInstance(ItemList.DYE_ONLY_ITEMS[i].get(1));
        }
        infinityDyeCell = ItemInfinityCell
            .getSubItem(StorageChannel.ITEMS, "InfinityCell.dye.name", "InfinityDyeCell", subDyeItems);

        String[] colors = { "Black", "Pink", "Red", "Orange", "Yellow", "Green", "Lime", "Blue", "LightBlue", "Cyan",
            "Brown", "Magenta", "Purple", "Gray", "LightGray", "White" };
        var subDyeFluid = new ItemInfinityCell.SubItem[colors.length];
        for (int i = 0; i < colors.length; i++) {
            String color = colors[i];
            String fluidName = "dye.chemical.dye" + color.toLowerCase();
            subDyeFluid[i] = ItemInfinityCell.SubItem.getInstance(FluidRegistry.getFluid(fluidName));
        }
        infinityDyeFluidCell = ItemInfinityCell
            .getSubItem(StorageChannel.FLUIDS, "InfinityCell.dye.fluid.name", "InfinityDyeFluidCell", subDyeFluid);

        infinityCobblestoneCell = ItemInfinityCell.getSubItem(
            StorageChannel.ITEMS,
            "InfinityCobblestoneCell",
            ItemInfinityCell.SubItem.getInstance(Blocks.cobblestone));

        GameRegistry.registerItem(wirelessUpgradeChip, "WirelessUpgradeChip");
        GameRegistry.registerItem(stick, "Stick");
        GameRegistry.registerItem(portableItem, "PortableItem");
        GameRegistry.registerItem(veinMiningPickaxe, "VeinMiningPickaxe");
        GameRegistry.registerItem(suspiciousStew, "SuspiciousStew");
        GameRegistry.registerItem(steamRocket, "SteamRocket");
        GameRegistry.registerItem(nullPointerException, "NullPointerException");
        GameRegistry.registerItem(netherTeleporter, "NetherTeleporter");
        GameRegistry.registerItem(fakeItemSiren, "FakeItemSiren");
        GameRegistry.registerItem(testItem, "TestItem");
        GameRegistry.registerItem(electricProspectorTool, "ElectricProspectorTool");
        GameRegistry.registerItem(KFCFamily, "KFCFamily");
        GameRegistry.registerItem(twilightSword, "TwilightSword");
        GameRegistry.registerItem(circuitIntegratedPlus, "CircuitIntegratedPlus");
        GameRegistry.registerItem(timeStopPocketWatch, "TimeStopPocketWatch");
        GameRegistry.registerItem(infinityTorch, "InfinityTorch");
        GameRegistry.registerItem(infinityWaterBucket, "InfinityWaterBucket");
        GameRegistry.registerItem(infinityLavaBucket, "InfinityLavaBucket");
        GameRegistry.registerItem(infinityHoneyBucket, "InfinityHoneyBucket");
        GameRegistry.registerItem(infinityShimmerBucket, "InfinityShimmerBucket");
        GameRegistry.registerItem(superstrongSponge, "SuperstrongSponge");
        GameRegistry.registerItem(slimeSaddle, "SlimeSaddle");
        GameRegistry.registerItem(RecordLoader.recordSus, "record_sus");
        GameRegistry.registerItem(RecordLoader.recordNewHorizons, "record_new_horizons");
        GameRegistry.registerItem(infinityFuelRodDepleted, "InfinityFuelRodDepleted");
        GameRegistry.registerItem(infinityFuelRod, "InfinityFuelRod");
        GameRegistry.registerItem(metaItem, "MetaItem");

        GameRegistry.registerItem(satietyRing, "SatietyRing");
        GameRegistry.registerItem(rejectionRing, "RejectionRing");
        GameRegistry.registerItem(superReachRing, "SuperReachRing");
        GameRegistry.registerItem(royalGel, "RoyalGel");
        GameRegistry.registerItem(physicsCape, "PhysicsCape");
        GameRegistry.registerItem(luckyHorseshoe, "LuckyHorseshoe");
        GameRegistry.registerItem(infinityCell, "InfinityCell");
        GameRegistry.registerItem(ItemDebug.INSTANCE, "debug");

        GTNLItemList.RecordSus.set(new ItemStack(RecordLoader.recordSus, 1));
        GTNLItemList.RecordNewHorizons.set(new ItemStack(RecordLoader.recordNewHorizons, 1));
        GTNLItemList.RecordLavaChicken.set(new ItemStack(RecordLoader.recordLavaChicken, 1));
        GTNLItemList.InfinityFuelRodDepleted.set(new ItemStack(infinityFuelRodDepleted, 1));
        GTNLItemList.InfinityFuelRod.set(new ItemStack(infinityFuelRod, 1));
        GTNLItemList.HoneyBucket.set(new ItemStack(honeyBucket, 1));
        GTNLItemList.ShimmerBucket.set(new ItemStack(shimmerBucket, 1));
        GTNLItemList.InfinityCell.set(new ItemStack(infinityCell, 1));
        GTNLItemList.InfinityDyeCell.set(infinityDyeCell);
        GTNLItemList.InfinityDyeFluidCell.set(infinityDyeFluidCell);
        GTNLItemList.InfinityCobblestoneCell.set(infinityCobblestoneCell);
    }

    public static void registryMetaItems() {
        GTNLItemList.TrollFace.set(
            MetaItemAdder.initItem(
                "TrollFace",
                0,
                new String[] { TextUtils.texter("Never Gonna Give You Up~", "Tooltip_TrollFace") }));
        GTNLItemList.DepletedExcitedNaquadahFuelRod.set(
            MetaItemAdder.initItem(
                "§bDepleted Excited Naquadah Fuel Rod",
                1,
                new String[] {
                    TextUtils.texter("§oUltimate Form of Naquadah Fuel.", "Tooltip_DepletedExcitedNaquadahFuelRod") }));
        GTNLItemList.BlazeCube.set(
            MetaItemAdder.initItem(
                "§eBlaze Cube",
                2,
                new String[] { TextUtils.texter("§8§oBlaze Storm.", "Tooltip_BlazeCube") }));
        GTNLItemList.EnhancementCore.set(
            MetaItemAdder.initItem(
                "Enhancement Core",
                3,
                new String[] { TextUtils.texter("§8§oThe road to completion!", "Tooltip_EnhancementCore") }));
        GTNLItemList.StellarConstructionFrameMaterial.set(
            MetaItemAdder.initItem(
                "Stellar Construction Frame Material",
                4,
                new String[] { TextUtils.texter("A test item, no use.", "Tooltip_TestItem0.line1") }));
        GTNLItemList.ActivatedGaiaPylon.set(MetaItemAdder.initItem("Activated Gaia Pylon", 5));
        GTNLItemList.PrecisionSteamMechanism.set(MetaItemAdder.initItem("Precision Steam Mechanism", 6));
        GTNLItemList.MeteorMinerSchematic1.set(
            MetaItemAdder.initItem(
                "Meteor Miner Tier 1 Schematic",
                7,
                new String[] { TextUtils
                    .texter("Schematic needed to enable the Tier 1 Meteor Miner", "Tooltip_MeteorMinerSchematic1") }));
        GTNLItemList.MeteorMinerSchematic2.set(
            MetaItemAdder.initItem(
                "Meteor Miner Tier 2 Schematic",
                8,
                new String[] { TextUtils
                    .texter("Schematic needed to enable the Tier 2 Meteor Miner", "Tooltip_MeteorMinerSchematic2") }));
        GTNLItemList.CircuitResonaticULV.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic ULV",
                9,
                new String[] { TextUtils.texter("An Original Circuit", "Tooltip_CircuitResonaticULV_00"),
                    TextUtils.texter("§fULV-Tier", "Tooltip_CircuitResonaticULV_01") }));
        GTNLItemList.CircuitResonaticLV.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic LV",
                10,
                new String[] { TextUtils.texter("An Basic Circuit", "Tooltip_CircuitResonaticLV_00"),
                    TextUtils.texter("LV-Tier", "Tooltip_CircuitResonaticLV_01") }));
        GTNLItemList.CircuitResonaticMV.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic MV",
                11,
                new String[] { TextUtils.texter("An Good Circuit", "Tooltip_CircuitResonaticMV_00"),
                    TextUtils.texter("§6MV-Tier", "Tooltip_CircuitResonaticMV_01") }));
        GTNLItemList.CircuitResonaticHV.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic HV",
                12,
                new String[] { TextUtils.texter("An Adavanced Circuit", "Tooltip_CircuitResonaticHV_00"),
                    TextUtils.texter("§eHV-Tier", "Tooltip_CircuitResonaticHV_01") }));
        GTNLItemList.CircuitResonaticEV.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic EV",
                13,
                new String[] { TextUtils.texter("An Data Circuit", "Tooltip_CircuitResonaticEV_00"),
                    TextUtils.texter("§8EV-Tier", "Tooltip_CircuitResonaticEV_01") }));
        GTNLItemList.CircuitResonaticIV.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic IV",
                14,
                new String[] { TextUtils.texter("An Elite Circuit", "Tooltip_CircuitResonaticIV_00"),
                    TextUtils.texter("§aIV-Tier", "Tooltip_CircuitResonaticIV_01") }));
        GTNLItemList.CircuitResonaticLuV.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic LuV",
                15,
                new String[] { TextUtils.texter("An Master Circuit", "Tooltip_CircuitResonaticLuV_00"),
                    TextUtils.texter("§dLuV-Tier", "Tooltip_CircuitResonaticLuV_01") }));
        GTNLItemList.CircuitResonaticZPM.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic ZPM",
                16,
                new String[] { TextUtils.texter("An Ultimate Circuit", "Tooltip_CircuitResonaticZPM_00"),
                    TextUtils.texter("§bZPM-Tier", "Tooltip_CircuitResonaticZPM_01") }));
        GTNLItemList.CircuitResonaticUV.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic UV",
                17,
                new String[] { TextUtils.texter("An Superconductor Circuit", "Tooltip_CircuitResonaticUV_00"),
                    TextUtils.texter("§2UV-Tier", "Tooltip_CircuitResonaticUV_01") }));
        GTNLItemList.CircuitResonaticUHV.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic UHV",
                18,
                new String[] { TextUtils.texter("An Infinite Circuit", "Tooltip_CircuitResonaticUHV_00"),
                    TextUtils.texter("§4UHV-Tier", "Tooltip_CircuitResonaticUHV_01") }));
        GTNLItemList.CircuitResonaticUEV.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic UEV",
                19,
                new String[] { TextUtils.texter("An Bio Circuit", "Tooltip_CircuitResonaticUEV_00"),
                    TextUtils.texter("§5UEV-Tier", "Tooltip_CircuitResonaticUEV_01") }));
        GTNLItemList.CircuitResonaticUIV.set(
            MetaItemAdder.initItem(
                "Circuit Resonatic UIV",
                20,
                new String[] { TextUtils.texter("An Optical Circuit", "Tooltip_CircuitResonaticUIV_00"),
                    TextUtils.texter("§l§1UIV-Tier", "Tooltip_CircuitResonaticUIV_01") }));
        GTNLItemList.VerySimpleCircuit.set(
            MetaItemAdder.initItem(
                "Very Simple Circuit",
                21,
                new String[] { TextUtils.texter("An Very Simple Circuit", "Tooltip_VerySimpleCircuit_00"),
                    TextUtils.texter("§fULV-Tier", "Tooltip_VerySimpleCircuit_01") }));
        GTNLItemList.SimpleCircuit.set(
            MetaItemAdder.initItem(
                "Simple Circuit",
                22,
                new String[] { TextUtils.texter("An Simple Circuit", "Tooltip_SimpleCircuit_00"),
                    TextUtils.texter("LV-Tier", "Tooltip_SimpleCircuit_01") }));
        GTNLItemList.BasicCircuit.set(
            MetaItemAdder.initItem(
                "Basic Circuit",
                23,
                new String[] { TextUtils.texter("An Basic Circuit", "Tooltip_BasicCircuit_00"),
                    TextUtils.texter("§6MV-Tier", "Tooltip_BasicCircuit_01") }));
        GTNLItemList.AdvancedCircuit.set(
            MetaItemAdder.initItem(
                "Advanced Circuit",
                24,
                new String[] { TextUtils.texter("An Advanced Circuit", "Tooltip_AdvancedCircuit_00"),
                    TextUtils.texter("§eHV-Tier", "Tooltip_AdvancedCircuit_01") }));
        GTNLItemList.EliteCircuit.set(
            MetaItemAdder.initItem(
                "Elite Circuit",
                25,
                new String[] { TextUtils.texter("An Elite Circuit", "Tooltip_EliteCircuit_00"),
                    TextUtils.texter("§8EV-Tier", "Tooltip_EliteCircuit_01") }));
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
                new String[] { TextUtils.texter("§dSpeed: +1%%", "Tooltip_LVParallelControllerCore_00"),
                    TextUtils.texter("§5EU Usage: -2%%", "Tooltip_LVParallelControllerCore_01"),
                    TextUtils.texter("§bParallel: 0", "Tooltip_LVParallelControllerCore_02") }));

        GTNLItemList.MVParallelControllerCore.set(
            MetaItemAdder.initItem(
                "MV Parallel Controller Core",
                49,
                new String[] { TextUtils.texter("§dSpeed: +2%%", "Tooltip_MVParallelControllerCore_00"),
                    TextUtils.texter("§5EU Usage: -4%%", "Tooltip_MVParallelControllerCore_01"),
                    TextUtils.texter("§bParallel: 1", "Tooltip_MVParallelControllerCore_02") }));

        GTNLItemList.HVParallelControllerCore.set(
            MetaItemAdder.initItem(
                "HV Parallel Controller Core",
                50,
                new String[] { TextUtils.texter("§dSpeed: +4%%", "Tooltip_HVParallelControllerCore_00"),
                    TextUtils.texter("§5EU Usage: -6%%", "Tooltip_HVParallelControllerCore_01"),
                    TextUtils.texter("§bParallel: 4", "Tooltip_HVParallelControllerCore_02") }));

        GTNLItemList.EVParallelControllerCore.set(
            MetaItemAdder.initItem(
                "EV Parallel Controller Core",
                51,
                new String[] { TextUtils.texter("§dSpeed: +6%%", "Tooltip_EVParallelControllerCore_00"),
                    TextUtils.texter("§5EU Usage: -8%%", "Tooltip_EVParallelControllerCore_01"),
                    TextUtils.texter("§bParallel: 16", "Tooltip_EVParallelControllerCore_02") }));

        GTNLItemList.IVParallelControllerCore.set(
            MetaItemAdder.initItem(
                "IV Parallel Controller Core",
                52,
                new String[] { TextUtils.texter("§dSpeed: +8%%", "Tooltip_IVParallelControllerCore_00"),
                    TextUtils.texter("§5EU Usage: -10%%", "Tooltip_IVParallelControllerCore_01"),
                    TextUtils.texter("§bParallel: 64", "Tooltip_IVParallelControllerCore_02") }));

        GTNLItemList.LuVParallelControllerCore.set(
            MetaItemAdder.initItem(
                "LuV Parallel Controller Core",
                53,
                new String[] { TextUtils.texter("§dSpeed: +10%%", "Tooltip_LuVParallelControllerCore_00"),
                    TextUtils.texter("§5EU Usage: -12%%", "Tooltip_LuVParallelControllerCore_01"),
                    TextUtils.texter("§bParallel: 256", "Tooltip_LuVParallelControllerCore_02") }));

        GTNLItemList.ZPMParallelControllerCore.set(
            MetaItemAdder.initItem(
                "ZPM Parallel Controller Core",
                54,
                new String[] { TextUtils.texter("§dSpeed: +12%%", "Tooltip_ZPMParallelControllerCore_00"),
                    TextUtils.texter("§5EU Usage: -14%%", "Tooltip_ZPMParallelControllerCore_01"),
                    TextUtils.texter("§bParallel: 1024", "Tooltip_ZPMParallelControllerCore_02") }));

        GTNLItemList.UVParallelControllerCore.set(
            MetaItemAdder.initItem(
                "UV Parallel Controller Core",
                55,
                new String[] { TextUtils.texter("§dSpeed: +14%%", "Tooltip_UVParallelControllerCore_00"),
                    TextUtils.texter("§5EU Usage: -16%%", "Tooltip_UVParallelControllerCore_01"),
                    TextUtils.texter("§bParallel: 4096", "Tooltip_UVParallelControllerCore_02") }));

        GTNLItemList.UHVParallelControllerCore.set(
            MetaItemAdder.initItem(
                "UHV Parallel Controller Core",
                56,
                new String[] { TextUtils.texter("§dSpeed: +16%%", "Tooltip_UHVParallelControllerCore_00"),
                    TextUtils.texter("§5EU Usage: -18%%", "Tooltip_UHVParallelControllerCore_01"),
                    TextUtils.texter("§bParallel: 16384", "Tooltip_UHVParallelControllerCore_02") }));

        GTNLItemList.UEVParallelControllerCore.set(
            MetaItemAdder.initItem(
                "UEV Parallel Controller Core",
                57,
                new String[] { TextUtils.texter("§dSpeed: +18%%", "Tooltip_UEVParallelControllerCore_00"),
                    TextUtils.texter("§5EU Usage: -20%%", "Tooltip_UEVParallelControllerCore_01"),
                    TextUtils.texter("§bParallel: 65536", "Tooltip_UEVParallelControllerCore_02") }));

        GTNLItemList.UIVParallelControllerCore.set(
            MetaItemAdder.initItem(
                "UIV Parallel Controller Core",
                58,
                new String[] { TextUtils.texter("§dSpeed: +20%%", "Tooltip_UIVParallelControllerCore_00"),
                    TextUtils.texter("§5EU Usage: -22%%", "Tooltip_UIVParallelControllerCore_01"),
                    TextUtils.texter("§bParallel: 262144", "Tooltip_UIVParallelControllerCore_02") }));

        GTNLItemList.UMVParallelControllerCore.set(
            MetaItemAdder.initItem(
                "UMV Parallel Controller Core",
                59,
                new String[] { TextUtils.texter("§dSpeed: +22%%", "Tooltip_UMVParallelControllerCore_00"),
                    TextUtils.texter("§5EU Usage: -24%%", "Tooltip_UMVParallelControllerCore_01"),
                    TextUtils.texter("§bParallel: 1048576", "Tooltip_UMVParallelControllerCore_02") }));

        GTNLItemList.UXVParallelControllerCore.set(
            MetaItemAdder.initItem(
                "UXV Parallel Controller Core",
                60,
                new String[] { TextUtils.texter("§dSpeed: +24%%", "Tooltip_UXVParallelControllerCore_00"),
                    TextUtils.texter("§5EU Usage: -26%%", "Tooltip_UXVParallelControllerCore_01"),
                    TextUtils.texter("§bParallel: 4194304", "Tooltip_UXVParallelControllerCore_02") }));

        GTNLItemList.MAXParallelControllerCore.set(
            MetaItemAdder.initItem(
                "MAX Parallel Controller Core",
                61,
                new String[] { TextUtils.texter("§dSpeed: +26%%", "Tooltip_MAXParallelControllerCore_00"),
                    TextUtils.texter("§5EU Usage: -28%%", "Tooltip_MAXParallelControllerCore_01"),
                    TextUtils.texter("§bParallel: 16777216", "Tooltip_MAXParallelControllerCore_02") }));

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
                new String[] { TextUtils.texter("Raw Circuit", "Tooltip_NeutroniumBoule_00") }));
        GTNLItemList.NeutroniumWafer.set(
            MetaItemAdder.initItem(
                "Neutronium Wafer",
                76,
                new String[] { TextUtils.texter("Raw Circuit", "Tooltip_NeutroniumWafer_00") }));
        GTNLItemList.HighlyAdvancedSocWafer.set(
            MetaItemAdder.initItem(
                "Highly Advanced Soc Wafer",
                77,
                new String[] { TextUtils.texter("Raw Circuit", "Tooltip_HighlyAdvancedSocWafer_00") }));
        GTNLItemList.HighlyAdvancedSoc.set(
            MetaItemAdder.initItem(
                "Highly Advanced Soc",
                78,
                new String[] { TextUtils.texter("Highly Advanced SoC", "Tooltip_HighlyAdvancedSoc_00") }));
        GTNLItemList.ZnFeAlClCatalyst.set(MetaItemAdder.initItem("Zn-Fe-Al-Cl Catalyst", 79));
        GTNLItemList.BlackLight.set(
            MetaItemAdder.initItem(
                "Black Light",
                80,
                new String[] {
                    TextUtils.texter("§8long-wave §dultraviolet §8light source", "Tooltip_BlackLight_00") }));
        GTNLItemList.SteamgateDialingDevice.set(
            MetaItemAdder.initItem(
                "Steamgate Dialing Device",
                81,
                new String[] {
                    TextUtils.texter("Links steamgates for teleportation.", "Tooltip_SteamgateDialingDevice_00") }));
        GTNLItemList.SteamgateChevron.set(MetaItemAdder.initItem("Steamgate Chevron", 82));
        GTNLItemList.SteamgateChevronUpgrade.set(MetaItemAdder.initItem("Steamgate Chevron Upgrade", 83));
        GTNLItemList.SteamgateIrisBlade.set(MetaItemAdder.initItem("Steamgate Iris Blade", 84));
        GTNLItemList.SteamgateIrisUpgrade.set(MetaItemAdder.initItem("Steamgate Iris Upgrade", 85));
        GTNLItemList.SteamgateHeatContainmentPlate.set(
            MetaItemAdder.initItem(
                "Steamgate Heat Containment Plate",
                86,
                new String[] { TextUtils
                    .texter("Perfect temperature stability...", "Tooltip_SteamgateHeatContainmentPlate_00") }));
        GTNLItemList.SteamgateFrame.set(
            MetaItemAdder.initItem(
                "Steamgate Frame",
                87,
                new String[] { TextUtils.texter("Innumerable interlocking gears...", "Tooltip_SteamgateFrame_00") }));
        GTNLItemList.SteamgateCoreCrystal.set(
            MetaItemAdder.initItem(
                "Steamgate Core Crystal",
                88,
                new String[] { TextUtils
                    .texter("A crystal made of pure condensed staem...", "Tooltip_SteamgateCoreCrystal_00") }));
        GTNLItemList.HydraulicMotor.set(MetaItemAdder.initItem("Hydraulic Motor", 89));
        GTNLItemList.HydraulicPiston.set(MetaItemAdder.initItem("Hydraulic Piston", 90));
        GTNLItemList.HydraulicPump.set(
            MetaItemAdder.initItem(
                "Hydraulic Pump",
                91,
                new String[] {
                    TextUtils.texter("1,048,576 L/t (20,971,520 L/s) as Cover", "Tooltip_HydraulicPump_00") }));
        GTNLItemList.HydraulicArm.set(MetaItemAdder.initItem("Hydraulic Arm", 92));
        GTNLItemList.HydraulicConveyor.set(
            MetaItemAdder.initItem(
                "Hydraulic Conveyor",
                93,
                new String[] {
                    TextUtils.texter("16 stacks every 1/20 sec (as Cover)", "Tooltip_HydraulicConveyor_00") }));
        GTNLItemList.HydraulicRegulator.set(
            MetaItemAdder.initItem(
                "Hydraulic Regulator",
                94,
                new String[] {
                    TextUtils.texter("Configurable up to 20,971,520 L/sec (as Cover)", "Tooltip_HydraulicRegulator_00"),
                    TextUtils.texter(
                        "Rightclick/Screwdriver-rightclick/Shift-screwdriver-rightclick",
                        "Tooltip_HydraulicRegulator_01"),
                    TextUtils.texter(
                        "to adjust the pump speed by 1/16/256 L/sec per click",
                        "Tooltip_HydraulicRegulator_02") }));
        GTNLItemList.HydraulicVaporGenerator.set(MetaItemAdder.initItem("Hydraulic Vapor Generator", 95));
        GTNLItemList.HydraulicSteamJetSpewer.set(MetaItemAdder.initItem("Hydraulic Steam Jet Spewer", 96));
        GTNLItemList.HydraulicSteamReceiver.set(MetaItemAdder.initItem("Hydraulic Steam Receiver", 97));
        GTNLItemList.HydraulicSteamValve.set(
            MetaItemAdder.initItem(
                "Hydraulic Steam Valve",
                98,
                new String[] {
                    TextUtils.texter("16,777,216 L/t (335,544,320 L/s) as Cover", "Tooltip_HydraulicSteamValve_00") }));
        addItemTooltip(
            GTNLItemList.HydraulicSteamValve.get(1),
            AnimatedTooltipHandler.buildTextWithAnimatedEnd(text("Tips: 瑶光Alkaid要的")));
        GTNLItemList.HydraulicSteamRegulator.set(
            MetaItemAdder.initItem(
                "Hydraulic Steam Regulator",
                99,
                new String[] { TextUtils
                    .texter("16,777,216 L/t (335,544,320 L/s) as Cover", "Tooltip_HydraulicSteamRegulator_00") }));
        addItemTooltip(
            GTNLItemList.HydraulicSteamRegulator.get(1),
            AnimatedTooltipHandler.buildTextWithAnimatedEnd(text("Tips: 瑶光Alkaid要的")));
        GTNLItemList.SadBapyCatToken.set(
            MetaItemAdder.initItem(
                "SadBapyCat Token",
                100,
                new String[] { TextUtils.texter(":sadbapycat:", "Tooltip_SadBapyCatToken_00") }));
        GTNLItemList.CompressedSteamTurbine.set(
            MetaItemAdder.initItem(
                "Compressed Steam Turbine",
                101,
                new String[] { TextUtils.texter("Infinite Throughtput", "Tooltip_CompressedSteamTurbine_00") }));
        GTNLItemList.SteelTurbine.set(
            MetaItemAdder.initItem(
                "Steel Turbine",
                102,
                new String[] { TextUtils.texter("Stable like a Table", "Tooltip_SteelTurbine_00") }));
        GTNLItemList.PipelessSteamCover.set(
            MetaItemAdder.initItem(
                "Pipeless Steam Cover",
                103,
                new String[] {
                    TextUtils.texter(
                        "§b§oYou recall a dream of an endless, unseen sea of steam...",
                        "Tooltip_WirelessSteamCover_00"),
                    TextUtils.texter(
                        "§b§oNo pipes are needed; steam seems to flow through the very air itself.",
                        "Tooltip_WirelessSteamCover_01"),
                    TextUtils.texter(
                        "§b§oYou don’t know how it works—only that it delivers the full might of water’s power to you.",
                        "Tooltip_WirelessSteamCover_02"),
                    TextUtils.texter(
                        "Stores steam globally in a network, up to 2^(2^31) L.",
                        "Tooltip_WirelessSteamCover_03"),
                    TextUtils.texter(
                        "Does not connect to pipes. This cover withdraws Steam from the network.",
                        "Tooltip_WirelessSteamCover_04") }));
        GTNLItemList.IronTurbine.set(
            MetaItemAdder.initItem(
                "Iron Turbine",
                104,
                new String[] { TextUtils.texter("Sturdy and Durable", "Tooltip_IronTurbine_00") }));
        GTNLItemList.BronzeTurbine.set(
            MetaItemAdder.initItem(
                "Bronze Turbine",
                105,
                new String[] { TextUtils.texter("Antique and Solid", "Tooltip_BronzeTurbine_00") }));
        GTNLItemList.VoidCover.set(
            MetaItemAdder.initItem(
                "Void Cover",
                106,
                new String[] { TextUtils.texter("Just like the Void Assembly!", "Tooltip_VoidCover_00"),
                    TextUtils.texter("Immediately erases all matching contents", "Tooltip_VoidCover_01"),
                    TextUtils.texter("Must be configured to work", "Tooltip_VoidCover_02"),
                    TextUtils.texter(
                        "Can toggle between destroying input/output slots via button",
                        "Tooltip_VoidCover_03") }));

        GTNLItemList.ManaElectricProspectorTool
            .set(ElectricProspectorTool.initItem("Mana Electric Prospector Tool", 0, 17, 9999));
    }

    public static void registry() {
        registryItems();
        registryMetaItems();
        registryOreDictionary();
        registryOreBlackList();
        registrySuspiciousStewFlower();

        addItemTooltip(
            new ItemStack(ItemLoader.satietyRing, 1),
            AnimatedTooltipHandler.buildTextWithAnimatedEnd(text("Most machine recipe by zero_CM")));
    }

    public static void registrySuspiciousStewFlower() {
        // 蒲公英
        registerFlower(Blocks.yellow_flower, 0, new PotionEffect(Potion.field_76443_y.id, 7));
        // 罂粟
        registerFlower(Blocks.red_flower, 0, new PotionEffect(Potion.nightVision.id, 100));
        // 兰花
        registerFlower(Blocks.red_flower, 1, new PotionEffect(Potion.field_76443_y.id, 7));
        // 绒球葱
        registerFlower(Blocks.red_flower, 2, new PotionEffect(Potion.fireResistance.id, 60));
        // 蓝花美耳草
        registerFlower(Blocks.red_flower, 3, new PotionEffect(Potion.blindness.id, 220));
        // 郁金香
        registerFlower(Blocks.red_flower, 4, new PotionEffect(Potion.weakness.id, 140));
        registerFlower(Blocks.red_flower, 5, new PotionEffect(Potion.weakness.id, 140));
        registerFlower(Blocks.red_flower, 6, new PotionEffect(Potion.weakness.id, 140));
        registerFlower(Blocks.red_flower, 7, new PotionEffect(Potion.weakness.id, 140));
        // 滨菊
        registerFlower(Blocks.red_flower, 8, new PotionEffect(Potion.regeneration.id, 140));
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

        GTOreDictUnificator.registerOre("record", GTNLItemList.RecordSus.get(1));
        GTOreDictUnificator.registerOre("record", GTNLItemList.RecordLavaChicken.get(1));
        GTOreDictUnificator.registerOre("record", GTNLItemList.RecordNewHorizons.get(1));
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
