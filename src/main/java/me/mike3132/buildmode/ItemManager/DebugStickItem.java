package me.mike3132.buildmode.ItemManager;

import me.mike3132.buildmode.Main;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class DebugStickItem {

    private ItemStack stick;

    public DebugStickItem() {
        this.createStick();
    }

    public ItemStack getStick() {
        return this.stick;
    }

    private void createStick() {
        ItemStack item = new ItemStack(Material.DEBUG_STICK, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        for (String realLore : Main.plugin.getConfig().getStringList("Debug-Stick-Lore")) {
            lore.add(Main.chatColor("" + realLore));
        }
        meta.setDisplayName(Main.chatColor("" + Main.plugin.getConfig().getString("Debug-Stick-Name")));
        meta.setLore(lore);
        meta.addEnchant(Enchantment.LUCK, 10, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        stick = item;
    }
}
