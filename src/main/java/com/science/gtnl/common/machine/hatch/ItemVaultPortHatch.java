package com.science.gtnl.common.machine.hatch;

import static com.science.gtnl.Utils.enums.BlockIcons.*;

import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.api.IItemVault;

import appeng.api.config.AccessRestriction;
import appeng.api.config.Actionable;
import appeng.api.implementations.IPowerChannelState;
import appeng.api.networking.GridFlags;
import appeng.api.networking.IGridNode;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.networking.security.IActionHost;
import appeng.api.storage.ICellContainer;
import appeng.api.storage.IMEInventory;
import appeng.api.storage.IMEInventoryHandler;
import appeng.api.storage.StorageChannel;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.util.AECableType;
import appeng.api.util.DimensionalCoord;
import appeng.me.helpers.AENetworkProxy;
import appeng.me.helpers.IGridProxyable;
import appeng.util.item.AEFluidStack;
import appeng.util.item.AEItemStack;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.render.TextureFactory;

public class ItemVaultPortHatch extends MTEHatch
    implements ICellContainer, IGridProxyable, IActionHost, IPowerChannelState {

    public IItemVault controller;

    public IMEInventoryHandler<IAEItemStack> itemHandler;
    public IMEInventoryHandler<IAEFluidStack> fluidHandler;

    public ItemVaultPortHatch(int aID, String aName, String aNameRegional) {
        super(
            aID,
            aName,
            aNameRegional,
            3,
            0,
            new String[] { StatCollector.translateToLocal("Tooltip_ItemVaultPortHatch_00") });
    }

    public ItemVaultPortHatch(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 0, aDescription, aTextures);
        this.itemHandler = new ItemMEInventory();
        this.fluidHandler = new FluidMEInventory();
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new ItemVaultPortHatch(mName, mTier, mDescriptionArray, mTextures);
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
                .addIcon(OVERLAY_FRONT_ITEMVAULTPORTHATCH)
                .extFacing()
                .build() };
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        return new ITexture[] { aBaseTexture, TextureFactory.of(Textures.BlockIcons.OVERLAY_PIPE_IN),
            TextureFactory.builder()
                .addIcon(OVERLAY_FRONT_ITEMVAULTPORTHATCH)
                .extFacing()
                .build() };
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (aBaseMetaTileEntity.isServerSide()) {
            if (controller != null && !controller.isValid()) {
                unbind();
            }
        }
    }

    public void bind(IItemVault controller) {
        this.controller = controller;
    }

    public void unbind() {
        if (this.controller == null) return;
        this.controller = null;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    private AENetworkProxy gridProxy = null;

    @Override
    public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
        if (gridProxy == null) {
            if (getBaseMetaTileEntity() instanceof IGridProxyable gridProxyable) {
                gridProxy = new AENetworkProxy(gridProxyable, "proxy", GTNLItemList.ItemVaultPortHatch.get(1), true);
                gridProxy.setFlags(GridFlags.REQUIRE_CHANNEL);
                gridProxy.onReady();
                var bmte = getBaseMetaTileEntity();
                if (bmte.getWorld() != null) {
                    gridProxy.setOwner(
                        bmte.getWorld()
                            .getPlayerEntityByName(bmte.getOwnerName()));
                }
            }
        }
        super.onFirstTick(aBaseMetaTileEntity);
    }

    @Override
    public IGridNode getActionableNode() {
        AENetworkProxy gp = getProxy();
        return gp != null ? gp.getNode() : null;
    }

    @Override
    public AENetworkProxy getProxy() {
        if (gridProxy == null) {
            if (getBaseMetaTileEntity() instanceof IGridProxyable gridProxyable) {
                gridProxy = new AENetworkProxy(gridProxyable, "proxy", GTNLItemList.ItemVaultPortHatch.get(1), true);
                gridProxy.setFlags(GridFlags.REQUIRE_CHANNEL);
                gridProxy.onReady();
                var bmte = getBaseMetaTileEntity();
                if (bmte.getWorld() != null) {
                    gridProxy.setOwner(
                        bmte.getWorld()
                            .getPlayerEntityByName(bmte.getOwnerName()));
                }
            }
        }

        return gridProxy;
    }

    @Override
    public IGridNode getGridNode(ForgeDirection forgeDirection) {
        AENetworkProxy gp = getProxy();
        return gp != null ? gp.getNode() : null;
    }

    @Override
    public AECableType getCableConnectionType(ForgeDirection forgeDirection) {
        return AECableType.SMART;
    }

    @Override
    public void securityBreak() {}

    @Override
    @SuppressWarnings("rawtypes")
    public List<IMEInventoryHandler> getCellArray(StorageChannel channel) {
        if (channel == StorageChannel.ITEMS) {
            return Collections.singletonList(itemHandler);
        } else if (channel == StorageChannel.FLUIDS) {
            return Collections.singletonList(fluidHandler);
        }
        return Collections.emptyList();
    }

    @Override
    public void saveChanges(IMEInventory cellInventory) {
        // This is handled by host itself.
    }

    @Override
    public boolean isPowered() {
        return getProxy() != null && getProxy().isPowered();
    }

    @Override
    public boolean isActive() {
        return getProxy() != null && getProxy().isActive();
    }

    @Override
    public DimensionalCoord getLocation() {
        IGregTechTileEntity gtm = this.getBaseMetaTileEntity();
        return new DimensionalCoord(gtm.getWorld(), gtm.getXCoord(), gtm.getYCoord(), gtm.getZCoord());
    }

    public class ItemMEInventory implements IMEInventoryHandler<IAEItemStack> {

        @Override
        public IAEItemStack injectItems(IAEItemStack input, Actionable mode, BaseActionSource src) {
            final ItemStack inputStack = input.getItemStack();
            if (inputStack == null) return null;
            if (controller == null || getBaseMetaTileEntity() == null) return input;
            if (mode != Actionable.SIMULATE) getBaseMetaTileEntity().markDirty();
            long amount = controller.injectItems(input, mode != Actionable.SIMULATE);
            if (amount == 0) return input;
            if (amount == input.getStackSize()) return null;
            IAEItemStack result = AEItemStack.create(input.getItemStack());
            return result.copy()
                .setStackSize(input.getStackSize() - amount);
        }

        @Override
        public IAEItemStack extractItems(IAEItemStack request, Actionable mode, BaseActionSource src) {
            if (controller == null || getBaseMetaTileEntity() == null) return null;
            if (mode != Actionable.SIMULATE) getBaseMetaTileEntity().markDirty();
            long amount = controller.extractItems(request, mode != Actionable.SIMULATE);
            if (amount == 0) return null;
            if (amount == request.getStackSize()) return request.copy();
            IAEItemStack result = AEItemStack.create(request.getItemStack());
            return result.copy()
                .setStackSize(amount);
        }

        @Override
        public StorageChannel getChannel() {
            return StorageChannel.ITEMS;
        }

        @Override
        public AccessRestriction getAccess() {
            return AccessRestriction.READ_WRITE;
        }

        @Override
        public boolean isPrioritized(IAEItemStack input) {
            return true;
        }

        @Override
        public boolean canAccept(IAEItemStack input) {
            if (controller == null || input == null) return false;
            return controller.containsItems(input.getItemStack())
                || controller.itemsCount() < controller.maxItemCount();
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
    }

    public class FluidMEInventory implements IMEInventoryHandler<IAEFluidStack> {

        @Override
        public IAEFluidStack injectItems(IAEFluidStack input, Actionable mode, BaseActionSource src) {
            final FluidStack inputStack = input.getFluidStack();
            if (inputStack == null) return null;
            if (controller == null || getBaseMetaTileEntity() == null) return input;
            if (mode != Actionable.SIMULATE) getBaseMetaTileEntity().markDirty();
            long amount = controller.injectFluids(input, mode != Actionable.SIMULATE);
            if (amount == 0) return input;
            if (amount == input.getStackSize()) return null;
            IAEFluidStack result = AEFluidStack.create(input.getFluidStack());
            return result.copy()
                .setStackSize(input.getStackSize() - amount);
        }

        @Override
        public IAEFluidStack extractItems(IAEFluidStack request, Actionable mode, BaseActionSource src) {
            if (controller == null || getBaseMetaTileEntity() == null) return null;
            if (mode != Actionable.SIMULATE) getBaseMetaTileEntity().markDirty();
            long amount = controller.extractFluids(request, mode != Actionable.SIMULATE);
            if (amount == 0) return null;
            if (amount == request.getStackSize()) return request.copy();
            IAEFluidStack result = AEFluidStack.create(request.getFluidStack());
            return result.copy()
                .setStackSize(amount);
        }

        @Override
        public StorageChannel getChannel() {
            return StorageChannel.FLUIDS;
        }

        @Override
        public AccessRestriction getAccess() {
            return AccessRestriction.READ_WRITE;
        }

        @Override
        public boolean isPrioritized(IAEFluidStack input) {
            return true;
        }

        @Override
        public boolean canAccept(IAEFluidStack input) {
            if (controller == null || input == null) return false;
            return controller.containsFluids(input.getFluidStack())
                || controller.fluidsCount() < controller.maxFluidCount();
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
    }

}
