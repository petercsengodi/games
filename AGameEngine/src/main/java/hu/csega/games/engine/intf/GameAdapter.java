package hu.csega.games.engine.intf;

import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.games.engine.impl.GameEngine;

public interface GameAdapter {

	GameWindow createWindow(GameEngine engine);
	GameCanvas createCanvas(GameEngine engine);
	GameModelStore createStore(GameEngine engine);

}
