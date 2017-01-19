package hu.csega.superstition.xml;

public class XmlReader {

	private Object[] reference_list;
	private XmlDocument doc;
	private XmlNode root;

	public XmlReader() {
	}

	public void Open(String file_name) {
		doc = new XmlDocument();
		doc.Load(file_name);
		root = doc.FirstChild;
	}

	public Object Read() {
		int count = root.ChildNodes.Count;
		reference_list = new object[count];
		RInstances();

		for(int i = 0; i < count; i++)
		{
			RObject(reference_list[i], root.ChildNodes[i]);
		}

		return reference_list[0];
	}

	public void RInstances()
	{
		for(int i = 0; i < reference_list.Length; i++)
		{
			XmlNode node = root.ChildNodes[i];
			Type type = Type.GetType(node.Name);

			if(type == typeof(TexID))
			{
				string tex = node.Attributes["name"].Value;
				reference_list[i] = TextureLibrary.Instance().
						LoadImage(tex);
				continue;
			}

			if(type == typeof(MeshID))
			{
				string msh = node.Attributes["name"].Value;
				reference_list[i] = MeshLibrary.Instance().
						LoadMesh(msh);
				continue;
			}

			MethodInfo creator = type.GetMethod("GetNullInstance");
			if((creator != null) && (creator.IsStatic))
			{
				reference_list[i] = creator.Invoke(null, null);
			}
			else
			{
				ConstructorInfo c = type.GetConstructor(new Type[0]);
				reference_list[i] = c.Invoke(new object[0]);
			}

		}
	}

	private void RObject(object obj, XmlNode node)
	{
		int cattr = node.Attributes.Count;
		for(int i = 0; i < cattr; i++)
		{
			if(!node.Attributes[i].Name.Equals("ref"))
			{
				RField(obj, node.Attributes[i]);
			}
		}

		int cnode = node.ChildNodes.Count;
		for(int i = 0; i < cnode; i++)
		{
			RField(obj, node.ChildNodes[i]);
		}
	}

	private void RField(object obj, XmlNode field)
	{
		FieldInfo info = obj.GetType().GetField(field.Name);
		if(info.FieldType == typeof(ArrayList))
		{
			XmlAttribute attr = field.Attributes["ref"];
			int idx = int.Parse(attr.Value);
			object tmp = reference_list[idx];

			ArrayList list = (ArrayList)info.GetValue(obj);
			list.Add(tmp);
		}

		else if(info.FieldType.IsArray)
		{
			XmlAttribute attr = field.Attributes["ref"];
			int idx = int.Parse(attr.Value);
			object tmp = reference_list[idx];

			Array array = (Array)info.GetValue(obj);
			for(int i = 0; i < array.Length; i++)
			{
				if(array.GetValue(i) == null)
				{
					array.SetValue(tmp, i);
					break;
				}
			}
		}

		else if(info.FieldType == typeof(Vector2) ||
				info.FieldType == typeof(Vector3) ||
				info.FieldType == typeof(Matrix))
		{
			XmlNode val = field.FirstChild;
			Type val_type = info.FieldType;
			ConstructorInfo ctor = val_type.GetConstructor(
					new Type[0]);
			object val_obj = ctor.Invoke(new object[0]);

			for(int i = 0; i < val.Attributes.Count; i++)
			{
				RField(val_obj, val.Attributes[i]);
			}

			info.SetValue(obj, val_obj);
		}

		else
		{
			XmlAttribute attr = field.Attributes["ref"];
			if(attr == null)
			{
				info.SetValue(obj, null);
			}
			else
			{
				int idx = int.Parse(attr.Value);
				info.SetValue(obj, reference_list[idx]);
			}
		}

	} // End of function

	private void RField(object obj, XmlAttribute attr)
	{
		FieldInfo info = obj.GetType().GetField(attr.Name);
		if(info != null)
		{
			if(info.FieldType == typeof(double))
			{
				info.SetValue(obj, double.Parse(attr.Value));
			}
			else if(info.FieldType == typeof(float))
			{
				info.SetValue(obj, float.Parse(attr.Value));
			}
			else if(info.FieldType == typeof(int))
			{
				info.SetValue(obj, int.Parse(attr.Value));
			}
			else if(info.FieldType == typeof(string))
			{
				info.SetValue(obj, attr.Value);
			}

		}

	}

} // End of class