package hu.csega.superstition.ftm.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.superstition.ftm.model.FreeTriangleMeshModel;
import hu.csega.superstition.gamelib.model.STextureRef;
import hu.csega.superstition.util.TextureLibrary;

class TextureLoad implements ActionListener {

	private JFrame frame;
	private JFileChooser openDialog;
	private GameEngineFacade facade;

	public TextureLoad(JFrame frame, JFileChooser openDialog, GameEngineFacade facade) {
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
			STextureRef ref = new STextureRef(file.getAbsolutePath());
			TextureLibrary.instance().resolve(ref);

			FreeTriangleMeshModel model = (FreeTriangleMeshModel)facade.model();
			model.setTextureFilename(file.getAbsolutePath());
			model.invalidate();
			facade.window().repaintEverything();
			break;

		default:
			break;
		}
	}
}
