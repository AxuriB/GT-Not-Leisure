package com.science.gtnl.common.item.items;

import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import com.reavaritia.common.SubtitleDisplay;
import com.science.gtnl.Utils.TeleporterUtils;
import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.client.GTNLCreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherTeleporter extends Item implements SubtitleDisplay {

    private static final int COOLDOWN_TICKS = 200;

    public NetherTeleporter() {
        super();
        this.setUnlocalizedName("NetherTeleporter");
        this.setTextureName(RESOURCE_ROOT_ID + ":" + "NetherTeleporter");
        this.setMaxStackSize(1);
        this.setCreativeTab(GTNLCreativeTabs.GTNotLeisureItem);
        GTNLItemList.NetherTeleporter.set(new ItemStack(this, 1));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (stack.hasTagCompound()) {
            long lastUse = stack.getTagCompound()
                .getLong("lastUse");
            if (world.getTotalWorldTime() - lastUse < COOLDOWN_TICKS) {
                if (world.isRemote) {
                    long remain = (COOLDOWN_TICKS - (world.getTotalWorldTime() - lastUse)) / 20;
                    showSubtitle("Info_NetherTeleporter_Cooldown", remain);
                }
                return stack;
            }
        }

        if (!world.isRemote && player instanceof EntityPlayerMP playerMP) {

            int targetDim;
            if (player.dimension == 0) {
                targetDim = -1;
            } else if (player.dimension == -1) {
                targetDim = 0;
            } else {
                return stack;
            }

            transferPlayer(playerMP, targetDim);

            if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
            stack.getTagCompound()
                .setLong("lastUse", world.getTotalWorldTime());
        }

        return stack;
    }

    private void transferPlayer(EntityPlayerMP player, int targetDim) {
        WorldServer targetWorld = player.mcServer.worldServerForDimension(targetDim);

        double x = player.posX;
        double z = player.posZ;
        double y = player.posY;

        if (targetDim == -1) {
            x = Math.floor(x / 8);
            z = Math.floor(z / 8);
        } else {
            x = x * 8;
            z = z * 8;
        }

        int baseX = (int) x;
        int baseY = (int) y;
        int baseZ = (int) z;

        int safeX = baseX;
        int safeY = baseY;
        int safeZ = baseZ;

        int maxY = (targetDim == -1) ? 128 : 253;

        boolean found = false;
        for (int dx = -25; dx <= 25; dx++) {
            for (int dz = -25; dz <= 25; dz++) {
                for (int dy = -25; dy <= 25; dy++) {
                    int cx = baseX + dx;
                    int cy = MathHelper.clamp_int(baseY + dy, 1, maxY);
                    int cz = baseZ + dz;

                    if (targetWorld.isAirBlock(cx, cy, cz) && targetWorld.isAirBlock(cx, cy + 1, cz)) {

                        Block blockBelow = targetWorld.getBlock(cx, cy - 1, cz);
                        AxisAlignedBB collision = blockBelow
                            .getCollisionBoundingBoxFromPool(targetWorld, cx, cy - 1, cz);

                        if (collision != null) {
                            safeX = cx;
                            safeY = cy;
                            safeZ = cz;
                            found = true;
                            break;
                        }
                    }
                }
            }
        }

        if (!found) {
            safeY = Math.min(maxY, targetWorld.getTopSolidOrLiquidBlock(baseX, baseZ));
            if (safeY > 0 && safeY < maxY
                && targetWorld.isAirBlock(safeX, safeY + 1, safeZ)
                && targetWorld.isAirBlock(safeX, safeY + 2, safeZ)) found = true;
        }

        if (!found) {
            player.addChatMessage(new ChatComponentTranslation("Info_NetherTeleporter_Warning"));
            return;
        }

        player.mcServer.getConfigurationManager()
            .transferPlayerToDimension(player, targetDim, new TeleporterUtils(targetWorld, safeX, safeY, safeZ));
    }

    @SideOnly(Side.CLIENT)
    public void showSubtitle(String messageKey, long cooldown) {
        IChatComponent component = new ChatComponentTranslation(messageKey);
        component.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.WHITE));
        Minecraft.getMinecraft().ingameGUI.func_110326_a(component.getFormattedText() + cooldown + "s", true);
    }
}
