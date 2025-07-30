package com.science.gtnl.config;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import io.netty.buffer.ByteBuf;

public class ConfigData {

    // Machine
    public boolean enableRecipeOutputChance;
    public boolean enableMachineAmpLimit;
    public double recipeOutputChance;

    public int meteorMinerMaxBlockPerCycle;
    public int meteorMinerMaxRowPerCycle;

    public int euEveryEnhancementCore;
    public int euEveryDepletedExcitedNaquadahFuelRod;
    public double secondsOfArtificialStarProgressCycleTime;
    public boolean enableRenderDefaultArtificialStar;

    public boolean enablePortalToAlfheimBigBoom;
    public boolean enableEternalGregTechWorkshopSpiralRender;
    public boolean enableVoidMinerTweak;
    public boolean enableIntegratedOreFactoryChange;

    // Recipe
    public boolean enableDeleteRecipe;
    public boolean enableAprilFoolRecipe;
    public boolean enableShowDelRecipeTitle;
    public boolean enableSomethingRecipe;

    // Tickrate
    public float defaultTickrate;
    public float minTickrate;
    public float maxTickrate;
    public boolean showTickrateMessages;

    // Player Doll
    public boolean enableCustomPlayerDoll;

    // Extreme Anvil
    public String unbreakOre;

    // Infinity Sword
    public boolean enableInfinitySwordBypassMechanism;
    public boolean enableInfinitySwordExplosion;
    public boolean enableRenderInfinitySwordSpecial;

    // Chronarch's Clock
    public int chronarchsClockRadius;
    public int chronarchsClockSpeedMultiplier;
    public int chronarchsClockDurationTicks;
    public int chronarchsClockCooldown;

    // BloodMagic
    public boolean enableMeteorSetBlockOptimize;
    public int meteorParadigmChunkSize;
    public int meteorParadigmBatchUpdateInterval;

    // NotEnoughItems
    public boolean enableSpecialCheatIcon;
    public int specialIconType;

    // SuperCreeper
    public List<String> targetBlockSpecs;
    public String[] defaultTargetBlocks;
    public boolean enableSuperCreeper;
    public int blockTargetInterval;
    public int playerTargetInterval;
    public int blockFindRadius;
    public int playerFindRadius;
    public int explosionPower;
    public double moveSpeed;
    public double explosionTriggerRange;
    public double creeperSpeedBonusScale;
    public boolean enableCreeperBurningExplosion;
    public int burningExplosionTimer;
    public boolean enableCreeperIgnitedDeathExplosion;
    public boolean enableCreeperHurtByCreeperExplosion;
    public boolean enableCreeperKilledByCreeperExplosion;
    public boolean enableCreeperFindSpider;
    public boolean allowCreeperExplosionBypassGamerule;
    public double spiderMoveSpeed;
    public int spiderFindRadius;
    public int spiderTargetInterval;

    // Effect
    public int aweEffectID;
    public int perfectPhysiqueEffect;
    public int shimmerEffect;

    // Other
    public boolean enableSaturationHeal;

    // Message
    public boolean enableShowJoinMessage;
    public boolean enableShowAddMods;

    // Debug
    public boolean enableDebugMode;

    public void write(ByteBuf buf) {
        // Machine
        buf.writeBoolean(enableRecipeOutputChance);
        buf.writeBoolean(enableMachineAmpLimit);
        buf.writeDouble(recipeOutputChance);
        buf.writeInt(meteorMinerMaxBlockPerCycle);
        buf.writeInt(meteorMinerMaxRowPerCycle);
        buf.writeInt(euEveryEnhancementCore);
        buf.writeInt(euEveryDepletedExcitedNaquadahFuelRod);
        buf.writeDouble(secondsOfArtificialStarProgressCycleTime);
        buf.writeBoolean(enableRenderDefaultArtificialStar);
        buf.writeBoolean(enableEternalGregTechWorkshopSpiralRender);
        buf.writeBoolean(enablePortalToAlfheimBigBoom);
        buf.writeBoolean(enableVoidMinerTweak);
        buf.writeBoolean(enableIntegratedOreFactoryChange);

        // Recipe
        buf.writeBoolean(enableDeleteRecipe);
        buf.writeBoolean(enableAprilFoolRecipe);
        buf.writeBoolean(enableShowDelRecipeTitle);
        buf.writeBoolean(enableSomethingRecipe);

        // Tickrate
        buf.writeFloat(defaultTickrate);
        buf.writeFloat(minTickrate);
        buf.writeFloat(maxTickrate);
        buf.writeBoolean(showTickrateMessages);

        // Player Doll
        buf.writeBoolean(enableCustomPlayerDoll);

        // Extreme Anvil (String)
        if (unbreakOre != null) {
            byte[] strBytes = unbreakOre.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            buf.writeInt(strBytes.length);
            buf.writeBytes(strBytes);
        } else {
            buf.writeInt(0);
        }

        // Infinity Sword
        buf.writeBoolean(enableInfinitySwordBypassMechanism);
        buf.writeBoolean(enableInfinitySwordExplosion);
        buf.writeBoolean(enableRenderInfinitySwordSpecial);

        // Chronarch's Clock
        buf.writeInt(chronarchsClockRadius);
        buf.writeInt(chronarchsClockSpeedMultiplier);
        buf.writeInt(chronarchsClockDurationTicks);
        buf.writeInt(chronarchsClockCooldown);

        // BloodMagic
        buf.writeBoolean(enableMeteorSetBlockOptimize);
        buf.writeInt(meteorParadigmChunkSize);
        buf.writeInt(meteorParadigmBatchUpdateInterval);

        // NotEnoughItems
        buf.writeBoolean(enableSpecialCheatIcon);
        buf.writeInt(specialIconType);

        // SuperCreeper
        if (targetBlockSpecs != null) {
            buf.writeInt(targetBlockSpecs.size());
            for (String s : targetBlockSpecs) {
                if (s != null) {
                    byte[] bytes = s.getBytes(java.nio.charset.StandardCharsets.UTF_8);
                    buf.writeInt(bytes.length);
                    buf.writeBytes(bytes);
                } else {
                    buf.writeInt(0);
                }
            }
        } else {
            buf.writeInt(0);
        }

        if (defaultTargetBlocks != null) {
            buf.writeInt(defaultTargetBlocks.length);
            for (String s : defaultTargetBlocks) {
                if (s != null) {
                    byte[] bytes = s.getBytes(java.nio.charset.StandardCharsets.UTF_8);
                    buf.writeInt(bytes.length);
                    buf.writeBytes(bytes);
                } else {
                    buf.writeInt(0);
                }
            }
        } else {
            buf.writeInt(0);
        }

        buf.writeBoolean(enableSuperCreeper);
        buf.writeInt(blockTargetInterval);
        buf.writeInt(playerTargetInterval);
        buf.writeInt(blockFindRadius);
        buf.writeInt(playerFindRadius);
        buf.writeInt(explosionPower);
        buf.writeDouble(moveSpeed);
        buf.writeDouble(creeperSpeedBonusScale);
        buf.writeDouble(explosionTriggerRange);
        buf.writeBoolean(enableCreeperBurningExplosion);
        buf.writeBoolean(enableCreeperIgnitedDeathExplosion);
        buf.writeBoolean(enableCreeperHurtByCreeperExplosion);
        buf.writeBoolean(enableCreeperKilledByCreeperExplosion);
        buf.writeBoolean(enableCreeperFindSpider);
        buf.writeBoolean(allowCreeperExplosionBypassGamerule);
        buf.writeDouble(spiderMoveSpeed);
        buf.writeInt(spiderFindRadius);
        buf.writeInt(spiderTargetInterval);
        buf.writeInt(burningExplosionTimer);

        // Effect
        buf.writeInt(aweEffectID);
        buf.writeInt(perfectPhysiqueEffect);
        buf.writeInt(shimmerEffect);

        // Other
        buf.writeBoolean(enableSaturationHeal);

        // Message
        buf.writeBoolean(enableShowJoinMessage);
        buf.writeBoolean(enableShowAddMods);

        // Debug
        buf.writeBoolean(enableDebugMode);
    }

    public void read(ByteBuf buf) {
        // Machine
        enableRecipeOutputChance = buf.readBoolean();
        enableMachineAmpLimit = buf.readBoolean();
        recipeOutputChance = buf.readDouble();
        meteorMinerMaxBlockPerCycle = buf.readInt();
        meteorMinerMaxRowPerCycle = buf.readInt();
        euEveryEnhancementCore = buf.readInt();
        euEveryDepletedExcitedNaquadahFuelRod = buf.readInt();
        secondsOfArtificialStarProgressCycleTime = buf.readDouble();
        enableRenderDefaultArtificialStar = buf.readBoolean();
        enableEternalGregTechWorkshopSpiralRender = buf.readBoolean();
        enablePortalToAlfheimBigBoom = buf.readBoolean();
        enableVoidMinerTweak = buf.readBoolean();
        enableIntegratedOreFactoryChange = buf.readBoolean();

        // Recipe
        enableDeleteRecipe = buf.readBoolean();
        enableAprilFoolRecipe = buf.readBoolean();
        enableShowDelRecipeTitle = buf.readBoolean();
        enableSomethingRecipe = buf.readBoolean();

        // Tickrate
        defaultTickrate = buf.readFloat();
        minTickrate = buf.readFloat();
        maxTickrate = buf.readFloat();
        showTickrateMessages = buf.readBoolean();

        // Player Doll
        enableCustomPlayerDoll = buf.readBoolean();

        // Extreme Anvil (String)
        int unbreakOreLen = buf.readInt();
        if (unbreakOreLen > 0) {
            byte[] strBytes = new byte[unbreakOreLen];
            buf.readBytes(strBytes);
            unbreakOre = new String(strBytes, java.nio.charset.StandardCharsets.UTF_8);
        } else {
            unbreakOre = null;
        }

        // Infinity Sword
        enableInfinitySwordBypassMechanism = buf.readBoolean();
        enableInfinitySwordExplosion = buf.readBoolean();
        enableRenderInfinitySwordSpecial = buf.readBoolean();

        // Chronarch's Clock
        chronarchsClockRadius = buf.readInt();
        chronarchsClockSpeedMultiplier = buf.readInt();
        chronarchsClockDurationTicks = buf.readInt();
        chronarchsClockCooldown = buf.readInt();

        // BloodMagic
        meteorParadigmChunkSize = buf.readInt();
        meteorParadigmBatchUpdateInterval = buf.readInt();
        enableMeteorSetBlockOptimize = buf.readBoolean();

        // NotEnoughItems
        enableSpecialCheatIcon = buf.readBoolean();
        specialIconType = buf.readInt();

        // SuperCreeper
        int targetBlockSpecsSize = buf.readInt();
        targetBlockSpecs = new java.util.concurrent.CopyOnWriteArrayList<>();
        for (int i = 0; i < targetBlockSpecsSize; i++) {
            int strLen = buf.readInt();
            if (strLen > 0) {
                byte[] bytes = new byte[strLen];
                buf.readBytes(bytes);
                targetBlockSpecs.add(new String(bytes, java.nio.charset.StandardCharsets.UTF_8));
            } else {
                targetBlockSpecs.add(null);
            }
        }

        int defaultTargetBlocksLen = buf.readInt();
        if (defaultTargetBlocksLen > 0) {
            defaultTargetBlocks = new String[defaultTargetBlocksLen];
            for (int i = 0; i < defaultTargetBlocksLen; i++) {
                int strLen = buf.readInt();
                if (strLen > 0) {
                    byte[] bytes = new byte[strLen];
                    buf.readBytes(bytes);
                    defaultTargetBlocks[i] = new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
                } else {
                    defaultTargetBlocks[i] = null;
                }
            }
        } else {
            defaultTargetBlocks = null;
        }

        enableSuperCreeper = buf.readBoolean();
        blockTargetInterval = buf.readInt();
        playerTargetInterval = buf.readInt();
        blockFindRadius = buf.readInt();
        playerFindRadius = buf.readInt();
        explosionPower = buf.readInt();
        moveSpeed = buf.readDouble();
        creeperSpeedBonusScale = buf.readDouble();
        explosionTriggerRange = buf.readDouble();
        enableCreeperBurningExplosion = buf.readBoolean();
        enableCreeperIgnitedDeathExplosion = buf.readBoolean();
        enableCreeperHurtByCreeperExplosion = buf.readBoolean();
        enableCreeperKilledByCreeperExplosion = buf.readBoolean();
        enableCreeperFindSpider = buf.readBoolean();
        allowCreeperExplosionBypassGamerule = buf.readBoolean();
        spiderMoveSpeed = buf.readDouble();
        spiderFindRadius = buf.readInt();
        spiderTargetInterval = buf.readInt();
        burningExplosionTimer = buf.readInt();

        // Effect
        aweEffectID = buf.readInt();
        perfectPhysiqueEffect = buf.readInt();
        shimmerEffect = buf.readInt();

        // Other
        enableSaturationHeal = buf.readBoolean();

        // Message
        enableShowJoinMessage = buf.readBoolean();
        enableShowAddMods = buf.readBoolean();

        // Debug
        enableDebugMode = buf.readBoolean();
    }

    public void readFromConfig() {
        // Machine
        enableRecipeOutputChance = MainConfig.enableRecipeOutputChance;
        enableMachineAmpLimit = MainConfig.enableMachineAmpLimit;
        recipeOutputChance = MainConfig.recipeOutputChance;
        meteorMinerMaxBlockPerCycle = MainConfig.meteorMinerMaxBlockPerCycle;
        meteorMinerMaxRowPerCycle = MainConfig.meteorMinerMaxRowPerCycle;
        euEveryEnhancementCore = MainConfig.euEveryEnhancementCore;
        euEveryDepletedExcitedNaquadahFuelRod = MainConfig.euEveryDepletedExcitedNaquadahFuelRod;
        secondsOfArtificialStarProgressCycleTime = MainConfig.secondsOfArtificialStarProgressCycleTime;
        enableRenderDefaultArtificialStar = MainConfig.enableRenderDefaultArtificialStar;
        enableEternalGregTechWorkshopSpiralRender = MainConfig.enableEternalGregTechWorkshopSpiralRender;
        enablePortalToAlfheimBigBoom = MainConfig.enablePortalToAlfheimBigBoom;
        enableVoidMinerTweak = MainConfig.enableVoidMinerTweak;
        enableIntegratedOreFactoryChange = MainConfig.enableIntegratedOreFactoryChange;

        // Recipe
        enableDeleteRecipe = MainConfig.enableDeleteRecipe;
        enableSomethingRecipe = MainConfig.enableSomethingRecipe;
        enableAprilFoolRecipe = MainConfig.enableAprilFoolRecipe;
        enableShowDelRecipeTitle = MainConfig.enableShowDelRecipeTitle;

        // Tickrate
        defaultTickrate = MainConfig.defaultTickrate;
        minTickrate = MainConfig.minTickrate;
        maxTickrate = MainConfig.maxTickrate;
        showTickrateMessages = MainConfig.showTickrateMessages;

        // Player Doll
        enableCustomPlayerDoll = MainConfig.enableCustomPlayerDoll;

        // Extreme Anvil
        unbreakOre = MainConfig.unbreakOre;

        // Infinity Sword
        enableInfinitySwordBypassMechanism = MainConfig.enableInfinitySwordBypassMechanism;
        enableInfinitySwordExplosion = MainConfig.enableInfinitySwordExplosion;
        enableRenderInfinitySwordSpecial = MainConfig.enableRenderInfinitySwordSpecial;

        // Chronarch's Clock
        chronarchsClockRadius = MainConfig.chronarchsClockRadius;
        chronarchsClockSpeedMultiplier = MainConfig.chronarchsClockSpeedMultiplier;
        chronarchsClockDurationTicks = MainConfig.chronarchsClockDurationTicks;
        chronarchsClockCooldown = MainConfig.chronarchsClockCooldown;

        // BloodMagic
        meteorParadigmChunkSize = MainConfig.meteorParadigmChunkSize;
        meteorParadigmBatchUpdateInterval = MainConfig.meteorParadigmBatchUpdateInterval;
        enableMeteorSetBlockOptimize = MainConfig.enableMeteorSetBlockOptimize;

        // Not Enough Items
        enableSpecialCheatIcon = MainConfig.enableSpecialCheatIcon;
        specialIconType = MainConfig.specialIconType;

        // Super Creeper
        targetBlockSpecs = new CopyOnWriteArrayList<>(MainConfig.targetBlockSpecs);
        defaultTargetBlocks = MainConfig.defaultTargetBlocks != null ? MainConfig.defaultTargetBlocks.clone() : null;
        enableSuperCreeper = MainConfig.enableSuperCreeper;
        blockTargetInterval = MainConfig.blockTargetInterval;
        playerTargetInterval = MainConfig.playerTargetInterval;
        blockFindRadius = MainConfig.blockFindRadius;
        playerFindRadius = MainConfig.playerFindRadius;
        explosionPower = MainConfig.explosionPower;
        moveSpeed = MainConfig.moveSpeed;
        creeperSpeedBonusScale = MainConfig.creeperSpeedBonusScale;
        explosionTriggerRange = MainConfig.explosionTriggerRange;
        enableCreeperBurningExplosion = MainConfig.enableCreeperBurningExplosion;
        enableCreeperIgnitedDeathExplosion = MainConfig.enableCreeperIgnitedDeathExplosion;
        enableCreeperHurtByCreeperExplosion = MainConfig.enableCreeperHurtByCreeperExplosion;
        enableCreeperKilledByCreeperExplosion = MainConfig.enableCreeperKilledByCreeperExplosion;
        enableCreeperFindSpider = MainConfig.enableCreeperFindSpider;
        allowCreeperExplosionBypassGamerule = MainConfig.allowCreeperExplosionBypassGamerule;
        spiderMoveSpeed = MainConfig.spiderMoveSpeed;
        spiderFindRadius = MainConfig.spiderFindRadius;
        spiderTargetInterval = MainConfig.spiderTargetInterval;
        burningExplosionTimer = MainConfig.burningExplosionTimer;

        // Effect
        aweEffectID = MainConfig.aweEffectID;
        perfectPhysiqueEffect = MainConfig.perfectPhysiqueEffect;
        shimmerEffect = MainConfig.shimmerEffect;

        // Other
        enableSaturationHeal = MainConfig.enableSaturationHeal;

        // Message
        enableShowJoinMessage = MainConfig.enableShowJoinMessage;
        enableShowAddMods = MainConfig.enableShowAddMods;

        // Debug
        enableDebugMode = MainConfig.enableDebugMode;
    }

    public void writeToConfig() {
        // Machine
        MainConfig.enableRecipeOutputChance = enableRecipeOutputChance;
        MainConfig.enableMachineAmpLimit = enableMachineAmpLimit;
        MainConfig.recipeOutputChance = recipeOutputChance;
        MainConfig.meteorMinerMaxBlockPerCycle = meteorMinerMaxBlockPerCycle;
        MainConfig.meteorMinerMaxRowPerCycle = meteorMinerMaxRowPerCycle;
        MainConfig.euEveryEnhancementCore = euEveryEnhancementCore;
        MainConfig.euEveryDepletedExcitedNaquadahFuelRod = euEveryDepletedExcitedNaquadahFuelRod;
        MainConfig.secondsOfArtificialStarProgressCycleTime = secondsOfArtificialStarProgressCycleTime;
        MainConfig.enableRenderDefaultArtificialStar = enableRenderDefaultArtificialStar;
        MainConfig.enableEternalGregTechWorkshopSpiralRender = enableEternalGregTechWorkshopSpiralRender;
        MainConfig.enablePortalToAlfheimBigBoom = enablePortalToAlfheimBigBoom;
        MainConfig.enableVoidMinerTweak = enableVoidMinerTweak;
        MainConfig.enableIntegratedOreFactoryChange = enableIntegratedOreFactoryChange;

        // Recipe
        MainConfig.enableDeleteRecipe = enableDeleteRecipe;
        MainConfig.enableSomethingRecipe = enableSomethingRecipe;
        MainConfig.enableAprilFoolRecipe = enableAprilFoolRecipe;
        MainConfig.enableShowDelRecipeTitle = enableShowDelRecipeTitle;

        // Tickrate
        MainConfig.defaultTickrate = defaultTickrate;
        MainConfig.minTickrate = minTickrate;
        MainConfig.maxTickrate = maxTickrate;
        MainConfig.showTickrateMessages = showTickrateMessages;

        // Player Doll
        MainConfig.enableCustomPlayerDoll = enableCustomPlayerDoll;

        // Extreme Anvil
        MainConfig.unbreakOre = unbreakOre;

        // Infinity Sword
        MainConfig.enableInfinitySwordBypassMechanism = enableInfinitySwordBypassMechanism;
        MainConfig.enableInfinitySwordExplosion = enableInfinitySwordExplosion;
        MainConfig.enableRenderInfinitySwordSpecial = enableRenderInfinitySwordSpecial;

        // Chronarch's Clock
        MainConfig.chronarchsClockRadius = chronarchsClockRadius;
        MainConfig.chronarchsClockSpeedMultiplier = chronarchsClockSpeedMultiplier;
        MainConfig.chronarchsClockDurationTicks = chronarchsClockDurationTicks;
        MainConfig.chronarchsClockCooldown = chronarchsClockCooldown;

        // BloodMagic
        MainConfig.meteorParadigmChunkSize = meteorParadigmChunkSize;
        MainConfig.meteorParadigmBatchUpdateInterval = meteorParadigmBatchUpdateInterval;
        MainConfig.enableMeteorSetBlockOptimize = enableMeteorSetBlockOptimize;

        // Not Enough Items
        MainConfig.enableSpecialCheatIcon = enableSpecialCheatIcon;
        MainConfig.specialIconType = specialIconType;

        // Super Creeper
        MainConfig.targetBlockSpecs = new CopyOnWriteArrayList<>(targetBlockSpecs);
        MainConfig.defaultTargetBlocks = defaultTargetBlocks != null ? defaultTargetBlocks.clone() : null;
        MainConfig.enableSuperCreeper = enableSuperCreeper;
        MainConfig.blockTargetInterval = blockTargetInterval;
        MainConfig.playerTargetInterval = playerTargetInterval;
        MainConfig.blockFindRadius = blockFindRadius;
        MainConfig.playerFindRadius = playerFindRadius;
        MainConfig.explosionPower = explosionPower;
        MainConfig.moveSpeed = moveSpeed;
        MainConfig.creeperSpeedBonusScale = creeperSpeedBonusScale;
        MainConfig.explosionTriggerRange = explosionTriggerRange;
        MainConfig.enableCreeperBurningExplosion = enableCreeperBurningExplosion;
        MainConfig.enableCreeperIgnitedDeathExplosion = enableCreeperIgnitedDeathExplosion;
        MainConfig.enableCreeperHurtByCreeperExplosion = enableCreeperHurtByCreeperExplosion;
        MainConfig.enableCreeperKilledByCreeperExplosion = enableCreeperKilledByCreeperExplosion;
        MainConfig.enableCreeperFindSpider = enableCreeperFindSpider;
        MainConfig.allowCreeperExplosionBypassGamerule = allowCreeperExplosionBypassGamerule;
        MainConfig.spiderMoveSpeed = spiderMoveSpeed;
        MainConfig.spiderFindRadius = spiderFindRadius;
        MainConfig.spiderTargetInterval = spiderTargetInterval;
        MainConfig.burningExplosionTimer = burningExplosionTimer;

        // Effect
        MainConfig.aweEffectID = aweEffectID;
        MainConfig.perfectPhysiqueEffect = perfectPhysiqueEffect;
        MainConfig.shimmerEffect = shimmerEffect;

        // Other
        MainConfig.enableSaturationHeal = enableSaturationHeal;

        // Message
        MainConfig.enableShowJoinMessage = enableShowJoinMessage;
        MainConfig.enableShowAddMods = enableShowAddMods;

        // Debug
        MainConfig.enableDebugMode = enableDebugMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConfigData that)) return false;
        return euEveryEnhancementCore == that.euEveryEnhancementCore
            && euEveryDepletedExcitedNaquadahFuelRod == that.euEveryDepletedExcitedNaquadahFuelRod
            && Double.compare(that.secondsOfArtificialStarProgressCycleTime, secondsOfArtificialStarProgressCycleTime)
                == 0
            && enableRenderDefaultArtificialStar == that.enableRenderDefaultArtificialStar
            && enablePortalToAlfheimBigBoom == that.enablePortalToAlfheimBigBoom
            && enableEternalGregTechWorkshopSpiralRender == that.enableEternalGregTechWorkshopSpiralRender
            && enableVoidMinerTweak == that.enableVoidMinerTweak
            && enableIntegratedOreFactoryChange == that.enableIntegratedOreFactoryChange
            && enableDeleteRecipe == that.enableDeleteRecipe
            && enableAprilFoolRecipe == that.enableAprilFoolRecipe
            && enableShowDelRecipeTitle == that.enableShowDelRecipeTitle
            && enableSomethingRecipe == that.enableSomethingRecipe
            && Float.compare(that.defaultTickrate, defaultTickrate) == 0
            && Float.compare(that.minTickrate, minTickrate) == 0
            && Float.compare(that.maxTickrate, maxTickrate) == 0
            && showTickrateMessages == that.showTickrateMessages
            && enableCustomPlayerDoll == that.enableCustomPlayerDoll
            && Objects.equals(unbreakOre, that.unbreakOre)
            && enableInfinitySwordBypassMechanism == that.enableInfinitySwordBypassMechanism
            && enableInfinitySwordExplosion == that.enableInfinitySwordExplosion
            && enableRenderInfinitySwordSpecial == that.enableRenderInfinitySwordSpecial
            && chronarchsClockRadius == that.chronarchsClockRadius
            && chronarchsClockSpeedMultiplier == that.chronarchsClockSpeedMultiplier
            && chronarchsClockDurationTicks == that.chronarchsClockDurationTicks
            && chronarchsClockCooldown == that.chronarchsClockCooldown
            && enableMeteorSetBlockOptimize == that.enableMeteorSetBlockOptimize
            && meteorParadigmChunkSize == that.meteorParadigmChunkSize
            && meteorParadigmBatchUpdateInterval == that.meteorParadigmBatchUpdateInterval
            && enableSpecialCheatIcon == that.enableSpecialCheatIcon
            && specialIconType == that.specialIconType
            && Objects.equals(targetBlockSpecs, that.targetBlockSpecs)
            && Arrays.equals(defaultTargetBlocks, that.defaultTargetBlocks)
            && enableSuperCreeper == that.enableSuperCreeper
            && blockTargetInterval == that.blockTargetInterval
            && playerTargetInterval == that.playerTargetInterval
            && blockFindRadius == that.blockFindRadius
            && playerFindRadius == that.playerFindRadius
            && explosionPower == that.explosionPower
            && Double.compare(that.moveSpeed, moveSpeed) == 0
            && Double.compare(that.explosionTriggerRange, explosionTriggerRange) == 0
            && Double.compare(that.creeperSpeedBonusScale, creeperSpeedBonusScale) == 0
            && enableCreeperBurningExplosion == that.enableCreeperBurningExplosion
            && burningExplosionTimer == that.burningExplosionTimer
            && enableCreeperIgnitedDeathExplosion == that.enableCreeperIgnitedDeathExplosion
            && enableCreeperHurtByCreeperExplosion == that.enableCreeperHurtByCreeperExplosion
            && enableCreeperKilledByCreeperExplosion == that.enableCreeperKilledByCreeperExplosion
            && enableCreeperFindSpider == that.enableCreeperFindSpider
            && allowCreeperExplosionBypassGamerule == that.allowCreeperExplosionBypassGamerule
            && Double.compare(that.spiderMoveSpeed, spiderMoveSpeed) == 0
            && spiderFindRadius == that.spiderFindRadius
            && spiderTargetInterval == that.spiderTargetInterval
            && aweEffectID == that.aweEffectID
            && perfectPhysiqueEffect == that.perfectPhysiqueEffect
            && shimmerEffect == that.shimmerEffect
            && enableSaturationHeal == that.enableSaturationHeal
            && enableShowJoinMessage == that.enableShowJoinMessage
            && enableShowAddMods == that.enableShowAddMods
            && enableDebugMode == that.enableDebugMode
            && enableRecipeOutputChance == that.enableRecipeOutputChance
            && enableMachineAmpLimit == that.enableMachineAmpLimit
            && Double.compare(that.recipeOutputChance, recipeOutputChance) == 0
            && meteorMinerMaxBlockPerCycle == that.meteorMinerMaxBlockPerCycle
            && meteorMinerMaxRowPerCycle == that.meteorMinerMaxRowPerCycle;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(
            euEveryEnhancementCore,
            euEveryDepletedExcitedNaquadahFuelRod,
            secondsOfArtificialStarProgressCycleTime,
            enableRenderDefaultArtificialStar,
            enablePortalToAlfheimBigBoom,
            enableEternalGregTechWorkshopSpiralRender,
            enableVoidMinerTweak,
            enableIntegratedOreFactoryChange,
            enableDeleteRecipe,
            enableAprilFoolRecipe,
            enableShowDelRecipeTitle,
            enableSomethingRecipe,
            defaultTickrate,
            minTickrate,
            maxTickrate,
            showTickrateMessages,
            enableCustomPlayerDoll,
            unbreakOre,
            enableInfinitySwordBypassMechanism,
            enableInfinitySwordExplosion,
            enableRenderInfinitySwordSpecial,
            chronarchsClockRadius,
            chronarchsClockSpeedMultiplier,
            chronarchsClockDurationTicks,
            chronarchsClockCooldown,
            enableMeteorSetBlockOptimize,
            meteorParadigmChunkSize,
            meteorParadigmBatchUpdateInterval,
            enableSpecialCheatIcon,
            specialIconType,
            targetBlockSpecs,
            enableSuperCreeper,
            blockTargetInterval,
            playerTargetInterval,
            blockFindRadius,
            playerFindRadius,
            explosionPower,
            moveSpeed,
            explosionTriggerRange,
            creeperSpeedBonusScale,
            enableCreeperBurningExplosion,
            burningExplosionTimer,
            enableCreeperIgnitedDeathExplosion,
            enableCreeperHurtByCreeperExplosion,
            enableCreeperKilledByCreeperExplosion,
            enableCreeperFindSpider,
            allowCreeperExplosionBypassGamerule,
            spiderMoveSpeed,
            spiderFindRadius,
            spiderTargetInterval,
            aweEffectID,
            perfectPhysiqueEffect,
            shimmerEffect,
            enableSaturationHeal,
            enableShowJoinMessage,
            enableShowAddMods,
            enableDebugMode,
            enableRecipeOutputChance,
            enableMachineAmpLimit,
            recipeOutputChance,
            meteorMinerMaxBlockPerCycle,
            meteorMinerMaxRowPerCycle);
        result = 31 * result + Arrays.hashCode(defaultTargetBlocks);
        return result;
    }

}
