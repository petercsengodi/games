package hu.csega.game.rotary.objects;

import hu.csega.game.engine.GameField;
import hu.csega.game.engine.GameObject;

public class RotaryObject extends GameObject {

	public RotaryObject(GameField gameField) {
		this.gameField = gameField;
	}

	public GameField gameField;
}
