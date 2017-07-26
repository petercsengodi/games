package hu.csega.superstition.ftm;

import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import hu.csega.games.adapters.opengl.OpenGLGameAdapter;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.env.Environment;
import hu.csega.games.engine.impl.GameEngine;
import hu.csega.games.engine.intf.GameAdapter;
import hu.csega.games.engine.intf.GameCanvas;
import hu.csega.games.engine.intf.GameDescriptor;
import hu.csega.games.engine.intf.GameEngineStep;
import hu.csega.games.engine.intf.GameWindow;
import hu.csega.games.engine.intf.GameWindowListener;
import hu.csega.superstition.engines.connector.Connector;
import hu.csega.superstition.ftm.view.FreeTriangleMeshXYSideView;
import hu.csega.superstition.ftm.view.FreeTriangleMeshXZSideView;
import hu.csega.superstition.ftm.view.FreeTriangleMeshZYSideView;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class FreeTriangleMeshConnector implements Connector, GameWindow {

	private GameWindow gameWindow;
	private List<GameWindowListener> listeners = new ArrayList<>();

	@Override
	public void run(Environment env) {
		logger.info(className() + " start run()");

		startGameEngine();

		logger.info(className() + " end run()");
	}

	@Override
	public void register(GameWindowListener listener) {
		listeners.add(listener);
	}

	@Override
	public void add(GameCanvas canvas) {
	}

	@Override
	public void setFullScreen(boolean fullScreen) {
	}

	@Override
	public void showWindow() {
	}

	@Override
	public void closeWindow() {
		for(GameWindowListener listener: listeners) {
			listener.onFinishingWork();
		}
	}

	@Override
	public void dispose() {
		logger.info(className() + " start dispose()");

		logger.info(className() + " end dispose()");
	}

	private GameEngine startGameEngine() {

		GameDescriptor descriptor = new GameDescriptor();
		descriptor.setId("ftm");
		descriptor.setTitle("Free Triangle Mesh Tool");
		descriptor.setVersion("v00.00.0001");
		descriptor.setDescription("A tool for creating vertices and triangles based upon them.");

		GameAdapter adapter = new OpenGLGameAdapter();
		GameEngine engine = GameEngine.create(descriptor, adapter);
		GameEngineFacade facade = engine.getFacade();

		engine.step(GameEngineStep.INIT, new FreeTriangleMeshInitStep());
		engine.step(GameEngineStep.MODIFY, new FreeTriangleMeshModifyStep());
		engine.step(GameEngineStep.RENDER, new FreeTriangleMeshRenderStep());

		gameWindow = adapter.createWindow(engine);
		gameWindow.setFullScreen(true);

		JFrame frame = (JFrame) gameWindow;
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new GridLayout(2, 2));
		contentPane.add(new FreeTriangleMeshXZSideView(facade));

		engine.startIn(gameWindow);

		contentPane.add(new FreeTriangleMeshXYSideView(facade));
		contentPane.add(new FreeTriangleMeshZYSideView(facade));

		return engine;
	}

	private String className() {
		return getClass().getSimpleName();
	}

	@Override
	public void repaintEverything() {
		if(gameWindow != null)
			gameWindow.repaintEverything();
	}

	private static final Logger logger = LoggerFactory.createLogger(FreeTriangleMeshConnector.class);
}
