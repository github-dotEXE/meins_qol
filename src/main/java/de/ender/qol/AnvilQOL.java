package de.ender.qol;

import de.ender.core.CConfig;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.*;

public class AnvilQOL implements Listener {
    @EventHandler
    public void onAnvilPrepare(PrepareAnvilEvent event) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        if (config.getBoolean("max_anvil_remover")) {
            AnvilInventory inv = event.getInventory();
            inv.setMaximumRepairCost(1000);
            int repair_cost =inv.getRepairCost();
            if(repair_cost>=40) {
                event.getViewers().get(0).sendMessage("§bCost: "+ repair_cost+"lvl");
            }
        }
        if (config.getBoolean("mendify")) {
            AnvilInventory inv = event.getInventory();
            ItemStack first = inv.getFirstItem();
            ItemStack second = inv.getSecondItem();
            Player player = (Player) event.getViewers().get(0);
            if(first != null && second != null && second.getItemMeta() instanceof EnchantmentStorageMeta && first.getType() == Material.BOW) {
                EnchantmentStorageMeta esm = (EnchantmentStorageMeta) second.getItemMeta();
                List<Enchantment> firstEnchs = new ArrayList<>(first.getEnchantments().keySet());
                List<Enchantment> secondEnchs = new ArrayList<>(esm.getStoredEnchants().keySet());
                if((firstEnchs.contains(Enchantment.ARROW_INFINITE) && secondEnchs.contains(Enchantment.MENDING)) ||
                        (secondEnchs.contains(Enchantment.ARROW_INFINITE) && firstEnchs.contains(Enchantment.MENDING))){
                    ItemStack newResult = first;
                    newResult.addUnsafeEnchantment(Enchantment.MENDING,1);
                    newResult.addUnsafeEnchantment(Enchantment.ARROW_INFINITE,1);
                    event.setResult(newResult);
                    inv.setFirstItem(null);
                    inv.setSecondItem(null);
                    event.getView().setCursor(newResult);
                }
            }
        }
    }

}
