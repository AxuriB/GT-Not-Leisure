package com.science.gtnl.Utils.recipes;

import java.util.*;

import net.minecraft.item.ItemStack;

import com.science.gtnl.config.MainConfig;

import bartworks.system.material.WerkstoffLoader;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.recipe.RecipeMapBackend;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTRecipe;
import gtPlusPlus.api.recipe.GTPPRecipeMaps;

public class RemoveRecipes {

    public static void removeRecipes() {
        RecipeMapBackend AutoClaveRecipe = RecipeMaps.autoclaveRecipes.getBackend();
        RecipeMapBackend CircuitAssemblerRecipe = RecipeMaps.circuitAssemblerRecipes.getBackend();
        RecipeMapBackend FormingPressRecipe = RecipeMaps.formingPressRecipes.getBackend();
        RecipeMapBackend VacuumFurnaceRecipe = GTPPRecipeMaps.vacuumFurnaceRecipes.getBackend();
        RecipeMapBackend BlastFurnaceRecipe = RecipeMaps.blastFurnaceRecipes.getBackend();
        RecipeMapBackend AlloyBlastSmelterRecipe = GTPPRecipeMaps.alloyBlastSmelterRecipes.getBackend();
        Map<String, Integer> removedRecipeCounts = new HashMap<>();

        List<GTRecipe> recipesToRemoveFromAlloyBlastSmelter = new ArrayList<>();
        for (GTRecipe recipe : AlloyBlastSmelterRecipe.getAllRecipes()) {
            for (ItemStack input : recipe.mInputs) {
                if (input != null) {
                    // 熔融铕
                    if (input.isItemEqual(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Europium, 1))) {
                        recipesToRemoveFromAlloyBlastSmelter.add(recipe);
                        break;
                    }
                }
            }
        }
        AlloyBlastSmelterRecipe.removeRecipes(recipesToRemoveFromAlloyBlastSmelter);

        List<GTRecipe> recipesToRemoveFromAutoClave = new ArrayList<>();
        for (GTRecipe recipe : AutoClaveRecipe.getAllRecipes()) {
            for (ItemStack output : recipe.mOutputs) {
                if (output != null) {
                    // 活性生物晶圆
                    if (output.isItemEqual(ItemList.Circuit_Wafer_Bioware.get(1))) {
                        recipesToRemoveFromAutoClave.add(recipe);
                        break;
                    }
                }
            }
        }
        AutoClaveRecipe.removeRecipes(recipesToRemoveFromAutoClave);

        List<GTRecipe> recipesToRemoveFromFormingPress = new ArrayList<>();
        for (GTRecipe recipe : FormingPressRecipe.getAllRecipes()) {
            for (ItemStack output : recipe.mOutputs) {
                if (output != null) {
                    // 光学CPU密封外壳
                    if (output.isItemEqual(ItemList.Optical_Cpu_Containment_Housing.get(1))) {
                        recipesToRemoveFromFormingPress.add(recipe);
                        break;
                    }
                }
            }
        }
        FormingPressRecipe.removeRecipes(recipesToRemoveFromFormingPress);

        List<GTRecipe> recipesToRemoveFromBlastFurnace = new ArrayList<>();
        for (GTRecipe recipe : BlastFurnaceRecipe.getAllRecipes()) {
            for (ItemStack output : recipe.mOutputs) {
                if (output != null) {
                    // 铕锭
                    if (output.isItemEqual(GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Europium, 1))) {
                        recipesToRemoveFromBlastFurnace.add(recipe);
                        break;
                    }
                }
            }
        }
        BlastFurnaceRecipe.removeRecipes(recipesToRemoveFromBlastFurnace);

        List<GTRecipe> recipesToRemoveFromVacuumFurnace = new ArrayList<>();
        for (GTRecipe recipe : VacuumFurnaceRecipe.getAllRecipes()) {
            for (ItemStack output : recipe.mOutputs) {
                if (output != null) {
                    // 铂泡沫
                    if (output.isItemEqual(WerkstoffLoader.PTMetallicPowder.get(OrePrefixes.dust, 1))) {
                        recipesToRemoveFromVacuumFurnace.add(recipe);
                        break;
                    }
                    // 独居石泡沫
                    if (output.isItemEqual(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Europium, 1))) {
                        recipesToRemoveFromVacuumFurnace.add(recipe);
                        break;
                    }
                    // 镍黄铁泡沫
                    if (output.isItemEqual(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Promethium, 1))) {
                        recipesToRemoveFromVacuumFurnace.add(recipe);
                        break;
                    }
                }
            }
        }
        VacuumFurnaceRecipe.removeRecipes(recipesToRemoveFromVacuumFurnace);

        List<GTRecipe> recipesToRemoveFromCircuitAssembler = new ArrayList<>();
        List<ItemStack> targetOutputs = Arrays.asList(
            ItemList.Circuit_Crystalprocessor.get(1), // 晶体处理器
            ItemList.Circuit_Crystalcomputer.get(1), // 晶体处理器集群
            ItemList.Circuit_Ultimatecrystalcomputer.get(1), // 晶体处理器电脑
            ItemList.Circuit_Crystalmainframe.get(1), // 晶体处理器主机
            ItemList.Circuit_Bioprocessor.get(1), // 生物处理器
            ItemList.Circuit_Neuroprocessor.get(1), // 湿件处理器
            ItemList.Circuit_Wetwarecomputer.get(1), // 湿件处理器集群
            ItemList.Circuit_Wetwaresupercomputer.get(1) // 湿件处理器电脑
        );

        for (GTRecipe recipe : CircuitAssemblerRecipe.getAllRecipes()) {
            for (ItemStack output : recipe.mOutputs) {
                if (output == null) continue;
                for (ItemStack target : targetOutputs) {
                    if (output.isItemEqual(target)) {
                        recipesToRemoveFromCircuitAssembler.add(recipe);
                        break;
                    }
                }
            }
        }

        CircuitAssemblerRecipe.removeRecipes(recipesToRemoveFromCircuitAssembler);

        if (MainConfig.enableDebugMode) {
            removedRecipeCounts.put("高压釜", recipesToRemoveFromAutoClave.size());
            removedRecipeCounts.put("电路组装机", recipesToRemoveFromCircuitAssembler.size());
            removedRecipeCounts.put("冲压机床", recipesToRemoveFromFormingPress.size());
            removedRecipeCounts.put("真空干燥炉", recipesToRemoveFromVacuumFurnace.size());
            removedRecipeCounts.put("工业高炉", recipesToRemoveFromBlastFurnace.size());

            StringBuilder logMessage = new StringBuilder("GTNL: 移除了以下配方池中的配方：");
            for (Map.Entry<String, Integer> entry : removedRecipeCounts.entrySet()) {
                logMessage.append("\n- ")
                    .append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append("个");
            }
            System.out.println(logMessage);
        }
    }

}
