package com.science.gtnl.common.machine.hatch;

import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.BOLD;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.GRAY;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.GREEN;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.YELLOW;
import static gregtech.api.enums.GTValues.*;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.util.GTUtility;
import tectech.thing.metaTileEntity.hatch.MTEHatchWirelessDynamoMulti;

public class WirelessMultiDynamoHatch extends MTEHatchWirelessDynamoMulti {

    public boolean laserMode;

    public WirelessMultiDynamoHatch(int aID, String aName, String aNameRegional, int aTier, int aAmp) {
        super(aID, aName, aNameRegional, aTier, aAmp);
    }

    public WirelessMultiDynamoHatch(String aName, int aTier, int aAmp, String[] aDescription,
        ITexture[][][] aTextures) {
        super(aName, aTier, aAmp, aDescription, aTextures);
    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new WirelessMultiDynamoHatch(mName, mTier, Amperes, mDescriptionArray, mTextures);
    }

    @Override
    public ConnectionType getConnectionType() {
        return laserMode ? ConnectionType.LASER : ConnectionType.CABLE;
    }

    @Override
    public boolean isEnetInput() {
        return true;
    }

    @Override
    public String[] getDescription() {
        return new String[] { StatCollector.translateToLocal("Tooltip_WirelessMultiDynamoHatch_00"),
            StatCollector.translateToLocal("Tooltip_WirelessMultiDynamoHatch_01"),
            StatCollector.translateToLocal("Tooltip_WirelessMultiDynamoHatch_02"),
            AuthorColen + GRAY + BOLD + " & " + GREEN + BOLD + "Chrom",
            StatCollector.translateToLocal("gt.blockmachines.hatch.energytunnel.desc.1") + ": "
                + YELLOW
                + GTUtility.formatNumbers(maxAmperes * V[mTier])
                + GRAY
                + " EU/t" };
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setBoolean("laserMode", laserMode);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        laserMode = aNBT.getBoolean("laserMode");
    }

    @Override
    public void onScrewdriverRightClick(ForgeDirection side, EntityPlayer aPlayer, float aX, float aY, float aZ,
        ItemStack aTool) {
        super.onScrewdriverRightClick(side, aPlayer, aX, aY, aZ, aTool);
        if (getBaseMetaTileEntity().isServerSide()) {
            laserMode = !laserMode;
            GTUtility.sendChatToPlayer(
                aPlayer,
                StatCollector.translateToLocal(
                    "Info_WirelessMultiDynamoHatch_LaserMode_" + (laserMode ? "Enabled" : "Disabled")));
        }
    }
}
