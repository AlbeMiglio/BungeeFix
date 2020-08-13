package it.flowzz.bungeefix;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class BungeeFix extends JavaPlugin {

	public List<String> IPs;
	public List<String> prowlers;
	private static BungeeFix instance;
	
	public void onEnable() {
	    instance = this;
	    saveDefaultConfig();
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
	    boolean Cfg = Bukkit.getServer().spigot().getConfig().getBoolean("settings.bungeecord");
	    boolean Premium = Bukkit.getServer().getOnlineMode();
	    if(Cfg && (!(Premium))) {
	        return true;
	    }
	    return false;
	}
}
