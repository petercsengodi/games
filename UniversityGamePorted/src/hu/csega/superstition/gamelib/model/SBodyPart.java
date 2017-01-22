package hu.csega.superstition.gamelib.model;

import org.joml.Matrix4f;
import org.joml.Vector3f;

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

	@XmlField("centerPoint")
	public Vector3f[] getCenterPoint() {
		return centerPoint;
	}

	@XmlField("centerPoint")
	public void setCenterPoint(Vector3f[] centerPoint) {
		this.centerPoint = centerPoint;
	}

	@XmlField("mesh")
	public SMeshRef getMesh() {
		return mesh;
	}

	@XmlField("mesh")
	public void setMesh(SMeshRef mesh) {
		this.mesh = mesh;
	}

	@XmlField("modelTransformation")
	public Matrix4f[] getModelTransformation() {
		return modelTransformation;
	}

	@XmlField("modelTransformation")
	public void setModelTransformation(Matrix4f[] modelTransformation) {
		this.modelTransformation = modelTransformation;
	}

	private SConnection[] connections;
	private Vector3f[] centerPoint;
	private SMeshRef mesh;
	private Matrix4f[] modelTransformation;
}