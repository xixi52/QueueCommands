package me.artbnz.queuecommands;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Events implements Listener {
	Queue plugin;

	public Events(Queue plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onplayerjoinevent(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		PlayerFile yaml = Queue.getOfflinePlayerYaml(player.getName());
		if (yaml.contains("commands")) {
			List<String> commands = yaml.getStringList("commands");
			while ((commands.size() != 0) && (commands.get(0) != null)) {
				player.performCommand((String) commands.get(0));
				commands.remove(commands.get(0));
				yaml.set("commands", commands);
			}
		}
		if (yaml.contains("messages")) {
			List<String> messages = yaml.getStringList("messages");
			while ((messages.size() != 0) && (messages.get(0) != null)) {
				player.sendMessage((String) messages.get(0));
				messages.remove(messages.get(0));
				yaml.set("messages", messages);
			}
		}
		if (yaml.contains("consoleCommands")) {
			List<String> commands = yaml.getStringList("consoleCommands");
			while ((commands.size() != 0) && (commands.get(0) != null)) {
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), (String) commands.get(0));
				commands.remove(commands.get(0));
				yaml.set("consoleCommands", commands);
			}
		}
		yaml.save();
	}
}
