package mc.boboman13.pvppack.listeners;

import java.util.List;

import mc.boboman13.pvppack.messages.Message;
import mc.boboman13.pvppack.PVPPack;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerListener implements Listener{

	private PVPPack plugin;
	
	public PlayerListener(PVPPack plugin){
		this.plugin = plugin;
	}
	
	// When a player teleports
	@EventHandler
	public void onTeleport(PlayerTeleportEvent e){
		/* If TP is allowed */
		if(plugin.getConfigLoader().getAllowTPDuringCombat()){
			return;
		}
		
		/* Checks if it is an ender pearl */
		if(e.getCause() == TeleportCause.ENDER_PEARL || e.getCause() == TeleportCause.NETHER_PORTAL || e.getCause() == TeleportCause.END_PORTAL){
			return;
		}
		
		Player player = e.getPlayer();
		
		/* Checks if the player is tagged */
		if(plugin.getTagManager().isTagged(player)){
			e.setCancelled(true);
			player.sendMessage(plugin.getMessageHandler().getMessage(Message.CANNOT_TELEPORT));
		}
	}
	
	// When a player logs out
	@EventHandler
	public void onLeave(PlayerQuitEvent e){
		Player player = e.getPlayer();
		
		/* Checks if the player is tagged */
		if(plugin.getTagManager().isTagged(player)){
			PlayerInventory inv = player.getInventory();
			
			/* Broadcasts the message for all players to hear, if allowed */
			if(plugin.getConfigLoader().getBroadcastOnLog()){
				String toBroadcast = plugin.getMessageHandler().getMessage(Message.LOGGED_OUT_OF_COMBAT);
				toBroadcast = toBroadcast.replaceFirst("[PLAYER]", player.getName());
				
				
				plugin.getServer().broadcastMessage(toBroadcast);
			}
			
			/* If it is set to "kill" the player, kill it first */
			if(plugin.getConfigLoader().getKillOnCombatLog()){
				player.setHealth(0);
				return;
			}
			
			// TODO This is a bug that needs to be fixed, this doesn't work
			/* Now for the configurable percent of the items to drop, we drop them from the inventory */
			for(ItemStack item : inv.getContents()){
				if(Math.random() < plugin.getConfigLoader().getCombatLogDropPercentage() && item != null){ // If the random double (0.0 - 1.0) was less than 0.65 AND the item isn't null
					inv.remove(item);
					player.getLocation().getWorld().dropItemNaturally(player.getLocation(), item);
				}
			}
			
			/* Drops all armor */
			
			if(plugin.getConfigLoader().getDropArmor()){
				for (ItemStack item : inv.getArmorContents()){
					if( !(item.getType() == null || item.getType() == Material.AIR) ) player.getLocation().getWorld().dropItemNaturally(player.getLocation(), item);
				}
			}
			
			inv.setArmorContents(null);
			
		}
		
	}
	
	// When a player dies
	@EventHandler (priority = EventPriority.MONITOR)
	public void onDeath(PlayerDeathEvent e){
		Player player = e.getEntity();
		
		/* Checks if the player is tagged */
		if(plugin.getTagManager().isTagged(player)){
			plugin.getTagManager().untag(player);
		}
	}
	
	// When a player sends a command
	@EventHandler (ignoreCancelled = true)
	public void onPlayerCommand(PlayerCommandPreprocessEvent e){
		/* If the player isn't tagged, just disregard this event */
		if(!plugin.getTagManager().isTagged(e.getPlayer())){
			return;
		}
		
		String command = e.getMessage().toLowerCase();
		List<String> disabledCommands = plugin.getConfigLoader().getDisabledCommands();
		
		for(String dc : disabledCommands){
			/* If the command starts with any thing from the config */
			if(command.startsWith(dc.toLowerCase())){
				Player player = e.getPlayer();
				player.sendMessage(plugin.getMessageHandler().getMessage(Message.CANNOT_USE_COMMAND_IN_COMBAT));
				e.setCancelled(true);
			}
		}
	}
	
}
