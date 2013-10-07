package mc.boboman13.pvppack.hooks;

import java.util.logging.Level;

import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.kitteh.vanish.VanishManager;
import org.kitteh.vanish.staticaccess.VanishNoPacket;

import mc.boboman13.pvppack.PVPPack;
import mc.boboman13.pvppack.api.PlayerHitPlayerEvent;
import mc.boboman13.pvppack.messages.Message;

public class VanishNoPacketHook implements Listener, PluginHook{

	private PVPPack plugin;
	private VanishManager vm;
	
	private boolean isEnabled = false;
	
	public VanishNoPacketHook(PVPPack plugin){
		this.plugin = plugin;
		
		enable();
	}
	
	@Override
	public void enable() { // Enables the hook
		if(isEnabled) return;
		
		/* If the plugin isn't loaded */
		if(plugin.getServer().getPluginManager().getPlugin("VanishNoPacket") == null){
			plugin.log("VanishNoPacket not found, disabling hook.", Level.INFO);
		/* If the plugin is loaded */
		} else {
			plugin.log("VanishNoPacket found, hooking...", Level.INFO);
			
			try {
				vm = VanishNoPacket.getManager();
			} catch (Exception e) {
				plugin.log("Error loading VanishNoPacket, hook not loaded.", Level.SEVERE);
				return;
			}
			
			this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
		}
		
	}

	@Override
	public void disable() { // Disables the hook
		if(!isEnabled) return;
		
		HandlerList.unregisterAll(this);
	}
	
	@EventHandler (ignoreCancelled = true)
	public void onHit(PlayerHitPlayerEvent e){
		if(vm.isVanished(e.getDamager())){
			e.setCancelled(true);
			e.getDamager().sendMessage(plugin.getMessageHandler().getMessage(Message.CANNOT_PVP_WHILE_VANISHED));
		}
	}
	
}
