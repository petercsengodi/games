package hu.csega.game.rotary.objects;

import hu.csega.game.engine.GameField;
import hu.csega.game.engine.GameHitCircle;
import hu.csega.game.engine.GameVector;

public class RotaryPlayer extends RotaryObject {

	public RotaryPlayer(GameField gameField) {
		super(gameField);

		hitShapes.add(new GameHitCircle(0, 0, 50));
		outerBox.minX = -50;
		outerBox.minY = -50;
		outerBox.maxX = 50;
		outerBox.maxY = 50;
	}

	@Override
	public void checkConstraints() {

		// ??? collision detection?
	}

	public GameVector gravitationalPull = new GameVector();

}
