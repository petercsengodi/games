package hu.csega.superstition.animatool;

public class AnimaTool extends System.Windows.Forms.Form
{
	/// <summary>
	/// Required designer variable.
	/// </summary>
	private System.ComponentModel.Container components = null;
	private System.Windows.Forms.MainMenu mainMenu1;
	private System.Windows.Forms.MenuItem menuItem1;
	private System.Windows.Forms.MenuItem menuItem2;
	private System.Windows.Forms.MenuItem menuItem3;
	private System.Windows.Forms.MenuItem menuItem4;
	private System.Windows.Forms.MenuItem menuItem5;
	private System.Windows.Forms.MenuItem menuItem6;
	private System.Windows.Forms.MenuItem menuItem7;
	private System.Windows.Forms.MenuItem menuItem8;
	private System.Windows.Forms.MenuItem menuItem9;
	private System.Windows.Forms.TabControl tab_control;
	private System.Windows.Forms.TabPage tab_part_editor;
	private AnimaToolWindows.TreeObjectView trview;
	private AnimaToolWindows.PartEditor partEditor1;
	private System.Windows.Forms.MenuItem menuItem10;
	private System.Windows.Forms.MenuItem menuItem11;
	private AnimaToolWindows.WireFrameView wireFrameView1;
	private AnimaToolWindows.WireFrameView wireFrameView2;
	private AnimaToolWindows.WireFrameView wireFrameView3;
	private System.Windows.Forms.TabPage tab_scene_editor;
	private AnimaToolWindows.SceneEditor sceneEditor1;
	private System.Windows.Forms.Button grid_button;
	private System.Windows.Forms.RadioButton rad1_0;
	private System.Windows.Forms.RadioButton rad0_5;
	private System.Windows.Forms.RadioButton rad0_25;
	private System.Windows.Forms.TabControl tabControl1;
	private System.Windows.Forms.TabPage tabPage1;
	private System.Windows.Forms.TabPage tabPage2;
	private System.Windows.Forms.RadioButton rad0_1;
	private System.Windows.Forms.RadioButton rad0_05;
	private System.Windows.Forms.ComboBox operation_box;
	private System.Windows.Forms.Label label1;
	private AnimaToolWindows.DirectXView dxview;

	public AnimaTool()
	{
		//
		// Required for Windows Form Designer support
		//
		InitializeComponent();

		file_control = new FileOperations.FileControl(
				"Animation files (*.anm)|*.anm|All files (*.*)|*.*",
				"/res/anims",
				new FileOperations.FileOperation(LoadFile),
				new FileOperations.FileOperation(SaveFile));

		model = new CModel();
		model.RegisterView(dxview);
		model.RegisterView(trview);
		model.RegisterView(partEditor1);
		model.RegisterView(wireFrameView1);
		model.RegisterView(wireFrameView2);
		model.RegisterView(wireFrameView3);
		model.RegisterView(sceneEditor1);
		MeshLibrary.Initialize(dxview.Device);
		wireFrameView1.SetView(Perspectives.Front);
		wireFrameView2.SetView(Perspectives.Top);
		wireFrameView3.SetView(Perspectives.Left);
	}

	/// <summary>
	/// Clean up any resources being used.
	/// </summary>
	protected override void Dispose( bool disposing )
	{
		if( disposing )
		{
			if(components != null)
			{
				components.Dispose();
			}
			MeshLibrary.Instance().Dispose();
			file_control.Dispose();
		}
		base.Dispose( disposing );
	}

	/// <summary>
	/// Required method for Designer support - do not modify
	/// the contents of this method with the code editor.
	/// </summary>
	private void InitializeComponent()
	{
		this.trview = new AnimaToolWindows.TreeObjectView();
		this.mainMenu1 = new System.Windows.Forms.MainMenu();
		this.menuItem1 = new System.Windows.Forms.MenuItem();
		this.menuItem2 = new System.Windows.Forms.MenuItem();
		this.menuItem3 = new System.Windows.Forms.MenuItem();
		this.menuItem4 = new System.Windows.Forms.MenuItem();
		this.menuItem5 = new System.Windows.Forms.MenuItem();
		this.menuItem6 = new System.Windows.Forms.MenuItem();
		this.menuItem7 = new System.Windows.Forms.MenuItem();
		this.menuItem10 = new System.Windows.Forms.MenuItem();
		this.menuItem11 = new System.Windows.Forms.MenuItem();
		this.menuItem8 = new System.Windows.Forms.MenuItem();
		this.menuItem9 = new System.Windows.Forms.MenuItem();
		this.tab_control = new System.Windows.Forms.TabControl();
		this.tab_part_editor = new System.Windows.Forms.TabPage();
		this.partEditor1 = new AnimaToolWindows.PartEditor();
		this.tab_scene_editor = new System.Windows.Forms.TabPage();
		this.sceneEditor1 = new AnimaToolWindows.SceneEditor();
		this.dxview = new AnimaToolWindows.DirectXView();
		this.wireFrameView1 = new AnimaToolWindows.WireFrameView();
		this.wireFrameView2 = new AnimaToolWindows.WireFrameView();
		this.wireFrameView3 = new AnimaToolWindows.WireFrameView();
		this.grid_button = new System.Windows.Forms.Button();
		this.rad1_0 = new System.Windows.Forms.RadioButton();
		this.rad0_5 = new System.Windows.Forms.RadioButton();
		this.rad0_25 = new System.Windows.Forms.RadioButton();
		this.tabControl1 = new System.Windows.Forms.TabControl();
		this.tabPage1 = new System.Windows.Forms.TabPage();
		this.tabPage2 = new System.Windows.Forms.TabPage();
		this.rad0_1 = new System.Windows.Forms.RadioButton();
		this.rad0_05 = new System.Windows.Forms.RadioButton();
		this.operation_box = new System.Windows.Forms.ComboBox();
		this.label1 = new System.Windows.Forms.Label();
		this.tab_control.SuspendLayout();
		this.tab_part_editor.SuspendLayout();
		this.tab_scene_editor.SuspendLayout();
		this.tabControl1.SuspendLayout();
		this.tabPage1.SuspendLayout();
		this.tabPage2.SuspendLayout();
		this.SuspendLayout();
		//
		// trview
		//
		this.trview.Location = new System.Drawing.Point(0, 0);
		this.trview.Name = "trview";
		this.trview.Size = new System.Drawing.Size(120, 296);
		this.trview.TabIndex = 0;
		//
		// mainMenu1
		//
		this.mainMenu1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
				this.menuItem1,
				this.menuItem7,
				this.menuItem8});
		//
		// menuItem1
		//
		this.menuItem1.Index = 0;
		this.menuItem1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
				this.menuItem2,
				this.menuItem3,
				this.menuItem4,
				this.menuItem5,
				this.menuItem6});
		this.menuItem1.Text = "File";
		//
		// menuItem2
		//
		this.menuItem2.Index = 0;
		this.menuItem2.Text = "New";
		this.menuItem2.Click += new System.EventHandler(this.menuItem2_Click);
		//
		// menuItem3
		//
		this.menuItem3.Index = 1;
		this.menuItem3.Text = "Open";
		this.menuItem3.Click += new System.EventHandler(this.menuItem3_Click);
		//
		// menuItem4
		//
		this.menuItem4.Index = 2;
		this.menuItem4.Text = "Save";
		this.menuItem4.Click += new System.EventHandler(this.menuItem4_Click);
		//
		// menuItem5
		//
		this.menuItem5.Index = 3;
		this.menuItem5.Text = "Save as...";
		this.menuItem5.Click += new System.EventHandler(this.menuItem5_Click);
		//
		// menuItem6
		//
		this.menuItem6.Index = 4;
		this.menuItem6.Text = "Quit";
		this.menuItem6.Click += new System.EventHandler(this.menuItem6_Click);
		//
		// menuItem7
		//
		this.menuItem7.Index = 1;
		this.menuItem7.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
				this.menuItem10,
				this.menuItem11});
		this.menuItem7.Text = "Part";
		//
		// menuItem10
		//
		this.menuItem10.Index = 0;
		this.menuItem10.Text = "Add Part";
		this.menuItem10.Click += new System.EventHandler(this.menuItem10_Click);
		//
		// menuItem11
		//
		this.menuItem11.Index = 1;
		this.menuItem11.Text = "Delete Part";
		this.menuItem11.Click += new System.EventHandler(this.menuItem11_Click);
		//
		// menuItem8
		//
		this.menuItem8.Index = 2;
		this.menuItem8.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
				this.menuItem9});
		this.menuItem8.Text = "Help";
		//
		// menuItem9
		//
		this.menuItem9.Index = 0;
		this.menuItem9.Text = "About";
		//
		// tab_control
		//
		this.tab_control.Controls.Add(this.tab_part_editor);
		this.tab_control.Controls.Add(this.tab_scene_editor);
		this.tab_control.Location = new System.Drawing.Point(400, 0);
		this.tab_control.Name = "tab_control";
		this.tab_control.SelectedIndex = 0;
		this.tab_control.Size = new System.Drawing.Size(336, 256);
		this.tab_control.TabIndex = 1;
		//
		// tab_part_editor
		//
		this.tab_part_editor.Controls.Add(this.partEditor1);
		this.tab_part_editor.Location = new System.Drawing.Point(4, 22);
		this.tab_part_editor.Name = "tab_part_editor";
		this.tab_part_editor.Size = new System.Drawing.Size(328, 230);
		this.tab_part_editor.TabIndex = 0;
		this.tab_part_editor.Text = "Part Editor";
		//
		// partEditor1
		//
		this.partEditor1.Location = new System.Drawing.Point(0, 0);
		this.partEditor1.Name = "partEditor1";
		this.partEditor1.Part = null;
		this.partEditor1.Size = new System.Drawing.Size(336, 232);
		this.partEditor1.TabIndex = 0;
		//
		// tab_scene_editor
		//
		this.tab_scene_editor.Controls.Add(this.sceneEditor1);
		this.tab_scene_editor.Location = new System.Drawing.Point(4, 22);
		this.tab_scene_editor.Name = "tab_scene_editor";
		this.tab_scene_editor.Size = new System.Drawing.Size(328, 230);
		this.tab_scene_editor.TabIndex = 2;
		this.tab_scene_editor.Text = "Scene Editor";
		//
		// sceneEditor1
		//
		this.sceneEditor1.Location = new System.Drawing.Point(0, 0);
		this.sceneEditor1.Name = "sceneEditor1";
		this.sceneEditor1.Size = new System.Drawing.Size(328, 232);
		this.sceneEditor1.TabIndex = 0;
		//
		// dxview
		//
		this.dxview.Location = new System.Drawing.Point(0, 0);
		this.dxview.Name = "dxview";
		this.dxview.Size = new System.Drawing.Size(336, 272);
		this.dxview.TabIndex = 0;
		//
		// wireFrameView1
		//
		this.wireFrameView1.Location = new System.Drawing.Point(120, 0);
		this.wireFrameView1.Name = "wireFrameView1";
		this.wireFrameView1.Size = new System.Drawing.Size(280, 256);
		this.wireFrameView1.TabIndex = 2;
		//
		// wireFrameView2
		//
		this.wireFrameView2.Location = new System.Drawing.Point(120, 256);
		this.wireFrameView2.Name = "wireFrameView2";
		this.wireFrameView2.Size = new System.Drawing.Size(280, 288);
		this.wireFrameView2.TabIndex = 3;
		//
		// wireFrameView3
		//
		this.wireFrameView3.Location = new System.Drawing.Point(0, 0);
		this.wireFrameView3.Name = "wireFrameView3";
		this.wireFrameView3.Size = new System.Drawing.Size(336, 272);
		this.wireFrameView3.TabIndex = 4;
		//
		// grid_button
		//
		this.grid_button.Location = new System.Drawing.Point(8, 304);
		this.grid_button.Name = "grid_button";
		this.grid_button.Size = new System.Drawing.Size(96, 24);
		this.grid_button.TabIndex = 5;
		this.grid_button.Text = "Grid: On";
		this.grid_button.Click += new System.EventHandler(this.grid_button_Click);
		//
		// rad1_0
		//
		this.rad1_0.Location = new System.Drawing.Point(8, 336);
		this.rad1_0.Name = "rad1_0";
		this.rad1_0.TabIndex = 6;
		this.rad1_0.Text = "1.0 grid";
		this.rad1_0.CheckedChanged += new System.EventHandler(this.rad_CheckedChanged);
		//
		// rad0_5
		//
		this.rad0_5.Location = new System.Drawing.Point(8, 360);
		this.rad0_5.Name = "rad0_5";
		this.rad0_5.TabIndex = 7;
		this.rad0_5.Text = "0.5 grid";
		this.rad0_5.CheckedChanged += new System.EventHandler(this.rad_CheckedChanged);
		//
		// rad0_25
		//
		this.rad0_25.Location = new System.Drawing.Point(8, 384);
		this.rad0_25.Name = "rad0_25";
		this.rad0_25.TabIndex = 8;
		this.rad0_25.Text = "0.25 grid";
		this.rad0_25.CheckedChanged += new System.EventHandler(this.rad_CheckedChanged);
		//
		// tabControl1
		//
		this.tabControl1.Controls.Add(this.tabPage1);
		this.tabControl1.Controls.Add(this.tabPage2);
		this.tabControl1.Location = new System.Drawing.Point(400, 256);
		this.tabControl1.Name = "tabControl1";
		this.tabControl1.SelectedIndex = 0;
		this.tabControl1.Size = new System.Drawing.Size(336, 296);
		this.tabControl1.TabIndex = 9;
		//
		// tabPage1
		//
		this.tabPage1.Controls.Add(this.wireFrameView3);
		this.tabPage1.Location = new System.Drawing.Point(4, 22);
		this.tabPage1.Name = "tabPage1";
		this.tabPage1.Size = new System.Drawing.Size(328, 270);
		this.tabPage1.TabIndex = 0;
		this.tabPage1.Text = "Wire Frame";
		//
		// tabPage2
		//
		this.tabPage2.Controls.Add(this.dxview);
		this.tabPage2.Location = new System.Drawing.Point(4, 22);
		this.tabPage2.Name = "tabPage2";
		this.tabPage2.Size = new System.Drawing.Size(328, 270);
		this.tabPage2.TabIndex = 1;
		this.tabPage2.Text = "DirectX View";
		//
		// rad0_1
		//
		this.rad0_1.Checked = true;
		this.rad0_1.Location = new System.Drawing.Point(8, 408);
		this.rad0_1.Name = "rad0_1";
		this.rad0_1.TabIndex = 10;
		this.rad0_1.TabStop = true;
		this.rad0_1.Text = "0.1 grid";
		this.rad0_1.CheckedChanged += new System.EventHandler(this.rad_CheckedChanged);
		//
		// rad0_05
		//
		this.rad0_05.Location = new System.Drawing.Point(8, 432);
		this.rad0_05.Name = "rad0_05";
		this.rad0_05.TabIndex = 11;
		this.rad0_05.Text = "0.05 grid";
		this.rad0_05.CheckedChanged += new System.EventHandler(this.rad_CheckedChanged);
		//
		// operation_box
		//
		this.operation_box.Items.AddRange(new object[] {
				"move",
				"zoom",
		"rotate"});
		this.operation_box.Location = new System.Drawing.Point(8, 504);
		this.operation_box.Name = "operation_box";
		this.operation_box.Size = new System.Drawing.Size(104, 21);
		this.operation_box.TabIndex = 18;
		this.operation_box.Text = "move";
		this.operation_box.SelectedIndexChanged += new System.EventHandler(this.operation_box_SelectedIndexChanged);
		//
		// label1
		//
		this.label1.Location = new System.Drawing.Point(8, 480);
		this.label1.Name = "label1";
		this.label1.Size = new System.Drawing.Size(64, 24);
		this.label1.TabIndex = 17;
		this.label1.Text = "Operation:";
		this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
		//
		// AnimaTool
		//
		this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
		this.ClientSize = new System.Drawing.Size(736, 545);
		this.Controls.Add(this.operation_box);
		this.Controls.Add(this.label1);
		this.Controls.Add(this.rad0_05);
		this.Controls.Add(this.rad0_1);
		this.Controls.Add(this.tabControl1);
		this.Controls.Add(this.rad0_25);
		this.Controls.Add(this.rad0_5);
		this.Controls.Add(this.rad1_0);
		this.Controls.Add(this.grid_button);
		this.Controls.Add(this.wireFrameView2);
		this.Controls.Add(this.wireFrameView1);
		this.Controls.Add(this.tab_control);
		this.Controls.Add(this.trview);
		this.Menu = this.mainMenu1;
		this.Name = "AnimaTool";
		this.Text = "AnimaTool";
		this.tab_control.ResumeLayout(false);
		this.tab_part_editor.ResumeLayout(false);
		this.tab_scene_editor.ResumeLayout(false);
		this.tabControl1.ResumeLayout(false);
		this.tabPage1.ResumeLayout(false);
		this.tabPage2.ResumeLayout(false);
		this.ResumeLayout(false);

	}

	static void Main()
	{
		Application.Run(new AnimaTool());
	}

	private CModel model;

	private FileOperations.FileControl file_control;

	private void LoadFile(string file_name)
	{
		CModelData data = (CModelData)XmlHandler.Load(file_name);
		model.SetModelData(data);
		model.UpdateViews();
	}

	private void SaveFile(string file_name)
	{
		//		XmlDocument doc = new XmlDocument();
		//		XmlNode root = doc.CreateElement("root");
		//		doc.AppendChild(root);
		//
		//		XmlAttribute root_max = doc.CreateAttribute("max");
		//		root_max.Value = model.max_scenes.ToString();
		//		root.Attributes.Append(root_max);
		//
		//		System.Type type = typeof(Matrix);
		//		FieldInfo[] infos = type.GetFields();
		//
		//		foreach(CPart part in model.parts)
		//		{
		//			XmlNode p = doc.CreateElement("part");
		//			root.AppendChild(p);
		//
		//			// mesh file
		//			XmlAttribute source = doc.CreateAttribute("source");
		//			source.Value = part.mesh_file;
		//			p.Attributes.Append(source);
		//
		//			// center_point
		//			XmlNode center = doc.CreateElement("center");
		//			p.AppendChild(center);
		//			XmlAttribute x = doc.CreateAttribute("x");
		//			x.Value = part.center_point.X.ToString();
		//			center.Attributes.Append(x);
		//			XmlAttribute y = doc.CreateAttribute("y");
		//			y.Value = part.center_point.Y.ToString();
		//			center.Attributes.Append(y);
		//			XmlAttribute z = doc.CreateAttribute("z");
		//			z.Value = part.center_point.Z.ToString();
		//			center.Attributes.Append(z);
		//
		//			// connections
		//			XmlAttribute num = doc.CreateAttribute("connections");
		//			num.Value = part.connections.Length.ToString();
		//			p.Attributes.Append(num);
		//
		//			// TODO: connections
		//
		//			// model transform
		//			for(int i = 0; i < model.max_scenes; i++)
		//			{
		//				Matrix m = part.model_transform[i];
		//				XmlNode matrix = doc.CreateElement("matrix");
		//				p.AppendChild(matrix);
		//
		//				for(int j = 0; j < infos.Length; j++)
		//				{
		//					XmlAttribute attr = doc.CreateAttribute(
		//						infos[j].Name);
		//					float val = (float)infos[j].GetValue(m);
		//					attr.Value = val.ToString();
		//					matrix.Attributes.Append(attr);
		//				}
		//			}
		//		}
		//
		//		doc.Save(file_name);

		CModelData data = model.GetModelData();
		XmlHandler.Save(file_name, data);
	}

	private void menuItem2_Click(object sender, System.EventArgs e)
	{
		file_control.New();
		model.parts.Clear();
		model.scene = 0;
		model.max_scenes = 1;
		model.UpdateViews();
	}

	private void menuItem3_Click(object sender, System.EventArgs e)
	{
		file_control.Open();
	}

	private void menuItem4_Click(object sender, System.EventArgs e)
	{
		file_control.Save();
	}

	private void menuItem5_Click(object sender, System.EventArgs e)
	{
		file_control.SaveAs();
	}

	private void menuItem6_Click(object sender, System.EventArgs e)
	{
		//		file_control.Save();
		Close();
	}

	protected override void OnClosing(CancelEventArgs e)
	{
		file_control.Save();
		base.OnClosing(e);
	}

	private void menuItem10_Click(object sender, System.EventArgs e)
	{
		model.parts.Add(new CPart(model));
		model.UpdateViews();
	}

	private void menuItem11_Click(object sender, System.EventArgs e)
	{
		if(model.Selected != null)
		{
			model.DeleteConnectedPart(
					model.parts.IndexOf(model.Selected));
			model.parts.Remove(model.Selected);
			model.UpdateViews();
		}
	}

	private void grid_button_Click(object sender, System.EventArgs e)
	{
		if(model.SnapToGrid)
		{
			model.SetSnapToGrid(false);
			grid_button.Text = "Grid: Off";
		}
		else
		{
			model.SetSnapToGrid(true);
			grid_button.Text = "Grid: On";
		}
	}

	private void rad_CheckedChanged(object sender, System.EventArgs e)
	{
		if(sender == rad1_0)
		{
			model.grid_step = 1f;
		}
		else if(sender == rad0_5)
		{
			model.grid_step = 0.5f;
		}
		else if(sender == rad0_25)
		{
			model.grid_step = 0.25f;
		}
		else if(sender == rad0_1)
		{
			model.grid_step = 0.1f;
		}
		else if(sender == rad0_05)
		{
			model.grid_step = 0.05f;
		}

		dxview.UpdateGrid();
		dxview.Invalidate();
		wireFrameView1.Invalidate();
		wireFrameView2.Invalidate();
		wireFrameView3.Invalidate();
	}


	private static MoveOperation operation = MoveOperation.Move;

	public static MoveOperation GetOperatoion()
	{
		return operation;
	}

	private void operation_box_SelectedIndexChanged(object sender, System.EventArgs e)
	{
		operation = (MoveOperation)operation_box.SelectedIndex;
	}
}