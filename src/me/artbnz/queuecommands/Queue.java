package me.artbnz.queuecommands;

import java.io.File;
import java.util.UUID;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Queue extends JavaPlugin implements Listener {
	Queue plugin;

	public void onEnable() {
		getCommand("qc").setExecutor(new CmdQc(this));
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		pm.registerEvents(new Events(this), this);
		File usersDir1 = new File("plugins/Queue/");
		if (!usersDir1.exists()) {
			usersDir1.mkdir();
		}
		File usersDir = new File("plugins/Queue/UserData/");
		if (!usersDir.exists()) {
			usersDir.mkdir();
		}
	}

	public void onDisable() {
		this.plugin = null;
	}

	public static PlayerFile getPlayerYaml(UUID string) {
		return new PlayerFile("plugins/Queue/UserData/" + string + ".yml");
	}

	public static PlayerFile getOfflinePlayerYaml(String PlayerName) {
		return new PlayerFile("plugins/Queue/UserData/" + PlayerName + ".yml");
	}
}
