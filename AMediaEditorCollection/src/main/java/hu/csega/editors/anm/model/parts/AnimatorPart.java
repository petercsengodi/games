package hu.csega.editors.anm.model.parts;

import java.util.List;

public class AnimatorPart {

	private String id;
	private String displayName;
	private AnimatorTexture texture;
	private List<AnimatorTriangle> triangles;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public AnimatorTexture getTexture() {
		return texture;
	}

	public void setTexture(AnimatorTexture texture) {
		this.texture = texture;
	}

	public List<AnimatorTriangle> getTriangles() {
		return triangles;
	}

	public void setTriangles(List<AnimatorTriangle> triangles) {
		this.triangles = triangles;
	}

	@Override
	public String toString() {
		return displayName;
	}

}
