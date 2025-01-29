package com.science.gtnl.common.item.ReAvaritia;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.common.util.ForgeDirection;

import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.client.CreativeTabsLoader;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.entity.EntityImmortalItem;

public class InfinityPickaxe extends ItemPickaxe {

    private static final ToolMaterial INFINITY = EnumHelper.addToolMaterial("INFINITY", 32, 9999, 9999F, 49.0F, 200);
    public static final Material[] MATERIALS = new Material[] { Material.rock, Material.iron, Material.ice,
        Material.glass, Material.piston, Material.anvil, Material.grass, Material.ground, Material.sand, Material.snow,
        Material.craftedSnow, Material.clay };

    @SideOnly(Side.CLIENT)
    private IIcon hammer;

    public InfinityPickaxe() {
        super(INFINITY);
        this.setUnlocalizedName("InfinityPickaxe");
        this.setCreativeTab(CreativeTabsLoader.ReAvaritia);
        this.setTextureName("sciencenotleisure:InfinityPickaxe");
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        super.setDamage(stack, 0);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
        ItemStack pick = new ItemStack(this);
        pick.addEnchantment(Enchantment.fortune, 10);
        list.add(pick);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumHelper.addRarity("COSMIC", EnumChatFormatting.RED, "Cosmic");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.itemIcon = ir.registerIcon("sciencenotleisure:InfinityPickaxe");
        this.hammer = ir.registerIcon("sciencenotleisure:InfinityHammer");
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        boolean isHammerMode = stack.hasTagCompound() && stack.getTagCompound()
            .getBoolean("HammerMode");
        return isHammerMode ? hammer : itemIcon;
    }

    @Override
    public IIcon getIconIndex(ItemStack stack) {
        return getIcon(stack, 0);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (player.isSneaking()) {
            NBTTagCompound tags = stack.getTagCompound();
            if (tags == null) {
                tags = new NBTTagCompound();
                stack.setTagCompound(tags);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack) < 10) {
                stack.addEnchantment(Enchantment.fortune, 10);
            }

            boolean isHammerMode = !tags.getBoolean("HammerMode");
            tags.setBoolean("HammerMode", isHammerMode);
            player.swingItem();

            if (world.isRemote) {
                String key = isHammerMode ? TextLocalization.Tooltip_Infinity_Mode_2
                    : TextLocalization.Tooltip_Infinity_Mode_1;
                showSubtitle(key);
            }
        }
        return stack;
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        boolean isHammerMode = stack.hasTagCompound() && stack.getTagCompound()
            .getBoolean("HammerMode");

        return isHammerMode ? 5.0F
            : ForgeHooks.isToolEffective(stack, block, meta) ? efficiencyOnProperMaterial
                : Math.max(func_150893_a(stack, block), 6.0F);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
        boolean isHammerMode = stack.hasTagCompound() && stack.getTagCompound()
            .getBoolean("HammerMode");

        if (isHammerMode) {
            MovingObjectPosition raycast = ToolHelper.raytraceFromEntity(player.worldObj, player, true, 10);
            if (raycast != null) {
                breakOtherBlock(player, stack, x, y, z, x, y, z, raycast.sideHit);
            }
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    private void showSubtitle(String messageKey) {
        String localized = StatCollector.translateToLocal(messageKey);
        ChatComponentText text = new ChatComponentText(EnumChatFormatting.WHITE + localized);
        Minecraft.getMinecraft().ingameGUI.func_110326_a(text.getFormattedText(), true);
    }

    private void breakOtherBlock(EntityPlayer player, ItemStack stack, int x, int y, int z, int originX, int originY,
        int originZ, int side) {
        World world = player.worldObj;
        Material mat = world.getBlock(x, y, z)
            .getMaterial();
        if (!ToolHelper.isRightMaterial(mat, MATERIALS)) return;

        ForgeDirection direction = ForgeDirection.getOrientation(side);
        int fortune = EnchantmentHelper.getFortuneModifier(player);
        boolean silk = EnchantmentHelper.getSilkTouchModifier(player);
        boolean doY = direction.offsetY == 0;

        ToolHelper.removeBlocksInIteration(
            player,
            stack,
            world,
            x,
            y,
            z,
            -8,
            doY ? -1 : -8,
            -8,
            8,
            doY ? 14 : 8,
            8,
            null,
            MATERIALS,
            silk,
            fortune,
            false);
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        return new EntityImmortalItem(world, location, itemstack);
    }

    @Override
    public boolean hasEffect(ItemStack par1ItemStack, int pass) {
        return true;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (player.getFoodStats()
                .needFood()
                || player.getFoodStats()
                    .getSaturationLevel() < 20.0F) {
                player.getFoodStats()
                    .addStats(20, 20.0F);
            }
        }
    }
}
