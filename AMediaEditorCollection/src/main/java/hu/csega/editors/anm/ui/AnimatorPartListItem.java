package hu.csega.editors.anm.ui;

public class AnimatorPartListItem {

	private int index;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		if(name == null) {
			return nameFromIndex();
		}

		String trimmed = name.trim();
		if(trimmed.length() == 0) {
			return nameFromIndex();
		}

		return trimmed;
	}

	private String nameFromIndex() {
		return "[Index: " + index + ']';
	}
}
