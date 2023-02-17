package com.eimacon.bugfix.listeners;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.eimacon.bugfix.Bugfix;
import com.eimacon.bugfix.utils.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;

public class PlayerControlListener implements Listener {

    public static final HashMap<Player, ControlData> controlDataMap = new HashMap<>();
    public static final HashMap<Player, Player> targetMap = new HashMap<>();
    public static final HashMap<Player, Player> controllerMap = new HashMap<>();

    public static boolean isControlled(Player target) {
        return targetMap.containsKey(target);
    }

    public static boolean isController(Player controller) {
        return controllerMap.containsKey(controller);
    }

    public static boolean controlPlayer(Player controller, Player target) {
        if(controller.getUniqueId().equals(target.getUniqueId()))
            return false;
        if(isControlled(controller) || isController(controller) || isControlled(target) || isController(target)) {
           return false;
        }
        ControlData playerController = controlDataMap.get(controller);
        if(playerController == null) {
            controlDataMap.put(controller, playerController = new ControlData());
        }
        controllerMap.put(controller, target);
        targetMap.put(target, controller);
        playerController.setup(controller, target);
        return true;
    }

    public static void stopControl(Player target) {
        Player controller = targetMap.get(target);
        if(controller != null) {
            ControlData playerController = controlDataMap.get(controller);
            if(playerController != null) {
                playerController.reset();
                controlDataMap.remove(controller);
                targetMap.remove(target);
                controllerMap.remove(controller);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if(controllerMap.containsKey(event.getPlayer())) {
            //controllerMap.get(event.getPlayer()).setGameMode(GameMode.SPECTATOR);
            //controllerMap.get(event.getPlayer()).setSpectatorTarget();
            controllerMap.get(event.getPlayer()).teleport(event.getTo());
        } else if(isControlled(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onJump(PlayerJumpEvent event) {
        if(controllerMap.containsKey(event.getPlayer())) {
            //controllerMap.get(event.getPlayer()).setGameMode(GameMode.SPECTATOR);
            //controllerMap.get(event.getPlayer()).setSpectatorTarget();
            controllerMap.get(event.getPlayer()).teleport(event.getTo());
        } else if(isControlled(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onToggleFlightEvent(PlayerToggleFlightEvent event) {
        if(isController(event.getPlayer())) {
            controllerMap.get(event.getPlayer()).setFlying(event.isFlying());
        }
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        if(isControlled(event.getPlayer())) event.setCancelled(true);
        else if(isController(event.getPlayer())) {
            controllerMap.get(event.getPlayer()).getInventory().setHeldItemSlot(event.getNewSlot());
        }
    }

    @EventHandler
    public void InvEvent(InventoryClickEvent event) {
            //event.getWhoClicked().sendMessage("clicked!");
            if(isControlled((Player) event.getWhoClicked())) event.setCancelled(true);
            else if(isController((Player) event.getWhoClicked())) {
                for(int i = 0; i < event.getInventory().getSize(); i++) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Bugfix.getInstance(), () -> {
                        controllerMap.get((Player) event.getWhoClicked()).getInventory().setContents(event.getWhoClicked().getInventory().getContents());
                        controllerMap.get((Player) event.getWhoClicked()).updateInventory();
                    }, 1);
                }
            }
    }

    @EventHandler
    public void DropItem(PlayerDropItemEvent event) {
        //event.getWhoClicked().sendMessage("clicked!");
        if(isControlled(event.getPlayer())) event.setCancelled(true);
        else if(isController(event.getPlayer())) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Bugfix.getInstance(), () -> {
                    controllerMap.get(event.getPlayer()).getInventory().setContents(event.getPlayer().getInventory().getContents());
                    controllerMap.get(event.getPlayer()).updateInventory();
                }, 1);
        }
    }

    @EventHandler
    public void PickupItem(PlayerAttemptPickupItemEvent event) {
        //event.getWhoClicked().sendMessage("clicked!");
        if(isControlled(event.getPlayer())) event.setCancelled(true);
        else if(isController(event.getPlayer())) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Bugfix.getInstance(), () -> {
                controllerMap.get(event.getPlayer()).getInventory().setContents(event.getPlayer().getInventory().getContents());
                controllerMap.get(event.getPlayer()).updateInventory();
            }, 1);
        }
    }

    @EventHandler
    public void PlaceBlock(BlockPlaceEvent event) {
        if(isControlled(event.getPlayer())) event.setCancelled(true);
    }

    @EventHandler
    public void BreakBlock(BlockBreakEvent event) {
        if(isControlled(event.getPlayer())) event.setCancelled(true);
    }

    private static class ControlData {

        private Location bupControllerPos;
        private boolean bupAllowFly;
        private boolean bupCollidable;
        private ItemStack[] bupInventory;

        private Player controller;
        private Player target;

        public void setup(Player controller, Player target) {
            this.controller = controller;
            this.target = target;
            this.bupAllowFly = target.getAllowFlight();
            this.bupCollidable = controller.isCollidable();
            this.bupInventory = controller.getInventory().getContents();

            bupControllerPos = controller.getLocation().clone();

            target.setWalkSpeed(0);
            target.setFlySpeed(0);
            //target.set
            //target.setGravity(false);
            //target.setAllowFlight(true);

            controller.teleport(target);
            controller.setInvisible(true);
            controller.setInvulnerable(true);
            (controller).setCollidable(false);
            controller.hidePlayer(Bugfix.getInstance(), target);
            controller.getInventory().setContents(target.getInventory().getContents());
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.hidePlayer(Bugfix.getInstance(), controller);
            }
            target.hidePlayer(Bugfix.getInstance(), controller);
        }

        public void reset() {
            controller.teleport(bupControllerPos);

            target.setWalkSpeed(0.2f);
            target.showPlayer(Bugfix.getInstance(), controller);
            for(Player p : Bukkit.getOnlinePlayers()) {
                if(VanishManager.isVanished(controller)) return;
                p.showPlayer(Bugfix.getInstance(), controller);
            }
            controller.showPlayer(Bugfix.getInstance(), target);
            target.setFlySpeed(0.1f);
            target.setGravity(true);
            target.setAllowFlight(bupAllowFly);
            target.setCollidable(bupCollidable);
            controller.getInventory().setContents(bupInventory);
            controller.setInvisible(false);
            controller.setInvulnerable(false);
        }

    }

}
