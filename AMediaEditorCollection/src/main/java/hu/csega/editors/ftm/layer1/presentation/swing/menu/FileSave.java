package hu.csega.editors.ftm.layer1.presentation.swing.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import hu.csega.editors.ftm.layer4.data.FreeTriangleMeshSnapshots;
import hu.csega.editors.ftm.layer5.integration.FileSystemIntegration;
import hu.csega.editors.ftm.model.FreeTriangleMeshModel;
import hu.csega.games.engine.GameEngineFacade;

class FileSave implements ActionListener {

	private JFrame frame;
	private JFileChooser saveDialog;
	private GameEngineFacade facade;

	public FileSave(JFrame frame, JFileChooser saveDialog, GameEngineFacade facade) {
		this.frame = frame;
		this.saveDialog = saveDialog;
		this.facade = facade;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int result = saveDialog.showDialog(frame, "Save");

		switch(result) {
		case JFileChooser.APPROVE_OPTION:
			File file = saveDialog.getSelectedFile();
			FreeTriangleMeshModel model = (FreeTriangleMeshModel)facade.model();
			byte[] serialized = FileSystemIntegration.serialize(model);
			FreeTriangleMeshSnapshots.writeAllBytes(file, serialized);
			break;

		default:
			break;
		}
	}
}
