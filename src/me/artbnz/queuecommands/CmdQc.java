package me.artbnz.queuecommands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdQc implements CommandExecutor {
	public static Queue plugin;

	public CmdQc(Queue instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equals("qc") && args.length > 0) {
			if (args[0].equals("addp")) {
				if ((!sender.hasPermission("queuecommands.addp")) && ((sender instanceof Player))) {
					sender.sendMessage(ChatColor.RED + "You don't have permission to access this command.");
					return false;
				}
				if (args.length < 3) {
					sender.sendMessage(ChatColor.RED + "/qc addp [player] [command] <args>" + ChatColor.AQUA
							+ " Send command by player as soon as possible.");
					return false;
				}
				Player target = sender.getServer().getPlayer(args[1]);
				PlayerFile yaml = Queue.getOfflinePlayerYaml(args[1]);
				if(target == null) {
					if (!yaml.contains("commands")) {
						String oldArgs = args[1];
						args[1] = "";
						String command = Arrays.toString(args).replace("[", "").replaceAll("[],,]", "")
								.replace("addp  ", "").replace("&", "§");
						args[1] = oldArgs;
						List<String> commands = new ArrayList<String>();
						commands.add(command);
						yaml.createNewStringList("commands", commands);
						yaml.save();
						sender.sendMessage(ChatColor.RED + "[QueueCommands] : " + args[1] + " is now offline.");
						sender.sendMessage(
								ChatColor.RED + "Added " + args[1] + " to the queue with the command /" + command);
						return false;
					}
					if (yaml.contains("commands")) {
						String oldArgs = args[1];
						args[1] = "";
						String command = Arrays.toString(args).replace("[", "").replaceAll("[],,]", "")
								.replace("addp  ", "").replace("&", "§");
						args[1] = oldArgs;
						List<String> commands = yaml.getStringList("commands");
						commands.add(command);
						yaml.set("commands", commands);
						yaml.save();
						sender.sendMessage(ChatColor.RED + "[QueueCommands] : " + args[1] + " is now offline.");
						sender.sendMessage(
								ChatColor.RED + "Added " + args[1] + " to the queue with the command /" + command);
						return false;
					}
				}
				if (target.isOnline()) {
					String oldArgs = args[1];
					args[1] = "";
					String command = Arrays.toString(args).replace("[", "").replaceAll("[],,]", "")
							.replace("addp  ", "").replace("&", "§");
					args[1] = oldArgs;
					target.performCommand(command);
					sender.sendMessage(ChatColor.GOLD + "Forced " + args[1] + " to run /" + command);
					return false;
				}
			}
			if (args[0].equals("clear")) {
				if ((!sender.hasPermission("queuecommands.clear")) && ((sender instanceof Player))) {
					sender.sendMessage(ChatColor.RED + "You don't have permission to access this command.");
					return false;
				}
				if (args.length != 2) {
					sender.sendMessage(ChatColor.RED + "/qc clear [player]" + ChatColor.AQUA + " Clear queue player");
					return false;
				}
				PlayerFile yaml = Queue.getOfflinePlayerYaml(args[1]);
				yaml.delete();
				sender.sendMessage(ChatColor.GOLD + args[1] + "s queue cleared!");
			}
			if (args[0].equals("message")) {
				if ((!sender.hasPermission("queuecommands.message")) && ((sender instanceof Player))) {
					sender.sendMessage(ChatColor.RED + "You don't have permission to access this command.");
					return false;
				}
				if (args.length < 3) {
					sender.sendMessage(ChatColor.RED + "/qc message [player] [message]" + ChatColor.AQUA
							+ " Send message to player as soon as possible.");
					return false;
				}
				PlayerFile yaml = Queue.getOfflinePlayerYaml(args[1]);
				Player target = sender.getServer().getPlayer(args[1]);
				if(target == null) {
					if (yaml.contains("messages")) {
						String oldArgs = args[1];
						args[1] = "";
						String message = Arrays.toString(args).replace("[", "").replaceAll("[],,]", "")
								.replace("message  ", "").replace("&", "§");
						args[1] = oldArgs;
						List<String> messages = yaml.getStringList("messages");
						messages.add(message);
						yaml.set("messages", messages);
						yaml.save();
						sender.sendMessage(ChatColor.RED + "[QueueCommands] : " + args[1] + " is now offline.");
						sender.sendMessage(ChatColor.RED + "Added message to " + args[1] + "s queue!");
						return false;
					}
					if (!yaml.contains("messages")) {
						String oldArgs = args[1];
						args[1] = "";
						String message = Arrays.toString(args).replace("[", "").replaceAll("[],,]", "")
								.replace("message  ", "").replace("&", "�");
						args[1] = oldArgs;
						List<String> messages = new ArrayList<String>();
						messages.add(message);
						yaml.createNewStringList("messages", messages);
						yaml.save();
						sender.sendMessage(ChatColor.RED + "[QueueCommands] : " + args[1] + " is now offline.");
						sender.sendMessage(ChatColor.RED + "Added message to " + args[1] + "s queue!");
						return false;
					}
				}
				if (target.isOnline()) {
					String oldArgs = args[1];
					args[1] = "";
					String message = Arrays.toString(args).replace("[", "").replaceAll("[],,]", "")
							.replace("message  ", "").replace("&", "§");
					args[1] = oldArgs;
					List<String> messages = new ArrayList<String>();
					messages.add(message);
					while ((messages.size() != 0) && (messages.get(0) != null)) {
						target.sendMessage((String) messages.get(0));
						messages.remove(messages.get(0));
					}
					sender.sendMessage(ChatColor.GREEN + "[QueueCommands] : " + target
							+ " is now online, Successful run message.");
					return false;
				}
			}
			if (args[0].equals("addc")) {
				if ((!sender.hasPermission("queuecommands.addc")) && ((sender instanceof Player))) {
					sender.sendMessage(ChatColor.RED + "You don't have permission to access this command.");
					return false;
				}
				if (args.length < 3) {
					sender.sendMessage(ChatColor.RED + "/qc addc [player] [command] <args>" + ChatColor.AQUA
							+ " Send command by console to player as soon as possible.");
					return false;
				}
				PlayerFile yaml = Queue.getOfflinePlayerYaml(args[1]);
				Player target = sender.getServer().getPlayer(args[1]);
				if(target == null) {
					if (yaml.contains("consoleCommands")) {
						String oldArgs = args[1];
						args[1] = "";
						String consoleCmd = Arrays.toString(args).replace("[", "").replaceAll("[],,]", "")
								.replace("addc  ", "").replace("&", "§");
						args[1] = oldArgs;
						List<String> commands = yaml.getStringList("consoleCommands");
						commands.add(consoleCmd);
						yaml.set("consoleCommands", commands);
						yaml.save();
						sender.sendMessage(ChatColor.RED + "[QueueCommands] : " + args[1] + " is now offline.");
						sender.sendMessage(ChatColor.RED + "Added " + args[1] + " to the queue with the console command /"
								+ consoleCmd);
						return false;
					}
					if (!yaml.contains("consoleCommands")) {
						String oldArgs = args[1];
						args[1] = "";
						String consoleCmd = Arrays.toString(args).replace("[", "").replaceAll("[],,]", "")
								.replace("addc  ", "").replace("&", "§");
						args[1] = oldArgs;
						List<String> commands = new ArrayList<String>();
						commands.add(consoleCmd);
						yaml.createNewStringList("consoleCommands", commands);
						yaml.save();
						sender.sendMessage(ChatColor.RED + "[QueueCommands] : " + args[1] + " is now offline.");
						sender.sendMessage(ChatColor.RED + "Added " + args[1] + " to the queue with the console command /"
								+ consoleCmd);
						return false;
					}
				}
				if (target.isOnline()) {
					String oldArgs = args[1];
					args[1] = "";
					String consoleCmd = Arrays.toString(args).replace("[", "").replaceAll("[],,]", "")
							.replace("addc  ", "").replace("&", "§");
					args[1] = oldArgs;
					List<String> commands = new ArrayList<String>();
					commands.add(consoleCmd);
					while ((commands.size() != 0) && (commands.get(0) != null)) {
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), (String) commands.get(0));
						commands.remove(commands.get(0));
					}
					sender.sendMessage(ChatColor.GREEN + "[QueueCommands] : " + target
							+ " is now online, Successful run command.");
					return false;
				}
			}
			if ((!args[0].equals("addp")) && (!args[0].equals("addc")) && (!args[0].equals("clear"))
					&& (!args[0].equals("message"))) {
				sender.sendMessage(ChatColor.RED + "[QueueCommands] : Invalid arguments!");
				return false;
			}
		} else {
			if (sender.hasPermission("queuecommands.help")) {
				sender.sendMessage(ChatColor.RED + "QueueCommands Command List");
				sender.sendMessage(ChatColor.RED + "/qc addp [player] [command] <args>" + ChatColor.AQUA
						+ " Send command by player as soon as possible.");
				sender.sendMessage(ChatColor.RED + "/qc addc [player] [command] <args>" + ChatColor.AQUA
						+ " Send command by console to player as soon as possible.");
				sender.sendMessage(ChatColor.RED + "/qc clear [player]" + ChatColor.AQUA + " Clear queue player");
				sender.sendMessage(ChatColor.RED + "/qc message [player] [message]" + ChatColor.AQUA
						+ " Send message to player as soon as possible.");
			} else {
				sender.sendMessage(ChatColor.RED + "You don't have permission to access this command.");
			}
		}
		return false;
	}
}
