package hu.csega.editors.anm.layer1.swing.components.partlist;

public class AnimatorPartListItem {

	private int index;
	private String name;
	private String mesh;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMesh() {
		return mesh;
	}

	public void setMesh(String mesh) {
		this.mesh = mesh;
	}

	@Override
	public String toString() {
		if(name == null) {
			return nameFromIndexAndMesh();
		}

		String trimmed = name.trim();
		if(trimmed.length() == 0) {
			return nameFromIndexAndMesh();
		}

		return trimmed;
	}

	private String nameFromIndexAndMesh() {
		return "[Index: " + index + ", " + mesh + ']';
	}
}
