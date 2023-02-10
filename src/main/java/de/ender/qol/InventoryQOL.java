package de.ender.qol;

import de.ender.core.CConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryQOL implements Listener {
    @EventHandler
    public void onInventoryInteract(InventoryClickEvent event) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        Player player = (Player) event.getWhoClicked();


        if (config.getBoolean("middle_to_delete")) {
            ClickType click = event.getClick();
            ItemStack item  = event.getCurrentItem();
            if(click == ClickType.SHIFT_RIGHT && item != null) {
                String lowerType = item.getType().toString().toLowerCase();
                if (!(lowerType.contains("diamond") || lowerType.contains("netherite") || lowerType.contains("iron") || lowerType.contains("bow") || lowerType.contains("shulker_box"))) {
                    player.sendActionBar("§cItem Deleted - [Shift + Right_Click]");
                    event.setCurrentItem(null);
                }
            }
        }

    }
}
