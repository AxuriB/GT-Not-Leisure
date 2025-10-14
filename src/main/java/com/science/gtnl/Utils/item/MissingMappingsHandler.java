package com.science.gtnl.Utils.item;

import static com.science.gtnl.Utils.enums.ModList.ReAvaritia;
import static com.science.gtnl.Utils.enums.ModList.ScienceNotLeisure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class MissingMappingsHandler {

    // spotless:off
    private static final Remapper REMAPPER = new Remapper()
        // Block remappings
        .remapBlock("ScienceNotLeisure:RealArtificialStarRenderer", GameRegistry.findBlock(ScienceNotLeisure.ID, "RealArtificialStarRenderer"))
        .remapBlock("ScienceNotLeisure:tile.RealArtificialStarRenderer", GameRegistry.findBlock(ScienceNotLeisure.ID, "tile.RealArtificialStarRenderer"))
        .remapBlock("ScienceNotLeisure:LaserBeacon", GameRegistry.findBlock(ScienceNotLeisure.ID, "LaserBeacon"))
        .remapBlock("ScienceNotLeisure:tile.LaserBeacon", GameRegistry.findBlock(ScienceNotLeisure.ID, "tile.LaserBeacon"))
        .remapBlock("ScienceNotLeisure:NanoPhagocytosisPlantRenderer", GameRegistry.findBlock(ScienceNotLeisure.ID, "NanoPhagocytosisPlantRenderer"))
        .remapBlock("ScienceNotLeisure:tile.NanoPhagocytosisPlantRenderer", GameRegistry.findBlock(ScienceNotLeisure.ID, "tile.NanoPhagocytosisPlantRenderer"))
        .remapBlock("ScienceNotLeisure:playerDoll", GameRegistry.findBlock(ScienceNotLeisure.ID, "playerDoll"))
        .remapBlock("ScienceNotLeisure:tile.playerDoll", GameRegistry.findBlock(ScienceNotLeisure.ID, "tile.playerDoll"))
        .remapBlock("ScienceNotLeisure:blockGiantBrickuoiaSapling", GameRegistry.findBlock(ScienceNotLeisure.ID, "blockGiantBrickuoiaSapling"))

        .remapBlock("ScienceNotLeisure:tile.DenseNeutronCollector", GameRegistry.findBlock(ReAvaritia.ID, "tile.DenseNeutronCollector"))
        .remapBlock("ScienceNotLeisure:tile.DenserNeutronCollector", GameRegistry.findBlock(ReAvaritia.ID, "tile.DenserNeutronCollector"))
        .remapBlock("ScienceNotLeisure:tile.DensestNeutronCollector", GameRegistry.findBlock(ReAvaritia.ID, "tile.DensestNeutronCollector"))

        .remapBlock("ScienceNotLeisure:ExtremeAnvil", GameRegistry.findBlock(ReAvaritia.ID, "ExtremeAnvil"))
        .remapBlock("ScienceNotLeisure:tile.ExtremeAnvil", GameRegistry.findBlock(ReAvaritia.ID, "tile.ExtremeAnvil"))
        .remapBlock("ScienceNotLeisure:DenseNeutronCollector", GameRegistry.findBlock(ReAvaritia.ID, "DenseNeutronCollector"))
        .remapBlock("ScienceNotLeisure:DenserNeutronCollector", GameRegistry.findBlock(ReAvaritia.ID, "DenserNeutronCollector"))
        .remapBlock("ScienceNotLeisure:DensestNeutronCollector", GameRegistry.findBlock(ReAvaritia.ID, "DensestNeutronCollector"))
        .remapBlock("ScienceNotLeisure:tile.NeutronCollector", GameRegistry.findBlock(ReAvaritia.ID, "tile.NeutronCollector"))
        .remapBlock("ScienceNotLeisure:tile.BlockSoulFarmland", GameRegistry.findBlock(ReAvaritia.ID, "tile.BlockSoulFarmland"))
        .remapBlock("ScienceNotLeisure:NeutronCollector", GameRegistry.findBlock(ReAvaritia.ID, "NeutronCollector"))
        .remapBlock("ScienceNotLeisure:BlockSoulFarmland", GameRegistry.findBlock(ReAvaritia.ID, "BlockSoulFarmland"))

        .remapBlock("ScienceNotLeisure:Stargate_Coil_Compressed", GameRegistry.findBlock(ScienceNotLeisure.ID, "Stargate_Coil_Compressed"))
        .remapBlock("ScienceNotLeisure:MetaBlock", GameRegistry.findBlock(ScienceNotLeisure.ID, "MetaBlock"))
        .remapBlock("ScienceNotLeisure:MetaBlockGlow", GameRegistry.findBlock(ScienceNotLeisure.ID, "MetaBlockGlow"))
        .remapBlock("ScienceNotLeisure:MetaBlockGlass", GameRegistry.findBlock(ScienceNotLeisure.ID, "MetaBlockGlass"))
        .remapBlock("ScienceNotLeisure:MetaBlockColumn", GameRegistry.findBlock(ScienceNotLeisure.ID, "MetaBlockColumn"))
        .remapBlock("ScienceNotLeisure:tile.MetaBlockColumn", GameRegistry.findBlock(ScienceNotLeisure.ID, "tile.MetaBlockColumn"))
        .remapBlock("ScienceNotLeisure:MetaCasing", GameRegistry.findBlock(ScienceNotLeisure.ID, "MetaCasing"))
        .remapBlock("ScienceNotLeisure:MetaCasing02", GameRegistry.findBlock(ScienceNotLeisure.ID, "MetaCasing02"))

        .remapBlock("ScienceNotLeisure:Stargate0", GameRegistry.findBlock(ScienceNotLeisure.ID, "Stargate0"))
        .remapBlock("ScienceNotLeisure:Stargate1", GameRegistry.findBlock(ScienceNotLeisure.ID, "Stargate1"))
        .remapBlock("ScienceNotLeisure:Stargate2", GameRegistry.findBlock(ScienceNotLeisure.ID, "Stargate2"))
        .remapBlock("ScienceNotLeisure:Stargate3", GameRegistry.findBlock(ScienceNotLeisure.ID, "Stargate3"))
        .remapBlock("ScienceNotLeisure:Stargate4", GameRegistry.findBlock(ScienceNotLeisure.ID, "Stargate4"))
        .remapBlock("ScienceNotLeisure:Stargate5", GameRegistry.findBlock(ScienceNotLeisure.ID, "Stargate5"))
        .remapBlock("ScienceNotLeisure:Stargate6", GameRegistry.findBlock(ScienceNotLeisure.ID, "Stargate6"))
        .remapBlock("ScienceNotLeisure:Stargate7", GameRegistry.findBlock(ScienceNotLeisure.ID, "Stargate7"))
        .remapBlock("ScienceNotLeisure:Stargate8", GameRegistry.findBlock(ScienceNotLeisure.ID, "Stargate8"))
        .remapBlock("ScienceNotLeisure:Stargate9", GameRegistry.findBlock(ScienceNotLeisure.ID, "Stargate9"))

        .remapBlock("ScienceNotLeisure:StargateTier0", GameRegistry.findBlock(ScienceNotLeisure.ID, "StargateTier0"))
        .remapBlock("ScienceNotLeisure:StargateTier1", GameRegistry.findBlock(ScienceNotLeisure.ID, "StargateTier1"))
        .remapBlock("ScienceNotLeisure:StargateTier2", GameRegistry.findBlock(ScienceNotLeisure.ID, "StargateTier2"))
        .remapBlock("ScienceNotLeisure:StargateTier3", GameRegistry.findBlock(ScienceNotLeisure.ID, "StargateTier3"))
        .remapBlock("ScienceNotLeisure:StargateTier4", GameRegistry.findBlock(ScienceNotLeisure.ID, "StargateTier4"))
        .remapBlock("ScienceNotLeisure:StargateTier5", GameRegistry.findBlock(ScienceNotLeisure.ID, "StargateTier5"))
        .remapBlock("ScienceNotLeisure:StargateTier6", GameRegistry.findBlock(ScienceNotLeisure.ID, "StargateTier6"))
        .remapBlock("ScienceNotLeisure:StargateTier7", GameRegistry.findBlock(ScienceNotLeisure.ID, "StargateTier7"))
        .remapBlock("ScienceNotLeisure:StargateTier8", GameRegistry.findBlock(ScienceNotLeisure.ID, "StargateTier8"))
        .remapBlock("ScienceNotLeisure:StargateTier9", GameRegistry.findBlock(ScienceNotLeisure.ID, "StargateTier9"))
        .remapBlock("ScienceNotLeisure:Stargate Coil Compressed", GameRegistry.findBlock(ScienceNotLeisure.ID, "StargateCoilCompressed"))

        // Item remappings
        .remapItem("ScienceNotLeisure:RecordSus", GameRegistry.findItem(ScienceNotLeisure.ID, "RecordSus"))
        .remapItem("ScienceNotLeisure:RecordNewHorizons", GameRegistry.findItem(ScienceNotLeisure.ID, "RecordNewHorizons"))
        .remapItem("ScienceNotLeisure:FakeItemSiren", GameRegistry.findItem(ScienceNotLeisure.ID, "FakeItemSiren"))
        .remapItem("ScienceNotLeisure:KFCFamily", GameRegistry.findItem(ScienceNotLeisure.ID, "KFCFamily"))
        .remapItem("ScienceNotLeisure:RejectionRing", GameRegistry.findItem(ScienceNotLeisure.ID, "RejectionRing"))
        .remapItem("ScienceNotLeisure:SatietyRing", GameRegistry.findItem(ScienceNotLeisure.ID, "SatietyRing"))
        .remapItem("ScienceNotLeisure:SuperReachRing", GameRegistry.findItem(ScienceNotLeisure.ID, "SuperReachRing"))
        .remapItem("ScienceNotLeisure:TestItem", GameRegistry.findItem(ScienceNotLeisure.ID, "TestItem"))
        .remapItem("ScienceNotLeisure:TwilightSword", GameRegistry.findItem(ScienceNotLeisure.ID, "TwilightSword"))
        .remapItem("ScienceNotLeisure:MetaItem", GameRegistry.findItem(ScienceNotLeisure.ID, "MetaItem"))
        .remapItem("ScienceNotLeisure:InfinityFuelRod", GameRegistry.findItem(ScienceNotLeisure.ID, "InfinityFuelRod"))
        .remapItem("ScienceNotLeisure:InfinityFuelRodDepleted", GameRegistry.findItem(ScienceNotLeisure.ID, "InfinityFuelRodDepleted"))
        .remapItem("ScienceNotLeisure:milledNaquadahEnriched", GameRegistry.findItem(ScienceNotLeisure.ID, "milledNaquadahEnriched"))
        .remapItem("ScienceNotLeisure:FrothNaquadahenrichedflotation", GameRegistry.findItem(ScienceNotLeisure.ID, "FrothNaquadahenrichedflotation"))

        .remapItem("ScienceNotLeisure:CrystalPickaxe", GameRegistry.findItem(ReAvaritia.ID, "CrystalPickaxe"))
        .remapItem("ScienceNotLeisure:CrystalHoe", GameRegistry.findItem(ReAvaritia.ID, "CrystalHoe"))
        .remapItem("ScienceNotLeisure:CrystalShovel", GameRegistry.findItem(ReAvaritia.ID, "CrystalShovel"))
        .remapItem("ScienceNotLeisure:CrystalAxe", GameRegistry.findItem(ReAvaritia.ID, "CrystalAxe"))
        .remapItem("ScienceNotLeisure:CrystalSword", GameRegistry.findItem(ReAvaritia.ID, "CrystalSword"))
        .remapItem("ScienceNotLeisure:BlazePickaxe", GameRegistry.findItem(ReAvaritia.ID, "BlazePickaxe"))
        .remapItem("ScienceNotLeisure:BlazeAxe", GameRegistry.findItem(ReAvaritia.ID, "BlazeAxe"))
        .remapItem("ScienceNotLeisure:BlazeHoe", GameRegistry.findItem(ReAvaritia.ID, "BlazeHoe"))
        .remapItem("ScienceNotLeisure:BlazeSword", GameRegistry.findItem(ReAvaritia.ID, "BlazeSword"))
        .remapItem("ScienceNotLeisure:BlazeShovel", GameRegistry.findItem(ReAvaritia.ID, "BlazeShovel"))
        .remapItem("ScienceNotLeisure:InfinitySword", GameRegistry.findItem(ReAvaritia.ID, "InfinitySword"))
        .remapItem("ScienceNotLeisure:InfinityAxe", GameRegistry.findItem(ReAvaritia.ID, "InfinityAxe"))
        .remapItem("ScienceNotLeisure:InfinityPickaxe", GameRegistry.findItem(ReAvaritia.ID, "InfinityPickaxe"))
        .remapItem("ScienceNotLeisure:InfinityShovel", GameRegistry.findItem(ReAvaritia.ID, "InfinityShovel"))
        .remapItem("ScienceNotLeisure:InfinityHoe", GameRegistry.findItem(ReAvaritia.ID, "InfinityHoe"))
        .remapItem("ScienceNotLeisure:InfinityTotem", GameRegistry.findItem(ReAvaritia.ID, "InfinityTotem"))
        .remapItem("ScienceNotLeisure:InfinityBucket", GameRegistry.findItem(ReAvaritia.ID, "InfinityBucket"))
        .remapItem("ScienceNotLeisure:MatterCluster", GameRegistry.findItem(ReAvaritia.ID, "MatterCluster"))

        ;

    // spotless:on

    public static void handleMappings(List<FMLMissingMappingsEvent.MissingMapping> mappings) {
        for (FMLMissingMappingsEvent.MissingMapping mapping : mappings) {
            if (REMAPPER.ignoreMappings.contains(mapping.name) || mapping.name.startsWith("Australia:")) {
                mapping.ignore();
                continue;
            }

            if (mapping.type == GameRegistry.Type.BLOCK) {
                Block block = REMAPPER.blockRemappings.get(mapping.name);
                if (block != null) {
                    mapping.remap(block);
                }
            } else if (mapping.type == GameRegistry.Type.ITEM) {
                Item item = REMAPPER.itemRemappings.get(mapping.name);
                if (item != null) {
                    mapping.remap(item);
                }
            }
        }
    }

    private static class Remapper {

        private final Map<String, Item> itemRemappings = new HashMap<>();
        private final Map<String, Block> blockRemappings = new HashMap<>();
        private final List<String> ignoreMappings = new ArrayList<>();

        public Remapper remapBlock(String oldName, Block newBlock) {
            blockRemappings.put(oldName, newBlock);
            return remapItem(oldName, Item.getItemFromBlock(newBlock));
        }

        public Remapper remapItem(String oldName, Item newItem) {
            itemRemappings.put(oldName, newItem);
            return this;
        }

        public Remapper ignore(String oldName) {
            ignoreMappings.add(oldName);
            return this;
        }
    }
}
