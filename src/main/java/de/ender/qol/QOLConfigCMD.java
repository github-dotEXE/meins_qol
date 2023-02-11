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

public class QOLConfigCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig(Main.CONFIG, plugin);
        FileConfiguration config = cconfig.getCustomConfig();
        Object args0 = config.getBoolean(args[0]);
        if(args.length == 1) {
            sender.sendMessage(ChatColor.GREEN + args[0] + "=" + args0);
            return false;
        }
        config.set(args[0], Boolean.parseBoolean(args[1]));
        cconfig.save();
        sender.sendMessage(ChatColor.GREEN + args[0] + " is now " + args[1]);
        return false;
    }


    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> commands = new ArrayList<>();
        List<String> completes = new ArrayList<>();

        if(args.length == 1) {
            commands.add("trample_protection");
            commands.add("easy_harvest");
            commands.add("sign_editing");
            commands.add("villager_cooldown");
            commands.add("reload_confirm_alias");
            commands.add("max_anvil_remover");
            commands.add("mendify");
            commands.add("middle_to_delete");
            commands.add("stop_sleeping");
            commands.add("villager_mover");
            commands.add("villager_burner");
            commands.add("boost_netherite");
            commands.add("dynamic_render_distance");
        } else if (args.length == 2) {
            commands.add("true");
            commands.add("false");
        }

        StringUtil.copyPartialMatches(args[args.length-1], commands,completes); //copy matches of first argument
        Collections.sort(commands);//sort the list
        return commands;

    }
}
