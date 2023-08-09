package de.ender.qol;

import de.ender.core.CConfig;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathQOL implements Listener {

    @EventHandler
    public static void deathCoords(PlayerDeathEvent e) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        if (config.getBoolean("death_coords")) {
            Player player = e.getPlayer();
            Location location = player.getLocation();
            player.sendMessage(ChatColor.GOLD+"Your Death Coordinates are:" + ChatColor.GRAY +
                    " X:" + ChatColor.DARK_RED + location.getBlockX() + ChatColor.GRAY +
                    " Y:" + ChatColor.GREEN + location.getBlockY() + ChatColor.GRAY +
                    " Z:" + ChatColor.BLUE + location.getBlockZ() );
        }
    }
    @EventHandler
    public static void expSaver(PlayerDeathEvent e) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        if (config.getBoolean("keep_exp_on_death")) {
            e.setKeepLevel(true);
            e.setDroppedExp(0);
        }
    }
}
