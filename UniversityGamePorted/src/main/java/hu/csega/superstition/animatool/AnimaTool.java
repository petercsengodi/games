package hu.csega.superstition.animatool;

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
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;

import hu.csega.superstition.fileoperations.FileControl;
import hu.csega.superstition.fileoperations.FileOperation;

public class AnimaTool extends JFrame {

	private JMenu mainMenu1;
	private JMenuItem menuItem1;
	private JMenuItem menuItem2;
	private JMenuItem menuItem3;
	private JMenuItem menuItem4;
	private JMenuItem menuItem5;
	private JMenuItem menuItem6;
	private JMenuItem menuItem7;
	private JMenuItem menuItem8;
	private JMenuItem menuItem9;
	private JTabbedPane tab_control;
	private JPanel tab_part_editor;
	private JMenuItem menuItem10;
	private JMenuItem menuItem11;
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

	// TODO uncomment, fix
	//	private TreeObjectView trview;
	//	private WireFrameView wireFrameView1;
	//	private WireFrameView wireFrameView2;
	//	private WireFrameView wireFrameView3;
	//	private SceneEditor sceneEditor1;
	//	private PartEditor partEditor1;
	//	private OpenGLView dxview;

	public AnimaTool() {

		InitializeComponent();

		file_control = new FileControl(
				new FileOperation() {
					@Override
					public void call(String file_name) {
						LoadFile(file_name);
					}
				},
				new FileOperation(){
					@Override
					public void call(String file_name) {
						SaveFile(file_name);
					}
				},
				"/res/anims", "anm");

		model = new CModel();
		//		model.RegisterView(partEditor1);
		//		model.RegisterView(dxview);
		//		model.RegisterView(trview);
		//		model.RegisterView(wireFrameView1);
		//		model.RegisterView(wireFrameView2);
		//		model.RegisterView(wireFrameView3);
		//		model.RegisterView(sceneEditor1);
		//		wireFrameView1.SetView(Perspectives.Front);
		//		wireFrameView2.SetView(Perspectives.Top);
		//		wireFrameView3.SetView(Perspectives.Left);
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
		this.mainMenu1 = new JMenu();
		this.menuItem1 = new JMenuItem();
		this.menuItem2 = new JMenuItem();
		this.menuItem3 = new JMenuItem();
		this.menuItem4 = new JMenuItem();
		this.menuItem5 = new JMenuItem();
		this.menuItem6 = new JMenuItem();
		this.menuItem7 = new JMenuItem();
		this.menuItem10 = new JMenuItem();
		this.menuItem11 = new JMenuItem();
		this.menuItem8 = new JMenuItem();
		this.menuItem9 = new JMenuItem();
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
		//
		//		this.trview = new TreeObjectView();
		//		this.trview.setLocation(new Point(0, 0));
		//		this.trview.setSize(new Dimension(120, 296));

		this.mainMenu1.add(this.menuItem1);
		this.mainMenu1.add(this.menuItem7);
		this.mainMenu1.add(this.menuItem8);

		this.menuItem1.add(this.menuItem2);
		this.menuItem1.add(this.menuItem3);
		this.menuItem1.add(this.menuItem4);
		this.menuItem1.add(this.menuItem5);
		this.menuItem1.add(this.menuItem6);
		this.menuItem1.setText("File");

		this.menuItem2.setText("New");
		this.menuItem2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				file_control.saveNew(AnimaTool.this);
				model.parts.clear();
				model.scene = 0;
				model.max_scenes = 1;
				model.UpdateViews();
			}
		});

		this.menuItem3.setText("Open");
		this.menuItem3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				file_control.open(AnimaTool.this);
			}
		});

		this.menuItem4.setText("Save");
		this.menuItem4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				file_control.save(AnimaTool.this);
			}
		});

		this.menuItem5.setText("Save as...");
		this.menuItem5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				file_control.saveAs(AnimaTool.this);
			}
		});

		this.menuItem6.setText("Quit");
		this.menuItem6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AnimaTool.this.onClosing();
				System.exit(0);
			}
		});

		this.menuItem7.add(this.menuItem10);
		this.menuItem7.add(this.menuItem11);
		this.menuItem7.setText("Part");

		this.menuItem10.setText("Add Part");
		this.menuItem10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.parts.add(new CPart(model));
				model.UpdateViews();
			}
		});

		this.menuItem11.setText("Delete Part");
		this.menuItem11.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.selected != null)
				{
					model.DeleteConnectedPart(
							model.parts.indexOf(model.selected));
					model.parts.remove(model.selected);
					model.UpdateViews();
				}
			}
		});

		this.menuItem8.add(this.menuItem9);
		this.menuItem8.setText("Help");

		this.menuItem9.setText("About");
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
				if(model.isSnapToGrid())
				{
					model.setSnapToGrid(false);
					grid_button.setText("Grid: Off");
				}
				else
				{
					model.setSnapToGrid(true);
					grid_button.setText("Grid: On");
				}
			}
		});

		ActionListener radChanged = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object sender = e.getSource();
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
				operation = (MoveOperation)operation_box.getSelectedItem();
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
		this.add(this.mainMenu1);
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

	public static void main(String[] args) throws Exception {
		AnimaTool tool = new AnimaTool();
		tool.setVisible(true);
	}

	private void LoadFile(String file_name) {
		//		SAnimation data = (SAnimation)XmlHandler.Load(file_name);
		//		model.SetModelData(data);
		//		model.UpdateViews();
	}

	private void SaveFile(String file_name) {
		//		SAnimation data = model.GetModelData();
		//		XmlHandler.Save(file_name, data);
	}

	protected void onClosing() {
		file_control.save(this);
	}

	public static MoveOperation GetOperatoion() {
		return operation;
	}

	private CModel model;
	private FileControl file_control;

	private static MoveOperation operation = MoveOperation.Move;

	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;

}