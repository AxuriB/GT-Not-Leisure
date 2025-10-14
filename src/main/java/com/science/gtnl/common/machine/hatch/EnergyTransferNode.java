package com.science.gtnl.common.machine.hatch;

import static com.science.gtnl.Utils.enums.BlockIcons.*;
import static gregtech.api.enums.Dyes.*;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import gregtech.api.enums.Dyes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.MTECable;
import gregtech.api.metatileentity.implementations.MTEHatchDynamo;
import gregtech.api.metatileentity.implementations.MTEHatchEnergy;
import gregtech.api.metatileentity.implementations.MTETieredMachineBlock;
import gregtech.api.render.TextureFactory;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import tectech.mechanics.pipe.IConnectsToEnergyTunnel;
import tectech.thing.metaTileEntity.hatch.MTEHatchDynamoMulti;
import tectech.thing.metaTileEntity.hatch.MTEHatchDynamoTunnel;
import tectech.thing.metaTileEntity.hatch.MTEHatchEnergyMulti;
import tectech.thing.metaTileEntity.pipe.MTEPipeLaser;
import tectech.thing.metaTileEntity.pipe.MTEPipeLaserMirror;
import tectech.thing.metaTileEntity.single.MTEDebugPowerGenerator;

public class EnergyTransferNode extends MTETieredMachineBlock implements IConnectsToEnergyTunnel {

    public long mVoltage;
    public long mAmperes;

    public EnergyTransferNode(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional, 7, 0, "");
    }

    public EnergyTransferNode(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 0, aDescription, aTextures);
    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new EnergyTransferNode(mName, mTier, mDescriptionArray, mTextures);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        aNBT.setLong("mVoltage", mVoltage);
        aNBT.setLong("mAmperes", mAmperes);
        aNBT.setInteger(
            "mFrontFacing",
            getBaseMetaTileEntity().getFrontFacing()
                .ordinal());
        aNBT.setBoolean("mode", getBaseMetaTileEntity().isAllowedToWork());
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        mVoltage = aNBT.getLong("mVoltage");
        mAmperes = aNBT.getLong("mAmperes");
    }

    @Override
    public String[] getDescription() {
        return new String[] { StatCollector.translateToLocal("Tooltip_EnergyTransferNode_00"),
            StatCollector.translateToLocal("Tooltip_EnergyTransferNode_01"),
            StatCollector.translateToLocal("Tooltip_EnergyTransferNode_02"),
            StatCollector.translateToLocal("Tooltip_EnergyTransferNode_03"),
            StatCollector.translateToLocal("Tooltip_EnergyTransferNode_04"),
            StatCollector.translateToLocal("Tooltip_EnergyTransferNode_05"),
            StatCollector.translateToLocal("Tooltip_EnergyTransferNode_06"),
            StatCollector.translateToLocal("Tooltip_EnergyTransferNode_07") };
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            return new ITexture[] { TextureFactory.of(OVERLAY_ENERGY_TRANSFER_NODE),
                getBaseMetaTileEntity().isAllowedToWork() ? Textures.BlockIcons.OVERLAYS_ENERGY_OUT_MULTI_LASER[mTier]
                    : Textures.BlockIcons.OVERLAYS_ENERGY_IN_MULTI_LASER[mTier],
                TextureFactory
                    .of(OVERLAY_ENERGY_TRANSFER_NODE_ACTIVE, Dyes.getModulation(colorIndex, MACHINE_METAL.getRGBA())) };
        } else {
            return new ITexture[] { TextureFactory.of(OVERLAY_ENERGY_TRANSFER_NODE),
                getBaseMetaTileEntity().isAllowedToWork() ? Textures.BlockIcons.OVERLAYS_ENERGY_IN_MULTI_LASER[mTier]
                    : Textures.BlockIcons.OVERLAYS_ENERGY_OUT_MULTI_LASER[mTier] };
        }
    }

    @Override
    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        return new ITexture[0][0][0];
    }

    @Override
    public long maxEUInput() {
        return mVoltage;
    }

    @Override
    public long maxEUOutput() {
        return mVoltage;
    }

    @Override
    public long maxEUStore() {
        return mVoltage * mAmperes * mAmperes * mAmperes;
    }

    @Override
    public long maxAmperesOut() {
        return mAmperes;
    }

    @Override
    public long maxAmperesIn() {
        return mAmperes;
    }

    @Override
    public long getMinimumStoredEU() {
        return mVoltage;
    }

    @Override
    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }

    @Override
    public boolean isFacingValid(ForgeDirection facing) {
        return true;
    }

    @Override
    public boolean isEnetInput() {
        return true;
    }

    @Override
    public boolean isEnetOutput() {
        return true;
    }

    @Override
    public boolean isInputFacing(ForgeDirection side) {
        if (getBaseMetaTileEntity().isAllowedToWork()) {
            return side == getBaseMetaTileEntity().getFrontFacing();
        } else {
            return side != getBaseMetaTileEntity().getFrontFacing();
        }
    }

    @Override
    public boolean isOutputFacing(ForgeDirection side) {
        if (getBaseMetaTileEntity().isAllowedToWork()) {
            return side != getBaseMetaTileEntity().getFrontFacing();
        } else {
            return side == getBaseMetaTileEntity().getFrontFacing();
        }
    }

    @Override
    public void doExplosion(long aExplosionPower) {}

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (aBaseMetaTileEntity.isServerSide() && aTick % 20 == 0) {
            byte color = getBaseMetaTileEntity().getColorization();
            if (color < 0) {
                return;
            }

            if (aBaseMetaTileEntity.isAllowedToWork()) {
                for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
                    if (side == aBaseMetaTileEntity.getFrontFacing()) continue;

                    IMetaTileEntity provider = findMTE(aBaseMetaTileEntity, color, side, true);
                    if (provider instanceof MTEHatchDynamo dynamo) {
                        mVoltage = Math.max(mVoltage, dynamo.maxEUOutput());
                        mAmperes = Math.max(mAmperes, dynamo.maxAmperesOut());
                        moveEnergy(dynamo, this);
                    }
                    if (provider instanceof MTEHatchDynamoMulti dynamoMulti) {
                        mVoltage = Math.max(mVoltage, dynamoMulti.maxEUOutput());
                        mAmperes = Math.max(mAmperes, dynamoMulti.Amperes);
                        moveEnergy(dynamoMulti, this);
                    }
                    if (provider instanceof MTEDebugPowerGenerator debug) {
                        mVoltage = Math.max(mVoltage, debug.getEUT());
                        mAmperes = Math.max(mAmperes, debug.getAMP());
                        moveEnergy(debug, this);
                    }
                }
                IMetaTileEntity receiver = findMTE(
                    aBaseMetaTileEntity,
                    color,
                    aBaseMetaTileEntity.getFrontFacing(),
                    false);
                if (receiver instanceof MTEHatchEnergy energy) {
                    moveEnergy(this, energy);
                }
                if (receiver instanceof MTEHatchEnergyMulti energyMulti) {
                    moveEnergy(this, energyMulti);
                }
            } else {
                for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
                    if (side == aBaseMetaTileEntity.getFrontFacing()) continue;

                    IMetaTileEntity receiver = findMTE(aBaseMetaTileEntity, color, side, false);
                    if (receiver instanceof MTEHatchEnergy energy) {
                        moveEnergy(this, energy);
                    }
                    if (receiver instanceof MTEHatchEnergyMulti energyMulti) {
                        moveEnergy(this, energyMulti);
                    }
                }
                IMetaTileEntity provider = findMTE(
                    aBaseMetaTileEntity,
                    color,
                    aBaseMetaTileEntity.getFrontFacing(),
                    true);
                if (provider instanceof MTEHatchDynamo dynamo) {
                    mVoltage = dynamo.maxEUOutput();
                    mAmperes = dynamo.maxAmperesOut();
                    moveEnergy(dynamo, this);
                }
                if (provider instanceof MTEHatchDynamoMulti dynamoMulti) {
                    mVoltage = dynamoMulti.maxEUOutput();
                    mAmperes = dynamoMulti.Amperes;
                    moveEnergy(dynamoMulti, this);
                }
                if (provider instanceof MTEDebugPowerGenerator debug) {
                    mVoltage = debug.getEUT();
                    mAmperes = debug.getAMP();
                    moveEnergy(debug, this);
                }
            }
        }
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, ForgeDirection side,
        ItemStack aStack) {
        return false;
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, ForgeDirection side,
        ItemStack aStack) {
        return false;
    }

    private void moveEnergy(MetaTileEntity dynamo, MetaTileEntity energy) {
        if (dynamo.maxEUOutput() > energy.maxEUInput()) {
            energy.doExplosion(dynamo.maxEUOutput());
            dynamo.setEUVar(
                dynamo.getBaseMetaTileEntity()
                    .getStoredEU() - dynamo.maxEUOutput());
        } else if (dynamo.maxEUOutput() >= energy.maxEUInput() * 0.8 && dynamo.maxEUOutput() <= energy.maxEUInput()) {
            long diff = Math.min(
                dynamo.maxAmperesOut() * 20L * dynamo.maxEUOutput(),
                Math.min(
                    energy.maxEUStore() - energy.getBaseMetaTileEntity()
                        .getStoredEU(),
                    dynamo.getBaseMetaTileEntity()
                        .getStoredEU()));
            dynamo.setEUVar(
                dynamo.getBaseMetaTileEntity()
                    .getStoredEU() - diff);
            energy.setEUVar(
                energy.getBaseMetaTileEntity()
                    .getStoredEU() + diff);
        }

    }

    public IMetaTileEntity findMTE(IGregTechTileEntity base, byte color, ForgeDirection travelDirection,
        boolean findProvider) {
        if (travelDirection == null) {
            return null;
        }

        ForgeDirection facingSide = travelDirection.getOpposite();

        for (short dist = 1; dist < 1000; dist++) {
            IGregTechTileEntity tGTTileEntity = base.getIGregTechTileEntityAtSideAndDistance(travelDirection, dist);
            if (tGTTileEntity == null) {
                break;
            }

            IMetaTileEntity aMetaTileEntity = tGTTileEntity.getMetaTileEntity();

            if (aMetaTileEntity instanceof MTEPipeLaserMirror tMirror && tGTTileEntity.getColorization() == color) {
                ForgeDirection mirrorFacing = tMirror.getBendDirection(facingSide);
                return findMTE(tMirror.getBaseMetaTileEntity(), color, mirrorFacing, findProvider);
            }

            if (findProvider && aMetaTileEntity instanceof MTEHatchDynamo
                && facingSide == tGTTileEntity.getFrontFacing()) {
                return aMetaTileEntity;
            }

            if (findProvider && aMetaTileEntity instanceof MTEHatchDynamoMulti
                && facingSide == tGTTileEntity.getFrontFacing()) {
                if (aMetaTileEntity instanceof MTEHatchDynamoTunnel && tGTTileEntity.getColorization() != color) break;
                return aMetaTileEntity;
            }

            if (!findProvider && aMetaTileEntity instanceof MTEHatchEnergy
                && facingSide == tGTTileEntity.getFrontFacing()) {
                return aMetaTileEntity;
            }

            if (!findProvider && aMetaTileEntity instanceof MTEHatchEnergyMulti
                && facingSide == tGTTileEntity.getFrontFacing()) {
                if (aMetaTileEntity instanceof MTEHatchEnergyMulti && tGTTileEntity.getColorization() != color) break;
                return aMetaTileEntity;
            }

            if (findProvider && aMetaTileEntity instanceof MTEDebugPowerGenerator) {
                return aMetaTileEntity;
            }

            if (aMetaTileEntity instanceof EnergyTransferNode && tGTTileEntity.getColorization() == color) {
                if ((findProvider && facingSide != tGTTileEntity.getFrontFacing())
                    || (!findProvider && facingSide == tGTTileEntity.getFrontFacing())) {
                    return aMetaTileEntity;
                }
            }

            if (aMetaTileEntity instanceof MTEPipeLaser pipe && tGTTileEntity.getColorization() == color) {
                if (pipe.connectionCount < 2) {
                    break;
                } else {
                    pipe.markUsed();
                    continue;
                }
            }

            if (aMetaTileEntity instanceof MTECable) {
                continue;
            }
            break;
        }
        return null;
    }

    @Override
    public boolean canConnect(ForgeDirection side) {
        return true;
    }

    @Override
    public void getWailaBody(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        super.getWailaBody(itemStack, currentTip, accessor, config);
        final NBTTagCompound tag = accessor.getNBTData();
        currentTip.add(
            EnumChatFormatting.BLUE
                + StatCollector.translateToLocalFormatted("Info_EnergyTransferNode_00", tag.getLong("mVoltage")));
        currentTip.add(
            EnumChatFormatting.YELLOW
                + StatCollector.translateToLocalFormatted("Info_EnergyTransferNode_01", tag.getLong("mAmperes")));

        if (tag.hasKey("mFrontFacing")) {
            byte facing = tag.getByte("mFrontFacing");
            if (facing >= 0 && facing < ForgeDirection.VALID_DIRECTIONS.length) {
                currentTip.add(
                    EnumChatFormatting.GREEN + StatCollector.translateToLocalFormatted(
                        "Info_EnergyTransferNode_02",
                        ForgeDirection.getOrientation(facing)
                            .name()));
            }
        }
        if (tag.hasKey("mode")) {
            boolean mode = tag.getBoolean("mode");
            currentTip.add(
                EnumChatFormatting.LIGHT_PURPLE
                    + StatCollector.translateToLocal("Info_EnergyTransferNode_03_" + (mode ? "Out" : "In")));
        }
    }

    @Override
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y,
        int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        final IGregTechTileEntity tileEntity = getBaseMetaTileEntity();
        if (tileEntity != null) {
            tag.setLong("mVoltage", mVoltage);
            tag.setLong("mAmperes", mAmperes);
            tag.setInteger(
                "mFrontFacing",
                getBaseMetaTileEntity().getFrontFacing()
                    .ordinal());
            tag.setBoolean("mode", getBaseMetaTileEntity().isAllowedToWork());
        }
    }
}
