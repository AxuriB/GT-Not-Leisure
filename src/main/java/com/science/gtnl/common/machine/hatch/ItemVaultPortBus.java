package com.science.gtnl.common.machine.hatch;

import static com.science.gtnl.Utils.enums.BlockIcons.*;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import com.science.gtnl.api.IItemVault;
import com.science.gtnl.common.machine.multiblock.SteamItemVault;

import appeng.api.AEApi;
import appeng.api.config.AccessRestriction;
import appeng.api.config.Actionable;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.IExternalStorageHandler;
import appeng.api.storage.IMEInventory;
import appeng.api.storage.IMEMonitor;
import appeng.api.storage.IMEMonitorHandlerReceiver;
import appeng.api.storage.StorageChannel;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IItemList;
import appeng.util.item.AEItemStack;
import appeng.util.item.ItemList;
import cpw.mods.fml.common.Optional;
import gregtech.api.enums.Mods;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.render.TextureFactory;

public class ItemVaultPortBus extends MTEHatch implements IMEMonitor<IAEItemStack> {

    public static class AE2ItemVaultPortHatchHandler implements IExternalStorageHandler {

        @Override
        @Optional.Method(modid = Mods.ModIDs.APPLIED_ENERGISTICS2)
        public boolean canHandle(TileEntity te, ForgeDirection d, StorageChannel channel, BaseActionSource mySrc) {
            return channel == StorageChannel.ITEMS && te instanceof BaseMetaTileEntity base
                && base.getMetaTileEntity() instanceof ItemVaultPortBus portBus
                && portBus.controller != null;
        }

        @Override
        @Optional.Method(modid = Mods.ModIDs.APPLIED_ENERGISTICS2)
        public IMEInventory<?> getInventory(TileEntity te, ForgeDirection d, StorageChannel channel,
            BaseActionSource src) {
            if (channel == StorageChannel.ITEMS) {
                return ((ItemVaultPortBus) (((BaseMetaTileEntity) te).getMetaTileEntity()));
            }
            return null;
        }
    }

    public HashMap<IMEMonitorHandlerReceiver<IAEItemStack>, Object> listeners = new HashMap<>();
    public IItemVault controller;

    public ItemVaultPortBus(int aID, String aName, String aNameRegional) {
        super(
            aID,
            aName,
            aNameRegional,
            3,
            0,
            new String[] { StatCollector.translateToLocal("Tooltip_ItemVaultPortBus_00") });
    }

    public ItemVaultPortBus(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 0, aDescription, aTextures);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new ItemVaultPortBus(mName, mTier, mDescriptionArray, mTextures);
    }

    @Override
    public boolean isFacingValid(ForgeDirection facing) {
        return true;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
        int colorIndex, boolean aActive, boolean aRedstone) {
        return super.getTexture(aBaseMetaTileEntity, side, facing, colorIndex, aActive, aRedstone);
    }

    @Override
    public ITexture[] getTexturesActive(ITexture aBaseTexture) {
        return new ITexture[] { aBaseTexture, TextureFactory.of(Textures.BlockIcons.OVERLAY_PIPE_IN),
            TextureFactory.builder()
                .addIcon(OVERLAY_FRONT_ITEMVAULTPORTBUS)
                .extFacing()
                .build() };
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        return new ITexture[] { aBaseTexture, TextureFactory.of(Textures.BlockIcons.OVERLAY_PIPE_IN),
            TextureFactory.builder()
                .addIcon(OVERLAY_FRONT_ITEMVAULTPORTBUS)
                .extFacing()
                .build() };
    }

    public void bind(SteamItemVault controller) {
        this.controller = controller;
    }

    public void unbind() {
        this.controller = null;
    }

    public static void registerAEIntegration() {
        AEApi.instance()
            .registries()
            .externalStorage()
            .addExternalStorageInterface(new AE2ItemVaultPortHatchHandler());
    }

    @Override
    public IItemList<IAEItemStack> getAvailableItems(IItemList<IAEItemStack> out, int iteration) {
        if (controller != null) {
            return controller.getStore();
        }
        return out;
    }

    @Override
    public IItemList<IAEItemStack> getStorageList() {
        IItemList<IAEItemStack> itemList = new ItemList();
        if (controller != null) {
            return controller.getStore();
        }
        return itemList;
    }

    @Override
    public void addListener(IMEMonitorHandlerReceiver<IAEItemStack> l, Object verificationToken) {
        if (listeners == null) listeners = new HashMap<>();
        listeners.put(l, verificationToken);
    }

    @Override
    public void removeListener(IMEMonitorHandlerReceiver<IAEItemStack> l) {
        if (listeners == null) listeners = new HashMap<>();
        listeners.remove(l);
    }

    @Override
    public AccessRestriction getAccess() {
        return AccessRestriction.READ_WRITE;
    }

    @Override
    public boolean isPrioritized(IAEItemStack input) {
        if (controller == null || input == null) return false;
        return controller.contains(input.getItemStack()) || controller.itemCount() < SteamItemVault.MAX_DISTINCT_ITEMS;
    }

    @Override
    public boolean canAccept(IAEItemStack input) {
        if (controller == null || input == null) return false;
        return controller.contains(input.getItemStack()) || controller.itemCount() < SteamItemVault.MAX_DISTINCT_ITEMS;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public int getSlot() {
        return 0;
    }

    @Override
    public boolean validForPass(int i) {
        return true;
    }

    @Override
    public IAEItemStack injectItems(IAEItemStack input, Actionable mode, BaseActionSource src) {
        final ItemStack inputStack = input.getItemStack();
        if (inputStack == null) return null;
        if (controller == null || getBaseMetaTileEntity() == null) return input;
        if (mode != Actionable.SIMULATE) getBaseMetaTileEntity().markDirty();
        long amount = controller.pull(input, mode != Actionable.SIMULATE);
        if (amount == 0) return input;
        if (amount == input.getStackSize()) return null;
        IAEItemStack result = AEItemStack.create(input.getItemStack());
        result.setStackSize(input.getStackSize() - amount);
        return result;
    }

    @Override
    public IAEItemStack extractItems(IAEItemStack request, Actionable mode, BaseActionSource src) {
        if (controller == null || getBaseMetaTileEntity() == null) return null;
        if (mode != Actionable.SIMULATE) getBaseMetaTileEntity().markDirty();
        long amount = controller.push(request, mode != Actionable.SIMULATE);
        if (amount == 0) return null;
        if (amount == request.getStackSize()) return request.copy();
        IAEItemStack result = AEItemStack.create(request.getItemStack());
        result.setStackSize(amount);
        return result;
    }

    @Override
    public StorageChannel getChannel() {
        return StorageChannel.ITEMS;
    }
}
