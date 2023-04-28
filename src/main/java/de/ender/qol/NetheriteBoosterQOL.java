package de.ender.qol;

import de.ender.core.CConfig;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NetheriteBoosterQOL implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageEvent event) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        if (config.getBoolean("boost_netherite")) {
            Entity entity = event.getEntity();
            EntityDamageEvent.DamageCause dc = event.getCause();
            if(entity instanceof Player){
                Player player = (Player) entity;
                PlayerInventory inv = player.getInventory();
                ItemStack chest = inv.getChestplate();
                ItemStack legg = inv.getLeggings();
                ItemStack boot = inv.getBoots();
                ItemStack helm = inv.getHelmet();
                if(chest != null&&helm != null&&legg != null&&boot != null&&
                        legg.getType()== Material.NETHERITE_LEGGINGS
                        &&boot.getType()== Material.NETHERITE_BOOTS && helm.getType()== Material.NETHERITE_HELMET) {
                    if(chest.getType()== Material.NETHERITE_CHESTPLATE&&(dc == EntityDamageEvent.DamageCause.FIRE || dc == EntityDamageEvent.DamageCause.FIRE_TICK
                            || dc == EntityDamageEvent.DamageCause.LAVA|| dc == EntityDamageEvent.DamageCause.HOT_FLOOR)) {
                        event.setDamage(0);
                        player.setFireTicks(0);
                        event.setCancelled(true);
                    } else if ((chest.getType()== Material.ELYTRA||chest.getType()== Material.NETHERITE_CHESTPLATE)&&(dc == EntityDamageEvent.DamageCause.FLY_INTO_WALL ||
                            dc == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || dc == EntityDamageEvent.DamageCause.CONTACT)) {
                        event.setDamage(event.getDamage()*6/9);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        if (config.getBoolean("boost_netherite_tools")) {
            Action action  = event.getAction();
            Block block = event.getClickedBlock();
            Player player= event.getPlayer();
            PotionEffect pe = player.getPotionEffect(PotionEffectType.FAST_DIGGING);
            if(pe != null && pe.getAmplifier() == 1 && action.isLeftClick()&&block != null && player.getItemInHand().getType() == Material.NETHERITE_PICKAXE) {
                if(block.getType().toString().toLowerCase().contains("deepslate")) {
                    player.addPotionEffect(PotionEffectType.FAST_DIGGING.createEffect(5, 9).withParticles(false));
                } else if (block.getType().toString().toLowerCase().contains("glass")) {
                    player.addPotionEffect(PotionEffectType.FAST_DIGGING.createEffect(5, 40).withParticles(false));
                }
            }
        }
    }
}
