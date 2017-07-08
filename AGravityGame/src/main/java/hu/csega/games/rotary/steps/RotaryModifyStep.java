package hu.csega.games.rotary.steps;

import java.util.Random;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.rotary.play.RotaryModel;

public class RotaryModifyStep implements GameEngineCallback {

	private static final double GAME_SPEED = 5.0; // seconds
	private static final Random RANDOM = new Random(System.currentTimeMillis());

	@Override
	public Object call(GameEngineFacade facade) {

		RotaryModel model = (RotaryModel) facade.model();
		if(model == null)
			return facade;

		model.call(facade);
		return facade;
	}

}
