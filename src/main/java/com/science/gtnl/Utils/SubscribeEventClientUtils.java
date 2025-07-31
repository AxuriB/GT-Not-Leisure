package com.science.gtnl.Utils;

import static com.science.gtnl.common.packet.ClientSoundHandler.PLAYING_SOUNDS;
import static com.science.gtnl.common.packet.ClientTitleDisplayHandler.*;
import static com.science.gtnl.common.render.PlayerDollRenderManagerClient.textureCache;
import static com.science.gtnl.common.render.tile.MeteorMinerMachineRender.visualStateMap;

import java.util.Map;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.WorldEvent;

import org.lwjgl.opengl.GL11;

import com.reavaritia.common.render.CustomEntityRenderer;
import com.science.gtnl.Utils.enums.Mods;
import com.science.gtnl.common.item.TimeStopManager;
import com.science.gtnl.common.packet.ClientTitleDisplayHandler;
import com.science.gtnl.config.MainConfig;
import com.science.gtnl.loader.EffectLoader;
import com.science.gtnl.mixins.early.Minecraft.AccessorGuiChat;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.client.ElectricJukeboxSound;

public class SubscribeEventClientUtils {

    private static final Random random = new Random();
    public static String haloNoiseIconTexture = Mods.ScienceNotLeisure.resourceDomain + ":halonoise";
    public static IIcon haloNoiseIcon;

    public static String cheatWrenchIconTexture = "nei:cheat_speical";
    public static IIcon cheatWrenchIcon;

    public static void registerAllIcons(net.minecraft.client.renderer.texture.IIconRegister ir) {
        haloNoiseIcon = ir.registerIcon(haloNoiseIconTexture);
        cheatWrenchIcon = ir.registerIcon(cheatWrenchIconTexture);
    }

    // Player
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientConnectedToServerEvent(FMLNetworkEvent.ClientConnectedToServerEvent aEvent) {
        PLAYING_SOUNDS.clear();
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.entity instanceof EntityPlayer) && MainConfig.enableDeathIncompleteMessage) return;
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.currentScreen instanceof GuiChat guiChat) {
            String chat = ((AccessorGuiChat) guiChat).getInputField()
                .getText();
            mc.thePlayer.sendChatMessage(chat + (chat.startsWith("/") ? "" : "-"));
            guiChat.onGuiClosed();
            mc.displayGuiScreen(null);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.thePlayer;

        if (player != null && !player.capabilities.isCreativeMode) {
            PotionEffect effect = player.getActivePotionEffect(EffectLoader.awe);

            if (effect != null && event.gui instanceof GuiInventory) {
                event.setCanceled(true);
                String[] messages = { "Awe_Cancel_02_01", "Awe_Cancel_02_02" };
                String message = messages[random.nextInt(messages.length)];
                ClientTitleDisplayHandler
                    .displayTitle(StatCollector.translateToLocal(message), 100, 0xFFFFFF, 3, 10, 20);
            }
        }
    }

    // Render
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre event) {
        if (event.map.getTextureType() == 1) {
            registerAllIcons(event.map);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        if (ticksRemaining > 0 && currentTitle != null && !currentTitle.isEmpty()) {
            GL11.glPushMatrix();
            Minecraft mc = Minecraft.getMinecraft();
            ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
            FontRenderer fr = mc.fontRenderer;

            int stringWidth = fr.getStringWidth(currentTitle);
            int stringHeight = 9;

            double scale = scaleText;
            int x = (res.getScaledWidth() - (int) (stringWidth * scale)) / 2;
            int y = (res.getScaledHeight() - (int) (stringHeight * scale)) / 2;

            if (StatCollector.canTranslate(currentTitle)) {
                currentTitle = StatCollector.translateToLocal(currentTitle);
            }

            int argb = getArgb();

            GL11.glTranslated(x, y, 0);
            GL11.glScaled(scale, scale, 1);

            fr.drawStringWithShadow(currentTitle, 0, 0, argb);

            GL11.glPopMatrix();

            ticksRemaining--;
        }
    }

    @SubscribeEvent
    public void onRenderPlayerPre(RenderLivingEvent.Pre event) {
        if (!(event.entity instanceof EntityPlayer player)) return;

        if (player.isPotionActive(EffectLoader.shimmering)) {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.4F);
        }
    }

    @SubscribeEvent
    public void onRenderPlayerPost(RenderLivingEvent.Post event) {
        if (!(event.entity instanceof EntityPlayer player)) return;

        if (player.isPotionActive(EffectLoader.shimmering)) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_BLEND);
        }
    }

    // Sound
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientTickEvent(TickEvent.ClientTickEvent aEvent) {
        if (aEvent.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayer player = mc.thePlayer;

            if (player != null && mc.theWorld != null && !PLAYING_SOUNDS.isEmpty()) {
                double playerX = player.posX;
                double playerY = player.posY;
                double playerZ = player.posZ;

                for (Map.Entry<String, ElectricJukeboxSound> entry : PLAYING_SOUNDS.entrySet()) {
                    ElectricJukeboxSound sound = entry.getValue();
                    sound.xPosition = (float) playerX;
                    sound.yPosition = (float) playerY;
                    sound.zPosition = (float) playerZ;
                }
            }
        }
    }

    // World
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        textureCache.clear();
        visualStateMap.clear();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        if (event.world.isRemote) {
            EntityRenderer renderer = Minecraft.getMinecraft().entityRenderer;
            if (renderer instanceof CustomEntityRenderer customEntityRenderer) {
                customEntityRenderer.resetShader();
            }
        }
        TimeStopManager.setTimeStopped(false);
    }
}
