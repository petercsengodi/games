package hu.csega.superstition.storygenerator;

public class XFileGrabber extends FileGrabber {

	public XFileGrabber() {
		super ("DirectX Files (*.x)|*.x|All Files(*.*)|*.*", "/res/meshes");
	}

	private static final long serialVersionUID = 1L;

}