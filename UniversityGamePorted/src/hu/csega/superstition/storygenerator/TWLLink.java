package hu.csega.superstition.storygenerator;

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
