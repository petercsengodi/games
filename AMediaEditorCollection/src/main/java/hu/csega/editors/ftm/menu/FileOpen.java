package hu.csega.editors.ftm.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import hu.csega.editors.FreeTriangleMeshToolStarter;
import hu.csega.editors.ftm.model.FreeTriangleMeshModel;
import hu.csega.editors.ftm.util.FreeTriangleMeshSnapshots;
import hu.csega.games.engine.GameEngineFacade;

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
			if(model == null) {
				model = new FreeTriangleMeshModel();
				model.setTextureFilename(FreeTriangleMeshToolStarter.DEFAULT_TEXTURE_FILE);
			}
			facade.setModel(model);
			facade.window().repaintEverything();
			break;

		default:
			break;
		}
	}
}
