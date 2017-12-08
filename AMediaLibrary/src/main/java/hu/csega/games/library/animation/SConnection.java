package hu.csega.games.library.animation;

import org.joml.Vector3f;
import org.joml.Vector4f;

import hu.csega.games.library.model.SObject;
import hu.csega.games.library.xml.XmlClass;
import hu.csega.games.library.xml.XmlField;

@XmlClass("Superstition.Connection")
public class SConnection implements SObject {

	public SConnection() {
		this.point = new Vector4f(0f, 0f, 0f, 1f);
	}

	public SConnection(float x, float y, float z, int objectIndex, int connectionIndex) {
		this.point = new Vector4f(x, y, z, 1);
		this.objectIndex = objectIndex;
		this.connectionIndex = connectionIndex;
	}

	@XmlField("point")
	public Vector4f getPoint() {
		return point;
	}

	@XmlField("point")
	public void setPoint(Vector4f point) {
		this.point.set(point);
	}

	public void setPoint(Vector3f p) {
		this.point.set(p.x, p.y, p.z, 1f);
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
	public void setConnectionIndex(int connectionIndex) {
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
		String sw = String.valueOf(point.w());

		int px = sx.indexOf(',');
		int py = sy.indexOf(',');
		int pz = sz.indexOf(',');
		int pw = sz.indexOf(',');

		String ssx, ssy, ssz, ssw;
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
		if (pw >= 0)
			ssw = sw.substring(0, Math.min(pw + 4, sw.length()));
		else
			ssw = sw;

		String var_string = "";
		if ((name != null) && (name.length() > 0)) {
			var_string = name + " ";
		}

		return var_string + "x:" + ssx + " y:" + ssy +
				" z:" + ssz + " w:" + ssw + " obj=" +
				objectIndex + " idx=" + connectionIndex;
	}

	private Vector4f point;
	private int objectIndex;
	private int connectionIndex;
	private String name;
}