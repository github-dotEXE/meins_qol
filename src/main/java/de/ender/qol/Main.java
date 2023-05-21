package de.ender.qol;

import de.ender.core.CConfig;
import de.ender.core.Log;
import de.ender.core.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static final String CONFIG = "QOL_Config";
    public static Main plugin;

    @Override
    public void onEnable() {
        Log.log(ChatColor.GREEN + "Enabling Meins QOL...");

        plugin = this;
        FileConfiguration config = new CConfig(CONFIG, plugin).getCustomConfig();

        getCommand("qolconfig").setExecutor(new QOLConfigCMD());
        getCommand("qolconfig").setTabCompleter(new QOLConfigCMD());
        getCommand("qolconfig").setPermission("qol.config");
        getCommand("reloadconfirm").setExecutor(new ReloadConfirmAlias());
        getCommand("delete").setExecutor(new DeleteCMD());

        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new SignQOL(), this);
        pluginManager.registerEvents(new HarvestablesQOL(), this);
        pluginManager.registerEvents(new TrampleQOL(), this);
        pluginManager.registerEvents(new VillagerCooldwonQOL(), this);
        pluginManager.registerEvents(new AnvilQOL(), this);
        pluginManager.registerEvents(new InventoryQOL(), this);
        pluginManager.registerEvents(new SleepQOL(), this);
        pluginManager.registerEvents(new VillagerMoverQOL(), this);
        pluginManager.registerEvents(new VillagerBurnerQOL(), this);
        pluginManager.registerEvents(new NetheriteBoosterQOL(), this);
        pluginManager.registerEvents(new TimeSaverQOL(), this);
        pluginManager.registerEvents(new BedSaverQOL(), this);
        pluginManager.registerEvents(new DeathQOL(), this);
        pluginManager.registerEvents(new CreeperQOL(), this);
        pluginManager.registerEvents(new DynamicRenderDistanceQOL(), this);

        UpdateChecker.check(this.getDescription().getVersion(), "github-dotEXE", "meins_qol","master");

        if (config.getBoolean("time_saver") && !Bukkit.getOnlinePlayers().isEmpty()) Bukkit.getWorlds().get(0).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
    }

    @Override
    public void onDisable() {
        Log.log(ChatColor.GREEN + "Disabling Meins QOL...");

        FileConfiguration config = new CConfig(CONFIG, plugin).getCustomConfig();

        if (config.getBoolean("time_saver")) Bukkit.getWorlds().get(0).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
    }

    public static Main getPlugin() {
        return plugin;
    }
}