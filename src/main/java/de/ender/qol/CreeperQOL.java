package de.ender.qol;

import de.ender.core.CConfig;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class CreeperQOL implements Listener {
    @EventHandler
    public static void onExplosion_allBlocks(EntityExplodeEvent event) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        if (config.getBoolean("100%_creeper") && event.getEntityType() == EntityType.CREEPER) {
            event.setYield(100);
        }
    }
    @EventHandler
    public static void onExplosion_noDamage(EntityExplodeEvent event) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        if (config.getBoolean("creeper_no_damage") && event.getEntityType() == EntityType.CREEPER) {
            event.setCancelled(true);
            Location loc = event.getLocation();
            World world = loc.getWorld();
            world.playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 4F, (float) (0.56+Math.random()*(0.56-0.84)) );
            world.spawnParticle(Particle.EXPLOSION_HUGE, loc, 0);
        }
    }
}
