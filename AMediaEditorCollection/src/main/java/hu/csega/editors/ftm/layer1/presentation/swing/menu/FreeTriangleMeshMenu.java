package hu.csega.editors.ftm.layer1.presentation.swing.menu;

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

		JMenu exportMenu = createExportMenu(frame, facade);
		menuBar.add(exportMenu);

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
		JFileChooser loadTextureDialog = new JFileChooser(".");
		loadTextureDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		loadTextureDialog.setDialogTitle("Select texture to load.");
		loadTextureDialog.setMultiSelectionEnabled(false);
		loadTextureDialog.setFileFilter(new FileNameExtensionFilter("Texture file", "jpg"));
		loadTextureDialog.setApproveButtonText("Load");

		JMenu textureMenu = new JMenu("Texture");

		JMenuItem textureLoadItem = new JMenuItem("Load");
		textureLoadItem.addActionListener(new TextureLoad(frame, loadTextureDialog, facade));
		textureMenu.add(textureLoadItem);

		return textureMenu;
	}

	private static JMenu createExportMenu(JFrame frame, GameEngineFacade facade) {
		JFileChooser meshExportDialog = new JFileChooser(".");
		meshExportDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		meshExportDialog.setDialogTitle("Select file to export to.");
		meshExportDialog.setMultiSelectionEnabled(false);
		meshExportDialog.setFileFilter(new FileNameExtensionFilter("Mesh export", "mesh"));
		meshExportDialog.setApproveButtonText("Export");

		JMenuItem meshExportItem = new JMenuItem("To Mesh");
		meshExportItem.addActionListener(new ExportMesh(frame, meshExportDialog, facade));

		JFileChooser mwcExportDialog = new JFileChooser(".");
		mwcExportDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		mwcExportDialog.setDialogTitle("Select file to export to.");
		mwcExportDialog.setMultiSelectionEnabled(false);
		mwcExportDialog.setFileFilter(new FileNameExtensionFilter("Mesh with collision map", "mwc"));
		mwcExportDialog.setApproveButtonText("Export");

		JMenuItem mwcExportItem = new JMenuItem("To Mesh With Collision");
		mwcExportItem.addActionListener(new ExportMWC(frame, mwcExportDialog, facade));

		JMenu exportMenu = new JMenu("Export");
		exportMenu.add(meshExportItem);
		exportMenu.add(mwcExportItem);

		return exportMenu;
	}

}
