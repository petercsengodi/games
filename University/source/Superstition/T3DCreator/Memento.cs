using System;
using System.Drawing;
using System.Collections;

using Microsoft.DirectX;

using FileOperations;

namespace T3DCreator
{
	/// <summary>
	/// Summary description for Memento.
	/// </summary>
	public class Memento
	{
		private ArrayList for_redo, for_undo;
		private FileControl control;

		public Memento()
		{
			this.control = null;
			this.for_redo = new ArrayList();
			this.for_undo = new ArrayList();
		}

		public Memento(FileControl control)
		{
			this.control = control;
			this.for_redo = new ArrayList();
			this.for_undo = new ArrayList();
		}

		public void Push(Operation operation)
		{
			for_undo.Add(operation);
			for_redo.Clear();
			operation.Transform();
			control.Change();
		}

		public void Undo()
		{
			if(for_undo.Count == 0) return;
			int last_index = for_undo.Count - 1;
			Operation operation = for_undo[last_index] as Operation;
			for_undo.RemoveAt(last_index);
			for_redo.Add(operation);
			operation.Invert();
			control.Change();
		}

		public void Redo()
		{
			if(for_redo.Count == 0) return;
			int last_index = for_redo.Count - 1;
			Operation operation = for_redo[last_index] as Operation;
			for_redo.RemoveAt(last_index);
			for_undo.Add(operation);
			operation.Transform();
			control.Change();
		}

		public void Clear()
		{
			for_undo.Clear();
			for_redo.Clear();
		}
	
	} // End of class Memento
}
