package hu.csega.editors.transformations.layer1.presentation.swing.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

import hu.csega.games.engine.GameEngineFacade;

@SuppressWarnings("unused")
public class FileExit implements ActionListener {

	private JFrame frame;
	private GameEngineFacade facade;

	public FileExit(JFrame frame, GameEngineFacade facade) {
		this.frame = frame;
		this.facade = facade;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}
}
