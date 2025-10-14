package com.science.gtnl.common.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class WaterCandleModel extends ModelBase {

    private final ModelRenderer bone;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r4;
    private final ModelRenderer cube_r5;
    private final ModelRenderer cube_r6;
    private final ModelRenderer cube_r7;
    private final ModelRenderer cube_r8;

    public WaterCandleModel() {
        textureWidth = 16;
        textureHeight = 16;

        bone = new ModelRenderer(this);
        bone.setRotationPoint(8.0F, 0.0F, -8.0F);
        bone.cubeList.add(new ModelBox(bone, 0, 8, -8.0F, 0.0F, 8.0F, 2, 3, 2, 0.0F));
        bone.cubeList.add(new ModelBox(bone, 0, 8, -11.0F, 0.0F, 8.0F, 2, 5, 2, 0.0F));
        bone.cubeList.add(new ModelBox(bone, 0, 8, -7.0F, 0.0F, 5.0F, 2, 5, 2, 0.0F));
        bone.cubeList.add(new ModelBox(bone, 0, 8, -10.0F, 0.0F, 5.0F, 2, 6, 2, 0.0F));

        cube_r1 = new ModelRenderer(this);
        cube_r1.setRotationPoint(-9.0F, 6.0F, 6.0F);
        bone.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, 0.7854F, 0.0F);
        cube_r1.cubeList.add(new ModelBox(cube_r1, 0, 0, -0.5F, 0.0F, 0.0F, 1, 1, 0, 0.0F));

        cube_r2 = new ModelRenderer(this);
        cube_r2.setRotationPoint(-9.0F, 6.0F, 6.0F);
        bone.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, -0.7854F, 0.0F);
        cube_r2.cubeList.add(new ModelBox(cube_r2, 0, 0, -0.5F, 0.0F, 0.0F, 1, 1, 0, 0.0F));

        cube_r3 = new ModelRenderer(this);
        cube_r3.setRotationPoint(-6.0F, 5.0F, 6.0F);
        bone.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.0F, 0.7854F, 0.0F);
        cube_r3.cubeList.add(new ModelBox(cube_r3, 0, 0, -0.5F, 0.0F, 0.0F, 1, 1, 0, 0.0F));

        cube_r4 = new ModelRenderer(this);
        cube_r4.setRotationPoint(-6.0F, 5.0F, 6.0F);
        bone.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.0F, -0.7854F, 0.0F);
        cube_r4.cubeList.add(new ModelBox(cube_r4, 0, 0, -0.5F, 0.0F, 0.0F, 1, 1, 0, 0.0F));

        cube_r5 = new ModelRenderer(this);
        cube_r5.setRotationPoint(-10.0F, 5.0F, 9.0F);
        bone.addChild(cube_r5);
        setRotationAngle(cube_r5, 0.0F, 0.7854F, 0.0F);
        cube_r5.cubeList.add(new ModelBox(cube_r5, 0, 0, -0.5F, 0.0F, 0.0F, 1, 1, 0, 0.0F));

        cube_r6 = new ModelRenderer(this);
        cube_r6.setRotationPoint(-10.0F, 5.0F, 9.0F);
        bone.addChild(cube_r6);
        setRotationAngle(cube_r6, 0.0F, -0.7854F, 0.0F);
        cube_r6.cubeList.add(new ModelBox(cube_r6, 0, 0, -0.5F, 0.0F, 0.0F, 1, 1, 0, 0.0F));

        cube_r7 = new ModelRenderer(this);
        cube_r7.setRotationPoint(-7.0F, 3.0F, 9.0F);
        bone.addChild(cube_r7);
        setRotationAngle(cube_r7, 0.0F, 0.7854F, 0.0F);
        cube_r7.cubeList.add(new ModelBox(cube_r7, 0, 0, -0.5F, 0.0F, 0.0F, 1, 1, 0, 0.0F));

        cube_r8 = new ModelRenderer(this);
        cube_r8.setRotationPoint(-7.0F, 3.0F, 9.0F);
        bone.addChild(cube_r8);
        setRotationAngle(cube_r8, 0.0F, -0.7854F, 0.0F);
        cube_r8.cubeList.add(new ModelBox(cube_r8, 0, 0, -0.5F, 0.0F, 0.0F, 1, 1, 0, 0.0F));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        bone.render(f5);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
