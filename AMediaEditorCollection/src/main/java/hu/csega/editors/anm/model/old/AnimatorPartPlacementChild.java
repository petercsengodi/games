package hu.csega.editors.anm.model.old;

public class AnimatorPartPlacementChild {

	public String joint;
	public AnimatorPartPlacement partPlacement = new AnimatorPartPlacement();

	public AnimatorPartPlacementChild() {
	}

	public AnimatorPartPlacementChild(AnimatorPartPlacementChild other) {
		copyFrom(other);
	}

	public void copyFrom(AnimatorPartPlacementChild other) {
		this.joint = other.joint;
		this.partPlacement.copyFrom(other.partPlacement);
	}

}
