package hu.csega.games.uncharted.objects;

import hu.csega.games.engine.GameField;
import hu.csega.games.engine.GameHitBox;

public class UnchartedPlayerBullet extends UnchartedObject {

	public UnchartedPlayerBullet(GameField gameField) {
		super(gameField);

		hitShapes.add(new GameHitBox(0, 100, 0, 30));
		outerBox.minX = 0;
		outerBox.minY = 0;
		outerBox.maxX = 100;
		outerBox.maxY = 30;
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
