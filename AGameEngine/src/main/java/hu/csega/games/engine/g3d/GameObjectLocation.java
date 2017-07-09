package hu.csega.games.engine.g3d;

public class GameObjectLocation {

	public GameObjectPosition position = new GameObjectPosition();
	public GameObjectDirection forward = new GameObjectDirection();
	public GameObjectDirection up = new GameObjectDirection();

	public void copyValuesFrom(GameObjectLocation other) {
		position.copyValuesFrom(other.position);
		forward.copyValuesFrom(other.forward);
		up.copyValuesFrom(other.up);
	}

}
