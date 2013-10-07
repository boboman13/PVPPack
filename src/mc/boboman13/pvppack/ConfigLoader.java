package mc.boboman13.pvppack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigLoader {

	private FileConfiguration config;
	private String messageFile;
	
	private List<String> affectedWorlds;
	private List<String> disabledCommands;
	private boolean debug;
	private boolean killoncombatlog;
	private int tagTime;
	private boolean useMetrics;
	private double combatlogdroppercentage;
	private boolean dropArmor;
	private boolean autoupdate;
	private boolean disableCreativePVP;
	private boolean disableFlyPVP;
	private boolean allowTpDuringCombat;
	private boolean broadcastOnLog;
	
	private boolean hookDC;
	private boolean hookVNP;
	private boolean hookID;
	private boolean hookBD;
	
	public ConfigLoader(FileConfiguration config){
		this.config = config;
		reloadValues();
	}
	
	public void reloadValues(){
		Bukkit.getServer().getPluginManager().getPlugin("PVPPack").reloadConfig();
		
		Map<String, Object> defaults = new HashMap<String, Object>();
		/* Set up all the defaults */
		defaults.put("options.debug", false);
		defaults.put("options.use-metrics", true);
		defaults.put("options.death-on-combatlog", false);
		defaults.put("options.drop-armor", true);
		defaults.put("options.tag-time", 0.65);
		defaults.put("options.auto-update", true);
		defaults.put("options.disable-creative-pvp", true);
		defaults.put("options.disable-fly-pvp", true);
		defaults.put("options.message-file", "en_US.yml");
		defaults.put("options.broadcast-on-log", false);
		defaults.put("options.allow-tp-during-combat", false);
		
		defaults.put("hooks.disguisecraft", true);
		defaults.put("hooks.vanishnopacket", true);
		defaults.put("hooks.idisguise", true);
		defaults.put("hooks.blockdisguise", true);
		
		/* Sets up all the ArrayLists of Strings */
		List<String> aw = new ArrayList<String>();
		aw.add("Factions");
		defaults.put("options.affected-worlds", aw);
		
		List<String> dc = new ArrayList<String>();
		dc.add("/command1");
		dc.add("/command2");
		defaults.put("options.disabled-commands", dc);
		
		config.addDefaults(defaults);
		config.options().copyDefaults(true);
		//Bukkit.getServer().getPluginManager().getPlugin("PVPPack").saveConfig();
		
		messageFile = config.getString("options.message-file");
		affectedWorlds = config.getStringList("options.affected-worlds");
		disabledCommands = config.getStringList("options.disabled-commands");
		debug = config.getBoolean("options.debug");
		tagTime = config.getInt("options.tag-time");
		useMetrics = config.getBoolean("options.use-metrics");
		killoncombatlog = config.getBoolean("options.death-on-combatlog");
		dropArmor = config.getBoolean("options.drop-armor");
		autoupdate = config.getBoolean("options.auto-update");
		disableCreativePVP = config.getBoolean("options.disable-creative-pvp");
		disableFlyPVP = config.getBoolean("options.disable-fly-pvp");
		broadcastOnLog = config.getBoolean("options.broadcast-on-log");
		allowTpDuringCombat = config.getBoolean("options.allow-tp-during-combat");
		/* Plugin hooks */
		hookDC = config.getBoolean("hooks.disguisecraft");
		hookVNP = config.getBoolean("hooks.vanishnopacket");
		hookID = config.getBoolean("hooks.idisguise");
		hookBD = config.getBoolean("hooks.blockdisguise");
	}

	public boolean getUseMetrics(){
		return useMetrics;
	}
	
	public int getTagTime(){
		return tagTime;
	}
	
	public String getMessageFile(){
		return messageFile;
	}
	
	public boolean getDebug(){
		return debug;
	}
	
	public boolean getKillOnCombatLog(){
		return killoncombatlog;
	}
	
	public List<String> getAffectedWorlds(){
		return affectedWorlds;
	}
	
	public List<String> getDisabledCommands(){
		return disabledCommands;
	}
	
	public boolean getDropArmor(){
		return dropArmor;
	}
	
	public boolean getAutoUpdate(){
		return autoupdate;
	}
	
	public boolean getBroadcastOnLog(){
		return broadcastOnLog;
	}
	
	public boolean getAllowTPDuringCombat(){
		return allowTpDuringCombat;
	}
	
	public boolean getDisableCreativePVP(){
		return disableCreativePVP;
	}
	
	public boolean getDisableFlyPVP(){
		return disableFlyPVP;
	}
	
	public boolean getHookIDisguise(){
		return hookID;
	}
	
	public boolean getHookDisguiseCraft(){
		return hookDC;
	}
	
	public boolean getHookVanishNoPacket(){
		return hookVNP;
	}
	
	public boolean getHookBlockDisguise(){
		return hookBD;
	}
	
	public double getCombatLogDropPercentage(){
		return combatlogdroppercentage;
	}
	
}
