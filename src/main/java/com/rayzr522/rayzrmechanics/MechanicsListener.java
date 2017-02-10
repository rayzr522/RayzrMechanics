/**
 * 
 */
package com.rayzr522.rayzrmechanics;

import static com.rayzr522.creativelynamedlib.utils.text.TextUtils.camelCase;
import static com.rayzr522.creativelynamedlib.utils.text.TextUtils.colorize;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * @author Rayzr
 *
 */
public class MechanicsListener implements Listener {
    private RayzrMechanics plugin;

    /**
     * @param plugin
     */
    public MechanicsListener(RayzrMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlace(BlockPlaceEvent e) {
        if (e.getPlayer().isSneaking() || e.getBlock().getType() != Material.TNT)
            return;
        new BukkitRunnable() {
            public void run() {
                if (e.isCancelled() || e.getBlock().getType() != Material.TNT) {
                    return;
                }
                e.getBlock().setType(Material.AIR);
                TNTPrimed tnt = (TNTPrimed) e.getBlock().getWorld().spawnEntity(e.getBlock().getLocation().add(0.5, 0, 0.5), EntityType.PRIMED_TNT);
                tnt.setFuseTicks(20);
                tnt.setVelocity(new Vector(0, 0.5, 0));
            }
        }.runTaskLater(plugin, 1L);
    }

    @EventHandler
    public void onExplode(AsyncPlayerChatEvent e) {
        @SuppressWarnings("deprecation")
        ItemStack item = e.getPlayer().getItemInHand();
        if (e.getMessage().equalsIgnoreCase("[item]") && item != null && item.getType() != Material.AIR) {
            StringBuilder msg = new StringBuilder('\n');
            msg.append(colorize("&3Type: &b")).append(camelCase(item.getType().name().toLowerCase().replace('_', ' '))).append('\n');
            msg.append(colorize("&3Amount: &b")).append(item.getAmount()).append('\n');
            msg.append(colorize("&3Durability: &b")).append(item.getDurability()).append('\n');
            e.setMessage(msg.toString());
        }
    }

}
