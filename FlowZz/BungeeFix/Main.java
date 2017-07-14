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
			if (Send.hasPermission("BungeeFix.reload"))
			{
			if (Args[0].equalsIgnoreCase("Reload"))
			{
				reloadConfig();
				Send.sendMessage("§aReload Completed.");
		        return true;
			}
			else
			{
				Send.sendMessage("§cCorrect syntax: /BungeeFix Reload");
			}
			}
			else
			{

				Send.sendMessage("§cNo permissions.");
				return false;
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
