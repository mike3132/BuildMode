package me.mike3132.buildmode.BuildManager;

import me.clip.placeholderapi.PlaceholderAPI;
import me.mike3132.buildmode.BossBarManager.BuildBossBar;
import me.mike3132.buildmode.ConfigManager.InventoryConfigHandler;
import me.mike3132.buildmode.ItemManager.GiveManager;
import me.mike3132.buildmode.Main;
import me.mike3132.buildmode.MessageManager.ActionBarMessages;
import me.mike3132.buildmode.MessageManager.ChatMessage;
import me.mike3132.buildmode.MessageManager.TitleMessages;
import me.mike3132.buildmode.SetManager.BuildSet;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class BuildManager {

    static boolean bossBarEnabled = Main.plugin.getConfig().getBoolean("Boss-Bar-Enabled");


    public static void onEnabled(Player player, String string) {
        if (!Main.plugin.getConfig().getStringList("WhitelistedWorlds").contains(player.getWorld().getName()) && string.equalsIgnoreCase("Jr")) {
            ChatMessage.sendMessage(player, "Build-Mode-Not-In-World");
            return;
        }

        if (bossBarEnabled) {
            BuildBossBar.getBar().addPlayer(player);
        }

        InventoryConfigHandler.saveInventory(player);

        if (!player.hasPermission("BuildMode.Override")) {
            player.setGameMode(GameMode.CREATIVE);
            player.getInventory().clear();
            ChatMessage.sendMessage(player, "Build-Mode-Enabled-Chat-Message");
        }

        TitleMessages.sendTitle(player, "Build-Mode-Enabled-Title", "Build-Mode-Enabled-SubTitle");


        BuildSet.addBuildingPlayers(player.getUniqueId(), string);
        for (String command : Main.plugin.getConfig().getStringList("Build-Mode-" + string + "-Enabled-Commands")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(player, command));
        }

        GiveManager.worldEditWand(player);
        GiveManager.fullbrightClock(player);
        GiveManager.lightItem(player);
        GiveManager.debugStick(player);
        GiveManager.teleportCompass(player);
    }

    public static void onDisabled(Player player, String string) {
        BuildSet.removeBuildingPlayers(player.getUniqueId(), string);
        BuildBossBar.getBar().removePlayer(player);

        if (!player.hasPermission("BuildMode.Override")) {
            player.getInventory().clear();
        }
        InventoryConfigHandler.loadInventory(player);

        if (!player.hasPermission("BuildMode.Override")) {
            player.setGameMode(GameMode.SURVIVAL);
            ChatMessage.sendMessage(player, "Build-Mode-Disabled-Chat-Message");
        }
        TitleMessages.sendTitle(player, "Build-Mode-Disabled-Title", "Build-Mode-Disabled-SubTitle");


        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        for (String command : Main.plugin.getConfig().getStringList("Build-Mode-" + string + "-Disabled-Commands")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(player, command));
        }

        if (!player.hasPermission("BuildMode.Override")) {
            boolean teleportEnabled = Main.plugin.getConfig().getBoolean("Build-Mode-Disabled-Safety");
            if (!teleportEnabled) return;

            Location location = player.getLocation().clone().subtract(0, 1, 0);
            Block block = location.getBlock();
            if (!block.getType().isSolid()) {
                for (String command : Main.plugin.getConfig().getStringList("Build-Mode-Disabled-Safety-Commands")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(player, command));
                    ChatMessage.sendMessage(player, "Build-Mode-Disabled-Safety-Message");
                    break;
                }
            }
        }

    }

    public static void buildModeRunnable(Player player, String string) {
        new BukkitRunnable() {
            @Override
            public void run() {
                boolean actionBarMessageEnabled = Main.plugin.getConfig().getBoolean("Action-Bar-Message-Enabled");
                if (!actionBarMessageEnabled) return;
                if (BuildSet.getBuildingPlayers(string).contains(player.getUniqueId())) {
                    ActionBarMessages.sendActionBar(player, "Action-Bar-Message");
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously(Main.plugin, 0L, 40L);
    }
}
