package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.enums.Mods.ExtraUtilities;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;
import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.common.machine.multiMachineClasses.MultiMachineBase;
import com.science.gtnl.loader.BlockLoader;
import com.science.gtnl.loader.RecipeRegister;

import gregtech.api.GregTechAPI;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.common.blocks.BlockCasings1;
import gregtech.common.blocks.BlockCasings8;

public class LapotronChip extends MultiMachineBase<LapotronChip> implements ISurvivalConstructable {

    public int tierLapisCaelestis = -1;
    public int tierGlass1 = -1;
    public int tierGlass2 = -1;

    public static final int HORIZONTAL_OFF_SET = 88;
    public static final int VERTICAL_OFF_SET = 97;
    public static final int DEPTH_OFF_SET = 11;

    public int tCountCasing = 0;

    public static final String STRUCTURE_PIECE_MAIN = "main";
    public static final String LC_STRUCTURE_FILE_PATH = RESOURCE_ROOT_ID + ":" + "multiblock/lapotron_chip"; // 文件路径
    public static String[][] shape = StructureUtils.readStructureFromFile(LC_STRUCTURE_FILE_PATH);

    public LapotronChip(String aName) {
        super(aName);
    }

    public LapotronChip(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    @Override
    protected boolean isEnablePerfectOverclock() {
        return false;
    }

    @Override
    protected float getSpeedBonus() {
        return 1;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new LapotronChip(this.mName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(StatCollector.translateToLocal("LapotronChipRecipeType"))
            .addInfo(StatCollector.translateToLocal("Tooltip_LapotronChip_00"))
            .addInfo(StatCollector.translateToLocal("Tooltip_LapotronChip_01"))
            .addSeparator()
            .addInfo(StatCollector.translateToLocal("StructureTooComplex"))
            .addInfo(StatCollector.translateToLocal("BLUE_PRINT_INFO"))
            .beginStructureBlock(177, 121, 177, true)
            .addInputBus(StatCollector.translateToLocal("Tooltip_LapotronChip_Casing"), 1)
            .addOutputBus(StatCollector.translateToLocal("Tooltip_LapotronChip_Casing"), 1)
            .addInputHatch(StatCollector.translateToLocal("Tooltip_LapotronChip_Casing"), 1)
            .addOutputHatch(StatCollector.translateToLocal("Tooltip_LapotronChip_Casing"), 1)
            .addEnergyHatch(StatCollector.translateToLocal("Tooltip_LapotronChip_Casing"), 1)
            .addMaintenanceHatch(StatCollector.translateToLocal("Tooltip_LapotronChip_Casing"), 1)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public int getCasingTextureID() {
        return ((BlockCasings1) GregTechAPI.sBlockCasings1).getTextureIndex(11);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_DTPF_ON)
                    .extFacing()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_DTPF_OFF)
                    .extFacing()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    @Override
    public IStructureDefinition<LapotronChip> getStructureDefinition() {
        return StructureDefinition.<LapotronChip>builder()
            .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
            .addElement('A', ofBlockAnyMeta(Blocks.iron_block))
            .addElement(
                'B',
                ExtraUtilities.isModLoaded()
                    ? withChannel(
                        "tierLapisCaelestis",
                        ofBlocksTiered(
                            LapotronChip::tierLapisCaelestis,
                            ImmutableList.of(
                                Pair.of(Block.getBlockFromName("ExtraUtilities:greenscreen"), 0),
                                Pair.of(Block.getBlockFromName("ExtraUtilities:greenscreen"), 1),
                                Pair.of(Block.getBlockFromName("ExtraUtilities:greenscreen"), 2),
                                Pair.of(Block.getBlockFromName("ExtraUtilities:greenscreen"), 3),
                                Pair.of(Block.getBlockFromName("ExtraUtilities:greenscreen"), 4),
                                Pair.of(Block.getBlockFromName("ExtraUtilities:greenscreen"), 5),
                                Pair.of(Block.getBlockFromName("ExtraUtilities:greenscreen"), 6),
                                Pair.of(Block.getBlockFromName("ExtraUtilities:greenscreen"), 7),
                                Pair.of(Block.getBlockFromName("ExtraUtilities:greenscreen"), 8),
                                Pair.of(Block.getBlockFromName("ExtraUtilities:greenscreen"), 9),
                                Pair.of(Block.getBlockFromName("ExtraUtilities:greenscreen"), 10),
                                Pair.of(Block.getBlockFromName("ExtraUtilities:greenscreen"), 11),
                                Pair.of(Block.getBlockFromName("ExtraUtilities:greenscreen"), 12),
                                Pair.of(Block.getBlockFromName("ExtraUtilities:greenscreen"), 13),
                                Pair.of(Block.getBlockFromName("ExtraUtilities:greenscreen"), 14),
                                Pair.of(Block.getBlockFromName("ExtraUtilities:greenscreen"), 15)),
                            -1,
                            (t, m) -> t.tierLapisCaelestis = m,
                            t -> t.tierLapisCaelestis))
                    : isAir())
            .addElement('C', ofBlock(sBlockCasings9, 7))
            .addElement('D', ofBlock(sBlockCasings1, 11))
            .addElement(
                'E',
                buildHatchAdder(LapotronChip.class)
                    .atLeast(InputBus, OutputBus, InputHatch, Maintenance, Energy, Energy.or(ExoticEnergy))
                    .casingIndex(((BlockCasings8) GregTechAPI.sBlockCasings8).getTextureIndex(10))
                    .dot(1)
                    .buildAndChain(onElementPass(x -> ++x.tCountCasing, ofBlock(sBlockCasings8, 10))))
            .addElement('F', ofBlock(BlockLoader.MetaBlockGlow, 0))
            .addElement(
                'G',
                withChannel(
                    "tierGlass1",
                    ofBlocksTiered(
                        LapotronChip::tierGlass1,
                        ImmutableList.of(
                            Pair.of(Blocks.stained_glass, 0),
                            Pair.of(Blocks.stained_glass, 1),
                            Pair.of(Blocks.stained_glass, 2),
                            Pair.of(Blocks.stained_glass, 3),
                            Pair.of(Blocks.stained_glass, 4),
                            Pair.of(Blocks.stained_glass, 5),
                            Pair.of(Blocks.stained_glass, 6),
                            Pair.of(Blocks.stained_glass, 7),
                            Pair.of(Blocks.stained_glass, 8),
                            Pair.of(Blocks.stained_glass, 9),
                            Pair.of(Blocks.stained_glass, 10),
                            Pair.of(Blocks.stained_glass, 11),
                            Pair.of(Blocks.stained_glass, 12),
                            Pair.of(Blocks.stained_glass, 13),
                            Pair.of(Blocks.stained_glass, 14),
                            Pair.of(Blocks.stained_glass, 15)),
                        -1,
                        (t, m) -> t.tierGlass1 = m,
                        t -> t.tierGlass1)))
            .addElement(
                'H',
                withChannel(
                    "tierGlass2",
                    ofBlocksTiered(
                        LapotronChip::tierGlass2,
                        ImmutableList.of(
                            Pair.of(Blocks.stained_glass, 0),
                            Pair.of(Blocks.stained_glass, 1),
                            Pair.of(Blocks.stained_glass, 2),
                            Pair.of(Blocks.stained_glass, 3),
                            Pair.of(Blocks.stained_glass, 4),
                            Pair.of(Blocks.stained_glass, 5),
                            Pair.of(Blocks.stained_glass, 6),
                            Pair.of(Blocks.stained_glass, 7),
                            Pair.of(Blocks.stained_glass, 8),
                            Pair.of(Blocks.stained_glass, 9),
                            Pair.of(Blocks.stained_glass, 10),
                            Pair.of(Blocks.stained_glass, 11),
                            Pair.of(Blocks.stained_glass, 12),
                            Pair.of(Blocks.stained_glass, 13),
                            Pair.of(Blocks.stained_glass, 14),
                            Pair.of(Blocks.stained_glass, 15)),
                        -1,
                        (t, m) -> t.tierGlass2 = m,
                        t -> t.tierGlass2)))
            .addElement('I', ofBlock(sBlockCasings1, 15))
            .addElement('K', ofBlockAnyMeta(Blocks.beacon))
            .build();
    }

    public static int tierLapisCaelestis(Block block, int meta) {
        if (block == Block.getBlockFromName("ExtraUtilities:greenscreen")) return meta + 1;
        return -1;
    }

    public static int tierGlass1(Block block, int meta) {
        if (block == Blocks.stained_glass) return meta + 1;
        return -1;
    }

    public static int tierGlass2(Block block, int meta) {
        if (block == Blocks.stained_glass) return meta + 1;
        return -1;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        this.buildPiece(
            STRUCTURE_PIECE_MAIN,
            stackSize,
            hintsOnly,
            HORIZONTAL_OFF_SET,
            VERTICAL_OFF_SET,
            DEPTH_OFF_SET);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        return -1;
    }

    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        tCountCasing = 0;
        if (!checkPiece(STRUCTURE_PIECE_MAIN, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET)) return false;
        return tCountCasing >= 10000;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeRegister.LapotronChipRecipes;
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic().setSpeedBonus(1F)
            .setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    @Override
    public int getMaxParallelRecipes() {
        return Integer.MAX_VALUE;
    }

}
