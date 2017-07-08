package hu.csega.games.rotary;

import hu.csega.games.adapters.swing.SwingGameAdapter;
import hu.csega.games.engine.GameEngineBuilder;
import hu.csega.games.engine.intf.GameAdapter;
import hu.csega.games.engine.intf.GameEngineStep;
import hu.csega.games.rotary.play.RotaryRenderingOptions;
import hu.csega.games.rotary.steps.RotaryBuildStep;
import hu.csega.games.rotary.steps.RotaryControlStep;
import hu.csega.games.rotary.steps.RotaryDisposeStep;
import hu.csega.games.rotary.steps.RotaryInitStep;
import hu.csega.games.rotary.steps.RotaryModifyStep;
import hu.csega.games.rotary.steps.RotaryRenderStep;

public class RotaryMain {

	public static void main(String[] args) {
		RotaryRenderingOptions options = new RotaryRenderingOptions();
		options.renderHitShapes = true;

		GameAdapter adapter = new SwingGameAdapter();

		GameEngineBuilder builder = new GameEngineBuilder();
		builder.create(
				"rotary",
				"Rotary",
				"v00.00.0002",
				"Gravitational pull direction for player can be changed dynamically.",
				adapter);

		builder.step(GameEngineStep.INIT, new RotaryInitStep());
		builder.step(GameEngineStep.BUILD, new RotaryBuildStep());
		builder.step(GameEngineStep.CONTROL, new RotaryControlStep());
		builder.step(GameEngineStep.MODIFY, new RotaryModifyStep());
		builder.step(GameEngineStep.RENDER, new RotaryRenderStep(options));
		builder.step(GameEngineStep.DISPOSE, new RotaryDisposeStep());

		builder.startEngine();
	}

}
