package de.ender.qol;

import de.ender.core.CConfig;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class SleepQOL implements Listener {
    @EventHandler
    public void stopSleeping(PlayerBedEnterEvent event) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        if (config.getBoolean("stop_sleeping")) {
            Player player =  event.getPlayer();
            player.sendActionBar(ChatColor.GOLD + "Don't Sleep!");
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void noExcuses(PlayerBedEnterEvent event) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        if (config.getBoolean("no_sleep_problems")) {
            PlayerBedEnterEvent.BedEnterResult result = event.getBedEnterResult();
            World world = event.getPlayer().getWorld();
            boolean storming = world.hasStorm();
            long time = world.getTime();
            if (result == PlayerBedEnterEvent.BedEnterResult.NOT_SAFE ||
                    (result == PlayerBedEnterEvent.BedEnterResult.TOO_FAR_AWAY && ((time >= 12542 && time <= 23459) || storming))) {
                event.setUseBed(Event.Result.ALLOW);
            }
        }
    }
}
