package me.mike3132.buildmode.ItemManager;

import me.mike3132.buildmode.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveManager {


    public static void worldEditWand(Player player) {
        boolean wandEnabled = Main.plugin.getConfig().getBoolean("Wand-Enabled");

        if (!wandEnabled) return;
        WorldEditWand worldEditWand = new WorldEditWand();
        ItemStack wand = worldEditWand.getWand();
        player.getInventory().setItem(0, wand);
    }

    public static void fullbrightClock(Player player) {
        boolean clockEnabled = Main.plugin.getConfig().getBoolean("Fullbright-Clock-Enabled");

        if (!clockEnabled) return;
        ItemStack clock = FullBrightClock.getClock();
        player.getInventory().setItem(4, clock);
    }

    public static void lightItem(Player player) {
        boolean lightEnabled = Main.plugin.getConfig().getBoolean("Light-Enabled");

        if (!lightEnabled) return;
        LightItem lightItem = new LightItem();
        ItemStack light = lightItem.getLight();
        player.getInventory().setItem(1, light);
    }

    public static void debugStick(Player player) {
        boolean stickEnabled = Main.plugin.getConfig().getBoolean("Debug-Stick-Enabled");

        if (!stickEnabled) return;
        DebugStickItem debugStickItem = new DebugStickItem();
        ItemStack stick = debugStickItem.getStick();
        player.getInventory().setItem(7, stick);
    }

    public static void teleportCompass(Player player) {
        boolean compassEnabled = Main.plugin.getConfig().getBoolean("Compass-Enabled");

        if (!compassEnabled) return;
        TeleportCompass teleportCompass = new TeleportCompass();
        ItemStack compass = teleportCompass.getCompass();
        player.getInventory().setItem(8, compass);
    }


}
