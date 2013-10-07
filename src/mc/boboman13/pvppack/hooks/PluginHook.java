package mc.boboman13.pvppack.hooks;

/**
 * 
 * @author boboman13
 */
public interface PluginHook {
	
	/**
	 * Enables the PluginHook. When it is not able to enable, it stays disabled
	 */
	public void enable();
	
	/**
	 * Disables the PluginHook
	 */
	public void disable();
	
}
