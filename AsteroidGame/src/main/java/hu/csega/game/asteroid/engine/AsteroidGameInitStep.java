package hu.csega.game.asteroid.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.joml.Vector2f;
import org.joml.Vector4f;
import hu.csega.game.asteroid.model.AsteroidGameModel;
import hu.csega.game.asteroid.model.AsteroidGameState;
import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameModelBuilder;
import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.games.engine.g3d.GameObjectDirection;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectPosition;
import hu.csega.games.engine.g3d.GameObjectVertex;
import hu.csega.games.engine.g3d.GameTexturePosition;
import hu.csega.games.library.model.mesh.SMesh;
import hu.csega.games.library.model.mesh.SShape;
import hu.csega.games.library.model.mesh.STriangle;
import hu.csega.games.library.model.mesh.SVertex;
import hu.csega.games.library.xml.XmlReader;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class AsteroidGameInitStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		AsteroidGameModel model = new AsteroidGameModel();
		model.setGameState(new AsteroidGameState());
		facade.setModel(model);

		GameModelStore store = facade.store();
		GameObjectHandler ship = builderFrom(store, "res/meshes/ship.mesh");
		model.setShipModel(ship);

		return facade;
	}

	public GameObjectHandler builderFrom(GameModelStore store, String filename) {
		SMesh mesh;

		try {
			mesh = (SMesh)XmlReader.read(filename);
		} catch (Exception ex) {
			throw new RuntimeException("Could not load file: " + filename, ex);
		}

		// TODO: this can handle only one figure
		SShape shape = mesh.getFigures().get(0);

		GameObjectHandler texture = store.loadTexture(shape.getTexture().getName());
		GameModelBuilder builder = new GameModelBuilder();
		builder.setTextureHandler(texture);


		// TODO: as first approach, we did not optimize anything with indices
		// and other properties may be filled out by the editor
		int index = 0;
		for(STriangle triangle : shape.getTriangles()) {
			for(SVertex vertex : triangle.getVertices()) {
				Vector4f vp = vertex.getPosition();
				Vector2f vt = vertex.getTextureCoordinates();

				GameObjectPosition p = new GameObjectPosition(vp.x, vp.y, vp.z);
				GameObjectDirection d = new GameObjectDirection(0f, 1f, 0f);
				GameTexturePosition tex = new GameTexturePosition(vt.x, vt.y);
				builder.getVertices().add(new GameObjectVertex(p, d, tex));

				builder.getIndices().add(index++);
			}
		}

		return store.buildModel(builder);
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
