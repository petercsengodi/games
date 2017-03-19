package hu.csega.superstition.storygenerator;

public class Node
{
	private ArrayList links;
	private int x, y;
	private string text;
	private string texture;
	private string element;
	private SpecialProperty special;
	private WeaponType weapon;
	private static int num;

	private Image map = null;

	public static void StaticInitializer(int _num)
	{
		num = _num;
	}

	public delegate void NodeFunction(Node baseNode, Node linkNode);

	public enum SpecialProperty {None, Start, Finish}
	public enum WeaponType {None, Torch, Sword, Gun}

	[Browsable(false)]
	public int X { get { return x; } set { x = value; } }
	[Browsable(false)]
	public int Y { get { return y; } set { y = value; } }

	[Category("Generator Properties"), Description("Node Text")]
	public String Text
	{
		get { return (string)text.Clone(); }
		set { text = (string) value.Clone(); }
	}

	[Category("Game Properties"), Description("Texture Image"),
	Editor(typeof(BmpFileGrabber), typeof(UITypeEditor)), DefaultValue("")]
	public string Texture
	{
		get { return (string)texture.Clone(); }
		set
		{
			texture = (string)value.Clone();
			if((texture == null) || (texture.CompareTo("") == 0)) map = null;
			else map = Bitmap.FromFile(texture);
		}
	}

	[Category("Game Properties"), Description("Element"),
	Editor(typeof(XFileGrabber), typeof(UITypeEditor)), DefaultValue("")]
	public string Element
	{
		get { return (string)element.Clone(); }
		set	{ element = (string)value.Clone(); }
	}


	[Category("Game Properties"), Description("Special")]
	public SpecialProperty Special
	{
		get { return special; }
		set { special = value; }
	}

	[Category("Game Properties"), Description("Weapon")]
	public WeaponType Weapon
	{
		get { return weapon; }
		set { weapon = value; }
	}

	public Node NewLink
	{
		set
		{
			if(links.IndexOf(value) < 0) links.Add(value);
		}
	}

	public Node RemoveLink
	{
		set
		{
			links.Remove(value);
		}
	}

	public NodeFunction Function
	{
		set
		{
			foreach(Object o in links)
				value(this, (Node)o);
		}
	}

	[Category("Read-Only"), Description("Texture View")]
	public Image Map { get { return map; } }

	public Node()
	{
		links = new ArrayList();
		texture = "";
		element = "";
		special = SpecialProperty.None;
		text = (num++).ToString();
	}

	public boolean isConnectedTo(Node node)
	{
		return (links.IndexOf(node) >= 0);
	}

	public void WriteToXMLNode(XmlNode story, XmlDocument StoryDocument, string filePath)
	{
		Node link; XmlNode n, l; XmlAttribute a;
		story.AppendChild(n = StoryDocument.CreateElement("Node"));

		n.Attributes.Append(a = StoryDocument.CreateAttribute("Text"));
		a.Value = text;

		n.Attributes.Append(a = StoryDocument.CreateAttribute("Texture"));
		a.Value = /*RelativePath(filePath, texture)*/ texture;

		n.Attributes.Append(a = StoryDocument.CreateAttribute("Element"));
		a.Value = /*RelativePath(filePath, element)*/ element;

		n.Attributes.Append(a = StoryDocument.CreateAttribute("Special"));
		switch(special)
		{
			case SpecialProperty.Start:
				a.Value = "Start";
				break;

			case SpecialProperty.Finish:
				a.Value = "Finish";
				break;

			default:
				a.Value = "None";
				break;
		}

		n.Attributes.Append(a = StoryDocument.CreateAttribute("Weapon"));
		switch(weapon)
		{
			case WeaponType.Torch:
				a.Value = "Torch";
				break;

			case WeaponType.Sword:
				a.Value = "Sword";
				break;

			case WeaponType.Gun:
				a.Value = "Gun";
				break;

			default:
				a.Value = "None";
				break;
		}

		n.Attributes.Append(a = StoryDocument.CreateAttribute("X"));
		a.Value = x.ToString();

		n.Attributes.Append(a = StoryDocument.CreateAttribute("Y"));
		a.Value = y.ToString();

		foreach(Object o in links)
		{
			link = o as Node;
			n.AppendChild(l = StoryDocument.CreateElement("Link"));
			l.Attributes.Append(a = StoryDocument.CreateAttribute("ref"));
			a.Value = link.Text;
		}
	}

	public void SetUpLinksFromXml(XmlNode thisNode, ArrayList nodes)
	{
		XmlNode xnode; XmlAttribute a; string link; Node lnode;
		IEnumerator enNode = thisNode.ChildNodes.GetEnumerator(), attr;
		while(enNode.MoveNext())
		{
			xnode = enNode.Current as XmlNode;
			attr = xnode.Attributes.GetEnumerator();
			attr.MoveNext(); a = attr.Current as XmlAttribute;
			link = a.Value;
			IEnumerator enList = nodes.GetEnumerator();
			while(enList.MoveNext())
			{
				lnode = enList.Current as Node;
				if(link.CompareTo(lnode.Text) == 0)
				{
					this.NewLink = lnode;
					lnode.NewLink = this;
				}
			}
		}
	}

	public static Node NodeFromXml(XmlNode thisNode)
	{
		Node node = new Node(); XmlAttribute a;
		IEnumerator en = thisNode.Attributes.GetEnumerator();
		while(en.MoveNext())
		{
			a = (XmlAttribute) en.Current;
			if(a.Name.CompareTo("X") == 0) node.X = int.Parse(a.Value);
			else if(a.Name.CompareTo("Y") == 0) node.Y = int.Parse(a.Value);
			else if(a.Name.CompareTo("Text") == 0) node.Text = a.Value;
			else if(a.Name.CompareTo("Texture") == 0) node.Texture = a.Value;
			else if(a.Name.CompareTo("Element") == 0) node.Element = a.Value;

			else if(a.Name.CompareTo("Special") == 0)
			{
				if(a.Value.CompareTo("Start") == 0) node.Special = SpecialProperty.Start;
				else if(a.Value.CompareTo("Finish") == 0) node.Special = SpecialProperty.Finish;
				else node.Special = SpecialProperty.None;
			}

			else if(a.Name.CompareTo("Weapon") == 0)
			{
				if(a.Value.CompareTo("Torch") == 0) node.Weapon = WeaponType.Torch;
				else if(a.Value.CompareTo("Sword") == 0) node.Weapon = WeaponType.Sword;
				else if(a.Value.CompareTo("Gun") == 0) node.Weapon = WeaponType.Gun;
				else node.Weapon = WeaponType.None;
			}
		}

		return node;
	}

}