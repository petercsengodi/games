package hu.csega.editors.anm;

import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import hu.csega.editors.anm.components.Component3DView;
import hu.csega.editors.anm.components.ComponentRefreshViews;
import hu.csega.editors.anm.layer1.swing.components.partlist.AnimatorPartEditorPanel;
import hu.csega.editors.anm.layer1.swing.components.partlist.AnimatorPartListModel;
import hu.csega.editors.anm.layer1.swing.menu.AnimatorMenu;
import hu.csega.editors.anm.layer1.swing.wireframe.AnimatorWireFrameView;
import hu.csega.editors.anm.ui.AnimatorCommonSettingsPanel;
import hu.csega.editors.anm.ui.AnimatorScenesPanel;
import hu.csega.editors.anm.ui.AnimatorUIComponents;
import hu.csega.editors.anm.ui.layout.root.AnimatorRootLayoutManager;
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

	private ComponentRefreshViews refreshViews;
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

		AnimatorInitStep animatorInitStep = new AnimatorInitStep();

		AnimatorRenderStep animatorRenderStep = new AnimatorRenderStep();
		Component3DView view = UnitStore.instance(Component3DView.class);
		view.setRenderer(animatorRenderStep);

		engine.step(GameEngineStep.INIT, animatorInitStep);
		engine.step(GameEngineStep.RENDER, animatorRenderStep);


		// Swing View(s)

		AnimatorUIComponents components = UnitStore.instance(AnimatorUIComponents.class);
		components.gameWindow = adapter.createWindow(engine);
		components.gameWindow.setFullScreen(true);
		logger.info("Window/Frame instance created: " + components.gameWindow.getClass().getName());

		components.frame = (JFrame) components.gameWindow;
		Container contentPane = components.frame.getContentPane();
		AnimatorRootLayoutManager layout = new AnimatorRootLayoutManager();
		contentPane.setLayout(layout);

		AnimatorMenu.createMenuForJFrame(components.frame, facade);

		components.tabbedPane = new JTabbedPane();

		components.panelFront = new AnimatorWireFrameView();
		components.tabbedPane.addTab("Front", components.panelFront);

		components.panelTop = new AnimatorWireFrameView();
		components.tabbedPane.addTab("Top", components.panelTop);

		components.panelSide = new AnimatorWireFrameView();
		components.tabbedPane.addTab("Side", components.panelSide);

		components.panelWireFrame = new AnimatorWireFrameView();
		components.tabbedPane.addTab("Wireframe", components.panelWireFrame);

		components.panel3D = new JPanel();
		components.panel3D.setLayout(new GridLayout(1, 1));
		components.tabbedPane.addTab("3D Canvas", components.panel3D);

		contentPane.add(AnimatorRootLayoutManager.MULTI_TAB, components.tabbedPane);

		// Adds canvas to content pane, but the model doesn't exist at this point.
		engine.startIn(components.gameWindow, components.panel3D);

		components.partListModel = new AnimatorPartListModel();
		components.partList = new JList<>(components.partListModel);
		components.partListScrollPane = new JScrollPane(components.partList);

		components.partEditorPanel = new AnimatorPartEditorPanel();
		components.commonSettingsPanel = new AnimatorCommonSettingsPanel();
		components.scenesPanel = new AnimatorScenesPanel();

		// Now the model exists.
		contentPane.add(AnimatorRootLayoutManager.PARTS_LIST, components.partListScrollPane);
		contentPane.add(AnimatorRootLayoutManager.PARTS_SETTINGS, components.partEditorPanel);
		contentPane.add(AnimatorRootLayoutManager.CORNER_CONTROLLER, components.commonSettingsPanel);
		contentPane.add(AnimatorRootLayoutManager.SCENE_EDITOR, components.scenesPanel);

		layout.updateAfterAllComponentsAreAdded();

		refreshViews = UnitStore.instance(ComponentRefreshViews.class);

		return engine;
	}

	private String className() {
		return getClass().getSimpleName();
	}

	@Override
	public void repaintEverything() {
		if(refreshViews != null) {
			refreshViews.refreshAll();
		}
	}

	private static final Logger logger = LoggerFactory.createLogger(AnimatorConnector.class);
}
