package hu.csega.editors.anm.layer1.swing.wireframe;

import java.util.Map;

import hu.csega.editors.anm.components.ComponentWireFrameConverter;
import hu.csega.editors.anm.layer4.data.animation.Animation;
import hu.csega.editors.anm.layer4.data.animation.AnimationPart;
import hu.csega.editors.anm.layer4.data.model.AnimationPersistent;
import hu.csega.editors.anm.layer4.data.model.AnimatorModel;
import hu.csega.games.library.MeshLibrary;
import hu.csega.games.library.model.SMeshRef;
import hu.csega.games.library.model.mesh.v1.SMesh;
import hu.csega.games.units.UnitStore;

public class AnimatorWireFrameConverter implements ComponentWireFrameConverter {

	private MeshLibrary meshLibrary;
	private AnimatorWireFrame wireFrame;

	@Override
	public AnimatorWireFrame transform(AnimatorModel model) {
		if(meshLibrary == null) {
			meshLibrary = UnitStore.instance(MeshLibrary.class);
		}

		// FIXME: some lightweight pattern not to always create new objects or something
		AnimatorWireFrame result = new AnimatorWireFrame();

		AnimationPersistent persistent = model.getPersistent();
		if(persistent != null) {
			Animation animation = persistent.getAnimation();
			if(animation != null) {
				Map<Integer, AnimationPart> allParts = animation.getParts();
				if(allParts != null) {
					collectWireFrame(result, allParts);
				}
			}
		}

		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public void accept(AnimatorModel model) {
		wireFrame = transform(model);
	}

	@Override
	public AnimatorWireFrame provide() {
		return wireFrame;
	}

	private void collectWireFrame(AnimatorWireFrame result, Map<Integer, AnimationPart> allParts) {
		for(Map.Entry<Integer, AnimationPart> entry : allParts.entrySet()) {
			AnimationPart part = entry.getValue();
			collectWireframe(result, part);
		}
	}

	private void collectWireframe(AnimatorWireFrame result, AnimationPart part) {
		SMeshRef ref = new SMeshRef(part.getMesh());
		SMesh mesh = meshLibrary.resolve(ref);
		mesh.getClass();
	}

}
