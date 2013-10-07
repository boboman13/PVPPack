package mc.boboman13.pvppack.hooks;

import java.util.logging.Level;

import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import pgDev.bukkit.DisguiseCraft.DisguiseCraft;
import pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI;

import mc.boboman13.pvppack.PVPPack;
import mc.boboman13.pvppack.api.PlayerHitPlayerEvent;
import mc.boboman13.pvppack.messages.Message;

public class DisguiseCraftHook implements Listener, PluginHook{

	private PVPPack plugin;
	private DisguiseCraftAPI DCAPI;
	
	private boolean isEnabled = false;
	
	public DisguiseCraftHook(PVPPack plugin){
		this.plugin = plugin;
		
		enable();
	}
	
	@Override
	public void enable() { // Enables the hook
		if(isEnabled) return;
		
		/* If DisguiseCraft isn't loaded */
		if(plugin.getServer().getPluginManager().getPlugin("DisguiseCraft") == null){
			plugin.log("DisguiseCraft not found, disabling hook.", Level.INFO);
		/* If DisguiseCraft is loaded */
		} else {
			plugin.log("DisguiseCraft found, hooking...", Level.INFO);
			
			DCAPI = DisguiseCraft.getAPI();
			this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
			isEnabled = true;
		}
	}

	@Override
	public void disable() { // Disables the hook
		if(!isEnabled) return;
		
		/* Unregisters all events in this class */
		HandlerList.unregisterAll(this);
		isEnabled = false;
	}
	
	@EventHandler (ignoreCancelled = true)
	public void onHit(PlayerHitPlayerEvent e){
		/* If the player is disguised */
		if(DCAPI.isDisguised(e.getDamager())){
			/* Cancels the hit because the player is disguised */
			e.setCancelled(true);
			e.getDamager().sendMessage(plugin.getMessageHandler().getMessage(Message.CANNOT_PVP_WHILE_DISGUISED));
		}
	}
	
}
