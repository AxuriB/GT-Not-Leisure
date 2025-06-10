package com.science.gtnl.common.block.Casings.Casing;

import net.minecraft.block.Block;

import com.science.gtnl.api.IMetaBlock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetaItemBlockCasing extends MetaItemBlockBase {

    public MetaItemBlockCasing(Block block) {
        super(block);
    }

    @Override
    public boolean canCreatureSpawn() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String[] getTooltips(int meta) {
        if (getThisBlock() instanceof IMetaBlock thisBlock) {
            return thisBlock.getTooltips(meta);
        }
        return new String[0];
    }

}
