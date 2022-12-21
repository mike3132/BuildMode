package me.mike3132.buildmode.BossBarManager;


import me.mike3132.buildmode.Main;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

public class BuildBossBar {

    private static final String barTitle = Main.chatColor("&6&lBuild Mode");
    private static final BossBar bar = Bukkit.createBossBar(barTitle, BarColor.GREEN, BarStyle.SOLID);


    public static BossBar getBar() {
        return bar;
    }



}
