package hu.csega.superstition.game.elements;

public abstract class Primitive extends Element {

	protected Texture face = null;
	protected VertexBuffer buffer = null;
	protected int count, lock_index;
	protected boolean foreign_buffer = false;

	protected boolean notEffectedByLight = false;
	public boolean NotEffectedByLight{ get { return notEffectedByLight; } set { notEffectedByLight = value; }}
	public int Count{ get { return count; } }

	/// <summary>
	/// Constructor.
	/// </summary>
	/// <param name="device">Device used by DirectX Engine.</param>
	public Primitive(Engine engine)
	{
		super(engine);
		buffer = null;
		lock_index = 0;
		foreign_buffer = false;
	}

	/// <summary>
	/// Constructor with foreign buffer.
	/// </summary>
	/// <param name="engine"></param>
	/// <param name="buffer"></param>
	/// <param name="buffer_index"></param>
	public Primitive(Engine engine, VertexBuffer buffer)
	{
		super(engine);
		this.buffer = buffer;
		foreign_buffer = true;
		lock_index = buffer.SizeInBytes;
	}

	/// <summary>
	/// Recreating Primitive.
	/// </summary>
	public void ReCreate()
	{
		if(face == null) return;

		if(!foreign_buffer)
		{
			buffer = new VertexBuffer(typeof(CustomVertex.PositionNormalTextured),
					count, engine.Device, 0, CustomVertex.PositionNormalTextured.Format, Pool.Managed);
			engine.AddToDisposeList(buffer);
		}

		buffer.Created += new EventHandler(Initialize);
		Initialize(buffer, null);
	}

	/// <summary>
	/// Initializing Primitive.
	/// </summary>
	/// <param name="buf">VertexBuffer.</param>
	/// <param name="ea">Not Used.</param>
	abstract public void Initialize(Object buf, EventArgs ea);

	/// <summary>
	/// ReInitialization of Primitive.
	/// </summary>
	public void ReInit(){ Initialize(buffer, null); }

	/// <summary>
	/// Dispose function.
	/// </summary>
	@Override
	public void dispose() {
		engine.RemoveFromDisposeList(buffer);
		if(buffer != null) buffer.dispose();
		buffer = null;
	}

} // End of Primitive