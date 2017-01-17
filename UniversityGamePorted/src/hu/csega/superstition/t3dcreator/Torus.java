package hu.csega.superstition.t3dcreator;

public class Torus : System.Windows.Forms.Form
{
	private System.Windows.Forms.Label label1;
	private System.Windows.Forms.Label label2;
	private System.Windows.Forms.Label label3;
	private System.Windows.Forms.Label label4;
	private System.Windows.Forms.NumericUpDown inner_rad;
	private System.Windows.Forms.NumericUpDown outer_rad;
	private System.Windows.Forms.Button button1;
	private System.Windows.Forms.NumericUpDown side;
	private System.Windows.Forms.NumericUpDown ring;
	/// <summary>
	/// Required designer variable.
	/// </summary>
	private System.ComponentModel.Container components = null;

	public Torus()
	{
		//
		// Required for Windows Form Designer support
		//
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

	#region Windows Form Designer generated code
	/// <summary>
	/// Required method for Designer support - do not modify
	/// the contents of this method with the code editor.
	/// </summary>
	private void InitializeComponent()
	{
		this.label1 = new System.Windows.Forms.Label();
		this.label2 = new System.Windows.Forms.Label();
		this.label3 = new System.Windows.Forms.Label();
		this.label4 = new System.Windows.Forms.Label();
		this.inner_rad = new System.Windows.Forms.NumericUpDown();
		this.outer_rad = new System.Windows.Forms.NumericUpDown();
		this.side = new System.Windows.Forms.NumericUpDown();
		this.ring = new System.Windows.Forms.NumericUpDown();
		this.button1 = new System.Windows.Forms.Button();
		((System.ComponentModel.ISupportInitialize)(this.inner_rad)).BeginInit();
		((System.ComponentModel.ISupportInitialize)(this.outer_rad)).BeginInit();
		((System.ComponentModel.ISupportInitialize)(this.side)).BeginInit();
		((System.ComponentModel.ISupportInitialize)(this.ring)).BeginInit();
		this.SuspendLayout();
		//
		// label1
		//
		this.label1.Location = new System.Drawing.Point(8, 8);
		this.label1.Name = "label1";
		this.label1.Size = new System.Drawing.Size(72, 24);
		this.label1.TabIndex = 0;
		this.label1.Text = "Inner Radius:";
		this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
		//
		// label2
		//
		this.label2.Location = new System.Drawing.Point(8, 40);
		this.label2.Name = "label2";
		this.label2.Size = new System.Drawing.Size(80, 24);
		this.label2.TabIndex = 1;
		this.label2.Text = "Outer Radius:";
		this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
		//
		// label3
		//
		this.label3.Location = new System.Drawing.Point(8, 72);
		this.label3.Name = "label3";
		this.label3.Size = new System.Drawing.Size(32, 24);
		this.label3.TabIndex = 2;
		this.label3.Text = "Side:";
		this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
		//
		// label4
		//
		this.label4.Location = new System.Drawing.Point(8, 104);
		this.label4.Name = "label4";
		this.label4.Size = new System.Drawing.Size(32, 24);
		this.label4.TabIndex = 3;
		this.label4.Text = "Ring:";
		this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
		//
		// inner_rad
		//
		this.inner_rad.DecimalPlaces = 2;
		this.inner_rad.Location = new System.Drawing.Point(80, 8);
		this.inner_rad.Minimum = new System.Decimal(new int[] {
																  1,
																  0,
																  0,
																  131072});
		this.inner_rad.Name = "inner_rad";
		this.inner_rad.Size = new System.Drawing.Size(88, 20);
		this.inner_rad.TabIndex = 4;
		this.inner_rad.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
		this.inner_rad.Value = new System.Decimal(new int[] {
																1,
																0,
																0,
																131072});
		this.inner_rad.ValueChanged += new System.EventHandler(this.inner_rad_ValueChanged);
		//
		// outer_rad
		//
		this.outer_rad.DecimalPlaces = 2;
		this.outer_rad.Location = new System.Drawing.Point(88, 40);
		this.outer_rad.Minimum = new System.Decimal(new int[] {
																  1,
																  0,
																  0,
																  131072});
		this.outer_rad.Name = "outer_rad";
		this.outer_rad.Size = new System.Drawing.Size(80, 20);
		this.outer_rad.TabIndex = 5;
		this.outer_rad.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
		this.outer_rad.Value = new System.Decimal(new int[] {
																1,
																0,
																0,
																0});
		this.outer_rad.ValueChanged += new System.EventHandler(this.outer_rad_ValueChanged);
		//
		// side
		//
		this.side.Location = new System.Drawing.Point(48, 72);
		this.side.Minimum = new System.Decimal(new int[] {
															 3,
															 0,
															 0,
															 0});
		this.side.Name = "side";
		this.side.Size = new System.Drawing.Size(56, 20);
		this.side.TabIndex = 6;
		this.side.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
		this.side.Value = new System.Decimal(new int[] {
														   3,
														   0,
														   0,
														   0});
		//
		// ring
		//
		this.ring.Location = new System.Drawing.Point(48, 104);
		this.ring.Minimum = new System.Decimal(new int[] {
															 3,
															 0,
															 0,
															 0});
		this.ring.Name = "ring";
		this.ring.Size = new System.Drawing.Size(56, 20);
		this.ring.TabIndex = 7;
		this.ring.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
		this.ring.Value = new System.Decimal(new int[] {
														   3,
														   0,
														   0,
														   0});
		//
		// button1
		//
		this.button1.DialogResult = System.Windows.Forms.DialogResult.OK;
		this.button1.Location = new System.Drawing.Point(112, 72);
		this.button1.Name = "button1";
		this.button1.Size = new System.Drawing.Size(56, 48);
		this.button1.TabIndex = 8;
		this.button1.Text = "OK";
		//
		// Torus
		//
		this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
		this.ClientSize = new System.Drawing.Size(176, 136);
		this.Controls.Add(this.button1);
		this.Controls.Add(this.ring);
		this.Controls.Add(this.side);
		this.Controls.Add(this.outer_rad);
		this.Controls.Add(this.inner_rad);
		this.Controls.Add(this.label4);
		this.Controls.Add(this.label3);
		this.Controls.Add(this.label2);
		this.Controls.Add(this.label1);
		this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
		this.Name = "Torus";
		this.Text = "Torus";
		((System.ComponentModel.ISupportInitialize)(this.inner_rad)).EndInit();
		((System.ComponentModel.ISupportInitialize)(this.outer_rad)).EndInit();
		((System.ComponentModel.ISupportInitialize)(this.side)).EndInit();
		((System.ComponentModel.ISupportInitialize)(this.ring)).EndInit();
		this.ResumeLayout(false);

	}
	#endregion

	public float OuterRadius
	{
		get { return (float)outer_rad.Value; }
	}

	public float InnerRadius
	{
		get { return (float)inner_rad.Value; }
	}

	public int Rings
	{
		get { return (int)ring.Value; }
	}

	public int Sides
	{
		get { return (int)side.Value; }
	}

	private void inner_rad_ValueChanged(object sender, System.EventArgs e)
	{
		if(inner_rad.Value > outer_rad.Value) inner_rad.Value = outer_rad.Value;
	}

	private void outer_rad_ValueChanged(object sender, System.EventArgs e)
	{
		if(inner_rad.Value > outer_rad.Value) outer_rad.Value = inner_rad.Value;
	}
}