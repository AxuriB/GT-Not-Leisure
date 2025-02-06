package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaBlockGlass;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTUtility.validMTEList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.NotNull;

import com.brandon3055.brandonscore.common.handlers.ProcessHandler;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.Utils.item.TextUtils;
import com.science.gtnl.Utils.rewrites.GTNL_ItemID;
import com.science.gtnl.common.hatch.MTEHatchCustomFluid;
import com.science.gtnl.common.machine.Special.PortalToAlfheimExplosion;
import com.science.gtnl.common.machine.multiMachineClasses.MultiMachineBase;
import com.science.gtnl.common.recipe.RecipeRegister;
import com.science.gtnl.config.MainConfig;

import gregtech.api.GregTechAPI;
import gregtech.api.enums.Textures;
import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.shutdown.ShutDownReasonRegistry;
import gregtech.common.blocks.BlockCasings8;
import gtPlusPlus.core.util.minecraft.FluidUtils;
import gtnhlanth.common.register.LanthItemList;
import tectech.thing.casing.TTCasingsContainer;

public class TeleportationArrayToAlfheim extends MultiMachineBase<TeleportationArrayToAlfheim> {

    public String[][] shape;
    protected static GTNL_ItemID Bread;
    public final ArrayList<MTEHatchCustomFluid> FluidManaInputHatch = new ArrayList<>();
    private int mCasing;
    private static final int PORTAL_MODE = 0;
    private static final int NATURE_MODE = 1;
    private static final int MANA_MODE = 2;
    private static final int RUNE_MODE = 3;

    public static void initStatics() {
        Bread = GTNL_ItemID.createNoNBT(new ItemStack(Items.bread));
    }

    public TeleportationArrayToAlfheim(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        this.shape = StructureUtils.readStructureFromFile(TATA_STRUCTURE_FILE_PATH);
    }

    public TeleportationArrayToAlfheim(String aName) {
        super(aName);
        this.shape = StructureUtils.readStructureFromFile(TATA_STRUCTURE_FILE_PATH);
    }

    protected float getSpeedBonus() {
        return 1F;
    }

    protected int getMaxParallelRecipes() {
        return 2147483647;
    }

    protected boolean isEnablePerfectOverclock() {
        return true;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        switch (machineMode) {
            case NATURE_MODE:
                return RecipeRegister.NatureSpiritArrayRecipes;
            case MANA_MODE:
                return RecipeRegister.ManaInfusionRecipes;
            case RUNE_MODE:
                return RecipeRegister.RuneAltarRecipes;
            default:
                return RecipeRegister.PortalToAlfheimRecipes;
        }
    }

    @NotNull
    @Override
    public Collection<RecipeMap<?>> getAvailableRecipeMaps() {
        return Arrays.asList(
            RecipeRegister.NatureSpiritArrayRecipes,
            RecipeRegister.ManaInfusionRecipes,
            RecipeRegister.RuneAltarRecipes,
            RecipeRegister.PortalToAlfheimRecipes);
    }

    @Override
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y,
        int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        tag.setInteger("mode", machineMode);
    }

    @Override
    public boolean supportsMachineModeSwitch() {
        return true;
    }

    @Override
    public void setMachineModeIcons() {
        machineModeIcons.clear();
        machineModeIcons.add(GTUITextures.OVERLAY_BUTTON_MACHINEMODE_DEFAULT);
        machineModeIcons.add(GTUITextures.OVERLAY_BUTTON_MACHINEMODE_LPF_FLUID);
        machineModeIcons.add(GTUITextures.OVERLAY_BUTTON_MACHINEMODE_LPF_METAL);
        machineModeIcons.add(GTUITextures.OVERLAY_BUTTON_MACHINEMODE_BENDING);
    }

    @Override
    public void addUIWidgets(ModularWindow.Builder builder, UIBuildContext buildContext) {
        super.addUIWidgets(builder, buildContext);
        setMachineModeIcons();
        builder.widget(createModeSwitchButton(builder));
    }

    @Override
    public int nextMachineMode() {
        if (machineMode == PORTAL_MODE) return NATURE_MODE;
        else if (machineMode == NATURE_MODE) return MANA_MODE;
        else if (machineMode == MANA_MODE) return RUNE_MODE;
        else return PORTAL_MODE;
    }

    @Override
    public final void onScrewdriverRightClick(ForgeDirection side, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        this.machineMode = (byte) ((this.machineMode + 1) % 4);
        GTUtility.sendChatToPlayer(
            aPlayer,
            StatCollector.translateToLocal("TeleportationArrayToAlfheim_Mode_" + this.machineMode));
    }

    @NotNull
    @Override
    public CheckRecipeResult checkProcessing() {
        boolean shouldExplode = false;
        long Strength = 0;
        IGregTechTileEntity aBaseMetaTileEntity = getBaseMetaTileEntity();
        for (ItemStack items : getStoredInputs()) {
            if (Bread.equalItemStack(items)) {
                Strength += 50L * items.stackSize;
                shouldExplode = true;
                items.stackSize = 0;
            }
        }
        updateSlots();
        if (Strength > 500) {
            Strength = 500;
        }
        if (shouldExplode) {
            World world = aBaseMetaTileEntity.getWorld();
            for (Object player : world.playerEntities) {
                if (player instanceof EntityPlayer) {
                    world.playSoundAtEntity((EntityPlayer) player, "sciencenotleisure:protal.boom", 1.0F, 1.0F); // 播放音效给所有玩家
                }
            }
            triggerExplosion(aBaseMetaTileEntity, Strength);
            return CheckRecipeResultRegistry.SUCCESSFUL;
        }
        return super.checkProcessing();
    }

    public void triggerExplosion(IGregTechTileEntity aBaseMetaTileEntity, float strength) {
        if (MainConfig.enablePortalToAlfheimBigBoom) {
            float power = strength;
            ProcessHandler.addProcess(
                new PortalToAlfheimExplosion(
                    aBaseMetaTileEntity.getWorld(),
                    aBaseMetaTileEntity.getXCoord(),
                    aBaseMetaTileEntity.getYCoord(),
                    aBaseMetaTileEntity.getZCoord(),
                    power));
        } else {
            triggerExplosion(aBaseMetaTileEntity, 5);
        }
        aBaseMetaTileEntity.getWorld()
            .setBlockToAir(
                aBaseMetaTileEntity.getXCoord(),
                aBaseMetaTileEntity.getYCoord(),
                aBaseMetaTileEntity.getZCoord());
        World world = aBaseMetaTileEntity.getWorld();
        int x = aBaseMetaTileEntity.getXCoord();
        int y = aBaseMetaTileEntity.getYCoord();
        int z = aBaseMetaTileEntity.getZCoord();
        world.createExplosion(null, x, y, z, strength * 20, true);
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        this.buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, horizontalOffSet, verticalOffSet, depthOffSet);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (this.mMachine) return -1;
        return this.survivialBuildPiece(
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

    public static final String STRUCTURE_PIECE_MAIN = "main";
    public static final String TATA_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/teleportation_array_to_alfheim";
    public final int horizontalOffSet = 11;
    public final int verticalOffSet = 15;
    public final int depthOffSet = 2;
    public static IStructureDefinition<TeleportationArrayToAlfheim> STRUCTURE_DEFINITION = null;

    @Override
    public IStructureDefinition<TeleportationArrayToAlfheim> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<TeleportationArrayToAlfheim>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofBlock(LanthItemList.SHIELDED_ACCELERATOR_CASING, 0))
                .addElement('B', ofBlock(sBlockCasings10, 3))
                .addElement('C', ofBlock(sBlockCasings4, 7))
                .addElement('D', ofBlock(sBlockCasings8, 7))
                .addElement(
                    'E',
                    ofChain(
                        buildHatchAdder(TeleportationArrayToAlfheim.class)
                            .atLeast(InputBus, OutputBus, InputHatch, OutputHatch, Energy.or(ExoticEnergy))
                            .dot(1)
                            .casingIndex(((BlockCasings8) GregTechAPI.sBlockCasings8).getTextureIndex(10))
                            .build(),
                        onElementPass(x -> ++x.mCasing, ofBlock(GregTechAPI.sBlockCasings8, 10)),
                        buildHatchAdder(TeleportationArrayToAlfheim.class)
                            .adder(TeleportationArrayToAlfheim::addFluidManaInputHatch)
                            .hatchId(21501)
                            .shouldReject(x -> !x.FluidManaInputHatch.isEmpty())
                            .casingIndex(((BlockCasings8) GregTechAPI.sBlockCasings8).getTextureIndex(10))
                            .dot(1)
                            .build()))
                .addElement('F', ofBlock(TTCasingsContainer.sBlockCasingsTT, 0))
                .addElement('G', ofBlock(MetaBlockGlass, 0))
                .addElement('H', ofBlock(MetaBlockGlass, 1))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        final MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.TeleportationArrayToAlfheimRecipeType)
            .addInfo(TextLocalization.Tooltip_TeleportationArrayToAlfheim_00)
            .addInfo(TextLocalization.Tooltip_TeleportationArrayToAlfheim_01)
            .addInfo(TextLocalization.Tooltip_TeleportationArrayToAlfheim_02)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(23, 18, 23, false)
            .addInputBus(TextLocalization.Tooltip_TeleportationArrayToAlfheim_Casing, 1)
            .addOutputBus(TextLocalization.Tooltip_TeleportationArrayToAlfheim_Casing, 1)
            .addInputHatch(TextLocalization.Tooltip_TeleportationArrayToAlfheim_Casing, 1)
            .addOutputHatch(TextLocalization.Tooltip_TeleportationArrayToAlfheim_Casing, 1)
            .addEnergyHatch(TextLocalization.Tooltip_TeleportationArrayToAlfheim_Casing, 1)
            .addMaintenanceHatch(TextLocalization.Tooltip_TeleportationArrayToAlfheim_Casing, 1)
            .addOtherStructurePart(
                TextLocalization.FluidManaInputHatch,
                TextLocalization.Tooltip_TeleportationArrayToAlfheim_Casing,
                1)
            .toolTipFinisher(TextUtils.SNL + "§r X §l§o§f年§6轮§f新§6城§f计§6划 §r§fby 咸到老时变成鱼");
        return tt;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new TeleportationArrayToAlfheim(this.mName);
    }

    public int getCasingTextureID() {
        return ((BlockCasings8) GregTechAPI.sBlockCasings8).getTextureIndex(10);
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
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        mCasing = 0;
        FluidManaInputHatch.clear();
        repairMachine();
        return checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet) && mCasing >= 350
            && checkHatch();
    }

    @Override
    public boolean checkHatch() {
        return super.checkHatch() && !FluidManaInputHatch.isEmpty();
    }

    public boolean addFluidManaInputHatch(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        } else {
            IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
            if (aMetaTileEntity instanceof MTEHatchCustomFluid && aMetaTileEntity.getBaseMetaTileEntity()
                .getMetaTileID() == 21501) {
                return addToMachineListInternal(FluidManaInputHatch, aTileEntity, aBaseCasingIndex);
            }
        }
        return false;
    }

    @Override
    public void updateSlots() {
        for (MTEHatchCustomFluid tHatch : validMTEList(FluidManaInputHatch)) tHatch.updateSlots();
        super.updateSlots();
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (this.mStartUpCheck < 0) {
            if (this.mMaxProgresstime > 0 && this.mProgresstime != 0 || this.getBaseMetaTileEntity()
                .hasWorkJustBeenEnabled()) {
                if (aTick % 20 == 0 || this.getBaseMetaTileEntity()
                    .hasWorkJustBeenEnabled()) {
                    if (!this.depleteInputFromRestrictedHatches(this.FluidManaInputHatch, 100)) {
                        this.causeMaintenanceIssue();
                        this.stopMachine(
                            ShutDownReasonRegistry
                                .outOfFluid(Objects.requireNonNull(FluidUtils.getFluidStack("fluidmana", 100))));
                    }
                }
            }
        }
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
    public String getMachineModeName() {
        return StatCollector.translateToLocal("TeleportationArrayToAlfheim_Mode_" + machineMode);
    }
}
