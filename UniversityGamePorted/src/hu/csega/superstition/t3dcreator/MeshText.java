package hu.csega.superstition.t3dcreator;

import javax.swing.JPanel;

public class MeshText extends JPanel {

	private System.Windows.Forms.Label label1;
	private System.Windows.Forms.Label label2;
	private System.Windows.Forms.Button font_button;
	private System.Windows.Forms.Label label3;
	private System.Windows.Forms.NumericUpDown num_deviation;
	private System.Windows.Forms.Button button1;
	/// <summary>
	/// Required designer variable.
	/// </summary>
	private System.ComponentModel.Container components = null;
	private System.Windows.Forms.TextBox text_box;
	private FontDialog dialog;

	public MeshText()
	{
		//
		// Required for Windows Form Designer support
		//
		InitializeComponent();
		dialog = new FontDialog();
		font = new Font("Arial", 12f);
		font_button.Text = font.Name + " " + font.Size;
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
			if(dialog == null) dialog.Dispose();
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
		this.text_box = new System.Windows.Forms.TextBox();
		this.label2 = new System.Windows.Forms.Label();
		this.font_button = new System.Windows.Forms.Button();
		this.label3 = new System.Windows.Forms.Label();
		this.num_deviation = new System.Windows.Forms.NumericUpDown();
		this.button1 = new System.Windows.Forms.Button();
		((System.ComponentModel.ISupportInitialize)(this.num_deviation)).BeginInit();
		this.SuspendLayout();
		//
		// label1
		//
		this.label1.Location = new System.Drawing.Point(8, 8);
		this.label1.Name = "label1";
		this.label1.Size = new System.Drawing.Size(40, 24);
		this.label1.TabIndex = 0;
		this.label1.Text = "Text:";
		this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
		//
		// text_box
		//
		this.text_box.Location = new System.Drawing.Point(48, 8);
		this.text_box.Name = "text_box";
		this.text_box.Size = new System.Drawing.Size(152, 20);
		this.text_box.TabIndex = 1;
		this.text_box.Text = "Hello world!";
		//
		// label2
		//
		this.label2.Location = new System.Drawing.Point(8, 40);
		this.label2.Name = "label2";
		this.label2.Size = new System.Drawing.Size(40, 24);
		this.label2.TabIndex = 2;
		this.label2.Text = "Font:";
		this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
		//
		// font_button
		//
		this.font_button.Location = new System.Drawing.Point(48, 40);
		this.font_button.Name = "font_button";
		this.font_button.Size = new System.Drawing.Size(152, 24);
		this.font_button.TabIndex = 3;
		this.font_button.Text = "none";
		this.font_button.Click += new System.EventHandler(this.font_button_Click);
		//
		// label3
		//
		this.label3.Location = new System.Drawing.Point(8, 72);
		this.label3.Name = "label3";
		this.label3.Size = new System.Drawing.Size(64, 24);
		this.label3.TabIndex = 4;
		this.label3.Text = "Deviation:";
		this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
		//
		// num_deviation
		//
		this.num_deviation.DecimalPlaces = 2;
		this.num_deviation.Location = new System.Drawing.Point(80, 72);
		this.num_deviation.Name = "num_deviation";
		this.num_deviation.RightToLeft = System.Windows.Forms.RightToLeft.No;
		this.num_deviation.Size = new System.Drawing.Size(64, 20);
		this.num_deviation.TabIndex = 5;
		this.num_deviation.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
		//
		// button1
		//
		this.button1.DialogResult = System.Windows.Forms.DialogResult.OK;
		this.button1.Location = new System.Drawing.Point(152, 72);
		this.button1.Name = "button1";
		this.button1.Size = new System.Drawing.Size(48, 24);
		this.button1.TabIndex = 6;
		this.button1.Text = "OK";
		//
		// MeshText
		//
		this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
		this.ClientSize = new System.Drawing.Size(210, 104);
		this.Controls.Add(this.button1);
		this.Controls.Add(this.num_deviation);
		this.Controls.Add(this.label3);
		this.Controls.Add(this.font_button);
		this.Controls.Add(this.label2);
		this.Controls.Add(this.text_box);
		this.Controls.Add(this.label1);
		this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
		this.Name = "MeshText";
		this.Text = "3D Text";
		((System.ComponentModel.ISupportInitialize)(this.num_deviation)).EndInit();
		this.ResumeLayout(false);

	}
	#endregion

	private Font font;
	public Font SelectedFont
	{
		get { return font; }
	}

	public float Deviation
	{
		get { return (float)num_deviation.Value; }
	}

	public string Value
	{
		get{ return text_box.Text; }
	}

	private void font_button_Click(object sender, System.EventArgs e)
	{
		DialogResult res = dialog.ShowDialog();
		if((res == DialogResult.OK) || (res == DialogResult.Yes))
		{
			font = dialog.Font;
			font_button.Text = font.Name + " " + font.Size;
		}
	}
}