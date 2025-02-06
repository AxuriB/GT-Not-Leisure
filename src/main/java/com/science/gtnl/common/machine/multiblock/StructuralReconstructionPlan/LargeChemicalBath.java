package com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.GregTechAPI.sBlockCasings2;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gtPlusPlus.core.block.ModBlocks.blockCasings2Misc;

import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.Utils.item.TextUtils;
import com.science.gtnl.common.machine.multiMachineClasses.GTMMultiMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.SoundResource;
import gregtech.api.enums.TAE;
import gregtech.api.enums.Textures;
import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gtPlusPlus.api.recipe.GTPPRecipeMaps;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock;

public class LargeChemicalBath extends GTMMultiMachineBase<LargeChemicalBath> implements ISurvivalConstructable {

    public static final String STRUCTURE_PIECE_MAIN = "main";
    private static IStructureDefinition<LargeChemicalBath> STRUCTURE_DEFINITION = null;
    public static final String LCB_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/large_chemical_bath";
    private static final int MACHINEMODE_OREWASH = 0;
    private static final int MACHINEMODE_SIMPLEWASH = 1;
    private static final int MACHINEMODE_CHEMBATH = 2;
    public final int horizontalOffSet = 2;
    public final int verticalOffSet = 1;
    public final int depthOffSet = 0;
    public static String[][] shape;

    public LargeChemicalBath(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        shape = StructureUtils.readStructureFromFile(LCB_STRUCTURE_FILE_PATH);
    }

    public LargeChemicalBath(String aName) {
        super(aName);
        shape = StructureUtils.readStructureFromFile(LCB_STRUCTURE_FILE_PATH);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new LargeChemicalBath(this.mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(TexturesGtBlock.oMCDIndustrialWashPlantActive)
                    .extFacing()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(TexturesGtBlock.oMCDIndustrialWashPlant)
                    .extFacing()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    public int getCasingTextureID() {
        return (byte) TAE.GTPP_INDEX(11);
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        switch (machineMode) {
            case MACHINEMODE_OREWASH -> {
                return RecipeMaps.oreWasherRecipes;
            }
            case MACHINEMODE_SIMPLEWASH -> {
                return GTPPRecipeMaps.simpleWasherRecipes;
            }
            default -> {
                return RecipeMaps.chemicalBathRecipes;
            }
        }
    }

    @Nonnull
    @Override
    public Collection<RecipeMap<?>> getAvailableRecipeMaps() {
        return Arrays
            .asList(RecipeMaps.oreWasherRecipes, GTPPRecipeMaps.simpleWasherRecipes, RecipeMaps.chemicalBathRecipes);
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.LargeChemicalBathRecipeType)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_00)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_01)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_02)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_03)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(5, 3, 7, true)
            .addInputHatch(TextLocalization.Tooltip_LargeChemicalBath_Casing)
            .addOutputHatch(TextLocalization.Tooltip_LargeChemicalBath_Casing)
            .addInputBus(TextLocalization.Tooltip_LargeChemicalBath_Casing)
            .addOutputBus(TextLocalization.Tooltip_LargeChemicalBath_Casing)
            .addEnergyHatch(TextLocalization.Tooltip_LargeChemicalBath_Casing)
            .addMaintenanceHatch(TextLocalization.Tooltip_LargeChemicalBath_Casing)
            .toolTipFinisher(TextUtils.SNL + TextUtils.SRP);
        return tt;
    }

    @Override
    public IStructureDefinition<LargeChemicalBath> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<LargeChemicalBath>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofBlock(sBlockCasings2, 14))
                .addElement(
                    'B',
                    buildHatchAdder(LargeChemicalBath.class).casingIndex(getCasingTextureID())
                        .dot(1)
                        .atLeast(InputHatch, OutputHatch, InputBus, OutputBus, Maintenance, Energy)
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(blockCasings2Misc, 4))))
                .addElement(
                    'C',
                    ofChain(ofBlock(Blocks.air, 0), ofBlock(Blocks.flowing_water, 0), ofBlock(Blocks.water, 0)))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    public int nextMachineMode() {
        if (machineMode == MACHINEMODE_OREWASH) return MACHINEMODE_SIMPLEWASH;
        else if (machineMode == MACHINEMODE_SIMPLEWASH) return MACHINEMODE_CHEMBATH;
        else return MACHINEMODE_OREWASH;
    }

    @Override
    public void setMachineModeIcons() {
        machineModeIcons.clear();
        machineModeIcons.add(GTUITextures.OVERLAY_BUTTON_MACHINEMODE_WASHPLANT);
        machineModeIcons.add(GTUITextures.OVERLAY_BUTTON_MACHINEMODE_SIMPLEWASHER);
        machineModeIcons.add(GTUITextures.OVERLAY_BUTTON_MACHINEMODE_CHEMBATH);
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected SoundResource getActivitySoundLoop() {
        return SoundResource.GT_MACHINES_MULTI_ORE_WASHER_PLANT_LOOP;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("mode", machineMode);
    }

    @Override
    public void loadNBTData(final NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        machineMode = aNBT.getInteger("mode");
    }

    @Override
    public void addUIWidgets(ModularWindow.Builder builder, UIBuildContext buildContext) {
        super.addUIWidgets(builder, buildContext);
        setMachineModeIcons();
        builder.widget(createModeSwitchButton(builder));
    }

    @Override
    public final void onScrewdriverRightClick(ForgeDirection side, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        this.machineMode = (byte) ((this.machineMode + 1) % 2);
        GTUtility
            .sendChatToPlayer(aPlayer, StatCollector.translateToLocal("LargeChemicalBath_Mode_" + this.machineMode));
    }

    @Override
    public String getMachineModeName() {
        return StatCollector.translateToLocal("LargeChemicalBath_Mode_" + machineMode);
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        mCasing = 0;
        ParallelTier = 0;

        if (checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet) && checkHatch()
            && mCasing >= 55) {
            replaceWater();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean supportsMachineModeSwitch() {
        return true;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, horizontalOffSet, verticalOffSet, depthOffSet);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (mMachine) return -1;
        return survivialBuildPiece(
            STRUCTURE_PIECE_MAIN,
            stackSize,
            horizontalOffSet,
            verticalOffSet,
            depthOffSet,
            elementBudget,
            env,
            false,
            true);
    }

    public void replaceWater() {
        IGregTechTileEntity aBaseMetaTileEntity = this.getBaseMetaTileEntity();
        World world = aBaseMetaTileEntity.getWorld();
        int baseX = aBaseMetaTileEntity.getXCoord();
        int baseY = aBaseMetaTileEntity.getYCoord();
        int baseZ = aBaseMetaTileEntity.getZCoord();

        ForgeDirection frontFacing = aBaseMetaTileEntity.getFrontFacing();
        ForgeDirection backFacing = frontFacing.getOpposite();

        ForgeDirection perpDir = backFacing.getRotation(ForgeDirection.DOWN);
        int perpX = perpDir.offsetX;
        int perpZ = perpDir.offsetZ;

        for (int step = 5; step >= 1; step--) {
            int mainX = backFacing.offsetX * step;
            int mainZ = backFacing.offsetZ * step;

            for (int offsetX = -1; offsetX <= 1; offsetX++) {
                for (int offsetY = 0; offsetY <= 1; offsetY++) {
                    int x = baseX + mainX + perpX * offsetX;
                    int y = baseY + offsetY;
                    int z = baseZ + mainZ + perpZ * offsetX;

                    Block block = world.getBlock(x, y, z);
                    if (block == Blocks.air || block == Blocks.flowing_water) {
                        world.setBlock(x, y, z, Blocks.water, 0, 3);
                    }
                }
            }
        }
    }
}
