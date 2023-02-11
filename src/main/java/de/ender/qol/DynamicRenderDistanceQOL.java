package de.ender.qol;

import de.ender.core.CConfig;
import de.ender.core.afk.AfkStartEvent;
import de.ender.core.afk.AfkStopEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DynamicRenderDistanceQOL implements Listener {

    Main plugin = Main.getPlugin();
    CConfig cconfig = new CConfig(Main.CONFIG, plugin);
    FileConfiguration config = cconfig.getCustomConfig();

    @EventHandler
    public void onAfkStart(AfkStartEvent event){
        if (config.getBoolean("dynamic_render_distance")) {
            Player player = event.getPlayer();
            player.setViewDistance(2);
            player.sendMessage(ChatColor.DARK_GRAY + "You are now AFK!");
        }
    }

    @EventHandler
    public void onAfkStop(AfkStopEvent event){
        if (config.getBoolean("dynamic_render_distance")) {
            Player player = event.getPlayer();
            player.setViewDistance(Bukkit.getViewDistance());
            player.sendMessage(ChatColor.GRAY + "You are no longer AFK!");
        }
    }
}
