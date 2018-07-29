package hu.csega.editors.anm.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.HashMap;
import java.util.Map;

import com.jogamp.opengl.awt.GLCanvas;

public class AnimatorContentPaneLayout implements LayoutManager {

	private Map<String, Component> components = new HashMap<>();
	private Map<Component, String> names = new HashMap<>();

	@Override
	public void addLayoutComponent(String name, Component component) {
		if(name == null)
			name = selectNameForComponent(component);

		components.put(name, component);
		names.put(component, name);
	}

	@Override
	public void removeLayoutComponent(Component component) {
		String name = names.remove(component);
		if(name != null)
			components.remove(name);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return PREFERRED_LAYOUT_SIZE;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return PREFERRED_LAYOUT_SIZE;
	}

	@Override
	public void layoutContainer(Container parent) {
		Dimension containerSize = parent.getSize();

		for(Component component : parent.getComponents()) {

			String name = names.get(component);
			if(name == null) {
				name = selectNameForComponent(component);
				components.put(name, component);
				names.put(component, name);
			}

			resizeComponent(name, component, containerSize);
		}
	}

	private String selectNameForComponent(Component component) {
		if(component instanceof GLCanvas && !components.containsKey(CANVAS3D))
			return CANVAS3D;

		return "unidentified-" + System.currentTimeMillis();
	}

	private void resizeComponent(String name, Component component, Dimension containerSize) {
		switch (name) {
		case FAR_LEFT_PANEL: {
			int height = (int)containerSize.getHeight();
			component.setBounds(0, 0, LEFT_PANEL_WIDTH, height);
			break;
		}
		case CANVAS3D: {
			int width = (int)((containerSize.getWidth() - LEFT_PANEL_WIDTH) / 2);
			int left = (int)(containerSize.getWidth() - width);
			int height = (int)(containerSize.getHeight() / 2);
			component.setBounds(left, 0, width, height);
			break;
		}
		default:
			break;
		}
	}

	public static final String FAR_LEFT_PANEL = "farLeftPanel";
	public static final String UPPER_LEFT = "upperLeft";
	public static final String UPPER_RIGHT = "upperRight";
	public static final String LOWER_LEFT = "lowerLeft";
	public static final String LOWER_RIGHT = "lowerRight";
	public static final String CANVAS3D = "canvas3d";

	private static final int LEFT_PANEL_WIDTH = 200;

	private static final Dimension PREFERRED_LAYOUT_SIZE = new Dimension(800, 500);
}
