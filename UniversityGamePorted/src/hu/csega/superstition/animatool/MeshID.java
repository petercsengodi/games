package hu.csega.superstition.animatool;

public class MeshID
{
	public string name;
	private Mesh mesh, simple_mesh;
	private Material[] materials;
	private TexID[] textures;
	private int subsets;

	public Mesh getMesh() {
		return mesh;
	}

	public Mesh getSimpleMesh() {
		return simple_mesh;
	}

	public int getSubsets() {
		return subsets;
	}

	public Material[] getMaterials() {
		return materials;
	}

	public TexID[] getTextures() {
		return textures;
	}

	public MeshID(string name, Mesh mesh, Mesh simple_mesh, Material[] materials, TexID[] textures, int subsets) {
		this.name = name;
		this.mesh = mesh;
		this.simple_mesh = simple_mesh;
		this.materials = materials;
		this.textures = textures;
		this.subsets = subsets;
	}

	private MeshID() {
		this.name = null;
		this.mesh = null;
		this.materials = null;
		this.textures = null;
		this.subsets = 0;
	}

	public static Object GetNullInstance() {
		return new MeshID();
	}

}