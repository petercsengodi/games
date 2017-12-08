package hu.csega.games.library.animation;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import hu.csega.games.library.model.SMeshRef;
import hu.csega.games.library.model.SObject;
import hu.csega.games.library.xml.XmlClass;
import hu.csega.games.library.xml.XmlField;

@XmlClass("Superstition.SBodyPart")
public class SBodyPart implements SObject {

	private SConnection[] connections;
	private Vector4f[] centerPoints;
	private SMeshRef mesh;
	private Matrix4f[] modelTransformations;

	@XmlField("connections")
	public SConnection[] getConnections() {
		return connections;
	}

	@XmlField("connections")
	public void setConnections(SConnection[] connections) {
		this.connections = connections;
	}

	@XmlField("centerPoints")
	public Vector4f[] getCenterPoints() {
		return centerPoints;
	}

	@XmlField("centerPoints")
	public void setCenterPoints(Vector4f[] centerPoints) {
		this.centerPoints = centerPoints;
	}

	public void setCenterPoints(Vector3f[] centerPoints) {
		if(centerPoints == null) {
			this.centerPoints = null;
			return;
		}

		this.centerPoints = new Vector4f[centerPoints.length];
		for(int i = 0; i < centerPoints.length; i++) {
			Vector3f c = centerPoints[i];
			this.centerPoints[i] = new Vector4f(c.x, c.y, c.z, 1f);
		}
	}

	@XmlField("mesh")
	public SMeshRef getMesh() {
		return mesh;
	}

	@XmlField("mesh")
	public void setMesh(SMeshRef mesh) {
		this.mesh = mesh;
	}

	@XmlField("modelTransformations")
	public Matrix4f[] getModelTransformations() {
		return modelTransformations;
	}

	@XmlField("modelTransformations")
	public void setModelTransformations(Matrix4f[] modelTransformations) {
		this.modelTransformations = modelTransformations;
	}
}