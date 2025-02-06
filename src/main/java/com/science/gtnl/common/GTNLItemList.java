package com.science.gtnl.common;

import static gregtech.api.enums.GTValues.NI;

import java.util.Locale;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.science.gtnl.Utils.Utils;

import gregtech.api.util.GTLanguageManager;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;

public enum GTNLItemList {

    TestCasing,
    TestMetaBlock01_0,
    BlazeCubeBlock,
    NewHorizonsCoil,
    HeatVent,
    SlicingBlades,
    NeutroniumPipeCasing,
    NeutroniumGearbox,
    MolybdenumDisilicideCoil,
    EnergeticPhotovoltaicBlock,
    AdvancedPhotovoltaicBlock,
    VibrantPhotovoltaicBlock,
    TungstensteelGearbox,
    DimensionallyStableCasing,
    PressureBalancedCasing,
    ABSUltraSolidCasing,
    GravitationalFocusingLensBlock,
    GaiaStabilizedForceFieldCasing,

    TrollFace,
    DepletedExcitedNaquadahFuelRod,
    BlazeCube,
    EnhancementCore,
    StellarConstructionFrameMaterial,
    ActivatedGaiaPylon,
    PrecisionSteamMechanism,
    MeteorMinerSchematic1,
    MeteorMinerSchematic2,
    CircuitResonaticULV,
    CircuitResonaticLV,
    CircuitResonaticMV,
    CircuitResonaticHV,
    CircuitResonaticEV,
    CircuitResonaticIV,
    CircuitResonaticLuV,
    CircuitResonaticZPM,
    CircuitResonaticUV,
    CircuitResonaticUHV,
    CircuitResonaticUEV,
    CircuitResonaticUIV,
    VerySimpleCircuit,
    SimpleCircuit,
    BasicCircuit,
    AdvancedCircuit,
    EliteCircuit,
    StargateSingularity,
    StargateCompressedSingularity,
    BiowareSMDCapacitor,
    BiowareSMDDiode,
    BiowareSMDInductor,
    BiowareSMDResistor,
    BiowareSMDTransistor,
    ExoticSMDCapacitor,
    ExoticSMDDiode,
    ExoticSMDInductor,
    ExoticSMDResistor,
    ExoticSMDTransistor,
    TemporallySMDCapacitor,
    TemporallySMDDiode,
    TemporallySMDInductor,
    TemporallySMDResistor,
    TemporallySMDTransistor,
    CosmicSMDCapacitor,
    CosmicSMDDiode,
    CosmicSMDInductor,
    CosmicSMDResistor,
    CosmicSMDTransistor,
    LVParallelControllerCore,
    MVParallelControllerCore,
    HVParallelControllerCore,
    EVParallelControllerCore,
    IVParallelControllerCore,
    LuVParallelControllerCore,
    ZPMParallelControllerCore,
    UVParallelControllerCore,
    UHVParallelControllerCore,
    UEVParallelControllerCore,
    UIVParallelControllerCore,
    UMVParallelControllerCore,
    UXVParallelControllerCore,
    MAXParallelControllerCore,

    StargateCoil,
    StargateTier0,
    StargateTier1,
    StargateTier2,
    StargateTier3,
    StargateTier4,
    StargateTier5,
    StargateTier6,
    StargateTier7,
    StargateTier8,
    StargateTier9,
    Stargate_Coil_Compressed,

    FluidManaInputHatch,
    FluidIceInputHatch,
    FluidBlazeInputHatch,

    LaserBeacon,
    BronzeBrickCasing,
    SteelBrickCasing,
    CrushingWheels,

    FortifyGlowstone,

    GaiaGlass,
    TerraGlass,
    FusionGlass,
    Laser_Cooling_Casing,
    Antifreeze_Heatproof_Machine_Casing,

    BlackLamp,
    BlackLampOff,
    BlackLampBorderless,
    BlackLampOffBorderless,

    PinkLamp,
    PinkLampOff,
    PinkLampBorderless,
    PinkLampOffBorderless,

    RedLamp,
    RedLampOff,
    RedLampBorderless,
    RedLampOffBorderless,

    OrangeLamp,
    OrangeLampOff,
    OrangeLampBorderless,
    OrangeLampOffBorderless,

    YellowLamp,
    YellowLampOff,
    YellowLampBorderless,
    YellowLampOffBorderless,

    GreenLamp,
    GreenLampOff,
    GreenLampBorderless,
    GreenLampOffBorderless,

    LimeLamp,
    LimeLampOff,
    LimeLampBorderless,
    LimeLampOffBorderless,

    BlueLamp,
    BlueLampOff,
    BlueLampBorderless,
    BlueLampOffBorderless,

    LightBlueLamp,
    LightBlueLampOff,
    LightBlueLampBorderless,
    LightBlueLampOffBorderless,

    CyanLamp,
    CyanLampOff,
    CyanLampBorderless,
    CyanLampOffBorderless,

    BrownLamp,
    BrownLampOff,
    BrownLampBorderless,
    BrownLampOffBorderless,

    MagentaLamp,
    MagentaLampOff,
    MagentaLampBorderless,
    MagentaLampOffBorderless,

    PurpleLamp,
    PurpleLampOff,
    PurpleLampBorderless,
    PurpleLampOffBorderless,

    GrayLamp,
    GrayLampOff,
    GrayLampBorderless,
    GrayLampOffBorderless,

    LightGrayLamp,
    LightGrayLampOff,
    LightGrayLampBorderless,
    LightGrayLampOffBorderless,

    WhiteLamp,
    WhiteLampOff,
    WhiteLampBorderless,
    WhiteLampOffBorderless,

    MegaBlastFurnace,
    BrickedBlastFurnace,
    ColdIceFreezer,
    BlazeBlastFurnace,
    ChemicalPlant,
    VacuumFreezer,
    LargeMacerationTower,
    LargeCutter,
    LargeSiftingFunnel,
    LargeIndustrialLathe,
    LargeMaterialPress,
    LargeWiremill,
    LargeBender,
    ElectricImplosionCompressor,
    LargeExtruder,
    LargeArcSmelter,
    LargeForming,
    LargeElectrolyzer,
    LargeElectromagnet,
    LargeAssembler,
    LargeMixer,
    LargeCentrifuge,
    LargeChemicalBath,
    LargeAutoclave,
    LargeSolidifier,
    LargeExtractor,

    LibraryOfRuina,
    MatterFabricator,
    LargeBrewer,
    HandOfJohnDavisonRockefeller,
    EnergeticPhotovoltaicPowerStation,
    AdvancedPhotovoltaicPowerStation,
    VibrantPhotovoltaicPowerStation,
    IndustrialArcaneAssembler,
    NineIndustrialMultiMachine,
    RareEarthCentrifugal,
    ProcessingArray,
    MeteorMiner,
    LargeSteamCrusher,
    NeutroniumWireCutting,
    EdenGarden,
    BloodSoulSacrificialArray,
    TeleportationArrayToAlfheim,
    LapotronChip,
    LargeSteamFurnace,
    LargeSteamThermalCentrifuge,
    SteamCracking,
    LargeSteamChemicalBath,
    PrimitiveDistillationTower,
    LargeSteamAlloySmelter,
    ComponentAssembler,
    RealArtificialStar,
    CheatOreProcessingFactory,
    Desulfurizer,
    LargeCircuitAssembler,
    PetrochemicalPlant,
    SmeltingMixingFurnace,
    WhiteNightGenerator,
    LargeSteamCircuitAssembler,
    SteamAssemblyCasing,
    GenerationEarthEngine;

    public boolean mHasNotBeenSet;
    public boolean mDeprecated;
    public boolean mWarned;

    public ItemStack mStack;

    // endregion

    GTNLItemList() {
        mHasNotBeenSet = true;
    }

    GTNLItemList(boolean aDeprecated) {
        if (aDeprecated) {
            mDeprecated = true;
            mHasNotBeenSet = true;
        }
    }

    public Item getItem() {
        sanityCheck();
        if (Utils.isStackInvalid(mStack)) return null;// TODO replace a default issue item
        return mStack.getItem();
    }

    public Block getBlock() {
        sanityCheck();
        return Block.getBlockFromItem(getItem());
    }

    public ItemStack get(int aAmount, Object... aReplacements) {
        sanityCheck();
        // if invalid, return a replacements
        if (Utils.isStackInvalid(mStack)) {
            GTLog.out.println("Object in the ItemList is null at:");
            new NullPointerException().printStackTrace(GTLog.out);
            return Utils.copyAmount(aAmount, TestMetaBlock01_0.get(1));
        }
        return Utils.copyAmount(aAmount, mStack);
    }

    public int getMeta() {
        return mStack.getItemDamage();
    }

    public GTNLItemList set(Item aItem) {
        mHasNotBeenSet = false;
        if (aItem == null) return this;
        ItemStack aStack = new ItemStack(aItem, 1, 0);
        mStack = Utils.copyAmount(1, aStack);
        return this;
    }

    public GTNLItemList set(ItemStack aStack) {
        if (aStack != null) {
            mHasNotBeenSet = false;
            mStack = Utils.copyAmount(1, aStack);
        }
        return this;
    }

    public boolean hasBeenSet() {
        return !mHasNotBeenSet;
    }

    /**
     * Returns the internal stack. This method is unsafe. It's here only for quick operations. DON'T CHANGE THE RETURNED
     * VALUE!
     */
    public ItemStack getInternalStack_unsafe() {
        return mStack;
    }

    public void sanityCheck() {
        if (mHasNotBeenSet)
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (mDeprecated && !mWarned) {
            new Exception(this + " is now deprecated").printStackTrace(GTLog.err);
            // warn only once
            mWarned = true;
        }
    }

    public ItemStack getWithName(long aAmount, String aDisplayName, Object... aReplacements) {
        ItemStack rStack = get(1, aReplacements);
        if (GTUtility.isStackInvalid(rStack)) return NI;

        // CamelCase alphanumeric words from aDisplayName
        StringBuilder tCamelCasedDisplayNameBuilder = new StringBuilder();
        final String[] tDisplayNameWords = aDisplayName.split("\\W");
        for (String tWord : tDisplayNameWords) {
            if (!tWord.isEmpty()) tCamelCasedDisplayNameBuilder.append(
                tWord.substring(0, 1)
                    .toUpperCase(Locale.US));
            if (tWord.length() > 1) tCamelCasedDisplayNameBuilder.append(
                tWord.substring(1)
                    .toLowerCase(Locale.US));
        }
        if (tCamelCasedDisplayNameBuilder.length() == 0) {
            // CamelCased DisplayName is empty, so use hash of aDisplayName
            tCamelCasedDisplayNameBuilder.append(((Long) (long) aDisplayName.hashCode()));
        }

        // Construct a translation key from UnlocalizedName and CamelCased DisplayName
        final String tKey = rStack.getUnlocalizedName() + ".with." + tCamelCasedDisplayNameBuilder + ".name";

        rStack.setStackDisplayName(GTLanguageManager.addStringLocalization(tKey, aDisplayName));
        return GTUtility.copyAmount(aAmount, rStack);
    }

}
