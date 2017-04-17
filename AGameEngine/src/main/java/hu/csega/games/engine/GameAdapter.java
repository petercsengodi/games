package hu.csega.games.engine;

import hu.csega.games.engine.g3d.GameModelStore;

public interface GameAdapter {

	GameWindow createWindow(GameEngine engine);
	GameCanvas createCanvas(GameEngine engine);
	GameThread createThread(GameEngine engine);
	GameModelStore createStore(GameEngine engine);

}
