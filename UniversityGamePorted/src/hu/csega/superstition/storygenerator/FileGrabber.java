package hu.csega.superstition.storygenerator;

public class FileGrabber : System.Drawing.Design.UITypeEditor
{
	private string filter, inital;

	public FileGrabber(string FilterString, string inital)
	{
		filter = (string) FilterString.Clone();
		this.inital = inital;
	}

	public override object EditValue(System.ComponentModel.ITypeDescriptorContext context, IServiceProvider provider, object value)
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

	public override System.Drawing.Design.UITypeEditorEditStyle GetEditStyle(System.ComponentModel.ITypeDescriptorContext context)
	{
		if (context != null && context.Instance != null)
		{
			return UITypeEditorEditStyle.Modal;
		}
		return base.GetEditStyle(context);
	}

}