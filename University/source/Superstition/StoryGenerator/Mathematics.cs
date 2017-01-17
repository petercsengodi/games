using System;

namespace Mathematics
{

	using System;
	using System.Collections;
	using Microsoft.DirectX;

	#region TWO WAY LINKED GRAPH
	
	/// <summary>
	/// Mathematical Graph, that's Nodes are linked in both ways
	/// </summary>
	delegate void TWLFunc(object theme);

	class TwoWayLinkedGraph
	{
		private TWLNode actual; public TWLNode Actual{ get { return actual; } }
		private ArrayList nodes;

		public TwoWayLinkedGraph()
		{
			actual = null;
			nodes = new ArrayList(0);
		}

		public void DoForAllNodes(TWLFunc func)
		{
			foreach(object o in nodes) func(o);
		}

		public void DoForAllLinks(TWLFunc func)
		{
			foreach(object o in nodes) ((TWLNode)o).DoForAllLinks(func);
		}

		public void AddNode(TWLNode node)
		{
			nodes.Add(node); node.OnInserted();
		}

		public void Link(TWLNode _from, TWLNode _to, TWLLink _link)
		{
			bool notfound = true;
			foreach(object o in nodes) if ((o as TWLNode) == _from) { notfound = false; break; }
			if(notfound){ nodes.Add(_from); _from.OnInserted(); }
			notfound = false;
			foreach(object o in nodes) if ((o as TWLNode) == _to) { notfound = false; break; }
			if(notfound){ nodes.Add(_to); _to.OnInserted(); }
			_link.SetLinks(_to, _from);
			_from.AddLink(_link);
			_link.OnLinked();
			_from.OnLinkStart();
			_to.AddLinkEnd(_link);
			_to.OnLinkEnd();
		}

		public bool IsRelated(TWLNode n1, TWLNode n2)
		{
			return n1.IsLinkedTo(n2) || n2.IsLinkedTo(n1);
		}
	}

	class TWLNode
	{
		protected ArrayList links, linkends;
		protected TWLNode actual; public TWLNode Actual{ get{ return actual; } }

		public TWLNode()
		{
			links = new ArrayList(0);
			linkends = new ArrayList(0);
			actual = null;
		}

		public void DoForAllLinks(TWLFunc func)
		{
			//			foreach(object o in links) ((TWLLink)o).Func(func);
			foreach(object o in links) func(o);
		}

		public void AddLink(TWLLink _link)
		{
			links.Add(_link);
		}

		public void AddLinkEnd(TWLLink _link)
		{
			linkends.Add(_link);
		}

		public bool IsLinkedTo(TWLNode n)
		{
			bool isLinked = false;
			
			foreach(object o in links) 
				if(n == ((TWLLink)o).GetTo())
				{
					isLinked = true;
					break;
				}
			
			return isLinked;
		}

		virtual public void OnInserted(){}
		virtual public void OnLinkEnd(){}
		virtual public void OnLinkStart(){}
	}

	class TWLLink
	{
		protected TWLNode From, To;

		public TWLLink()
		{
			To = null;
			From = null;
		}
		
		public TWLLink(TWLNode _To)
		{
			To = _To;
			From = null;
		}

		public TWLLink(TWLNode _To, TWLNode _From)
		{
			To = _To;
			From = _From;
		}

		public void SetLink(TWLNode _To)
		{
			To = _To;
		}

		public void SetLinks(TWLNode _To, TWLNode _From)
		{
			To = _To;
			From = _From;
		}

		public void Func(TWLFunc func)
		{
			func(To);
		}

		public TWLNode GetFrom()
		{
			return From;
		}

		public TWLNode GetTo()
		{
			return To;
		}

		virtual public void OnLinked(){}
	}
	
	#endregion

	public class StaticMathLibrary
	{
		public static bool InSquare(float x, float y, float x1, float y1, float x2, float y2)
		{
			if((x < x1) || (x > x2)) return false;
			if((y < y1) || (y > y2)) return false;
			return true;
		}

		public static bool InBox(Vector3 p, Vector3 c1, Vector3 c2)
		{
			if((p.X < c1.X) || (p.X > c2.X)) return false;
			if((p.Y < c1.Y) || (p.Y > c2.Y)) return false;
			if((p.Z < c1.Z) || (p.Z > c2.Z)) return false;
			return true;
		}

		public static Vector3 Center(Vector3 _corner1, Vector3 _corner2)
		{
			return new Vector3(
				(_corner1.X + _corner2.X) / 2f,
				(_corner1.Y + _corner2.Y) / 2f,
				(_corner1.Z + _corner2.Z) / 2f);
		}
	}

	public class StaticRandomLibrary
	{
		static private Random random;

		public static void Initialize()
		{
			random = new Random();
		}
		
		public static int SelectValue(double[] distribution)
		{
			return SelectValue(distribution, 0, distribution.Length-1);
		}
		
		public static int SelectValue(double[] distribution, int min, int max)
		{
			double val = random.NextDouble();
			double[] distribution2; int i;
			
			double all = 0.0;
			for(i = min; i <= max; i++) all += distribution[i];
			distribution2 = new double[distribution.Length];
			
			for(i = 0; i < distribution.Length; i++) 
			{
				if((i < min) || (i > max)) distribution2[i] = 0.0;
				else distribution2[i] = distribution[i] / all;
			}
					
			if(val == 0.0) return min; else if(val == 1.0) return max; else
				for(i=0; val >= 0.0; i++) val -= distribution2[i]; return i-1;
		}

		public static bool Decidion(double probability)
		{
			if(random.NextDouble() <= probability) return true; else return false;
		}

		public static double DoubleValue()
		{
			return random.NextDouble();
		}

		public static double DoubleValue(double Scale)
		{
			return random.NextDouble() * Scale;
		}
	}
}
