package hu.csega.superstition.game.network;

public class NetHost
{
	protected int port;
	protected string address;
	protected string name;

	public int Port{ get{ return port; } }
	public string Name{ get { return name; } }

	public NetHost(string file)
	{
		XmlDocument doc = new XmlDocument();
		XmlNode root;
		doc.Load(file);
		root = doc.FirstChild;
		foreach(XmlAttribute attr in root.Attributes)
		{
			if(attr.Name.Equals("name")) name = attr.Value;
			else if(attr.Name.Equals("address")) address = attr.Value;
			else if(attr.Name.Equals("port")) port = int.Parse(attr.Value);
		}
	}

	public IPAddress[] GetHost()
	{
		IPHostEntry entry = Dns.GetHostByName(address);
		return entry.AddressList;
	}
}