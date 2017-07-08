package hu.csega.games.pixel.fighter;

import hu.csega.games.adapters.swing.SwingGameAdapter;
import hu.csega.games.engine.impl.GameEngine;
import hu.csega.games.engine.intf.GameAdapter;
import hu.csega.games.engine.intf.GameDescriptor;
import hu.csega.games.engine.intf.GameEngineStep;
import hu.csega.games.pixel.fighter.engine.PixelFighterInitStep;
import hu.csega.games.pixel.fighter.engine.PixelFighterRenderingStep;

public class PixelFighterMain {

	public static void main(String[] args) {
		GameDescriptor descriptor = new GameDescriptor();
		descriptor.setId("pixelFighter");
		descriptor.setTitle("Pixel Fighter");
		descriptor.setVersion("v00.00.0001");
		descriptor.setDescription("Fighter game based on pixel graphics.");

		GameAdapter adapter = new SwingGameAdapter();

		GameEngine engine = GameEngine.create(descriptor, adapter);
		engine.step(GameEngineStep.INIT, new PixelFighterInitStep());
		engine.step(GameEngineStep.RENDER, new PixelFighterRenderingStep());
		engine.startInNewWindow();
	}

}
