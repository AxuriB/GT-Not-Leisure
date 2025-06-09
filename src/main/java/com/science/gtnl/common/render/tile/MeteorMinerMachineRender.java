package com.science.gtnl.common.render.tile;

import static tectech.Reference.MODID;
import static tectech.rendering.EOH.EOHRenderingUtils.*;
import static tectech.rendering.EOH.EOHTileEntitySR.*;
import static tectech.rendering.EOH.EOHTileEntitySR.STAR_LAYER_2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.science.gtnl.common.machine.multiblock.MeteorMiner;

import cpw.mods.fml.client.FMLClientHandler;
import gtneioreplugin.plugin.block.ModBlocks;

public class MeteorMinerMachineRender {

    private static final ArrayList<OrbitingObject> orbitingObjects = new ArrayList<>();

    private static final Map<String, Block> blocks = new HashMap<>();

    static {
        blocks.putAll(ModBlocks.blocks);
    }

    public static void renderTileEntity(MeteorMiner meteorMiner, double x, double y, double z,
        float timeSinceLastTick) {

        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);

        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);

        renderOuterSpaceShell();
        renderOrbitObjects(meteorMiner.getRenderAngle());
        renderStar(IItemRenderer.ItemRenderType.INVENTORY, 1);

        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    public static void renderStar(IItemRenderer.ItemRenderType type, Color color, int size) {
        GL11.glPushMatrix();
        GL11.glScalef(0.05f, 0.05f, 0.05f);

        if (type == IItemRenderer.ItemRenderType.INVENTORY) GL11.glRotated(180, 0, 1, 0);
        else if (type == IItemRenderer.ItemRenderType.EQUIPPED
            || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
                GL11.glTranslated(0.5, 0.5, 0.5);
                if (type == IItemRenderer.ItemRenderType.EQUIPPED) GL11.glRotated(90, 0, 1, 0);
            }

        renderStarLayer(0, STAR_LAYER_0, color, 1.0f, size);
        renderStarLayer(1, STAR_LAYER_1, color, 0.4f, size);
        renderStarLayer(2, STAR_LAYER_2, color, 0.2f, size);

        GL11.glPopMatrix();
    }

    public static void renderStar(IItemRenderer.ItemRenderType type, int size) {
        renderStar(type, new Color(1.0f, 0.4f, 0.05f, 1.0f), size);
    }

    public static void renderOuterSpaceShell() {
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);

        GL11.glPushMatrix();
        GL11.glScalef(0.05f, 0.05f, 0.05f);

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        FMLClientHandler.instance()
            .getClient()
            .getTextureManager()
            .bindTexture(new ResourceLocation(MODID, "models/spaceLayer.png"));

        final float scale = 0.01f * 17.5f;
        GL11.glScalef(scale, scale, scale);

        GL11.glColor4f(1, 1, 1, 1);

        spaceModel.renderAll();
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }

    public static ArrayList<OrbitingObject> getOrbitingObjects() {
        return orbitingObjects;
    }

    public static void generateImportantInfo() {
        int index = 0;
        for (Block block : selectNRandomElements(blocks.values(), 10)) {
            float MAX_ANGLE = 30;
            float xAngle = generateRandomFloat(-MAX_ANGLE, MAX_ANGLE);
            float zAngle = generateRandomFloat(-MAX_ANGLE, MAX_ANGLE);
            index += 1;
            float distance = index + generateRandomFloat(-0.2f, 0.2f);
            float scale = generateRandomFloat(0.2f, 0.9f);
            float rotationSpeed = generateRandomFloat(0.5f, 1.5f);
            float orbitSpeed = generateRandomFloat(0.5f, 1.5f);
            orbitingObjects.add(new OrbitingObject(block, distance, rotationSpeed, orbitSpeed, xAngle, zAngle, scale));
        }
    }

    private static void renderOrbitObjects(float angle) {
        if (getOrbitingObjects() != null) {
            if (getOrbitingObjects().isEmpty()) {
                generateImportantInfo();
            }
            for (OrbitingObject t : getOrbitingObjects()) {
                renderOrbit(t, angle);
            }
        }
    }

    private static void renderOrbit(final OrbitingObject orbitingObject, float angle) {
        GL11.glPushMatrix();
        GL11.glScalef(0.05f, 0.05f, 0.05f);

        GL11.glRotatef(orbitingObject.zAngle, 0, 0, 1);
        GL11.glRotatef(orbitingObject.xAngle, 1, 0, 0);
        GL11.glRotatef((orbitingObject.rotationSpeed * 1.5f * angle) % 360.0f, 0F, 1F, 0F);
        float STAR_RESCALE = 0.2f;
        GL11.glTranslated(-0.2 - orbitingObject.distance - STAR_RESCALE * 10, 0, 0);
        GL11.glRotatef((orbitingObject.orbitSpeed * 1.5f * angle) % 360.0f, 0F, 1F, 0F);

        FMLClientHandler.instance()
            .getClient()
            .getTextureManager()
            .bindTexture(TextureMap.locationBlocksTexture);
        renderBlockInWorld(orbitingObject.block, 0, orbitingObject.scale);

        GL11.glPopMatrix();
    }

    public static <T> ArrayList<T> selectNRandomElements(Collection<T> inputList, long n) {
        ArrayList<T> randomElements = new ArrayList<>((int) n);
        ArrayList<T> inputArray = new ArrayList<>(inputList);
        Random rand = new Random();
        IntStream.range(0, (int) n)
            .forEach(i -> {
                int randomIndex = rand.nextInt(inputArray.size());
                randomElements.add(inputArray.get(randomIndex));
                inputArray.remove(randomIndex);
            });
        return randomElements;
    }

    public static float generateRandomFloat(float a, float b) {
        Random rand = new Random();
        return rand.nextFloat() * (b - a) + a;
    }

    public static class OrbitingObject {

        public final Block block;
        public final float distance;
        public final float rotationSpeed;
        public final float orbitSpeed;
        public final float xAngle;
        public final float zAngle;
        public final float scale;

        public OrbitingObject(Block block, float distance, float rotationSpeed, float orbitSpeed, float xAngle,
            float zAngle, float scale) {
            this.block = block;
            this.distance = distance;
            this.rotationSpeed = rotationSpeed;
            this.orbitSpeed = orbitSpeed;
            this.xAngle = xAngle;
            this.zAngle = zAngle;
            this.scale = scale;
        }
    }
}
