package com.eimacon.bugfix.listeners;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.eimacon.bugfix.Bugfix;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class JumpListener implements Listener {

    HashMap map = new HashMap();

    @EventHandler
    public void onJump(PlayerJumpEvent e) {
        if(map.containsKey(e.getPlayer().getUniqueId())) {
            map.put(e.getPlayer().getUniqueId(), (float)map.get(e.getPlayer().getUniqueId()) + ((float)map.get(e.getPlayer().getUniqueId()) / 10));
        } else {
            map.put(e.getPlayer().getUniqueId(), (float)0.1);
            e.getPlayer().sendMessage("§eBei jedem Sprung springst du 50% höher als vorher! Versuche Minecraft durchzuspielen ohne zu sterben oder zum Mond zu fliegen!");
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(Bugfix.getInstance(), () -> {
            e.getPlayer().setVelocity(new Vector(e.getPlayer().getVelocity().getX(), e.getPlayer().getVelocity().getY() + (float)map.get(e.getPlayer().getUniqueId()), e.getPlayer().getVelocity().getZ()));
        }, 2);
    }

    @EventHandler
    public void entityDeath(EntityDeathEvent e) {
        if(e.getEntityType() == EntityType.ENDER_DRAGON) {
            map.clear();
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage("§eDu hast es geschafft! Der Enderdrache ist gestorben. Deine Zeit:");
                int ptt = p.getStatistic(Statistic.PLAY_ONE_MINUTE ) / 20;
                int ptminutes = ptt / 60;
                int pthours = ptminutes / 60;
                int ptdays = pthours / 24;
                p.sendMessage("§a" + ptdays + " Tage, " + pthours + " Stunden, " + ptminutes + " Minuten, " + ptt + " Sekunden!");
            }
        }
    }
}
