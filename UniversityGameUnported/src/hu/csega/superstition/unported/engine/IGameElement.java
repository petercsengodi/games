package hu.csega.superstition.unported.engine;

import hu.csega.superstition.unported.game.IPeriod;
import hu.csega.superstition.unported.game.Model;
import hu.csega.superstition.unported.game.object.IClipping;
import hu.csega.superstition.unported.game.object.IGameObject;

interface IGameElement extends IClipping, IPeriod, IGameObject {
	void setModel(Model model);
}