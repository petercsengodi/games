package hu.csega.superstition.t3dcreator;

import hu.csega.superstition.gamelib.legacy.modeldata.CEdge;
import hu.csega.superstition.gamelib.legacy.modeldata.CTriangle;
import hu.csega.superstition.tools.Updates;
import hu.csega.superstition.tools.presentation.ToolView;

public class TreeObjectView extends ToolView {

	private System.Windows.Forms.TreeView treeView1;

	public TreeObjectView() {
		// This call is required by the Windows.Forms Form Designer.
		InitializeComponent();
	}

	/// <summary>
	/// Clean up any resources being used.
	/// </summary>
	protected void Dispose(boolean disposing)
	{
		if( disposing )
		{
			if(components != null)
			{
				components.Dispose();
			}
		}
		base.Dispose( disposing );
	}

	/// <summary>
	/// Required method for Designer support - do not modify
	/// the contents of this method with the code editor.
	/// </summary>
	private void InitializeComponent()
	{
		this.treeView1 = new System.Windows.Forms.TreeView();
		this.SuspendLayout();
		//
		// treeView1
		//
		this.treeView1.Dock = System.Windows.Forms.DockStyle.Fill;
		this.treeView1.ImageIndex = -1;
		this.treeView1.Location = new System.Drawing.Point(0, 0);
		this.treeView1.Name = "treeView1";
		this.treeView1.SelectedImageIndex = -1;
		this.treeView1.Size = new System.Drawing.Size(150, 384);
		this.treeView1.TabIndex = 0;
		this.treeView1.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.treeView1_AfterSelect);
		//
		// TreeObjectView
		//
		this.Controls.Add(this.treeView1);
		this.Name = "TreeObjectView";
		this.Size = new System.Drawing.Size(150, 384);
		this.ResumeLayout(false);

	}

	public void UpdateView(Updates update)
	{
		if((update == Updates.Selection) ||
				(update == Updates.Move))
		{
			//			treeView1.SelectedNode = null;
			return;
		}

		TreeNode selected = null;
		treeView1.BeginUpdate();
		treeView1.Nodes.Clear();
		CModel model = (CModel)GetData();
		IPart sel = model.Selected as IPart;
		for(CFigure figure : model.figures)
		{
			TreeNode f = new TreeNode(figure.ToString());
			treeView1.Nodes.Add(f);
			if((sel != null) && (sel.hasPart(figure))) selected = f;

			for(CTriangle triangle : figure.triangles)
			{
				TreeNode t = new TreeNode(triangle.ToString());
				f.Nodes.Add(t);
				if((sel != null) && (sel.hasPart(triangle))) selected = t;

				for(CEdge edge : triangle.edges)
				{
					TreeNode v = new TreeNode(edge.from.ToString());
					t.Nodes.Add(v);
					if((sel != null) && (sel.hasPart(edge.from))) selected = v;
				} // End of foreach Vertices

				for(CEdge edge : triangle.edges)
				{
					TreeNode e = new TreeNode(edge.ToString());
					t.Nodes.Add(e);
					if((sel != null) && (sel.hasPart(edge))) selected = e;
				} // End of foreach Edges

			} // End of foreach Triangles

		} // End of foreach Figures

		//		if(selected != null) treeView1.SelectedNode = selected;

		treeView1.EndUpdate();
	}

	protected void InitializeView()
	{

	}

	protected void CloseView()
	{

	}

	private void treeView1_AfterSelect(Object sender, System.Windows.Forms.TreeViewEventArgs e)
	{
		if(e.Action != TreeViewAction.ByMouse) return;
		(GetData() as CModel).Selected = GetSelectedItem(e.Node);
	}

	/// <summary>
	/// Gets, which object is selected in the Tree View.
	/// </summary>
	/// <param name="item">Selected Node in Tree View.</param>
	/// <returns>Refenrece of the selected object.</returns>
	private Object GetSelectedItem(TreeNode item)
	{
		Object ret = null;
		CModel model = GetData() as CModel;
		IPart sel = model.Selected as IPart;
		int[] idx = new int[3];
		idx[0] = idx[1] = idx[2] = -1;
		TreeNode parent = item.Parent, actual = item;
		while(parent != null)
		{
			idx[2] = idx[1];
			idx[1] = idx[0];
			idx[0] = parent.Nodes.IndexOf(actual);
			actual = parent;
			parent = actual.Parent;
		}

		idx[2] = idx[1];
		idx[1] = idx[0];
		idx[0] = treeView1.Nodes.IndexOf(actual);

		if(idx[1] == -1)
		{ // A figure is selected.
			ret = model.figures[idx[0]];
		}
		else if(idx[2] == -1)
		{ // A triangle is selected.
			ret = (model.figures[idx[0]] as CFigure).triangles[idx[1]];
		}
		else
		{ // A vertex or edge is selected.
			if(idx[2] < 3)
			{
				ret = ((model.figures[idx[0]] as CFigure).triangles[idx[1]]
						as CTriangle).edges[idx[2]].from;
			}
			else
			{
				ret = ((model.figures[idx[0]] as CFigure).triangles[idx[1]]
						as CTriangle).edges[idx[2] - 3];
			}
		}

		return ret;
	}

} // End of class TreeObjectView