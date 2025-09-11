package com.science.gtnl.common.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import com.science.gtnl.common.item.items.Stick;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemStickRenderer implements IItemRenderer {

    private final RenderItem renderItem = new RenderItem();

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
        if (stack == null || !(stack.getItem() instanceof Stick stick)) {
            return;
        }

        ItemStack disguised = stick.getDisguisedStack(stack);
        if (disguised == null) {
            return;
        }

        TextureManager textureManager = Minecraft.getMinecraft()
            .getTextureManager();
        renderItem.renderItemIntoGUI(Minecraft.getMinecraft().fontRenderer, textureManager, disguised, 0, 0);
    }
}
