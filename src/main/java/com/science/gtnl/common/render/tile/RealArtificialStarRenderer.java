package com.science.gtnl.common.render.tile;

import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;

import java.util.List;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import com.science.gtnl.common.block.blocks.tile.TileEntityArtificialStar;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RealArtificialStarRenderer extends TileEntitySpecialRenderer {

    public static final ResourceLocation STAR_TEXTURE = new ResourceLocation(
        RESOURCE_ROOT_ID + ":" + "model/ArtificialStarRender.png");
    public static final IModelCustom STAR_MODEL = AdvancedModelLoader
        .loadModel(new ResourceLocation(RESOURCE_ROOT_ID + ":" + "model/ArtificialStarRender.obj"));

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float timeSinceLastTick) {
        if (!(tile instanceof TileEntityArtificialStar star)) return;
        final double size = star.size;
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        GL11.glRotated(star.Rotation, 1, 1, 1);
        renderStar(star, size);
        GL11.glPopMatrix();
    }

    public void renderStar(TileEntityArtificialStar tileEntityArtificialStar, double size) {
        List<IModelCustom> models = tileEntityArtificialStar.getModels();
        for (int i = 0; i < models.size(); i++) {
            IModelCustom model = models.get(i);
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            this.bindTexture(tileEntityArtificialStar.getTexture(i));
            GL11.glScaled(size, size, size);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
            model.renderAll();
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
    }
}
