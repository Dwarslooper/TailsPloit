package com.eimacon.bugfix.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class GiveItems implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        String message = "Cleared all illegal Items from Player Inventory!";
        sender.sendMessage(ChatColor.GREEN + message);
        Bukkit.getPlayer(sender.getName()).getInventory().clear();

        return false;
    }
}
