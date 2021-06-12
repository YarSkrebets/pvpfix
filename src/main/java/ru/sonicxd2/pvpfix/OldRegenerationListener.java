package ru.sonicxd2.pvpfix;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public final class OldRegenerationListener implements Listener {
    private final JavaPlugin plugin;
    private Map<Player, Long> lastRegen = new HashMap<>();

    @EventHandler
    public void onRegeneration(EntityRegainHealthEvent e) {
        if ((e.getEntityType() != EntityType.PLAYER) || (e.getRegainReason() != EntityRegainHealthEvent.RegainReason.SATIATED)) {
            return;
        }
        e.setCancelled(true);
        val player = (Player) e.getEntity();
        if (System.currentTimeMillis() - lastRegen.getOrDefault(player, 0L) > Settings.REGENERATION_MILLIS) {
            player.setHealth(Math.min(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue(),
                    player.getHealth() + Settings.REGENERATION_HEALTH));
            lastRegen.put(player, System.currentTimeMillis());
        }
        val exhaustion = player.getExhaustion() + Settings.REGENERATION_EXHAUSTION_PER_REGEN;
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            player.setExhaustion(exhaustion);
        }, 1l);

    }


    @EventHandler
    public void onExit(PlayerQuitEvent e) {
        lastRegen.remove(e.getPlayer());
    }
}
