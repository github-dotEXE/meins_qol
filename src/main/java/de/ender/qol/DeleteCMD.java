package de.ender.qol;

import de.ender.core.CConfig;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class DeleteCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Main plugin = Main.getPlugin();
            CConfig deleteItemsCConfig = new CConfig("deleteItems", plugin);
            FileConfiguration deleteItemsConfig = deleteItemsCConfig.getCustomConfig();

            Player player = ((Player) sender);
            UUID uuid = player.getUniqueId();

            List<String> list;
            boolean add = false;
            switch (args[0]){
                case "list":
                    list = deleteItemsConfig.getStringList(uuid+"."+args[1]);
                    player.sendMessage(ChatColor.DARK_PURPLE+"Your "+args[1]+" list is: "+ list);
                    break;
                case "add":
                    add = true;
                case "del":
                    list = deleteItemsConfig.getStringList(uuid+"."+args[1]);
                    String name =args[2].replace("&", "§");
                    if(add) list.add(name);
                    else list.remove(name);
                    deleteItemsConfig.set(uuid+"."+args[1],list);
                    player.sendMessage(ChatColor.GREEN+args[0]+"-ed/-eted "+args[1]+" "+ name);
                    break;

                case "undo":
                    ItemStack item = deleteItemsConfig.getItemStack("undo."+uuid+".item");
                    if(item !=null) {
                        player.getInventory().addItem(item);
                        deleteItemsConfig.set("undo." + uuid + ".item", null);
                        deleteItemsConfig.set("undo." + uuid + ".name", null);
                        player.sendMessage(ChatColor.GREEN+"Successfully added last deleted item to your inventory!");
                    } else {
                        player.sendMessage(ChatColor.RED+"Failed to get last deleted item!");
                    }
                    break;
            }
            deleteItemsCConfig.save();
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Main plugin = Main.getPlugin();
        CConfig deleteItemsCConfig = new CConfig("deleteItems", plugin);
        FileConfiguration deleteItemsConfig = deleteItemsCConfig.getCustomConfig();

        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();

        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        if(args.length == 1) {
            commands.add("add");
            commands.add("undo");
            commands.add("del");
            commands.add("list");
        } else if(args.length == 2) {
            commands.add("material");
            commands.add("keyword");
            commands.add("name");
        } else if(args.length == 3 && args[1].equals("material")) {
            for(Material m : Material.values()){
                commands.add(m.name());
            }
        } else if(args.length == 3 && args[1].equals("keyword")) {
            List<String> list = deleteItemsConfig.getStringList(uuid+".keyword");
            commands.addAll(list);
        } else if(args.length == 3 && args[1].equals("name")) {
            List<String> list = deleteItemsConfig.getStringList(uuid+".name");
            for(String s : list){
                commands.add(s.replace("§","&"));
            }
        }

        StringUtil.copyPartialMatches(args[args.length-1], commands, completions); //copy matches of first argument
        Collections.sort(completions);//sort the list
        return completions;
    }
}
