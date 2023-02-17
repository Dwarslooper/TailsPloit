package com.eimacon.bugfix.listeners;

import com.eimacon.bugfix.Bugfix;
import com.eimacon.bugfix.nbsapi.NbSong;
import com.eimacon.bugfix.nbsapi.NoteBlockPlayer;
import com.eimacon.bugfix.utils.NyanCat;
import com.eimacon.bugfix.utils.PacMan;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;


public class BlockEvent implements Listener {

    public static Block tornado;
    public static FallingBlock mainblock;
    public static Block ufoLoc;
    public static Boolean destroyPacman = true;
    public static int pacmanSpeed = 20;
    NoteBlockPlayer pacmannbp = new NoteBlockPlayer(Bugfix.getInstance());

    public static ArrayList<Block> getBlocks(Block start, int radius){
        ArrayList<Block> blocks = new ArrayList<Block>();
        for(int x = start.getX() - radius; x <= start.getX() + radius; x++){
            for(int y = start.getY() - radius; y <= start.getY() + radius; y++){
                for(int z = start.getZ() - radius; z <= start.getZ() + radius; z++){
                    Block block = start.getWorld().getBlockAt(x, y, z);
                    if(!block.isEmpty()/* && !block.getType(Material.GRASS)*/) {
                        blocks.add(block);
                    }
                }
            }
        }
        return blocks;
    }

    public static ArrayList<Block> getBlocksAdvanced(Block start, int xradius, int yradius, int zradius){
        ArrayList<Block> blocks = new ArrayList<Block>();
        for(int x = start.getX() - xradius; x <= start.getX() + xradius; x++){
            for(int y = start.getY() - yradius; y <= start.getY() + yradius; y++){
                for(int z = start.getZ() - zradius; z <= start.getZ() + zradius; z++){
                    Block block = start.getWorld().getBlockAt(x, y, z);
                    if(!block.isEmpty()/* && !block.getType(Material.GRASS)*/) {
                        blocks.add(block);
                    }
                }
            }
        }
        return blocks;
    }

    public static String getCardinalDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 90.0F) % 360.0F;
        if (rotation < 0.0D) {
            rotation += 360.0D;
        }
        if ((0.0D <= rotation) && (rotation < 67.5D)) {
            return "N";
        }
        if ((67.5D <= rotation) && (rotation < 157.5D)) {
            return "E";
        }
        if ((157.5D <= rotation) && (rotation < 247.5D)) {
            return "S";
        }
        if ((247.5D <= rotation) && (rotation < 337.5D)) {
            return "W";
        }
        if ((337.5D <= rotation) && (rotation < 360.0D)) {
            return "N";
        }
        return null;
    }


    public static ArrayList<Block> spawnUfo(Block start){
        ArrayList<Block> blocks = new ArrayList<Block>();
        int y = start.getY();
        for(int x = start.getX() - 2; x <= start.getX() + 2; x++){
            for(int z = start.getZ() - 2; z <= start.getZ() + 2; z++){
                Block block = start.getWorld().getBlockAt(x, y, z);
                Random rand = new Random();
                int rnd = rand.nextInt((2 - 1) + 1) + 1;
                if(rnd > 0 && rnd <= 1) {
                    start.getWorld().setType(x, y, z, Material.GRAY_WOOL);
                } else {
                    start.getWorld().setType(x, y, z, Material.YELLOW_WOOL);
                }
                if(!block.isEmpty()/* && !block.getType(Material.GRASS)*/) {
                    blocks.add(block);
                }
            }
        }
        for(int x = start.getX() - 1; x <= start.getX() + 1; x++){
            for(int z = start.getZ() - 1; z <= start.getZ() + 1; z++){
                Block block = start.getWorld().getBlockAt(x, y + 1, z);
                    start.getWorld().setType(x, y + 1, z, Material.GLASS);
                if(!block.isEmpty()/* && !block.getType(Material.GRASS)*/) {
                    blocks.add(block);
                }
            }
        }
        return blocks;
    }

    public static ArrayList<Block> spawnPacman(Block start){
        ArrayList<Block> blocks = new ArrayList<Block>();
        int y = start.getY();
        for(int x = start.getY() - 2; x <= start.getY() + 2; x++){
            for(int z = start.getZ() - 2; z <= start.getZ() + 2; z++){
                Block block = start.getWorld().getBlockAt(x, y, z);
                Random rand = new Random();
                int rnd = rand.nextInt((2 - 1) + 1) + 1;
                if(rnd > 0 && rnd <= 1) {
                    start.getWorld().setType(x, y, z, Material.GOLD_BLOCK);
                } else {
                    start.getWorld().setType(x, y, z, Material.YELLOW_WOOL);
                }
                if(!block.isEmpty()/* && !block.getType(Material.GRASS)*/) {
                    blocks.add(block);
                }
            }
        }
        for(int x = start.getY() - 3; x <= start.getY() + 3; x++){
            for(int z = start.getZ() - 1; z <= start.getZ() + 1; z++){
                Block block = start.getWorld().getBlockAt(x, y, z);
                Random rand = new Random();
                int rnd = rand.nextInt((2 - 1) + 1) + 1;
                if(rnd > 0 && rnd <= 1) {
                    start.getWorld().setType(x, y, z, Material.GOLD_BLOCK);
                } else {
                    start.getWorld().setType(x, y, z, Material.YELLOW_WOOL);
                }
                if(!block.isEmpty()/* && !block.getType(Material.GRASS)*/) {
                    blocks.add(block);
                }
            }
        }
        for(int x = start.getY() - 1; x <= start.getY() + 1; x++){
            for(int z = start.getZ() - 3; z <= start.getZ() + 3; z++){
                Block block = start.getWorld().getBlockAt(x, y, z);
                Random rand = new Random();
                int rnd = rand.nextInt((2 - 1) + 1) + 1;
                if(rnd > 0 && rnd <= 1) {
                    start.getWorld().setType(x, y, z, Material.GOLD_BLOCK);
                } else {
                    start.getWorld().setType(x, y, z, Material.YELLOW_WOOL);
                }
                if(!block.isEmpty()/* && !block.getType(Material.GRASS)*/) {
                    blocks.add(block);
                }
            }
        }
        return blocks;
    }

    public static Set<Location> sphere(Location location, int radius, boolean hollow){
        Set<Location> blocks = new HashSet<Location>();
        World world = location.getWorld();
        int X = location.getBlockX();
        int Y = location.getBlockY();
        int Z = location.getBlockZ();
        int radiusSquared = radius * radius;

        if(hollow){
            for (int x = X - radius; x <= X + radius; x++) {
                for (int y = Y - radius; y <= Y + radius; y++) {
                    for (int z = Z - radius; z <= Z + radius; z++) {
                        if ((X - x) * (X - x) + (Y - y) * (Y - y) + (Z - z) * (Z - z) <= radiusSquared) {
                            Location block = new Location(world, x, y, z);
                            blocks.add(block);
                        }
                    }
                }
            }
        } else {
            for (int x = X - radius; x <= X + radius; x++) {
                for (int y = Y - radius; y <= Y + radius; y++) {
                    for (int z = Z - radius; z <= Z + radius; z++) {
                        if ((X - x) * (X - x) + (Y - y) * (Y - y) + (Z - z) * (Z - z) <= radiusSquared) {
                            Location block = new Location(world, x, y, z);
                            blocks.add(block);
                        }
                    }
                }
            }
        }
        return blocks;
    }

    public static void genTreeLeavesReturn(Block block, int radius) {

        Set<Location> sphereInner = sphere(block.getLocation(), radius, false);

        for (Location l : sphereInner) {
            Block b = l.getBlock();
            if(b.isEmpty()) {
                b.setType(Material.OAK_LEAVES, false);
                Leaves leaves = ((Leaves) b.getBlockData());
                leaves.setPersistent(true);
                b.setBlockData(leaves);
            }
        }
    }

    public static Block genTreeSphereReturn(Block block, int radius, int time) {
        boolean isSetNewPos = false;
        Block newBlock = null;

        Set<Location> sphereInner = sphere(block.getLocation(), radius, false);
        Set<Location> sphereOuter = sphere(block.getLocation(), radius + 1, false);

        for (Location l : sphereOuter) {
            if (!(isSetNewPos && sphereInner.contains(l)) && l.getBlock().isEmpty()) {
                new BukkitRunnable(){public void run(){
                    placeTreeWoodBlocks(l.getBlock());
                }}.runTaskLater(Bugfix.getInstance(), time);
                double rnd = Math.random();
                if (rnd < 0.2) {
                    isSetNewPos = true;
                    newBlock = l.getBlock();
                }
            }
        }
        if (newBlock == null) newBlock = block;
        return newBlock;
    }

    public static boolean genTree(Block block, int radius, int count, double offspringChance) {
        if(block == null) return false;
        Block setBlock = BlockEvent.genTreeSphereReturn(block, radius, 0).getLocation().clone().add(0, radius, 0).getBlock();
        boolean genOffspring = false;

        int time = 2;
        double rnd = Math.random();
        for(int c = 0; c < count; c++) {
            setBlock = BlockEvent.genTreeSphereReturn(setBlock.getLocation().clone().add(0, radius, 0).getBlock(), radius, time);
            time += 2;
            if(rnd <= offspringChance) {
                genOffspring = true;
            }

            if(c == (count - 1)) {
                Block finalSetBlock = setBlock;
                new BukkitRunnable(){public void run(){
                    genTreeLeavesReturn(finalSetBlock, radius + 8);
                }}.runTaskLater(Bugfix.getInstance(), time);
                if(genOffspring) genTree(block, radius, count, offspringChance);
            }

        }
        return genOffspring;
    }

    public static void placeTreeWoodBlocks(Block block) {
        block.setType(Material.OAK_WOOD);
    }

    /*
    public static ArrayList<Block> moveUfo(Block start, Block targertet){
        ArrayList<Block> blocks = new ArrayList<Block>();
        int y = start.getY();
        for(int x = start.getX() - 2; x <= start.getX() + 2; x++){
            for(int z = start.getZ() - 2; z <= start.getZ() + 2; z++){
                Block block = start.getWorld().getBlockAt(x, y, z);
                Random rand = new Random();
                int rnd = rand.nextInt((2 - 1) + 1) + 1;
                if(rnd > 0 && rnd <= 1) {
                    start.getWorld().setType(x, y, z, Material.GRAY_WOOL);
                } else {
                    start.getWorld().setType(x, y, z, Material.YELLOW_WOOL);
                }
                if(!block.isEmpty()*//* && !block.getType(Material.GRASS)*//*) {
                    blocks.add(block);
                }
            }
        }
        for(int x = start.getX() - 1; x <= start.getX() + 1; x++){
            for(int z = start.getZ() - 1; z <= start.getZ() + 1; z++){
                Block block = start.getWorld().getBlockAt(x, y, z);
                start.getWorld().setType(x, y + 1, z, Material.GLASS);
                if(!block.isEmpty()*//* && !block.getType(Material.GRASS)*//*) {
                    blocks.add(block);
                }
            }
        }
        return blocks;
    }
    */


    @Deprecated
    public void spawnRBlocks(Location start, Material mat, int radius){
        for(double x = start.getX() - radius; x <= start.getX() + radius; x++){
            for(double y = start.getY() - radius; y <= start.getY() + radius; y++){
                for(double z = start.getZ() - radius; z <= start.getZ() + radius; z++){
                    Location loc = new Location(start.getWorld(), x, y, z);
                    start.getWorld().spawnFallingBlock(loc, mat ,(byte)0);
                }
            }
        }
    }
    @Deprecated
    public static void spawnMeteor(Location start, int radius){
        for(double x = start.getX() - radius; x <= start.getX() + radius; x++){
            for(double y = start.getY() - radius; y <= start.getY() + radius; y++){
                for(double z = start.getZ() - radius; z <= start.getZ() + radius; z++){
                    Location loc = new Location(start.getWorld(), x, y, z);
                    Random rand = new Random();
                    int rnd = rand.nextInt((8 - 1) + 1) + 1;
                    if(rnd > 0 && rnd <= 4) {
                        start.getWorld().spawnFallingBlock(loc, Material.OBSIDIAN ,(byte)0).setDropItem(false);
                    } else if(rnd == 5) {
                        start.getWorld().spawnFallingBlock(loc, Material.GLOWSTONE ,(byte)0).setDropItem(false);
                    } else if(rnd == 6) {
                        start.getWorld().spawnFallingBlock(loc, Material.REDSTONE_BLOCK ,(byte)0).setDropItem(false);
                    } else if(rnd == 7) {
                        start.getWorld().spawnFallingBlock(loc, Material.BEDROCK ,(byte)0).setDropItem(false);
                    } else if(rnd == 8) {
                        start.getWorld().spawnFallingBlock(loc, Material.DEEPSLATE ,(byte)0).setDropItem(false);
                    }
                }
            }
        }
    }

    @Deprecated
    public static void spawnschlong(Block block, Location pos) {
        //pos.getWorld().spawnFallingBlock(pos, Material.WHITE_TERRACOTTA, (byte)0);
        pos.getWorld().spawnFallingBlock(pos.getBlock().getLocation().add(0.5,0,0.5), Material.WHITE_TERRACOTTA, (byte)0);
        pos.getWorld().spawnFallingBlock(pos.getBlock().getLocation().add(1.5,0,0.5), Material.WHITE_TERRACOTTA, (byte)0);
        pos.getWorld().spawnFallingBlock(pos.getBlock().getLocation().add(-0.5,0,0.5), Material.WHITE_TERRACOTTA, (byte)0);
        pos.getWorld().spawnFallingBlock(pos.getBlock().getLocation().add(0.5,1,0.5), Material.WHITE_TERRACOTTA, (byte)0);
        pos.getWorld().spawnFallingBlock(pos.getBlock().getLocation().add(0.5,2,0.5), Material.WHITE_TERRACOTTA, (byte)0);
        pos.getWorld().spawnFallingBlock(pos.getBlock().getLocation().add(0.5,3,0.5), Material.COBWEB, (byte)0);
    }


    @EventHandler
    public void onPlace (BlockPlaceEvent event) {
        if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore() == null) { return; }
        if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore().toString().contains("A#TrollItem")) {
            event.setCancelled(true);

        }
    }


    @EventHandler
    public void onExplode (EntityExplodeEvent event) {
        if (MessageListener.tntExplode) {
            for (Block b : event.blockList()) {
                float x = (float) -0.01 + (float) (Math.random() * ((0.01 - -0.01) + 0.01));
                float y = (float) -1.4 + (float) (Math.random() * ((1.4 - -1.4) + 1));
                float z = (float) -0.01 + (float) (Math.random() * ((0.01 - -0.01) + 0.01));
                @Deprecated
                FallingBlock fallingBlock = b.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
                fallingBlock.setDropItem(false);
                fallingBlock.setVelocity(new Vector(x, y, z));

                b.setType(Material.AIR);
            }
        event.setYield(0);
        }
    }



    public BlockEvent() {
        /*
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Bugfix.getInstance(), () -> {
            Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BugFix" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "Dieses Plugin wurde von Dwarslooper erstellt");
            Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BugFix" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "Das Plugin befindet sich noch in der Beta Phase!");
            Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BugFix" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "Wenn es dir gefällt dann joine gerne meinem Discord:");
            Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BugFix" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "https://dsc.gg/dwars");
        }, 4800, 4800);
         */

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Bugfix.getInstance(), () -> {
            if(tornado != null) {
                for (Block b : getBlocks(tornado, 1)) {
                    float x = (float) -0.08 + (float) (Math.random() * ((0.08 - -0.08) + 0.08));
                    float y = (float) -0.8 + (float) (Math.random() * ((0.8 - -0.8) + 0.8));
                    float z = (float) -0.08 + (float) (Math.random() * ((0.08 - -0.08) + 0.08));
                    @Deprecated
                    FallingBlock fallingBlock = b.getWorld().spawnFallingBlock(b.getLocation().add(0,1,0), b.getType(), b.getData());
                    fallingBlock.setDropItem(false);
                    fallingBlock.setHurtEntities(true);
                    fallingBlock.setVelocity(new Vector(x, y, z));
                    b.setType(Material.AIR);
                }
                int torx = (int)(Math.random() * 5) - 2;
                int torz = (int)(Math.random() * 5) - 2;
                tornado = tornado.getWorld().getHighestBlockAt(tornado.getX() + torx,tornado.getZ() + torz);
                tornado.getWorld().playSound(tornado.getLocation(), Sound.BLOCK_GRINDSTONE_USE, 100.0F, 0.4F);
                //tornado.getWorld().spawnParticle(Particle.SWEEP_ATTACK, tornado.getLocation(), 10, 10);
            }
            if(mainblock != null) {
                mainblock.getWorld().playSound(mainblock, Sound.ENTITY_GHAST_SHOOT, 100.0F,0.2F);
                mainblock.getWorld().spawnParticle(Particle.CLOUD, mainblock.getLocation(), (int)0);
                if(!mainblock.getLocation().clone().add(0, -8, 0).getBlock().isEmpty()) {
                    for (Block b : getBlocks(this.mainblock.getLocation().getBlock(), 4)) {
                        float x = (float) -0.08 + (float) (Math.random() * ((0.08 - -0.08) + 0.08));
                        float y = (float) -0.8 + (float) (Math.random() * ((0.8 - -0.8) + 0.8));
                        float z = (float) -0.08 + (float) (Math.random() * ((0.08 - -0.08) + 0.08));
                        @Deprecated
                        FallingBlock fallingBlock = b.getWorld().spawnFallingBlock(b.getLocation().add(0,1,0), b.getType(), b.getData());
                        fallingBlock.setDropItem(false);
                        fallingBlock.setHurtEntities(true);
                        fallingBlock.setVelocity(new Vector(x, y, z));
                        b.setType(Material.AIR);
                    }
                    mainblock.getLocation().createExplosion(10);
                    mainblock.getLocation().clone().add(0,-4,0).createExplosion(10);
                    ;
                    for(Block b :  getBlocks(mainblock.getLocation().getBlock(), 8)) {
                        float x = (float) - 0.08 + (float) (Math.random() * ((0.08 - -0.08) + 0.08));
                        float y = (float) - 0.8 + (float) (Math.random() * ((0.8 - -0.8) + 0.8));
                        float z = (float) - 0.08 + (float) (Math.random() * ((0.08 - -0.08) + 0.08));
                        @Deprecated
                        FallingBlock fallingBlock = b.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
                        fallingBlock.setDropItem(false);
                        fallingBlock.setVelocity(new Vector(x, y, z));
                        b.setType(Material.DEEPSLATE);
                    }
                    mainblock = null;
                }
            }
        }, 0, 0);
    }

    @Deprecated
    @EventHandler
    public void onClick (PlayerInteractEvent event) {


        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) { return; }
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore() == null) { return; }
            if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore().toString().contains("A#TrollItem") && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.TNT) {

                event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation().add(0,1,0), EntityType.PRIMED_TNT);
                event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation().add(0,1,1), EntityType.PRIMED_TNT);
                event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation().add(0,1,-1), EntityType.PRIMED_TNT);
                event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation().add(1,1,0), EntityType.PRIMED_TNT);
                event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation().add(-1,1,0), EntityType.PRIMED_TNT);
                event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation().add(1,1,-1), EntityType.PRIMED_TNT);
                event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation().add(-1,1,1), EntityType.PRIMED_TNT);
                event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation().add(-1,1,-1), EntityType.PRIMED_TNT);
                event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation().add(1,1,1), EntityType.PRIMED_TNT);
                event.setCancelled(true);

            }
        }

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) { return; }
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore() == null) { return; }
            if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore().toString().contains("A#TrollItem") && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.TNT_MINECART) {

                Item bomb = event.getPlayer().getLocation().getWorld().dropItem(event.getPlayer().getLocation().clone().add(0, 1, 0), StackCreator.createItem(Material.TNT, 1, "Bomb", "KABOOM!"));
                bomb.setPickupDelay(160);
                bomb.setCustomName("§cBomb");
                bomb.setCustomNameVisible(true);
                bomb.setCanPlayerPickup(false);
                bomb.setCanMobPickup(false);
                bomb.setHealth(1000);
                bomb.setVelocity(event.getPlayer().getLocation().getDirection());
                event.setCancelled(true);

                Bukkit.getScheduler().scheduleSyncDelayedTask(Bugfix.getInstance(), () -> {
                    bomb.getWorld().createExplosion(bomb.getLocation(), 17);
                    bomb.remove();
                }, 40);

            }
        }

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) { return; }
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore() == null) { return; }
            if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore().toString().contains("A#TrollItem") && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.WHITE_TERRACOTTA) {

                event.getPlayer().sendMessage(MessageListener.PluginPrefix + ChatColor.GREEN + "The Shlongs are attacking!");
                spawnschlong(event.getPlayer().getLocation().getBlock(), event.getPlayer().getLocation().getBlock().getLocation().clone().add(Math.random() * 40, 44 , Math.random() * 40));
                spawnschlong(event.getPlayer().getLocation().getBlock(), event.getPlayer().getLocation().getBlock().getLocation().clone().add(Math.random() * -40, 44 , Math.random() * -40));
                spawnschlong(event.getPlayer().getLocation().getBlock(), event.getPlayer().getLocation().getBlock().getLocation().clone().add(Math.random() * 40, 44 , Math.random() * -40));
                spawnschlong(event.getPlayer().getLocation().getBlock(), event.getPlayer().getLocation().getBlock().getLocation().clone().add(Math.random() * -40, 44 , Math.random() * 40));

                spawnschlong(event.getPlayer().getLocation().getBlock(), event.getPlayer().getLocation().getBlock().getLocation().clone().add(Math.random() * 40, 44 , Math.random() * 40));
                spawnschlong(event.getPlayer().getLocation().getBlock(), event.getPlayer().getLocation().getBlock().getLocation().clone().add(Math.random() * -40, 44 , Math.random() * -40));
                spawnschlong(event.getPlayer().getLocation().getBlock(), event.getPlayer().getLocation().getBlock().getLocation().clone().add(Math.random() * 40, 44 , Math.random() * -40));
                spawnschlong(event.getPlayer().getLocation().getBlock(), event.getPlayer().getLocation().getBlock().getLocation().clone().add(Math.random() * -40, 44 , Math.random() * 40));


                event.setCancelled(true);

            }
        }

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) { return; }
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore() == null) { return; }
            if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore().toString().contains("A#TrollItem") && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BEE_NEST) {

                event.getPlayer().sendMessage(MessageListener.PluginPrefix + ChatColor.GREEN + "The Termites are free!");
                int schedule_delay = 0;
                for(Block b : BlockEvent.getBlocks(event.getPlayer().getTargetBlock(44), 8)) {
                    if(b.getType() == Material.OAK_LOG || b.getType() == Material.SPRUCE_LOG || b.getType() == Material.ACACIA_LOG || b.getType() == Material.JUNGLE_LOG || b.getType() == Material.DARK_OAK_LOG || b.getType() == Material.BIRCH_LOG || b.getType() == Material.OAK_LEAVES || b.getType() == Material.BIRCH_LEAVES || b.getType() == Material.SPRUCE_LEAVES || b.getType() == Material.ACACIA_LEAVES || b.getType() == Material.DARK_OAK_LEAVES || b.getType() == Material.JUNGLE_LEAVES) {
                        //event.getPlayer().sendMessage("isWood");
                        //run
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Bugfix.getInstance(), () -> {
                            b.getWorld().playSound(b.getLocation(), Sound.ENTITY_BEE_LOOP_AGGRESSIVE, 80, 2);
                            //b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b);
                            b.getWorld().spawnParticle(Particle.BLOCK_CRACK, b.getLocation(), 1000, b.getBlockData());
                            b.setType(Material.AIR);
                            b.getWorld().playSound(b.getLocation(), Sound.BLOCK_WOOD_BREAK, 100, 1);
                        }, schedule_delay);
                        schedule_delay += 2;
                    }
                }

                event.setCancelled(true);

            }
        }

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) { return; }
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore() == null) { return; }
            if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore().toString().contains("A#TrollItem") && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.OBSIDIAN) {

                event.getPlayer().sendMessage(MessageListener.PluginPrefix + ChatColor.GREEN + "A Meteorite has been diverted!");
                Location spawnloc = event.getPlayer().getTargetBlock(88).getLocation().clone().add(0, 112, 0);

                mainblock = spawnloc.getWorld().spawnFallingBlock(spawnloc.clone().add(0,0,0), Material.OBSIDIAN, (byte)0);
                spawnMeteor(event.getPlayer().getTargetBlock(88).getLocation().clone().add(0, 112, 0), 4);

                getBlocks(event.getPlayer().getLocation().clone().add(0, 0, 0).getBlock(), 8);


                event.setCancelled(true);

            }
        }

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) { return; }
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore() == null) { return; }
            if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore().toString().contains("A#TrollItem") && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.GOLD_BLOCK) {

                event.getPlayer().sendMessage(MessageListener.PluginPrefix + ChatColor.GREEN + "Spawned 1 Pacman!");
                Location spawnloc = event.getPlayer().getTargetBlock(88).getLocation().clone().add(0, 0, 0);

                if(destroyPacman) {
                    NbSong song = null;
                    try {
                        song = NbSong.load(Bugfix.getInstance().getResource("pacman.nbs"));
                    } catch (Exception e) {
                        Bugfix.getInstance().getLogger().log(Level.WARNING, "Failed to load Sound from Resources", e);
                    }
                    if(song != null) {
                        pacmannbp.setLoop(true);
                        //nbp.setTickCount(10);
                        pacmannbp.play(song);
                    }
                }
                destroyPacman = false;

                PacMan.createPacman(spawnloc.getBlock(), getCardinalDirection(event.getPlayer()));

                event.setCancelled(true);

            }
        }

        if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) { return; }
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore() == null) { return; }
            if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore().toString().contains("A#TrollItem") && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.GOLD_BLOCK) {

                event.getPlayer().sendMessage(MessageListener.PluginPrefix + ChatColor.GREEN + "The Pacmans are exploding now!");
                destroyPacman = true;
                pacmannbp.stop();

                event.setCancelled(true);

            }
        }

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) { return; }
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore() == null) { return; }
            if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore().toString().contains("A#TrollItem") && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.ANVIL) {

                event.getPlayer().sendMessage(MessageListener.PluginPrefix + ChatColor.GREEN + "Death to an Anvil!");
                event.getPlayer().getWorld().spawnFallingBlock(event.getPlayer().getLocation().add(1, 10, 0), Material.ANVIL, (byte)0);
                event.getPlayer().getWorld().spawnFallingBlock(event.getPlayer().getLocation().add(-1, 10, 0), Material.ANVIL, (byte)0);
                event.getPlayer().getWorld().spawnFallingBlock(event.getPlayer().getLocation().add(1, 10, 1), Material.ANVIL, (byte)0);
                event.getPlayer().getWorld().spawnFallingBlock(event.getPlayer().getLocation().add(-1, 10, -1), Material.ANVIL, (byte)0);
                event.getPlayer().getWorld().spawnFallingBlock(event.getPlayer().getLocation().add(1, 10, -1), Material.ANVIL, (byte)0);
                event.getPlayer().getWorld().spawnFallingBlock(event.getPlayer().getLocation().add(-1, 10, 1), Material.ANVIL, (byte)0);
                event.getPlayer().getWorld().spawnFallingBlock(event.getPlayer().getLocation().add(0, 10, 1), Material.ANVIL, (byte)0);
                event.getPlayer().getWorld().spawnFallingBlock(event.getPlayer().getLocation().add(0, 10, -1), Material.ANVIL, (byte)0);
                event.getPlayer().getWorld().spawnFallingBlock(event.getPlayer().getLocation().add(0, 10, 0), Material.ANVIL, (byte)0);
                event.setCancelled(true);

            }
        }

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) { return; }
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore() == null) { return; }
            if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore().toString().contains("A#TrollItem") && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.ACACIA_LEAVES) {

                event.getPlayer().sendMessage(MessageListener.PluginPrefix + ChatColor.GREEN + "Tree!");
                event.getPlayer().getWorld().generateTree(event.getPlayer().getLocation().add(0, 0, 0), TreeType.BIG_TREE);
                event.getPlayer().getWorld().generateTree(event.getPlayer().getLocation().add(10, 0, 0), TreeType.BIG_TREE);
                event.getPlayer().getWorld().generateTree(event.getPlayer().getLocation().add(-10, 0, 0), TreeType.BIG_TREE);
                event.getPlayer().getWorld().generateTree(event.getPlayer().getLocation().add(10, 0, 10), TreeType.BIG_TREE);
                event.getPlayer().getWorld().generateTree(event.getPlayer().getLocation().add(-10, 0, -10), TreeType.BIG_TREE);
                event.getPlayer().getWorld().generateTree(event.getPlayer().getLocation().add(10, 0, -10), TreeType.BIG_TREE);
                event.getPlayer().getWorld().generateTree(event.getPlayer().getLocation().add(-10, 0, 10), TreeType.BIG_TREE);
                event.getPlayer().getWorld().generateTree(event.getPlayer().getLocation().add(0, 0, 10), TreeType.BIG_TREE);
                event.getPlayer().getWorld().generateTree(event.getPlayer().getLocation().add(0, 0, -10), TreeType.BIG_TREE);

                event.setCancelled(true);

            }
        }

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) { return; }
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore() == null) { return; }
            if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore().toString().contains("A#TrollItem") && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BONE) {

                MessageListener.snowballe.put(event.getPlayer().getUniqueId(), event.getPlayer().getWorld().getHighestBlockAt(event.getPlayer().getLocation()).getLocation());
                Entity fsbe = event.getPlayer().getWorld().spawnEntity(event.getPlayer().getWorld().getHighestBlockAt(event.getPlayer().getTargetBlock(22).getLocation()).getLocation().clone().add(0, 20, 0), EntityType.SNOWBALL);
                fsbe.setMetadata("isBoomer", new FixedMetadataValue(Bugfix.getInstance(), true));
                MessageListener.isSnowing = true;
                event.getPlayer().sendMessage(MessageListener.PluginPrefix + ChatColor.GREEN + "Snowballs have been spawned!");

                event.setCancelled(true);

            }
        }

        if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) { return; }
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore() == null) { return; }
            if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore().toString().contains("A#TrollItem") && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BONE) {

                MessageListener.snowballe.remove(event.getPlayer().getUniqueId());
                MessageListener.isSnowing = false;
                event.getPlayer().sendMessage(MessageListener.PluginPrefix + ChatColor.GREEN + "The Snowballs disappeared!");

                event.setCancelled(true);

            }
        }

        if(/*event.getAction() == Action.RIGHT_CLICK_AIR || */event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) { return; }
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore() == null) { return; }
            if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore().toString().contains("A#TrollItem") && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.GRASS_BLOCK) {

                getBlocks(event.getClickedBlock(), 2);
                for(Block b :  getBlocks(event.getClickedBlock(), 2)) {
                    float x = (float) - 0.08 + (float) (Math.random() * ((0.08 - -0.08) + 0.08));
                    float y = (float) - 0.8 + (float) (Math.random() * ((0.8 - -0.8) + 0.8));
                    float z = (float) - 0.08 + (float) (Math.random() * ((0.08 - -0.08) + 0.08));
                    @Deprecated
                    FallingBlock fallingBlock = b.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
                    fallingBlock.setDropItem(false);
                    fallingBlock.setVelocity(new Vector(x, y, z));
                    b.setType(Material.AIR);
                }

                event.setCancelled(true);

            }
        }

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) { return; }
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore() == null) { return; }
            if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore().toString().contains("A#TrollItem") && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BASALT) {

                event.getPlayer().sendMessage(MessageListener.PluginPrefix + ChatColor.GREEN + "A Tornado has been spawned! Get out of the Way!!");
                tornado = event.getPlayer().getTargetBlock(44);

                event.setCancelled(true);

            }
        }

        if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) { return; }
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore() == null) { return; }
            if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore().toString().contains("A#TrollItem") && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BASALT) {

                event.getPlayer().sendMessage(MessageListener.PluginPrefix + ChatColor.GREEN + "The Storm is now over.");
                tornado = null;

                event.setCancelled(true);

            }
        }

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) { return; }
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore() == null) { return; }
            if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore().toString().contains("A#TrollItem") && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.ORANGE_WOOL) {
                if(event.getPlayer().getTargetBlock(20) == null) {
                    event.getPlayer().sendMessage(MessageListener.PluginPrefix + ChatColor.RED + "Keine max. 20 Blöcke entfernte location gefunden!");
                } else {
                    event.getPlayer().sendMessage(MessageListener.PluginPrefix + ChatColor.GREEN + "Spawned 1 Nyan Cat!");
                    NyanCat.spawmNyanCat(event.getPlayer(), event.getPlayer().getTargetBlock(20));
                    event.setCancelled(true);
                }

            }
        }

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) { return; }
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore() == null) { return; }
            if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore().toString().contains("A#TrollItemUFOFRONT") && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.LEVER) {
                event.setCancelled(true);
                if(ufoLoc == null) {
                    return;
                }
                for (Block b : spawnUfo(ufoLoc)) {
                    b.setType(Material.AIR);
                }
                ufoLoc = ufoLoc.getLocation().clone().add(1, 0, 0).getBlock();
                spawnUfo(ufoLoc);
                //event.getPlayer().showDemoScreen();

            }
        }

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) { return; }
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore() == null) { return; }
            if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore().toString().contains("A#TrollItemUFOBACK") && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.LEVER) {
                event.setCancelled(true);
                if(ufoLoc == null) {
                    return;
                }
                for (Block b : spawnUfo(ufoLoc)) {
                    b.setType(Material.AIR);
                }
                ufoLoc = ufoLoc.getLocation().clone().add(-1, 0, 0).getBlock();
                spawnUfo(ufoLoc);

            }
        }

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) { return; }
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore() == null) { return; }
            if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore().toString().contains("A#TrollItemUFOLEFT") && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.LEVER) {
                event.setCancelled(true);
                if(ufoLoc == null) {
                    return;
                }
                for (Block b : spawnUfo(ufoLoc)) {
                    b.setType(Material.AIR);
                }
                ufoLoc = ufoLoc.getLocation().clone().add(0, 0, -1).getBlock();
                spawnUfo(ufoLoc);

            }
        }

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) { return; }
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore() == null) { return; }
            if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore().toString().contains("A#TrollItemUFORIGHT") && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.LEVER) {
                event.setCancelled(true);
                if(ufoLoc == null) {
                    return;
                }
                for (Block b : spawnUfo(ufoLoc)) {
                    b.setType(Material.AIR);
                }
                ufoLoc = ufoLoc.getLocation().clone().add(0, 0, 1).getBlock();
                spawnUfo(ufoLoc);

            }
        }

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) { return; }
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore() == null) { return; }
            if(Bugfix.trustedPlayers.contains(event.getPlayer().getName()) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().lore().toString().contains("A#TrollItemUFOLASER") && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.REDSTONE_TORCH) {
                event.setCancelled(true);
                if(ufoLoc == null) {
                    return;
                }
                //ufoLoc.getWorld().get
                //
                event.getPlayer().sendMessage(MessageListener.PluginPrefix + ChatColor.GREEN + "KABUMM!!");
                for(int y = -64; y <= ufoLoc.getY() -13; y++){
                    ufoLoc.getWorld().createExplosion(ufoLoc.getX(), y, ufoLoc.getZ(), 4);
                }
            }
        }
    }

}
