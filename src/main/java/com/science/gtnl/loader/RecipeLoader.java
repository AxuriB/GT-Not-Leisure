package com.science.gtnl.loader;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.Nxer.TwistSpaceTechnology.recipe.machineRecipe.expanded.CircuitAssemblyLineWithoutImprintRecipePool;
import com.science.gtnl.Utils.recipes.RecipeUtil;
import com.science.gtnl.common.machine.OreProcessing.OP_NormalProcessing;
import com.science.gtnl.common.machine.multiMachineClasses.ProcessingArrayRecipeLoader;
import com.science.gtnl.common.materials.MaterialPool;
import com.science.gtnl.common.recipe.AprilFool.CactusWonderFakeRecipes;
import com.science.gtnl.common.recipe.AprilFool.CraftingTableAprilFoolRecipes;
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
import com.science.gtnl.common.recipe.GTNL.ElementCopyingRecipes;
import com.science.gtnl.common.recipe.GTNL.EternalGregTechWorkshopUpgradeRecipes;
import com.science.gtnl.common.recipe.GTNL.ExtremeExtremeEntityCrusherRecipes;
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
import com.science.gtnl.common.recipe.GTNL.RareEarthCentrifugalRecipes;
import com.science.gtnl.common.recipe.GTNL.ReFusionReactorRecipes;
import com.science.gtnl.common.recipe.GTNL.RealArtificialStarRecipes;
import com.science.gtnl.common.recipe.GTNL.ShallowChemicalCouplingRecipes;
import com.science.gtnl.common.recipe.GTNL.ShapedArcaneCraftingRecipes;
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
import com.science.gtnl.common.recipe.GregTech.ServerStart.CircuitAssemblerConvertRecipes;
import com.science.gtnl.common.recipe.GregTech.ServerStart.CircuitAssemblyLineRecipes;
import com.science.gtnl.common.recipe.GregTech.ServerStart.FormingPressRecipes;
import com.science.gtnl.common.recipe.GregTech.SpaceAssemblerRecipes;
import com.science.gtnl.common.recipe.GregTech.TranscendentPlasmaMixerRecipes;
import com.science.gtnl.common.recipe.GregTech.VacuumFreezerRecipes;
import com.science.gtnl.common.recipe.GregTech.VacuumFurnaceRecipes;
import com.science.gtnl.common.recipe.GregTech.multiDehydratorRecipes;
import com.science.gtnl.common.recipe.Special.OreDictionary.PortalToAlfheimOreRecipes;
import com.science.gtnl.common.recipe.Thaumcraft.TCRecipePool;
import com.science.gtnl.common.recipe.Thaumcraft.TCResearches;

import bartworks.API.recipe.BartWorksRecipeMaps;
import goodgenerator.util.CrackRecipeAdder;
import gregtech.api.enums.Mods;
import gregtech.api.recipe.RecipeMaps;

public class RecipeLoader {

    private static boolean recipesAdded;

    public static void loadRecipesServerStart() {
        if (!recipesAdded) {
            loadRecipes();
        }
        loadCircuitRelatedRecipes();
        recipesAdded = true;
    }

    public static void loadRecipes() {

        MeteorsRecipes.registerMeteors();

        TCRecipePool.loadRecipes();
        TCResearches.register();

        new OP_NormalProcessing().enumOreProcessingRecipes();
        new ShapedArcaneCraftingRecipes().loadRecipes();
        new InfusionCraftingRecipes().loadRecipes();
        ProcessingArrayRecipeLoader.registerDefaultGregtechMaps();

        CrackRecipeAdder.reAddBlastRecipe(MaterialPool.MolybdenumDisilicide, 800, 1920, 2300, true);
        CrackRecipeAdder.reAddBlastRecipe(MaterialPool.HSLASteel, 1000, 480, 1711, true);
        CrackRecipeAdder.reAddBlastRecipe(MaterialPool.Germaniumtungstennitride, 800, 30720, 8200, true);

        if (Mods.MobsInfo.isModLoaded()) {
            ExtremeExtremeEntityCrusherRecipes.init();
        }

        IRecipePool[] recipePools = new IRecipePool[] { new ChemicalRecipes(), new ElectrolyzerRecipes(),
            new MixerRecipes(), new multiDehydratorRecipes(), new AssemblerRecipes(), new AutoclaveRecipes(),
            new AlloyBlastSmelterRecipes(), new CompressorRecipes(), new ReFusionReactorRecipes(),
            new RealArtificialStarRecipes(), new PortalToAlfheimRecipes(), new NatureSpiritArrayRecipes(),
            new ManaInfusionRecipes(), new TranscendentPlasmaMixerRecipes(), new PlasmaForgeRecipes(),
            new CraftingTableRecipes(), new ChemicalBathRecipes(), new SteamCrackerRecipes(), new DesulfurizerRecipes(),
            new PetrochemicalPlantRecipes(), new FusionReactorRecipes(), new SmeltingMixingFurnaceRecipes(),
            new FluidExtraction(), new DigesterRecipes(), new DissolutionTankRecipes(), new CentrifugeRecipes(),
            new ChemicalDehydratorRecipes(), new RareEarthCentrifugalRecipes(), new MatterFabricatorRecipes(),
            new TheTwilightForestRecipes(), new IsaMillRecipes(), new CellRegulatorRecipes(),
            new VacuumFurnaceRecipes(), new FishingGroundRecipes(), new DistilleryRecipes(),
            new ElementCopyingRecipes(), new AlloySmelterRecipes(), new MolecularTransformerRecipes(),
            new NaquadahReactorRecipes(), new DragonEvolutionFusionCraftingRecipes(), new LaserEngraverRecipes(),
            new BacterialVatRecipes(), new CuttingRecipes(), new BlastFurnaceRecipes(), new FluidExtractorRecipes(),
            new DecayHastenerRecipes(), new PreciseAssemblerRecipes(), new FuelRefiningComplexRecipes(),
            new CrackingRecipes(), new DistillationTowerRecipes(), new SpaceMinerRecipes(), new SpaceDrillRecipes(),
            new SpaceAssemblerRecipes(), new PCBFactoryRecipes(), new PlatinumBasedTreatmentRecipes(),
            new ShallowChemicalCouplingRecipes(), new BloodDemonInjectionRecipes(), new AlchemicChemistrySetRecipes(),
            new AdvancedCircuitAssemblyLineRecipes(), new FallingTowerRecipes(), new AssemblingLineRecipes(),
            new GasCollectorRecipes(), new EternalGregTechWorkshopUpgradeRecipes(), new FluidCannerRecipes(),
            new VacuumFreezerRecipes() };

        IRecipePool[] recipePoolsAprilFool = new IRecipePool[] { new CraftingTableAprilFoolRecipes(),
            new SteamManufacturerRecipes(), new SteamCarpenterRecipe(), new LavaMakerRecipes(),
            new SteamWoodcutterRecipes(), new SteamGateAssemblerRecipes(), new CactusWonderFakeRecipes(),
            new InfernalCokeRecipes(), new SteamFusionReactorRecipes(), new SteamExtractinatorRecipes(),
            new RockBreakerRecipes()

        };

        IRecipePool[] recipePoolsServerStart = new IRecipePool[] { new FormingPressRecipes(),
            new CircuitAssemblerConvertRecipes(), new AlloyBlastSmelterRecipes(), new VacuumFurnaceRecipes() };

        for (IRecipePool recipePool : recipePools) {
            recipePool.loadRecipes();
        }

        for (IRecipePool recipePoolAprilFool : recipePoolsAprilFool) {
            recipePoolAprilFool.loadRecipes();
        }

        for (IRecipePool recipePoolServerStart : recipePoolsServerStart) {
            recipePoolServerStart.loadRecipes();
        }

        for (ItemStack stone : OreDictionary.getOres("stone")) {
            PortalToAlfheimOreRecipes.addManaInfusionOreRecipes(stone);
        }

        RecipeUtil
            .generateRecipesNotUsingCells(BartWorksRecipeMaps.bioLabRecipes, RecipeRegister.LargeBioLabRecipes, true);
    }

    private static void loadCircuitRelatedRecipes() {
        RecipeUtil.copyAllRecipes(RecipeRegister.ConvertToCircuitAssembler, RecipeMaps.circuitAssemblerRecipes);

        new CircuitAssemblyLineRecipes().loadRecipes();

        if (!recipesAdded && com.science.gtnl.Utils.enums.Mods.TwistSpaceTechnology.isModLoaded()) {
            CircuitAssemblyLineWithoutImprintRecipePool.loadRecipes();
        }
    }
}
