package hu.csega.editors.ftm.model;

import java.io.Serializable;

public class FreeTriangleMeshGroup implements Serializable {

	private int group;
	private String name;
	private boolean enabled;

	public FreeTriangleMeshGroup() {
		this.group = 0;
		this.name = "unknown";
		this.enabled = false;
	}

	public FreeTriangleMeshGroup(int group) {
		this.group = group;
		this.name = "unknown";
		this.enabled = true;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return group + " – " + name + " (" + (enabled ? "on" : "off") + ')';
	}

	private static final long serialVersionUID = 1L;
}
