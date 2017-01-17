package hu.csega.superstition.xml;

class XmlWriter
{
	private ArrayList reference_list;
	private object obj;
	private XmlDocument doc;
	private int index;

	public XmlWriter(object obj)
	{
		this.reference_list = new ArrayList();
		this.obj = obj;
		this.doc = new XmlDocument();
		this.index = 0;
	}

	public void Save(string file_name)
	{
		doc.Save(file_name);
	}

	public void Write()
	{
		XmlNode root = doc.CreateElement("DATAFILE");
		doc.AppendChild(root);
		reference_list.Add(obj);
		index = 0;
		while(index < reference_list.Count)
		{
			object tmp = reference_list[index];
			Type type = tmp.GetType();

			XmlNode node = doc.CreateElement(type.FullName);
			root.AppendChild(node);
			XmlAttribute a = doc.CreateAttribute("ref");
			a.Value = index.ToString();
			node.Attributes.Append(a);

			FieldInfo[] infos = type.GetFields();
			foreach(FieldInfo i in infos)
			{
				WField(tmp, i, node);
			}
			index++;
		}
	}

	public void WField(object obj, FieldInfo info, XmlNode root)
	{
		int reference;
		if(info.FieldType.IsArray)
		{
			Array array = (Array)info.GetValue(obj);
			foreach(object o in array)
			{
				reference = reference_list.IndexOf(o);
				if(reference == -1)
				{
					reference = reference_list.Count;
					reference_list.Add(o);
				}
				XmlNode node = doc.CreateElement(info.Name);
				XmlAttribute attr = doc.CreateAttribute("ref");
				attr.Value = reference.ToString();
				node.Attributes.Append(attr);
				root.AppendChild(node);
			}
			return;
		}

		else if(info.FieldType == typeof(ArrayList))
		{
			ArrayList list = (ArrayList)info.GetValue(obj);
			foreach(object o in list)
			{
				reference = reference_list.IndexOf(o);
				if(reference == -1)
				{
					reference = reference_list.Count;
					reference_list.Add(o);
				}
				XmlNode node = doc.CreateElement(info.Name);
				XmlAttribute attr = doc.CreateAttribute("ref");
				attr.Value = reference.ToString();
				node.Attributes.Append(attr);
				root.AppendChild(node);
			}
			return;
		}

		else if(info.FieldType == typeof(float) ||
			info.FieldType == typeof(double) ||
			info.FieldType == typeof(int) ||
			info.FieldType == typeof(string))
		{
			XmlAttribute attr = doc.CreateAttribute(info.Name);
			attr.Value = info.GetValue(obj).ToString();
			root.Attributes.Append(attr);
			return;
		}

		else if(info.FieldType == typeof(Vector2) ||
			info.FieldType == typeof(Vector3) ||
			info.FieldType == typeof(Matrix))
		{
			FieldInfo[] array = info.FieldType.GetFields();
			XmlNode tmp = doc.CreateElement(info.Name);
			root.AppendChild(tmp);
			XmlNode n = doc.CreateElement(info.FieldType.FullName);
			tmp.AppendChild(n);

			foreach(FieldInfo i in array)
			{
				object field = info.GetValue(obj);
				WField(field, i, n);
			}
		}

		else
		{
			XmlNode node = doc.CreateElement(info.Name);
			root.AppendChild(node);
			object field = info.GetValue(obj);

			if(field != null)
			{
				XmlAttribute attr = doc.CreateAttribute("ref");
				node.Attributes.Append(attr);

				reference = reference_list.IndexOf(field);
				if(reference == -1)
				{
					reference = reference_list.Count;
					reference_list.Add(field);
				}
				attr.Value = reference.ToString();
			}
		}

	} // End of function

} // End of class