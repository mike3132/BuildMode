package me.mike3132.buildmode.CommandManager;

import me.clip.placeholderapi.PlaceholderAPI;
import me.mike3132.buildmode.BossBarManager.BuildBossBar;
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
                    if (!player.getWorld().getName().equalsIgnoreCase("world")) {
                        ChatMessage.sendMessage(player, "Build-Mode-Not-In-World");
                        return true;
                    }
                    BuildBossBar.getBar().addPlayer(player);
                    InventoryConfigHandler.saveInventory(player);
                    player.setGameMode(GameMode.CREATIVE);
                    player.getInventory().clear();
                    ChatMessage.sendMessage(player, "Build-Mode-Enabled");
                    player.sendTitle(Main.chatColor("&dHave fun building"), Main.chatColor("&dEnjoy Creative"), 10, 30, 10);
                    BuildSet.addBuildingPlayers(player.getUniqueId());
                    for (String command : Main.plugin.getConfig().getStringList("Build-Mode-Jr-Enabled-Commands")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(player, command));
                    }

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (BuildSet.getBuildingPlayers().contains(player.getUniqueId())) {
                                String message = Main.chatColor("&2Use &a/build jr &2to exit");
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                            } else {
                                this.cancel();
                            }
                        }
                    }.runTaskTimerAsynchronously(Main.plugin, 0L, 40L);

                    return false;
                }
                BuildSet.removeBuildingPlayers(player.getUniqueId());
                BuildBossBar.getBar().removePlayer(player);
                player.getInventory().clear();
                InventoryConfigHandler.loadInventory(player);
                player.setGameMode(GameMode.SURVIVAL);
                ChatMessage.sendMessage(player, "Build-Mode-Disabled");
                player.sendTitle(Main.chatColor("&bThanks for building"), Main.chatColor("&dEnjoy Survival"), 10, 30, 10);
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                for (String command : Main.plugin.getConfig().getStringList("Build-Mode-Jr-Disabled-Commands")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(player, command));
                }
                break;
            case "SR":
                if (!player.hasPermission("BuildMode.Sr")) {
                    ChatMessage.sendMessage(player, "Build-Mode-sr-No-Perm");
                    return true;
                }
                if (!BuildSet.getBuildingPlayers().contains(player.getUniqueId())) {
                    BuildSet.addBuildingPlayers(player.getUniqueId());
                    for (String command : Main.plugin.getConfig().getStringList("Build-Mode-Sr-Enabled-Commands")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(player, command));
                    }
                    BuildBossBar.getBar().addPlayer(player);
                    InventoryConfigHandler.saveInventory(player);
                    player.setGameMode(GameMode.CREATIVE);
                    player.getInventory().clear();
                    ChatMessage.sendMessage(player, "Build-Mode-Enabled");
                    player.sendTitle(Main.chatColor("&dHave fun building"), Main.chatColor("&dEnjoy Creative"), 10, 30, 10);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (BuildSet.getBuildingPlayers().contains(player.getUniqueId())) {
                                String message = Main.chatColor("&2Use &a/build sr &2to exit");
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                            } else {
                                this.cancel();
                            }
                        }
                    }.runTaskTimerAsynchronously(Main.plugin, 0L, 40L);
                    return false;
                }
                BuildSet.removeBuildingPlayers(player.getUniqueId());
                BuildBossBar.getBar().removePlayer(player);
                player.getInventory().clear();
                InventoryConfigHandler.loadInventory(player);
                player.setGameMode(GameMode.SURVIVAL);
                ChatMessage.sendMessage(player, "Build-Mode-Disabled");
                player.sendTitle(Main.chatColor("&bThanks for building"), Main.chatColor("&dEnjoy Survival"), 10, 30, 10);
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                for (String command : Main.plugin.getConfig().getStringList("Build-Mode-Sr-Disabled-Commands")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(player, command));
                }
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
