package hu.csega.editors.ftm.layer1.presentation.swing.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import hu.csega.editors.FreeTriangleMeshToolStarter;
import hu.csega.editors.ftm.model.FreeTriangleMeshModel;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.library.model.STextureRef;

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
			String absolutePath = file.getAbsolutePath();

			String path = absolutePath;
			int index = path.indexOf(File.separator + "res" + File.separator);
			if(index > -1)
				path = path.substring(index + 1);

			STextureRef ref = new STextureRef(path);
			FreeTriangleMeshToolStarter.TEXTURES.resolve(ref);

			FreeTriangleMeshModel model = (FreeTriangleMeshModel)facade.model();
			model.setTextureFilename(path);
			model.invalidate();
			facade.window().repaintEverything();
			break;

		default:
			break;
		}
	}
}
