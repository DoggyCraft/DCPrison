package dk.doggycraft.dcprison;

import org.bukkit.Location;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;

public class PrisonRegionManager
{
	@SuppressWarnings("unused")
	private Prison			plugin;
	
	PrisonRegionManager(Prison p)
	{
		this.plugin = p;
	}
	
	public void load()
	{
		// Nothing to see here
	}
	
	public static BlockVector3 asBlockVector(Location location)
	{
		return (BlockVector3.at(location.getX(), location.getY(), location.getZ()));
	}
	
	public static Vector3 asVector(org.bukkit.Location location) {
        return Vector3.at(location.getX(), location.getY(), location.getZ());
    }
	
	public boolean isMemberOfRegion(org.bukkit.entity.Player player, org.bukkit.block.Block block)
	{
		Location blockLocation = block.getLocation();
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		RegionQuery query = container.createQuery();
		com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(blockLocation);
		ApplicableRegionSet set = query.getApplicableRegions(loc);
		
		for (ProtectedRegion region : set) {
			DefaultDomain members = region.getMembers();
			if (members.contains(player.getUniqueId()) == false)
			{
				continue;
			}
			return true;
		}
		return false;
	}
}