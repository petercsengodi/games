package hu.csega.superstition.ftm.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.superstition.ftm.model.FreeTriangleMeshModel;
import hu.csega.superstition.ftm.util.FreeTriangleMeshSnapshots;

class FileOpen implements ActionListener {

	private JFrame frame;
	private JFileChooser openDialog;
	private GameEngineFacade facade;

	public FileOpen(JFrame frame, JFileChooser openDialog, GameEngineFacade facade) {
		this.frame = frame;
		this.openDialog = openDialog;
		this.facade = facade;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int result = openDialog.showDialog(frame, "Open");

		switch(result) {
		case JFileChooser.APPROVE_OPTION:
			File file = openDialog.getSelectedFile();
			byte[] serialized = FreeTriangleMeshSnapshots.readAllBytes(file);
			FreeTriangleMeshModel model = (FreeTriangleMeshModel)FreeTriangleMeshSnapshots.deserialize(serialized);
			if(model == null)
				model = new FreeTriangleMeshModel();
			facade.setModel(model);
			facade.window().repaintEverything();
			break;

		default:
			break;
		}
	}
}
