package com.science.gtnl.common.recipe.GregTech;

import static com.dreammaster.scripts.IScriptLoader.missing;
import static gregtech.api.enums.Mods.*;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.dreammaster.item.NHItemList;
import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.Utils.item.ItemUtils;
import com.science.gtnl.api.IRecipePool;
import com.science.gtnl.common.material.MaterialPool;
import com.science.gtnl.config.MainConfig;

import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsBotania;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.metatileentity.implementations.MTEBasicMachineWithRecipe;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gtPlusPlus.core.material.MaterialsAlloy;
import gtPlusPlus.xmod.bop.blocks.BOPBlockRegistrator;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;

public class CraftingTableRecipes implements IRecipePool {

    public static long bitsd = GTModHandler.RecipeBits.NOT_REMOVABLE | GTModHandler.RecipeBits.REVERSIBLE
        | GTModHandler.RecipeBits.BUFFERED
        | GTModHandler.RecipeBits.DISMANTLEABLE;
    public static long recipeFlags = GTModHandler.RecipeBits.MIRRORED | GTModHandler.RecipeBits.KEEPNBT
        | GTModHandler.RecipeBits.BUFFERED
        | GTModHandler.RecipeBits.DISMANTLEABLE;

    @Override
    public void loadRecipes() {

        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeSteamCrusher.get(1),
            new Object[] { "ABA", "CDC", "EBE", 'A',
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Bronze, 1), 'B',
                ItemList.Component_Sawblade_Diamond.get(1), 'C', GregtechItemList.Controller_SteamMaceratorMulti.get(1),
                'D', GTOreDictUnificator.get(OrePrefixes.pipeHuge, Materials.Bronze, 1), 'E',
                GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.CobaltBrass, 1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.BronzeBrickCasing.get(2),
            new Object[] { "AAA", "DBD", "CCC", 'A', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 1),
                'B', "craftingToolWrench", 'C', new ItemStack(Blocks.brick_block, 1), 'D',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.CrudeSteel, 1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.SteelBrickCasing.get(2),
            new Object[] { "AAA", "DBD", "CCC", 'A', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1),
                'B', "craftingToolWrench", 'C', new ItemStack(Blocks.brick_block, 1), 'D',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.CrudeSteel, 1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.CheatOreProcessingFactory.get(1),
            new Object[] { "AAA", "ABA", "AAA", 'A', GTNLItemList.StargateSingularity.get(1), 'B',
                GTNLItemList.LargeSteamCrusher.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeSteamFurnace.get(1),
            new Object[] { "ABA", "CDC", "AEA", 'A', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 1),
                'B', ItemList.Casing_Pipe_Bronze.get(1), 'C', ItemList.Casing_Firebox_Bronze.get(1), 'D',
                new ItemStack(Items.cauldron, 1), 'E', ItemList.Machine_Bronze_Furnace.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeSteamAlloySmelter.get(1),
            new Object[] { "ABA", "CDC", "AEA", 'A', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 1),
                'B', GTNLItemList.BronzeTurbine.get(1), 'C', ItemList.Casing_Firebox_Bronze.get(1), 'D',
                new ItemStack(Items.cauldron, 1), 'E', ItemList.Machine_Bronze_AlloySmelter.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeSteamChemicalBath.get(1),
            new Object[] { "ABA", "CDC", "FEF", 'A', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 1),
                'B', GTOreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Wood, 1), 'C',
                ItemList.Casing_Pipe_Bronze.get(1), 'D',
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Bronze, 1), 'E',
                GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Bronze, 1), 'F',
                GTOreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Bronze, 1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.PrecisionSteamMechanism.get(1),
            new Object[] { "ABA", "CDC", "EBE", 'A', GTOreDictUnificator.get(OrePrefixes.stick, Materials.Bronze, 1),
                'B', GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Brass, 1), 'C',
                GTOreDictUnificator.get(OrePrefixes.springSmall, Materials.Bronze, 1), 'D',
                GTOreDictUnificator.get(OrePrefixes.springSmall, Materials.Steel, 1), 'E',
                GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Brass, 1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.PrimitiveDistillationTower.get(1),
            new Object[] { "ABA", "ABA", "CDC", 'A',
                GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Steel, 1), 'B',
                ItemList.Casing_Pipe_Steel.get(1), 'C', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1),
                'D', GTNLItemList.PrecisionSteamMechanism.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeSteamCircuitAssembler.get(1),
            new Object[] { "ABA", "BCB", "ABA", 'A',
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Bronze, 1), 'B',
                GTNLItemList.PrecisionSteamMechanism.get(1), 'C', GTNLItemList.SteamAssemblyCasing.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.SteamAssemblyCasing.get(1),
            new Object[] { "ABA", "ACA", "ABA", 'A', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 1),
                'B', GTNLItemList.PrecisionSteamMechanism.get(1), 'C', ItemList.Casing_Gearbox_Bronze.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.SteamCracking.get(1),
            new Object[] { "ABA", "DCD", "ABA", 'A', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 1),
                'B', GTNLItemList.PrecisionSteamMechanism.get(1), 'C',
                GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Bronze, 1), 'D',
                ItemList.Casing_Firebox_Bronze.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeSteamThermalCentrifuge.get(1),
            new Object[] { "ABA", "DCD", "ABA", 'A', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 1),
                'B', GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Bronze, 1), 'C',
                ItemList.Casing_Pipe_Bronze.get(1), 'D', ItemList.Casing_Firebox_Bronze.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.Desulfurizer.get(1),
            new Object[] { "ABA", "CDC", "EFE", 'A', "circuitAdvanced", 'B', NHItemList.AdsorptionFilter.getIS(1), 'C',
                ItemList.Electric_Pump_HV.get(1), 'D', ItemList.Hull_HV.get(1), 'E',
                GTOreDictUnificator.get(OrePrefixes.wireGt08, Materials.Electrum, 1L), 'F',
                ItemList.Electric_Motor_HV.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeCircuitAssembler.get(1),
            new Object[] { "ABA", "CDC", "EBE", 'A', ItemList.Robot_Arm_EV.get(1), 'B',
                GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1L), 'C', "circuitData", 'D',
                ItemList.Machine_EV_CircuitAssembler.get(1), 'E', ItemList.Conveyor_Module_EV.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.BrickedBlastFurnace.get(1),
            new Object[] { "ABA", "BCB", "ABA", 'A',
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Bronze, 1L), 'B',
                GTNLItemList.PrecisionSteamMechanism.get(1), 'C', ItemList.Machine_Bricked_BlastFurnace.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.NeutroniumPipeCasing.get(1),
            new Object[] { "ABA", "BCB", "ABA", 'A',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 1L), 'B',
                GTOreDictUnificator.get(OrePrefixes.pipeHuge, Materials.Neutronium, 1L), 'C',
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Neutronium, 1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.NeutroniumGearbox.get(1),
            new Object[] { "ABA", "CDC", "AEA", 'A',
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Neutronium, 1L), 'B',
                "craftingToolHardHammer", 'C', GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Neutronium, 1L),
                'D', GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Neutronium, 1L), 'E',
                "craftingToolWrench" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.EnergeticPhotovoltaicPowerStation.get(1),
            new Object[] { "ABA", "BCB", "ADA", 'A', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1L),
                'B', MaterialsAlloy.TUMBAGA.getBlock(1), 'C', GTNLItemList.EnergeticPhotovoltaicBlock.get(1), 'D',
                "circuitGood" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.AdvancedPhotovoltaicPowerStation.get(1),
            new Object[] { "ABA", "BCB", "ADA", 'A', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 1L),
                'B', GTModHandler.getModItem(EnderIO.ID, "blockIngotStorage", 1, 3), 'C',
                GTNLItemList.AdvancedPhotovoltaicBlock.get(1), 'D', "circuitAdvanced" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.VibrantPhotovoltaicPowerStation.get(1),
            new Object[] { "ABA", "BCB", "ADA", 'A',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1L), 'B',
                GTModHandler.getModItem(EnderIO.ID, "blockIngotStorage", 1, 6), 'C',
                GTNLItemList.VibrantPhotovoltaicBlock.get(1), 'D', "circuitData" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.TestItem.get(1),
            new Object[] { "ABA", "BCB", "ABA", 'A', new ItemStack(Items.golden_apple, 1, 1), 'B',
                GTModHandler.getModItem(Botania.ID, "manaResource", 1, 9), 'C', new ItemStack(Blocks.dragon_egg, 1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.CrushingWheels.get(2),
            new Object[] { "AAA", "BCB", "BDB", 'A',
                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.TungstenCarbide, 1L), 'B',
                GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Ultimet, 1L), 'C',
                ItemList.Casing_MiningOsmiridium.get(1L), 'D', ItemList.Electric_Motor_IV.get(1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.SlicingBlades.get(2),
            new Object[] { "AAA", "BCB", "BDB", 'A',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenCarbide, 1L), 'B',
                GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Ultimet, 1L), 'C',
                GregtechItemList.Casing_CuttingFactoryFrame.get(1L), 'D', ItemList.Electric_Motor_IV.get(1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.NinefoldInputHatchEV.get(1),
            new Object[] { " A ", " B ", "   ", 'A',
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, Materials.Titanium, 1L), 'B',
                ItemList.Hatch_Input_EV.get(1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.NinefoldInputHatchIV.get(1),
            new Object[] { " A ", " B ", "   ", 'A',
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, Materials.TungstenSteel, 1L), 'B',
                ItemList.Hatch_Input_IV.get(1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.NinefoldInputHatchLuV.get(1),
            new Object[] { " A ", " B ", "   ", 'A',
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, Materials.NiobiumTitanium, 1L), 'B',
                ItemList.Hatch_Input_LuV.get(1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.NinefoldInputHatchZPM.get(1),
            new Object[] { " A ", " B ", "   ", 'A',
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, Materials.Enderium, 1L), 'B',
                ItemList.Hatch_Input_ZPM.get(1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.NinefoldInputHatchUV.get(1),
            new Object[] { " A ", " B ", "   ", 'A',
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, Materials.Naquadah, 1L), 'B',
                ItemList.Hatch_Input_UV.get(1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.NinefoldInputHatchUHV.get(1),
            new Object[] { " A ", " B ", "   ", 'A',
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, Materials.Neutronium, 1L), 'B',
                ItemList.Hatch_Input_UHV.get(1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.NinefoldInputHatchUEV.get(1),
            new Object[] { " A ", " B ", "   ", 'A',
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, Materials.NetherStar, 1L), 'B',
                ItemList.Hatch_Input_UEV.get(1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.NinefoldInputHatchUIV.get(1),
            new Object[] { " A ", " B ", "   ", 'A',
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, Materials.MysteriousCrystal, 1L), 'B',
                ItemList.Hatch_Input_UIV.get(1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.NinefoldInputHatchUMV.get(1),
            new Object[] { " A ", " B ", "   ", 'A',
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, Materials.DraconiumAwakened, 1L), 'B',
                ItemList.Hatch_Input_UMV.get(1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.NinefoldInputHatchUXV.get(1),
            new Object[] { " A ", " B ", "   ", 'A',
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, Materials.Infinity, 1L), 'B',
                ItemList.Hatch_Input_UXV.get(1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.NinefoldInputHatchMAX.get(1),
            new Object[] { " A ", " B ", "   ", 'A',
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, MaterialsUEVplus.SpaceTime, 1L), 'B',
                ItemList.Hatch_Input_MAX.get(1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.BlackLight.get(1),
            new Object[] { "ABA", "CDC", "EBF", 'A',
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.TungstenCarbide, 1L), 'B',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenCarbide, 1L), 'C',
                GTModHandler.getModItem(IndustrialCraft2.ID, "blockAlloyGlass", 1, 0, missing), 'D',
                GTOreDictUnificator.get(OrePrefixes.spring, Materials.Europium, 1L), 'E', "circuitElite", 'F',
                GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Platinum, 1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeSteamExtractor.get(1),
            new Object[] { "ABA", "CDE", "ABA", 'A', ItemList.Casing_Pipe_Bronze.get(1), 'B',
                GTOreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Bronze, 1L), 'C',
                ItemList.Casing_Gearbox_Bronze.get(1), 'D', ItemList.Machine_Bronze_Extractor.get(1), 'E',
                new ItemStack(Blocks.glass, 1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeSteamOreWasher.get(1),
            new Object[] { "ABA", "CDC", "ABA", 'A', ItemList.Casing_Gearbox_Bronze.get(1), 'B',
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.WroughtIron, 1L), 'C',
                GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1L), 'D',
                GregtechItemList.Controller_SteamWasherMulti.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeSteamExtruder.get(1),
            new Object[] { "ABA", "CDC", "AAA", 'A', GregtechItemList.Casing_Machine_Custom_1.get(1), 'B',
                ItemList.Casing_Gearbox_Bronze.get(1), 'C', GTNLItemList.PrecisionSteamMechanism.get(1), 'D',
                "craftingPiston" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.TungstensteelGearbox.get(1),
            new Object[] { "ABA", "CDC", "AEA", 'A',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1L), 'B', "craftingToolHardHammer",
                'C', GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.TungstenSteel, 1L), 'D',
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 1L), 'E', "craftingToolWrench" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.RecordNewHorizons.get(1),
            new Object[] { "ABA", "CDC", "AEA", 'A', GTModHandler.getModItem(SGCraft.ID, "stargateRing", 1, 0, missing),
                'B', GTModHandler.getModItem(SGCraft.ID, "rfPowerUnit", 1, 0, missing), 'C',
                GTModHandler.getModItem(SGCraft.ID, "stargateRing", 1, 1, missing), 'D',
                GTModHandler.getModItem(SGCraft.ID, "stargateController", 1, 0, missing), 'E',
                GTModHandler.getModItem(SGCraft.ID, "stargateBase", 1, 0, missing) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.SatietyRing.get(1),
            new Object[] { "AAA", "ABA", "AAA", 'A', new ItemStack(Items.golden_apple, 1, 1), 'B',
                GTModHandler.getModItem(Thaumcraft.ID, "ItemBaubleBlanks", 1, 1, missing) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.KFCFamily.get(1),
            new Object[] { "AAA", "BCB", "AAA", 'A', GTModHandler.getModItem(PamsHarvestCraft.ID, "hotwingsItem", 1),
                'B', new ItemStack(Items.cooked_chicken, 1), 'C',
                GTOreDictUnificator.get(OrePrefixes.plateQuintuple, Materials.Paper, 1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.BigSteamInputHatch.get(1),
            new Object[] { "ABA", "CDC", "ABA", 'A', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1L),
                'B', GTOreDictUnificator.get(OrePrefixes.pipeHuge, Materials.Bronze, 1L), 'C',
                GTNLItemList.PrecisionSteamMechanism.get(1), 'D', GregtechItemList.Hatch_Input_Steam.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeSteamCentrifuge.get(1),
            new Object[] { "ABA", "CDC", "AEA", 'A', ItemList.Casing_BronzePlatedBricks.get(1), 'B',
                ItemList.Casing_Gearbox_Bronze.get(1), 'C', GTNLItemList.PrecisionSteamMechanism.get(1), 'D',
                GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Bronze, 1L), 'E',
                GregtechItemList.Controller_SteamCentrifugeMulti.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeSteamHammer.get(1),
            new Object[] { "ABA", "CDC", "AEA", 'A', ItemList.Casing_BronzePlatedBricks.get(1), 'B',
                new ItemStack(Blocks.anvil, 1), 'C', "craftingPiston", 'D', GTNLItemList.PrecisionSteamMechanism.get(1),
                'E', GregtechItemList.Controller_SteamForgeHammerMulti.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeSteamCompressor.get(1),
            new Object[] { "ABA", "CDC", "AEA", 'A', ItemList.Casing_BronzePlatedBricks.get(1), 'B',
                new ItemStack(Blocks.anvil, 1), 'C', ItemList.Casing_Gearbox_Bronze.get(1), 'D',
                MaterialsAlloy.TUMBAGA.getFrameBox(1), 'E', GregtechItemList.Controller_SteamCompressorMulti.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeSteamSifter.get(1),
            new Object[] { "ABA", "CDC", "AEA", 'A', ItemList.Casing_BronzePlatedBricks.get(1), 'B',
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Bronze, 1L), 'C',
                ItemList.Casing_Gearbox_Bronze.get(1), 'D', ItemList.Component_Filter.get(1), 'E',
                MaterialsAlloy.TUMBAGA.getFrameBox(1) });

        GTModHandler.addCraftingRecipe(
            GregtechItemList.Hatch_Input_Bus_Steam.get(1),
            new Object[] { " A ", " B ", "   ", 'A', "craftingToolScrewdriver", 'B',
                GregtechItemList.Hatch_Output_Bus_Steam.get(1) });

        GTModHandler.addCraftingRecipe(
            GregtechItemList.Hatch_Output_Bus_Steam.get(1),
            new Object[] { " A ", " B ", "   ", 'A', "craftingToolScrewdriver", 'B',
                GregtechItemList.Hatch_Input_Bus_Steam.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeBoilerBronze.get(1),
            new Object[] { "ABA", "CDC", "AEA", 'A', GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1L),
                'B', GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Bronze, 1L), 'C', "circuitBasic", 'D',
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Bronze, 1L), 'E',
                ItemList.Casing_Firebox_Bronze.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeBoilerSteel.get(1),
            new Object[] { "ABA", "CDC", "AEA", 'A',
                GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1L), 'B',
                GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1L), 'C', "circuitGood", 'D',
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1L), 'E',
                ItemList.Casing_Firebox_Steel.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeBoilerTitanium.get(1),
            new Object[] { "ABA", "CDC", "AEA", 'A', GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1L),
                'B', GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Titanium, 1L), 'C', "circuitAdvanced", 'D',
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 1L), 'E',
                ItemList.Casing_Firebox_Titanium.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeBoilerTungstenSteel.get(1),
            new Object[] { "ABA", "CDC", "AEA", 'A',
                GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1L), 'B',
                GTOreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 1L), 'C', "circuitData", 'D',
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 1L), 'E',
                ItemList.Casing_Firebox_TungstenSteel.get(1) });

        GTModHandler.addShapelessCraftingRecipe(
            GTOreDictUnificator.get(OrePrefixes.dust, Materials.Electrum, 2L),
            new Object[] { GTOreDictUnificator.get(OrePrefixes.dust, Materials.Gold, 1L),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Silver, 1L) });

        GTModHandler.addShapelessCraftingRecipe(
            GTOreDictUnificator.get(OrePrefixes.dust, Materials.Electrotine, 8L),
            new Object[] { GTOreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1L),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Electrum, 1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.PlayerDoll.get(1),
            new Object[] { "ABC", "DEF", "GHI", 'A', new ItemStack(Blocks.wool, 1, 14), 'B',
                new ItemStack(Blocks.wool, 1, 1), 'C', new ItemStack(Blocks.wool, 1, 4), 'D',
                new ItemStack(Blocks.wool, 1, 0), 'E', new ItemStack(Blocks.iron_block, 1), 'F',
                new ItemStack(Blocks.wool, 1, 5), 'G', new ItemStack(Blocks.wool, 1, 10), 'H',
                new ItemStack(Blocks.wool, 1, 9), 'I', new ItemStack(Blocks.wool, 1, 11) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.SteamAssemblerBronze.get(1),
            new Object[] { "ABA", "BCB", "ABA", 'A',
                GTOreDictUnificator.get(OrePrefixes.pipeSmall, Materials.Bronze, 1L), 'B',
                GTNLItemList.PrecisionSteamMechanism.get(1), 'C', ItemList.Casing_Gearbox_Bronze.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.SteamAssemblerSteel.get(1),
            new Object[] { "AAA", "BCB", "DBD", 'A',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.WroughtIron, 1L), 'B',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1L), 'C',
                GTNLItemList.SteamAssemblerBronze.get(1), 'D',
                GTOreDictUnificator.get(OrePrefixes.pipeSmall, Materials.Steel, 1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeSteamFormingPress.get(1),
            new Object[] { "ABA", "CDC", "ABA", 'A', ItemList.Casing_BronzePlatedBricks.get(1), 'B',
                new ItemStack(Blocks.piston, 1), 'C', GTNLItemList.PrecisionSteamMechanism.get(1), 'D',
                ItemList.Casing_Gearbox_Bronze.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.SteamTurbineLV.get(1),
            new Object[] { "ABA", "CDC", "AEA", 'A', ItemList.Electric_Pump_LV.get(1), 'B', "circuitBasic", 'C',
                GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1L), 'D', ItemList.Hull_LV.get(1), 'E',
                GTOreDictUnificator.get(OrePrefixes.cableGt16, Materials.Tin, 1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.SteamTurbineMV.get(1),
            new Object[] { "ABA", "CDC", "AEA", 'A', ItemList.Electric_Pump_MV.get(1), 'B', "circuitGood", 'C',
                GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Aluminium, 1L), 'D', ItemList.Hull_MV.get(1), 'E',
                GTOreDictUnificator.get(OrePrefixes.cableGt16, Materials.AnnealedCopper, 1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.SteamTurbineHV.get(1),
            new Object[] { "ABA", "CDC", "AEA", 'A', ItemList.Electric_Pump_HV.get(1), 'B', "circuitAdvanced", 'C',
                GTOreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 1L), 'D', ItemList.Hull_HV.get(1),
                'E', GTOreDictUnificator.get(OrePrefixes.cableGt16, Materials.Gold, 1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.SuperReachRing.get(1),
            new Object[] { "CB ", "BAB", " B ", 'A', GTModHandler.getModItem(Botania.ID, "reachRing", 1), 'B',
                GTOreDictUnificator.get(OrePrefixes.plate, MaterialsBotania.GaiaSpirit, 1L), 'C',
                GTModHandler.getModItem(Botania.ID, "lens", 1, 18) });

        GTModHandler.addCraftingRecipe(
            GregtechItemList.Hatch_Reservoir.get(1),
            new Object[] { "ABA", "BCB", "DDD", 'A',
                GTModHandler.getModItem(IndustrialCraft2.ID, "blockAlloyGlass", 1, 0, missing), 'B',
                new ItemStack(Items.water_bucket, 1), 'C', ItemList.Hull_LV.get(1), 'D',
                OrePrefixes.gem.get(Materials.Diamond) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeSteamMixer.get(1),
            new Object[] { "ABA", "CDC", "AEA", 'A', ItemList.Casing_BronzePlatedBricks.get(1), 'B',
                GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1L), 'C',
                GTNLItemList.PrecisionSteamMechanism.get(1), 'D', ItemList.Casing_Gearbox_Bronze.get(1), 'E',
                GregtechItemList.Controller_SteamMixerMulti.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.ElectricBlastFurnace.get(1),
            new Object[] { "ABA", "CDC", "ECE", 'A', ItemList.Robot_Arm_HV.get(1), 'B',
                ItemList.Machine_Multi_BlastFurnace.get(1), 'C', "circuitData", 'D',
                ItemList.Casing_CleanStainlessSteel.get(1), 'E',
                GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1L) });

        GTModHandler.addMachineCraftingRecipe(
            GTNLItemList.GasCollectorLV.get(1),
            bitsd,
            new Object[] { "ABA", "CDC", "AEA", 'A', new ItemStack(Blocks.iron_bars, 1), 'B',
                ItemList.FluidFilter.get(1), 'C', MTEBasicMachineWithRecipe.X.PUMP, 'D',
                MTEBasicMachineWithRecipe.X.HULL, 'E', MTEBasicMachineWithRecipe.X.CIRCUIT },
            1);

        GTModHandler.addMachineCraftingRecipe(
            GTNLItemList.GasCollectorMV.get(1),
            bitsd,
            new Object[] { "ABA", "CDC", "AEA", 'A', new ItemStack(Blocks.iron_bars, 1), 'B',
                ItemList.FluidFilter.get(1), 'C', MTEBasicMachineWithRecipe.X.PUMP, 'D',
                MTEBasicMachineWithRecipe.X.HULL, 'E', MTEBasicMachineWithRecipe.X.CIRCUIT },
            2);

        GTModHandler.addMachineCraftingRecipe(
            GTNLItemList.GasCollectorHV.get(1),
            bitsd,
            new Object[] { "ABA", "CDC", "AEA", 'A', new ItemStack(Blocks.iron_bars, 1), 'B',
                ItemList.FluidFilter.get(1), 'C', MTEBasicMachineWithRecipe.X.PUMP, 'D',
                MTEBasicMachineWithRecipe.X.HULL, 'E', MTEBasicMachineWithRecipe.X.CIRCUIT },
            3);

        GTModHandler.addMachineCraftingRecipe(
            GTNLItemList.GasCollectorEV.get(1),
            bitsd,
            new Object[] { "ABA", "CDC", "AEA", 'A', new ItemStack(Blocks.iron_bars, 1), 'B',
                ItemList.FluidFilter.get(1), 'C', MTEBasicMachineWithRecipe.X.PUMP, 'D',
                MTEBasicMachineWithRecipe.X.HULL, 'E', MTEBasicMachineWithRecipe.X.CIRCUIT },
            4);

        GTModHandler.addMachineCraftingRecipe(
            GTNLItemList.GasCollectorIV.get(1),
            bitsd,
            new Object[] { "ABA", "CDC", "AEA", 'A', new ItemStack(Blocks.iron_bars, 1), 'B',
                ItemList.FluidFilter.get(1), 'C', MTEBasicMachineWithRecipe.X.PUMP, 'D',
                MTEBasicMachineWithRecipe.X.HULL, 'E', MTEBasicMachineWithRecipe.X.CIRCUIT },
            5);

        GTModHandler.addMachineCraftingRecipe(
            GTNLItemList.GasCollectorLuV.get(1),
            bitsd,
            new Object[] { "ABA", "CDC", "AEA", 'A', new ItemStack(Blocks.iron_bars, 1), 'B',
                ItemList.FluidFilter.get(1), 'C', MTEBasicMachineWithRecipe.X.PUMP, 'D',
                MTEBasicMachineWithRecipe.X.HULL, 'E', MTEBasicMachineWithRecipe.X.CIRCUIT },
            6);

        GTModHandler.addMachineCraftingRecipe(
            GTNLItemList.GasCollectorZPM.get(1),
            bitsd,
            new Object[] { "ABA", "CDC", "AEA", 'A', new ItemStack(Blocks.iron_bars, 1), 'B',
                ItemList.FluidFilter.get(1), 'C', MTEBasicMachineWithRecipe.X.PUMP, 'D',
                MTEBasicMachineWithRecipe.X.HULL, 'E', MTEBasicMachineWithRecipe.X.CIRCUIT },
            7);

        GTModHandler.addMachineCraftingRecipe(
            GTNLItemList.GasCollectorUV.get(1),
            bitsd,
            new Object[] { "ABA", "CDC", "AEA", 'A', new ItemStack(Blocks.iron_bars, 1), 'B',
                ItemList.FluidFilter.get(1), 'C', MTEBasicMachineWithRecipe.X.PUMP, 'D',
                MTEBasicMachineWithRecipe.X.HULL, 'E', MTEBasicMachineWithRecipe.X.CIRCUIT },
            8);

        GTModHandler.addMachineCraftingRecipe(
            GTNLItemList.GasCollectorUHV.get(1),
            bitsd,
            new Object[] { "ABA", "CDC", "AEA", 'A', new ItemStack(Blocks.iron_bars, 1), 'B',
                ItemList.FluidFilter.get(1), 'C', MTEBasicMachineWithRecipe.X.PUMP, 'D',
                MTEBasicMachineWithRecipe.X.HULL, 'E', MTEBasicMachineWithRecipe.X.CIRCUIT },
            9);

        GTModHandler.addCraftingRecipe(
            GTNLItemList.SaplingBrickuoia.get(1),
            new Object[] { "AAA", "ABA", "ACA", 'A', new ItemStack(Blocks.brick_block, 1), 'B',
                new ItemStack(BOPBlockRegistrator.sapling_Rainforest, 1), 'C', new ItemStack(Items.dye, 1, 15) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.PortableBasicWorkBench.get(1),
            new Object[] { " AB", "CDA", "DE ", 'A', GTOreDictUnificator.get(OrePrefixes.screw, Materials.Iron, 1), 'B',
                new ItemStack(Blocks.crafting_table, 1), 'C', "craftingToolWrench", 'D',
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Wood, 1), 'E', "craftingToolScrewdriver" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.PortableAdvancedWorkBench.get(1),
            new Object[] { " AB", "CDA", "EF ", 'A', GTOreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 1),
                'B', GTModHandler.getModItem(TinkerConstruct.ID, "CraftingStation", 1), 'C', "craftingToolFile", 'D',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.WroughtIron, 1), 'E',
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.WroughtIron, 1), 'F',
                "craftingToolHardHammer" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.PortableFurnace.get(1),
            new Object[] { " AB", "CAA", "DE ", 'A', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 1), 'B',
                new ItemStack(Blocks.furnace, 1), 'C', "craftingToolWrench", 'D',
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Wood, 1), 'E', "craftingToolHardHammer" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.PortableAnvil.get(1),
            new Object[] { "ABC", "DEB", "FGH", 'A', "craftingToolFile", 'B',
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Steel, 1), 'C',
                new ItemStack(Blocks.anvil, 1), 'D', "craftingToolWrench", 'E',
                GTOreDictUnificator.get(OrePrefixes.block, Materials.Steel, 1), 'F',
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 1), 'G', "craftingToolHardHammer", 'H',
                "craftingToolSaw" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.PortableEnderChest.get(1),
            new Object[] { " AB", "CDA", "EF ", 'A',
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.EndSteel, 1), 'B',
                GTModHandler.getModItem(EnderStorage.ID, "enderChest", 1, 0), 'C', "craftingToolWrench", 'D',
                new ItemStack(Items.ender_eye, 1), 'E',
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.StainlessSteel, 1), 'F',
                "craftingToolHardHammer" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.PortableEnchantingTable.get(1),
            new Object[] { "ABC", "DEB", "FGA", 'A', GTOreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 1),
                'B', new ItemStack(Blocks.bookshelf, 1), 'C', new ItemStack(Blocks.enchanting_table, 1), 'D',
                "craftingToolWrench", 'E', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1), 'F',
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Aluminium, 1), 'G',
                "craftingToolHardHammer" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.PortableCompressedChest.get(1),
            new Object[] { "ABC", "DEB", "FGA", 'A',
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.StainlessSteel, 1), 'B',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1), 'C',
                GTModHandler.getModItem(AvaritiaAddons.ID, "CompressedChest", 1), 'D', "craftingToolWrench", 'E',
                GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.StainlessSteel, 1), 'F',
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Obsidian, 1), 'G', "craftingToolHardHammer" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.PortableInfinityChest.get(1),
            new Object[] { "ABC", "DEB", "FDG", 'A', "craftingToolWrench", 'B',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Infinity, 1), 'C',
                GTModHandler.getModItem(AvaritiaAddons.ID, "InfinityChest", 1), 'D',
                GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.CosmicNeutronium, 1), 'E',
                GTModHandler.getModItem(Avaritia.ID, "Resource", 1, 5), 'F',
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Infinity, 1), 'G', "craftingToolHardHammer" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.PortableCopperChest.get(1),
            new Object[] { "ABC", "DEB", "FGA", 'A', GTOreDictUnificator.get(OrePrefixes.screw, Materials.Copper, 1),
                'B', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Copper, 1), 'C',
                GTModHandler.getModItem(IronChests.ID, "BlockIronChest", 1, 3), 'D', "craftingToolWrench", 'E',
                GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Copper, 1), 'F',
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Copper, 1), 'G', "craftingToolHardHammer" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.PortableIronChest.get(1),
            new Object[] { "ABC", "DEB", "FGA", 'A', GTOreDictUnificator.get(OrePrefixes.screw, Materials.Iron, 1), 'B',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 1), 'C',
                GTModHandler.getModItem(IronChests.ID, "BlockIronChest", 1, 0), 'D', "craftingToolWrench", 'E',
                GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Iron, 1), 'F',
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Iron, 1), 'G', "craftingToolHardHammer" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.PortableSilverChest.get(1),
            new Object[] { "CA ", "ABA", " AD", 'A',
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Silver, 1), 'B',
                GTNLItemList.PortableCopperChest.get(1), 'C', "craftingToolWrench", 'D', "craftingToolHardHammer" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.PortableSteelChest.get(1),
            new Object[] { "ABC", "DEB", "FGA", 'A', GTOreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 1),
                'B', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1), 'C',
                GTModHandler.getModItem(IronChests.ID, "BlockIronChest", 1, 4), 'D', "craftingToolWrench", 'E',
                GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Steel, 1), 'F',
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 1), 'G', "craftingToolHardHammer" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.PortableGoldenChest.get(1),
            new Object[] { "ABC", "DEB", "FGA", 'A', GTOreDictUnificator.get(OrePrefixes.screw, Materials.Gold, 1), 'B',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Gold, 1), 'C',
                GTModHandler.getModItem(IronChests.ID, "BlockIronChest", 1, 1), 'D', "craftingToolWrench", 'E',
                GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Gold, 1), 'F',
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Gold, 1), 'G', "craftingToolHardHammer" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.PortableDiamondChest.get(1),
            new Object[] { "ABC", "DEB", "FGA", 'A', GTOreDictUnificator.get(OrePrefixes.screw, Materials.Diamond, 1),
                'B', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1), 'C',
                GTModHandler.getModItem(IronChests.ID, "BlockIronChest", 1, 2), 'D', "craftingToolWrench", 'E',
                GTOreDictUnificator.get(OrePrefixes.stick, Materials.Diamond, 1), 'F',
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Diamond, 1), 'G', "craftingToolHardHammer" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.PortableCrystalChest.get(1),
            new Object[] { "ABC", "DEB", "FGA", 'A', GTOreDictUnificator.get(OrePrefixes.screw, Materials.Glass, 1),
                'B', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Glass, 1), 'C',
                GTModHandler.getModItem(IronChests.ID, "BlockIronChest", 1, 5), 'D', "craftingToolWrench", 'E',
                GTOreDictUnificator.get(OrePrefixes.stick, Materials.Glass, 1), 'F',
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Glass, 1), 'G', "craftingToolHardHammer" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.PortableObsidianChest.get(1),
            new Object[] { "ABC", "DEB", "FGA", 'A', GTOreDictUnificator.get(OrePrefixes.screw, Materials.Obsidian, 1),
                'B', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 1), 'C',
                GTModHandler.getModItem(IronChests.ID, "BlockIronChest", 1, 6), 'D', "craftingToolWrench", 'E',
                GTOreDictUnificator.get(OrePrefixes.stick, Materials.Obsidian, 1), 'F',
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Obsidian, 1), 'G', "craftingToolHardHammer" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.PortableNetheriteChest.get(1),
            new Object[] { "ABC", "DEB", "FGA", 'A', GTOreDictUnificator.get(OrePrefixes.screw, Materials.Netherite, 1),
                'B', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Netherite, 1), 'C',
                GTModHandler.getModItem(IronChests.ID, "BlockIronChest", 1, 8), 'D', "craftingToolWrench", 'E',
                GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Netherite, 1), 'F',
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Netherite, 1), 'G',
                "craftingToolHardHammer" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.PortableDarkSteelChest.get(1),
            new Object[] { "ABC", "DEB", "FGA", 'A', GTOreDictUnificator.get(OrePrefixes.screw, Materials.DarkSteel, 1),
                'B', GTOreDictUnificator.get(OrePrefixes.plate, Materials.DarkSteel, 1), 'C',
                GTModHandler.getModItem(IronChests.ID, "BlockIronChest", 1, 9), 'D', "craftingToolWrench", 'E',
                GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.DarkSteel, 1), 'F',
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.DarkSteel, 1), 'G',
                "craftingToolHardHammer" });

        GTModHandler.addShapelessCraftingRecipe(
            GTNLItemList.DebugEnergyHatch.get(1),
            new Object[] { tectech.thing.CustomItemList.Machine_DebugGenny.get(1) });

        GTModHandler.addShapelessCraftingRecipe(
            tectech.thing.CustomItemList.Machine_DebugGenny.get(1),
            new Object[] { GTNLItemList.DebugEnergyHatch.get(1) });

        GTModHandler.addShapelessCraftingRecipe(
            MaterialPool.Breel.get(OrePrefixes.dust, 3),
            new Object[] { GTOreDictUnificator.get(OrePrefixes.dust, Materials.Bronze, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Bronze, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Steel, 1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.VibrationSafeCasing.get(1),
            new Object[] { "AAA", "BCB", "AAA", 'A',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.CrudeSteel, 1), 'B',
                MaterialPool.Breel.get(OrePrefixes.plateDouble, 1), 'C', ItemList.Casing_SolidSteel.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.ConcentratingSieveMesh.get(1),
            new Object[] { "AAA", "wBh", "AAA", 'A',
                GTOreDictUnificator.get(OrePrefixes.stick, Materials.CrudeSteel, 1), 'B',
                MaterialPool.Breel.get(OrePrefixes.plateDouble, 1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.StronzeWrappedCasing.get(4),
            new Object[] { "ABA", "BBB", "ABA", 'A', MaterialPool.Stronze.get(OrePrefixes.plate, 1), 'B',
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.CrudeSteel, 1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.BreelPipeCasing.get(2),
            new Object[] { "ABA", "BCB", "ABA", 'A',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.CrudeSteel, 1), 'B',
                MaterialPool.Breel.get(OrePrefixes.pipeMedium, 1), 'C',
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.CrudeSteel, 1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.SolarBoilingCell.get(1),
            new Object[] { "AAA", "BCB", 'A', new ItemStack(Blocks.glass, 1), 'B',
                MaterialPool.Stronze.get(OrePrefixes.pipeTiny, 1), 'C', ItemList.Machine_HP_Solar.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.HydraulicAssemblingCasing.get(1),
            new Object[] { "ABA", "CCC", "ABA", 'A', MaterialPool.Stronze.get(OrePrefixes.pipeTiny, 1), 'B',
                MaterialPool.Breel.get(OrePrefixes.plate, 1), 'C', GTNLItemList.HydraulicArm.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.HyperPressureBreelCasing.get(1),
            new Object[] { "AAA", "BCB", "AAA", 'A', MaterialPool.Breel.get(OrePrefixes.plate, 1), 'B',
                GTOreDictUnificator.get(OrePrefixes.stick, Materials.Beryllium, 1), 'C',
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Beryllium, 1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.BreelPlatedCasing.get(1),
            new Object[] { "AAA", "BCB", "AAA", 'A', MaterialPool.Breel.get(OrePrefixes.pipeTiny, 1), 'B',
                MaterialPool.Breel.get(OrePrefixes.plate, 1), 'C',
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.CrudeSteel, 1) });

        // Steam Extractinator
        GTModHandler.addCraftingRecipe(
            GTNLItemList.SteamExtractinator.get(1),
            new Object[] { "ABA", "CDC", "ABA", 'A', GTNLItemList.VibrationSafeCasing.get(1), 'B',
                ItemList.Casing_BronzePlatedBricks.get(1), 'C', GTNLItemList.HydraulicPump.get(1), 'D',
                MaterialPool.Stronze.get(OrePrefixes.pipeLarge, 1) });

        // Steam Fuser
        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeSteamAlloySmelter.get(1),
            new Object[] { "ABA", "CDC", "ABA", 'A', ItemList.Casing_BronzePlatedBricks.get(1), 'B',
                new ItemStack(Blocks.furnace, 1), 'C', GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1),
                'D', new ItemStack(Blocks.nether_brick, 1) });

        // Steam Conformer
        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeSteamExtruder.get(1),
            new Object[] { "BCA", "DEF", "AGA", 'A', ItemList.Casing_BronzePlatedBricks.get(1), 'B',
                GTNLItemList.HydraulicMotor.get(1), 'C', GTNLItemList.HydraulicPiston.get(1), 'D',
                new ItemStack(Blocks.anvil, 1), 'E', GregtechItemList.Controller_SteamForgeHammerMulti.get(1), 'F',
                GTOreDictUnificator.get(OrePrefixes.plateQuintuple, Materials.Steel, 1), 'G',
                GTNLItemList.HydraulicConveyor.get(1) });

        // Steam Rock Breaker
        GTModHandler.addCraftingRecipe(
            GTNLItemList.SteamRockBreaker.get(1),
            new Object[] { "ABA", "CDE", "ABA", 'A', ItemList.Casing_BronzePlatedBricks.get(1), 'B',
                ItemList.Casing_Pipe_Bronze.get(1), 'C', new ItemStack(Items.water_bucket, 1), 'D',
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.CrudeSteel, 1), 'E',
                new ItemStack(Items.lava_bucket, 1) });

        // Steam Carpenter
        GTModHandler.addCraftingRecipe(
            GTNLItemList.SteamCarpenter.get(1),
            new Object[] { "ABA", "CDC", "EEE", 'A', ItemList.Casing_BronzePlatedBricks.get(1), 'B',
                GTNLItemList.HydraulicPiston.get(1), 'D', new ItemStack(Blocks.glass, 1), 'C',
                GTNLItemList.HydraulicArm.get(1), 'E',
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Wood, 1) });

        // Steam Wood Cutter
        GTModHandler.addCraftingRecipe(
            GTNLItemList.SteamWoodcutter.get(1),
            new Object[] { "AAA", "DCD", "EBE", 'A', GTNLItemList.BronzeReinforcedWood.get(1), 'B',
                GTNLItemList.HydraulicPump.get(1), 'D', new ItemStack(Blocks.glass, 1), 'C',
                new ItemStack(Blocks.dirt, 1), 'E', GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Wood, 1) });

        // Steam Manufactrer
        GTModHandler.addCraftingRecipe(
            GTNLItemList.SteamManufacturer.get(1),
            new Object[] { "AAA", "DCD", "EBE", 'A', GTNLItemList.HydraulicArm.get(1), 'B',
                GTNLItemList.HydraulicConveyor.get(1), 'D', GTNLItemList.HydraulicAssemblingCasing.get(1), 'C',
                MaterialPool.Breel.get(OrePrefixes.plateDouble, 1), 'E', ItemList.Casing_Gearbox_Steel.get(1) });

        // Mega Solar Boiler
        GTModHandler.addCraftingRecipe(
            GTNLItemList.MegaSolarBoiler.get(1),
            new Object[] { "AAA", "DCD", "EBE", 'A', GTNLItemList.SolarBoilingCell.get(1), 'B',
                ItemList.Casing_SolidSteel.get(1), 'D', GTNLItemList.HydraulicPump.get(1), 'C', ItemList.Hull_HP, 'E',
                ItemList.Casing_BronzePlatedBricks });

        // Cactus Wonder
        GTModHandler.addCraftingRecipe(
            GTNLItemList.SteamCactusWonder.get(1),
            new Object[] { "ABA", "ACA", "ABA", 'A', new ItemStack(Blocks.cactus, 1), 'B',
                ItemList.Casing_BronzePlatedBricks.get(1), 'C', GTNLItemList.HydraulicRegulator.get(1) });

        // Fusion Reactor
        GTModHandler.addCraftingRecipe(
            GTNLItemList.SteamFusionReactor.get(1),
            new Object[] { "ABA", "DCD", "ABA", 'A', MaterialPool.Stronze.get(OrePrefixes.pipeHuge, 1), 'C',
                GTNLItemList.MegaSolarBoiler.get(1), 'B',
                GTOreDictUnificator.get(OrePrefixes.gem, Materials.Emerald, 1), 'D',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Beryllium, 1) });

        // Infernal Coke Oven
        GTModHandler.addCraftingRecipe(
            GTNLItemList.SteamInfernalCokeOven.get(1),
            new Object[] { "ABA", "DCD", "ABA", 'A', new ItemStack(Blocks.nether_brick, 1), 'C',
                ItemList.Machine_Bricked_BlastFurnace.get(1), 'B', MaterialPool.Breel.get(OrePrefixes.plate, 1), 'D',
                MaterialPool.Stronze.get(OrePrefixes.plate, 1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.HydraulicMotor.get(1),
            new Object[] { "ABC", "BDB", "CBA", 'A', GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Bronze, 1),
                'B', GTOreDictUnificator.get(OrePrefixes.pipeTiny, Materials.Bronze, 1), 'C',
                GTOreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 1), 'D', GTNLItemList.IronTurbine.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.HydraulicPiston.get(1),
            new Object[] { "AAA", "BCC", "DEF", 'A',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.CrudeSteel, 1), 'D',
                GTOreDictUnificator.get(OrePrefixes.pipeTiny, Materials.Bronze, 1), 'C',
                GTOreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 1), 'B', GTNLItemList.IronTurbine.get(1),
                'E', GTNLItemList.HydraulicMotor.get(1), 'F',
                GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Bronze, 1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.HydraulicPump.get(1),
            new Object[] { "ABC", "sDw", "CEA", 'A', GTNLItemList.HydraulicMotor.get(1), 'B',
                GTNLItemList.BronzeTurbine.get(1), 'C', GTOreDictUnificator.get(OrePrefixes.ring, Materials.Rubber, 1),
                'D', GTOreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Bronze, 1), 'E',
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.CrudeSteel, 1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.HydraulicArm.get(1),
            new Object[] { "AAA", "BCB", "DEC", 'A',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.CrudeSteel, 1), 'B',
                GTNLItemList.HydraulicMotor.get(1), 'C', GTOreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 1),
                'D', GTNLItemList.HydraulicPiston.get(1), 'E',
                GTOreDictUnificator.get(OrePrefixes.gear, Materials.CrudeSteel, 1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.HydraulicConveyor.get(1),
            new Object[] { "AAA", "BCB", "AAA", 'A', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 1),
                'B', GTNLItemList.HydraulicMotor.get(1), 'C',
                GTOreDictUnificator.get(OrePrefixes.gear, Materials.CrudeSteel, 1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.HydraulicRegulator.get(1),
            new Object[] { "ABC", "BDB", "CBA", 'A', GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Bronze, 1),
                'B', GTOreDictUnificator.get(OrePrefixes.pipeTiny, Materials.Bronze, 1), 'C',
                GTNLItemList.SteelTurbine.get(1), 'D', GTNLItemList.HydraulicPump.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.HydraulicSteamReceiver.get(1),
            new Object[] { "ABC", "ACB", "DAA", 'A', MaterialPool.CompressedSteam.get(OrePrefixes.plate, 1), 'B',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 1), 'C',
                MaterialPool.Stronze.get(OrePrefixes.pipeHuge, 1), 'D', GTNLItemList.HydraulicPump.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.HydraulicSteamJetSpewer.get(1),
            new Object[] { "ABC", "BDB", "CBA", 'A', MaterialPool.CompressedSteam.get(OrePrefixes.plate, 1), 'B',
                MaterialPool.CompressedSteam.get(OrePrefixes.stick, 1), 'C',
                MaterialPool.Breel.get(OrePrefixes.pipeHuge, 1), 'D',
                GTOreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Salt, 1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.HydraulicVaporGenerator.get(1),
            new Object[] { "ABC", "BDB", "CBA", 'A', MaterialPool.CompressedSteam.get(OrePrefixes.plateSuperdense, 1),
                'B', MaterialPool.CompressedSteam.get(OrePrefixes.plateDouble, 1), 'C',
                GTNLItemList.HydraulicSteamJetSpewer.get(1), 'D', GTNLItemList.CompressedSteamTurbine.get(1) });

        // Pipeless Hatch
        GTModHandler.addCraftingRecipe(
            GTNLItemList.PipelessSteamHatch.get(1),
            new Object[] { "AEA", "CBD", "AEA", 'A', GTNLItemList.BronzeReinforcedWood.get(1), 'B',
                ItemList.Hatch_Input_Bus_LV.get(1), 'C', MaterialPool.Stronze.get(OrePrefixes.pipeHuge, 1), 'D',
                MaterialPool.Breel.get(OrePrefixes.pipeHuge, 1), 'E', GTNLItemList.HydraulicRegulator.get(1) });

        // Pipeless Vent
        GTModHandler.addCraftingRecipe(
            GTNLItemList.PipelessSteamVent.get(1),
            new Object[] { "AEA", "CBD", "AEA", 'A', GTNLItemList.BronzeReinforcedWood.get(1), 'B',
                ItemList.Hatch_Output_Bus_LV.get(1), 'C', MaterialPool.Stronze.get(OrePrefixes.pipeHuge, 1), 'D',
                MaterialPool.Breel.get(OrePrefixes.pipeHuge, 1), 'E', GTNLItemList.HydraulicRegulator.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.PipelessSteamVent.get(1),
            new Object[] { "AEA", "CBD", "AEA", 'A', GTNLItemList.BronzeReinforcedWood.get(1), 'B',
                ItemList.Hatch_Output_Bus_LV.get(1), 'C', MaterialPool.Stronze.get(OrePrefixes.pipeHuge, 1), 'D',
                MaterialPool.Breel.get(OrePrefixes.pipeHuge, 1), 'E', GTNLItemList.HydraulicRegulator.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.IronTurbine.get(1),
            new Object[] { "ABC", "BDB", "CBE", 'A', "craftingToolFile", 'B',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 1), 'C',
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Iron, 1), 'D',
                GTOreDictUnificator.get(OrePrefixes.ring, Materials.Iron, 1), 'E', "craftingToolHardHammer" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.BronzeTurbine.get(1),
            new Object[] { "ABC", "BDB", "CBE", 'A', "craftingToolFile", 'B',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 1), 'C',
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Bronze, 1), 'D',
                GTOreDictUnificator.get(OrePrefixes.ring, Materials.Bronze, 1), 'E', "craftingToolHardHammer" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.IndustrialSteamCasing.get(1),
            new Object[] { "ABA", "ACA", "ADA", 'A', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                'B', "craftingToolWrench", 'C', GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Bronze, 1), 'D',
                "craftingToolHardHammer" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.AdvancedIndustrialSteamCasing.get(1),
            new Object[] { "ABA", "ACA", "ADA", 'A',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.WroughtIron, 1), 'B', "craftingToolWrench", 'C',
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1), 'D', "craftingToolHardHammer" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.BronzeMachineFrame.get(1),
            new Object[] { "ABA", "BCB", "ABA", 'A', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 1),
                'B', GTOreDictUnificator.get(OrePrefixes.stick, Materials.Bronze, 1), 'C', "craftingToolHardHammer" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.SteelMachineFrame.get(1),
            new Object[] { "ABA", "BCB", "ABA", 'A', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1),
                'B', GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 1), 'C', "craftingToolHardHammer" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeSteamLathe.get(1),
            new Object[] { "ABA", "CDE", "BFG", 'A', ItemList.Casing_BronzePlatedBricks.get(1), 'B',
                GTNLItemList.PrecisionSteamMechanism.get(1), 'C', GTNLItemList.HydraulicPump.get(1), 'D',
                ItemList.Casing_Gearbox_Bronze.get(1), 'E', OrePrefixes.gem.get(Materials.Diamond), 'F',
                MaterialPool.Breel.get(OrePrefixes.pipeLarge, 1), 'G', GTNLItemList.HydraulicPiston.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.LargeSteamCutting.get(1),
            new Object[] { "ABC", "DEF", "BAG", 'A', MaterialPool.Breel.get(OrePrefixes.pipeMedium, 1), 'B',
                GTNLItemList.PrecisionSteamMechanism.get(1), 'C', new ItemStack(Blocks.glass, 1), 'D',
                GTNLItemList.HydraulicConveyor.get(1), 'E', ItemList.Casing_Pipe_Bronze.get(1), 'F',
                ItemList.Component_Sawblade_Diamond.get(1), 'G', GTNLItemList.HydraulicArm.get(1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.CardboardBox.get(1),
            new Object[] { "ABA", "A A", "AAA", 'A', new ItemStack(Items.paper, 1), 'B',
                new ItemStack(Items.slime_ball, 1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.CardboardBox.get(1),
            new Object[] { "ABA", "A A", "AAA", 'A', new ItemStack(Items.paper, 1), 'B',
                GTModHandler.getModItem(IndustrialCraft2.ID, "itemHarz", 1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.PrimitiveBrickKiln.get(1),
            new Object[] { "ABA", "CDC", "EFE", 'A', ItemList.Casing_BronzePlatedBricks.get(1), 'B',
                "craftingToolWrench", 'C', ItemList.Casing_Firebricks.get(1), 'D', ItemList.Hull_Bronze_Bricks.get(1),
                'E', ItemList.Casing_Firebox_Bronze.get(1), 'F', "craftingToolHardHammer" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.StainlessSteelGearBox.get(1),
            new Object[] { "ABA", "CDC", "AEA", 'A',
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1L), 'B', "craftingToolHardHammer",
                'C', GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.StainlessSteel, 1L), 'D',
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.StainlessSteel, 1L), 'E',
                "craftingToolWrench" });

        GTModHandler.addCraftingRecipe(
            GTModHandler.getModItem(EtFuturumRequiem.ID, "netherite_helmet", 1),
            recipeFlags,
            new Object[] { "AAA", "ABC", "CCC", 'A', GTModHandler.getModItem(EtFuturumRequiem.ID, "netherite_scrap", 1),
                'B', new ItemStack(Items.diamond_helmet, 1), 'C',
                GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Gold, 1L) });

        GTModHandler.addCraftingRecipe(
            GTModHandler.getModItem(EtFuturumRequiem.ID, "netherite_chestplate", 1),
            recipeFlags,
            new Object[] { "AAA", "ABC", "CCC", 'A', GTModHandler.getModItem(EtFuturumRequiem.ID, "netherite_scrap", 1),
                'B', new ItemStack(Items.diamond_chestplate, 1), 'C',
                GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Gold, 1L) });

        GTModHandler.addCraftingRecipe(
            GTModHandler.getModItem(EtFuturumRequiem.ID, "netherite_leggings", 1),
            recipeFlags,
            new Object[] { "AAA", "ABC", "CCC", 'A', GTModHandler.getModItem(EtFuturumRequiem.ID, "netherite_scrap", 1),
                'B', new ItemStack(Items.diamond_leggings, 1), 'C',
                GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Gold, 1L) });

        GTModHandler.addCraftingRecipe(
            GTModHandler.getModItem(EtFuturumRequiem.ID, "netherite_boots", 1),
            recipeFlags,
            new Object[] { "AAA", "ABC", "CCC", 'A', GTModHandler.getModItem(EtFuturumRequiem.ID, "netherite_scrap", 1),
                'B', new ItemStack(Items.diamond_boots, 1), 'C',
                GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Gold, 1L) });

        GTModHandler.addCraftingRecipe(
            GTModHandler.getModItem(EtFuturumRequiem.ID, "netherite_sword", 1),
            recipeFlags,
            new Object[] { "AAA", "ABC", "CCC", 'A', GTModHandler.getModItem(EtFuturumRequiem.ID, "netherite_scrap", 1),
                'B', new ItemStack(Items.diamond_sword, 1), 'C',
                GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Gold, 1L) });

        GTModHandler.addCraftingRecipe(
            GTModHandler.getModItem(EtFuturumRequiem.ID, "netherite_pickaxe", 1),
            recipeFlags,
            new Object[] { "AAA", "ABC", "CCC", 'A', GTModHandler.getModItem(EtFuturumRequiem.ID, "netherite_scrap", 1),
                'B', new ItemStack(Items.diamond_pickaxe, 1), 'C',
                GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Gold, 1L) });

        GTModHandler.addCraftingRecipe(
            GTModHandler.getModItem(EtFuturumRequiem.ID, "netherite_axe", 1),
            recipeFlags,
            new Object[] { "AAA", "ABC", "CCC", 'A', GTModHandler.getModItem(EtFuturumRequiem.ID, "netherite_scrap", 1),
                'B', new ItemStack(Items.diamond_axe, 1), 'C',
                GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Gold, 1L) });

        GTModHandler.addCraftingRecipe(
            GTModHandler.getModItem(EtFuturumRequiem.ID, "netherite_spade", 1),
            recipeFlags,
            new Object[] { "AAA", "ABC", "CCC", 'A', GTModHandler.getModItem(EtFuturumRequiem.ID, "netherite_scrap", 1),
                'B', new ItemStack(Items.diamond_shovel, 1), 'C',
                GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Gold, 1L) });

        GTModHandler.addCraftingRecipe(
            GTModHandler.getModItem(EtFuturumRequiem.ID, "netherite_hoe", 1),
            recipeFlags,
            new Object[] { "AAA", "ABC", "CCC", 'A', GTModHandler.getModItem(EtFuturumRequiem.ID, "netherite_scrap", 1),
                'B', new ItemStack(Items.diamond_hoe, 1), 'C',
                GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Gold, 1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.LootBagRedemption.get(1),
            new Object[] { "ABA", "CDC", "EFE", 'A', GTOreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 1L),
                'B', ItemUtils.getEnchantedBook(Enchantment.fortune, 3), 'C', "circuitBasic", 'D',
                ItemList.Hull_LV.get(1), 'E', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1L), 'F',
                GTModHandler.getModItem(IronChests.ID, "BlockIronChest", 1) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.DieselGeneratorLV.get(1),
            new Object[] { "ABA", "CDC", "EFE", 'A', ItemList.Electric_Piston_LV.get(1), 'B', "circuitBasic", 'C',
                GregtechItemList.GTFluidTank_LV.get(1), 'D', ItemList.Hull_LV.get(1), 'E', ItemList.Electric_Pump_LV,
                'F', GTOreDictUnificator.get(OrePrefixes.cableGt08, Materials.Tin, 1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.DieselGeneratorMV.get(1),
            new Object[] { "ABA", "CDC", "EFE", 'A', ItemList.Electric_Piston_MV.get(1), 'B', "circuitGood", 'C',
                GregtechItemList.GTFluidTank_MV.get(1), 'D', ItemList.Hull_MV.get(1), 'E', ItemList.Electric_Pump_MV,
                'F', "cableGt08AnyCopper" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.DieselGeneratorHV.get(1),
            new Object[] { "ABA", "CDC", "EFE", 'A', ItemList.Electric_Piston_HV.get(1), 'B', "circuitAdvanced", 'C',
                GregtechItemList.GTFluidTank_HV.get(1), 'D', ItemList.Hull_HV.get(1), 'E', ItemList.Electric_Pump_HV,
                'F', GTOreDictUnificator.get(OrePrefixes.cableGt08, Materials.Gold, 1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.GasTurbineLV.get(1),
            new Object[] { "ABA", "CDC", "EFE", 'A', GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1L),
                'B', "circuitBasic", 'C', ItemList.Large_Fluid_Cell_Steel.get(1), 'D', ItemList.Hull_LV.get(1), 'E',
                ItemList.Electric_Piston_LV, 'F', GTOreDictUnificator.get(OrePrefixes.cableGt08, Materials.Tin, 1L) });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.GasTurbineMV.get(1),
            new Object[] { "ABA", "CDC", "EFE", 'A',
                GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Aluminium, 1L), 'B', "circuitGood", 'C',
                ItemList.Large_Fluid_Cell_Aluminium.get(1), 'D', ItemList.Hull_MV.get(1), 'E',
                ItemList.Electric_Piston_MV, 'F', "cableGt08AnyCopper" });

        GTModHandler.addCraftingRecipe(
            GTNLItemList.GasTurbineHV.get(1),
            new Object[] { "ABA", "CDC", "EFE", 'A',
                GTOreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 1L), 'B', "circuitAdvanced", 'C',
                ItemList.Large_Fluid_Cell_StainlessSteel.get(1), 'D', ItemList.Hull_HV.get(1), 'E',
                ItemList.Electric_Piston_HV, 'F', GTOreDictUnificator.get(OrePrefixes.cableGt08, Materials.Gold, 1L) });

        if (MainConfig.enableSomethingRecipe) {
            GTModHandler.addCraftingRecipe(
                tectech.thing.CustomItemList.hatch_CreativeMaintenance.get(1),
                recipeFlags,
                new Object[] { "ABA", "CDC", "ABA", 'A', "circuitAdvanced", 'B', ItemList.Hatch_Maintenance.get(1L),
                    'C', ItemList.Robot_Arm_HV.get(1L), 'D', ItemList.Hull_HV.get(1L) });
        }
    }
}
