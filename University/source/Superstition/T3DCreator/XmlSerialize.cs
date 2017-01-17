using System;
using System.IO;
using System.Xml;
using System.Threading;
using System.Reflection;
using System.Collections;

using Microsoft.DirectX;

namespace T3DCreator
{
	/// <summary>
	/// Summary description for XmlSerialize.
	/// </summary>
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
				info.FieldType == typeof(Vector3))
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

	class XmlReader
	{
		private object[] reference_list;
		private XmlDocument doc;
		private XmlNode root;

		public XmlReader()
		{
		}

		public void Open(string file_name)
		{
			doc = new XmlDocument();
			doc.Load(file_name);
			root = doc.FirstChild;
		}

		public object Read()
		{
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
			if(info == null) return;
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
				info.FieldType == typeof(Vector3))
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

}
