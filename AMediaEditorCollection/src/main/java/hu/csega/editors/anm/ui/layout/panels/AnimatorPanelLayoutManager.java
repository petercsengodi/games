package hu.csega.editors.anm.ui.layout.panels;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;

import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class AnimatorPanelLayoutManager implements LayoutManager, LayoutManager2 {

	private static final int MAX_NUMBER_OF_COMPONENTS = 30;

	private int width;
	private int height;

	private int preferredWidth;
	private int preferredHeight;

	private int numberOfComponents = 0;
	private Component[] components = new Component[MAX_NUMBER_OF_COMPONENTS];
	private AnimatorPanelLayoutChangeListener[] listeners = new AnimatorPanelLayoutChangeListener[MAX_NUMBER_OF_COMPONENTS];
	private boolean invalid = true;

	public AnimatorPanelLayoutManager(int preferredWidth, int preferredHeight) {
		this.preferredWidth = preferredWidth;
		this.preferredHeight = preferredHeight;

		this.width = preferredWidth;
		this.height = preferredHeight;
	}

	@Override
	public void addLayoutComponent(Component component, Object constraints) {
		if(numberOfComponents >= MAX_NUMBER_OF_COMPONENTS) {
			throw new IllegalStateException("Maximum number of components is already reached!");
		}

		components[numberOfComponents] = component;

		AnimatorPanelLayoutChangeListener listener = null;
		if(constraints instanceof AnimatorPanelLayoutChangeListener) {
			listener = (AnimatorPanelLayoutChangeListener) constraints;
			listeners[numberOfComponents] = listener;
		}

		numberOfComponents++;

		if(component != null && listener != null) {
			listener.arrange(component, width, height);
		}
	}

	@Override
	public void addLayoutComponent(String name, Component component) {
		addLayoutComponent(component, null);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		invalid = true;
	}

	@Override
	public void removeLayoutComponent(Component component) {
		// throw new UnsupportedOperationException("removeLayoutComponent");
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return new Dimension(preferredWidth, preferredHeight);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(100, 100);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return new Dimension(2000, 2000);
	}

	@Override
	public void layoutContainer(Container parent) {
		logger.info("Parent: " + (parent != null ? parent.getClass().getName() : "–"));

		if(invalid) {
			width = parent.getWidth();
			height = parent.getHeight();
			recalculatePositions(width, height);
		}
	}

	private void recalculatePositions(int width, int height) {
		logger.info("Recalculating positions for a box " + width + 'x' + height + '.');
		for(int i = 0; i < numberOfComponents; i++) {
			Component component = components[i];
			AnimatorPanelLayoutChangeListener listener = listeners[i];
			if(component != null && listener != null) {
				listener.arrange(component, width, height);
			}
		}
	}

	private static final Logger logger = LoggerFactory.createLogger(AnimatorPanelLayoutManager.class);
}
