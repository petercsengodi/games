package hu.csega.superstition.states;

import hu.csega.games.engine.GameEngineCallback;

public class SuperstitionState {

	public SuperstitionState(GameEngineCallback model, GameEngineCallback renderer) {
		this.model = model;
		this.renderer = renderer;
	}

	public GameEngineCallback getModel() {
		return model;
	}

	public void setModel(GameEngineCallback model) {
		this.model = model;
	}

	public GameEngineCallback getRenderer() {
		return renderer;
	}

	public void setRenderer(GameEngineCallback renderer) {
		this.renderer = renderer;
	}

	private GameEngineCallback model;
	private GameEngineCallback renderer;

}
