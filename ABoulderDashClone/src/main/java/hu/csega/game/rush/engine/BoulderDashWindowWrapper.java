package hu.csega.game.rush.engine;

import java.awt.Component;

import hu.csega.game.boulderdash.BoulderDashToolImpl;
import hu.csega.games.engine.GameCanvas;
import hu.csega.games.engine.GameWindow;
import hu.csega.games.engine.GameWindowListener;
import hu.csega.toolshed.framework.ToolWindow;

public class BoulderDashWindowWrapper implements GameWindow {

	private ToolWindow toolWindow;
	private BoulderDashToolImpl rush;

	public BoulderDashWindowWrapper(ToolWindow toolWindow, BoulderDashToolImpl rush) {
		this.toolWindow = toolWindow;
		this.rush = rush;
	}

	@Override
	public void register(GameWindowListener listener) {
		rush.registerGameWindowListener(listener);
	}

	@Override
	public void add(GameCanvas canvas) {
		Component component = null;
		if(component != null)
			toolWindow.addComponent(component);
	}

	@Override
	public void showWindow() {
	}

	@Override
	public void closeWindow() {
		toolWindow.getAwtWindow().setVisible(false);
	}

}
