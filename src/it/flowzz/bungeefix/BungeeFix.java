package it.flowzz.bungeefix;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.SpigotConfig;

public class BungeeFix extends JavaPlugin {

	public List<String> IPs;
	public List<String> prowlers;
	private static BungeeFix instance;
	
	public void onEnable() {
	    instance = this;
		if (!isBungeeCord()) {
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
	    IPs = new ArrayList<>();
		prowlers = new ArrayList<>();

		Bukkit.getPluginManager().registerEvents(new EventListener(), this);
		getCommand("bungeefix").setExecutor(new BungeeFixCMD());
	}

    public static BungeeFix getInstance() { return instance; }

    private boolean isBungeeCord() {
	    boolean Cfg = SpigotConfig.bungee;
	    boolean Premium = Bukkit.getServer().getOnlineMode();
	    if(Cfg && (!(Premium))) {
	        return true;
	    }
	    return false;
	}
}
