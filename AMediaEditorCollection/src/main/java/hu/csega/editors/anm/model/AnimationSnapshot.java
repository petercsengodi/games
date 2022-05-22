package hu.csega.editors.anm.model;

public class AnimationSnapshot {

	private byte[] bytes;
	private long timestamp;

	public AnimationSnapshot(byte[] bytes) {
		this.bytes = bytes;
		this.timestamp = System.currentTimeMillis();
	}

	public byte[] getBytes() {
		return bytes;
	}

	public long getTimestamp() {
		return timestamp;
	}

}
