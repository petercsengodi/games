package hu.csega.editors.anm;

import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import hu.csega.editors.anm.menu.AnimatorMenu;
import hu.csega.editors.anm.swing.AnimatorRootLayoutManager;
import hu.csega.editors.anm.swing.AnimatorWireFrameView;
import hu.csega.editors.anm.ui.AnimatorCommonSettingsPanel;
import hu.csega.editors.anm.ui.AnimatorPartEditorPanel;
import hu.csega.editors.anm.ui.AnimatorPartList;
import hu.csega.editors.anm.ui.AnimatorScenesPanel;
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
import hu.csega.games.units.UnitStore;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class AnimatorConnector implements Connector, GameWindow {

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
		descriptor.setId("anm");
		descriptor.setTitle("Animator Tool");
		descriptor.setVersion("v00.00.0001");
		descriptor.setDescription("A tool for creating small character animations.");
		descriptor.setMouseCentered(false);

		// Open GL View

		GameAdapter adapter = new OpenGLGameAdapter();
		GameEngine engine = GameEngine.create(descriptor, adapter);
		GameEngineFacade facade = engine.getFacade();

		UnitStore.registerInstance(GameEngineFacade.class, facade);

		engine.step(GameEngineStep.INIT, new AnimatorInitStep());
		engine.step(GameEngineStep.RENDER, new AnimatorRenderStep());


		// Swing View(s)

		gameWindow = adapter.createWindow(engine);
		gameWindow.setFullScreen(true);
		logger.info("Window/Frame instance created: " + gameWindow.getClass().getName());

		JFrame frame = (JFrame) gameWindow;
		Container contentPane = frame.getContentPane();
		AnimatorRootLayoutManager layout = new AnimatorRootLayoutManager();
		contentPane.setLayout(layout);

		AnimatorMenu.createMenuForJFrame(frame, facade);

		JTabbedPane tabbedPane = new JTabbedPane();

		JComponent panelWireFrame = new AnimatorWireFrameView();
		tabbedPane.addTab("Wireframe", panelWireFrame);

		JComponent panel3D = new JPanel();
		panel3D.setLayout(new GridLayout(1, 1));
		tabbedPane.addTab("3D Canvas", panel3D);

		contentPane.add(AnimatorRootLayoutManager.CANVAS3D, tabbedPane);

		// Adds canvas to content pane, but the model doesn't exist at this point.
		engine.startIn(gameWindow, panel3D);

		// Now the model exists.
		contentPane.add(AnimatorRootLayoutManager.PARTS_LIST, UnitStore.instance(AnimatorPartList.class).getAWTComponent());
		contentPane.add(AnimatorRootLayoutManager.PARTS_SETTINGS, UnitStore.instance(AnimatorPartEditorPanel.class));
		contentPane.add(AnimatorRootLayoutManager.CORNER_CONTROLLER, UnitStore.instance(AnimatorCommonSettingsPanel.class));
		contentPane.add(AnimatorRootLayoutManager.SCENE_EDITOR, UnitStore.instance(AnimatorScenesPanel.class));

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

	private static final Logger logger = LoggerFactory.createLogger(AnimatorConnector.class);
}
