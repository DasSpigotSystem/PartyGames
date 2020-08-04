package de.devnytake.skrimeparty.games.tntrun.listener;

import de.devnytake.skrimeparty.PartyGames;
import de.devnytake.skrimeparty.games.tntrun.TNTRun;
import de.devnytake.skrimeparty.gamestates.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright: DevNyTake
 * 02.08.2020 | 12:36
 */

public class PlayerMoveListener implements Listener {

    private PartyGames plugin;
    private TNTRun tntRun;

    private static List<Block> gravelBlocks;
    private static List<Block> sandBlocks;
    private static List<Block> tntBlocks;

    private int taskID;

    public PlayerMoveListener(PartyGames plugin) {
        this.plugin = plugin;

        gravelBlocks = new ArrayList<Block>();
        sandBlocks = new ArrayList<Block>();
        tntBlocks = new ArrayList<Block>();
        tntRun = new TNTRun(plugin);
    }

    @EventHandler
    public void onMove(final PlayerMoveEvent e){
        if(GameState.getCurrentGameState() != GameState.TNTRUN)return;
        if(!tntRun.isRunning())return;

        final Player p = e.getPlayer();
        final Block blockgravel = p.getLocation().subtract(0, 1, 0).getBlock();
        final Block blocktnt = p.getLocation().subtract(0, 2, 0).getBlock();

        if(blockgravel.getType() != Material.AIR){
            tntBlocks.add(blocktnt);
            if(blockgravel.getType() == Material.GRAVEL)
                gravelBlocks.add(blockgravel);
            else
                sandBlocks.add(blockgravel);
            taskID = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    blockgravel.setType(Material.AIR);
                    blocktnt.setType(Material.AIR);
                }
            }, 8);
        }
    }

    public static void reset(){
        for(Block block : tntBlocks){
            block.setType(Material.TNT);
            block.getWorld().getBlockAt(block.getLocation());
            block.getWorld().getBlockAt(block.getX(), block.getY(), block.getZ());
        }
        for(Block block : sandBlocks){
            block.setType(Material.SAND);
            block.getWorld().getBlockAt(block.getLocation());
            block.getWorld().getBlockAt(block.getX(), block.getY(), block.getZ());
        }
        for(Block block : gravelBlocks){
            block.setType(Material.GRAVEL);
            block.getWorld().getBlockAt(block.getLocation());
            block.getWorld().getBlockAt(block.getX(), block.getY(), block.getZ());
        }
    }
}
