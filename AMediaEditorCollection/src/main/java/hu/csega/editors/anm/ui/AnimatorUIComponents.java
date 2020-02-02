package hu.csega.editors.anm.ui;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import hu.csega.editors.anm.swing.AnimatorWireFrameView;

public class AnimatorUIComponents {

	//////////////////////////////////////////////////////////////////////////////
	// Part list

	public AnimatorPartListModel partListModel;
	public JList<AnimatorPartListItem> partList;
	public JScrollPane partListScrollPane;

	//////////////////////////////////////////////////////////////////////////////
	// Tabs

	public JTabbedPane tabbedPane;
	public AnimatorWireFrameView panelWireFrame;
	public JPanel panel3D;
	public AnimatorPartEditorPanel partEditorPanel;
	public AnimatorCommonSettingsPanel commonSettingsPanel;
	public AnimatorScenesPanel scenesPanel;

}
