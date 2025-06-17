package com.science.gtnl.common.command;

import static com.science.gtnl.Utils.steam.SteamWirelessNetworkManager.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import gregtech.api.util.GTUtility;
import gregtech.common.misc.spaceprojects.SpaceProjectManager;

public class CommandSteamNetwork extends CommandBase {

    @Override
    public String getCommandName() {
        return "steam_network";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/steam_network <global_steam_add/set/join/display>";
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] ss) {
        List<String> l = new ArrayList<>();
        String test = ss.length == 0 ? "" : ss[0].trim();
        if (ss.length == 0 || ss.length == 1 && (test.isEmpty()
            || Stream.of("global_steam_add", "global_steam_set", "global_steam_join", "global_steam_display")
                .anyMatch(s -> s.startsWith(test)))) {
            Stream.of("global_steam_add", "global_steam_set", "global_steam_join", "global_steam_display")
                .filter(s -> test.isEmpty() || s.startsWith(test))
                .forEach(l::add);
        }
        return l;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] strings) {
        if (strings.length < 1) {
            getCommandUsage(sender);
            return;
        }
        switch (strings[0]) {
            case "global_steam_add" -> {
                String username = strings[1];
                String formatted_username = EnumChatFormatting.BLUE + username + EnumChatFormatting.RESET;
                UUID uuid = SpaceProjectManager.getPlayerUUIDFromName(username);

                String Steam_String = strings[2];

                // Usage is /gt global_energy_add username EU

                String EU_string_formatted = EnumChatFormatting.RED
                    + GTUtility.formatNumbers(new BigInteger(Steam_String))
                    + EnumChatFormatting.RESET;

                if (addSteamToGlobalSteamMap(uuid, new BigInteger(Steam_String))) sender.addChatMessage(
                    new ChatComponentText(
                        "Successfully added " + EU_string_formatted
                            + "Steam to the global steam network of "
                            + formatted_username
                            + "."));
                else sender.addChatMessage(
                    new ChatComponentText(
                        "Failed to add " + EU_string_formatted
                            + "Steam to the global steam map of "
                            + formatted_username
                            + ". Insufficient steam in network. "));

                sender.addChatMessage(
                    new ChatComponentText(
                        formatted_username + " currently has "
                            + EnumChatFormatting.RED
                            + GTUtility.formatNumbers(new BigInteger(getUserSteam(uuid).toString()))
                            + EnumChatFormatting.RESET
                            + "Steam in their network."));

            }
            case "global_steam_set" -> {

                // Usage is /gt global_energy_set username EU

                String username = strings[1];
                String formatted_username = EnumChatFormatting.BLUE + username + EnumChatFormatting.RESET;
                UUID uuid = SpaceProjectManager.getPlayerUUIDFromName(username);

                String Steam_String_0 = strings[2];

                if ((new BigInteger(Steam_String_0).compareTo(BigInteger.ZERO)) < 0) {
                    sender
                        .addChatMessage(new ChatComponentText("Cannot set a users steam network to a negative value."));
                    break;
                }

                setUserSteam(uuid, new BigInteger(Steam_String_0));

                sender.addChatMessage(
                    new ChatComponentText(
                        "Successfully set " + formatted_username
                            + "'s global steam network to "
                            + EnumChatFormatting.RED
                            + GTUtility.formatNumbers(new BigInteger(Steam_String_0))
                            + EnumChatFormatting.RESET
                            + "Steam."));

            }
            case "global_steam_join" -> {

                // Usage is /gt global_energy_join username_of_you username_to_join

                String usernameSubject = strings[1];
                String usernameTeam = strings[2];

                String formattedUsernameSubject = EnumChatFormatting.BLUE + usernameSubject + EnumChatFormatting.RESET;
                String formattedUsernameTeam = EnumChatFormatting.BLUE + usernameTeam + EnumChatFormatting.RESET;

                UUID uuidSubject = SpaceProjectManager.getPlayerUUIDFromName(usernameSubject);
                UUID uuidTeam = SpaceProjectManager.getLeader(SpaceProjectManager.getPlayerUUIDFromName(usernameTeam));

                if (uuidSubject.equals(uuidTeam)) {
                    // leave team
                    SpaceProjectManager.putInTeam(uuidSubject, uuidSubject);
                    sender.addChatMessage(
                        new ChatComponentText(
                            "User " + formattedUsernameSubject + " has rejoined their own global steam network."));
                    break;
                }

                // join other's team

                if (SpaceProjectManager.getLeader(uuidSubject)
                    .equals(SpaceProjectManager.getLeader(uuidTeam))) {
                    sender.addChatMessage(new ChatComponentText("They are already in the same network!"));
                    break;
                }

                SpaceProjectManager.putInTeam(uuidSubject, uuidTeam);

                sender.addChatMessage(
                    new ChatComponentText(
                        "Success! " + formattedUsernameSubject + " has joined " + formattedUsernameTeam + "."));
                sender.addChatMessage(
                    new ChatComponentText(
                        "To undo this simply join your own network again with /steam_network global_steam_join "
                            + formattedUsernameSubject
                            + " "
                            + formattedUsernameSubject
                            + "."));

            }
            case "global_steam_display" -> {

                // Usage is /gt global_energy_display username.

                String username = strings[1];
                String formatted_username = EnumChatFormatting.BLUE + username + EnumChatFormatting.RESET;
                UUID userUUID = SpaceProjectManager.getPlayerUUIDFromName(username);

                if (!SpaceProjectManager.isInTeam(userUUID)) {
                    sender.addChatMessage(
                        new ChatComponentText("User " + formatted_username + " has no global steam network."));
                    break;
                }
                UUID teamUUID = SpaceProjectManager.getLeader(userUUID);

                sender.addChatMessage(
                    new ChatComponentText(
                        "User " + formatted_username
                            + " has "
                            + EnumChatFormatting.RED
                            + GTUtility.formatNumbers(getUserSteam(userUUID))
                            + EnumChatFormatting.RESET
                            + "Steam in their network."));
                if (!userUUID.equals(teamUUID)) sender.addChatMessage(
                    new ChatComponentText(
                        "User " + formatted_username
                            + " is currently in network of "
                            + EnumChatFormatting.BLUE
                            + SpaceProjectManager.getPlayerNameFromUUID(teamUUID)
                            + EnumChatFormatting.RESET
                            + "."));

            }
            default -> sender
                .addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Invalid command/syntax detected."));
        }
    }
}
