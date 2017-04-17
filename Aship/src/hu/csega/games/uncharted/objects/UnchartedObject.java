package hu.csega.games.uncharted.objects;

import hu.csega.games.engine.GameField;
import hu.csega.games.engine.g2d.GameObject;

public class UnchartedObject extends GameObject {

	public UnchartedObject(GameField gameField) {
		this.gameField = gameField;
	}

	public GameField gameField;
}
