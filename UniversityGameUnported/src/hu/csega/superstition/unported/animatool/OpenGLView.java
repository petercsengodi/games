package hu.csega.superstition.unported.animatool;

import javax.swing.JButton;

import org.joml.Vector3f;

import hu.csega.superstition.animation.ButtonControl;
import hu.csega.superstition.tools.presentation.ToolView;

public class OpenGLView extends ToolView {

	public OpenGLView()
	{
		// This call is required by the Windows.Forms Form Designer.
		InitializeComponent();
		bcontrol = new ButtonControl[3];
		for(int i = 0; i < 3; i++) {
			bcontrol[i] = new ButtonControl();
			bcontrol[i].down = false;
		}
	}

	/// <summary>
	/// Required method for Designer support - do not modify
	/// the contents of this method with the code editor.
	/// </summary>
	private void InitializeComponent()
	{
		this.button1 = new JButton();
		this.button2 = new JButton();
		//		this.SuspendLayout();
		//
		// button1
		//
		this.button1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
		this.button1.Location = new System.Drawing.Point(8, 40);
		this.button1.Name = "button1";
		this.button1.Size = new System.Drawing.Size(80, 23);
		this.button1.TabIndex = 0;
		this.button1.Text = "Hide Grid";
		this.button1.Click += new System.EventHandler(this.button1_Click);
		//
		// button2
		//
		this.button2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
		this.button2.Location = new System.Drawing.Point(416, 40);
		this.button2.Name = "button2";
		this.button2.Size = new System.Drawing.Size(48, 24);
		this.button2.TabIndex = 1;
		this.button2.Text = "Center";
		this.button2.Click += new System.EventHandler(this.button2_Click);
		//
		// DirectXView
		//
		this.Controls.Add(this.button2);
		this.Controls.Add(this.button1);
		this.Name = "DirectXView";
		this.Size = new System.Drawing.Size(472, 72);
		this.MouseUp += new System.Windows.Forms.MouseEventHandler(this.DirectXView_MouseUp);
		this.MouseMove += new System.Windows.Forms.MouseEventHandler(this.DirectXView_MouseMove);
		this.MouseDown += new System.Windows.Forms.MouseEventHandler(this.DirectXView_MouseDown);
		this.ResumeLayout(false);

	}

	private static final double alfa_step = 0.01, beta_step = 0.01;
	private static final double zoom_step = 0.1;
	private static final double move_step = 0.01;

	private Device device;
	//	private VertexBuffer vertices;
	private VertexBuffer grid;
	//	private int triangle_count = 0;
	private int line_count;

	private double alfa = 0f, beta = 0f;
	private Vector3f translation = new Vector3f(0f, 0f, 0f);
	private Vector3f cam_pos = new Vector3f(1f, 1f, -1f);
	private ButtonControl[] bcontrol;
	private System.Windows.Forms.Button button1;
	private System.Windows.Forms.Button button2;
	private boolean show_grid = true;

	public Device Device
	{
		get { return device; }
	}

	public void UpdateView(Updates update)
	{
		Invalidate();
	}

	//		public void UpdateView(Updates update)
	//		{
	//			int color, temp_color,
	//				color_normal = Color.Black.ToArgb(),
	//				color_selected = Color.Red.ToArgb();
	//			int start_index = 0;
	//			vertex_count = 0;
	//			triangle_count = 0;
	//
	//			CModel model = GetData() as CModel;
	//			for(CFigure figure : model.figures)
	//			{
	//				vertex_count += figure.vertices.Count;
	//				triangle_count += figure.triangles.Count;
	//			}
	//
	//			if(vertices != null)
	//			{
	//				vertices.dispose();
	//				vertices = null;
	//			}
	//			if(indices != null)
	//			{
	//				indices.dispose();
	//				indices = null;
	//			}
	//
	//			if(triangle_count == 0)
	//			{
	//				Invalidate();
	//				return;
	//			}
	//
	//			vertices = new VertexBuffer(
	//				typeof(CustomVertex.PositionColoredTextured),
	//				vertex_count, device, 0,
	//				CustomVertex.PositionColoredTextured.Format,
	//				Pool.Managed);
	//			indices = new IndexBuffer(typeof(System.Int32),
	//				triangle_count * 3, device, Usage.None, Pool.Managed);
	//
	//			GraphicsStream vertex_stream = vertices.Lock(0, 0, 0);
	//			GraphicsStream index_stream = indices.Lock(0, 0, 0);
	//
	//			for(CFigure figure : model.figures)
	//			{
	//				color = color_normal;
	//				if(model.Selected.Equals(figure)) color = color_selected;
	//				temp_color = color;
	//
	//				for(CVertex vertex : figure.vertices)
	//				{
	//					if(model.Selected.Equals(vertex)) color = color_selected;
	//					for(CTriangle t : vertex.triangles)
	//					{
	//						if(model.Selected.Equals(t))
	//							color = color_selected;
	//					}
	//					for(CEdge e : vertex.edges)
	//					{
	//						if(model.Selected.Equals(e))
	//							color = color_selected;
	//					}
	//
	//					vertex_stream.Write(new CustomVertex.PositionColoredTextured(
	//						vertex.position, color, vertex.texture_coordinates.X,
	//						vertex.texture_coordinates.Y));
	//
	//					color = temp_color;
	//				}
	//
	//				for(CTriangle triangle : figure.triangles)
	//				{
	//					index_stream.Write(start_index +
	//						figure.vertices.IndexOf(triangle.vertices[0]));
	//					index_stream.Write(start_index +
	//						figure.vertices.IndexOf(triangle.vertices[1]));
	//					index_stream.Write(start_index +
	//						figure.vertices.IndexOf(triangle.vertices[2]));
	//				}
	//
	//				start_index += figure.vertices.Count;
	//			}
	//
	//			vertex_stream.Close();
	//			vertices.Unlock();
	//			index_stream.Close();
	//			indices.Unlock();
	//
	//			Invalidate();
	//		}

	protected void InitializeView()
	{
		PresentParameters pp = new PresentParameters();
		pp.AutoDepthStencilFormat = DepthFormat.D16;
		pp.EnableAutoDepthStencil = true;
		pp.SwapEffect = SwapEffect.Discard;
		pp.Windowed = true;

		device = new Device(0, DeviceType.Hardware, this,
				CreateFlags.HardwareVertexProcessing, pp);

		TextureLibrary.Initialize(device);

		UpdateGrid();

		UpdateView();
	}

	public void UpdateGrid()
	{
		if(grid != null)
		{
			grid.dispose();
			grid = null;
		}

		int color = Color.Gray.ToArgb();

		CModel model = (CModel) GetData();

		int count = 0;
		double d = model.grid_from;
		while(d < model.grid_to + model.grid_error)
		{
			count ++;
			d += model.grid_step;
		}
		line_count = count*count * 3;

		grid = new VertexBuffer(
				typeof(CustomVertex.PositionColored),
				line_count * 2, device, 0,
				CustomVertex.PositionColored.Format,
				Pool.Managed);

		GraphicsStream stream = grid.Lock(0, 0, 0);

		for(double d1 = model.grid_from; d1 < model.grid_to + model.grid_error; d1 += model.grid_step)
			for(double d2 = model.grid_from; d2 < model.grid_to + model.grid_error; d2 += model.grid_step)
			{
				stream.Write(new CustomVertex.PositionColored(
						new Vector3f((float)(d1), (float)(d2), (float)(model.grid_from)), color));
				stream.Write(new CustomVertex.PositionColored(
						new Vector3f((float)(d1), (float)(d2), (float)(model.grid_to)), color));
				stream.Write(new CustomVertex.PositionColored(
						new Vector3f((float)(d1), (float)(model.grid_from), (float)(d2)), color));
				stream.Write(new CustomVertex.PositionColored(
						new Vector3f((float)(d1), (float)(model.grid_to), (float)(d2)), color));
				stream.Write(new CustomVertex.PositionColored(
						new Vector3f((float)(model.grid_from), (float)(d1), (float)(d2)), color));
				stream.Write(new CustomVertex.PositionColored(
						new Vector3f((float)(model.grid_to), (float)(d1), (float)(d2)), color));
			}

		stream.Close();
		grid.Unlock();
	}

	protected void CloseView()
	{
		if(device != null)
		{
			device.dispose();
			device = null;
		}
	}

	protected void OnPaintBackground(PaintEventArgs pevent)
	{
		if(device == null) super.OnPaintBackground (pevent);
	}


	protected void OnPaint(PaintEventArgs e)
	{
		if(device == null)
		{
			super.OnPaint (e);
			return;
		}

		device.Clear(ClearFlags.Target | ClearFlags.ZBuffer,
				Color.White, 1.0f, 0);

		device.BeginScene();
		device.RenderState.Lighting = false;
		device.RenderState.CullMode = Cull.CounterClockwise;
		device.RenderState.Wrap0 = WrapCoordinates.Zero;

		device.Transform.View = Matrix.LookAtLH(
				cam_pos,
				new Vector3f(0f, 0f, 0f),
				new Vector3f(0f, 1f, 0f));

		device.Transform.View = Matrix.RotationY((float)alfa) *
				Matrix.RotationX((float)beta) *
				device.Transform.View;

		device.Transform.View = Matrix.Translation(translation) *
				device.Transform.View;

		device.Transform.Projection = Matrix.PerspectiveFovLH(
				(float)(Math.PI/2),
				(float)((double)this.Width / this.Height),
				0.125f,
				1000f);

		if(show_grid)
		{
			device.VertexFormat = CustomVertex.PositionColored.Format;
			device.SetStreamSource(0, grid, 0);
			device.DrawPrimitives(PrimitiveType.LineList, 0, line_count);
		}

		CModel model = GetData() as CModel;

		for(CPart part in model.parts)
		{
			if(part.mesh_file == null) continue;
			if(part.GetMesh() == null) continue;
			if(part.GetMesh().Mesh == null) continue;

			device.Transform.World =
					part.model_transform[model.scene] *
					Matrix.Translation(part.center_point[model.scene]);

			for(int i = 0; i < part.GetMesh().Subsets; i++)
			{
				TexID id = part.GetMesh().Textures[i];
				if(id != null) device.SetTexture(0, id.Texture);
				part.GetMesh().Mesh.DrawSubset(i);
			}

		}

		device.SetTexture(0, null);
		device.Transform.World = Matrix.Identity;

		device.EndScene();
		device.Present();
	}

	private void DirectXView_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e)
	{
		if(!Initialized) return;
		int idx = -1;
		if(e.Button == MouseButtons.Left) idx = (int)MButton.Left;
		if(e.Button == MouseButtons.Right) idx = (int)MButton.Right;
		if(e.Button == MouseButtons.Middle) idx = (int)MButton.Middle;
		if(idx == -1) return;
		bcontrol[idx].down = true;
		bcontrol[idx].start_x = e.X;
		bcontrol[idx].start_y = e.Y;
	}

	private void DirectXView_MouseUp(Object sender, System.Windows.Forms.MouseEventArgs e)
	{
		if(!Initialized) return;
		int idx = -1;
		if(e.Button == MouseButtons.Left) idx = (int)MButton.Left;
		if(e.Button == MouseButtons.Right) idx = (int)MButton.Right;
		if(e.Button == MouseButtons.Middle) idx = (int)MButton.Middle;
		if(idx != -1) bcontrol[idx].down = false;
	}

	private void DirectXView_MouseMove(Object sender, System.Windows.Forms.MouseEventArgs e)
	{
		if(!Initialized) return;
		boolean invalidated = false;
		int idx;

		idx = (int)MButton.Left;
		if(bcontrol[idx].down)
		{
			alfa += (e.X - bcontrol[idx].start_x) * alfa_step;
			if(alfa < 0.0) alfa += Math.PI*2.0;
			else if(alfa > Math.PI*2.0) alfa -= Math.PI*2.0;
			beta += (e.Y - bcontrol[idx].start_y) * beta_step;
			if(beta < 0.0) beta += Math.PI*2.0;
			else if(beta > Math.PI*2.0) beta -= Math.PI*2.0;
			bcontrol[idx].start_x = e.X;
			bcontrol[idx].start_y = e.Y;
			invalidated = true;
		}

		idx = (int)MButton.Middle;
		if(bcontrol[idx].down)
		{
			//				zoom += (e.Y - bcontrol[idx].start_y) * zoom_step;
			//				if(zoom < 0.0 + zoom_step) zoom = 0.0 + zoom_step;
			//				bcontrol[idx].start_x = e.X;
			//				bcontrol[idx].start_y = e.Y;
			//				invalidated = true;

			float ftr =
					(float)((e.X - bcontrol[idx].start_x) * zoom_step) +
					(float)((e.Y - bcontrol[idx].start_y) * zoom_step);
			Vector3f tr = new Vector3f(ftr, ftr, -ftr);
			//				Matrix m = Matrix.RotationY((float)alfa) *
			//					Matrix.RotationX((float)beta);
			//				m.Invert();
			//				tr.TransformCoordinate(m);
			Vector3f temp = cam_pos + tr;

			if((temp.Length() >= zoom_step) && (temp.Z < 0f))
			{
				cam_pos = temp;
			}

			bcontrol[idx].start_x = e.X;
			bcontrol[idx].start_y = e.Y;
			invalidated = true;
		}

		idx = (int)MButton.Right;
		if(bcontrol[idx].down)
		{
			Vector3f tr = new Vector3f(
					(float)((e.X - bcontrol[idx].start_x) * move_step),
					(float)((bcontrol[idx].start_y - e.Y) * move_step),
					0f);

			Matrix m = Matrix.Identity;

			//				m = Matrix.LookAtLH(cam_pos,
			//					new Vector3f(0f, 0f, 0f),
			//					new Vector3f(0f, 1f, 0f));

			m = Matrix.RotationY((float)alfa) *
					Matrix.RotationX((float)beta) * m;

			//				m = Matrix.Translation(translation) * m;

			//				Matrix m = Matrix.RotationY((float)alfa) *
			//					Matrix.RotationX((float)beta);
			m.Invert();
			tr.TransformCoordinate(m);
			translation += tr;
			bcontrol[idx].start_x = e.X;
			bcontrol[idx].start_y = e.Y;
			invalidated = true;
		}

		if(invalidated) Invalidate();
	}

	private void button1_Click(Object sender, System.EventArgs e)
	{
		show_grid = !show_grid;
		if(show_grid) button1.Text = "Hide Grid";
		else button1.Text = "Show Grid";
		Invalidate();
	}

	private void button2_Click(Object sender, System.EventArgs e)
	{
		translation = new Vector3f(0f, 0f, 0f);
		cam_pos = new Vector3f(1f, 1f, -1f);
		alfa = 0f;
		beta = 0f;
		Invalidate();
	}

	@Override
	public void updateView(Updates update) {
		// TODO Auto-generated method stub

	}

} // End of class