package com.science.gtnl.Utils.gui.portableWorkbench;

import javax.annotation.Nonnull;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import wanion.avaritiaddons.common.Reference;

public abstract class GuiPortableAvaritiaddonsChest extends GuiContainer {

    private final static ResourceLocation avaritiaddonsChestGui = new ResourceLocation(
        Reference.MOD_ID,
        "textures/gui/avaritiaddonsChest.png");

    public GuiPortableAvaritiaddonsChest(@Nonnull final Container container) {
        super(container);
        xSize = 500;
        ySize = 276;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(final float par1, final int par2, final int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager()
            .bindTexture(avaritiaddonsChestGui);
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(guiLeft, guiTop, 0, 0.0, 0.0);
        tessellator.addVertexWithUV(guiLeft, guiTop + xSize, 0, 0.0, 1.0);
        tessellator.addVertexWithUV(guiLeft + xSize, guiTop + xSize, 0, 1.0, 1.0);
        tessellator.addVertexWithUV(guiLeft + xSize, guiTop, 0, 1.0, 0.0);
        tessellator.draw();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(final int p_146979_1_, final int p_146979_2_) {
        fontRendererObj.drawString(StatCollector.translateToLocal(getContainerName()), 7, 7, 0x404040);
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 169, 183, 0x404040);
    }

    @Nonnull
    protected abstract String getContainerName();
}
