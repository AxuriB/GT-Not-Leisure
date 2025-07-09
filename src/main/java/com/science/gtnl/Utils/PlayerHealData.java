package com.science.gtnl.Utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerHealData {

    public static final String TAG_HEAL_BONUS = "GTNL_HealBonus";
    private static final String PERSIST_TAG = EntityPlayer.PERSISTED_NBT_TAG;

    public static boolean getBonus(EntityPlayer player) {
        NBTTagCompound persistent = getPersistentTag(player, false);
        return persistent != null && persistent.getBoolean(TAG_HEAL_BONUS);
    }

    public static void addBonus(EntityPlayer player, boolean enable) {
        NBTTagCompound persistent = getPersistentTag(player, true);
        persistent.setBoolean(TAG_HEAL_BONUS, enable);
    }

    private static NBTTagCompound getPersistentTag(EntityPlayer player, boolean createIfMissing) {
        NBTTagCompound entityData = player.getEntityData();
        if (!entityData.hasKey(PERSIST_TAG)) {
            if (!createIfMissing) return null;
            entityData.setTag(PERSIST_TAG, new NBTTagCompound());
        }
        return entityData.getCompoundTag(PERSIST_TAG);
    }
}
