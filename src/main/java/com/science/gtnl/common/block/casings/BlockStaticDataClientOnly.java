package com.science.gtnl.common.block.casings;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockStaticDataClientOnly {

    @SideOnly(Side.CLIENT)
    public static Map<Integer, IIcon> iconsBlockMapBase = new HashMap<>();

    @SideOnly(Side.CLIENT)
    public static Map<Integer, IIcon> iconsBlockMapGlow = new HashMap<>();

    @SideOnly(Side.CLIENT)
    public static Map<Integer, IIcon> iconsBlockMapGlass = new HashMap<>();

}
