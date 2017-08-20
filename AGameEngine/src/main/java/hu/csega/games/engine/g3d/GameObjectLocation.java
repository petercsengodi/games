package hu.csega.games.engine.g3d;

public class GameObjectLocation {

	public GameObjectPosition position = new GameObjectPosition();
	public GameObjectRotation rotation = new GameObjectRotation();

	public void copyValuesFrom(GameObjectLocation other) {
		position.copyValuesFrom(other.position);
		rotation.copyValuesFrom(other.rotation);
	}

}
