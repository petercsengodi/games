package hu.csega.superstition.servertester;

public class PlayerView : System.Windows.Forms.UserControl
{
	/// <summary>
	/// Required designer variable.
	/// </summary>
	private System.ComponentModel.Container components = null;

	private PlayerViewFunc func;
	private Vector3 player;
	private Vector3[] crosses;

	public PlayerView()
	{
		InitializeComponent();
	}

	public void Initialize(PlayerViewFunc func)
	{
		this.player = new Vector3(0f, 0f, 0f);
		this.func = func;
	}

	public void SetPositions(Vector3 player, Vector3[] crosses)
	{
		this.player = player;
		this.crosses = crosses;
		Invalidate();
	}

	protected override void OnPaint(PaintEventArgs e)
	{
		base.OnPaint (e);

		float w = (float)this.Width, h = (float)this.Height;
		Graphics g = e.Graphics;
		g.TranslateTransform(w / 2f, h / 2f);
		Brush playerBR = new SolidBrush(Color.Red);
		Pen playerPN = new Pen(playerBR, 3f);
		Brush otherBR = new SolidBrush(Color.Blue);
		Pen otherPN = new Pen(otherBR, 2f);
		Point p11, p12, p21, p22;

		if(crosses != null)
		{
			for(int i = 0; i < crosses.Length; i++)
			{
				p11 = new Point((int)(crosses[i].X - 10f), (int)(crosses[i].Y - 10f));
				p12 = new Point((int)(crosses[i].X - 10f), (int)(crosses[i].Y + 10f));
				p21 = new Point((int)(crosses[i].X + 10f), (int)(crosses[i].Y - 10f));
				p22 = new Point((int)(crosses[i].X + 10f), (int)(crosses[i].Y + 10f));
				g.DrawLine(otherPN, p11, p22);
				g.DrawLine(otherPN, p12, p21);
			}
		}

		p11 = new Point((int)(player.X - 10f), (int)(player.Y - 10f));
		p12 = new Point((int)(player.X - 10f), (int)(player.Y + 10f));
		p21 = new Point((int)(player.X + 10f), (int)(player.Y - 10f));
		p22 = new Point((int)(player.X + 10f), (int)(player.Y + 10f));
		g.DrawLine(playerPN, p11, p22);
		g.DrawLine(playerPN, p12, p21);

		playerPN.Dispose();
		otherPN.Dispose();
		playerBR.Dispose();
		otherBR.Dispose();
	}

	protected override void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown (e);
		float w = (float)this.Width, h = (float)this.Height;
		float x = (e.X - w/2) - player.X;
		float y = (e.Y - w/2) - player.Y;
		func(x, y);
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
		components = new System.ComponentModel.Container();
	}
	#endregion
}