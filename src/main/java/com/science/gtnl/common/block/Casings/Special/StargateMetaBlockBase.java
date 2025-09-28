package com.science.gtnl.common.block.Casings.Special;

import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.science.gtnl.client.GTNLCreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class StargateMetaBlockBase extends Block {

    public String[] TextureName;
    public IIcon[] Texture;

    public StargateMetaBlockBase(String UnlocalizedName, String[] TextureName) {
        super(Material.iron);
        this.setHardness(1.0F);
        this.setResistance(6000000.0F);
        this.setBlockName(UnlocalizedName);
        this.setCreativeTab(GTNLCreativeTabs.GTNotLeisureBlock);
        this.TextureName = TextureName;
        this.setBlockTextureName(RESOURCE_ROOT_ID + ":" + "Stargate/" + "Stargate_Coil_Compressed");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        if (meta < 0 || meta >= this.Texture.length) {
            meta = 0;
        }

        return this.Texture[meta];
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 0; i < TextureName.length - 1; i++) {
            list.add(new ItemStack(itemIn, 1, i));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister reg) {
        this.Texture = new IIcon[TextureName.length];

        for (int i = 0; i < this.Texture.length; ++i) {
            this.Texture[i] = reg.registerIcon(this.getTextureName() + "_" + TextureName[i]);
        }
    }
}
