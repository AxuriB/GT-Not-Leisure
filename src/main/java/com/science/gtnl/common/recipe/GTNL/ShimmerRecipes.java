package com.science.gtnl.common.recipe.GTNL;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.science.gtnl.api.IRecipePool;

public class ShimmerRecipes implements IRecipePool {

    private static final List<ConversionEntry> conversions = new ArrayList<>();

    @Override
    public void loadRecipes() {
        ShimmerRecipes.registerConversion(new ItemStack(Items.iron_ingot), new ItemStack(Items.gold_ingot), 2);
    }

    public static void registerConversion(ItemStack input, ItemStack output, int multiplier) {
        conversions.add(new ConversionEntry(input, output, multiplier));
    }

    @Nullable
    public static ItemStack getConversionResult(ItemStack input) {
        for (ConversionEntry entry : conversions) {
            if (entry.matches(input)) {
                ItemStack result = entry.output.copy();
                result.stackSize = input.stackSize * entry.outputMultiplier;
                return result;
            }
        }
        return null;
    }

    public static class ConversionEntry {

        final ItemStack input;
        final ItemStack output;
        final int outputMultiplier;

        ConversionEntry(ItemStack input, ItemStack output, int outputMultiplier) {
            this.input = input.copy();
            this.output = output.copy();
            this.outputMultiplier = outputMultiplier;
        }

        boolean matches(ItemStack stack) {
            return stack != null && stack.isItemEqual(input);
        }
    }
}
