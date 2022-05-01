package hu.csega.editors.anm.ui;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import hu.csega.editors.anm.ui.layout.panels.AnimatorPanelFixedSizeLayoutListener;
import hu.csega.editors.anm.ui.layout.panels.AnimatorPanelLayoutChangeListener;
import hu.csega.editors.anm.ui.layout.panels.AnimatorPanelLayoutManager;

public class AnimatorPartEditorPanel extends JPanel {

	public AnimatorPanelLayoutManager layout;

	public JLabel labelJoints;
	public JScrollPane scrollJoints;
	public JList<String> joints;

	public JButton horizontalFlip;
	public JButton verticalFlip;

	public JButton rotateUp;
	public JButton rotateDown;
	public JButton rotateLeft;
	public JButton rotateRight;

	public AnimatorPartEditorPanel() {
		this.layout = new AnimatorPanelLayoutManager(200, 300);
		this.setLayout(layout);

		int offset = 0;

		this.labelJoints = new JLabel("Joints:");
		this.add(this.labelJoints, new AnimatorPanelLayoutChangeListener() {
			@Override
			public void arrange(Component component, int width, int height) {
				component.setBounds(5, 5, width - 10, 20);
			}
		});

		this.joints = new JList<>();
		this.scrollJoints = new JScrollPane(this.joints);
		this.add(this.scrollJoints, new AnimatorPanelLayoutChangeListener() {
			@Override
			public void arrange(Component component, int width, int height) {
				component.setBounds(5, 27, width - 10, 160);
			}
		});

		this.horizontalFlip = new JButton("Horiz. Flip");
		this.add(this.horizontalFlip, new AnimatorPanelLayoutChangeListener() {
			@Override
			public void arrange(Component component, int width, int height) {
				component.setBounds(5, 190, width / 2 - 10, 20);
			}
		});

		this.verticalFlip = new JButton("Vert. Flip");
		this.add(this.verticalFlip, new AnimatorPanelLayoutChangeListener() {
			@Override
			public void arrange(Component component, int width, int height) {
				component.setBounds(width / 2 + 5, 190, width / 2 - 10, 20);
			}
		});

		offset += 220;


		this.rotateUp = new JButton("▲");
		this.add(this.rotateUp, new AnimatorPanelFixedSizeLayoutListener(0, offset) {
			@Override
			protected void resize(Component component, int offsetX, int offsetY, int width, int height) {
				component.setBounds(width / 2 - 25, offsetY + 10, 50, 20);
			}
		});

		this.rotateDown = new JButton("▼");
		this.add(this.rotateDown, new AnimatorPanelFixedSizeLayoutListener(0, offset) {
			@Override
			protected void resize(Component component, int offsetX, int offsetY, int width, int height) {
				component.setBounds(width / 2 - 25, offsetY + 40, 50, 20);
			}
		});

		this.rotateLeft = new JButton("◄");
		this.add(this.rotateLeft, new AnimatorPanelFixedSizeLayoutListener(0, offset) {
			@Override
			protected void resize(Component component, int offsetX, int offsetY, int width, int height) {
				component.setBounds(width / 2 - 90, offsetY + 25, 50, 20);
			}
		});

		this.rotateRight = new JButton("►");
		this.add(this.rotateRight, new AnimatorPanelFixedSizeLayoutListener(0, offset) {
			@Override
			protected void resize(Component component, int offsetX, int offsetY, int width, int height) {
				component.setBounds(width / 2 + 40, offsetY + 25, 50, 20);
			}
		});

		offset += 60;
	}

	private static final long serialVersionUID = 1L;
}
