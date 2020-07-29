package de.devnytake.skrimeparty.map;


import de.devnytake.skrimeparty.PartyGames;
import de.devnytake.skrimeparty.games.Games;
import de.devnytake.skrimeparty.util.items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class Voting {

    private PartyGames plugin;
    private FileConfiguration config;

    private Inventory votingInventory;
    private int[] inventoryOrder = new int[] {2, 4, 6};

    private List<Games> spielModi;
    private HashMap<Player, Games> playerVotetForGame;
    private List<Player> votet;

    private Games[] votingGames;

    public Voting(PartyGames plugin, List<Games> spielModi){
        this.plugin = plugin;
        this.spielModi = spielModi;

        config = plugin.getCfg();
        votingGames = new Games[3];
        playerVotetForGame = new HashMap<Player, Games>();
        votet = new ArrayList<Player>();
    }

    public void chooseRandomSpielmodi(){
        for(int i = 0; i < votingGames.length; i++){
            Collections.shuffle(spielModi);
            votingGames[i] = spielModi.remove(0);
        }
    }

    public Inventory getVotingInventory(){
        votingInventory = Bukkit.createInventory(null, 9, "§eVote für einen Modus");
        for(int i = 0; i < votingGames.length; i++){
            Games currentGame = votingGames[i];
            votingInventory.setItem(inventoryOrder[i], new ItemManager(Material.PAPER)
                    .displayname("§8• §a" + currentGame.getName())
                    .lore("", "§8» §7Votes: §e" + currentGame.getVotes()).create());
        }
        return votingInventory;
    }

    public Games getWinnerGame(){
        Games winnerGame = votingGames[0];
        for(int i = 0; i < votingGames.length; i++){
            if(votingGames[i].getVotes() >= winnerGame.getVotes()){
                winnerGame = votingGames[i];
            }
        }
        return winnerGame;
    }

    public String getWinnerMap(Games spielmodi){
        List<String> maps = new ArrayList<String>();
        maps.addAll(config.getConfigurationSection(spielmodi.getName() + ".Maps").getKeys(false));
        Collections.shuffle(maps);
        return maps.remove(0);
    }

    public List<Player> getVotet() {
        return votet;
    }

    public Games[] getVotingGames() {
        return votingGames;
    }

    public HashMap<Player, Games> getPlayerVotetForGame() {
        return playerVotetForGame;
    }
}
