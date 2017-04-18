package hu.csega.games.engine;

public interface GameWindow {

	void register(GameWindowListener listener);
	void add(GameCanvas canvas);
	void showWindow();
	void closeWindow();

}
