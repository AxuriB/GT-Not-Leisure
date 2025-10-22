package com.science.gtnl.mixins.late.Gregtech;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.utils.recipes.ReversedRecipeRegistry;

import gregtech.api.util.GTShapedRecipe;

@Mixin(value = GTShapedRecipe.class, remap = false)
public class MixinGTShapedRecipe {

    @Inject(
        method = "<init>(Lnet/minecraft/item/ItemStack;ZZZ[Lnet/minecraft/enchantment/Enchantment;[I[Ljava/lang/Object;)V",
        at = @At("RETURN"))
    private void init(ItemStack aResult, boolean aDismantleAble, boolean aRemovableByGT, boolean aKeepingNBT,
        Enchantment[] aEnchantmentsAdded, int[] aEnchantmentLevelsAdded, Object[] aRecipe, CallbackInfo ci) {
        if (aDismantleAble) {
            ReversedRecipeRegistry.registerShaped(aResult, aRecipe);
        }
    }

}
