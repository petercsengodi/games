package hu.csega.game.uncharted.play;

import java.util.List;

import hu.csega.game.engine.GameColor;
import hu.csega.game.engine.GameGraphics;
import hu.csega.game.engine.GameHitShape;
import hu.csega.game.engine.GameObject;
import hu.csega.game.engine.GamePoint;
import hu.csega.game.engine.GameRendering;
import hu.csega.game.uncharted.pics.UnchartedSprites;

public class UnchartedRendering implements GameRendering {

	public UnchartedRendering(UnchartedPhysics physics, UnchartedRenderingOptions options) {
		this.physics = physics;
		this.options = options;
	}

	@Override
	public void render(GameGraphics g) {

		GameColor red = new GameColor(255, 0, 0);
		GameColor green = new GameColor(0, 255, 0);

		GameObject player = physics.player;
		GamePoint position = player.movementPosition;

		if(options.renderHitShapes) {
			g.drawHitShape(player.outerBox, position.x, position.y, green);
		}

		g.drawSprite(UnchartedSprites.SHIP, position.x, position.y);


		if(options.renderHitShapes) {

			List<GameHitShape> hitShapes = player.getHitShapes();
			if(hitShapes != null) {
				for(GameHitShape shape : hitShapes)
					g.drawHitShape(shape, position.x, position.y, red);
			}
		}
	}

	private UnchartedPhysics physics;

	private UnchartedRenderingOptions options;
}
