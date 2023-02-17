package com.eimacon.bugfix.commands;

import com.eimacon.bugfix.listeners.StackCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class ServerSwticher implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage("§e>> Current TPS: " + Bukkit.getTPS() + "\n§e>> Current Chunk Thread: 2\n§e>> Chunks overflowing: 0");
        return false;
    }
}
