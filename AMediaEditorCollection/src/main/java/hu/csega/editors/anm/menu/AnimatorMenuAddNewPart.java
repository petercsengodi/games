package hu.csega.editors.anm.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import hu.csega.editors.anm.components.ComponentPartManipulator;
import hu.csega.editors.anm.ui.AnimatorUIComponents;
import hu.csega.games.library.util.FileUtil;
import hu.csega.games.units.UnitStore;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class AnimatorMenuAddNewPart implements ActionListener {

	private AnimatorUIComponents ui;
	private FileUtil files;
	private ComponentPartManipulator manipulator;

	@Override
	public void actionPerformed(ActionEvent e) {
		if(ui == null) {
			ui = UnitStore.instance(AnimatorUIComponents.class);
			if(ui == null || ui.frame == null) {
				logger.error("Missing component: " + AnimatorUIComponents.class.getSimpleName() + ".frame");
				return;
			}
		}

		if(files == null) {
			files = UnitStore.instance(FileUtil.class);
			if(files == null) {
				logger.error("Missing component: " + FileUtil.class.getSimpleName());
				return;
			}
		}

		if(manipulator == null) {
			manipulator = UnitStore.instance(ComponentPartManipulator.class);
			if(manipulator == null) {
				logger.error("Missing component: " + ComponentPartManipulator.class.getSimpleName());
				return;
			}
		}

		if(ui.addNewPartFile == null) {
			ui.addNewPartFile = new JFileChooser();
			ui.addNewPartFile.setCurrentDirectory(new File(ui.meshDirectory));
		}

		int result = ui.addNewPartFile.showDialog(ui.frame, "Select Mesh");

		switch(result) {
		case JFileChooser.APPROVE_OPTION:
			File file = ui.addNewPartFile.getSelectedFile();
			String filename = file.getAbsolutePath();
			String projectPath = files.projectPath();
			if(filename.startsWith(projectPath)) {
				filename = filename.substring(projectPath.length());
			}

			logger.info("Selected file: " + filename);
			manipulator.addNewPart(filename);

			break;

		default:
			break;
		}
	}

	private static final Logger logger = LoggerFactory.createLogger(AnimatorMenuAddNewPart.class);
}
