package com.science.gtnl.Utils;

import net.minecraft.block.Block;

import com.github.bsideup.jabel.Desugar;

import appeng.api.definitions.IBlockDefinition;

@Desugar
public record BlockState(Block block, int meta) {

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public BlockState(IBlockDefinition block) {
        this(
            block.maybeBlock()
                .get(),
            block.maybeStack(1)
                .get()
                .getItemDamage());
    }
}
