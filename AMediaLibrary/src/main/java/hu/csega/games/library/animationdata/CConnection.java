package hu.csega.games.library.animationdata;

import org.joml.Vector3f;

import hu.csega.games.library.xml.XmlClass;
import hu.csega.games.library.xml.XmlField;

@XmlClass("Superstition.CConnection")
public class CConnection {

	@XmlField("point")
	public Vector3f getPoint() {
		return point;
	}

	@XmlField("point")
	public void setPoint(Vector3f point) {
		this.point = point;
	}

	@XmlField("object_index")
	public int getObject_index() {
		return object_index;
	}

	@XmlField("object_index")
	public void setObject_index(int object_index) {
		this.object_index = object_index;
	}

	@XmlField("connection_index")
	public int getConnection_index() {
		return connection_index;
	}

	@XmlField("connection_index")
	public void setConnection_index(int connection_index) {
		this.connection_index = connection_index;
	}

	@XmlField("name")
	public String getName() {
		return name;
	}

	@XmlField("name")
	public void setName(String name) {
		this.name = name;
	}

	public Vector3f point;
	public int object_index;
	public int connection_index;
	public String name;

	public CConnection() {

	}

	public CConnection(float x, float y, float z, int object_index, int connection_index) {
		this.point = new Vector3f(x, y, z);
		this.object_index = object_index;
		this.connection_index = connection_index;
	}

	@Override
	public String toString() {
		String sx = String.valueOf(point.x());
		String sy = String.valueOf(point.y());
		String sz = String.valueOf(point.z());

		int px = sx.indexOf(',');
		int py = sy.indexOf(',');
		int pz = sz.indexOf(',');

		String ssx, ssy, ssz;
		if (px >= 0)
			ssx = sx.substring(0, Math.min(px + 4, sx.length()));
		else
			ssx = sx;
		if (py >= 0)
			ssy = sy.substring(0, Math.min(py + 4, sy.length()));
		else
			ssy = sy;
		if (pz >= 0)
			ssz = sz.substring(0, Math.min(pz + 4, sz.length()));
		else
			ssz = sz;

		String var_string = "";
		if ((name != null) && (name.length() > 0)) {
			var_string = name + " ";
		}

		return var_string + "x:" + ssx + " y:" + ssy + " z:" + ssz + " obj=" + object_index + " idx="
		+ connection_index;
	}

}