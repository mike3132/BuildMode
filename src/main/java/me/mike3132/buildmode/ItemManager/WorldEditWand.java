package me.mike3132.buildmode.ItemManager;

import me.mike3132.buildmode.Main;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WorldEditWand {

    private ItemStack wand;

    public WorldEditWand() {
        this.createWand();
    }

    public ItemStack getWand() {
        return this.wand;
    }

    private void createWand() {
        ItemStack item = new ItemStack(Material.WOODEN_AXE, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        for(String realLore : Main.plugin.getConfig().getStringList("Wand-Lore")) {
            lore.add(Main.chatColor("" + realLore));
        }
        meta.setDisplayName(Main.chatColor("" + Main.plugin.getConfig().getString("Wand-Name")));
        meta.setLore(lore);
        meta.addEnchant(Enchantment.LUCK_OF_THE_SEA, 10, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        wand = item;

    }

}
