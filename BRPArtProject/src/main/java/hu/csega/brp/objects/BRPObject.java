package hu.csega.brp.objects;

public class BRPObject {

	private long randomSeed;
	private String label;
	private BRPType type;

	protected BRPObject(BRPType type) {
		this.randomSeed = System.currentTimeMillis();
		this.type = type;
		this.label = type.getLabel();
	}

	public long getRandomSeed() {
		return randomSeed;
	}

	public String getLabel() {
		return label;
	}

	public BRPType getType() {
		return type;
	}

}
