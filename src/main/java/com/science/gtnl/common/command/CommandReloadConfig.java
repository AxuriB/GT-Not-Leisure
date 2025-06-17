package com.science.gtnl.common.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import com.science.gtnl.ScienceNotLeisure;
import com.science.gtnl.Utils.enums.Mods;
import com.science.gtnl.common.packet.ConfigSyncPacket;
import com.science.gtnl.config.MainConfig;

public class CommandReloadConfig extends CommandBase {

    @Override
    public String getCommandName() {
        return "gtnl_reloadconfig";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/gtnl_reloadconfig - reload GTNL config";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        try {
            MainConfig.reloadConfig();

            ConfigSyncPacket msg = new ConfigSyncPacket(new MainConfig());
            for (EntityPlayerMP player : MinecraftServer.getServer()
                .getConfigurationManager().playerEntityList) {
                ScienceNotLeisure.network.sendTo(msg, player);
            }

            if (MainConfig.enableDeleteRecipe) {
                sender.addChatMessage(
                    new ChatComponentTranslation("Welcome_GTNL_DeleteRecipe")
                        .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
            }

            if (MainConfig.enableDebugMode) {
                sender.addChatMessage(
                    new ChatComponentTranslation("Welcome_GTNL_Debug")
                        .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            }

            if (!Mods.Overpowered.isModLoaded() && MainConfig.enableRecipeOutputChance) {
                sender.addChatMessage(
                    new ChatComponentTranslation("Welcome_GTNL_RecipeOutputChance_00")
                        .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD)));

                sender.addChatMessage(
                    new ChatComponentTranslation(
                        "Welcome_GTNL_RecipeOutputChance_01",
                        MainConfig.recipeOutputChance + "%")
                            .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD)));
            }

            sender.addChatMessage(
                new ChatComponentTranslation("Welcome_GTNL_reload")
                    .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));

        } catch (Exception e) {
            sender.addChatMessage(new ChatComponentText("Error Reload GTNL config: " + e.getMessage()));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
}
