package de.ender.qol;

import de.ender.core.CConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class InventoryQOL implements Listener {
    @EventHandler
    public void onInventoryInteract(InventoryClickEvent event) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        Player player = (Player) event.getWhoClicked();


        if (config.getBoolean("delete")) {
            ClickType click = event.getClick();
            ItemStack item  = event.getCurrentItem();
            UUID uuid = player.getUniqueId();
            if(click == ClickType.SHIFT_RIGHT && item != null && item.getItemMeta() != null) {
                CConfig deleteItemsCConfig = new CConfig("deleteItems", plugin);
                FileConfiguration deleteItemsConfig = deleteItemsCConfig.getCustomConfig();

                String material = item.getType().name();
                String lowerType = material.toLowerCase();
                String itemName = item.getItemMeta().getDisplayName();

                boolean doit= true;
                List<String> materials = deleteItemsConfig.getStringList(uuid+".material");
                List<String> keywords = deleteItemsConfig.getStringList(uuid+".keyword");
                List<String> names = deleteItemsConfig.getStringList(uuid+".name");

                //check if item has any property making it blacklisted from deletion
                for(String keyword : keywords){
                    if (lowerType.contains(keyword)) {
                        doit = false;
                        player.sendActionBar("§cKeyword '"+keyword+"' found!");
                        break;
                    }
                }
                if(doit){
                    doit=!materials.contains(material);
                    player.sendActionBar("§cMaterial '"+material+"' found!");
                }
                if (doit) {
                    for(String name : names){
                        if (itemName.contains(name)) {
                            doit = false;
                            player.sendActionBar("§cName '"+name+"§c' found!");
                            break;
                        }
                    }
                }

                if (doit) {
                    player.sendActionBar("§c" + material + " Deleted - [Shift + Right_Click] - /delete undo");
                    deleteItemsConfig.set("undo."+uuid+".name",material);
                    deleteItemsConfig.set("undo."+uuid+".item",item);
                    deleteItemsCConfig.save();
                    event.setCurrentItem(null);
                }
            }
        }

    }
}
