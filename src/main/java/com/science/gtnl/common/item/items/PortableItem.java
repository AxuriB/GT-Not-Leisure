package com.science.gtnl.common.item.items;

import static com.science.gtnl.ScienceNotLeisure.*;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.science.gtnl.CommonProxy;
import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.client.GTNLCreativeTabs;

public class PortableItem extends Item {

    public static final int BASIC = 0;
    public static final int ADVANCED = 1;
    public static final int FURNACE = 2;
    public static final int ANVIL = 3;
    public static final int ENDERCHEST = 4;
    public static final int ENCHANTING = 5;
    public static final int COMPRESSEDCHEST = 6;
    public static final int INFINITYCHEST = 7;

    public IIcon enchantingIcon;
    public IIcon basicIcon;
    public IIcon advancedIcon;
    public IIcon furnaceIcon;
    public IIcon anvilIcon;
    public IIcon enderChestIcon;
    public IIcon compressedChestIcon;
    public IIcon infinityChestIcon;

    public PortableItem() {
        super();
        this.setUnlocalizedName("PortableItem");
        this.setTextureName(RESOURCE_ROOT_ID + ":" + "PortableItem");
        this.setMaxStackSize(1);
        this.setCreativeTab(GTNLCreativeTabs.GTNotLeisureItem);
        this.setHasSubtypes(true);
        GTNLItemList.PortableBasicWorkBench.set(new ItemStack(this, 1, BASIC));
        GTNLItemList.PortableAdvancedWorkBench.set(new ItemStack(this, 1, ADVANCED));
        GTNLItemList.PortableFurnace.set(new ItemStack(this, 1, FURNACE));
        GTNLItemList.PortableAnvil.set(new ItemStack(this, 1, ANVIL));
        GTNLItemList.PortableEnderChest.set(new ItemStack(this, 1, ENDERCHEST));
        GTNLItemList.PortableCompressedChest.set(new ItemStack(this, 1, COMPRESSEDCHEST));
        GTNLItemList.PortableInfinityChest.set(new ItemStack(this, 1, INFINITYCHEST));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return "item.PortableItem." + itemStack.getItemDamage();
    }

    @Override
    public String getUnlocalizedName() {
        return "PortableItem";
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        basicIcon = iconRegister.registerIcon(RESOURCE_ROOT_ID + ":" + "PortableBasicWorkBench");
        advancedIcon = iconRegister.registerIcon(RESOURCE_ROOT_ID + ":" + "PortableAdvancedWorkBench");
        furnaceIcon = iconRegister.registerIcon(RESOURCE_ROOT_ID + ":" + "PortableFurnace");
        anvilIcon = iconRegister.registerIcon(RESOURCE_ROOT_ID + ":" + "PortableAnvil");
        enderChestIcon = iconRegister.registerIcon(RESOURCE_ROOT_ID + ":" + "PortableEnderChest");
        enchantingIcon = iconRegister.registerIcon(RESOURCE_ROOT_ID + ":" + "PortableEnchanting");
        compressedChestIcon = iconRegister.registerIcon(RESOURCE_ROOT_ID + ":" + "PortableCompressedChest");
        infinityChestIcon = iconRegister.registerIcon(RESOURCE_ROOT_ID + ":" + "PortableInfinityChest");
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        if (meta == BASIC) return basicIcon;
        if (meta == ADVANCED) return advancedIcon;
        if (meta == FURNACE) return furnaceIcon;
        if (meta == ANVIL) return anvilIcon;
        if (meta == ENDERCHEST) return enderChestIcon;
        if (meta == ENCHANTING) return enchantingIcon;
        if (meta == COMPRESSEDCHEST) return compressedChestIcon;
        if (meta == INFINITYCHEST) return infinityChestIcon;
        return basicIcon;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            int meta = stack.getItemDamage();
            if (meta == BASIC) {
                player.openGui(instance, CommonProxy.PortableBasicWorkBenchGUI, world, 0, 0, 0);
            } else if (meta == ADVANCED) {
                player.openGui(instance, CommonProxy.PortableAdvancedWorkBenchGUI, world, 0, 0, 0);
            } else if (meta == FURNACE) {
                player.openGui(instance, CommonProxy.PortableFurnaceGUI, world, 0, 0, 0);
            } else if (meta == ANVIL) {
                player.openGui(instance, CommonProxy.PortableAnvilGUI, world, 0, 0, 0);
            } else if (meta == ENDERCHEST) {
                player.openGui(instance, CommonProxy.PortableEnderChestGUI, world, 0, 0, 0);
            } else if (meta == ENCHANTING) {
                player.openGui(instance, CommonProxy.PortableEnchantingGUI, world, 0, 0, 0);
            } else if (meta == COMPRESSEDCHEST) {
                player.openGui(instance, CommonProxy.PortableCompressedChestGUI, world, 0, 0, 0);
            } else if (meta == INFINITYCHEST) {
                player.openGui(instance, CommonProxy.PortableInfinityChestGUI, world, 0, 0, 0);
            }
        }
        return stack;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int slot, boolean isSelected) {
        if (stack.getItemDamage() != FURNACE) return;

        IInventory inv = getFurnaceInventory(stack);
        boolean dirty = false;

        int burnTime = stack.hasTagCompound() ? stack.getTagCompound()
            .getInteger("BurnTime") : 0;
        int cookTime = stack.hasTagCompound() ? stack.getTagCompound()
            .getInteger("CookTime") : 0;
        int currentItemBurnTime = stack.hasTagCompound() ? stack.getTagCompound()
            .getInteger("CurrentItemBurnTime") : 0;

        ItemStack input = inv.getStackInSlot(0);
        ItemStack fuel = inv.getStackInSlot(1);
        ItemStack output = inv.getStackInSlot(2);

        if (burnTime > 0) {
            burnTime--;
        }

        boolean canSmelt = false;
        ItemStack result = null;
        if (input != null) {
            result = FurnaceRecipes.smelting()
                .getSmeltingResult(input);
            canSmelt = result != null && (output == null
                || (output.isItemEqual(result) && output.stackSize + result.stackSize <= output.getMaxStackSize()));
        }

        if (burnTime == 0) {
            currentItemBurnTime = 0;
            if (canSmelt && fuel != null && isItemFuel(fuel)) {
                currentItemBurnTime = burnTime = TileEntityFurnace.getItemBurnTime(fuel);
                if (fuel.stackSize == 1 && fuel.getItem()
                    .hasContainerItem(fuel)) {
                    inv.setInventorySlotContents(
                        1,
                        fuel.getItem()
                            .getContainerItem(fuel));
                } else {
                    fuel.stackSize--;
                    inv.setInventorySlotContents(1, fuel);
                    if (fuel.stackSize <= 0) inv.setInventorySlotContents(1, null);
                }
                dirty = true;
            }
        }

        if (input == null || !canSmelt) {
            cookTime = 0;
        }

        if (burnTime > 0 && canSmelt) {
            cookTime++;
            if (cookTime >= 200) {
                if (output == null) {
                    inv.setInventorySlotContents(2, result.copy());
                } else {
                    output.stackSize += result.stackSize;
                    inv.setInventorySlotContents(2, output);
                }
                input.stackSize--;
                inv.setInventorySlotContents(0, input);
                if (input.stackSize <= 0) inv.setInventorySlotContents(0, null);
                cookTime = 0;
                dirty = true;
            }
        } else {
            cookTime = Math.max(0, cookTime - 1);
            dirty = true;
        }

        if (dirty || burnTime != (stack.hasTagCompound() ? stack.getTagCompound()
            .getInteger("BurnTime") : 0)) {
            if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
            NBTTagCompound tag = stack.getTagCompound();
            tag.setInteger("CookTime", cookTime);
            tag.setInteger("BurnTime", burnTime);
            tag.setInteger("CurrentItemBurnTime", currentItemBurnTime);
            saveFurnaceInventory(stack, inv);
        }
    }

    public static IInventory getAdvancedInventory(ItemStack stack, int size) {
        InventoryBasic inv = new InventoryBasic("PortableAdvancedWorkbench", false, size);

        if (stack.hasTagCompound()) {
            NBTTagList list = stack.getTagCompound()
                .getTagList("Items", 10);
            for (int i = 0; i < list.tagCount(); i++) {
                NBTTagCompound itemTag = list.getCompoundTagAt(i);
                int slot = itemTag.getByte("Slot") & 255;
                if (slot < inv.getSizeInventory()) {
                    inv.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(itemTag));
                }
            }
        }

        return inv;
    }

    public static void saveAdvancedInventory(ItemStack stack, IInventory inv) {
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack s = inv.getStackInSlot(i);
            if (s != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                s.writeToNBT(tag);
                list.appendTag(tag);
            }
        }

        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound()
            .setTag("Items", list);
    }

    public static void saveInfinityInventory(@Nonnull ItemStack stack, @Nonnull IInventory inv) {
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack slotStack = inv.getStackInSlot(i);
            if (slotStack != null) {
                NBTTagCompound slotTag = new NBTTagCompound();
                slotTag.setShort("Slot", (short) i);
                slotStack.writeToNBT(slotTag);
                slotTag.setInteger("intCount", slotStack.stackSize);
                if (slotStack.hasTagCompound()) {
                    slotTag.setTag("tag", slotStack.getTagCompound());
                }
                list.appendTag(slotTag);
            }
        }

        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound()
            .setTag("Contents", list);
    }

    public static void loadInfinityInventory(@Nonnull ItemStack stack, @Nonnull IInventory inv) {
        if (!stack.hasTagCompound()) return;
        NBTTagCompound compound = stack.getTagCompound();
        if (!compound.hasKey("Contents")) return;

        NBTTagList list = compound.getTagList("Contents", 10);
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound slotTag = list.getCompoundTagAt(i);
            int slot = slotTag.getShort("Slot");
            if (slot >= 0 && slot < inv.getSizeInventory()) {
                ItemStack slotStack = ItemStack.loadItemStackFromNBT(slotTag);
                if (slotStack != null) {
                    slotStack.stackSize = slotTag.getInteger("intCount");
                    if (slotTag.hasKey("tag")) {
                        slotStack.setTagCompound(slotTag.getCompoundTag("tag"));
                    }
                    inv.setInventorySlotContents(slot, slotStack);
                }
            }
        }
    }

    public static IInventory getFurnaceInventory(ItemStack stack) {
        InventoryBasic inv = new InventoryBasic("PortableFurnace", false, 3);

        if (stack.hasTagCompound()) {
            NBTTagList list = stack.getTagCompound()
                .getTagList("Items", 10);
            for (int i = 0; i < list.tagCount(); i++) {
                NBTTagCompound itemTag = list.getCompoundTagAt(i);
                int slot = itemTag.getByte("Slot") & 255;
                if (slot < inv.getSizeInventory()) {
                    inv.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(itemTag));
                }
            }
        }

        return inv;
    }

    public static void saveFurnaceInventory(ItemStack stack, IInventory inv) {
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack s = inv.getStackInSlot(i);
            if (s != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                s.writeToNBT(tag);
                list.appendTag(tag);
            }
        }

        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound()
            .setTag("Items", list);
    }

    public static boolean isItemFuel(ItemStack stack) {
        return TileEntityFurnace.isItemFuel(stack);
    }

    @Override
    public void getSubItems(Item item, net.minecraft.creativetab.CreativeTabs tab, List<ItemStack> list) {
        list.add(new ItemStack(item, 1, BASIC));
        list.add(new ItemStack(item, 1, ADVANCED));
        list.add(new ItemStack(item, 1, FURNACE));
        list.add(new ItemStack(item, 1, ANVIL));
        list.add(new ItemStack(item, 1, ENDERCHEST));
        list.add(new ItemStack(item, 1, ENCHANTING));
        list.add(new ItemStack(item, 1, COMPRESSEDCHEST));
        list.add(new ItemStack(item, 1, INFINITYCHEST));
    }
}
