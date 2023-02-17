package com.eimacon.bugfix.utils;

import com.eimacon.bugfix.Bugfix;
import com.eimacon.bugfix.listeners.BlockEvent;
import com.eimacon.bugfix.nbsapi.NbSong;
import com.eimacon.bugfix.nbsapi.NoteBlockPlayer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

public class PacMan {

    private static NbSong song = null;
    private Block mblock;

    public static ArrayList<Block> spawnPacman(int dir, Block b) {

        //Player p;
        //((LivingEntity)p).setCollidable();

        //double y = block.getY();
        ArrayList<Block> blocks = new ArrayList<Block>();
        if(dir == 0) {
            int x = b.getX();

            for(int y = b.getY() - 2; y <= b.getY() + 2; y++){
                for(int z = b.getZ() - 2; z <= b.getZ() + 2; z++){
                    Block block = b.getWorld().getBlockAt(x, y, z);
                    b.getWorld().setType(x, y, z, Material.YELLOW_WOOL);
                    if(!block.isEmpty()/* && !block.getType(Material.GRASS)*/) {
                        blocks.add(block);
                    }
                }
            }
            for(int y = b.getY() - 3; y <= b.getY() + 3; y++){
                for(int z = b.getZ() - 1; z <= b.getZ() + 1; z++){
                    Block block = b.getWorld().getBlockAt(x, y, z);
                    b.getWorld().setType(x, y, z, Material.YELLOW_WOOL);
                    if(!block.isEmpty()/* && !block.getType(Material.GRASS)*/) {
                        blocks.add(block);
                    }
                }
            }
            for(int y = b.getY() - 1; y <= b.getY() + 1; y++){
                for(int z = b.getZ() - 2; z <= b.getZ() + 3; z++){
                    Block block = b.getWorld().getBlockAt(x, y, z);
                    b.getWorld().setType(x, y, z, Material.YELLOW_WOOL);
                    if(!block.isEmpty()/* && !block.getType(Material.GRASS)*/) {
                        blocks.add(block);
                    }
                }
            }

            b.getWorld().getBlockAt(b.getLocation().add(0, 0, -2)).setType(Material.AIR);
            b.getWorld().getBlockAt(b.getLocation().add(0, 1, -2)).setType(Material.AIR);
            b.getWorld().getBlockAt(b.getLocation().add(0, -1, -2)).setType(Material.AIR);
            b.getWorld().getBlockAt(b.getLocation().add(0, 0, -1)).setType(Material.AIR);
            b.getWorld().getBlockAt(b.getLocation().add(0, 2, -1)).setType(Material.BLACK_CONCRETE);
        } else if(dir == 1) {
            int x = b.getX();

            for(int y = b.getY() - 2; y <= b.getY() + 2; y++){
                for(int z = b.getZ() - 2; z <= b.getZ() + 2; z++){
                    Block block = b.getWorld().getBlockAt(x, y, z);
                    b.getWorld().setType(x, y, z, Material.YELLOW_WOOL);
                    if(!block.isEmpty()/* && !block.getType(Material.GRASS)*/) {
                        blocks.add(block);
                    }
                }
            }
            for(int y = b.getY() - 3; y <= b.getY() + 3; y++){
                for(int z = b.getZ() - 1; z <= b.getZ() + 1; z++){
                    Block block = b.getWorld().getBlockAt(x, y, z);
                    b.getWorld().setType(x, y, z, Material.YELLOW_WOOL);
                    if(!block.isEmpty()/* && !block.getType(Material.GRASS)*/) {
                        blocks.add(block);
                    }
                }
            }
            for(int y = b.getY() - 1; y <= b.getY() + 1; y++){
                for(int z = b.getZ() - 3; z <= b.getZ() + 2; z++){
                    Block block = b.getWorld().getBlockAt(x, y, z);
                    b.getWorld().setType(x, y, z, Material.YELLOW_WOOL);
                    if(!block.isEmpty()/* && !block.getType(Material.GRASS)*/) {
                        blocks.add(block);
                    }
                }
            }

            b.getWorld().getBlockAt(b.getLocation().add(0, 0, 2)).setType(Material.AIR);
            b.getWorld().getBlockAt(b.getLocation().add(0, 1, 2)).setType(Material.AIR);
            b.getWorld().getBlockAt(b.getLocation().add(0, -1, 2)).setType(Material.AIR);
            b.getWorld().getBlockAt(b.getLocation().add(0, 0, 1)).setType(Material.AIR);
            b.getWorld().getBlockAt(b.getLocation().add(0, 2, 1)).setType(Material.BLACK_CONCRETE);
        } else if(dir == 2) {
            int z = b.getZ();

            for(int y = b.getY() - 2; y <= b.getY() + 2; y++){
                for(int x = b.getX() - 2; x <= b.getX() + 2; x++){
                    Block block = b.getWorld().getBlockAt(x, y, z);
                    b.getWorld().setType(x, y, z, Material.YELLOW_WOOL);
                    if(!block.isEmpty()/* && !block.getType(Material.GRASS)*/) {
                        blocks.add(block);
                    }
                }
            }
            for(int y = b.getY() - 3; y <= b.getY() + 3; y++){
                for(int x = b.getX() - 1; x <= b.getX() + 1; x++){
                    Block block = b.getWorld().getBlockAt(x, y, z);
                    b.getWorld().setType(x, y, z, Material.YELLOW_WOOL);
                    if(!block.isEmpty()/* && !block.getType(Material.GRASS)*/) {
                        blocks.add(block);
                    }
                }
            }
            for(int y = b.getY() - 1; y <= b.getY() + 1; y++){
                for(int x = b.getX() - 2; x <= b.getX() + 3; x++){
                    Block block = b.getWorld().getBlockAt(x, y, z);
                    b.getWorld().setType(x, y, z, Material.YELLOW_WOOL);
                    if(!block.isEmpty()/* && !block.getType(Material.GRASS)*/) {
                        blocks.add(block);
                    }
                }
            }

            b.getWorld().getBlockAt(b.getLocation().add(-2, 0, 0)).setType(Material.AIR);
            b.getWorld().getBlockAt(b.getLocation().add(-2, 1, 0)).setType(Material.AIR);
            b.getWorld().getBlockAt(b.getLocation().add(-2, -1, 0)).setType(Material.AIR);
            b.getWorld().getBlockAt(b.getLocation().add(-1, 0, 0)).setType(Material.AIR);
            b.getWorld().getBlockAt(b.getLocation().add(-1, 2, 0)).setType(Material.BLACK_CONCRETE);
        } else if(dir == 3) {
            int z = b.getZ();

            for(int y = b.getY() - 2; y <= b.getY() + 2; y++){
                for(int x = b.getX() - 2; x <= b.getX() + 2; x++){
                    Block block = b.getWorld().getBlockAt(x, y, z);
                    b.getWorld().setType(x, y, z, Material.YELLOW_WOOL);
                    if(!block.isEmpty()/* && !block.getType(Material.GRASS)*/) {
                        blocks.add(block);
                    }
                }
            }
            for(int y = b.getY() - 3; y <= b.getY() + 3; y++){
                for(int x = b.getX() - 1; x <= b.getX() + 1; x++){
                    Block block = b.getWorld().getBlockAt(x, y, z);
                    b.getWorld().setType(x, y, z, Material.YELLOW_WOOL);
                    if(!block.isEmpty()/* && !block.getType(Material.GRASS)*/) {
                        blocks.add(block);
                    }
                }
            }
            for(int y = b.getY() - 1; y <= b.getY() + 1; y++){
                for(int x = b.getX() - 3; x <= b.getX() + 2; x++){
                    Block block = b.getWorld().getBlockAt(x, y, z);
                    b.getWorld().setType(x, y, z, Material.YELLOW_WOOL);
                    if(!block.isEmpty()/* && !block.getType(Material.GRASS)*/) {
                        blocks.add(block);
                    }
                }
            }

            b.getWorld().getBlockAt(b.getLocation().add(2, 0, 0)).setType(Material.AIR);
            b.getWorld().getBlockAt(b.getLocation().add(2, 1, 0)).setType(Material.AIR);
            b.getWorld().getBlockAt(b.getLocation().add(2, -1, 0)).setType(Material.AIR);
            b.getWorld().getBlockAt(b.getLocation().add(1, 0, 0)).setType(Material.AIR);
            b.getWorld().getBlockAt(b.getLocation().add(1, 2, 0)).setType(Material.BLACK_CONCRETE);
        }


        return blocks;
    }

    public static void createPacman(Block b, String s) {

        //spawnPacman(0, b);
        int dir = 0;
        if(s.equalsIgnoreCase("W")) dir = 1;
        else if(s.equalsIgnoreCase("N")) dir = 2;
        else if(s.equalsIgnoreCase("S")) dir = 3;
        else dir = 0;
        //Bukkit.getLogger().info("Dir: " + dir + ", Rot: " + s);

        PacmanTask task = new PacmanTask(b.getLocation().clone().add(0, 2, 0).getBlock(), dir, spawnPacman(dir, b.getLocation().clone().add(0, 2, 0).getBlock()));
        task.id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bugfix.getInstance(), task, 10, BlockEvent.pacmanSpeed);

    }

    private static class PacmanTask implements Runnable{
        private Block pacman;
        private final /*abstract*/ int direction;
        private ArrayList<Block> blocks;
        public int id = 0;
        public double lastDistance;
        public NoteBlockPlayer nbplayer;

        public PacmanTask(Block pacman, int direction, ArrayList<Block> blocks) {
            this.pacman = pacman;
            this.direction = direction;
            //this.blocks = blocks;
            this.blocks = blocks;
        }


        @Override
        public void run() {
            if(pacman != null) {
                Block pos = pacman;
                for(Block b : blocks) {
                    b.setType(Material.AIR);
                }
                for(Block block : BlockEvent.getBlocks(pos, 2)) {
                    block.setType(Material.AIR);
                }
                for(Block block : BlockEvent.getBlocks(pos.getLocation().clone().add(0, -1, 0).getBlock(), 2)) {
                    block.setType(Material.AIR);
                }
                for(Block block : BlockEvent.getBlocks(pos.getLocation().clone().add(0, 1, 0).getBlock(), 2)) {
                    block.setType(Material.AIR);
                }

                if(direction == 0) this.pacman = pos.getLocation().add(0, 0, -1).getBlock();
                if(direction == 1) this.pacman = pos.getLocation().add(0, 0, 1).getBlock();
                if(direction == 2) this.pacman = pos.getLocation().add(-1, 0, 0).getBlock();
                if(direction == 3) this.pacman = pos.getLocation().add(1, 0, 0).getBlock();

                this.blocks = spawnPacman(direction, pos.getLocation().getBlock());
                pos.getWorld().playSound(pos.getLocation(), Sound.ENTITY_PHANTOM_BITE, (float) (2), 0);
                if(BlockEvent.destroyPacman) {
                    Bukkit.getScheduler().cancelTask(id);
                    pos.getWorld().createExplosion(pos.getLocation(), 10);
                }
                //Bukkit.getLogger().info("moved pacman, pos: " + pos + ",");
            }
        }
    }
}
