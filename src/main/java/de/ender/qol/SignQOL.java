package de.ender.qol;

import de.ender.core.CConfig;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignQOL implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        if (config.getBoolean("sign_editing")) {
            Action action = event.getAction();
            Player player = event.getPlayer();
            Block block = event.getClickedBlock();
            if (block != null) {
                BlockState blockstate = block.getState();

                if (action == Action.RIGHT_CLICK_BLOCK && blockstate instanceof Sign) {
                    Sign sign = (Sign) blockstate;
                    player.openSign(sign);
                    //rest happens in sign listener
                }
            }
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Sign sign = (Sign) event.getBlock().getState();
        for(int i = 0; i <= sign.getLines().length-1; i++){
            sign.setLine(i,sign.getLine(i));
        }
        sign.update();
    }
}
