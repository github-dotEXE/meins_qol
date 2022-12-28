package de.ender.qol;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignQOL implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        BlockState blockstate = block.getState();

        if(action == Action.RIGHT_CLICK_BLOCK && blockstate instanceof Sign) {
            Sign sign = (Sign) blockstate;
            player.openSign(sign);
            //rest happens in sign listener
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Sign sign = (Sign) event.getBlock().getState();
        for(int i = 1; i <= sign.getLines().length; i++){
            sign.setLine(i,sign.getLine(i));
        }
        sign.update();
    }
}
