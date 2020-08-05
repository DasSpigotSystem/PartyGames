package de.devnytake.skrimeparty.gamestates.countdown;

import de.devnytake.skrimeparty.PartyGames;
import de.devnytake.skrimeparty.games.Games;
import de.devnytake.skrimeparty.games.tntrun.TNTRun;
import de.devnytake.skrimeparty.gamestates.GameState;
import de.devnytake.skrimeparty.map.Voting;
import org.bukkit.entity.Player;

/**
 * Copyright: DevNyTake
 * 02.08.2020 | 00:10
 */

public class InGameCountdown extends Countdown{

    private PartyGames plugin;

    private TNTRun tntRun;
    private Voting voting;
    private Games winnerGame;

    public InGameCountdown(PartyGames plugin) {
        this.plugin = plugin;
        voting = plugin.getVoting();
    }

    @Override
    public void start() {
        winnerGame = voting.getWinnerGame();
        //if TNTRun is playing
        if(winnerGame.equals(Games.TNTRUN)){
            tntRun = new TNTRun(plugin);
            for(Player player : plugin.getPlayers())
                tntRun.startIdle(player);
            GameState.setGameState(GameState.TNTRUN);
        }
    }

    @Override
    public void stop() {

    }
}
