package mc.boboman13.pvppack.messages;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import mc.boboman13.pvppack.PVPPack;

public class MessageHandler {

	private File messageFile = null;
	private FileConfiguration messages = null;
	
	private Locale locale;
	
	private PVPPack plugin;
	
	public MessageHandler(PVPPack plugin){
		this.plugin = plugin;
		
		reloadValues();
	}
	
	public void reloadValues(){
		String fileName = plugin.getConfigLoader().getMessageFile();
		
		/* Gets the locale, for the API */
		try {
			locale = Enum.valueOf(Locale.class, fileName.substring(0, fileName.length()-4));
			plugin.log("Using language file '"+fileName.substring(0, fileName.length()-4)+"', getting values...", Level.INFO);
		} catch (IllegalArgumentException e){
			plugin.log("Usage of custom language file detected.", Level.INFO);
			locale = Locale.custom;
		}
		
		/* Sees whether the messageFile is null... :o */
		if(messageFile == null){
			messageFile = new File(plugin.getDataFolder(), fileName);
		}
		
		/* Gives meaning to the messages FileConfiguration */
		messages = YamlConfiguration.loadConfiguration(messageFile);
		
		/* Grabs the defaults from the .jar */
		InputStream stream = plugin.getResource(fileName);
		if(stream != null){
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(stream);
	        messages.setDefaults(defConfig);
	        messages.options().copyDefaults(true);
	        try {
				messages.save(messageFile);
			} catch (IOException e) {
				plugin.log("Error saving message file to disk; not saving.", Level.WARNING);
			}
	    /* The admin gave us a file that isn't actually existant... bad boy! */
		} else {
			plugin.log("Message file does not exist.",Level.WARNING);
		}
		
	}

	public Locale getLocale(){
		return locale;
	}
	
	public String getMessage(Message message){
		String newMessage = messages.getString(message.toString());
		newMessage = "&7[&bPVP&7]&f "+newMessage;
		newMessage = ChatColor.translateAlternateColorCodes('&', newMessage);
		
		return newMessage;
	}
	
}
