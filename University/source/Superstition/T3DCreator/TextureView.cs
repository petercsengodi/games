using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Windows.Forms;

using Microsoft.DirectX;

namespace T3DCreatorWindows
{
	using T3DCreator;

	/// <summary>
	/// Summary description for WireFrameView.
	/// </summary>
	public class TextureView : CView
	{
		/// <summary> 
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public TextureView()
		{
			// This call is required by the Windows.Forms Form Designer.
			InitializeComponent();
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
			// 
			// TextureView
			// 
			this.Name = "TextureView";
			this.Size = new System.Drawing.Size(392, 344);
			this.MouseUp += new System.Windows.Forms.MouseEventHandler(this.WireFrameView_MouseUp);
			this.MouseMove += new System.Windows.Forms.MouseEventHandler(this.WireFrameView_MouseMove);
			this.MouseDown += new System.Windows.Forms.MouseEventHandler(this.WireFrameView_MouseDown);

		}
		#endregion

		private const double move_step = 1.0;
		private ButtonControl[] bcontrol;

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
		}

		protected override void CloseView()
		{
		}

		private CFigure SelectedFigure()
		{
			CModel model = (CModel)GetData();
			IPart part = model.Selected as IPart;
			if(part == null) return null;
			CFigure ret = null;
			foreach(CFigure f in model.figures)
			{
				if(f.hasPart(part)) return f;
			}
			return ret;
		}

		protected override void OnPaintBackground(PaintEventArgs pevent)
		{
			if(!Initialized) base.OnPaintBackground(pevent);
			else 
			{
				CFigure selected_figure = SelectedFigure();

				if((selected_figure != null) && (selected_figure.texID != null))
				{
					pevent.Graphics.DrawImage(selected_figure.texID.Map,
						0, 0, this.Width, this.Height);
					return;
				}

				base.OnPaintBackground(pevent);
			}
		}

		protected override void OnPaint(PaintEventArgs args)
		{
			if(!Initialized) 
			{
				base.OnPaint(args);
				return;
			}

			CModel model = (CModel)GetData();
			CFigure f = SelectedFigure();
			IPart part = model.Selected as IPart;

			if(f == null) return;

			Graphics g = args.Graphics;

			Brush brush_normal = new SolidBrush(Color.LightGray);
			Pen pen_normal = new Pen(brush_normal);
			Brush brush_selected = new SolidBrush(Color.Red);
			Pen pen_selected = new Pen(brush_selected, 3f);

			Pen pen;
			foreach(CTriangle t in f.triangles)
			{
				Vector2 tex1, tex2;
				foreach(CEdge e in t.edges)
				{
					pen = pen_normal;
					tex1 = e.from.texture_coordinates;
					tex2 = e.to.texture_coordinates;
					if(part != null)
					{
						if(part.hasPart(e)) pen = pen_selected;
						if(part.hasPart(e.from))
							tex1 += model.SelectedTexture;
						if(part.hasPart(e.to))
							tex2 += model.SelectedTexture;
					} 
					g.DrawLine(pen, 
						Transform(tex1),
						Transform(tex2));

					pen = pen_normal;
					tex1 = e.from.texture_coordinates;
					if(part != null)
					{
						if(part.hasPart(e.from))
						{
							pen = pen_selected;
							tex1 += model.SelectedTexture;
						}
					}
					Point poz = Transform(tex1);
					g.DrawRectangle(pen, poz.X - 4, poz.Y - 4, 9, 9);
				}

			}

			pen_selected.Dispose();
			brush_selected.Dispose();
			pen_normal.Dispose();
			brush_normal.Dispose();
		}

		private Point Transform(Vector2 vector)
		{
			Point ret = new Point();
			ret.X = (int)(vector.X * (this.Width - 1));
			ret.Y = (int)(vector.Y * (this.Height - 1));
			return ret;
		}

		private Vector2 InverseTransform(int x, int y)
		{
			Vector2 ret = new Vector2(x, y);
			ret.X = (float)x / (this.Width - 1);
			ret.Y = (float)y / (this.Height - 1);
			return ret;
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
				Point m = new Point(e.X, e.Y);

				foreach(CFigure figure in model.figures)
				{
					foreach(CVertex vertex in figure.vertices)
					{
						position = Transform(vertex.texture_coordinates);
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
				CModel model = (CModel)GetData();
				IPart part = model.Selected as IPart;
				if(part == null) return;
				Operation op = new MoveTexture(part, 
					model.SelectedTexture);
				model.SelectedTexture = new Vector2(0f, 0f);
				model.Memento.Push(op);
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
					Vector2 direction = InverseTransform(
						e.X - bcontrol[idx].start_x, 
						e.Y - bcontrol[idx].start_y);
					if((direction.X != 0f) || 
						(direction.Y != 0f))
					{
//						part.moveTexture(direction);
						model.SelectedTexture += direction;
						model.UpdateViews(Updates.Move);
						bcontrol[idx].start_x = e.X;
						bcontrol[idx].start_y = e.Y;
					}
				}

			}

			if(invalidated) Invalidate();
		}

	} // End of class Wire Frame View

}
