package com.science.gtnl.common.item.ReAvaritia;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.client.CreativeTabsLoader;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CrystalSword extends ItemSword {

    public CrystalSword() {
        super(EnumHelper.addToolMaterial("CRYSTAL", 3, 8888, 8.0F, 48.0F, 10));
        this.setUnlocalizedName("CrystalSword");
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.setCreativeTab(CreativeTabsLoader.ReAvaritia);
        this.setTextureName("reavaritia:CrystalSword");
        this.setMaxDamage(8888);
    }

    @Override
    public boolean hasEffect(ItemStack stack, int pass) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemStack, final EntityPlayer player, final List toolTip,
        final boolean advancedToolTips) {
        toolTip.add(TextLocalization.Tooltip_CrystalSword_00);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (player.isSneaking()) {
            toggleSwordMode(stack, world, player);
            return stack;
        } else {
            if (isSwordAuraActive(stack)) {
                handleSwordAttack(stack, world, player);
            }
        }
        return super.onItemRightClick(stack, world, player);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.block;
    }

    private void toggleSwordMode(ItemStack stack, World world, EntityPlayer player) {
        NBTTagCompound nbt = getOrCreateNBT(stack);
        boolean currentMode = nbt.getBoolean("SwordAuraMode");
        nbt.setBoolean("SwordAuraMode", !currentMode);

        String messageKey = !currentMode ? TextLocalization.Tooltip_CrystalSword_Aura_On
            : TextLocalization.Tooltip_CrystalSword_Aura_Off;

        if (world.isRemote) {
            showSubtitle(messageKey, 0);
        }
    }

    private void handleSwordAttack(ItemStack stack, World world, EntityPlayer player) {
        NBTTagCompound nbt = stack.getTagCompound();
        long lastUsed = nbt.getLong("LastUsed");
        long cooldown = 5000;

        if (System.currentTimeMillis() - lastUsed < cooldown) {
            if (world.isRemote) {
                long remainingTime = (cooldown - (System.currentTimeMillis() - lastUsed)) / 1000;
                showSubtitle(
                    TextLocalization.Tooltip_CrystalSword_Aura_00 + remainingTime
                        + TextLocalization.Tooltip_CrystalSword_Aura_01,
                    remainingTime);
            }
            return;
        }

        if (!world.isRemote) {
            executeSwordAttack(world, player);
        } else {
            spawnClientParticles(world, player);
        }

        nbt.setLong("LastUsed", System.currentTimeMillis());
    }

    private void executeSwordAttack(World world, EntityPlayer player) {
        Vec3 lookVec = player.getLookVec();
        double range = 100.0;
        double width = 2.0;

        Vec3 startPos = Vec3.createVectorHelper(player.posX, player.posY + player.getEyeHeight(), player.posZ);

        Vec3 endPos = Vec3.createVectorHelper(
            startPos.xCoord + lookVec.xCoord * range,
            startPos.yCoord + lookVec.yCoord * range,
            startPos.zCoord + lookVec.zCoord * range);

        AxisAlignedBB attackArea = calculateAttackArea(startPos, endPos, width);

        List<EntityLivingBase> targets = world.getEntitiesWithinAABB(EntityLivingBase.class, attackArea);
        for (EntityLivingBase target : targets) {
            if (target != player) {
                target.attackEntityFrom(DamageSource.outOfWorld, 52.0F);
            }
        }

        if (world.isRemote) {
            spawnClientParticles(world, player);
        }
    }

    private AxisAlignedBB calculateAttackArea(Vec3 start, Vec3 end, double width) {
        return AxisAlignedBB.getBoundingBox(
            Math.min(start.xCoord, end.xCoord) - width,
            Math.min(start.yCoord, end.yCoord) - width,
            Math.min(start.zCoord, end.zCoord) - width,
            Math.max(start.xCoord, end.xCoord) + width,
            Math.max(start.yCoord, end.yCoord) + width,
            Math.max(start.zCoord, end.zCoord) + width);
    }

    // 这里的粒子生成无法超过长度22格
    @SideOnly(Side.CLIENT)
    private void spawnClientParticles(World world, EntityPlayer player) {
        Vec3 lookVec = player.getLookVec();
        double range = 10000;
        double step = 0.5;

        Vec3 startPos = Vec3.createVectorHelper(player.posX, player.posY + player.getEyeHeight(), player.posZ);

        for (double d = 0; d < range; d += step) {
            double x = startPos.xCoord + lookVec.xCoord * d;
            double y = startPos.yCoord + lookVec.yCoord * d;
            double z = startPos.zCoord + lookVec.zCoord * d;

            for (int i = 0; i < 2; i++) {
                double offsetX = (world.rand.nextDouble() - 1) * 0.5;
                double offsetY = (world.rand.nextDouble() - 1) * 0.5;
                double offsetZ = (world.rand.nextDouble() - 1) * 0.5;

                String particleType = "flame";

                world.spawnParticle(
                    particleType,
                    x + offsetX,
                    y + offsetY,
                    z + offsetZ,
                    lookVec.xCoord * 0.5,
                    lookVec.yCoord * 0.5,
                    lookVec.zCoord * 0.5);
            }
        }
    }

    private NBTTagCompound getOrCreateNBT(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null) {
            nbt = new NBTTagCompound();
            stack.setTagCompound(nbt);
        }
        return nbt;
    }

    private boolean isSwordAuraActive(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound()
            .getBoolean("SwordAuraMode");
    }

    @SideOnly(Side.CLIENT)
    private void showSubtitle(String messageKey, long cooldown) {
        String message = cooldown > 0 ? messageKey : I18n.format(messageKey);
        ChatComponentText text = new ChatComponentText(EnumChatFormatting.WHITE + message);
        Minecraft.getMinecraft().ingameGUI.func_110326_a(text.getFormattedText(), true);
    }
}
