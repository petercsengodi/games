package hu.csega.games.engine.intf;

public interface GameWindow {

	void register(GameWindowListener listener);
	void add(GameCanvas canvas);
	void setFullScreen(boolean fullScreen);
	void showWindow();
	void closeWindow();

}
