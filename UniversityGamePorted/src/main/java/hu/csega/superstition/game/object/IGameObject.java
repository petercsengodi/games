package hu.csega.superstition.game.object;

import hu.csega.superstition.game.Engine;
import hu.csega.superstition.game.IPeriod;
import hu.csega.superstition.gamelib.network.GameObjectData;

public interface IGameObject extends IRenderObject, IPeriod {
	/** Reads serializable data. */
	GameObjectData getData();

	void Build(Engine engine);

	/** Pre-Render functionalities. F.e.: Light activation. */
	void preRender();

	/** Post-Render functionalities. F.e.: Light deactivation. */
	void postRender();
}