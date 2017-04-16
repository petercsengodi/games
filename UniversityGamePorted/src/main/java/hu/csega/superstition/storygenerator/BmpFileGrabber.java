package hu.csega.superstition.storygenerator;

public class BmpFileGrabber extends FileGrabber
{
	public BmpFileGrabber() {
		super("BMP image files (*.bmp)|*.bmp|All Files(*.*)|*.*",
				"/res/textures");
	}
}
