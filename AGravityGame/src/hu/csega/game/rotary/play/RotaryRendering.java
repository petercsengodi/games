package hu.csega.game.rotary.play;

import java.awt.Color;
import java.util.List;

import hu.csega.game.engine.GameColor;
import hu.csega.game.engine.GameGraphics;
import hu.csega.game.engine.GameHitShape;
import hu.csega.game.engine.GameObject;
import hu.csega.game.engine.GamePoint;
import hu.csega.game.engine.GameRendering;
import hu.csega.game.rotary.objects.RotaryMapElement;

public class RotaryRendering implements GameRendering {

	public RotaryRendering(RotaryRenderingOptions options) {
		this.options = options;
	}

	@Override
	public void render(GameGraphics g) {

		GameColor red = new GameColor(255, 0, 0);
		GameColor black = new GameColor(0, 0, 0);

		GameObject player = universe.player;
		GamePoint position = player.movementPosition;

		if(options.renderHitShapes) {
			g.drawHitShape(player.outerBox, position.x, position.y, red);
		}

		for(RotaryMapElement mapElement : universe.mapElements) {
			GamePoint p = mapElement.movementPosition;
			g.drawHitShape(mapElement.outerBox, p.x, p.y, black);
		}

		if(options.renderHitShapes) {

			List<GameHitShape> hitShapes = player.getHitShapes();
			if(hitShapes != null) {
				for(GameHitShape shape : hitShapes)
					g.drawHitShape(shape, position.x, position.y, red);
			}
		}
	}

	public RotaryUniverse universe;

	private RotaryRenderingOptions options;
}
