package hu.csega.games.engine.intf;

import java.awt.Container;

public interface GameWindow {

	void register(GameWindowListener listener);
	void add(GameCanvas canvas, Container container);
	void setFullScreen(boolean fullScreen);
	void showWindow();
	void closeWindow();
	void closeApplication();
	void repaintEverything();

}
