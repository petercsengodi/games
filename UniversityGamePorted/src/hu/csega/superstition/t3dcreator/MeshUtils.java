package hu.csega.superstition.t3dcreator;

import hu.csega.superstition.gamelib.legacy.modeldata.CTriangle;
import hu.csega.superstition.gamelib.legacy.modeldata.CVertex;

public class MeshUtils {
	private SaveFileDialog dialog;

	protected Device device;
	protected Mesh mesh;
	protected ExtendedMaterial[] materials;
	protected int[] adjacency;
	protected int num_faces, num_vertices;

	public MeshUtils(Device device)
	{
		this.device = device;
		dialog = new SaveFileDialog();
		dialog.Filter = "Mesh Files (*.x)|*.x|All files (*.*)|*.*";
		dialog.InitialDirectory = @"..\..\..\Superstition\bin\meshes";
		dialog.RestoreDirectory = true;
	}

	public void Export(CModel model)
	{
		DialogResult res = dialog.ShowDialog();
		if(res == DialogResult.Cancel) return;
		if(res == DialogResult.No) return;

		num_faces = 0;
		num_vertices = 0;

		for(CFigure f : model.figures)
		{
			num_vertices += f.vertices.Count;
			num_faces += f.triangles.Count;
		}

		mesh = new Mesh(num_faces, num_vertices, MeshFlags.Managed,
				CustomVertex.PositionNormalTextured.Format, device);

		materials = null;

		BuildMesh(mesh, model);

		mesh.Save(dialog.FileName, adjacency, materials,
				XFileFormat.Text);
	}

	public void BuildMesh(Mesh mesh, CModel model)
	{
		int si = 0; // start index
		//		int sf = 0; // start face



		using(VertexBuffer vbuf = mesh.VertexBuffer)
		{
			GraphicsStream vb = vbuf.Lock(0, 0, LockFlags.None);

			for(CFigure f : model.figures)
			{

				for(CVertex v : f.vertices)
				{
					vb.Write(new CustomVertex.PositionNormalTextured(
							v.position,
							new Vector3f(0f, 0f, 0f),
							v.texture_coordinates.X,
							v.texture_coordinates.Y));
				}
			}

			vbuf.Unlock();
		}

		using(IndexBuffer ibuf = mesh.IndexBuffer)
		{
			GraphicsStream ib = ibuf.Lock(0, 0, LockFlags.None);

			for(CFigure f : model.figures)
			{

				for(CTriangle t : f.triangles)
				{
					ib.Write((short)( si +
							f.vertices.IndexOf(t.edges[0].from)));
					ib.Write((short)( si +
							f.vertices.IndexOf(t.edges[1].from)));
					ib.Write((short)( si +
							f.vertices.IndexOf(t.edges[2].from)));
				}

				si += f.vertices.Count;
			}

			ibuf.Unlock();
		}

		adjacency = new int[num_faces * 3];



		// mesh.GenerateAdjacency(float.Epsilon, adjacency);

		int tidx = 0;
		int start = 0;
		for(CFigure f : model.figures)
		{
			// for every triangle in figure set adjacency information
			for(CTriangle t : f.triangles)
			{
				// get neighbours
				adjacency[tidx*3 + 0] =	start +
						f.triangles.IndexOf(t.neighbours[0]);
				adjacency[tidx*3 + 1] =	start +
						f.triangles.IndexOf(t.neighbours[1]);
				adjacency[tidx*3 + 2] =	start +
						f.triangles.IndexOf(t.neighbours[2]);
				tidx++;
			}
			start += f.triangles.Count;
		}



		mesh.ComputeNormals(adjacency);
		materials = new ExtendedMaterial[model.figures.Count];

		//		AttributeRange[] ranges = new AttributeRange[model.figures.Count];
		//		si = sf = 0;

		for(int i = 0; i < model.figures.Count; i++)
		{
			CFigure f = model.figures[i] as CFigure;
			materials[i] = new ExtendedMaterial();
			if(f.texID != null)
			{
				materials[i].TextureFilename = f.texID.name;
			}
			else
			{
				materials[i].TextureFilename = null;
			}

			Material mat = new Material();
			mat.Ambient = Color.FromArgb(f.ambient_color);
			mat.Diffuse = Color.FromArgb(f.diffuse_color);
			mat.Emissive = Color.FromArgb(f.emissive_color);
			materials[i].Material3D = mat;

			//			ranges[i] = new AttributeRange();
			//			ranges[i].AttributeId = i;
			//			ranges[i].FaceCount = f.triangles.Count;
			//			ranges[i].FaceStart = sf;
			//			sf += f.triangles.Count;
			//			ranges[i].VertexCount = f.vertices.Count;
			//			ranges[i].VertexStart = si;
			//			si += f.vertices.Count;
		}

		//		mesh.SetAttributeTable(ranges);

		int index = 0;
		int[] array = mesh.LockAttributeBufferArray(LockFlags.Discard);
		//		GraphicsStream gs = mesh.LockAttributeBuffer(0);
		for(int i = 0; i < model.figures.Count; i++)
		{
			CFigure f = (model.figures[i] as CFigure);
			for(int j = 0; j < f.triangles.Count; j++)
			{
				array[index++] = i;
				//				gs.Write((byte)0x1);
			}
		}

		mesh.UnlockAttributeBuffer(array);
		mesh.Optimize(MeshFlags.OptimizeAttributeSort, adjacency);
		//		mesh.OptimizeInPlace(MeshFlags.OptimizeAttributeSort, adjacency);

	} // End of function



	public void dispose()
	{
		dialog.dispose();
	}




	public CFigure[] MeshToFigures(Mesh mesh, Device device,
			GraphicsStream adjacency)
	{
		Mesh temp = mesh.Clone(MeshFlags.Use32Bit,
				CustomVertex.PositionTextured.Format,
				device);
		AttributeRange[] ranges = temp.GetAttributeTable();
		CFigure[] ret = new CFigure[ranges.Length];
		int index1, index2, index3;

		using(VertexBuffer vbuf = temp.VertexBuffer)
		{
			CustomVertex.PositionTextured[] vertices =
					(CustomVertex.PositionTextured[])vbuf.Lock(0,
							typeof(CustomVertex.PositionTextured),
							LockFlags.ReadOnly, temp.NumberVertices);

			using(IndexBuffer ibuf = temp.IndexBuffer)
			{

				int[] indices = (int[])ibuf.Lock(
						0, typeof(int), LockFlags.ReadOnly,
						temp.NumberFaces * 3);

				for(int a = 0; a < ranges.Length; a++)
				{
					AttributeRange range = ranges[a];
					CFigure figure = new CFigure();

					for(int v = 0; v < range.VertexCount; v++)
					{
						CVertex vertex = new CVertex();
						CustomVertex.PositionTextured pt =
								vertices[v + range.VertexStart];
						vertex.position = pt.Position;
						vertex.texture_coordinates =
								new Vector2(pt.Tu, pt.Tv);
						figure.vertices.Add(vertex);
					}

					for(int i = 0; i < range.FaceCount; i++)
					{
						index1 = indices[range.FaceStart*3 + i*3  ] - range.VertexStart;
						index2 = indices[range.FaceStart*3 + i*3+1] - range.VertexStart;
						index3 = indices[range.FaceStart*3 + i*3+2] - range.VertexStart;
						CTriangle triangle = new CTriangle(
								figure.triangles[index1],
								figure.triangles[index2],
								figure.triangles[index3]);
						figure.triangles.Add(triangle);
					}

					// figure.CalculateNeighbours();

					for(int i = 0; i < range.FaceCount; i++)
					{
						index1 = (int)adjacency.Read(typeof(int)) - range.VertexStart;
						index2 = (int)adjacency.Read(typeof(int)) - range.VertexStart;
						index3 = (int)adjacency.Read(typeof(int)) - range.VertexStart;
						CTriangle triangle = figure.triangles[i] as CTriangle;
						triangle.neighbours[0] = figure.triangles[index1] as CTriangle;
						triangle.neighbours[1] = figure.triangles[index2] as CTriangle;
						triangle.neighbours[2] = figure.triangles[index3] as CTriangle;
					}

					ret[a] = figure;
				}

			}
		}


		temp.dispose();
		return ret;
	}

	public CFigure SubsetToFigures(Mesh mesh, Device device,
			GraphicsStream adjacency)
	{
		Mesh temp = mesh.Clone(MeshFlags.Use32Bit,
				CustomVertex.PositionTextured.Format,
				device);
		CFigure figure = new CFigure();
		int index1, index2, index3;

		using(VertexBuffer vbuf = temp.VertexBuffer)
		{
			CustomVertex.PositionTextured[] vertices =
					(CustomVertex.PositionTextured[])vbuf.Lock(0,
							typeof(CustomVertex.PositionTextured),
							LockFlags.ReadOnly, temp.NumberVertices);

			using(IndexBuffer ibuf = temp.IndexBuffer)
			{

				int[] indices = (int[])ibuf.Lock(
						0, typeof(int), LockFlags.ReadOnly,
						temp.NumberFaces * 3);

				for(int v = 0; v < temp.NumberVertices; v++)
				{
					CVertex vertex = new CVertex();
					CustomVertex.PositionTextured pt =
							vertices[v];
					vertex.position = pt.Position;
					vertex.texture_coordinates =
							new Vector2(pt.Tu, pt.Tv);
					figure.vertices.Add(vertex);
				}

				for(int i = 0; i < temp.NumberFaces; i++)
				{
					index1 = indices[i*3  ];
					index2 = indices[i*3+1];
					index3 = indices[i*3+2];
					CTriangle triangle = new CTriangle(
							figure.vertices[index1],
							figure.vertices[index2],
							figure.vertices[index3]);
					figure.triangles.Add(triangle);
				}

				// figure.CalculateNeighbours();

				for(int i = 0; i < temp.NumberFaces; i++)
				{
					index1 = (int)adjacency.Read(typeof(int));
					index2 = (int)adjacency.Read(typeof(int));
					index3 = (int)adjacency.Read(typeof(int));
					CTriangle triangle = figure.triangles[i] as CTriangle;
					triangle.neighbours[0] = figure.triangles[index1] as CTriangle;
					triangle.neighbours[1] = figure.triangles[index2] as CTriangle;
					triangle.neighbours[2] = figure.triangles[index3] as CTriangle;
				}


			}
		}


		temp.dispose();
		return figure;
	}

}