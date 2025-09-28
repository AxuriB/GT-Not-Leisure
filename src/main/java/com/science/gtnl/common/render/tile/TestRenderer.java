package com.science.gtnl.common.render.tile;

import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TestRenderer {

    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks) {
        //
        // GL11.glPushMatrix();
        // GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        // renderRing(ring, x, y, z, partialTicks, 4, 1.5f, 64, 16);
        // GL11.glPopMatrix();
        //
        // GL11.glPushMatrix();
        // GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        // GL11.glRotated(rotation, 0, 0, 1);
        // renderRing(ring, x, y, z, partialTicks, 8, 1.5f, 64, 16);
        // GL11.glPopMatrix();
        //
        // GL11.glPushMatrix();
        // GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        // GL11.glRotated(rotation, 0, 1, 0);
        // renderRing(ring, x, y, z, partialTicks, 12, 1.5f, 64, 16);
        // GL11.glPopMatrix();
        //
        // GL11.glPushMatrix();
        // GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        // GL11.glRotated(rotation, 1, 0, 0);
        // renderRing(ring, x, y, z, partialTicks, 16, 1.5f, 64, 16);
        // GL11.glPopMatrix();
        //
        // GL11.glPushMatrix();
        // GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        // renderRing(ring, x, y, z, partialTicks, 24, 1.5f, 64, 16);
        // GL11.glPopMatrix();
        //
        // GL11.glPushMatrix();
        // GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        // GL11.glRotated(rotation, 0, 0, 1);
        // renderRing(ring, x, y, z, partialTicks, 32, 2, 64, 16);
        // GL11.glPopMatrix();
        //
        // GL11.glPushMatrix();
        // GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        // GL11.glRotated(rotation, 0, 1, 0);
        // renderRing(ring, x, y, z, partialTicks, 48, 2.5f, 64, 16);
        // GL11.glPopMatrix();
        //
        // GL11.glPushMatrix();
        // GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        // GL11.glRotated(rotation, 1, 0, 0);
        // renderRing(ring, x, y, z, partialTicks, 64, 3, 64, 16);
        // GL11.glPopMatrix();

    }

    private void renderRing(TileEntity ring, double x, double y, double z, float partialTicks, float radius,
        float tubeRadius, int segments, int sides) {
        GL11.glPushMatrix();

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);

        double interpolatedRotation = 0; // ring.prevRotation + (ring.rotation - ring.prevRotation) * partialTicks;
        GL11.glRotated(interpolatedRotation, 1, 0, 0);
        GL11.glRotated(interpolatedRotation, 0, 1, 0);
        GL11.glRotated(interpolatedRotation, 0, 0, 1);

        for (int i = 0; i < segments; i++) {
            double theta1 = 2 * Math.PI * i / segments;
            double theta2 = 2 * Math.PI * (i + 1) / segments;

            for (int j = 0; j < sides; j++) {
                double phi1 = 2 * Math.PI * j / sides;
                double phi2 = 2 * Math.PI * (j + 1) / sides;

                double v = radius + tubeRadius * Math.cos(phi1);
                double x00 = v * Math.cos(theta1);
                double y00 = tubeRadius * Math.sin(phi1);
                double z00 = v * Math.sin(theta1);

                double v1 = radius + tubeRadius * Math.cos(phi2);
                double x01 = v1 * Math.cos(theta1);
                double y01 = tubeRadius * Math.sin(phi2);
                double z01 = v1 * Math.sin(theta1);

                double x10 = v * Math.cos(theta2);
                double y10 = tubeRadius * Math.sin(phi1);
                double z10 = v * Math.sin(theta2);

                double x11 = v1 * Math.cos(theta2);
                double y11 = tubeRadius * Math.sin(phi2);
                double z11 = v1 * Math.sin(theta2);

                float hue = i / (float) segments;
                float rCol = (float) Math.sin(hue * Math.PI * 2) * 0.4f + 0.6f;
                float gCol = (float) Math.sin((hue + 0.33f) * Math.PI * 2) * 0.4f + 0.6f;
                float bCol = (float) Math.sin((hue + 0.66f) * Math.PI * 2) * 0.4f + 0.6f;
                GL11.glColor4f(rCol, gCol, bCol, 0.8f);

                GL11.glBegin(GL11.GL_TRIANGLES);
                GL11.glVertex3d(x00, y00, z00);
                GL11.glVertex3d(x10, y10, z10);
                GL11.glVertex3d(x11, y11, z11);

                GL11.glVertex3d(x00, y00, z00);
                GL11.glVertex3d(x11, y11, z11);
                GL11.glVertex3d(x01, y01, z01);
                GL11.glEnd();
            }
        }

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glPopMatrix();
    }
}
