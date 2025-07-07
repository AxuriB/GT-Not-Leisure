package com.science.gtnl.common.packet;

import com.science.gtnl.config.MainConfig;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class ConfigSyncPacket implements IMessage {

    public int euEnhCore;
    public int euFuelRod;
    public double starCycleTime;
    public boolean renderStar, portalBoom, cheatOwner, deleteRecipe, debug, infinityExpl, machineLimit, aprilFool,
        bypass, outputChance;

    public double recipeChance;

    public int meteorMinerMaxBlockPerCycle;
    public int meteorMinerMaxRowPerCycle;
    public boolean enableEternalRender;
    public boolean showDelRecipeTitle;

    public float defaultTickrate, minTickrate, maxTickrate;
    public boolean showTickrateMessages;

    public int chronarchRadius, chronarchSpeed, chronarchDuration, chronarchCooldown;

    public int meteorParadigmChunkSize, meteorParadigmUpdateInterval;

    public boolean enableSuperCreeper;
    public int blockTargetInterval, playerTargetInterval, blockFindRadius, playerFindRadius, explosionPower;
    public double moveSpeed, explosionTriggerRange;

    public int aweEffectID, perfectPhysiqueEffect;

    public ConfigSyncPacket() {}

    public ConfigSyncPacket(MainConfig cfg) {
        euEnhCore = cfg.euEveryEnhancementCore;
        euFuelRod = cfg.euEveryDepletedExcitedNaquadahFuelRod;
        starCycleTime = cfg.secondsOfArtificialStarProgressCycleTime;
        renderStar = cfg.enableRenderDefaultArtificialStar;
        portalBoom = cfg.enablePortalToAlfheimBigBoom;
        deleteRecipe = cfg.enableDeleteRecipe;
        debug = cfg.enableDebugMode;
        infinityExpl = cfg.enableInfinitySwordExplosion;
        machineLimit = cfg.enableMachineAmpLimit;
        aprilFool = cfg.enableAprilFoolRecipe;
        bypass = cfg.enableInfinitySwordBypassMechanism;
        outputChance = cfg.enableRecipeOutputChance;
        recipeChance = cfg.recipeOutputChance;

        meteorMinerMaxBlockPerCycle = cfg.meteorMinerMaxBlockPerCycle;
        meteorMinerMaxRowPerCycle = cfg.meteorMinerMaxRowPerCycle;
        enableEternalRender = cfg.enableEternalGregTechWorkshopSpiralRender;
        showDelRecipeTitle = cfg.enableShowDelRecipeTitle;

        defaultTickrate = cfg.defaultTickrate;
        minTickrate = cfg.minTickrate;
        maxTickrate = cfg.maxTickrate;
        showTickrateMessages = cfg.showTickrateMessages;

        chronarchRadius = cfg.chronarchsClockRadius;
        chronarchSpeed = cfg.chronarchsClockSpeedMultiplier;
        chronarchDuration = cfg.chronarchsClockDurationTicks;
        chronarchCooldown = cfg.chronarchsClockCooldown;

        meteorParadigmChunkSize = cfg.meteorParadigmChunkSize;
        meteorParadigmUpdateInterval = cfg.meteorParadigmBatchUpdateInterval;

        enableSuperCreeper = cfg.enableSuperCreeper;
        blockTargetInterval = cfg.blockTargetInterval;
        playerTargetInterval = cfg.playerTargetInterval;
        blockFindRadius = cfg.blockFindRadius;
        playerFindRadius = cfg.playerFindRadius;
        explosionPower = cfg.explosionPower;
        moveSpeed = cfg.moveSpeed;
        explosionTriggerRange = cfg.explosionTriggerRange;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(euEnhCore);
        buf.writeInt(euFuelRod);
        buf.writeDouble(starCycleTime);
        buf.writeBoolean(renderStar);
        buf.writeBoolean(portalBoom);
        buf.writeBoolean(cheatOwner);
        buf.writeBoolean(deleteRecipe);
        buf.writeBoolean(debug);
        buf.writeBoolean(infinityExpl);
        buf.writeBoolean(machineLimit);
        buf.writeBoolean(aprilFool);
        buf.writeBoolean(bypass);
        buf.writeBoolean(outputChance);
        buf.writeDouble(recipeChance);

        buf.writeInt(meteorMinerMaxBlockPerCycle);
        buf.writeInt(meteorMinerMaxRowPerCycle);
        buf.writeBoolean(enableEternalRender);
        buf.writeBoolean(showDelRecipeTitle);

        buf.writeFloat(defaultTickrate);
        buf.writeFloat(minTickrate);
        buf.writeFloat(maxTickrate);
        buf.writeBoolean(showTickrateMessages);

        buf.writeInt(chronarchRadius);
        buf.writeInt(chronarchSpeed);
        buf.writeInt(chronarchDuration);
        buf.writeInt(chronarchCooldown);

        buf.writeInt(meteorParadigmChunkSize);
        buf.writeInt(meteorParadigmUpdateInterval);

        buf.writeBoolean(enableSuperCreeper);
        buf.writeInt(blockTargetInterval);
        buf.writeInt(playerTargetInterval);
        buf.writeInt(blockFindRadius);
        buf.writeInt(playerFindRadius);
        buf.writeInt(explosionPower);
        buf.writeDouble(moveSpeed);
        buf.writeDouble(explosionTriggerRange);

        buf.writeInt(aweEffectID);
        buf.writeInt(perfectPhysiqueEffect);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        euEnhCore = buf.readInt();
        euFuelRod = buf.readInt();
        starCycleTime = buf.readDouble();
        renderStar = buf.readBoolean();
        portalBoom = buf.readBoolean();
        cheatOwner = buf.readBoolean();
        deleteRecipe = buf.readBoolean();
        debug = buf.readBoolean();
        infinityExpl = buf.readBoolean();
        machineLimit = buf.readBoolean();
        aprilFool = buf.readBoolean();
        bypass = buf.readBoolean();
        outputChance = buf.readBoolean();
        recipeChance = buf.readDouble();

        meteorMinerMaxBlockPerCycle = buf.readInt();
        meteorMinerMaxRowPerCycle = buf.readInt();
        enableEternalRender = buf.readBoolean();
        showDelRecipeTitle = buf.readBoolean();

        defaultTickrate = buf.readFloat();
        minTickrate = buf.readFloat();
        maxTickrate = buf.readFloat();
        showTickrateMessages = buf.readBoolean();

        chronarchRadius = buf.readInt();
        chronarchSpeed = buf.readInt();
        chronarchDuration = buf.readInt();
        chronarchCooldown = buf.readInt();

        meteorParadigmChunkSize = buf.readInt();
        meteorParadigmUpdateInterval = buf.readInt();

        enableSuperCreeper = buf.readBoolean();
        blockTargetInterval = buf.readInt();
        playerTargetInterval = buf.readInt();
        blockFindRadius = buf.readInt();
        playerFindRadius = buf.readInt();
        explosionPower = buf.readInt();
        moveSpeed = buf.readDouble();
        explosionTriggerRange = buf.readDouble();
    }

    public static class Handler implements IMessageHandler<ConfigSyncPacket, IMessage> {

        @Override
        public IMessage onMessage(ConfigSyncPacket msg, MessageContext ctx) {
            MainConfig.reloadConfig();
            MainConfig.euEveryEnhancementCore = msg.euEnhCore;
            MainConfig.euEveryDepletedExcitedNaquadahFuelRod = msg.euFuelRod;
            MainConfig.secondsOfArtificialStarProgressCycleTime = msg.starCycleTime;
            MainConfig.enableRenderDefaultArtificialStar = msg.renderStar;
            MainConfig.enablePortalToAlfheimBigBoom = msg.portalBoom;
            MainConfig.enableDeleteRecipe = msg.deleteRecipe;
            MainConfig.enableDebugMode = msg.debug;
            MainConfig.enableInfinitySwordExplosion = msg.infinityExpl;
            MainConfig.enableMachineAmpLimit = msg.machineLimit;
            MainConfig.enableAprilFoolRecipe = msg.aprilFool;
            MainConfig.enableInfinitySwordBypassMechanism = msg.bypass;
            MainConfig.enableRecipeOutputChance = msg.outputChance;
            MainConfig.recipeOutputChance = msg.recipeChance;

            MainConfig.meteorMinerMaxBlockPerCycle = msg.meteorMinerMaxBlockPerCycle;
            MainConfig.meteorMinerMaxRowPerCycle = msg.meteorMinerMaxRowPerCycle;
            MainConfig.enableEternalGregTechWorkshopSpiralRender = msg.enableEternalRender;
            MainConfig.enableShowDelRecipeTitle = msg.showDelRecipeTitle;

            MainConfig.defaultTickrate = msg.defaultTickrate;
            MainConfig.minTickrate = msg.minTickrate;
            MainConfig.maxTickrate = msg.maxTickrate;
            MainConfig.showTickrateMessages = msg.showTickrateMessages;

            MainConfig.chronarchsClockRadius = msg.chronarchRadius;
            MainConfig.chronarchsClockSpeedMultiplier = msg.chronarchSpeed;
            MainConfig.chronarchsClockDurationTicks = msg.chronarchDuration;
            MainConfig.chronarchsClockCooldown = msg.chronarchCooldown;

            MainConfig.meteorParadigmChunkSize = msg.meteorParadigmChunkSize;
            MainConfig.meteorParadigmBatchUpdateInterval = msg.meteorParadigmUpdateInterval;

            MainConfig.enableSuperCreeper = msg.enableSuperCreeper;
            MainConfig.blockTargetInterval = msg.blockTargetInterval;
            MainConfig.playerTargetInterval = msg.playerTargetInterval;
            MainConfig.blockFindRadius = msg.blockFindRadius;
            MainConfig.playerFindRadius = msg.playerFindRadius;
            MainConfig.explosionPower = msg.explosionPower;
            MainConfig.moveSpeed = msg.moveSpeed;
            MainConfig.explosionTriggerRange = msg.explosionTriggerRange;
            return null;
        }
    }
}
