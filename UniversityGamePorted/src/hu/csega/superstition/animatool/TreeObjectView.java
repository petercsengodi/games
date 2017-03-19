package hu.csega.superstition.animatool;

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
	protected void Dispose(boolean disposing )
	{
		if( disposing )
		{
			if(components != null)
			{
				components.Dispose();
			}
		}
		super.Dispose( disposing );
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

		//		TreeNode selected = null;
		treeView1.BeginUpdate();
		treeView1.Nodes.Clear();
		CModel model = (CModel)GetData();

		for(CPart p : model.parts)
		{
			treeView1.Nodes.Add(new TreeNode(p.ToString()));
		}

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
		CModel model = (GetData() as CModel);
		model.Selected = GetSelectedItem(e.Node);
		model.UpdateViews(Updates.Selection);
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
		int idx = treeView1.Nodes.IndexOf(item);
		if(idx != -1) ret = (GetData() as CModel).parts[idx];
		return ret;
	}

} // End of class TreeObjectView