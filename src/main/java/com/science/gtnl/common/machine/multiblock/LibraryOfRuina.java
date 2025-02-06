package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaBlockGlass;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaCasing;
import static goodgenerator.loader.Loaders.gravityStabilizationCasing;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.enums.Mods.*;
import static gregtech.api.enums.Textures.BlockIcons.casingTexturePages;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gtnhlanth.common.register.LanthItemList.SHIELDED_ACCELERATOR_CASING;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizon.structurelib.alignment.IAlignmentLimits;
import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.gtnewhorizons.gtnhintergalactic.block.IGBlocks;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.Utils.item.TextUtils;
import com.science.gtnl.common.machine.multiMachineClasses.GTMMultiMachineBase;
import com.science.gtnl.common.recipe.RecipeRegister;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;

public class LibraryOfRuina extends GTMMultiMachineBase<LibraryOfRuina> implements ISurvivalConstructable {

    public int multiTier = 0;
    private int mCasing;
    private static IStructureDefinition<LibraryOfRuina> STRUCTURE_DEFINITION = null;
    public static final String STRUCTURE_PIECE_MAIN = "main";
    public static String[][] shape;
    public static final String LOR_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/library_of_ruina";
    public final int horizontalOffSet = 34;
    public final int verticalOffSet = 34;
    public final int depthOffSet = 20;

    public LibraryOfRuina(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        shape = StructureUtils.readStructureFromFile(LOR_STRUCTURE_FILE_PATH);
    }

    public LibraryOfRuina(String aName) {
        super(aName);
        shape = StructureUtils.readStructureFromFile(LOR_STRUCTURE_FILE_PATH);
    }

    @Override
    public boolean isEnablePerfectOverclock() {
        return true;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new LibraryOfRuina(this.mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { casingTexturePages[1][14], TextureFactory.builder()
                .addIcon(Textures.BlockIcons.OVERLAY_FRONT_OIL_CRACKER_ACTIVE)
                .extFacing()
                .build(),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_FRONT_OIL_CRACKER_ACTIVE_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
            return new ITexture[] { casingTexturePages[1][14], TextureFactory.builder()
                .addIcon(Textures.BlockIcons.OVERLAY_FRONT_OIL_CRACKER)
                .extFacing()
                .build(),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_FRONT_OIL_CRACKER_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
        }
        return new ITexture[] { casingTexturePages[1][14] };
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeRegister.TheTwilightForestRecipes;
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.LibraryOfRuinaRecipeType)
            .addInfo(TextLocalization.Tooltip_LibraryOfRuina_00)
            .addInfo(TextLocalization.Tooltip_LibraryOfRuina_01)
            .addInfo(TextLocalization.Tooltip_LibraryOfRuina_02)
            .addInfo(TextLocalization.Tooltip_LibraryOfRuina_03)
            .addInfo(TextLocalization.Tooltip_LibraryOfRuina_04)
            .addInfo(TextLocalization.Tooltip_LibraryOfRuina_05)
            .addInfo(TextLocalization.Tooltip_LibraryOfRuina_06)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(69, 51, 73, true)
            .addInputHatch(TextLocalization.Tooltip_LibraryOfRuina_Casing)
            .addOutputHatch(TextLocalization.Tooltip_LibraryOfRuina_Casing)
            .addOutputBus(TextLocalization.Tooltip_LibraryOfRuina_Casing)
            .addEnergyHatch(TextLocalization.Tooltip_LibraryOfRuina_Casing)
            .addMaintenanceHatch(TextLocalization.Tooltip_LibraryOfRuina_Casing)
            .toolTipFinisher(TextUtils.SNL + "§r X §l§o§f年§6轮§f新§6城§f计§6划 §r§fby 咸到老时变成鱼");
        return tt;
    }

    @Override
    public IStructureDefinition<LibraryOfRuina> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<LibraryOfRuina>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofBlockAnyMeta(gravityStabilizationCasing))
                .addElement('B', ofBlock(MetaCasing, 13))
                .addElement('C', ofBlock(IGBlocks.SpaceElevatorCasing, 1))
                .addElement(
                    'D',
                    buildHatchAdder(LibraryOfRuina.class)
                        .atLeast(InputHatch, OutputHatch, InputBus, OutputBus, Maintenance, Energy.or(ExoticEnergy))
                        .casingIndex(GTUtility.getCasingTextureIndex(GregTechAPI.sBlockCasings5, 14))
                        .dot(1)
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlockAnyMeta(SHIELDED_ACCELERATOR_CASING))))
                .addElement('E', ofBlock(sBlockCasings10, 4))
                .addElement('F', ofBlock(sBlockCasings10, 11))
                .addElement('G', ofBlock(sBlockCasings9, 11))
                .addElement('H', ofBlock(MetaBlockGlass, 2))
                .addElement(
                    'I',
                    ofChain(
                        ofBlockAnyMeta(
                            TwilightForest.isModLoaded() ? GameRegistry.findBlock(TwilightForest.ID, "tile.TFPortal")
                                : Blocks.end_portal),
                        ofBlock(Blocks.water, 0)))
                .build();
        }
        return STRUCTURE_DEFINITION;
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

    @Override
    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (d, r, f) -> d.offsetY == 0 && r.isNotRotated() && !f.isVerticallyFliped();
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        mCasing = 0;
        this.multiTier = getMultiTier(aStack);

        if (checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet) && checkHatch()
            && mCasing >= 920
            && multiTier == 1) {
            replaceWaterWithPortal();
            return true;
        } else {
            replacePortalWithWater();
            return false;
        }
    }

    public int getMultiTier(ItemStack inventory) {
        if (inventory == null) return 0;
        return inventory.isItemEqual(GTModHandler.getModItem(NewHorizonsCoreMod.ID, "item.TwilightCrystal", 1)) ? 1 : 0;
    }

    public void replaceWaterWithPortal() {
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

        Block targetBlock = Blocks.end_portal;
        if (TwilightForest.isModLoaded()) {
            targetBlock = GameRegistry.findBlock(TwilightForest.ID, "tile.TFPortal");
            if (targetBlock == null) targetBlock = Blocks.end_portal;
        }

        for (int step = 10; step >= 8; step--) {
            int mainX = backFacing.offsetX * step;
            int mainZ = backFacing.offsetZ * step;

            for (int offset = -1; offset <= 1; offset++) {
                int x = baseX + mainX + perpX * offset;
                int z = baseZ + mainZ + perpZ * offset;
                int y = baseY - 1;

                Block block = world.getBlock(x, y, z);
                if (block == Blocks.water || block == Blocks.flowing_water) {
                    world.setBlock(x, y, z, targetBlock, 0, 3);
                }
            }
        }
    }

    public void replacePortalWithWater() {
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

        for (int step = 10; step >= 8; step--) {
            int mainX = backFacing.offsetX * step;
            int mainZ = backFacing.offsetZ * step;

            for (int offset = -1; offset <= 1; offset++) {
                int x = baseX + mainX + perpX * offset;
                int z = baseZ + mainZ + perpZ * offset;
                int y = baseY - 1;

                Block block = world.getBlock(x, y, z);
                if (block == Blocks.end_portal || (TwilightForest.isModLoaded()
                    && block == GameRegistry.findBlock(TwilightForest.ID, "tile.TFPortal"))) {
                    world.setBlock(x, y, z, Blocks.water, 0, 3);
                }
            }
        }
    }
}
