package me.mike3132.buildmode.ConfigManager;

import me.mike3132.buildmode.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public enum ConfigCreator {
    INVENTORIES, MESSAGES;


    public File getFile() {
        return new File(Main.plugin.getDataFolder(), this.toString().toLowerCase(Locale.ROOT) + ".yml");
    }

    public FileConfiguration get() {
        return YamlConfiguration.loadConfiguration(getFile());
    }

    public void save(FileConfiguration configuration) {
        try {
            configuration.save(getFile());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void create() {
        Main.plugin.saveResource(this.toString().toLowerCase(Locale.ROOT) + ".yml", false);
    }
}
