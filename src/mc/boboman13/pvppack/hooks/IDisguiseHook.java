package mc.boboman13.pvppack.hooks;

import java.util.logging.Level;

import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import de.robingrether.idisguise.api.DisguiseAPI;

import mc.boboman13.pvppack.PVPPack;
import mc.boboman13.pvppack.api.PlayerHitPlayerEvent;
import mc.boboman13.pvppack.messages.Message;

public class IDisguiseHook implements PluginHook, Listener{

	private PVPPack plugin;
	
	private boolean isEnabled = false;
	
	public IDisguiseHook(PVPPack plugin){
		this.plugin = plugin;
		
		enable();
	}
	
	@Override
	public void enable() {
		// Hook is already enabled
		if(isEnabled) return;
		
		if(plugin.getServer().getPluginManager().getPlugin("iDisguise") == null){
			plugin.log("iDisguise not found, disabling hook.", Level.INFO);
		} else {
			plugin.log("iDisguise found, hooking...", Level.INFO);
			
			plugin.getServer().getPluginManager().registerEvents(this, plugin);
			
			isEnabled = true;
		}
		
	}

	@Override
	public void disable() {
		// Hook is already disabled
		if(!isEnabled) return;
		
		HandlerList.unregisterAll(this);
		
		isEnabled = false;
	}
	
	@EventHandler (ignoreCancelled = true)
	public void onPVP(PlayerHitPlayerEvent e){
		/* Checks whether the hitter is disguised */
		if(DisguiseAPI.isDisguised(e.getDamager())){
			/* Cancel the event and tell them bad boy! */
			e.getDamager().sendMessage(plugin.getMessageHandler().getMessage(Message.CANNOT_PVP_WHILE_DISGUISED));
			
			e.setCancelled(true);
		}
		
	}
	
}
