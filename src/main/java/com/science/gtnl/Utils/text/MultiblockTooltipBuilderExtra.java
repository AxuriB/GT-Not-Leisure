package com.science.gtnl.Utils.text;

import net.minecraft.util.EnumChatFormatting;

import gregtech.api.structure.IStructureChannels;
import gregtech.api.util.MultiblockTooltipBuilder;

public class MultiblockTooltipBuilderExtra extends MultiblockTooltipBuilder {

    public static MultiblockTooltipBuilder addMachineType(MultiblockTooltipBuilder builder, CharSequence machine) {
        builder.addMachineType(machine.toString());
        return builder;
    }

    public static MultiblockTooltipBuilder addInfo(MultiblockTooltipBuilder builder, CharSequence info) {
        builder.addInfo(info.toString());
        return builder;
    }

    public static MultiblockTooltipBuilder addController(MultiblockTooltipBuilder builder, CharSequence info) {
        builder.addController(info.toString());
        return builder;
    }

    public static MultiblockTooltipBuilder addCasingInfoExactly(MultiblockTooltipBuilder builder,
        CharSequence casingName, int count, boolean isTiered) {
        return addCasingInfoExactlyColored(
            builder,
            casingName,
            EnumChatFormatting.GRAY,
            count,
            EnumChatFormatting.GOLD,
            isTiered);
    }

    public static MultiblockTooltipBuilder addCasingInfoExactlyColored(MultiblockTooltipBuilder builder,
        CharSequence casingName, EnumChatFormatting textColor, int count, EnumChatFormatting countColor,
        boolean isTiered) {
        builder.addCasingInfoExactly(casingName.toString(), count, isTiered);
        return builder;
    }

    public static MultiblockTooltipBuilder addCasingInfoMin(MultiblockTooltipBuilder builder, CharSequence casingName,
        int minCount, boolean isTiered) {
        return addCasingInfoMinColored(
            builder,
            casingName,
            EnumChatFormatting.GRAY,
            minCount,
            EnumChatFormatting.GOLD,
            isTiered);
    }

    public static MultiblockTooltipBuilder addCasingInfoMinColored(MultiblockTooltipBuilder builder,
        CharSequence casingName, EnumChatFormatting textColor, int minCount, EnumChatFormatting countColor,
        boolean isTiered) {
        builder.addCasingInfoMin(casingName.toString(), minCount, isTiered);
        return builder;
    }

    public static MultiblockTooltipBuilder addCasingInfoRange(MultiblockTooltipBuilder builder, CharSequence casingName,
        int minCount, int maxCount, boolean isTiered) {
        return addCasingInfoRangeColored(
            builder,
            casingName,
            EnumChatFormatting.GRAY,
            minCount,
            maxCount,
            EnumChatFormatting.GOLD,
            isTiered);
    }

    public static MultiblockTooltipBuilder addCasingInfoRangeColored(MultiblockTooltipBuilder builder,
        CharSequence casingName, EnumChatFormatting textColor, int minCount, int maxCount,
        EnumChatFormatting countColor, boolean isTiered) {
        builder.addCasingInfoRange(casingName.toString(), minCount, maxCount, isTiered);
        return builder;
    }

    public static MultiblockTooltipBuilder addOtherStructurePart(MultiblockTooltipBuilder builder, CharSequence name,
        CharSequence info) {
        builder.addOtherStructurePart(name.toString(), info.toString());
        return builder;
    }

    public static MultiblockTooltipBuilder addMaintenanceHatch(MultiblockTooltipBuilder builder, CharSequence info) {
        builder.addMaintenanceHatch(info.toString());
        return builder;
    }

    public static MultiblockTooltipBuilder addMufflerHatch(MultiblockTooltipBuilder builder, CharSequence info) {
        builder.addMufflerHatch(info.toString());
        return builder;
    }

    public static MultiblockTooltipBuilder addEnergyHatch(MultiblockTooltipBuilder builder, CharSequence info) {
        builder.addEnergyHatch(info.toString());
        return builder;
    }

    public static MultiblockTooltipBuilder addDynamoHatch(MultiblockTooltipBuilder builder, CharSequence info) {
        builder.addDynamoHatch(info.toString());
        return builder;
    }

    public static MultiblockTooltipBuilder addInputBus(MultiblockTooltipBuilder builder, CharSequence info) {
        builder.addInputBus(info.toString());
        return builder;
    }

    public static MultiblockTooltipBuilder addSteamInputBus(MultiblockTooltipBuilder builder, CharSequence info) {
        builder.addSteamInputBus(info.toString());
        return builder;
    }

    public static MultiblockTooltipBuilder addInputHatch(MultiblockTooltipBuilder builder, CharSequence info) {
        builder.addInputHatch(info.toString());
        return builder;
    }

    public static MultiblockTooltipBuilder addOutputBus(MultiblockTooltipBuilder builder, CharSequence info) {
        builder.addOutputBus(info.toString());
        return builder;
    }

    public static MultiblockTooltipBuilder addSteamOutputBus(MultiblockTooltipBuilder builder, CharSequence info) {
        builder.addSteamOutputBus(info.toString());
        return builder;
    }

    public static MultiblockTooltipBuilder addOutputHatch(MultiblockTooltipBuilder builder, CharSequence info) {
        builder.addOutputHatch(info.toString());
        return builder;
    }

    public static MultiblockTooltipBuilder addStructureInfo(MultiblockTooltipBuilder builder, CharSequence info) {
        builder.addStructureInfo(info.toString());
        return builder;
    }

    public static MultiblockTooltipBuilder addSubChannelUsage(MultiblockTooltipBuilder builder,
        IStructureChannels channel, CharSequence purpose) {
        builder.addSubChannelUsage(channel, purpose.toString());
        return builder;
    }

    public static MultiblockTooltipBuilder addStructureHint(MultiblockTooltipBuilder builder, CharSequence info) {
        builder.addStructureHint(info.toString());
        return builder;
    }

    public static MultiblockTooltipBuilder addStructureHint(MultiblockTooltipBuilder builder, CharSequence nameKey,
        int... dots) {
        builder.addStructureHint(nameKey.toString(), dots);
        return builder;
    }

}
