package mc.boboman13.pvppack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mc.boboman13.pvppack.listeners.CombatListener;
import mc.boboman13.pvppack.messages.Message;
import mc.boboman13.pvppack.runnables.PlayerRemover;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class TagManager {

	private PVPPack plugin;
	
	private List<Player> tagged = new ArrayList<Player>();
	private HashMap<Player, Integer> runnables = new HashMap<Player, Integer>();
	
	public TagManager(PVPPack plugin){
		this.plugin = plugin;
		
		plugin.getServer().getPluginManager().registerEvents(new CombatListener(plugin), plugin);
	}
	
	/**
	 * Checks whether a player is tagged by this plugin
	 * 
	 * @param The player to check for a tag
	 * @return Whether the specified player is tagged
	 */
	public boolean isTagged(Player player){
		return tagged.contains(player);
	}
	
	/**
	 * Tags a player for combat. If the player is already tagged, it will do nothing.
	 * 
	 * @param player The player to tag
	 */
	public void tag(Player player){
		if(!isInTaggableWorld(player)) return;
		
		if(!isTagged(player)){
			tagged.add(player);
			if(plugin.getConfigLoader().getDisableFlyPVP()) player.setAllowFlight(false);
			player.sendMessage(plugin.getMessageHandler().getMessage(Message.TAGGED));
			
			BukkitTask task = plugin.getServer().getScheduler().runTaskLater(plugin, new PlayerRemover(tagged, player, runnables, plugin), 20*plugin.getConfigLoader().getTagTime());
			runnables.put(player, task.getTaskId());
		}
	}
	
	/**
	 * Untags a player from combat. If a player isn't tagged, it won't do anything.
	 * 
	 * @param player The player to untag
	 */
	public void untag(Player player){
		if(isTagged(player)){
			tagged.remove(player);
			plugin.getServer().getScheduler().cancelTask(runnables.get(player));
			
			player.sendMessage(plugin.getMessageHandler().getMessage(Message.UNTAGGED));
		}
	}
	
	/**
	 * Renews the tag of the specified player. If the player isn't tagged, it will tag them.
	 * 
	 * @param player The player to renew their tag
	 */
	public void renewTag(Player player){
		if(!isInTaggableWorld(player)) return;
		
		if(!isTagged(player)){
			tag(player);
		} else {
			if(plugin.getConfigLoader().getDisableFlyPVP()) player.setAllowFlight(false);
			
			Integer runnable = runnables.get(player);
			if(runnable != null) plugin.getServer().getScheduler().cancelTask(runnables.get(player));
			
			BukkitTask task = plugin.getServer().getScheduler().runTaskLater(plugin, new PlayerRemover(tagged, player, runnables, plugin), 20*plugin.getConfigLoader().getTagTime());
			runnables.put(player, task.getTaskId());
		}
	}
	
	/* START OF PRIVATE SECTION (NON-API) */
	
	private boolean isInTaggableWorld(Player player){
		String world = player.getLocation().getWorld().getName();
		
		return plugin.getConfigLoader().getAffectedWorlds().contains(world);
	}
	
}