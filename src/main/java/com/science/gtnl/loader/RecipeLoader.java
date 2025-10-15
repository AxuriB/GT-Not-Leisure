package com.science.gtnl.loader;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import com.Nxer.TwistSpaceTechnology.recipe.machineRecipe.expanded.AssemblyLineWithoutResearchRecipePool;
import com.Nxer.TwistSpaceTechnology.recipe.machineRecipe.expanded.CircuitAssemblyLineWithoutImprintRecipePool;
import com.reavaritia.ReAvaItemList;
import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.Utils.enums.ModList;
import com.science.gtnl.Utils.machine.ProcessingArrayRecipeLoader;
import com.science.gtnl.Utils.recipes.RecipeUtil;
import com.science.gtnl.api.IRecipePool;
import com.science.gtnl.common.item.items.Stick;
import com.science.gtnl.common.machine.OreProcessing.CheatOreProcessingRecipes;
import com.science.gtnl.common.material.MaterialPool;
import com.science.gtnl.common.recipe.AprilFool.CactusWonderFakeRecipes;
import com.science.gtnl.common.recipe.AprilFool.InfernalCokeRecipes;
import com.science.gtnl.common.recipe.AprilFool.LavaMakerRecipes;
import com.science.gtnl.common.recipe.AprilFool.RockBreakerRecipes;
import com.science.gtnl.common.recipe.AprilFool.SteamCarpenterRecipe;
import com.science.gtnl.common.recipe.AprilFool.SteamExtractinatorRecipes;
import com.science.gtnl.common.recipe.AprilFool.SteamFusionReactorRecipes;
import com.science.gtnl.common.recipe.AprilFool.SteamGateAssemblerRecipes;
import com.science.gtnl.common.recipe.AprilFool.SteamManufacturerRecipes;
import com.science.gtnl.common.recipe.AprilFool.SteamWoodcutterRecipes;
import com.science.gtnl.common.recipe.BloodMagic.MeteorsRecipes;
import com.science.gtnl.common.recipe.GTNL.AdvancedCircuitAssemblyLineRecipes;
import com.science.gtnl.common.recipe.GTNL.AlchemicChemistrySetRecipes;
import com.science.gtnl.common.recipe.GTNL.BloodDemonInjectionRecipes;
import com.science.gtnl.common.recipe.GTNL.CellRegulatorRecipes;
import com.science.gtnl.common.recipe.GTNL.DecayHastenerRecipes;
import com.science.gtnl.common.recipe.GTNL.DesulfurizerRecipes;
import com.science.gtnl.common.recipe.GTNL.ElectrocellGeneratorRecipes;
import com.science.gtnl.common.recipe.GTNL.ElementCopyingRecipes;
import com.science.gtnl.common.recipe.GTNL.EternalGregTechWorkshopUpgradeRecipes;
import com.science.gtnl.common.recipe.GTNL.FallingTowerRecipes;
import com.science.gtnl.common.recipe.GTNL.FishingGroundRecipes;
import com.science.gtnl.common.recipe.GTNL.FuelRefiningComplexRecipes;
import com.science.gtnl.common.recipe.GTNL.GasCollectorRecipes;
import com.science.gtnl.common.recipe.GTNL.InfusionCraftingRecipes;
import com.science.gtnl.common.recipe.GTNL.IsaMillRecipes;
import com.science.gtnl.common.recipe.GTNL.ManaInfusionRecipes;
import com.science.gtnl.common.recipe.GTNL.MatterFabricatorRecipes;
import com.science.gtnl.common.recipe.GTNL.MolecularTransformerRecipes;
import com.science.gtnl.common.recipe.GTNL.NaquadahReactorRecipes;
import com.science.gtnl.common.recipe.GTNL.NatureSpiritArrayRecipes;
import com.science.gtnl.common.recipe.GTNL.PetrochemicalPlantRecipes;
import com.science.gtnl.common.recipe.GTNL.PlatinumBasedTreatmentRecipes;
import com.science.gtnl.common.recipe.GTNL.PortalToAlfheimRecipes;
import com.science.gtnl.common.recipe.GTNL.PrimitiveBrickKilnRecipes;
import com.science.gtnl.common.recipe.GTNL.RareEarthCentrifugalRecipes;
import com.science.gtnl.common.recipe.GTNL.ReFusionReactorRecipes;
import com.science.gtnl.common.recipe.GTNL.RealArtificialStarRecipes;
import com.science.gtnl.common.recipe.GTNL.RocketAssemblerRecipes;
import com.science.gtnl.common.recipe.GTNL.ShallowChemicalCouplingRecipes;
import com.science.gtnl.common.recipe.GTNL.ShapedArcaneCraftingRecipes;
import com.science.gtnl.common.recipe.GTNL.ShimmerRecipes;
import com.science.gtnl.common.recipe.GTNL.SmeltingMixingFurnaceRecipes;
import com.science.gtnl.common.recipe.GTNL.SpaceDrillRecipes;
import com.science.gtnl.common.recipe.GTNL.SpaceMinerRecipes;
import com.science.gtnl.common.recipe.GTNL.SteamCrackerRecipes;
import com.science.gtnl.common.recipe.GTNL.TheTwilightForestRecipes;
import com.science.gtnl.common.recipe.GregTech.AlloyBlastSmelterRecipes;
import com.science.gtnl.common.recipe.GregTech.AlloySmelterRecipes;
import com.science.gtnl.common.recipe.GregTech.AssemblerRecipes;
import com.science.gtnl.common.recipe.GregTech.AssemblingLineRecipes;
import com.science.gtnl.common.recipe.GregTech.AutoclaveRecipes;
import com.science.gtnl.common.recipe.GregTech.BacterialVatRecipes;
import com.science.gtnl.common.recipe.GregTech.BlastFurnaceRecipes;
import com.science.gtnl.common.recipe.GregTech.CentrifugeRecipes;
import com.science.gtnl.common.recipe.GregTech.ChemicalBathRecipes;
import com.science.gtnl.common.recipe.GregTech.ChemicalDehydratorRecipes;
import com.science.gtnl.common.recipe.GregTech.ChemicalRecipes;
import com.science.gtnl.common.recipe.GregTech.CompressorRecipes;
import com.science.gtnl.common.recipe.GregTech.CrackingRecipes;
import com.science.gtnl.common.recipe.GregTech.CraftingTableRecipes;
import com.science.gtnl.common.recipe.GregTech.CuttingRecipes;
import com.science.gtnl.common.recipe.GregTech.DigesterRecipes;
import com.science.gtnl.common.recipe.GregTech.DissolutionTankRecipes;
import com.science.gtnl.common.recipe.GregTech.DistillationTowerRecipes;
import com.science.gtnl.common.recipe.GregTech.DistilleryRecipes;
import com.science.gtnl.common.recipe.GregTech.DragonEvolutionFusionCraftingRecipes;
import com.science.gtnl.common.recipe.GregTech.ElectrolyzerRecipes;
import com.science.gtnl.common.recipe.GregTech.FluidCannerRecipes;
import com.science.gtnl.common.recipe.GregTech.FluidExtraction;
import com.science.gtnl.common.recipe.GregTech.FluidExtractorRecipes;
import com.science.gtnl.common.recipe.GregTech.FusionReactorRecipes;
import com.science.gtnl.common.recipe.GregTech.LaserEngraverRecipes;
import com.science.gtnl.common.recipe.GregTech.MixerRecipes;
import com.science.gtnl.common.recipe.GregTech.PCBFactoryRecipes;
import com.science.gtnl.common.recipe.GregTech.PlasmaForgeRecipes;
import com.science.gtnl.common.recipe.GregTech.PreciseAssemblerRecipes;
import com.science.gtnl.common.recipe.GregTech.Remove.NewAlloyBlastSmelterRecipes;
import com.science.gtnl.common.recipe.GregTech.Remove.NewFormingPressRecipes;
import com.science.gtnl.common.recipe.GregTech.Remove.NewVacuumFurnaceRecipes;
import com.science.gtnl.common.recipe.GregTech.ServerStart.CircuitAssemblerConvertRecipes;
import com.science.gtnl.common.recipe.GregTech.ServerStart.CircuitAssemblyLineRecipes;
import com.science.gtnl.common.recipe.GregTech.SpaceAssemblerRecipes;
import com.science.gtnl.common.recipe.GregTech.TargetChamberRecipes;
import com.science.gtnl.common.recipe.GregTech.TranscendentPlasmaMixerRecipes;
import com.science.gtnl.common.recipe.GregTech.VacuumFreezerRecipes;
import com.science.gtnl.common.recipe.GregTech.VacuumFurnaceRecipes;
import com.science.gtnl.common.recipe.GregTech.multiDehydratorRecipes;
import com.science.gtnl.common.recipe.OreDictionary.PortalToAlfheimOreRecipes;
import com.science.gtnl.common.recipe.Thaumcraft.TCRecipePool;
import com.science.gtnl.common.recipe.Thaumcraft.TCResearches;
import com.science.gtnl.config.MainConfig;

import bartworks.API.recipe.BartWorksRecipeMaps;
import cpw.mods.fml.common.registry.VillagerRegistry;
import goodgenerator.loader.Loaders;
import goodgenerator.util.CrackRecipeAdder;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.enums.Mods;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTRecipe;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;
import gtnhlanth.api.recipe.LanthanidesRecipeMaps;
import tectech.thing.CustomItemList;

public class RecipeLoader {

    private static boolean recipesAdded;

    public static void loadRecipesServerStart() {
        if (!recipesAdded) {
            loadRecipes();
            if (MainConfig.enableDeleteRecipe) {
                loadNewRemoveRecipes();
            }
        }
        loadCircuitRelatedRecipes();
        recipesAdded = true;
    }

    public static void loadNewRemoveRecipes() {
        IRecipePool[] recipePools = new IRecipePool[] { new NewAlloyBlastSmelterRecipes(),
            new NewVacuumFurnaceRecipes(), new NewFormingPressRecipes() };

        for (IRecipePool recipePool : recipePools) {
            recipePool.loadRecipes();
        }
    }

    public static void loadRecipes() {

        ProcessingArrayRecipeLoader.registerDefaultGregtechMaps();

        CrackRecipeAdder.reAddBlastRecipe(MaterialPool.MolybdenumDisilicide, 800, 1920, 2300, true);
        CrackRecipeAdder.reAddBlastRecipe(MaterialPool.HSLASteel, 1000, 480, 1711, true);
        CrackRecipeAdder.reAddBlastRecipe(MaterialPool.Germaniumtungstennitride, 800, 30720, 8200, true);

        registerBuffTargetChamberRecipe();

        IRecipePool[] recipePools = new IRecipePool[] { new TCRecipePool(), new ChemicalRecipes(),
            new ElectrolyzerRecipes(), new MixerRecipes(), new multiDehydratorRecipes(), new AssemblerRecipes(),
            new AutoclaveRecipes(), new AlloyBlastSmelterRecipes(), new CompressorRecipes(),
            new ReFusionReactorRecipes(), new RealArtificialStarRecipes(), new PortalToAlfheimRecipes(),
            new NatureSpiritArrayRecipes(), new ManaInfusionRecipes(), new TranscendentPlasmaMixerRecipes(),
            new PlasmaForgeRecipes(), new CraftingTableRecipes(), new ChemicalBathRecipes(), new SteamCrackerRecipes(),
            new DesulfurizerRecipes(), new PetrochemicalPlantRecipes(), new FusionReactorRecipes(),
            new SmeltingMixingFurnaceRecipes(), new FluidExtraction(), new DigesterRecipes(),
            new DissolutionTankRecipes(), new CentrifugeRecipes(), new ChemicalDehydratorRecipes(),
            new RareEarthCentrifugalRecipes(), new MatterFabricatorRecipes(), new TheTwilightForestRecipes(),
            new IsaMillRecipes(), new CellRegulatorRecipes(), new VacuumFurnaceRecipes(), new FishingGroundRecipes(),
            new DistilleryRecipes(), new ElementCopyingRecipes(), new AlloySmelterRecipes(),
            new MolecularTransformerRecipes(), new NaquadahReactorRecipes(), new DragonEvolutionFusionCraftingRecipes(),
            new LaserEngraverRecipes(), new BacterialVatRecipes(), new CuttingRecipes(), new BlastFurnaceRecipes(),
            new FluidExtractorRecipes(), new DecayHastenerRecipes(), new PreciseAssemblerRecipes(),
            new FuelRefiningComplexRecipes(), new CrackingRecipes(), new DistillationTowerRecipes(),
            new SpaceMinerRecipes(), new SpaceDrillRecipes(), new SpaceAssemblerRecipes(), new PCBFactoryRecipes(),
            new PlatinumBasedTreatmentRecipes(), new ShallowChemicalCouplingRecipes(), new BloodDemonInjectionRecipes(),
            new AlchemicChemistrySetRecipes(), new AdvancedCircuitAssemblyLineRecipes(), new FallingTowerRecipes(),
            new AssemblingLineRecipes(), new GasCollectorRecipes(), new EternalGregTechWorkshopUpgradeRecipes(),
            new FluidCannerRecipes(), new VacuumFreezerRecipes(), new MeteorsRecipes(), new CheatOreProcessingRecipes(),
            new ShapedArcaneCraftingRecipes(), new InfusionCraftingRecipes(), new ShimmerRecipes(),
            new SteamManufacturerRecipes(), new SteamCarpenterRecipe(), new LavaMakerRecipes(),
            new SteamWoodcutterRecipes(), new SteamGateAssemblerRecipes(), new CactusWonderFakeRecipes(),
            new InfernalCokeRecipes(), new SteamFusionReactorRecipes(), new SteamExtractinatorRecipes(),
            new RockBreakerRecipes(), new PrimitiveBrickKilnRecipes(), new TargetChamberRecipes(),
            new ElectrocellGeneratorRecipes(), new RocketAssemblerRecipes() };

        IRecipePool[] recipePoolsServerStart = new IRecipePool[] { new CircuitAssemblerConvertRecipes(),
            new AlloyBlastSmelterRecipes(), new VacuumFurnaceRecipes() };

        for (IRecipePool recipePool : recipePools) {
            recipePool.loadRecipes();
        }

        for (IRecipePool recipePoolServerStart : recipePoolsServerStart) {
            recipePoolServerStart.loadRecipes();
        }

        for (ItemStack stone : OreDictionary.getOres("stone")) {
            PortalToAlfheimOreRecipes.addManaInfusionOreRecipes(stone);
        }

        RecipeUtil
            .generateRecipesNotUsingCells(BartWorksRecipeMaps.bioLabRecipes, RecipePool.LargeBioLabRecipes, true, 1.1);

        for (RecipeMap<?> map : new RecipeMap<?>[] { RecipeMaps.transcendentPlasmaMixerRecipes,
            RecipeMaps.fusionRecipes }) {
            for (GTRecipe recipe : map.getAllRecipes()) {
                if (recipe == null) continue;

                GTRecipe copiedRecipe = recipe.copy();
                if (copiedRecipe == null) continue;

                FluidStack[] newInputs = recipe.mFluidOutputs;
                FluidStack[] newOutputs = recipe.mFluidInputs;

                if (newOutputs != null) {
                    for (FluidStack stack : newOutputs) {
                        if (stack != null) {
                            stack.amount = (int) (stack.amount * 0.9);
                        }
                    }
                }

                copiedRecipe.mFluidInputs = newInputs;
                copiedRecipe.mFluidOutputs = newOutputs;
                copiedRecipe.mEUt = 0;
                copiedRecipe.mDuration = 200;

                RecipePool.PlasmaCentrifugeRecipes.add(copiedRecipe);
            }
        }

        TCResearches.register();

        for (GTRecipe.RecipeAssemblyLine recipe : GTRecipe.RecipeAssemblyLine.sAssemblylineRecipes) {
            int duration = recipe.mDuration;

            if (duration > 200000) {
                duration /= 100;
            } else if (duration > 40000) {
                duration /= 10;
            } else if (duration > 10000) {
                duration /= 4;
            } else if (duration > 4000) {
                duration /= 2;
            }

            recipe.mDuration = Math.max(1, duration);
        }

        if (ModList.TwistSpaceTechnology.isModLoaded()) {
            AssemblyLineWithoutResearchRecipePool.loadRecipes();
        }
    }

    public static void loadVillageTrade() {
        for (int id = 0; id < 5; id++) {
            registerTradeForVillager(id);
        }

        // for (int id : VillagerRegistry.getRegisteredVillagers()) {
        // registerTradeForVillager(id);
        // }
    }

    @SuppressWarnings("unchecked")
    private static void registerTradeForVillager(int villagerId) {
        VillagerRegistry.instance()
            .registerVillageTradeHandler(villagerId, (villager, recipeList, random) -> {
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Items.diamond, 3),
                        Stick.setDisguisedStack(
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1),
                            9,
                            false)));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Items.emerald, 4),
                        Stick.setDisguisedStack(new ItemStack(Blocks.diamond_block, 1))));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Items.diamond, 1),
                        Stick.setDisguisedStack(new ItemStack(Items.emerald, 1))));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Items.emerald, 1),
                        Stick.setDisguisedStack(new ItemStack(Items.ender_eye, 1), 2, false)));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Items.diamond, 1),
                        Stick.setDisguisedStack(GTNLItemList.SatietyRing.get(1))));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Items.diamond, 1),
                        new ItemStack(Items.emerald, 1),
                        Stick.setDisguisedStack(GTNLItemList.VeinMiningPickaxe.get(1), 1, true)));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Blocks.snow, 1),
                        Stick.setDisguisedStack(
                            GTOreDictUnificator.get(OrePrefixes.nanite, MaterialsUEVplus.MagMatter, 1))));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Items.iron_ingot, 1),
                        GTModHandler.getModItem(Mods.Botania.ID, "bifrostPermPane", 1),
                        Stick.setDisguisedStack(
                            GTOreDictUnificator.get(GTModHandler.getModItem(Mods.Avaritia.ID, "Resource", 1, 6)))));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Items.nether_star, 1),
                        Stick.setDisguisedStack(CustomItemList.astralArrayFabricator.get(1))));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Items.compass, 1),
                        Stick.setDisguisedStack(ItemList.Transdimensional_Alignment_Matrix.get(1))));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Items.redstone, 1),
                        Stick.setDisguisedStack(GregtechItemList.Laser_Lens_Special.get(1))));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Blocks.redstone_block, 1),
                        Stick.setDisguisedStack(CustomItemList.Machine_DebugGenny.get(1))));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Blocks.dispenser, 1),
                        Stick.setDisguisedStack(CustomItemList.Machine_Multi_EyeOfHarmony.get(1))));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Blocks.furnace, 1),
                        Stick.setDisguisedStack(CustomItemList.Machine_Multi_ForgeOfGods.get(1))));
                recipeList.add(
                    new MerchantRecipe(new ItemStack(Blocks.coal_block, 1), Stick.setDisguisedStack(Loaders.AMForge)));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Blocks.tnt, 1),
                        Stick.setDisguisedStack(GTModHandler.getModItem(Mods.IndustrialCraft2.ID, "blockNuke", 1))));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Items.brick, 1),
                        Stick.setDisguisedStack(ItemList.Circuit_TranscendentMainframe.get(1))));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Items.bucket, 1),
                        Stick.setDisguisedStack(ReAvaItemList.InfinityBucket.get(1))));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Items.clock, 1),
                        Stick.setDisguisedStack(ReAvaItemList.ChronarchsClock.get(1))));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Items.wooden_sword, 1),
                        Stick.setDisguisedStack(ReAvaItemList.InfinitySword.get(1))));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Items.wooden_pickaxe, 1),
                        Stick.setDisguisedStack(ReAvaItemList.InfinityPickaxe.get(1))));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Items.wooden_axe, 1),
                        Stick.setDisguisedStack(ReAvaItemList.InfinityAxe.get(1))));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Items.wooden_shovel, 1),
                        Stick.setDisguisedStack(ReAvaItemList.InfinityShovel.get(1))));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Items.wooden_hoe, 1),
                        Stick.setDisguisedStack(ReAvaItemList.InfinityHoe.get(1))));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Items.leather_helmet, 1),
                        Stick.setDisguisedStack(GTModHandler.getModItem(Mods.Avaritia.ID, "Infinity_Helm", 1))));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Items.leather_chestplate, 1),
                        Stick.setDisguisedStack(GTModHandler.getModItem(Mods.Avaritia.ID, "Infinity_Chest", 1))));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Items.leather_leggings, 1),
                        Stick.setDisguisedStack(GTModHandler.getModItem(Mods.Avaritia.ID, "Infinity_Pants", 1))));
                recipeList.add(
                    new MerchantRecipe(
                        new ItemStack(Items.leather_boots, 1),
                        Stick.setDisguisedStack(GTModHandler.getModItem(Mods.Avaritia.ID, "Infinity_Shoes", 1))));

                if (Mods.SGCraft.isModLoaded()) {
                    recipeList.add(
                        new MerchantRecipe(
                            GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Naquadah, 9),
                            Stick.setDisguisedStack(GTModHandler.getModItem(Mods.SGCraft.ID, "stargateRing", 1, 0))));
                    recipeList.add(
                        new MerchantRecipe(
                            GTOreDictUnificator.get(OrePrefixes.ingot, Materials.NaquadahEnriched, 9),
                            Stick.setDisguisedStack(GTModHandler.getModItem(Mods.SGCraft.ID, "stargateRing", 1, 1))));
                    recipeList.add(
                        new MerchantRecipe(
                            GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Naquadria, 9),
                            Stick.setDisguisedStack(GTModHandler.getModItem(Mods.SGCraft.ID, "stargateBase", 1))));
                }
                if (Mods.EternalSingularity.isModLoaded()) {
                    recipeList.add(
                        new MerchantRecipe(
                            new ItemStack(Blocks.beacon, 1),
                            Stick.setDisguisedStack(
                                GTModHandler.getModItem(Mods.EternalSingularity.ID, "eternal_singularity", 1))));
                }
            });
    }

    private static void loadCircuitRelatedRecipes() {
        RecipeUtil.copyAllRecipes(RecipePool.ConvertToCircuitAssembler, RecipeMaps.circuitAssemblerRecipes);

        new CircuitAssemblyLineRecipes().loadRecipes();

        if (!recipesAdded && ModList.TwistSpaceTechnology.isModLoaded()) {
            CircuitAssemblyLineWithoutImprintRecipePool.loadRecipes();
        }
    }

    private static void registerBuffTargetChamberRecipe() {
        Collection<GTRecipe> targetChamberRecipe = LanthanidesRecipeMaps.targetChamberRecipes.getAllRecipes();
        LanthanidesRecipeMaps.targetChamberRecipes.getBackend()
            .clearRecipes();

        Map<ItemStack, Integer> waferMultiplier = new HashMap<>() {

            {
                put(ItemList.Circuit_Silicon_Wafer2.get(1), 1);
                put(ItemList.Circuit_Silicon_Wafer3.get(1), 2);
                put(ItemList.Circuit_Silicon_Wafer4.get(1), 4);
                put(ItemList.Circuit_Silicon_Wafer5.get(1), 8);
                put(ItemList.Circuit_Silicon_Wafer6.get(1), 16);
                put(ItemList.Circuit_Silicon_Wafer7.get(1), 32);
            }
        };
        for (GTRecipe recipe : targetChamberRecipe) {
            for (Map.Entry<ItemStack, Integer> entry : waferMultiplier.entrySet()) {
                if (recipe.mInputs[1].isItemEqual(entry.getKey())) {
                    int multiplier = entry.getValue();
                    for (ItemStack itemStack : recipe.mOutputs) {
                        itemStack.stackSize *= multiplier;
                    }
                } else {
                    for (ItemStack itemStack : recipe.mOutputs) {
                        itemStack.stackSize *= 4;
                    }
                }
                break;
            }
            LanthanidesRecipeMaps.targetChamberRecipes.add(recipe);
        }
    }
}
