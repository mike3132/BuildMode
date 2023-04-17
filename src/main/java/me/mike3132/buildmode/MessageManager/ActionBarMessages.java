package me.mike3132.buildmode.MessageManager;

import me.mike3132.buildmode.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class ActionBarMessages {
    private static String parseColor (String parseColor) {
        return ChatColor.translateAlternateColorCodes('&', parseColor);
    }
    public static void sendActionBar(Player player, String key) {
        File messagesConfig = new File(Main.plugin.getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages." + key);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(parseColor(message)));
    }

}
