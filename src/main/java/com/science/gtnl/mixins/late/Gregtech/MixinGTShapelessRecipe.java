package com.science.gtnl.mixins.late.Gregtech;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.Utils.recipes.ReversedRecipeRegistry;

import gregtech.api.util.GTShapelessRecipe;

@Mixin(value = GTShapelessRecipe.class, remap = false)
public class MixinGTShapelessRecipe {

    @Inject(
        method = "<init>(Lnet/minecraft/item/ItemStack;ZZZZ[Lnet/minecraft/enchantment/Enchantment;[I[Ljava/lang/Object;)V",
        at = @At("RETURN"))
    private void init(ItemStack aResult, boolean aDismantleAble, boolean aRemovableByGT, boolean aKeepingNBT,
        boolean overwriteNBT, Enchantment[] aEnchantmentsAdded, int[] aEnchantmentLevelsAdded, Object[] aRecipe,
        CallbackInfo ci) {
        if (aDismantleAble) {
            ReversedRecipeRegistry.registerShapeless(aResult, aRecipe);
        }
    }

}
