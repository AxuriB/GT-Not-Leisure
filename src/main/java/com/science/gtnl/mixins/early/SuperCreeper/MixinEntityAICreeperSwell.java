package com.science.gtnl.mixins.early.SuperCreeper;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.science.gtnl.config.MainConfig;
import com.science.gtnl.utils.Utils;

@Mixin(EntityAICreeperSwell.class)
public abstract class MixinEntityAICreeperSwell {

    @Shadow
    EntityCreeper swellingCreeper;

    @Unique
    private EntitySpider targetSpider;
    @Unique
    private final int spiderTargetInterval = MainConfig.spiderTargetInterval;
    @Unique
    private long lastSpiderTargetUpdateTick = 0;
    @Unique
    private final int spiderFindRadius = MainConfig.spiderFindRadius;

    @Unique
    private Utils.TargetInfo cachedBlockTarget = null;
    @Unique
    private Utils.TargetInfo cachedPlayerTarget = null;

    @Unique
    private long lastBlockTargetUpdateTick = 0;
    @Unique
    private long lastPlayerTargetUpdateTick = 0;

    @Unique
    private final int blockTargetInterval = MainConfig.blockTargetInterval;
    @Unique
    private final int playerTargetInterval = MainConfig.playerTargetInterval;
    @Unique
    private final int blockFindRadius = MainConfig.blockFindRadius;
    @Unique
    private final int playerFindRadius = MainConfig.playerFindRadius;
    @Unique
    private final int explosionPower = MainConfig.explosionPower;
    @Unique
    private final double moveSpeed = MainConfig.moveSpeed;
    @Unique
    private final double explosionTriggerRange = MainConfig.explosionTriggerRange;

    @Unique
    public Utils.TargetInfo findNearestTarget() {
        long currentTick = this.swellingCreeper.worldObj.getTotalWorldTime();

        if (currentTick - lastBlockTargetUpdateTick >= blockTargetInterval) {
            cachedBlockTarget = findNearestTargetBlock();
            lastBlockTargetUpdateTick = currentTick;
        }

        if (currentTick - lastPlayerTargetUpdateTick >= playerTargetInterval) {
            cachedPlayerTarget = findNearestTargetPlayer();
            lastPlayerTargetUpdateTick = currentTick;
        }

        if (cachedBlockTarget == null) return cachedPlayerTarget;
        if (cachedPlayerTarget == null) return cachedBlockTarget;

        return cachedBlockTarget.distance < cachedPlayerTarget.distance ? cachedBlockTarget : cachedPlayerTarget;
    }

    @Unique
    private Utils.TargetInfo findNearestTargetBlock() {
        double minDistance = Double.MAX_VALUE;
        Utils.TargetInfo closestTarget = null;

        int radius = blockFindRadius;

        for (int x = MathHelper.floor_double(this.swellingCreeper.posX) - radius; x
            <= MathHelper.floor_double(this.swellingCreeper.posX) + radius; ++x) {
            for (int y = MathHelper.floor_double(this.swellingCreeper.posY) - radius; y
                <= MathHelper.floor_double(this.swellingCreeper.posY) + radius; ++y) {
                for (int z = MathHelper.floor_double(this.swellingCreeper.posZ) - radius; z
                    <= MathHelper.floor_double(this.swellingCreeper.posZ) + radius; ++z) {
                    Block block = this.swellingCreeper.worldObj.getBlock(x, y, z);
                    int meta = this.swellingCreeper.worldObj.getBlockMetadata(x, y, z);
                    if (Utils.isTargetBlock(block, meta)) {
                        double dist = this.swellingCreeper.getDistance(x + 0.5, y + 0.5, z + 0.5);
                        if (dist < minDistance) {
                            minDistance = dist;
                            closestTarget = new Utils.TargetInfo(x, y, z, dist);
                        }
                    }
                }
            }
        }

        return closestTarget;
    }

    @Unique
    private Utils.TargetInfo findNearestTargetPlayer() {
        double minDistance = Double.MAX_VALUE;
        Utils.TargetInfo closestTarget = null;

        int radius = playerFindRadius;

        List<EntityPlayer> players = this.swellingCreeper.worldObj
            .getEntitiesWithinAABB(EntityPlayer.class, this.swellingCreeper.boundingBox.expand(radius, radius, radius));

        for (EntityPlayer player : players) {
            if (!player.isEntityAlive() || player.capabilities.isCreativeMode) continue;

            double dist = this.swellingCreeper.getDistanceToEntity(player);
            if (dist < minDistance) {
                minDistance = dist;
                closestTarget = new Utils.TargetInfo(player, dist);
            }
        }

        return closestTarget;
    }

    @Unique
    public boolean executeSpider() {
        long currentTick = this.swellingCreeper.worldObj.getTotalWorldTime();

        if (currentTick - lastSpiderTargetUpdateTick >= spiderTargetInterval) {
            lastSpiderTargetUpdateTick = currentTick;

            if (this.swellingCreeper.ridingEntity != null || this.swellingCreeper.isRiding()) {
                return false;
            }

            List<EntitySpider> nearbySpiders = this.swellingCreeper.worldObj.getEntitiesWithinAABB(
                EntitySpider.class,
                this.swellingCreeper.boundingBox.expand(spiderFindRadius, spiderFindRadius, spiderFindRadius));

            EntitySpider closestSpider = null;
            double closestDistance = spiderFindRadius;

            for (EntitySpider spider : nearbySpiders) {
                if (spider.isEntityAlive() && spider.riddenByEntity == null) {
                    double dist = this.swellingCreeper.getDistanceToEntity(spider);
                    if (dist < closestDistance) {
                        closestDistance = dist;
                        closestSpider = spider;
                    }
                }
            }

            if (closestSpider != null) {
                this.targetSpider = closestSpider;
                return true;
            }
        }

        return false;
    }

    @Inject(method = "shouldExecute", at = @At("HEAD"), cancellable = true)
    public void shouldExecute(CallbackInfoReturnable<Boolean> cir) {
        Utils.TargetInfo customTarget = findNearestTarget();
        if (MainConfig.enableCreeperFindSpider) executeSpider();
        double customTargetDistance = 0;
        double spiderDistance = 0;

        if (customTarget != null) {
            customTargetDistance = customTarget.distance;
        }
        if (this.targetSpider != null) {
            spiderDistance = this.swellingCreeper.getDistanceToEntity(this.targetSpider);
        }

        if (this.swellingCreeper.getCreeperState() < 0) {
            if (spiderDistance < spiderFindRadius || customTargetDistance < blockFindRadius) {
                cir.setReturnValue(true);
            }
        } else {
            cir.setReturnValue(false);
        }
        cir.cancel();
    }

    @Inject(method = "resetTask", at = @At("HEAD"), cancellable = true)
    public void resetTask(CallbackInfo ci) {
        this.targetSpider = null;
    }

    @Inject(method = "updateTask", at = @At("HEAD"), cancellable = true)
    public void updateTask(CallbackInfo ci) {
        Utils.TargetInfo customTarget = findNearestTarget();
        double customTargetDistance = Double.MAX_VALUE;
        double spiderDistance = 0;

        if (customTarget != null) {
            customTargetDistance = customTarget.distance;
        }
        if (this.targetSpider != null) {
            spiderDistance = this.swellingCreeper.getDistanceToEntity(this.targetSpider);
        }

        if (MainConfig.enableCreeperFindSpider && this.targetSpider != null
            && spiderDistance < customTargetDistance
            && this.swellingCreeper.ridingEntity == null
            && !this.swellingCreeper.isRiding()) {
            if (spiderDistance < 2.0D) {
                this.swellingCreeper.mountEntity(this.targetSpider);
            }
            this.swellingCreeper.getNavigator()
                .tryMoveToEntityLiving(this.targetSpider, 1.0D);
            return;
        } else {
            this.targetSpider = null;
        }

        if (customTarget == null) {
            this.swellingCreeper.setCreeperState(-1);
        } else {
            double targetX = customTarget.x + 0.5;
            double targetY = customTarget.y + 0.5;
            double targetZ = customTarget.z + 0.5;

            if (customTargetDistance <= explosionTriggerRange) {
                if (this.swellingCreeper.getCreeperState() < 1) {
                    this.swellingCreeper.setCreeperState(1);
                    ((AccessorEntityCreeper) this.swellingCreeper).setExplosionRadius(explosionPower);
                }
                this.swellingCreeper.getNavigator()
                    .clearPathEntity();
            } else {
                if (swellingCreeper.ridingEntity != null
                    && swellingCreeper.ridingEntity instanceof EntityLiving entityLiving) {
                    entityLiving.getNavigator()
                        .tryMoveToXYZ(
                            targetX,
                            targetY,
                            targetZ,
                            Math.max(2, moveSpeed + (MainConfig.creeperSpeedBonusScale / customTarget.distance)));
                } else {
                    this.swellingCreeper.getNavigator()
                        .tryMoveToXYZ(
                            targetX,
                            targetY,
                            targetZ,
                            Math.max(2, moveSpeed + (MainConfig.creeperSpeedBonusScale / customTarget.distance)));
                }

                boolean canSeeTarget = true;
                if (customTarget.isEntityTarget() && customTarget.entityTarget != null) {
                    canSeeTarget = this.swellingCreeper.getEntitySenses()
                        .canSee(customTarget.entityTarget);
                }

                if (!canSeeTarget) {
                    this.swellingCreeper.setCreeperState(-1);
                }
            }
        }
        ci.cancel();
    }
}
