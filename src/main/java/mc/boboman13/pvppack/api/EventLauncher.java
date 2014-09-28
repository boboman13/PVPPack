package mc.boboman13.pvppack.api;

import mc.boboman13.pvppack.PVPPack;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;

public class EventLauncher implements Listener {
	
	private PVPPack plugin;
	
	public EventLauncher(PVPPack plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler (priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityDamageEntity(EntityDamageByEntityEvent e){
		
		/* It has to hit a player for us to care */
		if(!(e.getEntity() instanceof Player)) return;
		
		Entity damager = e.getDamager();
		
		Player damaged = (Player)e.getEntity();
		Player pDamager = null;
		
		/* Checks whether damager was either a Player, Arrow, or...? */
		if(damager instanceof Player){
			pDamager = (Player)damager;
		} else if (damager instanceof Arrow){
			Arrow arrow = (Arrow)damager;
			
			/* If was shot from a Player */
			if(arrow.getShooter() instanceof Player){
				pDamager = (Player)arrow.getShooter();
			}
		} else if (damager instanceof Snowball){
			Snowball snowball = (Snowball)damager;
			
			if(snowball.getShooter() instanceof Player){
				pDamager = (Player)snowball.getShooter();
			}
		}
		
		/* If was not able to get it */
		if(pDamager == null) return;
		
		PlayerHitPlayerEvent event = new PlayerHitPlayerEvent(pDamager, damaged, e);
		
		plugin.getServer().getPluginManager().callEvent(event); // Calls the event
		
		/* If the PlayerHitPlayerEvent was cancelled, cancel this event */
		if(event.isCancelled()){
			e.setCancelled(true);
		}
		
	}
	
	public void onPotionSplash(PotionSplashEvent e){
		// TODO finish
	}
	
}
