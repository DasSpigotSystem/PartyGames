package de.devnytake.skrimeparty.gamestates.countdown;


import de.devnytake.skrimeparty.PartyGames;
import de.devnytake.skrimeparty.games.Games;
import de.devnytake.skrimeparty.gamestates.GameState;
import de.devnytake.skrimeparty.map.Map;
import de.devnytake.skrimeparty.map.Voting;
import de.devnytake.skrimeparty.util.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LobbyCountdown extends Countdown{

    private PartyGames plugin;

    private InGameCountdown inGameCountdown;

    private int seconds;
    private boolean isRunning;

    public LobbyCountdown(PartyGames plugin){
        this.plugin = plugin;

        seconds = plugin.getConfig().getInt("lobbyphase.seconds");
    }

    public void start() {
        isRunning = true;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                switch (seconds){
                    case 30: case 15: case 10: case 4: case 3: case 2:
                        Bukkit.broadcastMessage(plugin.getPrefix() + "§7Das Spiel startet in §a" + seconds + " §7Sekunden");
                        break;
                    case 5:
                        Bukkit.broadcastMessage(plugin.getPrefix() + "§7Das Spiel startet in §a" + seconds + " §7Sekunden");
                        Voting voting = plugin.getVoting();
                        for(Player players : plugin.getPlayers()){
                            players.sendTitle("§aSpielmodus: §e" + voting.getWinnerGame().getName(), "§aMap: §e" + voting.getWinnerMap(voting.getWinnerGame()));
                        }
                        break;
                    case 1:
                        Bukkit.broadcastMessage(plugin.getPrefix() + "§7Das Spiel startet in §aeiner §7Sekunde");
                        break;
                    case 0:
                        for(Player player : plugin.getPlayers()){
                            teleportToGame(player);
                        }
                        inGameCountdown = new InGameCountdown(plugin);
                        inGameCountdown.start();
                        Bukkit.getScheduler().cancelTask(taskID);
                        break;
                }
                seconds--;
            }
        }, 20, 20);
    }

    public void stop() {
        if (isRunning){
            isRunning = false;
            Bukkit.getScheduler().cancelTask(taskID);
            seconds = plugin.getConfig().getInt("lobbyphase.seconds");
        }
    }

    public void startIdle(){
        idleID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                Bukkit.broadcastMessage(plugin.getPrefix() +
                        "§7Bis zum Spielstart fehlen noch §e" + (plugin.getMinPlayers() - plugin.getPlayers().size()) +
                        " §7Spieler");
            }
        }, 20, 20*15);
    }

    public void stopIdle(){
        Bukkit.getScheduler().cancelTask(idleID);
    }

    private void teleportToGame(Player player) {
        Voting voting = plugin.getVoting();
        Games winnerGame = voting.getWinnerGame();
        String winnerMap = voting.getWinnerMap(winnerGame);

        Location spawnLocation = new LocationUtil(winnerGame.getName() + "." + "Maps." + winnerMap + ".Spawn").loadLocation();
        if (spawnLocation != null)
            player.teleport(spawnLocation);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
