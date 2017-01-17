package hu.csega.superstition.animatool;

public class PartEditor extends CView {

	private static final float angle = 0.1f;

	// TODO: window

	private CPart part;

	public PartEditor()
	{
		dialog = new OpenFileDialog();
		dialog.Filter = "Mesh files (*.x)|*.x|All files (*.*)|*.*";
		dialog.InitialDirectory = @"..\..\..\Superstition\bin\meshes";
		dialog.Multiselect = false;
		dialog.RestoreDirectory = true;
	}
	public CPart getPart() {
		return part;
	}

	public void setPart(CPart part) {
		this.part = part;
		createList();
	}

	public void createList()
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

	public override void updateView(Updates update)
	{
		this.Part = (GetData() as CModel).Selected as CPart;
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
