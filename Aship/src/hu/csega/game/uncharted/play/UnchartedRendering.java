package hu.csega.game.uncharted.play;

import hu.csega.game.engine.GameGraphics;
import hu.csega.game.engine.GameRendering;
import hu.csega.game.uncharted.pics.UnchartedSprites;

public class UnchartedRendering implements GameRendering {

	public UnchartedRendering(UnchartedPhysics physics) {
		this.physics = physics;
	}

	@Override
	public void render(GameGraphics g) {
		// g.crossHair(physics.x, physics.y);
		g.drawSprite(UnchartedSprites.SHIP, physics.x, physics.y);
	}

	private UnchartedPhysics physics;

}
