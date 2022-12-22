package me.mike3132.buildmode.CommandManager;

import me.clip.placeholderapi.PlaceholderAPI;
import me.mike3132.buildmode.BossBarManager.BuildBossBar;
import me.mike3132.buildmode.BuildManager.BuildManager;
import me.mike3132.buildmode.ChatManager.ChatMessage;
import me.mike3132.buildmode.ConfigManager.InventoryConfigHandler;
import me.mike3132.buildmode.Main;
import me.mike3132.buildmode.SetManager.BuildSet;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;


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
                if (!BuildSet.getBuildingPlayers().contains(player.getUniqueId())) {
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
                if (!BuildSet.getBuildingPlayers().contains(player.getUniqueId())) {
                    BuildManager.onEnabled(player, "Sr");
                    BuildManager.buildModeRunnable(player, "Sr");
                    return false;
                }
                BuildManager.onDisabled(player, "Sr");
                break;
            case "FULLBRIGHT":
                if (!BuildSet.getBuildingPlayers().contains(player.getUniqueId())) {
                    ChatMessage.sendMessage(player, "Build-Not-Active");
                    return false;
                }
                if (args.length == 1) {
                    ChatMessage.sendMessage(player, "Build-NightVision-Not-Enough-Args");
                    return false;
                }
                    switch (args[1].toUpperCase()) {
                        case "ON":
                            player.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(999999999, 255));
                            ChatMessage.sendMessage(player, "Build-NightVision-On");
                            break;
                        case "OFF":
                            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                            ChatMessage.sendMessage(player, "Build-NightVision-Off");
                            break;
                        default:
                            ChatMessage.sendMessage(player, "Build-NightVision-Random-Args");
                            break;

                }

                break;
            default:
                ChatMessage.sendMessage(player, "Build-Random-Args");
                break;
        }
        return false;
    }
}
