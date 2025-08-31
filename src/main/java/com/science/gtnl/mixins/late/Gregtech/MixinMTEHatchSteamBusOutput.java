package com.science.gtnl.mixins.late.Gregtech;

import static gregtech.api.util.GTUtility.*;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;

import org.spongepowered.asm.mixin.Mixin;

import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;

import gregtech.api.enums.ItemList;
import gregtech.api.gui.widgets.PhantomItemButton;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MTEHatchOutputBus;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.MTEHatchSteamBusOutput;

@Mixin(value = MTEHatchSteamBusOutput.class, remap = false)
public abstract class MixinMTEHatchSteamBusOutput extends MTEHatchOutputBus {

    public MixinMTEHatchSteamBusOutput(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier);
    }

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
    public void addUIWidgets(ModularWindow.Builder builder, UIBuildContext buildContext) {
        getBaseMetaTileEntity().add2by2Slots(builder);
        builder.widget(
            new PhantomItemButton(this).setPos(getGUIWidth() - 25, 40)
                .setBackground(PhantomItemButton.FILTER_BACKGROUND));
    }
}
