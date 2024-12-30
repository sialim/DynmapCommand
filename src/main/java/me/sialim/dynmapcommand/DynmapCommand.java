package me.sialim.dynmapcommand;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
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
                String plainMessage = messageTemplate.replace("{url}", dynmapUrl);
                String[] splitMessage = plainMessage.split("\\{url\\}", 2);


                String beforeUrl = ChatColor.translateAlternateColorCodes('&', splitMessage[0]);
                String afterUrl = splitMessage.length > 1
                        ? ChatColor.translateAlternateColorCodes('&', splitMessage[1])
                        : "";

                TextComponent message = new TextComponent(beforeUrl);
                TextComponent urlComponent = new TextComponent(dynmapUrl);
                urlComponent.setColor(net.md_5.bungee.api.ChatColor.BLUE);
                urlComponent.setUnderlined(true);
                urlComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, dynmapUrl));
                message.addExtra(urlComponent);
                if (!afterUrl.isEmpty()) message.addExtra(afterUrl);
                p.spigot().sendMessage(message);
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
