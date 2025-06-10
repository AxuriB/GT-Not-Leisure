package com.science.gtnl.common.machine.multiblock.SteamElevator;

import static gregtech.api.enums.GTValues.V;
import static mods.railcraft.common.util.inventory.InvTools.isItemEqualIgnoreNBT;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import com.science.gtnl.loader.RecipePool;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.MultiblockTooltipBuilder;

public class SteamWeatherModule extends SteamElevatorModule {

    public SteamWeatherModule(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional, 1);
    }

    public SteamWeatherModule(String aName) {
        super(aName, 1);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new SteamWeatherModule(this.mName);
    }

    @Override
    public String getMachineType() {
        return StatCollector.translateToLocal("SteamWeatherModuleRecipeType");
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return null;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        final MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(StatCollector.translateToLocal("SteamWeatherModuleRecipeType"))
            .addInfo(StatCollector.translateToLocal("Tooltip_SteamWeatherModule_01"))
            .addSeparator()
            .addInfo(StatCollector.translateToLocal("StructureTooComplex"))
            .addInfo(StatCollector.translateToLocal("BLUE_PRINT_INFO"))
            .beginStructureBlock(1, 5, 2, false)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipePool.SteamWeatherModuleFakeRecipes;
    }

    @Override
    @NotNull
    public CheckRecipeResult checkProcessing() {
        List<ItemStack> inputStacks = getStoredInputs();
        List<FluidStack> inputFluids = getStoredFluids();

        for (GTRecipe recipe : RecipePool.SteamWeatherModuleFakeRecipes.getAllRecipes()) {
            ItemStack[] recipeItems = recipe.mInputs.clone();
            FluidStack[] recipeFluids = recipe.mFluidInputs.clone();
            int specialValue = recipe.mSpecialValue;
            boolean matched = true;

            for (ItemStack recipeStack : recipeItems) {
                if (recipeStack == null) continue;

                boolean foundMatch = false;
                for (ItemStack inputStack : inputStacks) {
                    if (inputStack == null) continue;

                    if (isItemEqualIgnoreNBT(inputStack, recipeStack)
                        && inputStack.stackSize >= recipeStack.stackSize) {
                        foundMatch = true;
                        break;
                    }
                }

                if (!foundMatch) {
                    matched = false;
                    break;
                }
            }

            if (matched) {
                for (FluidStack recipeFluid : recipeFluids) {
                    if (recipeFluid == null) continue;

                    boolean foundFluid = false;
                    for (FluidStack inputFluid : inputFluids) {
                        if (inputFluid == null) continue;

                        if (inputFluid.isFluidEqual(recipeFluid) && inputFluid.amount >= recipeFluid.amount) {
                            foundFluid = true;
                            break;
                        }
                    }

                    if (!foundFluid) {
                        matched = false;
                        break;
                    }
                }
            }

            if (matched) {
                for (ItemStack recipeStack : recipe.mInputs) {
                    if (recipeStack != null) {
                        if (!depleteInput(recipeStack)) {
                            return CheckRecipeResultRegistry.NO_RECIPE;
                        }
                    }
                }
                for (FluidStack recipeFluid : recipe.mFluidInputs) {
                    if (recipeFluid != null) {
                        if (!depleteInput(recipeFluid)) {
                            return CheckRecipeResultRegistry.NO_RECIPE;
                        }
                    }
                }

                switch (specialValue) {
                    case 1:
                        getBaseMetaTileEntity().getWorld()
                            .getWorldInfo()
                            .setRaining(false);
                        getBaseMetaTileEntity().getWorld()
                            .getWorldInfo()
                            .setThundering(false);
                        break;
                    case 2:
                        getBaseMetaTileEntity().getWorld()
                            .getWorldInfo()
                            .setRaining(true);
                        getBaseMetaTileEntity().getWorld()
                            .getWorldInfo()
                            .setRainTime(50000);
                        getBaseMetaTileEntity().getWorld()
                            .getWorldInfo()
                            .setThundering(false);
                        break;
                    case 3:
                        getBaseMetaTileEntity().getWorld()
                            .getWorldInfo()
                            .setRaining(true);
                        getBaseMetaTileEntity().getWorld()
                            .getWorldInfo()
                            .setRainTime(50000);
                        getBaseMetaTileEntity().getWorld()
                            .getWorldInfo()
                            .setThundering(true);
                        getBaseMetaTileEntity().getWorld()
                            .getWorldInfo()
                            .setThunderTime(50000);
                        break;
                }
                lEUt = V[3];
                mMaxProgresstime = 128;
                return CheckRecipeResultRegistry.SUCCESSFUL;
            }
        }

        return CheckRecipeResultRegistry.NO_RECIPE;
    }

    @Override
    public boolean onRunningTick(ItemStack stack) {
        if (mProgresstime == 1) {
            final IGregTechTileEntity tileEntity = getBaseMetaTileEntity();
            if (tileEntity == null) {
                return super.onRunningTick(stack);
            }
            int x = tileEntity.getXCoord();
            int y = tileEntity.getYCoord();
            int z = tileEntity.getZCoord();
            World world = tileEntity.getWorld();

            List<EntityPlayer> playersInRange = world.getEntitiesWithinAABB(
                EntityPlayer.class,
                AxisAlignedBB.getBoundingBox(
                    x - getMachineEffectRange(),
                    y - getMachineEffectRange(),
                    z - getMachineEffectRange(),
                    x + getMachineEffectRange(),
                    y + getMachineEffectRange(),
                    z + getMachineEffectRange()));
            for (EntityPlayer player : playersInRange) {
                double distance = player.getDistance(x, y, z);
                if (distance <= getMachineEffectRange()) {
                    player.addPotionEffect(new PotionEffect(AlchemicalWizardry.customPotionFlightID, 1000, 1));
                }
            }
        }
        return super.onRunningTick(stack);
    }

    @Override
    protected int getMachineEffectRange() {
        return 64 * recipeOcCount;
    }
}
