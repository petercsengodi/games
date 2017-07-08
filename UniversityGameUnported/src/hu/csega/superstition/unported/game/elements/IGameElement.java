package hu.csega.superstition.unported.game.elements;

import hu.csega.superstition.game.object.IClipping;
import hu.csega.superstition.game.object.IGameObject;
import hu.csega.superstition.unported.game.IPeriod;
import hu.csega.superstition.unported.game.Model;

interface IGameElement extends IClipping, IPeriod, IGameObject {
	void setModel(Model model);
}