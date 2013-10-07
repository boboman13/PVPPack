package mc.boboman13.pvppack.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import mc.boboman13.pvppack.PVPPack;
import mc.boboman13.pvppack.api.PlayerHitPlayerEvent;
import mc.boboman13.pvppack.messages.Message;

public class CombatListener implements Listener{

	private PVPPack plugin;
	
	public CombatListener(PVPPack plugin){
		this.plugin = plugin;
	}
	
	@EventHandler (priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPVP(PlayerHitPlayerEvent e){
		Player damager = e.getDamager();
		
		/* If the player is in Creative and hit the person, the option is also on */
		if(plugin.getConfigLoader().getDisableCreativePVP() && damager.getGameMode() == GameMode.CREATIVE){
			damager.sendMessage(plugin.getMessageHandler().getMessage(Message.CANNOT_PVP_IN_CREATIVE));
			e.setCancelled(true);
		}
		
		/* Just simply renew each players tag */
		plugin.getTagManager().renewTag(e.getDamaged());
		plugin.getTagManager().renewTag(damager);
	}
	
}
