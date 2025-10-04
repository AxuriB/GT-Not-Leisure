package com.science.gtnl.common.recipe.GTNL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.science.gtnl.ScienceNotLeisure;
import com.science.gtnl.api.IRecipePool;

import cpw.mods.fml.common.registry.GameData;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.Mods;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;
import gtPlusPlus.api.recipe.GTPPRecipeMaps;

public class TestRecipes implements IRecipePool {

    public RecipeMap<?> As = GTPPRecipeMaps.vacuumFurnaceRecipes;

    @Override
    public void loadRecipes() {
        if (!Mods.PamsHarvestCraft.isModLoaded()) return;
        String targetModId = Mods.PamsHarvestCraft.ID;
        String[] targetItemNames = { "potItem", "skilletItem", "bakewareItem", "saucepanItem", "mortarandpestleItem",
            "mixingbowlItem", "cuttingboardItem", "juicerItem" };

        List<Item> targetItems = new ArrayList<>();
        for (String name : targetItemNames) {
            Item item = GameData.getItemRegistry()
                .getObject(targetModId + ":" + name);
            if (item != null) {
                targetItems.add(item);
            } else {
                ScienceNotLeisure.LOG.warn("Cannot find item: {}:{}", targetModId, name);
            }
        }

        if (targetItems.isEmpty()) {
            ScienceNotLeisure.LOG.warn("No target items found from {}", targetModId);
            return;
        }

        for (IRecipe recipe : CraftingManager.getInstance()
            .getRecipeList()) {
            if (!(recipe instanceof ShapelessRecipes || recipe instanceof ShapelessOreRecipe)) continue;

            List<Object> inputs = new ArrayList<>();
            if (recipe instanceof ShapelessRecipes shapelessRecipes) {
                inputs.addAll(shapelessRecipes.recipeItems);
            } else if (recipe instanceof ShapelessOreRecipe shapelessOreRecipe) {
                Collections.addAll(
                    inputs,
                    shapelessOreRecipe.getInput()
                        .toArray());
            }

            boolean hasTarget = false;
            boolean hasFluid = false;
            FluidStack fluid = null;

            List<Object> finalInputs = new ArrayList<>();

            for (Object in : inputs) {
                if (in == null) continue;
                ItemStack stack = null;
                Object resultInput = null;

                if (in instanceof ItemStack itemStack) {
                    stack = itemStack.copy();
                    resultInput = stack;
                } else if (in instanceof List<?>list && !list.isEmpty() && list.get(0) instanceof ItemStack itemStack) {
                    stack = itemStack.copy();
                    int[] ids = OreDictionary.getOreIDs(stack);
                    if (ids.length > 0) {
                        resultInput = new Object[] { OreDictionary.getOreName(ids[0]), 1 };
                    } else {
                        resultInput = stack;
                    }
                } else if (in instanceof String oreName) {
                    resultInput = new Object[] { oreName, 1 };
                    List<ItemStack> ores = OreDictionary.getOres(oreName);
                    if (!ores.isEmpty()) {
                        stack = ores.get(0)
                            .copy();
                    }
                }

                if (stack == null) continue;

                boolean isTool = false;
                for (Item target : targetItems) {
                    if (stack.getItem() == target) {
                        hasTarget = true;
                        isTool = true;
                        break;
                    }
                }

                FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(stack);
                if (fs != null && !hasFluid) {
                    hasFluid = true;
                    fluid = fs;
                    continue;
                }

                if (isTool) {
                    ItemStack zeroStack = stack.copy();
                    zeroStack.stackSize = 0;
                    finalInputs.add(zeroStack);
                } else {
                    finalInputs.add(resultInput);
                }
            }

            if (!hasTarget) continue;

            ItemStack output = recipe.getRecipeOutput();
            if (output == null) continue;
            output = output.copy();
            output.stackSize = Math.min(output.stackSize * 4, output.getMaxStackSize());

            GTValues.RA.stdBuilder()
                .itemInputs(finalInputs.toArray(new Object[0]))
                .itemOutputs(output)
                .fluidInputs(hasFluid ? new FluidStack[] { fluid } : new FluidStack[0])
                .duration(100)
                .eut(TierEU.LV)
                .addTo(As);
        }

        ScienceNotLeisure.LOG.info("Pam’s Cooking Tool recipes successfully converted.");
    }
}
