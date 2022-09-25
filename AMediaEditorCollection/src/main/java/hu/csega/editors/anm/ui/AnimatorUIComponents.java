package hu.csega.editors.anm.ui;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import hu.csega.editors.anm.layer1.swing.components.partlist.AnimatorPartEditorPanel;
import hu.csega.editors.anm.layer1.swing.components.partlist.AnimatorPartListItem;
import hu.csega.editors.anm.layer1.swing.components.partlist.AnimatorPartListModel;
import hu.csega.editors.anm.layer1.swing.wireframe.AnimatorWireFrameView;
import hu.csega.games.engine.intf.GameWindow;

public class AnimatorUIComponents {

	//////////////////////////////////////////////////////////////////////////////
	// 3D engine

	public GameWindow gameWindow;

	//////////////////////////////////////////////////////////////////////////////
	// Dialogs

	public JFrame frame;
	public JFileChooser addNewPartFile;
	public String userDirectory;
	public String meshDirectory;

	//////////////////////////////////////////////////////////////////////////////
	// Part list

	public AnimatorPartListModel partListModel;
	public JList<AnimatorPartListItem> partList;
	public JScrollPane partListScrollPane;

	//////////////////////////////////////////////////////////////////////////////
	// Tabs / Views

	public JTabbedPane tabbedPane;
	public AnimatorWireFrameView panelFront;
	public AnimatorWireFrameView panelTop;
	public AnimatorWireFrameView panelSide;
	public AnimatorWireFrameView panelWireFrame;
	public JPanel panel3D;
	public AnimatorPartEditorPanel partEditorPanel;
	public AnimatorCommonSettingsPanel commonSettingsPanel;
	public AnimatorScenesPanel scenesPanel;

}
