package com.science.gtnl.Utils.gui.recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import com.gtnewhorizons.modularui.api.ModularUITextures;
import com.gtnewhorizons.modularui.api.drawable.IDrawable;
import com.gtnewhorizons.modularui.api.forge.IItemHandlerModifiable;
import com.gtnewhorizons.modularui.api.math.Pos2d;
import com.gtnewhorizons.modularui.api.math.Size;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.common.widget.DrawableWidget;
import com.gtnewhorizons.modularui.common.widget.SlotWidget;
import com.science.gtnl.Utils.item.ItemUtils;
import com.science.gtnl.config.MainConfig;

import codechicken.nei.PositionedStack;
import gregtech.api.enums.SteamVariant;
import gregtech.api.recipe.BasicUIPropertiesBuilder;
import gregtech.api.recipe.NEIRecipePropertiesBuilder;
import gregtech.api.recipe.RecipeMapFrontend;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.MethodsReturnNonnullByDefault;
import gregtech.common.gui.modularui.UIHelper;
import gregtech.nei.GTNEIDefaultHandler;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RocketAssemblerFrontend extends RecipeMapFrontend {

    public static Object2IntOpenHashMap<GTRecipe> initializedRecipes = new Object2IntOpenHashMap<>();

    public RocketAssemblerFrontend(BasicUIPropertiesBuilder uiPropertiesBuilder,
        NEIRecipePropertiesBuilder neiPropertiesBuilder) {
        super(
            uiPropertiesBuilder.progressBarPos(new Pos2d(111, 80)),
            neiPropertiesBuilder.recipeBackgroundSize(new Size(170, 170)));
    }

    @Override
    public void addGregTechLogo(ModularWindow.Builder builder, Pos2d windowOffset) {
        builder.widget(
            new DrawableWidget().setDrawable(ItemUtils.PICTURE_GTNL_STEAM_LOGO)
                .setSize(18, 18)
                .setPos(new Pos2d(126, 116).add(windowOffset)));
    }

    @Override
    public List<Pos2d> getItemInputPositions(int itemInputCount) {
        return new ArrayList<>();
    }

    @Override
    public List<Pos2d> getItemOutputPositions(int itemOutputCount) {
        return Collections.singletonList(new Pos2d(-1000, -1000));
    }

    public static final int[][] rocketT1Inputs = new int[][] { { 121, 21 }, // 1 Lander
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

    public static final int[] rocketT1Output = new int[] { 121, 80 };

    @Override
    public void drawNEIOverlays(GTNEIDefaultHandler.CachedDefaultRecipe neiCachedRecipe) {
        final GTRecipe recipe = neiCachedRecipe.mRecipe;
        ItemStack[] inputs = recipe.mInputs;
        ItemStack output = recipe.mOutputs[0];
        if (inputs == null || output == null) return;

        int[][] selectedInputs = null;
        int[] selectedOutput = null;
        int tier = 0;

        switch (inputs.length) {
            case 20, 21 -> {
                selectedInputs = rocketT1Inputs;
                selectedOutput = rocketT1Output;
                tier = 1;
            }
            default -> {}
        }

        if (selectedInputs == null || selectedOutput == null) {
            super.drawNEIOverlays(neiCachedRecipe);
            return;
        }

        if (!initializedRecipes.containsKey(recipe)) {

            for (int i = 0; i < inputs.length; i++) {
                ItemStack stack = inputs[i];
                if (stack == null) continue;
                neiCachedRecipe.mInputs
                    .add(new PositionedStack(stack, selectedInputs[i][0], selectedInputs[i][1], false));
            }

            neiCachedRecipe.mOutputs.add(new PositionedStack(output, selectedOutput[0], selectedOutput[1], false));

            initializedRecipes.put(recipe, tier);
        }

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        // GL11.glEnable(GL11.GL_ALPHA_TEST);
        Minecraft.getMinecraft()
            .getTextureManager()
            .bindTexture(ModularUITextures.ITEM_SLOT.location);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef(0f, 0f, MainConfig.test);

        for (int i = 0; i < inputs.length; i++) {
            Gui.func_146110_a(selectedInputs[i][0] - 1, selectedInputs[i][1] - 1, 0, 0, 18, 18, 18, 18);
        }
        Gui.func_146110_a(selectedOutput[0] - 1, selectedOutput[1] - 1, 0, 0, 18, 18, 18, 18);

        GL11.glDisable(GL11.GL_BLEND);
        // GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glPopMatrix();

        super.drawNEIOverlays(neiCachedRecipe);
    }

    @Override
    public ModularWindow.Builder createNEITemplate(IItemHandlerModifiable itemInputsInventory,
        IItemHandlerModifiable itemOutputsInventory, IItemHandlerModifiable specialSlotInventory,
        IItemHandlerModifiable fluidInputsInventory, IItemHandlerModifiable fluidOutputsInventory,
        Supplier<Float> progressSupplier, Pos2d windowOffset) {
        // Override regular createNEITemplate method, so we can remove the background texture with the ugly border.
        ModularWindow.Builder builder = ModularWindow.builder(neiProperties.recipeBackgroundSize);

        // First draw progress bar in background
        if (uiProperties.useProgressBar) {
            addProgressBar(builder, progressSupplier, windowOffset);
        }

        UIHelper.forEachSlots(
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

        addGregTechLogo(builder, windowOffset);

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
}
