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
	public static final String PARTS_LIST = "partsList";
	public static final String PARTS_SETTINGS = "partsSettings";
	public static final String CORNER_CONTROLLER = "cornerController";
	public static final String SCENE_EDITOR = "sceneEditor";

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

		int w20 = (int)(width * 0.2);
		int w40 = (int)(width * 0.4);
		int w60 = (int)(width * 0.6);

		int h20 = (int)(height * 0.2);
		int h80 = (int)(height * 0.8);

		component = nameToComponent.get(CANVAS3D);
		if(component != null) {
			component.setBounds(w40, 0, w60, h80);
		}

		component = nameToComponent.get(PARTS_LIST);
		if(component != null) {
			component.setBounds(0, 0, w20, h80);
		}

		component = nameToComponent.get(PARTS_SETTINGS);
		if(component != null) {
			component.setBounds(w20, 0, w20, h80);
		}

		component = nameToComponent.get(CORNER_CONTROLLER);
		if(component != null) {
			component.setBounds(0, h80, w40, h20);
		}

		component = nameToComponent.get(SCENE_EDITOR);
		if(component != null) {
			component.setBounds(w40, h80, w60, h20);
		}
	}

	private static final Logger logger = LoggerFactory.createLogger(AnimatorRootLayoutManager.class);
}
