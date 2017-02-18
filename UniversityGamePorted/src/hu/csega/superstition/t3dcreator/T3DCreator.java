package hu.csega.superstition.t3dcreator;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;

import hu.csega.superstition.common.Disposable;
import hu.csega.superstition.fileoperations.FileControl;
import hu.csega.superstition.tools.presentation.WireFrameView;

public class T3DCreator extends JFrame implements Disposable {

	private JMenuBar menuBar;

	private JMenu menuFile;
	private JMenu menuEdit;
	private JMenu menuModel;
	private JMenu menuFigure;
	private JMenu menuMesh;
	private JMenu menuHelp;

	private JMenuItem menuItem2;
	private JMenuItem menuItem3;
	private JMenuItem menuItem4;
	private JMenuItem menuItem5;
	private JMenuItem menuItem6;
	private JMenuItem menuItem8;
	private JMenuItem menuItem9;
	private JMenuItem menuItem10;
	private JButton button2;
	private JMenuItem menuItem11;
	private JTabbedPane tabControl1;
	private JPanel tabPage1;
	private JPanel tabPage2;
	private JRadioButton radioButton1;
	private JRadioButton radioButton2;
	private JRadioButton radioButton3;
	private JMenuItem menuItem13;
	private JMenuItem menuItem15;
	private JMenuItem menuItem16;
	private JMenuItem menuItem18;
	private JMenuItem menuItem19;
	private JMenuItem menuItem20;
	private JMenuItem menuItem25;
	private JMenuItem menuItem23;
	private JMenuItem menuItem26;
	private JRadioButton radioButton4;
	private JRadioButton radioButton5;
	private JMenuItem menuItem27;
	private JMenuItem menuItem21;
	private JLabel label1;
	private JComboBox<Object> operation_box;
	private JMenuItem menuItem31;
	private JMenuItem menuItem32;
	private JMenuItem menuItem33;
	private JMenuItem menuItem34;
	private JMenuItem menuItem35;
	private JMenuItem menuItem36;
	private JMenuItem menuItem37;

	private JPanel leftSide;
	private JPanel rightSide;

	private WireFrameView wireFrameView1;
	private WireFrameView wireFrameView2;
	private WireFrameView wireFrameView3;

	// TODO uncomment, fix
	//	private TreeObjectView treeObjectView1;
	//	private DirectXView directXView1;
	//	private TextureView textureView1;

	public T3DCreator()
	{
		//
		// Required for Windows Form Designer support
		//
		initializeComponent();

		// TODO uncomment, fix
		//		control = new FileControl(
		//				"T3DC Model file (*.t3d)|*.t3d|XML files (*.xml)|*.xml|All files (*.*)|*.*",
		//				@"..\t3d_files",
		//				new FileOperation(OpenFile),
		//				new FileOperation(SaveFile));
		//
		//		model = new CModel(control);
		//		model.RegisterView(treeObjectView1);
		//		model.RegisterView(directXView1);
		//		model.RegisterView(textureView1);
		//
		//		model.RegisterView(wireFrameView1);
		wireFrameView1.setView(Perspectives.Front);
		//		model.RegisterView(wireFrameView2);
		wireFrameView2.setView(Perspectives.Top);
		//		model.RegisterView(wireFrameView3);
		wireFrameView3.setView(Perspectives.Left);
		//
		//		util = new MeshUtils(directXView1.Device);
		//
		//		fprop = new FigureProperties();
		//		mprop = new ModelProperties();

		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/// <summary>
	/// Clean up any resources being used.
	/// </summary>
	@Override
	public void dispose() {
		super.dispose();
		// TODO uncomment, fix
		//		if( disposing )
		//		{
		//			if (components != null)
		//			{
		//				components.Dispose();
		//			}
		//			util.Dispose();
		//			fprop.Dispose();
		//		}
		//		base.Dispose( disposing );
	}

	private void initializeComponent()
	{
		this.menuBar = new JMenuBar();

		this.menuFile = new JMenu();
		this.menuEdit = new JMenu();
		this.menuModel = new JMenu();
		this.menuFigure = new JMenu();
		this.menuMesh = new JMenu();
		this.menuHelp = new JMenu();

		this.menuItem2 = new JMenuItem();
		this.menuItem3 = new JMenuItem();
		this.menuItem4 = new JMenuItem();
		this.menuItem5 = new JMenuItem();
		this.menuItem6 = new JMenuItem();
		this.menuItem15 = new JMenuItem();
		this.menuItem16 = new JMenuItem();
		this.menuItem26 = new JMenuItem();
		this.menuItem25 = new JMenuItem();
		this.menuItem21 = new JMenuItem();
		this.menuItem27 = new JMenuItem();
		this.menuItem18 = new JMenuItem();
		this.menuItem9 = new JMenuItem();
		this.menuItem10 = new JMenuItem();
		this.menuItem11 = new JMenuItem();
		this.menuItem32 = new JMenuItem();
		this.menuItem31 = new JMenuItem();
		this.menuItem33 = new JMenuItem();
		this.menuItem34 = new JMenuItem();
		this.menuItem35 = new JMenuItem();
		this.menuItem36 = new JMenuItem();
		this.menuItem37 = new JMenuItem();
		this.menuItem23 = new JMenuItem();
		this.menuItem20 = new JMenuItem();
		this.menuItem19 = new JMenuItem();
		this.menuItem13 = new JMenuItem();
		this.menuItem8 = new JMenuItem();
		this.button2 = new JButton();
		this.tabControl1 = new JTabbedPane();
		this.tabPage1 = new JPanel();
		this.tabPage2 = new JPanel();
		this.radioButton1 = new JRadioButton();
		this.radioButton2 = new JRadioButton();
		this.radioButton3 = new JRadioButton();
		this.radioButton4 = new JRadioButton();
		this.radioButton5 = new JRadioButton();
		this.label1 = new JLabel();
		this.operation_box = new JComboBox<Object>();

		this.wireFrameView1 = new WireFrameView();
		this.wireFrameView2 = new WireFrameView();
		this.wireFrameView3 = new WireFrameView();

		// TODO uncomment, fix
		//		this.treeObjectView1 = new TreeObjectView();
		//		this.directXView1 = new DirectXView();
		//		this.textureView1 = new TextureView();
		//		this.SuspendLayout();
		//		this.tabControl1.SuspendLayout();
		//		this.tabPage1.SuspendLayout();
		//		this.tabPage2.SuspendLayout();
		//
		// main_menu
		//
		this.menuBar.add(this.menuFile);
		this.menuBar.add(this.menuEdit);
		this.menuBar.add(this.menuModel);
		this.menuBar.add(this.menuFigure);
		this.menuBar.add(this.menuMesh);
		this.menuBar.add(this.menuHelp);
		//
		// menuItem1
		//
		this.menuFile.add(this.menuItem2);
		this.menuFile.add(this.menuItem3);
		this.menuFile.add(this.menuItem4);
		this.menuFile.add(this.menuItem5);
		this.menuFile.add(this.menuItem6);
		this.menuFile.setText("File");
		//
		// menuItem2
		//
		this.menuItem2.setText("New");
		// this.menuItem2.Click += new System.EventHandler(this.menuItem2_Click);
		//
		// menuItem3
		//
		this.menuItem3.setText("Open");
		// this.menuItem3.Click += new System.EventHandler(this.menuItem3_Click);
		//
		// menuItem4
		//
		this.menuItem4.setText("Save");
		// this.menuItem4.Click += new System.EventHandler(this.menuItem4_Click);
		//
		// menuItem5
		//
		this.menuItem5.setText("Save As...");
		//		this.menuItem5.Click += new System.EventHandler(this.menuItem5_Click);
		//
		// menuItem6
		//
		this.menuItem6.setText("Quit");
		//		this.menuItem6.Click += new System.EventHandler(this.menuItem6_Click);
		//
		// menuItem14
		//
		this.menuEdit.add(this.menuItem15);
		this.menuEdit.add(this.menuItem16);
		this.menuEdit.addSeparator();
		this.menuEdit.add(this.menuItem26);
		this.menuEdit.add(this.menuItem25);
		this.menuEdit.addSeparator();
		this.menuEdit.add(this.menuItem21);
		this.menuEdit.setText("Edit");
		//
		// menuItem15
		//
		this.menuItem15.setText("Undo");
		//this.menuItem15.Click += new System.EventHandler(this.menuItem15_Click);
		//
		// menuItem16
		//
		this.menuItem16.setText("Redo");
		//this.menuItem16.Click += new System.EventHandler(this.menuItem16_Click);

		//
		// menuItem26
		//
		this.menuItem26.setText("Split Edge");
		//this.menuItem26.Click += new System.EventHandler(this.menuItem26_Click);
		//
		// menuItem25
		//
		this.menuItem25.setText("Split Triangle");
		//this.menuItem25.Click += new System.EventHandler(this.menuItem25_Click);

		//
		// menuItem21
		//
		this.menuItem21.setText("Resize");
		//
		// menuItem28
		//
		this.menuModel.add(this.menuItem27);
		this.menuModel.addSeparator();
		this.menuModel.add(this.menuItem18);
		this.menuModel.setText("Model");
		//
		// menuItem27
		//
		this.menuItem27.setText("Select Model");
		//this.menuItem27.Click += new System.EventHandler(this.menuItem27_Click);
		//
		// menuItem18
		//
		this.menuItem18.setText("Model Properties");
		//this.menuItem18.Click += new System.EventHandler(this.menuItem18_Click);
		//
		// menuItem22
		//
		this.menuFigure.add(this.menuItem9);
		this.menuFigure.add(this.menuItem23);
		this.menuFigure.add(this.menuItem20);
		this.menuFigure.addSeparator();
		this.menuFigure.add(this.menuItem19);
		this.menuFigure.setText("Figure");
		//
		// menuItem9
		//
		this.menuItem9.add(this.menuItem10);
		this.menuItem9.add(this.menuItem11);
		this.menuItem9.add(this.menuItem32);
		this.menuItem9.add(this.menuItem31);
		this.menuItem9.add(this.menuItem33);
		this.menuItem9.add(this.menuItem34);
		this.menuItem9.add(this.menuItem35);
		this.menuItem9.add(this.menuItem36);
		this.menuItem9.add(this.menuItem37);
		this.menuItem9.setText("Add Figure");
		//
		// menuItem10
		//
		this.menuItem10.setText("Tetra");
		//this.menuItem10.Click += new System.EventHandler(this.menuItem10_Click);
		//
		// menuItem11
		//
		this.menuItem11.setText("Cube");
		//this.menuItem11.Click += new System.EventHandler(this.menuItem11_Click);

		this.menuItem31.setText("Sphere");
		//this.menuItem31.Click += new System.EventHandler(this.menuItem31_Click);
		//
		// menuItem33
		//
		this.menuItem33.setText("Cylinder");
		//this.menuItem33.Click += new System.EventHandler(this.menuItem33_Click);
		//
		// menuItem34
		//
		this.menuItem34.setText("Box");
		//this.menuItem34.Click += new System.EventHandler(this.menuItem34_Click);
		//
		// menuItem35
		//
		this.menuItem35.setText("Torus");
		//this.menuItem35.Click += new System.EventHandler(this.menuItem35_Click);
		//
		// menuItem36
		//
		this.menuItem36.setText("Text");
		//this.menuItem36.Click += new System.EventHandler(this.menuItem36_Click);
		//
		// menuItem37
		//
		this.menuItem37.setText("Teapot");
		//this.menuItem37.Click += new System.EventHandler(this.menuItem37_Click);
		//
		// menuItem23
		//
		this.menuItem23.setText("Delete Figure");
		//this.menuItem23.Click += new System.EventHandler(this.menuItem23_Click);
		//
		// menuItem20
		//
		this.menuItem20.setText("Load Figure Texture");
		//this.menuItem20.Click += new System.EventHandler(this.menuItem20_Click);
		//
		// menuItem19
		//
		this.menuItem19.setText("Figure Properties");
		//this.menuItem19.Click += new System.EventHandler(this.menuItem19_Click);
		//
		// menuItem12
		//
		this.menuMesh.add(this.menuItem13);
		this.menuMesh.setText("Mesh");
		//
		// menuItem13
		//
		this.menuItem13.setText("Export");
		//this.menuItem13.Click += new System.EventHandler(this.menuItem13_Click);
		//
		// menuItem7
		//
		this.menuHelp.add(this.menuItem8);
		this.menuHelp.setText("Help");
		//
		// menuItem8
		//
		this.menuItem8.setText("About");
		//this.menuItem8.Click += new System.EventHandler(this.menuItem8_Click);

		// TODO uncomment, fix
		//		//
		//		// treeObjectView1
		//		//
		//		this.treeObjectView1.setLocation(new Point(8, 8);
		//		this.treeObjectView1.setName("treeObjectView1";
		//		this.treeObjectView1.setSize(new Dimension(176, 400);
		//		this.treeObjectView1.TabIndex = 0;
		//		//
		//		// directXView1
		//		//
		//		this.directXView1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
		//		this.directXView1.setLocation(new Point(504, 8);
		//		this.directXView1.setName("directXView1";
		//		this.directXView1.setSize(new Dimension(256, 248);
		//		this.directXView1.TabIndex = 1;

		this.wireFrameView1.setLocation(new Point(192, 8));
		this.wireFrameView1.setSize(new Dimension(272, 248));

		this.wireFrameView2.setLocation(new Point(192, 280));
		this.wireFrameView2.setSize(new Dimension(272, 248));

		this.wireFrameView3.setLocation(new Point(0, 0));
		this.wireFrameView3.setSize(new Dimension(280, 248));

		this.button2.setLocation(new Point(8, 448));
		this.button2.setName("button2");
		this.button2.setSize(new Dimension(176, 24));
		this.button2.setText("Grid: On");
		//		this.button2.Click += new System.EventHandler(this.button2_Click);

		this.tabPage1.add(this.wireFrameView3);
		this.tabPage1.setSize(new Dimension(288, 254));

		// this.tabPage2.add(this.textureView1);
		this.tabPage2.setSize(new Dimension(288, 254));

		this.tabControl1.addTab("Left View", this.tabPage1);
		this.tabControl1.addTab("Texture View", this.tabPage2);
		this.tabControl1.setLocation(new Point(480, 264));
		this.tabControl1.setSize(new Dimension(296, 280));

		//		this.textureView1.setLocation(new Point(24, 8);
		//		this.textureView1.setName("textureView1";
		//		this.textureView1.setSize(new Dimension(240, 240);
		//		this.textureView1.TabIndex = 0;

		this.radioButton1.setLocation(new Point(8, 480));
		this.radioButton1.setName("radioButton1");
		this.radioButton1.setSize(new Dimension(40, 24));
		this.radioButton1.setText("1.0");
		//this.radioButton1.CheckedChanged += new System.EventHandler(this.radioButton1_CheckedChanged);

		this.radioButton2.setLocation(new Point(64, 480));
		this.radioButton2.setName("radioButton2");
		this.radioButton2.setSize(new Dimension(40, 24));
		this.radioButton2.setText("0.5");
		//this.radioButton2.CheckedChanged += new System.EventHandler(this.radioButton2_CheckedChanged);

		this.radioButton3.setLocation(new Point(128, 480));
		this.radioButton3.setName("radioButton3");
		this.radioButton3.setSize(new Dimension(56, 24));
		this.radioButton3.setText("0.25");
		//this.radioButton3.CheckedChanged += new System.EventHandler(this.radioButton3_CheckedChanged);
		//
		// radioButton4
		//
		this.radioButton4.setLocation(new Point(8, 504));
		this.radioButton4.setName("radioButton4");
		this.radioButton4.setSize(new Dimension(40, 24));
		this.radioButton4.setText("0.1");
		// this.radioButton4.CheckedChanged += new System.EventHandler(this.radioButton4_CheckedChanged);
		//
		// radioButton5
		//
		this.radioButton5.setLocation(new Point(64, 504));
		this.radioButton5.setName("radioButton5");
		this.radioButton5.setSize(new Dimension(48, 24));
		this.radioButton5.setText("0.05");
		//this.radioButton5.CheckedChanged += new System.EventHandler(this.radioButton5_CheckedChanged);
		//
		// label1
		//
		this.label1.setLocation(new Point(8, 416));
		this.label1.setName("label1");
		this.label1.setSize(new Dimension(64, 24));
		this.label1.setText("Operation:");
		this.label1.setVerticalAlignment(JLabel.CENTER);
		//this.label1.Click += new System.EventHandler(this.label1_Click);
		//
		// operation_box
		//
		this.operation_box.addItem("move");
		this.operation_box.addItem("zoom");
		this.operation_box.addItem("rotate");

		this.operation_box.setLocation(new Point(80, 416));
		this.operation_box.setName("operation_box");
		this.operation_box.setSize(new Dimension(104, 21));
		this.operation_box.setName("move");
		// this.operation_box.SelectedIndexChanged += new System.EventHandler(this.operation_box_SelectedIndexChanged);
		//
		// T3DCreator
		//
		//		this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
		//		this.ClientSize = new System.Drawing.Size(792, 545);

		this.leftSide = new JPanel();
		this.leftSide.setLayout(new BorderLayout());
		this.leftSide.add(this.operation_box);
		this.leftSide.add(this.label1);
		this.leftSide.add(this.radioButton5);
		this.leftSide.add(this.radioButton4);
		this.leftSide.add(this.radioButton3);
		this.leftSide.add(this.radioButton2);
		this.leftSide.add(this.radioButton1);
		this.leftSide.add(this.button2);

		this.rightSide = new JPanel();
		this.rightSide.setLayout(new GridLayout(2, 2));
		this.rightSide.add(this.wireFrameView1);
		this.rightSide.add(new JPanel());
		this.rightSide.add(this.wireFrameView2);
		this.rightSide.add(this.tabControl1);

		Container contentPane = this.getContentPane();
		contentPane.setLayout(new FlowLayout());
		contentPane.add(leftSide);
		contentPane.add(rightSide);
		//		contentPane.add(this.directXView1);
		//		contentPane.add(this.treeObjectView1);
		//		this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;

		this.setJMenuBar(this.menuBar);
		this.setTitle("Triangle 3D Creator Program");

		// this.Closing += new System.ComponentModel.CancelEventHandler(this.T3DCreator_Closing);
		//		this.tabControl1.ResumeLayout(false);
		//		this.tabPage1.ResumeLayout(false);
		//		this.tabPage2.ResumeLayout(false);
		//		this.ResumeLayout(false);
	}

	public static void main(String[] args) throws Exception {
		T3DCreator tool = new T3DCreator();
		tool.setVisible(true);
	}

	private FileControl control;
	private CModel model;
	private MeshUtils util;
	private FigureProperties fprop;
	private ModelProperties mprop;

	private void OpenFile(String file_name)
	{
		//		CModel m = (CModel)XmlSerialize.Open(file_name);
		//
		//		try
		//		{
		//			// For earlier versions
		//			CFigure figure = m.figures[0] as CFigure;
		//			CTriangle triangle = figure.triangles[0] as CTriangle;
		//			//			if(triangle.neighbours[0] == null)
		//			//			{
		//			foreach(CFigure f in m.figures)
		//			{
		//				f.CalculateNeighbours();
		//			}
		//			//			}
		//
		//			if(!model.Verify())
		//				throw new Exception("Loaded model is not valid!");
		//
		//			model.Selected = null;
		//			model.Memento.Clear();
		//			model.figures.Clear();
		//			foreach(CFigure f in m.figures)
		//			{
		//				model.figures.Add(f);
		//			}
		//			model.UpdateViews(Updates.Full);
		//		}
		//		catch(Exception e)
		//		{
		//			System.out.println(e.getMessage());
		//		}

	}

	private void SaveFile(String file_name)
	{
		//		XmlSerialize.Save(file_name, model);
	}

	private static MoveOperation operation = MoveOperation.Move;
	public static MoveOperation GetOperatoion()
	{
		return operation;
	}

	//	private void menuItem2_Click(Object sender, ActionEvent e)
	//	{
	//		control.New();
	//		model.figures.Clear();
	//		model.Selected = null;
	//		model.UpdateViews(Updates.Full);
	//		model.Memento.Clear();
	//	}
	//
	//	private void menuItem3_Click(Object sender, ActionEvent e)
	//	{
	//		control.Open();
	//	}
	//
	//	private void menuItem4_Click(Object sender, ActionEvent e)
	//	{
	//		control.Save();
	//	}
	//
	//	private void menuItem5_Click(Object sender, ActionEvent e)
	//	{
	//		control.SaveAs();
	//	}
	//
	//	private void menuItem6_Click(Object sender, ActionEvent e)
	//	{
	//		control.Save();
	//		Close();
	//	}
	//
	//	private void menuItem8_Click(Object sender, ActionEvent e)
	//	{
	//		MessageBox.Show("Triangle 3D Creator\n\nby P�ter Cseng�di\n2006.");
	//	}
	//
	//	private void menuItem10_Click(Object sender, ActionEvent e)
	//	{
	//		model.Memento.Push(new AddFigure(model, InitialFigure.Tetra));
	//		model.UpdateViews();
	//	}
	//
	//	private void button2_Click(Object sender, ActionEvent e)
	//	{
	//		if(model.SnapToGrid)
	//		{
	//			model.SetSnapToGrid(false);
	//			button2.setText("Grid: Off");
	//		}
	//		else
	//		{
	//			model.SetSnapToGrid(true);
	//			button2.setText("Grid: On");
	//		}
	//	}
	//
	//	private void menuItem11_Click(Object sender, ActionEvent e)
	//	{
	//		model.Memento.Push(new AddFigure(model, InitialFigure.Cube));
	//		model.UpdateViews();
	//	}
	//
	//	private void radioButton1_CheckedChanged(object sender, ActionEvent e)
	//	{
	//		if(model.grid_step == 1.0) return;
	//		model.grid_step = 1.0;
	//		UpdateGrid();
	//	}
	//
	//	private void radioButton2_CheckedChanged(object sender, ActionEvent e)
	//	{
	//		if(model.grid_step == 0.5) return;
	//		model.grid_step = 0.5;
	//		UpdateGrid();
	//	}
	//
	//	private void radioButton3_CheckedChanged(object sender, ActionEvent e)
	//	{
	//		if(model.grid_step == 0.25) return;
	//		model.grid_step = 0.25;
	//		UpdateGrid();
	//	}

	public void UpdateGrid()
	{
		//		directXView1.UpdateGrid();
		//		directXView1.Invalidate();
		wireFrameView1.invalidate();
		wireFrameView2.invalidate();
		wireFrameView3.invalidate();
	}

	//	private void T3DCreator_Closing(object sender, System.ComponentModel.CancelEventArgs e)
	//	{
	//		TextureLibrary.DisposeLibrary();
	//		control.Dispose();
	//	}
	//
	//	private void menuItem13_Click(Object sender, ActionEvent e)
	//	{
	//		util.Export(model);
	//	}
	//
	//	private void menuItem15_Click(Object sender, ActionEvent e)
	//	{
	//		model.Memento.Undo();
	//		model.UpdateViews();
	//	}
	//
	//	private void menuItem16_Click(Object sender, ActionEvent e)
	//	{
	//		model.Memento.Redo();
	//		model.UpdateViews();
	//	}
	//
	//	private void menuItem18_Click(Object sender, ActionEvent e)
	//	{
	//		mprop.Model = model;
	//		mprop.Show();
	//	}
	//
	//	private void menuItem19_Click(Object sender, ActionEvent e)
	//	{
	//		CFigure f = model.Selected as CFigure;
	//		if(f != null)
	//		{
	//			fprop.Model = model;
	//			fprop.Figure = f;
	//			fprop.Show();
	//		}
	//
	//	}
	//
	//	private void menuItem20_Click(Object sender, ActionEvent e)
	//	{
	//		CFigure figure = model.Selected as CFigure;
	//		if(figure == null) return;
	//		figure.texID = TextureLibrary.LoadImage();
	//		model.UpdateViews(Updates.Full);
	//	}
	//
	//	private void menuItem23_Click(Object sender, ActionEvent e)
	//	{
	//		CFigure figure = model.Selected as CFigure;
	//		if(figure == null) return;
	//		Operation op = new DeleteFigure(model, figure);
	//		model.Memento.Push(op);
	//		model.Selected = null;
	//		model.UpdateViews(Updates.Full);
	//	}
	//
	//	private void menuItem25_Click(Object sender, ActionEvent e)
	//	{
	//		CFigure figure = null;
	//		CTriangle triangle = model.Selected as CTriangle;
	//		if(triangle == null) return;
	//
	//		foreach(CFigure f in model.figures)
	//		{
	//			if(f.hasPart(triangle))
	//			{
	//				figure = f;
	//				break;
	//			}
	//		}
	//
	//		if(figure == null) return;
	//		Operation op = new TriangleSplit(figure, triangle);
	//		model.Memento.Push(op);
	//		model.Selected = null;
	//		model.UpdateViews(Updates.Full);
	//	}
	//
	//	private void menuItem26_Click(Object sender, ActionEvent e)
	//	{
	//		CFigure figure = null;
	//		CEdge edge = model.Selected as CEdge;
	//		if(edge == null) return;
	//
	//		foreach(CFigure f in model.figures)
	//		{
	//			if(f.hasPart(edge))
	//			{
	//				figure = f;
	//				break;
	//			}
	//		}
	//
	//		if(figure == null) return;
	//		Operation op = new EdgeSplit(figure, edge);
	//		model.Memento.Push(op);
	//		model.Selected = null;
	//		model.UpdateViews(Updates.Full);
	//	}
	//
	//	private void radioButton4_CheckedChanged(object sender, ActionEvent e)
	//	{
	//		if(model.grid_step == 0.1) return;
	//		model.grid_step = 0.1;
	//		UpdateGrid();
	//	}
	//
	//	private void radioButton5_CheckedChanged(object sender, ActionEvent e)
	//	{
	//		if(model.grid_step == 0.05) return;
	//		model.grid_step = 0.05;
	//		UpdateGrid();
	//	}
	//
	//	private void menuItem27_Click(Object sender, ActionEvent e)
	//	{
	//		model.Selected = model;
	//		model.UpdateViews(Updates.Selection);
	//	}
	//
	//	private void operation_box_SelectedIndexChanged(object sender, ActionEvent e)
	//	{
	//		operation = (MoveOperation)operation_box.SelectedIndex;
	//	}
	//
	//	private void menuItem31_Click(Object sender, ActionEvent e)
	//	{
	//		model.AddMeshFigure(InitialFigure.Sphere, directXView1.Device);
	//	}
	//
	//	private void menuItem33_Click(Object sender, ActionEvent e)
	//	{
	//		model.AddMeshFigure(InitialFigure.Cylinder, directXView1.Device);
	//	}
	//
	//	private void menuItem34_Click(Object sender, ActionEvent e)
	//	{
	//		model.AddMeshFigure(InitialFigure.Box, directXView1.Device);
	//	}
	//
	//	private void menuItem35_Click(Object sender, ActionEvent e)
	//	{
	//		model.AddMeshFigure(InitialFigure.Torus, directXView1.Device);
	//	}
	//
	//	private void menuItem36_Click(Object sender, ActionEvent e)
	//	{
	//		model.AddMeshFigure(InitialFigure.TextMesh, directXView1.Device);
	//	}
	//
	//	private void menuItem37_Click(Object sender, ActionEvent e)
	//	{
	//		model.AddMeshFigure(InitialFigure.Torus, directXView1.Device);
	//	}
	//
	//	private void label1_Click(Object sender, ActionEvent e)
	//	{
	//
	//	}

	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;

}