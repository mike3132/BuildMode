package me.mike3132.buildmode.MessageManager;

import me.mike3132.buildmode.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class TitleMessages {

    private static String parseColor (String parseColor) {
        return ChatColor.translateAlternateColorCodes('&', parseColor);
    }


    public static void sendTitle(Player player, String key, String subKey) {
        boolean titleEnabled = Main.plugin.getConfig().getBoolean("Title-Message-Enabled");

        if (!titleEnabled) return;

        File messagesConfig = new File(Main.plugin.getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String title = configMessages.getString("Messages." + key);
        String subTitle = configMessages.getString("Messages." + subKey);

        int titleFadeIn = Main.plugin.getConfig().getInt("Title-Fade-In");
        int titleStay = Main.plugin.getConfig().getInt("Title-Stay");
        int titleFadeOut = Main.plugin.getConfig().getInt("Title-Fade-Out");

        player.sendTitle(parseColor(title), parseColor(subTitle), titleFadeIn, titleStay, titleFadeOut);
    }

}
