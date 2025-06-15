package com.science.gtnl.loader;

import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.*;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.text;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.Utils.text.AnimatedText;
import com.science.gtnl.client.GTNLCreativeTabs;
import com.science.gtnl.common.block.Casings.Base.ItemBlockBase;
import com.science.gtnl.common.block.Casings.Base.MetaBlockBase;
import com.science.gtnl.common.block.Casings.Casing.MetaBlockConstructors;
import com.science.gtnl.common.block.Casings.Casing.MetaCasing;
import com.science.gtnl.common.block.Casings.Casing.MetaItemBlockCasing;
import com.science.gtnl.common.block.Casings.Column.ItemBlockColumn;
import com.science.gtnl.common.block.Casings.Column.MetaBlockColumn;
import com.science.gtnl.common.block.Casings.Glass.ItemBlockGlass;
import com.science.gtnl.common.block.Casings.Glass.MetaBlockGlass;
import com.science.gtnl.common.block.Casings.Glow.ItemBlockGlow;
import com.science.gtnl.common.block.Casings.Glow.MetaBlockGlow;
import com.science.gtnl.common.block.Casings.Special.BlocksStargate;
import com.science.gtnl.common.block.Casings.Special.StargateMetaBlockBase;
import com.science.gtnl.common.block.blocks.BlockArtificialStarRender;
import com.science.gtnl.common.block.blocks.BlockEternalGregTechWorkshopRender;
import com.science.gtnl.common.block.blocks.BlockLaserBeacon;
import com.science.gtnl.common.block.blocks.BlockNanoPhagocytosisPlantRender;
import com.science.gtnl.common.block.blocks.BlockPlayerDoll;
import com.science.gtnl.common.block.blocks.tile.TileEntityArtificialStar;
import com.science.gtnl.common.block.blocks.tile.TileEntityEternalGregTechWorkshop;
import com.science.gtnl.common.block.blocks.tile.TileEntityLaserBeacon;
import com.science.gtnl.common.block.blocks.tile.TileEntityNanoPhagocytosisPlant;
import com.science.gtnl.common.block.blocks.tile.TileEntityPlayerDoll;
import com.science.gtnl.common.item.items.SaplingBrickuoia;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.util.GTRecipeBuilder;
import gtPlusPlus.core.item.base.itemblock.ItemBlockMeta;

public class BlockLoader {

    public static Block BlockArtificialStarRender;
    public static Block LaserBeacon;
    public static Block PlayerDoll;
    public static Block BlockNanoPhagocytosisPlantRender;
    public static Block BlockEternalGregTechWorkshopRender;
    public static final Block MetaBlock = new MetaBlockBase("MetaBlock", "MetaBlock");
    public static final Block MetaBlockGlow = new MetaBlockGlow("MetaBlockGlow", "MetaBlockGlow");
    public static final Block MetaBlockGlass = new MetaBlockGlass("MetaBlockGlass", "MetaBlockGlass");
    public static final Block MetaBlockColumn = new MetaBlockColumn("MetaBlockColumn", "MetaBlockColumn");
    public static final com.science.gtnl.common.block.Casings.Casing.MetaCasing MetaCasing = new MetaCasing(
        "MetaCasing",
        (byte) 0);
    public static final MetaCasing MetaCasing02 = new MetaCasing("MetaCasing02", (byte) 32);
    public static Block StargateTier0 = new BlocksStargate(0);
    public static Block StargateTier1 = new BlocksStargate(1);
    public static Block StargateTier2 = new BlocksStargate(2);
    public static Block StargateTier3 = new BlocksStargate(3);
    public static Block StargateTier4 = new BlocksStargate(4);
    public static Block StargateTier5 = new BlocksStargate(5);
    public static Block StargateTier6 = new BlocksStargate(6);
    public static Block StargateTier7 = new BlocksStargate(7);
    public static Block StargateTier8 = new BlocksStargate(8);
    public static Block StargateTier9 = new BlocksStargate(9);
    public static Block Stargate_Coil_Compressed = new StargateMetaBlockBase(
        "Stargate_Coil_Compressed",
        new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" });

    public static void registryBlocks() {

        BlockLoader.BlockEternalGregTechWorkshopRender = new BlockEternalGregTechWorkshopRender();
        GameRegistry
            .registerTileEntity(TileEntityEternalGregTechWorkshop.class, "EternalGregTechWorkshopRenderTileEntity");

        BlockLoader.BlockNanoPhagocytosisPlantRender = new BlockNanoPhagocytosisPlantRender();
        GameRegistry.registerTileEntity(TileEntityNanoPhagocytosisPlant.class, "NanoPhagocytosisPlantRenderTileEntity");

        BlockLoader.BlockArtificialStarRender = new BlockArtificialStarRender();
        GameRegistry.registerTileEntity(TileEntityArtificialStar.class, "ArtificialStarRenderTileEntity");

        BlockLoader.PlayerDoll = new BlockPlayerDoll();
        GameRegistry.registerTileEntity(TileEntityPlayerDoll.class, "playerDollTileEntity");

        BlockLoader.LaserBeacon = new BlockLaserBeacon();
        GameRegistry.registerTileEntity(TileEntityLaserBeacon.class, "LaserBeaconTileEntity");

        GameRegistry
            .registerBlock(BlockLoader.MetaBlock, ItemBlockBase.class, BlockLoader.MetaBlock.getUnlocalizedName());
        GameRegistry.registerBlock(
            BlockLoader.MetaBlockGlow,
            ItemBlockGlow.class,
            BlockLoader.MetaBlockGlow.getUnlocalizedName());
        GameRegistry.registerBlock(
            BlockLoader.MetaBlockGlass,
            ItemBlockGlass.class,
            BlockLoader.MetaBlockGlass.getUnlocalizedName());
        GameRegistry.registerBlock(
            BlockLoader.MetaBlockColumn,
            ItemBlockColumn.class,
            BlockLoader.MetaBlockColumn.getUnlocalizedName());

        GameRegistry.registerBlock(
            BlockLoader.MetaCasing,
            MetaItemBlockCasing.class,
            BlockLoader.MetaCasing.getUnlocalizedName());

        GameRegistry.registerBlock(
            BlockLoader.MetaCasing02,
            MetaItemBlockCasing.class,
            BlockLoader.MetaCasing02.getUnlocalizedName());

        GameRegistry.registerBlock(BlockLoader.StargateTier0, "StargateTier0");
        GTNLItemList.StargateTier0.set(new ItemStack(BlockLoader.StargateTier0));
        GameRegistry.registerBlock(BlockLoader.StargateTier1, "StargateTier1");
        GTNLItemList.StargateTier1.set(new ItemStack(BlockLoader.StargateTier1));
        GameRegistry.registerBlock(BlockLoader.StargateTier2, "StargateTier2");
        GTNLItemList.StargateTier2.set(new ItemStack(BlockLoader.StargateTier2));
        GameRegistry.registerBlock(BlockLoader.StargateTier3, "StargateTier3");
        GTNLItemList.StargateTier3.set(new ItemStack(BlockLoader.StargateTier3));
        GameRegistry.registerBlock(BlockLoader.StargateTier4, "StargateTier4");
        GTNLItemList.StargateTier4.set(new ItemStack(BlockLoader.StargateTier4));
        GameRegistry.registerBlock(BlockLoader.StargateTier5, "StargateTier5");
        GTNLItemList.StargateTier5.set(new ItemStack(BlockLoader.StargateTier5));
        GameRegistry.registerBlock(BlockLoader.StargateTier6, "StargateTier6");
        GTNLItemList.StargateTier6.set(new ItemStack(BlockLoader.StargateTier6));
        GameRegistry.registerBlock(BlockLoader.StargateTier7, "StargateTier7");
        GTNLItemList.StargateTier7.set(new ItemStack(BlockLoader.StargateTier7));
        GameRegistry.registerBlock(BlockLoader.StargateTier8, "StargateTier8");
        GTNLItemList.StargateTier8.set(new ItemStack(BlockLoader.StargateTier8));
        GameRegistry.registerBlock(BlockLoader.StargateTier9, "StargateTier9");
        GTNLItemList.StargateTier9.set(new ItemStack(BlockLoader.StargateTier9));
        GameRegistry.registerBlock(BlockLoader.Stargate_Coil_Compressed, ItemBlockMeta.class, "StargateCoilCompressed");
        GTNLItemList.Stargate_Coil_Compressed.set(new ItemStack(BlockLoader.Stargate_Coil_Compressed));
    }

    public static void registryBlockContainers() {

        GTNLItemList.TestMetaBlock01_0.set(ItemBlockBase.initMetaBlock("TestMetaBlock01_0", 0));
        GTNLItemList.NewHorizonsCoil.set(
            ItemBlockBase.initMetaBlock(
                "NewHorizonsCoil",
                1,
                new String[] { RESET + StatCollector.translateToLocal("gt.coilheattooltip") }));
        addItemTooltip(
            GTNLItemList.NewHorizonsCoil.get(1),
            AnimatedText.buildTextWithAnimatedEnd(
                text("179,769,313,486,231,590,772,930,519,078,902,473,361,797,697,894,230,657,273,430,081,")));
        addItemTooltip(
            GTNLItemList.NewHorizonsCoil.get(1),
            AnimatedText.buildTextWithAnimatedEnd(
                text("157,732,675,805,500,963,132,708,477,322,407,536,021,120,113,879,871,393,357,658,789,")));
        addItemTooltip(
            GTNLItemList.NewHorizonsCoil.get(1),
            AnimatedText.buildTextWithAnimatedEnd(
                text("768,814,416,622,492,847,430,639,474,124,377,767,893,424,865,485,276,302,219,601,246,")));
        addItemTooltip(
            GTNLItemList.NewHorizonsCoil.get(1),
            AnimatedText.buildTextWithAnimatedEnd(
                text("094,119,453,082,952,085,005,768,838,150,682,342,462,881,473,913,110,540,827,237,163,")));
        addItemTooltip(
            GTNLItemList.NewHorizonsCoil.get(1),
            AnimatedText.buildTextWithAnimatedEnd(
                text("350,510,684,586,298,239,947,245,938,479,716,304,835,356,329,624,224,137,216")));

        GTNLItemList.StargateCoil.set(ItemBlockBase.initMetaBlock("StargateCoil", 2));
        GTNLItemList.BlackLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Black Lamp Off",
                3,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.BlackLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Black Lamp Off Borderless",
                4,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.PinkLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Pink Lamp Off",
                5,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.PinkLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Pink Lamp Off Borderless",
                6,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.RedLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Red Lamp Off",
                7,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.RedLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Red Lamp Off Borderless",
                8,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.OrangeLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Orange Lamp Off",
                9,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.OrangeLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Orange Lamp Off Borderless",
                10,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.YellowLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Yellow Lamp Off",
                11,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.YellowLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Yellow Lamp Off Borderless",
                12,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.GreenLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Green Lamp Off",
                13,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.GreenLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Green Lamp Off Borderless",
                14,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.LimeLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Lime Lamp Off",
                15,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.LimeLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Lime Lamp Off Borderless",
                16,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.BlueLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Blue Lamp Off",
                17,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.BlueLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Blue Lamp Off Borderless",
                18,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.LightBlueLampOff.set(
            ItemBlockBase.initMetaBlock(
                "LightBlue Lamp Off",
                19,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.LightBlueLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "LightBlue Lamp Off Borderless",
                20,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.CyanLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Cyan Lamp Off",
                21,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.CyanLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Cyan Lamp Off Borderless",
                22,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.BrownLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Brown Lamp Off",
                23,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.BrownLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Brown Lamp Off Borderless",
                24,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.MagentaLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Magenta Lamp Off",
                25,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.MagentaLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Magenta Lamp Off Borderless",
                26,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.PurpleLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Purple Lamp Off",
                27,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.PurpleLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Purple Lamp Off Borderless",
                28,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.GrayLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Gray Lamp Off",
                29,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.GrayLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Gray Lamp Off Borderless",
                30,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.LightGrayLampOff.set(
            ItemBlockBase.initMetaBlock(
                "LightGray Lamp Off",
                31,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.LightGrayLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "LightGray Lamp Off Borderless",
                32,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.WhiteLampOff.set(
            ItemBlockBase.initMetaBlock(
                "White Lamp Off",
                33,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.WhiteLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "White Lamp Off Borderless",
                34,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.BlazeCubeBlock.set(ItemBlockBase.initMetaBlock("BlazeCubeBlock", 35));

        GTNLItemList.FortifyGlowstone.set(ItemBlockGlow.initMetaBlockGlow("Fortify_Glowstone", 0));
        GTNLItemList.BlackLamp.set(ItemBlockGlow.initMetaBlockGlow("Black Lamp", 1));
        GTNLItemList.BlackLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Black Lamp Borderless",
                2,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.PinkLamp.set(ItemBlockGlow.initMetaBlockGlow("Pink Lamp", 3));
        GTNLItemList.PinkLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Pink Lamp Borderless",
                4,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.RedLamp.set(ItemBlockGlow.initMetaBlockGlow("Red Lamp", 5));
        GTNLItemList.RedLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Red Lamp Borderless",
                6,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.OrangeLamp.set(ItemBlockGlow.initMetaBlockGlow("Orange Lamp", 7));
        GTNLItemList.OrangeLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Orange Lamp Borderless",
                8,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.YellowLamp.set(ItemBlockGlow.initMetaBlockGlow("Yellow Lamp", 9));
        GTNLItemList.YellowLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Yellow Lamp Borderless",
                10,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.GreenLamp.set(ItemBlockGlow.initMetaBlockGlow("Green Lamp", 11));
        GTNLItemList.GreenLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Green Lamp Borderless",
                12,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.LimeLamp.set(ItemBlockGlow.initMetaBlockGlow("Lime Lamp", 13));
        GTNLItemList.LimeLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Lime Lamp Borderless",
                14,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.BlueLamp.set(ItemBlockGlow.initMetaBlockGlow("Blue Lamp", 15));
        GTNLItemList.BlueLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Blue Lamp Borderless",
                16,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.LightBlueLamp.set(ItemBlockGlow.initMetaBlockGlow("LightBlue Lamp", 17));
        GTNLItemList.LightBlueLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "LightBlue Lamp Borderless",
                18,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.CyanLamp.set(ItemBlockGlow.initMetaBlockGlow("Cyan Lamp", 19));
        GTNLItemList.CyanLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Cyan Lamp Borderless",
                20,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.BrownLamp.set(ItemBlockGlow.initMetaBlockGlow("Brown Lamp", 21));
        GTNLItemList.BrownLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Brown Lamp Borderless",
                22,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.MagentaLamp.set(ItemBlockGlow.initMetaBlockGlow("Magenta Lamp", 23));
        GTNLItemList.MagentaLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Magenta Lamp Borderless",
                24,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.PurpleLamp.set(ItemBlockGlow.initMetaBlockGlow("Purple Lamp", 25));
        GTNLItemList.PurpleLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Purple Lamp Borderless",
                26,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.GrayLamp.set(ItemBlockGlow.initMetaBlockGlow("Gray Lamp", 27));
        GTNLItemList.GrayLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Gray Lamp Borderless",
                28,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.LightGrayLamp.set(ItemBlockGlow.initMetaBlockGlow("LightGray Lamp", 29));
        GTNLItemList.LightGrayLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "LightGray Lamp Borderless",
                30,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.WhiteLamp.set(ItemBlockGlow.initMetaBlockGlow("White Lamp", 31));
        GTNLItemList.WhiteLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "White Lamp Borderless",
                32,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));

        GTNLItemList.GaiaGlass.set(ItemBlockGlass.initMetaBlockGlass("Gaia Glass", 0));
        GTNLItemList.TerraGlass.set(ItemBlockGlass.initMetaBlockGlass("Terra Glass", 1));
        GTNLItemList.FusionGlass.set(ItemBlockGlass.initMetaBlockGlass("Fusion Glass", 2));
        GTNLItemList.ConcentratingSieveMesh.set(ItemBlockGlass.initMetaBlockGlass("Concentrating Sieve Mesh", 3));

        GTNLItemList.BronzeBrickCasing.set(ItemBlockColumn.initMetaBlock("Bronze Brick Casing", 0));
        GTNLItemList.SteelBrickCasing.set(ItemBlockColumn.initMetaBlock("Steel Brick Casing", 1));
        GTNLItemList.CrushingWheels.set(ItemBlockColumn.initMetaBlock("Crushing Wheels", 2));
        GTNLItemList.SolarBoilingCell.set(ItemBlockColumn.initMetaBlock("Solar Boiling Cell", 3));

        GTNLItemList.TestCasing
            .set(MetaBlockConstructors.initMetaBlockCasing("Test Casing", (byte) 0, BlockLoader.MetaCasing));
        GTNLItemList.SteamAssemblyCasing
            .set(MetaBlockConstructors.initMetaBlockCasing("Steam Assembly Casing", (byte) 1, BlockLoader.MetaCasing));
        GTNLItemList.HeatVent
            .set(MetaBlockConstructors.initMetaBlockCasing("Heat Vent", (byte) 2, BlockLoader.MetaCasing));
        GTNLItemList.SlicingBlades
            .set(MetaBlockConstructors.initMetaBlockCasing("Slicing Blades", (byte) 3, BlockLoader.MetaCasing));
        GTNLItemList.NeutroniumPipeCasing
            .set(MetaBlockConstructors.initMetaBlockCasing("Neutronium Pipe Casing", (byte) 4, BlockLoader.MetaCasing));
        GTNLItemList.NeutroniumGearbox.set(
            MetaBlockConstructors.initMetaBlockCasing("Neutronium Gear Box Casing", (byte) 5, BlockLoader.MetaCasing));
        GTNLItemList.Laser_Cooling_Casing
            .set(MetaBlockConstructors.initMetaBlockCasing("Laser Cooling Casing", (byte) 6, BlockLoader.MetaCasing));
        GTNLItemList.Antifreeze_Heatproof_Machine_Casing.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Antifreeze Heatproof Machine Casing", (byte) 7, BlockLoader.MetaCasing));
        GTNLItemList.MolybdenumDisilicideCoil.set(
            MetaBlockConstructors.initMetaBlockCasing("Molybdenum Disilicide Coil", (byte) 8, BlockLoader.MetaCasing));
        GTNLItemList.EnergeticPhotovoltaicBlock.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Energetic Photovoltaic Block", (byte) 9, BlockLoader.MetaCasing));
        GTNLItemList.AdvancedPhotovoltaicBlock.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Advanced Photovoltaic Block", (byte) 10, BlockLoader.MetaCasing));
        GTNLItemList.VibrantPhotovoltaicBlock.set(
            MetaBlockConstructors.initMetaBlockCasing("Vibrant Photovoltaic Block", (byte) 11, BlockLoader.MetaCasing));
        GTNLItemList.TungstensteelGearbox.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Tungstensteel Gear Box Casing", (byte) 12, BlockLoader.MetaCasing));
        GTNLItemList.DimensionallyStableCasing.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Dimensionally Stable Casing", (byte) 13, BlockLoader.MetaCasing));
        GTNLItemList.PressureBalancedCasing.set(
            MetaBlockConstructors.initMetaBlockCasing("Pressure Balanced Casing", (byte) 14, BlockLoader.MetaCasing));
        GTNLItemList.ABSUltraSolidCasing.set(
            MetaBlockConstructors.initMetaBlockCasing("ABS Ultra-Solid Casing", (byte) 15, BlockLoader.MetaCasing));
        GTNLItemList.GravitationalFocusingLensBlock.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Gravitational Focusing Lens Block", (byte) 16, BlockLoader.MetaCasing));
        GTNLItemList.GaiaStabilizedForceFieldCasing.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Gaia Stabilized Force Field Casing", (byte) 17, BlockLoader.MetaCasing));
        GTNLItemList.HyperCore
            .set(MetaBlockConstructors.initMetaBlockCasing("Hyper Core", (byte) 18, BlockLoader.MetaCasing));
        GTNLItemList.ChemicallyResistantCasing.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Chemically Resistant Casing", (byte) 19, BlockLoader.MetaCasing));
        GTNLItemList.UltraPoweredCasing
            .set(MetaBlockConstructors.initMetaBlockCasing("Ultra Powered Casing", (byte) 20, BlockLoader.MetaCasing));
        GTNLItemList.SteamgateRingBlock
            .set(MetaBlockConstructors.initMetaBlockCasing("Steamgate Ring Block", (byte) 21, BlockLoader.MetaCasing));
        GTNLItemList.SteamgateChevronBlock.set(
            MetaBlockConstructors.initMetaBlockCasing("Steamgate Chevron Block", (byte) 22, BlockLoader.MetaCasing));
        GTNLItemList.IronReinforcedWood
            .set(MetaBlockConstructors.initMetaBlockCasing("Iron Reinforced Wood", (byte) 23, BlockLoader.MetaCasing));
        GTNLItemList.BronzeReinforcedWood.set(
            MetaBlockConstructors.initMetaBlockCasing("Bronze Reinforced Wood", (byte) 24, BlockLoader.MetaCasing));
        GTNLItemList.SteelReinforcedWood
            .set(MetaBlockConstructors.initMetaBlockCasing("Steel Reinforced Wood", (byte) 25, BlockLoader.MetaCasing));
        GTNLItemList.BreelPipeCasing
            .set(MetaBlockConstructors.initMetaBlockCasing("Breel Pipe Casing", (byte) 26, BlockLoader.MetaCasing));
        GTNLItemList.StronzeWrappedCasing.set(
            MetaBlockConstructors.initMetaBlockCasing("Stronze-Wrapped Casing", (byte) 27, BlockLoader.MetaCasing));
        GTNLItemList.HydraulicAssemblingCasing.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Hydraulic Assembling Casing", (byte) 28, BlockLoader.MetaCasing));
        GTNLItemList.HyperPressureBreelCasing.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Hyper Pressure Breel Casing", (byte) 29, BlockLoader.MetaCasing));
        GTNLItemList.BreelPlatedCasing
            .set(MetaBlockConstructors.initMetaBlockCasing("Breel-Plated Casing", (byte) 30, BlockLoader.MetaCasing));
        GTNLItemList.SteamCompactPipeCasing.set(
            MetaBlockConstructors.initMetaBlockCasing("Steam Compact Pipe Casing", (byte) 31, BlockLoader.MetaCasing));
        GTNLItemList.VibrationSafeCasing.set(
            MetaBlockConstructors.initMetaBlockCasing("Vibration-Safe Casing", (byte) 0, BlockLoader.MetaCasing02));
    }

    public static void registry() {
        registryBlocks();
        registryBlockContainers();
    }

    public static void registryAnotherData() {
        ItemStack GaiaGlass = new ItemStack(BlockLoader.MetaBlockGlass, 1, 0);
        ItemStack TerraGlass = new ItemStack(BlockLoader.MetaBlockGlass, 1, 1);
        ItemStack FusionGlass = new ItemStack(BlockLoader.MetaBlockGlass, 1, 2);

        for (int LampMeta = 1; LampMeta <= 32; LampMeta++) {
            ItemStack LampTier = new ItemStack(BlockLoader.MetaBlockGlow, 1, LampMeta);
            OreDictionary.registerOre("blockGlassHV", LampTier);
            OreDictionary.registerOre("blockGlass", LampTier);
        }

        for (int LampOffMeta = 3; LampOffMeta <= 34; LampOffMeta++) {
            ItemStack LampOffTier = new ItemStack(BlockLoader.MetaBlock, 1, LampOffMeta);
            OreDictionary.registerOre("blockGlassHV", LampOffTier);
            OreDictionary.registerOre("blockGlass", LampOffTier);
        }

        OreDictionary.registerOre("blockGlassUEV", GaiaGlass);
        OreDictionary.registerOre("blockGlassUV", TerraGlass);
        OreDictionary.registerOre("blockGlassZPM", FusionGlass);

    }

    public static Block SaplingBrickuoia;

    public static void registerTreeBrickuoia() {
        SaplingBrickuoia = new SaplingBrickuoia();
        GTNLItemList.SaplingBrickuoia.set(new ItemStack(SaplingBrickuoia, 1));
        SaplingBrickuoia.setCreativeTab(GTNLCreativeTabs.GTNotLeisureItem);
        addItemTooltip(
            GTNLItemList.SaplingBrickuoia.get(1),
            () -> StatCollector.translateToLocal("Tooltip_GiantBrickuoiaSapling_00"));
        addItemTooltip(
            GTNLItemList.SaplingBrickuoia.get(1),
            () -> StatCollector.translateToLocal("Tooltip_GiantBrickuoiaSapling_01"));
        addItemTooltip(GTNLItemList.SaplingBrickuoia.get(1), () -> "");
        addItemTooltip(
            GTNLItemList.SaplingBrickuoia.get(1),
            () -> StatCollector.translateToLocal("Tooltip_GiantBrickuoiaSapling_02"));
        OreDictionary.registerOre("treeSapling", new ItemStack(SaplingBrickuoia, 1, GTRecipeBuilder.WILDCARD));
    }
}
