package hu.csega.superstition.unported.game.network;

public class NetHost
{
	protected int port;
	protected String address;
	protected String name;

	public int Port{ get{ return port; } }
	public String Name{ get { return name; } }

	public NetHost(String file)
	{
		XmlDocument doc = new XmlDocument();
		XmlNode root;
		doc.Load(file);
		root = doc.FirstChild;
		for(XmlAttribute attr : root.Attributes)
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