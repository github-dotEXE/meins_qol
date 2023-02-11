package de.ender.qol;

import com.destroystokyo.paper.entity.Pathfinder;
import de.ender.core.CConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.UUID;

public class VillagerMoverQOL implements @NotNull Listener  {

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        if (config.getBoolean("villager_mover")) {
            plugin = Main.getPlugin();
            cconfig = new CConfig("follows", plugin);
            config = cconfig.getCustomConfig();

            Player player = event.getPlayer();
            Entity entity = event.getRightClicked();
            UUID uuid = entity.getUniqueId();

            if (player.isSneaking() && entity instanceof Villager) {
                Villager villager = (Villager) entity;
                Pathfinder pathfinder = villager.getPathfinder();
                if (config.getInt(String.valueOf(uuid)) == 0) {

                    FileConfiguration finalConfig = config;
                    CConfig finalCconfig = cconfig;
                    new BukkitRunnable() {
                        boolean FSuccess = false;
                        boolean success = false;

                        @Override
                        public void run() {
                            if (player.getWorld() == villager.getWorld() || player.getLocation().distance(villager.getLocation()) >= 1.5) {
                                success = pathfinder.moveTo(player, new Random().nextDouble()/3+0.4);
                            } else {
                                pathfinder.stopPathfinding();
                            }
                            if (!FSuccess) {
                                player.sendMessage(ChatColor.GREEN + "<VillagerFollow> Villager now following!");
                                FSuccess = true;
                                finalConfig.set(String.valueOf(uuid), getTaskId());
                                finalCconfig.save();
                            }
                        }
                    }.runTaskTimer(plugin, 0L, 5L);

                } else {
                    player.sendMessage(ChatColor.GOLD + "<VillagerFollow> Villager no longer following!");
                    int taskID = config.getInt(String.valueOf(uuid));
                    Bukkit.getScheduler().cancelTask(taskID);
                    pathfinder.stopPathfinding();
                    config.set(String.valueOf(uuid), null);
                    cconfig.save();

                }

            }
        }
    }
}
