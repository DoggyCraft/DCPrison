package main.java.dk.doggycraft.dcprison;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.Door;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class BlockListener implements Listener
{
	private Prison	plugin;

	BlockListener(Prison p)
	{
		this.plugin = p;
	}

	@EventHandler
	public void onSignChange(SignChangeEvent event)
	{
		Player player = event.getPlayer();

		if (plugin.getPrisonManager().isNewRankupSign(event.getBlock(), event.getLines()))
		{
			this.plugin.logDebug("It is! Does the player have OP or the perms?");
			if (!player.isOp() && !plugin.getPermissionsManager().hasPermission(player, "prison.placesign"))
			{
				this.plugin.logDebug("No.. remove it");
				event.setCancelled(true);
				event.getBlock().setType(Material.AIR);
				event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.SIGN, 1));

				plugin.sendInfo(player, ChatColor.RED + "Du prøvede at placere et Prison skilt, men du kan ikke placere Prison skilte");

				return;
			}

			this.plugin.logDebug("Perms and all that is good, format the sign to fit requirements");
			if (!plugin.getPrisonManager().handleNewRankupSign(event))
			{
				event.setCancelled(true);
				event.getBlock().setType(Material.AIR);
				event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.SIGN, 1));
			}
		}
		else if (plugin.getPrisonManager().isNewPrestigeSign(event.getBlock(), event.getLines()))
		{
			this.plugin.logDebug("It is! Does the player have OP or the perms?");
			if (!player.isOp() && !plugin.getPermissionsManager().hasPermission(player, "prison.placesign"))
			{
				this.plugin.logDebug("No.. remove it");
				event.setCancelled(true);
				event.getBlock().setType(Material.AIR);
				event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.SIGN, 1));

				plugin.sendInfo(player, ChatColor.RED + "Du prøvede at placere et Prison skilt, men du kan ikke placere Prison skilte");

				return;
			}

			this.plugin.logDebug("Perms and all that is good, format the sign to fit requirements");
			if (!plugin.getPrisonManager().handleNewPrestigeSign(event))
			{
				event.setCancelled(true);
				event.getBlock().setType(Material.AIR);
				event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.SIGN, 1));
			}
		}
		else
		{
			this.plugin.logDebug("Safe checks...");
			this.plugin.logDebug("Checking if the sign is a real Rankup sign");
			if (plugin.getPrisonManager().isRankupSign(event.getBlock()))
			{
				this.plugin.logDebug("It is! Does the player have OP or the perms?");
				if (!player.isOp() && !plugin.getPermissionsManager().hasPermission(player, "prison.placesign"))
				{
					this.plugin.logDebug("No.. remove it");
					event.setCancelled(true);
					event.getBlock().setType(Material.AIR);
					event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.SIGN, 1));

					plugin.sendInfo(player, ChatColor.RED + "Du prøvede at placere et Prison skilt, men du kan ikke placere Prison skilte");

					return;
				}

				this.plugin.logDebug("Perms and all that is good, format the sign to fit requirements");
				if (!plugin.getPrisonManager().handleNewRankupSign(event))
				{
					event.setCancelled(true);
					event.getBlock().setType(Material.AIR);
					event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.SIGN, 1));
				}
			}
			else if (plugin.getPrisonManager().isPrestigeSign(event.getBlock()))
			{
				this.plugin.logDebug("It is! Does the player have OP or the perms?");
				if (!player.isOp() && !plugin.getPermissionsManager().hasPermission(player, "prison.placesign"))
				{
					this.plugin.logDebug("No.. remove it");
					event.setCancelled(true);
					event.getBlock().setType(Material.AIR);
					event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.SIGN, 1));

					plugin.sendInfo(player, ChatColor.RED + "Du prøvede at placere et Prison skilt, men du kan ikke placere Prison skilte");

					return;
				}

				this.plugin.logDebug("Perms and all that is good, format the sign to fit requirements");
				if (!plugin.getPrisonManager().handleNewPrestigeSign(event))
				{
					event.setCancelled(true);
					event.getBlock().setType(Material.AIR);
					event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.SIGN, 1));
				}
			}
			else
			{
				this.plugin.logDebug("It's not, return");
				return;
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();

		if (!event.getHand().equals(EquipmentSlot.HAND))
		{
			return;
		}
		
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			return;
		}
		this.plugin.logDebug("Rightclick on a block detected...");
		if (event.getClickedBlock() == null)
		{
			this.plugin.logDebug("Block is null, returning");
			return;
		}
		this.plugin.logDebug("Block is not null, what is it and is it a sign?");
		this.plugin.logDebug(String.valueOf(event.getClickedBlock().getType()));
		if (event.getClickedBlock().getType() != Material.WALL_SIGN)
		{
			this.plugin.logDebug("It wasn't, is it an iron door?");
			if (event.getClickedBlock().getType() != Material.IRON_DOOR_BLOCK)
			{
				this.plugin.logDebug("Not an iron door - returning");
				return;
			}
			
			this.plugin.logDebug("Yes it is, should the player be able to open the door?");
			if (!plugin.getPrisonManager().playerCanOpenDoor(player, event.getClickedBlock()))
			{
				this.plugin.logDebug("No - do nothing");
				return;
			}
			this.plugin.logDebug("Hell yea!");
			BlockState state = event.getClickedBlock().getState();
			Door door = (Door) state.getData();
			if (door.isTopHalf())
			{
				state = event.getClickedBlock().getRelative(BlockFace.DOWN).getState();
			}
			Openable o = (Openable) state.getData();
			if (o.isOpen() == true)
			{
				this.plugin.logDebug("Door is open - closing");
				o.setOpen(false);
				state.setData((MaterialData) o);
				state.update();
				event.getPlayer().sendMessage(ChatColor.YELLOW + "Lukkede døren!");
				/* Plays a sound */
				World w = player.getWorld();
				w.playSound(event.getClickedBlock().getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 10, 1);
			}
			else
			{
				this.plugin.logDebug("Door is closed - opening");
				o.setOpen(true);
				state.setData((MaterialData) o);
				state.update();
				event.getPlayer().sendMessage(ChatColor.YELLOW + "Åbnede døren!");
				/* Plays a sound */
				World w = player.getWorld();
				w.playSound(event.getClickedBlock().getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 10, 1);
			}
			return;
		}
		this.plugin.logDebug("It's a sign, checking if it's a rankup sign or not");
		if (plugin.getPrisonManager().isRankupSign(event.getClickedBlock()))
		{
			this.plugin.logDebug("It is indeed a rankup sign, do rankup, no questions asked!");
			Bukkit.dispatchCommand(player,"rankup");
		}
		this.plugin.logDebug("It wasn't, is it a prestige sign?");
		if (plugin.getPrisonManager().isPrestigeSign(event.getClickedBlock()))
		{
			this.plugin.logDebug("It is, doing prestige command...");
			Bukkit.dispatchCommand(player,"prestige");
		}
		else
		{
			this.plugin.logDebug("Just some other sign...");
			return;
		}
	}
}