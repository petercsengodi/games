package hu.csega.superstition.t3dcreator;

public class ColorDisplay extends System.Windows.Forms.UserControl
{
	private System.Windows.Forms.Button button1;
	private System.Windows.Forms.Panel panel1;
	private System.Windows.Forms.Label label1;
	/// <summary>
	/// Required designer variable.
	/// </summary>
	private System.ComponentModel.Container components = null;

	public ColorDisplay()
	{
		// This call is required by the Windows.Forms Form Designer.
		InitializeComponent();

		color = Color.White;

		dialog = new ColorDialog();
		dialog.AnyColor = true;
		dialog.FullOpen = true;
		dialog.Color = color;

		ChangeEvent += new ColorChanged(OnColorChanged);
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


	/// <summary>
	/// Required method for Designer support - do not modify
	/// the contents of this method with the code editor.
	/// </summary>
	private void InitializeComponent()
	{
		this.button1 = new System.Windows.Forms.Button();
		this.panel1 = new System.Windows.Forms.Panel();
		this.label1 = new System.Windows.Forms.Label();
		this.SuspendLayout();
		//
		// button1
		//
		this.button1.Location = new System.Drawing.Point(64, 32);
		this.button1.Name = "button1";
		this.button1.TabIndex = 0;
		this.button1.Text = "Select";
		this.button1.Click += new System.EventHandler(this.button1_Click);
		//
		// panel1
		//
		this.panel1.Location = new System.Drawing.Point(8, 8);
		this.panel1.Name = "panel1";
		this.panel1.Size = new System.Drawing.Size(48, 48);
		this.panel1.TabIndex = 1;
		//
		// label1
		//
		this.label1.Location = new System.Drawing.Point(64, 8);
		this.label1.Name = "label1";
		this.label1.Size = new System.Drawing.Size(72, 24);
		this.label1.TabIndex = 2;
		this.label1.Text = "Color String";
		//
		// ColorDisplay
		//
		this.Controls.Add(this.label1);
		this.Controls.Add(this.panel1);
		this.Controls.Add(this.button1);
		this.Name = "ColorDisplay";
		this.Size = new System.Drawing.Size(150, 64);
		this.ResumeLayout(false);

	}


	public event ColorChanged ChangeEvent;

	private Color color;
	public Color Color{
		get{ return color; }
		set
		{
			color = value;
			dialog.Color = color;
			panel1.BackColor = color;
			panel1.Invalidate();
			ChangeEvent(color);
		}
	}

	public string Label
	{
		get{ return label1.Text; }
		set{ label1.Text = value; }
	}

	private ColorDialog dialog;

	protected virtual void OnColorChanged(Color color){}

	private void button1_Click(object sender, System.EventArgs e)
	{
		dialog.Color = color;
		DialogResult res = dialog.ShowDialog();
		if(res == DialogResult.Cancel) return;
		if(res == DialogResult.No) return;
		color = dialog.Color;
		panel1.BackColor = color;
		panel1.Invalidate();
		ChangeEvent(color);
	}

}