package main.java.dk.doggycraft.dcprison;

import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;

import net.milkbowl.vault.economy.Economy;

public class PrisonManager
{
	private Prison				plugin;
	
	PrisonManager(Prison p)
	{
		this.plugin = p;
	}
	
	public void load()
	{
		// Nothing to see here
	}
	
	public void removeSign(SignChangeEvent event)
	{
		event.setCancelled(true);
		event.getBlock().setType(Material.AIR);
		event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.SIGN, 1));
		return;
	}
	
	public boolean handleNewRankupSign(SignChangeEvent event)
	{
		String[] lines = event.getLines();

		event.setLine(0, "&4&mI                   I");
		event.setLine(1, "&cKlik for at");
		event.setLine(2, "&cRankup");
		event.setLine(3, "&4&mI                   I");

		event.getPlayer().sendMessage(ChatColor.AQUA + "Du har placeret et rankup skilt!");

		return true;
	}
	
	public boolean handleNewPrestigeSign(SignChangeEvent event)
	{
		String[] lines = event.getLines();

		event.setLine(0, "&4&mI                   I");
		event.setLine(1, "&cKlik for at gå");
		event.setLine(2, "&cPrestige");
		event.setLine(3, "&4&mI                   I");

		event.getPlayer().sendMessage(ChatColor.AQUA + "Du har placeret et prestige skilt!");

		return true;
	}

	public boolean isNewRankupSign(Block clickedBlock, String[] lines)
	{
		if (clickedBlock.getType() != Material.WALL_SIGN)
		{
			return false;
		}
		
		clickedBlock.getState();
		
		if (!lines[0].equalsIgnoreCase("Rankup"))
		{
			return false;
		}

		return true;
	}
	
	public boolean isRankupSign(Block clickedBlock)
	{
		BlockState state = clickedBlock.getState();

		Sign sign = (Sign) state;

		String[] lines = sign.getLines();

		if (!lines[1].equalsIgnoreCase("§cKlik for at"))
		{
			return false;
		}
		if (!lines[2].equalsIgnoreCase("§cRankup"))
		{
			return false;
		}

		return true;
	}
	
	public boolean isNewPrestigeSign(Block clickedBlock, String[] lines)
	{
		if (clickedBlock.getType() != Material.WALL_SIGN)
		{
			return false;
		}
		
		clickedBlock.getState();

		if (!lines[0].equalsIgnoreCase("Prestige"))
		{
			return false;
		}

		return true;
	}
	
	public boolean isPrestigeSign(Block clickedBlock)
	{
		BlockState state = clickedBlock.getState();

		Sign sign = (Sign) state;

		String[] lines = sign.getLines();

		if (!lines[1].equalsIgnoreCase("§cKlik for at gå"))
		{
			return false;
		}
		if (!lines[2].equalsIgnoreCase("§cPrestige"))
		{
			return false;
		}

		return true;
	}
	
	public boolean isIronDoor(Block clickedBlock)
	{
		if ((clickedBlock == null) || (clickedBlock.getType() != Material.IRON_DOOR))
		{
			return false;
		}
		return true;
	}
	
	public boolean playerCanOpenDoor(Player player, Block clickedBlock)
	{
		if (!player.isOp() && !plugin.getPermissionsManager().hasPermission(player, "prison.irondoor"))
		{
			if (!plugin.getRegionManager().canBuild(player, clickedBlock))
			{
				return false;
			}
			return true;
		}
		return true;
	}

}