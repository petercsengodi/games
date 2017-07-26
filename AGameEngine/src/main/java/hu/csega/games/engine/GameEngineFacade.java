package hu.csega.games.engine;

import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.games.engine.intf.GameControl;
import hu.csega.games.engine.intf.GameGraphics;
import hu.csega.games.engine.intf.GameTimer;
import hu.csega.games.engine.intf.GameWindow;
import hu.csega.toolshed.logging.Logger;

/**
 * Everything that matters during running the engine is here.
 */
public interface GameEngineFacade {

	/**
	 * Gets the model object registered in the engine.
	 */
	Object model();

	/**
	 * Asks for new re-run of the BUILD step.
	 */
	GameEngineFacade requestRebuild();

	/**
	 * @return Returns the input parameter.
	 */
	Object setModel(Object model);

	/**
	 * Gets the graphics interface for rendering.
	 * Only available during rendering step, otherwise an exception will be thrown.
	 */
	GameGraphics graphics();

	/**
	 * Gets the model store.
	 */
	GameModelStore store();

	/**
	 * Gets the game control interface.
	 */
	GameControl control();

	/**
	 * Gets the main frame / window, if applicable.
	 */
	GameWindow window();

	/**
	 * Gets the timer interface.
	 * Only available in the modifying step, otherwise this method throws an exception.
	 */
	GameTimer timer();

	/**
	 * Gets the common logger object.
	 */
	Logger logger();

}
