package com.science.gtnl.common.effect.effects;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.science.gtnl.common.effect.EffectBase;
import com.science.gtnl.config.MainConfig;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PerfectPhysiqueEffect extends EffectBase {

    public static final PerfectPhysiqueEffect instance = new PerfectPhysiqueEffect();

    public PerfectPhysiqueEffect() {
        super(MainConfig.perfectPhysiqueEffect, "perfect_physique", false, 0xFFD700, 2);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event) {
        if (event.entity instanceof EntityPlayer player) {
            PotionEffect effect = player.getActivePotionEffect(this);

            if (effect != null) {
                if (player.getHealth() > 0.0F && player.getHealth() < 15.0F) {
                    player.setHealth(15.0F);
                }
                player.getFoodStats()
                    .addStats(20, 20.0F);
            }
        }
    }
}
