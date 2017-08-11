package hu.csega.superstition.fileoperations;

import java.awt.Component;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileControl {

	private boolean file_open;
	private boolean file_changed;

	private String file_name;
	private JFileChooser save_dialog;
	private JFileChooser open_dialog;
	private AreYouSure are_you_sure;

	private FileOperation load_op, save_op;

	public FileControl(FileOperation load_op, FileOperation save_op, String init_dir, String... extensions) {
		file_name = null;

		open_dialog = new JFileChooser(init_dir);
		open_dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		open_dialog.setDialogTitle("Select file to open.");
		open_dialog.setMultiSelectionEnabled(false);
		open_dialog.setFileFilter(new FileNameExtensionFilter(null /* TODO */ , extensions));
		open_dialog.setApproveButtonText("Open");

		save_dialog = new JFileChooser();
		save_dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		save_dialog.setDialogTitle("Select file to save.");
		save_dialog.setMultiSelectionEnabled(false);
		save_dialog.setFileFilter(new FileNameExtensionFilter(null /* TODO */ , extensions));
		save_dialog.setApproveButtonText("Save");

		are_you_sure = new AreYouSure();

		this.load_op = load_op;
		this.save_op = save_op;
	}


	public boolean isFileOpen() {
		return file_open;
	}

	public boolean isFileChanged() {
		return file_changed;
	}

	public String getFileName() {
		return file_name;
	}

	public void change()
	{
		if(file_open)
			file_changed = true;
	}


	public void open(Component parent)
	{
		if(file_open)
		{
			if(file_changed)
				save(parent);
		}

		int result = open_dialog.showOpenDialog(parent);

		switch(result)
		{
		case JFileChooser.APPROVE_OPTION:
			file_name = open_dialog.getSelectedFile().getName();

			load_op.call(file_name);

			file_changed = false;
			file_open = true;
			break;

		default:
			break;
		}

	}

	public void saveAs(Component parent)
	{
		int result = save_dialog.showDialog(parent, "Save");

		switch(result)
		{
		case JFileChooser.APPROVE_OPTION:
			file_name = save_dialog.getSelectedFile().getName();

			save_op.call(file_name);

			file_changed = false;
			break;

		default:
			break;
		}
	}

	public void save(Component parent)
	{
		if(!file_open) return;
		if(file_name == null) saveAs(parent);

		if(are_you_sure.doYouWantToSave(parent))
		{
			save_op.call(file_name);
		}

		file_changed = false;
	}

	public void saveNew(Component parent)
	{
		if(file_open)
		{
			if(file_changed) save(parent);
		}

		file_name = null;
		file_open = true;
		file_changed = false;
	}

}