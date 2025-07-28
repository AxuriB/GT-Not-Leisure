package com.science.gtnl.mixins.late.vm_tweak;

import java.util.Objects;
import java.util.Optional;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.gtnewhorizons.modularui.api.math.Alignment;
import com.gtnewhorizons.modularui.common.widget.DynamicPositionedColumn;
import com.gtnewhorizons.modularui.common.widget.SlotWidget;
import com.gtnewhorizons.modularui.common.widget.TextWidget;
import com.science.gtnl.Utils.machine.VMTweakHelper;

import bwcrossmod.galacticgreg.MTEVoidMinerBase;
import gregtech.api.metatileentity.implementations.MTEEnhancedMultiBlockBase;
import gtneioreplugin.plugin.block.ModBlocks;
import gtneioreplugin.plugin.item.ItemDimensionDisplay;

@Mixin(value = MTEVoidMinerBase.class, remap = false)
public abstract class MTEVoidMinerBase_Mixin extends MTEEnhancedMultiBlockBase<MTEVoidMinerBase_Mixin> {

    public MTEVoidMinerBase_Mixin(String aName) {
        super(aName);
    }

    @ModifyVariable(method = "handleExtraDrops", at = @At("HEAD"), require = 1, remap = false, argsOnly = true)
    private int vmTweak$mapDimensionIdForExtraDrops(int id) {
        return VMTweakHelper.dimMapping.inverse()
            .getOrDefault(vmTweak$resolveDimensionKey(), id);
    }

    @ModifyVariable(method = "handleModDimDef", at = @At("HEAD"), require = 1, remap = false, argsOnly = true)
    private int vmTweak$mapDimensionIdForModDef(int id) {
        return vmTweak$dim = VMTweakHelper.dimMapping.inverse()
            .getOrDefault(vmTweak$resolveDimensionKey(), id);
    }

    @ModifyVariable(method = "handleModDimDef", at = @At("STORE"), require = 1, remap = false)
    private String vmTweak$mapDimensionChunkProviderName(String id) {
        return VMTweakHelper.cache.getOrDefault(vmTweak$dim, id);
    }

    @Unique
    private int vmTweak$dim;

    @Unique
    private String vmTweak$resolveDimensionKey() {
        return Optional.ofNullable(this.mInventory[1])
            .filter(s -> s.getItem() instanceof ItemDimensionDisplay)
            .map(ItemDimensionDisplay::getDimension)
            .orElse("None");
    }

    @Unique
    private String vmTweak$mLastDimensionOverride = "None";

    @Inject(method = "saveNBTData", at = @At("HEAD"), require = 1, remap = false)
    public void vmTweak$saveNBT(NBTTagCompound aNBT, CallbackInfo c) {
        aNBT.setString("mLastDimensionOverride", this.vmTweak$mLastDimensionOverride);
    }

    @Inject(method = "loadNBTData", at = @At("HEAD"), require = 1, remap = false)
    public void vmTweak$loadNBT(NBTTagCompound aNBT, CallbackInfo c) {
        this.vmTweak$mLastDimensionOverride = aNBT.getString("mLastDimensionOverride");
    }

    @Inject(method = "working", at = @At("HEAD"), remap = false)
    public void vmTweak$onWorkingTick(CallbackInfoReturnable<Boolean> cir) {
        String dim = Optional.ofNullable(this.mInventory[1])
            .filter(s -> s.getItem() instanceof ItemDimensionDisplay)
            .map(ItemDimensionDisplay::getDimension)
            .orElse("None");

        if (!Objects.equals(dim, vmTweak$mLastDimensionOverride)) {
            vmTweak$mLastDimensionOverride = dim;
            totalWeight = 0;
        }
    }

    @Unique
    private String vmTweak$getDimensionDisplayName() {
        String ext = null;
        try {
            Block block = ModBlocks.getBlock(vmTweak$mLastDimensionOverride);
            ext = new ItemStack(block).getDisplayName();
        } catch (Exception ignored) {}

        return EnumChatFormatting.YELLOW + StatCollector.translateToLocal("Info_Dimension_Override")
            + (ext == null ? vmTweak$mLastDimensionOverride : ext);
    }

    @Override
    protected void drawTexts(DynamicPositionedColumn screenElements, SlotWidget inventorySlot) {
        super.drawTexts(screenElements, inventorySlot);
        screenElements.widget(
            TextWidget.dynamicString(this::vmTweak$getDimensionDisplayName)
                .setSynced(true)
                .setDefaultColor(EnumChatFormatting.YELLOW)
                .setTextAlignment(Alignment.CenterLeft)
                .setEnabled(true));
    }

    @Shadow
    private float totalWeight;
}
