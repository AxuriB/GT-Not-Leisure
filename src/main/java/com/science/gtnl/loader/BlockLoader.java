package com.science.gtnl.loader;

import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.*;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

import com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler;
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
import com.science.gtnl.common.block.blocks.BlockHoneyFluid;
import com.science.gtnl.common.block.blocks.BlockLaserBeacon;
import com.science.gtnl.common.block.blocks.BlockNanoPhagocytosisPlantRender;
import com.science.gtnl.common.block.blocks.BlockPlayerDoll;
import com.science.gtnl.common.block.blocks.BlockPlayerLeash;
import com.science.gtnl.common.block.blocks.BlockShimmerFluid;
import com.science.gtnl.common.block.blocks.tile.TileEntityArtificialStar;
import com.science.gtnl.common.block.blocks.tile.TileEntityEternalGregTechWorkshop;
import com.science.gtnl.common.block.blocks.tile.TileEntityLaserBeacon;
import com.science.gtnl.common.block.blocks.tile.TileEntityNanoPhagocytosisPlant;
import com.science.gtnl.common.block.blocks.tile.TileEntityPlayerDoll;
import com.science.gtnl.common.item.items.SaplingBrickuoia;

import bartworks.common.loaders.ItemRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.util.GTRecipeBuilder;
import gtPlusPlus.core.item.base.itemblock.ItemBlockMeta;

public class BlockLoader {

    public static Block blockArtificialStarRender;
    public static Block laserBeacon;
    public static Block playerDoll;
    public static Block blockPlayerLeash;
    public static Block blockNanoPhagocytosisPlantRender;
    public static Block blockEternalGregTechWorkshopRender;
    public static final Block metaBlock = new MetaBlockBase("MetaBlock", "MetaBlock");
    public static final Block metaBlockGlow = new MetaBlockGlow("MetaBlockGlow", "MetaBlockGlow");
    public static final Block metaBlockGlass = new MetaBlockGlass("MetaBlockGlass", "MetaBlockGlass");
    public static final Block metaBlockColumn = new MetaBlockColumn("MetaBlockColumn", "MetaBlockColumn");
    public static final MetaCasing metaCasing = new MetaCasing("MetaCasing", (byte) 0);
    public static final MetaCasing metaCasing02 = new MetaCasing("MetaCasing02", (byte) 32);
    public static Block stargateTier0 = new BlocksStargate(0);
    public static Block stargateTier1 = new BlocksStargate(1);
    public static Block stargateTier2 = new BlocksStargate(2);
    public static Block stargateTier3 = new BlocksStargate(3);
    public static Block stargateTier4 = new BlocksStargate(4);
    public static Block stargateTier5 = new BlocksStargate(5);
    public static Block stargateTier6 = new BlocksStargate(6);
    public static Block stargateTier7 = new BlocksStargate(7);
    public static Block stargateTier8 = new BlocksStargate(8);
    public static Block stargateTier9 = new BlocksStargate(9);
    public static Block stargateCoilCompressed = new StargateMetaBlockBase(
        "Stargate_Coil_Compressed",
        new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" });
    public static BlockHoneyFluid honeyFluidBlock;
    public static Fluid honeyFluid;
    public static BlockShimmerFluid shimmerFluidBlock;
    public static Fluid shimmerFluid;

    public static void registryBlocks() {

        blockArtificialStarRender = new BlockArtificialStarRender();
        laserBeacon = new BlockLaserBeacon();
        playerDoll = new BlockPlayerDoll();
        blockPlayerLeash = new BlockPlayerLeash();
        blockNanoPhagocytosisPlantRender = new BlockNanoPhagocytosisPlantRender();
        blockEternalGregTechWorkshopRender = new BlockEternalGregTechWorkshopRender();

        GameRegistry
            .registerTileEntity(TileEntityEternalGregTechWorkshop.class, "EternalGregTechWorkshopRenderTileEntity");
        GameRegistry.registerTileEntity(TileEntityNanoPhagocytosisPlant.class, "NanoPhagocytosisPlantRenderTileEntity");
        GameRegistry.registerTileEntity(TileEntityArtificialStar.class, "ArtificialStarRenderTileEntity");
        GameRegistry.registerTileEntity(TileEntityPlayerDoll.class, "playerDollTileEntity");
        GameRegistry.registerTileEntity(TileEntityLaserBeacon.class, "LaserBeaconTileEntity");

        GameRegistry
            .registerBlock(BlockLoader.metaBlock, ItemBlockBase.class, BlockLoader.metaBlock.getUnlocalizedName());
        GameRegistry.registerBlock(
            BlockLoader.metaBlockGlow,
            ItemBlockGlow.class,
            BlockLoader.metaBlockGlow.getUnlocalizedName());
        GameRegistry.registerBlock(
            BlockLoader.metaBlockGlass,
            ItemBlockGlass.class,
            BlockLoader.metaBlockGlass.getUnlocalizedName());
        GameRegistry.registerBlock(
            BlockLoader.metaBlockColumn,
            ItemBlockColumn.class,
            BlockLoader.metaBlockColumn.getUnlocalizedName());

        GameRegistry.registerBlock(
            BlockLoader.metaCasing,
            MetaItemBlockCasing.class,
            BlockLoader.metaCasing.getUnlocalizedName());

        GameRegistry.registerBlock(
            BlockLoader.metaCasing02,
            MetaItemBlockCasing.class,
            BlockLoader.metaCasing02.getUnlocalizedName());

        GameRegistry.registerBlock(BlockLoader.stargateTier0, "StargateTier0");
        GTNLItemList.StargateTier0.set(new ItemStack(BlockLoader.stargateTier0));
        GameRegistry.registerBlock(BlockLoader.stargateTier1, "StargateTier1");
        GTNLItemList.StargateTier1.set(new ItemStack(BlockLoader.stargateTier1));
        GameRegistry.registerBlock(BlockLoader.stargateTier2, "StargateTier2");
        GTNLItemList.StargateTier2.set(new ItemStack(BlockLoader.stargateTier2));
        GameRegistry.registerBlock(BlockLoader.stargateTier3, "StargateTier3");
        GTNLItemList.StargateTier3.set(new ItemStack(BlockLoader.stargateTier3));
        GameRegistry.registerBlock(BlockLoader.stargateTier4, "StargateTier4");
        GTNLItemList.StargateTier4.set(new ItemStack(BlockLoader.stargateTier4));
        GameRegistry.registerBlock(BlockLoader.stargateTier5, "StargateTier5");
        GTNLItemList.StargateTier5.set(new ItemStack(BlockLoader.stargateTier5));
        GameRegistry.registerBlock(BlockLoader.stargateTier6, "StargateTier6");
        GTNLItemList.StargateTier6.set(new ItemStack(BlockLoader.stargateTier6));
        GameRegistry.registerBlock(BlockLoader.stargateTier7, "StargateTier7");
        GTNLItemList.StargateTier7.set(new ItemStack(BlockLoader.stargateTier7));
        GameRegistry.registerBlock(BlockLoader.stargateTier8, "StargateTier8");
        GTNLItemList.StargateTier8.set(new ItemStack(BlockLoader.stargateTier8));
        GameRegistry.registerBlock(BlockLoader.stargateTier9, "StargateTier9");
        GTNLItemList.StargateTier9.set(new ItemStack(BlockLoader.stargateTier9));
        GameRegistry.registerBlock(BlockLoader.stargateCoilCompressed, ItemBlockMeta.class, "StargateCoilCompressed");
        GTNLItemList.Stargate_Coil_Compressed.set(new ItemStack(BlockLoader.stargateCoilCompressed));

        honeyFluid = new Fluid("honey").setViscosity(6000)
            .setDensity(1500);
        FluidRegistry.registerFluid(honeyFluid);
        honeyFluidBlock = new BlockHoneyFluid(honeyFluid);
        GameRegistry.registerBlock(honeyFluidBlock, "honey");
        GTNLItemList.HoneyFluidBlock.set(new ItemStack(BlockLoader.honeyFluidBlock));

        shimmerFluid = new Fluid("shimmer").setViscosity(800);
        FluidRegistry.registerFluid(shimmerFluid);
        shimmerFluidBlock = new BlockShimmerFluid(shimmerFluid);
        GameRegistry.registerBlock(shimmerFluidBlock, "shimmer");
        GTNLItemList.ShimmerFluidBlock.set(new ItemStack(BlockLoader.shimmerFluidBlock));

        GTNLItemList.ShirabonReinforcedBoronSilicateGlass.set(new ItemStack(ItemRegistry.bw_realglas2, 1, 1));
        addItemTooltip(
            GTNLItemList.ShirabonReinforcedBoronSilicateGlass.get(1),
            AnimatedText.SCIENCE_NOT_LEISURE_CHANGE);
        GTNLItemList.QuarkGluonPlasmaReinforcedBoronSilicateGlass.set(new ItemStack(ItemRegistry.bw_realglas2, 1, 2));
        addItemTooltip(
            GTNLItemList.QuarkGluonPlasmaReinforcedBoronSilicateGlass.get(1),
            AnimatedText.SCIENCE_NOT_LEISURE_CHANGE);
    }

    public static void registryBlockContainers() {

        GTNLItemList.TestMetaBlock01_0.set(ItemBlockBase.initMetaBlock("TestMetaBlock01_0", 0));
        GTNLItemList.NewHorizonsCoil.set(
            ItemBlockBase.initMetaBlock(
                "NewHorizonsCoil",
                1,
                new String[] { RESET + StatCollector.translateToLocal("gt.coilheattooltip") }));
        AnimatedTooltipHandler.addItemTooltip(
            GTNLItemList.NewHorizonsCoil.get(1),
            AnimatedTooltipHandler.animatedText(
                "179,769,313,486,231,590,772,930,519,078,902,473,361,797,697,894,230,657,273,430,081,",
                1,
                80,
                RED,
                GOLD,
                YELLOW,
                GREEN,
                AQUA,
                BLUE,
                LIGHT_PURPLE));
        AnimatedTooltipHandler.addItemTooltip(
            GTNLItemList.NewHorizonsCoil.get(1),
            AnimatedTooltipHandler.animatedText(
                "157,732,675,805,500,963,132,708,477,322,407,536,021,120,113,879,871,393,357,658,789,",
                1,
                80,
                GOLD,
                YELLOW,
                GREEN,
                AQUA,
                BLUE,
                LIGHT_PURPLE,
                RED));
        AnimatedTooltipHandler.addItemTooltip(
            GTNLItemList.NewHorizonsCoil.get(1),
            AnimatedTooltipHandler.animatedText(
                "768,814,416,622,492,847,430,639,474,124,377,767,893,424,865,485,276,302,219,601,246,",
                1,
                80,
                YELLOW,
                GREEN,
                AQUA,
                BLUE,
                LIGHT_PURPLE,
                RED,
                GOLD));
        AnimatedTooltipHandler.addItemTooltip(
            GTNLItemList.NewHorizonsCoil.get(1),
            AnimatedTooltipHandler.animatedText(
                "094,119,453,082,952,085,005,768,838,150,682,342,462,881,473,913,110,540,827,237,163,",
                1,
                80,
                GREEN,
                AQUA,
                BLUE,
                LIGHT_PURPLE,
                RED,
                GOLD,
                YELLOW));
        AnimatedTooltipHandler.addItemTooltip(
            GTNLItemList.NewHorizonsCoil.get(1),
            AnimatedTooltipHandler.animatedText(
                "350,510,684,586,298,239,947,245,938,479,716,304,835,356,329,624,224,137,216"
                    + StatCollector.translateToLocal("gt.coilunittooltip"),
                1,
                80,
                AQUA,
                BLUE,
                LIGHT_PURPLE,
                RED,
                GOLD,
                YELLOW,
                GREEN));

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
            .set(MetaBlockConstructors.initMetaBlockCasing("Test Casing", (byte) 0, BlockLoader.metaCasing));
        GTNLItemList.SteamAssemblyCasing
            .set(MetaBlockConstructors.initMetaBlockCasing("Steam Assembly Casing", (byte) 1, BlockLoader.metaCasing));
        GTNLItemList.HeatVent
            .set(MetaBlockConstructors.initMetaBlockCasing("Heat Vent", (byte) 2, BlockLoader.metaCasing));
        GTNLItemList.SlicingBlades
            .set(MetaBlockConstructors.initMetaBlockCasing("Slicing Blades", (byte) 3, BlockLoader.metaCasing));
        GTNLItemList.NeutroniumPipeCasing
            .set(MetaBlockConstructors.initMetaBlockCasing("Neutronium Pipe Casing", (byte) 4, BlockLoader.metaCasing));
        GTNLItemList.NeutroniumGearbox.set(
            MetaBlockConstructors.initMetaBlockCasing("Neutronium Gear Box Casing", (byte) 5, BlockLoader.metaCasing));
        GTNLItemList.Laser_Cooling_Casing
            .set(MetaBlockConstructors.initMetaBlockCasing("Laser Cooling Casing", (byte) 6, BlockLoader.metaCasing));
        GTNLItemList.Antifreeze_Heatproof_Machine_Casing.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Antifreeze Heatproof Machine Casing", (byte) 7, BlockLoader.metaCasing));
        GTNLItemList.MolybdenumDisilicideCoil.set(
            MetaBlockConstructors.initMetaBlockCasing("Molybdenum Disilicide Coil", (byte) 8, BlockLoader.metaCasing));
        GTNLItemList.EnergeticPhotovoltaicBlock.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Energetic Photovoltaic Block", (byte) 9, BlockLoader.metaCasing));
        GTNLItemList.AdvancedPhotovoltaicBlock.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Advanced Photovoltaic Block", (byte) 10, BlockLoader.metaCasing));
        GTNLItemList.VibrantPhotovoltaicBlock.set(
            MetaBlockConstructors.initMetaBlockCasing("Vibrant Photovoltaic Block", (byte) 11, BlockLoader.metaCasing));
        GTNLItemList.TungstensteelGearbox.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Tungstensteel Gear Box Casing", (byte) 12, BlockLoader.metaCasing));
        GTNLItemList.DimensionallyStableCasing.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Dimensionally Stable Casing", (byte) 13, BlockLoader.metaCasing));
        GTNLItemList.PressureBalancedCasing.set(
            MetaBlockConstructors.initMetaBlockCasing("Pressure Balanced Casing", (byte) 14, BlockLoader.metaCasing));
        GTNLItemList.ABSUltraSolidCasing.set(
            MetaBlockConstructors.initMetaBlockCasing("ABS Ultra-Solid Casing", (byte) 15, BlockLoader.metaCasing));
        GTNLItemList.GravitationalFocusingLensBlock.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Gravitational Focusing Lens Block", (byte) 16, BlockLoader.metaCasing));
        GTNLItemList.GaiaStabilizedForceFieldCasing.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Gaia Stabilized Force Field Casing", (byte) 17, BlockLoader.metaCasing));
        GTNLItemList.HyperCore
            .set(MetaBlockConstructors.initMetaBlockCasing("Hyper Core", (byte) 18, BlockLoader.metaCasing));
        GTNLItemList.ChemicallyResistantCasing.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Chemically Resistant Casing", (byte) 19, BlockLoader.metaCasing));
        GTNLItemList.UltraPoweredCasing
            .set(MetaBlockConstructors.initMetaBlockCasing("Ultra Powered Casing", (byte) 20, BlockLoader.metaCasing));
        GTNLItemList.SteamgateRingBlock
            .set(MetaBlockConstructors.initMetaBlockCasing("Steamgate Ring Block", (byte) 21, BlockLoader.metaCasing));
        GTNLItemList.SteamgateChevronBlock.set(
            MetaBlockConstructors.initMetaBlockCasing("Steamgate Chevron Block", (byte) 22, BlockLoader.metaCasing));
        GTNLItemList.IronReinforcedWood
            .set(MetaBlockConstructors.initMetaBlockCasing("Iron Reinforced Wood", (byte) 23, BlockLoader.metaCasing));
        GTNLItemList.BronzeReinforcedWood.set(
            MetaBlockConstructors.initMetaBlockCasing("Bronze Reinforced Wood", (byte) 24, BlockLoader.metaCasing));
        GTNLItemList.SteelReinforcedWood
            .set(MetaBlockConstructors.initMetaBlockCasing("Steel Reinforced Wood", (byte) 25, BlockLoader.metaCasing));
        GTNLItemList.BreelPipeCasing
            .set(MetaBlockConstructors.initMetaBlockCasing("Breel Pipe Casing", (byte) 26, BlockLoader.metaCasing));
        GTNLItemList.StronzeWrappedCasing.set(
            MetaBlockConstructors.initMetaBlockCasing("Stronze-Wrapped Casing", (byte) 27, BlockLoader.metaCasing));
        GTNLItemList.HydraulicAssemblingCasing.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Hydraulic Assembling Casing", (byte) 28, BlockLoader.metaCasing));
        GTNLItemList.HyperPressureBreelCasing.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Hyper Pressure Breel Casing", (byte) 29, BlockLoader.metaCasing));
        GTNLItemList.BreelPlatedCasing
            .set(MetaBlockConstructors.initMetaBlockCasing("Breel-Plated Casing", (byte) 30, BlockLoader.metaCasing));
        GTNLItemList.SteamCompactPipeCasing.set(
            MetaBlockConstructors.initMetaBlockCasing("Steam Compact Pipe Casing", (byte) 31, BlockLoader.metaCasing));
        GTNLItemList.VibrationSafeCasing.set(
            MetaBlockConstructors.initMetaBlockCasing("Vibration-Safe Casing", (byte) 0, BlockLoader.metaCasing02));
    }

    public static void registry() {
        registryBlocks();
        registryBlockContainers();
    }

    public static Block SaplingBrickuoia;

    public static void registerTreeBrickuoia() {
        SaplingBrickuoia = new SaplingBrickuoia();
        GTNLItemList.SaplingBrickuoia.set(new ItemStack(SaplingBrickuoia, 1));
        SaplingBrickuoia.setCreativeTab(GTNLCreativeTabs.GTNotLeisureItem);
        AnimatedTooltipHandler.addItemTooltip(
            GTNLItemList.SaplingBrickuoia.get(1),
            () -> StatCollector.translateToLocal("Tooltip_GiantBrickuoiaSapling_00"));
        AnimatedTooltipHandler.addItemTooltip(
            GTNLItemList.SaplingBrickuoia.get(1),
            () -> StatCollector.translateToLocal("Tooltip_GiantBrickuoiaSapling_01"));
        AnimatedTooltipHandler.addItemTooltip(GTNLItemList.SaplingBrickuoia.get(1), () -> "");
        AnimatedTooltipHandler.addItemTooltip(
            GTNLItemList.SaplingBrickuoia.get(1),
            () -> StatCollector.translateToLocal("Tooltip_GiantBrickuoiaSapling_02"));
        OreDictionary.registerOre("treeSapling", new ItemStack(SaplingBrickuoia, 1, GTRecipeBuilder.WILDCARD));
    }
}
