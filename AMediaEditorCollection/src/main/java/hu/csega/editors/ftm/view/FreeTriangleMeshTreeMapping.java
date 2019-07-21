package hu.csega.editors.ftm.view;

import java.util.HashSet;
import java.util.Set;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import hu.csega.editors.ftm.model.FreeTriangleMeshModel;
import hu.csega.games.engine.GameEngineFacade;

public class FreeTriangleMeshTreeMapping implements TreeModel {

	private Set<TreeModelListener> listeners = new HashSet<>();
	private String root = "Mesh";
	private GameEngineFacade facade;

	public FreeTriangleMeshTreeMapping(GameEngineFacade facade) {
		this.facade = facade;
	}

	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public Object getChild(Object parent, int index) {
		try {
			if(parent == root && index >= 0 && index < 9) {
				FreeTriangleMeshModel model = (FreeTriangleMeshModel) facade.model();
				if(model != null) {
					return model.getGroups().get(index);
				}
			}
		} catch(RuntimeException ex) {
			ex.printStackTrace();
		}

		return null;
	}

	@Override
	public int getChildCount(Object parent) {
		if(parent == root) {
			return 9;
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
				FreeTriangleMeshModel model = (FreeTriangleMeshModel) facade.model();
				if(model != null) {
					return model.getGroups().indexOf(childObject);
				}
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
