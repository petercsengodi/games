using System;
using System.IO;
using System.Windows.Forms;

namespace FileOperations
{
	/// <summary>
	/// Class that has the functions for controlling
	/// file operations with this program's model.
	/// </summary>
	public class FileControl : IDisposable
	{
		private bool file_opened;
		private bool file_changed;
		
		private string file_name;
		private SaveFileDialog save_dialog;
		private OpenFileDialog open_dialog;
		private AreYouSure are_you_sure;

		private FileOperation load_op, save_op;

		public FileControl(string filter, string init_dir, 
			FileOperation load_op, FileOperation save_op)
		{
			file_name = null;

			open_dialog = new OpenFileDialog();
			open_dialog.Filter = filter;
			open_dialog.InitialDirectory = init_dir;
			open_dialog.Multiselect = false;
			open_dialog.Title = "Select file to open.";

			save_dialog = new SaveFileDialog();
			save_dialog.Filter = filter;
			save_dialog.InitialDirectory = init_dir;
			save_dialog.Title = "Select file to save.";

			are_you_sure = new AreYouSure();

			this.load_op = load_op;
			this.save_op = save_op;
		}

//		public FileControl(string filter, 
//			FileOperation load_op, FileOperation save_op)
//		{
//			file_name = null;
//
//			open_dialog = new OpenFileDialog();
//			open_dialog.Filter = filter;
//			open_dialog.InitialDirectory = ".";
//			open_dialog.Multiselect = false;
//			open_dialog.Title = "Select file to open.";
//
//			save_dialog = new SaveFileDialog();
//			save_dialog.Filter = filter;
//			save_dialog.InitialDirectory = ".";
//			save_dialog.Title = "Select file to save.";
//
//			this.load_op = load_op;
//			this.save_op = save_op;
//		}

		/// <summary>
		/// True, if the model is opened.
		/// </summary>
		public bool FileOpened{ get{ return file_opened; } }
		
		/// <summary>
		/// True, if the model is changed.
		/// </summary>
		public bool FileChanged{ get{ return file_changed; } }

		/// <summary>
		/// Returns the model's file name.
		/// </summary>
		public string FileName{ get{ return file_name; } }
		
		/// <summary>
		/// If model file is opened, sets the model be changed.
		/// </summary>
		public void Change()
		{
			if(file_opened) file_changed = true;
		}

		/// <summary>
		/// Opens a specified file.
		/// </summary>
		public void Open()
		{
			// save old file
			if(file_opened)
			{
				if(file_changed) Save();
			}

			DialogResult result = open_dialog.ShowDialog();

			switch(result)
			{
				case DialogResult.OK:
				case DialogResult.Yes:
					file_name = open_dialog.FileName;

					load_op(file_name);

					file_changed = false;
					file_opened = true;
					break;

				default:
					break;
			}

		}

		/// <summary>
		/// Saves the model into a new file.
		/// </summary>
		public void SaveAs()
		{
			DialogResult result = save_dialog.ShowDialog();

			switch(result)
			{
				case DialogResult.OK:
				case DialogResult.Yes:
					file_name = save_dialog.FileName;

					save_op(file_name);

					file_changed = false;
					break;

				default:
					break;
			}
		}


		/// <summary>
		/// Saves the model.
		/// </summary>
		public void Save()
		{
			if(!file_opened) return;
			if(file_name == null) SaveAs();

			if(are_you_sure.ShowDialog() == DialogResult.Yes)
			{
				save_op(file_name);
			}

			file_changed = false;
		}

		public void New()
		{
			if(file_opened)
			{
				if(file_changed) Save();
			}

			file_name = null;
			file_opened = true;
			file_changed = false;
		}

		#region IDisposable Members

		public void Dispose()
		{
			open_dialog.Dispose();
			save_dialog.Dispose();
			are_you_sure.Dispose();
		}

		#endregion

	}

	public delegate void FileOperation(string file_name);
}