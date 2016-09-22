package hu.csega.pixel.fighter.pix;

import hu.csega.pixel.PixelLibrary;
import hu.csega.pixel.PixelSpriteResizePolicy;
import hu.csega.pixel.SpriteLibrary;

public class PixelFighterSprites {

	private static final int ZOOM = 4;

	public static SpriteLibrary PLAYER;

	static {
		PLAYER = SpriteLibrary.create(PixelLibrary.load(PixelFighterSprites.class, "player.pix"),
				ZOOM, PixelSpriteResizePolicy.ONLY_USED_AREA);
	}
}
