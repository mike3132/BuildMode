package me.mike3132.buildmode.BossBarManager;


import me.mike3132.buildmode.Main;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

public class BuildBossBar {

    private static final String barTitle = Main.chatColor("" + Main.plugin.getConfig().getString("BossBar-Title"));
    private static final String barColor = Main.chatColor("" + Main.plugin.getConfig().getString("BossBar-Color"));
    private static final String barStyle = Main.chatColor("" + Main.plugin.getConfig().getString("BossBar-Style"));
    private static final BossBar bar = Bukkit.createBossBar(barTitle, BarColor.valueOf(barColor), BarStyle.valueOf(barStyle));


    public static BossBar getBar() {
        return bar;
    }



}
