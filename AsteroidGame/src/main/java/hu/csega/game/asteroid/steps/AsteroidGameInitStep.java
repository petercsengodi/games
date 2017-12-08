package hu.csega.game.asteroid.steps;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import hu.csega.game.asteroid.model.AsteroidGameModel;
import hu.csega.game.asteroid.model.AsteroidGameState;
import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class AsteroidGameInitStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		AsteroidGameModel model = new AsteroidGameModel();
		model.setGameState(new AsteroidGameState());
		facade.setModel(model);

		GameModelStore store = facade.store();
		GameObjectHandler shipTexture = store.loadTexture("res/textures/red.png");
		GameObjectHandler ship = builderFrom(store, "res/ftm/heart.ftm", shipTexture);
		model.setShipModel(ship);

		return facade;
	}

	public GameObjectHandler builderFrom(GameModelStore store, String filename, GameObjectHandler texture) {
		return null;
	}

	public static byte[] readAllBytes(String fileName) {
		File file = new File(fileName);
		return readAllBytes(file);
	}

	public static byte[] readAllBytes(File file) {
		int size = (int)file.length();
		if(size == 0) {
			logger.error("Zero sized file: " + file.getName());
			return null;
		}

		byte[] ret = new byte[size];
		byte[] array = new byte[2000];
		int pos = 0;
		int read;

		try (InputStream ios = new FileInputStream(file)) {
			while ( (read = ios.read(array, 0, 2000)) >= 0 ) {
				if(read == 0)
					continue;

				if(pos + read > size) {
					logger.error("Invalid sized file: " + file.getName());
					return null;
				}

				System.arraycopy(array, 0, ret, pos, read);
				pos += read;
			}
		} catch(IOException ex) {
			logger.error("Error during reading file: " + file.getName(), ex);
			return null;
		}

		return ret;
	}

	private static final Logger logger = LoggerFactory.createLogger(AsteroidGameInitStep.class);
}
