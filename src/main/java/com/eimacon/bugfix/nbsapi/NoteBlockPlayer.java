package com.eimacon.bugfix.nbsapi;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class NoteBlockPlayer {

	private final Plugin plugin;

	private NbSong song = null;
	private Runnable stopHandler = null;
	private boolean loop = false;
	private Location location;
	private BukkitTask task = null;
	private int tickCount = 0;

	public NoteBlockPlayer(Plugin plugin) {
		this.plugin = plugin;
	}

	private void tick() {
		if(tickCount >= song.getLength()) {
			if(isLoop()) {
				tickCount = 0;
			} else {
				Bukkit.getScheduler().runTask(plugin, this::stop);
				return;
			}
		}

		for(NbLayer layer : song.getLayerList()) {
			NbNote note = layer.getNote(tickCount);
			if(note != null) {
				if(location == null) {
					for(Player online : Bukkit.getOnlinePlayers()) {
						online.playSound(
								online.getLocation().clone().add(0, -1, 0),
								note.getInstrument().getMinecraftSound(),
								SoundCategory.RECORDS,
								note.getMinecraftVolume(),
								note.getMinecraftPitch()
						);
					}
				} else {
					location.getWorld().playSound(
							location,
							note.getInstrument().getMinecraftSound(),
							SoundCategory.RECORDS,
							note.getMinecraftVolume(),
							note.getMinecraftPitch()
					);
				}
			}
		}

		tickCount++;
	}

	public void play(NbSong song) {
		if(isPlaying()) stop();

		this.song = song;

		tickCount = 0;

		int period = Math.round(20f / song.getSpeed());

		if(task != null)
			task.cancel();

		task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::tick, 0, period);
	}

	public void stop() {
		if(!isPlaying()) return;

		if(task != null) {
			task.cancel();
			task = null;
		}

		if(stopHandler != null)
			stopHandler.run();

		tickCount = 0;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public boolean isLoop() {
		return loop;
	}

	public void setStopHandler(Runnable runnable) {
		this.stopHandler = runnable;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public boolean isPlaying() {
		return task != null;
	}

	public NbSong getSong() {
		return song;
	}

	public int getTickCount() {
		return tickCount;
	}

	public void setTickCount(int tickCount) {
		this.tickCount = tickCount;
	}

	public boolean isFinalEnd() {
		return tickCount >= song.getLength();
	}

}
