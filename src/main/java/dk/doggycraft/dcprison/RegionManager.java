package main.java.dk.doggycraft.dcprison;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class RegionManager
{
	private String				pluginName			= "null";
	private Prison			plugin;
	
	RegionManager(Prison p)
	{
		this.plugin = p;
	}
	
	public void load()
	{
		// Nothing to see here
	}
	
	private WorldGuardPlugin getWorldGuard() {
		
	    Plugin wgplugin = plugin.getServer().getPluginManager().getPlugin("WorldGuard");
	 
	    // WorldGuard may not be loaded
	    if (wgplugin == null || !(wgplugin instanceof WorldGuardPlugin)) {
	        return null; // Maybe you want throw an exception instead
	    }
	 
	    return (WorldGuardPlugin) wgplugin;
	}
	
	public boolean canBuild(org.bukkit.entity.Player player, org.bukkit.block.Block block)
	{
		return getWorldGuard().canBuild(player,block.getLocation().getBlock().getRelative(0, 0, 0));
	}
}