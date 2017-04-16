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

import hu.csega.games.engine.env.Disposable;
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

	private JMenu menuSubAddFigure;

	private JMenuItem menuItemNew;
	private JMenuItem menuItemOpen;
	private JMenuItem menuItemSave;
	private JMenuItem menuItemSaveAs;
	private JMenuItem menuItemExit;
	private JMenuItem menuItemAbout;
	private JMenuItem menuItemTetra;
	private JButton button2;
	private JMenuItem menuItemCube;
	private JTabbedPane tabControl1;
	private JPanel tabPage1;
	private JPanel tabPage2;
	private JRadioButton radioButton1;
	private JRadioButton radioButton2;
	private JRadioButton radioButton3;
	private JMenuItem menuItemExport;
	private JMenuItem menuItemUndo;
	private JMenuItem menuItemRedo;
	private JMenuItem menuItemModelProperties;
	private JMenuItem menuItemFigureProperties;
	private JMenuItem menuItemLoadFigureTexture;
	private JMenuItem menuItemSplitTriangle;
	private JMenuItem menuItemDeleteFigure;
	private JMenuItem menuItemSplitEdge;
	private JRadioButton radioButton4;
	private JRadioButton radioButton5;
	private JMenuItem menuItemSelectModel;
	private JMenuItem menuItemResize;
	private JLabel label1;
	private JComboBox<Object> operation_box;
	private JMenuItem menuItemSphere;
	private JMenuItem menuItemCylinder;
	private JMenuItem menuItemBox;
	private JMenuItem menuItemTorus;
	private JMenuItem menuItemText;
	private JMenuItem menuItemTeapot;

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
		//				components.dispose();
		//			}
		//			util.dispose();
		//			fprop.dispose();
		//		}
		//		super.dispose( disposing );
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

		this.menuSubAddFigure = new JMenu();

		this.menuItemNew = new JMenuItem();
		this.menuItemOpen = new JMenuItem();
		this.menuItemSave = new JMenuItem();
		this.menuItemSaveAs = new JMenuItem();
		this.menuItemExit = new JMenuItem();
		this.menuItemUndo = new JMenuItem();
		this.menuItemRedo = new JMenuItem();
		this.menuItemSplitEdge = new JMenuItem();
		this.menuItemSplitTriangle = new JMenuItem();
		this.menuItemResize = new JMenuItem();
		this.menuItemSelectModel = new JMenuItem();
		this.menuItemModelProperties = new JMenuItem();
		this.menuItemTetra = new JMenuItem();
		this.menuItemCube = new JMenuItem();
		this.menuItemSphere = new JMenuItem();
		this.menuItemCylinder = new JMenuItem();
		this.menuItemBox = new JMenuItem();
		this.menuItemTorus = new JMenuItem();
		this.menuItemText = new JMenuItem();
		this.menuItemTeapot = new JMenuItem();
		this.menuItemDeleteFigure = new JMenuItem();
		this.menuItemLoadFigureTexture = new JMenuItem();
		this.menuItemFigureProperties = new JMenuItem();
		this.menuItemExport = new JMenuItem();
		this.menuItemAbout = new JMenuItem();
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
		this.operation_box = new JComboBox<>();

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

		this.menuBar.add(this.menuFile);
		this.menuBar.add(this.menuEdit);
		this.menuBar.add(this.menuModel);
		this.menuBar.add(this.menuFigure);
		this.menuBar.add(this.menuMesh);
		this.menuBar.add(this.menuHelp);

		this.menuFile.add(this.menuItemNew);
		this.menuFile.add(this.menuItemOpen);
		this.menuFile.add(this.menuItemSave);
		this.menuFile.add(this.menuItemSaveAs);
		this.menuFile.add(this.menuItemExit);
		this.menuFile.setText("File");

		this.menuItemNew.setText("New");
		// this.menuItem2.Click += new System.EventHandler(this.menuItem2_Click);

		this.menuItemOpen.setText("Open");
		// this.menuItem3.Click += new System.EventHandler(this.menuItem3_Click);

		this.menuItemSave.setText("Save");
		// this.menuItem4.Click += new System.EventHandler(this.menuItem4_Click);

		this.menuItemSaveAs.setText("Save As...");
		//		this.menuItem5.Click += new System.EventHandler(this.menuItem5_Click);

		this.menuItemExit.setText("Exit");
		//		this.menuItem6.Click += new System.EventHandler(this.menuItem6_Click);

		this.menuEdit.add(this.menuItemUndo);
		this.menuEdit.add(this.menuItemRedo);
		this.menuEdit.addSeparator();
		this.menuEdit.add(this.menuItemSplitEdge);
		this.menuEdit.add(this.menuItemSplitTriangle);
		this.menuEdit.addSeparator();
		this.menuEdit.add(this.menuItemResize);
		this.menuEdit.setText("Edit");

		this.menuItemUndo.setText("Undo");
		//this.menuItem15.Click += new System.EventHandler(this.menuItem15_Click);

		this.menuItemRedo.setText("Redo");
		//this.menuItem16.Click += new System.EventHandler(this.menuItem16_Click);

		this.menuItemSplitEdge.setText("Split Edge");
		//this.menuItem26.Click += new System.EventHandler(this.menuItem26_Click);

		this.menuItemSplitTriangle.setText("Split Triangle");
		//this.menuItem25.Click += new System.EventHandler(this.menuItem25_Click);


		this.menuItemResize.setText("Resize");

		this.menuModel.add(this.menuItemSelectModel);
		this.menuModel.addSeparator();
		this.menuModel.add(this.menuItemModelProperties);
		this.menuModel.setText("Model");

		this.menuItemSelectModel.setText("Select Model");
		//this.menuItem27.Click += new System.EventHandler(this.menuItem27_Click);

		this.menuItemModelProperties.setText("Model Properties");
		//this.menuItem18.Click += new System.EventHandler(this.menuItem18_Click);

		this.menuFigure.add(this.menuSubAddFigure);
		this.menuFigure.add(this.menuItemDeleteFigure);
		this.menuFigure.add(this.menuItemLoadFigureTexture);
		this.menuFigure.addSeparator();
		this.menuFigure.add(this.menuItemFigureProperties);
		this.menuFigure.setText("Figure");

		this.menuSubAddFigure.add(this.menuItemTetra);
		this.menuSubAddFigure.add(this.menuItemCube);
		this.menuSubAddFigure.addSeparator();
		this.menuSubAddFigure.add(this.menuItemSphere);
		this.menuSubAddFigure.add(this.menuItemCylinder);
		this.menuSubAddFigure.add(this.menuItemBox);
		this.menuSubAddFigure.add(this.menuItemTorus);
		this.menuSubAddFigure.add(this.menuItemText);
		this.menuSubAddFigure.add(this.menuItemTeapot);
		this.menuSubAddFigure.setText("Add Figure");

		this.menuItemTetra.setText("Tetra");
		//this.menuItem10.Click += new System.EventHandler(this.menuItem10_Click);

		this.menuItemCube.setText("Cube");
		//this.menuItem11.Click += new System.EventHandler(this.menuItem11_Click);

		this.menuItemSphere.setText("Sphere");
		//this.menuItem31.Click += new System.EventHandler(this.menuItem31_Click);

		this.menuItemCylinder.setText("Cylinder");
		//this.menuItem33.Click += new System.EventHandler(this.menuItem33_Click);

		this.menuItemBox.setText("Box");
		//this.menuItem34.Click += new System.EventHandler(this.menuItem34_Click);

		this.menuItemTorus.setText("Torus");
		//this.menuItem35.Click += new System.EventHandler(this.menuItem35_Click);

		this.menuItemText.setText("Text");
		//this.menuItem36.Click += new System.EventHandler(this.menuItem36_Click);

		this.menuItemTeapot.setText("Teapot");
		//this.menuItem37.Click += new System.EventHandler(this.menuItem37_Click);

		this.menuItemDeleteFigure.setText("Delete Figure");
		//this.menuItem23.Click += new System.EventHandler(this.menuItem23_Click);

		this.menuItemLoadFigureTexture.setText("Load Figure Texture");
		//this.menuItem20.Click += new System.EventHandler(this.menuItem20_Click);

		this.menuItemFigureProperties.setText("Figure Properties");
		//this.menuItem19.Click += new System.EventHandler(this.menuItem19_Click);

		this.menuMesh.add(this.menuItemExport);
		this.menuMesh.setText("Mesh");

		this.menuItemExport.setText("Export");
		//this.menuItem13.Click += new System.EventHandler(this.menuItem13_Click);

		this.menuHelp.add(this.menuItemAbout);
		this.menuHelp.setText("Help");

		this.menuItemAbout.setText("About");
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

		this.radioButton4.setLocation(new Point(8, 504));
		this.radioButton4.setName("radioButton4");
		this.radioButton4.setSize(new Dimension(40, 24));
		this.radioButton4.setText("0.1");
		// this.radioButton4.CheckedChanged += new System.EventHandler(this.radioButton4_CheckedChanged);

		this.radioButton5.setLocation(new Point(64, 504));
		this.radioButton5.setName("radioButton5");
		this.radioButton5.setSize(new Dimension(48, 24));
		this.radioButton5.setText("0.05");
		//this.radioButton5.CheckedChanged += new System.EventHandler(this.radioButton5_CheckedChanged);

		this.label1.setLocation(new Point(8, 416));
		this.label1.setName("label1");
		this.label1.setSize(new Dimension(64, 24));
		this.label1.setText("Operation:");
		this.label1.setVerticalAlignment(JLabel.CENTER);
		//this.label1.Click += new System.EventHandler(this.label1_Click);

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
		//			for(CFigure f : m.figures)
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
		//			for(CFigure f : m.figures)
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
	//	private void radioButton1_CheckedChanged(Object sender, ActionEvent e)
	//	{
	//		if(model.grid_step == 1.0) return;
	//		model.grid_step = 1.0;
	//		UpdateGrid();
	//	}
	//
	//	private void radioButton2_CheckedChanged(Object sender, ActionEvent e)
	//	{
	//		if(model.grid_step == 0.5) return;
	//		model.grid_step = 0.5;
	//		UpdateGrid();
	//	}
	//
	//	private void radioButton3_CheckedChanged(Object sender, ActionEvent e)
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

	//	private void T3DCreator_Closing(Object sender, System.ComponentModel.CancelEventArgs e)
	//	{
	//		TextureLibrary.DisposeLibrary();
	//		control.dispose();
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
	//		for(CFigure f : model.figures)
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
	//		for(CFigure f : model.figures)
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
	//	private void radioButton4_CheckedChanged(Object sender, ActionEvent e)
	//	{
	//		if(model.grid_step == 0.1) return;
	//		model.grid_step = 0.1;
	//		UpdateGrid();
	//	}
	//
	//	private void radioButton5_CheckedChanged(Object sender, ActionEvent e)
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
	//	private void operation_box_SelectedIndexChanged(Object sender, ActionEvent e)
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