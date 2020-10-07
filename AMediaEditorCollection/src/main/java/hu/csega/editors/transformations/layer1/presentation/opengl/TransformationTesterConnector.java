package hu.csega.editors.transformations.layer1.presentation.opengl;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import hu.csega.editors.transformations.layer1.presentation.swing.TransformationTesterTransformedView;
import hu.csega.editors.transformations.layer1.presentation.swing.TransformationTesterXYSideView;
import hu.csega.editors.transformations.layer1.presentation.swing.TransformationTesterXZSideView;
import hu.csega.editors.transformations.layer1.presentation.swing.menu.TransformationTesterMenu;
import hu.csega.games.adapters.opengl.OpenGLCanvas;
import hu.csega.games.adapters.opengl.OpenGLGameAdapter;
import hu.csega.games.common.Connector;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.env.Environment;
import hu.csega.games.engine.impl.GameEngine;
import hu.csega.games.engine.intf.GameAdapter;
import hu.csega.games.engine.intf.GameCanvas;
import hu.csega.games.engine.intf.GameDescriptor;
import hu.csega.games.engine.intf.GameEngineStep;
import hu.csega.games.engine.intf.GameWindow;
import hu.csega.games.engine.intf.GameWindowListener;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class TransformationTesterConnector implements Connector, GameWindow {

	private GameWindow gameWindow;
	private List<GameWindowListener> listeners = new ArrayList<>();

	@Override
	public void initialize() {
	}

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
	public void add(GameCanvas canvas, Container container) {
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
		descriptor.setId("tt");
		descriptor.setTitle("Transformation Tester");
		descriptor.setVersion("v00.00.0001");
		descriptor.setDescription("An application for testing java and OpenGL transformations.");
		descriptor.setMouseCentered(false);

		GameAdapter adapter = new OpenGLGameAdapter();
		GameEngine engine = GameEngine.create(descriptor, adapter);
		GameEngineFacade facade = engine.getFacade();

		TransformationTesterRenderStep renderer = new TransformationTesterRenderStep();

		engine.step(GameEngineStep.INIT, new TransformationTesterInitStep());
		engine.step(GameEngineStep.MODIFY, new TransformationTesterModifyStep());
		engine.step(GameEngineStep.RENDER, renderer);

		engine.getControl().registerKeyListener(new TransformationTesterKeyListener());

		gameWindow = adapter.createWindow(engine);
		gameWindow.setFullScreen(true);

		JFrame frame = (JFrame) gameWindow;
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new GridLayout(2, 2));
		contentPane.add(new TransformationTesterXZSideView(facade));

		TransformationTesterMenu.createMenuForJFrame(frame, facade);

		engine.startIn(gameWindow, contentPane);

		contentPane.add(new TransformationTesterXYSideView(facade));
		contentPane.add(new TransformationTesterTransformedView(facade));

		TransformationTesterMouseController mouseController = new TransformationTesterMouseController(facade);

		GameCanvas canvas = engine.getCanvas();
		Component component = ((OpenGLCanvas) canvas).getRealCanvas();
		component.addMouseListener(mouseController);
		component.addMouseMotionListener(mouseController);
		component.addMouseWheelListener(mouseController);

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

	private static final Logger logger = LoggerFactory.createLogger(TransformationTesterConnector.class);
}
