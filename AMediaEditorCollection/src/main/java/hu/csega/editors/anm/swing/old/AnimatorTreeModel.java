package hu.csega.editors.anm.swing.old;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import hu.csega.editors.anm.model.old.AnimatorScene;

public class AnimatorTreeModel implements TreeModel {

	private Set<TreeModelListener> listeners = new HashSet<>();
	private AnimatorTreeRoot root = new AnimatorTreeRoot();

	public void update(AnimatorScene scene) {
		root.update(scene);
	}

	@Override
	public AnimatorTreeRoot getRoot() {
		return root;
	}

	@Override
	public AnimatorTreeNode getChild(Object parent, int index) {
		AnimatorTreeNode node = (AnimatorTreeNode) parent;
		List<? extends AnimatorTreeNode> children = node.getChildren();
		return children.get(index);
	}

	@Override
	public int getChildCount(Object parent) {
		AnimatorTreeNode node = (AnimatorTreeNode) parent;
		List<? extends AnimatorTreeNode> children = node.getChildren();
		return children.size();
	}

	@Override
	public boolean isLeaf(Object parent) {
		AnimatorTreeNode node = (AnimatorTreeNode) parent;
		List<? extends AnimatorTreeNode> children = node.getChildren();
		return (children == null || children.isEmpty());
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
	}

	@Override
	public int getIndexOfChild(Object parent, Object childObject) {
		AnimatorTreeNode node = (AnimatorTreeNode) parent;
		AnimatorTreeNode child = (AnimatorTreeNode) childObject;
		List<? extends AnimatorTreeNode> children = node.getChildren();
		return children.indexOf(child);
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		listeners.remove(l);
	}


}
