package me.mike3132.buildmode.CommandManager;

import me.mike3132.buildmode.BuildManager.BuildManager;
import me.mike3132.buildmode.ItemManager.GiveManager;
import me.mike3132.buildmode.Main;
import me.mike3132.buildmode.MessageManager.ChatMessage;
import me.mike3132.buildmode.SetManager.BuildSet;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class BuildCommand implements CommandExecutor {

    public BuildCommand() {
        Main.plugin.getCommand("Build").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.chatColor("Only players can use this command"));
            return false;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            ChatMessage.sendMessage(player, "Build-Not-Enough-Args");
            return true;
        }

        switch (args[0].toUpperCase()) {
            case "JR":
                if (!player.hasPermission("BuildMode.Jr")) {
                    ChatMessage.sendMessage(player, "Build-Mode-jr-No-Perm");
                    return true;
                }
                if (!BuildSet.getBuildingPlayers("Jr").contains(player.getUniqueId())) {
                    BuildManager.onEnabled(player, "Jr");
                    BuildManager.buildModeRunnable(player, "Jr");
                    return false;
                }
                BuildManager.onDisabled(player, "Jr");
                break;
            case "SR":
                if (!player.hasPermission("BuildMode.Sr")) {
                    ChatMessage.sendMessage(player, "Build-Mode-sr-No-Perm");
                    return true;
                }
                if (!BuildSet.getBuildingPlayers("Sr").contains(player.getUniqueId())) {
                    BuildManager.onEnabled(player, "Sr");
                    BuildManager.buildModeRunnable(player, "Sr");
                    return false;
                }
                BuildManager.onDisabled(player, "Sr");
                break;
            case "CINV":
                if (player.hasPermission("BuildMode.Override")) {
                    GiveManager.worldEditWand(player);
                    GiveManager.fullbrightClock(player);
                    GiveManager.lightItem(player);
                    GiveManager.debugStick(player);
                    GiveManager.teleportCompass(player);
                    return false;
                }
                if (!BuildSet.getBuildingPlayers("Jr").contains(player.getUniqueId()) &&
                        !BuildSet.getBuildingPlayers("Sr").contains(player.getUniqueId())) {
                    ChatMessage.sendMessage(player, "Build-Mode-Creative-Inventory-Not-Active");
                    return true;
                }
                GiveManager.worldEditWand(player);
                GiveManager.fullbrightClock(player);
                GiveManager.lightItem(player);
                GiveManager.debugStick(player);
                GiveManager.teleportCompass(player);
                break;
            default:
                ChatMessage.sendMessage(player, "Build-Random-Args");
                break;
        }
        return false;
    }
}
