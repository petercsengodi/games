package hu.csega.editors.anm.layer1.view3d;

import java.util.List;

import hu.csega.games.engine.g3d.GameObjectPlacement;

public class AnimatorSet {

	private GameObjectPlacement camera;
	private List<AnimatorSetPart> parts;

	public GameObjectPlacement getCamera() {
		return camera;
	}

	public void setCamera(GameObjectPlacement camera) {
		this.camera = camera;
	}

	public List<AnimatorSetPart> getParts() {
		return parts;
	}

	public void setParts(List<AnimatorSetPart> parts) {
		this.parts = parts;
	}

}
