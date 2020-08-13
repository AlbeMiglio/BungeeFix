package it.flowzz.bungeefix;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.List;

public class EventListener implements Listener {

    private static BungeeFix main = BungeeFix.getInstance();
    private FileConfiguration getConfig() { return main.getConfig(); }

    @EventHandler
    public void onJoin(PlayerLoginEvent e) {
        String IP = e.getRealAddress().getHostAddress();
        Player p = e.getPlayer();
        List<String> IPs = getConfig().getStringList("Bungeecord IPs");
        if (IPs.contains(IP)) {
            return;
        }
        if (getConfig().getBoolean("AutoBan.Enabled")) {
            if (main.prowlers.contains(e.getPlayer().getName())) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("BanCommand")
                        .replaceAll("%player%", p.getName()));
                    main.prowlers.remove(p.getName());
            }
            else {
                main.prowlers.add(e.getPlayer().getName());
            }
            }
            e.setKickMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Kick Message")));
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
    }
}
