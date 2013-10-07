package mc.boboman13.pvppack.runnables;

import java.util.HashMap;
import java.util.List;

import mc.boboman13.pvppack.PVPPack;
import mc.boboman13.pvppack.messages.Message;

import org.bukkit.entity.Player;

public class PlayerRemover implements Runnable {

	private List<Player> list;
	private Player player;
	private HashMap<Player, Integer>  hash;
	private PVPPack plugin;
	
	public PlayerRemover(List<Player> list, Player player, HashMap<Player, Integer> hash, PVPPack plugin){
		this.list = list;
		this.player = player;
		this.hash = hash;
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		list.remove(player);
		hash.remove(player);
		if(player.isOnline()){
			player.sendMessage(plugin.getMessageHandler().getMessage(Message.UNTAGGED));
		}
	}

}
