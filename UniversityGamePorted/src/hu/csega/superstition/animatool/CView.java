package hu.csega.superstition.animatool;

import javax.swing.JPanel;

public abstract class CView extends JPanel {

	private boolean initialized;
	private Object data;

	public boolean isInitialized() {
		return initialized;
	}

	public CView()
	{
		initialized = false;
		data = null;
	}

	public void SetData(Object data)
	{
		this.data = data;
		if(!initialized)
		{
			initialized = true;
		}
	}

	public Object getData()
	{
		return data;
	}

	public abstract void updateView(Updates update);


	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;
}
