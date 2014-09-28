package mc.boboman13.pvppack;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import mc.boboman13.pvppack.api.PVPPackAPI;
import mc.boboman13.pvppack.commands.CommandManager;
import mc.boboman13.pvppack.debug.DebugCommandListener;
import mc.boboman13.pvppack.hooks.BlockDisguiseHook;
import mc.boboman13.pvppack.hooks.DisguiseCraftHook;
import mc.boboman13.pvppack.hooks.IDisguiseHook;
import mc.boboman13.pvppack.hooks.PluginHook;
import mc.boboman13.pvppack.hooks.VanishNoPacketHook;
import mc.boboman13.pvppack.listeners.CombatListener;
import mc.boboman13.pvppack.listeners.PlayerListener;
import mc.boboman13.pvppack.messages.Message;
import mc.boboman13.pvppack.messages.MessageHandler;
import mc.boboman13.pvppack.runnables.LoadMetrics;
import mc.boboman13.pvppack.updater.Updater;
import mc.boboman13.pvppack.updater.Updater.UpdateResult;
import mc.boboman13.pvppack.updater.Updater.UpdateType;

import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * PVPPack is a plugin designed to make the player vs. player experience in Minecraft legit and simple.
 * Features include anti-combat logging and teleporting, hooking plugins to disable pvp during
 * special times, and more.
 * 
 * @author boboman13
 */
public class PVPPack extends JavaPlugin{
	
	private PluginManager pm;
	private ConfigLoader config;
	private TagManager tm;
	private MessageHandler mh;
	private Updater updater;
	private CommandManager cm;
	
	private CombatListener cl;
	private PlayerListener pl;
	
	private PVPPackAPI api;
	
	private List<PluginHook> hooks = new ArrayList<PluginHook>();
	
	/**
	 * Called when PVPPack is enabled.
	 */
	@Override
	public void onEnable(){
		api = new PVPPackAPI(this);
		
		pm = getServer().getPluginManager();
		cl = new CombatListener(this);
		pl = new PlayerListener(this);
		pm.registerEvents(cl, this);
		pm.registerEvents(pl, this);
		
		saveDefaultConfig();
		config = new ConfigLoader(getConfig());
		mh = new MessageHandler(this);
		tm = new TagManager(this);
		cm = new CommandManager(this);
		this.getCommand("pvppack").setExecutor(cm);
		
		if(config.getHookDisguiseCraft()) hooks.add(new DisguiseCraftHook(this));
		if(config.getHookVanishNoPacket()) hooks.add(new VanishNoPacketHook(this));
		if(config.getHookIDisguise()) hooks.add(new IDisguiseHook(this));
		if(config.getHookBlockDisguise()) hooks.add(new BlockDisguiseHook(this));
		
		if(config.getDebug()) pm.registerEvents(new DebugCommandListener(this), this);
		
		if(config.getUseMetrics()){
			this.getServer().getScheduler().runTaskAsynchronously(this, new LoadMetrics(this));
		}
		
		if(config.getAutoUpdate()){
			log(mh.getMessage(Message.START_UPDATE_CHECK), Level.INFO);
			
			updater = new Updater(this, "pvppack", this.getFile(), UpdateType.NO_DOWNLOAD, true);
			
			if(updater.getResult() == UpdateResult.UPDATE_AVAILABLE){
				log(mh.getMessage(Message.UPDATE_AVAILABLE)+updater.getLatestVersionString(), Level.INFO);
				
				/* Sets up a join listener for players when the plugin is outdated */
				pm.registerEvents(new Listener(){
					
					@EventHandler
					public void onJoin(PlayerJoinEvent e){
						/* If the player is OP and an update is available */
						if(e.getPlayer().isOp() || e.getPlayer().hasPermission("pvppack.notify")){
							e.getPlayer().sendMessage(mh.getMessage(Message.UPDATE_AVAILABLE)+updater.getLatestVersionString()+".");
						}
					}
					
				}, this);
			}
		}
		
		log(mh.getMessage(Message.ENABLED)+ getDescription().getVersion(), Level.INFO);
	}
	
	/**
	 * Called when PVPPack is disabled
	 */
	@Override
	public void onDisable(){
		HandlerList.unregisterAll(cl);
		HandlerList.unregisterAll(pl);
	}
	
	/**
	 * Logs the message specified into the console
	 * 
	 * @param message The message to be logged
	 * @param level The level to which to log
	 */
	public void log(String message, Level level){
		this.getLogger().log(level, message);
	}
	
	/**
	 * Gets the TagManager for tagging players
	 * 
	 * @return The TagManager instance
	 */
	public TagManager getTagManager(){
		return tm;
	}
	
	/**
	 * Gets the list of plugin hooks for PVPPack (some may be disabled)
	 * 
	 * @return The list of active hooks
	 */
	public List<PluginHook> getActiveHooks(){
		return hooks;
	}
	
	/**
	 * Gets the PVPPackAPI
	 * 
	 * @return The instance of the PVPPackAPI
	 */
	public PVPPackAPI getAPI(){
		return api;
	}
	
	/**
	 * Gets the ConfigLoader instance of this plugin
	 * 
	 * @return The ConfigLoader instance
	 */
	public ConfigLoader getConfigLoader(){
		return config;
	}
	
	/**
	 * Gets the MessageHandler
	 * 
	 * @return The MessageHandler instance
	 */
	public MessageHandler getMessageHandler(){
		return mh;
	}
}
