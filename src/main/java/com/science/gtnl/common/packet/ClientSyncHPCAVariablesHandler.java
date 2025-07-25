package com.science.gtnl.common.packet;

import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.HighPerformanceComputationArray;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;

@SideOnly(Side.CLIENT)
public class ClientSyncHPCAVariablesHandler {

    public static void apply(int x, int y, int z, UUID uuid, int totalLens, boolean mMachine) {
        World world = Minecraft.getMinecraft().theWorld;
        if (world != null) {
            TileEntity te = world.getTileEntity(x, y, z);
            if (te instanceof IGregTechTileEntity gtTE
                && gtTE.getMetaTileEntity() instanceof HighPerformanceComputationArray hpca) {
                hpca.randomUUID = uuid;
                hpca.totalLens = totalLens;
                hpca.mMachine = mMachine;
            }
        }
    }
}
