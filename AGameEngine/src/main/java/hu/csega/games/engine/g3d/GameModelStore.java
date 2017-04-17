package hu.csega.games.engine.g3d;

public interface GameModelStore {

	GameObjectHandler loadTexture(String filename);

	GameObjectHandler buildModel(GameModelBuilder builder);

	GameObjectHandler loadModel(String filename);

	GameObjectHandler loadAnimation(String filename);

	void dispose(GameObjectHandler handler);

	void disposeAll();

}
