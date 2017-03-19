package hu.csega.superstition.game.object;

import org.joml.Vector3f;

import hu.csega.superstition.game.Engine;
import hu.csega.superstition.gamelib.network.GameObjectData;

public abstract class MapObject extends Clipper implements IGameObject {

	public MapObject(Vector3f corner1, Vector3f corner2) {
		super(corner1, corner2);
	}

	@Override
	public abstract void Render();

	@Override
	public abstract GameObjectData getData();

	@Override
	public abstract void Build(Engine engine);

	public void PreRender() {
	}

	public void PostRender() {
	}

	@Override
	public void Period() {
	}

}
