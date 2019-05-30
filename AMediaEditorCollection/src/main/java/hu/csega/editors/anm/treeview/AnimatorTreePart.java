package hu.csega.editors.anm.treeview;

import java.util.List;

import hu.csega.editors.anm.model.AnimatorScene;
import hu.csega.editors.anm.model.parts.AnimatorJoint;
import hu.csega.editors.anm.model.parts.AnimatorPart;

public class AnimatorTreePart implements AnimatorTreeNode {

	private List<AnimatorTreePart> cachedChildren = null;
	private final AnimatorPart wrapped;
	private final AnimatorScene scene;

	public AnimatorTreePart(AnimatorPart wrapped, AnimatorScene scene) {
		this.wrapped = wrapped;
		this.scene = scene;
	}

	@Override
	public List<? extends AnimatorTreeNode> getChildren() {
		if(cachedChildren == null) {
			for(AnimatorJoint joint : wrapped.joints) {
				if(joint.name != null) {
					// ??????? AnimatorPart part = scene.partPlacements.get(joint.name);
					// ????? cachedChildren.add(new AnimatorTreePart(part, scene));
				}
			}
		}

		return cachedChildren;
	}

	@Override
	public String toString() {
		return wrapped.name;
	}
}
