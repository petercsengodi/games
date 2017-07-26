package hu.csega.superstition.animation;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import hu.csega.superstition.tools.presentation.ToolView;

public class TreeObjectView extends ToolView implements TreeModel, TreeSelectionListener {

	private AnimationModel model;
	private JTree treeView1;
	private List<TreeModelListener> modelListeners = new ArrayList<>();

	public TreeObjectView(AnimationModel model) {
		this.model = model;
		InitializeComponent();
	}

	private void InitializeComponent()
	{
		this.treeView1 = new JTree();
		this.treeView1.setSize(150, 384);
		this.treeView1.addTreeSelectionListener(this);
		this.treeView1.setModel(this);

		this.setLayout(new FlowLayout());
		this.add(treeView1);
		this.setPreferredSize(new Dimension(150, 384));
	}


	@Override
	public void updateView(Updates update) {
		if(update == Updates.SELECTION_ONLY || update == Updates.ACTION_ONLY) {
			return;
		}

		treeView1.repaint();
	}

	/**
	 * Gets, which object is selected in the Tree View.
	 */
	private Object selectedItem(TreeNode item)
	{
		Object ret = null;
		CModel model = GetData() as CModel;
		int idx = treeView1.Nodes.IndexOf(item);
		if(idx != -1) ret = (GetData() as CModel).parts[idx];
		return ret;
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public Object getRoot() {
		return model;
	}

	@Override
	public Object getChild(Object parent, int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getChildCount(Object parent) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isLeaf(Object node) {
		if(node == model)
			return false;
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		modelListeners.add(l);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		modelListeners.remove(l);
	}

	private static final long serialVersionUID = 1L;
}
