package dk.doggycraft.dcprison;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class PrisonSign
{
	private Location	location;
	private String		world;
	private String		region;

	public PrisonSign(Location location, String region)
	{
		this.location = location;
		this.world = location.getWorld().getName();
		this.region = region;
	}

	public Location getLocation()
	{
		return this.location;
	}

	public String getWorld()
	{
		return this.world;
	}

	public World getWorldWorld()
	{
		return getLocation().getWorld();
	}

	public String getRegion()
	{
		return this.region;
	}

	public boolean onWall()
	{
		return getLocation().getBlock().getType() == Material.WALL_SIGN;
	}

	public void destroyAgent(boolean drop)
	{
		getLocation().getBlock().setType(Material.AIR);

		if (drop)
		{
			getWorldWorld().dropItem(getLocation(), new ItemStack(Material.SIGN, 1));
		}
	}
}