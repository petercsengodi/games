package hu.csega.editors.transformations.layer1.presentation.swing.menu;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import hu.csega.games.engine.GameEngineFacade;

public class TransformationTesterMenu {

	public static void createMenuForJFrame(JFrame frame, GameEngineFacade facade) {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = createFileMenu(frame, facade);
		menuBar.add(fileMenu);

		JMenu cameraMenu = createCameraMenu(frame, facade);
		menuBar.add(cameraMenu);

		frame.setJMenuBar(menuBar);
	}

	private static JMenu createFileMenu(JFrame frame, GameEngineFacade facade) {
		JMenu fileMenu = new JMenu("File");

		JMenuItem fileExit = new JMenuItem("Exit");
		fileExit.addActionListener(new FileExit(frame, facade));
		fileMenu.add(fileExit);

		return fileMenu;
	}

	private static JMenu createCameraMenu(JFrame frame, GameEngineFacade facade) {
		JMenu fileMenu = new JMenu("Camera");

		JMenuItem fileExit = new JMenuItem("Reset");
		fileExit.addActionListener(new CameraReset(frame, facade));
		fileMenu.add(fileExit);

		return fileMenu;
	}
}
