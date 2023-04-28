package de.ender.qol;

import de.ender.core.CConfig;
import org.bukkit.Location;
import org.bukkit.block.Bed;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.UUID;

public class BedSaverQOL implements Listener {
    @EventHandler
    public void onBedClick(PlayerBedEnterEvent event){
        Main plugin = Main.getPlugin();
        CConfig qolCConfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration qolConfig = qolCConfig.getCustomConfig();

        if (qolConfig.getBoolean("bed_saver")) {
            CConfig bedCConfig = new CConfig("bedLocations", plugin);
            FileConfiguration bedConfig = bedCConfig.getCustomConfig();

            Player player = event.getPlayer();
            UUID uuid = player.getUniqueId();
            Location bedLoc = event.getBed().getLocation();
            Location oldBed = bedConfig.getLocation(uuid+".o");

            if(oldBed==null){
                bedConfig.set(uuid+".o",bedLoc);
                bedCConfig.save();
            } else if(oldBed.distance(bedLoc)>=2) {
                bedConfig.set(uuid + ".t", oldBed);
                bedConfig.set(uuid + ".o", bedLoc);
                bedCConfig.save();
            }
        }
    }
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        Main plugin = Main.getPlugin();
        CConfig qolCConfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration qolConfig = qolCConfig.getCustomConfig();

        if (qolConfig.getBoolean("bed_saver")) {
            CConfig bedCConfig = new CConfig("bedLocations", plugin);
            FileConfiguration bedConfig = bedCConfig.getCustomConfig();

            UUID uuid = event.getPlayer().getUniqueId();
            Location bedLoc1 = bedConfig.getLocation(uuid+".o");
            Location bedLoc2 = bedConfig.getLocation(uuid+".t");
            
            if(bedLoc1 != null && bedLoc1.getBlock().getState() instanceof Bed) event.setRespawnLocation(bedLoc1);
            else if (bedLoc2 != null && bedLoc2.getBlock().getState() instanceof Bed) event.setRespawnLocation(bedLoc2);
        }
    }
}
