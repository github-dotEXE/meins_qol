package de.ender.qol;

import de.ender.core.CConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReloadConfirmAlias implements @Nullable CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        if (config.getBoolean("reload_confirm_alias")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.performCommand("reload confirm");
            } else sender.sendMessage(ChatColor.RED + "Command is for players!");
        }
        return false;
    }

}
