package com.science.gtnl.common.item.items;

import static com.science.gtnl.ScienceNotLeisure.*;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

import org.lwjgl.input.Keyboard;

import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.client.GTNLCreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Stick extends Item {

    public IIcon defaultIcon;
    public ItemStack fakeItem;

    public Stick() {
        this.setMaxStackSize(64);
        this.setUnlocalizedName("Stick");
        this.setCreativeTab(GTNLCreativeTabs.GTNotLeisureItem);
        this.setTextureName(RESOURCE_ROOT_ID + ":" + "Stick");
        GTNLItemList.Stick.set(new ItemStack(this, 1));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.defaultIcon = reg.registerIcon(RESOURCE_ROOT_ID + ":" + "Stick");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
        if (isShiftDown()) return defaultIcon;
        ItemStack disguised = getDisguisedStack(stack);
        if (disguised != null) {
            return disguised.getIconIndex();
        }
        return defaultIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack) {
        if (isShiftDown()) return defaultIcon;
        ItemStack disguised = getDisguisedStack(stack);
        if (disguised != null) {
            return disguised.getIconIndex();
        }
        return defaultIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        return fakeItem != null ? fakeItem.getItem()
            .getIconFromDamage(damage) : defaultIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int p_82790_2_) {
        return fakeItem != null ? fakeItem.getItem()
            .getColorFromItemStack(fakeItem, p_82790_2_) : 16777215;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (isShiftDown()) {
            return super.getItemStackDisplayName(stack);
        }
        ItemStack disguised = getDisguisedStack(stack);
        if (disguised != null) {
            return disguised.getDisplayName();
        }
        return super.getItemStackDisplayName(stack);
    }

    @Override
    public int getDamage(ItemStack stack) {
        if (isShiftDown()) {
            return super.getDamage(stack);
        }
        ItemStack disguised = getDisguisedStack(stack);
        if (disguised != null) {
            return disguised.getItemDamage();
        }
        return super.getDamage(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getSpriteNumber() {
        return fakeItem != null ? fakeItem.getItemSpriteNumber() : 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return fakeItem != null && fakeItem.getItem()
            .requiresMultipleRenderPasses();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean adv) {
        if (stack.hasTagCompound()) {
            if (isShiftDown()) {
                ItemStack disguised = getDisguisedStack(stack);
                if (disguised != null) {
                    list.add("§5§o仔细检查," + disguised.getDisplayName() + "是一个由纸板做的假东西，希望你能得到一张收据");
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack, int pass) {
        return stack.hasTagCompound() && stack.getTagCompound()
            .getBoolean("Enchanted");
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
        list.add(setDisguisedStack(new ItemStack(Items.diamond)));
        list.add(setDisguisedStack(new ItemStack(Blocks.diamond_block)));
    }

    public ItemStack getDisguisedStack(ItemStack stack) {
        if (!stack.hasTagCompound()) return null;
        NBTTagCompound tag = stack.getTagCompound();

        String id = tag.getString("ID");
        int meta = tag.getInteger("Meta");

        Item item = (Item) Item.itemRegistry.getObject(id);
        if (item != null) {
            return new ItemStack(item, 1, meta);
        }
        return null;
    }

    public ItemStack setDisguisedStack(ItemStack disguised) {
        if (disguised == null || disguised.getItem() == null) {
            return new ItemStack(this);
        }

        ItemStack stack = new ItemStack(this);

        NBTTagCompound tag = new NBTTagCompound();

        String regName = Item.itemRegistry.getNameForObject(disguised.getItem());
        if (regName == null) {
            return stack;
        }

        tag.setString("ID", regName);
        tag.setInteger("Meta", disguised.getItemDamage());

        stack.setTagCompound(tag);
        ((Stick) stack.getItem()).fakeItem = disguised;
        return stack;
    }

    public static boolean isShiftDown() {
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }
}
