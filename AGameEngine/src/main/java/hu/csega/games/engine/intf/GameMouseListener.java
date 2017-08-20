package hu.csega.games.engine.intf;

import hu.csega.games.engine.GameEngineFacade;

/**
 * Listener interface for mouse events.
 */
public interface GameMouseListener {

	void pressed(GameEngineFacade facade, int x, int y, boolean leftMouse, boolean rightMouse);
	void released(GameEngineFacade facade, int x, int y, boolean leftMouse, boolean rightMouse);
	void clicked(GameEngineFacade facade, int x, int y, boolean leftMouse, boolean rightMouse);
	void moved(GameEngineFacade facade, int deltaX, int deltaY, boolean leftMouseDown, boolean rightMouseDown);

}
