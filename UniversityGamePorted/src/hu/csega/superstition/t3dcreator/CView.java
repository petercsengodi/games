package hu.csega.superstition.t3dcreator;

public class CView {
	private boolean initialized;
	private Object data;

	protected boolean isInitialized() {
		return initialized;
	}

	public CView() {
		initialized = false;
		data = null;
	}

	protected void Dispose( boolean disposing )
	{
		if( disposing )
		{
			closeView();
			data = null;
		}

		initialized = false;
	}

	/// <summary>
	/// Sets Model for View and initializes the View.
	/// </summary>
	/// <param name="data">Model to show.</param>
	public void setData(Object data)
	{
		this.data = data;
		if(!initialized)
		{
			initializeView();
			initialized = true;
		}
	}

	/// <summary>
	/// Gets Model shown by this View.
	/// </summary>
	/// <returns>Model shown by this View.</returns>
	public Object getData()
	{
		return data;
	}

	/// <summary>
	/// Signals the View to update itself.
	/// </summary>
	public void updateView()
	{
		updateView(Updates.Full);
	}

	/// <summary>
	/// Signals the View to update itself.
	/// </summary>
	public void updateView(Updates update)
	{
	}

	/// <summary>
	/// Makes View-special initializations.
	/// </summary>
	protected void initializeView()
	{
	}

	/// <summary>
	/// Makes View-special Disposing functions.
	/// </summary>
	protected void closeView()
	{
	}

	public void invalidate() {
	}
}