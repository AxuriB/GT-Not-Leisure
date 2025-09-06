package com.science.gtnl.Utils;

import net.minecraft.item.ItemStack;

import appeng.api.AEApi;
import appeng.api.definitions.IBlockDefinition;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.Getter;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class CraftingUnitHandler {

    private static final Object2ObjectMap<SimpleItem, BlockState> CraftingUnitItemMap = new Object2ObjectOpenHashMap<>();

    private static final Object2ObjectMap<BlockState, ItemStack> CraftingUnitBlockMap = new Object2ObjectOpenHashMap<>();

    @Getter
    private static final BlockState baseUnit;

    static {
        var def = AEApi.instance()
            .definitions()
            .blocks()
            .craftingUnit();
        baseUnit = new BlockState(
            def.maybeBlock()
                .get(),
            def.maybeStack(1)
                .get()
                .getItemDamage());
    }

    public static void addMatch(ItemStack item, BlockState blockState) {
        CraftingUnitItemMap.put(SimpleItem.getInstance(item), blockState);
        CraftingUnitBlockMap.put(blockState, item);
    }

    public static void addMatch(ItemStack item, IBlockDefinition block) {
        addMatch(item, new BlockState(block));
    }

    public static boolean isReplaceable(ItemStack item, BlockState block) {
        return (baseUnit.equals(block) || CraftingUnitBlockMap.containsKey(block))
            && (item == null || CraftingUnitItemMap.containsKey(SimpleItem.getInstance(item)));
    }

    public static BlockState getMatchBlock(ItemStack item) {
        return CraftingUnitItemMap.get(SimpleItem.getInstance(item));
    }

    public static ItemStack getMatchItem(BlockState block) {
        var item = CraftingUnitBlockMap.get(block);
        if (item != null) {
            item = item.copy();
            item.stackSize = 1;
        }
        return item;
    }

    public static void register() {
        var def = AEApi.instance()
            .definitions();
        var materials = def.materials();
        var blocks = def.blocks();
        addMatch(
            materials.cell1kPart()
                .maybeStack(1)
                .get(),
            blocks.craftingStorage1k());
        addMatch(
            materials.cell4kPart()
                .maybeStack(1)
                .get(),
            blocks.craftingStorage4k());
        addMatch(
            materials.cell16kPart()
                .maybeStack(1)
                .get(),
            blocks.craftingStorage16k());
        addMatch(
            materials.cell64kPart()
                .maybeStack(1)
                .get(),
            blocks.craftingStorage64k());
        addMatch(
            materials.cell256kPart()
                .maybeStack(1)
                .get(),
            blocks.craftingStorage256k());
        addMatch(
            materials.cell1024kPart()
                .maybeStack(1)
                .get(),
            blocks.craftingStorage1024k());
        addMatch(
            materials.cell4096kPart()
                .maybeStack(1)
                .get(),
            blocks.craftingStorage4096k());
        addMatch(
            materials.cell16384kPart()
                .maybeStack(1)
                .get(),
            blocks.craftingStorage16384k());
    }
}
