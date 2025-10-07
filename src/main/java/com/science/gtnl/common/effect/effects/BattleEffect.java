package com.science.gtnl.common.effect.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;

import com.science.gtnl.Utils.ExtraSpawnerAnimals;
import com.science.gtnl.common.effect.EffectBase;

public class BattleEffect extends EffectBase {

    public int tick;

    public ExtraSpawnerAnimals spawnerAnimals = new ExtraSpawnerAnimals();

    public BattleEffect(int id) {
        super(id, "battle", false, 0x7F60B8, 5);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        if (entity.worldObj.isRemote) return;

        if (!(entity instanceof EntityPlayerMP entityPlayerMP)) return;

        tick++;

        if (entityPlayerMP.getServerForPlayer()
            .getGameRules()
            .getGameRuleBooleanValue("doMobSpawning") && tick % (20 / amplifier) == 0) {
            spawnerAnimals.setMaxSpawnSize(amplifier * 10);
            spawnerAnimals.setMaxSpawnMultiplier(amplifier * 4);
            spawnerAnimals.setMaxAttempts(amplifier * 2);
            spawnerAnimals.setMAX_SPAWN_DISTANCE(Math.max(0, 24f - amplifier * 2));
            spawnerAnimals.setMaxChunkRadius(8 + amplifier / 10);

            spawnerAnimals.findChunksForSpawning(entityPlayerMP.getServerForPlayer(), false, true, false);
        }
    }

}
