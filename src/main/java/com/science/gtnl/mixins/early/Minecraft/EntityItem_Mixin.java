package com.science.gtnl.mixins.early.Minecraft;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.common.block.blocks.BlockShimmerFluid;

@Mixin(EntityItem.class)
public abstract class EntityItem_Mixin {

    @Inject(method = "onUpdate", at = @At("TAIL"))
    private void onUpdate_ShimmerLift(CallbackInfo ci) {
        EntityItem item = (EntityItem) (Object) this;
        NBTTagCompound tag = item.getEntityData();

        if (!tag.getBoolean("ShimmerConverted")) return;

        World world = item.worldObj;
        int x = MathHelper.floor_double(item.posX);
        int y = MathHelper.floor_double(item.posY);
        int z = MathHelper.floor_double(item.posZ);

        boolean isInShimmer = false;

        if (world.blockExists(x, y, z)) {
            Block blockAtY = world.getBlock(x, y, z);
            if (blockAtY instanceof BlockShimmerFluid) {
                isInShimmer = true;
            }
        }

        if (!isInShimmer && world.blockExists(x, y - 1, z)) {
            Block blockBelow = world.getBlock(x, y - 1, z);
            if (blockBelow instanceof BlockShimmerFluid) {
                isInShimmer = true;
                y = y - 1;
            }
        }

        if (!isInShimmer) return;

        double fluidTopY = y + 1.0;
        double targetY = fluidTopY + 0.95;
        double dy = targetY - item.posY;

        if (dy > 0.05) {
            item.motionY = Math.min(0.07, dy * 0.15);
            item.motionX = 0;
            item.motionZ = 0;
            item.velocityChanged = true;
            item.fallDistance = 0f;
            item.onGround = false;
        } else {
            item.motionY = 0;
            item.motionX = 0;
            item.motionZ = 0;
            item.velocityChanged = true;
        }
    }
}
