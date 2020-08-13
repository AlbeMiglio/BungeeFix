package it.flowzz.bungeefix;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class BungeeFixCMD implements CommandExecutor {

    private static BungeeFix main = BungeeFix.getInstance();
    private FileConfiguration getConfig() { return main.getConfig(); }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length <= 1) {
            sender.sendMessage("§cCorrect syntax: /BungeeFix <Remove|Add> {IP}");
            return false;
        }
        if (!sender.hasPermission("bungeefix.use")) {
            sender.sendMessage("§cNot enough permissions.");
            return false;
        }
        if (args[0].equalsIgnoreCase("add")) {
            main.IPs = getConfig().getStringList("Bungeecord IPs");
            main.IPs.add(args[1]);
            getConfig().set("Bungeecord IPs", main.IPs);
            sender.sendMessage("§aIP added correctly!");
            main.saveConfig();
            return true;
        }
        else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("rem")) {
            main.IPs = getConfig().getStringList("Bungeecord IPs");
            main.IPs.remove(args[1]);
            getConfig().set("Bungeecord IPs", main.IPs);
            sender.sendMessage("§aIP removed correctly!");
            main.saveConfig();
            return true;
        }
        else {
            sender.sendMessage("§cCorrect syntax: /BungeeFix <Remove|Add> {IP}");
            return false;
        }
    }
}
