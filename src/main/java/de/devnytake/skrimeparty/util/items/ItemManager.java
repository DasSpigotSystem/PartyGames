package de.devnytake.skrimeparty.util.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Copyright: DevNyTake
 * 15.07.2020 | 08:26
 */

public class ItemManager {

    ItemStack item;
    ItemMeta meta;

    public ItemManager(Material material){
        this(material, 1, 0);
    }

    public ItemManager(Material material, int amount){
        this(material, amount, 0);
    }

    public ItemManager(Material material, int amount, int subID){
        item = new ItemStack(material, amount, (short)subID);
        meta = item.getItemMeta();
    }

    public ItemManager displayname(String name){
        meta.setDisplayName(name);
        return this;
    }

    public ItemManager lore(String... list){
        meta.setLore(Arrays.asList(list));
        return this;
    }

    public ItemStack create(){
        item.setItemMeta(meta);
        return item;
    }
}
