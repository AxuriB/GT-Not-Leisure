package com.science.gtnl.common.render.item;

import static net.minecraft.client.renderer.ItemRenderer.renderItemIn2D;

import java.util.Objects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemNullPointerExceptionRender implements IItemRenderer {

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();

        GL11.glColor4d(1f, 1f, 1f, 1f);

        if (type == ItemRenderType.INVENTORY) {
            GL11.glScalef(80f, 80f, 80f);
            GL11.glScalef(-1f, -1f, 1f);
            GL11.glRotatef(-45f, 0f, 0f, 1f);
            GL11.glTranslatef(-0.85f, -0.65f, 0f);
        } else if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            GL11.glScalef(10f, 10f, 10f);
            GL11.glRotatef(45f, 0f, 0f, 1f);
            GL11.glTranslatef(0.01f, -0.5f, 0f);
        } else if (type == ItemRenderType.EQUIPPED) {
            GL11.glRotatef(110f, 0f, 0f, 1f);
            GL11.glRotatef(-80f, 1f, 0f, 0f);
            GL11.glScalef(20f, 20f, 20f);
            GL11.glTranslatef(-0.08f, -0.53f, 0f);
        }

        IIcon icon = Objects.requireNonNull(item.getItem())
            .getIcon(item, 0);
        Minecraft.getMinecraft()
            .getTextureManager()
            .bindTexture(TextureMap.locationItemsTexture);

        if (icon != null) {
            renderItemIn2D(
                Tessellator.instance,
                icon.getMaxU(),
                icon.getMinV(),
                icon.getMinU(),
                icon.getMaxV(),
                icon.getIconWidth(),
                icon.getIconHeight(),
                0.03F);
        }

        GL11.glPopMatrix();
    }
}
