package de.devnytake.skrimeparty.util;

import org.bukkit.entity.Player;

public interface GameUtil {

    void createStartInventory(Player p);

    void createScoreboard(Player p);

    void updateScoreboard();

}
