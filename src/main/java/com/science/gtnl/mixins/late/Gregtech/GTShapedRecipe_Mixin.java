package com.science.gtnl.mixins.late.Gregtech;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.Utils.recipes.ReversedRecipeRegistry;

import gregtech.api.util.GTShapedRecipe;

@SuppressWarnings("UnusedMixin")
@Mixin(value = GTShapedRecipe.class, remap = false)
public class GTShapedRecipe_Mixin {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(ItemStack aResult, boolean aDismantleAble, boolean aRemovableByGT, boolean aKeepingNBT,
        Enchantment[] aEnchantmentsAdded, int[] aEnchantmentLevelsAdded, Object[] aRecipe, CallbackInfo ci) {
        if (aDismantleAble) {
            ReversedRecipeRegistry.registerShaped(aResult, aRecipe);
        }
    }

}
