package com.science.gtnl.mixins.late.Bartwork;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import bartworks.system.material.Werkstoff;
import bartworks.util.NonNullWrappedHashMap;
import gregtech.api.enums.OrePrefixes;

@Mixin(value = Werkstoff.GenerationFeatures.class, remap = false)
public abstract class Werkstoff_Mixin {

    @Accessor(remap = false)
    public static NonNullWrappedHashMap<OrePrefixes, Integer> getPrefixLogic() {
        throw new AssertionError();
    }

    @Accessor(value = "prefixLogic", remap = false)
    public static void setPrefixLogic(NonNullWrappedHashMap<OrePrefixes, Integer> prefixLogic) {
        throw new AssertionError();
    }

    @Inject(method = "initPrefixLogic", at = @At("TAIL"), remap = false)
    private static void injectAdditionalPrefixLogic(CallbackInfo ci) {
        NonNullWrappedHashMap<OrePrefixes, Integer> prefixLogic = getPrefixLogic();
        prefixLogic.put(OrePrefixes.plateSuperdense, 0x200);
        prefixLogic.put(OrePrefixes.nanite, 0x200);
    }
}
