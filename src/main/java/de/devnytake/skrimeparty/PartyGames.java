package de.devnytake.skrimeparty;

import de.devnytake.skrimeparty.commands.SetupCommand;
import de.devnytake.skrimeparty.commands.SpielmodiCommand;
import de.devnytake.skrimeparty.games.Games;
import de.devnytake.skrimeparty.games.tntrun.listener.PlayerMoveListener;
import de.devnytake.skrimeparty.gamestates.GameState;
import de.devnytake.skrimeparty.gamestates.countdown.LobbyCountdown;
import de.devnytake.skrimeparty.listener.PlayerConnectionListener;
import de.devnytake.skrimeparty.listener.VotingItemListener;
import de.devnytake.skrimeparty.map.Voting;
import de.devnytake.skrimeparty.mysql.MySQL;
import de.devnytake.skrimeparty.util.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright: DevNyTake
 * 15.07.2020 | 08:11
 */

public class PartyGames extends JavaPlugin {

    public static PartyGames instance;
    private LobbyCountdown lobbyCountdown;
    private Voting voting;

    private ArrayList<Games> spielModi;
    private List<Player> players;
    private int minPlayers, maxPlayers;

    @Override
    public void onEnable() {
        init();
        loadConfig();
        initSpielmodi();

        Location lobbyLocation = new LocationUtil("Lobby").loadLocation();
        if(lobbyLocation != null) {
            GameState.setGameState(GameState.LOBBY);
            lobbyCountdown.startIdle();
            sendConsoleMessage("§7_____________[§c" + getDescription().getName() + "§7]_____________");
            sendConsoleMessage("");
            sendConsoleMessage("§bDeveloper: " + getDescription().getAuthors().get(0));
            sendConsoleMessage("§eVersion: " + getDescription().getVersion());
            sendConsoleMessage("");
            sendConsoleMessage("§7_____________[§c" + getDescription().getName() + "§7]_____________");
        }else
            Bukkit.getConsoleSender().sendMessage("§cDie Lobby wurde noch nicht gesetzt!");
    }

    @Override
    public void onDisable() {

    }

    private void init(){

        //Listener
        new PlayerConnectionListener(this);
        new VotingItemListener(this);
        new PlayerMoveListener(this);

        //Commands
        new SetupCommand(this);
        new SpielmodiCommand(this);

        //Others
        instance = this;
        lobbyCountdown = new LobbyCountdown(this);

        spielModi = new ArrayList<>();
        players = new ArrayList<>();
        minPlayers = getConfig().getInt("lobbyphase.minPlayers");
        maxPlayers = getConfig().getInt("lobbyphase.maxPlayers");
    }

    private void initSpielmodi(){
        for(Games games : Games.values()) {
            spielModi.add(games);
            getConfig().set("Spielmodi." + games, games.getName());
            saveConfig();
        }

        voting = new Voting(this, spielModi);
        voting.chooseRandomSpielmodi();
    }

    private void loadConfig(){
        MySQL.USERNAME = getConfig().getString("mysql.username");
        MySQL.PASSWORD = getConfig().getString("mysql.password");
        MySQL.DATABASE = getConfig().getString("mysql.database");
        MySQL.HOST = getConfig().getString("mysql.host");
        MySQL.PORT = getConfig().getString("mysql.port");

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    private void sendConsoleMessage(String message){
        Bukkit.getConsoleSender().sendMessage(message);
    }

    public String getPrefix(){
        return this.getConfig().getString("text.prefix").replace('&', '§');
    }

    public String getNoPerm(){
        return this.getConfig().getString("text.noPermissions").replace('&', '§');
    }

    public static PartyGames getInstance() {
        return instance;
    }

    public ArrayList<Games> getSpielModi() {
        return spielModi;
    }

    public LobbyCountdown getLobbyCountdown() {
        return lobbyCountdown;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Voting getVoting() {
        return voting;
    }
}
