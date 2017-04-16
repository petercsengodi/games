package hu.csega.superstition.engine;

import hu.csega.superstition.game.IPeriod;
import hu.csega.superstition.game.Model;
import hu.csega.superstition.game.object.IClipping;
import hu.csega.superstition.game.object.IGameObject;

interface IGameElement extends IClipping, IPeriod, IGameObject {
	void setModel(Model model);
}