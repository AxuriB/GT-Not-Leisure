package com.science.gtnl.common.block.Casings.Casing;

import net.minecraft.item.ItemStack;

import com.science.gtnl.Utils.item.MetaItemStackUtils;

import gregtech.api.enums.Textures;
import gregtech.api.render.TextureFactory;

public final class MetaBlockConstructors {

    public static ItemStack initMetaBlock(int meta, MetaCasingBase basicBlock) {
        return MetaItemStackUtils.initMetaItemStack(meta, basicBlock, basicBlock.getUsedMetaSet());
    }

    public static ItemStack initMetaBlock(int meta, MetaCasingBase basicBlock, String[] tooltips) {
        basicBlock.getTooltipsMap()
            .put(meta, tooltips);
        return MetaItemStackUtils.initMetaItemStack(meta, basicBlock, basicBlock.getUsedMetaSet());
    }

    public static void setCasingTextureForMetaBlock(int meta, MetaBlockCasingBase basicBlock) {
        Textures.BlockIcons
            .setCasingTextureForId(basicBlock.getTextureIndex(meta), TextureFactory.of(basicBlock, meta));
    }

    public static ItemStack initMetaBlockCasing(int meta, MetaBlockCasingBase basicBlock) {
        setCasingTextureForMetaBlock(meta, basicBlock);
        return initMetaBlock(meta, basicBlock);
    }

    public static ItemStack initMetaBlockCasing(int meta, MetaBlockCasingBase basicBlock, String[] tooltips) {
        setCasingTextureForMetaBlock(meta, basicBlock);
        return initMetaBlock(meta, basicBlock, tooltips);
    }

}
