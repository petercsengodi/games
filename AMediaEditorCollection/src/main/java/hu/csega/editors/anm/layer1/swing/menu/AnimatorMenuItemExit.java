package hu.csega.editors.anm.layer1.swing.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import hu.csega.games.engine.GameEngineFacade;

@SuppressWarnings("unused")
class AnimatorMenuItemExit implements ActionListener {

	private JFrame frame;
	private JFileChooser saveDialog;
	private GameEngineFacade facade;

	public AnimatorMenuItemExit(JFrame frame, JFileChooser saveDialog, GameEngineFacade facade) {
		this.frame = frame;
		this.saveDialog = saveDialog;
		this.facade = facade;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}
}
