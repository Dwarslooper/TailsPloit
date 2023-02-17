package com.eimacon.bugfix.nbsapi;

import java.util.HashMap;

public class NbLayer {
	
	private HashMap<Integer, NbNote> noteMap = new HashMap<Integer, NbNote>();
	private String name = null;
	private byte volume = -1;
	
	/**
	 * Creates an empty layer with name and volume.
	 * @param name The layer's name.
	 * @param volume The layer's volume (0-100).
	 * @throws IllegalArgumentException
	 */
	public NbLayer(String name, byte volume) throws IllegalArgumentException {
		setName(name);
		setVolume(volume);
	}
	
	/**
	 * Returns the note list of the layer.
	 * @return The map.
	 */
	public HashMap<Integer, NbNote> getNoteMap() {
		return noteMap;
	}
	
	public void setNote(int pos, NbNote note) throws IllegalArgumentException {
		if (pos < 0) throw new IllegalArgumentException("Note position must not be negative.");
		noteMap.put(pos, note);
	}
	
	public NbNote getNote(int pos) {
		if (pos < 0) throw new IllegalArgumentException("Note position must not be negative.");
		return this.noteMap.get(pos);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getVolume() {
		return volume;
	}

	public void setVolume(byte volume) throws IllegalArgumentException {
		if (volume < 0 || volume > 100) throw new IllegalArgumentException("Volume must be from 0 to 100.");
		this.volume = volume;

		for(NbNote note : noteMap.values()) {
			note.setVolume((byte) (note.getVolume() * volume / 100));
		}
	}
	
}
