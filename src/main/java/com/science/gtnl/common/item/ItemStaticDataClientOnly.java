package com.science.gtnl.common.item;

import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

@SideOnly(Side.CLIENT)
public class ItemStaticDataClientOnly {

    @SideOnly(Side.CLIENT)
    public static Int2ObjectMap<IIcon> iconsMapMetaItem01 = new Int2ObjectOpenHashMap<>();

    @SideOnly(Side.CLIENT)
    public static Int2ObjectMap<IIcon> iconsMapElectricProspectorTool = new Int2ObjectOpenHashMap<>();

    @SideOnly(Side.CLIENT)
    public static Int2ObjectMap<IIcon> iconsMapIzumik = new Int2ObjectOpenHashMap<>();

}
