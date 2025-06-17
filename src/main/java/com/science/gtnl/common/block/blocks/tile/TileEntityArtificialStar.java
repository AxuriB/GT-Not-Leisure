package com.science.gtnl.common.block.blocks.tile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

import com.science.gtnl.common.render.tile.RealArtificialStarRender;

import lombok.Getter;

public class TileEntityArtificialStar extends TileEntity {

    // 当前的旋转角度
    public double Rotation = 0;
    // 当前的大小
    public double size = 0;
    // 目标大小
    public double targetSize = 12;
    // 初始大小
    public double initialSize = 0;
    // 初始旋转速度
    public double initialRotationSpeed = 0;
    // 目标旋转速度
    public double targetRotationSpeed = 0.5;
    // 记录已更新的tick
    public int ticks = 0;
    // 变大的时间（以tick为单位）
    public int duration = 100;
    // 当前放大到第几个模型
    public int currentModelIndex = 0;
    // 模型列表
    @Getter
    public final List<IModelCustom> models = new ArrayList<>();
    // 纹理列表
    public final List<ResourceLocation> textures = new ArrayList<>();

    public TileEntityArtificialStar() {
        models.add(RealArtificialStarRender.STAR_MODEL);
        textures.add(RealArtificialStarRender.STAR_TEXTURE);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        return 65536;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setDouble("size", size);
        nbt.setDouble("Rotation", Rotation);
        nbt.setInteger("currentModelIndex", currentModelIndex);
        nbt.setInteger("modelsCount", models.size());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        size = nbt.getDouble("size");
        Rotation = nbt.getDouble("Rotation");
        currentModelIndex = nbt.getInteger("currentModelIndex");
        int modelsCount = nbt.getInteger("modelsCount");
        models.clear();
        textures.clear();
        for (int i = 0; i < modelsCount; i++) {
            models.add(RealArtificialStarRender.STAR_MODEL);
            textures.add(RealArtificialStarRender.STAR_TEXTURE);
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (currentModelIndex < models.size()) {
            if (ticks < duration) {
                double t = (double) ticks / duration;
                double easedT = cubicEaseOut(t);
                size = initialSize + (targetSize - initialSize) * easedT;
                Rotation += initialRotationSpeed + (targetRotationSpeed - initialRotationSpeed) * easedT;
                ticks++;
            } else {
                size = targetSize;
                Rotation = (Rotation + targetRotationSpeed) % 360d;
                currentModelIndex++;
                ticks = 0;
            }
        } else {
            Rotation = (Rotation + targetRotationSpeed) % 360d;
        }
    }

    public ResourceLocation getTexture(int index) {
        return textures.get(index);
    }

    public double cubicEaseOut(double t) {
        return 1 - Math.pow(1 - t, 3);
    }
}
