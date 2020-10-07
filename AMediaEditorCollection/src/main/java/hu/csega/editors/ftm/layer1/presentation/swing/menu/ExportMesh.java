package hu.csega.editors.ftm.layer1.presentation.swing.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import hu.csega.editors.ftm.model.FreeTriangleMeshModel;
import hu.csega.editors.ftm.util.FreeTriangleMeshExport;
import hu.csega.games.engine.GameEngineFacade;

class ExportMesh implements ActionListener {

	private JFrame frame;
	private JFileChooser exportDialog;
	private GameEngineFacade facade;

	public ExportMesh(JFrame frame, JFileChooser saveDialog, GameEngineFacade facade) {
		this.frame = frame;
		this.exportDialog = saveDialog;
		this.facade = facade;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int result = exportDialog.showDialog(frame, "Export");

		switch(result) {
		case JFileChooser.APPROVE_OPTION:
			File file = exportDialog.getSelectedFile();
			FreeTriangleMeshModel model = (FreeTriangleMeshModel)facade.model();
			FreeTriangleMeshExport.export(model, file.getAbsolutePath());
			break;

		default:
			break;
		}
	}
}
