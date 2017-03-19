package hu.csega.superstition.storygenerator;

import java.awt.Image;

import org.joml.Vector3f;

import com.jogamp.opengl.util.texture.Texture;

import hu.csega.superstition.util.StaticMathLibrary;

public class Room extends TWLNode
{
	public final int WALL_LEFT = 1,
			WALL_RIGHT = 2,
			WALL_FRONT = 4,
			WALL_BACK = 8,
			WALL_FLOOR = 16,
			WALL_CEILING = 32,
			WALL_SUM = 63;

	public final int DG_LEFT = 0,
			DG_RIGHT = 180,
			DG_FRONT = 90,
			DG_BACK = 270;

	protected int walls;
	protected Vector3f corner1, corner2;
	protected String face, texString;
	protected Image bitmap;

	protected Texture texture = null;
	protected Device device = null;
	protected VertexBuffer buffer = null;

	public Vector3 Lower{ get{ return corner1;} }
	public Vector3 Upper{ get{ return corner2;} }

	public string Face{ get{ return (string)face.Clone(); }
	set
	{
		face = (string)value.Clone();
		if((face != null) && (face.CompareTo("") != 0))
			bitmap = Bitmap.FromFile(face);
	}
	}

	public string DirectXTexture
	{
		get { return (string)texString.Clone(); }
		set
		{
			texString = (string)value.Clone();
			texture = TextureLibrary.getTexture(texString);
		}
	}

	public String getMark() {
		return face;
	}

	public void setMark(String face) {
		this.face = face;
	}

	public Image BITMAP
	{
		get { return bitmap; }
		set { bitmap = value; }
	}

	private Node node;
	public Node NODE { get { return node; } set { node = value; } }

	public Room(Vector3 _corner1, Vector3 _corner2)
	{
		walls = WALL_SUM;
		corner1 = Vector3.Minimize(_corner1, _corner2);
		corner2 = Vector3.Maximize(_corner1, _corner2);
	}

	public Vector3 CenterOnFloor
	{
		get
		{
			Vector3 average = StaticMathLibrary.Center(corner1, corner2);
			average.Y = corner1.Y + 1.0f;
			return average;
		}
	}

	public void SubWall(int wall_dif)
	{
		walls = walls & (WALL_SUM ^ wall_dif);
	}

	public void Draw(Graphics g)
	{
		if(bitmap == null)
		{
			SolidBrush brush = new SolidBrush(Color.Black);
			g.FillRectangle(brush, corner1.X, corner1.Y, corner2.X - corner1.X, corner2.Y - corner1.Y);
			brush.Dispose();
		} g.DrawImage(bitmap, corner1.X, corner1.Z, corner2.X - corner1.X, corner2.Z - corner1.Z);
	}

	public void Initialize(Device device)
	{
		this.device = device;
		buffer = new VertexBuffer(typeof(CustomVertex.PositionTextured),
				36, this.device, 0, CustomVertex.PositionTextured.Format, Pool.Default);
		buffer.Created += new EventHandler(OnReCreate);
		OnReCreate(buffer, null);
	}

	public void OnReCreate(Object buf, EventArgs ea)
	{
		GraphicsStream stream = buffer.Lock(0, 0, 0);

		stream.Write(new CustomVertex.PositionTextured(corner1.X, corner1.Y, corner1.Z, 0f, 0f));
		stream.Write(new CustomVertex.PositionTextured(corner1.X, corner1.Y, corner2.Z, 0f, 1f));
		stream.Write(new CustomVertex.PositionTextured(corner2.X, corner1.Y, corner1.Z, 1f, 0f));
		stream.Write(new CustomVertex.PositionTextured(corner1.X, corner1.Y, corner2.Z, 0f, 1f));
		stream.Write(new CustomVertex.PositionTextured(corner2.X, corner1.Y, corner1.Z, 1f, 0f));
		stream.Write(new CustomVertex.PositionTextured(corner2.X, corner1.Y, corner2.Z, 1f, 1f));

		stream.Write(new CustomVertex.PositionTextured(corner1.X, corner2.Y, corner1.Z, 0f, 0f));
		stream.Write(new CustomVertex.PositionTextured(corner1.X, corner2.Y, corner2.Z, 0f, 1f));
		stream.Write(new CustomVertex.PositionTextured(corner2.X, corner2.Y, corner1.Z, 1f, 0f));
		stream.Write(new CustomVertex.PositionTextured(corner1.X, corner2.Y, corner2.Z, 0f, 1f));
		stream.Write(new CustomVertex.PositionTextured(corner2.X, corner2.Y, corner1.Z, 1f, 0f));
		stream.Write(new CustomVertex.PositionTextured(corner2.X, corner2.Y, corner2.Z, 1f, 1f));

		stream.Write(new CustomVertex.PositionTextured(corner1.X, corner1.Y, corner1.Z, 0f, 0f));
		stream.Write(new CustomVertex.PositionTextured(corner1.X, corner1.Y, corner2.Z, 0f, 1f));
		stream.Write(new CustomVertex.PositionTextured(corner1.X, corner2.Y, corner1.Z, 1f, 0f));
		stream.Write(new CustomVertex.PositionTextured(corner1.X, corner1.Y, corner2.Z, 0f, 1f));
		stream.Write(new CustomVertex.PositionTextured(corner1.X, corner2.Y, corner1.Z, 1f, 0f));
		stream.Write(new CustomVertex.PositionTextured(corner1.X, corner2.Y, corner2.Z, 1f, 1f));

		stream.Write(new CustomVertex.PositionTextured(corner2.X, corner1.Y, corner1.Z, 0f, 0f));
		stream.Write(new CustomVertex.PositionTextured(corner2.X, corner1.Y, corner2.Z, 0f, 1f));
		stream.Write(new CustomVertex.PositionTextured(corner2.X, corner2.Y, corner1.Z, 1f, 0f));
		stream.Write(new CustomVertex.PositionTextured(corner2.X, corner1.Y, corner2.Z, 0f, 1f));
		stream.Write(new CustomVertex.PositionTextured(corner2.X, corner2.Y, corner1.Z, 1f, 0f));
		stream.Write(new CustomVertex.PositionTextured(corner2.X, corner2.Y, corner2.Z, 1f, 1f));

		stream.Write(new CustomVertex.PositionTextured(corner1.X, corner1.Y, corner1.Z, 0f, 0f));
		stream.Write(new CustomVertex.PositionTextured(corner1.X, corner2.Y, corner1.Z, 0f, 1f));
		stream.Write(new CustomVertex.PositionTextured(corner2.X, corner1.Y, corner1.Z, 1f, 0f));
		stream.Write(new CustomVertex.PositionTextured(corner1.X, corner2.Y, corner1.Z, 0f, 1f));
		stream.Write(new CustomVertex.PositionTextured(corner2.X, corner1.Y, corner1.Z, 1f, 0f));
		stream.Write(new CustomVertex.PositionTextured(corner2.X, corner2.Y, corner1.Z, 1f, 1f));

		stream.Write(new CustomVertex.PositionTextured(corner1.X, corner1.Y, corner2.Z, 0f, 0f));
		stream.Write(new CustomVertex.PositionTextured(corner1.X, corner2.Y, corner2.Z, 0f, 1f));
		stream.Write(new CustomVertex.PositionTextured(corner2.X, corner1.Y, corner2.Z, 1f, 0f));
		stream.Write(new CustomVertex.PositionTextured(corner1.X, corner2.Y, corner2.Z, 0f, 1f));
		stream.Write(new CustomVertex.PositionTextured(corner2.X, corner1.Y, corner2.Z, 1f, 0f));
		stream.Write(new CustomVertex.PositionTextured(corner2.X, corner2.Y, corner2.Z, 1f, 1f));

		buffer.Unlock();
	}

	public void DrawDirectX()
	{
		if(device == null) return;
		device.SetTexture(0, texture);
		device.SetStreamSource(0, buffer, 0);
		device.VertexFormat = CustomVertex.PositionTextured.Format;
		device.DrawPrimitives(PrimitiveType.TriangleList, 0, 12);
	}
}