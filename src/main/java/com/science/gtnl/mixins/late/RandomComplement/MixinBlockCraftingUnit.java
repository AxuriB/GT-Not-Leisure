package com.science.gtnl.mixins.late.RandomComplement;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.science.gtnl.ScienceNotLeisure;
import com.science.gtnl.Utils.BlockState;
import com.science.gtnl.Utils.CraftingUnitHandler;
import com.science.gtnl.Utils.Utils;
import com.science.gtnl.common.packet.StatusMessage;

import appeng.block.AEBaseTileBlock;
import appeng.block.crafting.BlockCraftingUnit;
import appeng.me.cluster.implementations.CraftingCPUCluster;
import appeng.tile.crafting.TileCraftingTile;
import appeng.util.Platform;

@Mixin(value = BlockCraftingUnit.class, remap = false)
public class MixinBlockCraftingUnit extends AEBaseTileBlock {

    public MixinBlockCraftingUnit(Material mat) {
        super(mat);
    }

    @Inject(method = "onBlockActivated", at = @At("HEAD"), remap = true, cancellable = true)
    public void onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float hitX, float hitY,
        float hitZ, CallbackInfoReturnable<Boolean> cir) {
        if (Platform.isServer()) {
            TileCraftingTile tg = this.getTileEntity(w, x, y, z);
            if (tg == null) return;
            boolean isBusy = tg.getCluster() instanceof CraftingCPUCluster
                && ((CraftingCPUCluster) tg.getCluster()).isBusy();
            ItemStack item = p.getHeldItem();
            BlockState blockState;
            if (p.isSneaking()) {
                if (item == null) {
                    if (isBusy) {
                        ScienceNotLeisure.network.sendTo(
                            new StatusMessage(new ChatComponentTranslation("error.rc.cpu")),
                            (EntityPlayerMP) p);
                        cir.setReturnValue(true);
                    } else if (CraftingUnitHandler
                        .isReplaceable(null, blockState = new BlockState(this, this.getDamageValue(w, x, y, z)))) {
                            var block = CraftingUnitHandler.getBaseUnit();
                            w.setBlock(x, y, z, block.block(), block.meta(), 1);
                            Utils.placeItemBackInInventory(p, CraftingUnitHandler.getMatchItem(blockState));
                            cir.setReturnValue(true);
                        }
                }
            } else if (CraftingUnitHandler
                .isReplaceable(item, blockState = new BlockState(this, this.getDamageValue(w, x, y, z)))) {
                    if (isBusy) {
                        ScienceNotLeisure.network.sendTo(
                            new StatusMessage(new ChatComponentTranslation("error.rc.cpu")),
                            (EntityPlayerMP) p);
                        cir.setReturnValue(true);
                    } else {
                        var block = CraftingUnitHandler.getMatchBlock(item);
                        w.setBlock(x, y, z, block.block(), block.meta(), 1);
                        item.stackSize--;
                        Utils.placeItemBackInInventory(p, CraftingUnitHandler.getMatchItem(blockState));
                        cir.setReturnValue(true);
                    }
                }
        }
    }
}
