package hu.csega.games.uncharted.objects;

import hu.csega.games.engine.g2d.GameObject;

public class UnchartedObject extends GameObject {

	public UnchartedObject(UnchartedField gameField) {
		this.gameField = gameField;
	}

	public UnchartedField gameField;
}
