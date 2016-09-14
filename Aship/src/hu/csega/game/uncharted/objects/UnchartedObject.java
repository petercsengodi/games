package hu.csega.game.uncharted.objects;

import hu.csega.game.engine.GameField;
import hu.csega.game.engine.GameObject;

public class UnchartedObject extends GameObject {

	public UnchartedObject(GameField gameField) {
		this.gameField = gameField;
	}

	public GameField gameField;
}
