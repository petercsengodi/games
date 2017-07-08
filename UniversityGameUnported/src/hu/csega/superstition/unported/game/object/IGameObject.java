package hu.csega.superstition.game.object;

import hu.csega.superstition.gamelib.network.GameObjectData;
import hu.csega.superstition.unported.game.Engine;
import hu.csega.superstition.unported.game.IPeriod;

public interface IGameObject extends IRenderObject, IPeriod {
	/** Reads serializable data. */
	GameObjectData getData();

	void Build(Engine engine);

	/** Pre-Render functionalities. F.e.: Light activation. */
	void preRender();

	/** Post-Render functionalities. F.e.: Light deactivation. */
	void postRender();
}