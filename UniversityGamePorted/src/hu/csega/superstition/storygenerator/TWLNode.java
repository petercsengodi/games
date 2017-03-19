package hu.csega.superstition.storygenerator;

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
		//			for(object o : links) ((TWLLink)o).Func(func);
		for(Object o : links) func(o);
	}

	public void AddLink(TWLLink _link)
	{
		links.Add(_link);
	}

	public void AddLinkEnd(TWLLink _link)
	{
		linkends.Add(_link);
	}

	public boolean IsLinkedTo(TWLNode n)
	{
		boolean isLinked = false;

		for(Object o : links)
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