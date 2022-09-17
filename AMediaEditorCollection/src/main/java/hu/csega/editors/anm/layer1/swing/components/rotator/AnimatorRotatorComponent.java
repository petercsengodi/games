package hu.csega.editors.anm.layer1.swing.components.rotator;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollBar;

public class AnimatorRotatorComponent extends JPanel implements LayoutManager {

	private double aroundXAxis;
	private double aroundYAxis;
	private double aroundZAxis;

	private List<AnimatorRotationListener> listeners = null;

	private JScrollBar controlXAxis;
	private JScrollBar controlYAxis;
	private JScrollBar controlZAxis;
	private JPanel canvas;

	public AnimatorRotatorComponent() {
		this.controlXAxis = new JScrollBar(JScrollBar.VERTICAL);
		this.controlYAxis = new JScrollBar(JScrollBar.HORIZONTAL);
		this.controlZAxis = new JScrollBar(JScrollBar.VERTICAL);
		this.canvas = new JPanel();

		this.setLayout(this);

		// Without "name" argument the addLayoutComponent method is not called.
		this.add("controlXAxis", controlXAxis);
		this.add("controlYAxis", controlYAxis);
		this.add("controlZAxis", controlZAxis);
		this.add("canvas", canvas);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(AnimatorRotatorCanvas.CANVAS_MIN_SIZE + 2 * SCROLL_BAR_MIN_SIZE,
				AnimatorRotatorCanvas.CANVAS_MIN_SIZE + SCROLL_BAR_MIN_SIZE);
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		if(comp == null)
			return;

		Dimension preferredSize = getPreferredSize();
		int width = (int) Math.max(this.getWidth(), preferredSize.getWidth());
		int height = (int) Math.max(this.getHeight(), preferredSize.getHeight());

		if(comp == controlXAxis) {
			comp.setBounds(0, 0, SCROLL_BAR_MIN_SIZE, height - SCROLL_BAR_MIN_SIZE);
		} else if(comp == controlYAxis) {
			comp.setBounds(SCROLL_BAR_MIN_SIZE, height - SCROLL_BAR_MIN_SIZE,
					width - 2 * SCROLL_BAR_MIN_SIZE, SCROLL_BAR_MIN_SIZE);
		} else if(comp == controlZAxis) {
			comp.setBounds(width - SCROLL_BAR_MIN_SIZE, 0, SCROLL_BAR_MIN_SIZE,
					height - SCROLL_BAR_MIN_SIZE);
		} else if(comp == canvas) {
			comp.setBounds(SCROLL_BAR_MIN_SIZE, 0, width - 2 * SCROLL_BAR_MIN_SIZE,
					height - SCROLL_BAR_MIN_SIZE);
		}
	}

	@Override
	public void removeLayoutComponent(Component comp) {
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getPreferredSize();
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getPreferredSize();
	}

	@Override
	public void layoutContainer(Container parent) {
	}

	public void addListener(AnimatorRotationListener listener) {
		if(listeners == null)
			listeners = new ArrayList<>();

		listeners.add(listener);
	}

	private void notifyListeners() {
		if(listeners != null) {
			for(AnimatorRotationListener listener : listeners)
				listener.changed(aroundXAxis, aroundYAxis, aroundZAxis);
		}
	}

	private static final int SCROLL_BAR_MIN_SIZE = 20;

	private static final long serialVersionUID = 1L;

}
