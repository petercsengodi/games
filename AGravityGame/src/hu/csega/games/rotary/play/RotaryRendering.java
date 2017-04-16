package hu.csega.games.rotary.play;

import java.util.List;

import hu.csega.games.engine.GameColor;
import hu.csega.games.engine.GameGraphics;
import hu.csega.games.engine.GameHitShape;
import hu.csega.games.engine.GamePoint;
import hu.csega.games.engine.GameRendering;
import hu.csega.games.rotary.objects.RotaryMapElement;
import hu.csega.games.rotary.objects.RotaryPlayer;

public class RotaryRendering implements GameRendering {

	public RotaryRendering(RotaryRenderingOptions options) {
		this.options = options;
	}

	@Override
	public void render(GameGraphics g) {
		GameColor red = new GameColor(255, 0, 0);
		GameColor black = new GameColor(0, 0, 0);

		RotaryPlayer player = universe.player;
		GamePoint position = player.movementPosition;

		g.rotate(player.gravitationalPull);
		g.translate(-position.x, -position.y);

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
