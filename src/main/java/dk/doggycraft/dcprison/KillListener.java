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
		
		if (!plugin.getPermissionsManager().hasPermission(killer, "prison.showkill"))
		{
			return;
		}
		
		String playerPrefix = plugin.getChatManager().getPrefix(player);
		String killerPrefix = plugin.getChatManager().getPrefix(killer);
		
		String killMessage = plugin.killMessage.replace("{killerPrefix}", killerPrefix).replace("{killerDisplayname}", killer.getDisplayName()).replace("{killedPrefix}", playerPrefix).replace("{killedDisplayname}", player.getDisplayName());
		plugin.sendToAll(ChatColor.translateAlternateColorCodes('&', killMessage));
	}
}