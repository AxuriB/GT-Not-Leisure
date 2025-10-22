package com.science.gtnl.mixins.late.RandomComplement;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.glodblock.github.inventory.InventoryHandler;
import com.glodblock.github.inventory.gui.GuiType;
import com.glodblock.github.network.CPacketCraftRequest;
import com.glodblock.github.util.BlockPos;
import com.science.gtnl.utils.RCAEBaseContainer;

@Mixin(value = CPacketCraftRequest.Handler.class, remap = false)
public class MixinCPacketCraftRequest {

    @Redirect(
        method = "onMessage(Lcom/glodblock/github/network/CPacketCraftRequest;Lcpw/mods/fml/common/network/simpleimpl/MessageContext;)Lcpw/mods/fml/common/network/simpleimpl/IMessage;",
        at = @At(
            value = "INVOKE",
            target = "Lcom/glodblock/github/inventory/InventoryHandler;openGui(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lcom/glodblock/github/util/BlockPos;Lnet/minecraftforge/common/util/ForgeDirection;Lcom/glodblock/github/inventory/gui/GuiType;)V",
            ordinal = 1))
    public void onMessage(EntityPlayer p, World world, BlockPos pos, ForgeDirection face, GuiType guiType) {
        Container old = null;
        if (p.openContainer instanceof RCAEBaseContainer c) {
            old = c.rc$getOldContainer();
        }

        InventoryHandler.openGui(p, world, pos, face, guiType);

        if (p.openContainer instanceof RCAEBaseContainer c && old != null) {
            c.rc$setOldContainer(old);
        }
    }
}
