package hu.csega.superstition.t3dcreator;

public class WireFrameView extends CView
{
	/// <summary>
	/// Required designer variable.
	/// </summary>
	private System.ComponentModel.Container components = null;
	private System.Windows.Forms.Button button1;
	private System.Windows.Forms.Button button2;
	private System.Windows.Forms.Label label1;

	public WireFrameView()
	{
		// This call is required by the Windows.Forms Form Designer.
		InitializeComponent();

		view = Matrix.Identity;
		translation = new Vector3(0f, 0f, 0f);
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
		this.label1 = new System.Windows.Forms.Label();
		this.button1 = new System.Windows.Forms.Button();
		this.button2 = new System.Windows.Forms.Button();
		this.SuspendLayout();
		//
		// label1
		//
		this.label1.Location = new System.Drawing.Point(8, 8);
		this.label1.Name = "label1";
		this.label1.Size = new System.Drawing.Size(72, 16);
		this.label1.TabIndex = 0;
		this.label1.Text = "label1";
		//
		// button1
		//
		this.button1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
		this.button1.Location = new System.Drawing.Point(8, 312);
		this.button1.Name = "button1";
		this.button1.Size = new System.Drawing.Size(80, 23);
		this.button1.TabIndex = 1;
		this.button1.Text = "Hide Grid";
		this.button1.Click += new System.EventHandler(this.button1_Click);
		//
		// button2
		//
		this.button2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
		this.button2.Location = new System.Drawing.Point(336, 312);
		this.button2.Name = "button2";
		this.button2.Size = new System.Drawing.Size(48, 24);
		this.button2.TabIndex = 2;
		this.button2.Text = "Center";
		this.button2.Click += new System.EventHandler(this.button2_Click);
		//
		// WireFrameView
		//
		this.Controls.Add(this.button2);
		this.Controls.Add(this.button1);
		this.Controls.Add(this.label1);
		this.Name = "WireFrameView";
		this.Size = new System.Drawing.Size(392, 344);
		this.MouseUp += new System.Windows.Forms.MouseEventHandler(this.WireFrameView_MouseUp);
		this.MouseMove += new System.Windows.Forms.MouseEventHandler(this.WireFrameView_MouseMove);
		this.MouseDown += new System.Windows.Forms.MouseEventHandler(this.WireFrameView_MouseDown);
		this.ResumeLayout(false);

	}
	#endregion

	private const double zoom_step = 0.1;
	private const double move_step = 1.0;

	private Matrix view, invert;
	private Vector3 translation;
	private double zoom;
	private ButtonControl[] bcontrol;
	private bool show_grid;
	private Vector3 angle;
	private const float rotation =
		(float)Math.PI / (float)18;

	public void SetView(Perspectives perspective)
	{
		view = Matrix.Identity;
		view.M11 = 0f;
		view.M22 = 0f;
		view.M33 = 0f;

		invert = Matrix.Identity;
		invert.M11 = 0f;
		invert.M22 = 0f;
		invert.M33 = 0f;

		switch(perspective)
		{
			case Perspectives.Left:
				view.M31 = 1f;
				view.M22 = -1f;
				invert.M13 = 1f;
				invert.M22 = -1f;
				label1.Text = "Left";
				angle.X = rotation;
				break;

			case Perspectives.Top:
				view.M11 = 1f;
				view.M32 = -1f;
				invert.M11 = 1f;
				invert.M23 = -1f;
				label1.Text = "Top";
				angle.Y = rotation;
				break;

			default:
				view.M11 = 1f;
				view.M22 = -1f;
				invert.M11 = 1f;
				invert.M22 = -1f;
				label1.Text = "Front";
				angle.Z = -rotation;
				break;
		}

	}

	public override void UpdateView(Updates update)
	{
		Invalidate();
	}

	protected override void InitializeView()
	{
		bcontrol = new ButtonControl[3];
		for(int i = 0; i < 3; i++)
		{
			bcontrol[i] = new ButtonControl();
			bcontrol[i].down = false;
		}
		show_grid = true;
		zoom = 50.0;
	}

	protected override void CloseView()
	{

	}

	protected override void OnPaintBackground(PaintEventArgs pevent)
	{
		if(!Initialized) base.OnPaintBackground(pevent);
	}

	protected override void OnPaint(PaintEventArgs args)
	{
		if(!Initialized)
		{
			base.OnPaint(args);
			return;
		}

		Graphics g = args.Graphics;
		g.Clear(Color.LightCyan);
		g.TranslateTransform(
			(float)this.Width / 2f,
			(float)this.Height / 2f);

		Brush brush_normal = new SolidBrush(Color.Black);
		Pen pen_normal = new Pen(brush_normal);
		Brush brush_selected = new SolidBrush(Color.Red);
		Pen pen_selected = new Pen(brush_selected, 3f);
		Brush brush_grid = new SolidBrush(Color.LightGray);
		Pen pen_grid = new Pen(brush_grid);
		Brush brush_origo = new SolidBrush(Color.Orange);
		Pen pen_origo = new Pen(brush_origo, 2f);

		if(show_grid) DrawGrid(pen_grid, g);

		// Drawing origo
		Vector3 vorigo = new Vector3(0f, 0f, 0f);
		Point porigo = Transform(vorigo);
		g.DrawLine(pen_origo, porigo.X - 10, porigo.Y,
			porigo.X + 10, porigo.Y);
		g.DrawLine(pen_origo, porigo.X, porigo.Y - 10,
			porigo.X, porigo.Y + 10);

		Pen pen;
		Point pos;

		Vector3 p1, p2;

		CModel model = (CModel)GetData();
		IPart sel = model.Selected as IPart;
		foreach(CFigure f in model.figures)
		{
			foreach(CTriangle t in f.triangles)
			{
/*
				// test // TODO: Delete
				Vector3 normal = Vector3.Cross(
					t.edges[1].from.position - t.edges[0].from.position,
					t.edges[2].from.position - t.edges[0].from.position);
				normal.Normalize();
*/
				foreach(CEdge e in t.edges)
				{
					// Draw edge
					pen = pen_normal;
					p1 = e.from.position;
					p2 = e.to.position;

/*						// test // TODO: Delete
					float res;
					res = Vector3.Dot(new Vector3(1f, 0f, 0f), normal);
					if(res > 0) p1 = p1*(1f + 1f/p1.Length());
					res = Vector3.Dot(new Vector3(1f, 0f, 0f), normal);
					if(res > 0) p2 = p2*(1f + 1f/p2.Length());
*/
					if(sel != null)
					{
						if(sel.hasPart(e)) pen = pen_selected;
						if(sel.hasPart(e.from))
							p1 = Vector3.TransformCoordinate(p1,
								model.SelectedMatrix);
						if(sel.hasPart(e.to))
							p2 = Vector3.TransformCoordinate(p2,
								model.SelectedMatrix);
					}
					g.DrawLine(pen, Transform(p1), Transform(p2));


					// Draw vertex
					pen = pen_normal;
					if(sel != null)
					{
						if(sel.hasPart(e.from))
						{
							pen = pen_selected;
						}
					}
					pos = Transform(p1);
					g.DrawRectangle(pen, pos.X - 4, pos.Y - 4, 9, 9);

/*

					// test // TODO : delete
					pen = pen_normal;
					if(sel != null)
					{
						if(sel.hasPart(e.to))
						{
							pen = pen_selected;
						}
					}
					pos = Transform(p2);
					g.DrawRectangle(pen, pos.X - 4, pos.Y - 4, 9, 9);

					// test // TODO: delete
					Brush brush_blue = new SolidBrush(Color.Blue);
					Pen pen_blue = new Pen(brush_blue, 3f);
					if((sel != null) && sel.hasPart(t))
					{
						for(int i = 0; i < 3; i++)
						{
							CTriangle tn = t.neighbours[i];
							Vector3 ps = new Vector3(0f, 0f, 0f);
							int s = (i + 1) % 3;
							if(tn.edges[0].from.Equals(t.edges[s].from)) ps = tn.edges[0].from.position;
							if(tn.edges[1].from.Equals(t.edges[s].from)) ps = tn.edges[1].from.position;
							if(tn.edges[2].from.Equals(t.edges[s].from)) ps = tn.edges[2].from.position;

							Vector3 normal2 = Vector3.Cross(
								tn.edges[1].from.position - tn.edges[0].from.position,
								tn.edges[2].from.position - tn.edges[0].from.position);
							normal.Normalize();
							res = Vector3.Dot(new Vector3(1f, 0f, 0f), normal2);
							if(res > 0) ps = ps*(1f + 1f/ps.Length());

							p1 = t.edges[i].from.position;
							p2 = t.edges[s].from.position;

							// test // TODO: Delete
							res = Vector3.Dot(new Vector3(1f, 0f, 0f), normal);
							if(res > 0) p1 = p1*(1f + 1f/p1.Length());
							res = Vector3.Dot(new Vector3(1f, 0f, 0f), normal);
							if(res > 0) p2 = p2*(1f + 1f/p2.Length());

							g.DrawLine(pen_blue, Transform(p1), Transform(ps));
							g.DrawLine(pen_blue, Transform(p2), Transform(ps));
						}

					}
					brush_blue.Dispose();
					pen_blue.Dispose();

*/

				} // end of for:Edge
			} // end of for:Triangle
		} // end of for:Figure

		pen_grid.Dispose();
		brush_grid.Dispose();
		pen_selected.Dispose();
		brush_selected.Dispose();
		pen_normal.Dispose();
		brush_normal.Dispose();
		pen_origo.Dispose();
		brush_origo.Dispose();
	}

	private Point Transform(Vector3 vector)
	{
		vector.TransformCoordinate(view);
		vector.Multiply((float)zoom);
		vector.Add(translation);
		Point ret = new Point((int)vector.X, (int)vector.Y);
		return ret;
	}

	private Vector3 InverseTransform(float x, float y)
	{
		Vector3 ret = new Vector3(x, y, 0f);
		ret.Subtract(translation);
		ret.Multiply(1f / (float)zoom);
		ret.TransformCoordinate(invert);
		return ret;
	}

	private Vector3 SnapToGrid(Vector3 vector)
	{
		CModel model = (CModel) GetData();
		vector.X = (float)(Math.Round(vector.X / model.grid_step) * model.grid_step);
		vector.Y = (float)(Math.Round(vector.Y / model.grid_step) * model.grid_step);
		vector.Z = (float)(Math.Round(vector.Z / model.grid_step) * model.grid_step);
		return vector;
	}

	private void DrawGrid(Pen pen, Graphics g)
	{
		CModel model = (CModel) GetData();
		int fromX = (int)(model.grid_from * zoom + translation.X);
		int fromY = (int)(model.grid_from * zoom + translation.Y);
		int toX = (int)(model.grid_to * zoom + translation.X);
		int toY = (int)(model.grid_to * zoom + translation.Y);
		int gridI;
		for(double grid = model.grid_from; grid < model.grid_to + model.grid_error; grid += model.grid_step)
		{
			gridI = (int)(grid * zoom + translation.X);
			g.DrawLine(pen, gridI, fromY, gridI, toY);
			gridI = (int)(grid * zoom + translation.Y);
			g.DrawLine(pen, fromX, gridI, toX, gridI);
		}
	}

	private void WireFrameView_MouseDown(object sender, System.Windows.Forms.MouseEventArgs e)
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

		if(e.Button == MouseButtons.Left)
		{

			Point position;
			CModel model = (CModel)GetData();
			Point m = new Point(e.X - this.Width / 2,
				e.Y - this.Height / 2);

			foreach(CFigure figure in model.figures)
			{
				foreach(CVertex vertex in figure.vertices)
				{
					position = Transform(vertex.position);
					if((m.X >= position.X - 4) && (m.X <= position.X + 4)
						&& (m.Y >= position.Y - 4) && (m.Y <= position.Y + 4))
					{
						model.Selected = vertex;
						return;
					}
				}

			} // end of for each

		}
	} // End of Function

	private void WireFrameView_MouseUp(object sender, System.Windows.Forms.MouseEventArgs e)
	{
		if(!Initialized) return;
		int idx = -1;
		if(e.Button == MouseButtons.Left) idx = (int)MButton.Left;
		if(e.Button == MouseButtons.Right) idx = (int)MButton.Right;
		if(e.Button == MouseButtons.Middle) idx = (int)MButton.Middle;
		if(idx != -1) bcontrol[idx].down = false;

		if(e.Button == MouseButtons.Left)
		{
			CModel model = GetData() as CModel;
			IPart part = model.Selected as IPart;
			if(part == null) return;
			Operation op = new ScaleSelected(part,
				model.SelectedMatrix);
			model.Memento.Push(op);
			model.SelectedMatrix = Matrix.Identity;
			model.UpdateViews(Updates.Move);
		}
	}

	private void WireFrameView_MouseMove(object sender, System.Windows.Forms.MouseEventArgs e)
	{
		if(!Initialized) return;
		bool invalidated = false;
		int idx;

		idx = (int)MButton.Left;
		if(bcontrol[idx].down)
		{
			CModel model = (CModel)GetData();
			IPart part = model.Selected as IPart;
			if(part != null)
			{
				Vector3 to_point = InverseTransform(e.X, e.Y);
				Point origin = new Point(bcontrol[idx].start_x, bcontrol[idx].start_y);
				Point difference = new Point(e.X - origin.X, e.Y - origin.Y);
				Vector3 from_point = InverseTransform(origin.X, origin.Y);
				Vector3 direction = to_point - from_point;
			//	Vector3 direction = InverseTransform(difference.X, difference.Y);
				Vector3 tr = part.centerPoint();
				Vector3 scale = new Vector3(0f, 0f, 0f);
				if(Math.Abs(tr.X - from_point.X) > double.Epsilon)
					scale.X = (to_point.X - tr.X) / (from_point.X - tr.X);
				if(Math.Abs(tr.Y - from_point.Y) > double.Epsilon)
					scale.Y = (to_point.Y - tr.Y) / (from_point.Y - tr.Y);
				if(Math.Abs(tr.Z - from_point.Z) > double.Epsilon)
					scale.Z = (to_point.Z - tr.Z) / (from_point.Z - tr.Z);

				if(model.SnapToGrid &&
					T3DCreator.GetOperatoion() == 0)
				{
					direction = SnapToGrid(direction);
				}
				if((direction.X != 0f) ||
					(direction.Y != 0f) ||
					(direction.Z != 0f))
				{
					switch(T3DCreator.GetOperatoion())
					{
						case MoveOperation.Rotate:
							model.SelectedMatrix = model.SelectedMatrix *
								Matrix.Translation(-tr) *
								Matrix.RotationX((difference.X + difference.Y) * angle.X * rotation) *
								Matrix.RotationY((difference.X + difference.Y) * angle.Y * rotation) *
								Matrix.RotationZ((difference.X + difference.Y) * angle.Z * rotation) *
								Matrix.Translation(tr);
							break;

						case MoveOperation.Scale:
							model.SelectedMatrix = model.SelectedMatrix *
								Matrix.Translation(-tr) *
								Matrix.Scaling(scale.X, scale.Y, scale.Z) *
								Matrix.Translation(tr);
							break;

						default:
							model.SelectedMatrix = model.SelectedMatrix
								* Matrix.Translation(direction);
							break;
					}

					model.UpdateViews(Updates.Move);
					bcontrol[idx].start_x = e.X;
					bcontrol[idx].start_y = e.Y;
				}
			}

		}

		idx = (int)MButton.Middle;
		if(bcontrol[idx].down)
		{
			zoom += (e.Y - bcontrol[idx].start_y) * zoom_step;
			if(zoom < zoom_step) zoom = zoom_step;
			bcontrol[idx].start_x = e.X;
			bcontrol[idx].start_y = e.Y;
			invalidated = true;
		}

		idx = (int)MButton.Right;
		if(bcontrol[idx].down)
		{
			Vector3 tr = new Vector3(
				(float)((e.X - bcontrol[idx].start_x) * move_step),
				(float)((e.Y - bcontrol[idx].start_y) * move_step),
				0f);
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
		zoom = 20.0;
		Invalidate();
	}

} // End of class Wire Frame View
