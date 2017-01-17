using System;
using System.IO;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Windows.Forms;

using Microsoft.DirectX;

using GameLib;
using AnimaTool;

namespace AnimaToolWindows
{
	/// <summary>
	/// Summary description for PartEditor.
	/// </summary>
	public class PartEditor : CView
	{
		private System.Windows.Forms.TextBox file_box;
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.Button change_button;
		private System.Windows.Forms.Label label2;
		private System.Windows.Forms.NumericUpDown x_num;
		private System.Windows.Forms.NumericUpDown y_num;
		private System.Windows.Forms.NumericUpDown z_num;
		private System.Windows.Forms.Label label3;
		private System.Windows.Forms.Label label4;
		private System.Windows.Forms.Label label5;
		private System.Windows.Forms.Button add_button;
		private System.Windows.Forms.Button remove_button;
		private System.Windows.Forms.ListView conn_list;
		
		private OpenFileDialog dialog;
		private System.Windows.Forms.Button up_button;
		private System.Windows.Forms.Button down_button;
		private System.Windows.Forms.Button left_button;
		private System.Windows.Forms.Button right_button;
		private System.Windows.Forms.Button scale_up_button;
		private System.Windows.Forms.Button scale_down_button;
		private System.Windows.Forms.Button clear_matrix_button;
		private System.Windows.Forms.Button back_button;
		private System.Windows.Forms.Button front_button;
		private System.Windows.Forms.Label label6;
		private System.Windows.Forms.Label label7;
		private System.Windows.Forms.ComboBox connect_box;
		private System.Windows.Forms.ComboBox index_box;
		private System.Windows.Forms.TextBox var_box;
		private System.Windows.Forms.Label label8;
		private System.Windows.Forms.Button edt_button;

		/// <summary> 
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public PartEditor()
		{
			// This call is required by the Windows.Forms Form Designer.
			InitializeComponent();

			dialog = new OpenFileDialog();
			dialog.Filter = "Mesh files (*.x)|*.x|All files (*.*)|*.*";
			dialog.InitialDirectory = @"..\..\..\Superstition\bin\meshes";
			dialog.Multiselect = false;
			dialog.RestoreDirectory = true;
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
				dialog.Dispose();
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
			this.file_box = new System.Windows.Forms.TextBox();
			this.label1 = new System.Windows.Forms.Label();
			this.change_button = new System.Windows.Forms.Button();
			this.label2 = new System.Windows.Forms.Label();
			this.conn_list = new System.Windows.Forms.ListView();
			this.x_num = new System.Windows.Forms.NumericUpDown();
			this.y_num = new System.Windows.Forms.NumericUpDown();
			this.z_num = new System.Windows.Forms.NumericUpDown();
			this.label3 = new System.Windows.Forms.Label();
			this.label4 = new System.Windows.Forms.Label();
			this.label5 = new System.Windows.Forms.Label();
			this.add_button = new System.Windows.Forms.Button();
			this.remove_button = new System.Windows.Forms.Button();
			this.up_button = new System.Windows.Forms.Button();
			this.down_button = new System.Windows.Forms.Button();
			this.left_button = new System.Windows.Forms.Button();
			this.right_button = new System.Windows.Forms.Button();
			this.scale_up_button = new System.Windows.Forms.Button();
			this.scale_down_button = new System.Windows.Forms.Button();
			this.clear_matrix_button = new System.Windows.Forms.Button();
			this.back_button = new System.Windows.Forms.Button();
			this.front_button = new System.Windows.Forms.Button();
			this.label6 = new System.Windows.Forms.Label();
			this.label7 = new System.Windows.Forms.Label();
			this.connect_box = new System.Windows.Forms.ComboBox();
			this.index_box = new System.Windows.Forms.ComboBox();
			this.var_box = new System.Windows.Forms.TextBox();
			this.label8 = new System.Windows.Forms.Label();
			this.edt_button = new System.Windows.Forms.Button();
			((System.ComponentModel.ISupportInitialize)(this.x_num)).BeginInit();
			((System.ComponentModel.ISupportInitialize)(this.y_num)).BeginInit();
			((System.ComponentModel.ISupportInitialize)(this.z_num)).BeginInit();
			this.SuspendLayout();
			// 
			// file_box
			// 
			this.file_box.Location = new System.Drawing.Point(64, 0);
			this.file_box.Name = "file_box";
			this.file_box.ReadOnly = true;
			this.file_box.Size = new System.Drawing.Size(184, 20);
			this.file_box.TabIndex = 0;
			this.file_box.Text = "<empty>";
			this.file_box.TextChanged += new System.EventHandler(this.file_box_TextChanged);
			// 
			// label1
			// 
			this.label1.Location = new System.Drawing.Point(0, 0);
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(64, 24);
			this.label1.TabIndex = 1;
			this.label1.Text = "Mesh:";
			this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
			// 
			// change_button
			// 
			this.change_button.Location = new System.Drawing.Point(256, 0);
			this.change_button.Name = "change_button";
			this.change_button.Size = new System.Drawing.Size(72, 24);
			this.change_button.TabIndex = 2;
			this.change_button.Text = "Chage";
			this.change_button.Click += new System.EventHandler(this.change_button_Click);
			// 
			// label2
			// 
			this.label2.Location = new System.Drawing.Point(0, 24);
			this.label2.Name = "label2";
			this.label2.Size = new System.Drawing.Size(72, 24);
			this.label2.TabIndex = 3;
			this.label2.Text = "Connections:";
			this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
			// 
			// conn_list
			// 
			this.conn_list.GridLines = true;
			this.conn_list.Location = new System.Drawing.Point(72, 24);
			this.conn_list.MultiSelect = false;
			this.conn_list.Name = "conn_list";
			this.conn_list.Size = new System.Drawing.Size(176, 56);
			this.conn_list.TabIndex = 4;
			this.conn_list.View = System.Windows.Forms.View.List;
			this.conn_list.SelectedIndexChanged += new System.EventHandler(this.conn_list_SelectedIndexChanged);
			// 
			// x_num
			// 
			this.x_num.DecimalPlaces = 3;
			this.x_num.Location = new System.Drawing.Point(24, 88);
			this.x_num.Maximum = new System.Decimal(new int[] {
																  5000,
																  0,
																  0,
																  0});
			this.x_num.Minimum = new System.Decimal(new int[] {
																  5000,
																  0,
																  0,
																  -2147483648});
			this.x_num.Name = "x_num";
			this.x_num.RightToLeft = System.Windows.Forms.RightToLeft.No;
			this.x_num.Size = new System.Drawing.Size(64, 20);
			this.x_num.TabIndex = 5;
			this.x_num.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
			// 
			// y_num
			// 
			this.y_num.DecimalPlaces = 3;
			this.y_num.Location = new System.Drawing.Point(112, 88);
			this.y_num.Maximum = new System.Decimal(new int[] {
																  5000,
																  0,
																  0,
																  0});
			this.y_num.Minimum = new System.Decimal(new int[] {
																  5000,
																  0,
																  0,
																  -2147483648});
			this.y_num.Name = "y_num";
			this.y_num.RightToLeft = System.Windows.Forms.RightToLeft.No;
			this.y_num.Size = new System.Drawing.Size(64, 20);
			this.y_num.TabIndex = 6;
			this.y_num.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
			// 
			// z_num
			// 
			this.z_num.DecimalPlaces = 3;
			this.z_num.Location = new System.Drawing.Point(200, 88);
			this.z_num.Maximum = new System.Decimal(new int[] {
																  5000,
																  0,
																  0,
																  0});
			this.z_num.Minimum = new System.Decimal(new int[] {
																  5000,
																  0,
																  0,
																  -2147483648});
			this.z_num.Name = "z_num";
			this.z_num.RightToLeft = System.Windows.Forms.RightToLeft.No;
			this.z_num.Size = new System.Drawing.Size(64, 20);
			this.z_num.TabIndex = 7;
			this.z_num.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
			// 
			// label3
			// 
			this.label3.Location = new System.Drawing.Point(8, 88);
			this.label3.Name = "label3";
			this.label3.Size = new System.Drawing.Size(16, 24);
			this.label3.TabIndex = 8;
			this.label3.Text = "x:";
			this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
			// 
			// label4
			// 
			this.label4.Location = new System.Drawing.Point(96, 88);
			this.label4.Name = "label4";
			this.label4.Size = new System.Drawing.Size(16, 24);
			this.label4.TabIndex = 9;
			this.label4.Text = "y:";
			this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
			// 
			// label5
			// 
			this.label5.Location = new System.Drawing.Point(184, 88);
			this.label5.Name = "label5";
			this.label5.Size = new System.Drawing.Size(16, 24);
			this.label5.TabIndex = 10;
			this.label5.Text = "z:";
			this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
			// 
			// add_button
			// 
			this.add_button.Location = new System.Drawing.Point(256, 32);
			this.add_button.Name = "add_button";
			this.add_button.Size = new System.Drawing.Size(72, 24);
			this.add_button.TabIndex = 11;
			this.add_button.Text = "Add";
			this.add_button.Click += new System.EventHandler(this.add_button_Click);
			// 
			// remove_button
			// 
			this.remove_button.Location = new System.Drawing.Point(256, 56);
			this.remove_button.Name = "remove_button";
			this.remove_button.Size = new System.Drawing.Size(72, 24);
			this.remove_button.TabIndex = 12;
			this.remove_button.Text = "Remove";
			this.remove_button.Click += new System.EventHandler(this.remove_button_Click);
			// 
			// up_button
			// 
			this.up_button.Location = new System.Drawing.Point(40, 136);
			this.up_button.Name = "up_button";
			this.up_button.Size = new System.Drawing.Size(24, 24);
			this.up_button.TabIndex = 13;
			this.up_button.Text = "^";
			this.up_button.Click += new System.EventHandler(this.up_button_Click);
			// 
			// down_button
			// 
			this.down_button.Location = new System.Drawing.Point(40, 184);
			this.down_button.Name = "down_button";
			this.down_button.Size = new System.Drawing.Size(24, 24);
			this.down_button.TabIndex = 14;
			this.down_button.Text = "¡";
			this.down_button.Click += new System.EventHandler(this.down_button_Click);
			// 
			// left_button
			// 
			this.left_button.Location = new System.Drawing.Point(16, 160);
			this.left_button.Name = "left_button";
			this.left_button.Size = new System.Drawing.Size(24, 24);
			this.left_button.TabIndex = 15;
			this.left_button.Text = "<";
			this.left_button.Click += new System.EventHandler(this.left_button_Click);
			// 
			// right_button
			// 
			this.right_button.Location = new System.Drawing.Point(64, 160);
			this.right_button.Name = "right_button";
			this.right_button.Size = new System.Drawing.Size(24, 24);
			this.right_button.TabIndex = 16;
			this.right_button.Text = ">";
			this.right_button.Click += new System.EventHandler(this.right_button_Click);
			// 
			// scale_up_button
			// 
			this.scale_up_button.Location = new System.Drawing.Point(104, 144);
			this.scale_up_button.Name = "scale_up_button";
			this.scale_up_button.Size = new System.Drawing.Size(24, 24);
			this.scale_up_button.TabIndex = 17;
			this.scale_up_button.Text = "+";
			this.scale_up_button.Click += new System.EventHandler(this.scale_up_button_Click);
			// 
			// scale_down_button
			// 
			this.scale_down_button.Location = new System.Drawing.Point(136, 144);
			this.scale_down_button.Name = "scale_down_button";
			this.scale_down_button.Size = new System.Drawing.Size(24, 24);
			this.scale_down_button.TabIndex = 18;
			this.scale_down_button.Text = "-";
			this.scale_down_button.Click += new System.EventHandler(this.scale_down_button_Click);
			// 
			// clear_matrix_button
			// 
			this.clear_matrix_button.Location = new System.Drawing.Point(80, 192);
			this.clear_matrix_button.Name = "clear_matrix_button";
			this.clear_matrix_button.Size = new System.Drawing.Size(80, 24);
			this.clear_matrix_button.TabIndex = 19;
			this.clear_matrix_button.Text = "Clear Matrix";
			this.clear_matrix_button.Click += new System.EventHandler(this.clear_matrix_button_Click);
			// 
			// back_button
			// 
			this.back_button.Location = new System.Drawing.Point(72, 128);
			this.back_button.Name = "back_button";
			this.back_button.Size = new System.Drawing.Size(24, 24);
			this.back_button.TabIndex = 20;
			this.back_button.Text = "B";
			this.back_button.Click += new System.EventHandler(this.back_button_Click);
			// 
			// front_button
			// 
			this.front_button.Location = new System.Drawing.Point(8, 192);
			this.front_button.Name = "front_button";
			this.front_button.Size = new System.Drawing.Size(24, 24);
			this.front_button.TabIndex = 21;
			this.front_button.Text = "F";
			this.front_button.Click += new System.EventHandler(this.front_button_Click);
			// 
			// label6
			// 
			this.label6.Location = new System.Drawing.Point(176, 112);
			this.label6.Name = "label6";
			this.label6.Size = new System.Drawing.Size(120, 16);
			this.label6.TabIndex = 22;
			this.label6.Text = "Connect to:";
			// 
			// label7
			// 
			this.label7.Location = new System.Drawing.Point(176, 152);
			this.label7.Name = "label7";
			this.label7.Size = new System.Drawing.Size(120, 16);
			this.label7.TabIndex = 23;
			this.label7.Text = "Index point:";
			// 
			// connect_box
			// 
			this.connect_box.Location = new System.Drawing.Point(176, 128);
			this.connect_box.Name = "connect_box";
			this.connect_box.Size = new System.Drawing.Size(152, 21);
			this.connect_box.TabIndex = 25;
			this.connect_box.SelectedIndexChanged += new System.EventHandler(this.connect_box_SelectedIndexChanged);
			// 
			// index_box
			// 
			this.index_box.Location = new System.Drawing.Point(176, 168);
			this.index_box.Name = "index_box";
			this.index_box.Size = new System.Drawing.Size(152, 21);
			this.index_box.TabIndex = 26;
			// 
			// var_box
			// 
			this.var_box.Location = new System.Drawing.Point(232, 192);
			this.var_box.Name = "var_box";
			this.var_box.Size = new System.Drawing.Size(88, 20);
			this.var_box.TabIndex = 27;
			this.var_box.Text = "";
			// 
			// label8
			// 
			this.label8.Location = new System.Drawing.Point(176, 192);
			this.label8.Name = "label8";
			this.label8.Size = new System.Drawing.Size(56, 24);
			this.label8.TabIndex = 28;
			this.label8.Text = "Variable:";
			this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
			// 
			// edt_button
			// 
			this.edt_button.Location = new System.Drawing.Point(272, 80);
			this.edt_button.Name = "edt_button";
			this.edt_button.Size = new System.Drawing.Size(56, 24);
			this.edt_button.TabIndex = 29;
			this.edt_button.Text = "Edit";
			this.edt_button.Click += new System.EventHandler(this.edt_button_Click);
			// 
			// PartEditor
			// 
			this.Controls.Add(this.edt_button);
			this.Controls.Add(this.label8);
			this.Controls.Add(this.var_box);
			this.Controls.Add(this.index_box);
			this.Controls.Add(this.connect_box);
			this.Controls.Add(this.label7);
			this.Controls.Add(this.label6);
			this.Controls.Add(this.front_button);
			this.Controls.Add(this.back_button);
			this.Controls.Add(this.clear_matrix_button);
			this.Controls.Add(this.scale_down_button);
			this.Controls.Add(this.scale_up_button);
			this.Controls.Add(this.right_button);
			this.Controls.Add(this.left_button);
			this.Controls.Add(this.down_button);
			this.Controls.Add(this.up_button);
			this.Controls.Add(this.remove_button);
			this.Controls.Add(this.add_button);
			this.Controls.Add(this.label5);
			this.Controls.Add(this.label4);
			this.Controls.Add(this.label3);
			this.Controls.Add(this.z_num);
			this.Controls.Add(this.y_num);
			this.Controls.Add(this.x_num);
			this.Controls.Add(this.conn_list);
			this.Controls.Add(this.label2);
			this.Controls.Add(this.change_button);
			this.Controls.Add(this.label1);
			this.Controls.Add(this.file_box);
			this.Name = "PartEditor";
			this.Size = new System.Drawing.Size(336, 224);
			((System.ComponentModel.ISupportInitialize)(this.x_num)).EndInit();
			((System.ComponentModel.ISupportInitialize)(this.y_num)).EndInit();
			((System.ComponentModel.ISupportInitialize)(this.z_num)).EndInit();
			this.ResumeLayout(false);

		}
		#endregion

		private CPart part;
		public CPart Part
		{
			get{ return part; }
			set
			{ 
				part = value as CPart; 
				CreateList();
			}
		}

		public void CreateList()
		{
			conn_list.Items.Clear();

			if(part == null)
			{
				file_box.Text = "";
				return;
			}

			for(int i = 0; i < part.connections.Length; i++)
			{
				conn_list.Items.Add(new ListViewItem(
					part.connections[i].ToString()));
			}

			file_box.Text = part.mesh_file;

			connect_box.Items.Clear();
			CModel model = GetData() as CModel;
			connect_box.Items.Add("null");
			foreach(CPart p in model.parts)
			{
				if(p == part) connect_box.Items.Add("[this]");
				else connect_box.Items.Add(p.ToString());
			}

			index_box.Items.Clear();
			index_box.Items.Add("null");
			var_box.Text = "";
		}

		public override void UpdateView(Updates update)
		{
			this.Part = (GetData() as CModel).Selected as CPart;
		}

		protected override void InitializeView()
		{
			
		}

		protected override void CloseView()
		{
			
		}

		private void remove_button_Click(object sender, System.EventArgs e)
		{
			if(part == null) return;
			CModel model = GetData() as CModel;
			ListView.SelectedIndexCollection indices 
				= conn_list.SelectedIndices;
			CConnection[] old = part.connections;
			CConnection[] _new = new CConnection[old.Length - indices.Count];

			bool real;
			int count = 0;

			for(int i = 0; i < old.Length; i++)
			{
				real = true;
				for(int j = 0; j < indices.Count; j++)
				{
					if(indices[j] == i) 
					{
						model.DeleteConnection(
							model.parts.IndexOf(part), i);
						real = false;
					}
				}
				if(real) _new[count++] = old[i];
			}

			part.connections = _new;
			CreateList();
			(GetData() as CModel).UpdateViews();
		}

		private void add_button_Click(object sender, System.EventArgs e)
		{
			if(part == null) return;
			CConnection[] old = part.connections;
			CConnection[] _new = new CConnection[old.Length + 1];

			for(int i = 0; i < old.Length; i++)
			{
				_new[i] = old[i];
			}

			int idx1, idx2;
			idx1 = connect_box.SelectedIndex - 1;
			idx2 = index_box.SelectedIndex - 1;

			if(idx1 >= 0)
			{
				CModel model = GetData() as CModel;
				if(model.parts.IndexOf(part) == idx1) 
				{
					idx2 = -1;
				}
				
				if(idx2 < 0) idx1 = -1;

			} else idx2 = -1;

			_new[old.Length] = new CConnection((float)x_num.Value, 
				(float)y_num.Value, (float)z_num.Value, idx1, idx2);
			if((var_box.Text != null) && (var_box.Text.Length > 0))
			{
				_new[old.Length].name = var_box.Text;
			}

			part.connections = _new;
			CreateList();
			(GetData() as CModel).UpdateViews();
		}

		private void file_box_TextChanged(object sender, System.EventArgs e)
		{
			if(part == null)
			{
				file_box.Text = "";
				return;
			}

			file_box.Text = part.mesh_file;
		}

		private void change_button_Click(object sender, System.EventArgs e)
		{
			if(part == null) return;

			DialogResult res = dialog.ShowDialog();
			if(res == DialogResult.Yes || res == DialogResult.OK)
			{
				part.mesh_file = Path.GetFileName(dialog.FileName);
				file_box.Text = part.mesh_file;
				part.SetMesh(MeshLibrary.Instance().
					LoadMesh(part.mesh_file));
				(GetData() as CModel).UpdateViews();
			}
		}

		private const float angle = 0.1f;

		private void ChangeMatrix(Matrix matrix, Side side)
		{
			if(part == null) return;
			CModel model = GetData() as CModel;
			if(side == Side.Right)
				part.model_transform[model.scene] =
					part.model_transform[model.scene] * matrix;
			else part.model_transform[model.scene] =
					 matrix * part.model_transform[model.scene];

			model.PreserveConnections(part);
			model.UpdateViews();
		}

		private void up_button_Click(object sender, System.EventArgs e)
		{
			ChangeMatrix(Matrix.RotationZ(angle), Side.Left);
		}

		private void down_button_Click(object sender, System.EventArgs e)
		{
			ChangeMatrix(Matrix.RotationZ(-angle), Side.Left);
		}

		private void left_button_Click(object sender, System.EventArgs e)
		{
			ChangeMatrix(Matrix.RotationY(angle), Side.Left);
		}

		private void right_button_Click(object sender, System.EventArgs e)
		{
			ChangeMatrix(Matrix.RotationY(-angle), Side.Left);
		}

		private void back_button_Click(object sender, System.EventArgs e)
		{
			ChangeMatrix(Matrix.RotationX(-angle), Side.Left);
		}

		private void front_button_Click(object sender, System.EventArgs e)
		{
			ChangeMatrix(Matrix.RotationX(angle), Side.Left);
		}

		private void scale_up_button_Click(object sender, System.EventArgs e)
		{
			ChangeMatrix(Matrix.Scaling(1.1f, 1.1f, 1.1f), Side.Left);
		}

		private void scale_down_button_Click(object sender, System.EventArgs e)
		{
			ChangeMatrix(Matrix.Scaling(1f/1.1f, 1f/1.1f, 1f/1.1f), Side.Left);
		}

		private void clear_matrix_button_Click(object sender, System.EventArgs e)
		{
			if(part == null) return;
			CModel model = GetData() as CModel;
			part.model_transform[model.scene] = Matrix.Identity;
			model.PreserveConnections(part);
			model.UpdateViews();
		}

		private void conn_list_SelectedIndexChanged(object sender, System.EventArgs e)
		{
			if(conn_list.SelectedIndices == null) return;
			if(conn_list.SelectedIndices.Count == 0) return;
			int idx = conn_list.SelectedIndices[0];
			if(part == null) return;
			CConnection conn = part.connections[idx];
			x_num.Value = (decimal)conn.point.X;
			y_num.Value = (decimal)conn.point.Y;
			z_num.Value = (decimal)conn.point.Z;

			connect_box.SelectedIndex = conn.object_index + 1;
			connect_box_SelectedIndexChanged(connect_box, null);
			index_box.SelectedIndex = conn.connection_index + 1;
			var_box.Text = conn.name;
		}

		private void connect_box_SelectedIndexChanged(object sender, System.EventArgs e)
		{
			if(part == null) return;
			int idx = connect_box.SelectedIndex - 1;
			index_box.Text = "";
			index_box.Items.Clear();
			index_box.Items.Add("null");
			if(idx < 0) return;
			CModel model = GetData() as CModel;
			CPart p = (CPart)model.parts[idx];

			foreach(CConnection con in p.connections)
			{
				index_box.Items.Add("x:" + con.point.X + 
					" y:" + con.point.Y + " z:" + con.point.Z);
			}
		}

		private void edt_button_Click(object sender, System.EventArgs e)
		{
			if(part == null) return;
			CModel model = GetData() as CModel;
			ListView.SelectedIndexCollection indices 
				= conn_list.SelectedIndices;
			CConnection[] old = part.connections;
			CConnection[] _new = new CConnection[old.Length];

			bool real;
			int count = 0;

			for(int i = 0; i < old.Length; i++)
			{
				real = true;
				for(int j = 0; j < indices.Count; j++)
				{
					if(indices[j] == i) 
					{
						_new[count] = new CConnection();
						_new[count].point.X = (float)x_num.Value;
						_new[count].point.Y = (float)y_num.Value;
						_new[count].point.Z = (float)z_num.Value;

						int idx1, idx2;
						idx1 = connect_box.SelectedIndex - 1;
						idx2 = index_box.SelectedIndex - 1;

						if(idx1 >= 0)
						{
							if(model.parts.IndexOf(part) == idx1) 
							{
								idx2 = -1;
							}
				
							if(idx2 < 0) idx1 = -1;

						} 
						else idx2 = -1;

						_new[count].object_index = idx1;
						_new[count].connection_index = idx2;
						if((var_box.Text != null) && (var_box.Text.Length > 0))
						{
							_new[count].name = var_box.Text;
						}

						real = false;
						count++;
					}
				}
				if(real) _new[count++] = old[i];
			}

			part.connections = _new;
			CreateList();
			(GetData() as CModel).UpdateViews();
		}






	}

	public enum Side
	{
		Left, Right
	}
}
