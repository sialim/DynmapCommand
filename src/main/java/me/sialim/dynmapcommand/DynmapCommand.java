package me.sialim.dynmapcommand;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class DynmapCommand extends JavaPlugin {
    private String dynmapUrl;
    private String messageTemplate;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        dynmapUrl = getConfig().getString("dynmap-url", "http://replace-in-config.com");
        messageTemplate = getConfig().getString("message", "Click here to open the map: {url}");
    }

    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("map")) {
            if (sender instanceof Player p) {
                String message = messageTemplate.replace("{url}", dynmapUrl);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                return true;
            }
        } else if (command.getName().equalsIgnoreCase("mapreload")) {
            if (sender instanceof Player p) return false;
            if (sender instanceof ConsoleCommandSender) {
                reloadConfig();
                dynmapUrl = getConfig().getString("dynmap-url", "http://your-dynmap-url.com");
                sender.sendMessage(ChatColor.GREEN + "Map URL configuration reloaded.");
                return true;
            }
        }
        return false;
    }
}
