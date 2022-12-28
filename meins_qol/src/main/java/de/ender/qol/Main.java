package de.ender.qol;

import de.ender.core.CConfig;
import de.ender.core.MCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static final String CONFIG = "QOL_Config";
    public static Main plugin;

    @Override
    public void onEnable() {
        new MCore().log(ChatColor.GREEN + "Enabling Meins QOL...");

        plugin = this;
        new CConfig(CONFIG, plugin);

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new SignQOL(), this);
        pluginManager.registerEvents(new HarvestablesQOL(), this);
        pluginManager.registerEvents(new TrampleQOL(), this);
        pluginManager.registerEvents(new VillagerCooldwonQOL(), this);
    }

    @Override
    public void onDisable() {
        new MCore().log(ChatColor.GREEN + "Disabling Meins QOL... :(");
    }

    public static Main getPlugin() {
        return plugin;
    }
}