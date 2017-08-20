package hu.csega.game.rush.engine;

import java.awt.Component;

import hu.csega.game.rush.RushGameToolImpl;
import hu.csega.games.adapters.opengl.OpenGLCanvas;
import hu.csega.games.engine.intf.GameCanvas;
import hu.csega.games.engine.intf.GameWindow;
import hu.csega.games.engine.intf.GameWindowListener;
import hu.csega.toolshed.framework.ToolWindow;

public class RushGameWindowWrapper implements GameWindow {

	private ToolWindow toolWindow;
	private RushGameToolImpl rush;

	public RushGameWindowWrapper(ToolWindow toolWindow, RushGameToolImpl rush) {
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

		//		if(canvas instanceof SwingCanvas)
		//			component = ((SwingCanvas)canvas).getRealCanvas();

		if(component != null)
			toolWindow.addComponent(component);
	}

	@Override
	public void setFullScreen(boolean fullScreen) {
		this.toolWindow.setFullScreen(fullScreen);
	}

	@Override
	public void repaintEverything() {
		// TODO
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
