package hu.csega.games.engine.intf;

import hu.csega.games.engine.GameEngineFacade;

/**
 * Listener interface for checking a special key.
 */
public interface GameKeyListener {

	void hit(GameEngineFacade facade, char key);

}
