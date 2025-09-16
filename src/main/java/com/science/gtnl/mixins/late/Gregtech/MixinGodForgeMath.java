package com.science.gtnl.mixins.late.Gregtech;

import static tectech.thing.metaTileEntity.multi.godforge.upgrade.ForgeOfGodsUpgrade.*;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.science.gtnl.common.machine.multiblock.FOGAlloyBlastSmelterModule;
import com.science.gtnl.common.machine.multiblock.FOGAlloySmelterModule;
import com.science.gtnl.common.machine.multiblock.FOGExtractorModule;

import tectech.thing.metaTileEntity.multi.godforge.MTEBaseModule;
import tectech.thing.metaTileEntity.multi.godforge.MTEForgeOfGods;
import tectech.thing.metaTileEntity.multi.godforge.util.GodforgeMath;

@Mixin(value = GodforgeMath.class, remap = false)
public abstract class MixinGodForgeMath {

    @Inject(method = "allowModuleConnection", at = @At("HEAD"), cancellable = true)
    private static void recipesLoader(MTEBaseModule module, MTEForgeOfGods godforge,
        CallbackInfoReturnable<Boolean> cir) {
        if (module instanceof FOGAlloySmelterModule) {
            cir.setReturnValue(true);
            return;
        }
        if ((module instanceof FOGAlloyBlastSmelterModule || module instanceof FOGExtractorModule)
            && godforge.isUpgradeActive(FDIM)) {
            cir.setReturnValue(true);
        }
    }
}
