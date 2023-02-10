package de.ender.qol;

import de.ender.core.CConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class SleepQOL implements Listener {
    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        if (config.getBoolean("stop_sleeping")) {
            Player player =  event.getPlayer();
            player.sendActionBar(ChatColor.GOLD + "Don't Sleep!");
            event.setCancelled(true);
        }
    }
}
