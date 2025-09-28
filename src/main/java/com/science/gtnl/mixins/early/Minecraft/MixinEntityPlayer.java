package com.science.gtnl.mixins.early.Minecraft;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.api.mixinHelper.ILeashedToEntity;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase implements ILeashedToEntity {

    @Unique
    private boolean isLeashed;

    @Unique
    private Entity leashedToEntity;

    @Unique
    private NBTTagCompound nbtTagCompound;

    public MixinEntityPlayer(World p_i1594_1_) {
        super(p_i1594_1_);
    }

    @Inject(method = "onUpdate", at = @At("TAIL"))
    public void mixin$onUpdate(CallbackInfo ci) {
        if (!this.worldObj.isRemote) {
            this.updateLeashedState();
        }
    }

    @Override
    public void setLeashedToEntity(Entity entityIn, boolean sendAttachNotification) {
        this.isLeashed = true;
        this.leashedToEntity = entityIn;

        if (!this.worldObj.isRemote && sendAttachNotification && this.worldObj instanceof WorldServer) {
            ((WorldServer) this.worldObj).getEntityTracker()
                .func_151247_a(this, new S1BPacketEntityAttach(1, this, this.leashedToEntity));
        }
    }

    @Override
    public boolean getLeashed() {
        return this.isLeashed;
    }

    @Override
    public Entity getLeashedToEntity() {
        return this.leashedToEntity;
    }

    /**
     * Applies logic related to leashes, for example dragging the entity or breaking the leash.
     */
    @Override
    public void updateLeashedState() {
        if (this.nbtTagCompound != null) {
            this.recreateLeash();
        }

        if (this.isLeashed) {
            if (this.leashedToEntity == null || this.leashedToEntity.isDead) {
                this.clearLeashed(true, false);
            }
        }
    }

    /**
     * Removes the leash from this entity. Second parameter tells whether to send a packet to surrounding players.
     */
    @Override
    public void clearLeashed(boolean p_110160_1_, boolean dropItem) {
        if (this.isLeashed) {
            this.isLeashed = false;
            this.leashedToEntity = null;

            if (!this.worldObj.isRemote && dropItem) {
                this.dropItem(Items.lead, 1);
            }

            if (!this.worldObj.isRemote && p_110160_1_ && this.worldObj instanceof WorldServer) {
                ((WorldServer) this.worldObj).getEntityTracker()
                    .func_151247_a(this, new S1BPacketEntityAttach(1, this, (Entity) null));
            }
        }
    }

    @Unique
    public void recreateLeash() {
        if (this.isLeashed && this.nbtTagCompound != null) {
            if (this.nbtTagCompound.hasKey("UUIDMost", 4) && this.nbtTagCompound.hasKey("UUIDLeast", 4)) {
                UUID uuid = new UUID(this.nbtTagCompound.getLong("UUIDMost"), this.nbtTagCompound.getLong("UUIDLeast"));
                List list = this.worldObj
                    .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(10.0D, 10.0D, 10.0D));
                Iterator iterator = list.iterator();

                while (iterator.hasNext()) {
                    EntityLivingBase entitylivingbase = (EntityLivingBase) iterator.next();

                    if (entitylivingbase.getUniqueID()
                        .equals(uuid)) {
                        this.leashedToEntity = entitylivingbase;
                        break;
                    }
                }
            } else if (this.nbtTagCompound.hasKey("X", 99) && this.nbtTagCompound.hasKey("Y", 99)
                && this.nbtTagCompound.hasKey("Z", 99)) {
                    int i = this.nbtTagCompound.getInteger("X");
                    int j = this.nbtTagCompound.getInteger("Y");
                    int k = this.nbtTagCompound.getInteger("Z");
                    EntityLeashKnot entityleashknot = EntityLeashKnot.getKnotForBlock(this.worldObj, i, j, k);

                    if (entityleashknot == null) {
                        entityleashknot = EntityLeashKnot.func_110129_a(this.worldObj, i, j, k);
                    }

                    this.leashedToEntity = entityleashknot;
                } else {
                    this.clearLeashed(false, true);
                }
        }

        this.nbtTagCompound = null;
    }

}
