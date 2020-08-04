package de.devnytake.skrimeparty.commands;

import de.devnytake.skrimeparty.PartyGames;
import de.devnytake.skrimeparty.map.Map;
import de.devnytake.skrimeparty.util.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Copyright: DevNyTake
 * 16.07.2020 | 15:28
 */

public class SetupCommand implements CommandExecutor {

    private PartyGames plugin;

    public SetupCommand(PartyGames plugin) {
        this.plugin = plugin;
        plugin.getCommand("setup").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage("§cDazu musst du ein Spieler sein!");
            return false;
        }
        if(!cmd.getName().equals("setup"))return false;
        Player p = (Player)sender;
        if(p.hasPermission("PartyGames.commands.setup")){
            if(args.length == 0){
                p.sendMessage("---------§9Setup-----------");
                p.sendMessage("");
                p.sendMessage("§9/setup §esetspawn");
                p.sendMessage("§9/setup §ecreate §7<SpielModi> <Map-Name> <Builder>");
                p.sendMessage("§9/setup §esetspawn §7<SpielModi> <Map-Name>");
                p.sendMessage("");
                p.sendMessage("---------§9Setup-----------");
            } else {
                if (args[0].equalsIgnoreCase("create")) {
                    Map map = new Map(args[1], args[2]);
                    if (!map.exists()) {
                        map.create(args[3]);
                    }
                } else if (args[0].equalsIgnoreCase("setspawn")) {
                    Map map = new Map(args[1], args[2]);
                    if (map.exists()) {
                        map.setSpawn(p.getLocation());
                    }
                }else if(args[0].equalsIgnoreCase("setspawn")){
                    new LocationUtil(p.getLocation(), "Lobby");
                }
            }
        } else
            p.sendMessage(plugin.getPrefix() + plugin.getNoPerm());
        return false;
    }
}
