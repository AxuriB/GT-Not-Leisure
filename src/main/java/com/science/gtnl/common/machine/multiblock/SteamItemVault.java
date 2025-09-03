package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofChain;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.onElementPass;
import static com.science.gtnl.ScienceNotLeisure.*;
import static com.science.gtnl.Utils.enums.BlockIcons.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.*;
import static gregtech.api.util.GTUtility.*;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.api.IItemVault;
import com.science.gtnl.common.machine.hatch.ItemVaultPortHatch;
import com.science.gtnl.common.machine.multiMachineClasses.SteamMultiMachineBase;
import com.science.gtnl.loader.BlockLoader;

import appeng.api.AEApi;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IItemList;
import appeng.util.item.AEFluidStack;
import appeng.util.item.AEItemStack;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IHatchElement;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MTEHatchInputBus;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTUtility;
import gregtech.api.util.IGTHatchAdder;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.common.tileentities.machines.MTEHatchCraftingInputME;
import gregtech.common.tileentities.machines.MTEHatchInputBusME;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.MTEHatchSteamBusInput;
import lombok.Getter;
import lombok.Setter;

public class SteamItemVault extends SteamMultiMachineBase<SteamItemVault>
    implements ISurvivalConstructable, IItemVault {

    public static long MAX_DISTINCT_ITEMS = 256;
    public static long MAX_DISTINCT_FLUIDS = 256;

    public static BigInteger MAX_CAPACITY_ITEM = BigInteger.valueOf(640000)
        .multiply(BigInteger.valueOf(MAX_DISTINCT_ITEMS));
    public static BigInteger MAX_CAPACITY_FLUID = BigInteger.valueOf(640000000)
        .multiply(BigInteger.valueOf(MAX_DISTINCT_FLUIDS));

    public BigInteger capacityItem = MAX_CAPACITY_ITEM;
    public BigInteger capacityFluid = MAX_CAPACITY_FLUID;
    public long capacityPerItem = capacityItem.divide(BigInteger.valueOf(MAX_DISTINCT_ITEMS))
        .longValue();
    public long capacityPerFluid = capacityFluid.divide(BigInteger.valueOf(MAX_DISTINCT_FLUIDS))
        .longValue();

    public boolean locked = true;
    @Setter
    @Getter
    public boolean doVoidExcess = false;
    public ItemVaultPortHatch portHatch = null;

    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final String SIV_STRUCTURE_FILE_PATH = RESOURCE_ROOT_ID + ":" + "multiblock/steam_item_vault";
    private static final String[][] shape = StructureUtils.readStructureFromFile(SIV_STRUCTURE_FILE_PATH);
    private static final int HORIZONTAL_OFF_SET = 3;
    private static final int VERTICAL_OFF_SET = 8;
    private static final int DEPTH_OFF_SET = 0;

    public static NumberFormat nf = NumberFormat.getNumberInstance();

    public IItemList<IAEItemStack> STORE_ITEM = AEApi.instance()
        .storage()
        .createItemList();

    public IItemList<IAEFluidStack> STORE_FLUID = AEApi.instance()
        .storage()
        .createFluidList();

    public SteamItemVault(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public SteamItemVault(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new SteamItemVault(super.mName);
    }

    @Override
    public long maxItemCount() {
        return MAX_DISTINCT_ITEMS;
    }

    @Override
    public long maxFluidCount() {
        return MAX_DISTINCT_FLUIDS;
    }

    @Override
    public boolean hasItem() {
        return true;
    }

    @Override
    public boolean hasFluid() {
        return true;
    }

    @Override
    public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
        if (checkStructure(true)) {
            this.mStartUpCheck = -1;
            this.mUpdate = 200;
        }
        super.onFirstTick(aBaseMetaTileEntity);
    }

    @Override
    public void onBlockDestroyed() {
        if (portHatch != null) {
            portHatch.unbind();
        }
        super.onBlockDestroyed();
    }

    @Override
    public @NotNull CheckRecipeResult checkProcessing() {
        mEfficiency = 10000;
        mEfficiencyIncrease = 10000;
        lEUt = 128;
        mMaxProgresstime = 20;

        ArrayList<ItemStack> inputItems = getStoredInputs();
        ArrayList<FluidStack> inputFluids = getStoredFluids();

        if (!inputItems.isEmpty()) {
            for (ItemStack aItem : inputItems) {
                ItemStack toDeplete = aItem.copy();
                toDeplete.stackSize = this.injectItems(aItem, true);
                depleteInput(toDeplete);
            }
        }

        if (!inputFluids.isEmpty()) {
            for (FluidStack aFluid : inputFluids) {
                FluidStack toDeplete = aFluid.copy();
                toDeplete.amount = this.injectFluids(aFluid, true);
                depleteInput(toDeplete, false);
            }
        }

        if (!this.mOutputBusses.isEmpty() || !this.mSteamOutputs.isEmpty()) {
            if (STORE_ITEM.getFirstItem() != null) {
                IAEItemStack stack = STORE_ITEM.getFirstItem()
                    .copy();
                stack.setStackSize(stack.getStackSize() - this.tryAddOutput(stack.getItemStack()).stackSize);
                if (stack.getStackSize() > 0) {
                    this.extractItems(stack, true);
                }
            }
        }

        if (this.lEUt > 0) this.lEUt = -this.lEUt;

        return CheckRecipeResultRegistry.SUCCESSFUL;
    }

    @Override
    public ArrayList<ItemStack> getStoredInputsForColor(Optional<Byte> color) {
        ArrayList<ItemStack> rList = new ArrayList<>();
        Map<GTUtility.ItemId, ItemStack> inputsFromME = new HashMap<>();
        for (MTEHatchInputBus tHatch : validMTEList(mInputBusses)) {
            if (tHatch instanceof MTEHatchCraftingInputME) {
                continue;
            }
            byte busColor = tHatch.getColor();
            if (color.isPresent() && busColor != -1 && busColor != color.get()) continue;
            tHatch.mRecipeMap = getRecipeMap();
            IGregTechTileEntity tileEntity = tHatch.getBaseMetaTileEntity();
            boolean isMEBus = tHatch instanceof MTEHatchInputBusME;
            assert tileEntity != null;
            for (int i = tileEntity.getSizeInventory() - 1; i >= 0; i--) {
                ItemStack itemStack = tileEntity.getStackInSlot(i);
                if (itemStack != null) {
                    if (isMEBus) {
                        // Prevent the same item from different ME buses from being recognized
                        inputsFromME.put(GTUtility.ItemId.createNoCopy(itemStack), itemStack);
                    } else {
                        rList.add(itemStack);
                    }
                }
            }
        }

        for (MTEHatchSteamBusInput tHatch : validMTEList(mSteamInputs)) {
            byte busColor = tHatch.getColor();
            if (color.isPresent() && busColor != -1 && busColor != color.get()) continue;
            tHatch.mRecipeMap = getRecipeMap();
            IGregTechTileEntity tileEntity = tHatch.getBaseMetaTileEntity();
            assert tileEntity != null;
            for (int i = tileEntity.getSizeInventory() - 1; i >= 0; i--) {
                ItemStack itemStack = tileEntity.getStackInSlot(i);
                if (itemStack != null) {
                    rList.add(itemStack);
                }
            }
        }

        if (!inputsFromME.isEmpty()) {
            rList.addAll(inputsFromME.values());
        }
        return rList;

    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (aBaseMetaTileEntity.isServerSide()) {
            this.locked = !aBaseMetaTileEntity.isActive();
        }
    }

    @Override
    public void onModeChangeByScrewdriver(ForgeDirection side, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        this.setDoVoidExcess(!doVoidExcess);
        GTUtility.sendChatToPlayer(
            aPlayer,
            StatCollector.translateToLocal("Info_SteamItemVault_AutoVoiding") + doVoidExcess);
    }

    @Override
    public IStructureDefinition<SteamItemVault> getStructureDefinition() {
        return StructureDefinition.<SteamItemVault>builder()
            .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
            .addElement('A', ofBlock(BlockLoader.metaCasing, 29))
            .addElement('B', ofFrame(Materials.Steel))
            .addElement(
                'C',
                ofChain(
                    buildSteamWirelessInput(SteamItemVault.class).casingIndex(getCasingTextureID())
                        .dot(1)
                        .build(),
                    buildSteamBigInput(SteamItemVault.class).casingIndex(getCasingTextureID())
                        .dot(1)
                        .build(),
                    buildSteamInput(SteamItemVault.class).casingIndex(getCasingTextureID())
                        .dot(1)
                        .build(),
                    buildHatchAdder(SteamItemVault.class)
                        .atLeast(
                            SteamHatchElement.InputBus_Steam,
                            SteamHatchElement.OutputBus_Steam,
                            InputBus,
                            OutputBus,
                            InputHatch,
                            SteamItemVaultPortElement.SteamItemVaultPortBus)
                        .dot(1)
                        .casingIndex(getCasingTextureID())
                        .build(),
                    onElementPass(x -> x.tCountCasing++, ofBlock(BlockLoader.metaCasing02, 0))))
            .addElement('D', chainAllGlasses())
            .build();
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        if (!checkPiece(STRUCTURE_PIECE_MAIN, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET) || !checkHatch()) {
            if (portHatch != null) {
                portHatch.unbind();
                portHatch = null;
            }
            return false;
        }
        if (portHatch != null && portHatch.controller == null) portHatch.bind(this);
        return tCountCasing >= 30;
    }

    @Override
    public void clearHatches() {
        super.clearHatches();
        if (portHatch != null) {
            portHatch = null;
        }
    }

    @Override
    protected int getCasingTextureID() {
        return GTUtility.getTextureId((byte) 116, (byte) 32);
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
        return this.survivalBuildPiece(
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
    public String getMachineType() {
        return StatCollector.translateToLocal("SteamItemVaultRecipeType");
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        final MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(StatCollector.translateToLocal("SteamItemVaultRecipeType"))
            .addInfo(
                StatCollector
                    .translateToLocalFormatted("Tooltip_SteamItemVault_00", MAX_DISTINCT_ITEMS, MAX_DISTINCT_FLUIDS))
            .addInfo(StatCollector.translateToLocalFormatted("Tooltip_SteamItemVault_01", MAX_DISTINCT_ITEMS))
            .addInfo(StatCollector.translateToLocalFormatted("Tooltip_SteamItemVault_02", MAX_DISTINCT_FLUIDS))
            .addInfo(StatCollector.translateToLocal("Tooltip_SteamItemVault_03"))
            .addInfo(StatCollector.translateToLocal("Tooltip_SteamItemVault_04"))
            .addInfo(StatCollector.translateToLocal("Tooltip_SteamItemVault_05"))
            .addInfo(StatCollector.translateToLocal("Tooltip_SteamItemVault_06"))
            .addInfo(StatCollector.translateToLocal("Tooltip_SteamItemVault_07"))
            .addInfo(StatCollector.translateToLocal("Tooltip_SteamItemVault_08"))
            .addInfo(StatCollector.translateToLocalFormatted("Tooltip_SteamItemVault_09", nf.format(MAX_CAPACITY_ITEM)))
            .addInfo(
                StatCollector.translateToLocalFormatted("Tooltip_SteamItemVault_10", nf.format(MAX_CAPACITY_FLUID)))
            .addSeparator()
            .addInfo(StatCollector.translateToLocal("StructureTooComplex"))
            .addInfo(StatCollector.translateToLocal("BLUE_PRINT_INFO"))
            .beginStructureBlock(7, 11, 7, false)
            .addInputBus(StatCollector.translateToLocal("Tooltip_SteamItemVault_Casing"), 1)
            .addOutputBus(StatCollector.translateToLocal("Tooltip_SteamItemVault_Casing"), 1)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
        int colorIndex, boolean aActive, boolean aRedstone) {
        if (side == facing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_STEAM_ITEM_VAULT_ACTIVE)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_STEAM_ITEM_VAULT_ACTIVE_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_STEAM_ITEM_VAULT)
                    .extFacing()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    @Override
    public boolean supportsPowerPanel() {
        return false;
    }

    @Override
    public int getTierRecipes() {
        return 0;
    }

    @Override
    public String[] getInfoData() {
        ArrayList<String> ll = new ArrayList<>();
        ll.add(
            EnumChatFormatting.YELLOW + StatCollector.translateToLocal("Info_SteamItemVault_StoredItems")
                + EnumChatFormatting.RESET);

        int i = 0;
        for (IAEItemStack tank : STORE_ITEM) {
            String localizedName = Objects.requireNonNull(
                tank.getItem()
                    .getItemStackDisplayName(tank.getItemStack()));
            String amount = nf.format(tank.getStackSize());
            String percentage = capacityPerItem > 0 ? String.valueOf(tank.getStackSize() * 100 / capacityPerItem) : "";
            ll.add(MessageFormat.format("{0} - {1}: {2} ({3}%)", i++, localizedName, amount, percentage));
            if (i >= 32) break;
        }

        ll.add(
            EnumChatFormatting.YELLOW + StatCollector.translateToLocal("Info_SteamItemVault_StoredFluids")
                + EnumChatFormatting.RESET);

        int j = 0;
        for (IAEFluidStack tank : STORE_FLUID) {
            String localizedName = Objects.requireNonNull(
                tank.getFluid()
                    .getLocalizedName(tank.getFluidStack()));
            String amount = nf.format(tank.getStackSize());
            String percentage = capacityPerFluid > 0 ? String.valueOf(tank.getStackSize() * 100 / capacityPerFluid)
                : "";
            ll.add(MessageFormat.format("{0} - {1}: {2} ({3}%)", j++, localizedName, amount, percentage));
            if (j >= 32) break;
        }

        ll.add(
            EnumChatFormatting.YELLOW + StatCollector.translateToLocal("Info_SteamItemVault_OperationalData")
                + EnumChatFormatting.RESET);

        ll.add(
            StatCollector.translateToLocalFormatted("Info_SteamItemVault_ItemUsed", nf.format(getItemStoredAmount())));
        ll.add(StatCollector.translateToLocalFormatted("Info_SteamItemVault_ItemTotal", nf.format(capacityItem)));
        ll.add(
            StatCollector.translateToLocalFormatted("Info_SteamItemVault_PerItemCapacity", nf.format(capacityPerItem)));
        ll.add(StatCollector.translateToLocalFormatted("Info_SteamItemVault_ItemUsedTypes", nf.format(itemsCount())));
        ll.add(
            StatCollector.translateToLocalFormatted("Info_SteamItemVault_ItemTotalTypes", nf.format(maxItemCount())));

        ll.add(
            StatCollector
                .translateToLocalFormatted("Info_SteamItemVault_FluidUsed", nf.format(getFluidStoredAmount())));
        ll.add(StatCollector.translateToLocalFormatted("Info_SteamItemVault_FluidTotal", nf.format(capacityFluid)));
        ll.add(
            StatCollector
                .translateToLocalFormatted("Info_SteamItemVault_PerFluidCapacity", nf.format(capacityPerFluid)));
        ll.add(StatCollector.translateToLocalFormatted("Info_SteamItemVault_FluidUsedTypes", nf.format(fluidsCount())));
        ll.add(
            StatCollector.translateToLocalFormatted("Info_SteamItemVault_FluidTotalTypes", nf.format(maxFluidCount())));

        ll.add(StatCollector.translateToLocalFormatted("Info_SteamItemVault_RunningCost", getActualEnergyUsage()));
        ll.add(StatCollector.translateToLocalFormatted("Info_SteamItemVault_AutoVoiding", doVoidExcess));
        ll.add(EnumChatFormatting.STRIKETHROUGH + "---------------------------------------------");

        return ll.toArray(new String[0]);
    }

    private String ensureUUID(NBTTagCompound aNBT) {
        if (aNBT.hasKey("storeUUID")) {
            return aNBT.getString("storeUUID");
        } else {
            String uuid = UUID.randomUUID()
                .toString();
            aNBT.setString("storeUUID", uuid);
            return uuid;
        }
    }

    @Override
    public void setItemNBT(NBTTagCompound aNBT) {
        aNBT.setByteArray("capacityItem", capacityItem.toByteArray());
        aNBT.setByteArray("capacityFluid", capacityFluid.toByteArray());
        aNBT.setBoolean("doVoidExcess", doVoidExcess);
        aNBT.setBoolean("locked", locked);

        String uuid = ensureUUID(aNBT);

        NBTTagCompound storeRoot = new NBTTagCompound();
        NBTTagList itemNbt = new NBTTagList();
        for (IAEItemStack aeItem : STORE_ITEM) {
            NBTTagCompound nbt = new NBTTagCompound();
            aeItem.writeToNBT(nbt);
            itemNbt.appendTag(nbt);
        }
        NBTTagList fluidNbt = new NBTTagList();
        for (IAEFluidStack aeFluid : STORE_FLUID) {
            NBTTagCompound nbt = new NBTTagCompound();
            aeFluid.writeToNBT(nbt);
            fluidNbt.appendTag(nbt);
        }
        storeRoot.setTag("STORE_ITEM", itemNbt);
        storeRoot.setTag("STORE_FLUID", fluidNbt);

        File worldDir = DimensionManager.getCurrentSaveRootDirectory();
        File dataDir = new File(worldDir, "data");
        if (!dataDir.exists()) dataDir.mkdirs();

        File storeFile = new File(dataDir, "ItemVault_" + uuid + ".dat");
        try {
            CompressedStreamTools.safeWrite(storeRoot, storeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        aNBT.setByteArray("capacityItem", capacityItem.toByteArray());
        aNBT.setByteArray("capacityFluid", capacityFluid.toByteArray());
        aNBT.setBoolean("doVoidExcess", doVoidExcess);
        aNBT.setBoolean("locked", locked);
        ensureUUID(aNBT);
        NBTTagList itemNbt = new NBTTagList();
        aNBT.setTag("STORE_ITEM", itemNbt);
        NBTTagList fluidNbt = new NBTTagList();
        aNBT.setTag("STORE_FLUID", fluidNbt);
        for (IAEItemStack aeItem : STORE_ITEM) {
            var nbt = new NBTTagCompound();
            aeItem.writeToNBT(nbt);
            itemNbt.appendTag(nbt);
        }
        for (IAEFluidStack aeFluid : STORE_FLUID) {
            var nbt = new NBTTagCompound();
            aeFluid.writeToNBT(nbt);
            fluidNbt.appendTag(nbt);
        }
        super.saveNBTData(aNBT);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        this.setCapacityItem(new BigInteger(aNBT.getByteArray("capacityItem")));
        this.setCapacityFluid(new BigInteger(aNBT.getByteArray("capacityFluid")));
        this.setDoVoidExcess(aNBT.getBoolean("doVoidExcess"));
        this.locked = aNBT.getBoolean("locked");
        if (aNBT.hasKey("storeUUID")) {
            String uuid = aNBT.getString("storeUUID");
            try {
                File worldDir = DimensionManager.getCurrentSaveRootDirectory();
                File dataDir = new File(worldDir, "data");
                File vaultFile = new File(dataDir, "ItemVault_" + uuid + ".dat");

                if (vaultFile.exists()) {
                    NBTTagCompound fileNBT = CompressedStreamTools.read(vaultFile);
                    NBTTagList itemNbt = fileNBT.getTagList("STORE_ITEM", 10);
                    NBTTagList fluidNbt = fileNBT.getTagList("STORE_FLUID", 10);

                    for (int i = 0; i < itemNbt.tagCount(); i++) {
                        STORE_ITEM.add(AEItemStack.loadItemStackFromNBT(itemNbt.getCompoundTagAt(i)));
                    }

                    for (int i = 0; i < fluidNbt.tagCount(); i++) {
                        STORE_FLUID.add(AEFluidStack.loadFluidStackFromNBT(fluidNbt.getCompoundTagAt(i)));
                    }

                    if (!vaultFile.delete()) {
                        System.err.println("Warning: Failed to delete vault file " + vaultFile);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        NBTTagList itemNbt = aNBT.getTagList("STORE_ITEM", 10);
        if (itemNbt != null) {
            for (int i = 0; i < itemNbt.tagCount(); i++) {
                STORE_ITEM.add(AEItemStack.loadItemStackFromNBT(itemNbt.getCompoundTagAt(i)));
            }
        }
        NBTTagList fluidNbt = aNBT.getTagList("STORE_FLUID", 10);
        if (fluidNbt != null) {
            for (int i = 0; i < fluidNbt.tagCount(); i++) {
                STORE_FLUID.add(AEFluidStack.loadFluidStackFromNBT(fluidNbt.getCompoundTagAt(i)));
            }
        }
        super.loadNBTData(aNBT);
    }

    @Override
    public int injectItems(ItemStack aItem, boolean doInput) {
        if (locked) return 0;
        if (STORE_ITEM.size() >= MAX_DISTINCT_ITEMS) return 0;
        var aeItem = getStoredItem(aItem);
        long size = aeItem == null ? 0 : aeItem.getStackSize();
        if (size >= capacityPerItem) return doVoidExcess ? aItem.stackSize : 0;
        if (capacityPerItem - size < aItem.stackSize) {
            if (doInput) {
                if (aeItem == null) {
                    STORE_ITEM.addStorage(
                        AEItemStack.create(aItem)
                            .setStackSize(capacityPerItem - size));
                } else {
                    aeItem.setStackSize(capacityPerItem);
                }
                portHatch.postUpdateItem(aItem, capacityPerFluid - size);
            }
            return doVoidExcess ? aItem.stackSize : (int) (capacityPerItem - size);
        } else {
            if (doInput) {
                if (aeItem == null) {
                    STORE_ITEM.addStorage(AEItemStack.create(aItem));
                } else {
                    aeItem.setStackSize(size + aItem.stackSize);
                }
                portHatch.postUpdateItem(aItem, aItem.stackSize);
            }
            return aItem.stackSize;
        }
    }

    @Override
    public long injectItems(IAEItemStack aItem, boolean doInput) {
        if (locked) return 0;
        if (STORE_ITEM.size() >= MAX_DISTINCT_ITEMS) return 0;
        var aeItem = getStoredItem(aItem.getItemStack());
        long size = aeItem == null ? 0 : aeItem.getStackSize();
        if (size >= capacityPerItem) return doVoidExcess ? aItem.getStackSize() : 0;
        if (capacityPerItem - size < aItem.getStackSize()) {
            if (doInput) {
                if (aeItem == null) {
                    STORE_ITEM.addStorage(
                        aItem.copy()
                            .setStackSize(capacityPerItem - size));
                } else {
                    aeItem.setStackSize(capacityPerItem);
                }
                portHatch.postUpdateItem(aItem.getItemStack(), capacityPerFluid - size);
            }
            return doVoidExcess ? aItem.getStackSize() : (int) (capacityPerItem - size);
        } else {
            if (doInput) {
                if (aeItem == null) {
                    STORE_ITEM.addStorage(aItem);
                } else {
                    aeItem.setStackSize(size + aItem.getStackSize());
                }
                portHatch.postUpdateItem(aItem.getItemStack(), aItem.getStackSize());
            }
            return aItem.getStackSize();
        }
    }

    @Override
    public int injectFluids(FluidStack aFluid, boolean doInput) {
        if (locked) return 0;
        if (STORE_FLUID.size() >= MAX_DISTINCT_FLUIDS) return 0;
        var aeFluid = getStoredFluid(aFluid);
        long size = aeFluid == null ? 0 : aeFluid.getStackSize();
        if (size >= capacityPerFluid) return doVoidExcess ? aFluid.amount : 0;
        if (capacityPerFluid - size < aFluid.amount) {
            if (doInput) {
                if (aeFluid == null) {
                    STORE_FLUID.addStorage(
                        AEFluidStack.create(aFluid)
                            .setStackSize(capacityPerFluid - size));
                } else {
                    aeFluid.setStackSize(capacityPerFluid);
                }
                portHatch.postUpdateFluid(aFluid, capacityPerFluid - size);
            }
            return doVoidExcess ? aFluid.amount : (int) (capacityPerFluid - size);
        } else {
            if (doInput) {
                if (aeFluid == null) {
                    STORE_FLUID.addStorage(AEFluidStack.create(aFluid));
                } else {
                    aeFluid.setStackSize(size + aFluid.amount);
                }
                portHatch.postUpdateFluid(aFluid, capacityPerFluid - aFluid.amount);
            }
            return aFluid.amount;
        }
    }

    @Override
    public long injectFluids(IAEFluidStack aFluid, boolean doInput) {
        if (locked) return 0;
        if (STORE_FLUID.size() >= MAX_DISTINCT_FLUIDS) return 0;
        var aeFluid = getStoredFluid(aFluid.getFluidStack());
        long size = aeFluid == null ? 0 : aeFluid.getStackSize();
        if (size >= capacityPerFluid) return doVoidExcess ? aFluid.getStackSize() : 0;
        if (capacityPerFluid - size < aFluid.getStackSize()) {
            if (doInput) {
                if (aeFluid == null) {
                    STORE_FLUID.addStorage(
                        AEFluidStack.create(aFluid)
                            .setStackSize(capacityPerFluid - size));
                } else {
                    aeFluid.setStackSize(capacityPerFluid);
                }
                portHatch.postUpdateFluid(aFluid.getFluidStack(), capacityPerFluid - size);
            }
            return doVoidExcess ? aFluid.getStackSize() : capacityPerFluid - size;
        } else {
            if (doInput) {
                if (aeFluid == null) {
                    STORE_FLUID.addStorage(AEFluidStack.create(aFluid));
                } else {
                    aeFluid.setStackSize(size + aFluid.getStackSize());
                }
                portHatch.postUpdateFluid(aFluid.getFluidStack(), aFluid.getStackSize());
            }
            return aFluid.getStackSize();
        }
    }

    @Override
    public long extractItems(IAEItemStack aItem, boolean doOutput) {
        if (locked) return 0;
        var aeItem = getStoredItem(aItem.getItemStack());
        if (aeItem == null) return 0;
        long storedSize = aeItem.getStackSize();
        long requestSize = aItem.getStackSize();
        if (storedSize > requestSize) {
            if (doOutput) {
                aeItem.setStackSize(storedSize - requestSize);
                portHatch.postUpdateItem(aItem.getItemStack(), -requestSize);
            }
            return requestSize;
        } else {
            if (doOutput) {
                aeItem.setStackSize(0);
                portHatch.postUpdateItem(aItem.getItemStack(), -storedSize);
            }
            return storedSize;
        }
    }

    @Override
    public long extractFluids(IAEFluidStack aFluid, boolean doOutput) {
        if (locked) return 0;
        var aeFluid = getStoredFluid(aFluid.getFluidStack());
        if (aeFluid == null) return 0;
        long storedSize = aeFluid.getStackSize();
        long requestSize = aFluid.getStackSize();
        if (storedSize > requestSize) {
            if (doOutput) {
                aeFluid.setStackSize(storedSize - requestSize);
                portHatch.postUpdateFluid(aFluid.getFluidStack(), -requestSize);
            }
            return requestSize;
        } else {
            if (doOutput) {
                aeFluid.setStackSize(0);
                portHatch.postUpdateFluid(aFluid.getFluidStack(), -storedSize);
            }
            return storedSize;
        }
    }

    public void setCapacityItem(BigInteger capacityItem) {
        if (capacityItem.compareTo(MAX_CAPACITY_ITEM) > 0) {
            this.capacityItem = MAX_CAPACITY_ITEM;
            this.capacityPerItem = Long.MAX_VALUE;
        } else {
            this.capacityItem = capacityItem;
            this.capacityPerItem = capacityItem.divide(BigInteger.valueOf(MAX_DISTINCT_ITEMS))
                .longValue();
        }
    }

    public void setCapacityFluid(BigInteger capacityFluid) {
        if (capacityFluid.compareTo(MAX_CAPACITY_FLUID) > 0) {
            this.capacityFluid = MAX_CAPACITY_FLUID;
            this.capacityPerFluid = Long.MAX_VALUE;
        } else {
            this.capacityFluid = capacityFluid;
            this.capacityPerFluid = capacityFluid.divide(BigInteger.valueOf(MAX_DISTINCT_FLUIDS))
                .longValue();
        }
    }

    @Override
    public long itemsCount() {
        return STORE_ITEM.size();
    }

    @Override
    public long fluidsCount() {
        return STORE_FLUID.size();
    }

    @Override
    public IAEItemStack getStoredItem(@Nullable ItemStack aItem) {
        if (aItem == null) return null;
        return STORE_ITEM.findPrecise(AEItemStack.create(aItem));
    }

    @Override
    public IAEFluidStack getStoredFluid(@Nullable FluidStack aFluid) {
        if (aFluid == null) return null;
        return STORE_FLUID.findPrecise(AEFluidStack.create(aFluid));
    }

    @Override
    public boolean containsItems(ItemStack aItem) {
        return getStoredItem(aItem) != null;
    }

    @Override
    public boolean containsFluids(FluidStack aFluid) {
        return getStoredFluid(aFluid) != null;
    }

    public BigInteger getItemStoredAmount() {
        BigInteger amount = BigInteger.ZERO;
        for (IAEItemStack item : STORE_ITEM) {
            amount = amount.add(BigInteger.valueOf(item.getStackSize()));
        }
        return amount;
    }

    public BigInteger getFluidStoredAmount() {
        BigInteger amount = BigInteger.ZERO;
        for (IAEFluidStack fluid : STORE_FLUID) {
            amount = amount.add(BigInteger.valueOf(fluid.getStackSize()));
        }
        return amount;
    }

    @Override
    public IItemList<IAEItemStack> getStoreItems() {
        return STORE_ITEM;
    }

    @Override
    public IItemList<IAEFluidStack> getStoreFluids() {
        return STORE_FLUID;
    }

    public boolean addPortBusToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity != null) {
            final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
            if (aMetaTileEntity instanceof ItemVaultPortHatch itemVaultPortHatch) {
                if (this.portHatch != null) return false;
                this.portHatch = itemVaultPortHatch;
                this.portHatch.updateTexture(aBaseCasingIndex);
                return true;
            }
        }
        return false;
    }

    public enum SteamItemVaultPortElement implements IHatchElement<SteamItemVault> {

        SteamItemVaultPortBus(SteamItemVault::addPortBusToMachineList, ItemVaultPortHatch.class) {

            @Override
            public long count(SteamItemVault steamItemVault) {
                return steamItemVault.portHatch != null ? 1 : 0;
            }
        };

        private final List<Class<? extends IMetaTileEntity>> mteClasses;
        private final IGTHatchAdder<SteamItemVault> adder;

        @SafeVarargs
        SteamItemVaultPortElement(IGTHatchAdder<SteamItemVault> adder, Class<? extends IMetaTileEntity>... mteClasses) {
            this.mteClasses = Collections.unmodifiableList(Arrays.asList(mteClasses));
            this.adder = adder;
        }

        @Override
        public List<? extends Class<? extends IMetaTileEntity>> mteClasses() {
            return mteClasses;
        }

        @Override
        public IGTHatchAdder<? super SteamItemVault> adder() {
            return adder;
        }
    }

}
