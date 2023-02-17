package com.eimacon.bugfix.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DoCommand implements CommandExecutor {
    //do one - two
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) {
            sender.sendMessage(ChatColor.GREEN + "Hallo, " + sender.getName() + "! Du hast als argument " + args[0] + " eingegeben!");
        } else {
            sender.sendMessage(ChatColor.RED + "Du bist ein Kek, " + sender.getName());
        }
        return false;
    }
}
