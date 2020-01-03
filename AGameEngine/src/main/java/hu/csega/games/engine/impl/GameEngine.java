package hu.csega.games.engine.impl;

import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.env.Disposable;
import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.games.engine.intf.GameAdapter;
import hu.csega.games.engine.intf.GameCanvas;
import hu.csega.games.engine.intf.GameControl;
import hu.csega.games.engine.intf.GameDescriptor;
import hu.csega.games.engine.intf.GameEngineStep;
import hu.csega.games.engine.intf.GameGraphics;
import hu.csega.games.engine.intf.GameThread;
import hu.csega.games.engine.intf.GameTimer;
import hu.csega.games.engine.intf.GameWindow;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class GameEngine implements Disposable {

	public static GameEngine create(GameDescriptor descriptor, GameAdapter adapter) {
		GameEngine engine = new GameEngine();
		engine.descriptor = descriptor;
		engine.adapter = adapter;
		engine.control = new GameControlImpl(engine);
		return engine;
	}

	private boolean initialized;
	private boolean rebuildRequested;

	private GameDescriptor descriptor;
	private GameAdapter adapter;
	private GameWindow window;
	private GameCanvas canvas;
	private GameThread thread;
	private GameControl control;
	private GameModelStore store;
	private GameTimer timer;

	private Object model;
	private GameEngineFacade facade;
	private GameGraphics graphics;

	private Map<GameEngineStep, List<GameEngineCallback>> callbacks;

	private GameEngine() {
		facade = new GameEngineFacadeImpl(this);
		callbacks = new HashMap<>();

		for(GameEngineStep step : GameEngineStep.values()) {
			List<GameEngineCallback> functions = new ArrayList<GameEngineCallback>();
			callbacks.put(step, functions);
		}

		graphics = null;
		initialized = false;
		rebuildRequested = true;
	}

	public void startInNewWindow() {
		// TODO not nice this way.
		GameWindow gameWindow = adapter.createWindow(this);
		gameWindow.setFullScreen(true);
		startIn(gameWindow, ((JFrame)gameWindow).getContentPane());
	}

	public void startIn(GameWindow gameWindow, Container container) {
		this.window = gameWindow;
		this.store = adapter.createStore(this);
		this.canvas = adapter.createCanvas(this);

		if(!initialized) {
			logger.info("Running initialization.");
			runStep(GameEngineStep.INIT, null);
			initialized = true;
		}

		this.thread = new GameThreadImpl(this);
		thread.start();

		window.add(canvas, container);
		window.register(new DefaultGameWindowListener(this.thread, this));
		window.showWindow();
	}

	public boolean isRebuildRequested() {
		return rebuildRequested;
	}

	public GameEngineFacade requestRebuild() {
		rebuildRequested = true;
		return getFacade();
	}

	public GameDescriptor getDescriptor() {
		return descriptor;
	}

	public GameCanvas getCanvas() {
		return canvas;
	}

	public GameControl getControl() {
		return control;
	}

	public GameWindow getWindow() {
		return window;
	}

	public GameEngineFacade getFacade() {
		return facade;
	}

	public GameEngine step(String stepName, GameEngineCallback callback) {
		GameEngineStep step = GameEngineStep.valueOf(stepName);
		return step(step, callback);
	}

	public GameEngine step(GameEngineStep step, GameEngineCallback callback) {
		callbacks.get(step).add(callback);
		return this;
	}

	public GameEngine removeStep(String stepName, GameEngineCallback callback) {
		GameEngineStep step = GameEngineStep.valueOf(stepName);
		return removeStep(step, callback);
	}

	public GameEngine removeStep(GameEngineStep step, GameEngineCallback callback) {
		callbacks.get(step).remove(callback);
		return this;
	}

	public synchronized GameEngine runStep(GameEngineStep step, Object object) {
		if(step == GameEngineStep.RENDER) {
			graphics = (GameGraphics) object;
		}

		if(step == GameEngineStep.MODIFY) {
			timer = (GameTimer) object;
		}

		List<GameEngineCallback> list = callbacks.get(step);
		for(GameEngineCallback callback : list) {
			callback.call(facade);
		}

		if(step == GameEngineStep.BUILD) {
			rebuildRequested = false;
		}

		graphics = null;
		timer = null;
		return this;
	}

	public GameGraphics getGraphics() {
		if(graphics == null) {
			throw new IllegalStateException("No graphics currently!");
		}
		return graphics;
	}

	public GameModelStore getStore() {
		return store;
	}

	public GameTimer getTimer() {
		if(timer == null) {
			throw new IllegalStateException("No timer currently!");
		}
		return timer;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}

	@Override
	public void dispose() {
		if(canvas != null)
			canvas.dispose();
	}

	private static final Logger logger = LoggerFactory.createLogger(GameEngine.class);

}
