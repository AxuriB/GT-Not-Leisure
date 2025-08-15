package com.science.gtnl.Utils.item;

import static com.science.gtnl.Utils.enums.Mods.Baubles;
import static gregtech.api.enums.Mods.AE2FluidCraft;
import static gregtech.api.enums.Mods.Botania;
import static gregtech.api.util.GTModHandler.getModItem;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import com.gtnewhorizons.modularui.api.drawable.UITexture;
import com.reavaritia.common.item.InfinityTotem;
import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.Utils.enums.Mods;

import baubles.api.BaublesApi;
import gregtech.api.util.GTModHandler;

public class ItemUtils {

    public static final UITexture PICTURE_GTNL_LOGO = UITexture
        .fullImage(Mods.ScienceNotLeisure.ID, "gui/picture/logo");

    public static final UITexture PICTURE_GTNL_STEAM_LOGO = UITexture
        .fullImage(Mods.ScienceNotLeisure.ID, "gui/picture/steam_logo");

    public static NBTTagCompound writeItemStackToNBT(ItemStack stack) {
        NBTTagCompound compound = new NBTTagCompound();

        stack.writeToNBT(compound);
        compound.setInteger("IntCount", stack.stackSize);

        return compound;
    }

    public static ItemStack readItemStackFromNBT(NBTTagCompound compound) {
        ItemStack stack = ItemStack.loadItemStackFromNBT(compound);

        if (stack == null) return null;

        if (compound.hasKey("IntCount")) stack.stackSize = compound.getInteger("IntCount");

        return stack;
    }

    public static void removeItemFromPlayer(EntityPlayer player, ItemStack stack) {
        // 从物品栏中移除
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack inventoryStack = player.inventory.getStackInSlot(i);
            if (inventoryStack != null && inventoryStack.getItem() instanceof InfinityTotem
                && inventoryStack == stack) {
                player.inventory.setInventorySlotContents(i, null);
                return;
            }
        }

        // 从饰品栏中移除
        if (Baubles.isModLoaded()) {
            IInventory baublesInventory = BaublesApi.getBaubles(player);
            if (baublesInventory != null) {
                for (int i = 0; i < baublesInventory.getSizeInventory(); i++) {
                    ItemStack baubleStack = baublesInventory.getStackInSlot(i);
                    if (baubleStack != null && baubleStack.getItem() instanceof InfinityTotem && baubleStack == stack) {
                        baublesInventory.setInventorySlotContents(i, null);
                        return;
                    }
                }
            }
        }
    }

    public static Block getBlockFromItemStack(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemBlock block) {
            return Block.getBlockFromItem(block);
        }
        return Blocks.air;
    }

    public static ItemStack createItemStack(String aModID, String aItem, long aAmount, int aMeta, String aNBTString,
        ItemStack aReplacement) {
        ItemStack s = getModItem(aModID, aItem, aAmount, aMeta);
        if (s == null) return aReplacement;
        try {
            s.stackTagCompound = (NBTTagCompound) JsonToNBT.func_150315_a(aNBTString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return s;
    }

    public static ItemStack createSpecialFlower(String typeName) {
        ItemStack stack = GTModHandler.getModItem(Botania.ID, "specialFlower", 1);
        if (stack == null) return null;

        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) {
            tag = new NBTTagCompound();
            stack.setTagCompound(tag);
        }

        tag.setString("type", typeName);
        return stack;
    }

    public static ItemStack createFluidPacket(FluidStack fluid, int amount) {
        if (fluid == null) return new ItemStack(Blocks.fire);
        ItemStack packet = GTModHandler.getModItem(AE2FluidCraft.ID, "fluid_packet", 1);
        NBTTagCompound tag = packet.getTagCompound();
        if (tag == null) {
            tag = new NBTTagCompound();
            packet.setTagCompound(tag);
        }

        NBTTagCompound fluidTag = new NBTTagCompound();
        String fluidName = fluid.getFluid()
            .getName();

        fluidTag.setString("FluidName", fluidName);
        fluidTag.setInteger("Amount", amount);

        tag.setTag("FluidStack", fluidTag);
        return packet;
    }

    public static ItemStack getIntegratedCircuitPlus(int config) {
        return GTNLItemList.CircuitIntegratedPlus.getWithDamage(0, config);
    }
}
