package hu.csega.games.engine;

import java.awt.Component;

public interface GameAdapter {

	GameControl createWindow(GameEngine engine);
	Component createCanvas(GameEngine engine);

}
