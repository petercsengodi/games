package hu.csega.games.engine.g3d;

public class GameObjectLocation {

	public void copyValuesFrom(GameObjectLocation other) {
		position.copyValuesFrom(other.position);
		forward.copyValuesFrom(other.forward);
		up.copyValuesFrom(other.up);
	}

	public GameObjectPosition position = new GameObjectPosition();
	public GameObjectDirection forward = new GameObjectDirection();
	public GameObjectDirection up = new GameObjectDirection();

}
