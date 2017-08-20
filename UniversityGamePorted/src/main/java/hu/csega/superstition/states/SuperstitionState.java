package hu.csega.superstition.states;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.intf.GameKeyListener;
import hu.csega.games.engine.intf.GameMouseListener;

public class SuperstitionState {

	private GameEngineCallback model;
	private GameEngineCallback builder;
	private GameEngineCallback renderer;
	private GameKeyListener keyListener;
	private GameMouseListener mouseListener;

	public SuperstitionState(
			GameEngineCallback model,
			GameEngineCallback builder,
			GameEngineCallback renderer,
			GameKeyListener keyListener,
			GameMouseListener mouseListener) {
		this.model = model;
		this.builder = builder;
		this.renderer = renderer;
		this.keyListener = keyListener;
		this.mouseListener = mouseListener;
	}

	public GameEngineCallback getModel() {
		return model;
	}

	public void setModel(GameEngineCallback model) {
		this.model = model;
	}

	public GameEngineCallback getBuilder() {
		return builder;
	}

	public void setBuilder(GameEngineCallback builder) {
		this.builder = builder;
	}

	public GameEngineCallback getRenderer() {
		return renderer;
	}

	public void setRenderer(GameEngineCallback renderer) {
		this.renderer = renderer;
	}

	public GameKeyListener getKeyListener() {
		return keyListener;
	}

	public void setKeyListener(GameKeyListener keyListener) {
		this.keyListener = keyListener;
	}

	public GameMouseListener getMouseListener() {
		return mouseListener;
	}

	public void setMouseListener(GameMouseListener mouseListener) {
		this.mouseListener = mouseListener;
	}

}
