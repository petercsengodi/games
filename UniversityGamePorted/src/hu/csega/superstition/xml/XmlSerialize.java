package hu.csega.superstition.xml;

public class XmlSerialize
{
	private XmlSerialize(){}

	public static void Save(string file_name, object obj)
	{
		XmlWriter writer = new XmlWriter(obj);
		writer.Write();
		writer.Save(file_name);
	}

	public static object Open(string file_name)
	{
		XmlReader reader = new XmlReader();
		reader.Open(file_name);
		return reader.Read();
	}
}