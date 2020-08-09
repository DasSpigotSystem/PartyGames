package de.devnytake.skrimeparty.games.heißekartoffel;

import de.devnytake.skrimeparty.PartyGames;
import de.devnytake.skrimeparty.util.GameUtil;
import de.devnytake.skrimeparty.util.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criterias;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;

/**
 * Copyright: DevNyTake
 * 06.08.2020 | 21:27
 */

public class HeißeKartoffel implements GameUtil {

    private PartyGames plugin;

    private HashMap<Scoreboard, Player> boards;

    public HeißeKartoffel(PartyGames plugin){
        this.plugin = plugin;
        boards = new HashMap<>();
    }

    @Override
    public void createStartInventory(Player p) {
        p.getInventory().clear();

        p.getInventory().setItem(8, Items.backToLobby);
    }

    @Override
    public void createScoreboard(Player p) {
        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = sb.registerNewObjective("devnytake", "heißekartoffel");

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§bPartyGames §8• §eHeißekartoffel");

        p.setScoreboard(sb);
        boards.put(sb, p);
    }

    @Override
    public void updateScoreboard() {

    }
}
