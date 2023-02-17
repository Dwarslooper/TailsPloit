package com.eimacon.bugfix.nbsapi;

public class NbNote {

	private NbInstrument instrument;

	private byte volume;
	private byte pitch;
	private float mcVolume;
	private float mcPitch;
	
	public NbNote(NbInstrument instrument, byte volume, byte pitch) throws IllegalArgumentException {
		if (pitch < 0 || pitch > 87) throw new IllegalArgumentException("Pitch must be from 0 to 87.");
		setInstrument(instrument);
		setVolume(volume);
		setPitch(pitch);
	}
	
	public byte getVolume() {
		return this.volume;
	}
	
	public void setInstrument(NbInstrument instrument) {
		this.instrument = instrument;
	}

	public NbInstrument getInstrument() {
		return instrument;
	}
	
	public void setVolume(byte volume) {
		if(volume < 0 || volume > 100) throw new IllegalArgumentException("Volume must be from 0 to 100.");
		this.volume = volume;
		this.mcVolume = volume / 50F;
	}

	public byte getPitch() {
		return pitch;
	}

	public void setPitch(byte pitch) throws IllegalArgumentException {
		if (pitch < 0 || pitch > 87) throw new IllegalArgumentException("Pitch must be from 0 to 87.");
		this.pitch = pitch;
		int mcNote = pitch - 33;
		if(mcNote < 0) mcNote = 0;
		if(mcNote > 24) mcNote = 24;
		this.mcPitch = (float)Math.pow(2.0D, (mcNote - 12.0D) / 12.0D);
	}
	
	public float getMinecraftPitch() {
		return mcPitch;
	}

	public float getMinecraftVolume() {
		return this.mcVolume;
	}
}
