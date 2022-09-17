package hu.csega.editors.anm.layer1.view3d;

import hu.csega.editors.anm.AnimatorRenderStep;
import hu.csega.editors.anm.components.Component3DView;

public class Animator3DView implements Component3DView {

	private AnimatorRenderStep renderer;
	private AnimatorSet set;

	@Override
	public void setRenderer(AnimatorRenderStep renderer) {
		this.renderer = renderer;
	}

	@Override
	public AnimatorSet provide() {
		return set;
	}

	@Override
	public void accept(AnimatorSet set) {
		this.set = set;

		if(this.set == null) {
			this.set = new AnimatorSet();
		}

		if(renderer != null) {
			renderer.setAnimatorSet(set);
		}
	}

}
