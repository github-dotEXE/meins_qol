package de.ender.qol;

import com.google.common.collect.Lists;
import de.ender.core.CConfig;
import de.ender.core.MCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.MerchantRecipe;

import java.util.Iterator;
import java.util.List;

public class VillagerCooldwonQOL implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        if (config.getBoolean("villager_cooldown")) {
            Player player = event.getPlayer();
            Entity entity = event.getRightClicked();
            if(entity instanceof Villager) {
                Villager villager = (Villager) entity;
                List<MerchantRecipe> recipes = Lists.newArrayList(villager.getRecipes());
                Iterator<MerchantRecipe> recipeIterator;
                for (recipeIterator = recipes.iterator(); recipeIterator.hasNext(); ) {
                    MerchantRecipe recipe = recipeIterator.next();
                    recipe.setUses(0);
                    recipe.setDemand(1); // price multiplier * demand * price = price increase //demand 1 == demand will not do anything
                }
                villager.setRecipes(recipes);
            }
        }
    }
}
