package de.devnytake.skrimeparty.commands;


import de.devnytake.skrimeparty.PartyGames;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Copyright: DevNyTake
 * 16.07.2020 | 15:51
 */

public class SpielmodiCommand implements CommandExecutor {

    private PartyGames plugin;

    public SpielmodiCommand(PartyGames plugin){
        this.plugin = plugin;
        plugin.getCommand("spielmodi").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender.hasPermission("PartyGames.commands.spielmodi")){
            if(args.length == 0){
                for(int i = 0; i < plugin.getSpielModi().size(); i++){
                    sender.sendMessage("§e" + plugin.getSpielModi().get(i).getName());
                }
            }else
                sender.sendMessage(plugin.getPrefix() + "§cBitte nutze §e/spielmodi");
        }else
            sender.sendMessage(plugin.getPrefix() + plugin.getNoPerm());
        return false;
    }
}
