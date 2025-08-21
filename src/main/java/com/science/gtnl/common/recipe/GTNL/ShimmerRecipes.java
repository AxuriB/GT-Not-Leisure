package com.science.gtnl.common.recipe.GTNL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.science.gtnl.Utils.recipes.DisassemblerHelper;
import com.science.gtnl.Utils.recipes.ReversedRecipeRegistry;
import com.science.gtnl.api.IRecipePool;

public class ShimmerRecipes implements IRecipePool {

    public static Map<Item, List<ConversionEntry>> conversionMap = new HashMap<>();

    @Override
    public void loadRecipes() {
        DisassemblerHelper.loadAssemblerRecipesToDisassembler();
        ReversedRecipeRegistry.registerAllReversedRecipes();
    }

    public static void registerConversion(ItemStack input, List<ItemStack> outputs) {
        if (input == null || outputs == null || outputs.isEmpty()) return;

        List<ItemStack> filteredOutputs = outputs.stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        if (filteredOutputs.isEmpty()) return;

        ConversionEntry entry = new ConversionEntry(input, filteredOutputs);

        conversionMap.computeIfAbsent(input.getItem(), k -> new ArrayList<>())
            .add(entry);
    }

    @Nullable
    public static List<ItemStack> getConversionResult(ItemStack input) {
        if (input == null || input.stackSize <= 0) return null;
        List<ConversionEntry> entries = conversionMap.get(input.getItem());
        if (entries == null) return null;

        for (ConversionEntry entry : entries) {
            if (entry.matches(input)) {
                return entry.getScaledOutputs(input.stackSize);
            }
        }

        return null;
    }

    public static boolean isInConversions(ItemStack input) {
        if (input == null) return false;
        List<ConversionEntry> entries = conversionMap.get(input.getItem());
        if (entries == null) return false;
        for (ConversionEntry entry : entries) {
            if (entry.matches(input)) return true;
        }
        return false;
    }

    public static class ConversionEntry {

        final ItemStack input;
        final List<ItemStack> outputs;

        public ConversionEntry(ItemStack input, List<ItemStack> outputs) {
            this.input = input.copy();
            this.outputs = outputs.stream()
                .map(ItemStack::copy)
                .collect(Collectors.toList());
        }

        boolean matches(ItemStack stack) {
            return stack != null && stack.isItemEqual(input) && stack.stackSize > 0;
        }

        public List<ItemStack> getScaledOutputs(int scale) {
            return outputs.stream()
                .map(out -> {
                    ItemStack scaled = out.copy();
                    scaled.stackSize = scaled.stackSize * scale;
                    return scaled;
                })
                .collect(Collectors.toList());
        }
    }
}
