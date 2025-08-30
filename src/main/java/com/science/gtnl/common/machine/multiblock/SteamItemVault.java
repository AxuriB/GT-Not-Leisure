package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofChain;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.onElementPass;
import static com.science.gtnl.ScienceNotLeisure.*;
import static com.science.gtnl.Utils.enums.BlockIcons.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.*;
import static gregtech.api.util.GTUtility.*;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.ItemTank;
import com.science.gtnl.api.IItemVault;
import com.science.gtnl.common.machine.hatch.ItemVaultPortBus;
import com.science.gtnl.common.machine.multiMachineClasses.SteamMultiMachineBase;
import com.science.gtnl.loader.BlockLoader;

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
import gregtech.common.items.ItemIntegratedCircuit;
import gregtech.common.tileentities.machines.MTEHatchCraftingInputME;
import gregtech.common.tileentities.machines.MTEHatchInputBusME;

public class SteamItemVault extends SteamMultiMachineBase<SteamItemVault>
    implements ISurvivalConstructable, IItemVault {

    public static int MAX_DISTINCT_ITEMS = 128;
    public static final BigInteger MAX_CAPACITY = BigInteger.valueOf(1280000)
        .multiply(BigInteger.valueOf(MAX_DISTINCT_ITEMS));

    public BigInteger capacity = MAX_CAPACITY;
    public long capacityPerItem = capacity.divide(BigInteger.valueOf(MAX_DISTINCT_ITEMS))
        .longValue();

    public boolean locked = true;
    public boolean doVoidExcess = false;
    public int itemSelector = -1;
    public ItemVaultPortBus portBus = null;

    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final String SIV_STRUCTURE_FILE_PATH = RESOURCE_ROOT_ID + ":" + "multiblock/steam_item_vault";
    private static final String[][] shape = StructureUtils.readStructureFromFile(SIV_STRUCTURE_FILE_PATH);
    private static final int HORIZONTAL_OFF_SET = 2;
    private static final int VERTICAL_OFF_SET = 5;
    private static final int DEPTH_OFF_SET = 0;

    public static NumberFormat nf = NumberFormat.getNumberInstance();

    public ItemTank[] STORE = IntStream.range(0, MAX_DISTINCT_ITEMS)
        .mapToObj(i -> new ItemTank())
        .toArray(ItemTank[]::new);

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
    public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
        checkMachine(aBaseMetaTileEntity, mInventory[1]);
        super.onFirstTick(aBaseMetaTileEntity);
    }

    @Override
    public void onBlockDestroyed() {
        if (portBus != null) {
            portBus.unbind();
        }
        super.onBlockDestroyed();
    }

    @Override
    public @NotNull CheckRecipeResult checkProcessing() {
        mEfficiency = 10000;
        mEfficiencyIncrease = 10000;
        lEUt = 128;
        mMaxProgresstime = 20;

        ItemStack itemStack = getControllerSlot();
        this.itemSelector = (itemStack != null && itemStack.getItem() instanceof ItemIntegratedCircuit)
            ? itemStack.getItemDamage()
            : -1;

        // Suck in items
        ArrayList<ItemStack> inputItems = getStoredInputs();

        if (!inputItems.isEmpty()) {
            for (ItemStack aItem : inputItems) {
                ItemStack toDeplete = aItem.copy();
                toDeplete.stackSize = this.pull(aItem, true);
                depleteInput(toDeplete);
            }
        }

        // Push out items
        if (!this.mOutputBusses.isEmpty() || !this.mSteamOutputs.isEmpty()) {
            ItemTank sItem = this.getSelectedItem();
            boolean isItemSelected = this.itemSelector != -1;
            if (!isItemSelected || !sItem.isEmpty()) {
                if (isItemSelected) {
                    ItemStack stack = sItem.get(sItem.getCapacity());
                    if (stack != null) {
                        stack.stackSize = stack.stackSize - this.tryAddOutput(stack).stackSize;
                        if (stack.stackSize > 0) this.push(stack, true);
                    }
                } else {
                    ItemTank first = this.firstNotNull();
                    if (first != null && !first.isEmpty()) {
                        ItemStack stack = first.get(first.getCapacity());
                        if (stack != null) {
                            stack.stackSize = stack.stackSize - this.tryAddOutput(stack).stackSize;
                            if (stack.stackSize > 0) this.push(stack, true);
                        }
                    }
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

        if (!inputsFromME.isEmpty()) {
            rList.addAll(inputsFromME.values());
        }
        return rList;

    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (aBaseMetaTileEntity.isServerSide()) this.locked = !aBaseMetaTileEntity.isActive();
    }

    @Override
    public void onModeChangeByScrewdriver(ForgeDirection side, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        this.setDoVoidExcess(!doVoidExcess);
        GTUtility.sendChatToPlayer(aPlayer, "Auto-voiding " + (this.doVoidExcess ? "enabled" : "disabled"));
    }

    @Override
    public IStructureDefinition<SteamItemVault> getStructureDefinition() {
        return StructureDefinition.<SteamItemVault>builder()
            .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
            .addElement('A', chainAllGlasses())
            .addElement('B', ofBlock(BlockLoader.metaCasing, 29))
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
                    buildHatchAdder(SteamItemVault.class).casingIndex(getCasingTextureID())
                        .dot(1)
                        .atLeast(
                            SteamHatchElement.InputBus_Steam,
                            SteamHatchElement.OutputBus_Steam,
                            InputBus,
                            OutputBus,
                            SteamItemVaultPort.SteamItemVaultPortBus)
                        .buildAndChain(onElementPass(x -> ++x.tCountCasing, ofBlock(BlockLoader.metaCasing02, 0)))))
            .addElement('D', ofFrame(Materials.Steel))
            .build();
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        if (!checkPiece(STRUCTURE_PIECE_MAIN, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET) || !checkHatch()) {
            return false;
        }
        if (portBus != null) portBus.bind(this);
        return tCountCasing >= 30;
    }

    @Override
    public void clearHatches() {
        super.clearHatches();
        if (portBus != null) {
            portBus.unbind();
            portBus = null;
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
            .addInfo(StatCollector.translateToLocalFormatted("Tooltip_SteamItemVault_00", MAX_DISTINCT_ITEMS))
            .addInfo(StatCollector.translateToLocalFormatted("Tooltip_SteamItemVault_01", MAX_DISTINCT_ITEMS))
            .addInfo(StatCollector.translateToLocal("Tooltip_SteamItemVault_02"))
            .addInfo(StatCollector.translateToLocal("Tooltip_SteamItemVault_03"))
            .addInfo(StatCollector.translateToLocal("Tooltip_SteamItemVault_04"))
            .addInfo(StatCollector.translateToLocal("Tooltip_SteamItemVault_05"))
            .addInfo(StatCollector.translateToLocal("Tooltip_SteamItemVault_06"))
            .addInfo(StatCollector.translateToLocalFormatted("Tooltip_SteamItemVault_07", nf.format(MAX_CAPACITY)))
            .addSeparator()
            .addInfo(StatCollector.translateToLocal("StructureTooComplex"))
            .addInfo(StatCollector.translateToLocal("BLUE_PRINT_INFO"))
            .beginStructureBlock(5, 7, 5, false)
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
        for (int i = 0; i < Math.max(32, MAX_DISTINCT_ITEMS); i++) {
            ItemTank tank = STORE[i];
            if (tank.isEmpty()) {
                ll.add(
                    MessageFormat.format(
                        "{0} - {1}: {2} ({3}%)",
                        i,
                        StatCollector.translateToLocal("Info_SteamItemVault_StoredItems_NULL"),
                        0,
                        0));
            } else {
                String localizedName = STORE[i].name();
                String amount = nf.format(STORE[i].amount());
                String percentage = capacityPerItem > 0 ? String.valueOf(STORE[i].amount() * 100 / capacityPerItem)
                    : "";
                ll.add(MessageFormat.format("{0} - {1}: {2} ({3}%)", i, localizedName, amount, percentage));
            }
        }
        ll.add(
            EnumChatFormatting.YELLOW + StatCollector.translateToLocal("Info_SteamItemVault_OperationalData")
                + EnumChatFormatting.RESET);
        ll.add(StatCollector.translateToLocalFormatted("Info_SteamItemVault_Used", nf.format(getStoredAmount())));
        ll.add(StatCollector.translateToLocalFormatted("Info_SteamItemVault_Total", nf.format(capacity)));
        ll.add(
            StatCollector.translateToLocalFormatted("Info_SteamItemVault_PerItemCapacity", nf.format(capacityPerItem)));
        ll.add(StatCollector.translateToLocalFormatted("Info_SteamItemVault_RunningCost", getActualEnergyUsage()));
        ll.add(StatCollector.translateToLocalFormatted("Info_SteamItemVault_AutoVoiding", doVoidExcess));
        ll.add(EnumChatFormatting.STRIKETHROUGH + "---------------------------------------------");

        return ll.toArray(new String[0]);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        aNBT.setByteArray("capacity", capacity.toByteArray());
        aNBT.setBoolean("doVoidExcess", doVoidExcess);
        aNBT.setBoolean("lockItem", locked);
        aNBT.setInteger("itemSelector", itemSelector);

        NBTTagCompound itemNbt = new NBTTagCompound();
        aNBT.setTag("STORE", itemNbt);

        for (int i = 0; i < MAX_DISTINCT_ITEMS; i++) {
            STORE[i].writeToNBT(itemNbt, String.valueOf(i));
        }

        super.saveNBTData(aNBT);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        this.setCapacity(new BigInteger(aNBT.getByteArray("capacity")));
        this.setDoVoidExcess(aNBT.getBoolean("doVoidExcess"));
        this.locked = aNBT.getBoolean("lockItem");
        this.itemSelector = aNBT.getInteger("itemSelector");

        NBTTagCompound itemNbt = (NBTTagCompound) aNBT.getTag("STORE");
        for (int i = 0; i < MAX_DISTINCT_ITEMS; i++) {
            STORE[i].readFromNBT(itemNbt, String.valueOf(i));
        }

        super.loadNBTData(aNBT);
    }

    @Override
    public int pull(ItemStack aItem, boolean doPull) {
        if (locked) return 0;
        int index = getItemPosition(aItem);
        if (index >= 0) {
            return STORE[index].fill(aItem, doPull);
        } else if (itemCount() < MAX_DISTINCT_ITEMS) {
            return STORE[getNullSlot()].setCapacity(capacityPerItem)
                .fill(aItem, doPull);
        }
        return 0;
    }

    @Override
    public long pull(ItemStack aItem, long amount, boolean doPull) {
        if (locked) return 0;
        int index = getItemPosition(aItem);
        if (index >= 0) {
            ItemTank tank = STORE[index];
            if (doPull) return tank.add(amount);
            return doVoidExcess ? amount
                : tank.amount() + amount > tank.capacity() ? tank.capacity() - tank.amount() : amount;
        } else if (itemCount() < MAX_DISTINCT_ITEMS) {
            ItemTank tank = STORE[getNullSlot()];
            if (doPull) return tank.add(amount, aItem);
            return doVoidExcess ? amount : Math.min(amount, tank.capacity());
        }
        return 0;
    }

    @Override
    public ItemStack push(ItemStack aItem, boolean doPush) {
        if (locked) return null;
        int index = getItemPosition(aItem);
        if (index < 0) return null;
        return STORE[index].drain(aItem.stackSize, doPush);
    }

    @Override
    public ItemStack push(int amount, boolean doPush) {
        if (locked) return null;
        int index = firstNotNullSlot();
        if (index < 0) return null;
        return STORE[index].drain(amount, doPush);
    }

    @Override
    public long push(ItemStack aItem, long amount, boolean doPush) {
        if (locked) return 0;
        int index = getItemPosition(aItem);
        if (index < 0) return 0;
        if (doPush) return STORE[index].remove(amount);
        return STORE[index].amount(amount);
    }

    @Override
    public long getcapacityPerItem() {
        return this.capacityPerItem;
    }

    @Override
    public void setCapacity(BigInteger capacity) {
        if (capacity.compareTo(MAX_CAPACITY) > 0) {
            this.capacity = MAX_CAPACITY;
            this.capacityPerItem = Long.MAX_VALUE;
        } else {
            this.capacity = capacity;
            this.capacityPerItem = capacity.divide(BigInteger.valueOf(MAX_DISTINCT_ITEMS))
                .longValue();
        }

        for (int i = 0; i < MAX_DISTINCT_ITEMS; i++) {
            ItemTank tank = STORE[i];
            if (tank.setCapacity(capacityPerItem)
                .amount() > capacityPerItem) {
                STORE[i] = new ItemTank(tank.get(), capacityPerItem, capacityPerItem);
            }
        }
    }

    @Override
    public int itemCount() {
        int tCount = 0;
        for (int i = 0; i < MAX_DISTINCT_ITEMS; i++) {
            if (!STORE[i].isEmpty()) tCount++;
        }
        return tCount;
    }

    @Override
    public int getItemPosition(String itemName) {
        for (int i = 0; i < MAX_DISTINCT_ITEMS; i++) {
            if (!STORE[i].isEmpty() && STORE[i].name()
                .equals(itemName)) return i;
        }
        return -1;
    }

    @Override
    public int getItemPosition(ItemStack aItem) {
        for (int i = 0; i < MAX_DISTINCT_ITEMS; i++) {
            if (STORE[i].contains(aItem)) return i;
        }
        return -1;
    }

    @Override
    public int getNullSlot() {
        for (int i = 0; i < MAX_DISTINCT_ITEMS; i++) {
            if (STORE[i].isEmpty()) return i;
        }
        return -1;
    }

    @Override
    public boolean contains(String itemName) {
        return getItemPosition(itemName) >= 0;
    }

    @Override
    public boolean contains(ItemStack aItem) {
        return getItemPosition(aItem) >= 0;
    }

    @Override
    public int firstNotNullSlot() {
        for (int i = 0; i < MAX_DISTINCT_ITEMS; i++) {
            if (!STORE[i].isEmpty()) return i;
        }
        return -1;
    }

    @Override
    public ItemTank firstNotNull() {
        for (int i = 0; i < MAX_DISTINCT_ITEMS; i++) {
            if (!STORE[i].isEmpty()) return STORE[i];
        }
        return null;
    }

    @Override
    public BigInteger getStoredAmount() {
        BigInteger amount = BigInteger.ZERO;
        for (int i = 0; i < MAX_DISTINCT_ITEMS; i++) {
            amount = amount.add(BigInteger.valueOf(STORE[i].amount()));
        }
        return amount;
    }

    @Override
    public int getItemSelector() {
        return itemSelector;
    }

    @Override
    public ItemTank getSelectedItem() {
        return itemSelector != -1 ? STORE[itemSelector] : null;
    }

    @Override
    public void setDoVoidExcess(boolean doVoidExcess) {
        this.doVoidExcess = doVoidExcess;
        for (int i = 0; i < MAX_DISTINCT_ITEMS; i++) {
            STORE[i].setVoidExcess(doVoidExcess);
        }
    }

    @Override
    public ItemTank.ItemTankInfo[] getTankInfo() {
        ItemTank.ItemTankInfo[] info = new ItemTank.ItemTankInfo[MAX_DISTINCT_ITEMS];
        for (int i = 0; i < MAX_DISTINCT_ITEMS; i++) {
            info[i] = STORE[i].getInfo();
        }
        return info;
    }

    @Override
    public ItemTank[] getStore() {
        return STORE;
    }

    public boolean addMultiHatchToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity != null) {
            final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
            if (aMetaTileEntity instanceof ItemVaultPortBus itemVaultPortBus) {
                if (this.portBus != null) return false;
                this.portBus = itemVaultPortBus;
                this.portBus.updateTexture(aBaseCasingIndex);
                return true;
            }
        }
        return false;
    }

    public enum SteamItemVaultPort implements IHatchElement<SteamItemVault> {

        SteamItemVaultPortBus;

        private final List<? extends Class<? extends IMetaTileEntity>> mteClasses;

        @SafeVarargs
        SteamItemVaultPort(Class<? extends IMetaTileEntity>... mteClasses) {
            this.mteClasses = Arrays.asList(mteClasses);
        }

        @Override
        public List<? extends Class<? extends IMetaTileEntity>> mteClasses() {
            return mteClasses;
        }

        @Override
        public IGTHatchAdder<? super SteamItemVault> adder() {
            return SteamItemVault::addMultiHatchToMachineList;
        }

        @Override
        public long count(SteamItemVault t) {
            return t.portBus == null ? 0 : 1;
        }
    }

}
