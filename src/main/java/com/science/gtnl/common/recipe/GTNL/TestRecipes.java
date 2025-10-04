package com.science.gtnl.common.recipe.GTNL;

import java.util.*;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.science.gtnl.ScienceNotLeisure;
import com.science.gtnl.api.IRecipePool;

import cpw.mods.fml.common.registry.GameData;
import gregtech.api.enums.*;
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

        Set<String> recipeSignatures = new HashSet<>();

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
            for (Object in : inputs) {
                ItemStack stack = extractStack(in);
                if (stack == null) continue;
                for (Item target : targetItems) {
                    if (stack.getItem() == target) {
                        hasTarget = true;
                        break;
                    }
                }
            }

            if (!hasTarget) continue;

            ItemStack output = recipe.getRecipeOutput();
            if (output == null) continue;
            output = output.copy();
            output.stackSize = Math.min(output.stackSize * 4, output.getMaxStackSize());

            List<List<Object>> expandedInputs = expandInputs(inputs);

            for (List<Object> combo : expandedInputs) {
                if (!combo.isEmpty()) {
                    ItemStack first = combo.get(0) instanceof ItemStack s ? s : null;
                    if (first != null) {
                        int[] ids = OreDictionary.getOreIDs(first);
                        for (int id : ids) {
                            String oreName = OreDictionary.getOreName(id);
                            Item matchedTool = findToolByOreName(targetItems, oreName);
                            if (matchedTool != null) {
                                ItemStack toolStack = new ItemStack(matchedTool, 0);
                                combo.set(0, toolStack);
                                break;
                            }
                        }
                    }
                }

                List<Object> finalInputs = new ArrayList<>();
                boolean hasFluid = false;
                FluidStack fluid = null;

                for (Object obj : combo) {
                    if (!(obj instanceof ItemStack stack)) continue;

                    FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(stack);
                    if (fs != null && !hasFluid) {
                        hasFluid = true;
                        fluid = fs;
                        continue;
                    }

                    boolean isTool = false;
                    for (Item target : targetItems) {
                        if (stack.getItem() == target) {
                            isTool = true;
                            break;
                        }
                    }

                    if (isTool) {
                        ItemStack zero = stack.copy();
                        zero.stackSize = 0;
                        finalInputs.add(zero);
                    } else {
                        finalInputs.add(stack);
                    }
                }

                String sig = makeRecipeSignature(finalInputs, output, fluid);
                if (recipeSignatures.contains(sig)) continue;
                recipeSignatures.add(sig);

                GTValues.RA.stdBuilder()
                    .itemInputs(finalInputs.toArray(new Object[0]))
                    .itemOutputs(output)
                    .fluidInputs(hasFluid ? new FluidStack[] { fluid } : new FluidStack[0])
                    .duration(100)
                    .eut(TierEU.LV)
                    .addTo(As);
            }
        }

        ScienceNotLeisure.LOG.info("Pam’s Cooking Tool recipes successfully expanded, deduplicated, and converted.");
    }

    private static ItemStack extractStack(Object in) {
        if (in instanceof ItemStack itemStack) {
            return itemStack.copy();
        } else if (in instanceof List<?>list && !list.isEmpty() && list.get(0) instanceof ItemStack itemStack) {
            return itemStack.copy();
        } else if (in instanceof String oreName) {
            List<ItemStack> ores = OreDictionary.getOres(oreName);
            if (!ores.isEmpty()) return ores.get(0)
                .copy();
        }
        return null;
    }

    private static List<List<Object>> expandInputs(List<Object> inputs) {
        List<List<Object>> result = new ArrayList<>();
        result.add(new ArrayList<>());

        for (Object in : inputs) {
            List<ItemStack> candidates = new ArrayList<>();

            if (in instanceof ItemStack stack) {
                candidates.add(stack.copy());
            } else if (in instanceof String oreName) {
                candidates.addAll(OreDictionary.getOres(oreName));
            } else if (in instanceof List<?>list) {
                for (Object o : list) {
                    if (o instanceof ItemStack stack2) {
                        candidates.add(stack2.copy());
                    }
                }
            }

            if (candidates.isEmpty()) continue;

            List<List<Object>> newResult = new ArrayList<>();
            for (List<Object> base : result) {
                for (ItemStack c : candidates) {
                    List<Object> newCombo = new ArrayList<>(base);
                    newCombo.add(c);
                    newResult.add(newCombo);
                }
            }
            result = newResult;
        }

        return result;
    }

    /**
     * 根据输入物品的矿辞名，匹配拥有相同矿辞的工具
     */
    private static Item findToolByOreName(List<Item> targetItems, String oreName) {
        if (oreName == null || oreName.isEmpty()) return null;

        for (Item item : targetItems) {
            ItemStack toolStack = new ItemStack(item);
            int[] ids = OreDictionary.getOreIDs(toolStack);

            for (int id : ids) {
                String toolOreName = OreDictionary.getOreName(id);
                if (toolOreName.equalsIgnoreCase(oreName)) {
                    return item;
                }
            }
        }

        return null;
    }

    private static String makeRecipeSignature(List<Object> inputs, ItemStack output, FluidStack fluid) {
        StringBuilder sb = new StringBuilder();
        sb.append("OUT:")
            .append(Item.itemRegistry.getNameForObject(output.getItem()))
            .append(":")
            .append(output.getItemDamage())
            .append(":")
            .append(output.stackSize);

        sb.append(";IN:");
        List<String> parts = new ArrayList<>();
        for (Object obj : inputs) {
            if (obj instanceof ItemStack stack) {
                String name = Item.itemRegistry.getNameForObject(stack.getItem());
                parts.add(name + ":" + stack.getItemDamage());
            }
        }
        Collections.sort(parts);
        for (String s : parts) sb.append(s)
            .append(",");

        if (fluid != null) {
            sb.append(";FLUID:")
                .append(
                    fluid.getFluid()
                        .getName())
                .append(":")
                .append(fluid.amount);
        }

        return sb.toString();
    }
}
