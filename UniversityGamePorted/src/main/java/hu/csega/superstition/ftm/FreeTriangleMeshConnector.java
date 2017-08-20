package hu.csega.superstition.ftm;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

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
import hu.csega.superstition.ftm.menu.FreeTriangleMeshMenu;
import hu.csega.superstition.ftm.view.FreeTriangleMeshTexture;
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
	public void closeApplication() {
		for(GameWindowListener listener: listeners) {
			listener.onFinishingWork();
		}
		System.exit(0);
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
		descriptor.setMouseCentered(false);

		GameAdapter adapter = new OpenGLGameAdapter();
		GameEngine engine = GameEngine.create(descriptor, adapter);
		GameEngineFacade facade = engine.getFacade();

		engine.step(GameEngineStep.INIT, new FreeTriangleMeshInitStep());
		engine.step(GameEngineStep.MODIFY, new FreeTriangleMeshModifyStep());
		engine.step(GameEngineStep.RENDER, new FreeTriangleMeshRenderStep());

		engine.getControl().registerKeyListener(new FreeTriangleMeshKeyListener());

		gameWindow = adapter.createWindow(engine);
		gameWindow.setFullScreen(true);

		JFrame frame = (JFrame) gameWindow;
		KeyListener keyListener = (KeyListener) gameWindow;
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new GridLayout(2, 2));
		contentPane.add(new FreeTriangleMeshXZSideView(facade));

		FreeTriangleMeshMenu.createMenuForJFrame(frame, facade);

		engine.startIn(gameWindow);

		contentPane.add(new FreeTriangleMeshXYSideView(facade));

		JTabbedPane bottomRightTab = new JTabbedPane();
		bottomRightTab.addKeyListener(keyListener);
		bottomRightTab.addTab("ZY Wireframe", new FreeTriangleMeshZYSideView(facade));
		bottomRightTab.addTab("Texture Window", new FreeTriangleMeshTexture(facade));

		contentPane.add(bottomRightTab);

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
