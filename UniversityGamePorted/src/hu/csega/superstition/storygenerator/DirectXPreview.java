package hu.csega.superstition.storygenerator;

import javax.swing.JPanel;

import org.joml.Vector3f;

import hu.csega.superstition.storygenerator.maze.StructedGridMaze;
import hu.csega.superstition.util.StaticRandomLibrary;

public class DirectXPreview extends JPanel
{
	private System.ComponentModel.IContainer components;
	private TwoWayLinkedGraph Map = null;
	private ArrayList nodes;
	private boolean RightMouseDown = false;
	private Vector3f pos = Vector3.Empty;
	private float angle1 = 0f, angle2 = 0f, dX, dY;
	private Device device = null;
	private Matrix camera;
	private System.Windows.Forms.ImageList imageList1;
	private Vector3f Vup = Vector3.Empty;

	public DirectXPreview(ArrayList nodes)
	{
		//
		// Required for Windows Form Designer support
		//
		InitializeComponent();

		// DirectX Initialization
		PresentParameters pparams = new PresentParameters();
		pparams.Windowed = true;
		pparams.SwapEffect = SwapEffect.Discard;

		try
		{
			pparams.EnableAutoDepthStencil = true;
			pparams.AutoDepthStencilFormat = DepthFormat.D16;

			device = new Device(0, DeviceType.Hardware, this, CreateFlags.SoftwareVertexProcessing, pparams);
			device.DeviceReset += new EventHandler(OnDeviceReset);
			OnDeviceReset(device, null);
			RenderInitializing();
			TextureLibrary.InitializeTextureLibrary(device, imageList1);
		}
		catch(Direct3DXException)
		{
			Dispose(true);
			device = null;
		}

		StaticRandomLibrary.Initialize();
		this.nodes = nodes;
		int count = nodes.Capacity;
		int side = (int)(Math.Ceiling(Math.Sqrt(count ) / 3.0));
		StructedGridMaze maze = new StructedGridMaze(side+1, side+1);

		// Maze Initialization
		ArrayList rooms = new ArrayList(0);
		for(Object o1 : nodes)
		{
			Node node = o1 as Node;
			Room room = maze.SelectRandomRoom();
			room.NODE = node;
			if((node.Texture != null) && (node.Texture.CompareTo("") != 0))
				room.DirectXTexture = node.Texture;

			for(Object o2 : rooms)
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

		Map.DoForAllNodes(new TWLFunc(InitializeRoom));
		Invalidate();
	}

	/// <summary>
	/// Function that runs if Device is reset.
	/// </summary>
	/// <param name="sender">Reference for Device.</param>
	/// <param name="ea">Arguments, not used.</param>
	public void OnDeviceReset(Object sender, EventArgs ea)
	{
		Device device = sender as Device;
		// On Device Reset Stuff
	}

	/// <summary>
	/// Initializing Variables for Rendering;
	/// </summary>
	private void RenderInitializing()
	{
		camera = Matrix.PerspectiveFovLH( (float)(Math.PI / 4.0),
				1.0f, 0.125f /* 1.0f */, 10000.0f);
		Vup = new Vector3f(0f, 1f, 0f);
		device.RenderState.Lighting = false;
		device.RenderState.Clipping = true;
		device.RenderState.CullMode = Cull.None;
	}

	/// <summary>
	/// Clean up any resources being used.
	/// </summary>
	protected void dispose( boolean disposing )
	{
		if( disposing )
		{
			if(components != null)
			{
				components.Dispose();
			}
			TextureLibrary.TextureDispose();
		}
		super.Dispose( disposing );
	}


	/// <summary>
	/// Required method for Designer support - do not modify
	/// the contents of this method with the code editor.
	/// </summary>
	private void InitializeComponent()
	{
		this.components = new System.ComponentModel.Container();
		System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(DirectXPreview));
		this.imageList1 = new System.Windows.Forms.ImageList(this.components);
		//
		// imageList1
		//
		this.imageList1.ImageSize = new System.Drawing.Size(16, 16);
		this.imageList1.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList1.ImageStream")));
		this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
		//
		// DirectXPreview
		//
		this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
		this.ClientSize = new System.Drawing.Size(592, 566);
		this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
		this.Name = "DirectXPreview";
		this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
		this.Text = "DirectX Preview";
		this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.DirectXPreview_KeyDown);
		this.MouseDown += new System.Windows.Forms.MouseEventHandler(this.DirectXPreview_MouseDown);
		this.MouseUp += new System.Windows.Forms.MouseEventHandler(this.DirectXPreview_MouseUp);
		this.Paint += new System.Windows.Forms.PaintEventHandler(this.DirectXPreview_Paint);
		this.MouseMove += new System.Windows.Forms.MouseEventHandler(this.DirectXPreview_MouseMove);

	}


	private void DirectXPreview_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e)
	{
		if(e.Button == MouseButtons.Right)
		{
			RightMouseDown = true;
			dX = e.X;
			dY = e.Y;
		}
	}

	private void DirectXPreview_MouseUp(Object sender, System.Windows.Forms.MouseEventArgs e)
	{
		if(e.Button == MouseButtons.Right)
		{
			RightMouseDown = false;
		}
	}

	private void DirectXPreview_MouseMove(Object sender, System.Windows.Forms.MouseEventArgs e)
	{
		if(RightMouseDown)
		{
			float con = 1000f;
			angle1 += (e.X - dX) / con;
			angle2 += (e.Y - dY) / con;
			dX = e.X;
			dY = e.Y;
			if(angle2 < -(float)(Math.PI / 4)) angle2 = -(float)(Math.PI / 4);
			if(angle2 > (float)(Math.PI / 4)) angle2 = (float)(Math.PI / 4);
			if(angle1 < 0f) angle1 += (float)(Math.PI * 2);
			if(angle1 > (float)(Math.PI * 2)) angle1 -= (float)(Math.PI * 2);

			DirectXPreview_Paint(null, null);
		}
	}

	public void InitializeRoom(Object theme)
	{
		((Room) theme).Initialize(device);
	}

	public void DrawRoom(Object theme)
	{
		((Room) theme).DrawDirectX();
	}

	private void DirectXPreview_Paint(Object sender, System.Windows.Forms.PaintEventArgs e)
	{
		if(device == null) return;
		// Render Initialization
		device.Clear(ClearFlags.Target | ClearFlags.ZBuffer, Color.Cyan, 1.0f, 0);

		device.BeginScene();
		device.Transform.Projection = camera;

		device.Transform.View =  Matrix.LookAtLH(pos,
				new Vector3f((float)(Math.Sin(angle1) * Math.Cos(angle2)) + pos.X,
						pos.Y + (float)(Math.Sin(angle2)),
						(float)(Math.Cos(angle1) * Math.Cos(angle2)) + pos.Z),
				Vup);


		device.SetTexture(0, null);
		Map.DoForAllNodes(new TWLFunc(DrawRoom));
		device.EndScene();
		device.Present();
	}

	private void DirectXPreview_KeyDown(Object sender, System.Windows.Forms.KeyEventArgs e)
	{
		float move = 0.5f;

		if(e.KeyCode == Keys.Left) { pos.X -= move * (float)Math.Cos(angle1); pos.Z += move * (float)Math.Sin(angle1); }
		if(e.KeyCode == Keys.Right) { pos.X += move * (float)Math.Cos(angle1); pos.Z -= move * (float)Math.Sin(angle1); }
		if(e.KeyCode == Keys.PageDown) pos.Y -= move;
		if(e.KeyCode == Keys.PageUp) pos.Y += move;
		if(e.KeyCode == Keys.Down) { pos.X -= move * (float)Math.Sin(angle1); pos.Z -= move * (float)Math.Cos(angle1); }
		if(e.KeyCode == Keys.Up) { pos.X += move * (float)Math.Sin(angle1); pos.Z += move * (float)Math.Cos(angle1); }
		DirectXPreview_Paint(null, null);
	}
}