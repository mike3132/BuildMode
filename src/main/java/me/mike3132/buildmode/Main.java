package me.mike3132.buildmode;

import me.mike3132.buildmode.CommandManager.BuildCommand;
import me.mike3132.buildmode.CommandManager.AdminCommands;
import me.mike3132.buildmode.ConfigManager.ConfigCreator;
import me.mike3132.buildmode.EventManager.BuildEvents;
import me.mike3132.buildmode.SetManager.BuildSet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Main extends JavaPlugin {

    public static Main plugin;

    public static String chatColor(String chatColor) {
        return ChatColor.translateAlternateColorCodes('&', chatColor);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        getServer().getConsoleSender().sendMessage(chatColor("&4Enabled"));

        // Event loader
        Bukkit.getPluginManager().registerEvents(new BuildEvents(), this);

        // Command loader
        registerBuildCommand();
        registerReloadCommand();

        // Config loader
        saveDefaultConfig();
        getConfig();
        ConfigCreator.INVENTORIES.create();
        ConfigCreator.MESSAGES.create();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (BuildSet.getBuildingPlayers().contains(player.getUniqueId())) {
                final ItemStack[] itemstacks = Objects.requireNonNull(ConfigCreator.INVENTORIES.get().getList(player.getUniqueId().toString())).stream().map(o -> (ItemStack) o).toArray(ItemStack[]::new);
                player.getInventory().setContents(itemstacks);
            }
        }
    }


    // Command register
    public void registerBuildCommand() {
        new BuildCommand();
    }
    public void registerReloadCommand() {
        new AdminCommands();
    }

}
