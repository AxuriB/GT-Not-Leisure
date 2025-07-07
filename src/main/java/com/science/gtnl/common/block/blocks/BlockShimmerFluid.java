package com.science.gtnl.common.block.blocks;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

import com.science.gtnl.common.block.BlockFluidBase;
import com.science.gtnl.common.recipe.GTNL.ShimmerRecipes;
import com.science.gtnl.loader.EffectLoader;

public class BlockShimmerFluid extends BlockFluidBase {

    private final Map<UUID, Integer> itemTimeMap = new WeakHashMap<>();

    public BlockShimmerFluid(Fluid fluid) {
        super(fluid, Material.water);
        this.setBlockName("shimmer");
        this.setQuantaPerBlock(7);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (world.isRemote) return;

        if (entity instanceof EntityLivingBase living) {
            PotionEffect effect = living.getActivePotionEffect(EffectLoader.shimmering);
            if (effect == null) {
                living.addPotionEffect(new PotionEffect(EffectLoader.shimmering.getId(), 6000, 0));
            }
        }

        if (entity instanceof EntityItem itemEntity) {
            handleItemEntity(world, itemEntity);
        }

        if (entity instanceof IProjectile) {
            if (entity.motionY < 0) {
                entity.motionY *= -0.7;
            } else {
                entity.motionY += 0.5;
            }

            entity.isAirBorne = true;
            entity.velocityChanged = true;
        }
    }

    private void handleItemEntity(World world, EntityItem itemEntity) {
        UUID uuid = itemEntity.getUniqueID();

        int time = itemTimeMap.getOrDefault(uuid, 0) + 1;

        itemTimeMap.put(uuid, time);

        if (itemEntity.getEntityData()
            .getBoolean("ShimmerConverted")) {
            if (isInFluid(world, itemEntity) && !isAboveFluid(world, itemEntity)) {
                itemEntity.motionY = 0.05;
                itemEntity.velocityChanged = true;
            }
            return;
        }

        if (time < 100) return;

        ItemStack original = itemEntity.getEntityItem();
        ItemStack result = ShimmerRecipes.getConversionResult(original);

        if (result != null) {
            itemEntity.setEntityItemStack(result);
            itemEntity.lifespan = 6000;
            itemEntity.getEntityData()
                .setBoolean("ShimmerConverted", true);
            itemTimeMap.remove(uuid);
        }
    }

    private boolean isInFluid(World world, Entity entity) {
        int x = MathHelper.floor_double(entity.posX);
        int y = MathHelper.floor_double(entity.posY);
        int z = MathHelper.floor_double(entity.posZ);
        return world.getBlock(x, y, z) == this;
    }

    private boolean isAboveFluid(World world, Entity entity) {
        int x = MathHelper.floor_double(entity.posX);
        int y = MathHelper.floor_double(entity.posY + 0.5);
        int z = MathHelper.floor_double(entity.posZ);
        return world.getBlock(x, y, z) != this;
    }
}
