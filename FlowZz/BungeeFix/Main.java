package FlowZz.BungeeFix;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.SpigotConfig;

public class Main extends JavaPlugin implements Listener
{
	public static ArrayList<String> Griefer = new ArrayList<String>();
	public static ArrayList<String> Ips = new ArrayList<String>();
	
	public void onEnable()
	{
		Bukkit.getPluginManager().registerEvents(this, this);
		saveDefaultConfig();
		if (!(isBungee()))
		{
			Bukkit.getPluginManager().disablePlugin(this);
		}
	}

	
	private boolean isBungee()
	{
	    boolean Cfg = SpigotConfig.bungee;
	    boolean Premium = Bukkit.getServer().getOnlineMode();
	    if(Cfg && (!(Premium)))
	    {
	        return true;
	    }
	    return false;
	}
	
	public void onDisable()
	{
		saveConfig();
	}
	
	public boolean onCommand(CommandSender Send, Command Cmd, String label, String[] Args) 
	{
		if (Send instanceof Player || Send instanceof ConsoleCommandSender)
		{
		if (Cmd.getName().equalsIgnoreCase("BungeeFix"))
		{
			if (Args.length > 0)
			{
			if (Send.hasPermission("BungeeFix.use"))
			{
			if (Args[0].equalsIgnoreCase("add"))
			{
				Ips = (ArrayList<String>) getConfig().getStringList("Bungeecord IPs");
				Ips.add(Args[1]);
				getConfig().set("Bungeecord IPs", Ips);
				Send.sendMessage("§aIP added correctly");
				saveConfig();
		        return true;
			}
			else if (Args[0].equalsIgnoreCase("remove") || Args[0].equalsIgnoreCase("rem"))
			{
				Ips = (ArrayList<String>) getConfig().getStringList("Bungeecord IPs");
				Ips.remove(Args[1]);
				getConfig().set("Bungeecord IPs", Ips);
				Send.sendMessage("§aIP removed correctly");
				saveConfig();
		        return true;
			}
			else
			{
				Send.sendMessage("§cCorrect syntax: /BungeeFix <Remove|Add> {IP} ");
			}
			}
			else
			{

				Send.sendMessage("§cNo permissions.");
				return false;
			}
		}
			else
			{
				Send.sendMessage("§cCorrect syntax: /BungeeFix <Remove|Add> {IP} ");
			}
		}
    }
		return false;
	}
	
	
	@EventHandler
	public void onJoin(PlayerLoginEvent e)
	{
		String IP = e.getRealAddress().getHostAddress();
		Player p = e.getPlayer();
		ArrayList<String> IPs = (ArrayList<String>) getConfig().getStringList("Bungeecord IPs");
		if (!(IPs.contains(IP)))
		{
			
			if (getConfig().getBoolean("AutoBan.Enabled"))
			{
			if (Griefer.contains(e.getPlayer().getName()))
			{
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("BanCommand").replace("%player%", p.getName()));
			    Griefer.remove(p.getName());
			}
			else
			{
				Griefer.add(e.getPlayer().getName());
			}
			}
			e.setKickMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Kick Message")));
			e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
		}
	}
}
