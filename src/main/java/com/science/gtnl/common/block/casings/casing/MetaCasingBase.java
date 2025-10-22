package com.science.gtnl.common.block.casings.casing;

import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.science.gtnl.api.IMetaBlock;
import com.science.gtnl.client.GTNLCreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class MetaCasingBase extends Block implements IMetaBlock {

    public final Set<Integer> usedMetaSet = new HashSet<>(16);
    public final Map<Integer, String[]> tooltipsMap = new HashMap<>(16);
    public final Map<Integer, IIcon> iconMap = new HashMap<>(16);
    public final String unlocalizedName;

    public MetaCasingBase(String unlocalizedName) {
        super(Material.iron);
        this.unlocalizedName = unlocalizedName;
        this.setCreativeTab(GTNLCreativeTabs.GTNotLeisureBlock);
    }

    @Override
    public abstract boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z);

    @Override
    public abstract boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z);

    @Override
    public abstract boolean isNormalCube(IBlockAccess world, int x, int y, int z);

    @Override
    public Set<Integer> getUsedMetaSet() {
        return usedMetaSet;
    }

    @Override
    public Map<Integer, String[]> getTooltipsMap() {
        return tooltipsMap;
    }

    @Override
    public Map<Integer, IIcon> getIconMap() {
        return iconMap;
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + this.unlocalizedName;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return iconMap.get(meta);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        Map<Integer, IIcon> iconMap;
        Set<Integer> usedMetaSet;
        if ((iconMap = this.iconMap) == null || (usedMetaSet = this.usedMetaSet) == null) {
            throw new NullPointerException("Null in " + this.unlocalizedName);
        }
        String root = RESOURCE_ROOT_ID + ":" + this.unlocalizedName + "/";
        this.blockIcon = reg.registerIcon(root + "0");
        for (int Meta : usedMetaSet) {
            iconMap.put(Meta, reg.registerIcon(root + Meta));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item aItem, CreativeTabs aCreativeTabs, List<ItemStack> list) {
        Set<Integer> usedMetaSet;
        if ((usedMetaSet = this.usedMetaSet) == null) {
            throw new NullPointerException("Null in " + this.unlocalizedName);
        }
        for (int Meta : usedMetaSet) {
            list.add(new ItemStack(aItem, 1, Meta));
        }
    }

    @Override
    public int damageDropped(int metadata) {
        return metadata;
    }

    @Override
    public int getDamageValue(World aWorld, int aX, int aY, int aZ) {
        return aWorld.getBlockMetadata(aX, aY, aZ);
    }

}
