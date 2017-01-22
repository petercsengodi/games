package hu.csega.superstition.gamelib.model.animation;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import hu.csega.superstition.gamelib.model.SObject;
import hu.csega.superstition.xml.XmlClass;
import hu.csega.superstition.xml.XmlField;

@XmlClass("Superstition.SBodyPart")
public class SBodyPart implements SObject {

	@XmlField("connections")
	public SConnection[] getConnections() {
		return connections;
	}

	@XmlField("connections")
	public void setConnections(SConnection[] connections) {
		this.connections = connections;
	}

	@XmlField("centerPoints")
	public Vector3f[] getCenterPoints() {
		return centerPoints;
	}

	@XmlField("centerPoints")
	public void setCenterPoints(Vector3f[] centerPoints) {
		this.centerPoints = centerPoints;
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

	private SConnection[] connections;
	private Vector3f[] centerPoints;
	private SMeshRef mesh;
	private Matrix4f[] modelTransformations;
}