package de.devnytake.skrimeparty.games.tntrun;

import de.devnytake.skrimeparty.PartyGames;
import de.devnytake.skrimeparty.games.tntrun.listener.PlayerMoveListener;
import de.devnytake.skrimeparty.gamestates.GameState;
import de.devnytake.skrimeparty.util.GameUtil;
import de.devnytake.skrimeparty.util.LocationUtil;
import de.devnytake.skrimeparty.util.items.Items;
import org.bukkit.*;
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
    private int idleID;


    public TNTRun(PartyGames plugin){
        this.plugin = plugin;

        tntRunPlayers = new ArrayList<Player>();
        seconds = 0;
        boards = new HashMap<Scoreboard, Player>();
    }

    public void startIdle(final Player player){
        tntRunPlayers.addAll(plugin.getPlayers());
        idleID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            int idleSeconds = 10;
            public void run() {
                switch (idleSeconds){
                    case 10: case 5: case 4: case 3: case 2:
                        Bukkit.broadcastMessage(plugin.getPrefix() + "§7Die Schutzzeit endet in §a" + idleSeconds + " §7Sekunden");
                        break;
                    case 1:
                        Bukkit.broadcastMessage(plugin.getPrefix() + "§7Die Schutzzeit endet in §aeiner §7Sekunde");
                        break;
                    case 0:
                        PlayerMoveListener.isRunning = true;
                        startCountdown();
                        createScoreboard(player);
                        createStartInventory(player);
                        updateScoreboard();
                        Bukkit.getScheduler().cancelTask(idleID);
                        break;
                }
                idleSeconds--;
            }
        }, 20, 20);
    }

    public void check(){
        if(tntRunPlayers.size() <= 1) {
            for(int i = 0; i < tntRunPlayers.size(); i++){
                Player wonPlayer = tntRunPlayers.get(i);
                sendWinnerMessage(wonPlayer);
            }
            cancleScheduler();
            PlayerMoveListener.isRunning = false;
            Bukkit.getOnlinePlayers().forEach(player ->{
                player.setGameMode(GameMode.SPECTATOR);
            });

            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                int idleSeconds = 5;
                public void run() {
                    if(idleSeconds == 0){
                        Location lobbyLocation = new LocationUtil("Lobby").loadLocation();
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            player.teleport(lobbyLocation);
                            player.setGameMode(GameMode.SURVIVAL);
                            plugin.getPlayers().clear();
                            plugin.getPlayers().add(player);
                            player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
                            Items.load(player);
                        });
                        GameState.setGameState(GameState.LOBBY);
                        if(plugin.getPlayers().size() >= plugin.getMinPlayers()){
                            if(!plugin.getLobbyCountdown().isRunning()){
                                plugin.getLobbyCountdown().start();
                            }
                        }else
                            plugin.getLobbyCountdown().startIdle();
                        PlayerMoveListener.reset();
                    }
                    idleSeconds--;
                }
            }, 20, 20);
        }
    }

    public void createStartInventory(Player p) {
        p.getInventory().clear();

        p.getInventory().setItem(8, Items.backToLobby);
    }

    public void createScoreboard(Player p) {
        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = sb.registerNewObjective("devnytake", "tntrun");

        //TODO: Müsen noch ebenen hinzugefügt werden

        Team players = sb.registerNewTeam("players");
        Team time = sb.registerNewTeam("time");

        players.addEntry(ChatColor.BLACK.toString());
        time.addEntry(ChatColor.RED.toString());

        DateFormat df = new SimpleDateFormat("mm:ss");
        Date d = new Date( (seconds*1000));

        players.setPrefix("§8» §a" + tntRunPlayers.size());
        time.setPrefix("§8» §a" + df.format(d));

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§bPartyGames §8• §4TNTRun");

        obj.getScore("").setScore(6);
        obj.getScore("§8• §7Spieler").setScore(5);
        obj.getScore(ChatColor.BLACK.toString()).setScore(4);
        obj.getScore(" ").setScore(3);
        obj.getScore("§8• §7Spielzeit").setScore(2);
        obj.getScore(ChatColor.RED.toString()).setScore(1);
        obj.getScore("  ").setScore(0);

        p.setScoreboard(sb);
        boards.put(sb, p);
    }

    public void updateScoreboard() {
        updateID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                for(Scoreboard boards : boards.keySet()){
                    DateFormat df = new SimpleDateFormat("mm:ss");
                    Date d = new Date( (seconds*1000));
                    boards.getTeam("time").setPrefix("§8» §a" + df.format(d));
                    boards.getTeam("players").setPrefix("§8» §a" + tntRunPlayers.size());
                }
            }
        }, 20, 20);
    }

    private void startCountdown(){
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                seconds++;
            }
        }, 20, 20);
    }

    public void cancleScheduler(){
        Bukkit.getScheduler().cancelTask(taskID);
        Bukkit.getScheduler().cancelTask(updateID);
    }

    private void sendWinnerMessage(Player wonPlayer){

        DateFormat df = new SimpleDateFormat("mm:ss");
        Date d = new Date( (seconds*1000));

        Bukkit.broadcastMessage("       §4TNTRun        ");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("§7Gewonnen: §a" + wonPlayer.getName());
        Bukkit.broadcastMessage("§7Spielzeit: §c" + df.format(d));
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("       §4TNTRun        ");
    }
}
