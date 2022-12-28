package de.ender.qol;

import de.ender.core.MCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        new MCore().log(ChatColor.GREEN + "Enabling Meins QOL...");

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new SignQOL(), this);
        pluginManager.registerEvents(new HarvestablesQOL(), this);
    }

    @Override
    public void onDisable() {
        new MCore().log(ChatColor.GREEN + "Disabling Meins QOL... :(");
    }
}
