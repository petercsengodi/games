package hu.csega.editors.anm.treeview;

import java.util.ArrayList;
import java.util.List;

public class AnimatorTreeRoot implements AnimatorTreeNode {

	private List<AnimatorTreeNode> children = new ArrayList<>();

	@Override
	public List<AnimatorTreeNode> getChildren() {
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
