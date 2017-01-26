package hu.csega.superstition.t3dcreator;

import javax.swing.JPanel;

import hu.csega.superstition.gamelib.legacy.modeldata.CEdge;
import hu.csega.superstition.gamelib.legacy.modeldata.CTriangle;

public class T3DCreator extends JPanel {

	private System.Windows.Forms.MainMenu main_menu;
	private System.Windows.Forms.MenuItem menuItem1;
	private System.Windows.Forms.MenuItem menuItem2;
	private System.Windows.Forms.MenuItem menuItem3;
	private System.Windows.Forms.MenuItem menuItem4;
	private System.Windows.Forms.MenuItem menuItem5;
	private System.Windows.Forms.MenuItem menuItem6;
	private System.Windows.Forms.MenuItem menuItem7;
	private System.Windows.Forms.MenuItem menuItem8;
	private System.Windows.Forms.MenuItem menuItem9;
	private System.Windows.Forms.MenuItem menuItem10;
	private T3DCreatorWindows.TreeObjectView treeObjectView1;
	private T3DCreatorWindows.DirectXView directXView1;
	private T3DCreatorWindows.WireFrameView wireFrameView1;
	private T3DCreatorWindows.WireFrameView wireFrameView2;
	private T3DCreatorWindows.WireFrameView wireFrameView3;
	private System.Windows.Forms.Button button2;
	private System.Windows.Forms.MenuItem menuItem11;
	private System.Windows.Forms.TabControl tabControl1;
	private System.Windows.Forms.TabPage tabPage1;
	private System.Windows.Forms.TabPage tabPage2;
	private System.Windows.Forms.RadioButton radioButton1;
	private System.Windows.Forms.RadioButton radioButton2;
	private System.Windows.Forms.RadioButton radioButton3;
	private T3DCreatorWindows.TextureView textureView1;
	private System.Windows.Forms.MenuItem menuItem12;
	private System.Windows.Forms.MenuItem menuItem13;
	private System.Windows.Forms.MenuItem menuItem14;
	private System.Windows.Forms.MenuItem menuItem15;
	private System.Windows.Forms.MenuItem menuItem16;
	private System.Windows.Forms.MenuItem menuItem17;
	private System.Windows.Forms.MenuItem menuItem18;
	private System.Windows.Forms.MenuItem menuItem19;
	private System.Windows.Forms.MenuItem menuItem20;
	private System.Windows.Forms.MenuItem menuItem24;
	private System.Windows.Forms.MenuItem menuItem25;
	private System.Windows.Forms.MenuItem menuItem23;
	private System.Windows.Forms.MenuItem menuItem26;
	private System.Windows.Forms.RadioButton radioButton4;
	private System.Windows.Forms.RadioButton radioButton5;
	private System.Windows.Forms.MenuItem menuItem27;
	private System.Windows.Forms.MenuItem menuItem28;
	private System.Windows.Forms.MenuItem menuItem22;
	private System.Windows.Forms.MenuItem menuItem29;
	private System.Windows.Forms.MenuItem menuItem30;
	private System.Windows.Forms.MenuItem menuItem21;
	private System.Windows.Forms.Label label1;
	private System.Windows.Forms.ComboBox operation_box;
	private System.Windows.Forms.MenuItem menuItem31;
	private System.Windows.Forms.MenuItem menuItem32;
	private System.Windows.Forms.MenuItem menuItem33;
	private System.Windows.Forms.MenuItem menuItem34;
	private System.Windows.Forms.MenuItem menuItem35;
	private System.Windows.Forms.MenuItem menuItem36;
	private System.Windows.Forms.MenuItem menuItem37;
	/// <summary>
	/// Required designer variable.
	/// </summary>
	private System.ComponentModel.Container components = null;

	public T3DCreator()
	{
		//
		// Required for Windows Form Designer support
		//
		InitializeComponent();

		control = new FileControl(
				"T3DC Model file (*.t3d)|*.t3d|XML files (*.xml)|*.xml|All files (*.*)|*.*",
				@"..\t3d_files",
				new FileOperation(OpenFile),
				new FileOperation(SaveFile));

		model = new CModel(control);
		model.RegisterView(treeObjectView1);
		model.RegisterView(directXView1);
		model.RegisterView(textureView1);

		model.RegisterView(wireFrameView1);
		wireFrameView1.SetView(T3DCreatorWindows.Perspectives.Front);
		model.RegisterView(wireFrameView2);
		wireFrameView2.SetView(T3DCreatorWindows.Perspectives.Top);
		model.RegisterView(wireFrameView3);
		wireFrameView3.SetView(T3DCreatorWindows.Perspectives.Left);

		util = new MeshUtils(directXView1.Device);

		fprop = new FigureProperties();
		mprop = new ModelProperties();
	}

	/// <summary>
	/// Clean up any resources being used.
	/// </summary>
	protected override void Dispose( bool disposing )
	{
		if( disposing )
		{
			if (components != null)
			{
				components.Dispose();
			}
			util.Dispose();
			fprop.Dispose();
		}
		base.Dispose( disposing );
	}

	/// <summary>
	/// Required method for Designer support - do not modify
	/// the contents of this method with the code editor.
	/// </summary>
	private void InitializeComponent()
	{
		this.main_menu = new System.Windows.Forms.MainMenu();
		this.menuItem1 = new System.Windows.Forms.MenuItem();
		this.menuItem2 = new System.Windows.Forms.MenuItem();
		this.menuItem3 = new System.Windows.Forms.MenuItem();
		this.menuItem4 = new System.Windows.Forms.MenuItem();
		this.menuItem5 = new System.Windows.Forms.MenuItem();
		this.menuItem6 = new System.Windows.Forms.MenuItem();
		this.menuItem14 = new System.Windows.Forms.MenuItem();
		this.menuItem15 = new System.Windows.Forms.MenuItem();
		this.menuItem16 = new System.Windows.Forms.MenuItem();
		this.menuItem24 = new System.Windows.Forms.MenuItem();
		this.menuItem26 = new System.Windows.Forms.MenuItem();
		this.menuItem25 = new System.Windows.Forms.MenuItem();
		this.menuItem17 = new System.Windows.Forms.MenuItem();
		this.menuItem21 = new System.Windows.Forms.MenuItem();
		this.menuItem28 = new System.Windows.Forms.MenuItem();
		this.menuItem27 = new System.Windows.Forms.MenuItem();
		this.menuItem30 = new System.Windows.Forms.MenuItem();
		this.menuItem18 = new System.Windows.Forms.MenuItem();
		this.menuItem22 = new System.Windows.Forms.MenuItem();
		this.menuItem9 = new System.Windows.Forms.MenuItem();
		this.menuItem10 = new System.Windows.Forms.MenuItem();
		this.menuItem11 = new System.Windows.Forms.MenuItem();
		this.menuItem32 = new System.Windows.Forms.MenuItem();
		this.menuItem31 = new System.Windows.Forms.MenuItem();
		this.menuItem33 = new System.Windows.Forms.MenuItem();
		this.menuItem34 = new System.Windows.Forms.MenuItem();
		this.menuItem35 = new System.Windows.Forms.MenuItem();
		this.menuItem36 = new System.Windows.Forms.MenuItem();
		this.menuItem37 = new System.Windows.Forms.MenuItem();
		this.menuItem23 = new System.Windows.Forms.MenuItem();
		this.menuItem20 = new System.Windows.Forms.MenuItem();
		this.menuItem29 = new System.Windows.Forms.MenuItem();
		this.menuItem19 = new System.Windows.Forms.MenuItem();
		this.menuItem12 = new System.Windows.Forms.MenuItem();
		this.menuItem13 = new System.Windows.Forms.MenuItem();
		this.menuItem7 = new System.Windows.Forms.MenuItem();
		this.menuItem8 = new System.Windows.Forms.MenuItem();
		this.treeObjectView1 = new T3DCreatorWindows.TreeObjectView();
		this.directXView1 = new T3DCreatorWindows.DirectXView();
		this.wireFrameView1 = new T3DCreatorWindows.WireFrameView();
		this.wireFrameView2 = new T3DCreatorWindows.WireFrameView();
		this.wireFrameView3 = new T3DCreatorWindows.WireFrameView();
		this.button2 = new System.Windows.Forms.Button();
		this.tabControl1 = new System.Windows.Forms.TabControl();
		this.tabPage1 = new System.Windows.Forms.TabPage();
		this.tabPage2 = new System.Windows.Forms.TabPage();
		this.textureView1 = new T3DCreatorWindows.TextureView();
		this.radioButton1 = new System.Windows.Forms.RadioButton();
		this.radioButton2 = new System.Windows.Forms.RadioButton();
		this.radioButton3 = new System.Windows.Forms.RadioButton();
		this.radioButton4 = new System.Windows.Forms.RadioButton();
		this.radioButton5 = new System.Windows.Forms.RadioButton();
		this.label1 = new System.Windows.Forms.Label();
		this.operation_box = new System.Windows.Forms.ComboBox();
		this.tabControl1.SuspendLayout();
		this.tabPage1.SuspendLayout();
		this.tabPage2.SuspendLayout();
		this.SuspendLayout();
		//
		// main_menu
		//
		this.main_menu.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
				this.menuItem1,
				this.menuItem14,
				this.menuItem28,
				this.menuItem22,
				this.menuItem12,
				this.menuItem7});
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
		this.menuItem5.Text = "Save As...";
		this.menuItem5.Click += new System.EventHandler(this.menuItem5_Click);
		//
		// menuItem6
		//
		this.menuItem6.Index = 4;
		this.menuItem6.Text = "Quit";
		this.menuItem6.Click += new System.EventHandler(this.menuItem6_Click);
		//
		// menuItem14
		//
		this.menuItem14.Index = 1;
		this.menuItem14.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
				this.menuItem15,
				this.menuItem16,
				this.menuItem24,
				this.menuItem26,
				this.menuItem25,
				this.menuItem17,
				this.menuItem21});
		this.menuItem14.Text = "Edit";
		//
		// menuItem15
		//
		this.menuItem15.Index = 0;
		this.menuItem15.Text = "Undo";
		this.menuItem15.Click += new System.EventHandler(this.menuItem15_Click);
		//
		// menuItem16
		//
		this.menuItem16.Index = 1;
		this.menuItem16.Text = "Redo";
		this.menuItem16.Click += new System.EventHandler(this.menuItem16_Click);
		//
		// menuItem24
		//
		this.menuItem24.Index = 2;
		this.menuItem24.Text = "-";
		//
		// menuItem26
		//
		this.menuItem26.Index = 3;
		this.menuItem26.Text = "Split Edge";
		this.menuItem26.Click += new System.EventHandler(this.menuItem26_Click);
		//
		// menuItem25
		//
		this.menuItem25.Index = 4;
		this.menuItem25.Text = "Split Triangle";
		this.menuItem25.Click += new System.EventHandler(this.menuItem25_Click);
		//
		// menuItem17
		//
		this.menuItem17.Index = 5;
		this.menuItem17.Text = "-";
		//
		// menuItem21
		//
		this.menuItem21.Index = 6;
		this.menuItem21.Text = "Resize";
		//
		// menuItem28
		//
		this.menuItem28.Index = 2;
		this.menuItem28.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
				this.menuItem27,
				this.menuItem30,
				this.menuItem18});
		this.menuItem28.Text = "Model";
		//
		// menuItem27
		//
		this.menuItem27.Index = 0;
		this.menuItem27.Text = "Select Model";
		this.menuItem27.Click += new System.EventHandler(this.menuItem27_Click);
		//
		// menuItem30
		//
		this.menuItem30.Index = 1;
		this.menuItem30.Text = "-";
		//
		// menuItem18
		//
		this.menuItem18.Index = 2;
		this.menuItem18.Text = "Model Properties";
		this.menuItem18.Click += new System.EventHandler(this.menuItem18_Click);
		//
		// menuItem22
		//
		this.menuItem22.Index = 3;
		this.menuItem22.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
				this.menuItem9,
				this.menuItem23,
				this.menuItem20,
				this.menuItem29,
				this.menuItem19});
		this.menuItem22.Text = "Figure";
		//
		// menuItem9
		//
		this.menuItem9.Index = 0;
		this.menuItem9.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
				this.menuItem10,
				this.menuItem11,
				this.menuItem32,
				this.menuItem31,
				this.menuItem33,
				this.menuItem34,
				this.menuItem35,
				this.menuItem36,
				this.menuItem37});
		this.menuItem9.Text = "Add Figure";
		//
		// menuItem10
		//
		this.menuItem10.Index = 0;
		this.menuItem10.Text = "Tetra";
		this.menuItem10.Click += new System.EventHandler(this.menuItem10_Click);
		//
		// menuItem11
		//
		this.menuItem11.Index = 1;
		this.menuItem11.Text = "Cube";
		this.menuItem11.Click += new System.EventHandler(this.menuItem11_Click);
		//
		// menuItem32
		//
		this.menuItem32.Index = 2;
		this.menuItem32.Text = "-";
		//
		// menuItem31
		//
		this.menuItem31.Index = 3;
		this.menuItem31.Text = "Sphere";
		this.menuItem31.Click += new System.EventHandler(this.menuItem31_Click);
		//
		// menuItem33
		//
		this.menuItem33.Index = 4;
		this.menuItem33.Text = "Cylinder";
		this.menuItem33.Click += new System.EventHandler(this.menuItem33_Click);
		//
		// menuItem34
		//
		this.menuItem34.Index = 5;
		this.menuItem34.Text = "Box";
		this.menuItem34.Click += new System.EventHandler(this.menuItem34_Click);
		//
		// menuItem35
		//
		this.menuItem35.Index = 6;
		this.menuItem35.Text = "Torus";
		this.menuItem35.Click += new System.EventHandler(this.menuItem35_Click);
		//
		// menuItem36
		//
		this.menuItem36.Index = 7;
		this.menuItem36.Text = "Text";
		this.menuItem36.Click += new System.EventHandler(this.menuItem36_Click);
		//
		// menuItem37
		//
		this.menuItem37.Index = 8;
		this.menuItem37.Text = "Teapot";
		this.menuItem37.Click += new System.EventHandler(this.menuItem37_Click);
		//
		// menuItem23
		//
		this.menuItem23.Index = 1;
		this.menuItem23.Text = "Delete Figure";
		this.menuItem23.Click += new System.EventHandler(this.menuItem23_Click);
		//
		// menuItem20
		//
		this.menuItem20.Index = 2;
		this.menuItem20.Text = "Load Figure Texture";
		this.menuItem20.Click += new System.EventHandler(this.menuItem20_Click);
		//
		// menuItem29
		//
		this.menuItem29.Index = 3;
		this.menuItem29.Text = "-";
		//
		// menuItem19
		//
		this.menuItem19.Index = 4;
		this.menuItem19.Text = "Figure Properties";
		this.menuItem19.Click += new System.EventHandler(this.menuItem19_Click);
		//
		// menuItem12
		//
		this.menuItem12.Index = 4;
		this.menuItem12.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
				this.menuItem13});
		this.menuItem12.Text = "Mesh";
		//
		// menuItem13
		//
		this.menuItem13.Index = 0;
		this.menuItem13.Text = "Export";
		this.menuItem13.Click += new System.EventHandler(this.menuItem13_Click);
		//
		// menuItem7
		//
		this.menuItem7.Index = 5;
		this.menuItem7.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
				this.menuItem8});
		this.menuItem7.Text = "Help";
		//
		// menuItem8
		//
		this.menuItem8.Index = 0;
		this.menuItem8.Text = "About";
		this.menuItem8.Click += new System.EventHandler(this.menuItem8_Click);
		//
		// treeObjectView1
		//
		this.treeObjectView1.Location = new System.Drawing.Point(8, 8);
		this.treeObjectView1.Name = "treeObjectView1";
		this.treeObjectView1.Size = new System.Drawing.Size(176, 400);
		this.treeObjectView1.TabIndex = 0;
		//
		// directXView1
		//
		this.directXView1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
		this.directXView1.Location = new System.Drawing.Point(504, 8);
		this.directXView1.Name = "directXView1";
		this.directXView1.Size = new System.Drawing.Size(256, 248);
		this.directXView1.TabIndex = 1;
		//
		// wireFrameView1
		//
		this.wireFrameView1.Location = new System.Drawing.Point(192, 8);
		this.wireFrameView1.Name = "wireFrameView1";
		this.wireFrameView1.Size = new System.Drawing.Size(272, 248);
		this.wireFrameView1.TabIndex = 2;
		//
		// wireFrameView2
		//
		this.wireFrameView2.Location = new System.Drawing.Point(192, 280);
		this.wireFrameView2.Name = "wireFrameView2";
		this.wireFrameView2.Size = new System.Drawing.Size(272, 248);
		this.wireFrameView2.TabIndex = 3;
		//
		// wireFrameView3
		//
		this.wireFrameView3.Location = new System.Drawing.Point(0, 0);
		this.wireFrameView3.Name = "wireFrameView3";
		this.wireFrameView3.Size = new System.Drawing.Size(280, 248);
		this.wireFrameView3.TabIndex = 4;
		//
		// button2
		//
		this.button2.Location = new System.Drawing.Point(8, 448);
		this.button2.Name = "button2";
		this.button2.Size = new System.Drawing.Size(176, 24);
		this.button2.TabIndex = 6;
		this.button2.Text = "Grid: On";
		this.button2.Click += new System.EventHandler(this.button2_Click);
		//
		// tabControl1
		//
		this.tabControl1.Controls.Add(this.tabPage1);
		this.tabControl1.Controls.Add(this.tabPage2);
		this.tabControl1.Location = new System.Drawing.Point(480, 264);
		this.tabControl1.Name = "tabControl1";
		this.tabControl1.SelectedIndex = 0;
		this.tabControl1.Size = new System.Drawing.Size(296, 280);
		this.tabControl1.TabIndex = 9;
		//
		// tabPage1
		//
		this.tabPage1.Controls.Add(this.wireFrameView3);
		this.tabPage1.Location = new System.Drawing.Point(4, 22);
		this.tabPage1.Name = "tabPage1";
		this.tabPage1.Size = new System.Drawing.Size(288, 254);
		this.tabPage1.TabIndex = 0;
		this.tabPage1.Text = "Left View";
		//
		// tabPage2
		//
		this.tabPage2.Controls.Add(this.textureView1);
		this.tabPage2.Location = new System.Drawing.Point(4, 22);
		this.tabPage2.Name = "tabPage2";
		this.tabPage2.Size = new System.Drawing.Size(288, 254);
		this.tabPage2.TabIndex = 1;
		this.tabPage2.Text = "Texture View";
		//
		// textureView1
		//
		this.textureView1.Location = new System.Drawing.Point(24, 8);
		this.textureView1.Name = "textureView1";
		this.textureView1.Size = new System.Drawing.Size(240, 240);
		this.textureView1.TabIndex = 0;
		//
		// radioButton1
		//
		this.radioButton1.Checked = true;
		this.radioButton1.Location = new System.Drawing.Point(8, 480);
		this.radioButton1.Name = "radioButton1";
		this.radioButton1.Size = new System.Drawing.Size(40, 24);
		this.radioButton1.TabIndex = 10;
		this.radioButton1.TabStop = true;
		this.radioButton1.Text = "1.0";
		this.radioButton1.CheckedChanged += new System.EventHandler(this.radioButton1_CheckedChanged);
		//
		// radioButton2
		//
		this.radioButton2.Location = new System.Drawing.Point(64, 480);
		this.radioButton2.Name = "radioButton2";
		this.radioButton2.Size = new System.Drawing.Size(40, 24);
		this.radioButton2.TabIndex = 11;
		this.radioButton2.Text = "0.5";
		this.radioButton2.CheckedChanged += new System.EventHandler(this.radioButton2_CheckedChanged);
		//
		// radioButton3
		//
		this.radioButton3.Location = new System.Drawing.Point(128, 480);
		this.radioButton3.Name = "radioButton3";
		this.radioButton3.Size = new System.Drawing.Size(56, 24);
		this.radioButton3.TabIndex = 12;
		this.radioButton3.Text = "0.25";
		this.radioButton3.CheckedChanged += new System.EventHandler(this.radioButton3_CheckedChanged);
		//
		// radioButton4
		//
		this.radioButton4.Location = new System.Drawing.Point(8, 504);
		this.radioButton4.Name = "radioButton4";
		this.radioButton4.Size = new System.Drawing.Size(40, 24);
		this.radioButton4.TabIndex = 13;
		this.radioButton4.Text = "0.1";
		this.radioButton4.CheckedChanged += new System.EventHandler(this.radioButton4_CheckedChanged);
		//
		// radioButton5
		//
		this.radioButton5.Location = new System.Drawing.Point(64, 504);
		this.radioButton5.Name = "radioButton5";
		this.radioButton5.Size = new System.Drawing.Size(48, 24);
		this.radioButton5.TabIndex = 14;
		this.radioButton5.Text = "0.05";
		this.radioButton5.CheckedChanged += new System.EventHandler(this.radioButton5_CheckedChanged);
		//
		// label1
		//
		this.label1.Location = new System.Drawing.Point(8, 416);
		this.label1.Name = "label1";
		this.label1.Size = new System.Drawing.Size(64, 24);
		this.label1.TabIndex = 15;
		this.label1.Text = "Operation:";
		this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
		this.label1.Click += new System.EventHandler(this.label1_Click);
		//
		// operation_box
		//
		this.operation_box.Items.AddRange(new object[] {
				"move",
				"zoom",
		"rotate"});
		this.operation_box.Location = new System.Drawing.Point(80, 416);
		this.operation_box.Name = "operation_box";
		this.operation_box.Size = new System.Drawing.Size(104, 21);
		this.operation_box.TabIndex = 16;
		this.operation_box.Text = "move";
		this.operation_box.SelectedIndexChanged += new System.EventHandler(this.operation_box_SelectedIndexChanged);
		//
		// T3DCreator
		//
		this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
		this.ClientSize = new System.Drawing.Size(792, 545);
		this.Controls.Add(this.operation_box);
		this.Controls.Add(this.label1);
		this.Controls.Add(this.radioButton5);
		this.Controls.Add(this.radioButton4);
		this.Controls.Add(this.radioButton3);
		this.Controls.Add(this.radioButton2);
		this.Controls.Add(this.radioButton1);
		this.Controls.Add(this.tabControl1);
		this.Controls.Add(this.button2);
		this.Controls.Add(this.wireFrameView2);
		this.Controls.Add(this.wireFrameView1);
		this.Controls.Add(this.directXView1);
		this.Controls.Add(this.treeObjectView1);
		this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
		this.MaximumSize = new System.Drawing.Size(798, 598);
		this.Menu = this.main_menu;
		this.Name = "T3DCreator";
		this.Text = "Triangle 3D Creator Program";
		this.Closing += new System.ComponentModel.CancelEventHandler(this.T3DCreator_Closing);
		this.tabControl1.ResumeLayout(false);
		this.tabPage1.ResumeLayout(false);
		this.tabPage2.ResumeLayout(false);
		this.ResumeLayout(false);

	}

	static void Main()
	{
		Application.Run(new T3DCreator());
	}

	private FileControl control;
	private CModel model;
	private MeshUtils util;
	private FigureProperties fprop;
	private ModelProperties mprop;

	private void OpenFile(string file_name)
	{
		CModel m = (CModel)XmlSerialize.Open(file_name);

		try
		{
			// For earlier versions
			CFigure figure = m.figures[0] as CFigure;
			CTriangle triangle = figure.triangles[0] as CTriangle;
			//			if(triangle.neighbours[0] == null)
			//			{
			foreach(CFigure f in m.figures)
			{
				f.CalculateNeighbours();
			}
			//			}

			if(!model.Verify())
				throw new Exception("Loaded model is not valid!");

			model.Selected = null;
			model.Memento.Clear();
			model.figures.Clear();
			foreach(CFigure f in m.figures)
			{
				model.figures.Add(f);
			}
			model.UpdateViews(Updates.Full);
		}
		catch(Exception e)
		{
			Console.WriteLine(e.Message);
		}

	}

	private void SaveFile(string file_name)
	{
		XmlSerialize.Save(file_name, model);
	}

	private static MoveOperation operation = MoveOperation.Move;
	public static MoveOperation GetOperatoion()
	{
		return operation;
	}

	private void menuItem2_Click(object sender, System.EventArgs e)
	{
		control.New();
		model.figures.Clear();
		model.Selected = null;
		model.UpdateViews(Updates.Full);
		model.Memento.Clear();
	}

	private void menuItem3_Click(object sender, System.EventArgs e)
	{
		control.Open();
	}

	private void menuItem4_Click(object sender, System.EventArgs e)
	{
		control.Save();
	}

	private void menuItem5_Click(object sender, System.EventArgs e)
	{
		control.SaveAs();
	}

	private void menuItem6_Click(object sender, System.EventArgs e)
	{
		control.Save();
		Close();
	}

	private void menuItem8_Click(object sender, System.EventArgs e)
	{
		MessageBox.Show("Triangle 3D Creator\n\nby P�ter Cseng�di\n2006.");
	}

	private void menuItem10_Click(object sender, System.EventArgs e)
	{
		model.Memento.Push(new AddFigure(model, InitialFigure.Tetra));
		model.UpdateViews();
	}

	private void button2_Click(object sender, System.EventArgs e)
	{
		if(model.SnapToGrid)
		{
			model.SetSnapToGrid(false);
			button2.Text = "Grid: Off";
		}
		else
		{
			model.SetSnapToGrid(true);
			button2.Text = "Grid: On";
		}
	}

	private void menuItem11_Click(object sender, System.EventArgs e)
	{
		model.Memento.Push(new AddFigure(model, InitialFigure.Cube));
		model.UpdateViews();
	}

	private void radioButton1_CheckedChanged(object sender, System.EventArgs e)
	{
		if(model.grid_step == 1.0) return;
		model.grid_step = 1.0;
		UpdateGrid();
	}

	private void radioButton2_CheckedChanged(object sender, System.EventArgs e)
	{
		if(model.grid_step == 0.5) return;
		model.grid_step = 0.5;
		UpdateGrid();
	}

	private void radioButton3_CheckedChanged(object sender, System.EventArgs e)
	{
		if(model.grid_step == 0.25) return;
		model.grid_step = 0.25;
		UpdateGrid();
	}

	public void UpdateGrid()
	{
		directXView1.UpdateGrid();
		directXView1.Invalidate();
		wireFrameView1.Invalidate();
		wireFrameView2.Invalidate();
		wireFrameView3.Invalidate();
	}

	private void T3DCreator_Closing(object sender, System.ComponentModel.CancelEventArgs e)
	{
		TextureLibrary.DisposeLibrary();
		control.Dispose();
	}

	private void menuItem13_Click(object sender, System.EventArgs e)
	{
		util.Export(model);
	}

	private void menuItem15_Click(object sender, System.EventArgs e)
	{
		model.Memento.Undo();
		model.UpdateViews();
	}

	private void menuItem16_Click(object sender, System.EventArgs e)
	{
		model.Memento.Redo();
		model.UpdateViews();
	}

	private void menuItem18_Click(object sender, System.EventArgs e)
	{
		mprop.Model = model;
		mprop.Show();
	}

	private void menuItem19_Click(object sender, System.EventArgs e)
	{
		CFigure f = model.Selected as CFigure;
		if(f != null)
		{
			fprop.Model = model;
			fprop.Figure = f;
			fprop.Show();
		}

	}

	private void menuItem20_Click(object sender, System.EventArgs e)
	{
		CFigure figure = model.Selected as CFigure;
		if(figure == null) return;
		figure.texID = TextureLibrary.LoadImage();
		model.UpdateViews(Updates.Full);
	}

	private void menuItem23_Click(object sender, System.EventArgs e)
	{
		CFigure figure = model.Selected as CFigure;
		if(figure == null) return;
		Operation op = new DeleteFigure(model, figure);
		model.Memento.Push(op);
		model.Selected = null;
		model.UpdateViews(Updates.Full);
	}

	private void menuItem25_Click(object sender, System.EventArgs e)
	{
		CFigure figure = null;
		CTriangle triangle = model.Selected as CTriangle;
		if(triangle == null) return;

		foreach(CFigure f in model.figures)
		{
			if(f.hasPart(triangle))
			{
				figure = f;
				break;
			}
		}

		if(figure == null) return;
		Operation op = new TriangleSplit(figure, triangle);
		model.Memento.Push(op);
		model.Selected = null;
		model.UpdateViews(Updates.Full);
	}

	private void menuItem26_Click(object sender, System.EventArgs e)
	{
		CFigure figure = null;
		CEdge edge = model.Selected as CEdge;
		if(edge == null) return;

		foreach(CFigure f in model.figures)
		{
			if(f.hasPart(edge))
			{
				figure = f;
				break;
			}
		}

		if(figure == null) return;
		Operation op = new EdgeSplit(figure, edge);
		model.Memento.Push(op);
		model.Selected = null;
		model.UpdateViews(Updates.Full);
	}

	private void radioButton4_CheckedChanged(object sender, System.EventArgs e)
	{
		if(model.grid_step == 0.1) return;
		model.grid_step = 0.1;
		UpdateGrid();
	}

	private void radioButton5_CheckedChanged(object sender, System.EventArgs e)
	{
		if(model.grid_step == 0.05) return;
		model.grid_step = 0.05;
		UpdateGrid();
	}

	private void menuItem27_Click(object sender, System.EventArgs e)
	{
		model.Selected = model;
		model.UpdateViews(Updates.Selection);
	}

	private void operation_box_SelectedIndexChanged(object sender, System.EventArgs e)
	{
		operation = (MoveOperation)operation_box.SelectedIndex;
	}

	private void menuItem31_Click(object sender, System.EventArgs e)
	{
		model.AddMeshFigure(InitialFigure.Sphere, directXView1.Device);
	}

	private void menuItem33_Click(object sender, System.EventArgs e)
	{
		model.AddMeshFigure(InitialFigure.Cylinder, directXView1.Device);
	}

	private void menuItem34_Click(object sender, System.EventArgs e)
	{
		model.AddMeshFigure(InitialFigure.Box, directXView1.Device);
	}

	private void menuItem35_Click(object sender, System.EventArgs e)
	{
		model.AddMeshFigure(InitialFigure.Torus, directXView1.Device);
	}

	private void menuItem36_Click(object sender, System.EventArgs e)
	{
		model.AddMeshFigure(InitialFigure.TextMesh, directXView1.Device);
	}

	private void menuItem37_Click(object sender, System.EventArgs e)
	{
		model.AddMeshFigure(InitialFigure.Torus, directXView1.Device);
	}

	private void label1_Click(object sender, System.EventArgs e)
	{

	}


}