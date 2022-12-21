package me.mike3132.buildmode.ChatManager;

import me.mike3132.buildmode.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class ChatMessage {

    public static void sendMessage(Player player, String key) {
        File messagesConfig = new File(Main.plugin.getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages.Prefix") + configMessages.getString("Messages." + key);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }


}
