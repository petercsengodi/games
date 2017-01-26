package hu.csega.superstition.game.elements;

public abstract class Primitive extends Element {

	protected Texture face = null;
	protected VertexBuffer buffer = null;
	protected int count, lock_index;
	protected bool foreign_buffer = false;

	protected bool notEffectedByLight = false;
	public bool NotEffectedByLight{ get { return notEffectedByLight; } set { notEffectedByLight = value; }}
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
	public virtual void ReCreate()
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
	abstract public void Initialize(object buf, EventArgs ea);

	/// <summary>
	/// ReInitialization of Primitive.
	/// </summary>
	public virtual void ReInit(){ Initialize(buffer, null); }

	/// <summary>
	/// Dispose function.
	/// </summary>
	@Override
	public override void Dispose()
	{
		engine.RemoveFromDisposeList(buffer);
		if(buffer != null) buffer.Dispose();
		buffer = null;
	}

} // End of Primitive