package hu.csega.editors.anm.treeview;

import java.util.ArrayList;
import java.util.List;

import hu.csega.editors.anm.model.AnimatorScene;
import hu.csega.editors.anm.model.parts.AnimatorPart;

public class AnimatorTreeRoot implements AnimatorTreeNode {

	private List<AnimatorTreeNode> children = new ArrayList<>();
	private AnimatorScene scene = null;

	public void update(AnimatorScene scene) {
		this.scene = scene;
		this.children.clear();

		for(String root : scene.roots) {
			AnimatorPart part = scene.parts.get(root);
			children.add(new AnimatorTreePart(part, scene));
		}
	}

	@Override
	public List<? extends AnimatorTreeNode> getChildren() {
		return children;
	}

	@Override
	public String toString() {
		return (isSaved() ? "Animation" : "Animation [not saved!!!]");
	}

	private boolean isSaved() {
		return false;
	}
}
