package de.ender.qol;

import de.ender.core.CConfig;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class TrampleQOL implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        if (config.getBoolean("trample_protection")) {
            Action action = event.getAction();
            Block block = event.getClickedBlock();
            if(action == Action.PHYSICAL && block != null) {
                if (block.getType() == Material.FARMLAND) event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onEntityInteract(EntityInteractEvent event) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        if (config.getBoolean("trample_protection")) {
            if(event.getBlock().getType() == Material.FARMLAND && event.getEntity() instanceof Creature) {
                event.setCancelled(true);
            }
        }
    }
}
