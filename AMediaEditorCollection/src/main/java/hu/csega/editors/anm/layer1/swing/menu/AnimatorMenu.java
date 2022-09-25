package hu.csega.editors.anm.layer1.swing.menu;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import hu.csega.games.engine.GameEngineFacade;

public class AnimatorMenu {

	public static void createMenuForJFrame(JFrame frame, GameEngineFacade facade) {
		JMenuBar menuBar = new JMenuBar();

		menuBar.add(createFileMenu(frame, facade));
		menuBar.add(createModelMenu(frame, facade));
		menuBar.add(createViewMenu(frame, facade));

		frame.setJMenuBar(menuBar);
	}

	private static JMenu createFileMenu(JFrame frame, GameEngineFacade facade) {
		JFileChooser saveDialog = new JFileChooser(".");
		saveDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		saveDialog.setDialogTitle("Select file to save.");
		saveDialog.setMultiSelectionEnabled(false);
		saveDialog.setFileFilter(new FileNameExtensionFilter("Animator file", "anm"));
		saveDialog.setApproveButtonText("Save");

		JFileChooser openDialog = new JFileChooser(".");
		openDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		openDialog.setDialogTitle("Select file to open.");
		openDialog.setMultiSelectionEnabled(false);
		openDialog.setFileFilter(new FileNameExtensionFilter("Animator file", "anm"));
		openDialog.setApproveButtonText("Open");

		JMenu menu = new JMenu("File");

		/*
		JMenuItem fileNew = new JMenuItem("New");
		fileNew.addActionListener(new FileNew(frame, saveDialog, facade));
		fileMenu.add(fileNew);
		 */

		JMenuItem fileOpen = new JMenuItem("Open");
		fileOpen.addActionListener(new AnimatorMenuItemOpen(frame, openDialog, facade));
		menu.add(fileOpen);

		/*
		JMenuItem fileSave = new JMenuItem("Save");
		fileSave.addActionListener(new FileSave(frame, saveDialog, facade));
		fileMenu.add(fileSave);
		 */

		JMenuItem fileExit = new JMenuItem("Exit");
		fileExit.addActionListener(new AnimatorMenuItemExit(frame, saveDialog, facade));
		menu.add(fileExit);

		return menu;
	}

	private static JMenu createModelMenu(JFrame frame, GameEngineFacade facade) {
		JMenu menu = new JMenu("Model");

		JMenuItem addNewPart = new JMenuItem("Add New Part");
		addNewPart.addActionListener(new AnimatorMenuAddNewPart());
		menu.add(addNewPart);

		return menu;
	}

	private static JMenu createViewMenu(JFrame frame, GameEngineFacade facade) {
		JMenu menu = new JMenu("View");

		JMenuItem refreshViews = new JMenuItem("Refresh All");
		refreshViews.addActionListener(new AnimatorMenuRefreshAll());
		menu.add(refreshViews);

		return menu;
	}
}
