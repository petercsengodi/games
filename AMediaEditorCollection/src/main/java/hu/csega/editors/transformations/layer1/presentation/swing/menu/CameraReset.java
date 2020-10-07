package hu.csega.editors.transformations.layer1.presentation.swing.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

import hu.csega.editors.transformations.layer4.data.TransformationTesterModel;
import hu.csega.games.engine.GameEngineFacade;

@SuppressWarnings("unused")
public class CameraReset implements ActionListener {

	private JFrame frame;
	private GameEngineFacade facade;

	public CameraReset(JFrame frame, GameEngineFacade facade) {
		this.frame = frame;
		this.facade = facade;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		TransformationTesterModel model = (TransformationTesterModel)facade.model();
		model.resetCamera();
		facade.window().repaintEverything();
	}
}
