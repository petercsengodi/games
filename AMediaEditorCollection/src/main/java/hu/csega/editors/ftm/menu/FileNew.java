package hu.csega.editors.ftm.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import hu.csega.editors.ftm.model.FreeTriangleMeshModel;
import hu.csega.games.engine.GameEngineFacade;

@SuppressWarnings("unused")
class FileNew implements ActionListener {

	private JFrame frame;
	private JFileChooser saveDialog;
	private GameEngineFacade facade;

	public FileNew(JFrame frame, JFileChooser saveDialog, GameEngineFacade facade) {
		this.frame = frame;
		this.saveDialog = saveDialog;
		this.facade = facade;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		facade.setModel(new FreeTriangleMeshModel());
		facade.window().repaintEverything();
	}
}
