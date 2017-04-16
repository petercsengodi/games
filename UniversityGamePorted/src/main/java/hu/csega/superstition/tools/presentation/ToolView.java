package hu.csega.superstition.tools.presentation;

import java.awt.Graphics2D;

import javax.swing.JPanel;

import hu.csega.superstition.common.Disposable;
import hu.csega.superstition.tools.Updates;

public abstract class ToolView extends JPanel implements Disposable {

	private boolean invalidated;
	private boolean initialized;
	private Object data;

	public ToolView() {
		this.invalidated = true;
		this.initialized = false;
		this.data = null;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
		if(!initialized) {
			initializeView();
			initialized = true;
			invalidated = true;
		}
	}

	public boolean isInvalidated() {
		return invalidated;
	}

	@Override
	public void invalidate() {
		invalidated = true;
	}

	@Override
	public void repaint() {
		invalidated = false;
		super.repaint();
	}

	@Override
	public void dispose()
	{
		closeView();
		data = null;
		initialized = false;
	}

	public void initializeView(){
	}

	public void closeView() {
	}

	public void updateView() {
		updateView(Updates.Full);
	}

	public void paintCanvas(ToolCanvas canvas, Graphics2D g) {
	}

	public abstract void updateView(Updates update);

	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;
}
