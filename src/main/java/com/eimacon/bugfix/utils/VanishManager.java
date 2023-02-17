package com.eimacon.bugfix.utils;

import com.eimacon.bugfix.Bugfix;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class VanishManager {

    private static final List<Player> VANISHED = new ArrayList<>();

    public static String joinMessage = "§e%s joined the game";
    public static String quitMessage = "§e%s left the game";

    public static boolean isVanished(Player player) {
        return VANISHED.contains(player);
    }

    @SuppressWarnings("deprecation")
    public static void setVanished(Player player, boolean toggle, boolean fakeMessage) {
        if (VANISHED.contains(player) == toggle)
            return;
        if (toggle) {
            VANISHED.add(player);
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (!player.equals(online) && !isVanished(online) && !Bugfix.trustedPlayers.contains(online.getName())) {
                    online.hidePlayer(Bugfix.getInstance(), player);
                }
            }
            if(fakeMessage && quitMessage != null)
                Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + " left the game"/*quitMessage*/);
        } else {
            VANISHED.remove(player);
            for (Player online : Bukkit.getOnlinePlayers()) {
                online.showPlayer(Bugfix.getInstance(), player);
            }
            if(fakeMessage && joinMessage != null)
                Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + " joined the game"/*joinMessage*/);
        }
    }

    @SuppressWarnings("deprecation")
    public static void joined(PlayerJoinEvent event) {
        if(event.getJoinMessage() == null)
            joinMessage = null;
        else
            joinMessage = event.getJoinMessage().replace("%s", event.getPlayer().getDisplayName());

        for(Player vanish : VANISHED) {
            event.getPlayer().hidePlayer(Bugfix.getInstance(), vanish);
        }
    }

    @SuppressWarnings("deprecation")
    public static void quit(PlayerQuitEvent event) {
        if(event.getQuitMessage() == null)
            quitMessage = null;
        else
            quitMessage = event.getQuitMessage().replace("%s", event.getPlayer().getDisplayName());

        VANISHED.remove(event.getPlayer());
    }

}
