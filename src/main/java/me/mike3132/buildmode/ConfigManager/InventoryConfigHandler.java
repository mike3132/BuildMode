package me.mike3132.buildmode.ConfigManager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class InventoryConfigHandler {



    public static void saveInventory(Player player) {
        if (!player.hasPermission("BuildMode.Override")) {
            FileConfiguration config = ConfigCreator.INVENTORIES.get();
            config.set(player.getUniqueId().toString(), player.getInventory().getContents());
            ConfigCreator.INVENTORIES.save(config);
        }
    }

    public static void loadInventory(Player player) {
        if(!player.hasPermission("BuildMode.Override")) {
            FileConfiguration config = ConfigCreator.INVENTORIES.get();
            final ItemStack[] itemstacks = Objects.requireNonNull(config.getList(player.getUniqueId().toString()))
                    .stream().map(o -> (ItemStack) o).toArray(ItemStack[]::new);
            player.getInventory().setContents(itemstacks);
        }
    }

}
