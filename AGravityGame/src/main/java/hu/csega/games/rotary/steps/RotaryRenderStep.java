package hu.csega.games.rotary.steps;

import java.util.List;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g2d.GameColor;
import hu.csega.games.engine.g2d.GameHitShape;
import hu.csega.games.engine.g2d.GamePoint;
import hu.csega.games.engine.intf.GameGraphics;
import hu.csega.games.rotary.objects.RotaryMapElement;
import hu.csega.games.rotary.objects.RotaryPlayer;
import hu.csega.games.rotary.play.RotaryModel;
import hu.csega.games.rotary.play.RotaryRenderingOptions;

public class RotaryRenderStep implements GameEngineCallback {

	private static final int TILE_WIDTH_AND_HEIGHT = 64;

	private RotaryRenderingOptions options;

	public RotaryRenderStep(RotaryRenderingOptions options) {
		this.options = options;
	}

	@Override
	public Object call(GameEngineFacade facade) {

		RotaryModel model = (RotaryModel) facade.model();
		if(model == null)
			return facade;

		GameColor red = new GameColor(255, 0, 0);
		GameColor black = new GameColor(0, 0, 0);

		RotaryPlayer player = model.getPlayer();
		GamePoint position = player.movementPosition;

		GameGraphics g = facade.graphics();
		g.rotate(player.gravitationalPull);
		g.translate(-position.x, -position.y);

		if(options.renderHitShapes) {
			g.drawHitShape(player.outerBox, position.x, position.y, red);
		}

		for(RotaryMapElement mapElement : model.getMapElements()) {
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

		return facade;
	}

}
