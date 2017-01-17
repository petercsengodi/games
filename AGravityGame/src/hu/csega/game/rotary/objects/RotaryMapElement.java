package hu.csega.game.rotary.objects;

import hu.csega.game.engine.GameField;
import hu.csega.game.engine.GameHitBox;

public class RotaryMapElement extends RotaryObject {

	public RotaryMapElement(GameField gameField) {
		super(gameField);

		hitShapes.add(new GameHitBox(-50, -50, 50, 50));
		outerBox.minX = -50;
		outerBox.minY = -50;
		outerBox.maxX = 50;
		outerBox.maxY = 50;
	}

	@Override
	public void checkConstraints() {

		// ??? collision detection?
	}
}