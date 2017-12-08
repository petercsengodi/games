package hu.csega.editors.ftm.menu;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import hu.csega.games.engine.GameEngineFacade;

public class FreeTriangleMeshMenu {

	public static void createMenuForJFrame(JFrame frame, GameEngineFacade facade) {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = createFileMenu(frame, facade);
		menuBar.add(fileMenu);

		JMenu textureMenu = createTextureMenu(frame, facade);
		menuBar.add(textureMenu);

		frame.setJMenuBar(menuBar);
	}

	private static JMenu createFileMenu(JFrame frame, GameEngineFacade facade) {
		JFileChooser saveDialog = new JFileChooser(".");
		saveDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		saveDialog.setDialogTitle("Select file to save.");
		saveDialog.setMultiSelectionEnabled(false);
		saveDialog.setFileFilter(new FileNameExtensionFilter("FreeTriangleMesh file", "ftm"));
		saveDialog.setApproveButtonText("Save");

		JFileChooser openDialog = new JFileChooser(".");
		openDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		openDialog.setDialogTitle("Select file to open.");
		openDialog.setMultiSelectionEnabled(false);
		openDialog.setFileFilter(new FileNameExtensionFilter("FreeTriangleMesh file", "ftm"));
		openDialog.setApproveButtonText("Open");

		JMenu fileMenu = new JMenu("File");

		JMenuItem fileNew = new JMenuItem("New");
		fileNew.addActionListener(new FileNew(frame, saveDialog, facade));
		fileMenu.add(fileNew);

		JMenuItem fileOpen = new JMenuItem("Open");
		fileOpen.addActionListener(new FileOpen(frame, openDialog, facade));
		fileMenu.add(fileOpen);

		JMenuItem fileSave = new JMenuItem("Save");
		fileSave.addActionListener(new FileSave(frame, saveDialog, facade));
		fileMenu.add(fileSave);

		JMenuItem fileExit = new JMenuItem("Exit");
		fileExit.addActionListener(new FileExit(frame, saveDialog, facade));
		fileMenu.add(fileExit);

		return fileMenu;
	}

	private static JMenu createTextureMenu(JFrame frame, GameEngineFacade facade) {
		JFileChooser openDialog = new JFileChooser(".");
		openDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		openDialog.setDialogTitle("Select file to open.");
		openDialog.setMultiSelectionEnabled(false);
		openDialog.setFileFilter(new FileNameExtensionFilter("Texture file", "jpg"));
		openDialog.setApproveButtonText("Open");

		JMenu textureMenu = new JMenu("Texture");

		JMenuItem fileOpen = new JMenuItem("Load");
		fileOpen.addActionListener(new TextureLoad(frame, openDialog, facade));
		textureMenu.add(fileOpen);

		return textureMenu;
	}

}
