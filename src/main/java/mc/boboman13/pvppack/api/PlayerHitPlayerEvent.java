package mc.boboman13.pvppack.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerHitPlayerEvent extends Event implements Cancellable{
	private static final HandlerList handlers = new HandlerList();
    
	private Player damaged;
	private Player damager;
	private EntityDamageByEntityEvent event;
	
	private boolean cancelled;
 
    public PlayerHitPlayerEvent(Player damager, Player damaged, EntityDamageByEntityEvent event) {
        this.damager = damager;
        this.damaged = damaged;
        
        this.event = event;
    }
 
    public Player getDamager(){
    	return damager;
    }
 
    public Player getDamaged(){
    	return damaged;
    }
    
    public EntityDamageByEntityEvent getEntityDamageByEntityEvent(){
    	return event;
    }
    
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		cancelled = arg0;
	}
}
