package hu.csega.superstition.storygenerator;

public class BmpFileGrabber extends FileGrabber
{
	public BmpFileGrabber()
	: base ("BMP image files (*.bmp)|*.bmp|All Files(*.*)|*.*",
			@"..\..\..\Superstition\bin\textures")
	{
	}
}
