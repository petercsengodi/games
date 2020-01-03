package hu.csega.games.engine;

import hu.csega.games.engine.impl.GameEngine;
import hu.csega.games.engine.intf.GameAdapter;
import hu.csega.games.engine.intf.GameDescriptor;
import hu.csega.games.engine.intf.GameEngineStep;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

/**
 * This class encapsulated the steps of building up a new engine instance.
 */
public class GameEngineBuilder {

	private boolean engineStarted = false;
	private GameEngine engine;

	/**
	 * Creates a new Game Engine instance as registers it as the
	 * commonly available engine.
	 */
	public GameEngineBuilder create(String id, String title, String version, String description,
			GameAdapter adapter) {

		GameDescriptor descriptor = new GameDescriptor();
		descriptor.setId(id);
		descriptor.setTitle(title);
		descriptor.setVersion(version);
		descriptor.setDescription(description);

		engine = GameEngine.create(descriptor, adapter);

		return this;
	}

	/**
	 * Registers a new step callback for the current engine.
	 */
	public GameEngineBuilder step(String stepName, GameEngineCallback callback) {
		GameEngineStep step = GameEngineStep.valueOf(stepName);
		return step(step, callback);
	}

	/**
	 * Registers a new step callback for the current engine.
	 */
	public GameEngineBuilder step(GameEngineStep step, GameEngineCallback callback) {
		engine.step(step, callback);
		return this;
	}

	public void startEngine() {
		if(!engineStarted) {

			if(engine == null) {
				logger.error("Game Engine wasn't created!");
			} else {
				// TODO not nice this way
				engine.startInNewWindow();
			}

			engineStarted = true;

		} else {

			logger.error("Game Engine already started!");

		}
	}

	private static final Logger logger = LoggerFactory.createLogger(GameEngineBuilder.class);
}
