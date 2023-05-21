package de.ender.qol;

import de.ender.core.CConfig;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class HarvestablesQOL implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        if (config.getBoolean("easy_harvest")) {
            Action action = event.getAction();
            Player player = event.getPlayer();
            Block block = event.getClickedBlock();
            BlockData blockdata;
            if (block != null) {
                blockdata = block.getBlockData();
                Material blocktype = block.getType();
                ItemStack item = player.getInventory().getItemInMainHand();
                Material itemtype = item.getType();

                if (action == Action.RIGHT_CLICK_BLOCK && blockdata instanceof Ageable &&
                        (itemtype == Material.AIR || itemtype == Material.BONE_MEAL || itemtype.name().toLowerCase().contains("hoe")) &&
                        !(blocktype.name().toLowerCase().contains("stem")|| blocktype == Material.BAMBOO|| blocktype == Material.SWEET_BERRY_BUSH)) {
                    Ageable ageable = (Ageable) blockdata;
                    if (ageable.getAge() == ageable.getMaximumAge()) {
                        ItemStack[] drops = block.getDrops(player.getInventory().getItemInMainHand()).toArray(new ItemStack[0]);
                        player.getInventory().addItem(drops);
                        ageable.setAge(0);
                        block.setBlockData(ageable);
                        player.playSound(block.getLocation(), blockdata.getSoundGroup().getBreakSound(), 1f, 1f);
                    }
                }
            }
        }
    }
}
