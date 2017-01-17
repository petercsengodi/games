using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Windows.Forms;

using Microsoft.DirectX;
using Microsoft.DirectX.Direct3D;

namespace AnimaToolWindows
{
	using AnimaTool;

	/// <summary>
	/// Summary description for DirectXView.
	/// </summary>
	public class DirectXView : CView
	{
		/// <summary> 
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public DirectXView()
		{
			// This call is required by the Windows.Forms Form Designer.
			InitializeComponent();
			bcontrol = new ButtonControl[3];
			for(int i = 0; i < 3; i++)
			{
				bcontrol[i] = new ButtonControl();
				bcontrol[i].down = false;
			}
		}

		/// <summary> 
		/// Clean up any resources being used.
		/// </summary>
		protected override void Dispose( bool disposing )
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

		#region Component Designer generated code
		/// <summary> 
		/// Required method for Designer support - do not modify 
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.button1 = new System.Windows.Forms.Button();
			this.button2 = new System.Windows.Forms.Button();
			this.SuspendLayout();
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
		#endregion

		private const double alfa_step = 0.01, beta_step = 0.01;
		private const double zoom_step = 0.1;
		private const double move_step = 0.01;

		private Device device;
//		private VertexBuffer vertices;
		private VertexBuffer grid;
//		private int triangle_count = 0;
		private int line_count;

		private double alfa = 0f, beta = 0f;
		private Vector3 translation = new Vector3(0f, 0f, 0f);
		private Vector3 cam_pos = new Vector3(1f, 1f, -1f);
		private ButtonControl[] bcontrol;
		private System.Windows.Forms.Button button1;
		private System.Windows.Forms.Button button2;
		private bool show_grid = true;

		public Device Device 
		{
			get { return device; }
		}

		public override void UpdateView(Updates update)
		{
			Invalidate();			
		}

		#region Old Update View - Commented

		//		public override void UpdateView(Updates update)
		//		{
		//			int color, temp_color,
		//				color_normal = Color.Black.ToArgb(),
		//				color_selected = Color.Red.ToArgb();
		//			int start_index = 0;
		//			vertex_count = 0;
		//			triangle_count = 0;
		//			
		//			CModel model = GetData() as CModel;
		//			foreach(CFigure figure in model.figures)
		//			{
		//				vertex_count += figure.vertices.Count;
		//				triangle_count += figure.triangles.Count;
		//			}
		//
		//			if(vertices != null) 
		//			{
		//				vertices.Dispose();
		//				vertices = null;
		//			}
		//			if(indices != null) 
		//			{
		//				indices.Dispose();
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
		//			foreach(CFigure figure in model.figures)
		//			{
		//				color = color_normal;
		//				if(model.Selected.Equals(figure)) color = color_selected;
		//				temp_color = color;
		//
		//				foreach(CVertex vertex in figure.vertices)
		//				{
		//					if(model.Selected.Equals(vertex)) color = color_selected;
		//					foreach(CTriangle t in vertex.triangles)
		//					{
		//						if(model.Selected.Equals(t))
		//							color = color_selected;
		//					}
		//					foreach(CEdge e in vertex.edges)
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
		//				foreach(CTriangle triangle in figure.triangles)
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

		#endregion

		protected override void InitializeView()
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
				grid.Dispose();
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
						new Vector3((float)(d1), (float)(d2), 
						(float)(model.grid_from)), color));
					stream.Write(new CustomVertex.PositionColored(
						new Vector3((float)(d1), (float)(d2), 
						(float)(model.grid_to)), color));
					stream.Write(new CustomVertex.PositionColored(
						new Vector3((float)(d1), (float)(model.grid_from), 
						(float)(d2)), color));
					stream.Write(new CustomVertex.PositionColored(
						new Vector3((float)(d1), (float)(model.grid_to), 
						(float)(d2)), color));
					stream.Write(new CustomVertex.PositionColored(
						new Vector3((float)(model.grid_from), (float)(d1), 
						(float)(d2)), color));
					stream.Write(new CustomVertex.PositionColored(
						new Vector3((float)(model.grid_to), (float)(d1), 
						(float)(d2)), color));
				}

			stream.Close();
			grid.Unlock();
		}
		
		protected override void CloseView()
		{
			if(device != null)
			{
				device.Dispose();
				device = null;
			}
		}

		protected override void OnPaintBackground(PaintEventArgs pevent)
		{
			if(device == null) base.OnPaintBackground (pevent);
		}


		protected override void OnPaint(PaintEventArgs e)
		{
			if(device == null)
			{
				base.OnPaint (e);
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
				new Vector3(0f, 0f, 0f),
				new Vector3(0f, 1f, 0f));

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

			foreach(CPart part in model.parts)
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

		private void DirectXView_MouseDown(object sender, System.Windows.Forms.MouseEventArgs e)
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

		private void DirectXView_MouseUp(object sender, System.Windows.Forms.MouseEventArgs e)
		{
			if(!Initialized) return;
			int idx = -1;
			if(e.Button == MouseButtons.Left) idx = (int)MButton.Left;
			if(e.Button == MouseButtons.Right) idx = (int)MButton.Right;
			if(e.Button == MouseButtons.Middle) idx = (int)MButton.Middle;
			if(idx != -1) bcontrol[idx].down = false;
		}

		private void DirectXView_MouseMove(object sender, System.Windows.Forms.MouseEventArgs e)
		{
			if(!Initialized) return;
			bool invalidated = false;
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
				Vector3 tr = new Vector3(ftr, ftr, -ftr);
				//				Matrix m = Matrix.RotationY((float)alfa) * 
				//					Matrix.RotationX((float)beta);
				//				m.Invert();
				//				tr.TransformCoordinate(m);
				Vector3 temp = cam_pos + tr;

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
				Vector3 tr = new Vector3(
					(float)((e.X - bcontrol[idx].start_x) * move_step),
					(float)((bcontrol[idx].start_y - e.Y) * move_step),
					0f);

				Matrix m = Matrix.Identity;

				//				m = Matrix.LookAtLH(cam_pos,
				//					new Vector3(0f, 0f, 0f),
				//					new Vector3(0f, 1f, 0f));

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

		private void button1_Click(object sender, System.EventArgs e)
		{
			show_grid = !show_grid;
			if(show_grid) button1.Text = "Hide Grid";
			else button1.Text = "Show Grid";
			Invalidate();
		}

		private void button2_Click(object sender, System.EventArgs e)
		{
			translation = new Vector3(0f, 0f, 0f);
			cam_pos = new Vector3(1f, 1f, -1f);
			alfa = 0f;
			beta = 0f;
			Invalidate();
		}

	} // End of class 

	struct ButtonControl
	{
		public bool down;
		public int start_x, start_y;
	}

	enum MButton
	{
		Left = 0, Right = 1, Middle = 2
	}
}
