package dk.doggycraft.dcprison;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Openable;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.material.MaterialData;
/* Removed open IRON_DOOR functionality...
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.material.Door;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;
*/

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
			if (!player.isOp() && !plugin.getPermissionsManager().hasPermission(player, "prison.placesign"))
			{
				plugin.getPrisonManager().removeSign(event);
				
				plugin.sendInfo(player, ChatColor.RED + "Du prøvede at placere et Prison skilt, men du kan ikke placere Prison skilte");

				return;
			}
			if (!plugin.getPrisonManager().handleNewRankupSign(event))
			{
				plugin.getPrisonManager().removeSign(event);
				return;
			}
		}
		else if (plugin.getPrisonManager().isNewPrestigeSign(event.getBlock(), event.getLines()))
		{
			if (!player.isOp() && !plugin.getPermissionsManager().hasPermission(player, "prison.placesign"))
			{
				plugin.getPrisonManager().removeSign(event);

				plugin.sendInfo(player, ChatColor.RED + "Du prøvede at placere et Prison skilt, men du kan ikke placere Prison skilte");

				return;
			}
			if (!plugin.getPrisonManager().handleNewPrestigeSign(event))
			{
				plugin.getPrisonManager().removeSign(event);
				return;
			}
		}
		else if (plugin.getPrisonManager().isNewPromoteSign(event.getBlock(), event.getLines()))
		{
			if (!player.isOp() && !plugin.getPermissionsManager().hasPermission(player, "prison.placesign"))
			{
				plugin.getPrisonManager().removeSign(event);

				plugin.sendInfo(player, ChatColor.RED + "Du prøvede at placere et Prison skilt, men du kan ikke placere Prison skilte");

				return;
			}
			if (!plugin.getPrisonManager().handleNewPromoteSign(event))
			{
				plugin.getPrisonManager().removeSign(event);
				return;
			}
		}
		else
		{
			// Safe checks...
			if (plugin.getPrisonManager().isRankupSign(event.getBlock()))
			{
				if (!player.isOp() && !plugin.getPermissionsManager().hasPermission(player, "prison.placesign"))
				{
					plugin.getPrisonManager().removeSign(event);

					plugin.sendInfo(player, ChatColor.RED + "Du prøvede at placere et Prison skilt, men du kan ikke placere Prison skilte");

					return;
				}
				if (!plugin.getPrisonManager().handleNewRankupSign(event))
				{
					plugin.getPrisonManager().removeSign(event);
					
					return;
				}
			}
			else if (plugin.getPrisonManager().isPrestigeSign(event.getBlock()))
			{
				if (!player.isOp() && !plugin.getPermissionsManager().hasPermission(player, "prison.placesign"))
				{
					plugin.getPrisonManager().removeSign(event);

					plugin.sendInfo(player, ChatColor.RED + "Du prøvede at placere et Prison skilt, men du kan ikke placere Prison skilte");

					return;
				}
				if (!plugin.getPrisonManager().handleNewPrestigeSign(event))
				{
					plugin.getPrisonManager().removeSign(event);
					
					return;
				}
			}
			else if (plugin.getPrisonManager().isPromoteSign(event.getBlock()))
			{
				if (!player.isOp() && !plugin.getPermissionsManager().hasPermission(player, "prison.placesign"))
				{
					plugin.getPrisonManager().removeSign(event);

					plugin.sendInfo(player, ChatColor.RED + "Du prøvede at placere et Prison skilt, men du kan ikke placere Prison skilte");

					return;
				}
				if (!plugin.getPrisonManager().handleNewPromoteSign(event))
				{
					plugin.getPrisonManager().removeSign(event);

					return;
				}
			}
			else
			{
				return;
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();

		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			return;
		}
		
		if (!event.getHand().equals(EquipmentSlot.HAND))
		{
			return;
		}
		
		Block clickedBlock = event.getClickedBlock();
		if (clickedBlock == null)
		{
			return;
		}
		
		plugin.logDebug("Block:" + event.getClickedBlock().getType());
		if (clickedBlock.getType() != Material.OAK_WALL_SIGN)
		{
			// Remove the Open IRON_DOOR Functionality - not working with 1.13.2 anyway
			if ((clickedBlock.getType() != Material.IRON_DOOR))
			{
				return;
			}
			plugin.logDebug("can the player open?");
			if (!plugin.getPrisonManager().playerCanOpenDoor(player, clickedBlock))
			{
				return;
			}
			plugin.logDebug("yes");

			//org.bukkit.block.data.type.Door()

			Door door = (Door) clickedBlock.getBlockData();

//			if (door.isTopHalf())
//			{
//				clickedBlock = clickedBlock.getRelative(BlockFace.DOWN);
//				door = (Door)clickedBlock.getBlockData();
//			}

			if (!door.isOpen())
			{
				door.setOpen(true);
				player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 10, 1);

				clickedBlock.setBlockData(door);
			}
			else
			{
				door.setOpen(false);
				player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 10, 1);

				clickedBlock.setBlockData(door);
			}
		}
		else if (plugin.getPrisonManager().isRankupSign(event.getClickedBlock()))
		{
			Bukkit.dispatchCommand(player,"rankup");
		}
		else if (plugin.getPrisonManager().isPrestigeSign(event.getClickedBlock()))
		{
			Bukkit.dispatchCommand(player,"prestige");
		}
		else if (plugin.getPrisonManager().isPromoteSign(event.getClickedBlock()))
		{
			plugin.logDebug(plugin.getPermissionsManager().getGroup(player.getName()));

			if (plugin.getPermissionsManager().getGroup(player.getName()).toLowerCase().contains("c-vagt")) {
				int price = 10000000;
				if (plugin.getEconomyManager().has(player, price))
				{
					plugin.getEconomyManager().withdrawPlayer(player, price);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " promote vagt");
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "warp " + player.getName() + " vagtcentral");

					plugin.sendInfo(player, ChatColor.GOLD + "Tillykke, og velkommen til som en vagt i Block B!");
				}
				else {
					plugin.sendInfo(player, ChatColor.RED + "Du skal have $" + price + " for at kunne blive promoted til B-Vagt!");
				}
			}
			else if (plugin.getPermissionsManager().getGroup(player.getName()).toLowerCase().contains("b-vagt")) {
				int price = 40000000;
				if (plugin.getEconomyManager().has(player, price))
				{
					plugin.getEconomyManager().withdrawPlayer(player, price);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " promote vagt");
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "warp " + player.getName() + " vagtcentral");

					plugin.sendInfo(player, ChatColor.GOLD + "Tillykke, og velkommen til som en vagt i Block A!");
				}
				else {
					plugin.sendInfo(player, ChatColor.RED + "Du skal have $" + price + " for at kunne blive promoted til A-Vagt!");
				}
			}
			else {
				plugin.sendInfo(player, ChatColor.RED + "Kun C-Vagter og B-Vagter kan blive promotede!");
			}
		}
	}
}