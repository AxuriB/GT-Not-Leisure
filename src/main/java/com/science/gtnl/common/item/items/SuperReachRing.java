package com.science.gtnl.common.item.items;

import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.client.GTNLCreativeTabs;

import baubles.api.BaubleType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.common.Botania;
import vazkii.botania.common.item.equipment.bauble.ItemBauble;

public class SuperReachRing extends ItemBauble {

    public SuperReachRing() {
        super("SuperReachRing");
        this.setTextureName(RESOURCE_ROOT_ID + ":" + "SuperReachRing");
        this.setCreativeTab(GTNLCreativeTabs.GTNotLeisureItem);
        GTNLItemList.SuperReachRing.set(new ItemStack(this, 1));
    }

    @Override
    public void onEquippedOrLoadedIntoWorld(ItemStack stack, EntityLivingBase player) {
        Botania.proxy.setExtraReach(player, 100F);
    }

    @Override
    public void onUnequipped(ItemStack stack, EntityLivingBase player) {
        Botania.proxy.setExtraReach(player, -100F);
    }

    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        return BaubleType.RING;
    }

    @Override
    public String getUnlocalizedNameInefficiently(ItemStack itemStack) {
        String s = this.getUnlocalizedName(itemStack);
        return s == null ? "" : StatCollector.translateToLocal(s);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon(this.getIconString());
    }

}
