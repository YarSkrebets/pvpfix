package ru.sonicxd2.pvpfix;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PvpFixPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new OldRegenerationListener(this), this);
        Bukkit.getPluginManager().registerEvents(new NerfBowListener(), this);
    }
}
