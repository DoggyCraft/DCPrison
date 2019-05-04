package dk.doggycraft.dcprison;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillListener implements Listener
{
	private Prison	plugin;

	KillListener(Prison p)
	{
		
		this.plugin = p;
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event)
	{
		Player player = event.getEntity();
		Player killer = player.getKiller();
		
		if (killer == null)
		{
			return;
		}

		String playerPrefix = plugin.getChatManager().getPrefix(player);
		String killerPrefix = plugin.getChatManager().getPrefix(killer);
		
		if (!plugin.getPermissionsManager().hasPermission(killer, "prison.showkill"))
		{
			return;
		}
		
		plugin.sendToAll(ChatColor.RED + killerPrefix + ChatColor.RED + killer.getDisplayName() + ChatColor.DARK_RED + " dræbte " + ChatColor.RED + playerPrefix + ChatColor.RED + player.getDisplayName() + ChatColor.DARK_RED + "!");
	}
}