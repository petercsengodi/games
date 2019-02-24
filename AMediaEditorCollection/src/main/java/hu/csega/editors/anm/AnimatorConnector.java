package hu.csega.editors.anm;

import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import hu.csega.editors.anm.menu.AnimatorMenu;
import hu.csega.editors.anm.model.AnimatorScene;
import hu.csega.editors.anm.model.parts.AnimatorPart;
import hu.csega.editors.anm.swing.AnimatorContentPaneLayout;
import hu.csega.editors.anm.treeview.AnimatorTreeModel;
import hu.csega.games.adapters.opengl.OpenGLGameAdapter;
import hu.csega.games.common.Connector;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.env.Environment;
import hu.csega.games.engine.impl.GameEngine;
import hu.csega.games.engine.intf.GameAdapter;
import hu.csega.games.engine.intf.GameCanvas;
import hu.csega.games.engine.intf.GameDescriptor;
import hu.csega.games.engine.intf.GameWindow;
import hu.csega.games.engine.intf.GameWindowListener;
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
		descriptor.setId("anm");
		descriptor.setTitle("Animator Tool");
		descriptor.setVersion("v00.00.0001");
		descriptor.setDescription("A tool for creating small character animations.");
		descriptor.setMouseCentered(false);

		// Open GL View

		GameAdapter adapter = new OpenGLGameAdapter();
		GameEngine engine = GameEngine.create(descriptor, adapter);
		GameEngineFacade facade = engine.getFacade();

		/*
		engine.step(GameEngineStep.INIT, new FreeTriangleMeshInitStep());
		engine.step(GameEngineStep.MODIFY, new FreeTriangleMeshModifyStep());
		engine.step(GameEngineStep.RENDER, new FreeTriangleMeshRenderStep());

		engine.getControl().registerKeyListener(new FreeTriangleMeshKeyListener());
		 */

		// Swing View(s)

		gameWindow = adapter.createWindow(engine);
		gameWindow.setFullScreen(true);

		JFrame frame = (JFrame) gameWindow;
		// KeyListener keyListener = (KeyListener) gameWindow;
		Container contentPane = frame.getContentPane();
		AnimatorContentPaneLayout layout = new AnimatorContentPaneLayout();
		contentPane.setLayout(layout);

		AnimatorMenu.createMenuForJFrame(frame, facade);

		// Test data // FIXME remove
		AnimatorScene scene = new AnimatorScene();
		scene.add("Root part", new AnimatorPart());

		// Upper left tile
		AnimatorTreeModel treeModel = new AnimatorTreeModel();
		JTree tree = new JTree(treeModel);
		JScrollPane scrollPaneForTree = new JScrollPane(tree);
		contentPane.add(scrollPaneForTree);
		layout.addLayoutComponent(AnimatorContentPaneLayout.FAR_LEFT_PANEL, scrollPaneForTree);

		treeModel.update(scene);

		// Upper right tile
		engine.startIn(gameWindow);

		// Lower left tile
		/*
		contentPane.add(new FreeTriangleMeshXYSideView(facade));
		 */

		// Lower right tile
		/*
		JTabbedPane bottomRightTab = new JTabbedPane();
		bottomRightTab.addKeyListener(keyListener);
		bottomRightTab.addTab("ZY Wireframe", new FreeTriangleMeshZYSideView(facade));
		bottomRightTab.addTab("Texture Window", new FreeTriangleMeshTexture(facade));
		contentPane.add(bottomRightTab);
		 */

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
