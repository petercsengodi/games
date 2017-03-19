package hu.csega.superstition.storygenerator;

import javax.swing.JPanel;

public class FileGrabber extends JPanel
{
	private String filter, inital;

	public FileGrabber(string FilterString, string inital)
	{
		filter = (string) FilterString.Clone();
		this.inital = inital;
	}

	public object EditValue(System.ComponentModel.ITypeDescriptorContext context, IServiceProvider provider, object value)
	{
		object ret;

		OpenFileDialog openFile = new OpenFileDialog();
		openFile.CheckFileExists = true;
		openFile.Multiselect = false;
		openFile.Filter = filter;
		openFile.InitialDirectory = inital;
		openFile.RestoreDirectory = true; // Need?
		DialogResult result = openFile.ShowDialog();

		if(result != DialogResult.Cancel){
			ret = Path.GetFileName(openFile.FileName);
		}
		else ret = value;

		openFile.Dispose();
		return ret;
	}

	public System.Drawing.Design.UITypeEditorEditStyle GetEditStyle(System.ComponentModel.ITypeDescriptorContext context)
	{
		if (context != null && context.Instance != null)
		{
			return UITypeEditorEditStyle.Modal;
		}
		return base.GetEditStyle(context);
	}

}