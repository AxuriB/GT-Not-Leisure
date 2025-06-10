package com.science.gtnl.common.block.Casings.Glass;

import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;
import static com.science.gtnl.Utils.text.TextUtils.texter;
import static com.science.gtnl.common.block.Casings.Glass.ItemBlockGlass.MetaBlockSet;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.science.gtnl.client.GTNLCreativeTabs;
import com.science.gtnl.common.block.Casings.BlockStaticDataClientOnly;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetaBlockGlass extends Block {

    // region Constructors
    protected MetaBlockGlass(Material materialIn) {
        super(materialIn);
    }

    public MetaBlockGlass() {
        this(Material.glass);
        this.setHardness(1.0F);
        this.setLightOpacity(0);
        this.setResistance(5.0F);
        this.setCreativeTab(GTNLCreativeTabs.GTNotLeisureBlock);
    }

    public MetaBlockGlass(String unlocalizedName, String localName) {
        this();
        this.unlocalizedName = unlocalizedName;
        texter(localName, "MetaBlockGlass." + unlocalizedName + ".name");
    }

    // endregion
    // -----------------------
    // region member variables

    public String unlocalizedName;

    // endregion
    // -----------------------
    // region getters

    @Override
    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    // endregion
    // -----------------------
    // region setters

    public void setUnlocalizedName(String aUnlocalizedName) {
        this.unlocalizedName = aUnlocalizedName;
    }

    // endregion
    // -----------------------
    // region Overrides
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return meta < BlockStaticDataClientOnly.iconsBlockMapGlass.size()
            ? BlockStaticDataClientOnly.iconsBlockMapGlass.get(meta)
            : BlockStaticDataClientOnly.iconsBlockMapGlass.get(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.blockIcon = reg.registerIcon(RESOURCE_ROOT_ID + ":" + "MetaBlockGlass/0");
        for (int Meta : MetaBlockSet) {
            BlockStaticDataClientOnly.iconsBlockMapGlass
                .put(Meta, reg.registerIcon(RESOURCE_ROOT_ID + ":" + "MetaBlockGlass/" + Meta));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item aItem, CreativeTabs aCreativeTabs, List list) {
        for (int Meta : MetaBlockSet) {
            list.add(new ItemStack(aItem, 1, Meta));
        }
    }

    @Override
    public boolean isNormalCube(IBlockAccess aWorld, int aX, int aY, int aZ) {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockAccess worldIn, int x, int y, int z, int side) {
        Block block = worldIn.getBlock(x, y, z);

        if (worldIn.getBlockMetadata(x, y, z) != worldIn.getBlockMetadata(
            x - Facing.offsetsXForSide[side],
            y - Facing.offsetsYForSide[side],
            z - Facing.offsetsZForSide[side])) {
            return true;
        }

        if (block == this) {
            return false;
        }

        return super.shouldSideBeRendered(worldIn, x, y, z, side);
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

    // endregion
}
