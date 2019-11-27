package hu.csega.editors.anm.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.util.Hashtable;
import java.util.Map;

import com.jogamp.opengl.awt.GLCanvas;

import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class AnimatorRootLayoutManager implements LayoutManager, LayoutManager2 {

	public static final String CANVAS3D = "openGLCanvas";

	private Map<Component, String> componentToName = new Hashtable<>();
	private Map<String, Component> nameToComponent = new Hashtable<>();
	private boolean invalid = true;

	@Override
	public void addLayoutComponent(Component component, Object constraints) {
		String name = (constraints instanceof String ? (String)constraints : null);
		addLayoutComponent(name, component);
	}

	@Override
	public void addLayoutComponent(String name, Component component) {
		logger.info("Adding component: " + component + " With name: " + name);

		if(component instanceof GLCanvas) {
			name = CANVAS3D;
			logger.info("Adding OpenGL related canvas. Name set to: " + name);
		}

		if(name == null || component == null) {
			throw new RuntimeException("Neither of the arguments should be null!");
		}

		if(nameToComponent.containsKey(name)) {
			throw new IllegalStateException("Name already used: " + name);
		}

		if(componentToName.containsKey(component)) {
			throw new IllegalStateException("Component already added: " + component);
		}

		nameToComponent.put(name, component);
		componentToName.put(component, name);
		logger.info("Added component: " + component + " With name: " + name);
		invalid = true;
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
		if(component != null) {
			String name = componentToName.remove(component);
			if(name != null) {
				nameToComponent.remove(name);
			}
		}

		invalid = true;
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return new Dimension(1024, 768);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(800, 600);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return new Dimension(8000, 6000);
	}

	@Override
	public void layoutContainer(Container parent) {
		logger.info("Parent: " + (parent != null ? parent.getClass().getName() : "–"));

		if(invalid) {
			int width = parent.getWidth();
			int height = parent.getHeight();
			recalculatePositions(width, height);
		}
	}

	private void recalculatePositions(int width, int height) {
		logger.info("Recalculating positions for a box " + width + 'x' + height + '.');
		Component component;

		component = nameToComponent.get(CANVAS3D);
		if(component != null) {
			component.setBounds(0, 0, width, height);
		}
	}

	private static final Logger logger = LoggerFactory.createLogger(AnimatorRootLayoutManager.class);
}
