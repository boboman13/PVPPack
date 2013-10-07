package mc.boboman13.pvppack.runnables;

import java.io.IOException;
import java.util.logging.Level;

import mc.boboman13.pvppack.PVPPack;
import mc.boboman13.pvppack.metrics.Metrics;

public class LoadMetrics implements Runnable {

	private PVPPack plugin;
	
	public LoadMetrics(PVPPack plugin){
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		try{
			plugin.log("Metrics collection was enabled, starting up Metrics...", Level.INFO);
			Metrics metrics = new Metrics(plugin);
			metrics.start();
			plugin.log("Metrics collection set up successfully.", Level.INFO);
		} catch (IOException e){
			plugin.log("Exception in Metrics collection, collection disabled.", Level.INFO);
		}
	}

}
