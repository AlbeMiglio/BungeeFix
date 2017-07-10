// 
// Decompiled by Procyon v0.5.30
// 

package FlowZz.BungeeFix;

import org.bukkit.event.EventHandler;
import org.bukkit.ChatColor;
import java.util.ArrayList;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.spigotmc.SpigotConfig;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)this);
        this.saveDefaultConfig();
        this.reloadConfig();
        if (!this.isBungee()) {
            Bukkit.getPluginManager().disablePlugin((Plugin)this);
        }
    }
    
    private boolean isBungee() {
        final boolean Cfg = SpigotConfig.bungee;
        final boolean Premium = Bukkit.getServer().getOnlineMode();
        return Cfg && !Premium;
    }
    
    public void onDisable() {
        this.saveConfig();
    }
    
    public boolean onCommand(final CommandSender Send, final Command Cmd, final String label, final String[] Args) {
        if ((Send instanceof Player || Send instanceof ConsoleCommandSender) && Cmd.getName().equalsIgnoreCase("BungeeFix")) {
            if (!Send.hasPermission("BungeeFix.reload")) {
                Send.sendMessage("§cNo permissions.");
                return false;
            }
            if (Args[0].equalsIgnoreCase("Reload")) {
                this.reloadConfig();
                Send.sendMessage("§aReload Completed.");
                return true;
            }
            Send.sendMessage("§cCorrect syntax: /BungeeFix Reload");
        }
        return false;
    }
    
    @EventHandler
    public void onJoin(final PlayerLoginEvent e) {
        final String IP = e.getRealAddress().getHostAddress();
        final ArrayList<String> IPs = (ArrayList<String>)this.getConfig().getStringList("Bungeecord IPs");
        if (!IPs.contains(IP)) {
            e.setKickMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("Kick Message")));
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
    }
}
