package hu.csega.superstition.t3dcreator;

import java.text.Normalizer.Form;

import javax.swing.JPanel;

public class Sphere extends JPanel {

	private System.Windows.Forms.Label label1;
	private System.Windows.Forms.Label label2;
	private System.Windows.Forms.Button button1;
	private System.Windows.Forms.NumericUpDown slice;
	private System.Windows.Forms.NumericUpDown stack;
	/// <summary>
	/// Required designer variable.
	/// </summary>
	private System.ComponentModel.Container components = null;

	public Sphere()
	{
		InitializeComponent();
	}

	public Sphere(string Title)
	{
		InitializeComponent();
		this.Text = Title;
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
		this.label1 = new System.Windows.Forms.Label();
		this.label2 = new System.Windows.Forms.Label();
		this.slice = new System.Windows.Forms.NumericUpDown();
		this.stack = new System.Windows.Forms.NumericUpDown();
		this.button1 = new System.Windows.Forms.Button();
		((System.ComponentModel.ISupportInitialize)(this.slice)).BeginInit();
		((System.ComponentModel.ISupportInitialize)(this.stack)).BeginInit();
		this.SuspendLayout();
		//
		// label1
		//
		this.label1.Location = new System.Drawing.Point(8, 8);
		this.label1.Name = "label1";
		this.label1.Size = new System.Drawing.Size(64, 24);
		this.label1.TabIndex = 0;
		this.label1.Text = "Slice:";
		this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
		//
		// label2
		//
		this.label2.Location = new System.Drawing.Point(8, 40);
		this.label2.Name = "label2";
		this.label2.Size = new System.Drawing.Size(64, 24);
		this.label2.TabIndex = 1;
		this.label2.Text = "Stack:";
		this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
		//
		// slice
		//
		this.slice.Location = new System.Drawing.Point(80, 8);
		this.slice.Minimum = new System.Decimal(new int[] {
				3,
				0,
				0,
				0});
		this.slice.Name = "slice";
		this.slice.Size = new System.Drawing.Size(64, 20);
		this.slice.TabIndex = 2;
		this.slice.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
		this.slice.Value = new System.Decimal(new int[] {
				5,
				0,
				0,
				0});
		//
		// stack
		//
		this.stack.Location = new System.Drawing.Point(80, 40);
		this.stack.Minimum = new System.Decimal(new int[] {
				3,
				0,
				0,
				0});
		this.stack.Name = "stack";
		this.stack.Size = new System.Drawing.Size(64, 20);
		this.stack.TabIndex = 3;
		this.stack.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
		this.stack.Value = new System.Decimal(new int[] {
				5,
				0,
				0,
				0});
		//
		// button1
		//
		this.button1.DialogResult = System.Windows.Forms.DialogResult.OK;
		this.button1.Location = new System.Drawing.Point(160, 24);
		this.button1.Name = "button1";
		this.button1.Size = new System.Drawing.Size(88, 24);
		this.button1.TabIndex = 4;
		this.button1.Text = "Ok";
		//
		// Sphere
		//
		this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
		this.ClientSize = new System.Drawing.Size(256, 70);
		this.Controls.Add(this.button1);
		this.Controls.Add(this.stack);
		this.Controls.Add(this.slice);
		this.Controls.Add(this.label2);
		this.Controls.Add(this.label1);
		this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
		this.Name = "Sphere";
		this.Text = "Sphere";
		((System.ComponentModel.ISupportInitialize)(this.slice)).EndInit();
		((System.ComponentModel.ISupportInitialize)(this.stack)).EndInit();
		this.ResumeLayout(false);

	}


	public int Slice
	{
		get { return (int)slice.Value; }
	}

	public int Stack
	{
		get { return (int)stack.Value; }
	}

}