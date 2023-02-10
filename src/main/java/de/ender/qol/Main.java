package de.ender.qol;

import de.ender.core.CConfig;
import de.ender.core.MCore;
import de.ender.core.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static final String CONFIG = "QOL_Config";
    public static Main plugin;

    @Override
    public void onEnable() {
        new MCore().log(ChatColor.GREEN + "Enabling Meins QOL...");

        plugin = this;
        FileConfiguration config = new CConfig(CONFIG, plugin).getCustomConfig();

        getCommand("qolconfig").setExecutor(new QOLConfigCMD());
        getCommand("qolconfig").setTabCompleter(new QOLConfigCMD());
        getCommand("qolconfig").setPermission("qol.config");
        getCommand("reloadconfirm").setExecutor(new ReloadConfirmAlias());

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new SignQOL(), this);
        pluginManager.registerEvents(new HarvestablesQOL(), this);
        pluginManager.registerEvents(new TrampleQOL(), this);
        pluginManager.registerEvents(new VillagerCooldwonQOL(), this);
        pluginManager.registerEvents(new AnvilQOL(), this);
        pluginManager.registerEvents(new InventoryQOL(), this);
        pluginManager.registerEvents(new SleepQOL(), this);

        new UpdateChecker().check("1.5", "github-dotEXE", "meins_qol");
    }

    @Override
    public void onDisable() {
        new MCore().log(ChatColor.GREEN + "Disabling Meins QOL...");
    }

    public static Main getPlugin() {
        return plugin;
    }
}