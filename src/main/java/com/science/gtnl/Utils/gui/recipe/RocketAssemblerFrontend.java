package com.science.gtnl.Utils.gui.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import org.apache.commons.lang3.tuple.Pair;

import com.gtnewhorizons.modularui.api.ModularUITextures;
import com.gtnewhorizons.modularui.api.drawable.IDrawable;
import com.gtnewhorizons.modularui.api.forge.IItemHandlerModifiable;
import com.gtnewhorizons.modularui.api.math.Pos2d;
import com.gtnewhorizons.modularui.api.math.Size;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.common.widget.DrawableWidget;
import com.gtnewhorizons.modularui.common.widget.SlotWidget;
import com.science.gtnl.Utils.item.ItemUtils;

import gregtech.api.enums.SteamVariant;
import gregtech.api.gui.modularui.SteamTexture;
import gregtech.api.recipe.BasicUIProperties;
import gregtech.api.recipe.BasicUIPropertiesBuilder;
import gregtech.api.recipe.NEIRecipePropertiesBuilder;
import gregtech.api.recipe.RecipeMapFrontend;
import gregtech.api.util.MethodsReturnNonnullByDefault;
import gregtech.common.gui.modularui.UIHelper;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RocketAssemblerFrontend extends RecipeMapFrontend {

    public RocketAssemblerFrontend(BasicUIPropertiesBuilder uiPropertiesBuilder,
        NEIRecipePropertiesBuilder neiPropertiesBuilder) {
        super(
            uiPropertiesBuilder.progressBarPos(new Pos2d(111, 80)),
            neiPropertiesBuilder.recipeBackgroundSize(new Size(170, 170)));
    }

    @Override
    public List<Pos2d> getItemInputPositions(int itemInputCount) {
        return new ArrayList<>();
    }

    @Override
    public List<Pos2d> getItemOutputPositions(int itemOutputCount) {
        return new ArrayList<>();
    }

    public static final int[][] rocketT1 = new int[][] { { 121, 21 }, // 1 Lander
        { 121, 39 }, // 2 Control Computer
        { 103, 30 }, // 3 Fuel Canister
        { 139, 30 }, // 4 Fuel Canister
        { 103, 48 }, // 5 Empty (Fuel)
        { 139, 48 }, // 6 Empty (Fuel)
        { 40, 30 }, // 7 Nose Cone
        { 31, 48 }, // 8 Heavy Plating
        { 49, 48 }, // 9 Heavy Plating
        { 31, 66 }, // 10 Heavy Plating
        { 49, 66 }, // 11 Heavy Plating
        { 31, 84 }, // 12 Heavy Plating
        { 49, 84 }, // 13 Heavy Plating
        { 31, 102 }, // 14 Heavy Plating
        { 49, 102 }, // 15 Heavy Plating
        { 40, 120 }, // 16 Engine
        { 13, 102 }, // 17 Fin
        { 67, 102 }, // 18 Fin
        { 13, 120 }, // 19 Fin
        { 67, 120 }, // 20 Fin
        { 121, 57 } // 21 Chest
    };

    // @Override
    // public void drawNEIOverlays(GTNEIDefaultHandler.CachedDefaultRecipe neiCachedRecipe) {
    // final GTRecipe recipe = neiCachedRecipe.mRecipe;
    //
    // if (!initializedRecipes.contains(recipe)) {
    // ItemStack[] itemStacks = recipe.mInputs;
    // if (itemStacks == null) return;
    //
    // if (itemStacks.length <= 21) {
    // for (int i = 0; i < itemStacks.length; i++) {
    // ItemStack stack = itemStacks[i];
    // if (stack == null) continue;
    //
    // int[] pos = rocketT1[i];
    // neiCachedRecipe.mInputs.add(new PositionedStack(stack, pos[0], pos[1], false));
    // }
    // }
    //
    // initializedRecipes.add(recipe);
    // }
    //
    // super.drawNEIOverlays(neiCachedRecipe);
    // }

    @Override
    public ModularWindow.Builder createNEITemplate(IItemHandlerModifiable itemInputsInventory,
        IItemHandlerModifiable itemOutputsInventory, IItemHandlerModifiable specialSlotInventory,
        IItemHandlerModifiable fluidInputsInventory, IItemHandlerModifiable fluidOutputsInventory,
        Supplier<Float> progressSupplier, Pos2d windowOffset) {
        ModularWindow.Builder builder = ModularWindow.builder(neiProperties.recipeBackgroundSize);

        for (int i = 0; i < rocketT1.length; i++) {
            int[] pos = rocketT1[i];
            builder.widget(
                SlotWidget.phantom(itemInputsInventory, i)
                    .setBackground(ModularUITextures.ITEM_SLOT)
                    .setPos(new Pos2d(pos[0], pos[1]).add(windowOffset))
                    .setSize(18, 18));
        }

        builder.widget(
            SlotWidget.phantom(itemOutputsInventory, 0)
                .setBackground(ModularUITextures.ITEM_SLOT)
                .setPos(new Pos2d(139, 80).add(windowOffset))
                .setSize(18, 18));

        forEachSlots(
            (i, backgrounds, pos) -> builder.widget(
                SlotWidget.phantom(itemInputsInventory, i)
                    .setBackground(backgrounds)
                    .setPos(pos)
                    .setSize(18, 18)),
            (i, backgrounds, pos) -> builder.widget(
                SlotWidget.phantom(itemOutputsInventory, i)
                    .setBackground(backgrounds)
                    .setPos(pos)
                    .setSize(18, 18)),
            (i, backgrounds, pos) -> {
                if (uiProperties.useSpecialSlot) builder.widget(
                    SlotWidget.phantom(specialSlotInventory, 0)
                        .setBackground(backgrounds)
                        .setPos(pos)
                        .setSize(18, 18));
            },
            (i, backgrounds, pos) -> builder.widget(
                SlotWidget.phantom(fluidInputsInventory, i)
                    .setBackground(backgrounds)
                    .setPos(pos)
                    .setSize(18, 18)),
            (i, backgrounds, pos) -> builder.widget(
                SlotWidget.phantom(fluidOutputsInventory, i)
                    .setBackground(backgrounds)
                    .setPos(pos)
                    .setSize(18, 18)),
            ModularUITextures.ITEM_SLOT,
            ModularUITextures.FLUID_SLOT,
            uiProperties,
            uiProperties.maxItemInputs,
            uiProperties.maxItemOutputs,
            uiProperties.maxFluidInputs,
            uiProperties.maxFluidOutputs,
            SteamVariant.NONE,
            windowOffset);

        addProgressBar(builder, progressSupplier, windowOffset);

        builder.widget(
            new DrawableWidget().setDrawable(ItemUtils.PICTURE_GTNL_STEAM_LOGO)
                .setSize(18, 18)
                .setPos(new Pos2d(130, 111).add(windowOffset)));

        for (Pair<IDrawable, Pair<Size, Pos2d>> specialTexture : uiProperties.specialTextures) {
            builder.widget(
                new DrawableWidget().setDrawable(specialTexture.getLeft())
                    .setSize(
                        specialTexture.getRight()
                            .getLeft())
                    .setPos(
                        specialTexture.getRight()
                            .getRight()
                            .add(windowOffset)));
        }

        return builder;
    }

    public static void forEachSlots(UIHelper.ForEachSlot forEachItemInputSlot,
        UIHelper.ForEachSlot forEachItemOutputSlot, UIHelper.ForEachSlot forEachSpecialSlot,
        UIHelper.ForEachSlot forEachFluidInputSlot, UIHelper.ForEachSlot forEachFluidOutputSlot,
        IDrawable itemSlotBackground, IDrawable fluidSlotBackground, BasicUIProperties uiProperties, int itemInputCount,
        int itemOutputCount, int fluidInputCount, int fluidOutputCount, SteamVariant steamVariant, Pos2d offset) {
        List<Pos2d> itemInputPositions = uiProperties.itemInputPositionsGetter.apply(itemInputCount)
            .stream()
            .map(p -> p.add(offset))
            .collect(Collectors.toList());
        for (int i = 0; i < itemInputPositions.size(); i++) {
            forEachItemInputSlot.accept(
                i,
                getBackgroundsForSlot(itemSlotBackground, uiProperties, false, false, i, false, steamVariant),
                itemInputPositions.get(i));
        }

        List<Pos2d> itemOutputPositions = uiProperties.itemOutputPositionsGetter.apply(itemOutputCount)
            .stream()
            .map(p -> p.add(offset))
            .collect(Collectors.toList());
        for (int i = 0; i < itemOutputPositions.size(); i++) {
            forEachItemOutputSlot.accept(
                i,
                getBackgroundsForSlot(itemSlotBackground, uiProperties, false, true, i, false, steamVariant),
                itemOutputPositions.get(i));
        }

        List<Pos2d> fluidInputPositions = uiProperties.fluidInputPositionsGetter.apply(fluidInputCount)
            .stream()
            .map(p -> p.add(offset))
            .collect(Collectors.toList());
        for (int i = 0; i < fluidInputPositions.size(); i++) {
            forEachFluidInputSlot.accept(
                i,
                getBackgroundsForSlot(fluidSlotBackground, uiProperties, true, false, i, false, steamVariant),
                fluidInputPositions.get(i));
        }

        List<Pos2d> fluidOutputPositions = uiProperties.fluidOutputPositionsGetter.apply(fluidOutputCount)
            .stream()
            .map(p -> p.add(offset))
            .collect(Collectors.toList());
        for (int i = 0; i < fluidOutputPositions.size(); i++) {
            forEachFluidOutputSlot.accept(
                i,
                getBackgroundsForSlot(fluidSlotBackground, uiProperties, true, true, i, false, steamVariant),
                fluidOutputPositions.get(i));
        }
    }

    public static IDrawable[] getBackgroundsForSlot(IDrawable base, BasicUIProperties uiProperties, boolean isFluid,
        boolean isOutput, int index, boolean isSpecial, SteamVariant steamVariant) {
        IDrawable overlay = getOverlay(uiProperties, isFluid, isOutput, index, isSpecial, steamVariant);
        if (overlay != null) {
            return new IDrawable[] { base, overlay };
        } else {
            return new IDrawable[] { base };
        }
    }

    @Nullable
    public static IDrawable getOverlay(BasicUIProperties uiProperties, boolean isFluid, boolean isOutput, int index,
        boolean isSpecial, SteamVariant steamVariant) {
        if (isSpecial && !uiProperties.useSpecialSlot) {
            return null;
        }
        if (steamVariant != SteamVariant.NONE) {
            SteamTexture steamTexture = uiProperties.getOverlayForSlotSteam(index, isFluid, isOutput, isSpecial);
            if (steamTexture != null) {
                return steamTexture.get(steamVariant);
            } else {
                return null;
            }
        } else {
            return uiProperties.getOverlayForSlot(index, isFluid, isOutput, isSpecial);
        }
    }

}
