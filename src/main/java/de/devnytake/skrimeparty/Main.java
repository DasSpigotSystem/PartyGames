package de.devnytake.skrimeparty;

import de.devnytake.skrimeparty.commands.SetupCommand;
import de.devnytake.skrimeparty.commands.SpielmodiCommand;
import de.devnytake.skrimeparty.games.Games;
import de.devnytake.skrimeparty.gamestates.GameState;
import de.devnytake.skrimeparty.map.Map;
import de.devnytake.skrimeparty.mysql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright: DevNyTake
 * 15.07.2020 | 08:11
 */

public class Main extends JavaPlugin {

    public static Main instance;

    private File file;
    private YamlConfiguration cfg;

    public List<String> spielModi;
    public List<Map> maps;

    @Override
    public void onEnable() {
        init();
        loadConfig();
        initSpielmodi();

        GameState.setGameState(GameState.LOBBY);

        sendConsoleMessage("§7_____________[§c" + getDescription().getName() + "§7]_____________");
        sendConsoleMessage("");
        sendConsoleMessage("§bDeveloper: " + getDescription().getAuthors().get(0));
        sendConsoleMessage("§eVersion: " + getDescription().getVersion());
        sendConsoleMessage("");
        sendConsoleMessage("§7_____________[§c" + getDescription().getName() + "§7]_____________");
    }

    @Override
    public void onDisable() {

    }

    private void init(){

        //Listener

        //Commands
        new SetupCommand(this);
        new SpielmodiCommand(this);

        //Others
        instance = this;

        spielModi = new ArrayList<String>();
        maps = new ArrayList<Map>();

        file = new File("plugins//SkrimeParty", "maps.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
    }

    private void initSpielmodi(){
        spielModi.add(Games.TNTRUN.getName());
        spielModi.add(Games.AMPELRENNEN.getName());
        spielModi.add(Games.FINDEDENKNOPF.getName());
        spielModi.add(Games.JUMPANDRUN.getName());
        getConfig().set("Spielmodi", spielModi);
        saveConfig();

        if(!cfg.contains("Maps"))return;
        for(String current : spielModi){
            String mapName = cfg.getString("Maps." + current + ".Name");
            Map map = new Map(current, mapName);
            if(map.isFinished()){
                maps.add(map);
            }else
                Bukkit.getConsoleSender().sendMessage("§cDie Map §e" + map.getName() + " §cist noch nicht fertig eingerichtet!");
        }
    }

    private void loadConfig(){

        MySQL.USERNAME = getConfig().getString("mysql.username");
        MySQL.PASSWORD = getConfig().getString("mysql.password");
        MySQL.DATABASE = getConfig().getString("mysql.database");
        MySQL.HOST = getConfig().getString("mysql.host");
        MySQL.PORT = getConfig().getString("mysql.port");

        getConfig().options().copyDefaults(true);
        saveConfig();
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

    public static Main getInstance() {
        return instance;
    }

    public List<String> getSpielModi() {
        return spielModi;
    }
}
