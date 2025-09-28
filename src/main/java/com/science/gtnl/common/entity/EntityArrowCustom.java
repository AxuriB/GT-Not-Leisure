package com.science.gtnl.common.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityArrowCustom extends EntityArrow {

    public EntityArrowCustom(World world) {
        super(world);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.inGround && this.ticksInGround >= 40) {
            this.setDead();
        }
    }

    public void generateAdvancedArrowRain(EntityPlayer player) {
        World world = this.worldObj;
        if (player == null) return;

        AxisAlignedBB area = AxisAlignedBB.getBoundingBox(
            player.posX - 50,
            player.posY - 50,
            player.posZ - 50,
            player.posX + 50,
            player.posY + 50,
            player.posZ + 50);

        List<Entity> targets = world.getEntitiesWithinAABBExcludingEntity(player, area);

        for (Entity entity : targets) {
            if (!isValidTarget(entity, player)) continue;

            final int arrowsPerTarget = 5;
            final double verticalOffset = 15.0;
            final double horizontalSpread = 3.5;

            for (int i = 0; i < arrowsPerTarget; i++) {
                EntityArrowCustom arrow = new EntityArrowCustom(world);

                double spawnX = entity.posX + (world.rand.nextDouble() - 0.5) * horizontalSpread;
                double spawnZ = entity.posZ + (world.rand.nextDouble() - 0.5) * horizontalSpread;

                arrow.setPosition(spawnX, entity.posY + verticalOffset, spawnZ);

                arrow.addVelocity(
                    (world.rand.nextDouble() - 0.5) * 0.4,
                    -2.0 + (world.rand.nextDouble() - 0.5) * 0.3,
                    (world.rand.nextDouble() - 0.5) * 0.4);

                arrow.setDamage(35.0F);
                arrow.shootingEntity = player;
                arrow.setIsCritical(true);
                arrow.canBePickedUp = 0;

                world.spawnEntityInWorld(arrow);
            }
        }
    }

    private boolean isValidTarget(Entity entity, EntityPlayer player) {
        if (entity == player || entity instanceof EntitySaddleSlime) return false;

        if (player.isSneaking()) {
            return entity instanceof EntityLivingBase;
        } else {
            return entity instanceof IMob;
        }
    }

}
