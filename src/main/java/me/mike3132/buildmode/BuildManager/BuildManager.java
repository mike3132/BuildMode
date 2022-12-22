package me.mike3132.buildmode.BuildManager;

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
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class BuildManager {

    public static void onEnabled(Player player, String string) {
        if (!Main.plugin.getConfig().getStringList("WhiteilstedWorlds").contains(player.getWorld().getName())) {
            ChatMessage.sendMessage(player, "Build-Mode-Not-In-World");
            return;
        }

        BuildBossBar.getBar().addPlayer(player);
        InventoryConfigHandler.saveInventory(player);
        player.setGameMode(GameMode.CREATIVE);
        player.getInventory().clear();
        ChatMessage.sendMessage(player, "Build-Mode-Enabled");
        player.sendTitle(Main.chatColor("&dHave fun building"), Main.chatColor("&dEnjoy Creative"), 10, 30, 10);
        BuildSet.addBuildingPlayers(player.getUniqueId());
        for (String command : Main.plugin.getConfig().getStringList("Build-Mode-" + string + "-Enabled-Commands")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(player, command));
        }
    }

    public static void onDisabled(Player player, String string) {
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
        for (String command : Main.plugin.getConfig().getStringList("Build-Mode-"+ string + "-Disabled-Commands")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(player, command));
        }
    }

    public static void buildModeRunnable(Player player, String string) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (BuildSet.getBuildingPlayers().contains(player.getUniqueId())) {
                    String message = Main.chatColor("&2Use &a/build " + string + " &2to exit");
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously(Main.plugin, 0L, 40L);
    }
}
