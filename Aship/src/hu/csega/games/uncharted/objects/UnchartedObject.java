package hu.csega.games.uncharted.objects;

import hu.csega.games.engine.GameField;
import hu.csega.games.engine.impl.GameObject;

public class UnchartedObject extends GameObject {

	public UnchartedObject(GameField gameField) {
		this.gameField = gameField;
	}

	public GameField gameField;
}
