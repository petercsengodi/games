package hu.csega.games.rotary.objects;

import hu.csega.games.engine.GameField;
import hu.csega.games.engine.g2d.GameObject;

public class RotaryObject extends GameObject {

	public RotaryObject(GameField gameField) {
		this.gameField = gameField;
	}

	public GameField gameField;
}
