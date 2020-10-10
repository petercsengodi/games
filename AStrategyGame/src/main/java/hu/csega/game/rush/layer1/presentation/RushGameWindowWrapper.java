package hu.csega.game.rush.layer1.presentation;

import java.awt.Component;
import java.awt.Container;

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

	public Container getContainer() {
		return this.toolWindow.getAwtWindow();
	}

	@Override
	public void register(GameWindowListener listener) {
		rush.registerGameWindowListener(listener);
	}

	@Override
	public void add(GameCanvas canvas, Container container) {
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
