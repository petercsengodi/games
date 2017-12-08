package hu.csega.game.asteroid.engine;

import java.awt.Component;

import hu.csega.game.asteroid.AsteroidGameToolImpl;
import hu.csega.games.adapters.opengl.OpenGLCanvas;
import hu.csega.games.engine.intf.GameCanvas;
import hu.csega.games.engine.intf.GameWindow;
import hu.csega.games.engine.intf.GameWindowListener;
import hu.csega.toolshed.framework.ToolWindow;

public class AsteroidGameWindowWrapper implements GameWindow {

	private ToolWindow toolWindow;
	private AsteroidGameToolImpl rush;

	public AsteroidGameWindowWrapper(ToolWindow toolWindow, AsteroidGameToolImpl rush) {
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

		if(canvas instanceof OpenGLCanvas)
			component = ((OpenGLCanvas)canvas).getRealCanvas();

		if(component != null)
			toolWindow.addComponent(component);
	}

	@Override
	public void setFullScreen(boolean fullScreen) {
		this.toolWindow.setFullScreen(fullScreen);
	}

	@Override
	public void repaintEverything() {
	}

	@Override
	public void showWindow() {
	}

	@Override
	public void closeWindow() {
		toolWindow.getAwtWindow().setVisible(false);
	}

	@Override
	public void closeApplication() {
		toolWindow.getAwtWindow().setVisible(false);
		System.exit(0);
	}

}
