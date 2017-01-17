package hu.csega.superstition.game.menu;

class Frame : IDisposable
{
	Vector3[] side = new Vector3[2]{
									   new Vector3(-4f, -0.75f, 0f),
									   new Vector3(4f, 1.25f, 0f)
								   };
	float ext = 0.5f;

	Primitive[] triangles;

	public Frame(ModelParams param)
	{
		triangles = new Primitive[32];
		Vector3[] v = new Vector3[16];

		int outer, front, left, up;
		int UP = 1, LEFT = 2, FRONT = 4, OUTER = 8;

		for(outer = 0; outer <= 1; outer++)
			for(front = 0; front <= 1; front++)
				for(left = 0; left <= 1; left++)
					for(up = 0; up <= 1; up++)
					{
						v[outer*OUTER + front*FRONT + left*LEFT + up*UP] =
							new Vector3(
							(float)(side[left].X + outer * ext * ((left==1)?1:-1)),
							(float)(side[up].Y + (1 - outer) * ext * ((up==1)?-1:1)),
							(float)(side[0].Z - front * ext)
							);
					}

		Engine engine = param.engine;

		// Front part
		triangles[0] = engine.Pr_TesselatedTriangle(
			v[FRONT+LEFT+UP+OUTER], v[FRONT+UP], v[FRONT+UP+OUTER], @"..\textures\menu_textures\wood.bmp");
		triangles[1] = engine.Pr_TesselatedTriangle(
			v[FRONT+UP+LEFT], v[FRONT+UP], v[FRONT+LEFT+UP+OUTER], @"..\textures\menu_textures\wood.bmp");
		triangles[2] = engine.Pr_TesselatedTriangle(
			v[FRONT+LEFT+OUTER], v[FRONT+OUTER], v[FRONT], @"..\textures\menu_textures\wood.bmp");
		triangles[3] = engine.Pr_TesselatedTriangle(
			v[FRONT], v[FRONT+LEFT], v[FRONT+LEFT+OUTER], @"..\textures\menu_textures\wood.bmp");
		triangles[4] = engine.Pr_TesselatedTriangle(
			v[FRONT+LEFT+UP+OUTER], v[FRONT+LEFT], v[FRONT+UP+LEFT], @"..\textures\menu_textures\wood.bmp");
		triangles[5] = engine.Pr_TesselatedTriangle(
			v[FRONT+LEFT+OUTER], v[FRONT+LEFT], v[FRONT+LEFT+UP+OUTER], @"..\textures\menu_textures\wood.bmp");
		triangles[6] = engine.Pr_TesselatedTriangle(
			v[FRONT+UP+OUTER], v[FRONT+UP], v[FRONT], @"..\textures\menu_textures\wood.bmp");
		triangles[7] = engine.Pr_TesselatedTriangle(
			v[FRONT], v[FRONT+OUTER], v[FRONT+UP+OUTER], @"..\textures\menu_textures\wood.bmp");

		// Back part
		triangles[8] = engine.Pr_TesselatedTriangle(
			v[LEFT+UP+OUTER], v[UP+OUTER], v[UP], @"..\textures\menu_textures\wood.bmp");
		triangles[9] = engine.Pr_TesselatedTriangle(
			v[UP], v[UP+LEFT], v[LEFT+UP+OUTER], @"..\textures\menu_textures\wood.bmp");
		triangles[10] = engine.Pr_TesselatedTriangle(
			v[LEFT+OUTER], v[0], v[OUTER], @"..\textures\menu_textures\wood.bmp");
		triangles[11] = engine.Pr_TesselatedTriangle(
			v[LEFT], v[0], v[LEFT+OUTER], @"..\textures\menu_textures\wood.bmp");
		triangles[12] = engine.Pr_TesselatedTriangle(
			v[LEFT+UP+OUTER], v[UP+LEFT], v[LEFT], @"..\textures\menu_textures\wood.bmp");
		triangles[13] = engine.Pr_TesselatedTriangle(
			v[LEFT], v[LEFT+OUTER], v[LEFT+UP+OUTER], @"..\textures\menu_textures\wood.bmp");
		triangles[14] = engine.Pr_TesselatedTriangle(
			v[UP+OUTER], v[0], v[UP], @"..\textures\menu_textures\wood.bmp");
		triangles[15] = engine.Pr_TesselatedTriangle(
			v[OUTER], v[0], v[UP+OUTER], @"..\textures\menu_textures\wood.bmp");

		// Inner Tile
		triangles[16] = engine.Pr_TesselatedTriangle(
			v[FRONT+LEFT+UP], v[LEFT], v[LEFT+UP], @"..\textures\menu_textures\wood.bmp");
		triangles[17] = engine.Pr_TesselatedTriangle(
			v[FRONT+LEFT], v[LEFT], v[FRONT+UP+LEFT], @"..\textures\menu_textures\wood.bmp");
		triangles[18] = engine.Pr_TesselatedTriangle(
			v[FRONT+UP], v[UP], v[0], @"..\textures\menu_textures\wood.bmp");
		triangles[19] = engine.Pr_TesselatedTriangle(
			v[0], v[FRONT], v[FRONT+UP], @"..\textures\menu_textures\wood.bmp");
		triangles[20] = engine.Pr_TesselatedTriangle(
			v[FRONT+LEFT+UP], v[LEFT+UP], v[UP], @"..\textures\menu_textures\wood.bmp");
		triangles[21] = engine.Pr_TesselatedTriangle(
			v[UP], v[FRONT+UP], v[FRONT+UP+LEFT], @"..\textures\menu_textures\wood.bmp");
		triangles[22] = engine.Pr_TesselatedTriangle(
			v[FRONT+LEFT], v[0], v[LEFT], @"..\textures\menu_textures\wood.bmp");
		triangles[23] = engine.Pr_TesselatedTriangle(
			v[FRONT], v[0], v[FRONT+LEFT], @"..\textures\menu_textures\wood.bmp");

		// Outer Tile
		triangles[24] = engine.Pr_TesselatedTriangle(
			v[FRONT+LEFT+OUTER], v[LEFT+OUTER+UP], v[LEFT+OUTER], @"..\textures\menu_textures\wood.bmp");
		triangles[25] = engine.Pr_TesselatedTriangle(
			v[LEFT+OUTER+UP], v[LEFT+OUTER+FRONT], v[FRONT+UP+LEFT+OUTER], @"..\textures\menu_textures\wood.bmp");
		triangles[26] = engine.Pr_TesselatedTriangle(
			v[FRONT+UP+OUTER], v[OUTER], v[UP+OUTER], @"..\textures\menu_textures\wood.bmp");
		triangles[27] = engine.Pr_TesselatedTriangle(
			v[FRONT+OUTER], v[OUTER], v[FRONT+UP+OUTER], @"..\textures\menu_textures\wood.bmp");
		triangles[28] = engine.Pr_TesselatedTriangle(
			v[FRONT+LEFT+UP+OUTER], v[UP+OUTER], v[LEFT+UP+OUTER], @"..\textures\menu_textures\wood.bmp");
		triangles[29] = engine.Pr_TesselatedTriangle(
			v[FRONT+UP+OUTER], v[UP+OUTER], v[FRONT+UP+LEFT+OUTER], @"..\textures\menu_textures\wood.bmp");
		triangles[30] = engine.Pr_TesselatedTriangle(
			v[FRONT+LEFT+OUTER], v[LEFT+OUTER], v[OUTER], @"..\textures\menu_textures\wood.bmp");
		triangles[31] = engine.Pr_TesselatedTriangle(
			v[OUTER], v[FRONT+OUTER], v[FRONT+LEFT+OUTER], @"..\textures\menu_textures\wood.bmp");

		for(int i = 0; i < 32; i++)
		{
			triangles[i].NotEffectedByLight = true;
		}
	}

	public void Render(Vector3 translation)
	{
		for(int i = 0; i < triangles.Length; i++)
		{
			triangles[i].Render(translation);
		}
	}

	#region IDisposable Members

	public void Dispose()
	{
		for(int i = 0; i < triangles.Length; i++)
		{
			triangles[i].Dispose();
		}
	}

	#endregion
}