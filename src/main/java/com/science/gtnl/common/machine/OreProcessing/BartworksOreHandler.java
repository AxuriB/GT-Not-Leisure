package com.science.gtnl.common.machine.OreProcessing;

import static com.science.gtnl.Utils.Utils.setStackSize;
import static gregtech.api.enums.OrePrefixes.*;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import com.science.gtnl.loader.RecipePool;

import bartworks.system.material.Werkstoff;
import gregtech.api.enums.GTValues;

public class BartworksOreHandler {

    public void processBWOreRecipes() {
        for (Werkstoff werkstoff : Werkstoff.werkstoffHashSet) {
            if (!werkstoff.hasItemType(ore)) continue;
            ArrayList<ItemStack> outputs = new ArrayList<>();
            // basic output
            outputs.add(werkstoff.get(dust, Integer.MAX_VALUE));

            // gem output
            if (werkstoff.hasItemType(gem)) {
                if (werkstoff.hasItemType(gemExquisite)) {
                    outputs.add(werkstoff.get(gemExquisite, Integer.MAX_VALUE));
                    outputs.add(werkstoff.get(gemFlawless, Integer.MAX_VALUE));
                    outputs.add(werkstoff.get(gem, Integer.MAX_VALUE));
                } else {
                    outputs.add(werkstoff.get(gem, Integer.MAX_VALUE));
                }
            }

            // byproducts
            if (werkstoff.getNoOfByProducts() >= 1) {
                if (werkstoff.getNoOfByProducts() == 1) {
                    outputs.add(setStackSize(werkstoff.getOreByProduct(0, dust), Integer.MAX_VALUE));
                } else {
                    for (int i = 0; i < werkstoff.getNoOfByProducts(); i++) {
                        outputs.add(setStackSize(werkstoff.getOreByProduct(i, dust), Integer.MAX_VALUE));
                    }
                }
            } else {
                outputs.add(werkstoff.get(dust, Integer.MAX_VALUE));
            }

            if (werkstoff.hasItemType(rawOre)) {
                ArrayList<ItemStack> rawOreOutputs = new ArrayList<>();
                rawOreOutputs.add(werkstoff.get(dust, Integer.MAX_VALUE));

                if (werkstoff.hasItemType(gem)) {
                    if (werkstoff.hasItemType(gemExquisite)) {
                        rawOreOutputs.add(werkstoff.get(gemExquisite, Integer.MAX_VALUE));
                        rawOreOutputs.add(werkstoff.get(gemFlawless, Integer.MAX_VALUE));
                        rawOreOutputs.add(werkstoff.get(gem, Integer.MAX_VALUE));
                    } else {
                        rawOreOutputs.add(werkstoff.get(gem, Integer.MAX_VALUE));
                    }
                }

                if (werkstoff.getNoOfByProducts() >= 1) {
                    if (werkstoff.getNoOfByProducts() == 1) {
                        rawOreOutputs.add(setStackSize(werkstoff.getOreByProduct(0, dust), Integer.MAX_VALUE));
                    } else {
                        for (int i = 0; i < werkstoff.getNoOfByProducts(); i++) {
                            rawOreOutputs.add(setStackSize(werkstoff.getOreByProduct(i, dust), Integer.MAX_VALUE));
                        }
                    }
                } else {
                    rawOreOutputs.add(werkstoff.get(dust, Integer.MAX_VALUE));
                }

                GTValues.RA.stdBuilder()
                    .itemInputs(werkstoff.get(rawOre, 1))
                    .itemOutputs(rawOreOutputs.toArray(new ItemStack[] {}))
                    .eut(0)
                    .duration(1)
                    .addTo(RecipePool.CheatOreProcessingRecipes);
            }

            GTValues.RA.stdBuilder()
                .itemInputs(werkstoff.get(ore, 1))
                .itemOutputs(outputs.toArray(new ItemStack[] {}))
                .eut(0)
                .duration(1)
                .addTo(RecipePool.CheatOreProcessingRecipes);
        }
    }
}
