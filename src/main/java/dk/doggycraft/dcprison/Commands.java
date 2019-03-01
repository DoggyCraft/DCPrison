package main.java.dk.doggycraft.dcprison;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands
{
	private Prison	plugin	= null;

	Commands(Prison p)
	{
		this.plugin = p;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player player = null;

		if ((sender instanceof Player))
		{
			player = (Player) sender;
		}

		if (player == null)
		{
			if (cmd.getName().equalsIgnoreCase("prison"))
			{
				if (args.length == 1)
				{
					if(args[0].equalsIgnoreCase("reload"))
					{
						plugin.reloadSettings();
						this.plugin.log(this.plugin.getDescription().getFullName() + ": Genindlæst konfiguration.");

						return true;
					}
				}
			}

			return true;
		}

		if (cmd.getName().equalsIgnoreCase("prison"))
		{
			if (args.length == 0)
			{
				commandHelp(sender);
				return true;
			}
			if (args.length == 1)
			{
				if (args[0].equalsIgnoreCase("reload"))
				{
					if ((!player.isOp()) && (!player.hasPermission("prison.reload")))
					{
						return false;
					}

					this.plugin.reloadSettings();
					sender.sendMessage(ChatColor.YELLOW + this.plugin.getDescription().getFullName() + ":" + ChatColor.AQUA + " Genindlæst konfiguration.");
					return true;
				}
				if (args[0].equalsIgnoreCase("help"))
				{
					if ((!player.isOp()) && (!player.hasPermission("prison.list")))
					{
						return false;
					}

					commandList(sender);

					return true;
				}
			}
			else
			{

				if (args.length > 2)
				{
					sender.sendMessage(ChatColor.RED + "For mange argumenter! Tjek /prison help");
					return true;
				}
			}
		}
		return true;
	}

	private boolean commandHelp(CommandSender sender)
	{
		sender.sendMessage(ChatColor.YELLOW + "------------------ " + plugin.getDescription().getFullName() + " ------------------");
		sender.sendMessage(ChatColor.AQUA + "Lavet af DoggyCraft");
		sender.sendMessage(ChatColor.AQUA + "");
		sender.sendMessage(ChatColor.AQUA + "Brug " + ChatColor.WHITE + "/prison help" + ChatColor.AQUA + ", for at få en liste over kommandoer");

		return true;
	}

	private boolean commandList(CommandSender sender)
	{
		sender.sendMessage(ChatColor.YELLOW + "------------------ " + this.plugin.getDescription().getFullName() + " ------------------");
		sender.sendMessage(ChatColor.AQUA + "/prison" + ChatColor.WHITE + " - Info om pluginnet");
		if ((sender.isOp()) || (sender.hasPermission("prison.reload")))
		{
			sender.sendMessage(ChatColor.AQUA + "/prison reload" + ChatColor.WHITE + " - Genindlæser DCPrison systemet");
		}

		return true;
	}

}