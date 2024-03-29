package me.mike3132.buildmode.EventManager;

import me.mike3132.buildmode.BuildManager.BuildManager;
import me.mike3132.buildmode.MessageManager.ChatMessage;
import me.mike3132.buildmode.ConfigManager.InventoryConfigHandler;
import me.mike3132.buildmode.Main;
import me.mike3132.buildmode.SetManager.BuildSet;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class BuildEvents implements Listener {

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent pc) {
        Player player = pc.getPlayer();
        Block block = pc.getClickedBlock();
        if (pc.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (player.hasPermission("BuildMode.Override")) return;

            if (BuildSet.getBuildingPlayers("Jr").contains(player.getUniqueId())) {
                List<Material> blacklistedBlocks = new ArrayList<>();
                for (String string : Main.plugin.getConfig().getStringList("BlackListedBlocks")) {
                    blacklistedBlocks.add(Material.valueOf(string));
                }

                if (block.getType() != Material.AIR && blacklistedBlocks.contains(block.getType())) {
                    ChatMessage.sendMessage(player, "Build-Mode-Inventory-Disabled");
                    pc.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent wc) {
        Player player = wc.getPlayer();
        if (player.hasPermission("BuildMode.Override")) return;

        if (BuildSet.getBuildingPlayers("Jr").contains(player.getUniqueId())) {
            ChatMessage.sendMessage(player, "Build-Mode-World-Change");
            BuildSet.removeBuildingPlayers(player.getUniqueId(), "Jr");
            InventoryConfigHandler.loadInventory(player);
            player.setGameMode(GameMode.SURVIVAL);
            BuildManager.onDisabled(player, "Jr");
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent pdi) {
        Player player = pdi.getPlayer();
        if (player.hasPermission("BuildMode.Override")) return;

        if (BuildSet.getBuildingPlayers("Jr").contains(player.getUniqueId()) ||
                BuildSet.getBuildingPlayers("Sr").contains(player.getUniqueId())) {
            ChatMessage.sendMessage(player, "Build-Mode-Drop-Item-Disabled");;
            pdi.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent pqe) {
        Player player = pqe.getPlayer();

        if (BuildSet.getBuildingPlayers("Jr").contains(player.getUniqueId()) ||
                BuildSet.getBuildingPlayers("Sr").contains(player.getUniqueId())) {
            BuildSet.removeBuildingPlayers(player.getUniqueId(), "Jr");
            BuildSet.removeBuildingPlayers(player.getUniqueId(), "Sr");
            BuildManager.onDisabled(player, "Sr");
            BuildManager.onDisabled(player, "Jr");
        }
    }
}
