package hu.csega.games.pixel.fighter.engine;

import java.awt.image.BufferedImage;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g2d.GameObject;
import hu.csega.games.engine.g2d.GamePoint;
import hu.csega.games.engine.intf.GameControl;
import hu.csega.games.engine.intf.GameGraphics;
import hu.csega.games.pixel.fighter.pix.PixelFighterSprites;

public class PixelFighterRenderingStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		PixelFighterModel model = (PixelFighterModel) facade.model();
		if(model == null)
			return facade;

		GameControl control = facade.control();

		GameObject player = model.getPlayer();
		GamePoint position = player.movementPosition;

		int index = (control.isControlOn() ? 1 : 0);

		GameGraphics g = facade.graphics();
		BufferedImage image = PixelFighterSprites.PLAYER.get(index);
		g.drawSprite(image, position.x, position.y - image.getHeight());

		return facade;
	}
}
