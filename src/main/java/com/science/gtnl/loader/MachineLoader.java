package com.science.gtnl.loader;

import static com.science.gtnl.common.CustomItemList.MTEInfinityHatchOutputBusME;
import static com.science.gtnl.common.CustomItemList.MTEInfinityHatchOutputME;
import static com.science.gtnl.common.CustomItemList.MTEIntegratedOutputHatchME;
import static com.science.gtnl.common.CustomItemList.hatch_AutoSterileMaintenanceHatch;

import net.minecraft.item.ItemStack;

import com.science.gtnl.Utils.TextLocalization;
import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.common.hatch.MTEHatchCustomFluid;
import com.science.gtnl.common.hatch.MTEHatchSterileMaintenance;
import com.science.gtnl.common.hatch.MTEInfinityHatchOutputBusME;
import com.science.gtnl.common.hatch.MTEInfinityHatchOutputME;
import com.science.gtnl.common.hatch.MTEIntegratedOutputHatchME;
import com.science.gtnl.common.hatch.MTEQuadrupleOutputHatch;
import com.science.gtnl.common.machine.BloodSoulSacrificialArray;
import com.science.gtnl.common.machine.GenerationEarthEngine;
import com.science.gtnl.common.machine.RealArtificialStar;
import com.science.gtnl.common.machine.SteamMulti.LargeSteamCircuitAssembler;
import com.science.gtnl.common.machine.TeleportationArrayToAlfheim;

import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.util.minecraft.FluidUtils;
import tectech.thing.metaTileEntity.hatch.MTEHatchCapacitor;
import tectech.thing.metaTileEntity.hatch.MTEHatchRack;

public class MachineLoader {

    public static ItemStack LargeSteamCircuitAssembler;
    public static ItemStack GenerationEarthEngine;
    public static ItemStack BloodSoulSacrificialArray;
    public static ItemStack TeleportationArrayToAlfheim;
    public static ItemStack RealArtificialStar;

    public static void loadMachines() {
        hatch_AutoSterileMaintenanceHatch.set(
            new MTEHatchSterileMaintenance(
                21001,
                "gtnl.sterile.maintenance",
                "Auto-Taping Sterile Maintenance Hatch",
                14).getStackForm(1L));

        MTEInfinityHatchOutputBusME.set(
            new MTEInfinityHatchOutputBusME(21002, "gtnl.ae2.InfinityOutputBusME", "Infinity Output Bus (ME)", 4)
                .getStackForm(1L));

        MTEInfinityHatchOutputME.set(
            new MTEInfinityHatchOutputME(21003, "gtnl.ae2.InfinityOutputME", "Infinity Output Hatch (ME)", 3)
                .getStackForm(1L));

        MTEIntegratedOutputHatchME.set(
            new MTEIntegratedOutputHatchME(21004, "gtnl.ae2.IntegratedOutputHatchME", "Integrated Output Hatch (ME)")
                .getStackForm(1L));

        LargeSteamCircuitAssembler = new LargeSteamCircuitAssembler(
            21005,
            "NameLargeSteamCircuitAssembler",
            TextLocalization.NameLargeSteamCircuitAssembler).getStackForm(1);
        GTNLItemList.LargeSteamCircuitAssembler.set(LargeSteamCircuitAssembler);

        GenerationEarthEngine = new GenerationEarthEngine(
            21006,
            "NameGenerationEarthEngine",
            TextLocalization.NameGenerationEarthEngine).getStackForm(1);
        GTNLItemList.GenerationEarthEngine.set(GenerationEarthEngine);

        BloodSoulSacrificialArray = new BloodSoulSacrificialArray(
            21007,
            "NameBloodSoulSacrificialArray",
            TextLocalization.NameBloodSoulSacrificialArray).getStackForm(1);
        GTNLItemList.BloodSoulSacrificialArray.set(BloodSoulSacrificialArray);

        RealArtificialStar = new RealArtificialStar(
            21008,
            "NameRealArtificialStar",
            TextLocalization.NameRealArtificialStar).getStackForm(1);
        GTNLItemList.RealArtificialStar.set(RealArtificialStar);

        TeleportationArrayToAlfheim = new TeleportationArrayToAlfheim(
            21009,
            "NameTeleportationArrayToAlfheim",
            TextLocalization.NameTeleportationArrayToAlfheim).getStackForm(1);
        GTNLItemList.TeleportationArrayToAlfheim.set(TeleportationArrayToAlfheim);

        MTEHatchRack.run();
        MTEHatchCapacitor.run();
    }

    public static void registerMTEHatch() {
        GTNLItemList.QuadrupleOutputHatchEV.set(
            new MTEQuadrupleOutputHatch(21500, "gtnl.Hatch.QuadrupleOutputHatchEV", "Quadruple Output Hatch (EV)", 4)
                .getStackForm(1L));

        GTNLItemList.FluidManaInputHatch.set(
            new MTEHatchCustomFluid(
                FluidUtils.getFluidStack("fluidmana", 1)
                    .getFluid(),
                512000,
                21501,
                "gtnl.Hatch.FluidManaInputHatch",
                "Fluid Mana Input Hatch",
                5).getStackForm(1L));
    }

    public static void run() {
        Logger.INFO("GTNL Content | Registering Custom MTE Hatches.");
        registerMTEHatch();
    }
}
