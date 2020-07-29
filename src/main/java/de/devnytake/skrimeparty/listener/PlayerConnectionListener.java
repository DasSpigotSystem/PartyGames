package de.devnytake.skrimeparty.listener;


import de.devnytake.skrimeparty.PartyGames;
import de.devnytake.skrimeparty.gamestates.GameState;
import de.devnytake.skrimeparty.gamestates.countdown.LobbyCountdown;
import de.devnytake.skrimeparty.map.Map;
import de.devnytake.skrimeparty.map.Voting;
import de.devnytake.skrimeparty.util.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener implements Listener {

    private PartyGames plugin;

    public PlayerConnectionListener(PartyGames plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent e){
        if(GameState.getCurrentGameState() != GameState.LOBBY)return;
        Player p = e.getPlayer();
        plugin.getPlayers().add(p);
        e.setJoinMessage(plugin.getPrefix() + "§7» §a" + p.getName()
                + " §7[" + plugin.getPlayers().size() + "/" + plugin.getMaxPlayers() + "]");
        Items.load(p);
        LobbyCountdown lobbyCountdown = plugin.getLobbyCountdown();
        if(plugin.getPlayers().size() >= plugin.getMinPlayers()){
            if(!lobbyCountdown.isRunning()){
                lobbyCountdown.stopIdle();
                lobbyCountdown.start();
            }
        }
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent e){
        if(GameState.getCurrentGameState() != GameState.LOBBY)return;
        Player p = e.getPlayer();
        plugin.getPlayers().remove(p);
        Voting voting = plugin.getVoting();
        voting.getVotet().remove(p);
        if(voting.getVotet().contains(p))
            voting.getPlayerVotetForGame().get(p).setVotes(voting.getPlayerVotetForGame().get(p).getVotes()-1);
        e.setQuitMessage(plugin.getPrefix() + "§7« §c" + p.getName()
                + " §7[" + plugin.getPlayers().size() + "/" + plugin.getMaxPlayers() + "]");
        LobbyCountdown lobbyCountdown = plugin.getLobbyCountdown();
        if(plugin.getPlayers().size() < plugin.getMinPlayers()){
            if(lobbyCountdown.isRunning()){
                lobbyCountdown.stop();
                lobbyCountdown.startIdle();
            }
        }
    }
}
