package de.devnytake.skrimeparty.listener;


import de.devnytake.skrimeparty.PartyGames;
import de.devnytake.skrimeparty.gamestates.GameState;
import de.devnytake.skrimeparty.map.Voting;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class VotingItemListener implements Listener {

    private PartyGames plugin;

    public VotingItemListener(PartyGames plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent e) {
        if (GameState.getCurrentGameState() != GameState.LOBBY) return;
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem().getType() == Material.CHEST) {
                Voting voting = plugin.getVoting();
                Player p = e.getPlayer();
                p.openInventory(voting.getVotingInventory());
            }
        }
    }

    @EventHandler
    public void onInventory(final InventoryClickEvent e) {
        if (GameState.getCurrentGameState() != GameState.LOBBY) return;
        Player p = (Player) e.getWhoClicked();
        Voting voting = plugin.getVoting();
        if (!voting.getVotet().contains(p)) {
            for (int i = 0; i < voting.getVotingGames().length; i++) {
                try {
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§8• §a" + voting.getVotingGames()[i].getName())) {
                        String gameName = voting.getVotingGames()[i].getName();
                        voting.getPlayerVotetForGame().put(p, voting.getVotingGames()[i]);
                        voting.getVotingGames()[i].setVotes(voting.getVotingGames()[i].getVotes() + 1);
                        p.sendMessage(plugin.getPrefix() + "§aDu hast für den Modus §e" + gameName + " §aabgestimmt");
                        voting.getVotet().add(p);
                        p.closeInventory();
                    }
                } catch (Exception ex) {

                }
            }
        } else {
            p.closeInventory();
            p.sendMessage(plugin.getPrefix() + "§cDu hast bereits Gevotet!");
        }

    }
}
