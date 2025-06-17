package com.science.gtnl.common.block.Casings.Column;

import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;
import static com.science.gtnl.Utils.text.TextUtils.texter;
import static com.science.gtnl.common.block.Casings.Column.ItemBlockColumn.MetaBlockSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.science.gtnl.client.GTNLCreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetaBlockColumn extends Block {

    public String[] TextureName;
    public Map<Integer, IIcon[]> TextureMap;

    public MetaBlockColumn() {
        super(Material.iron);
        this.setHardness(1.0F);
        this.setResistance(6000000.0F);
        this.TextureName = new String[] { "Side", "Top", "Bottom" };
        this.TextureMap = new HashMap<>();
        this.setCreativeTab(GTNLCreativeTabs.GTNotLeisureBlock);
    }

    public MetaBlockColumn(String unlocalizedName, String localName) {
        this();
        this.setBlockName(unlocalizedName);
        texter(localName, "MetaBlockColumn." + unlocalizedName + ".name");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        IIcon[] textures = this.TextureMap.get(meta);
        if (textures == null) {
            textures = this.TextureMap.get(0); // 如果没有找到对应的 meta，默认返回 meta 为 0 的纹理
        }
        return side == 1 ? textures[1] // 顶部纹理
            : (side == 0 ? textures[2] // 底部纹理
                : textures[0]); // 侧面纹理
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister reg) {
        for (int meta : MetaBlockSet) {
            IIcon[] textures = new IIcon[TextureName.length];
            for (int i = 0; i < this.TextureName.length; ++i) {
                textures[i] = reg
                    .registerIcon(RESOURCE_ROOT_ID + ":" + "MetaBlockColumn/" + meta + "_" + TextureName[i]);
            }
            this.TextureMap.put(meta, textures);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
        for (int meta : MetaBlockSet) {
            list.add(new ItemStack(item, 1, meta));
        }
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    @Override
    public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
        return false;
    }

    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
        return false;
    }
}
