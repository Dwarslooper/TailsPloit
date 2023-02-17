package com.eimacon.bugfix.utils;

import com.eimacon.bugfix.Bugfix;
import com.eimacon.bugfix.nbsapi.NbSong;
import com.eimacon.bugfix.nbsapi.NoteBlockPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.logging.Level;

public class NyanCat {

    private static NbSong song = null;

    public static NbSong getOrLoadSong() {
        try {
            song = NbSong.load(Bugfix.getInstance().getResource("nyancat.nbs"));
        } catch (Exception e) {
            Bugfix.getInstance().getLogger().log(Level.WARNING, "Failed to load Sound from Resources", e);
        }
        return song;
    }

    public static void spawmNyanCat(Player player, Block block) {
        Vector dir = player.getLocation().getDirection().multiply(-30);
        Location location = block.getLocation().add(dir);
        Entity cat = player.getWorld().spawnEntity(location.add(0,8,0), EntityType.CAT);
        cat.setGravity(false);
        cat.teleport(cat.getLocation().setDirection(player.getLocation().getDirection()));
        Location targetLoc = block.getLocation();
        NyanCatTask task = new NyanCatTask(cat, targetLoc, targetLoc.toVector());
        task.nbplayer = new NoteBlockPlayer(Bugfix.getInstance());
        NbSong song = getOrLoadSong();
        if(song != null) {
            task.nbplayer.setLoop(true);
            task.nbplayer.setLocation(block.getLocation());
            task.nbplayer.play(song);
        }
        task.id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bugfix.getInstance(), task, 10, 0);
    }

    private static class NyanCatTask implements Runnable{
        private final Entity cat;
        private final Location targetLoc;
        private final Vector target;
        public int id = 0;
        public double lastDistance;
        public NoteBlockPlayer nbplayer;

        public NyanCatTask(Entity cat, Location targetLoc, Vector target) {
            this.cat = cat;
            this.targetLoc = targetLoc;
            this.target = target;
        }

        @Override
        public void run() {
            if(cat != null) {
                Vector pos = cat.getLocation().toVector();
                Vector velocity = target.clone().subtract(pos);
                cat.setVelocity(velocity.normalize().multiply(0.187));
                cat.getWorld().spawnParticle(Particle.REDSTONE, cat.getLocation().clone().add(0,0.4,0), 0, 0, 0, 0, 1, new Particle.DustOptions(Color.fromRGB((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)),2));
                cat.getWorld().spawnParticle(Particle.END_ROD, cat.getLocation().clone().add(0,0.4,0), 0, 0, 0, 0, 1);
                double distance = targetLoc.distance(cat.getLocation());
                if(distance < 2 || (lastDistance - distance) == 0D) {
                    cat.getLocation().createExplosion(4);
                    cat.remove();
                    nbplayer.stop();
                    Bukkit.getScheduler().cancelTask(id);
                }
                lastDistance = distance;
            }
        }
    }
}
