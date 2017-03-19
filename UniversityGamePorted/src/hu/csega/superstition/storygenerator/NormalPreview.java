package hu.csega.superstition.storygenerator;

import javax.swing.JPanel;

import hu.csega.superstition.storygenerator.maze.StructedGridMaze;
import hu.csega.superstition.util.StaticRandomLibrary;

public class NormalPreview extends JPanel
{
	private System.ComponentModel.IContainer components;
	private TwoWayLinkedGraph Map = null;
	private ArrayList nodes;
	private boolean RightMouseDown = false;
	private int scrX = 0, scrY = 0, dX, dY;
	private Bitmap backBuffer = null;
	private System.Windows.Forms.ImageList imageList1;
	private Graphics paint = null;

	public Image RoomImage { get { return imageList1.Images[0]; } }
	public Image CorridorImage { get { return imageList1.Images[1]; } }

	public NormalPreview(ArrayList nodes)
	{
		//
		// Required for Windows Form Designer support
		//
		InitializeComponent();
		StaticRandomLibrary.Initialize();
		backBuffer = new Bitmap(Bounds.Width, Bounds.Height);

		this.nodes = nodes;
		int count = nodes.Capacity;
		int side = (int)(Math.Ceiling(Math.Sqrt(count ) / 3.0));
		StructedGridMaze maze = new StructedGridMaze(side+1, side+1, this);

		ArrayList rooms = new ArrayList(0);

		foreach(Object o1 in nodes)
		{
			Node node = o1 as Node;
			Room room = maze.SelectRandomRoom();
			room.NODE = node;
			if((node.Texture != null) && (node.Texture.CompareTo("") != 0))
				room.Face = node.Texture;

			foreach(Object o2 in rooms)
			{
				Room room2 = o2 as Room;
				if(node.isConnectedTo(room2.NODE))
				{
					maze.Connect(room, room2);
				}
			}

			rooms.Add(room);
		}

		Map = maze.Generate();
	}

	/// <summary>
	/// Clean up any resources being used.
	/// </summary>
	protected void Dispose( boolean disposing )
	{
		if( disposing )
		{
			if(components != null)
			{
				components.Dispose();
			}
		}
		base.Dispose( disposing );
	}



	/// <summary>
	/// Required method for Designer support - do not modify
	/// the contents of this method with the code editor.
	/// </summary>
	private void InitializeComponent()
	{
		this.components = new System.ComponentModel.Container();
		System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(NormalPreview));
		this.imageList1 = new System.Windows.Forms.ImageList(this.components);
		//
		// imageList1
		//
		this.imageList1.ImageSize = new System.Drawing.Size(16, 16);
		this.imageList1.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList1.ImageStream")));
		this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
		//
		// NormalPreview
		//
		this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
		this.ClientSize = new System.Drawing.Size(664, 374);
		this.Name = "NormalPreview";
		this.Text = "Normal Preview";
		this.Resize += new System.EventHandler(this.NormalPreview_Resize);
		this.MouseDown += new System.Windows.Forms.MouseEventHandler(this.NormalPreview_MouseDown);
		this.MouseUp += new System.Windows.Forms.MouseEventHandler(this.NormalPreview_MouseUp);
		this.Paint += new System.Windows.Forms.PaintEventHandler(this.NormalPreview_Paint);
		this.MouseMove += new System.Windows.Forms.MouseEventHandler(this.NormalPreview_MouseMove);

	}


	private void NormalPreview_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e)
	{
		if(e.Button == MouseButtons.Right)
		{
			RightMouseDown = true;
			dX = e.X;
			dY = e.Y;
		}
	}

	private void NormalPreview_MouseUp(Object sender, System.Windows.Forms.MouseEventArgs e)
	{
		if(e.Button == MouseButtons.Right) RightMouseDown = false;
	}

	private void NormalPreview_MouseMove(Object sender, System.Windows.Forms.MouseEventArgs e)
	{
		if(RightMouseDown)
		{
			if(RightMouseDown)
			{
				scrX += (e.X - dX);
				scrY += (e.Y - dY);
				dX = e.X;
				dY = e.Y;
				Invalidate();
			}
		}
	}

	private void NormalPreview_Paint(Object sender, System.Windows.Forms.PaintEventArgs e)
	{
		paint = Graphics.FromImage(backBuffer);
		paint.Clear(Color.White);

		System.Drawing.Drawing2D.Matrix matrix;
		matrix = new System.Drawing.Drawing2D.Matrix(1f, 0f, 0f, 1f, 0f, 0f);
		//			matrix.Translate(Bounds.Width / 2, Bounds.Height / 2);
		matrix.Translate(scrX, scrY);
		matrix.Scale(5f, 5f);
		paint.Transform = matrix;
		Map.DoForAllNodes(new TWLFunc(DrawRoom));
		paint = null;
		e.Graphics.DrawImage(backBuffer, 0, 0);
	}

	public void DrawRoom(Object theme)
	{
		((Room) theme).Draw(paint);
	}


	protected void OnPaintBackground(PaintEventArgs pevent)
	{
	}


	private void NormalPreview_Resize(Object sender, System.EventArgs e)
	{
		backBuffer.Dispose();
		backBuffer = new Bitmap(Width, Height);
	}

}
