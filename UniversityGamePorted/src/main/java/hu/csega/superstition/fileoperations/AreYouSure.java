package hu.csega.superstition.fileoperations;

import java.awt.Component;

import javax.swing.JOptionPane;

public class AreYouSure {

	public boolean doYouWantToSave(Component parent) {
		return show(parent, "Do you want to save?");
	}

	public boolean show(Component parent, String msg) {
		int dialogButton = JOptionPane.YES_NO_OPTION;
		JOptionPane.showConfirmDialog(null, msg, "Warning", dialogButton);

		return (dialogButton == JOptionPane.YES_OPTION);
	}
}
