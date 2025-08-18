package com.science.gtnl.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent;

import org.lwjgl.input.Mouse;

import com.cleanroommc.modularui.api.event.MouseInputEvent;
import com.glodblock.github.client.gui.GuiItemMonitor;
import com.gtnewhorizons.modularui.api.KeyboardUtil;
import com.science.gtnl.ScienceNotLeisure;
import com.science.gtnl.Utils.ClientUtils;
import com.science.gtnl.common.packet.KeyBindingHandler;

import appeng.client.gui.implementations.GuiMEMonitorable;
import codechicken.nei.BookmarkPanel;
import codechicken.nei.LayoutManager;
import codechicken.nei.Widget;
import codechicken.nei.bookmark.BookmarksGridSlot;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GTNLInputHandler {

    public static GTNLInputHandler INSTANCE = new GTNLInputHandler();
    private static final Map<String, BooleanSupplier> keys;
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static int tick = 0;
    public static int counter = 0;
    private static int counter1 = 0;
    public static GuiScreen oldGui = null;
    public static final List<Runnable> list = new ArrayList<>();

    private GTNLInputHandler() {
        FMLCommonHandler.instance()
            .bus()
            .register(this);
    }

    // Mouse.isButtonDown(2) = 按下鼠标中键
    static {
        Map<String, BooleanSupplier> map = new HashMap<>();

        map.put("RetrieveItem", () -> KeyboardUtil.isCtrlKeyDown() && Mouse.isButtonDown(2));
        map.put("StartCraft", () -> KeyboardUtil.isAltKeyDown() && Mouse.isButtonDown(2));

        // noinspection Java9CollectionFactory
        keys = Collections.unmodifiableMap(map);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void r$onGuiMouseEvent(MouseInputEvent.Pre event) {
        if (work(event)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (tick > 0) {
            tick--;
        }
        counter = (counter + ((++counter1 & 1) == 0 ? 1 : 0)) % 14;
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onInputEvent(final InputEvent.KeyInputEvent event) {
        if (!mc.thePlayer.capabilities.isCreativeMode && tick == 0 && mc.gameSettings.keyBindPickBlock.isPressed()) {
            EntityClientPlayerMP player = mc.thePlayer;
            World world = player.worldObj;
            ClientUtils.onBeforePickBlock(player, world, true);
            tick = 10;
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onInputEvent(final InputEvent.MouseInputEvent event) {
        if (!mc.thePlayer.capabilities.isCreativeMode && tick == 0 && mc.gameSettings.keyBindPickBlock.isPressed()) {
            EntityClientPlayerMP player = mc.thePlayer;
            World world = player.worldObj;
            ClientUtils.onBeforePickBlock(player, world, true);
            tick = 10;
        }
    }

    private boolean work(GuiScreenEvent event) {
        for (Map.Entry<String, BooleanSupplier> key : keys.entrySet()) {
            if (!key.getValue()
                .getAsBoolean()) continue;
            var mouse = new MouseHelper(Minecraft.getMinecraft());
            final int x = mouse.getX();
            final int y = mouse.getY();
            final Widget focused = LayoutManager.instance()
                .getWidgetUnderMouse(x, y);

            if (!(focused instanceof BookmarkPanel bp)) return false;

            BookmarksGridSlot ing = bp.getSlotMouseOver(x, y);
            if (ing == null) return false;

            ItemStack item = ing.getItemStack();
            if (item == null) return false;
            final var oldGui = Minecraft.getMinecraft().currentScreen;
            ScienceNotLeisure.network.sendToServer(
                new KeyBindingHandler(
                    key.getKey(),
                    item,
                    oldGui instanceof GuiMEMonitorable || oldGui instanceof GuiItemMonitor));
            if (key.getKey()
                .equals("StartCraft")) {
                GTNLInputHandler.oldGui = oldGui;
            }
            return true;
        }

        return false;
    }

    private static class MouseHelper {

        private final Minecraft mc;
        private final ScaledResolution scaledresolution;

        private MouseHelper(Minecraft mc) {
            this.mc = mc;
            this.scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        }

        private int getX() {
            int i = scaledresolution.getScaledWidth();
            return Mouse.getX() * i / this.mc.displayWidth;
        }

        private int getY() {
            int j = scaledresolution.getScaledHeight();
            return j - Mouse.getY() * j / this.mc.displayHeight - 1;
        }
    }

}
