package hu.csega.superstition.t3dcreator;

public class CView : System.Windows.Forms.UserControl
{
	private bool initialized;
	private object data;

	protected bool Initialized
	{
		get { return initialized; }
	}

	public CView()
	{
		initialized = false;
		data = null;
	}

	/// <summary>
	/// Clean up any resources being used.
	/// </summary>
	protected override void Dispose( bool disposing )
	{
		if( disposing )
		{
			CloseView();
			data = null;
		}
		initialized = false;
		base.Dispose( disposing );
	}

	/// <summary>
	/// Sets Model for View and initializes the View.
	/// </summary>
	/// <param name="data">Model to show.</param>
	public void SetData(object data)
	{
		this.data = data;
		if(!initialized)
		{
			InitializeView();
			initialized = true;
		}
	}

	/// <summary>
	/// Gets Model shown by this View.
	/// </summary>
	/// <returns>Model shown by this View.</returns>
	public object GetData()
	{
		return data;
	}

	/// <summary>
	/// Signals the View to update itself.
	/// </summary>
	public void UpdateView()
	{
		UpdateView(Updates.Full);
	}

	/// <summary>
	/// Signals the View to update itself.
	/// </summary>
	public virtual void UpdateView(Updates update)
	{
	}

	/// <summary>
	/// Makes View-special initializations.
	/// </summary>
	protected virtual void InitializeView()
	{
	}

	/// <summary>
	/// Makes View-special Disposing functions.
	/// </summary>
	protected virtual void CloseView()
	{
	}
}