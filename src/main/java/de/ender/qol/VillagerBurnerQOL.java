package de.ender.qol;

import de.ender.core.CConfig;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class VillagerBurnerQOL implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        if (config.getBoolean("villager_burner")) {
            Player player = event.getPlayer();
            Entity entity = event.getRightClicked();
            if(entity instanceof Villager&&player.getItemInHand().getType() == Material.FLINT_AND_STEEL) {
                entity.setFireTicks(1000);
            }
        }
    }
}
