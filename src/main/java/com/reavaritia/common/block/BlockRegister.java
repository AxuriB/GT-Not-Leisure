package com.reavaritia.common.block;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.reavaritia.ReAvaItemList;
import com.reavaritia.common.block.ExtremeAnvil.BlockExtremeAnvil;
import com.reavaritia.common.block.NeutronCollector.ItemBlockNeutronCollector;
import com.reavaritia.common.block.NeutronCollector.NeutronCollector;
import com.reavaritia.common.block.NeutronCollector.TileEntityNeutronCollector;
import com.science.gtnl.config.MainConfig;

import cpw.mods.fml.common.registry.GameRegistry;

public class BlockRegister {

    public static Block ExtremeAnvil;
    public static Block BlockSoulFarmland;
    public static Block NeutronCollector;
    public static Block DenseNeutronCollector;
    public static Block DenserNeutronCollector;
    public static Block DensestNeutronCollector;

    public static void registryBlocks() {

        ExtremeAnvil = new BlockExtremeAnvil();
        NeutronCollector = new NeutronCollector(
            "NeutronCollector",
            3600,
            2,
            "NeutronCollector",
            "NeutronCollector",
            ItemBlockNeutronCollector.class,
            ReAvaItemList.NeutronCollector);

        DenseNeutronCollector = new NeutronCollector(
            "DenseNeutronCollector",
            3600,
            3,
            "DenseNeutronCollector",
            "DenseNeutronCollector",
            ItemBlockNeutronCollector.ItemBlockDenseNeutronCollector.class,
            ReAvaItemList.DenseNeutronCollector);

        DenserNeutronCollector = new NeutronCollector(
            "DenserNeutronCollector",
            3600,
            4,
            "DenserNeutronCollector",
            "DenserNeutronCollector",
            ItemBlockNeutronCollector.ItemBlockDenserNeutronCollector.class,
            ReAvaItemList.DenserNeutronCollector);

        DensestNeutronCollector = new NeutronCollector(
            "DensestNeutronCollector",
            200,
            4,
            "DensestNeutronCollector",
            "DensestNeutronCollector",
            ItemBlockNeutronCollector.ItemBlockDensestNeutronCollector.class,
            ReAvaItemList.DensestNeutronCollector);

        GameRegistry.registerTileEntity(TileEntityNeutronCollector.class, "NeutronCollectorTileEntity");

        BlockSoulFarmland = new BlockSoulFarmland();
    }

    public static void registryAnotherData() {
        ItemStack ExtremeAnvilBlock = new ItemStack(ExtremeAnvil, 1, 0);
        ItemStack Bedrock = new ItemStack(Blocks.bedrock, 1, 0);
        ItemStack EndPortal = new ItemStack(Blocks.end_portal, 1, 0);
        ItemStack EndPortalFrame = new ItemStack(Blocks.end_portal_frame, 1, 0);
        ItemStack CommandBlock = new ItemStack(Blocks.command_block, 1, 0);

        OreDictionary.registerOre(MainConfig.unbreakOre, CommandBlock);
        OreDictionary.registerOre(MainConfig.unbreakOre, EndPortal);
        OreDictionary.registerOre(MainConfig.unbreakOre, EndPortalFrame);
        OreDictionary.registerOre(MainConfig.unbreakOre, Bedrock);
        OreDictionary.registerOre(MainConfig.unbreakOre, ExtremeAnvilBlock);

    }

}
