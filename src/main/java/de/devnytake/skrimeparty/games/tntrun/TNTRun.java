package de.devnytake.skrimeparty.games.tntrun;

import de.devnytake.skrimeparty.PartyGames;
import de.devnytake.skrimeparty.util.GameUtil;
import de.devnytake.skrimeparty.util.items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TNTRun implements GameUtil {

    private PartyGames plugin;

    private HashMap<Scoreboard, Player> boards;
    private List<Player> tntRunPlayers;
    private int seconds;

    private int taskID;
    private int updateID;

    public TNTRun(PartyGames plugin){
        tntRunPlayers = new ArrayList<Player>();
        seconds = 0;

        tntRunPlayers.addAll(plugin.getPlayers());
        boards = new HashMap<Scoreboard, Player>();

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                seconds++;
            }
        }, 20, 20);
    }

    public void createStartInventory(Player p) {
        p.getInventory().clear();

        p.getInventory().setItem(8, new ItemManager(Material.BED).displayname("§8• §cZurück zur Lobby").create());
        p.getInventory().setItem(0, new ItemManager(Material.CARROT).displayname("§8• Speedboost").create());
    }

    public void createScoreboard(Player p) {
        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = sb.registerNewObjective("devnytake", "tntrun");

        //TODO: Müsen noch ebenen hinzugefügt werden

        Team players = sb.registerNewTeam("players");
        Team time = sb.registerNewTeam("time");

        players.addEntry(ChatColor.BLACK.toString());
        time.addEntry(ChatColor.RED.toString());

        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date d = new Date( (seconds*60*60*1000));

        players.setPrefix("§8» §a" + tntRunPlayers.size());
        time.setPrefix("§8» §a" + df.format(d));

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§bPartyGames §8• §4TNTRun");

        obj.getScore("").setScore(6);
        obj.getScore("§8• §7Spieler").setScore(5);
        obj.getScore(ChatColor.BLACK.toString()).setScore(4);
        obj.getScore(" ").setScore(3);
        obj.getScore("8• §7Spielzeit").setScore(2);
        obj.getScore(ChatColor.RED.toString()).setScore(1);
        obj.getScore("  ").setScore(0);

        p.setScoreboard(sb);
        boards.put(sb, p);
    }

    public void updateScoreboard() {
        updateID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                for(Scoreboard boards : boards.keySet()){
                    DateFormat df = new SimpleDateFormat("HH:mm:ss");
                    Date d = new Date( (seconds*60*60*1000));
                    boards.getTeam("time").setPrefix("§8» §a" + df.format(d));
                    boards.getTeam("players").setPrefix("§8» §a" + tntRunPlayers.size());
                }
            }
        }, 20, 20);
    }
}
