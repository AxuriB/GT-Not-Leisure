package com.science.gtnl.mixins.late.TinkersGregworks;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import gregtech.api.enums.Materials;
import tconstruct.library.crafting.FluidType;
import vexatos.tgregworks.integration.TGregRegistry;
import vexatos.tgregworks.integration.recipe.tconstruct.TGregFluidType;

@Mixin(value = TGregRegistry.class, remap = false)
public class MixinTGregRegistry {

    @Inject(method = "registerFluids", at = @At("HEAD"))
    private void onRegisterFluids(CallbackInfo ci) {
        TGregRegistry self = (TGregRegistry) (Object) this;
        try {
            for (Materials m : self.toolMaterials) {
                if (m.mStandardMoltenFluid != null) {
                    Integer matID = self.matIDs.get(m);
                    if (matID == null) {
                        continue;
                    }
                    TGregFluidType fluidType = new TGregFluidType(
                        m,
                        m.mStandardMoltenFluid.getBlock(),
                        0,
                        m.mStandardMoltenFluid.getTemperature(),
                        m.mStandardMoltenFluid,
                        true,
                        matID);
                    self.toolMaterialFluidTypes.put(m, fluidType);
                    FluidType.registerFluidType(m.mStandardMoltenFluid.getName(), fluidType);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
