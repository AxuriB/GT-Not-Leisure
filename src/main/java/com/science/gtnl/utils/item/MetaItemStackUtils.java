package com.science.gtnl.utils.item;

import java.util.Map;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import com.science.gtnl.ScienceNotLeisure;

public class MetaItemStackUtils {

    // generate item stack when registry
    public static ItemStack initMetaItemStack(int Meta, Item basicItem, Set<Integer> aContainerSet) {

        // Handle the Name
        StatCollector.translateToLocal(basicItem.getUnlocalizedName() + "." + Meta + ".name");
        // Hold the list of Meta-generated Items
        aContainerSet.add(Meta);

        return new ItemStack(basicItem, 1, Meta);
    }

    // generate itemBlock stack when registry
    public static ItemStack initMetaItemStack(int Meta, Block baseBlock, Set<Integer> aContainerSet) {
        StatCollector.translateToLocal(baseBlock.getUnlocalizedName() + "." + Meta + ".name");
        aContainerSet.add(Meta);
        return new ItemStack(baseBlock, 1, Meta);
    }

    public static void metaItemStackTooltipsAdd(Map<Integer, String[]> tooltipsMap, int meta, String[] tooltips) {
        if (tooltipsMap.containsKey(meta)) {
            ScienceNotLeisure.LOG.info("failed to Replace a tooltips:{} ...", tooltips[0]);
            return;
        }
        tooltipsMap.put(meta, tooltips);
    }

}
