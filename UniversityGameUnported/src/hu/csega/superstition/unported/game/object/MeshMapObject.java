package hu.csega.superstition.game.object;

import org.joml.Vector3f;

import hu.csega.superstition.gamelib.network.GameObjectData;
import hu.csega.superstition.unported.game.Library;

public class MeshMapObject extends MapObject {

	protected Element element;
	protected float Angle;
	protected String mesh;

	public MeshMapObject(String mesh, Vector3f position) {
		super(position, position);
		this.position = position;
		this.Angle = 0f;
		this.mesh = mesh;
	}

	protected class MeshMapObjectData extends GameObjectData {

		public float angle;
		public Vector3f position;
		public String mesh;

		public MeshMapObjectData() {
			description = "Mesh Map Object";
		}

		public Object create() {
			return new MeshMapObject(this);
		}

	}

	public MeshMapObject(GameObjectData data) {
		super(new Vector3f(), new Vector3f());
		MeshMapObjectData d = (MeshMapObjectData)data;
		this.Angle = d.angle;
		this.position = d.position;
		this.mesh = d.mesh;
		element = Library.Meshes().getMesh(mesh);
	}

	@Override
	public GameObjectData getData() {
		MeshMapObjectData ret = new MeshMapObjectData();
		ret.angle = this.Angle;
		ret.mesh = this.mesh;
		ret.position = this.position;
		return ret;
	}

	public void Build(Engine engine) {
		element = Library.Meshes().getMesh(mesh);
	}

	@Override
	public void Render() {
		element.Render(position, 0f, Angle, 0f);
	}
}