package hu.csega.game.uncharted.play;

import hu.csega.game.engine.GameGraphics;
import hu.csega.game.engine.GameRendering;

public class UnchartedRendering implements GameRendering {

	public UnchartedRendering(UnchartedPhysics physics) {
		this.physics = physics;
	}

	@Override
	public void render(GameGraphics g) {
		g.crossHair(physics.x, physics.y);

	}

	private UnchartedPhysics physics;

}
