package hu.csega.games.uncharted.objects;

import hu.csega.games.engine.GameField;
import hu.csega.games.engine.GameHitBox;

public class UnchartedEnemy extends UnchartedObject {

	public UnchartedEnemy(GameField gameField) {
		super(gameField);

		hitShapes.add(new GameHitBox(0, 50, 0, 40));
		outerBox.minX = 0;
		outerBox.minY = 0;
		outerBox.maxX = 50;
		outerBox.maxY = 40;
	}

	@Override
	public void checkConstraints() {

		if(this.movementPosition.x < this.gameField.minX + outerBox.minX) {
			this.movementPosition.x = this.gameField.minX + outerBox.minX;
			if(this.movementSpeed.x < 0)
				this.movementSpeed.x = 0;
		}

		if(this.movementPosition.x > this.gameField.maxX - outerBox.maxX) {
			this.movementPosition.x = this.gameField.maxX - outerBox.maxX;
			if(this.movementSpeed.x > 0)
				this.movementSpeed.x = 0;
		}

		if(this.movementPosition.y < this.gameField.minY + outerBox.minY) {
			this.movementPosition.y = this.gameField.minY + outerBox.minY;
			if(this.movementSpeed.y < 0)
				this.movementSpeed.y = 0;
		}

		if(this.movementPosition.y > this.gameField.maxY - outerBox.maxY) {
			this.movementPosition.y = this.gameField.maxY - outerBox.maxY;
			if(this.movementSpeed.y > 0)
				this.movementSpeed.y = 0;
		}

	}


}
