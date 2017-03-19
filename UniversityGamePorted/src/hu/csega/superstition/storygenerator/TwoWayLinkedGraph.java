package hu.csega.superstition.storygenerator;

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
		for(Object o : nodes) func(o);
	}

	public void DoForAllLinks(TWLFunc func)
	{
		for(Object o : nodes) ((TWLNode)o).DoForAllLinks(func);
	}

	public void AddNode(TWLNode node)
	{
		nodes.Add(node); node.OnInserted();
	}

	public void Link(TWLNode _from, TWLNode _to, TWLLink _link)
	{
		boolean notfound = true;
		for(Object o : nodes) if ((o as TWLNode) == _from) { notfound = false; break; }
		if(notfound){ nodes.Add(_from); _from.OnInserted(); }
		notfound = false;
		for(Object o : nodes) if ((o as TWLNode) == _to) { notfound = false; break; }
		if(notfound){ nodes.Add(_to); _to.OnInserted(); }
		_link.SetLinks(_to, _from);
		_from.AddLink(_link);
		_link.OnLinked();
		_from.OnLinkStart();
		_to.AddLinkEnd(_link);
		_to.OnLinkEnd();
	}

	public boolean IsRelated(TWLNode n1, TWLNode n2)
	{
		return n1.IsLinkedTo(n2) || n2.IsLinkedTo(n1);
	}
}