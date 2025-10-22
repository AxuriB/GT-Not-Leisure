package com.science.gtnl.utils;

import java.util.Objects;

import net.minecraft.block.Block;

import com.github.bsideup.jabel.Desugar;

import appeng.api.definitions.IBlockDefinition;

@Desugar
public record BlockState(Block block, int meta) {

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BlockState that = (BlockState) o;
        return meta == that.meta && Objects.equals(block, that.block);
    }

    @Override
    public int hashCode() {
        return Objects.hash(block, meta);
    }

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
