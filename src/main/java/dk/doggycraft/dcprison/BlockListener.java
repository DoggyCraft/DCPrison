package main.java.dk.doggycraft.dcprison;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
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

		plugin.logDebug("Hand clicked with:" + event.getHand());
		if (!event.getHand().equals(EquipmentSlot.HAND))
		{
			return;
		}
		
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			return;
		}
		plugin.logDebug("it's a rightclick");
		if (event.getClickedBlock() == null)
		{
			return;
		}
		plugin.logDebug("Block:" + event.getClickedBlock().getType());
		if (event.getClickedBlock().getType() != Material.WALL_SIGN)
		{
			if ((event.getClickedBlock().getType() != Material.LEGACY_IRON_DOOR) && (event.getClickedBlock().getType() != Material.IRON_DOOR))
			{
				return;
			}
			plugin.logDebug("can the player open?");
			if (!plugin.getPrisonManager().playerCanOpenDoor(player, event.getClickedBlock()))
			{
				return;
			}
			plugin.logDebug("yes");
			/*BlockState state = event.getClickedBlock().getState();
			Door door = (Door) state.getData();
			plugin.logDebug("Door is tophalf:" + door.isTopHalf());
			plugin.logDebug("Door hinge:" + door.getHinge());
			if (door.isTopHalf())
			{
				state = event.getClickedBlock().getRelative(BlockFace.DOWN).getState();
			}
			Openable o = (Openable) state.getData();*/
			
			Block clickedBlock = event.getClickedBlock();
			BlockData data = event.getClickedBlock().getBlockData();
			Door door = (Door)data;
			
			if (door.getHalf() == Half.TOP)
			{
				clickedBlock = clickedBlock.getRelative(BlockFace.DOWN);
				door = (Door)clickedBlock.getBlockData();
			}
			
			https://github.com/Psychlist1972/Minecraft-DoorCloser/blob/master/src/main/java/net/tenrem/doorcloser/InteractListener.java

			if (data instanceof Openable)
			{
				if (!((Openable)data).isOpen())
				{
					((Openable)data).setOpen(true);

					event.getClickedBlock().setBlockData(data);
				}
				else
				{
					((Openable)data).setOpen(false);

					event.getClickedBlock().setBlockData(data);
				}
			}
			else
			{
				//nothing
			}
			if (o.isOpen())
			{
				o.setOpen(false);
				state.setData((MaterialData) o);
				state.update();
				/* Plays a sound */
				player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 10, 1);
			}
			else
			{
				o.setOpen(true);
				state.setData((MaterialData) o);
				state.update();
				/* Plays a sound */
				player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 10, 1);
			}
			return;
		}
		if (plugin.getPrisonManager().isRankupSign(event.getClickedBlock()))
		{
			Bukkit.dispatchCommand(player,"rankup");
			return;
		}
		if (plugin.getPrisonManager().isPrestigeSign(event.getClickedBlock()))
		{
			Bukkit.dispatchCommand(player,"prestige");
			return;
		}
		else
		{
			return;
		}
	}
}