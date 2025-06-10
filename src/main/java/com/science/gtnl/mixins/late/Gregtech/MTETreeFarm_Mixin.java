package com.science.gtnl.mixins.late.Gregtech;

import static gregtech.api.enums.GTValues.RA;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.loader.RecipePool;

import gregtech.api.enums.TierEU;
import gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.production.MTETreeFarm;

@SuppressWarnings("UnusedMixin")
@Mixin(value = MTETreeFarm.class, remap = false)
public class MTETreeFarm_Mixin {

    @Inject(
        method = "registerTreeProducts(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)V",
        at = @At("TAIL"))
    private static void injectSteamWoodcutterRecipe(ItemStack saplingIn, ItemStack log, ItemStack saplingOut,
        ItemStack leaves, ItemStack fruit, CallbackInfo ci) {

        // Hijacking this for steam update. Yes this is gross, no I don't care, it's april fools baybee

        if (log == null) return;

        RA.stdBuilder()
            .itemInputs(new ItemStack(saplingIn.getItem(), 0, saplingIn.getItemDamage()))
            .itemOutputs(new ItemStack(log.getItem(), 64, log.getItemDamage()))
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(TierEU.RECIPE_LV)
            .addTo(RecipePool.SteamWoodcutterRecipes);
    }
}
