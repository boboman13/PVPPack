package mc.boboman13.pvppack.commands;

import mc.boboman13.pvppack.PVPPack;
import mc.boboman13.pvppack.messages.Message;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor{

	private PVPPack plugin;
	
	public CommandManager(PVPPack plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		/* All commands that start with "/pvppack" end up here */
		
		/* If there were no arguments after /pvppack */
		if(args.length == 0 || args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("help")){
			// TODO: multi language support
			
			sender.sendMessage(ChatColor.AQUA+"()----------( "+ChatColor.RED+ChatColor.ITALIC+"PVPPack"+ChatColor.AQUA+" )----------()");
			sender.sendMessage(ChatColor.GREEN+" - /pvppack "+ChatColor.DARK_GREEN+"h"+ChatColor.GREEN+"elp"+ChatColor.BLUE+" | View the help menu.");
			sender.sendMessage(ChatColor.GREEN+" - /pvppack "+ChatColor.DARK_GREEN+"r"+ChatColor.GREEN+"eload"+ChatColor.BLUE+" | Reload the plugin.");
			return true;
		}
		
		/* If it was /pvppack reload OR /pvppack r */
		if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r")){
			/* Need to check for permission if it is a player */
			if(sender instanceof Player && !sender.hasPermission("pvppack.reload")){
				sender.sendMessage(plugin.getMessageHandler().getMessage(Message.NOT_SUFFICIENT_PERMISSIONS));
				return true;
			}
			
			plugin.getConfigLoader().reloadValues();
			plugin.getMessageHandler().reloadValues();
			sender.sendMessage(plugin.getMessageHandler().getMessage(Message.PLUGIN_RELOADED));
			return true;
		}
		
		/* Command wasn't recognized */
		sender.sendMessage(plugin.getMessageHandler().getMessage(Message.COMMAND_NOT_RECOGNIZED));
		return true;
	}

}
