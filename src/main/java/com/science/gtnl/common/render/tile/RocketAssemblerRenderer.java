package com.science.gtnl.common.render.tile;

import static com.science.gtnl.ScienceNotLeisure.*;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.RocketAssembler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Mods;

@SideOnly(Side.CLIENT)
public class RocketAssemblerRenderer {

    private static final ResourceLocation TEXTURE = new ResourceLocation(
        Mods.GalaxySpace.ID + ":" + "textures/model/tier7rocket.png");
    private static final ResourceLocation MODEL = new ResourceLocation(
        Mods.GalaxySpace.ID + ":" + "models/tier7rocket.obj");

    private static final IModelCustom model = AdvancedModelLoader.loadModel(MODEL);

    public static void renderTileEntityAt(RocketAssembler machine, double x, double y, double z, float partialTicks) {

        ForgeDirection back = machine.getExtendedFacing()
            .getRelativeBackInWorld();
        ForgeDirection up = machine.getExtendedFacing()
            .getRelativeUpInWorld();

        int xOffset = 7 * back.offsetX + -1 * up.offsetX;
        int yOffset = 7 * back.offsetY + -1 * up.offsetY;
        int zOffset = 7 * back.offsetZ + -1 * up.offsetZ;
        GL11.glPushMatrix();

        GL11.glTranslated(x + 0.5 + xOffset, y + 0.2 + yOffset, z + 0.5 + zOffset);

        Minecraft.getMinecraft()
            .getTextureManager()
            .bindTexture(TEXTURE);
        model.renderAll();

        GL11.glPopMatrix();
    }
}
