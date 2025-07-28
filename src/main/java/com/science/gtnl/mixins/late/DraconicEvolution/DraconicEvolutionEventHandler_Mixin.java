package com.science.gtnl.mixins.late.DraconicEvolution;

import static com.reavaritia.common.item.InfinitySword.cancelDragonArmor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.brandon3055.draconicevolution.common.handler.MinecraftForgeEventHandler;
import com.brandon3055.draconicevolution.common.items.armor.CustomArmorHandler;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mixin(value = MinecraftForgeEventHandler.class, remap = false)
public abstract class DraconicEvolutionEventHandler_Mixin {

    /**
     * @reason Overwrites the original onLivingDeath handler to support custom behavior
     *         when a player is holding the InfinitySword. If the player is sneaking and
     *         holding the InfinitySword, this handler can be used to cancel the death
     *         event, allowing for resurrection-like mechanics or other effects.
     *         Also retains the default behavior of Draconic Evolution's armor death handler.
     * @author GTNotLeisure
     */
    @Overwrite
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onLivingDeath(LivingDeathEvent event) {
        if (event.entityLiving instanceof EntityPlayer && !cancelDragonArmor) {
            CustomArmorHandler.onPlayerDeath(event);
        }
    }
}
