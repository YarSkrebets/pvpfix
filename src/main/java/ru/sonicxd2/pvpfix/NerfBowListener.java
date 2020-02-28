package ru.sonicxd2.pvpfix;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public final class NerfBowListener implements Listener {
    @EventHandler
    public void onProjectileHit(EntityDamageByEntityEvent e) {
        if ((e.getEntityType() == EntityType.PLAYER) && (e.getDamager().getType() == EntityType.ARROW)) {
            e.setDamage(EntityDamageEvent.DamageModifier.BASE,
                    e.getDamage(EntityDamageEvent.DamageModifier.BASE) * Settings.ARROW_DAMAGE_MODIFIER);
        }
    }

}
