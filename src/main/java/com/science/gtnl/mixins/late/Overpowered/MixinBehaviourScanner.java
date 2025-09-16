package com.science.gtnl.mixins.late.Overpowered;

import static com.science.gtnl.Utils.steam.SteamWirelessNetworkManager.getUserSteam;

import java.util.ArrayList;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.science.gtnl.Utils.enums.ModList;
import com.science.gtnl.Utils.recipes.ChanceBonusManager;
import com.science.gtnl.common.machine.hatch.WirelessSteamDynamoHatch;
import com.science.gtnl.common.machine.hatch.WirelessSteamEnergyHatch;
import com.science.gtnl.config.MainConfig;

import gregtech.api.enums.SoundResource;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.items.MetaBaseItem;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.metatileentity.implementations.MTEMultiBlockBase;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.common.items.behaviors.BehaviourScanner;
import gregtech.common.misc.spaceprojects.SpaceProjectManager;

@Mixin(value = BehaviourScanner.class, remap = false)
public class MixinBehaviourScanner {

    /**
     * @reason Overwrites this method to add voltage chance bonus information
     * @author GTNotLeisure
     */
    @Overwrite
    public boolean onItemUseFirst(MetaBaseItem aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX,
        int aY, int aZ, ForgeDirection side, float hitX, float hitY, float hitZ) {
        final NBTTagCompound tNBT = aStack.getTagCompound();
        if ((aPlayer instanceof EntityPlayerMP) && aItem.canUse(aStack, 20000.0D)) {
            final ArrayList<String> tList = new ArrayList<>();
            if (aItem.use(
                aStack,
                GTUtility.getCoordinateScan(tList, aPlayer, aWorld, 1, aX, aY, aZ, side, hitX, hitY, hitZ),
                aPlayer)) {
                TileEntity tile = aWorld.getTileEntity(aX, aY, aZ);
                if (tile instanceof BaseMetaTileEntity baseMetaTileEntity) {
                    IMetaTileEntity meta = baseMetaTileEntity.getMetaTileEntity();
                    if (meta instanceof WirelessSteamDynamoHatch || meta instanceof WirelessSteamEnergyHatch) {
                        String username = aPlayer.getCommandSenderName();
                        String formatted_username = EnumChatFormatting.BLUE + username + EnumChatFormatting.RESET;
                        UUID userUUID = SpaceProjectManager.getPlayerUUIDFromName(username);
                        UUID teamUUID = SpaceProjectManager.getLeader(userUUID);

                        if (!SpaceProjectManager.isInTeam(userUUID)) {
                            String notInNetwork = String
                                .format(StatCollector.translateToLocal("Info_SteamNetwork_00"), formatted_username);
                            tList.add(notInNetwork);
                        } else {
                            String steamInfo = String.format(
                                StatCollector.translateToLocal("Info_SteamNetwork_01"),
                                formatted_username,
                                EnumChatFormatting.RED,
                                GTUtility.formatNumbers(getUserSteam(userUUID)),
                                "L" + EnumChatFormatting.RESET);
                            tList.add(steamInfo);

                            if (!userUUID.equals(teamUUID)) {
                                String networkInfo = String.format(
                                    StatCollector.translateToLocal("Info_SteamNetwork_02"),
                                    formatted_username,
                                    EnumChatFormatting.BLUE,
                                    SpaceProjectManager.getPlayerNameFromUUID(teamUUID),
                                    EnumChatFormatting.RESET);
                                tList.add(networkInfo);
                            }
                        }
                    }

                    if (!ModList.Overpowered.isModLoaded() && MainConfig.enableRecipeOutputChance) {
                        if (meta instanceof MTEMultiBlockBase mte) {
                            GTRecipe recipe = ChanceBonusManager.customProvider.getRecipeForMachine(mte);
                            if (recipe != null) {
                                int tier = GTUtility.getTier(mte.getMaxInputVoltage());
                                int baseTier = GTUtility.getTier(recipe.mEUt);
                                double bonusPerTier = MainConfig.recipeOutputChance;
                                double bonus = tier <= baseTier ? 0.0 : (tier - baseTier) * bonusPerTier;

                                String debugMessage = String.format(
                                    StatCollector.translateToLocal("Info_VoltageChanceBonus_00"),
                                    bonus,
                                    tier,
                                    baseTier);
                                tList.add(debugMessage);
                            }
                        }

                    }
                }

                final int tList_sS = tList.size();
                tNBT.setInteger("dataLinesCount", tList_sS);
                for (int i = 0; i < tList_sS; i++) {
                    tNBT.setString("dataLines" + i, tList.get(i));
                    GTUtility.sendChatToPlayer(aPlayer, tList.get(i));
                }
            }
            return true;
        }
        GTUtility.doSoundAtClient(SoundResource.IC2_TOOLS_OD_SCANNER, 1, 1.0F, aX, aY, aZ);
        return aPlayer instanceof EntityPlayerMP;
    }
}
