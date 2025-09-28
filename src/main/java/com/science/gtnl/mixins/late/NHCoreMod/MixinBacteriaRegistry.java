package com.science.gtnl.mixins.late.NHCoreMod;

import java.util.LinkedHashMap;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.dreammaster.bartworksHandler.BacteriaRegistry;

import bartworks.util.BioCulture;

@Mixin(value = BacteriaRegistry.class, remap = false)
public class MixinBacteriaRegistry {

    @Redirect(method = "runAllPostinit", at = @At(value = "INVOKE", target = "Ljava/util/LinkedHashMap;clear()V"))
    private void disableCultureSetClear(LinkedHashMap<String, BioCulture> instance) {
        // 拦截 clear 方法，不执行任何操作
    }
}
