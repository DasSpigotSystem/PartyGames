package de.devnytake.skrimeparty.util.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright: DevNyTake
 * 15.07.2020 | 08:26
 */

public class Items {

    public static ItemStack voting, backToLobby;

    static {
        voting = new ItemManager(Material.CHEST).displayname("§8• §6Voting").create();
        backToLobby = new ItemManager(Material.BED).displayname("§8• §cZurück zur Lobby").create();
    }

    public static void load(Player player){
        player.getInventory().clear();

        player.getInventory().setItem(8, voting);
    }

}
