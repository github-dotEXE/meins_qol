package de.ender.qol;

import de.ender.core.CConfig;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.block.sign.Side;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class MiniMessageQOL implements Listener {
    @EventHandler
    public void onChatMessage(AsyncChatEvent event){
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();
        if(config.getBoolean("minimessage") && event.getPlayer().hasPermission("qol.minimessage"))
            event.message(MiniMessage.miniMessage().deserialize(PlainTextComponentSerializer.plainText().serialize(event.message())));
    }
    @EventHandler
    public void onSignChange(SignChangeEvent event){
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();
        if(config.getBoolean("minimessage") && event.getPlayer().hasPermission("qol.minimessage")) {
            for (int i = 0; i <4; i++) {
                Component line = event.line(i);
                if(line == null) continue;
                event.line(i,MiniMessage.miniMessage().deserialize(PlainTextComponentSerializer.plainText().serialize(line)));
            }
        }
    }
}
