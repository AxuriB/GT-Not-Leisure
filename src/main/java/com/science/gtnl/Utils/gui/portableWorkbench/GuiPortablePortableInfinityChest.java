package com.science.gtnl.Utils.gui.portableWorkbench;

import java.util.Iterator;
import java.util.Objects;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import wanion.avaritiaddons.Avaritiaddons;
import wanion.avaritiaddons.client.RenderItemInfinity;
import wanion.avaritiaddons.network.InfinityChestClick;

public class GuiPortablePortableInfinityChest extends GuiPortableAvaritiaddonsChest {

    private static final RenderItemInfinity renderItemInfinity = new RenderItemInfinity();

    private Slot clickedSlot;
    private boolean isRightMouseClick;
    private GuiButton selectedButton;
    private ItemStack draggedStack;

    private int returningStackStartX;
    private int returningStackStartY;
    private Slot returningStackDestSlot;
    private long returningStackTime;
    private ItemStack returningStack;

    private Slot lastDragSlot;
    private long lastDragTime;
    private int dragMode;
    private int dragButton;
    private boolean dragInProgress;
    private int dragItemCount;

    private long lastClickTime;
    private Slot lastClickedSlot;
    private int lastClickButton;
    private boolean doubleClickFlag;
    private ItemStack doubleClickStack;

    public GuiPortablePortableInfinityChest(@Nonnull final ItemStack stack, final InventoryPlayer inventoryPlayer) {
        super(new ContainerPortableInfinityChest(stack, inventoryPlayer));
    }

    @Override
    protected @NotNull String getContainerName() {
        return "container.InfinityChest";
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTick) {
        drawDefaultBackground();
        drawGuiContainerBackgroundLayer(partialTick, mouseX, mouseY);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        for (GuiButton aButtonList : buttonList) aButtonList.drawButton(mc, mouseX, mouseY);
        for (GuiLabel aLabelList : labelList) aLabelList.func_146159_a(mc, mouseX, mouseY);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glPushMatrix();
        GL11.glTranslatef((float) guiLeft, (float) guiTop, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        Slot theSlot = null;
        short short1 = 240;
        short short2 = 240;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, short1, short2);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int k1;

        for (int i1 = 0; i1 < inventorySlots.inventorySlots.size(); ++i1) {
            Slot slot = inventorySlots.inventorySlots.get(i1);
            drawSlot(slot);

            if (isMouseOverSlot(slot, mouseX, mouseY) && slot.func_111238_b()) {
                theSlot = slot;
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                int j1 = slot.xDisplayPosition;
                k1 = slot.yDisplayPosition;
                GL11.glColorMask(true, true, true, false);
                drawGradientRect(j1, k1, j1 + 16, k1 + 16, -2130706433, -2130706433);
                GL11.glColorMask(true, true, true, true);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
            }
        }

        // Forge: Force lighting to be disabled as there are some issue where lighting would
        // incorrectly be applied based on items that are in the inventory.
        GL11.glDisable(GL11.GL_LIGHTING);
        drawGuiContainerForegroundLayer(mouseX, mouseY);
        GL11.glEnable(GL11.GL_LIGHTING);
        InventoryPlayer inventoryplayer = mc.thePlayer.inventory;
        ItemStack itemstack = draggedStack == null ? inventoryplayer.getItemStack() : draggedStack;

        if (itemstack != null) {
            byte b0 = 8;
            k1 = draggedStack == null ? 8 : 16;
            String s = null;

            if (draggedStack != null && isRightMouseClick) {
                itemstack = itemstack.copy();
                itemstack.stackSize = MathHelper.ceiling_float_int((float) itemstack.stackSize / 2.0F);
            } else if (field_147007_t && field_147008_s.size() > 1) {
                itemstack = itemstack.copy();
                itemstack.stackSize = dragItemCount;

                if (itemstack.stackSize == 0) {
                    s = EnumChatFormatting.YELLOW + "0";
                }
            }

            drawItemStack(itemstack, mouseX - guiLeft - b0, mouseY - guiTop - k1, s);
        }

        if (returningStack != null) {
            float f1 = (float) (Minecraft.getSystemTime() - returningStackTime) / 100.0F;

            if (f1 >= 1.0F) {
                f1 = 1.0F;
                returningStack = null;
            }

            k1 = returningStackDestSlot.xDisplayPosition - returningStackStartX;
            int j2 = returningStackDestSlot.yDisplayPosition - returningStackStartY;
            int l1 = returningStackStartX + (int) ((float) k1 * f1);
            int i2 = returningStackStartY + (int) ((float) j2 * f1);
            drawItemStack(returningStack, l1, i2, null);
        }

        GL11.glPopMatrix();

        if (inventoryplayer.getItemStack() == null && theSlot != null && theSlot.getHasStack()) {
            ItemStack itemstack1 = theSlot.getStack();
            renderToolTip(itemstack1, mouseX, mouseY);
        }

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
    }

    private void drawItemStack(ItemStack p_146982_1_, int p_146982_2_, int p_146982_3_, String p_146982_4_) {
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        zLevel = 200.0F;
        renderItemInfinity.zLevel = 200.0F;
        FontRenderer font = null;
        if (p_146982_1_ != null) font = Objects.requireNonNull(p_146982_1_.getItem())
            .getFontRenderer(p_146982_1_);
        if (font == null) font = fontRendererObj;
        renderItemInfinity
            .renderItemAndEffectIntoGUI(font, mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_);
        renderItemInfinity.renderItemOverlayIntoGUI(
            font,
            mc.getTextureManager(),
            p_146982_1_,
            p_146982_2_,
            p_146982_3_ - (draggedStack == null ? 0 : 8),
            p_146982_4_);
        zLevel = 0.0F;
        renderItemInfinity.zLevel = 0.0F;
    }

    private void drawSlot(final Slot slot) {
        int i = slot.xDisplayPosition;
        int j = slot.yDisplayPosition;
        ItemStack itemstack = slot.getStack();
        boolean flag = false;
        boolean flag1 = slot == clickedSlot && draggedStack != null && !isRightMouseClick;
        ItemStack itemstack1 = mc.thePlayer.inventory.getItemStack();
        String s = null;

        if (slot == clickedSlot && draggedStack != null && isRightMouseClick && itemstack != null) {
            itemstack = itemstack.copy();
            itemstack.stackSize /= 2;
        } else if (field_147007_t && field_147008_s.contains(slot) && itemstack1 != null) {
            if (field_147008_s.size() == 1) {
                return;
            }

            if (Container.func_94527_a(slot, itemstack1, true) && inventorySlots.canDragIntoSlot(slot)) {
                itemstack = itemstack1.copy();
                flag = true;
                Container.func_94525_a(
                    field_147008_s,
                    dragMode,
                    itemstack,
                    slot.getStack() == null ? 0 : slot.getStack().stackSize);

                if (itemstack.stackSize > itemstack.getMaxStackSize()) {
                    s = EnumChatFormatting.YELLOW + "" + itemstack.getMaxStackSize();
                    itemstack.stackSize = itemstack.getMaxStackSize();
                }

                if (itemstack.stackSize > slot.getSlotStackLimit()) {
                    s = EnumChatFormatting.YELLOW + "" + slot.getSlotStackLimit();
                    itemstack.stackSize = slot.getSlotStackLimit();
                }
            } else {
                field_147008_s.remove(slot);
                func_146980_g();
            }
        }

        zLevel = 100.0F;
        renderItemInfinity.zLevel = 100.0F;

        if (itemstack == null) {
            IIcon iicon = slot.getBackgroundIconIndex();

            if (iicon != null) {
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_BLEND); // Forge: Blending needs to be enabled for
                mc.getTextureManager()
                    .bindTexture(TextureMap.locationItemsTexture);
                drawTexturedModelRectFromIcon(i, j, iicon, 16, 16);
                GL11.glDisable(GL11.GL_BLEND); // Forge: And clean that up
                GL11.glEnable(GL11.GL_LIGHTING);
                flag1 = true;
            }
        }

        if (!flag1) {
            if (flag) {
                drawRect(i, j, i + 16, j + 16, -2130706433);
            }

            GL11.glEnable(GL11.GL_DEPTH_TEST);
            renderItemInfinity.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), itemstack, i, j);
            renderItemInfinity.renderItemOverlayIntoGUI(fontRendererObj, mc.getTextureManager(), itemstack, i, j, s);
        }
        renderItemInfinity.zLevel = 0.0F;
        zLevel = 0.0F;
    }

    private void func_146980_g() {
        ItemStack itemstack = mc.thePlayer.inventory.getItemStack();

        if (itemstack != null && field_147007_t) {
            dragItemCount = itemstack.stackSize;
            ItemStack itemstack1;
            int i;

            for (Iterator<Slot> iterator = field_147008_s.iterator(); iterator
                .hasNext(); dragItemCount -= itemstack1.stackSize - i) {
                Slot slot = iterator.next();
                itemstack1 = itemstack.copy();
                i = slot.getStack() == null ? 0 : slot.getStack().stackSize;
                Container.func_94525_a(field_147008_s, dragMode, itemstack1, i);

                if (itemstack1.stackSize > itemstack1.getMaxStackSize())
                    itemstack1.stackSize = itemstack1.getMaxStackSize();

                if (itemstack1.stackSize > slot.getSlotStackLimit()) itemstack1.stackSize = slot.getSlotStackLimit();
            }
        }
    }

    /**
     * Returns the slot at the given coordinates or null if there is none.
     */
    private Slot getSlotAtPosition(int p_146975_1_, int p_146975_2_) {
        for (int k = 0; k < inventorySlots.inventorySlots.size(); ++k) {
            Slot slot = inventorySlots.inventorySlots.get(k);

            if (isMouseOverSlot(slot, p_146975_1_, p_146975_2_)) {
                return slot;
            }
        }

        return null;
    }

    /**
     * Called when the mouse is clicked.
     */
    @Override
    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
        if (p_73864_3_ == 0) {
            for (int l = 0; l < this.buttonList.size(); ++l) {
                GuiButton guibutton = this.buttonList.get(l);

                if (guibutton.mousePressed(this.mc, p_73864_1_, p_73864_2_)) {
                    GuiScreenEvent.ActionPerformedEvent.Pre event = new GuiScreenEvent.ActionPerformedEvent.Pre(
                        this,
                        guibutton,
                        this.buttonList);
                    if (MinecraftForge.EVENT_BUS.post(event)) break;
                    this.selectedButton = event.button;
                    event.button.func_146113_a(this.mc.getSoundHandler());
                    this.actionPerformed(event.button);
                    if (this.equals(this.mc.currentScreen)) MinecraftForge.EVENT_BUS
                        .post(new GuiScreenEvent.ActionPerformedEvent.Post(this, event.button, this.buttonList));
                }
            }
        }
        boolean flag = p_73864_3_ == mc.gameSettings.keyBindPickBlock.getKeyCode() + 100;
        Slot slot = getSlotAtPosition(p_73864_1_, p_73864_2_);
        long l = Minecraft.getSystemTime();
        doubleClickFlag = lastClickedSlot == slot && l - lastClickTime < 250L && lastClickButton == p_73864_3_;
        dragInProgress = false;

        if (p_73864_3_ == 0 || p_73864_3_ == 1 || flag) {
            int i1 = guiLeft;
            int j1 = guiTop;
            boolean flag1 = p_73864_1_ < i1 || p_73864_2_ < j1 || p_73864_1_ >= i1 + xSize || p_73864_2_ >= j1 + ySize;
            int k1 = -1;

            if (slot != null) {
                k1 = slot.slotNumber;
            }

            if (flag1) {
                k1 = -999;
            }

            if (mc.gameSettings.touchscreen && flag1 && mc.thePlayer.inventory.getItemStack() == null) {
                mc.displayGuiScreen(null);
                return;
            }

            if (k1 != -1) {
                if (mc.gameSettings.touchscreen) {
                    if (slot != null && slot.getHasStack()) {
                        clickedSlot = slot;
                        draggedStack = null;
                        isRightMouseClick = p_73864_3_ == 1;
                    } else {
                        clickedSlot = null;
                    }
                } else if (!field_147007_t) {
                    if (mc.thePlayer.inventory.getItemStack() == null) {
                        if (p_73864_3_ == mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                            handleMouseClick(slot, k1, p_73864_3_, 3);
                        } else {
                            boolean flag2 = k1 != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
                            byte b0 = 0;

                            if (flag2) {
                                doubleClickStack = slot.getHasStack() ? slot.getStack() : null;
                                b0 = 1;
                            } else if (k1 == -999) {
                                b0 = 4;
                            }

                            handleMouseClick(slot, k1, p_73864_3_, b0);
                        }

                        dragInProgress = true;
                    } else {
                        field_147007_t = true;
                        dragButton = p_73864_3_;
                        field_147008_s.clear();

                        if (p_73864_3_ == 0) {
                            dragMode = 0;
                        } else if (p_73864_3_ == 1) {
                            dragMode = 1;
                        }
                    }
                }
            }
        }

        lastClickedSlot = slot;
        lastClickTime = l;
        lastClickButton = p_73864_3_;
    }

    /**
     * Called when a mouse button is pressed and the mouse is moved around. Parameters are : mouseX, mouseY,
     * lastButtonClicked & timeSinceMouseClick.
     */
    @Override
    protected void mouseClickMove(int p_146273_1_, int p_146273_2_, int p_146273_3_, long p_146273_4_) {
        Slot slot = getSlotAtPosition(p_146273_1_, p_146273_2_);
        ItemStack itemstack = mc.thePlayer.inventory.getItemStack();

        if (clickedSlot != null && mc.gameSettings.touchscreen) {
            if (p_146273_3_ == 0 || p_146273_3_ == 1) {
                if (draggedStack == null) {
                    if (slot != clickedSlot) {
                        draggedStack = clickedSlot.getStack()
                            .copy();
                    }
                } else if (draggedStack.stackSize > 1 && slot != null
                    && Container.func_94527_a(slot, draggedStack, false)) {
                        long i1 = Minecraft.getSystemTime();

                        if (lastDragSlot == slot) {
                            if (i1 - lastDragTime > 500L) {
                                handleMouseClick(clickedSlot, clickedSlot.slotNumber, 0, 0);
                                handleMouseClick(slot, slot.slotNumber, 1, 0);
                                handleMouseClick(clickedSlot, clickedSlot.slotNumber, 0, 0);
                                lastDragTime = i1 + 750L;
                                --draggedStack.stackSize;
                            }
                        } else {
                            lastDragSlot = slot;
                            lastDragTime = i1;
                        }
                    }
            }
        } else if (field_147007_t && slot != null
            && itemstack != null
            && itemstack.stackSize > field_147008_s.size()
            && Container.func_94527_a(slot, itemstack, true)
            && slot.isItemValid(itemstack)
            && inventorySlots.canDragIntoSlot(slot)) {
                field_147008_s.add(slot);
                func_146980_g();
            }
    }

    @Override
    protected void handleMouseClick(final Slot slot, int slotNumber, final int mouseButton, final int modifier) {
        if (slot != null) slotNumber = slot.slotNumber;
        final short nextTransactionID = mc.thePlayer.openContainer.getNextTransactionID(mc.thePlayer.inventory);
        final ItemStack itemStack = mc.thePlayer.openContainer
            .slotClick(slotNumber, mouseButton, modifier, mc.thePlayer);
        Avaritiaddons.networkWrapper.sendToServer(
            new InfinityChestClick(
                inventorySlots.windowId,
                slotNumber,
                mouseButton,
                modifier,
                itemStack,
                nextTransactionID));
    }

    @Override
    protected void mouseMovedOrUp(int p_146286_1_, int p_146286_2_, int p_146286_3_) {
        if (selectedButton != null && p_146286_3_ == 0) {
            selectedButton.mouseReleased(p_146286_1_, p_146286_2_);
            selectedButton = null;
        }
        Slot slot = getSlotAtPosition(p_146286_1_, p_146286_2_);
        int l = guiLeft;
        int i1 = guiTop;
        boolean flag = p_146286_1_ < l || p_146286_2_ < i1 || p_146286_1_ >= l + xSize || p_146286_2_ >= i1 + ySize;
        int j1 = -1;

        if (slot != null) {
            j1 = slot.slotNumber;
        }

        if (flag) {
            j1 = -999;
        }

        Slot slot1;
        Iterator<Slot> iterator;

        if (doubleClickFlag && slot != null && p_146286_3_ == 0 && inventorySlots.func_94530_a(null, slot)) {
            if (isShiftKeyDown()) {
                if (slot.inventory != null && doubleClickStack != null) {
                    iterator = inventorySlots.inventorySlots.iterator();

                    while (iterator.hasNext()) {
                        slot1 = iterator.next();

                        if (slot1 != null && slot1.canTakeStack(mc.thePlayer)
                            && slot1.getHasStack()
                            && slot1.inventory == slot.inventory
                            && Container.func_94527_a(slot1, doubleClickStack, true)) {
                            handleMouseClick(slot1, slot1.slotNumber, p_146286_3_, 1);
                        }
                    }
                }
            } else {
                handleMouseClick(slot, j1, p_146286_3_, 6);
            }

            doubleClickFlag = false;
            lastClickTime = 0L;
        } else {
            if (field_147007_t && dragButton != p_146286_3_) {
                field_147007_t = false;
                field_147008_s.clear();
                dragInProgress = true;
                return;
            }

            if (dragInProgress) {
                dragInProgress = false;
                return;
            }

            boolean flag1;

            if (clickedSlot != null && mc.gameSettings.touchscreen) {
                if (p_146286_3_ == 0 || p_146286_3_ == 1) {
                    if (draggedStack == null && slot != clickedSlot) {
                        draggedStack = clickedSlot.getStack();
                    }

                    flag1 = Container.func_94527_a(slot, draggedStack, false);

                    if (j1 != -1 && draggedStack != null && flag1) {
                        handleMouseClick(clickedSlot, clickedSlot.slotNumber, p_146286_3_, 0);
                        handleMouseClick(slot, j1, 0, 0);

                        if (mc.thePlayer.inventory.getItemStack() != null) {
                            handleMouseClick(clickedSlot, clickedSlot.slotNumber, p_146286_3_, 0);
                            returningStackStartX = p_146286_1_ - l;
                            returningStackStartY = p_146286_2_ - i1;
                            returningStackDestSlot = clickedSlot;
                            returningStack = draggedStack;
                            returningStackTime = Minecraft.getSystemTime();
                        } else {
                            returningStack = null;
                        }
                    } else if (draggedStack != null) {
                        returningStackStartX = p_146286_1_ - l;
                        returningStackStartY = p_146286_2_ - i1;
                        returningStackDestSlot = clickedSlot;
                        returningStack = draggedStack;
                        returningStackTime = Minecraft.getSystemTime();
                    }

                    draggedStack = null;
                    clickedSlot = null;
                }
            } else if (field_147007_t && !field_147008_s.isEmpty()) {
                handleMouseClick(null, -999, Container.func_94534_d(0, dragMode), 5);
                iterator = field_147008_s.iterator();

                while (iterator.hasNext()) {
                    slot1 = iterator.next();
                    handleMouseClick(slot1, slot1.slotNumber, Container.func_94534_d(1, dragMode), 5);
                }

                handleMouseClick(null, -999, Container.func_94534_d(2, dragMode), 5);
            } else if (mc.thePlayer.inventory.getItemStack() != null) {
                if (p_146286_3_ == mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                    handleMouseClick(slot, j1, p_146286_3_, 3);
                } else {
                    flag1 = j1 != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));

                    if (flag1) {
                        doubleClickStack = slot != null && slot.getHasStack() ? slot.getStack() : null;
                    }

                    handleMouseClick(slot, j1, p_146286_3_, flag1 ? 1 : 0);
                }
            }
        }

        if (mc.thePlayer.inventory.getItemStack() == null) {
            lastClickTime = 0L;
        }

        field_147007_t = false;
    }

    private boolean isMouseOverSlot(Slot p_146981_1_, int p_146981_2_, int p_146981_3_) {
        return func_146978_c(
            p_146981_1_.xDisplayPosition,
            p_146981_1_.yDisplayPosition,
            16,
            16,
            p_146981_2_,
            p_146981_3_);
    }

}
