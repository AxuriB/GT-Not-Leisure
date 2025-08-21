package com.science.gtnl.Utils.item;

import static com.science.gtnl.Utils.enums.ModList.Baubles;
import static gregtech.api.enums.Mods.AE2FluidCraft;
import static gregtech.api.enums.Mods.Botania;
import static gregtech.api.util.GTModHandler.getModItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import com.gtnewhorizons.modularui.api.drawable.UITexture;
import com.reavaritia.common.item.InfinityTotem;
import com.science.gtnl.ScienceNotLeisure;
import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.Utils.enums.ModList;
import com.science.gtnl.common.packet.WirelessPickBlock;

import baubles.api.BaublesApi;
import gregtech.api.enums.Mods;
import gregtech.api.util.GTModHandler;

public class ItemUtils {

    public static final Random rand = new Random();

    public static final UITexture PICTURE_GTNL_LOGO = UITexture
        .fullImage(ModList.ScienceNotLeisure.ID, "gui/picture/logo");

    public static final UITexture PICTURE_GTNL_STEAM_LOGO = UITexture
        .fullImage(ModList.ScienceNotLeisure.ID, "gui/picture/steam_logo");

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
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack inventoryStack = player.inventory.getStackInSlot(i);
            if (inventoryStack != null && inventoryStack.getItem() instanceof InfinityTotem
                && inventoryStack == stack) {
                player.inventory.setInventorySlotContents(i, null);
                return;
            }
        }

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

    public static boolean canBeEnchanted(ItemStack stack) {
        return canBeEnchanted(stack, 30, false);
    }

    public static boolean canBeEnchanted(ItemStack stack, boolean allowExisting) {
        return canBeEnchanted(stack, 30, allowExisting);
    }

    public static boolean canBeEnchanted(ItemStack stack, int level, boolean allowExisting) {
        if (stack == null || level <= 0) return false;
        if (!allowExisting && stack.isItemEnchanted()) return false;

        List<EnchantmentData> enchantments = EnchantmentHelper.buildEnchantmentList(rand, stack, level);
        return enchantments != null && !enchantments.isEmpty();
    }

    public static ItemStack enchantItem(ItemStack stack, int level) {
        return enchantItem(stack, level, false);
    }

    public static ItemStack enchantItem(ItemStack stack, int level, boolean allowExisting) {
        if (stack == null || level <= 0) return stack;
        if (!allowExisting && stack.isItemEnchanted()) return stack;

        boolean isBook = stack.getItem() == Items.book;
        List<EnchantmentData> enchantments = EnchantmentHelper.buildEnchantmentList(rand, stack, level);

        if (enchantments == null || enchantments.isEmpty()) return stack;

        if (isBook) {
            stack.func_150996_a(Items.enchanted_book);
        }

        int skipIndex = isBook && enchantments.size() > 1 ? rand.nextInt(enchantments.size()) : -1;

        for (int i = 0; i < enchantments.size(); i++) {
            EnchantmentData data = enchantments.get(i);

            if (!isBook || i != skipIndex) {
                if (isBook) {
                    Items.enchanted_book.addEnchantment(stack, data);
                } else {
                    stack.addEnchantment(data.enchantmentobj, data.enchantmentLevel);
                }
            }
        }

        return stack;
    }

    public static ItemStack applyRandomEnchantments(ItemStack stack, int count, int minLevel, int maxLevel) {
        return applyRandomEnchantments(stack, count, minLevel, maxLevel, false);
    }

    public static ItemStack applyRandomEnchantments(ItemStack stack, int count, int minLevel, int maxLevel,
        boolean allowExisting) {
        if (stack == null || count <= 0) return stack;
        if (!allowExisting && stack.isItemEnchanted()) return stack;

        List<Enchantment> available = new ArrayList<>();
        for (Enchantment ench : Enchantment.enchantmentsList) {
            if (ench != null && ench.canApply(stack)) {
                available.add(ench);
            }
        }

        if (available.isEmpty()) return stack;

        Collections.shuffle(available, rand);

        int applyCount = Math.min(count, available.size());

        for (int i = 0; i < applyCount; i++) {
            Enchantment ench = available.get(i);
            int level = rand.nextInt(ench.getMaxLevel() - ench.getMinLevel() + 1) + ench.getMinLevel();
            level = Math.max(minLevel, level);
            level = Math.min(maxLevel, level);

            stack.addEnchantment(ench, level);
        }

        return stack;
    }

    public static ItemStack createPlayerSkull(String playerName) {
        ItemStack skullStack = new ItemStack(Items.skull, 1, 3);

        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("SkullOwner", playerName);
        skullStack.setTagCompound(tag);

        return skullStack;
    }

    public static final boolean isBackHandIns = Mods.Backhand.isModLoaded();

    public static boolean placeItemInHotbar(EntityPlayer player, ItemStack result, boolean isCreative, boolean useAE) {
        if (result == null) return false;
        Minecraft mc = Minecraft.getMinecraft();
        InventoryPlayer inv = player.inventory;
        int backHandSlot = isBackHandIns ? 1 : 0;

        for (int i = 0; i < 9; i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null && stack.isItemEqual(result) && ItemStack.areItemStackTagsEqual(stack, result)) {
                inv.currentItem = i;
                return true;
            }
        }

        int foundSlot = -1;
        for (int i = 9; i < inv.mainInventory.length + backHandSlot; i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null && stack.isItemEqual(result) && ItemStack.areItemStackTagsEqual(stack, result)) {
                foundSlot = i;
                break;
            }
        }

        if (foundSlot != -1) {
            int emptyHotbar = -1;
            if (inv.getStackInSlot(inv.currentItem) == null) {
                emptyHotbar = inv.currentItem;
            } else {
                for (int i = 0; i < 9; i++) {
                    if (inv.getStackInSlot(i) == null) {
                        emptyHotbar = i;
                        break;
                    }
                }
            }

            if (emptyHotbar != -1) {
                int windowId = player.inventoryContainer.windowId;
                mc.playerController.windowClick(windowId, foundSlot, 0, 0, player);
                mc.playerController.windowClick(windowId, emptyHotbar + 36, 0, 0, player);
                inv.currentItem = emptyHotbar;
            } else {
                int slot = inv.currentItem;
                int windowId = player.inventoryContainer.windowId;
                mc.playerController.windowClick(windowId, foundSlot, 0, 0, player);
                mc.playerController.windowClick(windowId, slot + 36, 0, 0, player);
                inv.currentItem = slot;

                int emptySlot = inv.getFirstEmptyStack();
                if (emptySlot != -1) {
                    mc.playerController.windowClick(windowId, emptySlot, 0, 0, player);
                }
            }
            return true;
        } else if (useAE && !isCreative) {
            int emptyHotbar = -1;
            if (inv.getStackInSlot(inv.currentItem) == null) {
                emptyHotbar = inv.currentItem;
            } else {
                for (int i = 0; i < 9; i++) {
                    if (inv.getStackInSlot(i) == null) {
                        emptyHotbar = i;
                        break;
                    }
                }
            }
            if (player.isSneaking()) {
                result.stackSize = 1;
            } else {
                result.stackSize = result.getMaxStackSize();
            }

            if (emptyHotbar != -1) {
                inv.currentItem = emptyHotbar;
                ScienceNotLeisure.network.sendToServer(new WirelessPickBlock(result, inv.currentItem));
                return true;
            } else {
                int slot = inv.currentItem;
                int windowId = player.inventoryContainer.windowId;
                mc.playerController.windowClick(windowId, slot + 36, 0, 0, player);
                int emptySlot = inv.getFirstEmptyStack();
                if (emptySlot != -1) {
                    mc.playerController.windowClick(windowId, emptySlot, 0, 0, player);
                }
                ScienceNotLeisure.network.sendToServer(new WirelessPickBlock(result, inv.currentItem));
                return true;
            }
        }

        if (isCreative) {
            int emptyHotbar = -1;
            if (inv.getStackInSlot(inv.currentItem) == null) {
                emptyHotbar = inv.currentItem;
            } else {
                for (int i = 0; i < 9; i++) {
                    if (inv.getStackInSlot(i) == null) {
                        emptyHotbar = i;
                        break;
                    }
                }
            }
            if (emptyHotbar != -1) {
                int slotId = player.inventoryContainer.inventorySlots.size() - 9 + emptyHotbar;
                inv.setInventorySlotContents(emptyHotbar, result);
                inv.currentItem = emptyHotbar;
                mc.playerController.sendSlotPacket(result, slotId - backHandSlot);
                return true;
            } else {
                int slot = inv.currentItem;
                int slotIdHotbar = player.inventoryContainer.inventorySlots.size() - 9 + slot;
                inv.setInventorySlotContents(slot, result);
                mc.playerController.sendSlotPacket(result, slotIdHotbar - backHandSlot);
                return true;
            }
        }

        int emptyHotbar = -1;
        if (inv.getStackInSlot(inv.currentItem) == null) {
            emptyHotbar = inv.currentItem;
        } else {
            for (int i = 0; i < 9; i++) {
                if (inv.getStackInSlot(i) == null) {
                    emptyHotbar = i;
                    break;
                }
            }
        }

        if (emptyHotbar != -1) {
            int windowId = player.inventoryContainer.windowId;
            inv.setInventorySlotContents(emptyHotbar, result);
            inv.currentItem = emptyHotbar;
            mc.playerController.windowClick(windowId, emptyHotbar + 36, 0, 0, player);
            return true;
        } else {
            int slot = inv.currentItem;
            int windowId = player.inventoryContainer.windowId;
            mc.playerController.windowClick(windowId, slot + 36, 0, 0, player);
            mc.playerController.windowClick(windowId, slot + 36, 0, 0, player);

            int emptySlot = inv.getFirstEmptyStack();
            if (emptySlot != -1) {
                mc.playerController.windowClick(windowId, emptySlot, 0, 0, player);
            }
            return true;
        }
    }

}
