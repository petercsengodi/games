package hu.csega.superstition.game.object;

import org.joml.Vector3f;

import hu.csega.superstition.gamelib.network.GameObjectData;
import hu.csega.superstition.storygenerator.Room;
import hu.csega.superstition.unported.game.Engine;

public abstract class DynamicObject extends Clipable implements IGameObject {

	protected boolean alive;

	public boolean isAlive() {
		return alive;
	}

	// Others
	public Room CurrentRoom = null;
	protected Engine engine; // !! Only for On-Line building

	public DynamicObject(Vector3f _position, Vector3f _corner1, Vector3f _corner2) {
		super(_position, _corner1, _corner2);
		alive = true;
	}

	public DynamicObject() {
		super(new Vector3f(), new Vector3f(), new Vector3f());
		alive = true;
	}

	public abstract void render();

	/// <summary>
	/// Preparation of rendeing. For example: light activation.
	/// </summary>
	@Override
	public void preRender() {
	}

	/// <summary>
	/// Post functions of rendeing. For example: light deactivation.
	/// </summary>
	@Override
	public void postRender() {
	}

	@Override
	public abstract GameObjectData getData();

	@Override
	public abstract void Build(Engine engine);

	public void period() {
		float deltat = 0.04f;
		position.add(diff, position);
		velocity.mul(deltat, diff);
	}
}