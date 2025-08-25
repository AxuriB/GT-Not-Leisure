package com.science.gtnl.common.item.items;

import static com.science.gtnl.ScienceNotLeisure.*;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.science.gtnl.CommonProxy;
import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.Utils.gui.portableWorkbench.InventoryInfinityChest;
import com.science.gtnl.client.GTNLCreativeTabs;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PortableItem extends Item {

    public PortableItem() {
        super();
        this.setUnlocalizedName("PortableItem");
        this.setTextureName(RESOURCE_ROOT_ID + ":" + "PortableItem");
        this.setMaxStackSize(1);
        this.setCreativeTab(GTNLCreativeTabs.GTNotLeisureItem);
        this.setHasSubtypes(true);
        for (PortableType type : PortableType.values()) {
            GTNLItemList.valueOf(type.getUnlocalizedName())
                .set(new ItemStack(this, 1, type.ordinal()));
        }
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
        for (PortableType type : PortableType.values()) {
            type.icon = iconRegister.registerIcon(RESOURCE_ROOT_ID + ":" + type.getUnlocalizedName());
        }
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        PortableType type = PortableType.byMeta(meta);
        return type != null ? type.icon : null;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            PortableType type = PortableType.byMeta(stack.getItemDamage());
            if (type != null && type.guiId >= 0) {
                player.openGui(instance, type.guiId, world, 0, 0, 0);
            }
        }
        return stack;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int slot, boolean isSelected) {
        if (stack.getItemDamage() != PortableType.FURNACE.getMeta()) return;

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
            if (canSmelt && fuel != null && TileEntityFurnace.isItemFuel(stack)) {
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

    public static IInventory getInventory(ItemStack stack, int size) {
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

    public static InventoryInfinityChest getInventory(ItemStack stack) {
        InventoryInfinityChest inv = new InventoryInfinityChest(64);

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

    public static void saveInventory(ItemStack stack, IInventory inv) {
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

    public static InventoryInfinityChest getInfinityInventory(@Nonnull ItemStack stack) {
        InventoryInfinityChest inv = new InventoryInfinityChest(Integer.MAX_VALUE);
        if (!stack.hasTagCompound()) return inv;
        NBTTagCompound compound = stack.getTagCompound();
        if (!compound.hasKey("Contents")) return inv;

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
        return inv;
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

    @Override
    public void getSubItems(Item item, net.minecraft.creativetab.CreativeTabs tab, List<ItemStack> list) {
        for (PortableType type : PortableType.values()) {
            list.add(new ItemStack(item, 1, type.ordinal()));
        }
    }

    @SubscribeEvent
    public void onItemEntityDamage(LivingHurtEvent event) {
        if (!(event.entity instanceof EntityItem entityItem)) return;
        ItemStack stack = entityItem.getEntityItem();
        if (stack == null) return;

        if (!(stack.getItem() instanceof PortableItem)) return;

        int meta = stack.getItemDamage();

        boolean isProtected = meta == PortableType.OBSIDIAN.getMeta() || meta == PortableType.NETHERITE.getMeta()
            || meta == PortableType.DARKSTEEL.getMeta();

        if (!isProtected) return;

        DamageSource source = event.source;
        if (source.isFireDamage() || source.isExplosion()) {
            event.setCanceled(true);
        }
    }

    public enum PortableType {

        BASIC("BasicWorkBench", CommonProxy.PortableBasicWorkBenchGUI),
        ADVANCED("AdvancedWorkBench", CommonProxy.PortableAdvancedWorkBenchGUI),
        FURNACE("Furnace", CommonProxy.PortableFurnaceGUI),
        ANVIL("Anvil", CommonProxy.PortableAnvilGUI),
        ENDERCHEST("EnderChest", CommonProxy.PortableEnderChestGUI),
        ENCHANTING("EnchantingTable", CommonProxy.PortableEnchantingGUI),
        COMPRESSEDCHEST("CompressedChest", CommonProxy.PortableCompressedChestGUI),
        INFINITYCHEST("InfinityChest", CommonProxy.PortableInfinityChestGUI),
        COPPER("CopperChest", CommonProxy.PortableCopperChestGUI),
        IRON("IronChest", CommonProxy.PortableIronChestGUI),
        SILVER("SilverChest", CommonProxy.PortableSilverChestGUI),
        STEEL("SteelChest", CommonProxy.PortableSteelChestGUI),
        GOLD("GoldenChest", CommonProxy.PortableGoldenChestGUI),
        DIAMOND("DiamondChest", CommonProxy.PortableDiamondChestGUI),
        CRYSTAL("CrystalChest", CommonProxy.PortableCrystalChestGUI),
        OBSIDIAN("ObsidianChest", CommonProxy.PortableObsidianChestGUI),
        NETHERITE("NetheriteChest", CommonProxy.PortableNetheriteChestGUI),
        DARKSTEEL("DarkSteelChest", CommonProxy.PortableDarkSteelChestGUI);

        private final String baseName;
        public final int guiId;
        public IIcon icon;

        PortableType(String baseName, int guiId) {
            this.baseName = baseName;
            this.guiId = guiId;
        }

        public int getMeta() {
            return ordinal();
        }

        public String getUnlocalizedName() {
            return "Portable" + baseName;
        }

        public static PortableType byMeta(int meta) {
            return (meta >= 0 && meta < values().length) ? values()[meta] : null;
        }
    }
}
