package main.java.dk.doggycraft.dcprison;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.material.Door;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;

public class PrisonManager
{
	private Prison				plugin;
	private Random				random				= new Random();
	private Economy				economy				= null;
	
	PrisonManager(Prison p)
	{
		this.plugin = p;
	}
	
	/*public String getRankFromSign(Block clickedBlock)
	{
		if (clickedBlock.getType() != Material.WALL_SIGN)
		{
			return null;
		}

		BlockState state = clickedBlock.getState();

		Sign sign = (Sign) state;

		String[] lines = sign.getLines();

		return lines[2];
	}*/
	
	public void load()
	{
		// Nothing to see here
	}
	
	public boolean handleNewRankupSign(SignChangeEvent event)
	{
		String[] lines = event.getLines();

		event.setLine(0, "");
		event.setLine(1, "Klik for at:");
		event.setLine(2, "Rankup!");

		event.getPlayer().sendMessage(ChatColor.AQUA + "You placed a rankup sign!");

		return true;
	}
	
	public boolean handleNewPrestigeSign(SignChangeEvent event)
	{
		String[] lines = event.getLines();

		event.setLine(0, "");
		event.setLine(1, "Klik for at:");
		event.setLine(2, "Gå i Prestige!");

		event.getPlayer().sendMessage(ChatColor.AQUA + "You placed a prestige sign!");

		return true;
	}

	public boolean isNewRankupSign(Block clickedBlock, String[] lines)
	{
		this.plugin.logDebug("Checking if the sign is a new Rankup sign");
		if (clickedBlock.getType() != Material.WALL_SIGN)
		{
			this.plugin.logDebug("Not a sign");
			return false;
		}
		
		clickedBlock.getState();
		
		if (!lines[0].equalsIgnoreCase("Rankup"))
		{
			this.plugin.logDebug("Not written rankup on the first line: " + lines[0]);
			return false;
		}

		return true;
	}
	
	public boolean isRankupSign(Block clickedBlock)
	{
		this.plugin.logDebug("Checking the lines");
		BlockState state = clickedBlock.getState();

		Sign sign = (Sign) state;

		String[] lines = sign.getLines();

		if (!lines[1].equalsIgnoreCase("Klik for at:"))
		{
			this.plugin.logDebug("Not written Klik for at: on the second line: " + lines[1]);
			return false;
		}
		if (!lines[2].equalsIgnoreCase("Rankup!"))
		{
			this.plugin.logDebug("Not written Rankup! on the third line: " + lines[2]);
			return false;
		}

		return true;
	}
	
	public boolean isNewPrestigeSign(Block clickedBlock, String[] lines)
	{
		this.plugin.logDebug("It isn't... Checking if the sign is a new Prestige sign");
		if (clickedBlock.getType() != Material.WALL_SIGN)
		{
			this.plugin.logDebug("Not a sign");
			return false;
		}
		
		clickedBlock.getState();

		if (!lines[0].equalsIgnoreCase("Prestige"))
		{
			this.plugin.logDebug("Not written Prestige on the first line: " + lines[0]);
			return false;
		}

		return true;
	}
	
	public boolean isPrestigeSign(Block clickedBlock)
	{
		BlockState state = clickedBlock.getState();

		Sign sign = (Sign) state;

		String[] lines = sign.getLines();

		if (!lines[1].equalsIgnoreCase("Klik for at:"))
		{
			this.plugin.logDebug("Not written Klik for at: on the second line: " + lines[1]);
			return false;
		}
		if (!lines[2].equalsIgnoreCase("Gå i Prestige!"))
		{
			this.plugin.logDebug("Not written Gå i Prestige! on the third line: " + lines[2]);
			return false;
		}

		return true;
	}
	
	public boolean isIronDoor(Block clickedBlock)
	{
		if ((clickedBlock == null) || (clickedBlock.getType() != Material.IRON_DOOR))
		{
			this.plugin.logDebug("Not an iron door");
			return false;
		}
		return true;
	}
	
	public boolean playerCanOpenDoor(Player player, Block clickedBlock)
	{
		this.plugin.logDebug("is the player an operator, or does the player have the irondoor perm?");
		if (!player.isOp() && !plugin.getPermissionsManager().hasPermission(player, "prison.irondoor"))
		{
			this.plugin.logDebug("No perms, checking region...");
			if (!plugin.getRegionManager().canBuild(player, clickedBlock))
			{
				this.plugin.logDebug("Player can't build there, return false");
				return false;
			}
			this.plugin.logDebug("Player can build there, return true");
			return true;
		}
		this.plugin.logDebug("Player has perms or operator, sesam open up!");
		return true;
	}

}