package de.ender.qol;

import de.ender.core.CConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class QOLConfigCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();
        if(args.length == 0) {
            for(Map.Entry<String, Object> entry : config.getValues(false).entrySet()){
                sender.sendMessage(ChatColor.GREEN + entry.getKey() + " = " + entry.getValue());
            }
            return false;
        }
        boolean value0 = config.getBoolean(args[0]);
        if(args.length == 1) {
            sender.sendMessage(ChatColor.GREEN + args[0] + " = " + value0);
            return false;
        }
        config.set(args[0], Boolean.parseBoolean(args[1]));
        cconfig.save();
        sender.sendMessage(ChatColor.GREEN + args[0] + " is now " + args[1]);
        return false;
    }


    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> commands = new ArrayList<>();
        List<String> completes = new ArrayList<>();

        if(args.length == 1) {
            Main plugin = Main.getPlugin();
            CConfig cconfig = new CConfig(Main.CONFIG, plugin);
            FileConfiguration config = cconfig.getCustomConfig();
            commands.addAll(config.getKeys(false));
        } else if (args.length == 2) {
            commands.add("true");
            commands.add("false");
        }
        StringUtil.copyPartialMatches(args[args.length-1], commands,completes); //copy matches of first argument
        Collections.sort(commands);//sort the list
        return commands;
    }
}
