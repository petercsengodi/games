package hu.csega.editors.anm.layer1.view3d;

import hu.csega.editors.anm.components.Component3DView;
import hu.csega.editors.anm.components.ComponentOpenGLTransformer;
import hu.csega.games.units.UnitStore;

public class AnimatorOpenGLTransformer implements ComponentOpenGLTransformer {

	private Component3DView view;

	private AnimatorSet set;

	@Override
	public void accept(AnimatorSet set) {
		if(view == null) {
			view = UnitStore.instance(Component3DView.class);
			if(view == null) {
				return;
			}
		}

		this.set = set;
		view.accept(this.set);
	}

}
