package com.science.gtnl.common.effect.effects;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;

import com.science.gtnl.common.effect.EffectBase;
import com.science.gtnl.common.packet.ClientTitleDisplayHandler;
import com.science.gtnl.config.MainConfig;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AweEffect extends EffectBase {

    public static final AweEffect instance = new AweEffect();

    public static final DamageSource awe_damage = new DamageSource("damage.gtnl.AweDamage").setExplosion()
        .setDamageBypassesArmor()
        .setDamageIsAbsolute()
        .setDamageAllowedInCreativeMode()
        .setMagicDamage();

    private static final Set<EntityPlayer> affectedPlayers = new HashSet<>();
    private static final Random random = new Random();

    public AweEffect() {
        super(MainConfig.aweEffectID, "awe", false, 0xFF00FF, 1);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.entity instanceof EntityPlayer player) {
            PotionEffect effect = player.getActivePotionEffect(this);

            if (effect != null && !player.capabilities.isCreativeMode) {
                int level = effect.getAmplifier();
                double pullSpeed = level * 0.02;

                if (!affectedPlayers.contains(player)) {
                    float damage = 50.0F + random.nextFloat() * 10.0F;
                    player.attackEntityFrom(awe_damage, damage);
                    affectedPlayers.add(player);
                }

                player.capabilities.isFlying = false;
                player.capabilities.allowFlying = false;

                player.motionY = -pullSpeed;
                player.setSneaking(true);

                if (player.rotationPitch < 45) {
                    player.rotationPitch += (float) (0.2 * level);
                }
                if (player.rotationPitch > 45) {
                    player.rotationPitch -= (float) (0.2 * level);
                }

            } else {

                affectedPlayers.remove(player);
                if (player.capabilities.isCreativeMode && !player.capabilities.allowFlying) {
                    player.capabilities.allowFlying = true;
                    player.sendPlayerAbilities();
                }
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onGuiOpen(GuiOpenEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.thePlayer;

        if (player != null && !player.capabilities.isCreativeMode) {
            PotionEffect effect = player.getActivePotionEffect(this);

            if (effect != null && event.gui instanceof GuiIngameMenu) {
                event.setCanceled(true);
                ClientTitleDisplayHandler
                    .displayTitle(StatCollector.translateToLocal("Awe_Cancel_01"), 100, 0xFFFFFF, 3, 10, 20);
            }
        }
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLiving, BaseAttributeMap attributeMap,
        int amplifier) {
        super.removeAttributesModifiersFromEntity(entityLiving, attributeMap, amplifier);

        if (entityLiving instanceof EntityPlayerMP player) {
            if (!player.capabilities.isCreativeMode) {
                player.playerNetServerHandler.kickPlayerFromServer(StatCollector.translateToLocal("Awe_Kick"));
            } else {
                if (!player.capabilities.allowFlying) {
                    player.capabilities.allowFlying = true;
                    player.sendPlayerAbilities();
                }
            }
        }
    }
}
