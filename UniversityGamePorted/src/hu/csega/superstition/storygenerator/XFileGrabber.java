package hu.csega.superstition.storygenerator;

public class XFileGrabber extends FileGrabber
{
	public XFileGrabber()
	: base ("DirectX Files (*.x)|*.x|All Files(*.*)|*.*",
			@"..\..\..\Superstition\bin\meshes")
	{
	}
}