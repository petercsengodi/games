package hu.csega.editors.anm.model;

import java.util.ArrayList;
import java.util.List;

public class AnimatorPartPlacement {

	public String part;
	public AnimatorTransformation transformation = new AnimatorTransformation();
	public List<AnimatorPartPlacementChild> children = new ArrayList<>();

	public AnimatorPartPlacement() {
	}

	public AnimatorPartPlacement(AnimatorPartPlacement other) {
		copyFrom(other);
	}

	public void copyFrom(AnimatorPartPlacement other) {
		this.part = other.part;
		this.transformation.copyFrom(other.transformation);
		this.children.clear();
		this.children.addAll(other.children);
	}

}
