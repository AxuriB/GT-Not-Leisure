package com.science.gtnl.common.machine.multiblock;

import static bartworks.system.material.WerkstoffLoader.BWBlockCasingsAdvanced;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaBlockColumn;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.GregTechAPI.sBlockFrames;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gtPlusPlus.core.block.ModBlocks.blockCustomMachineCasings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableList;
import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.Utils.item.TextUtils;

import gregtech.api.GregTechAPI;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.objects.GTRenderedTexture;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import gregtech.common.blocks.BlockCasings1;
import gregtech.common.blocks.BlockCasings2;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.MTESteamMultiBase;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;

public class LargeSteamFurnace extends MTESteamMultiBase<LargeSteamFurnace> implements ISurvivalConstructable {

    private static final String STRUCTURE_PIECE_MAIN = "main";
    public IStructureDefinition<LargeSteamFurnace> STRUCTURE_DEFINITION = null;
    public static final String LSF_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/large_steam_furnace"; // 文件路径
    public String[][] shape;

    @Override
    public IStructureDefinition<LargeSteamFurnace> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<LargeSteamFurnace>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement(
                    'A',
                    ofChain(
                        buildSteamInput(LargeSteamFurnace.class)
                            .casingIndex(((BlockCasings1) GregTechAPI.sBlockCasings1).getTextureIndex(10))
                            .dot(1)
                            .build(),
                        buildHatchAdder(LargeSteamFurnace.class)
                            .atLeast(SteamHatchElement.InputBus_Steam, SteamHatchElement.OutputBus_Steam)
                            .casingIndex(((BlockCasings1) GregTechAPI.sBlockCasings1).getTextureIndex(10))
                            .dot(1)
                            .buildAndChain(),
                        ofBlocksTiered(
                            this::getTierMachineCasing,
                            ImmutableList.of(Pair.of(sBlockCasings1, 10), Pair.of(sBlockCasings2, 0)),
                            -1,
                            (t, m) -> t.tierMachineCasing = m,
                            t -> t.tierMachineCasing)))
                .addElement(
                    'B',
                    ofBlocksTiered(
                        LargeSteamFurnace::getTierPipeCasing,
                        ImmutableList.of(Pair.of(sBlockCasings2, 12), Pair.of(sBlockCasings2, 13)),
                        -1,
                        (t, m) -> t.tierPipeCasing = m,
                        t -> t.tierPipeCasing))
                .addElement(
                    'C',
                    ofBlocksTiered(
                        LargeSteamFurnace::getTierFireboxCasing,
                        ImmutableList.of(Pair.of(sBlockCasings3, 13), Pair.of(sBlockCasings3, 14)),
                        -1,
                        (t, m) -> t.tierFireboxCasing = m,
                        t -> t.tierFireboxCasing))
                .addElement(
                    'D',
                    ofBlocksTiered(
                        LargeSteamFurnace::getTierFrameCasing,
                        ImmutableList.of(Pair.of(sBlockFrames, 300), Pair.of(sBlockFrames, 305)),
                        -1,
                        (t, m) -> t.tierFrameCasing = m,
                        t -> t.tierFrameCasing))
                .addElement(
                    'E',
                    ofBlocksTiered(
                        LargeSteamFurnace::getTierPlatedCasing,
                        ImmutableList.of(Pair.of(blockCustomMachineCasings, 0), Pair.of(sBlockCasings2, 0)),
                        -1,
                        (t, m) -> t.tierPlatedCasing = m,
                        t -> t.tierPlatedCasing))
                .addElement(
                    'F',
                    ofBlocksTiered(
                        LargeSteamFurnace::getTierBrickCasing,
                        ImmutableList.of(Pair.of(MetaBlockColumn, 0), Pair.of(MetaBlockColumn, 1)),
                        -1,
                        (t, m) -> t.tierBrickCasing = m,
                        t -> t.tierBrickCasing))
                .addElement('G', ofBlock(Blocks.stonebrick, 0))
                .addElement(
                    'H',
                    ofBlocksTiered(
                        LargeSteamFurnace::getTierAdvancedCasing,
                        ImmutableList
                            .of(Pair.of(BWBlockCasingsAdvanced, 32066), Pair.of(BWBlockCasingsAdvanced, 32071)),
                        -1,
                        (t, m) -> t.tierAdvancedCasing = m,
                        t -> t.tierAdvancedCasing))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    public int tierPipeCasing = -1;
    public int tierBrickCasing = -1;
    public int tierPlatedCasing = -1;
    public int tierFrameCasing = -1;
    public int tierMachineCasing = -1;
    public int tierFireboxCasing = -1;
    public int tierAdvancedCasing = -1;
    public int tierMachine = 1;

    public int tCountCasing = 0;

    public boolean isBroken = true;

    public static final int HORIZONTAL_OFF_SET = 7;
    public static final int VERTICAL_OFF_SET = 6;
    public static final int DEPTH_OFF_SET = 1;

    public int getTierMachineCasing(Block block, int meta) {
        if (block == sBlockCasings1 && 10 == meta) {
            tCountCasing++;
            return 1;
        }
        if (block == sBlockCasings2 && 0 == meta) {
            tCountCasing++;
            return 2;
        }
        return 0;
    }

    public static int getTierBrickCasing(Block block, int meta) {
        if (block == MetaBlockColumn && 0 == meta) return 1;
        if (block == MetaBlockColumn && 1 == meta) return 2;
        return 0;
    }

    public static int getTierPlatedCasing(Block block, int meta) {
        if (block == blockCustomMachineCasings && 0 == meta) return 1;
        if (block == sBlockCasings2 && 0 == meta) return 2;
        return 0;
    }

    public static int getTierAdvancedCasing(Block block, int meta) {
        if (block == BWBlockCasingsAdvanced && 32066 == meta) return 1;
        if (block == BWBlockCasingsAdvanced && 32071 == meta) return 2;
        return 0;
    }

    public static int getTierPipeCasing(Block block, int meta) {
        if (block == sBlockCasings2 && 12 == meta) return 1;
        if (block == sBlockCasings2 && 13 == meta) return 2;
        return 0;
    }

    public static int getTierFireboxCasing(Block block, int meta) {
        if (block == sBlockCasings3 && 13 == meta) return 1;
        if (block == sBlockCasings3 && 14 == meta) return 2;
        return 0;
    }

    public static int getTierFrameCasing(Block block, int meta) {
        if (block == sBlockFrames && 300 == meta) return 1;
        if (block == sBlockFrames && 305 == meta) return 2;
        return 0;
    }

    protected void updateHatchTexture() {
        for (MTEHatch h : mSteamInputs) h.updateTexture(getCasingTextureID());
        for (MTEHatch h : mSteamOutputs) h.updateTexture(getCasingTextureID());
        for (MTEHatch h : mSteamInputFluids) h.updateTexture(getCasingTextureID());
        for (MTEHatch h : mInputBusses) h.updateTexture(getCasingTextureID());
        for (MTEHatch h : mOutputBusses) h.updateTexture(getCasingTextureID());
    }

    public int getCasingTextureID() {
        if (tierPipeCasing == 2 || tierMachineCasing == 2
            || tierFrameCasing == 2
            || tierPlatedCasing == 2
            || tierBrickCasing == 2
            || tierAdvancedCasing == 2
            || tierFireboxCasing == 2) return ((BlockCasings2) GregTechAPI.sBlockCasings2).getTextureIndex(0);
        return ((BlockCasings1) GregTechAPI.sBlockCasings1).getTextureIndex(10);
    }

    public LargeSteamFurnace(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        this.shape = StructureUtils.readStructureFromFile(LSF_STRUCTURE_FILE_PATH);
    }

    public LargeSteamFurnace(String aName) {
        super(aName);
        this.shape = StructureUtils.readStructureFromFile(LSF_STRUCTURE_FILE_PATH);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new LargeSteamFurnace(this.mName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.LargeSteamFurnaceRecipeType)
            .addInfo(TextLocalization.Tooltip_LargeSteamFurnace_00)
            .addInfo(TextLocalization.Tooltip_LargeSteamFurnace_01)
            .addInfo(TextLocalization.Tooltip_LargeSteamFurnace_02)
            .addInfo(TextLocalization.HIGH_PRESSURE_TOOLTIP_NOTICE)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(9, 8, 10, false)
            .addInputBus(TextLocalization.Tooltip_LargeSteamFurnace_Casing, 1)
            .addOutputBus(TextLocalization.Tooltip_LargeSteamFurnace_Casing, 1)
            .toolTipFinisher(TextUtils.SNL + TextUtils.SQY);
        return tt;
    }

    @Override
    protected GTRenderedTexture getFrontOverlay() {
        return new GTRenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_STEAM_FURNACE);
    }

    @Override
    protected GTRenderedTexture getFrontOverlayActive() {
        return new GTRenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_STEAM_FURNACE_ACTIVE);
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeMaps.furnaceRecipes;
    }

    @NotNull
    @Override
    public Collection<RecipeMap<?>> getAvailableRecipeMaps() {
        return Arrays.asList(RecipeMaps.furnaceRecipes);
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {

        return new ProcessingLogic() {

            @NotNull
            @Override
            protected CheckRecipeResult validateRecipe(@Nonnull GTRecipe recipe) {
                if (availableVoltage < recipe.mEUt) {
                    return CheckRecipeResultRegistry.insufficientPower(recipe.mEUt);
                } else return CheckRecipeResultRegistry.SUCCESSFUL;
            }

            @Override
            @Nonnull
            protected OverclockCalculator createOverclockCalculator(@NotNull GTRecipe recipe) {
                return OverclockCalculator.ofNoOverclock(recipe)
                    .setEUtDiscount(0.5 * tierMachine)
                    .setSpeedBoost(0.5 / tierMachine);
            }
        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    public boolean checkHatches() {
        return !mSteamInputFluids.isEmpty();
    }

    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        tierPipeCasing = -1;
        tierMachineCasing = -1;
        tierFrameCasing = -1;
        tierPlatedCasing = -1;
        tierFireboxCasing = -1;
        tierAdvancedCasing = -1;
        tierBrickCasing = -1;
        tCountCasing = 0;
        if (!checkPiece(STRUCTURE_PIECE_MAIN, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET)) return false;
        if (tierPipeCasing < 0 && tierMachineCasing < 0
            && tierFrameCasing < 0
            && tierPlatedCasing < 0
            && tierBrickCasing < 0
            && tierFireboxCasing < 0
            && tierAdvancedCasing < 0) return false;
        if (tierPipeCasing == 1 && tierMachineCasing == 1
            && tierFrameCasing == 1
            && tierPlatedCasing == 1
            && tierBrickCasing == 1
            && tierFireboxCasing == 1
            && tierAdvancedCasing == 1
            && tCountCasing >= 100
            && checkHatches()) {
            updateHatchTexture();
            tierMachine = 1;
            return true;
        }
        if (tierPipeCasing == 2 && tierMachineCasing == 2
            && tierFrameCasing == 2
            && tierPlatedCasing == 2
            && tierBrickCasing == 2
            && tierFireboxCasing == 2
            && tierAdvancedCasing == 2
            && tCountCasing >= 100
            && checkHatches()) {
            updateHatchTexture();
            tierMachine = 2;
            return true;
        }
        return false;
    }

    @Override
    public int getMaxParallelRecipes() {
        if (tierMachine == 1) {
            return 32;
        } else if (tierMachine == 2) {
            return 64;
        }
        return 32;
    }

    @Override
    public void onValueUpdate(byte aValue) {
        tierMachineCasing = aValue;
    }

    @Override
    public byte getUpdateData() {
        return (byte) tierMachineCasing;
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final ForgeDirection side,
        final ForgeDirection facing, final int aColorIndex, final boolean aActive, final boolean aRedstone) {
        if (side == facing) {
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                aActive ? getFrontOverlayActive() : getFrontOverlay() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    @Override
    public String getMachineType() {
        return TextLocalization.LargeSteamFurnaceRecipeType;
    }

    @Override
    public int getTierRecipes() {
        return 3;
    }

    @Override
    public String[] getInfoData() {
        ArrayList<String> info = new ArrayList<>(Arrays.asList(super.getInfoData()));
        info.add("Machine Tier: " + EnumChatFormatting.YELLOW + tierMachine);
        info.add("Parallel: " + EnumChatFormatting.YELLOW + getMaxParallelRecipes());
        return info.toArray(new String[0]);
    }

    @Override
    public void getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        super.getWailaBody(itemStack, currenttip, accessor, config);
        NBTTagCompound tag = accessor.getNBTData();
        currenttip.add(
            StatCollector.translateToLocal("GTPP.machines.tier") + ": "
                + EnumChatFormatting.YELLOW
                + getSteamTierTextForWaila(tag)
                + EnumChatFormatting.RESET);
        currenttip.add(
            StatCollector.translateToLocal("GT5U.multiblock.curparallelism") + ": "
                + EnumChatFormatting.BLUE
                + tag.getInteger("parallel")
                + EnumChatFormatting.RESET);
    }

    @Override
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y,
        int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        tag.setInteger("tierMachine", tierMachine);
        tag.setInteger("parallel", getMaxParallelRecipes());
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("tierMachine", tierMachine);
        aNBT.setInteger("mMode", machineMode);
    }

    @Override
    public void loadNBTData(final NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        tierMachine = aNBT.getInteger("tierMachine");
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
        if (this.mMachine) return -1;
        return this.survivialBuildPiece(
            STRUCTURE_PIECE_MAIN,
            stackSize,
            HORIZONTAL_OFF_SET,
            VERTICAL_OFF_SET,
            DEPTH_OFF_SET,
            elementBudget,
            env,
            false,
            true);
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (aBaseMetaTileEntity.isServerSide()) {
            if (mUpdate < -250) mUpdate = 50;
            if ((aTick % 1200) == 0) {
                isBroken = true;
            }
        }
    }
}
