package hu.csega.games.pixel.fighter;

import java.awt.image.BufferedImage;

import hu.csega.games.engine.GameRendering;
import hu.csega.games.engine.impl.GameGraphics;
import hu.csega.games.engine.impl.GameObject;
import hu.csega.games.engine.impl.GamePoint;
import hu.csega.games.pixel.fighter.pix.PixelFighterSprites;

public class PixelFighterRendering implements GameRendering {

	public PixelFighterRendering(PixelFighterPhysics physics) {
		this.physics = physics;
	}

	@Override
	public void render(GameGraphics g) {
		GameObject player = physics.getPlayer();
		GamePoint position = player.movementPosition;

		int index = (physics.punch() ? 1 : 0);

		BufferedImage image = PixelFighterSprites.PLAYER.get(index);
		g.drawSprite(image, position.x, position.y - image.getHeight());
	}

	private PixelFighterPhysics physics;
}
