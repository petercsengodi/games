package hu.csega.editors.anm.view3d;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import hu.csega.editors.anm.components.Component3DView;
import hu.csega.editors.anm.model.Animation;
import hu.csega.editors.anm.model.AnimationMisc;
import hu.csega.editors.anm.model.AnimationPersistent;
import hu.csega.editors.anm.model.AnimationPlacement;
import hu.csega.editors.anm.model.AnimationVector;
import hu.csega.games.engine.g3d.GameObjectPlacement;

public class Animator3DView implements Component3DView {

	private Component canvas;
	private AnimatorSet set;

	@Override
	public AnimatorSet provide() {
		return set;
	}

	@Override
	public void accept(AnimationPersistent persistent) {
		if(set == null) {
			set = new AnimatorSet();
		}

		int currentScene = 0;
		GameObjectPlacement camera = new GameObjectPlacement();
		List<AnimatorSetPart> parts = new ArrayList<>();

		if(persistent != null) {
			AnimationMisc misc = persistent.getMisc();
			if(misc != null) {
				currentScene = misc.getCurrentScene();

				AnimationPlacement cam = misc.getCamera();
				if(cam != null) {
					AnimationVector pos = cam.getPosition();
					if(pos != null && pos.getV() != null) {
						float[] v = pos.getV();
						camera.position.set(v[0], v[1], v[2]);
					}

					AnimationVector tar = cam.getTarget();
					if(tar != null && tar.getV() != null) {
						float[] v = tar.getV();
						camera.target.set(v[0], v[1], v[2]);
					}

					AnimationVector up = cam.getTarget();
					if(up != null && up.getV() != null) {
						float[] v = up.getV();
						camera.up.set(v[0], v[1], v[2]);
					}
				}
			}

			Animation animation = persistent.getAnimation();
			if(animation != null) {
				animation.getScenes(); // TODO
			}

		}

		set.setCamera(camera);
		set.setParts(parts);
		if(canvas != null)
			canvas.repaint();
	}

}
