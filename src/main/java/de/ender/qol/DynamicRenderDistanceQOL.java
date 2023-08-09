package de.ender.qol;

import de.ender.core.CConfig;
import de.ender.core.afk.AfkJoinEvent;
import de.ender.core.afk.AfkLeaveEvent;
import de.ender.core.afk.AfkStartEvent;
import de.ender.core.afk.AfkStopEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class DynamicRenderDistanceQOL implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onAFK(AfkStartEvent event){
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        if(config.getBoolean("afk_render_distance")) {
            event.getPlayer().setViewDistance(2); //doesn't go under 10 but should they fix it, it will go lower I guess
            event.getPlayer().setSendViewDistance(2);
        }
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onNotAFK(AfkStopEvent event) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        if (config.getBoolean("afk_render_distance")) {
            event.getPlayer().setViewDistance(Bukkit.getViewDistance());
            event.getPlayer().setSendViewDistance(Bukkit.getViewDistance());
        }
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onAFKJoin(AfkJoinEvent event) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        if (config.getBoolean("afk_render_distance")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    event.getPlayer().setViewDistance(Bukkit.getViewDistance());
                    event.getPlayer().setSendViewDistance(Bukkit.getViewDistance());
                }
            }.runTaskLater(Main.getPlugin(),10);
        }
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onAFKLeave(AfkLeaveEvent event) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        if (config.getBoolean("afk_render_distance")) {
            event.getPlayer().setViewDistance(Bukkit.getViewDistance());
            event.getPlayer().setSendViewDistance(Bukkit.getViewDistance());
        }
    }
}
