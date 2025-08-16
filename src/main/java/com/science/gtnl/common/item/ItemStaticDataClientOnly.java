package com.science.gtnl.common.item;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemStaticDataClientOnly {

    @SideOnly(Side.CLIENT)
    public static Map<Integer, IIcon> iconsMapMetaItem01 = new HashMap<>();

    @SideOnly(Side.CLIENT)
    public static Map<Integer, IIcon> iconsMapElectricProspectorTool = new HashMap<>();

    @SideOnly(Side.CLIENT)
    public static Map<Integer, IIcon> iconsMapIzumik = new HashMap<>();

}
