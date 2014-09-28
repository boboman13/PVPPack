package mc.boboman13.pvppack.debug;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import mc.boboman13.pvppack.PVPPack;
import mc.boboman13.pvppack.messages.Message;

public class DebugCommandListener implements Listener{

	private PVPPack plugin;
	
	public DebugCommandListener(PVPPack plugin){
		this.plugin = plugin;
	}
	
	@EventHandler (priority = EventPriority.LOW)
	public void onCommand(PlayerCommandPreprocessEvent e){
		if(e.getMessage().toLowerCase().startsWith("/tag")){
			Player player = e.getPlayer();
			if(!player.hasPermission("pvppack.debug.tag")){
				player.sendMessage(plugin.getMessageHandler().getMessage(Message.NOT_SUFFICIENT_PERMISSIONS));
				e.setCancelled(true);
				return;
			}
			
			String[] args = e.getMessage().split(" ");
			if(args.length == 1){
				player.sendMessage(plugin.getMessageHandler().getMessage(Message.CANNOT_FIND_PLAYER));
				player.sendMessage(plugin.getMessageHandler().getMessage(Message.TAGGING_COMMAND_USAGE));
				e.setCancelled(true);
				return;
			}
			Player toTag = plugin.getServer().getPlayer(args[1]);
			if(toTag == null){
				player.sendMessage(plugin.getMessageHandler().getMessage(Message.CANNOT_FIND_PLAYER));
				e.setCancelled(true);
				return;
			}
			
			plugin.getTagManager().tag(toTag);
			player.sendMessage(plugin.getMessageHandler().getMessage(Message.TAGGED_PLAYER));
			e.setCancelled(true);
		}
	}
	
}
