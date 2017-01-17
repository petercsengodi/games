using System;
using System.Collections;

using Microsoft.DirectX;

namespace Mathematics
{

	#region TWO WAY LINKED GRAPH
	
	/// <summary>
	/// Mathematical Graph, that's Nodes are linked in both ways
	/// </summary>
	public delegate void TWLFunc(object theme);

	/// <summary>
	/// Memory representation of a two way linked graph.
	/// </summary>
	public class TwoWayLinkedGraph
	{
		private TWLNode actual; 
		
		/// <summary>
		/// Returns the referenc of the currently used Node.
		/// </summary>
		public TWLNode Actual{ get { return actual; } }
		
		private ArrayList nodes;

		public TwoWayLinkedGraph()
		{
			actual = null;
			nodes = new ArrayList(0);
		}

		/// <summary>
		/// Calls a function for every Node in Graph.
		/// </summary>
		/// <param name="func">Functionality for Nodes.</param>
		public void DoForAllNodes(TWLFunc func)
		{
			foreach(object o in nodes) func(o);
		}

		/// <summary>
		/// Calls a function for every Link in Graph.
		/// </summary>
		/// <param name="func">Functionality for Links.</param>
		public void DoForAllLinks(TWLFunc func)
		{
			foreach(object o in nodes) 
				((TWLNode)o).DoForAllLinks(func);
		}

		/// <summary>
		/// Adds a new Node into Graph.
		/// </summary>
		/// <param name="node">Node to be added.</param>
		public void AddNode(TWLNode node)
		{
			nodes.Add(node); node.OnInserted();
		}

		/// <summary>
		/// Makes a link between two Nodes.
		/// </summary>
		/// <param name="_from">Node, that the link should lead from.</param>
		/// <param name="_to">Node, that the link should be directed to.</param>
		/// <param name="_link">A link object.</param>
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

		/// <summary>
		/// Gets, if Nodes are linked somehow.
		/// </summary>
		/// <param name="n1">First Node object.</param>
		/// <param name="n2">Second Node object.</param>
		/// <returns>True, if Nodes are linked.</returns>
		public bool IsRelated(TWLNode n1, TWLNode n2)
		{
			return n1.IsLinkedTo(n2) || n2.IsLinkedTo(n1);
		}
	}

	/// <summary>
	/// Represents a Node in Graph.
	/// </summary>
	public class TWLNode
	{
		protected ArrayList links, linkends;
		protected TWLNode actual; 
		/// <summary>
		/// Returnds currently used Node.
		/// </summary>
		public TWLNode Actual{ get{ return actual; } }

		public TWLNode()
		{
			links = new ArrayList(0);
			linkends = new ArrayList(0);
			actual = null;
		}

		/// <summary>
		/// Calls the function for every link that leads 
		/// from this Node.
		/// </summary>
		/// <param name="func"></param>
		public void DoForAllLinks(TWLFunc func)
		{
			foreach(object o in links) func(o);
		}

		/// <summary>
		/// Adds a link that leads from this Node.
		/// </summary>
		/// <param name="_link"></param>
		public void AddLink(TWLLink _link)
		{
			links.Add(_link);
		}

		/// <summary>
		/// Adds a link ending to this Node.
		/// </summary>
		/// <param name="_link"></param>
		public void AddLinkEnd(TWLLink _link)
		{
			linkends.Add(_link);
		}

		/// <summary>
		/// Gives, if the Node is linked to another Node.
		/// </summary>
		/// <param name="n">Other Node object.</param>
		/// <returns>True, if a link leads from this Node
		/// to the other Node.</returns>
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

		/// <summary>
		/// Is called, when a Node is Insered.
		/// </summary>
		virtual public void OnInserted(){}

		/// <summary>
		/// Is called, when the node is marked as
		/// the destination of a Link.
		/// </summary>
		virtual public void OnLinkEnd(){}

		/// <summary>
		/// Is called, when the node is marked as
		/// the source of a Link.
		/// </summary>
		virtual public void OnLinkStart(){}
	}

	/// <summary>
	/// Represents a Link in Graph.
	/// </summary>
	public class TWLLink
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

		/// <summary>
		/// Sets destination Node.
		/// </summary>
		/// <param name="_To">Node object.</param>
		public void SetLink(TWLNode _To)
		{
			To = _To;
		}

		/// <summary>
		/// Sets Destination and Source Nodes
		/// </summary>
		/// <param name="_To">Destination Node.</param>
		/// <param name="_From">Source Node.</param>
		public void SetLinks(TWLNode _To, TWLNode _From)
		{
			To = _To;
			From = _From;
		}

		/// <summary>
		/// Calls a function for destination Node.
		/// </summary>
		/// <param name="func"></param>
		public void Func(TWLFunc func)
		{
			func(To);
		}

		/// <summary>
		/// Gets Source Node.
		/// </summary>
		/// <returns>Reference of Source Node.</returns>
		public TWLNode GetFrom()
		{
			return From;
		}

		/// <summary>
		/// Gets Destination Node.
		/// </summary>
		/// <returns>Destination Node.</returns>
		public TWLNode GetTo()
		{
			return To;
		}

		/// <summary>
		/// Is called, when two Nodes get linked.
		/// </summary>
		virtual public void OnLinked(){}
	}
	
	#endregion

	/// <summary>
	/// Gathered mathematic functions.
	/// </summary>
	public class StaticMathLibrary
	{
		/// <summary>
		/// Check, if a 2D point is in a square.
		/// </summary>
		/// <param name="x">Point X coordinate.</param>
		/// <param name="y">Point y coordinate.</param>
		/// <param name="x1">Square lower x coordinate.</param>
		/// <param name="y1">Square lower y coordinate.</param>
		/// <param name="x2">Square upper x coordinate.</param>
		/// <param name="y2">Squer upper y coordinate,</param>
		/// <returns>True, if point is in the square.</returns>
		public static bool InSquare(float x, float y, float x1, float y1, float x2, float y2)
		{
			if((x < x1) || (x > x2)) return false;
			if((y < y1) || (y > y2)) return false;
			return true;
		}

		/// <summary>
		/// Checks, if a 3D point is in a box.
		/// </summary>
		/// <param name="p">Point coordinates.</param>
		/// <param name="c1">Box lower coordinates.</param>
		/// <param name="c2">Box upper coordinates.</param>
		/// <returns>True, if point is in the box.</returns>
		public static bool InBox(Vector3 p, Vector3 c1, Vector3 c2)
		{
			if((p.X < c1.X) || (p.X > c2.X)) return false;
			if((p.Y < c1.Y) || (p.Y > c2.Y)) return false;
			if((p.Z < c1.Z) || (p.Z > c2.Z)) return false;
			return true;
		}

		/// <summary>
		/// Gives the center point of a box.
		/// </summary>
		/// <param name="_corner1"></param>
		/// <param name="_corner2"></param>
		/// <returns></returns>
		public static Vector3 Center(Vector3 _corner1, Vector3 _corner2)
		{
			return new Vector3(
				(_corner1.X + _corner2.X) / 2f,
				(_corner1.Y + _corner2.Y) / 2f,
				(_corner1.Z + _corner2.Z) / 2f);
		}
	}

	/// <summary>
	/// Gathered random functions.
	/// </summary>
	public class StaticRandomLibrary
	{
		static private Random random;

		/// <summary>
		/// This function-catalogue should be initialized.
		/// </summary>
		public static void Initialize()
		{
			random = new Random();
		}
		
		/// <summary>
		/// Selects a value between zero and a positive number
		/// using a given distribution.
		/// </summary>
		/// <param name="distribution">Distribution representation.
		/// The upper limit of the positive numbers is the length
		/// of this array.</param>
		/// <returns>Selected int value.</returns>
		public static int SelectValue(double[] distribution)
		{
			return SelectValue(distribution, 0, distribution.Length-1);
		}
		
		/// <summary>
		/// Selects a value between zero and a positive number
		/// using a given distribution.
		/// </summary>
		/// <param name="distribution">Distribution representation.</param>
		/// <param name="min">Lower limit.</param>
		/// <param name="max">Upper limit.</param>
		/// <returns>Selected int value.</returns>
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

		/// <summary>
		/// Makes a yes/no decidion.
		/// </summary>
		/// <param name="probability">Probability for true value.</param>
		/// <returns>Selected value.</returns>
		public static bool Decidion(double probability)
		{
			if(random.NextDouble() <= probability) return true; else return false;
		}

		/// <summary>
		/// Returns a random double value between 0.0 and 1.0.
		/// </summary>
		/// <returns>A random double value between 0.0 and 1.0.</returns>
		public static double DoubleValue()
		{
			return random.NextDouble();
		}

		/// <summary>
		/// Returns a random double value between 0.0 and Scale.
		/// </summary>
		/// <param name="Scale">Upper limit.</param>
		/// <returns>A random double value between 0.0 and Scale.</returns>
		public static double DoubleValue(double Scale)
		{
			return random.NextDouble() * Scale;
		}

		/// <summary>
		/// Returns a random float value between 0f and 1f.
		/// </summary>
		/// <returns>A random float value between 0f and 1f.</returns>
		public static float FloatValue()
		{
			return (float)random.NextDouble();
		}

		/// <summary>
		/// Returns a random float value between 0f and Scale.
		/// </summary>
		/// <param name="Scale">Upper limit.</param>
		/// <returns>A random float value between 0f and Scale.</returns>
		public static float FloatValue(float Scale)
		{
			return (float)random.NextDouble() * Scale;
		}

		/// <summary>
		/// Returns a random float value between Border and Scale.
		/// </summary>
		/// <param name="Scale">Upper limit.</param>
		/// <param name="Border">Lower limit.</param>
		/// <returns>A random float value between Border and Scale.</returns>
		public static float FloatValue(float Border, float Scale)
		{
			return (float)random.NextDouble() * (Scale - Border) + Border;
		}

		/// <summary>
		/// Returns a random int value between 0 and Scale.
		/// </summary>
		/// <param name="Scale">Upper limit.</param>
		/// <returns>A random int value between 0 and Scale.</returns>
		public static int IntValue(int Scale)
		{
			return (int)Math.Floor(random.NextDouble() * Scale);
		}
	}
}
