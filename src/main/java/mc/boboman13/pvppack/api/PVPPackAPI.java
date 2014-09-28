package mc.boboman13.pvppack.api;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import mc.boboman13.pvppack.PVPPack;
import mc.boboman13.pvppack.TagManager;
import mc.boboman13.pvppack.hooks.PluginHook;
import mc.boboman13.pvppack.messages.Locale;

public class PVPPackAPI {

	private PVPPack plugin;
	//private EventLauncher launcher;
	
	public PVPPackAPI(PVPPack plugin){
		this.plugin = plugin;
		
		/* Sets up the EventLauncher for event API */
		new EventLauncher(plugin);
	}
	
	/**
	 * Gets the PVPPackAPI
	 * 
	 * @return The API instance of the PVPPack plugin, throws a PVPPackLoadingException if there was an error loading the API
	 * @throws PVPPackLoadingException
	 */
	public static PVPPackAPI getAPI() throws PVPPackLoadingException{
		Plugin pvpPlugin = Bukkit.getServer().getPluginManager().getPlugin("PVPPack");
		if(pvpPlugin == null){
			throw new PVPPackLoadingException();
		} else if ( !(pvpPlugin instanceof PVPPack) ){
			throw new PVPPackLoadingException();
		}
		return ((PVPPack)pvpPlugin).getAPI();
	}
	
	/**
	 * Gets the plugin hooks. They are only included if they were allowed in the config.yml for PVPPack
	 * 
	 * @return PluginHooks that were originally activated
	 */
	public List<PluginHook> getHooks(){
		return plugin.getActiveHooks();
	}
	
	/**
	 * Gets the TagManager instance
	 * 
	 * @return TagManager instance
	 */
	public TagManager getTagManager(){
		return plugin.getTagManager();
	}
	
	/**
	 * Gets the Locale being used by PVPPack
	 * 
	 * @return The locale currently being used
	 */
	public Locale getLocale(){
		return plugin.getMessageHandler().getLocale();
	}
}
