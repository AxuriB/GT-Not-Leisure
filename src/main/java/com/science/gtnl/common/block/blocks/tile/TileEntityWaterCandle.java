package com.science.gtnl.common.block.blocks.tile;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import com.science.gtnl.loader.EffectLoader;

public class TileEntityWaterCandle extends TileEntity {

    @Override
    public void updateEntity() {
        if (worldObj.isRemote) return;

        List<EntityPlayer> playersInRange = worldObj.getEntitiesWithinAABB(
            EntityPlayer.class,
            AxisAlignedBB.getBoundingBox(xCoord - 64, yCoord - 64, zCoord - 64, xCoord + 64, yCoord + 64, zCoord + 64));

        for (EntityPlayer player : playersInRange) {
            if (player.dimension == worldObj.provider.dimensionId) {
                double distance = player.getDistance(xCoord, yCoord, zCoord);

                if (distance <= 64.0D) {
                    player.addPotionEffect(new PotionEffect(EffectLoader.battle.id, 20, 2));
                }
            }
        }

    }
}
