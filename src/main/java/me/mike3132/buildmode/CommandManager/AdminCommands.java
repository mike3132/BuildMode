package me.mike3132.buildmode.CommandManager;

import me.mike3132.buildmode.ChatManager.ChatMessage;
import me.mike3132.buildmode.Main;
import me.mike3132.buildmode.SetManager.BuildSet;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AdminCommands implements CommandExecutor {

    public AdminCommands() {
        Main.plugin.getCommand("BmAdmin").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.chatColor("Only players can use this command"));
            return false;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("BuildMode.Admin")) {
            ChatMessage.sendMessage(player, "Build-Mode-Admin-No-Permission");
            return true;
        }
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("Reload")) {
                player.sendMessage(Main.chatColor("&dBuild &bMode &7> &aPlugin config reloaded in &2" + String.valueOf(System.currentTimeMillis() - 1) + " &ams"));
                Main.plugin.reloadConfig();
                return false;
            }
            if (args[0].equalsIgnoreCase("List")) {
                if (BuildSet.getBuildingPlayers("Jr").isEmpty() && BuildSet.getBuildingPlayers("Sr").isEmpty()) {
                    ChatMessage.sendMessage(player, "Build-Mode-Admin-No-Builders");
                    return false;
                }
                player.sendMessage(Main.chatColor("&dBuild &bMode &7> &dCurrent players in build mode"));

                for (UUID uuid : BuildSet.getBuildingPlayers("Jr")) {
                    String playerName = Bukkit.getPlayer(uuid).getName();
                    player.sendMessage(Main.chatColor("&3" + playerName));
                }
                for (UUID uuid : BuildSet.getBuildingPlayers("Sr")) {
                    String playerName = Bukkit.getPlayer(uuid).getName();
                    player.sendMessage(Main.chatColor("&3" + playerName));
                }

            }
            if (args[0].equalsIgnoreCase("Help")) {
                ChatMessage.sendMessage(player, "Help-Header");
                ChatMessage.sendMessage(player, "Help-A");
                ChatMessage.sendMessage(player, "Help-B");
                ChatMessage.sendMessage(player, "Help-C");
                ChatMessage.sendMessage(player, "Help-D");
                ChatMessage.sendMessage(player, "Help-Footer");
                return false;
            }
        } else {
            ChatMessage.sendMessage(player, "Help-Header");
            ChatMessage.sendMessage(player, "Help-A");
            ChatMessage.sendMessage(player, "Help-B");
            ChatMessage.sendMessage(player, "Help-C");
            ChatMessage.sendMessage(player, "Help-D");
            ChatMessage.sendMessage(player, "Help-Footer");
        }

        return false;
    }
}
