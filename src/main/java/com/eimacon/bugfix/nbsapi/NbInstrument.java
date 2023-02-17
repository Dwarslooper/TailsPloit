package com.eimacon.bugfix.nbsapi;

import org.bukkit.Sound;

public enum NbInstrument {
	PIANO (0, "HARP"),
	BASS_GUITAR (1, "BASS"),
	BASS_DRUM (2, "BASEDRUM"),
	SNARE_DRUM (3, "SNARE"),
	STICK (4, "HAT"),
	GUITAR (5, "GUITAR"),
	FLUTE (6, "FLUTE"),
	BELL (7, "BELL"),
	CHIME (8, "CHIME"),
	XYLOPHONE (9, "XYLOPHONE"),
	IRON_XYLOPHONE (10, "IRON_XYLOPHONE"),
	COW_BELL (11, "COW_BELL"),
	DIDGERIDOO(12, "DIDGERIDOO"),
	BIT  (13, "BIT"),
	BANJO (14, "BANJO"),
	PLING (15, "PLING");
	
	private final int ID;
	private Sound mcSound;
	
	private NbInstrument(int ID, String mcSound) {
		this.ID = ID;
		try {
			this.mcSound = Sound.valueOf("BLOCK_NOTE_BLOCK_" + mcSound);
		} catch(IllegalArgumentException e) {
			this.mcSound = null;
		}
	}
	
	public int getID() {
		return ID;
	}
	
	public Sound getMinecraftSound() {
		return mcSound;
	}
	
	public static NbInstrument fromID(int ID) throws IllegalArgumentException {
		for(NbInstrument instrument : values())
			if(instrument.ID == ID)
				return instrument;
		throw new IllegalArgumentException("ID must be from 0 to 15.");
	}
}
