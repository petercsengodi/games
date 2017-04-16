package hu.csega.game.rotary.objects;

import hu.csega.games.engine.GameField;
import hu.csega.games.engine.GameObject;

public class RotaryObject extends GameObject {

	public RotaryObject(GameField gameField) {
		this.gameField = gameField;
	}

	public GameField gameField;
}
