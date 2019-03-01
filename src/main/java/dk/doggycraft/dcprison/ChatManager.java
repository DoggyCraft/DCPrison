package main.java.dk.doggycraft.dcprison;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import net.milkbowl.vault.chat.Chat;

public class ChatManager
{
	private String				pluginName			= "null";
	private Prison			plugin;
	private static Chat vaultChat;
	
	public ChatManager(Prison p)
	{
		this.plugin = p;
			
		RegisteredServiceProvider<Chat> chatProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
		vaultChat = chatProvider.getProvider();
	}

	public void load()
	{
		// Nothing to see here
	}

	public Plugin getPlugin()
	{
		return plugin;
	}

	public String getPrefix(Player player)
	{
		String prefix = vaultChat.getPlayerPrefix(player);
		
		if (prefix == null)
		{
			return "";
		}
		
		prefix = ChatColor.translateAlternateColorCodes('&', prefix);
		
		return prefix;
	}
}