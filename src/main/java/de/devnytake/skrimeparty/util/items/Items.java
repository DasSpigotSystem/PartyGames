package de.devnytake.skrimeparty.util.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright: DevNyTake
 * 15.07.2020 | 08:26
 */

public class Items {

    public static ItemStack voting;

    static {
        voting = new ItemManager(Material.CHEST).displayname("§8• §6Voting").create();
    }

    public static void load(Player player){
        player.getInventory().clear();

        player.getInventory().setItem(8, voting);
    }

}
