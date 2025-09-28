package com.science.gtnl.mixins.late.Bartwork;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import bartworks.common.blocks.BWBlocks;

@Mixin(value = BWBlocks.class, remap = false)
public interface AccessorBWBlocks {

    @Accessor("textureNames")
    String[] getTextureNames();

}
