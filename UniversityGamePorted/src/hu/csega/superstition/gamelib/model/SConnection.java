package hu.csega.superstition.gamelib.model;

import org.joml.Vector3f;

import hu.csega.superstition.xml.XmlClass;
import hu.csega.superstition.xml.XmlField;

@XmlClass("Superstition.Connection")
public class SConnection implements SObject {

	public SConnection() {
	}

	public SConnection(float x, float y, float z, int objectIndex, int connectionIndex) {
		this.point = new Vector3f(x, y, z);
		this.objectIndex = objectIndex;
		this.connectionIndex = connectionIndex;
	}

	@XmlField("point")
	public Vector3f getPoint() {
		return point;
	}

	@XmlField("point")
	public void setPoint(Vector3f point) {
		this.point = point;
	}

	@XmlField("objectIndex")
	public int getObjectIndex() {
		return objectIndex;
	}

	@XmlField("objectIndex")
	public void setObjectIndex(int objectIndex) {
		this.objectIndex = objectIndex;
	}

	@XmlField("connectionIndex")
	public int getConnectionIndex() {
		return connectionIndex;
	}

	@XmlField("connectionIndex")
	public void setConnection_index(int connectionIndex) {
		this.connectionIndex = connectionIndex;
	}

	@XmlField("name")
	public String getName() {
		return name;
	}

	@XmlField("name")
	public void setName(String name) {
		this.name = name;
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

		return var_string + "x:" + ssx + " y:" + ssy + " z:" + ssz + " obj=" +
			objectIndex + " idx=" + connectionIndex;
	}

	private Vector3f point;
	private int objectIndex;
	private int connectionIndex;
	private String name;
}