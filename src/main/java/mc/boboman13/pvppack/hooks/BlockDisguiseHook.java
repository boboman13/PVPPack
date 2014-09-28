package mc.boboman13.pvppack.hooks;

import java.util.logging.Level;

import mc.boboman13.pvppack.PVPPack;
import mc.boboman13.pvppack.api.PlayerHitPlayerEvent;
import mc.boboman13.pvppack.messages.Message;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.ne0nx3r0.blockdisguise.api.BlockDisguiseApi;

public class BlockDisguiseHook implements PluginHook, Listener {

	private boolean isEnabled = false;
	
	private PVPPack plugin;
	private BlockDisguiseApi API;
	
	public BlockDisguiseHook(PVPPack plugin){
		this.plugin = plugin;
		
		enable();
	}
	
	@Override
	public void enable() {
		if(isEnabled) return;

		/* If BlockDisguise isn't loaded */
		if(plugin.getServer().getPluginManager().getPlugin("BlockDisguise") == null){
			plugin.log("BlockDisguise not found, disabling hook.", Level.INFO);
		/* If BlockDisguise is loaded */
		} else {
			plugin.log("BlockDisguise found, hooking...", Level.INFO);
			
			API = (BlockDisguiseApi) plugin.getServer().getPluginManager().getPlugin("BlockDisguise");
			this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
			isEnabled = true;
		}
	}

	@Override
	public void disable() {
		if(!isEnabled) return;

		/* Unregisters all events in this class */
		HandlerList.unregisterAll(this);
		isEnabled = false;
	}

	@EventHandler (priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onHit(PlayerHitPlayerEvent e){
		Player player = e.getDamager();
		
		if(API.isDisguised(player)){
			// Event is cancelled because the player is disguised with BlockDisguise
			e.setCancelled(true);
			player.sendMessage(plugin.getMessageHandler().getMessage(Message.CANNOT_PVP_WHILE_DISGUISED));
		}
	}
	
}
