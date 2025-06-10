package com.science.gtnl.common.recipe.GTNL;

import static gregtech.api.enums.Mods.Botania;
import static gregtech.api.enums.Mods.Thaumcraft;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.science.gtnl.api.IRecipePool;
import com.science.gtnl.common.material.MaterialPool;
import com.science.gtnl.loader.RecipePool;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;

public class ManaInfusionRecipes implements IRecipePool {

    final RecipeMap<?> MIR = RecipePool.ManaInfusionRecipes;

    @Override
    public void loadRecipes() {

        ItemStack daybloom = GTModHandler.getModItem(Botania.ID, "specialFlower", 1);
        NBTTagCompound daybloomType = daybloom.getTagCompound();
        if (daybloomType != null) {
            daybloomType.setString("type", "daybloom");
        } else {
            daybloomType = new NBTTagCompound();
            daybloomType.setString("type", "daybloom");
            daybloom.setTagCompound(daybloomType);
        }

        ItemStack daybloomDecor = GTModHandler.getModItem(Botania.ID, "specialFlower", 1);
        NBTTagCompound daybloomDecorType = daybloomDecor.getTagCompound();
        if (daybloomDecorType != null) {
            daybloomDecorType.setString("type", "daybloomDecor");
        } else {
            daybloomDecorType = new NBTTagCompound();
            daybloomDecorType.setString("type", "daybloomDecor");
            daybloomDecor.setTagCompound(daybloomDecorType);
        }

        ItemStack hydroangeas = GTModHandler.getModItem(Botania.ID, "specialFlower", 1);
        NBTTagCompound hydroangeasType = hydroangeas.getTagCompound();
        if (hydroangeasType != null) {
            hydroangeasType.setString("type", "hydroangeas");
        } else {
            hydroangeasType = new NBTTagCompound();
            hydroangeasType.setString("type", "hydroangeas");
            hydroangeas.setTagCompound(hydroangeasType);
        }

        ItemStack hydroangeasDecor = GTModHandler.getModItem(Botania.ID, "specialFlower", 1);
        NBTTagCompound hydroangeasDecorType = hydroangeasDecor.getTagCompound();
        if (hydroangeasDecorType != null) {
            hydroangeasDecorType.setString("type", "hydroangeasDecor");
        } else {
            hydroangeasDecorType = new NBTTagCompound();
            hydroangeasDecorType.setString("type", "hydroangeasDecor");
            hydroangeasDecor.setTagCompound(hydroangeasDecorType);
        }

        ItemStack nightshade = GTModHandler.getModItem(Botania.ID, "specialFlower", 1);
        NBTTagCompound nightshadeType = nightshade.getTagCompound();
        if (nightshadeType != null) {
            nightshadeType.setString("type", "nightshade");
        } else {
            nightshadeType = new NBTTagCompound();
            nightshadeType.setString("type", "nightshade");
            nightshade.setTagCompound(nightshadeType);
        }

        ItemStack nightshadeDecor = GTModHandler.getModItem(Botania.ID, "specialFlower", 1);
        NBTTagCompound nightshadeDecorType = nightshadeDecor.getTagCompound();
        if (nightshadeDecorType != null) {
            nightshadeDecorType.setString("type", "nightshadeDecor");
        } else {
            nightshadeDecorType = new NBTTagCompound();
            nightshadeDecorType.setString("type", "nightshadeDecor");
            nightshadeDecor.setTagCompound(nightshadeDecorType);
        }

        ItemStack bellethorn = GTModHandler.getModItem(Botania.ID, "specialFlower", 1);
        NBTTagCompound bellethornType = bellethorn.getTagCompound();
        if (bellethornType != null) {
            bellethornType.setString("type", "bellethorn");
        } else {
            bellethornType = new NBTTagCompound();
            bellethornType.setString("type", "bellethorn");
            bellethorn.setTagCompound(bellethornType);
        }

        ItemStack bellethornChibi = GTModHandler.getModItem(Botania.ID, "specialFlower", 1);
        NBTTagCompound bellethornChibiType = bellethornChibi.getTagCompound();
        if (bellethornChibiType != null) {
            bellethornChibiType.setString("type", "bellethornChibi");
        } else {
            bellethornChibiType = new NBTTagCompound();
            bellethornChibiType.setString("type", "bellethornChibi");
            bellethornChibi.setTagCompound(bellethornChibiType);
        }

        ItemStack agricarnation = GTModHandler.getModItem(Botania.ID, "specialFlower", 1);
        NBTTagCompound agricarnationType = agricarnation.getTagCompound();
        if (agricarnationType != null) {
            agricarnationType.setString("type", "agricarnation");
        } else {
            agricarnationType = new NBTTagCompound();
            agricarnationType.setString("type", "agricarnation");
            agricarnation.setTagCompound(agricarnationType);
        }

        ItemStack agricarnationChibi = GTModHandler.getModItem(Botania.ID, "specialFlower", 1);
        NBTTagCompound agricarnationChibiType = agricarnationChibi.getTagCompound();
        if (agricarnationChibiType != null) {
            agricarnationChibiType.setString("type", "agricarnationChibi");
        } else {
            agricarnationChibiType = new NBTTagCompound();
            agricarnationChibiType.setString("type", "agricarnationChibi");
            agricarnationChibi.setTagCompound(agricarnationChibiType);
        }

        ItemStack hopperhock = GTModHandler.getModItem(Botania.ID, "specialFlower", 1);
        NBTTagCompound hopperhockType = hopperhock.getTagCompound();
        if (hopperhockType != null) {
            hopperhockType.setString("type", "hopperhock");
        } else {
            hopperhockType = new NBTTagCompound();
            hopperhockType.setString("type", "hopperhock");
            hopperhock.setTagCompound(hopperhockType);
        }

        ItemStack hopperhockChibi = GTModHandler.getModItem(Botania.ID, "specialFlower", 1);
        NBTTagCompound hopperhockChibiType = hopperhockChibi.getTagCompound();
        if (hopperhockChibiType != null) {
            hopperhockChibiType.setString("type", "hopperhockChibi");
        } else {
            hopperhockChibiType = new NBTTagCompound();
            hopperhockChibiType.setString("type", "hopperhockChibi");
            hopperhockChibi.setTagCompound(hopperhockChibiType);
        }

        ItemStack rannuncarpus = GTModHandler.getModItem(Botania.ID, "specialFlower", 1);
        NBTTagCompound rannuncarpusType = rannuncarpus.getTagCompound();
        if (rannuncarpusType != null) {
            rannuncarpusType.setString("type", "rannuncarpus");
        } else {
            rannuncarpusType = new NBTTagCompound();
            rannuncarpusType.setString("type", "rannuncarpus");
            rannuncarpus.setTagCompound(rannuncarpusType);
        }

        ItemStack rannuncarpusChibi = GTModHandler.getModItem(Botania.ID, "specialFlower", 1);
        NBTTagCompound rannuncarpusChibiType = rannuncarpusChibi.getTagCompound();
        if (rannuncarpusChibiType != null) {
            rannuncarpusChibiType.setString("type", "rannuncarpusChibi");
        } else {
            rannuncarpusChibiType = new NBTTagCompound();
            rannuncarpusChibiType.setString("type", "rannuncarpusChibi");
            rannuncarpusChibi.setTagCompound(rannuncarpusChibiType);
        }

        ItemStack clayconia = GTModHandler.getModItem(Botania.ID, "specialFlower", 1);
        NBTTagCompound clayconiaType = clayconia.getTagCompound();
        if (clayconiaType != null) {
            clayconiaType.setString("type", "clayconia");
        } else {
            clayconiaType = new NBTTagCompound();
            clayconiaType.setString("type", "clayconia");
            clayconia.setTagCompound(clayconiaType);
        }

        ItemStack clayconiaChibi = GTModHandler.getModItem(Botania.ID, "specialFlower", 1);
        NBTTagCompound clayconiaChibiType = clayconiaChibi.getTagCompound();
        if (clayconiaChibiType != null) {
            clayconiaChibiType.setString("type", "clayconiaChibi");
        } else {
            clayconiaChibiType = new NBTTagCompound();
            clayconiaChibiType.setString("type", "clayconiaChibi");
            clayconiaChibi.setTagCompound(clayconiaChibiType);
        }

        ItemStack marimorphosis = GTModHandler.getModItem(Botania.ID, "specialFlower", 1);
        NBTTagCompound marimorphosisType = marimorphosis.getTagCompound();
        if (marimorphosisType != null) {
            marimorphosisType.setString("type", "marimorphosis");
        } else {
            marimorphosisType = new NBTTagCompound();
            marimorphosisType.setString("type", "marimorphosis");
            marimorphosis.setTagCompound(marimorphosisType);
        }

        ItemStack marimorphosisChibi = GTModHandler.getModItem(Botania.ID, "specialFlower", 1);
        NBTTagCompound marimorphosisChibiType = marimorphosisChibi.getTagCompound();
        if (marimorphosisChibiType != null) {
            marimorphosisChibiType.setString("type", "marimorphosisChibi");
        } else {
            marimorphosisChibiType = new NBTTagCompound();
            marimorphosisChibiType.setString("type", "marimorphosisChibi");
            marimorphosisChibi.setTagCompound(marimorphosisChibiType);
        }

        ItemStack bubbell = GTModHandler.getModItem(Botania.ID, "specialFlower", 1);
        NBTTagCompound bubbellType = bubbell.getTagCompound();
        if (bubbellType != null) {
            bubbellType.setString("type", "bubbell");
        } else {
            bubbellType = new NBTTagCompound();
            bubbellType.setString("type", "bubbell");
            bubbell.setTagCompound(bubbellType);
        }

        ItemStack bubbellChibi = GTModHandler.getModItem(Botania.ID, "specialFlower", 1);
        NBTTagCompound bubbellChibiType = bubbellChibi.getTagCompound();
        if (bubbellChibiType != null) {
            bubbellChibiType.setString("type", "bubbellChibi");
        } else {
            bubbellChibiType = new NBTTagCompound();
            bubbellChibiType.setString("type", "bubbellChibi");
            bubbellChibi.setTagCompound(bubbellChibiType);
        }

        ItemStack solegnolia = GTModHandler.getModItem(Botania.ID, "specialFlower", 1);
        NBTTagCompound solegnoliaType = solegnolia.getTagCompound();
        if (solegnoliaType != null) {
            solegnoliaType.setString("type", "solegnolia");
        } else {
            solegnoliaType = new NBTTagCompound();
            solegnoliaType.setString("type", "solegnolia");
            solegnolia.setTagCompound(solegnoliaType);
        }

        ItemStack solegnoliaChibi = GTModHandler.getModItem(Botania.ID, "specialFlower", 1);
        NBTTagCompound solegnoliaChibiType = solegnoliaChibi.getTagCompound();
        if (solegnoliaChibiType != null) {
            solegnoliaChibiType.setString("type", "solegnoliaChibi");
        } else {
            solegnoliaChibiType = new NBTTagCompound();
            solegnoliaChibiType.setString("type", "solegnoliaChibi");
            solegnoliaChibi.setTagCompound(solegnoliaChibiType);
        }

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)), daybloom)
            .itemOutputs(daybloomDecor)
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(2500))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)), hydroangeas)
            .itemOutputs(hydroangeasDecor)
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(2500))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)), nightshade)
            .itemOutputs(nightshadeDecor)
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(2500))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)), bellethorn)
            .itemOutputs(bellethornChibi)
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(2500))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                agricarnation)
            .itemOutputs(agricarnationChibi)
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(2500))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)), hopperhock)
            .itemOutputs(hopperhockChibi)
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(2500))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                rannuncarpus)
            .itemOutputs(rannuncarpusChibi)
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(2500))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)), clayconia)
            .itemOutputs(clayconiaChibi)
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(2500))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                marimorphosis)
            .itemOutputs(marimorphosisChibi)
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(2500))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)), bubbell)
            .itemOutputs(bubbellChibi)
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(2500))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)), solegnolia)
            .itemOutputs(solegnoliaChibi)
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(2500))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "conjurationCatalyst", 1)),
                new ItemStack(Blocks.tallgrass, 1))
            .itemOutputs(new ItemStack(Blocks.tallgrass, 2, 1))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(2000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "conjurationCatalyst", 1)),
                new ItemStack(Blocks.leaves, 1, 0))
            .itemOutputs(new ItemStack(Blocks.leaves, 2, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(2000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "conjurationCatalyst", 1)),
                new ItemStack(Blocks.leaves, 1))
            .itemOutputs(new ItemStack(Blocks.leaves, 2, 1))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(2000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "conjurationCatalyst", 1)),
                new ItemStack(Blocks.leaves, 1, 2))
            .itemOutputs(new ItemStack(Blocks.leaves, 2, 2))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(2000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "conjurationCatalyst", 1)),
                new ItemStack(Blocks.leaves, 1, 3))
            .itemOutputs(new ItemStack(Blocks.leaves, 2, 3))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(2000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "conjurationCatalyst", 1)),
                new ItemStack(Blocks.leaves2, 1))
            .itemOutputs(new ItemStack(Blocks.leaves2, 2))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(2000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "conjurationCatalyst", 1)),
                new ItemStack(Blocks.leaves2, 1, 1))
            .itemOutputs(new ItemStack(Blocks.leaves2, 2, 1))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(2000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "conjurationCatalyst", 1)),
                new ItemStack(Blocks.gravel, 1))
            .itemOutputs(new ItemStack(Blocks.gravel, 2))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(720))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "conjurationCatalyst", 1)),
                new ItemStack(Blocks.soul_sand, 1))
            .itemOutputs(new ItemStack(Blocks.soul_sand, 2))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(4500))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "conjurationCatalyst", 1)),
                new ItemStack(Blocks.netherrack, 1, 0))
            .itemOutputs(new ItemStack(Blocks.netherrack, 2, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(200))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "conjurationCatalyst", 1)),
                new ItemStack(Items.snowball, 1, 0))
            .itemOutputs(new ItemStack(Items.snowball, 2, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(200))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "conjurationCatalyst", 1)),
                new ItemStack(Items.coal, 1, 0))
            .itemOutputs(new ItemStack(Items.coal, 2, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(7500))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "conjurationCatalyst", 1)),
                new ItemStack(Items.quartz, 1, 0))
            .itemOutputs(new ItemStack(Items.quartz, 2, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(7500))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "conjurationCatalyst", 1)),
                new ItemStack(Items.glowstone_dust, 1, 0))
            .itemOutputs(new ItemStack(Items.glowstone_dust, 2, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(5000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "conjurationCatalyst", 1)),
                new ItemStack(Items.redstone, 1, 0))
            .itemOutputs(new ItemStack(Items.redstone, 2, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(5000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.double_plant, 1, 5))
            .itemOutputs(new ItemStack(Blocks.yellow_flower, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(400))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.yellow_flower, 1, 0))
            .itemOutputs(new ItemStack(Blocks.red_flower, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(400))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.red_flower, 1, 0))
            .itemOutputs(new ItemStack(Blocks.red_flower, 1, 1))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(400))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.red_flower, 1, 1))
            .itemOutputs(new ItemStack(Blocks.red_flower, 1, 2))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(400))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.red_flower, 1, 2))
            .itemOutputs(new ItemStack(Blocks.red_flower, 1, 3))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(400))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.red_flower, 1, 3))
            .itemOutputs(new ItemStack(Blocks.red_flower, 1, 4))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(400))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.red_flower, 1, 4))
            .itemOutputs(new ItemStack(Blocks.red_flower, 1, 5))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(400))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.red_flower, 1, 5))
            .itemOutputs(new ItemStack(Blocks.red_flower, 1, 6))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(400))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.red_flower, 1, 6))
            .itemOutputs(new ItemStack(Blocks.red_flower, 1, 7))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(400))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.red_flower, 1, 7))
            .itemOutputs(new ItemStack(Blocks.red_flower, 1, 8))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(400))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.red_flower, 1, 8))
            .itemOutputs(new ItemStack(Blocks.double_plant, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(400))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.double_plant, 1, 0))
            .itemOutputs(new ItemStack(Blocks.double_plant, 1, 1))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(400))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.double_plant, 1, 1))
            .itemOutputs(new ItemStack(Blocks.double_plant, 1, 4))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(400))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.double_plant, 1, 4))
            .itemOutputs(new ItemStack(Blocks.double_plant, 1, 5))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(400))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.tallgrass, 1))
            .itemOutputs(new ItemStack(Blocks.tallgrass, 1, 2))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(500))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.deadbush, 1, 0))
            .itemOutputs(new ItemStack(Blocks.tallgrass, 1))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(500))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.tallgrass, 1, 2))
            .itemOutputs(new ItemStack(Blocks.deadbush, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(500))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                GTModHandler.getModItem(Botania.ID, "stone", 1, 2))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "stone", 1, 3))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(200))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                GTModHandler.getModItem(Botania.ID, "stone", 1, 1))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "stone", 1, 2))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(200))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                GTModHandler.getModItem(Botania.ID, "stone", 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "stone", 1, 1))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(200))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                GTModHandler.getModItem(Botania.ID, "stone", 1, 3))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "stone", 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(200))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Items.quartz, 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "manaResource", 1, 10))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(200))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.dirt, 1, 0))
            .itemOutputs(new ItemStack(Blocks.dirt, 1, 1))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(120))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.brick_block, 1, 0))
            .itemOutputs(new ItemStack(Items.brick, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(25))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.clay, 1, 0))
            .itemOutputs(new ItemStack(Items.clay_ball, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(25))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.hardened_clay, 1, 0))
            .itemOutputs(new ItemStack(Blocks.sand, 1, 1))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(50))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.cobblestone, 1, 0))
            .itemOutputs(new ItemStack(Blocks.sand, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(50))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Items.redstone, 1, 0))
            .itemOutputs(new ItemStack(Items.glowstone_dust, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(300))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Items.glowstone_dust, 1, 0))
            .itemOutputs(new ItemStack(Items.redstone, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(300))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Items.glowstone_dust, 1, 0))
            .itemOutputs(new ItemStack(Items.redstone, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(300))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Items.ghast_tear, 1, 0))
            .itemOutputs(new ItemStack(Items.ender_pearl, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(8000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Items.slime_ball, 1, 0))
            .itemOutputs(new ItemStack(Blocks.cactus, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(1200))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.cactus, 1, 0))
            .itemOutputs(new ItemStack(Items.slime_ball, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(1200))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        for (int meta = 15; meta >= 0; meta--) {
            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                    new ItemStack(Blocks.wool, 1, meta))
                .itemOutputs(new ItemStack(Items.string, 3, 0))
                .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(100))
                .duration(20)
                .eut(2048)
                .addTo(MIR);
        }

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Items.writable_book, 1, 0))
            .itemOutputs(new ItemStack(Items.name_tag, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(4000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Items.flint, 1, 0))
            .itemOutputs(new ItemStack(Items.gunpowder, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(16000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Items.flint, 1, 0))
            .itemOutputs(new ItemStack(Items.gunpowder, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(4000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Items.gunpowder, 1, 0))
            .itemOutputs(new ItemStack(Items.flint, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(4000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Items.gunpowder, 1, 0))
            .itemOutputs(new ItemStack(Items.flint, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(200))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Items.blaze_rod, 1, 0))
            .itemOutputs(new ItemStack(Blocks.nether_wart, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(4000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Items.poisonous_potato, 1, 0))
            .itemOutputs(new ItemStack(Items.potato, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(1200))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Items.pumpkin_seeds, 1, 0))
            .itemOutputs(new ItemStack(Items.dye, 1, 3))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(16000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Items.melon_seeds, 1, 0))
            .itemOutputs(new ItemStack(Items.pumpkin_seeds, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(16000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Items.carrot, 1, 0))
            .itemOutputs(new ItemStack(Items.melon_seeds, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(16000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Items.potato, 1, 0))
            .itemOutputs(new ItemStack(Items.carrot, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(16000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.wheat, 1, 0))
            .itemOutputs(new ItemStack(Items.potato, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(16000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Items.dye, 1, 3))
            .itemOutputs(new ItemStack(Items.wheat_seeds, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(16000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Items.fish, 1, 3))
            .itemOutputs(new ItemStack(Items.fish, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(200))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Items.fish, 1, 2))
            .itemOutputs(new ItemStack(Items.fish, 1, 3))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(200))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Items.fish, 1, 1))
            .itemOutputs(new ItemStack(Items.fish, 1, 2))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(200))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Items.fish, 1, 0))
            .itemOutputs(new ItemStack(Items.fish, 1, 1))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(200))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.vine, 1, 0))
            .itemOutputs(new ItemStack(Blocks.waterlily, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(320))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.waterlily, 1, 0))
            .itemOutputs(new ItemStack(Blocks.vine, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(120))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.waterlily, 1, 0))
            .itemOutputs(new ItemStack(Blocks.vine, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(200))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.snow, 1, 0))
            .itemOutputs(new ItemStack(Blocks.ice, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(2250))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.stonebrick, 1, 0))
            .itemOutputs(new ItemStack(Blocks.stonebrick, 1, 3))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(150))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                GTModHandler.getModItem(Botania.ID, "quartzTypeElf", 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "quartz", 4, 5))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(25))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                GTModHandler.getModItem(Botania.ID, "quartzTypeRed", 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "quartz", 4, 4))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(25))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                GTModHandler.getModItem(Botania.ID, "quartzTypeLavender", 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "quartz", 4, 3))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(25))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                GTModHandler.getModItem(Botania.ID, "quartzTypeBlaze", 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "quartz", 4, 2))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(25))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                GTModHandler.getModItem(Botania.ID, "quartzTypeMana", 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "quartz", 4, 1))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(25))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                GTModHandler.getModItem(Botania.ID, "quartzTypeDark", 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "quartz", 4, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(25))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.quartz_block, 1, 0))
            .itemOutputs(new ItemStack(Items.quartz, 4, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(25))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.glowstone, 1, 0))
            .itemOutputs(new ItemStack(Items.glowstone_dust, 4, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(25))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.sapling, 1, 5))
            .itemOutputs(new ItemStack(Blocks.sapling, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(120))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.sapling, 1, 4))
            .itemOutputs(new ItemStack(Blocks.sapling, 1, 5))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(120))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.sapling, 1, 3))
            .itemOutputs(new ItemStack(Blocks.sapling, 1, 4))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(120))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.sapling, 1, 2))
            .itemOutputs(new ItemStack(Blocks.sapling, 1, 3))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(120))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.sapling, 1, 1))
            .itemOutputs(new ItemStack(Blocks.sapling, 1, 2))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(120))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.sapling, 1, 0))
            .itemOutputs(new ItemStack(Blocks.sapling, 1, 1))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(120))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.log2, 1, 0))
            .itemOutputs(new ItemStack(Blocks.log2, 1, 1))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(40))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.log, 1, 3))
            .itemOutputs(new ItemStack(Blocks.log2, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(40))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.log, 1, 2))
            .itemOutputs(new ItemStack(Blocks.log, 1, 3))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(40))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.log, 1, 1))
            .itemOutputs(new ItemStack(Blocks.log, 1, 2))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(40))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.log, 1, 0))
            .itemOutputs(new ItemStack(Blocks.log, 1, 1))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(40))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Blocks.log2, 1, 1))
            .itemOutputs(new ItemStack(Blocks.log, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(40))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "alchemyCatalyst", 1)),
                new ItemStack(Items.rotten_flesh, 1, 0))
            .itemOutputs(new ItemStack(Items.leather, 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(600))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 1))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "manaResource", 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(3000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(GTModHandler.getModItem(Thaumcraft.ID, "ItemResource", 1, 2))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "manaResource", 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(3000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.block, Materials.Steel, 1))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "storage", 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(27000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.block, Materials.Thaumium, 1))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "storage", 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(27000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Items.gunpowder, 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "manaResource", 1, 23))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(1000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Items.redstone, 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "manaResource", 1, 23))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(1000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Items.sugar, 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "manaResource", 1, 23))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(2000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Items.glowstone_dust, 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "manaResource", 1, 23))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(750))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Thaumium, 1))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "manaResource", 1, 23))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(250))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Tritanium, 1))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "manaResource", 1, 23))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(150))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(GTModHandler.getModItem("DraconicEvolution", "draconiumDust", 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "manaResource", 1, 23))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(50))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        for (int meta = 15; meta >= 0; meta--) {
            GTValues.RA.stdBuilder()
                .itemInputs(GTModHandler.getModItem(Botania.ID, "dye", 1, meta))
                .itemOutputs(GTModHandler.getModItem(Botania.ID, "manaResource", 1, 23))
                .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(1500))
                .duration(20)
                .eut(2048)
                .addTo(MIR);
        }

        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Diamond, 1))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "manaResource", 1, 2))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(10000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.gemFlawless, Materials.Diamond, 1))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "manaResource", 1, 2))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(20000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Items.diamond, 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "manaResource", 1, 2))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(40000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Blocks.diamond_block, 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "storage", 1, 3))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(360000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Blocks.tallgrass, 1))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "grassSeeds", 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(2500))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Blocks.deadbush, 1, 1))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "grassSeeds", 1, 1))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(2500))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Blocks.red_mushroom, 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "grassSeeds", 1, 2))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(6500))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Blocks.brown_mushroom, 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "grassSeeds", 1, 2))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(6500))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Items.quartz, 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "quartz", 1, 1))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(1000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Blocks.glass, 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "manaGlass", 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(250))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Items.string, 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "manaResource", 1, 16))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(5000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Items.glass_bottle, 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "manaBottle", 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(10000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Items.ender_pearl, 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "manaResource", 1, 1))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(5000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Blocks.piston, 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "pistonRelay", 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(15000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Items.cookie, 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "manaCookie", 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(20000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Items.potato, 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "tinyPotato", 1, 0))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(31337))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(GTModHandler.getModItem("harvestcraft", "wovencottonItem", 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "manaResource", 1, 22))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(15000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(GTModHandler.getModItem("harvestcraft", "wovencottonItem", 1, 0))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "manaResource", 1, 22))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(15000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(GTModHandler.getModItem("ForbiddenMagic", "Cores", 1, 8))
            .itemOutputs(GTModHandler.getModItem("ForbiddenMagic", "Cores", 1, 7))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(10000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(GTModHandler.getModItem("ForbiddenMagic", "WandCaps", 1, 4))
            .itemOutputs(GTModHandler.getModItem("ForbiddenMagic", "WandCaps", 1, 3))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(1000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(GTModHandler.getModItem("ForbiddenMagic", "WandCores", 1, 12))
            .itemOutputs(GTModHandler.getModItem("ForbiddenMagic", "WandCores", 1, 11))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(10000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(Botania.ID, "terraPlate", 1)),
                GTModHandler.getModItem(Botania.ID, "manaResource", 1, 0),
                GTModHandler.getModItem(Botania.ID, "manaResource", 1, 1),
                GTModHandler.getModItem(Botania.ID, "manaResource", 1, 2))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "manaResource", 1, 4))
            .fluidInputs(MaterialPool.FluidMana.getFluidOrGas(500000))
            .duration(20)
            .eut(2048)
            .addTo(MIR);
    }
}
