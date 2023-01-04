package me.mike3132.buildmode.CommandManager;

import me.mike3132.buildmode.BuildManager.BuildManager;
import me.mike3132.buildmode.ChatManager.ChatMessage;
import me.mike3132.buildmode.ItemManager.*;
import me.mike3132.buildmode.Main;
import me.mike3132.buildmode.SetManager.BuildSet;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;



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
                if (!BuildSet.getBuildingPlayers("Jr").contains(player.getUniqueId()) &&
                        !BuildSet.getBuildingPlayers("Sr").contains(player.getUniqueId())) {
                    ChatMessage.sendMessage(player, "Build-Mode-Creative-Inventory-Not-Active");
                    return true;
                }
                WorldEditWand worldEditWand = new WorldEditWand();
                ItemStack wand = worldEditWand.getWand();
                ItemStack clock = FullBrightClock.getClock();
                TeleportCompass teleportCompass = new TeleportCompass();
                ItemStack compass = teleportCompass.getCompass();
                LightItem lightItem = new LightItem();
                ItemStack light = lightItem.getLight();
                DebugStickItem debugStickItem = new DebugStickItem();
                ItemStack stick = debugStickItem.getStick();

                player.getInventory().setItem(0, wand);
                player.getInventory().setItem(1, light);
                player.getInventory().setItem(4, clock);
                player.getInventory().setItem(7, stick);
                player.getInventory().setItem(8, compass);
                break;
            default:
                ChatMessage.sendMessage(player, "Build-Random-Args");
                break;
        }
        return false;
    }
}
