package com.science.gtnl.mixins.late.Gregtech;

import static gregtech.api.util.GTUtility.*;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.gtnewhorizons.modularui.api.forge.ItemHandlerHelper;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.science.gtnl.api.mixinHelper.IItemStorage;

import gregtech.api.enums.ItemList;
import gregtech.api.gui.widgets.PhantomItemButton;
import gregtech.api.interfaces.IDataCopyable;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IItemLockable;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.MTEHatchSteamBusOutput;

@Mixin(value = MTEHatchSteamBusOutput.class, remap = false)
public abstract class MixinMTEHatchSteamBusOutput extends MTEHatch
    implements IItemLockable, IDataCopyable, IItemStorage {

    public MixinMTEHatchSteamBusOutput(int aID, String aName, String aNameRegional, int aTier, int aInvSlotCount,
        String aDescription, ITexture... aTextures) {
        super(aID, aName, aNameRegional, aTier, aInvSlotCount, aDescription, aTextures);
    }

    @Unique
    private ItemStack lockedItem = null;
    @Unique
    private static final String DATA_STICK_DATA_TYPE = "outputBusFilter";
    @Unique
    private static final String LOCKED_ITEM_NBT_KEY = "lockedItem";

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        if (!acceptsItemLock() || !(aPlayer instanceof EntityPlayerMP)) {
            openGui(aPlayer);
            return true;
        }

        final ItemStack dataStick = aPlayer.inventory.getCurrentItem();
        if (!ItemList.Tool_DataStick.isStackEqual(dataStick, false, true)) {
            openGui(aPlayer);
            return true;
        }

        if (!pasteCopiedData(aPlayer, dataStick.stackTagCompound)) {
            aPlayer.addChatMessage(new ChatComponentTranslation("GT5U.machines.output_bus.invalid"));
            return false;
        }

        aPlayer.addChatMessage(new ChatComponentTranslation("GT5U.machines.output_bus.loaded"));
        return true;

    }

    @Override
    public void onLeftclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        if (!acceptsItemLock() || !(aPlayer instanceof EntityPlayerMP)) {
            return;
        }
        final ItemStack dataStick = aPlayer.inventory.getCurrentItem();
        if (!ItemList.Tool_DataStick.isStackEqual(dataStick, false, true)) {
            return;
        }

        dataStick.stackTagCompound = getCopiedData(aPlayer);
        dataStick.setStackDisplayName("Output Bus Configuration");
        aPlayer.addChatMessage(new ChatComponentTranslation("GT5U.machines.output_bus.saved"));
    }

    @Override
    public NBTTagCompound getCopiedData(EntityPlayer player) {
        final NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("type", DATA_STICK_DATA_TYPE);
        if (lockedItem != null) {
            nbt.setTag(LOCKED_ITEM_NBT_KEY, lockedItem.writeToNBT(new NBTTagCompound()));
        }
        return nbt;
    }

    @Override
    public boolean pasteCopiedData(EntityPlayer player, NBTTagCompound nbt) {
        if (nbt == null || !DATA_STICK_DATA_TYPE.equals(nbt.getString("type"))) return false;
        if (nbt.hasKey(LOCKED_ITEM_NBT_KEY)) {
            lockedItem = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(LOCKED_ITEM_NBT_KEY));
        } else {
            lockedItem = null;
        }
        return true;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        if (lockedItem != null) {
            aNBT.setTag(LOCKED_ITEM_NBT_KEY, lockedItem.writeToNBT(new NBTTagCompound()));
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        if (aNBT.hasKey(LOCKED_ITEM_NBT_KEY)) {
            lockedItem = ItemStack.loadItemStackFromNBT(aNBT.getCompoundTag(LOCKED_ITEM_NBT_KEY));
        }
    }

    @Override
    public String getCopiedDataIdentifier(EntityPlayer player) {
        return DATA_STICK_DATA_TYPE;
    }

    @Override
    public void addUIWidgets(ModularWindow.Builder builder, UIBuildContext buildContext) {
        getBaseMetaTileEntity().add2by2Slots(builder);
        builder.widget(
            new PhantomItemButton(this).setPos(getGUIWidth() - 25, 40)
                .setBackground(PhantomItemButton.FILTER_BACKGROUND));
    }

    @Override
    public void setLockedItem(@Nullable ItemStack itemStack) {
        if (itemStack == null) {
            clearLock();
        } else {
            lockedItem = ItemHandlerHelper.copyStackWithSize(itemStack, 1);
        }
    }

    @Nullable
    @Override
    public ItemStack getLockedItem() {
        return lockedItem;
    }

    @Override
    public void clearLock() {
        lockedItem = null;
    }

    @Override
    public boolean isLocked() {
        return lockedItem != null;
    }

    @Override
    public boolean acceptsItemLock() {
        return true;
    }

    @Override
    public boolean storePartial(ItemStack aStack) {
        return storePartial(aStack, false);
    }

    @Override
    public boolean storePartial(ItemStack stack, boolean simulate) {
        markDirty();

        if (lockedItem != null && !lockedItem.isItemEqual(stack)) return false;

        int invLength = mInventory.length;

        for (int i = 0; i < invLength && stack.stackSize > 0; i++) {
            @Nullable
            ItemStack slot = mInventory[i];

            // the slot has an item and the stacks can't be merged; ignore it
            if (!isStackInvalid(slot) && !areStacksEqual(slot, stack)) continue;

            int inSlot = slot == null ? 0 : slot.stackSize;

            int toInsert = Math
                .min(Math.min(getInventoryStackLimit(), stack.getMaxStackSize() - inSlot), stack.stackSize);

            if (toInsert == 0) continue;

            if (!simulate) {
                // if the slot is invalid create an empty stack in it
                if (isStackInvalid(slot)) mInventory[i] = slot = stack.splitStack(0);

                slot.stackSize += toInsert;
            }

            stack.stackSize -= toInsert;
        }

        return stack.stackSize == 0;
    }
}
