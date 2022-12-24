package me.mike3132.buildmode.EventManager;

import me.mike3132.buildmode.ItemManager.FullBrightClock;
import me.mike3132.buildmode.SetManager.BuildSet;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class ItemEvents implements Listener {

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent pie) {
        Player player = pie.getPlayer();
        Action action = pie.getAction();
        FullBrightClock fullBrightClock = new FullBrightClock();
        ItemStack clock = fullBrightClock.getClock();

        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (BuildSet.getBuildingPlayers("Jr").contains(player.getUniqueId()) ||
                    BuildSet.getBuildingPlayers("Sr").contains(player.getUniqueId())) {
                if (player.getInventory().getItemInMainHand().isSimilar(clock)) {
                    if (!player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                        player.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(999999999, 255));
                        return;
                    }
                    player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                }
            }
        }

    }
}
