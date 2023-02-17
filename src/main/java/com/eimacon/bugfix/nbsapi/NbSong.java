package com.eimacon.bugfix.nbsapi;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class NbSong {

	public static NbSong load(InputStream in) throws IOException {
		DataInputStream input = new DataInputStream(in);
		short songLength = input.readShort();
		byte version = 0;
		if(songLength == 0) {
			version = input.readByte();
			input.readByte(); //Vanilla Instrument Count
			if (version >= 3)
				songLength = readShort(input);
		}
		short songHeight = readShort(input);
		String songName = readString(input);
		String author = readString(input);
		String originalAuthor = readString(input);
		String description = readString(input);
		float speed = readShort(input) / 100f;
		input.readBoolean(); //Auto Save
		input.readByte(); //Auto Save Duration
		input.readByte(); // Time Signature
		readInt(input); // Minutes Spent
		readInt(input); // Left Clicks
		readInt(input); // Right Clicks
		readInt(input); // Blocks Added
		readInt(input); // Blocks Removed
		readString(input); // Midi Schematic File Path

		if(version >= 1) {
			input.readBoolean(); // is Loop
			input.readByte(); // Max Loop Count
			readShort(input); // Loop Start Tick
		}

		HashMap<Short, NbLayer> layerMap = new HashMap<>();

		short tick = -1;
		while (true) {
			short jumpDistance = readShort(input);
			if (jumpDistance == 0) break;
			tick += jumpDistance;
			short layer = -1;
			while (true) {
				short jumpLayers = readShort(input);
				if (jumpLayers == 0) break;
				layer += jumpLayers;
				NbInstrument instrument = NbInstrument.fromID(input.readByte());

				NbLayer nLayer = layerMap.get(layer);
				if(nLayer == null)
					layerMap.put(layer, nLayer = new NbLayer("",(byte) 100));

				byte pitch = input.readByte(); //Pitch
				byte volume = input.readByte(); //Note Velocity
				nLayer.setNote(tick, new NbNote(instrument, volume, pitch));
				input.readByte(); //Note Padding
				readShort(input); //Block Note Pitch
			}
		}

		if (version > 0 && version < 3) {
			songLength = tick;
		}

		for (short i = 0; i < songHeight; i++) {
			NbLayer layer = layerMap.get(i);

			String layerName = readString(input);
			if (version >= 4)
				input.readBoolean(); // layer lock
			byte layerVolume = input.readByte();
			if (version >= 2)
				input.readByte(); // layer stereo

			if(layer != null) {
				layer.setName(layerName);
				layer.setVolume(layerVolume);
			}
		}
		input.close();

		return new NbSong(version, songLength, songHeight, songName, author, originalAuthor, description, speed, layerMap.values());
	}

	private static String readString(DataInputStream input) throws IOException {
		int length = readInt(input);
		StringBuilder sb = new StringBuilder(length);
		for (; length > 0; --length) {
			char c = (char) input.readByte();
			if (c == (char) 0x0D) {
				c = ' ';
			}
			sb.append(c);
		}
		return sb.toString();
	}

	private static short readShort(DataInputStream input) throws IOException {
		int byte1 = input.readUnsignedByte();
		int byte2 = input.readUnsignedByte();
		return (short) (byte1 + (byte2 << 8));
	}

	private static int readInt(DataInputStream input) throws IOException {
		int byte1 = input.readUnsignedByte();
		int byte2 = input.readUnsignedByte();
		int byte3 = input.readUnsignedByte();
		int byte4 = input.readUnsignedByte();
		return (byte1 + (byte2 << 8) + (byte3 << 16) + (byte4 << 24));
	}

	private final byte version;
	private final short songLength;
	private final short songHeight;
	private final String songName;
	private final String author;
	private final String originalAuthor;
	private final String description;
	private final float speed;
	private final Collection<NbLayer> layers;

	private NbSong(byte version, short songLength, short songHeight, String songName, String author, String originalAuthor,
				   String description, float speed, Collection<NbLayer> layers) {
		this.version = version;
		this.songLength = songLength;
		this.songHeight = songHeight;
		this.songName = songName;
		this.author = author;
		this.originalAuthor = originalAuthor;
		this.description = description;
		this.speed = speed;
		this.layers = layers;
	}

	public byte getVersion() {
		return this.version;
	}

	public short getLength() {
		return this.songLength;
	}

	public short getSongHeight() {
		return this.songHeight;
	}

	public String getName() {
		return this.songName;
	}

	public String getAuthor() {
		return this.author;
	}

	public String getOriginalAuthor() {
		return this.originalAuthor;
	}

	public String getDescription() {
		return this.description;
	}

	public float getSpeed() {
		return this.speed;
	}

	public Collection<NbLayer> getLayerList() {
		return layers;
	}

}
