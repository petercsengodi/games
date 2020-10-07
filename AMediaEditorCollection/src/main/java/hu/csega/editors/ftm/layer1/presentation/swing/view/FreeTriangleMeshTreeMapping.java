package hu.csega.editors.ftm.layer1.presentation.swing.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import hu.csega.games.engine.GameEngineFacade;

public class FreeTriangleMeshTreeMapping implements TreeModel {

	private Set<TreeModelListener> listeners = new HashSet<>();
	private String root = "Mesh";
	private List<FreeTriangleMeshGroupTreeNode> nodes;

	public FreeTriangleMeshTreeMapping(GameEngineFacade facade) {
		this.nodes = new ArrayList<>();
		for(int groupIndex = 1; groupIndex <= 9; groupIndex++) {
			FreeTriangleMeshGroupTreeNode node = new FreeTriangleMeshGroupTreeNode(facade, groupIndex);
			nodes.add(node);
		}
	}

	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public Object getChild(Object parent, int nodeIndex) {
		try {
			if(parent == root && nodeIndex >= 0 && nodeIndex < 9) {
				return nodes.get(nodeIndex);
			}
		} catch(RuntimeException ex) {
			ex.printStackTrace();
		}

		return "";
	}

	@Override
	public int getChildCount(Object parent) {
		if(parent == root) {
			return nodes.size();
		} else {
			return 0;
		}
	}

	@Override
	public boolean isLeaf(Object parent) {
		return (parent != root);
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
	}

	@Override
	public int getIndexOfChild(Object parent, Object childObject) {
		try {
			if(parent == root) {
				return nodes.indexOf(childObject);
			}
		} catch(RuntimeException ex) {
			ex.printStackTrace();
		}

		return -1;
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
