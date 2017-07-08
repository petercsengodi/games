package hu.csega.superstition.unported.t3dcreator;

import java.util.ArrayList;
import java.util.List;

import hu.csega.superstition.fileoperations.FileControl;
import hu.csega.superstition.unported.t3dcreator.operations.Operation;

public class Memento {
	private List<Operation> for_redo;
	private List<Operation> for_undo;
	private FileControl control;

	public Memento() {
		this(null);
	}

	public Memento(FileControl control) {
		this.control = control;
		this.for_redo = new ArrayList<>();
		this.for_undo = new ArrayList<>();
	}

	public void Push(Operation operation) {
		for_undo.add(operation);
		for_redo.clear();
		operation.Transform();
		control.change();
	}

	public void Undo() {
		if (for_undo.size() == 0)
			return;

		int last_index = for_undo.size() - 1;
		Operation operation = for_undo.get(last_index);
		for_undo.remove(last_index);
		for_redo.add(operation);
		operation.Invert();
		control.change();
	}

	public void Redo() {
		if (for_redo.size() == 0)
			return;

		int last_index = for_redo.size() - 1;
		Operation operation = for_redo.get(last_index);
		for_redo.remove(last_index);
		for_undo.add(operation);
		operation.Transform();
		control.change();
	}

	public void Clear() {
		for_undo.clear();
		for_redo.clear();
	}

} // End of class Memento