package hu.csega.superstition.animation;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import hu.csega.superstition.fileoperations.FileControl;
import hu.csega.superstition.fileoperations.FileOperation;

public class AnimationTool extends JFrame {

	private AnimationFacade facade;
	private FileControl fileControl;
	private Action action = Action.MOVE;

	private JMenuBar menuBar;

	private JMenu menuFile;
	private JMenuItem menuItemNew;
	private JMenuItem menuItemOpen;
	private JMenuItem menuItemSave;
	private JMenuItem menuItemSaveAs;
	private JMenuItem menuItemExit;

	private JMenuItem menuItemAddPart;
	private JMenuItem menuItemRemovePart;
	private JMenu menuParts;

	private JMenu menuHelp;
	private JMenuItem menuItemAbout;

	private JTabbedPane tab_control;
	private JPanel tab_part_editor;
	private JPanel tab_scene_editor;
	private JButton grid_button;
	private JRadioButton rad1_0;
	private JRadioButton rad0_5;
	private JRadioButton rad0_25;
	private JTabbedPane tabControl1;
	private JPanel tabPage1;
	private JPanel tabPage2;
	private JRadioButton rad0_1;
	private JRadioButton rad0_05;
	private JComboBox<Object> operation_box;
	private JLabel label1;

	private TreeObjectView treeView1;

	// TODO uncomment, fix
	//	private WireFrameView wireFrameView1;
	//	private WireFrameView wireFrameView2;
	//	private WireFrameView wireFrameView3;
	//	private SceneEditor sceneEditor1;
	//	private PartEditor partEditor1;
	//	private OpenGLView dxview;

	public AnimationTool(AnimationFacade facade) {
		this.facade = facade;
		InitializeComponent();

		fileControl = new FileControl(
				new FileOperation() {
					@Override
					public void call(String file_name) {
						loadFile(file_name);
					}
				},
				new FileOperation(){
					@Override
					public void call(String file_name) {
						saveFile(file_name);
					}
				},
				"/res/anims", "anm");

		//		model.addView(partEditor1);
		//		model.addView(dxview);
		//		model.RegisterView(wireFrameView1);
		//		model.RegisterView(wireFrameView2);
		//		model.RegisterView(wireFrameView3);
		//		model.RegisterView(sceneEditor1);
		//		wireFrameView1.SetView(Perspectives.Front);
		//		wireFrameView2.SetView(Perspectives.Top);
		//		wireFrameView3.SetView(Perspectives.Left);
	}

	public void initializeAndShwoTool() {
		// AnimationModel model = facade.getModel();
		// model.addView(treeView1);
		this.setVisible(true);
	}

	protected void dispose( boolean disposing )
	{
		//		if( disposing )
		//		{
		//			if(components != null)
		//			{
		//				components.dispose();
		//			}
		//			MeshLibrary.Instance().dispose();
		//			file_control.dispose();
		//		}
		//		super.dispose( disposing );
	}

	private void InitializeComponent()
	{
		this.menuBar = new JMenuBar();

		this.menuFile = new JMenu("File");
		this.menuItemNew = new JMenuItem("New");
		this.menuItemOpen = new JMenuItem("Open");
		this.menuItemSave = new JMenuItem("Save");
		this.menuItemSaveAs = new JMenuItem("Save as...");
		this.menuItemExit = new JMenuItem("Exit");

		this.menuParts = new JMenu("Parts");
		this.menuItemAddPart = new JMenuItem("Add Part");
		this.menuItemRemovePart = new JMenuItem("Remove Part");

		this.menuHelp = new JMenu("Help");
		this.menuItemAbout = new JMenuItem("About");

		this.tab_control = new JTabbedPane();
		this.tab_part_editor = new JPanel();
		this.tab_scene_editor = new JPanel();
		this.grid_button = new JButton();
		this.rad1_0 = new JRadioButton();
		this.rad0_5 = new JRadioButton();
		this.rad0_25 = new JRadioButton();
		this.tabControl1 = new JTabbedPane();
		this.tabPage1 = new JPanel();
		this.tabPage2 = new JPanel();
		this.rad0_1 = new JRadioButton();
		this.rad0_05 = new JRadioButton();
		this.operation_box = new JComboBox<>();
		this.label1 = new JLabel();

		// TODO uncomment, fix
		//		this.sceneEditor1 = new SceneEditor();
		//		this.partEditor1 = new PartEditor();
		//		this.dxview = new OpenGLView();
		//		this.wireFrameView1 = new WireFrameView();
		//		this.wireFrameView2 = new WireFrameView();
		//		this.wireFrameView3 = new WireFrameView();

		this.treeView1 = new TreeObjectView(facade);
		this.treeView1.setLocation(new Point(0, 0));
		this.treeView1.setSize(new Dimension(120, 296));

		this.menuHelp.add(this.menuItemAbout);
		this.menuBar.add(this.menuHelp);

		this.menuParts.add(this.menuItemAddPart);
		this.menuParts.add(this.menuItemRemovePart);
		this.menuBar.add(this.menuParts);

		this.menuFile.add(this.menuItemNew);
		this.menuFile.add(this.menuItemOpen);
		this.menuFile.add(this.menuItemSave);
		this.menuFile.add(this.menuItemSaveAs);
		this.menuFile.add(this.menuItemExit);
		this.menuBar.add(this.menuFile);

		this.menuItemNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileControl.saveNew(AnimationTool.this);
				AnimationModel model = facade.getModel();
				model.clear();
			}
		});

		this.menuItemOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileControl.open(AnimationTool.this);
			}
		});

		this.menuItemSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileControl.save(AnimationTool.this);
			}
		});

		this.menuItemSaveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileControl.saveAs(AnimationTool.this);
			}
		});

		this.menuItemExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AnimationTool.this.onClosing();
				System.exit(0);
			}
		});

		this.menuItemAddPart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//				model.parts.add(new CPart(model));
				//				model.UpdateViews();
			}
		});

		this.menuItemRemovePart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//				if(model.selected != null)
				//				{
				//					model.DeleteConnectedPart(
				//							model.parts.indexOf(model.selected));
				//					model.parts.remove(model.selected);
				//					model.UpdateViews();
				//				}
			}
		});

		this.tab_control.addTab("Part Editor", this.tab_part_editor);
		this.tab_control.addTab("Scene Editor", this.tab_scene_editor);
		this.tab_control.setLocation(new Point(400, 0));
		this.tab_control.setSize(new Dimension(336, 256));

		// TODO uncomment, fix
		//		this.tab_part_editor.add(this.partEditor1);
		this.tab_part_editor.setLocation(new Point(4, 22));
		this.tab_part_editor.setSize(new Dimension(328, 230));

		// TODO uncomment, fix
		//		this.partEditor1.setLocation(new Point(0, 0));
		//		this.partEditor1.setPart(null);
		//		this.partEditor1.setSize(new Dimension(336, 232));

		// TODO uncomment, fix
		//		this.tab_scene_editor.add(this.sceneEditor1);
		this.tab_scene_editor.setLocation(new Point(4, 22));
		this.tab_scene_editor.setSize(new Dimension(328, 230));

		// TODO uncomment, fix
		//		this.sceneEditor1.setLocation(new Point(0, 0));
		//		this.sceneEditor1.setSize(new Dimension(328, 232));
		//
		//		this.dxview.setLocation(new Point(0, 0));
		//		this.dxview.setSize(new Dimension(336, 272));
		//
		//		this.wireFrameView1.setLocation(new Point(120, 0));
		//		this.wireFrameView1.setSize(new Dimension(280, 256));
		//
		//		this.wireFrameView2.setLocation(new Point(120, 256));
		//		this.wireFrameView2.setSize(new Dimension(280, 288));
		//
		//		this.wireFrameView3.setLocation(new Point(0, 0));
		//		this.wireFrameView3.setSize(new Dimension(336, 272));

		this.grid_button.setLocation(new Point(8, 304));
		this.grid_button.setSize(new Dimension(96, 24));
		this.grid_button.setText("Grid: On");
		this.grid_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AnimationModel model = facade.getModel();
				if(model.isSnapToGrid()) {
					model.setSnapToGrid(false);
					grid_button.setText("Grid: Off");
				} else {
					model.setSnapToGrid(true);
					grid_button.setText("Grid: On");
				}
			}
		});

		ActionListener radChanged = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AnimationModel model = facade.getModel();
				Object sender = e.getSource();

				if(sender == rad1_0) {
					model.setGridStep(1f);
				} else if(sender == rad0_5) {
					model.setGridStep(0.5f);
				} else if(sender == rad0_25) {
					model.setGridStep(0.25f);
				} else if(sender == rad0_1) {
					model.setGridStep(0.1f);
				} else if(sender == rad0_05) {
					model.setGridStep(0.05f);
				}

				// TODO uncomment, fix
				//				dxview.UpdateGrid();
				//				dxview.repaint();
				//				wireFrameView1.repaint();
				//				wireFrameView2.repaint();
				//				wireFrameView3.repaint();
			}
		};

		this.rad1_0.setLocation(new Point(8, 336));
		this.rad1_0.setText("1.0 grid");
		this.rad1_0.addActionListener(radChanged);

		this.rad0_5.setLocation(new Point(8, 360));
		this.rad0_5.setText("0.5 grid");
		this.rad0_5.addActionListener(radChanged);

		this.rad0_25.setLocation(new Point(8, 384));
		this.rad0_25.setText("0.25 grid");
		this.rad0_25.addActionListener(radChanged);

		this.tabControl1.addTab("Wireframe View", this.tabPage1);
		this.tabControl1.addTab("OpenGL View", this.tabPage2);
		this.tabControl1.setLocation(new Point(400, 256));
		this.tabControl1.setSize(new Dimension(336, 296));

		// TODO uncomment, fix
		//		this.tabPage1.add(this.wireFrameView3);
		this.tabPage1.setLocation(new Point(4, 22));
		this.tabPage1.setSize(new Dimension(328, 270));

		// TODO uncomment, fix
		//		this.tabPage2.add(this.dxview);
		this.tabPage2.setLocation(new Point(4, 22));
		this.tabPage2.setSize(new Dimension(328, 270));

		this.rad0_1.setLocation(new Point(8, 408));
		this.rad0_1.setText("0.1 grid");
		this.rad0_1.addActionListener(radChanged);

		this.rad0_05.setLocation(new Point(8, 432));
		this.rad0_05.setText("0.05 grid");
		this.rad0_05.addActionListener(radChanged);

		this.operation_box.addItem("move");
		this.operation_box.addItem("zoom");
		this.operation_box.addItem("rotate");

		this.operation_box.setLocation(new Point(8, 504));
		this.operation_box.setSize(new Dimension(104, 21));
		this.operation_box.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// operation = (MoveOperation)operation_box.getSelectedItem();
			}
		});

		this.label1.setLocation(new Point(8, 480));
		this.label1.setSize(new Dimension(64, 24));
		this.label1.setText("Operation:");

		Container contentPane = getContentPane();

		contentPane.add(this.operation_box);
		contentPane.add(this.label1);
		contentPane.add(this.rad0_05);
		contentPane.add(this.rad0_1);
		contentPane.add(this.tabControl1);
		contentPane.add(this.rad0_25);
		contentPane.add(this.rad0_5);
		contentPane.add(this.rad1_0);
		contentPane.add(this.grid_button);
		contentPane.add(this.tab_control);
		// TODO uncomment, fix
		//		contentPane.add(this.wireFrameView2);
		//		contentPane.add(this.wireFrameView1);
		//		contentPane.add(this.trview);

		contentPane.setSize(new Dimension(736, 545));

		this.setSize(new Dimension(800, 600));
		this.menuBar.add(menuFile);
		this.setJMenuBar(menuBar);
		this.setTitle("AnimaTool");

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				super.windowClosing(e);
				System.exit(0);
			}
		});

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	private void loadFile(String file_name) {
		//		SAnimation data = (SAnimation)XmlHandler.Load(file_name);
		//		model.SetModelData(data);
		//		model.UpdateViews();
	}

	private void saveFile(String file_name) {
		//		SAnimation data = model.GetModelData();
		//		XmlHandler.Save(file_name, data);
	}

	protected void onClosing() {
		fileControl.save(this);
	}

	public Action getAction() {
		return action;
	}

	private static final long serialVersionUID = 1L;

}