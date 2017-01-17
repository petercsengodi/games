package hu.csega.superstition.t3dcreator;

public class CEdge : IPart
{
	public CVertex from, to;

	public CEdge()
	{
		this.from = null;
		this.to = null;
	}

	public CEdge(CVertex from, CVertex to)
	{
		this.from = from;
		this.to = to;
	}

	public override string ToString()
	{
		if(from == null) return base.ToString();
		return "Edge (" + from.position.X.ToString() + ";" +
			from.position.Y.ToString() + ";" +
			from.position.Z.ToString() + ") -> (" +
			to.position.X.ToString() + ";" +
			to.position.Y.ToString() + ";" +
			to.position.Z.ToString() + ")";
	}

	public float Length()
	{
		return (from.position - to.position).Length();
	}

	#region IPart Members

	public void move(Microsoft.DirectX.Vector3 direction)
	{
		this.to.move(direction);
		this.from.move(direction);
	}

	public void moveTexture(Vector2 direction)
	{
		this.to.moveTexture(direction);
		this.from.moveTexture(direction);
	}

	public bool hasPart(IPart part)
	{
		if(part.Equals(this)) return true;
		if(part.Equals(from)) return true;
		if(part.Equals(to)) return true;
		return false;
	}

	public Vector3 centerPoint()
	{
		Vector3 ret = (from.position + to.position) * 0.5f;
		return ret;
	}

	public void scale(Matrix matrix)
	{
		from.scale(matrix);
		to.scale(matrix);
	}

	#endregion
}